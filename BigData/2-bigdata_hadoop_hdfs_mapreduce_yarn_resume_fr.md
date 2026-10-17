# Guide d'étude Big Data : Hadoop, HDFS, MapReduce & YARN

*D'après le cours du Dr. Maher HENI, Fondamentaux de la Data Science*

---

## Table des matières

**Partie 1 : Hadoop & HDFS**
1. Pourquoi Hadoop ?
2. Qu'est-ce que Hadoop ?
3. Principes et architecture de HDFS
4. Blocs et réplication
5. Prise en compte des racks (rack awareness)
6. Compression de fichiers
7. Démarrage du NameNode, journal des edits, fsimage
8. Écriture d'un fichier dans HDFS
9. Gestion du cluster
10. Haute disponibilité du NameNode, Secondary NameNode, Fédération
11. Commandes shell HDFS
12. Point de contrôle de la Partie 1

**Partie 2 : MapReduce & YARN**
1. Le modèle MapReduce v1
2. Phases : Map, Shuffle, Reduce, Combiner
3. Exemple WordCount (+ code Java)
4. Splits et classes d'entrée
5. Exécution d'un job (MRv1) et tolérance aux pannes
6. Limites de MRv1
7. YARN
8. Hadoop 1 vs Hadoop 2
9. Spark et Ambari
10. Point de contrôle de la Partie 2

---

# PARTIE 1 : HADOOP & HDFS

## 1. Pourquoi Hadoop ?

**L'idée centrale :** quand les données sont trop volumineuses pour une seule machine, on les répartit sur de nombreuses machines bon marché (un *cluster*) et on **amène le calcul vers les données** au lieu de déplacer les données vers le calcul.

- Lire un énorme fichier depuis un seul disque est lent. Le lire **en parallèle** depuis plusieurs disques est rapide.
- La vitesse du réseau entre les nœuds compte, donc Hadoop essaie d'éviter de déplacer les données.
- Contexte tiré des slides : le marché du Big Data devait passer d'environ **5,4 Md$ (2012) à 53,4 Md$ (2017)** (Wikibon). Hortonworks prévoyait que plus de la moitié des données mondiales serait stockée dans Hadoop d'ici 5 ans.

### Hadoop est adapté pour
- Traiter **d'énormes volumes de données** en parallèle
- La **variété** des données : structurées, semi-structurées, non structurées
- Fonctionner sur du **matériel standard peu coûteux** (commodity hardware)

### Hadoop n'est PAS adapté pour
- Le **traitement transactionnel** (accès aléatoire, comme un système bancaire)
- Les tâches **non parallélisables**
- L'accès aux données à **faible latence**
- La gestion de **nombreux petits fichiers**
- Les calculs intensifs sur de **petites quantités de données**

> **À retenir :** Hadoop = **traitement par lots (batch)** de gros volumes de données, pas de l'OLTP.

---

## 2. Qu'est-ce que Hadoop ?

Un **framework open source Apache** (écrit en **Java**) pour le calcul **distribué, scalable et fiable** sur de grandes données. Il masque la complexité du système sous-jacent à l'utilisateur et est conçu pour du matériel hétérogène.

### Les 4 modules principaux
| Module | Rôle |
|---|---|
| **HDFS** | Stockage distribué (où vivent les données) |
| **MapReduce** | Modèle de traitement (comment le travail est calculé) |
| **YARN** | Gestion des ressources et ordonnancement |
| **Hadoop Common** | Bibliothèques et utilitaires partagés |

### L'écosystème (projets open source autour de Hadoop)
HBase, Hive, Pig, Spark, Kafka, NiFi, Mahout, Impala, ZooKeeper, Avro, Ambari...

---

## 3. HDFS : Hadoop Distributed File System

### Principes de base
- **Distribué, scalable, tolérant aux pannes, haut débit**
- Données accessibles via **MapReduce**
- Les fichiers sont **découpés en blocs**
- **3 réplicas** de chaque bloc par défaut
- On peut **créer, supprimer, copier**, mais **pas modifier** les fichiers (écriture unique, lecture multiple)
- Conçu pour la **lecture en flux (streaming)**, pas pour l'accès aléatoire
- **Localité des données :** traiter les données sur ou près de leur lieu de stockage pour réduire les transferts réseau

### Architecture : Maître / Esclave

```
                 NameNode  (maître)
                 FsImage + journal des Edits, métadonnées uniquement
                /     |      |      \
         DataNode DataNode DataNode DataNode  (esclaves)
          blocs    blocs    blocs    blocs
```

| | NameNode (Maître) | DataNode (Esclave) |
|---|---|---|
| Nombre | 1 actif par cluster (2 pour la HA) | Nombreux par cluster |
| Stocke | Les **métadonnées** : noms de fichiers, quels blocs, où | Les **blocs de données** réels |
| Fichiers | **FsImage** (instantané) + **journal des Edits** (modifications) | Fichiers de blocs dans son répertoire de données |
| Rôle | Gère l'**accès des clients** et l'espace de noms | Signale son état **périodiquement (heartbeat)** au NameNode |

---

## 4. Blocs et réplication

### Blocs
- Chaque fichier est découpé en blocs. **Taille de bloc par défaut = 128 Mo**.
- Les blocs résident sur différents DataNodes physiques.
- Si le dernier morceau fait moins de 128 Mo, il n'occupe que l'espace nécessaire.
- **Exemple :** un fichier de 410 Mo devient **128 + 128 + 128 + 26 Mo** (4 blocs).

### Réplication
- Le facteur de réplication est **configurable par fichier** ; par défaut = **3**.
- **Stratégie de placement (rack-aware) :**
  1. **1er réplica :** sur n'importe quel nœud du cluster
  2. **2e réplica :** sur un **nœud différent dans le même rack**
  3. **3e réplica :** sur un **nœud différent dans un second rack**
- Cela équilibre la **tolérance aux pannes** (survit à la panne d'un rack) et le **coût réseau**.

---

## 5. Configuration des racks

- Ici, un « rack » désigne le **switch réseau** auquel un nœud est connecté, et non le châssis métallique physique.
- Défini via un **script de topologie**, référencé dans `core-site.xml` :

```xml
<property>
  <name>net.topology.script.file.name</name>
  <value>/etc/hadoop/conf/rack-topology.sh</value>
</property>
```

- Le script reçoit des adresses IP de nœuds en arguments et renvoie les **noms de racks** sur la sortie standard (un par IP).
- Astuce simple : utiliser des IP de la forme `10.x.y.z` où **x = cluster, y = rack, z = nœud**, et décoder `y/z` dans le script.

---

## 6. Compression de fichiers

**Avantages :** moins d'espace de stockage et transferts réseau/disque plus rapides.
**Question clé :** le format est-il **splittable** (découpable) ? (nécessaire pour la lecture parallèle)

| Format | Algorithme | Extension | Splittable ? |
|---|---|---|---|
| DEFLATE | DEFLATE | .deflate | Non |
| gzip | DEFLATE | .gz | Non |
| bzip2 | bzip2 | .bz2 | **Oui** |
| LZO | LZO | .lzo | Oui, **si indexé** |
| LZ4 | LZ4 | .lz4 | Non |
| Snappy | Snappy | .snappy | Non |

### Lequel choisir ? (du meilleur au pire)
1. Utiliser un **format conteneur** (SequenceFile, Avro, ORC, Parquet)
2. Utiliser un **compresseur rapide** (LZO, LZ4, Snappy)
3. Utiliser un format **splittable** (bzip2, qui est lent, ou LZO indexé)
4. **Découper le fichier en morceaux** et compresser chacun séparément (morceau ≈ taille d'un bloc HDFS)
5. Stocker **sans compression**

> Le bon choix dépend de la taille du fichier, du format des données et des outils utilisés.

---

## 7. Démarrage du NameNode

1. Le NameNode **lit le fsimage** en mémoire.
2. Le NameNode **applique les modifications du journal des edits**.
3. Le NameNode **attend les rapports de blocs** des DataNodes.
   - Il ne **stocke pas** les emplacements des blocs de façon persistante ; il les reconstruit à partir des DataNodes.
   - Il quitte le **SafeMode** lorsque **99,9 % des blocs** ont au moins une copie signalée.

Stocké dans le répertoire du NameNode (`namedir`) : fichiers `fsimage_*` et de nombreux `edits_*`.

---

## 8. Écriture d'un fichier dans HDFS (mécanisme de réplication)

1. Le client contacte le **NameNode**, qui décide **quels DataNodes** stockeront les réplicas de chaque bloc.
2. Le NameNode enregistre le nouveau fichier en mémoire et dans le **journal des edits**.
3. L'API client envoie un bloc au **premier DataNode**.
4. Le premier DataNode fait une **chaîne (daisy-chain)** : il écrit sur le 2e, le 2e écrit sur le 3e (avec des acquittements remontant le long de la chaîne).
5. Le premier DataNode **confirme au NameNode** que la réplication est terminée.

> L'écriture réussit si au moins une écriture par bloc réussit.

---

## 9. Gestion du cluster

| Tâche | Comment |
|---|---|
| **Ajouter un DataNode** | Démarrer un nouveau DataNode pointant vers le NameNode ; lancer `hadoop balancer` pour rééquilibrer les blocs |
| **Retirer un nœud** | Le retirer simplement, ou mieux : l'ajouter au *fichier d'exclusion* et attendre que tous ses blocs soient déplacés |
| **Interface Web** | Console d'administration du NameNode sur le port **50070** |
| **Vérifier la santé du système de fichiers** | `hadoop fsck` |

---

## 10. NameNode : HA, Secondaire, Fédération

### 10.1 Haute disponibilité (HA) du NameNode HDFS-2
- **NameNode Actif + NameNode Standby** pour un basculement rapide.
- Chaque modification du système de fichiers est écrite sur **au moins 3 JournalNodes** (quorum) par le NN actif.
- Le Standby applique les modifications depuis les JournalNodes en temps réel.
- **Les DataNodes envoient les emplacements de blocs et les heartbeats aux deux** NameNodes.
- Le basculement est **bien plus rapide qu'un démarrage à froid**.

### 10.2 Secondary NameNode
- Le NN primaire **ne peut pas fusionner fsimage + journal des edits pendant qu'il tourne**. Le Secondary le fait :
  1. Copie le nouveau journal des edits depuis le primaire
  2. Le fusionne dans le fsimage
  3. Recopie le nouveau fsimage fusionné vers le primaire
- **Ce n'est PAS une solution de haute disponibilité**, mais il offre un **démarrage plus rapide**. Il n'a pas d'image complète, donc les transactions en cours seraient perdues.
- Moins nécessaire avec la HA, mais garde des avantages : pas de nœuds de quorum, moins de trafic réseau, moins d'éléments mobiles.

### 10.3 Options de mise en place
- **Hadoop 2 avec HA :** pas de point unique de défaillance (la meilleure option, largement supportée).
- **Hadoop 2 sans HA / Hadoop 1.x :** copier `namedir` vers un NFS (RAID), utiliser une IP virtuelle pour la sauvegarde du NameNode. Le basculement nécessite encore du temps pour lire les blocs.

### 10.4 NameNode fédéré (HDFS-2)
- **Problème :** le NameNode peut devenir un **goulot d'étranglement** sur de très grands clusters (environ **1 Go de RAM pour 1 million de blocs**, soit ~100 To de données).
- **Solution :** plusieurs NameNodes, chacun gérant son propre **espace de noms / pool de blocs**. Les DataNodes s'enregistrent auprès de tous les NN et stockent des données pour tous les pools.
- Un espace de noms + son pool de blocs = **Namespace Volume**. Un nouveau **ClusterID** identifie tous les NN.
- **Avantages :** la panne d'un NN n'affecte pas les autres pools, et meilleure scalabilité pour de nombreuses opérations sur les fichiers.

---

## 11. Commandes shell HDFS (`hadoop fs`)

```bash
hadoop fs -ls .                                  # lister le répertoire HDFS courant
hadoop fs -mkdir /newdir                         # créer un répertoire
hadoop fs -put localsrc dst                      # local -> HDFS
hadoop fs -copyFromLocal localsrc dst            # identique à put
hadoop fs -get <src> <localdst>                  # HDFS -> local
hadoop fs -copyToLocal <src> <localdst>          # identique à get
```

- Les commandes prennent des **URI** : `scheme://authority/path`
  - Système de fichiers local : `file:///myfile.txt`
  - HDFS : `hdfs://localhost:9000/user/virtuser/myfile.txt`
- **Style POSIX :** `cat, chgrp, chmod, chown, cp, du, ls, mkdir, mv, rm, stat, tail`
- **Spécifiques à HDFS :** `copyFromLocal, put, copyToLocal, get, getmerge, setrep`

---

## 12. Point de contrôle de la Partie 1 (avec réponses)

| Question | Réponse |
|---|---|
| Vrai/Faux : Hadoop est conçu pour le traitement transactionnel. | **Faux.** Il est conçu pour le traitement **par lots (batch)**. |
| Citez quelques projets open source Hadoop. | MapReduce, YARN, Ambari, Hive, HBase... |
| Nombre de réplicas par défaut ? | **3** |
| Vrai/Faux : les données sont amenées au programme. | **Faux.** C'est le **programme qui est amené aux données**. |
| Vrai/Faux : au moins 2 NameNodes sont requis pour un cluster autonome. | **Faux.** **1** est requis ; **2 sont nécessaires pour la HA**. |

---

# PARTIE 2 : MAPREDUCE & YARN

## 1. MapReduce v1 : le modèle

- Les données sont stockées dans un **système de fichiers distribué** (HDFS).
- **Amener la fonction aux données**, et distribuer l'application aux nœuds qui détiennent les données.
- Passe à l'échelle de **milliers de nœuds et de pétaoctets** de données.

### Les 3 étapes
1. **Phase Map :** découper le job en petites parties
2. **Shuffle :** transférer la sortie intermédiaire pour le traitement final
3. **Phase Reduce :** ramener toutes les sorties à un seul jeu de résultats

### Moteur MRv1 : Maître / Esclave
| Composant | Rôle |
|---|---|
| **JobTracker** (1 maître) | Accepte les jobs des clients, les découpe en tâches map/reduce, envoie les tâches aux TaskTrackers **proches des données**, surveille les tâches et la santé des TaskTrackers |
| **TaskTracker** (nombreux esclaves) | Exécute les tâches map et reduce, rapporte son état au JobTracker, gère le stockage et la transmission de la sortie intermédiaire |

### API vs environnement d'exécution
- **API :** `org.apache.hadoop.mapred` (ancienne) et `org.apache.hadoop.mapreduce` (nouvelle, plus flexible, peut nécessiter une recompilation pour YARN).
- **Environnements d'exécution :** JobTracker/TaskTracker classique (Hadoop 1) ou **YARN** (MapReduce v2).

---

## 2. Les phases MapReduce en détail

### 2.1 Phase Map
- Les **Mappers** sont de petits programmes, distribués sur le cluster et **locaux aux données**.
- Chacun reçoit **un split** de l'entrée, puis l'analyse, la filtre ou la transforme.
- Sortie = **paires `<clé, valeur>`** regroupées.
- La sortie du mapper est **triée** et stockée sur le **système de fichiers local** du nœud (pas HDFS).

### 2.2 Phase Shuffle
- La sortie des mappers est **regroupée localement par clé**.
- Un nœud est choisi pour traiter **chaque clé unique**.
- Le déplacement des données est **géré de façon transparente** par MapReduce.
- La distribution utilise un **partitionneur** (normalement un **partitionneur par hachage**).
- Le nombre de tâches map et reduce **n'a pas besoin de correspondre**.

### 2.3 Phase Reduce
- Les **Reducers** rassemblent **toutes les valeurs des clés dont ils sont responsables**.
- Chaque reducer écrit sa sortie dans **son propre fichier** dans HDFS (donc répliqué).
- Par défaut : **un fichier part de sortie par tâche reduce**.

### 2.4 Combiner (optionnel)
- Un **mini-reduce côté map**, avant le shuffle.
- Trie et fusionne les données **avant** de les envoyer aux reducers, ce qui **minimise le trafic réseau** et réduit l'effort de fusion.
- Exemple : `<Tiger,1>, <Tiger,1>` sur un nœud devient `<Tiger,2>` avant l'envoi.

```
Blocs HDFS -> [Map] -> tri (& combine) -> copie/Shuffle -> fusion -> [Reduce] -> HDFS
```

---

## 3. Exemple WordCount (Animaux)

**Objectif :** compter la fréquence de chaque animal (équivalent SQL : `SELECT COUNT(NAME) FROM ANIMALS GROUP BY NAME`).

| Étape | Entrée nœud 1 : Tiger, Lion, Lion, Panther, Wolf | Entrée nœud 2 : Tiger, Tiger, Wolf, Panther |
|---|---|---|
| **Map** | `<Tiger,1> <Lion,1> <Lion,1> <Panther,1>...` | `<Tiger,1> <Tiger,1> <Panther,1>...` |
| **Shuffle** | Toutes les valeurs d'une même clé vont au même reducer | |
| **Reduce** | `<Panther,<1,1>>` -> `<Panther,2>`<br>`<Tiger,<1,1,1>>` -> `<Tiger,3>` | `<Lion,<1,1>>` -> `<Lion,2>` (sur un autre nœud) |

Les tâches map ont 2 rôles : **filtrer les lignes** et **transformer en `<Text(nom), Integer(1)>`**.

### Code Java (ancienne API `mapred`)

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
      output.collect(word, one);          // émet <mot, 1>
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
      sum += values.next().get();         // additionne les 1
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
conf.setCombinerClass(Reduce.class);   // Reducer réutilisé comme Combiner
conf.setReducerClass(Reduce.class);
conf.setInputFormat(TextInputFormat.class);
conf.setOutputFormat(TextOutputFormat.class);
FileInputFormat.setInputPaths(conf, new Path(args[0]));
FileOutputFormat.setOutputPath(conf, new Path(args[1]));
JobClient.runJob(conf);
```

**Trois éléments à toujours retenir :** classe Mapper + classe Reducer + Driver (configuration du job).

---

## 4. Splits et classes d'entrée

- Les fichiers sont stockés en blocs de 128 Mo ; MapReduce divise les données en **splits**. **Une tâche Map s'exécute par split.**
- Les splits correspondent normalement à la taille du bloc, mais dépendent du nombre de tâches map demandées, de la possibilité de découper la compression, etc.
- Objectif : **traiter un maximum de données localement**.

| Classe | Rôle |
|---|---|
| **InputSplitter** | Prend un fichier HDFS et le transforme en splits |
| **RecordReader** | Lit un split en enregistrements (ex. un enregistrement par ligne, `LineRecordReader`). Un enregistrement peut s'étendre sur plusieurs splits |
| **InputFormat** | Transforme chaque enregistrement en paire `<clé, valeur>` pour la tâche Map |

---

## 5. Comment Hadoop (MRv1) exécute un job

1. Le **client** (JobClient) lance le job
2. Demande au JobTracker un **nouvel ID de job**
3. **Copie les ressources du job** (jar, configuration) dans HDFS
4. **Soumet** le job au JobTracker
5. Le JobTracker **initialise** le job
6. Le JobTracker **récupère les splits d'entrée**
7. Les TaskTrackers envoient des **heartbeats** (et reçoivent des tâches en réponse)
8. Le TaskTracker **récupère les ressources du job**
9. **Lance** une JVM enfant
10. **Exécute** la tâche Map ou Reduce

### Tolérance aux pannes dans MRv1 (3 types de défaillance)
1. **Une tâche échoue** -> le JobTracker/TaskTracker la relance
2. **Un TaskTracker échoue** (les heartbeats s'arrêtent) -> ses tâches sont replanifiées ailleurs
3. **Le JobTracker échoue** -> le cas le plus grave (point unique de défaillance)

---

## 6. Limites de MapReduce v1

| Problème | Explication |
|---|---|
| **Scalabilité** | Un seul JobTracker suit des milliers de TaskTrackers, des centaines de jobs et des dizaines de milliers de tâches : il est **surchargé** (limite autour de 4000 nœuds) |
| **Utilisation des ressources** | **Slots map et slots reduce** fixes, donc des ressources peuvent rester inactives |
| **Uniquement MapReduce** | Impossible d'exécuter d'autres charges (streaming, graphes, in-memory...) |
| **Contrôle centralisé du flux des jobs** | Le JobTracker fait tout |
| **Couplage fort** | Modèle de programmation lié à l'infrastructure de gestion des ressources |
| **Point unique de défaillance** | JobTracker |

Hadoop est désormais utilisé pour toutes sortes de tâches au-delà de sa conception d'origine.

---

## 7. YARN (Yet Another Resource Negotiator)

**Idée :** séparer les deux rôles du JobTracker (**gestion des ressources** et **ordonnancement/surveillance des jobs**) en daemons distincts.

### Composants
| Composant | Rôle |
|---|---|
| **ResourceManager (RM)** | Global ; **autorité suprême** qui arbitre les ressources entre toutes les applications. Suit les NodeManagers actifs et les ressources disponibles. Se concentre **uniquement sur l'ordonnancement** |
| **NodeManager (NM)** | Un par nœud (esclave) ; fournit les ressources de calcul sous forme de **conteneurs** et gère les processus qui s'y exécutent |
| **ApplicationMaster (AM)** | **Un par application** ; négocie les ressources avec le RM, travaille avec les NM pour exécuter et surveiller les tâches. De courte durée, dédié |
| **Conteneur (Container)** | Ensemble de ressources (RAM, CPU, disque, E/S réseau) pouvant exécuter n'importe quel type de tâche (même un ApplicationMaster) |
| **Client** | Soumet tout type d'application supportée par YARN |

> Une **application** = soit un job MapReduce classique, soit un **DAG** (graphe orienté acyclique) de jobs.

### Terminologie MRv1 vs YARN
| MRv1 | YARN |
|---|---|
| Gestionnaire de cluster / JobTracker | **ResourceManager** |
| JobTracker (partie job) | **ApplicationMaster** (dédié, de courte durée) |
| TaskTracker | **NodeManager** |
| Un job MapReduce particulier | **Application distribuée** |
| Slot | **Conteneur** |

### Comment YARN exécute une application (étapes)
1. Le client **soumet** l'application YARN au **ResourceManager**
2. Le RM **démarre un conteneur** sur un NodeManager qui héberge l'**ApplicationMaster**
3. L'AM envoie des **demandes de ressources** (via heartbeat) et le RM répond avec des **IDs de conteneurs**
4. L'AM **lance des conteneurs** sur d'autres NodeManagers, qui exécutent les processus de l'application
5. Plusieurs applications s'exécutent côte à côte (App 1 avec AM 1, App 2 avec AM 2) en partageant le même cluster

### Fonctionnalités de YARN
| Fonctionnalité | Signification |
|---|---|
| **Scalabilité** | Un AM par job, le RM ne fait que de l'ordonnancement. Passe à l'échelle de milliers de nœuds / pétaoctets. Supporte les mises à jour progressives (rolling upgrades) |
| **Multi-tenance** | Des moteurs batch, interactifs et temps réel accèdent aux **mêmes données** en même temps sur un pool de nœuds partagé |
| **Compatibilité** | Les jobs MapReduce existants s'exécutent sans changement avec les mêmes API/CLI (recompilation parfois nécessaire) |
| **Meilleure utilisation du cluster** | Le NodeManager crée des **conteneurs dynamiques** au lieu de slots map/reduce fixes |
| **Fiabilité / disponibilité** | HA du RM : après un redémarrage, il récupère l'état des applications (stocké dans HDFS) et ne relance que les tâches incomplètes. NameNode HA aussi |
| **Facilité de maintenance** | La séparation des fonctions facilite les améliorations |

---

## 8. Hadoop 1 vs Hadoop 2

| | **Hadoop 1** | **Hadoop 2** |
|---|---|---|
| Stockage | HDFS | HDFS2 (avec HA, fédération) |
| Gestion des ressources | Dans MapReduce (JobTracker) | **YARN** |
| Traitement | **MapReduce uniquement** | MapReduce v2, **Spark, Tez, HBase, Storm/stream, graphes...** |
| Type | Système à usage unique (batch) | **Plateforme polyvalente** (batch, interactif, en ligne, streaming) |

**HDFS est commun aux deux versions.** YARN est *agnostique* au type de travail. MapReduce devient simplement une application YARN parmi d'autres.

---

## 9. Spark avec Hadoop 2+, et Ambari

### Spark
- Un framework **en mémoire (in-memory)**, alternative à MapReduce (plus rapide).
- Supporte les charges générales, le **streaming, les requêtes interactives, le machine learning**.
- **Spark SQL :** intégrer des requêtes SQL dans des programmes Java, Scala, Python
- **MLlib :** bibliothèque de machine learning
- **GraphX :** graphes et calcul parallèle
- **Spark Streaming :** traitement de données en flux

### Apache Ambari (provisionner, gérer, surveiller)
- **Provisionner :** assistant pas à pas pour installer les services Hadoop sur de nombreux hôtes
- **Gérer :** démarrer/arrêter/reconfigurer les services de façon centralisée
- **Surveiller :** tableau de bord de santé du cluster (utilise **Ganglia** pour les métriques et **Nagios** pour les alertes/e-mails)
- Propose des **API REST** pour l'intégration dans vos propres applications

---

## 10. Point de contrôle de la Partie 2 (avec réponses)

| Question | Réponse |
|---|---|
| Listez les phases d'un job MR. | **Map, Shuffle, Reduce** (+ **Combiner** optionnel) |
| Limites de MR v1 ? | Contrôle centralisé des jobs ; couplage fort entre le modèle de programmation et la gestion des ressources ; Hadoop utilisé au-delà de sa conception d'origine (aussi : scalabilité du JobTracker, slots fixes) |
| Qu'est-ce qui remplace le JobTracker dans YARN ? | Le **ResourceManager** et l'**ApplicationMaster** |
| Principales fonctionnalités de YARN ? | **Multi-tenance, utilisation du cluster, scalabilité, compatibilité** |

---

# FICHE DE RÉVISION RAPIDE

**Chiffres à retenir**
- Taille de bloc par défaut : **128 Mo**
- Réplication par défaut : **3**
- Sortie du safe mode du NameNode : **99,9 %** des blocs signalés
- RAM du NameNode : ~**1 Go par million de blocs**
- Port de l'interface Web : **50070**
- La HA nécessite **3 JournalNodes** (quorum) et **2 NameNodes** (Actif + Standby)

**Phrases clés**
- HDFS = **écriture unique, lecture multiple** ; pas de modification, pas d'accès aléatoire.
- NameNode = **métadonnées** ; DataNode = **données**.
- Règle des réplicas : 1er n'importe où, 2e même rack (nœud différent), 3e rack différent.
- Le Secondary NameNode **n'est pas une sauvegarde** ; il fusionne seulement fsimage + edits.
- Map -> `<clé,valeur>` ; Shuffle -> regroupement par clé (partitionneur par hachage) ; Reduce -> agrégation.
- Combiner = reduce local **avant** le shuffle pour économiser le réseau.
- La sortie du Map va sur le **disque local** ; la sortie du Reduce va dans **HDFS**.
- MRv1 : **JobTracker + TaskTrackers** ; YARN : **ResourceManager + NodeManagers + ApplicationMaster par application**.
- YARN permet à Hadoop d'exécuter **plusieurs moteurs** (MapReduce, Spark, Tez, HBase) sur les mêmes données.

**Questions d'examen probables à travailler**
1. Expliquez le rôle du NameNode par rapport aux DataNodes.
2. Décrivez comment un fichier de 410 Mo est stocké et répliqué.
3. Déroulez WordCount : Map -> Shuffle -> Reduce (+ Combiner).
4. Pourquoi YARN a-t-il été créé ? Quelles étaient les limites de MRv1 ?
5. Comparez les architectures Hadoop 1 et Hadoop 2.
6. Expliquez NameNode HA vs Secondary NameNode vs Fédération.
7. Quels formats de compression sont splittables, et pourquoi est-ce important ?
