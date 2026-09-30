# System Design & Software Architecture: Complete Professional Guide

> A structured reference covering the core concepts of system design and architecture, from fundamentals to distributed systems, reliability, security, and documentation practice.

---

## Table of Contents

1. [What System Design Is](#1-what-system-design-is)
2. [Requirements and Estimation](#2-requirements-and-estimation)
3. [Networking Fundamentals](#3-networking-fundamentals)
4. [API Design](#4-api-design)
5. [Scalability](#5-scalability)
6. [Load Balancing and Traffic Management](#6-load-balancing-and-traffic-management)
7. [Caching](#7-caching)
8. [Databases and Storage](#8-databases-and-storage)
9. [Distributed Systems Theory](#9-distributed-systems-theory)
10. [Messaging and Asynchronous Processing](#10-messaging-and-asynchronous-processing)
11. [Architectural Styles](#11-architectural-styles)
12. [Microservice and Resilience Patterns](#12-microservice-and-resilience-patterns)
13. [Reliability Engineering](#13-reliability-engineering)
14. [Observability](#14-observability)
15. [Security Architecture](#15-security-architecture)
16. [Cloud and Infrastructure Architecture](#16-cloud-and-infrastructure-architecture)
17. [Data Architecture](#17-data-architecture)
18. [Reusable Building Blocks](#18-reusable-building-blocks)
19. [Design Principles](#19-design-principles)
20. [Documenting Architecture](#20-documenting-architecture)
21. [Worked Case Studies](#21-worked-case-studies)
22. [Design Process Framework](#22-design-process-framework)
23. [Reference Numbers](#23-reference-numbers)
24. [Learning Roadmap and Resources](#24-learning-roadmap-and-resources)

---

## 1. What System Design Is

**System design** is the process of defining the components, interfaces, data flow, and infrastructure of a system so that it meets its functional and non-functional requirements.

**Software architecture** is the set of significant decisions about a system's structure that are expensive to change later: how it is decomposed, how the parts communicate, where data lives, and which quality attributes are prioritized.

Key ideas:

- **Everything is a trade-off.** There is no "best" design, only the best fit for the requirements, team, budget, and timeline.
- **Architecture is driven by quality attributes** (performance, availability, security, cost, maintainability), not only features.
- **Start simple, evolve deliberately.** Premature distribution is a common and costly mistake.
- **Decisions must be explainable.** A good architect can state the alternatives considered and why one was chosen.

### Quality Attributes (Non-Functional Requirements)

| Attribute | Question it answers |
|---|---|
| Performance | How fast (latency) and how much (throughput)? |
| Scalability | Can it handle 10x or 100x load? |
| Availability | What percentage of time is it usable? |
| Reliability | Does it behave correctly over time, including under failure? |
| Durability | Will data survive failures? |
| Consistency | Do all users see the same data at the same time? |
| Security | Is it protected against misuse and data loss? |
| Maintainability | How easy is it to change, test, and operate? |
| Observability | Can we understand internal state from outputs? |
| Cost efficiency | What does it cost per user or per request? |
| Portability / Interoperability | Can it run elsewhere and integrate with others? |

---

## 2. Requirements and Estimation

### 2.1 Functional vs. Non-Functional

- **Functional**: what the system does ("users can shorten a URL").
- **Non-functional**: how well it does it ("redirects in under 100 ms at p99, 99.95% available").
- **Constraints**: budget, compliance (GDPR, PCI-DSS, HIPAA), team skills, existing tech, deadlines.

### 2.2 Capacity Estimation (Back-of-the-Envelope)

Estimate to decide whether you need sharding, caching, a CDN, or a single database is enough.

**Steps**

1. Estimate users: DAU (daily active users), MAU.
2. Estimate actions per user per day.
3. Compute **QPS** (queries per second) = daily requests / 86,400. Peak is usually 2 to 10 times average.
4. Estimate **storage** = records per day × size per record × retention.
5. Estimate **bandwidth** = QPS × average payload size.
6. Estimate **memory for cache** using the 80/20 rule (20% of data serves 80% of reads).

**Example: URL shortener**

- 100M new URLs per month, read:write ratio 100:1.
- Writes: 100M / (30 × 86,400) ≈ 40 writes/s. Reads ≈ 4,000/s. Peak maybe 10,000/s.
- Storage: 100M × 500 bytes × 12 months × 5 years ≈ 3 TB.
- Cache: 20% of daily read traffic ≈ tens of GB, fits in a small Redis cluster.

Conclusion: read-heavy, modest storage, caching is highly effective, sharding likely only after several years.

### 2.3 Useful Approximations

- 1 day ≈ 86,400 s ≈ 10⁵ s.
- 1M requests/day ≈ 12 requests/s.
- 1 KB × 1M = 1 GB. 1 KB × 1B = 1 TB.

---

## 3. Networking Fundamentals

### 3.1 Request Path

```
Client → DNS → CDN/Edge → Load Balancer → Reverse Proxy/API Gateway → Service → Cache/DB
```

### 3.2 Core Concepts

- **DNS**: maps names to IPs. Supports geo-routing, weighted routing, failover, and TTL-based caching.
- **TCP**: reliable, ordered, connection-oriented (handshake, congestion control). **UDP**: connectionless, low overhead, used for video, gaming, DNS, QUIC.
- **HTTP/1.1** (one request per connection at a time, keep-alive), **HTTP/2** (multiplexing, header compression), **HTTP/3** (over QUIC/UDP, avoids head-of-line blocking).
- **TLS**: encryption in transit, certificate chains, handshake cost, session resumption. **mTLS** authenticates both sides (service mesh, zero trust).
- **WebSockets**: full-duplex persistent connection (chat, live dashboards). **Server-Sent Events**: server-to-client stream over HTTP. **Long polling**: fallback.
- **Proxy vs. reverse proxy**: a forward proxy acts for clients; a reverse proxy (Nginx, HAProxy, Envoy) acts for servers and provides TLS termination, routing, caching, compression, and rate limiting.
- **CDN**: caches static (and some dynamic) content at edge locations close to users. Cuts latency and origin load. Concepts: push vs. pull CDN, cache invalidation, TTL, origin shield.
- **OSI layers** matter for load balancer types: L4 (TCP/UDP) vs. L7 (HTTP).

---

## 4. API Design

### 4.1 Styles

| Style | Best for | Notes |
|---|---|---|
| **REST** | Public and CRUD-style APIs | Resource-oriented, stateless, cacheable, uses HTTP verbs and status codes |
| **GraphQL** | Diverse clients needing flexible queries | Avoids over/under-fetching; watch for N+1 queries, query cost, caching difficulty |
| **gRPC** | Internal service-to-service | Protobuf, HTTP/2, streaming, strong typing, code generation |
| **WebSocket / SSE** | Real-time push | Stateful connections need sticky routing or a pub/sub backplane |
| **Webhooks** | Event notification to third parties | Need retries, signatures, idempotency |

### 4.2 REST Best Practices

- Nouns for resources (`/orders/{id}`), verbs via HTTP methods.
- Correct status codes (200, 201, 204, 400, 401, 403, 404, 409, 422, 429, 500, 503).
- **Idempotency**: GET, PUT, DELETE are idempotent; make POST safe with an `Idempotency-Key` header.
- **Pagination**: offset (simple, slow at depth) vs. **cursor/keyset** (stable and fast).
- **Versioning**: URI (`/v1`), header, or media type. Prefer additive, backward-compatible changes.
- **Rate limiting** and quotas with `429` and `Retry-After`.
- **Filtering, sorting, field selection**, consistent error format (e.g., RFC 7807 Problem Details).
- **OpenAPI** spec as the contract; contract testing (Pact) between services.

### 4.3 Contract Principles

- Be conservative in what you send, liberal in what you accept (Postel), but validate strictly at the edge.
- Never break consumers silently: deprecate, announce, version, remove.

---

## 5. Scalability

### 5.1 Vertical vs. Horizontal

- **Vertical (scale up)**: bigger machine. Simple, but has a ceiling and is a single point of failure.
- **Horizontal (scale out)**: more machines. Needs stateless services, load balancing, and data partitioning. Enables fault tolerance.

### 5.2 Making Services Scalable

- **Stateless services**: keep session state in a shared store (Redis) or in signed tokens (JWT), not in server memory.
- **Externalize state**: DB, cache, object storage, queue.
- **Autoscaling**: on CPU, memory, request rate, or queue depth (Kubernetes HPA, KEDA, cloud autoscaling groups).
- **Async processing**: move slow work off the request path.
- **Data partitioning** and **replication** for the storage tier.
- **Bottleneck hunting**: measure first. Amdahl's law: the serial part limits the speedup.

### 5.3 Performance Terms

- **Latency**: time for one request. Track percentiles (p50, p95, p99), not averages. Tail latency dominates user experience and gets worse with fan-out.
- **Throughput**: requests or bytes per second.
- **Little's Law**: `L = λ × W` (concurrent requests = arrival rate × average time in system). Useful for sizing thread pools and connection pools.
- **Backpressure**: a system signals upstream to slow down instead of collapsing.

---

## 6. Load Balancing and Traffic Management

### 6.1 Types

- **L4 load balancer**: routes on IP/port, very fast (e.g., AWS NLB, HAProxy TCP mode).
- **L7 load balancer**: routes on URL, headers, cookies; does TLS termination and path-based routing (Nginx, Envoy, AWS ALB).
- **Global load balancing**: DNS or anycast to steer users to the nearest healthy region.

### 6.2 Algorithms

Round robin, weighted round robin, least connections, least response time, IP hash, **consistent hashing** (minimizes remapping when nodes change), power of two choices.

### 6.3 Related Concepts

- **Health checks** (active and passive), connection draining.
- **Sticky sessions**: convenient but hurts even distribution and failover; prefer stateless.
- **API Gateway**: single entry for auth, routing, rate limiting, request transformation, caching, and analytics (Kong, Apigee, AWS API Gateway).
- **Service mesh** (Istio, Linkerd): sidecar proxies handling mTLS, retries, timeouts, traffic shifting, and telemetry.
- **Rate limiting algorithms**: token bucket, leaky bucket, fixed window, sliding window log, sliding window counter.
- **Traffic release strategies**: blue/green, canary, rolling, shadow traffic, feature flags.

---

## 7. Caching

Caching trades **freshness and memory** for **speed and reduced load**.

### 7.1 Where to Cache

Browser → CDN → API gateway → application (in-process) → distributed cache (Redis, Memcached) → database buffer pool.

### 7.2 Caching Patterns

| Pattern | How it works | Trade-off |
|---|---|---|
| **Cache-aside (lazy loading)** | App checks cache, on miss reads DB and fills cache | Simple; first request is slow; stale risk |
| **Read-through** | Cache layer loads from DB itself | Cleaner app code |
| **Write-through** | Write to cache and DB together | Consistent, slower writes |
| **Write-back (write-behind)** | Write to cache, flush to DB later | Fast writes, risk of data loss |
| **Write-around** | Write to DB, skip cache | Avoids polluting cache with rarely read data |
| **Refresh-ahead** | Proactively refresh hot keys | Reduces miss latency |

### 7.3 Eviction Policies

LRU, LFU, FIFO, TTL-based, random. Redis offers several `maxmemory-policy` options.

### 7.4 Classic Problems

- **Cache invalidation**: the hard part. Use TTLs, event-driven invalidation, versioned keys.
- **Cache stampede (thundering herd)**: many requests miss at once. Fixes: request coalescing/locking, jittered TTLs, early refresh.
- **Cache penetration**: requests for nonexistent keys. Fix: cache negative results, Bloom filter.
- **Hot key**: one key overloads one node. Fix: local cache, key replication, key splitting.
- **Consistency**: cache and DB can diverge. Decide if stale reads are acceptable.

### 7.5 HTTP Caching

`Cache-Control`, `ETag`/`If-None-Match`, `Last-Modified`, `Vary`, `stale-while-revalidate`.

---

## 8. Databases and Storage

### 8.1 Choosing a Data Store

| Type | Examples | Strengths | Typical use |
|---|---|---|---|
| **Relational (SQL)** | PostgreSQL, MySQL | ACID, joins, mature tooling, strong consistency | Transactions, business data |
| **Key-value** | Redis, DynamoDB | Very fast lookups, simple scaling | Sessions, caches, counters |
| **Document** | MongoDB, Couchbase | Flexible schema, nested data | Content, catalogs, profiles |
| **Wide-column** | Cassandra, ScyllaDB, HBase | Massive write throughput, linear scaling | Time series, messaging, IoT |
| **Graph** | Neo4j, Neptune | Relationship traversal | Social graphs, fraud, recommendations |
| **Search** | Elasticsearch, OpenSearch | Full-text, faceting, analytics | Search, logs |
| **Time-series** | InfluxDB, TimescaleDB, Prometheus | Efficient time-ordered data | Metrics, monitoring |
| **Object storage** | S3, GCS, MinIO | Cheap, durable, huge | Files, media, backups, data lake |
| **Vector** | pgvector, Pinecone, Milvus | Similarity search on embeddings | RAG, semantic search |
| **NewSQL** | CockroachDB, Spanner, TiDB | SQL plus horizontal scale | Global transactional workloads |

Rule of thumb: **default to PostgreSQL** until requirements clearly demand otherwise.

### 8.2 Transactions and ACID

- **Atomicity**, **Consistency**, **Isolation**, **Durability**.
- **Isolation levels**: read uncommitted, read committed, repeatable read, snapshot, serializable. Anomalies: dirty read, non-repeatable read, phantom read, lost update, write skew.
- **Concurrency control**: pessimistic locking, optimistic locking (version column), MVCC.
- **BASE** (basically available, soft state, eventual consistency) as the distributed-system counterpart.

### 8.3 Indexing

- **B-tree** (default, range and equality), **hash**, **LSM tree** (write-optimized: Cassandra, RocksDB), **inverted index** (search), **GiST/GIN**, **bitmap**.
- Composite index order matters (leftmost prefix). Covering indexes avoid table lookups.
- Indexes speed reads but slow writes and use storage. Use `EXPLAIN` to verify.

### 8.4 Replication

- **Leader-follower (primary-replica)**: writes to leader, reads can go to followers. Replication lag causes stale reads ("read your own writes" problem).
- **Synchronous vs. asynchronous** replication: durability vs. latency.
- **Multi-leader**: multi-region writes, requires conflict resolution (last-write-wins, CRDTs, merge functions).
- **Leaderless (quorum)**: Dynamo-style. With N replicas, write quorum W, read quorum R: `R + W > N` gives strong-ish consistency.
- **Failover**: promotion, split-brain risk, fencing.

### 8.5 Partitioning (Sharding)

Splitting data across nodes for scale.

- **Range**: simple, supports range scans, risk of hotspots.
- **Hash**: even distribution, loses range scans.
- **Directory/lookup**: flexible, extra hop and a new dependency.
- **Consistent hashing** with virtual nodes: smooth rebalancing.
- Pitfalls: cross-shard joins and transactions, resharding cost, hot shards, choosing the shard key (high cardinality, evenly distributed, matches query patterns).

**Vertical partitioning** splits columns/tables by function; **functional partitioning** gives each service its own database.

### 8.6 Other Storage Concepts

- **Normalization vs. denormalization**: normalize for integrity, denormalize for read speed.
- **OLTP vs. OLAP**: row stores for transactions, columnar stores (BigQuery, Redshift, ClickHouse, Parquet) for analytics.
- **Connection pooling** (PgBouncer, HikariCP): databases handle limited connections.
- **Backups, PITR** (point-in-time recovery), and **tested restores**.
- **Data lifecycle**: hot, warm, cold, archive tiers; retention and deletion policies.
- **Schema migrations**: expand and contract pattern for zero downtime.

---

## 9. Distributed Systems Theory

### 9.1 Why It Is Hard

The **fallacies of distributed computing**: the network is not reliable, latency is not zero, bandwidth is not infinite, the network is not secure, topology changes, there is more than one administrator, transport cost is not zero, the network is not homogeneous.

Partial failure is the defining challenge: parts fail while others keep running, and you often cannot tell whether a remote call failed or is just slow.

### 9.2 CAP and PACELC

- **CAP**: during a network **P**artition, you must choose **C**onsistency or **A**vailability. (Without a partition you can have both.)
  - **CP** systems refuse some requests to stay consistent (ZooKeeper, etcd, HBase).
  - **AP** systems stay available and reconcile later (Cassandra, DynamoDB default, Riak).
- **PACELC**: if Partitioned, choose A or C; **E**lse (normal operation), choose **L**atency or **C**onsistency. More realistic, since most of the time there is no partition.

### 9.3 Consistency Models (strongest to weakest)

Linearizability → sequential → causal → read-your-writes / monotonic reads → eventual consistency.

### 9.4 Time and Ordering

- Physical clocks drift; NTP is not enough for ordering events.
- **Logical clocks** (Lamport), **vector clocks**, **hybrid logical clocks**; Google Spanner uses TrueTime.
- Prefer designs that do not depend on synchronized clocks.

### 9.5 Consensus and Coordination

- **Consensus**: nodes agree on a value despite failures. **Raft** (etcd, Consul) and **Paxos**; **ZAB** (ZooKeeper).
- **Leader election**, **distributed locks** (with fencing tokens; Redlock is debated), **membership** via **gossip protocols**.
- **Quorums**: majority (N/2 + 1) tolerates ⌊(N−1)/2⌋ failures. Use odd cluster sizes.

### 9.6 Delivery Semantics

- **At-most-once**: may lose messages.
- **At-least-once**: may duplicate; consumers must be **idempotent**.
- **Exactly-once**: effectively achieved through at-least-once plus idempotent processing or transactional writes (e.g., Kafka transactions).

### 9.7 Distributed Transactions

- **Two-phase commit (2PC)**: atomic across participants but blocking and fragile.
- **Saga pattern**: sequence of local transactions with **compensating actions**. Two styles: **choreography** (events) and **orchestration** (central coordinator).
- **Transactional outbox** + change data capture: atomically update DB and publish event.

---

## 10. Messaging and Asynchronous Processing

### 10.1 Why Async

Decoupling, load leveling (absorb spikes), resilience to downstream outages, fan-out, and background work.

### 10.2 Models

- **Message queue** (RabbitMQ, SQS, ActiveMQ): point-to-point, each message consumed by one worker.
- **Publish/subscribe** (SNS, Google Pub/Sub, Redis Pub/Sub): message delivered to many subscribers.
- **Event log / streaming** (Kafka, Pulsar, Kinesis, Redpanda): durable, ordered, replayable log; consumer groups; partitions give parallelism and per-key ordering.

### 10.3 Key Concepts

- **Partitions and ordering**: ordering is guaranteed only within a partition; choose the key carefully.
- **Consumer groups**, offsets, rebalancing, lag monitoring.
- **Dead letter queues (DLQ)** for poison messages; retries with exponential backoff and jitter.
- **Idempotent consumers** using message IDs or natural keys.
- **Event types**: commands (intent), events (facts), queries.
- **Schema evolution**: Avro/Protobuf with a schema registry; backward and forward compatibility.
- **Event-carried state transfer** vs. **notification events**.
- **Outbox pattern** and **CDC** (Debezium) for reliable publication.
- **Stream processing**: Flink, Kafka Streams, Spark Streaming; windowing (tumbling, sliding, session), watermarks, late data.

### 10.4 Queue vs. Log

| | Queue | Log |
|---|---|---|
| Retention | Deleted after consume | Kept by time/size |
| Replay | No | Yes |
| Consumers | Competing | Independent groups |
| Ordering | Best effort | Per partition |

---

## 11. Architectural Styles

### 11.1 Monolith

Single deployable unit. **Pros**: simple to develop, test, deploy, debug; no network overhead; easy transactions. **Cons**: scaling is all-or-nothing, tight coupling can grow, long builds, one bug can affect everything.

### 11.2 Modular Monolith

One deployment, strict internal module boundaries with explicit interfaces and separate data ownership. Often the best starting point and a safe path to microservices later.

### 11.3 Layered (N-tier)

Presentation → business → persistence → database. Familiar and simple; can become rigid and lead to anemic domain models.

### 11.4 Microservices

Small, independently deployable services organized around business capabilities, each owning its data.

- **Pros**: independent scaling and deployment, team autonomy, technology flexibility, fault isolation.
- **Cons**: operational complexity, network failures, distributed data consistency, harder debugging and testing, need for mature DevOps, observability, and platform engineering.
- **Use when**: multiple teams, clear domain boundaries, differing scaling needs. **Avoid when**: small team, unclear domain, early product stage.
- **Anti-patterns**: distributed monolith (services must deploy together), shared database, chatty services, too-fine granularity ("nanoservices").

### 11.5 Service-Oriented Architecture (SOA)

Coarser services sharing an enterprise service bus. Precursor to microservices; more centralized governance.

### 11.6 Event-Driven Architecture (EDA)

Components communicate by producing and reacting to events. Loose coupling, easy extension, natural audit trail. Harder to trace flow and reason about end-to-end behavior; eventual consistency.

### 11.7 CQRS (Command Query Responsibility Segregation)

Separate the write model from the read model, each optimized independently (e.g., normalized write DB, denormalized read views or search index). Adds complexity and eventual consistency; use where read and write needs differ significantly.

### 11.8 Event Sourcing

Persist state as an append-only sequence of events; current state is derived by replay. Provides complete audit history, temporal queries, and rebuildable projections. Needs snapshots, event versioning, and care with privacy and deletion.

### 11.9 Hexagonal (Ports and Adapters) / Clean / Onion Architecture

Domain logic at the center with no framework or infrastructure dependencies; **ports** define interfaces; **adapters** implement them (REST, DB, queue). Dependencies point inward. Improves testability and replaceability of infrastructure.

### 11.10 Domain-Driven Design (DDD)

- **Strategic**: ubiquitous language, **bounded contexts**, context maps (shared kernel, customer/supplier, anti-corruption layer), subdomains (core, supporting, generic).
- **Tactical**: entities, value objects, aggregates (consistency boundary), repositories, domain services, domain events.
- Use bounded contexts to decide microservice boundaries.

### 11.11 Serverless / FaaS

Functions triggered by events, managed infrastructure, pay per use (AWS Lambda, Cloud Functions). Great for spiky or event-driven workloads. Watch cold starts, execution limits, vendor lock-in, local testing, and cost at sustained high volume.

### 11.12 Other Styles

- **Microkernel/plugin**: core plus extensions (IDEs, browsers).
- **Pipes and filters**: data flows through processing stages.
- **Space-based**: in-memory data grids for extreme scale.
- **Client-server, peer-to-peer, master-worker, batch/MapReduce.**
- **BFF (Backend for Frontend)**: per-client API layer.
- **Cell-based architecture**: isolated, identical cells limit blast radius.

### 11.13 Choosing a Style

| Situation | Suggested direction |
|---|---|
| New product, small team | Modular monolith |
| Many teams, distinct domains | Microservices with clear bounded contexts |
| Spiky, event-driven work | Serverless / EDA |
| Complex reads and writes | CQRS, possibly event sourcing |
| Heavy audit or replay needs | Event sourcing |
| Long-lived business logic | Hexagonal + DDD |

---

## 12. Microservice and Resilience Patterns

### 12.1 Integration and Structure

- **API Gateway**, **BFF**, **service discovery** (client-side or server-side; Kubernetes DNS, Consul).
- **Strangler Fig**: gradually replace a legacy system by routing functionality to new services.
- **Anti-Corruption Layer**: translate between your model and a legacy/external model.
- **Sidecar**, **Ambassador**, **Adapter**: helper containers next to the main service.
- **Database per service**; share data via APIs or events, not tables.
- **Aggregator / API composition** for cross-service queries; **CQRS read models** for heavy joins.

### 12.2 Resilience Patterns

| Pattern | Purpose |
|---|---|
| **Timeouts** | Never wait forever; set on every remote call |
| **Retries with exponential backoff + jitter** | Recover from transient errors without synchronized storms |
| **Circuit breaker** | Stop calling a failing dependency; states: closed, open, half-open |
| **Bulkhead** | Isolate resources (thread pools, connection pools) so one failure does not sink everything |
| **Rate limiting / load shedding** | Protect capacity by rejecting excess load early |
| **Fallback / graceful degradation** | Serve cached, default, or reduced results |
| **Idempotency** | Make retries safe |
| **Backpressure** | Slow producers when consumers lag |
| **Hedged requests** | Send a second request after a delay to cut tail latency |
| **Health checks, readiness/liveness probes** | Route traffic only to healthy instances |
| **Leader election** | Single active coordinator |
| **Compensating transaction** | Undo completed steps after failure |

Retries multiply load. Use retry budgets and only retry idempotent operations.

### 12.3 Deployment Patterns

Blue/green, canary, rolling updates, feature flags, dark launches, immutable infrastructure, GitOps, progressive delivery (Argo Rollouts, Flagger).

### 12.4 Common Anti-Patterns

Distributed monolith, shared database, synchronous call chains (availability multiplies down), golden hammer, big ball of mud, premature optimization, resume-driven development, no clear ownership.

---

## 13. Reliability Engineering

### 13.1 Availability

| Availability | Downtime per year | Per month |
|---|---|---|
| 99% | 3.65 days | 7.3 h |
| 99.9% ("three nines") | 8.76 h | 43.8 min |
| 99.95% | 4.38 h | 21.9 min |
| 99.99% | 52.6 min | 4.4 min |
| 99.999% | 5.26 min | 26 s |

- **Serial dependencies multiply**: two 99.9% components in series ≈ 99.8%.
- **Parallel redundancy improves**: `1 − (1−A)ⁿ`.
- Eliminate **single points of failure (SPOF)** at every layer.

### 13.2 SLI, SLO, SLA, Error Budget

- **SLI**: measured indicator (e.g., proportion of requests under 300 ms).
- **SLO**: target for an SLI (99.9% over 30 days).
- **SLA**: contractual promise with penalties, looser than the SLO.
- **Error budget** = 1 − SLO. When exhausted, prioritize reliability over features.
- Use **golden signals**: latency, traffic, errors, saturation. RED (rate, errors, duration) for services; USE (utilization, saturation, errors) for resources.

### 13.3 Redundancy and Failure Domains

- Spread across **availability zones**, then **regions**.
- **Failure domains**: process, host, rack, AZ, region, provider, dependency. Design to limit **blast radius**.
- **Active-active** vs. **active-passive** vs. **pilot light** / **warm standby**.

### 13.4 Disaster Recovery

- **RPO** (recovery point objective): how much data loss is tolerable.
- **RTO** (recovery time objective): how long recovery may take.
- Strategies by cost: backup and restore → pilot light → warm standby → multi-site active-active.
- Test restores and failovers regularly. Untested backups are hopes, not backups.

### 13.5 Operations Practices

- **Chaos engineering** (Chaos Monkey, Litmus): inject failure to verify resilience.
- **Load, stress, soak, and spike testing** (k6, Gatling, Locust).
- **Capacity planning** and headroom.
- **Incident management**: on-call, runbooks, severity levels, blameless **postmortems**.
- **Toil reduction** through automation.
- **Graceful shutdown**, **zero-downtime deploys**, **rollbacks**.

---

## 14. Observability

Observability is the ability to ask new questions about system behavior from its outputs.

### 14.1 Three Pillars

- **Metrics**: numeric time series (Prometheus, Grafana, Datadog). Cheap, good for alerting and trends. Beware high-cardinality labels.
- **Logs**: structured (JSON), correlated with trace IDs, centralized (ELK, Loki, Splunk). Do not log secrets or PII.
- **Traces**: request path across services (OpenTelemetry, Jaeger, Tempo, Zipkin). Spans, trace context propagation (W3C `traceparent`).

Add **profiling** and **events** as further signals.

### 14.2 Practices

- Instrument with **OpenTelemetry** to stay vendor-neutral.
- Alert on **symptoms** (SLO burn rate), not every cause. Avoid alert fatigue.
- Dashboards per service with golden signals; separate dashboards for dependencies.
- **Health endpoints** and synthetic monitoring.
- **Correlation IDs** across all logs and calls.
- Track **deployment markers** on graphs to connect changes to regressions.

---

## 15. Security Architecture

### 15.1 Principles

Least privilege, defense in depth, secure by default, zero trust (never trust the network, always verify), fail securely, separation of duties, minimize attack surface, assume breach.

### 15.2 Authentication and Authorization

- **AuthN**: passwords (hashed with Argon2/bcrypt), MFA, passkeys/WebAuthn, SSO.
- **AuthZ models**: RBAC, ABAC, ReBAC, policy engines (OPA).
- **OAuth 2.0** (delegated authorization; use Authorization Code + PKCE) and **OpenID Connect** (identity layer). **SAML** for enterprise SSO.
- **JWT**: signed (RS256/ES256) access tokens with short lifetimes; **refresh token rotation**; revocation strategy; do not store sensitive data in the payload.
- **Service-to-service**: mTLS, workload identity (SPIFFE/SPIRE), signed tokens.

### 15.3 Data Protection

- **Encryption in transit** (TLS 1.2+/1.3) and **at rest** (AES-256, KMS-managed keys, envelope encryption).
- **Secrets management**: Vault, cloud secret managers; never in code or images; rotation.
- **PII handling**: minimization, masking, tokenization, retention limits, data residency.
- **Compliance**: GDPR, SOC 2, ISO 27001, PCI-DSS, HIPAA.

### 15.4 Application and Infrastructure Security

- **OWASP Top 10**: injection, broken access control, cryptographic failures, SSRF, misconfiguration, vulnerable components, and others.
- Input validation, output encoding, CSRF protection, CORS configuration, security headers (CSP, HSTS).
- **Rate limiting**, WAF, DDoS protection, bot management.
- **Network security**: segmentation, private subnets, security groups, network policies, bastion/zero-trust access.
- **Supply chain**: dependency scanning, SBOM, signed images (Sigstore/cosign), pinned versions.
- **DevSecOps**: SAST, DAST, SCA, container and IaC scanning in CI; CIS benchmarks and hardening baselines; audit logging.
- **Threat modeling**: STRIDE (spoofing, tampering, repudiation, information disclosure, denial of service, elevation of privilege).

---

## 16. Cloud and Infrastructure Architecture

### 16.1 Cloud Fundamentals

- Service models: **IaaS**, **PaaS**, **SaaS**, **FaaS**. Shared responsibility model.
- **Regions**, **availability zones**, edge locations.
- **Well-Architected pillars** (AWS/Azure/GCP): operational excellence, security, reliability, performance efficiency, cost optimization, sustainability.

### 16.2 Typical Reference Architecture (AWS-style)

```
Route 53 (DNS)
   → CloudFront (CDN) + WAF
      → ALB (multi-AZ, public subnets)
         → App tier: ECS/EKS/EC2 auto scaling (private subnets, 2+ AZs)
            → ElastiCache (Redis)
            → RDS/Aurora Multi-AZ (data subnets) + read replicas
            → SQS/SNS/MSK for async work
            → S3 for objects
   Observability: CloudWatch / Prometheus + Grafana
   Security: IAM, KMS, Secrets Manager, security groups
   Delivery: CI/CD → registry → Kubernetes/ECS, IaC via Terraform
```

### 16.3 Networking in the Cloud

VPC, public/private subnets, route tables, NAT gateway, internet gateway, security groups vs. NACLs, VPC peering, transit gateway, PrivateLink, VPN/Direct Connect.

### 16.4 Containers and Kubernetes

- **Docker**: image layers, multi-stage builds, small base images, non-root users.
- **Kubernetes objects**: Pod, Deployment, StatefulSet, DaemonSet, Job/CronJob, Service (ClusterIP/NodePort/LoadBalancer), Ingress/Gateway API, ConfigMap, Secret, PV/PVC, HPA/VPA, Namespace, RBAC, NetworkPolicy.
- **Control plane**: API server, etcd, scheduler, controller manager. **Node**: kubelet, container runtime, kube-proxy.
- **Patterns**: readiness/liveness/startup probes, resource requests and limits, PodDisruptionBudgets, affinity/anti-affinity and topology spread across zones, rolling updates, operators and CRDs, Helm/Kustomize, GitOps (Argo CD, Flux).

### 16.5 Infrastructure as Code and Delivery

- **Terraform/OpenTofu**, Pulumi, CloudFormation; **Ansible** for configuration.
- Remote state with locking, modules, environments, drift detection, policy as code (OPA, Sentinel).
- **CI/CD**: build once, promote artifacts; automated tests, security scans, and approvals; DORA metrics (deployment frequency, lead time, change failure rate, MTTR).
- **12-Factor App**: config in environment, stateless processes, logs as streams, dev/prod parity, disposable processes.

### 16.6 Cost Architecture (FinOps)

Right-sizing, autoscaling, reserved/savings plans, spot instances for fault-tolerant work, storage tiering, egress awareness, tagging and cost allocation, unit economics (cost per request/user).

### 16.7 Multi-Region and Multi-Cloud

Multi-region adds latency, data consistency, and cost challenges; justify with RTO/RPO or latency needs. Multi-cloud increases complexity and rarely pays off unless required by regulation or risk policy. Avoid accidental lock-in through open standards where cheap, but accept managed services where they add real value.

---

## 17. Data Architecture

- **Data warehouse** (structured, curated; Snowflake, BigQuery, Redshift), **data lake** (raw, cheap object storage), **lakehouse** (Delta, Iceberg, Hudi combine both).
- **ETL vs. ELT**; orchestration with Airflow, Dagster; transformation with dbt.
- **Batch vs. streaming**: **Lambda architecture** (batch + speed layers) vs. **Kappa** (stream only).
- **Change Data Capture** to move data from OLTP to analytics and search.
- **Data mesh**: domain-owned data products with federated governance.
- **Data quality, lineage, cataloging, governance, and privacy** are architecture concerns, not afterthoughts.
- **Polyglot persistence**: different stores for different needs, with clear ownership and synchronization.
- **ML/AI systems**: feature stores, model serving, vector databases, RAG pipelines (chunking, embeddings, retrieval, re-ranking, evaluation), caching of LLM responses, guardrails, cost and latency budgets, evaluation and monitoring for drift.

---

## 18. Reusable Building Blocks

### 18.1 Unique ID Generation

Auto-increment (simple, single node), UUID v4 (no coordination, poor index locality), **UUID v7 / ULID** (time-sortable), **Snowflake** (timestamp + machine ID + sequence; sortable, 64-bit), **Base62** encoding for short IDs, pre-generated key ranges.

### 18.2 Consistent Hashing

Places nodes and keys on a ring; a key maps to the next node clockwise. Adding or removing a node remaps only about 1/N of keys. Virtual nodes even out load.

### 18.3 Bloom Filter

Probabilistic set membership: no false negatives, some false positives. Used to avoid disk/DB lookups for nonexistent items. Variants: counting Bloom filter, Cuckoo filter. Related: **HyperLogLog** (cardinality), **Count-Min Sketch** (frequency).

### 18.4 Rate Limiter

- **Token bucket** (allows bursts), **leaky bucket** (smooth output), **sliding window counter** (accurate and efficient).
- Store counters in Redis with atomic operations (Lua scripts); limit by user, IP, API key, or endpoint.
- Return `429` with `Retry-After`; consider distributed correctness and clock skew.

### 18.5 Other Common Components

- **Distributed lock / lease** with TTL and fencing tokens.
- **Task scheduler / delayed jobs** (timing wheel, delayed queues).
- **Pub/sub notification system** (push, email, SMS with provider abstraction and retries).
- **Search index** (inverted index, ranking, typeahead via trie).
- **Geospatial index** (geohash, quadtree, S2, H3).
- **Object storage pattern**: metadata in DB, blobs in S3, pre-signed URLs for direct upload/download.
- **Write-ahead log**, **Merkle trees** (anti-entropy), **gossip**, **heartbeat**.
- **Feature flags** and **configuration service**.

---

## 19. Design Principles

- **SOLID**: single responsibility, open/closed, Liskov substitution, interface segregation, dependency inversion.
- **DRY, KISS, YAGNI**; composition over inheritance; separation of concerns.
- **High cohesion, low coupling**; explicit boundaries and interfaces.
- **Law of Demeter**; **principle of least astonishment**.
- **Design for failure**; assume every dependency will fail.
- **Evolutionary architecture**: use fitness functions (automated checks for architectural properties) to guard against erosion.
- **Conway's Law**: system structure mirrors organization structure; use the **inverse Conway maneuver** to shape teams around desired architecture.
- **Reversible vs. irreversible decisions**: decide slowly on one-way doors, quickly on two-way doors.
- **Build vs. buy**: build what differentiates you; buy or adopt commodity.
- **Technical debt**: track it deliberately, pay it down with intent.

---

## 20. Documenting Architecture

### 20.1 C4 Model

1. **Context**: system and its users and external systems.
2. **Container**: deployable units (apps, databases, queues).
3. **Component**: major parts inside a container.
4. **Code**: classes (usually generated, rarely drawn).

Tools: Structurizr, draw.io, PlantUML, Mermaid, Excalidraw. Prefer **diagrams as code**.

### 20.2 Architecture Decision Records (ADR)

Short documents stored with the code, one per significant decision.

```markdown
# ADR-007: Use Kafka for order events

## Status
Accepted (2026-05-12)

## Context
Order service must notify inventory, billing, and analytics reliably,
peak 5k events/s, consumers must be able to replay.

## Decision
Use Apache Kafka with the transactional outbox pattern.

## Alternatives Considered
- RabbitMQ: simpler operations, no replay
- SQS/SNS: managed, weaker ordering and replay

## Consequences
+ Replay, high throughput, decoupled consumers
- Operational complexity, need schema registry and lag monitoring
```

### 20.3 Design Document Template

1. **Title, authors, date, status, reviewers**
2. **Context and problem**
3. **Goals and non-goals**
4. **Requirements** (functional, non-functional, constraints)
5. **Capacity estimates**
6. **High-level architecture** (diagram)
7. **Detailed design**: data model, APIs, key flows, algorithms
8. **Alternatives considered** and trade-offs
9. **Failure modes and mitigations**
10. **Security and privacy**
11. **Observability and SLOs**
12. **Rollout, migration, and rollback plan**
13. **Testing strategy**
14. **Cost estimate**
15. **Open questions and risks**

### 20.4 Other Practices

Architecture reviews, RFC processes, runbooks, API docs (OpenAPI), README-driven architecture, keeping docs near the code and updated with it.

---

## 21. Worked Case Studies

Each summary shows the shape of a good answer: requirements, estimates, design, deep dive, trade-offs.

### 21.1 URL Shortener

- **Requirements**: create short link, redirect, optional expiry, analytics; 100:1 read-heavy; low-latency redirects; high availability.
- **API**: `POST /urls` → `{shortUrl}`; `GET /{code}` → `301/302`.
- **Key generation**: Base62 of a unique ID (counter ranges per server or Snowflake). 7 chars of Base62 ≈ 3.5 trillion codes.
- **Storage**: key-value or SQL table `(code, long_url, created_at, expires_at, user_id)`, sharded by code hash.
- **Read path**: CDN/edge → cache (Redis) → DB. Cache-aside with TTL.
- **Redirect code**: 301 (cacheable, less load, less analytics accuracy) vs. 302 (every hit reaches you).
- **Analytics**: publish click events to Kafka, aggregate asynchronously.
- **Concerns**: collision handling, abuse and malware detection, rate limiting, custom aliases.

### 21.2 Rate Limiter

- Where: API gateway or middleware. Algorithm: token bucket per key.
- State in Redis with atomic Lua script; local approximate counters for very high scale.
- Rules engine for per-plan limits; headers `X-RateLimit-*`; fail-open vs. fail-closed decision.

### 21.3 News Feed / Social Timeline

- **Fan-out on write** (push): precompute follower feeds; fast reads, expensive for users with millions of followers.
- **Fan-out on read** (pull): compute at read time; cheap writes, slow reads.
- **Hybrid**: push for normal users, pull for celebrities, merge at read time.
- Components: post service, graph service, feed cache (Redis sorted sets), ranking service, media on object storage + CDN, cursor pagination.

### 21.4 Chat System

- Persistent connections (WebSocket) to gateway nodes; presence via heartbeats; a connection registry maps user to gateway.
- Routing between gateways via pub/sub; messages persisted in a wide-column store keyed by `(conversation_id, time-sortable message_id)`.
- Delivery states (sent, delivered, read), offline push notifications, ordering per conversation, group fan-out, end-to-end encryption options.

### 21.5 Notification Service

- API → validation and preferences → queue per channel (push, email, SMS) → workers → provider adapters.
- Retries with backoff, DLQ, deduplication with idempotency keys, templating, rate limiting per provider and user, delivery tracking and analytics.

### 21.6 File Storage / Sync (Drive-like)

- Metadata service (SQL) separate from block storage (object store).
- **Chunking** files, content-hash **deduplication**, delta sync, resumable uploads with pre-signed URLs, versioning, sharing and ACLs, notification of changes via long polling or WebSocket, virus scanning pipeline.

### 21.7 Video Streaming

- Upload → object storage → transcoding pipeline (queue + workers, multiple bitrates/codecs) → segments (HLS/DASH) → CDN.
- Adaptive bitrate on client, thumbnail generation, metadata DB, recommendation and search as separate systems, cost dominated by storage and egress.

### 21.8 Ride Sharing / Location Service

- Drivers send location updates every few seconds; store in in-memory geospatial index (geohash/H3/Redis GEO).
- Matching service finds nearby drivers, offer flow with timeouts, trip state machine, surge pricing from streaming aggregates, payments via saga, ETA service using maps and traffic data.

### 21.9 E-Commerce Checkout

- Cart, catalog, inventory, order, payment, shipping as separate bounded contexts.
- Checkout as an **orchestrated saga**: reserve inventory → charge payment → confirm order → ship; compensations on failure.
- **Idempotency keys** on payment calls, outbox for events, inventory reservation with expiry, search via Elasticsearch fed by CDC, cache product pages, oversell prevention with atomic decrements.

### 21.10 Distributed Cache / Key-Value Store

- Partition with consistent hashing, replicate to N nodes, quorum reads/writes, gossip for membership, hinted handoff and read repair, LSM-tree storage, vector clocks or LWW for conflicts, Merkle trees for anti-entropy.

### 21.11 Web Crawler / Search

- URL frontier with priority and politeness (per-host queues), DNS caching, content dedup via hashing/simhash, robots.txt, distributed workers, inverted index build with MapReduce/Spark, ranking, typeahead with tries.

### 21.12 RAG-Based AI Assistant (AI-era case)

- Ingestion: parse, chunk, embed, store in vector DB with metadata.
- Query: rewrite, retrieve (hybrid keyword + vector), re-rank, build prompt, call LLM, stream answer, cite sources.
- Concerns: latency and cost budgets, caching, guardrails and prompt-injection defense, PII handling, evaluation datasets, feedback loop, observability of prompts and outputs, fallback models.

---

## 22. Design Process Framework

A repeatable approach for interviews and real projects:

1. **Clarify** the problem, users, scope, and success criteria. Ask questions first.
2. **Define requirements**: functional, non-functional, constraints. State assumptions.
3. **Estimate scale**: QPS, storage, bandwidth, read/write ratio.
4. **Define APIs and data model**: entities, relationships, access patterns.
5. **Draw the high-level design**: clients, edge, services, data stores, queues.
6. **Walk through key flows** end to end (write path and read path).
7. **Deep dive** into the hardest parts: bottlenecks, partitioning, consistency, hot spots.
8. **Address reliability and failure**: SPOFs, replication, retries, degradation, DR.
9. **Cover security, observability, and operations.**
10. **Discuss trade-offs and alternatives**, and what you would change at 10x scale.
11. **Plan evolution**: MVP first, then phased hardening; migration and rollout.

**Communication tips**: think aloud, name trade-offs explicitly, prefer simple designs, justify every component ("why is this here?"), and revisit requirements when a decision depends on them.

---

## 23. Reference Numbers

### Latency (approximate orders of magnitude)

| Operation | Time |
|---|---|
| L1 cache reference | ~1 ns |
| Main memory reference | ~100 ns |
| SSD random read | ~100 µs |
| Same-datacenter round trip | ~0.5 ms |
| HDD seek | ~10 ms |
| Cross-continent round trip | ~100–150 ms |

Rules of thumb: memory is ~1,000× faster than SSD and ~100,000× faster than a cross-continent call. Compress before sending over long links; batch small writes; keep hot data in memory.

### Powers of Two

| Power | Approx. | Unit |
|---|---|---|
| 2¹⁰ | 1 thousand | 1 KB |
| 2²⁰ | 1 million | 1 MB |
| 2³⁰ | 1 billion | 1 GB |
| 2⁴⁰ | 1 trillion | 1 TB |
| 2⁵⁰ | 1 quadrillion | 1 PB |

### Rough Single-Node Capabilities (very approximate, workload-dependent)

- Well-tuned web server: thousands to tens of thousands of simple requests/s.
- Redis: 50k–200k+ ops/s per node.
- PostgreSQL: thousands to tens of thousands of simple queries/s.
- Kafka broker: hundreds of MB/s throughput.

Always benchmark your own workload.

---

## 24. Learning Roadmap and Resources

### 24.1 Suggested Path

**Phase 1: Foundations (weeks 1–4)**
Networking, HTTP, databases and indexing, caching, load balancing, API design, estimation. Practice: redesign a URL shortener with a written design doc.

**Phase 2: Distributed systems (weeks 5–10)**
Replication, partitioning, consistency, CAP/PACELC, consensus, messaging, idempotency, sagas. Practice: add Kafka and outbox to a project; build a small sharded KV store.

**Phase 3: Architecture and patterns (weeks 11–16)**
Monolith vs. microservices, DDD, CQRS, event sourcing, hexagonal architecture, resilience patterns. Practice: split a modular monolith into two services with an event-driven integration.

**Phase 4: Reliability, security, cloud (weeks 17–24)**
SLOs, observability, chaos testing, threat modeling, multi-AZ AWS with Terraform. Practice: define SLOs, build burn-rate alerts, run a game-day.

**Phase 5: Mastery (ongoing)**
One design per week, write ADRs, read postmortems and engineering blogs, review real architectures, mock design sessions.

### 24.2 Books

- *Designing Data-Intensive Applications*, Martin Kleppmann (the essential text)
- *System Design Interview* Vol. 1 and 2, Alex Xu
- *Fundamentals of Software Architecture*, Mark Richards and Neal Ford
- *Software Architecture: The Hard Parts*, Ford, Richards, Sadalage, Dehghani
- *Building Microservices*, Sam Newman
- *Domain-Driven Design*, Eric Evans; *Implementing DDD*, Vaughn Vernon
- *Clean Architecture*, Robert C. Martin
- *Release It!*, Michael Nygard (resilience patterns)
- *Site Reliability Engineering* and *The SRE Workbook* (free online)
- *Database Internals*, Alex Petrov
- *Building Evolutionary Architectures*, Ford, Parsons, Kua
- *A Philosophy of Software Design*, John Ousterhout
- *Team Topologies*, Skelton and Pais

### 24.3 Online Resources

- High Scalability blog, ByteByteGo, System Design Primer (GitHub)
- Engineering blogs: Netflix, Uber, Stripe, Cloudflare, Discord, Meta, Google Cloud
- AWS, Azure, and Google Well-Architected Frameworks
- Martin Fowler's site (patterns catalog), microservices.io (Chris Richardson)
- Papers: Google File System, MapReduce, Bigtable, Dynamo, Spanner, Raft, Kafka, "Time, Clocks, and the Ordering of Events"
- Certifications: AWS Solutions Architect (Associate/Professional), CKA/CKAD, Terraform Associate

### 24.4 Practice Checklist

- [ ] Write a design doc with estimates for 3 classic systems
- [ ] Draw C4 diagrams for your own projects
- [ ] Add ADRs to your repositories
- [ ] Load test a service and find its bottleneck
- [ ] Implement a rate limiter and a consistent hash ring
- [ ] Build a saga with compensations
- [ ] Deploy a multi-AZ stack with Terraform
- [ ] Define SLOs and error-budget alerts in Prometheus
- [ ] Run a failure-injection exercise and write a postmortem
- [ ] Do a timed mock design session weekly

---

*End of guide. Treat every section as a starting point: the real skill is knowing which trade-off fits the problem in front of you.*
