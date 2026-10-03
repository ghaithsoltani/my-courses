# 🐘 Big Data Full Stack Guide
### Hadoop · Spark · Hive · Kafka · Setup on Ubuntu 26.04 (Multipass VM)

> **Author:** soltani | **VM:** bigdata | **Date:** September 2026  
> **Stack:** Hadoop 3.4.1 · Spark 3.5.8 · Hive 4.0.1 · Kafka 3.9.0

---

## 📋 Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Environment Setup](#environment-setup)
3. [Hadoop HDFS](#hadoop-hdfs)
4. [Apache Spark](#apache-spark)
5. [Apache Hive](#apache-hive)
6. [Apache Kafka](#apache-kafka)
7. [Start All Services](#start-all-services)
8. [Sales Analytics Project](#sales-analytics-project)
9. [Troubleshooting](#troubleshooting)
10. [Quick Reference](#quick-reference)

---

## 🏗️ Architecture Overview

```
Real World Data (CSV / Streams / APIs)
            │
            ▼
    ┌───────────────┐
    │  🟡 Kafka     │  ← Real-time event streaming
    │  Port: 9092   │
    └───────┬───────┘
            │
            ▼
    ┌───────────────┐
    │  🐘 Hadoop    │  ← Distributed storage (HDFS)
    │  Port: 9000   │
    └───────┬───────┘
            │
            ▼
    ┌───────────────┐
    │  ⚡ Spark     │  ← Fast data processing
    │  Port: 4040   │
    └───────┬───────┘
            │
            ▼
    ┌───────────────┐
    │  🐝 Hive      │  ← SQL queries on big data
    │  Port: 10000  │
    └───────────────┘
            │
            ▼
    📊 Analytics / Dashboards / Reports
```

### Data Flow
```
CSV File → HDFS Storage → Spark Processing → Hive Tables → SQL Analytics
                ↑
         Kafka (real-time orders stream)
                ↓
         Charts (matplotlib)
```

---

## ⚙️ Environment Setup

### VM Information
| Property | Value |
|----------|-------|
| VM Name | bigdata |
| OS | Ubuntu 26.04 LTS |
| CPUs | 4 |
| RAM | 8 GB |
| Disk | 40 GB |
| IP | 10.239.169.254 |

### Access the VM
```bash
# From host machine
multipass shell bigdata

# Or via SSH
ssh ubuntu@10.239.169.254
```

### Environment Variables
Add to `~/.bashrc`:
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export HADOOP_HOME=/opt/hadoop
export SPARK_HOME=/opt/spark
export HIVE_HOME=/opt/hive
export KAFKA_HOME=/opt/kafka
export PATH=$PATH:$JAVA_HOME/bin \
             :$HADOOP_HOME/bin:$HADOOP_HOME/sbin \
             :$SPARK_HOME/bin:$SPARK_HOME/sbin \
             :$HIVE_HOME/bin \
             :$KAFKA_HOME/bin

# Python virtual environment
source ~/venv/bin/activate
```

Apply changes:
```bash
source ~/.bashrc
```

### Installed Versions
```bash
java -version          # OpenJDK 11.0.32
hadoop version         # Hadoop 3.4.1
spark-shell --version  # Spark 3.5.8
hive --version         # Hive 4.0.1
kafka-topics.sh --version  # 3.9.0
```

---

## 🐘 Hadoop HDFS

### What is Hadoop?
Hadoop is a **distributed storage system** (HDFS) + resource manager (YARN). Think of it as a giant warehouse that can store petabytes of data across many machines.

```
HDFS Architecture:
┌─────────────┐     ┌─────────────┐
│  NameNode   │────▶│  DataNode 1 │
│ (metadata)  │     │  (data)     │
└─────────────┘     ├─────────────┤
                    │  DataNode 2 │
                    │  (data)     │
                    ├─────────────┤
                    │  DataNode 3 │
                    │  (data)     │
                    └─────────────┘
```

### Configuration Files

**`/opt/hadoop/etc/hadoop/core-site.xml`**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <property>
    <name>fs.defaultFS</name>
    <value>hdfs://localhost:9000</value>
  </property>
</configuration>
```

**`/opt/hadoop/etc/hadoop/hdfs-site.xml`**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <property>
    <name>dfs.replication</name>
    <value>1</value>
  </property>
  <property>
    <name>dfs.namenode.name.dir</name>
    <value>/opt/hadoop/data/namenode</value>
  </property>
  <property>
    <name>dfs.datanode.data.dir</name>
    <value>/opt/hadoop/data/datanode</value>
  </property>
</configuration>
```

### Start & Stop HDFS
```bash
# Format namenode (first time only!)
hdfs namenode -format

# Start HDFS
start-dfs.sh

# Start YARN
start-yarn.sh

# Stop everything
stop-dfs.sh
stop-yarn.sh
```

### HDFS Commands
```bash
# List files
hdfs dfs -ls /
hdfs dfs -ls /data/sales/

# Create directory
hdfs dfs -mkdir -p /data/sales

# Upload file from local to HDFS
hdfs dfs -put /tmp/sales.csv /data/sales/

# Download from HDFS to local
hdfs dfs -get /data/sales/sales.csv /tmp/

# View file content
hdfs dfs -cat /data/sales/sales.csv

# Delete file
hdfs dfs -rm /data/sales/sales.csv

# Delete directory (recursive)
hdfs dfs -rm -r /data/sales

# Check disk usage
hdfs dfs -du -h /data/

# Set permissions
hdfs dfs -chmod -R 777 /user/hive/warehouse

# Copy within HDFS
hdfs dfs -cp /data/sales/sales.csv /backup/

# Move within HDFS
hdfs dfs -mv /data/old/ /data/new/

# Count files
hdfs dfs -count /data/
```

### Web UIs
| Service | URL |
|---------|-----|
| NameNode | http://10.239.169.254:9870 |
| ResourceManager | http://10.239.169.254:8088 |
| DataNode | http://10.239.169.254:9864 |

### Check HDFS Health
```bash
# Check running services
jps

# Check HDFS report
hdfs dfsadmin -report

# Check safe mode
hdfs dfsadmin -safemode get
```

---

## ⚡ Apache Spark

### What is Spark?
Spark is a **fast data processing engine** that runs in memory. It's like a smart factory worker who takes raw materials from HDFS, processes them, and produces results — 100x faster than MapReduce.

```
Spark Architecture:
┌──────────────────┐
│   Driver Program  │  ← Your Python/Scala code
│   (SparkContext) │
└────────┬─────────┘
         │  tasks
    ┌────▼────┐
    │ Cluster │
    │ Manager │  ← YARN / Standalone
    └────┬────┘
    ┌────▼────────────────┐
    │  Worker Node 1      │
    │  ┌──────────────┐   │
    │  │  Executor    │   │  ← runs tasks in memory
    │  │  Task | Task │   │
    │  └──────────────┘   │
    └─────────────────────┘
```

### Spark Session (Python)
```python
from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("My App") \
    .master("local[*]") \
    .getOrCreate()
```

### Read Data
```python
# Read CSV
df = spark.read.csv("hdfs://localhost:9000/data/sales/sales.csv",
                    header=True, inferSchema=True)

# Read JSON
df = spark.read.json("hdfs://localhost:9000/data/orders.json")

# Read Parquet (best format for Big Data)
df = spark.read.parquet("hdfs://localhost:9000/data/parquet/")

# Read from Hive
df = spark.sql("SELECT * FROM salesdb.sales_raw")
```

### DataFrame Operations
```python
# Show data
df.show()
df.show(5)          # first 5 rows
df.printSchema()    # column types

# Select columns
df.select("product", "price", "country").show()

# Filter rows
df.filter(df.country == "Tunisia").show()
df.filter(df.price > 100).show()

# Add new column
from pyspark.sql.functions import col, round
df = df.withColumn("revenue", round(col("price") * col("quantity"), 2))

# Group by + aggregate
from pyspark.sql.functions import sum, avg, count
df.groupBy("country") \
  .agg(sum("revenue").alias("total"),
       count("order_id").alias("orders")) \
  .orderBy("total", ascending=False) \
  .show()

# Sort
df.orderBy("price", ascending=False).show()

# Distinct values
df.select("country").distinct().show()

# Count rows
print(df.count())

# Drop column
df = df.drop("unwanted_column")

# Rename column
df = df.withColumnRenamed("old_name", "new_name")

# Fill null values
df = df.fillna({"price": 0, "country": "Unknown"})

# Convert to Pandas
pandas_df = df.toPandas()
```

### Spark SQL
```python
# Register temp view
df.createOrReplaceTempView("sales")

# Run SQL
result = spark.sql("""
    SELECT country,
           ROUND(SUM(price * quantity), 2) as revenue,
           COUNT(*) as orders
    FROM sales
    GROUP BY country
    ORDER BY revenue DESC
""")
result.show()
```

### Save Data
```python
# Save as Parquet (recommended)
df.write.mode("overwrite").parquet("hdfs://localhost:9000/output/sales/")

# Save as CSV
df.write.mode("overwrite").csv("hdfs://localhost:9000/output/csv/",
                                header=True)

# Save to Hive table
df.write.mode("overwrite").saveAsTable("salesdb.sales_processed")
```

### Run Spark Job
```bash
# Submit Python script
spark-submit my_script.py

# Submit with Hive support
spark-submit \
  --packages org.apache.spark:spark-hive_2.12:3.5.8 \
  --conf spark.pyspark.python=/home/ubuntu/venv/bin/python3 \
  --conf spark.pyspark.driver.python=/home/ubuntu/venv/bin/python3 \
  my_script.py

# Interactive PySpark shell
pyspark

# Interactive Scala shell
spark-shell
```

### Spark Web UI
| Service | URL |
|---------|-----|
| Spark UI (running job) | http://10.239.169.254:4040 |
| Spark History Server | http://10.239.169.254:18080 |

---

## 🐝 Apache Hive

### What is Hive?
Hive lets you query big data using **SQL**. It translates SQL queries into Spark/MapReduce jobs. Think of it as Excel but for billions of rows stored in HDFS.

```
Hive Architecture:
┌──────────────┐
│  SQL Query   │  ← You write SQL
└──────┬───────┘
       │
┌──────▼───────┐
│  Hive Driver │  ← Parses SQL
└──────┬───────┘
       │
┌──────▼───────┐
│  Metastore   │  ← Stores table metadata
│  (Derby DB)  │
└──────┬───────┘
       │
┌──────▼───────┐
│  HDFS/Spark  │  ← Executes on data
└──────────────┘
```

### Configuration

**`$SPARK_HOME/conf/hive-site.xml`**
```xml
<?xml version="1.0"?>
<configuration>
  <property>
    <name>hive.metastore.warehouse.dir</name>
    <value>hdfs://localhost:9000/user/hive/warehouse</value>
  </property>
  <property>
    <name>hive.exec.scratchdir</name>
    <value>hdfs://localhost:9000/tmp/hive</value>
  </property>
  <property>
    <name>javax.jdo.option.ConnectionURL</name>
    <value>jdbc:derby:;databaseName=/opt/hive/metastore_db;create=true</value>
  </property>
  <property>
    <name>datanucleus.schema.autoCreateAll</name>
    <value>true</value>
  </property>
</configuration>
```

### Hive with Spark (Python)
```python
from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("Hive Example") \
    .config("spark.sql.warehouse.dir", "hdfs://localhost:9000/user/hive/warehouse") \
    .enableHiveSupport() \
    .getOrCreate()

# Create database
spark.sql("CREATE DATABASE IF NOT EXISTS salesdb")
spark.sql("USE salesdb")

# Create table from DataFrame
df.write.mode("overwrite").saveAsTable("salesdb.sales_raw")

# Query Hive table
spark.sql("SHOW DATABASES").show()
spark.sql("SHOW TABLES IN salesdb").show()
spark.sql("SELECT * FROM salesdb.sales_raw LIMIT 10").show()
spark.sql("DESCRIBE salesdb.sales_raw").show()

# Create external table
spark.sql("""
    CREATE EXTERNAL TABLE IF NOT EXISTS salesdb.sales_ext (
        order_id INT,
        date STRING,
        product STRING,
        country STRING,
        quantity INT,
        price DOUBLE
    )
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    LOCATION 'hdfs://localhost:9000/data/sales/'
""")
```

### HiveQL (SQL dialect)
```sql
-- Create database
CREATE DATABASE IF NOT EXISTS salesdb;
USE salesdb;

-- Create table
CREATE TABLE IF NOT EXISTS employees (
    id INT,
    name STRING,
    salary DOUBLE,
    department STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';

-- Insert data
INSERT INTO employees VALUES (1, 'Ahmed', 5000.0, 'Engineering');

-- Query
SELECT department, AVG(salary) as avg_salary
FROM employees
GROUP BY department
ORDER BY avg_salary DESC;

-- Partitioned table (better performance)
CREATE TABLE sales_partitioned (
    order_id INT,
    product STRING,
    price DOUBLE
)
PARTITIONED BY (country STRING, year INT);

-- Show partitions
SHOW PARTITIONS sales_partitioned;

-- Drop table
DROP TABLE IF EXISTS employees;

-- Drop database
DROP DATABASE IF EXISTS salesdb CASCADE;
```

### Start Hive Services
```bash
# Initialize metastore (first time only)
$HIVE_HOME/bin/schematool -initSchema -dbType derby

# Start metastore service
hive --service metastore &

# Start HiveServer2
hive --service hiveserver2 &

# Connect via Beeline
beeline -u jdbc:hive2://localhost:10000 -e "SHOW DATABASES;"
```

---

## 🟡 Apache Kafka

### What is Kafka?
Kafka is a **distributed message streaming platform**. Think of it as a newspaper press — it publishes events continuously, and many subscribers can read the same events. Perfect for real-time data pipelines.

```
Kafka Architecture:
┌──────────────┐     ┌─────────────────────────┐     ┌──────────────┐
│  Producer 1  │────▶│                         │────▶│  Consumer 1  │
│  (orders)    │     │    Kafka Broker          │     │  (warehouse) │
├──────────────┤     │                         │     ├──────────────┤
│  Producer 2  │────▶│  Topic: sales-orders    │────▶│  Consumer 2  │
│  (website)   │     │  ┌─────────────────┐    │     │  (shipping)  │
└──────────────┘     │  │ Partition 0     │    │     ├──────────────┤
                     │  │ msg1,msg2,msg3  │    │────▶│  Consumer 3  │
                     │  └─────────────────┘    │     │  (analytics) │
                     └─────────────────────────┘     └──────────────┘
```

### Key Concepts
| Concept | Description |
|---------|-------------|
| **Topic** | A category of messages (like a database table) |
| **Partition** | A subdivision of a topic for parallelism |
| **Producer** | App that sends messages to Kafka |
| **Consumer** | App that reads messages from Kafka |
| **Broker** | A Kafka server |
| **Zookeeper** | Manages Kafka cluster metadata |
| **Offset** | Position of a message in a partition |

### Start Kafka
```bash
# Start Zookeeper first
$KAFKA_HOME/bin/zookeeper-server-start.sh -daemon \
  $KAFKA_HOME/config/zookeeper.properties
sleep 5

# Start Kafka broker
$KAFKA_HOME/bin/kafka-server-start.sh -daemon \
  $KAFKA_HOME/config/server.properties
sleep 5

# Verify both running
jps | grep -E "QuorumPeerMain|Kafka"
```

### Topic Management
```bash
# Create topic
kafka-topics.sh --create \
  --topic sales-orders \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1

# List topics
kafka-topics.sh --list \
  --bootstrap-server localhost:9092

# Describe topic
kafka-topics.sh --describe \
  --topic sales-orders \
  --bootstrap-server localhost:9092

# Delete topic
kafka-topics.sh --delete \
  --topic sales-orders \
  --bootstrap-server localhost:9092
```

### Produce & Consume (Terminal)
```bash
# Send messages (producer)
kafka-console-producer.sh \
  --topic sales-orders \
  --bootstrap-server localhost:9092

# Read messages (consumer) - from beginning
kafka-console-consumer.sh \
  --topic sales-orders \
  --bootstrap-server localhost:9092 \
  --from-beginning

# Read latest messages only
kafka-console-consumer.sh \
  --topic sales-orders \
  --bootstrap-server localhost:9092
```

### Kafka with Python
```python
from kafka import KafkaProducer, KafkaConsumer
import json, time

# ── Producer ──────────────────────────────
producer = KafkaProducer(
    bootstrap_servers='localhost:9092',
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

# Send a message
order = {
    'order_id': 1,
    'product': 'Laptop',
    'price': 999.99,
    'quantity': 2,
    'country': 'Tunisia',
    'timestamp': time.strftime('%Y-%m-%d %H:%M:%S')
}
producer.send('sales-orders', value=order)
producer.flush()
print("Order sent!")

# ── Consumer ──────────────────────────────
consumer = KafkaConsumer(
    'sales-orders',
    bootstrap_servers='localhost:9092',
    auto_offset_reset='earliest',
    consumer_timeout_ms=5000,
    value_deserializer=lambda x: json.loads(x.decode('utf-8'))
)

for message in consumer:
    order = message.value
    print(f"Received: {order['product']} x{order['quantity']} = "
          f"${order['price'] * order['quantity']:.2f}")
```

### Consumer Groups
```bash
# List consumer groups
kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --list

# Check consumer group lag
kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --group my-group
```

---

## 🚀 Start All Services

### Startup Script
Save as `~/start_bigdata.sh`:
```bash
#!/bin/bash

echo "🚀 Starting Big Data Stack..."

# Load environment
export HADOOP_HOME=/opt/hadoop
export SPARK_HOME=/opt/spark
export KAFKA_HOME=/opt/kafka
export HIVE_HOME=/opt/hive
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$SPARK_HOME/bin:$KAFKA_HOME/bin

# 1. Start Hadoop HDFS
echo "▶ Starting Hadoop HDFS..."
start-dfs.sh
sleep 5

# 2. Start YARN
echo "▶ Starting YARN..."
start-yarn.sh
sleep 5

# 3. Start Zookeeper
echo "▶ Starting Zookeeper..."
$KAFKA_HOME/bin/zookeeper-server-start.sh -daemon \
  $KAFKA_HOME/config/zookeeper.properties
sleep 5

# 4. Start Kafka
echo "▶ Starting Kafka..."
$KAFKA_HOME/bin/kafka-server-start.sh -daemon \
  $KAFKA_HOME/config/server.properties
sleep 5

# 5. Start Hive Metastore
echo "▶ Starting Hive Metastore..."
hive --service metastore > /tmp/hive-metastore.log 2>&1 &
sleep 10

# 6. Check all services
echo ""
echo "✅ Running Services:"
jps
echo ""
echo "🎉 Big Data Stack is ready!"
```

```bash
chmod +x ~/start_bigdata.sh
~/start_bigdata.sh
```

### Shutdown Script
Save as `~/stop_bigdata.sh`:
```bash
#!/bin/bash

echo "🛑 Stopping Big Data Stack..."

export HADOOP_HOME=/opt/hadoop
export KAFKA_HOME=/opt/kafka
export PATH=$PATH:$HADOOP_HOME/sbin:$KAFKA_HOME/bin

# Stop Kafka
$KAFKA_HOME/bin/kafka-server-stop.sh

# Stop Zookeeper
$KAFKA_HOME/bin/zookeeper-server-stop.sh

# Stop YARN
stop-yarn.sh

# Stop HDFS
stop-dfs.sh

# Kill Hive processes
pkill -f HiveMetaStore 2>/dev/null
pkill -f HiveServer2 2>/dev/null

echo "✅ All services stopped"
jps
```

### Check All Services
```bash
jps
# Should show:
# NameNode        ← HDFS
# DataNode        ← HDFS
# SecondaryNameNode ← HDFS
# ResourceManager ← YARN
# NodeManager     ← YARN
# QuorumPeerMain  ← Zookeeper
# Kafka           ← Kafka
```

---

## 📊 Sales Analytics Project

### Project Structure
```
~/sales-project/
├── sales_analysis.py      # Basic Spark analysis
├── full_project.py        # Full pipeline with Hive + Kafka + Charts
├── hive-site.xml          # Hive configuration
└── charts/
    └── dashboard.png      # Generated charts
```

### Data Flow
```
/tmp/sales.csv
      │
      ▼ hdfs dfs -put
hdfs://localhost:9000/data/sales/sales.csv
      │
      ▼ spark-submit
Spark reads + processes + analyzes
      │
      ├──▶ Hive table: salesdb.sales_raw
      ├──▶ Charts: ~/sales-project/charts/dashboard.png
      └──▶ Kafka topic: sales-orders (real-time simulation)
```

### Sample Data (`sales.csv`)
```csv
order_id,date,product,category,quantity,price,country
1,2024-01-01,Laptop,Electronics,2,999.99,Tunisia
2,2024-01-02,Phone,Electronics,5,499.99,Algeria
3,2024-01-03,Desk,Furniture,1,299.99,Morocco
```

### Run Basic Analysis
```bash
cd ~/sales-project
spark-submit sales_analysis.py
```

### Run Full Pipeline
```bash
spark-submit \
  --packages org.apache.spark:spark-hive_2.12:3.5.8 \
  --conf spark.pyspark.python=/home/ubuntu/venv/bin/python3 \
  --conf spark.pyspark.driver.python=/home/ubuntu/venv/bin/python3 \
  full_project.py
```

### Results Summary

| Country | Revenue | Orders |
|---------|---------|--------|
| 🇹🇳 Tunisia | $8,039.71 | 6 |
| 🇩🇿 Algeria | $6,199.90 | 3 |
| 🇪🇬 Egypt | $3,399.88 | 3 |
| 🇲🇦 Morocco | $3,199.93 | 3 |

| Category | Revenue | Units |
|----------|---------|-------|
| Electronics | $18,349.53 | 47 |
| Furniture | $2,489.89 | 11 |

---

## 🔧 Troubleshooting

### HDFS not starting
```bash
# Check logs
cat /opt/hadoop/logs/hadoop-ubuntu-namenode-bigdata.log | tail -50

# Re-format namenode (WARNING: deletes all data!)
hdfs namenode -format -force
start-dfs.sh
```

### Kafka connection refused
```bash
# Check if Zookeeper is running
jps | grep QuorumPeerMain

# Check Kafka logs
cat /opt/kafka/logs/server.log | tail -30

# Restart Kafka
$KAFKA_HOME/bin/kafka-server-stop.sh
sleep 3
$KAFKA_HOME/bin/kafka-server-start.sh -daemon \
  $KAFKA_HOME/config/server.properties
```

### Hive warehouse error
```bash
# Fix permissions
hdfs dfs -mkdir -p /user/hive/warehouse
hdfs dfs -chmod -R 777 /user/hive/warehouse
hdfs dfs -mkdir -p /tmp/hive
hdfs dfs -chmod -R 777 /tmp/hive
```

### PATH not found after venv activation
```bash
export HADOOP_HOME=/opt/hadoop
export SPARK_HOME=/opt/spark
export KAFKA_HOME=/opt/kafka
export HIVE_HOME=/opt/hive
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin \
           :$SPARK_HOME/bin:$KAFKA_HOME/bin:$HIVE_HOME/bin
```

### Port already in use
```bash
# Find what's using port 9092
sudo lsof -i :9092
sudo kill -9 <PID>
```

---

## 📖 Quick Reference

### HDFS Cheatsheet
| Command | Description |
|---------|-------------|
| `hdfs dfs -ls /` | List root directory |
| `hdfs dfs -mkdir /path` | Create directory |
| `hdfs dfs -put local.csv /hdfs/` | Upload file |
| `hdfs dfs -get /hdfs/file.csv ./` | Download file |
| `hdfs dfs -cat /hdfs/file.csv` | View file |
| `hdfs dfs -rm /hdfs/file.csv` | Delete file |
| `hdfs dfs -du -h /` | Disk usage |
| `hdfs dfsadmin -report` | HDFS health |

### Spark Cheatsheet
| Command | Description |
|---------|-------------|
| `spark-submit script.py` | Run Python script |
| `pyspark` | Interactive Python shell |
| `df.show()` | Display DataFrame |
| `df.count()` | Count rows |
| `df.printSchema()` | Show schema |
| `df.filter(...)` | Filter rows |
| `df.groupBy(...).agg(...)` | Group & aggregate |
| `df.write.parquet(path)` | Save as Parquet |

### Kafka Cheatsheet
| Command | Description |
|---------|-------------|
| `kafka-topics.sh --list` | List topics |
| `kafka-topics.sh --create --topic X` | Create topic |
| `kafka-topics.sh --describe --topic X` | Topic details |
| `kafka-console-producer.sh --topic X` | Send messages |
| `kafka-console-consumer.sh --topic X` | Read messages |
| `kafka-consumer-groups.sh --list` | List consumers |

### Hive Cheatsheet
| Command | Description |
|---------|-------------|
| `SHOW DATABASES;` | List databases |
| `USE mydb;` | Select database |
| `SHOW TABLES;` | List tables |
| `DESCRIBE table;` | Table schema |
| `SELECT * FROM table LIMIT 10;` | Query data |
| `DROP TABLE table;` | Delete table |

### Ports Reference
| Service | Port | URL |
|---------|------|-----|
| HDFS NameNode | 9870 | http://IP:9870 |
| HDFS Data | 9000 | hdfs://localhost:9000 |
| YARN ResourceManager | 8088 | http://IP:8088 |
| Spark UI | 4040 | http://IP:4040 |
| HiveServer2 | 10000 | jdbc:hive2://localhost:10000 |
| Kafka | 9092 | localhost:9092 |
| Zookeeper | 2181 | localhost:2181 |

---

## 🎓 Learning Resources

### YouTube Tutorials (Watch in Order)
1. [Hadoop in 5 Minutes — Simplilearn](https://www.youtube.com/watch?v=aReuLtY0YMI)
2. [What is Kafka? — IBM](https://www.youtube.com/watch?v=aj9CDZm0Glc)
3. [What is Spark? — IBM](https://www.youtube.com/watch?v=ymtq8oles8A)
4. [Big Data Architecture — TechWorld with Nana](https://www.youtube.com/watch?v=bAyrObl7TYE)

### Online Courses
| Course | Platform | Level |
|--------|----------|-------|
| Big Data with Spark & Hadoop | Udemy | Beginner |
| Data Engineering with Kafka | Coursera | Intermediate |
| Apache Spark Deep Dive | Pluralsight | Advanced |

### Documentation
- [Hadoop Docs](https://hadoop.apache.org/docs/)
- [Spark Docs](https://spark.apache.org/docs/latest/)
- [Kafka Docs](https://kafka.apache.org/documentation/)
- [Hive Docs](https://cwiki.apache.org/confluence/display/Hive/Home)

---

*Generated on Ubuntu 26.04 LTS · Multipass VM bigdata · September 2026*
