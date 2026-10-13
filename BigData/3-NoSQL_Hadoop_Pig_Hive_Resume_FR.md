# Les systèmes de gestion de données NoSQL — Hadoop, Pig et Hive
**Chapitre :** Les systèmes de gestion de données NoSQL (fichier « X05 et X06 — PIG et HIVE »)
**Enseignant :** Dr. Maher HENI
**Type de document :** Résumé d'étude avec explications, tableaux comparatifs, code et conseils d'examen

---

## Table des matières

1. [Vue d'ensemble du chapitre](#1-vue-densemble-du-chapitre)
2. [L'environnement de traitement Hadoop v1](#2-lenvironnement-de-traitement-hadoop-v1)
3. [L'environnement de traitement Hadoop v2](#3-lenvironnement-de-traitement-hadoop-v2)
4. [Pig et Hive : deux langages open source](#4-pig-et-hive--deux-langages-open-source)
5. [Apache Pig](#5-apache-pig)
6. [Pig vs SQL](#6-pig-vs-sql)
7. [Caractéristiques du langage Pig Latin](#7-caractéristiques-du-langage-pig-latin)
8. [Exemple de script Pig : WordCount](#8-exemple-de-script-pig--wordcount)
9. [Apache Hive](#9-apache-hive)
10. [Pourquoi du SQL pour Hadoop ?](#10-pourquoi-du-sql-pour-hadoop-)
11. [Java vs Hive : l'algorithme WordCount](#11-java-vs-hive--lalgorithme-wordcount)
12. [Les composants de Hive](#12-les-composants-de-hive)
13. [Démarrer Hive : le shell](#13-démarrer-hive--le-shell)
14. [Types de données dans Hive](#14-types-de-données-dans-hive)
15. [Partitionnement et bucketing](#15-partitionnement-et-bucketing)
16. [Organisation physique des données (entrepôt Hive)](#16-organisation-physique-des-données-entrepôt-hive)
17. [Création d'une table dans Hive](#17-création-dune-table-dans-hive)
18. [Remarques sur le support de cours](#18-remarques-sur-le-support-de-cours)
19. [Fiche de révision rapide](#19-fiche-de-révision-rapide)

---

## 1. Vue d'ensemble du chapitre

Ce chapitre présente **l'écosystème Hadoop** pour le traitement de grandes masses de données (Big Data) et deux outils de haut niveau construits au-dessus de **MapReduce** :

| Outil | Origine | Idée centrale |
|---|---|---|
| **Pig** | Développé à l'origine chez **Yahoo** | Langage de **flux de données** (*Pig Latin*), idéal pour les **transformations (ETL)** |
| **Hive** | Développé à l'origine chez **Facebook** | Interface de type **SQL** (*HiveQL*) pour interroger des données sur Hadoop |

**Fil conducteur :** MapReduce est puissant mais **difficile à programmer** (beaucoup de code Java). Pig et Hive **cachent cette complexité** : on écrit quelques lignes, et l'outil les **traduit en jobs MapReduce**.

---

## 2. L'environnement de traitement Hadoop v1

Hadoop v1 est un **framework multi-couches** :

| Couche | Composants |
|---|---|
| **API** | MapReduce · Pig · Hive · HBase |
| **Processing Framework** (cadre de traitement) **+ Resource Management** (gestion des ressources) | **MapReduce v1** (assure *les deux* rôles à la fois) |
| **Distributed Storage** (stockage distribué) | **HDFS** |

```
┌────────────────────────────────────────────┐
│ API :   MapReduce │ Pig │ Hive │ HBase     │
├────────────────────────────────────────────┤
│ Processing Framework  ┐                    │
│                       ├─ MapReduce v1      │
│ Resource Management   ┘                    │
├────────────────────────────────────────────┤
│ Distributed Storage :  HDFS                │
└────────────────────────────────────────────┘
```

> 🔑 **Limite de la v1 :** MapReduce v1 gère à la fois le **traitement** et les **ressources du cluster**. C'est ce que la v2 sépare.

---

## 3. L'environnement de traitement Hadoop v2

**Quatre niveaux** dans un cluster Hadoop, **de bas en haut** :

1. **Stockage distribué**
2. **Gestion des ressources**
3. **Cadre de traitement**
4. **API** (interfaces de programmation d'applications)

| Couche | Composants |
|---|---|
| **API** | MapReduce · MapReduce · Pig · Hive · HBase · **Giraph** (traitement de graphes) · **MPI** (passage de messages) · **Storm** (données en flux / *streaming*) |
| **Processing Framework** | **MapReduce v2** · **Tez** · **Hoya** *(+ Giraph, MPI, Storm)* |
| **Resource Management** | **YARN** |
| **Distributed Storage** | **HDFS** |

### Hadoop v1 vs v2

| Critère | **Hadoop v1** | **Hadoop v2** |
|---|---|---|
| Gestion des ressources | Intégrée à **MapReduce v1** | **YARN** (couche séparée) |
| Cadres de traitement | **MapReduce uniquement** | MapReduce v2, **Tez**, **Hoya**, Giraph, MPI, Storm… |
| Types de traitement | Batch (par lots) | Batch, **graphes**, **messages**, **flux temps réel** |
| Stockage | HDFS | HDFS |

> 💡 *(Ajout, non détaillé dans les diapositives)* **YARN** = *Yet Another Resource Negotiator*. **Hoya** = HBase sur YARN. **Tez** = moteur d'exécution plus rapide que MapReduce classique, utilisé notamment par Hive et Pig.

---

## 4. Pig et Hive : deux langages open source

| Critère | **Pig** | **Hive** |
|---|---|---|
| **Origine** | Yahoo | Facebook |
| **Nature** | **Langage de programmation de haut niveau**, excellent pour la **transformation de données (ETL)** — *meilleur que Hive pour les données non structurées* | Fournit une **interface de type SQL**, permettant l'**abstraction** des données au-dessus de données **semi-structurées non relationnelles** |
| **Langage** | **Pig Latin** | **HiveQL** |
| **Paradigme** | **Procédural / flux de données** | **Déclaratif** (dialecte SQL) |
| **Requêtes ad hoc** | **Ne convient pas** aux requêtes ad hoc | **Très bon** pour l'analyse ad hoc, mais pas nécessairement pour les utilisateurs finaux ; tire parti de l'**expertise SQL** |
| **Lecture des données** | Lit les données dans un **grand nombre de formats** de fichiers et de bases de données | Utilise une interface **SerDe** (sérialisation / désérialisation) pour lire les données d'une table et les réécrire dans n'importe quel format personnalisé. Nombreux SerDes standard, et on peut écrire le sien |
| **Exécution** | Le **compilateur** traduit Pig Latin en **séquences de programmes MapReduce** | Les requêtes sont traduites en jobs MapReduce |
| **Public recommandé** | Personnes familiarisées avec les **langages de script** (ex. **Python**) | Personnes familières avec **SQL** |

> 🔑 **À retenir pour l'examen :** *Pig = procédural (« comment »), Hive = déclaratif (« quoi »).*

---

## 5. Apache Pig

### Modes d'exécution

| Mode | Description |
|---|---|
| **Mode local** | Sur **une seule machine**, **sans nécessiter HDFS** |
| **Mode MapReduce / Hadoop** | Exécution **sur un cluster HDFS** ; le script Pig est **converti en job MapReduce** |

### Points clés

- En **shell interactif**, l'invite est **`grunt>`**.
- Les scripts Pig portent, **par convention**, le suffixe **`.pig`**.
- Le langage s'appelle **Pig Latin**.

### Architecture (schéma du cours)

```
 CLIENT :   Terminal Linux (grunt>)  |  Scripts Pig  |  Pig Latin embarqué
                          \               |               /
                           ▼              ▼              ▼
 PIG :                 ┌─────────────────────────────┐
                       │   Compilateur Pig Latin     │
                       └──────────────┬──────────────┘
                              ┌───────┴────────┐
                              ▼                ▼
 HADOOP :              LocalJobRunner     Cluster Hadoop
                        (Mode local)      (Mode Hadoop)
```

Les trois façons de soumettre du code (terminal `grunt>`, scripts, Pig Latin embarqué) passent **toutes par le même compilateur**, qui envoie ensuite le travail vers le **mode local** ou le **cluster Hadoop**.

---

## 6. Pig vs SQL

### Contrairement à SQL, Pig…

- utilise l'**évaluation paresseuse** (*lazy evaluation*) : rien n'est exécuté tant qu'on ne demande pas un résultat (`STORE`, `DUMP`) ;
- utilise des techniques **ETL** (Extract, Transform, Load) ;
- peut **stocker des données à tout moment** pendant un pipeline ;
- **déclare des plans d'exécution** ;
- prend en charge les **divisions de pipeline** (un même flux peut être scindé en plusieurs branches).

### Pig Latin vs SQL

| | **Pig Latin** | **SQL** |
|---|---|---|
| Type | Langage **procédural** | Langage **déclaratif** |
| Paradigme | **Pipeline** (suite d'étapes) | Une requête décrit le résultat voulu |

### SGBD relationnels vs MapReduce

| | **SGBD (relationnels)** | **Système MapReduce** |
|---|---|---|
| **Vitesse d'interrogation** | **Beaucoup plus rapides** une fois les données chargées | Plus lent à l'interrogation |
| **Chargement des données** | **Beaucoup plus long** dans les systèmes de base de données | Plus rapide (pas de schéma imposé à l'avance) |
| **Fonctionnalités prêtes à l'emploi** | Stockage en **colonnes**, données **compressées**, **index** pour accès aléatoire efficace, **tolérance aux pannes au niveau des transactions** | Moins de fonctionnalités natives |

---

## 7. Caractéristiques du langage Pig Latin

| Élément | Rôle |
|---|---|
| **`LOAD`** | La plupart des scripts commencent par `LOAD` pour **lire les données de HDFS** (ou du système de fichiers local en mode local) |
| **`USING`** | **Mappe** les données du fichier au **modèle de données de Pig** (ex. chargement d'un fichier `.csv`) |
| **Agrégations** | Utilisées pour **résumer** l'ensemble de données ; variantes : **`GROUP`**, **`ALL`**, **`GROUP ALL`** |
| **`FOREACH … GENERATE`** | **Transforme les données** colonne par colonne |

### Principaux opérateurs (vus dans l'exemple)

| Opérateur | Rôle |
|---|---|
| `LOAD` | Charger des données |
| `FOREACH … GENERATE` | Transformer / projeter |
| `TOKENIZE` | Découper une ligne en mots (donne un *bag*) |
| `FLATTEN` | « Aplatir » un *bag* : un élément par ligne |
| `FILTER … BY` | Filtrer des lignes |
| `GROUP … BY` | Regrouper |
| `COUNT` | Compter |
| `ORDER … BY` | Trier |
| `STORE … INTO` | Écrire le résultat |

---

## 8. Exemple de script Pig : WordCount

```pig
input_lines = LOAD '/tmp/my-data' AS (line: chararray);

-- Extraire les mots de chaque ligne et les mettre dans un "bag" Pig,
-- puis aplatir le bag pour obtenir un mot par ligne
words = FOREACH input_lines GENERATE FLATTEN(TOKENIZE(line)) AS word;

-- Éliminer les "mots" qui ne sont que des espaces
filtered_words = FILTER words BY word MATCHES '\\w+';

-- Créer un groupe pour chaque mot
word_groups = GROUP filtered_words BY word;

-- Compter les entrées de chaque groupe
word_count = FOREACH word_groups GENERATE COUNT(filtered_words) AS count, group AS word;

-- Trier par nombre d'occurrences décroissant et écrire le résultat
ordered_word_count = ORDER word_count BY count DESC;
STORE ordered_word_count INTO '/tmp/sorted-word-count';
```

### Explication pas à pas

| Étape | Instruction | Ce qui se passe |
|---|---|---|
| 1 | `LOAD` | Lit chaque ligne du fichier (`chararray` = chaîne) |
| 2 | `FOREACH … FLATTEN(TOKENIZE(…))` | Découpe chaque ligne en mots et met **un mot par ligne** |
| 3 | `FILTER … MATCHES '\\w+'` | Garde seulement les mots composés de caractères alphanumériques |
| 4 | `GROUP … BY word` | Regroupe les lignes **identiques** |
| 5 | `FOREACH … COUNT` | Compte les éléments de chaque groupe (`group` = la clé de regroupement) |
| 6 | `ORDER … BY count DESC` | Trie du plus fréquent au moins fréquent |
| 7 | `STORE … INTO` | **Écrit** le résultat dans HDFS (c'est ici que le calcul démarre vraiment — évaluation paresseuse) |

---

## 9. Apache Hive

### Qu'est-ce que Hive ?

> **Un système de gestion et d'interrogation de données structurées construit sur Hadoop.**

| Composant utilisé | Rôle |
|---|---|
| **MapReduce** | Pour l'**exécution** |
| **HDFS** | Pour le **stockage** |
| **Métadonnées** | Décrivent les **fichiers bruts** (schéma des tables) |

### Principes de construction clés

1. **SQL** est un langage de gestion de données **familier**.
2. **Extensibilité** : types, fonctions, formats, scripts.
3. **Évolutivité et performances**.

---

## 10. Pourquoi du SQL pour Hadoop ?

- **Alimenter l'entrepôt de données** est un cas d'usage **très courant** de Hadoop.
- MapReduce est très évolutif mais **notoirement difficile à utiliser** :
  - l'**API Java** est **fastidieuse** et demande de l'expertise en programmation ;
  - des langages inconnus (comme **Pig**) demandent aussi de l'expertise ;
  - **nombreux formats de fichiers**, mécanismes de stockage, options de configuration, etc.
- Le **support SQL ouvre les données à un public beaucoup plus large** :
  - syntaxe **familière** et largement connue ;
  - **catalogue commun** pour l'identification des données et de leur structure ;
  - **séparation claire** entre la définition du **quoi** (ce qu'on veut) et du **comment** (pour l'obtenir).

---

## 11. Java vs Hive : l'algorithme WordCount

Le cours compare **le même programme** (compter les occurrences de chaque mot) dans deux langages.

### En Java (MapReduce) — plus de 58 lignes

Le programme comprend :
- une classe **`Map`** (étend `Mapper`) : découpe chaque ligne en mots et émet `(mot, 1)` ;
- une classe **`Reduce`** (étend `Reducer`) : additionne les `1` de chaque mot ;
- une méthode **`main`** : configure le job (types de clés/valeurs, classes Map/Reduce, formats d'entrée/sortie, chemins) et le lance avec `job.waitForCompletion(true)`.

```java
public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        StringTokenizer tokenizer = new StringTokenizer(value.toString());
        while (tokenizer.hasMoreTokens()) {
            word.set(tokenizer.nextToken());
            context.write(word, one);                 // émet (mot, 1)
        }
    }
}

public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) sum += val.get();
        context.write(key, new IntWritable(sum));     // émet (mot, total)
    }
}
```
*(Extrait des classes `Map` et `Reduce` ; le programme complet inclut aussi les imports et la méthode `main`.)*

### En HiveQL — seulement 6 lignes

```sql
CREATE TABLE docs (line STRING);
LOAD DATA INPATH 'docs' OVERWRITE INTO TABLE docs;
CREATE TABLE word_counts AS
SELECT word, count(1) AS count FROM
  (SELECT explode(split(line, '\s')) AS word FROM docs)
GROUP BY word
ORDER BY word;
```
*(Source citée par le cours : Capriolo, Wampler & Rutherglen, 2012, « Programming Hive ».)*

### Comparaison

| | **Java (MapReduce)** | **HiveQL** |
|---|---|---|
| Taille | **Plus de 58 lignes** | **6 lignes** |
| Compilation | **Oui** | **Non** |
| Fichier JAR | **À créer** (Java ARchive) pour l'exécuter | **Inutile** |
| Expertise requise | Programmation Java + MapReduce | **SQL** |

### Les 3 outils pour le même problème

| | Lignes de code (approx.) | Style |
|---|---|---|
| MapReduce Java | 58+ | Impératif, bas niveau |
| Pig Latin | ~7 instructions | Procédural, flux de données |
| HiveQL | 6 | Déclaratif, SQL |

---

## 12. Les composants de Hive

Architecture en **4 niveaux** (schéma du cours) :

| Niveau | Composants |
|---|---|
| **Applications** | **Hive Application** |
| **Client Interfaces (remote)** | **Web Browser** · **Thrift Client** · **JDBC** · **ODBC** |
| **Query Execution** (exécution des requêtes) | **Hive Web Interface** · **Hive Server** · **CLI** (`hive>`) |
| **Metadata** | **Metastore** · **Hive Metastore Driver** · **JobConf Config** |

**Flux :**
- Le **navigateur web** passe par la **Hive Web Interface**.
- Les clients **Thrift, JDBC et ODBC** passent par le **Hive Server**.
- Une **application Hive** locale utilise la **CLI**.
- Tout s'appuie sur le **Metastore** (la base qui stocke les **métadonnées** : tables, colonnes, partitions).

---

## 13. Démarrer Hive : le shell

Le shell Hive se trouve dans **`$HIVE_HOME/bin/hive`**.

**Depuis le shell, on peut :**
- exécuter des **requêtes**, du **DML** et du **DDL** ;
- **voir et manipuler les métadonnées** des tables ;
- obtenir les **plans d'exécution** des requêtes (`EXPLAIN`, la stratégie d'exécution).

```text
$ $HIVE_HOME/bin/hive
...
hive> show tables;
mytab1
mytab2
mytab3
OK
Time taken: 2.987 seconds
hive> quit;
```

---

## 14. Types de données dans Hive

Hive prend en charge des types **scalaires** et **structurés** :

| Famille | Types | Exemple |
|---|---|---|
| **Entiers** | `tinyint`, `smallint`, `int`, `bigint` | |
| **Décimaux** | `float`, `double` | |
| **Booléen** | `boolean` | |
| **Texte / binaire** | `string`, `binary` | |
| **Date/heure** | `timestamp` | |
| **Array** (tableau) | `array<type>` | `array<int>` |
| **Struct** (structure) | `struct<champ:type,...>` | `struct<f1:int,f2:array<string>>` |
| **Map** (dictionnaire) | `map<clé,valeur>` | `map<int,string>` |
| **Union** | `uniontype<...>` | `uniontype<int,string,double>` |

---

## 15. Partitionnement et bucketing

### Partitionnement

- On peut partitionner sur **une ou plusieurs colonnes**.
- **Partitionnement par valeur uniquement** : le partitionnement **par plage n'est pas (encore) pris en charge**.
- **Sous-répertoires imbriqués dans HDFS** pour **chaque combinaison** de valeurs des colonnes de partition.

**Exemple :** colonnes de partition `ds` et `ctry`.

| Combinaison | Sous-répertoire HDFS |
|---|---|
| `ds = 20090801`, `ctry = US` | `…/hive/warehouse/pview/ds=20090801/ctry=US` |
| `ds = 20090801`, `ctry = CA` | `…/hive/warehouse/pview/ds=20090801/ctry=CA` |

> 🔑 **Avantage :** une requête qui filtre sur `ds` ou `ctry` ne lit que les répertoires concernés (**élimination de partitions**) → requêtes beaucoup plus rapides.

### Bucketing (regroupement)

- **Sous-partitionnement** des données, par **hachage**, à l'intérieur des partitions.
- Utile pour l'**échantillonnage** et **améliore certaines opérations de jointure**.

| | **Partitionnement** | **Bucketing** |
|---|---|---|
| Critère | **Valeur** de la colonne | **Hachage** de la colonne |
| Structure | **Sous-répertoires** | **Fichiers** (buckets) |
| Utilité | Éliminer des données à lire | Échantillonnage, jointures |

---

## 16. Organisation physique des données (entrepôt Hive)

```
.../hive/warehouse/
    db1.db/                     ← une base (schéma) = un sous-répertoire « .db »
        tab1/                   ← table « tab1 »
            tab1.dat
        part_tab1/              ← table partitionnée par la colonne « state »
            state=NJ/           ← un sous-répertoire par valeur unique
                part_tab1.dat
            state=CA/
                part_tab1.dat
```

**Points clés :**
- Les **bases de données (schémas)** sont contenues dans des sous-répertoires **« .db »**.
- Les **fichiers de données** sont de **simples fichiers HDFS**.
- Le **format interne** peut varier **d'une table à l'autre** (texte délimité, *sequence file*, etc.).
- Les **prédicats de requête éliminent** les partitions (répertoires) qu'il n'est pas nécessaire de lire.
- Hive supporte les **tables externes** (les données restent à leur emplacement d'origine).

---

## 17. Création d'une table dans Hive

### Création d'une table à fichier délimité

```sql
hive> create table users
(
   id int,
   office_id int,
   name string,
   children array<string>
)
row format delimited
   fields terminated by '|'
   collection items terminated by ':'
stored as textfile;
```

| Clause | Signification |
|---|---|
| `row format delimited` | Les lignes sont des **champs séparés par des délimiteurs** |
| `fields terminated by '|'` | Les **champs** sont séparés par le caractère **`|`** |
| `collection items terminated by ':'` | Les **éléments d'une collection** (array) sont séparés par **`:`** |
| `stored as textfile` | Stockage en **fichier texte** |

### Inspection des tables

```sql
hive> show tables;
OK
users
Time taken: 2.542 seconds

hive> describe users;
OK
id          int
office_id   int
name        string
children    array<string>
Time taken: 0.129 seconds
```

### Fichier de données associé : `users.dat`

```text
1|1|Bob Smith|Mary
2|1|Frank Barney|James:Liz:Karen
3|2|Ellen Lacy|Randy:Martin
4|3|Jake Gray|
5|4|Sally Fields|John:Fred:Sue:Hank:Robert
```

**Lecture d'une ligne :** `2|1|Frank Barney|James:Liz:Karen` → `id = 2`, `office_id = 1`, `name = Frank Barney`, `children = ["James", "Liz", "Karen"]`.
Pour `4|3|Jake Gray|` → la liste `children` est **vide**.

> 💡 *(Ajout)* Pour charger ce fichier dans la table : `LOAD DATA LOCAL INPATH 'users.dat' INTO TABLE users;` — puis, par exemple, `SELECT name, children[0] FROM users;` pour obtenir le premier enfant.

---

## 18. Remarques sur le support de cours

Quelques coquilles et points à connaître pour éviter la confusion :

1. **Titre du fichier** : « X05 et X06 » regroupe deux séances, **Pig** et **Hive**, dans le chapitre sur les systèmes **NoSQL** (écosystème Hadoop).
2. **« Pig run »** (diapositive Pig) : la commande s'appelle simplement **`pig`** ; l'invite en mode interactif est `grunt>`.
3. **« GROUPE, TOUT, GROUPE TOUT »** : ce sont les mots-clés Pig **`GROUP`**, **`ALL`**, **`GROUP ALL`** (traduits par erreur en français).
4. **Script Pig WordCount** : le support écrit `TOKENIZE(LINE)` ; Pig est **sensible à la casse**, il faut `TOKENIZE(line)` (nom du champ défini dans le `LOAD`).
5. **WordCount Java** : le support écrit `Job = new Job(conf, "wordcount");` ; la bonne syntaxe est **`Job job = new Job(conf, "wordcount");`**.
6. **WordCount Hive** : en HiveQL, l'expression régulière doit s'écrire **`'\\s'`** (échappement doublé) ; et `'docs'` doit être entre **apostrophes droites** (le support utilise des guillemets typographiques `‘docs'`).
7. **Exemple de shell Hive** : le support affiche une fois `mytab12` (probablement un artefact de mise en page) ; la liste correcte est `mytab1`, `mytab2`, `mytab3`.
8. **Les noms « v1 / v2 »** : en pratique, Hadoop v2 (puis v3) est la version utilisée ; MapReduce v1 est **historique**.
9. **Pig et Hive aujourd'hui** *(ajout)* : les deux outils sont en recul face à **Apache Spark** (Spark SQL, DataFrames), mais restent très présents dans les cours et dans de nombreux entrepôts existants.

---

## 19. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Hadoop v1** | API (MapReduce, Pig, Hive, HBase) → **MapReduce v1** (traitement + ressources) → **HDFS** |
| **Hadoop v2** | Ajoute **YARN** (gestion des ressources) et d'autres cadres : **Tez, Hoya, Giraph, MPI, Storm** |
| **4 niveaux Hadoop v2** | Stockage distribué → Gestion des ressources → Cadre de traitement → API |
| **Pig** | Origine **Yahoo** · langage **Pig Latin** · **procédural** / flux de données · ETL |
| **Hive** | Origine **Facebook** · langage **HiveQL** · **déclaratif** (SQL) · analyse ad hoc |
| **Pig recommandé pour** | Les familiers des langages de script (Python) |
| **Hive recommandé pour** | Les familiers de **SQL** |
| **Modes Pig** | **Local** (sans HDFS) · **MapReduce/Hadoop** (sur cluster) |
| **Shell Pig** | Invite **`grunt>`** · scripts `.pig` |
| **Pig vs SQL** | Évaluation **paresseuse**, ETL, stockage à tout moment, plans d'exécution, divisions de pipeline |
| **SGBD vs MapReduce** | SGBD : requêtes **plus rapides** mais **chargement plus lent** ; riche en fonctionnalités |
| **Opérateurs Pig** | `LOAD`, `FOREACH…GENERATE`, `FILTER`, `GROUP`, `ORDER`, `STORE` |
| **Hive =** | **MapReduce** (exécution) + **HDFS** (stockage) + **métadonnées** |
| **Principes de Hive** | **SQL** familier · **extensibilité** · **évolutivité/performances** |
| **WordCount** | Java : **58+ lignes** · HiveQL : **6 lignes**, sans compilation ni JAR |
| **Composants Hive** | Clients (Web, Thrift, JDBC, ODBC) · Serveur / CLI · **Metastore** |
| **SerDe** | **Ser**ialisation / **De**sérialisation : lire/écrire des formats personnalisés |
| **Types Hive** | Scalaires + `array`, `struct`, `map`, `uniontype` |
| **Partitionnement** | Par **valeur** (pas par plage) → **sous-répertoires** HDFS (`col=valeur`) |
| **Bucketing** | Sous-partitionnement par **hachage** → échantillonnage, jointures |
| **Entrepôt Hive** | `.../hive/warehouse/db.db/table/` ; fichiers HDFS ordinaires ; **tables externes** possibles |
| **Création de table** | `row format delimited … fields terminated by … stored as textfile` |

### Questions d'examen typiques
1. Décrire les **couches** de **Hadoop v1** puis de **Hadoop v2**. Quel est l'apport de **YARN** ?
2. Citer les **quatre niveaux** d'un cluster Hadoop v2, de bas en haut.
3. **Comparer Pig et Hive** (origine, langage, paradigme, public, cas d'usage).
4. Pourquoi dit-on que **Pig Latin est procédural** et **HiveQL déclaratif** ?
5. Quels sont les **deux modes d'exécution de Pig** ? Que signifie l'invite `grunt>` ?
6. Citer **cinq différences** entre **Pig et SQL** (évaluation paresseuse, ETL, etc.).
7. Pourquoi les SGBD sont-ils **plus rapides à l'interrogation** mais **plus lents au chargement** que MapReduce ?
8. **Expliquer le script Pig WordCount** instruction par instruction.
9. Pourquoi ajouter une **couche SQL** à Hadoop ? Quels problèmes de MapReduce résout-elle ?
10. Comparer **WordCount en Java** et **en HiveQL** (lignes de code, compilation, JAR).
11. Décrire l'**architecture de Hive** et le rôle du **Metastore**.
12. Qu'est-ce qu'un **SerDe** ?
13. Quels sont les **types de données** de Hive ? Donner un exemple de `array`, `struct` et `map`.
14. Différence entre **partitionnement** et **bucketing** ; donner l'arborescence HDFS d'une table partitionnée par `state`.
15. **Écrire** l'instruction `CREATE TABLE` d'une table `users` avec une colonne de type `array<string>` et dire comment lire la ligne `2|1|Frank Barney|James:Liz:Karen`.

---

*Fin du résumé — NoSQL : Hadoop, Pig et Hive.*
