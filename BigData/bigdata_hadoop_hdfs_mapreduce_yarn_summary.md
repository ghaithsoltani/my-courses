# Big Data Study Guide: Hadoop, HDFS, MapReduce & YARN

*Based on the course by Dr. Maher HENI, Data Science Foundations*

---

## Table of Contents

**Part 1: Hadoop & HDFS**
1. Why Hadoop?
2. What is Hadoop?
3. HDFS principles & architecture
4. Blocks and replication
5. Rack awareness
6. File compression
7. NameNode startup, edits log, fsimage
8. Writing a file to HDFS
9. Cluster management
10. NameNode High Availability, Secondary NameNode, Federation
11. HDFS shell commands
12. Part 1 checkpoint

**Part 2: MapReduce & YARN**
1. MapReduce v1 model
2. Phases: Map, Shuffle, Reduce, Combiner
3. WordCount example (+ Java code)
4. Splits and input classes
5. How a job runs (MRv1) & fault tolerance
6. Limits of MRv1
7. YARN
8. Hadoop 1 vs Hadoop 2
9. Spark and Ambari
10. Part 2 checkpoint

---

# PART 1: HADOOP & HDFS

## 1. Why Hadoop?

**The big idea:** when data is too big for one machine, you spread it across many cheap machines (a *cluster*) and **bring the computation to the data** instead of moving the data to the computation.

- Reading a huge file from one disk is slow. Reading it **in parallel** from many disks is fast.
- Network speed between nodes matters, so Hadoop tries to avoid moving data around.
- Context from the slides: the Big Data market was forecast to grow from about **$5.4B (2012) to $53.4B (2017)** (Wikibon). Hortonworks predicted that more than half of the world's data would be stored in Hadoop within 5 years.

### Hadoop is good for
- Processing **huge amounts of data** in parallel
- **Variety** of data: structured, semi-structured, unstructured
- Running on **cheap commodity hardware**

### Hadoop is NOT good for
- **Transaction processing** (random access, like a bank system)
- Jobs that **cannot be parallelized**
- **Low-latency** data access
- Handling **many small files**
- Compute-intensive work on **small amounts of data**

> **Remember:** Hadoop = **batch processing** of big data, not OLTP.

---

## 2. What is Hadoop?

An **Apache open-source framework** (written in **Java**) for **distributed, scalable, reliable** computing on large data. It hides the complexity of the underlying system from the user and is designed for heterogeneous hardware.

### The 4 core modules
| Module | Role |
|---|---|
| **HDFS** | Distributed storage (where data lives) |
| **MapReduce** | Processing model (how work is computed) |
| **YARN** | Resource management and scheduling |
| **Hadoop Common** | Shared libraries and utilities |

### The ecosystem (open source projects around Hadoop)
HBase, Hive, Pig, Spark, Kafka, NiFi, Mahout, Impala, ZooKeeper, Avro, Ambari...

---

## 3. HDFS: Hadoop Distributed File System

### Core principles
- **Distributed, scalable, fault-tolerant, high throughput**
- Data accessed through **MapReduce**
- Files are **split into blocks**
- **3 replicas** of each block by default
- You can **create, delete, copy**, but **cannot update** files (write-once, read-many)
- Designed for **streaming reads**, not random access
- **Data locality:** process data on or near where it is stored to reduce network transfer

### Architecture: Master / Slave

```
                 NameNode  (master)
                 FsImage + Edits Log, metadata only
                /     |      |      \
         DataNode DataNode DataNode DataNode  (slaves)
          blocks   blocks   blocks   blocks
```

| | NameNode (Master) | DataNode (Slave) |
|---|---|---|
| Count | 1 active per cluster (2 for HA) | Many per cluster |
| Stores | **Metadata**: file names, which blocks, where | The **actual data blocks** |
| Files | **FsImage** (snapshot) + **Edits Log** (changes) | Block files in its data directory |
| Role | Manages **client access** and namespace | Reports its status **periodically (heartbeat)** to the NameNode |

---

## 4. Blocks and Replication

### Blocks
- Each file is split into blocks. **Default block size = 128 MB**.
- Blocks live on different physical DataNodes.
- If the last chunk is smaller than 128 MB, it only uses the space it needs.
- **Example:** a 410 MB file becomes **128 + 128 + 128 + 26 MB** (4 blocks).

### Replication
- Replication factor is **configurable per file**; default = **3**.
- **Placement strategy (rack-aware):**
  1. **1st replica:** on any node in the cluster
  2. **2nd replica:** on a **different node in the same rack**
  3. **3rd replica:** on a **different node in a second rack**
- This balances **fault tolerance** (survives a rack failure) with **network cost**.

---

## 5. Rack Configuration

- A "rack" here means the **network switch** a node is connected to, not the physical metal frame.
- Defined through a **topology script**, referenced in `core-site.xml`:

```xml
<property>
  <name>net.topology.script.file.name</name>
  <value>/etc/hadoop/conf/rack-topology.sh</value>
</property>
```

- The script receives node IP addresses as arguments and returns **rack names** on stdout (one per IP).
- Simple trick: use IPs like `10.x.y.z` where **x = cluster, y = rack, z = node**, and decode `y/z` in the script.

---

## 6. File Compression

**Benefits:** less storage space and faster network/disk transfer.
**Key question:** is the format **splittable**? (needed for parallel reading)

| Format | Algorithm | Extension | Splittable? |
|---|---|---|---|
| DEFLATE | DEFLATE | .deflate | No |
| gzip | DEFLATE | .gz | No |
| bzip2 | bzip2 | .bz2 | **Yes** |
| LZO | LZO | .lzo | Yes, **if indexed** |
| LZ4 | LZ4 | .lz4 | No |
| Snappy | Snappy | .snappy | No |

### Which to choose? (best to worst)
1. Use a **container format** (SequenceFile, Avro, ORC, Parquet)
2. Use a **fast compressor** (LZO, LZ4, Snappy)
3. Use a **splittable** format (bzip2, which is slow, or indexed LZO)
4. **Split the file into chunks** and compress each separately (chunk ≈ HDFS block size)
5. Store **uncompressed**

> The right choice depends on file size, data format, and the tools used.

---

## 7. NameNode Startup

1. NameNode **reads fsimage** into memory.
2. NameNode **applies the edits log** changes.
3. NameNode **waits for block reports** from DataNodes.
   - It does **not** store block locations persistently; it rebuilds them from DataNodes.
   - It leaves **SafeMode** when **99.9% of blocks** have at least one reported copy.

Stored in the NameNode directory (`namedir`): `fsimage_*` and many `edits_*` files.

---

## 8. Writing a File to HDFS (Replication Mechanism)

1. Client contacts the **NameNode**, which decides **which DataNodes** will store each block's replicas.
2. NameNode records the new file in memory and in the **edits log**.
3. Client API sends a block to the **first DataNode**.
4. First DataNode **daisy-chains**: writes to the 2nd, the 2nd writes to the 3rd (with acknowledgments back along the chain).
5. First DataNode **confirms to the NameNode** that replication is complete.

> The write succeeds if at least one write per block succeeds.

---

## 9. Cluster Management

| Task | How |
|---|---|
| **Add a DataNode** | Start a new DataNode pointing to the NameNode; run `hadoop balancer` to rebalance blocks |
| **Remove a node** | Just remove it, or better: add it to the *exclude file* and wait until all its blocks are moved |
| **Web UI** | NameNode admin console on port **50070** |
| **Check file system health** | `hadoop fsck` |

---

## 10. NameNode: HA, Secondary, Federation

### 10.1 HDFS-2 NameNode High Availability (HA)
- **Active NameNode + Standby NameNode** for fast failover.
- Every file system change is written to **at least 3 JournalNodes** (quorum) by the Active NN.
- Standby applies changes from the JournalNodes in real time.
- **DataNodes send block locations and heartbeats to both** NameNodes.
- Failover is **much faster than a cold start**.

### 10.2 Secondary NameNode
- Primary NN **cannot merge fsimage + edits log while running**. The Secondary does it:
  1. Copies new edits log from the primary
  2. Merges it into fsimage
  3. Copies the new merged fsimage back to the primary
- **NOT a high-availability solution**, but gives **faster startup**. It has no complete image, so in-flight transactions would be lost.
- Less necessary with HA, but still has perks: no quorum nodes, less network traffic, fewer moving parts.

### 10.3 Setup options
- **Hadoop 2 with HA:** no single point of failure (best, widely supported).
- **Hadoop 2 without HA / Hadoop 1.x:** copy `namedir` to NFS (RAID), use a virtual IP for the NameNode backup. Failover still needs time to read blocks.

### 10.4 Federated NameNode (HDFS-2)
- **Problem:** the NameNode can become a **bottleneck** on huge clusters (about **1 GB of RAM per 1 million blocks** or ~100 TB of data).
- **Solution:** multiple NameNodes, each managing its own **namespace / block pool**. DataNodes register with all NNs and store data for all pools.
- A namespace + its block pool = **Namespace Volume**. A new **ClusterID** identifies all NNs.
- **Benefits:** one NN failure doesn't affect other pools, and better scalability for many file operations.

---

## 11. HDFS Shell Commands (`hadoop fs`)

```bash
hadoop fs -ls .                                  # list current HDFS directory
hadoop fs -mkdir /newdir                         # create directory
hadoop fs -put localsrc dst                      # local -> HDFS
hadoop fs -copyFromLocal localsrc dst            # same as put
hadoop fs -get <src> <localdst>                  # HDFS -> local
hadoop fs -copyToLocal <src> <localdst>          # same as get
```

- Commands take **URIs**: `scheme://authority/path`
  - Local file system: `file:///myfile.txt`
  - HDFS: `hdfs://localhost:9000/user/virtuser/myfile.txt`
- **POSIX-like:** `cat, chgrp, chmod, chown, cp, du, ls, mkdir, mv, rm, stat, tail`
- **HDFS-specific:** `copyFromLocal, put, copyToLocal, get, getmerge, setrep`

---

## 12. Part 1 Checkpoint (with answers)

| Question | Answer |
|---|---|
| True/False: Hadoop is designed for transaction processing. | **False.** It's built for **batch** processing. |
| Name some Hadoop open-source projects. | MapReduce, YARN, Ambari, Hive, HBase... |
| Default number of replicas? | **3** |
| True/False: data is brought to the program. | **False.** The **program is brought to the data**. |
| True/False: at least 2 NameNodes are required for a standalone cluster. | **False.** **1** is required; **2 are needed for HA**. |

---

# PART 2: MAPREDUCE & YARN

## 1. MapReduce v1: The Model

- Data is stored in a **distributed file system** (HDFS).
- **Bring the function to the data**, and distribute the application to the nodes holding the data.
- Scales to **thousands of nodes and petabytes** of data.

### The 3 steps
1. **Map phase:** break the job into small parts
2. **Shuffle:** transfer intermediate output for final processing
3. **Reduce phase:** boil all outputs down to a single result set

### MRv1 engine: Master / Slave
| Component | Role |
|---|---|
| **JobTracker** (1 master) | Accepts jobs from clients, splits them into map/reduce tasks, sends tasks to TaskTrackers **close to the data**, monitors tasks and TaskTracker health |
| **TaskTracker** (many slaves) | Runs map and reduce tasks, reports status to the JobTracker, manages storage and transmission of intermediate output |

### API vs execution environment
- **APIs:** `org.apache.hadoop.mapred` (old) and `org.apache.hadoop.mapreduce` (new, more flexible, may need recompiling for YARN).
- **Execution environments:** classic JobTracker/TaskTracker (Hadoop 1) or **YARN** (MapReduce v2).

---

## 2. The MapReduce Phases in Detail

### 2.1 Map phase
- **Mappers** are small programs, distributed over the cluster and **local to the data**.
- Each gets **one split** of the input, then parses, filters or transforms it.
- Output = grouped **`<key, value>` pairs**.
- Mapper output is **sorted** and stored on the node's **local file system** (not HDFS).

### 2.2 Shuffle phase
- Mapper output is **grouped locally by key**.
- One node is chosen to process **each unique key**.
- Data movement is **handled transparently** by MapReduce.
- Distribution uses a **partitioner** (normally a **hash partitioner**).
- The number of map and reduce tasks **do not need to match**.

### 2.3 Reduce phase
- **Reducers** gather **all values for the keys they are responsible for**.
- Each reducer writes its output to **its own file** in HDFS (hence replicated).
- Default: **one output part-file per reduce task**.

### 2.4 Combiner (optional)
- A **mini-reduce on the map side**, before the shuffle.
- Sorts and merges data **before** sending it to reducers, which **minimizes network traffic** and reduces merge effort.
- Example: `<Tiger,1>, <Tiger,1>` on one node becomes `<Tiger,2>` before being sent.

```
HDFS blocks -> [Map] -> sort (& combine) -> copy/Shuffle -> merge -> [Reduce] -> HDFS
```

---

## 3. WordCount Example (Animals)

**Goal:** count how often each animal appears (SQL equivalent: `SELECT COUNT(NAME) FROM ANIMALS GROUP BY NAME`).

| Step | Node 1 input: Tiger, Lion, Lion, Panther, Wolf | Node 2 input: Tiger, Tiger, Wolf, Panther |
|---|---|---|
| **Map** | `<Tiger,1> <Lion,1> <Lion,1> <Panther,1>...` | `<Tiger,1> <Tiger,1> <Panther,1>...` |
| **Shuffle** | All values of one key go to the same reducer | |
| **Reduce** | `<Panther,<1,1>>` -> `<Panther,2>`<br>`<Tiger,<1,1,1>>` -> `<Tiger,3>` | `<Lion,<1,1>>` -> `<Lion,2>` (on another node) |

Map tasks have 2 jobs: **filter rows** and **transform into `<Text(name), Integer(1)>`**.

### Java code (old `mapred` API)

**Mapper**
```java
public static class Map extends MapReduceBase
    implements Mapper<LongWritable, Text, Text, IntWritable> {
  private final static IntWritable one = new IntWritable(1);
  private Text word = new Text();

  public void map(LongWritable key, Text value,
      OutputCollector<Text, IntWritable> output, Reporter reporter)
      throws IOException {
    String line = value.toString();
    StringTokenizer tokenizer = new StringTokenizer(line);
    while (tokenizer.hasMoreTokens()) {
      word.set(tokenizer.nextToken());
      output.collect(word, one);          // emit <word, 1>
    }
  }
}
```

**Reducer**
```java
public static class Reduce extends MapReduceBase
    implements Reducer<Text, IntWritable, Text, IntWritable> {
  public void reduce(Text key, Iterator<IntWritable> values,
      OutputCollector<Text, IntWritable> output, Reporter reporter)
      throws IOException {
    int sum = 0;
    while (values.hasNext()) {
      sum += values.next().get();         // add up the 1s
    }
    output.collect(key, new IntWritable(sum));
  }
}
```

**Driver (main)**
```java
JobConf conf = new JobConf(WordCount.class);
conf.setJobName("wordcount");
conf.setOutputKeyClass(Text.class);
conf.setOutputValueClass(IntWritable.class);
conf.setMapperClass(Map.class);
conf.setCombinerClass(Reduce.class);   // Reducer reused as Combiner
conf.setReducerClass(Reduce.class);
conf.setInputFormat(TextInputFormat.class);
conf.setOutputFormat(TextOutputFormat.class);
FileInputFormat.setInputPaths(conf, new Path(args[0]));
FileOutputFormat.setOutputPath(conf, new Path(args[1]));
JobClient.runJob(conf);
```

**Three pieces to always remember:** Mapper class + Reducer class + Driver (job configuration).

---

## 4. Splits and Input Classes

- Files are stored in 128 MB blocks; MapReduce divides data into **splits**. **One Map task runs per split.**
- Splits normally match the block size, but depend on requested map tasks, compression splittability, etc.
- Goal: **process as much data locally as possible**.

| Class | Job |
|---|---|
| **InputSplitter** | Takes an HDFS file and turns it into splits |
| **RecordReader** | Reads a split into records (e.g. one record per line, `LineRecordReader`). A record may span several splits |
| **InputFormat** | Turns each record into a `<key, value>` pair for the Map task |

---

## 5. How Hadoop (MRv1) Runs a Job

1. **Client** (JobClient) runs the job
2. Asks JobTracker for a **new job ID**
3. **Copies job resources** (jar, config) to HDFS
4. **Submits** the job to the JobTracker
5. JobTracker **initializes** the job
6. JobTracker **retrieves input splits**
7. TaskTrackers send **heartbeats** (and receive tasks in the reply)
8. TaskTracker **retrieves job resources**
9. **Launches** a child JVM
10. **Runs** the Map or Reduce task

### Fault tolerance in MRv1 (3 failure types)
1. **Task fails** -> JobTracker/TaskTracker re-runs the task
2. **TaskTracker fails** (heartbeat stops) -> its tasks are rescheduled elsewhere
3. **JobTracker fails** -> the most serious (single point of failure)

---

## 6. Limits of MapReduce v1

| Problem | Explanation |
|---|---|
| **Scalability** | One JobTracker tracks thousands of TaskTrackers, hundreds of jobs and tens of thousands of tasks, so it becomes **overloaded** (limit around 4000 nodes) |
| **Resource utilization** | Fixed **map slots and reduce slots**, so resources can sit idle |
| **Only MapReduce** | Can't run other workloads (streaming, graph, in-memory...) |
| **Centralized job control flow** | JobTracker does everything |
| **Tight coupling** | Programming model tied to the resource-management infrastructure |
| **Single point of failure** | JobTracker |

Hadoop is now used for all sorts of tasks beyond its original design.

---

## 7. YARN (Yet Another Resource Negotiator)

**Idea:** split the JobTracker's two jobs (**resource management** and **job scheduling/monitoring**) into separate daemons.

### Components
| Component | Role |
|---|---|
| **ResourceManager (RM)** | Global; **ultimate authority** that arbitrates resources among all applications. Tracks live NodeManagers and available resources. Focuses **only on scheduling** |
| **NodeManager (NM)** | One per node (slave); provides compute resources as **containers** and manages processes in them |
| **ApplicationMaster (AM)** | **One per application**; negotiates resources with the RM, works with NMs to run and monitor tasks. Short-lived, dedicated |
| **Container** | Resource bundle (RAM, CPU, disk, network I/O) that can run any task type (even an ApplicationMaster) |
| **Client** | Submits any type of YARN-supported application |

> An **application** = either one classic MapReduce job, or a **DAG** (directed acyclic graph) of jobs.

### MRv1 vs YARN terminology
| MRv1 | YARN |
|---|---|
| Cluster manager / JobTracker | **ResourceManager** |
| JobTracker (job part) | **ApplicationMaster** (dedicated, short-lived) |
| TaskTracker | **NodeManager** |
| One particular MapReduce job | **Distributed Application** |
| Slot | **Container** |

### How YARN runs an application (steps)
1. Client **submits** the YARN application to the **ResourceManager**
2. RM **starts a container** on a NodeManager that hosts the **ApplicationMaster**
3. AM sends **resource requests** (via heartbeat) and RM replies with **container IDs**
4. AM **launches containers** on other NodeManagers, running the application processes
5. Multiple applications run side by side (App 1 with AM 1, App 2 with AM 2) sharing the same cluster

### YARN features
| Feature | Meaning |
|---|---|
| **Scalability** | One AM per job, RM only schedules. Scales to thousands of nodes / petabytes. Supports rolling upgrades |
| **Multi-tenancy** | Batch, interactive and real-time engines access the **same data** at the same time on a shared node pool |
| **Compatibility** | Existing MapReduce jobs run unchanged with the same APIs/CLI (may need recompiling) |
| **Higher cluster utilization** | NodeManager creates **dynamic containers** instead of fixed map/reduce slots |
| **Reliability / availability** | RM High Availability: after restart it recovers application state (stored in HDFS) and re-runs only incomplete tasks. HA NameNode too |
| **Serviceability** | Separation of functions makes improvements easier |

---

## 8. Hadoop 1 vs Hadoop 2

| | **Hadoop 1** | **Hadoop 2** |
|---|---|---|
| Storage | HDFS | HDFS2 (with HA, federation) |
| Resource management | Inside MapReduce (JobTracker) | **YARN** |
| Processing | **MapReduce only** | MapReduce v2, **Spark, Tez, HBase, Storm/stream, graph...** |
| Type | Single-use system (batch) | **Multi-purpose platform** (batch, interactive, online, streaming) |

**HDFS is common to both versions.** YARN is *agnostic* to the type of work. MapReduce becomes just one YARN application.

---

## 9. Spark with Hadoop 2+, and Ambari

### Spark
- An **in-memory** framework, alternative to MapReduce (faster).
- Supports general workloads, **streaming, interactive queries, machine learning**.
- **Spark SQL:** embed SQL queries in Java, Scala, Python programs
- **MLlib:** machine learning library
- **GraphX:** graphs and parallel computation
- **Spark Streaming:** process streaming data

### Apache Ambari (provision, manage, monitor)
- **Provision:** step-by-step wizard to install Hadoop services on many hosts
- **Manage:** central start/stop/reconfigure of services
- **Monitor:** dashboard for cluster health (uses **Ganglia** for metrics and **Nagios** for alerts/emails)
- Offers **REST APIs** for integration into your own applications

---

## 10. Part 2 Checkpoint (with answers)

| Question | Answer |
|---|---|
| List the phases of an MR job. | **Map, Shuffle, Reduce** (+ optional **Combiner**) |
| Limits of MR v1? | Centralized job control; tight coupling of programming model and resource management; Hadoop now used beyond its original design (also: JobTracker scalability, fixed slots) |
| What replaces the JobTracker in YARN? | **ResourceManager** and **ApplicationMaster** |
| Main features of YARN? | **Multi-tenancy, cluster utilization, scalability, compatibility** |

---

# QUICK REVISION CHEAT SHEET

**Numbers to remember**
- Default block size: **128 MB**
- Default replication: **3**
- NameNode safe mode exit: **99.9%** of blocks reported
- NameNode RAM: ~**1 GB per 1 million blocks**
- Web UI port: **50070**
- HA needs **3 JournalNodes** (quorum) and **2 NameNodes** (Active + Standby)

**One-liners**
- HDFS = **write once, read many**; no updates, no random access.
- NameNode = **metadata**; DataNode = **data**.
- Replica rule: 1st anywhere, 2nd same rack (different node), 3rd different rack.
- Secondary NameNode is **not a backup**; it only merges fsimage + edits.
- Map -> `<key,value>`; Shuffle -> group by key (hash partitioner); Reduce -> aggregate.
- Combiner = local reduce **before** shuffle to save network.
- Map output goes to **local disk**; Reduce output goes to **HDFS**.
- MRv1: **JobTracker + TaskTrackers**; YARN: **ResourceManager + NodeManagers + per-app ApplicationMaster**.
- YARN lets Hadoop run **many engines** (MapReduce, Spark, Tez, HBase) on the same data.

**Likely exam questions to practice**
1. Explain the role of NameNode vs DataNodes.
2. Describe how a 410 MB file is stored and replicated.
3. Walk through WordCount: Map -> Shuffle -> Reduce (+ Combiner).
4. Why was YARN created? What were the limits of MRv1?
5. Compare Hadoop 1 and Hadoop 2 architectures.
6. Explain NameNode HA vs Secondary NameNode vs Federation.
7. Which compression formats are splittable, and why does it matter?
