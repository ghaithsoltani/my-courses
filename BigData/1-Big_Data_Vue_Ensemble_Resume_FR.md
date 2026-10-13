# Big Data : Vue d'ensemble — Résumé complet du cours
**Chapitre :** Big Data — Vue d'ensemble (fichier « X01 — BIG DATA Overview »)
**Enseignant :** Dr. Maher HENI
**Type de document :** Résumé d'étude avec explications, tableaux comparatifs et conseils d'examen

---

## Table des matières

1. [Plan du chapitre](#1-plan-du-chapitre)
2. [Les grandes tendances qui changent le monde](#2-les-grandes-tendances-qui-changent-le-monde)
3. [Un monde de plus en plus interconnecté et instrumenté](#3-un-monde-de-plus-en-plus-interconnecté-et-instrumenté)
4. [Quelques statistiques : Facebook](#4-quelques-statistiques--facebook)
5. [Propriétés intrinsèques du Big Data](#5-propriétés-intrinsèques-du-big-data)
6. [Les types de données](#6-les-types-de-données)
7. [Les caractéristiques du Big Data : les V](#7-les-caractéristiques-du-big-data--les-v)
8. [Les unités de mesure](#8-les-unités-de-mesure)
9. [La grande différence : schema on load vs schema on run](#9-la-grande-différence--schema-on-load-vs-schema-on-run)
10. [Qui en a besoin ?](#10-qui-en-a-besoin-)
11. [Hadoop : origine et historique](#11-hadoop--origine-et-historique)
12. [Qu'est-ce que Hadoop ?](#12-quest-ce-que-hadoop-)
13. [Les améliorations matérielles : pourquoi Hadoop ?](#13-les-améliorations-matérielles--pourquoi-hadoop-)
14. [Qui utilise déjà Hadoop ?](#14-qui-utilise-déjà-hadoop-)
15. [Penser différemment](#15-penser-différemment)
16. [L'écosystème Hadoop](#16-lécosystème-hadoop)
17. [Les composants de base de Hadoop (rappel — ajout)](#17-les-composants-de-base-de-hadoop-rappel--ajout)
18. [Remarques sur le support de cours](#18-remarques-sur-le-support-de-cours)
19. [Fiche de révision rapide](#19-fiche-de-révision-rapide)

---

## 1. Plan du chapitre

Le support annonce le plan suivant :

1. **C'est quoi le Big Data ?**
2. **Hadoop : pourquoi, quand et comment ?**
   - Hadoop vs RDBMS
   - Hadoop : architecture — **HDFS**, **MapReduce**, **YARN**, **Hadoop Common**
   - Écosystème Hadoop — **HBase, Flume, Mahout, Spark, Hive, Sqoop**…
3. **Cas d'utilisation**

> ⚠️ Le PDF détaille surtout les parties 1 et 2 (définition du Big Data, les V, motivation de Hadoop, écosystème). L'architecture détaillée (HDFS, MapReduce, YARN), la comparaison complète Hadoop/SGBDR et les cas d'utilisation sont **annoncés au plan** mais traités dans d'autres chapitres. Un rappel en est donné en [section 17](#17-les-composants-de-base-de-hadoop-rappel--ajout).

---

## 2. Les grandes tendances qui changent le monde

Le **Big Data** se situe au **centre** de six grandes tendances qui se recoupent :

| Tendance | Idée |
|---|---|
| **Web 4.0** | Web intelligent et omniprésent |
| **IoT** (*Internet of Things*) | Objets connectés générant des données |
| **Cloud Computing** | Ressources informatiques à la demande |
| **Connectivité** | Réseaux omniprésents |
| **Mobilité** | Smartphones, tablettes… |
| **Capacité & bande passante (BP)** | Stockage et réseaux de plus en plus puissants |

```
              Web 4.0
        IoT     ▲     Cloud Computing
           ╲    │    ╱
            ╲ BIG DATA ╱
           ╱    │    ╲
 Connectivité   ▼     Mobilité
           Capacité & BP
```

**À retenir :** le Big Data n'est pas une mode isolée : il est **le produit et le moteur** de toutes ces tendances.

---

## 3. Un monde de plus en plus interconnecté et instrumenté

Chiffres cités par le cours (source : *statisticbrain.com*) :

| Source de données | Chiffre |
|---|---|
| **Twitter** | **500+ millions d'utilisateurs**, qui postent **55 millions de tweets par jour = 12 To** |
| **Google** | **1,2 trillion de recherches** (sans compter les autres services SaaS) |
| **Facebook** | **1,7+ milliard d'utilisateurs**, qui passent plus de **700 min** de connexion ; **25 To/jour** |
| **Étiquettes RFID** | **30 milliards** (contre **1,3 milliard en 2005**) |
| **Caméras** | **5,6 milliards** |
| **GPS actifs** | **Des centaines de millions** |
| **Compteurs intelligents** (*smart meters*) | **300 millions en 2017** |
| **Personnes sur le web** | **3+ milliards** |

> 💡 Ces chiffres datent de l'époque du support (années 2010) ; ils ont beaucoup augmenté depuis, mais l'**ordre de grandeur** et le message restent les mêmes : les données sont produites **partout et en continu**.

*Le support présente aussi une infographie « **Les données sont le nouvel or noir** » (analogie avec le pétrole : les données brutes n'ont de valeur qu'une fois raffinées).*

---

## 4. Quelques statistiques : Facebook

| Statistique | Valeur |
|---|---|
| **Comptes** | **955 millions** dans **70 langues** (≈ **10 %** de la population mondiale connectée) |
| **Photos** | **140 milliards** de photos importées ; **300 millions par jour** |
| **Volume** | La collection est **10 000 fois plus grande** que celle de la **Bibliothèque du Congrès** |
| **Publications** | **30 milliards** d'informations publiées chaque jour |
| **Interactions** | **2,7 milliards** de « J'aime » et commentaires par jour |
| **Liens** | **125 milliards** de connexions d'amis ; en moyenne **234 amis** par utilisateur |

### Question de réflexion posée par le cours
> **« Comment créer un profil Business de chaque personne ? »**

→ Avec autant de données comportementales, les entreprises peuvent bâtir un **profil détaillé** de chaque utilisateur (centres d'intérêt, habitudes d'achat…) — **opportunité commerciale**, mais aussi **enjeu de vie privée**.

---

## 5. Propriétés intrinsèques du Big Data

| Indicateur | Signification |
|---|---|
| **90 %** | des données ont été créées **ces cinq dernières années** |
| **80 %** | des données sont **non structurées** |
| **20 %** | seulement des données valables peuvent être traitées avec les **SGBDR classiques** |
| **1 sur 2** | entreprises n'ont **pas accès aux données** qu'elles pourraient utiliser |
| **83 %** | des **PDG** croient que la **BI et l'analytics** peuvent augmenter leur chiffre d'affaires |
| **5,4 ×** | Les entreprises **les plus performantes** utilisent **5,4 fois plus** l'analyse de données commerciales |

*Sources : GigaOM, IBM Software Group, IBM Institute for Business Value.*

**Conséquence :** les SGBDR classiques ne gèrent qu'une **petite partie** des données utiles → besoin de **nouveaux outils** (Hadoop, Spark, NoSQL).

---

## 6. Les types de données

| Type | Définition | Exemples |
|---|---|---|
| **Structuré** | Données qui peuvent être stockées et traitées dans un **format fixe** (un **schéma**) | Tables de SGBD relationnel |
| **Semi-structuré** | **Pas de structure formelle** de modèle de données (pas de définition de table SGBD), mais des **propriétés organisationnelles** (**balises**, marqueurs) séparant les éléments sémantiques et **facilitant l'analyse** | **XML**, **JSON** |
| **Non structuré** | Données de **forme inconnue**, **non stockables dans un SGBDR** et analysables seulement après **transformation en format structuré** | **Fichiers texte**, **images**, **audio**, **vidéos** |

> 🔑 Les données **non structurées croissent plus vite** que les autres : selon les experts, **80 % des données d'une organisation** sont non structurées.

---

## 7. Les caractéristiques du Big Data : les V

### 7.1 Les 4 V (modèle de base)

> **V⁴ = Volume · Velocity (vélocité) · Variety (variété) · Veracity (véracité)**

| V | Défi | Chiffre du cours |
|---|---|---|
| **Volume** | **Traitement rentable de la croissance** des données | Croissance **×50** entre **2010 et 2020**, jusqu'à **35 Zo** (zettaoctets) |
| **Velocity** | **Répondre à la vitesse** de création/arrivée des données | **30 milliards** de capteurs **RFID** |
| **Variety** | **Mélanger et tirer parti** de la variété des sources | **80 %** des données mondiales sont **non structurées** |
| **Veracity** | **Étudier la fiabilité** des sources de données | **1 PDG sur 3** ne fait **pas confiance** aux données qu'il utilise |

### 7.2 Les 7 V (« pour réussir en Big Data »)

La pyramide du cours, de la base au sommet :

```
                ┌───────┐
                │ Value │            ← sommet : le but
              ┌─┴───────┴─┐
              │Visualisation│
         ┌────┴────┬───────┴──┬─────────┐
         │Variability│ Variety │ Velocity │ Veracity
         └────┬────┴──────────┴─────────┘
         ┌────┴──────────────────────────┐
         │            VOLUME             │   ← base
         └───────────────────────────────┘
```

### 7.3 Les 10 V (« Encore plus »)

| V | Question associée |
|---|---|
| **Volume** | **Combien** de données y a-t-il ? |
| **Velocity** | **À quelle vitesse** les données sont-elles créées, déplacées ou accessibles ? |
| **Variété** | **Combien de types** de sources différentes existe-t-il ? |
| **Véracité** | Peut-on **faire confiance** aux données ? |
| **Validité** | Les données sont-elles **exactes et correctes** ? |
| **Viabilité** | Les données sont-elles **pertinentes** pour le cas d'usage en question ? |
| **Volatilité** | **À quelle fréquence** les données changent-elles ? |
| **Vulnérabilité** | Peut-on **sécuriser** les données ? |
| **Visualisation** | **Comment présenter** les données à l'utilisateur ? |
| **Valeur** | Ces données peuvent-elles produire un **retour sur investissement** significatif ? |

> 💡 **Astuce mnémotechnique :** les 3 V historiques (**Volume, Vélocité, Variété**) → 4 V (+ **Véracité**) → 7 V (+ **Variabilité, Visualisation, Valeur**) → 10 V (+ Validité, Viabilité, Volatilité, Vulnérabilité). Le **Volume** reste la base et la **Valeur** le but final.

---

## 8. Les unités de mesure

Deux systèmes d'unités coexistent :

| Unité (SI) | Symbole | Puissance de **10** | Usage binaire (déconseillé) | Unité IEC (1999) | Symbole | Puissance de **2** |
|---|---|---|---|---|---|---|
| kilooctet | kB | 10³ | 2¹⁰ | **kibioctet** | KiB | 2¹⁰ |
| mégaoctet | MB | 10⁶ | 2²⁰ | **mébioctet** | MiB | 2²⁰ |
| gigaoctet | GB | 10⁹ | 2³⁰ | **gibioctet** | GiB | 2³⁰ |
| téraoctet | TB | 10¹² | 2⁴⁰ | **tébioctet** | TiB | 2⁴⁰ |
| pétaoctet | PB | 10¹⁵ | 2⁵⁰ | **pébioctet** | PiB | 2⁵⁰ |
| exaoctet | EB | 10¹⁸ | 2⁶⁰ | **exbioctet** | EiB | 2⁶⁰ |
| zettaoctet | ZB | 10²¹ | 2⁷⁰ | **zébioctet** | ZiB | 2⁷⁰ |
| yottaoctet | YB | 10²⁴ | 2⁸⁰ | **yobioctet** | YiB | 2⁸⁰ |

**À retenir :** le **SI** (kilo, méga, giga…) est en **puissances de 10** ; la norme **IEC 1999** (kibi, mébi, gibi…) est en **puissances de 2**. L'ancien usage « 1 kB = 1024 octets » est **déconseillé**.

> 🔑 Les **35 Zo** de 2020 évoqués plus haut = 35 × 10²¹ octets.

---

## 9. La grande différence : schema on load vs schema on run

| | **Base de données régulière** | **Données brutes (Hadoop)** |
|---|---|---|
| **Image du cours** | Des **soldats en rang** (tout est ordonné d'avance) | Une **foule** (données telles quelles) |
| **Principe** | **Schema on load** (schéma appliqué **au chargement**) | **Schema on run** (appelé aussi *schema on read* : schéma appliqué **à la lecture**) |
| **Flux** | Données brutes → **schéma pour filtrer** → **stockage de données pré-filtrées** | Données brutes → **stockage tel quel (non filtré)** → **schéma pour filtrer au moment de la requête** → sortie |
| **Conséquence** | On **perd** le détail écarté au chargement ; il faut **connaître l'usage à l'avance** | On **garde toutes les données brutes** ; on peut définir **plusieurs schémas** selon les besoins |
| **Chargement** | **Lent** (transformation avant stockage) | **Rapide** (on stocke directement) |

> 💡 C'est exactement ce qui explique le passage de **ETL à ELT** (voir [section 15](#15-penser-différemment)) : on **charge d'abord**, on **transforme ensuite**.

---

## 10. Qui en a besoin ?

> **« Les SGBDR classiques ne répondent pas aux exigences d'aujourd'hui : pensez grands. »**

**Analogie illustrée du cours :**

| Image | Technologie | Lecture de l'analogie |
|---|---|---|
| 🏎️ **Voiture de sport** | **Base de données d'entreprise** | Très **rapide** sur de petits volumes, mais **capacité limitée** |
| 🚂 **Train de marchandises** | **Hadoop** | Transporte des **quantités énormes**, de façon fiable |
| 🚄 **Train à grande vitesse** | **Spark** | **Grand volume ET grande vitesse** (traitement en mémoire) |

*(L'interprétation de l'analogie est de moi, à partir des images du support.)*

> Citation du cours : **« Nous pensons que plus de la moitié des données mondiales seront stockées dans Apache Hadoop d'ici cinq ans. »**

---

## 11. Hadoop : origine et historique

- **Apache Hadoop** est **inspiré** de **MapReduce** et du **Google File System (GFS)**.
- **Architecture open source, flexible et disponible** pour le **calcul et le traitement de données à grande échelle** sur un réseau de **matériel de base** (*commodity hardware*).

### Chronologie (schéma du cours)

| Repère | Événement |
|---|---|
| **Google** | Développe le **Google File System** et **MapReduce** |
| **Google** | Publie l'**article sur MapReduce** |
| **Doug Cutting** (employé Yahoo! à l'époque) | Développeur de **Nutch** (robot d'exploration du web) : commence à utiliser MapReduce pour l'**indexation** |
| **Cutting / Yahoo!** | **Renomment leur projet « Hadoop »** (≈ 2006) |
| **Yahoo!** | **Rend Hadoop open source** (≈ 2008) |
| **Distributeurs** | **Cloudera**, puis **IBM BigInsights** et **Hortonworks** (jusqu'à ≈ 2011) |

> 💡 *(Précision historique ajoutée)* Le nom « Hadoop » vient du **jouet d'éléphant** du fils de Doug Cutting (d'où le logo). Les articles Google datent de **2003 (GFS)** et **2004 (MapReduce)** ; Hadoop devient projet Apache de premier niveau en **2008**. Les dates exactes sur le schéma du support sont approximatives.

---

## 12. Qu'est-ce que Hadoop ?

### Définition
> **Framework logiciel open source Apache** pour un calcul **fiable, évolutif et distribué** d'une **grande quantité de données**.

- **Masque** les détails du système sous-jacent et la **complexité** pour l'utilisateur.
- **Développé en Java.**
- Se compose de **sous-projets** : **MapReduce + HDFS + YARN + Hadoop Common**.

### Une nouvelle façon de stocker et traiter les données

On laisse **le système gérer automatiquement** la plupart des problèmes :
- les **pannes (échecs)** ;
- l'**évolutivité** ;
- la **réduction des communications** ;
- **distribuer les données et la puissance de calcul là où se trouvent les données** (*data locality*) ;
- faire du **parallélisme** une partie du système d'exploitation ;
- utiliser du **matériel relativement peu coûteux** (de 2 $ à 4 K$ — selon le support).

### Optimisé pour gérer

- Des **quantités massives de données** grâce au **parallélisme**.
- Une **variété de données** : structurées, non structurées, semi-structurées.
- L'utilisation de **matériel de base bon marché**.

> 🔑 **Fiabilité fournie par la réplication** (les blocs de données sont copiés sur plusieurs machines).

---

## 13. Les améliorations matérielles : pourquoi Hadoop ?

| Composant | **1990** | **2010** | Évolution |
|---|---|---|---|
| **CPU** | **44 MIPS** à 40 MHz | **147 600 MIPS** à 3,3 GHz | ≈ **×3 350** |
| **Mémoire RAM** | **640 Ko** (mémoire conventionnelle ; 256 Ko de mémoire étendue recommandés) | **8–32 Go** (et plus) | ≈ **×12 500 à ×50 000** |
| **Capacité des disques** | **20 Mo** | **1 To** | ≈ **×50 000** |
| **Vitesse des disques** (lecture/écriture) | — | **Pas d'évolution** depuis 7–10 ans : environ **80 Mo/s** | ≈ **×1** |

### Le raisonnement

- CPU, RAM et capacité de stockage ont **explosé**…
- …mais la **vitesse de lecture/écriture des disques stagne** (~80 Mo/s).
- **Conséquence :** lire **1 To sur un seul disque** prend **des heures** (≈ 3 h 30 à 80 Mo/s).
- **Solution de Hadoop :** **répartir les données sur de nombreux disques/machines** et les lire **en parallèle** — 1 000 disques lisant en parallèle traitent 1 To en **quelques secondes**.

> *(Le calcul est un ajout pour illustrer l'idée ; le support parle de « latence » alors qu'il s'agit en fait du **débit** de lecture/écriture.)*

---

## 14. Qui utilise déjà Hadoop ?

Le support présente une mosaïque de logos de grandes organisations, notamment :

| Catégorie | Exemples (cités par les logos) |
|---|---|
| **Web / réseaux sociaux** | **Facebook**, **Twitter**, **LinkedIn**, **Google**, **Yahoo!**, **AOL**, **Hulu**, **eBay**, **Alibaba Group** |
| **Technologie / matériel** | **IBM**, **Adobe**, **NetApp**, **Samsung**, **Dell**, **Nokia**, **Rackspace** |
| **Médias / voyage** | **The New York Times**, **Expedia**, **Orbitz**, **Groupon** |
| **Universités / institutions** | **Cornell University**, **University of Maryland**, **University of Glasgow**, **Social Security Administration** |
| **Autres** | **Monsanto**, **Box**, **Experian**, **Chevron**… |

**À retenir :** Hadoop est utilisé **dans tous les secteurs** (web, finance, médias, industrie, recherche, administration).

---

## 15. Penser différemment

Pour travailler avec Hadoop, il faut **penser différemment** :

1. **Paradigmes de traitement différents** (traitement distribué, MapReduce).
2. **Approches de stockage différentes** (HDFS, NoSQL, schema on read).
3. Penser **ELT** (*Extract – Load – Transform*) **plutôt qu'ETL** (*Extract – Transform – Load*).

| | **ETL** (approche classique) | **ELT** (approche Hadoop) |
|---|---|---|
| **Ordre** | Extraire → **Transformer** → Charger | Extraire → **Charger** → Transformer |
| **Où se fait la transformation ?** | Sur un serveur ETL **avant** l'entrepôt | **Dans le cluster**, après le chargement des données brutes |
| **Données brutes conservées ?** | Non (seul le résultat est chargé) | **Oui** |

> 🔑 Comprendre l'écosystème Hadoop, c'est **s'engager dans un processus d'apprentissage continu**.

---

## 16. L'écosystème Hadoop

### 16.1 Principe

- **Hadoop est devenu le noyau du système d'exploitation distribué pour le Big Data.**
- **Personne n'utilise le noyau seul.**
- C'est **une collection de projets Apache** (*Hadoop Open Source Projects*) : **Jaql, Avro, Flume, Spark, Hue, HBase, Mahout, Hive, Pig, Oozie, Impala**…

### 16.2 Schéma de l'écosystème (vue d'ensemble)

| Élément | Contenu |
|---|---|
| **Au centre** | **Hadoop** |
| **Composants de base (*core components*)** | **MapReduce** et **HDFS** (Hadoop Distributed File System) |
| **Composants de l'écosystème** | **ZooKeeper, Sqoop, Flume, Oozie, Pig, Chukwa, Mahout, Hive, Ambari, HBase, HCatalog, connecteurs R** |
| **Distributions** | **Apache, Cloudera, Hortonworks, IBM, Pivotal, Oracle, AWS EMR, Intel**… |
| **Couches d'architecture** | Hadoop Storage Layer · Data Sources / Industry Data Layer · Analytics Engine · Data Ingestion Layer · Visualization Layer · Hadoop Platform Management Layer · **HPL** (couche d'infrastructure physique) |
| **NoSQL** | Inclus dans l'écosystème (ex. HBase) |

### 16.3 Une plateforme Big Data : l'exemple de Cloudera

Architecture en couches, **de bas en haut** :

| Couche | Composants | Rôle |
|---|---|---|
| **Stockage** | **HDFS** | Système de fichiers distribué |
| **Traitement** | **MapReduce Runtime** (framework de programmation distribuée) · **Spark** | Exécution des calculs distribués |
| **Langages d'analyse** | **Pig / Hive** | Langages de haut niveau (flux de données / SQL) |
| **Base NoSQL** | **HBase** | Base orientée colonnes |
| **Agrégation de données** | **Sqoop / Flume** | Importer des données (bases relationnelles / flux de logs) |
| **Workflow** | **Oozie** | Enchaînement et planification de jobs |
| **Coordination** | **ZooKeeper** | Coordination des services distribués |
| **Data mining** | **Mahout** | Algorithmes de machine learning |
| **Interface** | **Hue** | Console web |

### 16.4 Rôle de chaque outil (récapitulatif)

| Outil | À quoi sert-il ? |
|---|---|
| **HDFS** | **Stocker** de gros fichiers de façon répartie et répliquée |
| **MapReduce** | **Calculer** en parallèle (Map puis Reduce) |
| **YARN** | **Gérer les ressources** du cluster |
| **Pig** | Langage de **flux de données** (ETL) |
| **Hive** | **SQL** sur Hadoop (HiveQL) |
| **HBase** | Base **NoSQL** orientée colonnes, accès aléatoire |
| **Sqoop** | **Importer/exporter** entre Hadoop et **bases relationnelles** |
| **Flume** | **Collecter** des flux de données (ex. **logs**) vers HDFS |
| **Oozie** | **Orchestrer** et **planifier** des workflows de jobs |
| **ZooKeeper** | **Coordination** et configuration distribuée |
| **Mahout** | **Machine learning / data mining** |
| **Spark** | **Traitement rapide en mémoire** (batch, streaming, ML) |
| **Hue** | **Interface web** pour utiliser le cluster |
| **Impala** | **SQL interactif** à faible latence sur Hadoop |
| **Avro** | **Sérialisation** de données |

> 💡 *(Ajout)* La colonne « À quoi sert-il ? » développe les noms cités par le support ; le PDF ne les détaille pas tous.

---

## 17. Les composants de base de Hadoop (rappel — ajout)

> ⚠️ **Section ajoutée** : le plan du support mentionne HDFS, MapReduce, YARN et Hadoop Common, détaillés dans d'autres chapitres. Voici un rappel court pour garder une vue complète.

| Composant | Rôle |
|---|---|
| **HDFS** (*Hadoop Distributed File System*) | **Stockage distribué** : les fichiers sont découpés en **blocs** répliqués (3 copies par défaut) sur plusieurs **DataNodes**, gérés par un **NameNode** |
| **MapReduce** | **Modèle de calcul parallèle** : **Map** (traiter les données en parallèle) puis **Reduce** (agréger) |
| **YARN** (*Yet Another Resource Negotiator*) | **Gestion des ressources** du cluster et ordonnancement des applications (Hadoop v2) |
| **Hadoop Common** | **Bibliothèques et utilitaires communs** utilisés par les autres modules |

### Hadoop vs SGBD relationnel (synthèse à partir des diapositives)

| Critère | **SGBD relationnel** | **Hadoop** |
|---|---|---|
| **Schéma** | **Schema on load** | **Schema on run (read)** |
| **Types de données** | Surtout **structurées** (~20 % des données utiles) | **Structurées, semi-structurées, non structurées** |
| **Volume** | Téraoctets | **Pétaoctets et plus** |
| **Matériel** | Serveurs haut de gamme | **Matériel de base bon marché** |
| **Évolutivité** | Verticale (machine plus puissante) | **Horizontale** (ajouter des machines) |
| **Fiabilité** | Sauvegardes, transactions | **Réplication** des données |
| **Approche** | **ETL** | **ELT** |

---

## 18. Remarques sur le support de cours

Quelques points à connaître pour éviter la confusion :

1. **Faute dans « Penser différemment »** : le support écrit « *ELT plutôt qu'ELT* » ; il faut lire **« ELT plutôt qu'ETL »** (extraction-chargement-transformation plutôt qu'extraction-transformation-chargement). La parenthèse « extraction-transformation-charge » est aussi répétée pour les deux.
2. **« Se compose de 3 sous-projets »** : le support en cite **quatre** (MapReduce, HDFS, YARN, Hadoop Common).
3. **« Disk latency »** : la valeur « 80 Mo/s » est un **débit** (vitesse de transfert), pas une latence.
4. **« Schema on run »** : le terme usuel est **« schema on read »** (schéma à la lecture).
5. **Chiffres datés** : les statistiques (Facebook : 955 millions de comptes ; Twitter : 500 millions d'utilisateurs ; 300 millions de smart meters « en 2017 »…) datent de **2012–2017** ; à citer comme **ordres de grandeur historiques**.
6. **« Jaql / Jaqi »** : le nom correct est **Jaql** (langage de requête JSON d'IBM) ; le logo « Jaqi » de la diapo est une coquille.
7. **« Plus de la moitié des données mondiales dans Hadoop d'ici cinq ans »** : citation **marketing** du support, **non vérifiée** ; aujourd'hui, une grande partie du stockage Big Data est aussi sur le **cloud** (S3, Azure, GCS) et **Spark**.
8. **Plan vs contenu** : le plan annonce *Hadoop vs RDBMS*, *architecture* et *cas d'utilisation* qui ne sont **pas détaillés** dans ce PDF.
9. **Matériel « 2 $ – 4 K »** : fourchette de prix citée par le support ; à comprendre comme **« machines bon marché, de quelques dollars à quelques milliers de dollars »**.

---

## 19. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Big Data** | Volumes **massifs, rapides et variés** de données, dépassant les capacités des SGBDR classiques |
| **Tendances associées** | **IoT, Cloud, Web 4.0, Mobilité, Connectivité, Capacité & BP** |
| **Chiffres clés** | **90 %** des données créées en 5 ans · **80 %** non structurées · **20 %** exploitables par les SGBDR |
| **Types de données** | **Structuré** (schéma fixe) · **Semi-structuré** (XML, JSON) · **Non structuré** (texte, image, audio, vidéo) |
| **4 V** | **Volume, Velocity, Variety, Veracity** |
| **7 V** | + **Variability, Visualisation, Value** |
| **10 V** | + **Validité, Viabilité, Volatilité, Vulnérabilité** |
| **Volume** | ×50 de 2010 à 2020 → **35 Zo** |
| **Unités** | SI (10³ᵏ) vs **IEC** (2¹⁰ᵏ : Ki, Mi, Gi, Ti…) |
| **Schema on load** | Bases classiques : schéma **appliqué au chargement** |
| **Schema on run/read** | Hadoop : **données brutes** stockées, schéma **appliqué à la lecture** |
| **Analogie** | Voiture de sport = SGBD · **Train de marchandises = Hadoop** · **TGV = Spark** |
| **Hadoop** | Framework **open source Apache**, **Java**, calcul **distribué, fiable, évolutif** |
| **Origine** | Inspiré de **Google File System** et **MapReduce** ; Doug Cutting / Yahoo! / **Nutch** |
| **Composants** | **HDFS + MapReduce + YARN + Hadoop Common** |
| **Principes** | Matériel **bon marché**, **réplication**, calcul **là où sont les données**, gestion automatique des **pannes** |
| **Pourquoi Hadoop ?** | CPU/RAM/disques ont explosé, mais le **débit disque stagne (~80 Mo/s)** → **lire en parallèle** |
| **Changer de mentalité** | **ELT plutôt qu'ETL** ; nouveaux paradigmes de traitement et de stockage |
| **Écosystème** | HBase, Hive, Pig, Sqoop, Flume, Oozie, ZooKeeper, Mahout, Spark, Hue, Impala, Avro… |
| **Plateforme** | Exemple : **Cloudera** (HDFS en bas → MapReduce/Spark → Pig/Hive/HBase → Oozie/Hue) |
| **Utilisateurs** | Facebook, Twitter, LinkedIn, Google, Yahoo!, IBM, eBay, NYT… |

### Questions d'examen typiques
1. **Définir le Big Data** et citer les grandes tendances qui lui sont associées.
2. Citer les **trois types de données** (structuré, semi-structuré, non structuré) avec des exemples.
3. Expliquer les **4 V** du Big Data ; citer les V supplémentaires (7 V, 10 V).
4. Pourquoi dit-on que **80 %** des données sont non structurées, et quelle est la conséquence pour les SGBDR ?
5. Convertir et comparer les **unités** (Go, To, Po, Eo, Zo) ; différence **SI / IEC**.
6. Expliquer **schema on load** vs **schema on run (read)**.
7. Pourquoi les **SGBDR classiques** ne suffisent-ils plus ? Expliquer l'analogie **voiture de sport / train / TGV**.
8. **Qu'est-ce que Hadoop ?** Quelle est son **origine** (Google, Nutch, Yahoo!) ?
9. Citer les **composants de base** de Hadoop et les **principes** de conception (matériel bon marché, réplication, localité des données).
10. **Pourquoi Hadoop** : expliquer l'évolution matérielle (CPU, RAM, disque) et le problème du **débit disque**.
11. Différence entre **ETL** et **ELT** ; pourquoi « penser ELT » avec Hadoop ?
12. Citer **cinq outils de l'écosystème Hadoop** et leur rôle.
13. Décrire l'architecture d'une **plateforme Big Data** (ex. **Cloudera**).
14. Donner des **exemples d'organisations** qui utilisent Hadoop.

---

*Fin du résumé — Big Data : Vue d'ensemble.*
