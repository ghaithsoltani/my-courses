# Chapitre 1 — Virtualisation (version étudiant complète)
**Module :** Virtualisation et Cloud Computing
**Type de document :** Résumé d'étude avec explications, schémas, tableaux comparatifs et conseils d'examen

> 📌 **Ce document remplace et complète** le résumé précédent du chapitre « Virtualisation » : ce support est une **version étudiante plus détaillée** (155 diapositives contre 97), avec des sections **entièrement nouvelles** : types de stockage, architectures NAS/SAN détaillées, VLAN/VxLAN, méthodologie de projet, et impact sur le **TCO/ROI**. Les sections déjà vues dans la version précédente sont reprises ici de façon plus complète.

---

## Table des matières

1. [Objectifs du module](#1-objectifs-du-module)
2. [Plan du chapitre](#2-plan-du-chapitre)
3. [Historique](#3-historique)
4. [Besoin en virtualisation](#4-besoin-en-virtualisation)
5. [Définition et gestion des ressources](#5-définition-et-gestion-des-ressources)
6. [Composants de la virtualisation](#6-composants-de-la-virtualisation)
7. [Machine physique vs machine virtuelle](#7-machine-physique-vs-machine-virtuelle)
8. [Les quatre caractéristiques de la virtualisation](#8-les-quatre-caractéristiques-de-la-virtualisation)
9. [Hyperviseur et types d'hyperviseurs](#9-hyperviseur-et-types-dhyperviseurs)
10. [Test de connaissance](#10-test-de-connaissance)
11. [Les cinq domaines de la virtualisation](#11-les-cinq-domaines-de-la-virtualisation)
12. [Virtualisation de serveurs](#12-virtualisation-de-serveurs)
13. [Virtualisation de postes de travail](#13-virtualisation-de-postes-de-travail)
14. [Serveur vs poste de travail : comparaison détaillée](#14-serveur-vs-poste-de-travail--comparaison-détaillée)
15. [Virtualisation d'applications](#15-virtualisation-dapplications)
16. [Virtualisation de stockage](#16-virtualisation-de-stockage)
17. [Virtualisation de réseaux](#17-virtualisation-de-réseaux)
18. [Les techniques de virtualisation du processeur](#18-les-techniques-de-virtualisation-du-processeur)
19. [Comparaison-synthèse des techniques CPU](#19-comparaison-synthèse-des-techniques-cpu)
20. [Avantages de la virtualisation](#20-avantages-de-la-virtualisation)
21. [Inconvénients et limites de la virtualisation](#21-inconvénients-et-limites-de-la-virtualisation)
22. [Méthodologie de mise en œuvre d'un projet de virtualisation](#22-méthodologie-de-mise-en-œuvre-dun-projet-de-virtualisation)
23. [Impact de la virtualisation sur le TCO et le ROI](#23-impact-de-la-virtualisation-sur-le-tco-et-le-roi)
24. [Remarques sur le support de cours](#24-remarques-sur-le-support-de-cours)
25. [Fiche de révision rapide](#25-fiche-de-révision-rapide)

---

## 1. Objectifs du module

Le module « Virtualisation et Cloud Computing » vise à :

- **Comprendre** les principaux concepts liés à la **virtualisation**, aux **centres de données** et au **Cloud Computing**.
- Connaître l'**origine du Cloud computing** et ses **concepts de base** (composants, modèle économique, etc.).
- Comprendre les **concepts de base** de la **virtualisation**.
- Être capable de **discuter les avantages et les limites** du Cloud computing.
- Apprendre à **manipuler les principaux services Cloud**.

**Prérequis :** connaissances techniques générales en informatique.

---

## 2. Plan du chapitre

1. **Historique**
2. **Besoin en virtualisation**
3. **Définition**
4. **Éléments de la virtualisation**
5. **Caractéristiques de la virtualisation**
6. **Types d'hyperviseurs**
7. **Domaines de virtualisation**
8. **Types de virtualisation**
9. **Méthodologie de mise en œuvre d'un projet de virtualisation**
10. **Impact de la virtualisation sur le TCO et ROI**

---

## 3. Historique

| Période | Événement |
|---|---|
| **1960** | Traitement des données avec des **mainframes** (super-ordinateurs coûteux), **mais sous-utilisés** (traitement d'**un seul processus à la fois**) |
| **Fin des années 1960** | **Virtualisation implémentée par IBM** pour les mainframes : **CP/CMS** (*Control Program / Cambridge Monitor System*, puis *Console Monitor System*). **Objectif :** permettre à **divers utilisateurs d'accéder simultanément** aux ordinateurs qui effectuaient des **traitements par lots** |
| **Années 1990** | **Problème de fort couplage** entre applications et serveurs physiques **d'un même fournisseur** ; serveurs **en sous-utilisation** → **virtualisation basée sur les technologies x86** |
| **2000** | Les fabricants de processeurs x86 **AMD et Intel** mettent en œuvre la **virtualisation matérielle** dans leurs gammes |

> 🔑 **À retenir :** la virtualisation **n'est pas née avec le cloud** : elle existe depuis les **mainframes des années 1960** (le terme vient de là), avec l'exemple historique **IBM CP/CMS**, bien avant l'apparition des serveurs x86.

---

## 4. Besoin en virtualisation

### 4.1 Prolifération du nombre de serveurs
- **Croissance importante** du nombre de **serveurs physiques** dans les **datacenters**.
- **Manque d'espace** pour les serveurs physiques.
- **Infrastructure rigide et coûteuse** (matériel et licences).
- **Manque d'efficacité énergétique**.

### 4.2 Sous-exploitation des serveurs
- **Habitude traditionnelle : un serveur par application** (web, fichiers, application, base de données, messagerie, impression…).
- Cette méthode **n'utilise qu'environ 5 à 15 %** de la puissance totale des serveurs.
- Avec les **technologies multi-cœurs**, le **taux d'utilisation du CPU est de plus en plus faible**.
- **La virtualisation permet de consolider les charges de travail** des serveurs sous-exploités **sur un seul serveur**, tout en **maîtrisant le taux d'utilisation global**.

### 4.3 Autres problèmes
- Besoin de **serveurs de test** pour le développement d'applications.
- **Déploiement rapide** d'applications.
- **Surcapacité et saturation** de l'OS / de la machine.
- **Logiciel et matériel étroitement couplés**.
- L'exécution de **plusieurs applications sur la même machine** crée souvent des **conflits**.

> ➜ **La virtualisation est la solution** à tous ces problèmes.

---

## 5. Définition et gestion des ressources

### 5.1 Définition
> La **virtualisation** est un **processus** qui permet une **utilisation plus efficace du matériel informatique physique** : c'est une **abstraction des ressources informatiques**.

- La virtualisation utilise un **logiciel (hyperviseur)** pour créer une **couche d'abstraction** sur le matériel : les éléments matériels d'**un seul ordinateur** (processeurs, mémoire, stockage…) sont **divisés en plusieurs ordinateurs virtuels**, les **machines virtuelles (VM)**.
- **Chaque VM exécute son propre OS** et se comporte comme un **ordinateur indépendant**, même si elle ne s'exécute que sur **une partie** du matériel sous-jacent.

### 5.2 Gestion des ressources en virtualisation *(section nouvelle)*

> La virtualisation permet de gérer les ressources physiques **de manière flexible**, en les **divisant** ou en les **regroupant** pour **optimiser leur utilisation**.

| Mode | Principe | Exemple |
|---|---|---|
| **1. Diviser** une ressource physique en plusieurs ressources virtuelles | Une ressource physique (serveur, processeur, disque) est **divisée en plusieurs ressources virtuelles** | Une **seule machine physique** peut exécuter **plusieurs VM indépendantes**, chacune avec son propre OS et ses applications → **virtualisation de serveurs** |
| **2. Regrouper** plusieurs ressources physiques en une seule virtuelle | Plusieurs ressources physiques sont **fusionnées** pour être présentées comme **une seule ressource virtuelle** | **Virtualisation de stockage** ou **de réseau** : plusieurs dispositifs physiques fusionnés en une **ressource unifiée et flexible** |

```
 DIVISER (ex. serveur)                      REGROUPER (ex. stockage)
 ┌─────────────┐        ┌───┐┌───┐┌───┐     ┌───┐ ┌───┐ ┌───┐          ┌──────────────┐
 │  1 serveur  │  ───►  │VM1││VM2││VM3│     │Disq│ │Disq│ │Disq│  ───►  │ 1 volume virtuel│
 │  physique   │        └───┘└───┘└───┘     │ 1 │ │ 2 │ │ 3 │          │   (groupe)      │
 └─────────────┘                            └───┘ └───┘ └───┘          └──────────────┘
```

> 🔑 **Cette distinction (diviser / regrouper) est la clé pour comprendre tous les domaines** : la virtualisation de **serveurs et d'applications** *divise* ; la virtualisation de **stockage et de réseau** *regroupe* (et peut aussi diviser).

---

## 6. Composants de la virtualisation

**Trois composants majeurs :**

| Composant | Rôle |
|---|---|
| **L'invité** (*guest*) | Le composant système qui **interagit avec la couche de virtualisation** au lieu de l'hôte (OS + applications de la VM) |
| **La couche de virtualisation** (*virtualization layer*) | Élément **responsable de recréer un environnement** (similaire ou différent de celui de l'hôte) dans lequel l'invité s'exécute |
| **L'hôte** (*host*) | **Environnement d'origine** où l'invité est censé être géré |

---

## 7. Machine physique vs machine virtuelle

| | **Machine physique** | **Machine virtuelle** |
|---|---|---|
| **Systèmes d'exploitation** | **Un unique OS** | **Plusieurs OS** |
| **Couplage** | **Hardware et software étroitement couplés** | **Hardware et software indépendants** |
| **Pannes** | **Les plantages d'applications affectent tout** | **Applications isolées** |
| **Ressources** | **Sous-utilisation** | **Multiplexage sûr** des ressources entre VM |

---

## 8. Les quatre caractéristiques de la virtualisation

### 8.1 Compatibilité
- **Compatibilité** entre **périphériques matériels**, **systèmes d'exploitation** et **applications**.
- La VM se comporte comme un ordinateur standard, de façon **« égale »** à une machine physique.

### 8.2 Isolation
- Chaque VM est **isolée de l'hôte et des autres VM virtualisées**.
- Les VM sont **séparées les unes des autres**, ce qui **empêche toute interférence ou accès direct** entre elles.
- **Objectif :** empêcher qu'une VM n'ait d'**impact sur les autres**, qu'il s'agisse de **défaillances**, de **failles de sécurité** ou de **consommation excessive de ressources**.
- ➜ **Sécurité, stabilité et confidentialité.**
- Si une VM se bloque ou est attaquée par un virus, **les autres VM ne sont pas affectées**.

### 8.3 Encapsulation
- **L'état complet d'une VM est contenu dans un fichier.**
- **Provisionnement** d'un serveur ≈ **copie d'un fichier**.
- **Migration** d'un serveur ≈ **migration de données**.
- Possibilité d'utiliser les **techniques de gestion des données** pour gérer les serveurs.

### 8.4 Indépendance
| Type | Explication |
|---|---|
| **Indépendance fonctionnelle** | Chaque VM peut être **gérée, configurée et exécutée comme un système autonome**, indépendant des autres VM hébergées sur le même hyperviseur |
| **Abstraction matérielle** | Les VM **détectent toujours le même ensemble matériel**, quel que soit le matériel physique ; les **pilotes sont indépendants** du matériel réel |

- Les VM peuvent être **déplacées vers un matériel différent sans modification**, d'un ordinateur portable à un serveur haut de gamme.

> 💡 **Moyen mnémotechnique :** **C**ompatibilité · **I**solation · **E**ncapsulation · **I**ndépendance → **« CIEI »**.

---

## 9. Hyperviseur et types d'hyperviseurs

### 9.1 Définition
> Un **hyperviseur**, ou **moniteur de machines virtuelles (VMM)**, est un **logiciel ou micrologiciel** qui permet de **créer et gérer des machines virtuelles**. Il fonctionne en **isolant les ressources matérielles physiques** (processeur, mémoire, stockage) et en les **allouant à plusieurs OS** qui s'exécutent **simultanément sur la même machine physique**.

### 9.2 Hyperviseur de type 1 (*bare metal*)

```
  ┌───────┐ ┌───────┐ ┌───────┐
  │Guest VM││Guest VM││Guest VM│
  └───┬───┘ └───┬───┘ └───┬───┘
  ┌───┴─────────┴─────────┴───┐
  │        Hypervisor          │
  │        Kernel Driver       │
  ├────────────────────────────┤
  │       Host Hardware        │
  └────────────────────────────┘
```

- Nommé **« Bare metal approach »** : fonctionne **directement sur le matériel**.
- Peut nécessiter la **virtualisation assistée par matériel** du CPU.
- **Ensemble limité de pilotes**, fournis par le fournisseur de l'hyperviseur.
- **Exemples :** **Xen**, **VMware ESXi**.

### 9.3 Hyperviseur de type 2 (*hosted*)

```
  ┌───────┐ ┌───────┐
  │Guest VM││Guest VM│
  └───┬───┘ └───┬───┘
  ┌───┴─────────┴───┐   Applications
  │    Hypervisor    │
  ├──────────────────┤
  │ Host Operating   │
  │     System       │
  ├──────────────────┤
  │  Host Hardware   │
  └──────────────────┘
```

- Nommé **« Hosted approach »** : exécute les VM **sur un OS hôte** (Windows, Unix…).
- **S'appuie sur l'OS hôte** pour la gestion des ressources physiques et ses **pilotes**.
- **Exemple :** **VirtualBox**.

### 9.4 Fournisseurs

| **Type 1** | **Type 2** |
|---|---|
| **VMware vSphere** | **Oracle VM VirtualBox** |
| **Microsoft Hyper-V** | **VMware Workstation** |
| **KVM** | **VMware Fusion** |
| **Xen hypervisor** | **QEMU** |
| **Citrix Hypervisor** | **Parallels Desktop** |

*(Marché dominé par VMware, Microsoft, Oracle et Citrix.)*

### 9.5 Comparaison type 1 / type 2 (version enrichie)

| Critère | **Type 1** | **Type 2** |
|---|---|---|
| **Accès au matériel** | **Direct, sans intermédiaire** → **performances maximales** | **Indirect via l'OS hôte** → **latence possible** |
| **Charges de travail** | **Optimisé pour les charges intensives** | **Moins efficace** pour les charges lourdes ; adapté aux **tâches légères / tests** |
| **Environnement d'usage** | **Production, cloud computing, datacenters** | **Développement, tests, machines personnelles** |
| **Sécurité** | **Hautement sécurisé** | **Risque de sécurité** plus élevé |
| **Matériel requis** | Peut nécessiter du **matériel spécifique** (virtualisation matérielle) | Fonctionne sur une **gamme plus large** de matériels grâce à l'OS hôte |
| **Coût** | **Plus élevé**, contrats de support importants | **Moins cher**, moins d'exigences |
| **Exemple concret** | **Amazon EC2 (AWS)** : utilise **Xen ou KVM** pour fournir des VM dans le cloud | **VirtualBox pour les développeurs** : tester des applications dans différents environnements |

---

## 10. Test de connaissance

**Questions du cours** *(sans réponse officielle dans le support)* :

1. *Le système d'exploitation invité exécuté sur la VM 20 doit-il être identique à celui de la VM 1 ?*
2. *Quand une VM est redémarrée, l'hôte doit-il aussi être redémarré ?*

**Réponses** *(ajout)* :
1. **Non.** Chaque VM a son **propre OS indépendant** (compatibilité, voir section 8.1) : on peut mélanger Windows, Linux, etc. sur un même hôte.
2. **Non.** Grâce à l'**isolation** (section 8.2), redémarrer une VM n'affecte **ni l'hôte ni les autres VM**.

---

## 11. Les cinq domaines de la virtualisation

| # | Domaine |
|---|---|
| 1 | **Virtualisation de serveur** |
| 2 | **Virtualisation de poste de travail** |
| 3 | **Virtualisation de plateforme / application** |
| 4 | **Virtualisation de stockage** |
| 5 | **Virtualisation de réseau** |

---

## 12. Virtualisation de serveurs

### 12.1 Définition
- Sert à **masquer les ressources du serveur** aux utilisateurs : **nombre et identité des OS, processeurs et serveurs physiques**.
- C'est le **processus de division d'un serveur physique en plusieurs serveurs virtuels uniques et isolés** au moyen d'un logiciel.
- Chaque serveur virtuel exécute **son propre OS indépendamment**.

### 12.2 Comment ça fonctionne ?
- On **fait abstraction du matériel** : on **isole le matériel** de tous les logiciels pouvant s'y exécuter.
- L'**abstraction** reconnaît les **ressources physiques** et crée des **alias logiques**.
- Le **nombre réel de VM** possibles est **limité** par les **ressources physiques** de l'hôte et par les **exigences des applications**.

### 12.3 Avantages (icônes du cours)

| Avantage | Explication |
|---|---|
| **Économies** | Moins de matériel, d'énergie, d'espace |
| **Approvisionnement plus efficace des ressources** | Création rapide de serveurs |
| **Productivité améliorée** | Moins de tâches manuelles |
| **Consolidation IT** | Moins de serveurs physiques |
| **Meilleure gestion** | Administration centralisée |
| **Développement d'applications plus facile et plus sûr** | Environnements de test isolés |
| **Flexibilité et évolutivité** | Ajout de ressources à la demande |
| **Hébergement de plusieurs OS** | Plusieurs systèmes sur un même matériel |
| **Gestion améliorée de la capacité de stockage** | Allocation souple |
| **Continuité d'activité / reprise après sinistre simplifiée** | Sauvegarde et migration de VM |

### 12.4 Inconvénients

| Inconvénient | Détail |
|---|---|
| **Risque et disponibilité** | Mettre en œuvre des techniques de **répartition de charge optimale** et de **récupération / résilience** |
| **VM sprawl** (étalement des VM) | Les VM **inutiles** doivent être **identifiées et mises hors service** pour **libérer les ressources** |
| **Pénuries de ressources** | La virtualisation permet de **dépasser l'utilisation normale** des ressources (sur-allocation) |
| **Licences** | Les **licences des hyperviseurs** doivent être **soigneusement contrôlées** |

> 🔑 **Point clé :** si **l'hôte tombe en panne**, **toutes les VM** qu'il héberge tombent → importance de la **haute disponibilité**.

---

## 13. Virtualisation de postes de travail

### 13.1 Définition
- Technologie qui **sépare les applications d'un utilisateur de son poste de travail**.
- Elle **héberge de manière centralisée** des postes de travail **dans un datacenter ou un cloud** : accès depuis **un appareil connecté à distance**.
- Permet d'exécuter **plusieurs OS de bureau**, chacun dans **sa propre VM**, sur le même ordinateur.

### 13.2 VDI — *Virtual Desktop Infrastructure*

- **L'image du bureau se déplace sur le réseau** ; l'utilisateur interagit **comme si le bureau était local**.
- **Caractéristiques :** les bureaux virtuels vivent **dans des VM sur un serveur centralisé** ; chaque bureau comprend une **image d'OS** (souvent Windows) ; un **broker de connexion** relie le client au pool de ressources.

| | **VDI persistant** | **VDI non persistant** |
|---|---|---|
| **Principe** | L'utilisateur se voit attribuer un **bureau standardisé** lors de sa première connexion ; il **retrouve toujours le même bureau**, et **ses modifications sont conservées** après redémarrage | L'utilisateur peut être connecté **au même ordinateur ou à un ordinateur aléatoire** du pool ; **aucune modification n'est enregistrée** au redémarrage |
| **Priorité** | **Confort et personnalisation** | **Réduction des coûts et simplicité** |
| **Public cible** | Utilisateurs à **besoins spécifiques** (développeurs, analystes, designers) | **Grand nombre d'utilisateurs** aux besoins similaires (administrations, centres d'appels, étudiants) |

**Le broker de connexion** — cinq tâches :

| # | Tâche | Description |
|---|---|---|
| 1 | **Authentification utilisateur** | Authentifie les utilisateurs et accorde l'accès |
| 2 | **Gestion des sessions** | Détermine quelle VM/session attribuer à chaque utilisateur |
| 3 | **Redirection** | Supervise les sessions actives, redirige en cas de problème, applique les politiques (accès, sécurité, ressources) |
| 4 | **Répartition des charges** | Équilibre les connexions pour éviter la surcharge de certains hôtes |
| 5 | **Surveillance et gestion des sessions** | Suivi en temps réel des sessions utilisateur |

**Avantages de la VDI :** **isolation**, **personnalisation**, **sécurité accrue** (données centralisées), **gestion centralisée** (mises à jour simplifiées).
**Inconvénients de la VDI :** **coûts initiaux élevés** (matériel, licences), **utilisation intensive** des ressources matérielles.

### 13.3 RDS — *Remote Desktop Services*

- Utilisé quand **peu d'applications** doivent être virtualisées (plutôt qu'un bureau complet).
- Les **applications sont diffusées** sur le terminal local, qui exécute **son propre OS**.
- Comme **seules les applications** sont virtualisées, la **densité d'utilisateurs par VM** est **plus élevée**.
- **RDS exécute les applications sur Microsoft Windows Server.**

**Fonctionnement :** les utilisateurs se connectent à un **serveur de bureau distant**, et **partagent le même OS et les mêmes ressources**.

| Avantages | Inconvénients |
|---|---|
| **Utilisation efficace des ressources** (partagées entre utilisateurs) | **Moins d'isolation** entre utilisateurs |
| **Coûts réduits** (moins de matériel, moins de licences) | **Moins de personnalisation** |
| **Gestion centralisée** des applications et mises à jour | **Administration et sécurité plus complexes** |

### 13.4 DaaS — *Desktop as a Service*

- **Transfère la charge de la virtualisation aux fournisseurs de services**, ce qui **allège la charge informatique** de l'entreprise.
- Permet de passer des **coûts d'investissement (CAPEX)** aux **coûts opérationnels (OPEX)**, avec des **coûts mensuels prévisibles**.

**Fonctionnement :** les bureaux virtuels sont hébergés sur des **serveurs cloud gérés par un fournisseur** ; accès via **clients légers** ou applications de bureau à distance, **par Internet**.

| Avantages | Inconvénients |
|---|---|
| **Gestion simplifiée** | **Dépendance au fournisseur** (une panne du fournisseur impacte le service) |
| **Évolutivité** | **Coûts opérationnels continus** |
| **Accès à distance** | **Dépendance à la connectivité Internet** |
| **Mises à jour et sécurité** gérées par le fournisseur | **Personnalisation limitée**, **latence**, enjeux de **sécurité et confidentialité** |

### 13.5 Avantages généraux de la virtualisation de poste de travail

| Avantage | Explication |
|---|---|
| **Optimisation des ressources** | Ressources regroupées au niveau du cloud/datacenter |
| **Amélioration de l'expérience utilisateur** | Accès à son environnement **de partout**, **sans transporter son terminal** |
| **Sécurité** | **Contrôle centralisé**, besoins de sécurité matérielle limités aux serveurs, **gestion des identités et des accès** basée sur les rôles |

> *(Dans la version précédente, un quatrième avantage — « agilité de la DSI » — était cité ; il reste valable même s'il n'est pas repris ici.)*

### 13.6 Limites et défis

- **Besoins en infrastructure.**
- **Performance dépendante de la connexion** (réseau).
- **Ressources serveur** (consommation importante, notamment GPU).
- **Personnalisation limitée.**

### 13.7 Exemples d'outils

**VMware Horizon · Microsoft Windows Virtual Desktop (WVD) · Citrix Virtual Apps and Desktops · Amazon WorkSpaces · Nutanix Xi Frame · Oracle Virtual Desktop Infrastructure (VDI)**…

---

## 14. Serveur vs poste de travail : comparaison détaillée

> ⭐ **Tableau nouveau et important** (diapositives 57-58 du support).

| Critère | **Virtualisation Serveur** | **Virtualisation Poste de travail** |
|---|---|---|
| **Définition** | Crée **plusieurs VM** sur un serveur physique unique, chacune exécutant un **OS et des applications** | Fournit des **bureaux virtuels accessibles à distance**, hébergés sur un serveur central |
| **Objectif** | **Héberger des applications et services** (bases de données, sites web, applications métiers) | Offrir aux utilisateurs finaux un **environnement de bureau centralisé**, accessible depuis divers appareils |
| **Cas d'utilisation** | **Consolidation** des serveurs ; **isolation** des environnements (prod vs test) ; **déploiement rapide** | **Gestion centralisée** des postes ; **accès à distance** pour employés/étudiants ; **sécurisation** (les données restent sur le serveur) |
| **Fonctionnement technique** | Basée sur un **hyperviseur** (VMware ESXi, Hyper-V, KVM) ; chaque VM a son **propre OS** | Utilise une **infrastructure VDI** ou des outils (Citrix, VMware Horizon, RDS) ; **OS partagé ou dédié** par utilisateur |
| **Ressources et performance** | Chaque VM consomme CPU/RAM/stockage ; **allocation flexible** | Nécessite des **ressources importantes** sur le serveur (dont **GPU** si applications graphiques) ; **stabilité** cruciale pour l'expérience utilisateur |
| **Gestion et maintenance** | Console centralisée (VMware vCenter) ; mises à jour **par VM séparément** | Gestion **centralisée** ; correctifs déployés **pour tous les utilisateurs simultanément** |
| **Sécurité** | Risques : compromission de l'hyperviseur ou des VM ; données sur **stockage centralisé sécurisé** | **Réduit les risques liés aux terminaux locaux** (données sensibles restent sur le serveur) ; **meilleure gestion des droits d'accès** |
| **Avantage spécifique** | **Réduction des coûts** (consolidation) ; **flexibilité** de test/déploiement | **Mobilité** des utilisateurs ; **gestion simplifiée** des postes de travail |

---

## 15. Virtualisation d'applications

### 15.1 Sans virtualisation (ancien modèle)
- **Installations locales** des applications.
- Les applications **écrivent directement sur le système de fichiers** et **impactent l'OS**.
- Elles doivent **cohabiter avec l'OS et entre elles** (DLL, composants, environnements).
- Écosystème **de plus en plus complexe**, demandant toujours plus de solutions de gestion (automatisation, déploiement, backup, recovery).

### 15.2 Avec virtualisation (nouveau modèle)
- **Applications virtualisées** ; **OS et applications séparés**.
- On gère l'OS et les applications **comme des unités distinctes et autonomes**, qui **collaborent**.
- **Isolation forte** des erreurs et de la sécurité.
- **Déploiement standard, indépendant de l'OS**, n'importe où et très vite.
- **Flexibilité** de choisir la bonne application **quel que soit l'état de l'OS**.

### 15.3 Principe
- Cible **tout ou partie** des applications métiers et/ou bureautiques.
- **Encapsule dans un même package** l'application et son **environnement système**, de manière **imperméable à l'OS**.
- Surtout utilisée sous **Windows** ; outils Linux existants (**Klik**).

### 15.4 Deux apports majeurs

| Apport | Explication |
|---|---|
| **Portabilité** | On peut **déplacer l'application et ses dépendances** d'un environnement à un autre **sans réinstallation** |
| **Isolation** | Les applications virtualisées **n'interfèrent pas** avec d'autres applications ou l'OS hôte : **évite les problèmes de compatibilité et de dépendance** |

### 15.5 Avantages

**Pas d'installation/désinstallation · pas d'impact sur l'OS (registre, fichiers système) · pas de conflits entre applications · plusieurs environnements d'exécution possibles · plusieurs versions de la même application · déploiement sur des postes non gérés · mises à jour, migration et rollback faciles.**

### 15.6 Limites *(section nouvelle)*

| Limite | Explication |
|---|---|
| **Performances** | Certaines applications intensives (**graphisme, jeux**) peuvent moins bien fonctionner qu'en mode natif |
| **Compatibilité limitée** | Les applications dépendantes de **matériel spécifique ou de pilotes** ne peuvent pas être entièrement encapsulées |
| **Complexité initiale** | Créer et gérer les packages demande des **compétences et outils spécifiques** |
| **Coûts logiciels** | Les solutions de virtualisation d'applications peuvent être **coûteuses** |

### 15.7 Exemples d'outils

**VMware ThinApp · VMware Horizon Apps · Microsoft App-V · Citrix Virtual Apps · Parallels Remote Application Server (RAS) · Klik.**

---

## 16. Virtualisation de stockage

> ⭐ **Section très largement enrichie** par rapport à la version précédente.

### 16.1 Principe
- Plusieurs **disques physiques** sont **combinés en un groupe**.
- À partir de ce groupe, le **stockage virtuel** ou des **blocs de stockage logique** sont **affectés à un serveur**.

### 16.2 Concepts fondamentaux *(nouveau)*

| Concept | Définition | Exemple du cours |
|---|---|---|
| **Enregistrement** (*Data Recording*) | **Écriture de données** sur un support de stockage | Création d'un fichier `rapport.txt` sur le disque dur local |
| **Sauvegarde** (*Backup*) | **Copie des données** dans le but de les **archiver**, pour **protéger contre la perte** | Sauvegarde automatique quotidienne du dossier Documents sur **Amazon S3** |
| **Archivage** (*Archiving*) | **Stockage à long terme** de données **non activement utilisées** mais devant être **conservées** | **Amazon Glacier** pour archiver des journaux inutilisés, selon des **politiques de conservation** |

**Archivage vs sauvegarde (*Backup & Archive are complementary*) :**

| | **Sauvegarde** | **Archivage** |
|---|---|---|
| **Cible** | **Données actives** (*active data*) | **Données statiques** (*static data*) |
| **But** | **Restauration** en cas de perte/dommage | **Réduction des coûts** de stockage ; conservation longue durée |
| **Fréquence** | **Régulière** (quotidienne…) | **Ponctuelle**, données rarement modifiées |

> 💡 *(Ajout)* **Différence clé :** la sauvegarde protège des **données qu'on utilise encore** ; l'archivage conserve des **données qu'on n'utilise plus** mais qu'on doit garder (obligation légale, historique…).

### 16.3 Les trois types de stockage *(nouveau)*

| Type | Définition | Exemples d'utilisation |
|---|---|---|
| **Stockage par fichier** | Les données sont stockées sous forme de **fichiers individuels**, organisés en **répertoires** ; **simplicité** d'utilisation et d'accès | **Systèmes de fichiers traditionnels** (NTFS, ext4) ; **partage de fichiers** en réseau (vidéo, audio, image) |
| **Stockage par bloc** | Les données sont **divisées en blocs de taille fixe**, chacun traité comme une **unité distincte** ; utilisé pour des **données structurées**, lues/écrites via des **adresses** | **Bases de données (SQL)** ; **applications critiques** nécessitant des **performances optimales** |
| **Stockage par objet** | Les données sont stockées sous forme d'**objets**, chacun contenant **données + métadonnées + identifiant unique** ; structure **plate**, **capacité presque illimitée**, **autogestion intelligente**, protocoles Internet standard | **Stockage, archivage et sauvegarde orientés Internet** |

> 🔑 **Correspondance avec DAS/NAS/SAN** *(ajout)* : le **stockage par fichier** correspond au **NAS**, le **stockage par bloc** au **SAN**, le **stockage par objet** est utilisé par les services cloud (type **Amazon S3**).

### 16.4 RAID (*Redundant Array of Independent Disks*)

- **Combine plusieurs disques durs indépendants** pour améliorer la **redondance** et/ou la **performance**.
- Utilise **deux disques ou plus** pour créer un système **tolérant aux pannes**.
- Réalisable **matériellement ou logiciellement**, utilisé avec des **serveurs réseau**.
- **Avantages généraux :** **améliore la disponibilité** des données en cas de panne, **optimise les performances** (lecture/écriture), **sécurise les données** tout en assurant la **continuité de service**.

| Niveau | Nom | Principe | Qualificatif du cours |
|---|---|---|---|
| **RAID 0** | **Volume agrégé par bandes (striping)** | Données **réparties sur tous les disques** ; **pas de parité ni de tolérance aux pannes** (« pas une vraie forme de RAID ») | **= Haute performance** |
| **RAID 1** | **Disque en miroir (mirroring)** | Les données sont **écrites simultanément sur deux disques** | **= Protection des données robuste** |
| **RAID 5** | **Bandes à parité répartie** | Striping + **espace d'un disque réservé à la parité**, répartie sur tous les disques | **= Équilibre entre protection des données et vitesse** |
| **RAID 10 (1+0)** | **Miroirs entrelacés** | Données **en miroir (RAID 1)** puis **entrelacées (RAID 0)** | **= Performances et fiabilité optimales** |
| **RAID 0+1** | **Bandes en miroir** | Données **par bandes (RAID 0)** puis **mises en miroir (RAID 1)** | Hybride/imbriqué |
| **RAID 50** *(nouveau)* | — | **Minimum 6 disques** ; reprend les avantages du **RAID 10** ; **augmente la sécurité** en écrivant les mêmes données sur **au moins deux volumes en RAID 5** | Sécurité renforcée |

**RAID + Hot Spare** *(nouveau)* :
- Les RAID **1, 5… et 10** peuvent supporter un **disque de secours (Hot Spare)**, **inutilisé en temps normal**.
- Quand un disque de la grappe RAID **tombe en panne**, le volume RAID **se reconstitue automatiquement** sur le disque laissé en spare.

### 16.5 DAS, NAS, SAN (version enrichie)

| | **DAS** | **NAS** | **SAN** |
|---|---|---|---|
| **Définition** | **Type d'accès traditionnel** aux données (directement attaché) | Périphérique de stockage de **niveau fichier**, connecté à un réseau, fournit des **lecteurs/dossiers partagés** (souvent via **SMB/CIFS**) | **Architecture** où baies de disques, bibliothèques de bandes et juke-box optiques **apparaissent comme des lecteurs connectés localement** |
| **Architecture** | Connexion directe | **Ethernet** ; protocoles **NFS, SMB/CIFS** | **Fibre Channel, iSCSI** ; **commutateurs et interfaces dédiées** |
| **Composants** | — | Unités de stockage intégrées, interface réseau Ethernet, **serveur de fichiers** (OS intégré) | **Hôtes (serveurs)**, **commutateurs Fibre Channel**, **baies de stockage** |
| **Mode d'accès** | Bloc (local) | **Fichier (File-Level)** | **Bloc (Block-Level)** |
| **Sécurité et fiabilité** | — | **RAID** pour la redondance ; **contrôle d'accès aux fichiers** (permissions) | **RAID** ; sécurité par **zonage** et **masquage de LUN** |

**Deux types de SAN** *(nouveau, schéma du cours)* :

| Type | Caractéristique |
|---|---|
| **IP SAN** | Basé sur **iSCSI**, utilise un **réseau Ethernet Gigabit** classique et des **switches IP** |
| **FC SAN** | Basé sur **Fibre Channel**, utilise des **commutateurs Fibre Channel dédiés** |

### 16.6 NAS vs SAN *(tableau nouveau)*

| Critère | **SAN** | **NAS** |
|---|---|---|
| **Type d'accès** | **Basé sur les blocs** (Block-Level) | **Basé sur les fichiers** (File-Level) |
| **Performance** | **Très haute** (latence minimale) | **Modérée** (dépend de l'Ethernet) |
| **Coût** | **Plus élevé** (infrastructure dédiée) | **Moins élevé** (réseaux existants) |
| **Scalabilité** | **Très élevée** | **Limitée** pour les grandes infrastructures |
| **Cas d'usage** | **Applications critiques, bases de données** | **Partage de fichiers, collaboration** |

### 16.7 Fibre Channel et iSCSI

| Technologie | Description |
|---|---|
| **Fibre Channel (FC)** | **Câblage optique** : bande passante élevée, longues distances, **insensible aux interférences électromagnétiques** ; technologie **gigabit** pour le réseau de stockage ; protocole **FCP** pour émettre des **commandes SCSI** sur Fibre Channel |
| **iSCSI** | Norme de réseau de stockage **basée sur IP** : commandes SCSI envoyées sur **TCP/IP**, **port TCP 3260** ; fonctionne en **Gigabit Ethernet ou fibre** ; connecte un SAN à **plusieurs serveurs distants** ; **carte réseau dédiée** requise |

> 💡 *(Ajout du support)* **Transceivers iSCSI** : câbles **cuivre** pour courtes distances, câbles **fibre optique** pour longues distances.

### 16.8 LUN (*Logical Unit Number*)

- Permet à un SAN de **diviser son stockage en éléments gérables**, **affectés à un ou plusieurs serveurs**.
- Un LUN est une **plage de blocs** extraite d'un **pool de stockage partagé**, **présentée au serveur comme un disque logique**.
- Référence logique pouvant désigner un **disque**, une **section de disque**, une **matrice entière** ou une **section de matrice**.
- Sert d'**identifiant logique** pour **attribuer des privilèges d'accès et de contrôle**.

### 16.9 Services de stockage AWS *(nouveau, schéma du cours)*

```
                     Application(s)  [On-Premises]
                  ┌───────┼────────┐
                 SAN      NAS     Objet
                  │        │        │
             FC/iSCSI  NFS/SMB   RESTful     ← protocoles d'accès
                  │        │        │
     Amazon Elastic   Amazon      Amazon S3 (Simple
     Block Store      Elastic File  Storage Service)
     (EBS)            System (EFS) /
                      FSx for Windows
                      File Server
```

| Besoin | Service AWS | Protocole |
|---|---|---|
| **Stockage bloc (SAN)** | **Amazon EBS** (*Elastic Block Store*) | FC / iSCSI |
| **Stockage fichier (NAS)** | **Amazon EFS** (*Elastic File System*), **FSx for Windows File Server** | NFS / SMB |
| **Stockage objet** | **Amazon S3** (*Simple Storage Service*) | RESTful (HTTP) |

---

## 17. Virtualisation de réseaux

> ⭐ **Section considérablement enrichie** (VLAN, VxLAN, approches externe/interne, nouveaux défis).

### 17.1 Définition et principe

- La **virtualisation de réseau (NV)** = **abstraction des ressources réseau**, traditionnellement fournies **par le matériel**, désormais fournies **par le logiciel**.
- Peut **combiner plusieurs réseaux physiques en un seul réseau virtuel**, ou **diviser un réseau physique** en réseaux virtuels **distincts et indépendants**.
- Permet de **déplacer des VM entre domaines sans reconfigurer le réseau**.
- Crée une **superposition (overlay)** : plusieurs **couches de réseau virtuel** sur la **même structure physique**.
- **Provisionnement en quelques minutes** au lieu de jours/semaines.

**Principe général** : **découpler les services réseau des composants matériels physiques** qui les exécutent.

| | **Infrastructure classique** | **Virtualisation des réseaux** |
|---|---|---|
| **Équipements** | **Matériel dédié** (routeurs, commutateurs, pare-feu) | Fonctions **émulées par logiciel** |
| **Gestion** | **Difficile**, configurations complexes | Simplifiée, grâce à **NV, SDN et NFV** |

### 17.2 Deux approches de virtualisation réseau *(nouveau)*

| | **Virtualisation réseau externe** | **Virtualisation réseau interne** |
|---|---|---|
| **Principe** | **Étend la virtualisation au-delà des serveurs individuels**, en utilisant des **ressources réseau partagées** (switches, routeurs) pour créer un **réseau virtuel** | Crée des **réseaux virtuels internes** à un **même serveur ou cluster** ; ne sortent **pas du serveur hôte** |
| **Technique** | Utilise les systèmes physiques d'un même **LAN** pour créer des **VLAN distincts** (ou l'inverse : relier des LAN distincts en un VLAN commun) | Communication entre **VM hébergées sur le même hôte physique** |
| **Cas d'usage** | **Environnements de production** où les VM communiquent entre serveurs/datacenters | **Tests et développement** (sandbox) où les VM n'ont pas besoin de ressources externes |

### 17.3 NV (*Network Virtualisation*) : fonctionnement

- **Dissocie les services réseau du matériel sous-jacent** et permet le **provisionnement virtuel d'un réseau entier**.
- Création, provisionnement et gestion des réseaux **par programmation**, en s'appuyant sur le réseau physique comme **« fond de panier »** de transfert de paquets.
- Les fonctions réseau (**commutation, routage, pare-feu, équilibrage de charge, VPN**…) sont **regroupées et fournies par logiciel** ; le matériel physique n'a plus qu'à **transférer des paquets IP**.

### 17.4 Exemples de NV : VLAN et VxLAN *(nouveau)*

| Technologie | Définition | Couche OSI | Usage |
|---|---|---|---|
| **VLAN** (*Virtual LAN*) | **Sous-section d'un LAN**, créée par logiciel, qui **combine des périphériques réseau en un groupe**, quel que soit l'emplacement physique. Améliore **vitesse et performance** des réseaux occupés, simplifie les modifications/ajouts | **Couche 2** (liaison de données), sur les **switchs** | **Petits réseaux** |
| **VxLAN** (*Virtual eXtensible LAN*) | Fournit un **cadre pour superposer des réseaux virtualisés de couche 2 sur des réseaux de couche 3** ; définit un **mécanisme d'encapsulation** et un **plan de contrôle** → **extension du VLAN** | **Couche 3** (réseau), sur les **routeurs** | **Grands réseaux** |

**Autres technologies d'encapsulation citées :** **NVGRE** (*Next Generation GRE*), **STT** (*Stateless Transport Tunneling*), **GENEVE** (*Generic Network Virtualization Encapsulation*).

**Avantages de NV :**
- **Réduction du temps de provisionnement** (semaines → minutes).
- **Gain en efficacité opérationnelle** (automatisation des processus manuels).
- **Placement et déplacement des charges** indépendamment de la **topologie physique**.
- **Amélioration de la sécurité réseau** au sein du datacenter.

### 17.5 SDN (*Software Defined Network*)

**Réseau classique :** la plupart des équipements se **configurent individuellement** → ❌ **coûteux en temps** pour de grands réseaux, ❌ **sujet aux erreurs humaines**.

**Principe SDN :**
- **Sépare le plan de contrôle** (gestion des flux réseau) **du plan de données** (transmission des paquets).
- Le **contrôle est centralisé** dans un **contrôleur SDN**, qui **programme les périphériques réseau**.
- **Protocole OpenFlow** : l'un des principaux protocoles pour l'interaction **contrôleur SDN ↔ équipements réseau** afin d'**acheminer les paquets**.
- **SDN définit des normes** (**NETCONF, REST, BGP-LS**…) pour **lire l'état du réseau** et **agir sur sa configuration** depuis des serveurs, par **transactions centralisées**.

```
 Réseau classique                         SDN
 switches avec control plane +            Couche applicative (services virtuels)
 data plane intégrés, configurés                    │ API
 un par un                                 ┌────────┴────────┐
                                           │  Contrôleur SDN   │  ← plan de contrôle
                                           └────────┬────────┘   (OpenFlow, NETCONF, REST, BGP-LS)
                                           Commutateurs (data plane uniquement)
```

### 17.6 NFV (*Network Function Virtualization*)

- **Approche classique :** équipements matériels **spécialisés** chez le client (**NAT, pare-feu, chiffrement, DNS, BRAS, DPI, routeur PE**…).
- **Approche NFV :** ces fonctions deviennent des **équipements virtuels** (vCPE) exécutés sur du **matériel standard** (**serveurs, stockage, commutateurs normalisés**).
- **Avantages :**
  - Fonctions réseau sur **matériel standard** plutôt que spécialisé.
  - **Consolidation** des ressources, **réduction** de l'encombrement, de la consommation électrique et des coûts totaux.
  - **Flexibilité et accélération** de la distribution des services/applications.
  - **Diminution du risque** lors des tests d'un nouveau service.

### 17.7 Nouveaux défis *(nouveau)*

| Défi | Description |
|---|---|
| **Plusieurs équipements** | Routeurs, Appliances, Serveurs… à gérer |
| **Complexité de gestion** | Intégration de nombreux équipements |
| **OPEX important** | Mises à niveau, renouvellements, déplacements sur site coûteux |

> ➜ **Une solution : virtualiser les fonctions sur les sites distants.**

### 17.8 SDN & NFV : synthèse

| | **SDN** | **NFV** |
|---|---|---|
| **Principe** | **Séparation du plan de contrôle et du plan de données** | **Séparation de la fonctionnalité et de la capacité** |
| **Apport** | **Interface standard** pour utiliser les ressources réseau ; **gain de programmation**, **simplification des équipements** | **Agilité et flexibilité** ; **réduction des variations d'équipements** |
| **Formule** | ***Software in the network*** | ***The network in software*** |
| **Niveaux impactés** (schéma du cours) | **Présentation / Session** de la pile réseau | **Application / Présentation** de la pile réseau |

> 💡 **SDN et NFV sont complémentaires** : SDN **pilote** le réseau (le « cerveau »), NFV **virtualise les équipements** eux-mêmes (le « corps »).

---

## 18. Les techniques de virtualisation du processeur

### 18.1 L'architecture x86 et les anneaux de privilèges

- Le nom « **x86** » désigne une série de **microprocesseurs Intel**, commençant avec le modèle **80286**, alimentant les PC **à partir de 1982**, en remplacement des gammes **8086/8088**.
- L'architecture x86 fournit **quatre niveaux de privilèges** (**anneaux / rings**) :

| Anneau | Contenu | Privilège |
|---|---|---|
| **Ring 3** | **Applications** | **Minimum** |
| **Ring 2 et 1** | **Pilotes** de périphériques | Intermédiaire |
| **Ring 0** | **Noyau du système** | **Maximum** |

### 18.2 Les quatre méthodes (le support en compte bien 4 cette fois)

> **4 méthodes** pour exploiter le CPU physique via des machines virtuelles :
> 1. **Virtualisation complète** (*Full virtualization*)
> 2. **Paravirtualisation**
> 3. **Virtualisation matérielle** (*Hardware-assisted*)
> 4. **Virtualisation basée sur l'OS** (*OS-based*) / cloisonnement

### 18.3 Virtualisation complète

- **1ᵉʳ type de virtualisation créé (1988** selon ce support).
- Exécution basée sur un **mélange de traduction binaire + exécution directe**.
- Le **code du noyau est traduit** pour remplacer les **instructions non virtualisables** ; le **code utilisateur s'exécute directement** sur le processeur → **haute performance**.
- Elle consiste à **émuler l'intégralité d'une machine physique** pour le système invité : celui-ci **« croit » s'exécuter sur une vraie machine physique**.

**Pourquoi cette simulation est coûteuse** *(détail nouveau)* :

| Élément émulé | Surcoût engendré |
|---|---|
| **Processeur** | **Traduire chaque instruction** pour le processeur réel |
| **Mémoire** | L'hyperviseur **traduit les adresses virtuelles en adresses physiques** |
| **Périphériques** | Disques, cartes réseau… doivent être **simulés** |
| **Coordination des ressources** | L'hyperviseur **équilibre** CPU/mémoire/ressources entre **toutes les VM** |

→ Tout cela crée un **surcoût système (overhead)** qui **limite le nombre d'instances** possibles.

### 18.4 Paravirtualisation

- Les OS invités **travaillent ensemble comme une unité cohésive** et **ont conscience** d'être virtualisés.
- L'**hyperviseur fournit une interface modifiée** aux OS invités : **collaboration étroite** entre OS invité et hyperviseur.
- Le **contrôle est donné à un des OS virtualisés** ; les autres **passent par une redirection** via celui-ci (mécanisme de **pilotes**).
- **Avantage :** peut offrir de **meilleures performances** que la virtualisation complète.
- **Inconvénient :** **dépendance** entre OS : si l'OS « de contrôle » tombe en panne, **les autres ne peuvent plus accéder au matériel**.
- **Exemple de technologie : Xen.**

### 18.5 Virtualisation assistée par matériel

- **Type de virtualisation complète** où le **microprocesseur** possède des **instructions spéciales** facilitant la virtualisation.
- **Virtualisation du processeur :** **VT-x** (Intel), **AMD-V** (AMD).
- **Virtualisation de la mémoire :** **EPT** (Intel), **RVI** (AMD).
- Ces technologies permettent à l'hyperviseur de créer des VM **plus efficaces**, en **réduisant l'overhead** lié à l'émulation.

### 18.6 Virtualisation assistée par l'OS (conteneurisation)

- Aussi appelée **conteneurisation**.
- **Pas d'hyperviseur** : la capacité de virtualisation **fait partie de l'OS** du serveur physique.
- **Tous les serveurs virtuels exécutent le même OS.**
- Technique d'**isolation** qui « emprisonne » l'exécution des applications.

**Caractéristiques détaillées** *(nouveau)* :

| Caractéristique | Explication |
|---|---|
| **Isolation des processus** | Chaque **conteneur** (espace utilisateur) est isolé des autres : les processus **ne peuvent pas interférer** |
| **Partage du noyau** | Toutes les instances **partagent le même noyau** → **moins d'overhead**, **meilleures performances** |
| **Ressources partagées** | Les conteneurs peuvent partager des **bibliothèques système et binaires** → **économie d'espace** |
| **Léger et rapide** | Démarrage **en quelques secondes**, idéal pour des **déploiements rapides et évolutifs** |
| **Docker et conteneurs** | Encapsulent applications + dépendances pour un déploiement **cohérent** dans différents environnements |

**La virtualisation au niveau du système d'exploitation** *(précision complémentaire)* :
- Permet des **environnements utilisateur complètement cloisonnés**.
- Il ne s'agit **pas d'OS invités** mais d'**environnements utilisateur cloisonnés** (« **jails** ») sur **un seul OS**.
- **Migration à chaud** d'un processus d'un environnement utilisateur à un autre.
- **Exemple :** utilisé chez les **hébergeurs web** qui proposent des **environnements privés à moindre coût**.
- **Exemples d'outils :** Oracle Solaris Zones, Linux LXC, **Docker**, AIX WPAR.

---

## 19. Comparaison-synthèse des techniques CPU

> ⭐ **Tableau très enrichi** par rapport à la version précédente (deux tableaux détaillés).

### 19.1 Premier tableau

| Critère | **Virtualisation complète** | **Paravirtualisation** | **Virtualisation assistée par matériel** |
|---|---|---|---|
| **Description** | **Simule entièrement le matériel** pour les VM | **Modifie l'OS invité** pour communiquer directement avec l'hyperviseur | Utilise des **extensions matérielles** (Intel VT-x, AMD-V) pour optimiser la virtualisation |
| **Modification du système invité** | **Non** | **Oui** | **Non** |
| **Performance** | **Moins performante** (surcharge d'émulation) | **Meilleure** (communication directe) | **Très performante** (accélération matérielle) |
| **Compatibilité** | **Très compatible** avec les OS non modifiés | **Compatible uniquement** avec les OS invités **modifiés** | **Compatible** avec les systèmes **non modifiés** |
| **Exemples d'hyperviseurs** | **VMware Workstation, VirtualBox** | **Xen** (mode paravirtualisation) | **VMware ESXi, KVM, Hyper-V, Xen** (mode assisté) |

### 19.2 Second tableau

| Critère | **Virtualisation complète** | **Paravirtualisation** | **Virtualisation assistée par matériel** |
|---|---|---|---|
| **Simplicité d'installation** | **Simple**, aucune modification du système invité | **Plus complexe** (noyau invité à modifier) | **Simple**, extensions activées automatiquement |
| **Soutien matériel requis** | **Non requis** (mais plus lent sans matériel adapté) | **Pas nécessaire** | **Nécessite** un processeur avec extensions (VT-x, AMD-V) |
| **Gestion des E/S** | **Plus lente** (émulation) | **Plus rapide** (pilotes paravirtualisés) | **Très rapide** (accès direct via extensions matérielles) |
| **Isolation des VM** | **Forte** (VM entièrement isolée) | **Moyenne** (partage de ressources, communication directe) | **Très forte** (gérée au niveau matériel) |
| **Utilisation des ressources** | **Plus élevée** (surcharge d'émulation) | **Moyenne** (moins de surcharge) | **Optimisée** (accélération matérielle) |

> 🔑 **Synthèse en une phrase** *(ajout)* : plus on **descend vers le matériel** (complète → para → assistée), plus la **performance augmente** et la **compatibilité avec un OS non modifié revient** (la paravirtualisation est le seul cas exigeant une **modification de l'invité**).

---

## 20. Avantages de la virtualisation

### 20.1 Optimisation de l'infrastructure et réduction des coûts

- **Sans virtualisation :** une seule charge de travail → **faible utilisation** des ressources disponibles.
- **Avec virtualisation :** **plusieurs charges de travail** se partagent le serveur → **utilisation bien plus élevée**.

| Réduction | Explication |
|---|---|
| **Coûts matériels** | **Moins de matériel** nécessaire |
| **Facture énergétique** | **Moins de consommation électrique** et de climatisation |
| **TCO** (*Total Cost of Ownership*) | **Coût total de possession** réduit |
| **ROI** (*Return on Investment*) | **Retour sur investissement** optimisé |

### 20.2 Amélioration de la qualité de service

| Apport | Explication |
|---|---|
| **Moins d'indisponibilité** | Les fonctionnalités avancées de la virtualisation **réduisent les interruptions** |
| **Approvisionnement instantané** | Nouveau serveur en **quelques minutes** (ex. **clonage**) |
| **Répartition de charge dynamique** | Chaque application reçoit les **ressources dont elle a besoin**, même en forte activité |
| **Mise en place de PRA** | Système géré comme des **fichiers indépendants du matériel** → **Plan de Reprise d'Activité facilité** |

### 20.3 Simplification de l'administration

```
   Réduction du nombre d'équipements
 + Sauvegarde instantanée par snapshot
 + Homogénéité de l'infrastructure
 = Moins de maintenance · simplification du travail · meilleure maîtrise de l'infrastructure
```

---

## 21. Inconvénients et limites de la virtualisation

> ⭐ **Section entièrement nouvelle** (absente de la version précédente).

| # | Inconvénient | Détail |
|---|---|---|
| 1 | **Performance réduite** | **Surcharge, latence, contention des ressources** |
| 2 | **Complexité de gestion** | **Dépannage et maintenance** difficiles |
| 3 | **Consommation accrue de ressources** | Besoin de **matériel robuste** |
| 4 | **Vulnérabilités de sécurité** | **Risque pour l'ensemble des VM** (si l'hyperviseur est compromis) |
| 5 | **Coût initial élevé** | **Investissements matériels et logiciels** |
| 6 | **Dépendance aux fournisseurs** | **Verrouillage technologique** (*vendor lock-in*) |
| 7 | **Limites pour applications spécifiques** | Problèmes pour le **temps réel** ou le **matériel spécialisé** |
| 8 | **Latence réseau** | Dans les **environnements virtualisés** |
| 9 | **Gestion des sauvegardes et des données** | Plus complexe à grande échelle |
| 10 | **Limites de scalabilité horizontale** | — |

> 🔑 **À retenir pour l'examen :** la virtualisation **n'est pas une solution magique** — elle **déplace** certains problèmes (sécurité centralisée sur l'hyperviseur, coût initial, dépendance au fournisseur) plutôt que de tous les **supprimer**.

---

## 22. Méthodologie de mise en œuvre d'un projet de virtualisation

> ⭐ **Section entièrement nouvelle** : **7 étapes**.

| # | Étape | Détails |
|---|---|---|
| 1 | **Analyse des besoins et planification stratégique** | **Définir les objectifs** du projet · **évaluer les besoins techniques** · **étude de faisabilité** · **choisir le type de virtualisation** |
| 2 | **Conception de l'architecture de virtualisation** | **Sélection des technologies** · **planification de l'infrastructure** · **planification de la sécurité** · **plan de migration** |
| 3 | **Déploiement de l'environnement de virtualisation** | **Préparer l'infrastructure matérielle** · **installer l'hyperviseur** · **configurer la solution de gestion des VM** · **configurer les politiques de stockage et réseau** |
| 4 | **Migration et intégration** | **Test de migration** · **migration en production** · **intégration avec les systèmes existants** |
| 5 | **Tests et optimisation** | **Tests de performance** · **tests de résilience** · **optimisation** |
| 6 | **Documentation et formation** | **Documentation du projet** · **formation du personnel** |
| 7 | **Supervision et maintenance** | **Mise en place de la supervision** · **maintenance continue** |

```
 1. Analyse des besoins ─► 2. Conception ─► 3. Déploiement ─► 4. Migration
                                                                      │
 7. Supervision & maintenance ◄── 6. Documentation & formation ◄── 5. Tests & optimisation
```

> 🔑 **Ce cycle ressemble à n'importe quel projet IT** (analyse → conception → déploiement → migration → test → documentation → exploitation), appliqué spécifiquement à la **virtualisation**.

---

## 23. Impact de la virtualisation sur le TCO et le ROI

> ⭐ **Section entièrement nouvelle.**

### 23.1 TCO — *Total Cost of Ownership*

> **Mesure le coût total d'un produit ou service sur toute sa durée de vie.** Englobe **tous les coûts directs et indirects**.

| Composant du TCO | Détail |
|---|---|
| **Coût d'acquisition** | Achat initial du matériel, des logiciels et des licences |
| **Coût d'exploitation** | Consommation énergétique, stockage, autres dépenses opérationnelles |
| **Coût de maintenance** | Mise à jour, réparation, remplacement |
| **Coût de formation** | Personnel devant gérer et utiliser l'infrastructure |
| **Coût de mise à niveau / obsolescence** | Améliorations ou remplacements pendant la durée de vie |

> ➜ **Critère clé** pour évaluer l'**efficacité économique** d'une technologie comme la virtualisation.

### 23.2 ROI — *Return On Investment*

> **Mesure le rendement ou les bénéfices générés par un investissement par rapport à son coût initial.**

**Formule :**

$$ROI = \dfrac{\text{Bénéfices nets}}{\text{Coût total de l'investissement}} \times 100$$

**Exemple du cours :** une entreprise investit **100 000 $** dans une nouvelle technologie, qui génère un **revenu brut de 150 000 $** :

$$ROI = \dfrac{150\,000 - 100\,000}{100\,000} \times 100 = 50\,\%$$

> ➜ Souvent utilisé pour **comparer la rentabilité** de plusieurs projets d'investissement ; **crucial** pour la **prise de décision stratégique** en matière d'investissements technologiques.

### 23.3 Impacts de la virtualisation sur le TCO

**Réduction des coûts matériels · diminution des coûts énergétiques · simplification de la gestion IT · optimisation de l'utilisation des ressources · (mais) coûts initiaux** (investissement de départ).

### 23.4 Impacts de la virtualisation sur le ROI

**Accélération de la mise en œuvre · flexibilité et évolutivité · amélioration de la continuité d'activité · réduction des temps d'arrêt · amortissement des coûts initiaux.**

### 23.5 Comparaison avant / après la virtualisation (estimations du cours)

| Aspect | **Avant (Physique)** | **Après (Virtualisé)** |
|---|---|---|
| **Nombre de serveurs** | **20 serveurs** | **8 serveurs** |
| **Utilisation moyenne** | **20 %** | **75 %** |
| **Coûts énergétiques** | **10 000 €/an** | **4 000 €/an** |
| **Coûts de maintenance** | **15 000 €/an** | **8 000 €/an** |
| **Temps d'arrêt annuel** | **40 heures** | **6 heures** |
| **ROI** | **Long** (amortissement en **3 ans**) | **Rapide** (amortissement en **12 à 18 mois**) |

> 🔑 **Chiffres présentés comme des estimations**, utiles pour illustrer l'ordre de grandeur des gains, **pas comme des valeurs universelles**.

---

## 24. Remarques sur le support de cours

1. **Relation avec la version précédente :** ce support (**155 diapositives**) est une **version étendue** du support précédent (**97 diapositives**) du même chapitre, avec les mêmes bases (historique, hyperviseurs, 5 caractéristiques…) mais **beaucoup plus de détails et de nouvelles sections** (stockage, réseau, méthodologie, TCO/ROI).
2. **« 3 méthodes » vs « 4 méthodes »** pour le CPU : contrairement à la version précédente (qui annonçait 3 méthodes pour en lister 4), **cette version annonce et liste correctement 4 méthodes**.
3. **x86 :** le support situe le début de la série au **80286 (1982)**, succédant aux **8086/8088**. Historiquement, la **famille x86 commence avec le 8086 (1978)** ; le 80286 en est un **successeur**. *(Précision ajoutée.)*
4. **Date « 1988 »** pour le premier type de virtualisation complète : à prendre comme un **repère du support**, les sources varient sur la datation exacte de l'histoire de la virtualisation x86.
5. **Diapositive « RAID +Hot Spare » et « RAID 50 » :** notions **avancées**, à connaître surtout pour une spécialisation en administration systèmes/stockage.
6. **Confusion possible stockage/réseau :** la section « **Concepts fondamentaux** » (enregistrement, sauvegarde, archivage) est une introduction **générale au stockage**, avant d'aborder les types (fichier/bloc/objet) puis DAS/NAS/SAN.
7. **SDN & NFV (diapositive 119) :** le schéma montre les termes « Application / Présentation / Session… » encadrés en rouge à des **niveaux différents** pour SDN et NFV, illustrant que ces deux technologies **interviennent à des couches différentes** de la pile réseau logicielle (au-delà du modèle OSI strict).
8. **Formule du ROI :** certaines présentations définissent le ROI comme `(Gain – Coût) / Coût`, ce qui est **cohérent avec l'exemple numérique donné** (150 000 − 100 000)/100 000 = 50 %, bien que la diapositive l'énonce d'abord comme « Bénéfices nets / Coût total ».
9. **Tableau « Comparaison avant et après »** : explicitement présenté comme **basé sur des estimations**, donc à **ne pas citer comme des statistiques officielles**.
10. **Tests de connaissance et questions de réflexion** : toujours **sans corrigé** dans le support ; les réponses proposées dans ce résumé sont des **ajouts**.

---

## 25. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Historique** | **Mainframes 1960** → IBM **CP/CMS** (fin 1960) → **x86** (1990, matérielle en **2000** chez AMD/Intel) |
| **Besoin** | **Prolifération** des serveurs, **sous-utilisation (5-15 %)**, rigidité, coût, énergie |
| **Virtualisation** | **Abstraction** : un matériel physique → plusieurs **VM** |
| **Gestion des ressources** | **Diviser** (1 serveur → plusieurs VM) ou **Regrouper** (plusieurs disques → 1 volume) |
| **3 composants** | **Invité · Couche de virtualisation (hyperviseur) · Hôte** |
| **4 caractéristiques** | **Compatibilité · Isolation · Encapsulation · Indépendance** |
| **Hyperviseur type 1** | **Bare metal**, datacenter : Xen, ESXi, Hyper-V, KVM |
| **Hyperviseur type 2** | **Hosted**, poste client : VirtualBox, VMware Workstation |
| **5 domaines** | **Serveur · Poste de travail · Application · Stockage · Réseau** |
| **VDI / RDS / DaaS** | **Bureau complet** / **Applications seules** / **Service externalisé** |
| **VDI persistant / non persistant** | **Bureau personnalisé conservé** / **bureau réinitialisé** |
| **Broker de connexion** | Authentification · gestion des sessions · redirection · répartition de charge · surveillance |
| **Virtualisation d'applications** | **Package app + environnement**, indépendant de l'OS ; **portabilité + isolation** |
| **3 types de stockage** | **Fichier** (NAS) · **Bloc** (SAN) · **Objet** (cloud, type S3) |
| **Sauvegarde vs archivage** | **Données actives, restauration** / **données statiques, conservation longue durée** |
| **RAID** | **0** (bandes, perf) · **1** (miroir, robustesse) · **5** (parité, équilibre) · **10/0+1** (hybride) · **50** (≥6 disques) |
| **Hot Spare** | **Disque de secours**, reconstruction automatique |
| **DAS/NAS/SAN** | **Direct** / **Fichier (Ethernet, SMB/NFS)** / **Bloc (FC, iSCSI)** |
| **IP SAN / FC SAN** | **iSCSI sur Ethernet** / **Fibre Channel dédié** |
| **LUN** | **Unité logique** de stockage affectée à un serveur |
| **Services AWS** | **EBS** (bloc) · **EFS/FSx** (fichier) · **S3** (objet) |
| **NV** | **Abstraction réseau** ; **VLAN** (couche 2, switchs, petits réseaux) · **VxLAN** (couche 3, routeurs, grands réseaux) |
| **NV externe / interne** | **Entre serveurs/datacenters** / **au sein d'un même serveur (sandbox)** |
| **SDN** | **Plan de contrôle séparé du plan de données** ; protocole **OpenFlow** ; *Software in the network* |
| **NFV** | **Fonctions réseau virtualisées** sur matériel standard ; *The network in software* |
| **Anneaux x86** | **Ring 3** applications · **Ring 1-2** pilotes · **Ring 0** noyau |
| **4 techniques CPU** | **Complète** (traduction binaire) · **Para** (hypercalls, Xen) · **Assistée par matériel** (VT-x/AMD-V) · **Par l'OS** (conteneurs) |
| **Avantages** | **Coûts** (TCO, ROI) · **qualité de service** (PRA, clonage) · **administration simplifiée** |
| **Inconvénients** | **Performance, complexité, sécurité centralisée, coût initial, vendor lock-in** |
| **Méthodologie (7 étapes)** | **Besoins → Conception → Déploiement → Migration → Tests → Documentation → Supervision** |
| **TCO** | **Coût total** sur tout le cycle de vie (acquisition, exploitation, maintenance, formation, obsolescence) |
| **ROI** | **(Gain − Coût) / Coût × 100** |
| **Avant/après virtualisation** | **20 → 8 serveurs**, utilisation **20 % → 75 %**, amortissement **3 ans → 12-18 mois** |

### Questions d'examen typiques
1. Retracer l'**historique** de la virtualisation (mainframes, IBM CP/CMS, x86).
2. Expliquer la **gestion des ressources** : diviser vs regrouper, avec un exemple de chaque.
3. Citer les **4 caractéristiques** de la virtualisation et leur bénéfice.
4. **Comparer hyperviseurs de type 1 et de type 2** avec des exemples concrets (AWS, VirtualBox).
5. **Comparer virtualisation de serveurs et de postes de travail** (tableau détaillé).
6. Différencier **VDI persistant et non persistant** ; rôle du **broker de connexion**.
7. Expliquer les **trois types de stockage** (fichier, bloc, objet) et leur lien avec NAS/SAN/cloud.
8. Différencier **sauvegarde et archivage**.
9. Décrire les niveaux **RAID 0, 1, 5, 10, 50** et le rôle du **Hot Spare**.
10. **Comparer DAS, NAS et SAN**, puis **NAS et SAN** en détail (tableau).
11. Expliquer **VLAN et VxLAN** (couche OSI, usage).
12. Différencier **virtualisation réseau externe et interne**.
13. Expliquer **SDN** (plan de contrôle/données, OpenFlow) et **NFV** (vCPE), puis leur **complémentarité**.
14. Citer les **4 techniques de virtualisation du processeur** et les comparer (performance, compatibilité, modification de l'OS invité).
15. Citer les **avantages et les inconvénients/limites** de la virtualisation.
16. Décrire les **7 étapes** d'un projet de virtualisation.
17. **Définir le TCO et le ROI**, donner leurs composants/formule, et calculer un ROI à partir d'un exemple chiffré.
18. Discuter l'impact de la virtualisation sur le **TCO** et le **ROI** d'une entreprise.

---

*Fin du résumé — Chapitre 1 : Virtualisation (version étudiant complète).*
