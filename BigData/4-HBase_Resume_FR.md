# HBase — Résumé complet du cours
**Chapitre :** Stockage et accès aux données — HBase (fichier « X_07 Hbase »)
**Enseignant :** Dr. Maher HENI
**Type de document :** Résumé d'étude avec explications, schémas, tableaux comparatifs et conseils d'examen

---

## Table des matières

1. [Qu'est-ce que HBase ?](#1-quest-ce-que-hbase-)
2. [Pourquoi le NoSQL ?](#2-pourquoi-le-nosql-)
3. [Pourquoi HBase en particulier ?](#3-pourquoi-hbase-en-particulier-)
4. [HBase et les propriétés ACID](#4-hbase-et-les-propriétés-acid)
5. [Le modèle de données HBase](#5-le-modèle-de-données-hbase)
6. [Penser « Map » : ce n'est pas une feuille de calcul](#6-penser--map--ce-nest-pas-une-feuille-de-calcul)
7. [Vue logique du modèle de données](#7-vue-logique-du-modèle-de-données)
8. [Les familles de colonnes (Column Family)](#8-les-familles-de-colonnes-column-family)
9. [HBase vs SGBD relationnel](#9-hbase-vs-sgbd-relationnel)
10. [Exemple concret : table relationnelle → HBase](#10-exemple-concret--table-relationnelle--hbase)
11. [Plus de détails sur le modèle de données](#11-plus-de-détails-sur-le-modèle-de-données)
12. [Les index dans HBase](#12-les-index-dans-hbase)
13. [Architecture d'un cluster HBase](#13-architecture-dun-cluster-hbase)
14. [Composants et rôles](#14-composants-et-rôles)
15. [Comment les tables sont-elles stockées ? (auto-sharding)](#15-comment-les-tables-sont-elles-stockées--auto-sharding)
16. [Quand NE PAS utiliser HBase ?](#16-quand-ne-pas-utiliser-hbase-)
17. [Quand utiliser HBase ?](#17-quand-utiliser-hbase-)
18. [Commandes de base du shell HBase (ajout)](#18-commandes-de-base-du-shell-hbase-ajout)
19. [Remarques sur le support de cours](#19-remarques-sur-le-support-de-cours)
20. [Fiche de révision rapide](#20-fiche-de-révision-rapide)

---

## 1. Qu'est-ce que HBase ?

- Une **implémentation de la conception BigTable de Google**.
  > *« Une Bigtable est une **carte triée multidimensionnelle, dispersée (clairsemée), répartie et persistante** »* — article de Google (OSDI 2006).
- Un **projet open source Apache de premier niveau** (*Top Level Project*).
  - Adopté et pris en charge par **HDP, CDH** et toutes les principales distributions Hadoop.
- Alimente certains des **plus grands sites web** (Facebook, Yahoo, etc.).
- C'est un **magasin de données NoSQL** de type **orienté colonnes** (*column-oriented data store*).

### Décryptage de la définition de BigTable

| Mot | Signification |
|---|---|
| **Sparse** (clairsemée) | Les colonnes qui n'existent pas ne coûtent rien (pas de `NULL` stockés) |
| **Distributed** (répartie) | Les données sont réparties sur **plusieurs machines** |
| **Persistent** (persistante) | Les données sont **durables** (stockées sur disque, HDFS) |
| **Multidimensional** | Indexée par **ligne, colonne et horodatage** |
| **Sorted map** (carte triée) | Structure **clé → valeur**, **triée par clé** |

---

## 2. Pourquoi le NoSQL ?

Trois arguments du cours :

1. **Une technologie rentable est nécessaire** pour gérer de **nouveaux volumes de données** (des pétaoctets aux zettaoctets).
2. L'**augmentation des volumes** conduit à un **partage (sharding) des SGBDR** : on répartit une base sur plusieurs serveurs (A, B, C…), ce qui devient **complexe et coûteux**.
3. Des **modèles de données flexibles** sont nécessaires pour les applications **Big Data**.

**Exemple du cours (table rigide) :**

| Id | Fn | Ln | Addr |
|---|---|---|---|
| 1 | Fred | Jones | Liberty, NY |
| 2 | John | Smith | **??????** |

→ Dans un modèle relationnel, **chaque ligne a les mêmes colonnes** : que faire quand une information est inconnue ou n'existe pas ? On stocke des `NULL`. Le NoSQL permet à **chaque ligne d'avoir son propre ensemble de colonnes**.

---

## 3. Pourquoi HBase en particulier ?

| Propriété | Explication |
|---|---|
| **Très évolutif** | **Partitionnement automatique** (*sharding*) ; **échelle linéaire et automatique** quand on ajoute des nœuds |
| **Faible latence** | Prend en charge la **lecture/écriture aléatoire** et les **balayages de petite plage** |
| **Hautement disponible** | Pas de point de défaillance unique (avec ZooKeeper et serveurs de secours) |
| **Forte cohérence** | Une lecture renvoie toujours la dernière valeur écrite |
| **Très bon pour les « données clairsemées »** | Pas de colonnes fixes |

---

## 4. HBase et les propriétés ACID

| Propriété | Ce que garantit HBase |
|---|---|
| **Atomicité** | Toutes les lectures/écritures d'une région sont effectuées par le **serveur de région affecté**. Tous les clients doivent parler à ce serveur pour accéder aux données. HBase fournit une **atomicité au niveau de la ligne** |
| **Cohérence et isolement** | Toute ligne retournée par n'importe quelle API sera **une ligne complète qui a existé à un moment donné** dans l'historique de la table. ⚠️ **Un scan n'est pas une vue cohérente de la table** : pas d'**isolation de cliché** (*snapshot isolation*) — mais chaque ligne renvoyée est cohérente |
| **Durabilité** | **Toutes les données visibles sont durables** : une lecture ne renverra jamais de données qui n'ont pas été rendues durables sur disque |

> 🔑 **À retenir :** HBase offre l'ACID **uniquement à l'échelle d'une seule ligne**, **pas** sur plusieurs lignes ni plusieurs tables.

---

## 5. Le modèle de données HBase

- Les données sont stockées dans des **tables HBase**, constituées de **lignes et de colonnes**.
- **Toutes les colonnes appartiennent à une famille de colonnes** particulière.
- Le **schéma d'une table ne définit que les familles de colonnes**.
  - Chaque ligne peut avoir un **nombre grand et variable de colonnes**.
- Une valeur est identifiée par : **(clé de ligne, clé de colonne, horodatage) → valeur**.
  - Le tuple **{row, column, version}** désigne **exactement une cellule**.
- **Chaque valeur de cellule a une version** (un **horodatage**).
- Les lignes sont **stockées dans l'ordre des clés de ligne**.
  - Les clés de ligne sont des **tableaux d'octets**, **triés lexicographiquement**.

> *« … une carte triée multidimensionnelle, clairsemée, répartie et persistante. La carte est indexée par une **clé de ligne, une clé de colonne et un horodatage** ; chaque valeur de la carte est un **tableau d'octets non interprété**. »* — article BigTable de Google

---

## 6. Penser « Map » : ce n'est pas une feuille de calcul

- La plupart des publications décrivent HBase comme **orienté colonnes**, ce qui est vrai mais **peut prêter à confusion**.
- **Tableau associatif (map / dictionnaire)** : type de données abstrait composé d'une collection de paires **(clé, valeur)**, chaque clé apparaissant **au plus une fois**.
- **Techniquement, HBase est une carte triée multidimensionnelle.**

| ❌ Feuille de calcul | ✅ HBase |
|---|---|
| Grille fixe de cellules (lignes × colonnes) | **Dictionnaire** de paires clé → valeur |
| Toutes les cellules « existent » (même vides) | Les colonnes **n'existent que lorsqu'elles sont insérées** (*sparse*) |
| Une valeur par cellule | **Plusieurs versions** horodatées par cellule |

> Les **familles de colonnes** contiennent des **colonnes avec des versions horodatées**. Les colonnes n'existent que quand elles sont insérées.

---

## 7. Vue logique du modèle de données

Vocabulaire :

| Terme | Définition |
|---|---|
| **Table** | Conteneur de lignes (ex. `HBTABLE`) |
| **Row key** (clé de ligne) | **Clé primaire implicite** ; sert à **stocker les lignes ordonnées** ; permet des **requêtes efficaces** |
| **Column family** | **Regroupement logique et physique** des colonnes |
| **Column** (colonne) | **N'existe que lorsqu'elle est insérée** ; peut avoir **plusieurs versions** ; **chaque ligne peut avoir un ensemble de colonnes différent** ; identifiée par sa clé |

**Exemple du cours :**

```text
Row key 11111 :
   cf_data: { 'cq_name': 'name1', 'cq_val': 1111 }
   cf_info: { 'cq_desc': 'desc11111' }

Row key 22222 :
   cf_data: { 'cq_name': 'name2',
              'cq_val': 2013 @ ts = 2013,
              'cq_val': 2012 @ ts = 2012 }
```

→ La ligne `22222` possède **deux versions** de `cq_val` (2013 et 2012) et **n'a pas** de famille `cf_info`.

### Stockage physique : les HFile

Les données sont écrites dans des fichiers **HFile**, **un ensemble de HFile par famille de colonnes** :

```text
HFile (cf_data)                              HFile (cf_info)
11111  cf_data  cq_name  name1  @ ts1        11111  cf_info  cq_desc  desc11111 @ ts1
11111  cf_data  cq_val   1111   @ ts1
22222  cf_data  cq_name  name2   @ ts1
22222  cf_data  cq_val   2013    @ ts1
22222  cf_data  cq_val   2012    @ ts2
```

Chaque ligne d'un HFile est une **paire clé-valeur complète** : `ligne | famille | qualificatif | horodatage | valeur`.

---

## 8. Les familles de colonnes (Column Family)

- **Unité de stockage de base.** Les colonnes d'une même famille doivent avoir des **propriétés et des tailles similaires**.
- **Configurable par famille de colonnes** :
  - **Plusieurs versions horodatées** (la « 3ᵉ dimension » du tableau) ;
  - **Compression** : *aucune, Gzip, LZO, Snappy* ;
  - **Stratégies de conservation des versions** : **TTL** (*Time To Live*).
- Une colonne se nomme avec la syntaxe : **`famille:qualificatif`**.

> *« Les clés de colonnes sont regroupées en ensembles appelés familles de colonnes, qui forment l'**unité de base du contrôle d'accès**. »* — article BigTable

| Pourquoi regrouper en familles ? | Explication |
|---|---|
| **Organisation physique** | Chaque famille a ses **propres fichiers** sur disque |
| **Réglages adaptés** | Compression, TTL, nombre de versions **par famille** |
| **Contrôle d'accès** | Les droits s'appliquent au niveau de la famille |

---

## 9. HBase vs SGBD relationnel

| Critère | **HBase** | **SGBD relationnel** |
|---|---|---|
| **Organisation des données** | Carte triée multidimensionnelle, clairsemée, répartie, persistante | Orientée **lignes** ou **colonnes** |
| **Transactions** | **ACID sur une seule ligne uniquement** | **Oui** (multi-lignes, multi-tables) |
| **Langage de requête** | **get / put / scan** uniquement (sauf couplage avec **Hive** ou une autre technologie) | **SQL** |
| **Sécurité** | Authentification / Autorisation | Authentification / Autorisation |
| **Index** | **Clé de ligne uniquement** (ou table spéciale) | **Oui** |
| **Débit** | **Millions** de requêtes par seconde | **Milliers** de requêtes par seconde |
| **Taille maximale** | **Pétaoctets (PB)** | **Téraoctets (TB)** |

---

## 10. Exemple concret : table relationnelle → HBase

### 10.1 Table relationnelle classique

| SSN *(clé primaire)* | Nom | Prénom | N° de compte | Type de compte | Horodatage |
|---|---|---|---|---|---|
| 01234 | Smith | John | abcd1234 | Checking | 20120618… |
| 01235 | Johnson | Michael | wxyz1234 | Checking | 20121118… |
| 01235 | Johnson | Michael | aabb1234 | Checking | 20151123… |
| 01236 | Mules | `null` | `null` | `null` | `null` |

→ Beaucoup de **`NULL`** pour `Mules`, et des informations **répétées** (Johnson Michael deux fois).

### 10.2 Vue logique HBase (« enregistrements »)

```text
Row key   Valeur (CF, Colonne, Version, Cellule)
01234     info: { 'lastName': 'Smith', 'firstName': 'John' }
          acct: { 'checking': 'abcd1234' }
01235     info: { 'lastName': 'Johnson', 'firstName': 'Michael' }
          acct: { 'checking': 'wxyz1234' @ts=2013,
                  'checking': 'aabb1234' @ts=2012 }
01236     info: { 'lastName': 'Mules' }
```

> ✅ **Bon pour les données clairsemées** : les colonnes inexistantes sont **simplement ignorées**. **Il n'y a pas de `NULL`.**
> Les deux comptes de Johnson sont deux **versions** de la colonne `acct:checking`.

### 10.3 Vue physique (« cellules »)

**Famille `info`**

| Row Key | Column Key | Timestamp | Cell Value |
|---|---|---|---|
| 01234 | info:fname | 1330843130 | John |
| 01234 | info:lname | 1330843130 | Smith |
| 01235 | info:fname | 1330843345 | Michael |
| 01235 | info:lname | 1330843345 | Johnson |
| 01236 | info:lname | *(aucun)* | Mules |

**Famille `acct`**

| Row Key | Column Key | Timestamp | Cell Value |
|---|---|---|---|
| 01234 | acct:checking | 1330843130 | abcd1234 |
| 01235 | acct:checking | 1330843345 | wxyz1234 |
| 01235 | acct:checking | 1330843239 | aabb1234 |

**Structure d'une paire clé/valeur :**

```
┌────────────────────────── Key ──────────────────────────┐
│  Row  │  Column Family  │  Column Qualifier │ Timestamp │  │  Value  │
```

> 💡 Chaque famille de colonnes est stockée **dans ses propres fichiers** ; on voit que `Mules` n'a **aucune ligne** dans `acct` : c'est la nature « clairsemée ».

---

## 11. Plus de détails sur le modèle de données

- **Pas de schéma** au sens SGBDR, **sauf qu'il faut déclarer les familles de colonnes**, car elles déterminent l'**organisation physique sur disque**.
  - Donc **chaque ligne peut avoir un ensemble de colonnes différent**.
- HBase est décrit comme un **magasin de paires clé-valeur**.
- **Chaque paire clé-valeur est versionnée** :
  - la version peut être un **horodatage** ou un **entier** ;
  - **mettre à jour une colonne = ajouter une nouvelle version**.
- **Toutes les données sont des tableaux d'octets** (`byte[]`), y compris :
  - le **nom de la table**,
  - les **noms de familles de colonnes**,
  - les **noms de colonnes** (appelés aussi **qualificatifs de colonne**).

| Conséquence | Explication |
|---|---|
| Pas de types SQL (`INT`, `DATE`…) | C'est à **l'application** de coder/décoder les octets |
| Mise à jour = nouvelle version | On **n'écrase pas** ; les anciennes versions peuvent être conservées (selon TTL / nombre de versions) |

---

## 12. Les index dans HBase

- **Tous les accès à la table passent par la clé de ligne** (sa clé primaire).
- Les lignes sont stockées dans l'**ordre lexicographique des octets**.
- Au sein d'une ligne, les **colonnes sont triées** → **scan rapide, peu coûteux et simple** des lignes et colonnes adjacentes.
  - Les **recherches par clé partielle** (préfixe) sont aussi possibles.
- HBase **ne gère pas les index de façon native**.
  - À la place, on peut **créer une table** qui joue le même rôle (table d'index).
- HBase prend en charge les **filtres de Bloom** (*Bloom filters*) :
  - servent à **décider rapidement** si une combinaison ligne/colonne **existe** dans le fichier de stockage ;
  - **réduisent les E/S** et le **temps d'accès**.
- Les **index secondaires ne sont pas pris en charge**.

> 🔑 **Conséquence pratique :** **la conception de la clé de ligne est LA décision la plus importante** en HBase, puisque c'est le seul moyen d'accès efficace.

---

## 13. Architecture d'un cluster HBase

Schéma du cours (de haut en bas) :

```text
                         ┌─────────────────────────────┐
                         │      ZooKeeper Quorum       │   ← coordination / surveillance
                         │ (ZooKeeper peers …)         │
                         └───────┬───────────┬─────────┘
          ① Le client trouve     │           │
          les adresses des       │           │
  ┌────────┐ serveurs de région  │       ┌───▼────────┐
  │ Client │◄────────────────────┘       │   Master   │   ← assigne les régions,
  └───┬────┘                             │   (HBase)  │     équilibrage de charge
      │ ② Le client lit/écrit           └────────────┘
      │ directement via le
      │ serveur de région
      ▼
 ┌─────────────────────┐   ┌─────────────────────┐
 │    Region Server    │   │    Region Server    │
 │ Coprocessors  WAL   │   │ Coprocessors  WAL   │
 │ Region  Region  …   │   │ Region  Region  …   │
 └─────────┬───────────┘   └─────────┬───────────┘
           │ HFile  HFile …          │ HFile  HFile …
 ┌─────────▼─────────────────────────▼───────────┐
 │               HDFS / GPFS                      │
 └────────────────────────────────────────────────┘
```

**Séquence d'un accès :**
1. Le **client** demande à **ZooKeeper** où se trouvent les **serveurs de région**.
2. Le client **lit et écrit les lignes directement** auprès du **Region Server** (le Master n'intervient pas).
3. Les Region Servers écrivent dans leur **WAL** (journal d'écriture) puis dans des **HFile** stockés sur **HDFS**.

---

## 14. Composants et rôles

| Composant | Rôle |
|---|---|
| **Region** | **Sous-ensemble de lignes** d'une table ; **éclatée automatiquement** quand elle devient trop grande |
| **Region Server** | **Héberge les tables**, effectue les **lectures** et gère les **tampons d'écriture**. Les **clients lui parlent directement** pour lire/écrire |
| **Master** | **Coordonne** les serveurs de région : détecte l'état des régions, fait l'**équilibrage de charge**, **attribue les régions** aux Region Servers, gère les **divisions de région**. **Ne fait pas partie du chemin de lecture/écriture**. **Hautement disponible** grâce à ZooKeeper et à un/des serveur(s) de **secours** |
| **ZooKeeper** | Produit **Apache** de l'écosystème Hadoop ; **composant critique** pour HBase. **Garantit qu'un Master est en cours d'exécution**, **enregistre les Region Servers**, **gère les pannes** de régions et de Master. Pièce maîtresse de la **tolérance aux pannes** |

> 💡 *(Ajout)* **WAL** = *Write-Ahead Log* : chaque écriture est d'abord journalisée pour pouvoir être rejouée en cas de panne d'un Region Server (c'est ce qui assure la durabilité). Les **coprocesseurs** sont des extensions de code exécutées côté Region Server.

---

## 15. Comment les tables sont-elles stockées ? (auto-sharding)

- Les données sont **« shardées »** (partitionnées) automatiquement.
- **Chaque shard (région) contient toutes les données d'une plage de clés** (*key-range*).

**Exemple du cours (clés de A à Z, 3 Region Servers) :**

| Region Server 1 | Region Server 2 | Region Server 3 |
|---|---|---|
| Keys **[A–D]** | Keys **[S–V]** | Keys **[I–M]** |
| Keys **[E–H]** | Keys **[N–R]** | Keys **[W–Z]** |

→ La table (vue logique : lignes A à Z triées) est découpée en **6 régions**, réparties sur **3 serveurs**. Quand une région devient trop grosse, elle est **automatiquement divisée** (*split*) et le Master la **réaffecte** si besoin.

| Avantage | Explication |
|---|---|
| **Scalabilité linéaire** | Ajouter un serveur = plus de régions réparties |
| **Pas d'intervention manuelle** | Le sharding est **automatique** (contrairement au sharding d'un SGBDR) |
| **Recherche rapide** | Le client sait grâce à la clé **quelle région/serveur** contacter |

---

## 16. Quand NE PAS utiliser HBase ?

HBase **n'est pas adapté à tous les problèmes**. Se poser 3 questions :

| # | Question | Réponse |
|---|---|---|
| 1 | **Avez-vous assez de données** pour justifier HBase ? | **Centaines de millions ou milliards de lignes** → HBase. **Quelques milliers / millions** de lignes → **SGBDR traditionnel** |
| 2 | **Avez-vous besoin des fonctionnalités d'un SGBDR ?** | Colonnes **typées**, **index secondaires**, **transactions**, langages de requête avancés… Les applications construites sur un SGBDR **ne peuvent pas être « portées » vers HBase** en changeant simplement de pilote |
| 3 | **Avez-vous assez de matériel ?** | HBase **ne fonctionne pas bien avec moins de 5 DataNodes** (réplication de blocs HDFS par défaut = **3**, plus un **NameNode**) |

> HBase peut tourner sur un ordinateur portable… « mais pourquoi voudriez-vous ? » 😉

---

## 17. Quand utiliser HBase ?

✅ **Utiliser HBase quand :**
- vous avez de **grandes quantités de données** (*pensez pétaoctets*) ;
- vous avez besoin d'un **accès aléatoire efficace** à l'intérieur de grands jeux de données ;
- vous devez **évoluer efficacement** ;
- le **modèle d'accès est simple et relativement fixe** ;
- le **modèle de données / schéma est clairsemé** (plutôt que fixe) ;
- les **propriétés ACID ne sont pas requises** sur plusieurs lignes ou plusieurs tables.

⚠️ **Rappel :** *Hadoop (HDFS/MapReduce) est conçu pour stocker et traiter par lots des jeux de données énormes* :
- **non destiné aux requêtes en temps réel** ;
- **ne prend pas en charge l'accès aléatoire** ;
- **ne gère pas bien des milliards de petits fichiers**.

→ **HBase ajoute justement l'accès aléatoire en temps réel** par-dessus HDFS.

⚠️ HBase fournit une **atomicité stricte au niveau de la ligne** — **aucune autre garantie transactionnelle** sur plusieurs lignes ou tables.

### Tableau de décision rapide

| Situation | Choix |
|---|---|
| Milliards de lignes, accès par clé, données clairsemées | **HBase** |
| Quelques milliers/millions de lignes, jointures, SQL, transactions | **SGBD relationnel** |
| Analyse par lots sur de gros fichiers | **HDFS + MapReduce / Hive / Pig** |
| Besoin d'index secondaires, de colonnes typées | **SGBD relationnel** |

---

## 18. Commandes de base du shell HBase (ajout)

> ⚠️ **Section ajoutée** : les diapositives ne contiennent pas de commandes. Voici les opérations de base du **shell HBase** (`hbase shell`) qui illustrent le modèle de données du cours. *(Non exécutées ici — à tester dans votre environnement.)*

```ruby
# 1. Créer la table : on ne déclare QUE les familles de colonnes
create 'clients', 'info', 'acct'

# 2. Insérer des cellules : put 'table', 'rowkey', 'famille:qualificatif', 'valeur'
put 'clients', '01234', 'info:lastName',  'Smith'
put 'clients', '01234', 'info:firstName', 'John'
put 'clients', '01234', 'acct:checking',  'abcd1234'
put 'clients', '01236', 'info:lastName',  'Mules'     # pas d'autres colonnes : pas de NULL

# 3. Lire une ligne
get 'clients', '01234'

# 4. Parcourir la table (scan trié par clé de ligne)
scan 'clients'
scan 'clients', {STARTROW => '01234', STOPROW => '01236'}

# 5. Mise à jour = nouvelle version
put 'clients', '01234', 'acct:checking', 'zzzz9999'
get 'clients', '01234', {COLUMN => 'acct:checking', VERSIONS => 3}

# 6. Supprimer
delete 'clients', '01234', 'acct:checking'
```

| Commande | Correspondance avec le cours |
|---|---|
| `create 'clients', 'info', 'acct'` | Le schéma ne définit que les **familles de colonnes** |
| `put` | Ajoute une **cellule** `(row, famille:qualificatif, version) → valeur` |
| `get` / `scan` | Les **seuls** modes d'accès natifs (**get / put / scan**) |
| `VERSIONS => 3` | Lecture de **plusieurs versions** horodatées |

---

## 19. Remarques sur le support de cours

1. **Doublon de diapositive** : la diapositive « *What is HBase ?* » (anglais) répète la diapositive « *HBase* » (français) — même contenu.
2. **« Filtres de floraison »** : c'est une mauvaise traduction de **« Bloom filters »** → **filtres de Bloom** (structure probabiliste, nom de son inventeur Burton Bloom).
3. **Exemple de table relationnelle** : le SSN est annoncé comme **clé primaire**, mais `01235` apparaît **deux fois** — ce qui violerait une clé primaire. L'exemple sert surtout à montrer la **répétition** et les **NULL** ; en vrai, la clé serait composée (SSN + n° de compte).
4. **Vue physique** : l'horodatage `1330843239` (aabb1234) est **plus ancien** que `1330843345` (wxyz1234) : la version la plus **récente** est donc `wxyz1234`.
5. **« Hadoop ne prend pas en charge l'accès aléatoire »** : cela concerne **HDFS/MapReduce**, pas HBase — qui est précisément construit pour offrir de l'accès aléatoire par-dessus HDFS.
6. **« Orienté colonnes »** : au sens de BigTable (familles de colonnes), **pas** au sens des bases analytiques en colonnes (type Parquet/ClickHouse). C'est pourquoi le cours insiste sur « Think Map ».
7. **Nombre minimum de nœuds** : « moins de 5 DataNodes » est une **recommandation**, pas une limite technique.
8. **Cohérence des scans** : un scan **n'offre pas d'isolation de cliché** — deux lignes lues par un même scan peuvent correspondre à des instants différents.

---

## 20. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **HBase** | Implémentation open source de **BigTable (Google)** sur **HDFS** ; NoSQL **orienté colonnes** |
| **BigTable** | Carte **triée, multidimensionnelle, clairsemée, répartie, persistante** |
| **Clé d'une cellule** | **(row key, column family:qualifier, timestamp)** → valeur |
| **Schéma** | On ne déclare **que les familles de colonnes** |
| **Colonnes** | Existent **seulement si insérées** ; **pas de NULL** ; **différentes d'une ligne à l'autre** |
| **Versions** | Chaque cellule est **versionnée** (timestamp) ; **mise à jour = nouvelle version** |
| **Types de données** | **Tout est `byte[]`** (tables, familles, qualificatifs, valeurs) |
| **Famille de colonnes** | **Unité de stockage** et de **contrôle d'accès** ; compression (Gzip, LZO, Snappy), TTL, versions |
| **Tri** | Lignes triées **lexicographiquement** par clé de ligne ; colonnes triées dans la ligne |
| **ACID** | **Atomicité au niveau de la ligne seulement** ; scans sans isolation de cliché ; durabilité garantie |
| **Index** | **Clé de ligne uniquement** ; pas d'index secondaires ; **filtres de Bloom** |
| **Accès** | **get / put / scan** (pas de SQL natif ; SQL via Hive) |
| **Performance** | Millions de requêtes/s ; capacité **pétaoctets** |
| **Region** | Sous-ensemble de lignes (plage de clés) ; **éclatée automatiquement** |
| **Region Server** | Héberge les régions ; **lit/écrit** directement avec les clients |
| **Master** | Attribue les régions, équilibre la charge ; **hors du chemin lecture/écriture** |
| **ZooKeeper** | Coordination ; garantit **un Master actif** ; enregistre les serveurs ; gère les pannes |
| **HFile / WAL** | Fichiers de données sur HDFS / journal d'écriture |
| **Quand utiliser** | **Milliards de lignes**, accès aléatoire, données clairsemées, pas besoin d'ACID multi-lignes |
| **Quand éviter** | Peu de données, besoin de SQL/jointures/index secondaires/transactions, **moins de 5 DataNodes** |

### Questions d'examen typiques
1. **Définir HBase** et expliquer la phrase « une Bigtable est une carte triée multidimensionnelle, clairsemée, répartie et persistante ».
2. Pourquoi le **NoSQL** ? Quelles limites du modèle relationnel face au Big Data ?
3. Citer les **points forts de HBase** (évolutivité, latence, disponibilité, cohérence, données clairsemées).
4. Expliquer les **propriétés ACID dans HBase**. Que signifie « atomicité au niveau de la ligne » ? Qu'est-ce que l'absence d'isolation de cliché pour un scan ?
5. Décrire le **modèle de données** : table, ligne, famille de colonnes, qualificatif, cellule, version.
6. Pourquoi dit-on que **HBase n'est pas une feuille de calcul** mais une **map** ?
7. **Comparer HBase et un SGBDR** (transactions, requêtes, index, débit, taille).
8. À partir d'une **table relationnelle** donnée, donner sa **vue logique** et sa **vue physique** HBase. Pourquoi n'y a-t-il pas de NULL ?
9. À quoi servent les **familles de colonnes** ? Quels paramètres peut-on configurer par famille ?
10. Comment HBase gère-t-il les **index** ? Que sont les **filtres de Bloom** ?
11. Décrire l'**architecture** d'un cluster HBase et le **rôle** de la Region, du Region Server, du Master et de ZooKeeper.
12. Expliquer l'**auto-sharding** avec un exemple de plages de clés.
13. **Quand utiliser** / **ne pas utiliser** HBase ? Donner les critères (volume, fonctionnalités, matériel).
14. Pourquoi le **Master ne fait-il pas partie du chemin de lecture/écriture** ? Que se passe-t-il s'il tombe en panne ?

---

*Fin du résumé — HBase.*
