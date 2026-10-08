# Introduction aux applications réparties — Résumé complet du cours
**Cours :** Développement des systèmes répartis
**Enseignant :** Mahran Farhat
**Type de document :** Résumé d'étude avec explications, tableaux comparatifs et conseils d'examen

---

## Table des matières

1. [Objectifs du cours](#1-objectifs-du-cours)
2. [Plan du chapitre](#2-plan-du-chapitre)
3. [Introduction aux systèmes distribués](#3-introduction-aux-systèmes-distribués)
4. [Exemples de systèmes distribués](#4-exemples-de-systèmes-distribués)
5. [Intérêts des systèmes distribués](#5-intérêts-des-systèmes-distribués)
6. [Inconvénients des systèmes distribués](#6-inconvénients-des-systèmes-distribués)
7. [Définition et caractéristiques d'un système distribué](#7-définition-et-caractéristiques-dun-système-distribué)
8. [Propriétés des systèmes distribués](#8-propriétés-des-systèmes-distribués)
9. [Les transparences (norme ISO RM-ODP)](#9-les-transparences-norme-iso-rm-odp)
10. [Quelques difficultés](#10-quelques-difficultés)
11. [Applications réparties](#11-applications-réparties)
12. [Programmation classique vs programmation répartie](#12-programmation-classique-vs-programmation-répartie)
13. [Modèles d'interaction dans un système distribué](#13-modèles-dinteraction-dans-un-système-distribué)
14. [Moyens de communication dans un système distribué](#14-moyens-de-communication-dans-un-système-distribué)
15. [Remarques sur le support de cours](#15-remarques-sur-le-support-de-cours)
16. [Fiche de révision rapide](#16-fiche-de-révision-rapide)

---

## 1. Objectifs du cours

Le cours vise à :

- **Présenter les principes de construction et le fonctionnement** des applications réparties.
- **Apprendre la conception** d'applications réparties :
  - motivations et concepts ;
  - architecture et exemples ;
  - problèmes et solutions.
- **Connaître les technologies majeures** pour le développement d'applications réparties.
- **Comprendre les solutions** techniques :
  - **Sockets Java** et **RPC** ;
  - **Web Services** ;
  - **Objets répartis en Java** (**RMI**, **CORBA**).

> 📌 Le support du cours (cours, TP, projets) est partagé sur Google Drive par l'enseignant.

---

## 2. Plan du chapitre

1. Introduction aux systèmes distribués/répartis
   - Présentation générale
   - Exemples de systèmes distribués
   - Intérêts des systèmes distribués
2. Propriétés des systèmes distribués
3. Applications réparties

---

## 3. Introduction aux systèmes distribués

### 3.1 Système centralisé vs système distribué

| Critère | **Système centralisé** | **Système distribué** |
|---|---|---|
| Localisation | **Tout est sur la même machine**, accessible par le programme | Ensemble d'**ordinateurs indépendants connectés en réseau** |
| Exécution | Système logiciel s'exécutant sur **une seule machine** | Les machines **communiquent via le réseau** |
| Accès aux ressources | Les applications accèdent **localement** aux ressources (données, code, périphériques, mémoire...) | Ressources **réparties** sur plusieurs machines |
| Vue de l'utilisateur | Une machine | L'ensemble apparaît **comme une seule entité** |

### 3.2 Deux visions d'un système distribué

| Vision | Description |
|---|---|
| **Vision matérielle** (architecture matérielle) | • Machine **multi-processeurs à mémoire partagée** <br>• **Cluster** d'ordinateurs dédiés au calcul / traitement massif parallèle <br>• **Ordinateurs standards connectés en réseau** |
| **Vision logicielle** | Système logiciel composé de **plusieurs entités s'exécutant indépendamment et en parallèle** sur un ensemble d'ordinateurs connectés en réseau |

### 3.3 Dans ce cours

- On s'intéresse à la **conception logicielle** des systèmes distribués : le **développement d'applications réparties**.
- Par défaut, sur une architecture matérielle de type **ordinateurs connectés en réseau**.

---

## 4. Exemples de systèmes distribués

- **Serveur FTP**
- **Serveur Web**
- **Calculs scientifiques** (exemple détaillé ci-dessous)

### Exemple détaillé : les calculs scientifiques distribués

**Architectures matérielles généralement utilisées :**
- Ensemble de **machines identiques** reliées par un **réseau dédié et très rapide** (*cluster*).
- Ensemble de **machines hétérogènes** connectées dans un **réseau local** ou par **Internet**.

**Principe général :**
1. Un (ou des) **serveur(s)** distribue(nt) des calculs aux **machines clientes**.
2. Chaque client **exécute son calcul** puis **renvoie le résultat** au serveur.

| Avantage | Inconvénient |
|---|---|
| Utilisation d'un **maximum de ressources de calcul** | Si le **réseau ou le serveur plante**, **arrêt du système** |

---

## 5. Intérêts des systèmes distribués

| Intérêt | Explication | Exemples du cours |
|---|---|---|
| **Utiliser et partager des ressources distantes** | Un même service peut être utilisé par **plusieurs acteurs situés à des endroits différents** | Système de fichiers accessible **depuis n'importe quelle machine** ; **imprimante partagée** entre toutes les machines |
| **Optimiser l'utilisation des ressources disponibles** | Exploiter la puissance de plusieurs machines | Calculs scientifiques distribués sur un ensemble de machines |
| **Système plus robuste** | **Redondance / duplication** pour la fiabilité ; plusieurs éléments identiques pour **résister à la montée en charge** | Deux serveurs de fichiers dupliqués, avec sauvegarde |

---

## 6. Inconvénients des systèmes distribués

- **Problème réseau** : le système **marche mal ou plus du tout**.
- **Élément central (serveur)** : souvent un élément est central au fonctionnement :
  - si le serveur plante → **plus rien ne fonctionne** (point unique de défaillance) ;
  - **goulot d'étranglement potentiel** si le débit d'information est très important.
- **Sans élément central** :
  - gestion **totalement décentralisée et distribuée** ;
  - nécessite la mise en place d'**algorithmes plus ou moins complexes**.

---

## 7. Définition et caractéristiques d'un système distribué

### 7.1 Définition

> Un **système réparti (ou distribué**, de *distributed system*) est un système comprenant **un ensemble de processus et un système de communication**.

C'est un **ensemble d'éléments reliés par un système de communication** :
- Les éléments ont des fonctions de **traitement** (processeurs), de **stockage** (mémoire), de **relation avec le monde extérieur** (capteurs, actionneurs).
- Ils **ne fonctionnent pas indépendamment** mais **collaborent** à une ou plusieurs tâches communes.
- **Conséquence :** une partie au moins de l'**état global** du système est **partagée** entre plusieurs éléments (sinon, on aurait un fonctionnement indépendant).

### 7.2 Trois caractéristiques clés

| Caractéristique | Ce que cela implique |
|---|---|
| **Système distribué = éclaté** | Il faut **connaître les éléments** du système (**identification** et **localisation**) et gérer le **déploiement** et la **présence des éléments essentiels** |
| **La communication à distance est centrale** | **Techniques et protocoles** de communication ; **contraintes de réseau** : **fiabilité** (perte de données) et **temps de propagation** (dépend du type de réseau et de sa charge) |
| **Naturellement concurrent et parallèle** | Chaque élément sur chaque machine est **autonome** ; besoin de **synchronisation** et de **coordination** entre éléments distants, et pour l'**accès aux ressources** (**exclusion mutuelle**...) |

---

## 8. Propriétés des systèmes distribués

### 8.1 Hétérogénéité

Elle peut concerner :
- les **machines** (puissance, architecture matérielle...) ;
- les **systèmes d'exploitation** de ces machines ;
- les **langages de programmation** des éléments logiciels ;
- les **réseaux** utilisés (impact sur performances, débit, disponibilité) : **réseau local rapide**, **Internet**, **réseaux sans fil**.

### 8.2 Fiabilité

**Nombreux points de panne ou de problèmes potentiels :**

| Source | Problèmes possibles |
|---|---|
| **Réseau** | Une partie du réseau peut être **inaccessible** ; les temps de communication **varient considérablement** selon la charge ; le réseau peut **perdre des données** transmises |
| **Machine** | Une ou plusieurs machines peuvent **planter** → **paralysie partielle ou totale** du système |

**Solution :** augmenter la fiabilité par **redondance / duplication** de certains éléments — *mais cela rend la gestion du système plus complexe*.

### 8.3 Tolérance aux fautes

**Capacité d'un système à gérer et résister à un ensemble de problèmes.** Le système doit pouvoir :
- fonctionner (**au moins de façon dégradée**) même en cas de **défaillance de certains éléments** ;
- **résister aux perturbations du système de communication** (perte de message, déconnexion temporaire, performances dégradées) ;
- **s'adapter facilement** aux changements d'environnement ou de conditions d'utilisation.

### 8.4 Sécurité

Un système distribué est **beaucoup plus sujet aux attaques** :
- les communications à travers le réseau peuvent être **interceptées** ;
- on ne connaît pas toujours bien l'**élément distant** avec qui on communique.

**Le système doit résister aux attaques** contre sa sécurité :
- violation de la **confidentialité** ;
- violation de l'**intégrité** ;
- **usage indu de ressources** ;
- **déni de service** (DoS).

**Solutions :**
- **Connexion sécurisée par authentification** avec les éléments distants.
- **Cryptage** des messages circulant sur le réseau.

### 8.5 Transparence (aperçu)

- Une fonctionnalité ou un élément est **invisible ou caché** à l'utilisateur ou à un autre élément du système.
- *Remarque du cours :* on devrait plutôt parler d'**opacité** dans certains cas.
- **But :** cacher l'architecture et le fonctionnement interne pour apparaître **comme une application unique et cohérente**.
- Détail dans la section suivante.

---

## 9. Les transparences (norme ISO RM-ODP)

L'ISO définit **8 transparences** (norme **RM-ODP**) :

| # | Transparence | Définition |
|---|---|---|
| 1 | **Accès** | Accéder aux ressources distantes **aussi facilement que localement**, et aux données **indépendamment de leur format de représentation** |
| 2 | **Localisation** | Accéder aux éléments/ressources **indépendamment de leur localisation** |
| 3 | **Concurrence** | **Exécution possible de plusieurs processus en parallèle** avec utilisation de **ressources partagées** |
| 4 | **Réplication** | Possibilité de **dupliquer** certains éléments/ressources pour **augmenter la fiabilité** |
| 5 | **Mobilité** | Possibilité de **déplacer** des éléments/ressources |
| 6 | **Panne** | Le système doit **supporter qu'un ou plusieurs éléments tombent en panne** |
| 7 | **Performance** | Possibilité de **reconfigurer** le système pour **augmenter les performances** |
| 8 | **Échelle** | Supporter l'**augmentation de la taille** du système (éléments, ressources, utilisateurs, étendue géographique) en **préservant ses performances** |

> 💡 **Astuce mnémotechnique :** **A**ccès · **L**ocalisation · **C**oncurrence · **R**éplication · **M**obilité · **P**anne · **P**erformance · **É**chelle.

---

## 10. Quelques difficultés

| Propriété du système | Difficulté engendrée |
|---|---|
| **Asynchronisme** du système de communication (**pas de borne supérieure stricte** pour le temps de transmission d'un message) | Difficulté de **détecter les défaillances** (un message lent ou une machine en panne ? impossible de distinguer) |
| **Dynamisme** (la composition du système **change en permanence**) | Difficulté de **définir un état global** du système et d'**administrer** le système |
| **Grande taille** (nombre de composants, d'utilisateurs, dispersion géographique) | La **capacité de croissance (scalability)** est une propriété importante, mais **difficile à réaliser** |

---

## 11. Applications réparties

### 11.1 Distinction « système » vs « application »

| | **Système** | **Application** |
|---|---|---|
| Rôle | **Gestion des ressources communes et de l'infrastructure**, lié de manière **étroite au matériel sous-jacent** | **Réponse à un problème spécifique** ; fournit des **services à ses utilisateurs** (qui peuvent être d'autres applications) |
| Exemples | **Système d'exploitation** (gestion de chaque élément) ; **système de communication** (échange d'informations entre les éléments) | Application bancaire, navigation web... |
| Caractéristique | **Cache la complexité** du matériel et des communications ; fournit des **services communs de plus haut niveau d'abstraction** | **Utilise** les services généraux fournis par le système |

> ⚠️ La distinction n'est **pas toujours évidente** : certaines applications travaillent directement à **bas niveau** (au contact du matériel), p. ex. **systèmes embarqués**, **réseaux de capteurs**.

### 11.2 Système réparti vs application répartie

| **Système réparti** | **Application répartie** |
|---|---|
| **Ensemble de systèmes calculatoires autonomes** (ordinateur, serveur, terminal...) **sans mémoire physique commune**, qui **communiquent à travers un réseau quelconque** | **Ensemble de processus** qui tournent **sur un système réparti** afin de **fournir ou utiliser un service déterminé** |

### 11.3 Formule clé

> **Application répartie = traitements coopérants sur des données réparties**

- **Traitement** : description = **programme** ; exécution = **flot d'exécution (processus)**.
- **Coopération = communication + synchronisation**, en définissant :
  - un **modèle d'exécution** ;
  - une **interface de programmation** (et/ou langage) ;
  - un **modèle de programmation** ;
  - des **outils de développement** ;
  - un **environnement d'exécution** : services systèmes (pour différents types d'infrastructures).

### 11.4 Exemples d'applications réparties

- **Navigation web**, **transfert de fichiers**
- **Guichets de banque** : **GAB** (Guichet Automatique de Banque), **DAB** (Distributeur Automatique de Banque)
- **Commerce électronique**
- **Jeux en réseau**
- **Télévision interactive**

---

## 12. Programmation classique vs programmation répartie

### 12.1 Principe

- La plupart des applications réparties sont de type **client/serveur** : le **client demande des services à un serveur**.
- **Programmation classique** : pour utiliser un service, on **appelle localement** une fonction / procédure / méthode (librairie, objet...).
- **Programmation répartie** : l'**appel de fonction / procédure / méthode peut se faire à distance**.
- **Objectif des outils** (RPC, RMI, Web Services...) : **simplifier le développement** d'applications réseau client/serveur en **s'abstrayant de l'aspect « distant »**.

### 12.2 Différences sur le plan matériel et système

| Aspect | Programmation classique | Programmation répartie |
|---|---|---|
| **Machine** | Utilisateur et fournisseur du service sur la **même machine** | **Deux machines différentes** (sans compter celles traversées) |
| **OS** | Même OS | **OS différents** |
| **Mémoire** | Même espace mémoire | **Espace mémoire non unitaire** : comment « passer un pointeur comme argument » ? |
| **CPU** | Même capacité de calcul | Capacités différentes |
| **Transport** | **Pas de problème de transport** | **Problèmes de transport** : pare-feu (*firewall*), réseau, etc. |
| **Disponibilité** | Assurée (tant qu'on a accès à la librairie) | Il faut **retrouver le service** : où se trouve-t-il ? qui le propose ? |

### 12.3 Différences sur le plan langage et données

| Aspect | Programmation classique | Programmation répartie |
|---|---|---|
| **Langage** | Un **même langage** (sinon utilisation de *binding*) | **Deux langages différents** |
| **Paradigme** | Même paradigme de programmation | **Paradigmes différents** : qu'est-ce qu'un objet pour un langage procédural ? comment gérer les erreurs ? |
| **Types de base** | Même représentation | Représentation potentiellement différente |
| **Information composite** | Même représentation | **Représentation différente** |
| **Paramètres** | Association simple paramètres effectifs/formels | Comment **associer** paramètres effectifs et formels ? Comment gérer les **différents types de passage de paramètre** ? |

---

## 13. Modèles d'interaction dans un système distribué

Les éléments distribués **interagissent et communiquent** selon plusieurs modèles :
**Client/Serveur**, **Diffusion de messages**, **Mémoire partagée**, **Pair à pair**, etc.

**Primitive de communication de base :** l'**envoi de message** d'un élément vers un autre. À partir d'envois de messages, on peut construire les **protocoles de communication** correspondant à un modèle d'interaction.

### Rôle des messages
- **Données échangées** entre les éléments (demande de requête, résultat d'une requête, donnée de toute nature...).
- **Gestion et contrôle des protocoles** (acquittement = message bien reçu, synchronisation, coordination...).

### 13.1 Modèle Client/Serveur

**Deux rôles distincts :**
- **Client** : demande que des **requêtes ou services** lui soient rendus.
- **Serveur** : **répond aux requêtes** des clients.

**Interaction :**
1. **Message du client vers le serveur** pour faire une requête.
2. **Exécution d'un traitement** par le serveur.
3. **Message du serveur vers le client** avec le résultat.

**Exemple :** Serveur Web — client = **navigateur web** ; requête = **récupérer le contenu d'une page HTML** gérée ou générée par le serveur.

**Particularités :**
- **Modèle le plus répandu**, fonctionnement **simple**.
- Abstraction de l'appel d'un service : proche de l'**appel d'une opération sur un élément logiciel** (interaction de base en programmation).
- **Liens forts** entre client et serveur.
- Un client peut aussi **jouer le rôle de serveur** (et vice-versa) dans une autre interaction.
- Le client doit généralement **connaître précisément le serveur** (sa localisation, p. ex. l'**URL** du site web).
- Interaction de type **« 1 vers 1 »**.

### 13.2 Modèle de diffusion de messages

**Deux rôles distincts :**
- **Émetteur** : envoie des messages (ou événements) **à tous les récepteurs** (**broadcast**), avec possibilité de cibler un **sous-ensemble** de récepteurs (**multicast**).
- **Récepteurs** : **reçoivent** les messages envoyés.

**Interaction :** l'émetteur envoie un message ; le **middleware** se charge de le **transmettre à chaque récepteur**.

**Deux modes de réception :**

| Mode | Fonctionnement | Analogie du cours |
|---|---|---|
| **PULL** | Le récepteur **vérifie lui-même** s'il a reçu un message (via **boîtes aux lettres**) | Aller relever son courrier dans sa boîte |
| **PUSH** | Le récepteur est **prévenu** que le message est disponible et il lui est **transmis** | Le facteur sonne à la porte et remet le courrier en main propre |

**Particularités :**
- **Dépendance plus faible** entre les participants.
- L'émetteur n'a **pas besoin d'être directement connecté** aux récepteurs, ni de **savoir combien ils sont**.
- Interaction de type **« 1 vers N »**.

### 13.3 Modèle à mémoire partagée

- Les éléments communiquent via une **mémoire partagée** à l'aide d'une **interface d'accès** :
  - **ajout** d'une donnée ;
  - **lecture** d'une donnée ;
  - **retrait** d'une donnée.
- Le **middleware** gère l'accès à la mémoire pour chaque participant.
- **Particularité :** **aucun lien, aucune interaction directe** entre les participants.

**Complexité :** la **gestion de la mémoire** dans un contexte distribué. Plusieurs solutions :

| Solution | Avantage | Inconvénient |
|---|---|---|
| **Toute la mémoire sur un seul site** | Accès simple | **Goulot d'étranglement** potentiel |
| **Mémoire éclatée sur plusieurs sites** (avec ou sans duplication des données) | Meilleure répartition de la charge | Nécessite des **algorithmes plus ou moins complexes** de gestion de mémoire distribuée |

### 13.4 Modèle pair à pair (peer to peer, P2P)

- **Un seul rôle** : **pas de distinction entre les participants**.
- **Chaque participant est connecté à tous les participants d'un groupe**, et tout le monde effectue **les mêmes types d'actions** ; pour partager des données, il faut effectuer un **calcul commun**.

**Exemples :**
- **Échange de fichiers (BitTorrent)** : parfois un **mode hybride client/serveur + P2P** — le serveur sert à **connaître la liste des fichiers et effectuer les recherches** ; le mode P2P est ensuite utilisé pour les **transferts** (chacun envoie une partie du fichier à d'autres participants).
- **Algorithmes de consensus** : chacun **mesure une valeur** (la même en théorie), l'**envoie aux autres**, puis chacun **exécute localement le même algorithme** pour **élire la bonne valeur**.

### 13.5 Comparaison des modèles d'interaction

| Critère | Client/Serveur | Diffusion de messages | Mémoire partagée | Pair à pair |
|---|---|---|---|---|
| **Rôles** | 2 (client, serveur) | 2 (émetteur, récepteur) | Participants + mémoire | **1 seul** (égaux) |
| **Type d'interaction** | **1 vers 1** | **1 vers N** | **Indirecte** via la mémoire | **N vers N** |
| **Couplage** | **Fort** | **Faible** | Très faible (aucun lien direct) | Fort au sein du groupe |
| **Connaître l'autre ?** | Oui (localisation du serveur) | Non (pas besoin de savoir combien) | Non | Oui (connecté à tous) |
| **Point faible** | Serveur = point central | — | Gestion de la mémoire distribuée | Complexité de coordination |
| **Exemples** | Serveur Web | Événements, boîtes aux lettres | Espace de données partagé | BitTorrent, consensus |

*(Les colonnes « Type d'interaction N vers N » et « Point faible » pour certains modèles sont ma synthèse à partir du contenu des diapositives, pour faciliter la révision.)*

---

## 14. Moyens de communication dans un système distribué

Technologies abordées dans la suite du cours :

| Technologie | Idée |
|---|---|
| **Sockets** | Communication bas niveau par flux/paquets sur le réseau |
| **RPC** (*Remote Procedure Call*) | **Appel de procédure à distance** |
| **Objets répartis** : **Java RMI**, **CORBA** | **Appel de méthodes sur des objets distants** |
| **Web Services** | Services accessibles via des protocoles web standards |
| **EJB** (*Enterprise JavaBeans*) | Composants métier côté serveur (Java EE) |

---

## 15. Remarques sur le support de cours

Quelques points à connaître pour éviter la confusion :

1. **Titre du fichier** : le PDF s'appelle « ch1-2 » ; c'est l'**introduction aux applications réparties** (chapitre d'introduction).
2. **Fautes de frappe dans les diapositives** : « massage » au lieu de **message** (diapos 15 et 29), « situatiuon », etc. — sans incidence sur le fond.
3. **Diapositives 35 à 37** : le titre « Mémoire partagée » est **répété** sur la diapositive du modèle **pair à pair** — le contenu concerne bien le **P2P**, pas la mémoire partagée.
4. **Diapositive 15 (Tolérance aux fautes)** : la première puce « Une partie du réseau peut être inaccessible » est **répétée** de la diapositive précédente ; elle illustre la nécessité de la tolérance aux fautes.
5. **Terminologie** : « réparti » et « distribué » sont **synonymes** dans ce cours (*distributed system*).

---

## 16. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| Système centralisé | Tout sur **une seule machine** |
| Système distribué | **Ordinateurs indépendants en réseau** apparaissant comme **une seule entité** |
| Vision matérielle | Multi-processeurs à mémoire partagée · cluster · ordinateurs en réseau |
| Vision logicielle | Entités **indépendantes et parallèles** sur des machines en réseau |
| Intérêts | **Partage de ressources** · **optimisation** · **robustesse** (réplication) |
| Inconvénients | **Réseau** · **serveur central** (panne, goulot) · **algorithmes complexes** si décentralisé |
| 3 caractéristiques | **Éclaté** · **communication centrale** · **concurrent et parallèle** |
| Propriétés | **Hétérogénéité** · **Fiabilité** · **Tolérance aux fautes** · **Sécurité** · **Transparence** |
| 8 transparences | Accès · Localisation · Concurrence · Réplication · Mobilité · Panne · Performance · Échelle |
| Difficultés | **Asynchronisme** → détection de pannes · **Dynamisme** → état global · **Taille** → scalabilité |
| Système vs application | Le système **gère l'infrastructure** ; l'application **résout un problème spécifique** |
| Application répartie | **Traitements coopérants sur des données réparties** |
| Coopération | **Communication + synchronisation** |
| Programmation répartie | Appel de méthode **à distance** → problèmes de **mémoire, OS, langage, transport, localisation** |
| Client/Serveur | **1 vers 1**, le plus répandu, liens forts |
| Diffusion de messages | **1 vers N**, broadcast/multicast, **PULL** ou **PUSH** |
| Mémoire partagée | Communication **indirecte** via une mémoire gérée par le middleware |
| Pair à pair | **Un seul rôle**, tous égaux (BitTorrent, consensus) |
| Technologies | **Sockets · RPC · RMI · CORBA · Web Services · EJB** |

### Questions d'examen typiques
1. Différencier un **système centralisé** et un **système distribué**.
2. Citer les **intérêts** et **inconvénients** des systèmes distribués.
3. Donner les **trois caractéristiques** d'un système distribué (éclaté, communication centrale, concurrent).
4. Expliquer l'**hétérogénéité**, la **fiabilité**, la **tolérance aux fautes** et la **sécurité**.
5. Citer et définir les **huit transparences** de la norme **ISO RM-ODP**.
6. Pourquoi l'**asynchronisme** rend-il la détection des défaillances difficile ?
7. Quelle est la différence entre **système réparti** et **application répartie** ?
8. Comparer **programmation classique** et **programmation répartie** (mémoire, OS, langage, transport).
9. Décrire le modèle **client/serveur** et donner un exemple ; quelles sont ses particularités ?
10. Expliquer la **diffusion de messages** (broadcast/multicast, **PULL** vs **PUSH**).
11. Décrire le modèle à **mémoire partagée** et les solutions de gestion de la mémoire en contexte distribué.
12. Expliquer le **pair à pair** avec deux exemples (BitTorrent, consensus).
13. Citer les **technologies de communication** étudiées dans le cours.

---

*Fin du résumé — Introduction aux applications réparties.*
