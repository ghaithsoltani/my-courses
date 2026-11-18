# Chapitre 1 — Virtualisation
**Cours :** Cloud Computing — Partie 1
**Auteurs du support :** Dr Afef Bohli & Dr Naourez Mejri (2021-2022)
**Type de document :** Résumé d'étude avec explications, schémas, tableaux comparatifs et conseils d'examen

> 🔗 **Lien avec le cours Cloud Computing (Partie 2)** : la virtualisation est la **technologie de base du cloud**. Ce chapitre explique *comment* on partage un matériel entre plusieurs « ordinateurs virtuels » ; la partie suivante explique *comment* le cloud en fait un service (IaaS, PaaS, SaaS, multi-tenancy, élasticité).

---

## Table des matières

1. [Introduction et historique](#1-introduction-et-historique)
2. [Pourquoi virtualiser ?](#2-pourquoi-virtualiser-)
3. [La virtualisation : définition et composants](#3-la-virtualisation--définition-et-composants)
4. [Machine physique vs machine virtuelle](#4-machine-physique-vs-machine-virtuelle)
5. [Principe de fonctionnement et hyperviseur](#5-principe-de-fonctionnement-et-hyperviseur)
6. [Les quatre caractéristiques de la virtualisation](#6-les-quatre-caractéristiques-de-la-virtualisation)
7. [Les types d'hyperviseurs](#7-les-types-dhyperviseurs)
8. [Test de connaissance](#8-test-de-connaissance)
9. [Les cinq domaines de la virtualisation](#9-les-cinq-domaines-de-la-virtualisation)
10. [Virtualisation de serveurs](#10-virtualisation-de-serveurs)
11. [Virtualisation de postes de travail](#11-virtualisation-de-postes-de-travail)
12. [Virtualisation d'applications](#12-virtualisation-dapplications)
13. [Virtualisation de réseaux (NV, SDN, NFV)](#13-virtualisation-de-réseaux-nv-sdn-nfv)
14. [Virtualisation de stockage](#14-virtualisation-de-stockage)
15. [Les techniques de virtualisation du processeur](#15-les-techniques-de-virtualisation-du-processeur)
16. [Comparaison-synthèse](#16-comparaison-synthèse)
17. [Avantages de la virtualisation](#17-avantages-de-la-virtualisation)
18. [Remarques sur le support de cours](#18-remarques-sur-le-support-de-cours)
19. [Fiche de révision rapide](#19-fiche-de-révision-rapide)

---

## 1. Introduction et historique

- Le terme **« virtualisation »** trouve ses racines dans les **mainframes des années 1960**, où il désignait une **méthode de division logique des ressources** du mainframe pour **différentes applications**.
- Depuis, le **sens du terme a évolué**.

### Trois grandes étapes de l'informatique

| Époque | Architecture | Caractéristiques |
|---|---|---|
| **1960-1970** | **Système centralisé** | **Tout est sur la même machine** : **un seul processeur** (une horloge commune), **une seule mémoire centrale** (un espace d'adressage commun), **un seul système d'exploitation** (gestion centralisée des ressources, état global facile à reconstituer) ; **accès local** aux ressources ; **peu d'utilisateurs** |
| **1980-1990** | **Évolution vers le réparti** | Machines **de plus en plus performantes** et **moins chères** ; **réseaux de plus en plus rapides** ; **regroupement** des machines (puissance de calcul et de stockage) ; **moins de centralisation** : accès multiples + **partage de ressources** ; **flexibilité** d'extension (matériel et logiciel) |
| **Années suivantes** | **Client/serveur** et **architectures à objets distribués** | Un serveur par application… ce qui conduit au problème ci-dessous |

---

## 2. Pourquoi virtualiser ?

### 2.1 Prolifération des serveurs
- **Croissance importante** du nombre de **serveurs physiques** dans les **datacenters**.
- **Manque d'espace** pour les serveurs physiques.
- **Infrastructure rigide et coûteuse** (**matériel et licences**).
- **Manque d'efficacité énergétique**.

### 2.2 Sous-exploitation des serveurs
- **Habitude traditionnelle : un serveur par application** (serveur web, de fichiers, d'application, de base de données, de messagerie, d'impression…).
- Cette méthode **n'utilise pas la puissance totale** : seulement **≈ 5 à 15 %** de la puissance des serveurs.
- Avec les **technologies multi-cœurs**, le **taux d'utilisation du CPU est de plus en plus faible**.

```
 Avant : 6 serveurs physiques, chacun utilisé à 5-15 %
 [Web] [Fichiers] [Application] [Base de données] [Messagerie] [Impression]
                                      │
                                      ▼  virtualisation = consolidation
 Après : 1 serveur physique hébergeant 6 machines virtuelles, taux d'utilisation global maîtrisé
```

> 💡 **La virtualisation consolide** les charges de travail des serveurs sous-exploités **sur un seul serveur**, tout en **maîtrisant le taux d'utilisation global**.

### 2.3 Autres problèmes
- Besoin de **serveurs de test** pour le développement d'applications.
- **Déploiement rapide** d'applications.
- **Surcapacité et saturation** de l'OS / de la machine.
- **Logiciel et matériel étroitement couplés.**
- L'exécution de **plusieurs applications sur la même machine** crée souvent des **conflits**.

---

## 3. La virtualisation : définition et composants

### 3.1 Définition
> La **virtualisation** est un **processus** qui permet une **utilisation plus efficace du matériel informatique physique** : c'est une **abstraction des ressources informatiques**.

- Un **logiciel** crée une **couche d'abstraction** sur le matériel : les éléments matériels d'**un seul ordinateur** (**processeurs, mémoire, stockage…**) sont **divisés en plusieurs ordinateurs virtuels**, appelés **machines virtuelles (VM)**.
- **Chaque VM exécute son propre système d'exploitation** et se comporte comme un **ordinateur indépendant**, même si elle ne s'exécute que sur **une partie** du matériel.

### 3.2 Les trois composants majeurs

| Composant | Rôle |
|---|---|
| **L'invité** (*guest*) | Le composant système qui **interagit avec la couche de virtualisation** au lieu de l'hôte (OS + applications de la VM) |
| **La couche de virtualisation** (*virtualization layer*) | Élément **responsable de recréer un environnement** (similaire ou différent de celui de l'hôte) dans lequel l'invité s'exécute (l'**hyperviseur**) |
| **L'hôte** (*host*) | **Environnement d'origine** où l'invité est censé être géré (**machine physique + OS éventuel**) |

### 3.3 Architecture traditionnelle vs virtualisée

```
 Traditionnelle                         Virtualisée
 ┌─────────────────┐                    ┌───────┐ ┌───────┐ ┌───────┐
 │  Applications   │                    │ Apps  │ │ Apps  │ │ Apps  │
 │ Operating System│                    │  OS   │ │  OS   │ │  OS   │   ← VM 1, 2, 3
 ├─────────────────┤                    ├───────┴─┴───────┴─┴───────┤
 │    Hardware     │                    │        Hypervisor          │
 └─────────────────┘                    ├────────────────────────────┤
   « ancien serveur »                   │          Hardware          │
                                        └────────────────────────────┘
```

**Exemple : virtualisation du matériel** — elle permet à **plusieurs OS invités** (machines virtuelles) de s'exécuter **simultanément sur un hôte physique unique**, en fournissant une **abstraction matérielle** et en **multiplexant** les ressources physiques.

---

## 4. Machine physique vs machine virtuelle

| | **Machine physique** | **Machine virtuelle** |
|---|---|---|
| **Systèmes d'exploitation** | **Un unique OS** | **Plusieurs OS** |
| **Couplage** | **Hardware et software étroitement couplés** | **Hardware et software indépendants** |
| **Pannes** | **Les plantages d'applications affectent tout** | **Applications isolées** |
| **Ressources** | **Sous-utilisation** | **Multiplexage sûr** des ressources entre les VM |

---

## 5. Principe de fonctionnement et hyperviseur

- La virtualisation consiste à construire un **isomorphisme** qui **associe un système invité virtuel à un système hôte réel**.
- Appliquée à **une machine entière**, elle fournit une **machine virtuelle (VM)** : l'**ensemble des ressources matérielles** — **processeurs, mémoire, périphériques d'E/S** — est **virtualisé**.
- L'OS exécuté dans une VM est le **système d'exploitation invité** ; chaque machine physique est appelée **hôte**.

### L'hyperviseur (VMM — *Virtual Machine Monitor*)
> Logiciel qui **gère plusieurs systèmes d'exploitation** (ou plusieurs instances du même OS) sur **un seul système informatique**. Il **gère le processeur, la mémoire et les autres ressources**, et **alloue à chaque OS ce dont il a besoin**.

- Les hyperviseurs sont **conçus pour une architecture de processeur donnée**.
- On les appelle aussi **gestionnaires de virtualisation**.

---

## 6. Les quatre caractéristiques de la virtualisation

| # | Caractéristique | Description | Bénéfice |
|---|---|---|---|
| 1 | **Compatibilité** | La VM est compatible avec les **périphériques matériels**, les **systèmes d'exploitation** et les **applications** | Une VM se comporte comme un ordinateur standard |
| 2 | **Isolation** | Chaque VM est **isolée de l'hôte et des autres VM** et **s'exécute indépendamment**. **Si une VM se bloque ou est attaquée par un virus, les autres ne sont pas affectées** | **Sécurité et stabilité** |
| 3 | **Encapsulation** | **L'état complet d'une VM est contenu dans un fichier** | **Provisionnement** d'un serveur = **copie d'un fichier** ; **migration** = **migration de données** ; on gère les serveurs avec les **techniques de gestion de données** |
| 4 | **Indépendance** (vis-à-vis du matériel) | Les VM **détectent toujours le même matériel virtuel**, quel que soit le matériel physique ; les **pilotes** sont indépendants du matériel de la machine | Une VM peut être **déplacée vers un matériel différent sans modification** (d'un portable à un serveur haut de gamme) |

> 💡 **Moyen mnémotechnique :** **C**ompatibilité · **I**solation · **E**ncapsulation · **I**ndépendance → **« CIEI »**. L'**encapsulation** explique le **snapshot**, le **clonage** et la **migration à chaud**.

---

## 7. Les types d'hyperviseurs

### 7.1 Hyperviseur de type 1 (*bare metal*)

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

- Nommé **« Bare metal approach »** : il **s'exécute directement sur le matériel** du système.
- Peut nécessiter la **prise en charge de la virtualisation assistée par matériel** par le CPU.
- **Ensemble limité de pilotes** matériels, fournis par le **fournisseur de l'hyperviseur**.
- **Exemples :** **Xen**, **VMware ESXi**.

### 7.2 Hyperviseur de type 2 (*hosted*)

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

- Nommé **« Hosted approach »** : exécute des VM **sur un système d'exploitation hôte** (Windows, Unix…).
- **S'appuie sur l'OS hôte** pour la gestion des ressources physiques.
- L'OS hôte fournit les **pilotes** pour communiquer avec le matériel.
- **Exemple :** **VirtualBox**.

### 7.3 Comparaison type 1 / type 2

| Critère | **Type 1 (bare metal)** | **Type 2 (hosted)** |
|---|---|---|
| **Position** | **Directement sur le matériel** | **Au-dessus d'un OS hôte** |
| **Performance** | **Plus efficace et plus performant** | Moins performant (couche d'OS en plus ; **latence**) |
| **Usage** | **Centres de données à fortes exigences** | **Postes clients / utilisateurs finaux** (pas pour le datacenter) |
| **Sécurité** | **Hautement sécurisé** | Moins de garanties de sécurité |
| **Charges** | **Très élevées** : **To de RAM**, **centaines de cœurs** | Charges modestes |
| **Coût** | **Plus élevé**, contrats de support plus importants | **Moins cher**, moins d'exigences |

### 7.4 Produits du marché

| **Type 1** | **Type 2** |
|---|---|
| **VMware vSphere** | **Oracle VM VirtualBox** |
| **Microsoft Hyper-V** | **VMware Workstation** |
| **KVM** | **VMware Fusion** |
| **Xen hypervisor** | **QEMU** |
| **Citrix Hypervisor** | **Parallels Desktop** |

*(Le marché comprend notamment VMware, Microsoft, Oracle et Citrix.)*

---

## 8. Test de connaissance

**Questions du cours :**

1. *Le système d'exploitation invité exécuté sur la VM 20 doit-il être identique à celui de la VM 1 ?*
2. *Quand une VM est redémarrée, l'hôte doit-il aussi être redémarré ?*

**Réponses** *(ajout)* :
1. **Non.** Chaque VM a **son propre OS**, indépendant : on peut mélanger Windows, Linux, etc. sur le même hôte.
2. **Non.** Grâce à l'**isolation**, redémarrer une VM **n'affecte ni l'hôte ni les autres VM**.

---

## 9. Les cinq domaines de la virtualisation

| # | Domaine |
|---|---|
| 1 | **Virtualisation de serveur** |
| 2 | **Virtualisation de réseau** |
| 3 | **Virtualisation de poste de travail** |
| 4 | **Virtualisation de plateforme / application** |
| 5 | **Virtualisation de stockage** |

---

## 10. Virtualisation de serveurs

### 10.1 Définition
- Sert à **masquer les ressources du serveur** aux utilisateurs : **nombre et identité des OS, des processeurs et des serveurs physiques**.
- C'est le **processus de division d'un serveur physique en plusieurs serveurs virtuels uniques et isolés** au moyen d'un **logiciel**.
- Chaque serveur virtuel **exécute son propre OS** de façon indépendante.

### 10.2 Comment ça marche ?
- On **fait abstraction du matériel** : on **isole le matériel de tous les logiciels** qui pourraient s'y exécuter.
- L'**abstraction** reconnaît les **ressources physiques** et crée des **alias logiques**.
- Le **nombre réel de VM** possibles est **limité** par les **ressources physiques** de l'hôte et par les **exigences des applications** qui s'y exécutent.

### 10.3 Avantages (10 icônes du cours)

| # | Avantage | Explication |
|---|---|---|
| 1 | **Économies** (*cost savings*) | Moins de matériel, d'énergie, d'espace |
| 2 | **Approvisionnement plus efficace des ressources** | Création rapide de serveurs |
| 3 | **Productivité améliorée** | Moins de tâches manuelles |
| 4 | **Consolidation IT** | Moins de serveurs physiques |
| 5 | **Meilleure gestion** | Administration centralisée |
| 6 | **Développement d'applications plus facile et plus sûr** | Environnements de test isolés |
| 7 | **Flexibilité et évolutivité** | Ajout de ressources à la demande |
| 8 | **Hébergement de plusieurs OS** | Plusieurs systèmes sur un matériel |
| 9 | **Gestion améliorée de la capacité de stockage** | Allocation souple |
| 10 | **Continuité d'activité / reprise après sinistre simplifiée** | Sauvegarde et migration de VM |

### 10.4 Inconvénients (10 points du cours)

| # | Inconvénient |
|---|---|
| 1 | **Coûts d'implémentation et de licences** |
| 2 | **VM sprawl** (étalement des VM) |
| 3 | **Sauvegarde des données** |
| 4 | **Point de défaillance unique** (*single point of failure*) |
| 5 | **Sécurité des serveurs** |
| 6 | **Création de serveurs virtuels trop facile** |
| 7 | **Contention des ressources** |
| 8 | **Problèmes de performance** |
| 9 | **Éducation et formation** |
| 10 | **Couche supplémentaire dans la pile** |

**Détails donnés par le cours :**

| Risque | Explication |
|---|---|
| **Risque et disponibilité** | Mettre en œuvre une **répartition de charge optimale** et des techniques de **récupération / résilience** |
| **VM sprawl** | Les VM **inutiles** doivent être **identifiées et mises hors service** pour **libérer les ressources** |
| **Pénuries de ressources** | La virtualisation permet de **dépasser l'utilisation normale** des ressources du serveur (**sur-allocation**) |
| **Licences** | Les **licences des hyperviseurs** doivent être **soigneusement contrôlées** (respect des accords) |

> 🔑 **Point clé :** si **l'hôte tombe en panne**, **toutes les VM** qu'il héberge tombent : d'où l'importance de la **haute disponibilité** et de la **redondance**.

---

## 11. Virtualisation de postes de travail

### 11.1 Définition
- Technologie qui **sépare les applications d'un utilisateur de son poste de travail**.
- Elle **héberge de manière centralisée** des postes de travail **dans un datacenter ou un cloud** : l'utilisateur y accède depuis **un appareil connecté à distance**.
- Elle permet d'exécuter **plusieurs OS de bureau**, chacun dans **sa propre VM**, sur le même ordinateur.
- Elle crée une **version logicielle de l'environnement de bureau et de l'OS** de l'utilisateur final, **découplée du reste de l'appareil**.

### 11.2 Les trois modes de déploiement

| Mode | Principe | Caractéristiques |
|---|---|---|
| **VDI** (*Virtual Desktop Infrastructure*) | **L'image du bureau voyage sur le réseau** ; l'utilisateur interagit **comme si le bureau était local** | Bureaux virtuels **dans des VM sur un serveur centralisé** ; **une image d'OS (souvent Windows) par bureau** ; un **broker de connexion** relie le client au pool de ressources ; **VDI persistant** ou **non persistant** |
| **RDS** (*Remote Desktop Services*) | On **virtualise seulement des applications** (pas tout le poste) ; **diffusées vers le terminal local** qui exécute **son propre OS** | Utilisé quand **peu d'applications** doivent être virtualisées ; **densité d'utilisateurs par VM plus élevée** |
| **DaaS** (*Desktop as a Service*) | **Transfère la charge de la virtualisation aux fournisseurs de services** | Allège la charge de la DSI ; **passage des coûts d'investissement (CAPEX) aux coûts opérationnels (OPEX)** ; **coûts mensuels prévisibles** |

> 💡 *(Ajout)* **VDI persistant** : chaque utilisateur retrouve **son propre bureau personnalisé** à chaque connexion. **VDI non persistant** : le bureau est **réinitialisé** à chaque déconnexion (image commune, idéal pour la sécurité et le coût).

### 11.3 Avantages

| Avantage | Explication |
|---|---|
| **Optimisation des ressources** | Ressources **regroupées** au niveau du cloud ou du datacenter |
| **Meilleure expérience utilisateur** | Accès à son environnement (**OS, applications, données**) **de partout**, y compris de chez soi, **sans transporter son terminal** |
| **Agilité de la DSI** | **Mise à jour applicative quasi immédiate** au lieu de plusieurs mois |
| **Sécurité** | **Contrôle de sécurité centralisé**, besoins de sécurité matérielle **limités aux serveurs**, accent sur la **gestion des identités et des accès** avec des **autorisations basées sur les rôles** |

---

## 12. Virtualisation d'applications

### 12.1 Sans virtualisation d'applications (ancien modèle)
- **Installations locales** des applications.
- Les applications **écrivent directement sur le système de fichiers** et **impactent l'OS**.
- Elles doivent **cohabiter avec l'OS et entre elles** (**DLL, composants, environnements**).
- L'environnement de travail devient un **écosystème complexe** exigeant toujours plus de solutions de gestion (**automatisation, déploiement, backup, recovery**…).

### 12.2 Avec virtualisation d'applications (nouveau modèle)
- **Applications virtualisées** ; **OS et applications séparés** : la **dépendance à l'OS est cassée**.
- On gère l'OS et les applications **comme des unités distinctes et autonomes**, qui peuvent **collaborer**.
- **Isolation forte** des erreurs et de la sécurité.
- **Déploiement standard, indépendant de l'OS**, n'importe où et très vite.
- **Flexibilité** de choisir la bonne application **quel que soit l'état de l'OS**.

### 12.3 Principe
- Elle cible **tout ou partie des applications métiers et/ou bureautiques**.
- Elle **encapsule dans un même package l'application et son environnement système**, **de manière imperméable à l'OS** sur lequel elle s'exécute.
- Technologie surtout utilisée sous **Windows**, mais il existe des outils **Linux** (ex. **Klik**).

### 12.4 Avantages

- **Pas d'installation / désinstallation**.
- **Plus d'impact sur l'OS** : **registre** et **fichiers système**.
- **Plus de conflits** entre applications.
- **Plusieurs environnements d'exécution** possibles.
- **Plusieurs versions de la même application**.
- **Déploiement sur des postes non gérés**.
- **Mises à jour, migration et rollback** facilités.

**Exemples de logiciels :** **VMware ThinApp**, **Microsoft App-V**.

### 12.5 Questions de réflexion du cours *(avec pistes de réponse — ajout)*

| Question | Piste de réponse |
|---|---|
| **Faut-il virtualiser des postes de travail complets ou juste les applications ?** | **Postes complets (VDI)** : environnement personnalisé, sécurité centralisée, mais **lourd en ressources**. **Applications seules (RDS, App-V)** : **plus léger**, **densité plus forte**, idéal si **peu d'applications** à fournir. Le choix dépend du **besoin**, du **budget** et du **nombre d'utilisateurs**. |
| **Comparer virtualisation de serveurs et de postes de travail** | **Serveur** : consolider **plusieurs serveurs** sur un matériel, **côté datacenter**, cible les **administrateurs**. **Poste de travail** : **héberger les bureaux des utilisateurs** dans le datacenter, cible l'**expérience utilisateur** et la **mobilité** ; nécessite aussi un **bon réseau** (le bureau voyage sur le réseau). |

---

## 13. Virtualisation de réseaux (NV, SDN, NFV)

### 13.1 Virtualisation de réseau (NV)

- **Abstraction des ressources réseau** traditionnellement fournies **par le matériel** vers le **logiciel**.
- Peut **combiner plusieurs réseaux physiques en un seul réseau virtuel** logiciel, ou **diviser un réseau physique en réseaux virtuels indépendants**.
- Permet de **déplacer des VM entre différents domaines sans reconfigurer le réseau**.
- Crée une **superposition (overlay)** : plusieurs **couches de réseau virtuel** sur la **même structure physique**.
- Plus besoin de **jours ou de semaines** pour provisionner l'infrastructure d'une nouvelle application : **quelques minutes**.

**Fonctionnement :**
- **Dissocie les services réseau du matériel** et permet le **provisionnement virtuel d'un réseau entier**.
- On **crée, provisionne et gère** les réseaux **par programmation**, tout en utilisant le réseau physique comme **« fond de panier »** de transfert de paquets.
- Les fonctions réseau — **commutation, routage, pare-feu, équilibrage de charge, VPN**… — sont **regroupées et fournies par logiciel** ; le réseau physique n'a plus qu'à **transférer des paquets IP**.

**Avantages :**

| Avantage | Détail |
|---|---|
| **Provisionnement accéléré** | De **semaines à minutes** |
| **Efficacité opérationnelle** | **Automatisation** des processus manuels |
| **Mobilité des charges** | Placement/déplacement **indépendant de la topologie physique** |
| **Sécurité accrue** | Amélioration de la sécurité **au sein du datacenter** |

### 13.2 SDN (*Software Defined Network*)

**Réseau classique :** la plupart des équipements réseau se **configurent individuellement**, en s'y connectant un par un.
- ❌ **Coûteux en temps** pour de grands réseaux ;
- ❌ **Sujet aux erreurs humaines**.

**SDN :** on **sépare le plan de contrôle du plan de données** :

```
 Réseau traditionnel                        Réseau SDN
 ┌──────────┐ ┌──────────┐                  Applications métier (App App App App)
 │ Switch   │ │ Switch   │                          │ API
 │ Control  │ │ Control  │                  ┌───────┴────────┐
 │ plane +  │ │ plane +  │                  │  Contrôleur SDN │  ← plan de contrôle centralisé
 │ Data plane│ │Data plane│                  │ (Control Plane) │
 └──────────┘ └──────────┘                  └───┬────┬────┬──┘   (OpenFlow, netconf, REST, BGP-LS…)
   chaque équipement a                       Switch Switch Switch  ← plan de données (forwarding)
   son propre « cerveau »                    programmables
```

- **SDN définit des normes** (**NETCONF, REST, BGP-LS…**) pour **lire l'état du réseau** et **agir sur sa configuration depuis des serveurs**.
- Ces protocoles permettent une **reconfiguration centralisée** du réseau, par **transactions**.

### 13.3 NFV (*Network Function Virtualization*)

- **Approche classique :** des **équipements matériels spécialisés** (**routeurs, pare-feu, NAT, DPI, chiffrement, DNS, BRAS, PE router…**), chez le client.
- **Approche NFV :** ces fonctions deviennent des **équipements virtuels** (**vCPE** — *virtual Customer Premises Equipment*), exécutés sur des **serveurs, stockage et commutateurs standard de haute capacité**.
- La NFV permet d'exécuter les **fonctions réseau sur une plateforme matérielle ouverte**, en **réduisant les dépenses d'investissement et d'exploitation** et en **accélérant l'innovation**.

**Avantages de la NFV :**
- Fonctions réseau sur du **matériel standard** plutôt que spécialisé.
- **Consolidation** des ressources, **moins d'encombrement, de consommation électrique et de coûts**.
- **Flexibilité** et **déploiement plus rapide** de services et applications.
- **Moins de risque** lors des **tests** d'un nouveau service.

### 13.4 SDN vs NFV

| | **SDN** | **NFV** |
|---|---|---|
| **Principe** | **Séparation du plan de contrôle et du plan de données** | **Séparation de la fonctionnalité et de la capacité** |
| **Utilise** | Une **interface standard** pour utiliser les ressources du réseau | Des **fonctions réseau virtualisées** sur matériel standard |
| **Gain** | **Programmabilité** ; **simplification** des équipements | **Agilité et flexibilité** ; **moins de variations** d'équipements |
| **Formule du cours** | ***Software in the network*** | ***The network in software*** |

> 💡 **SDN et NFV sont complémentaires** : le SDN **pilote** le réseau, la NFV **virtualise** les équipements.

---

## 14. Virtualisation de stockage

### 14.1 Principe
- **Plusieurs disques physiques** sont **combinés en un groupe** (*pool*).
- À partir de ce groupe, du **stockage virtuel** ou des **blocs de stockage logique** (**volumes virtuels**) sont **affectés à un serveur**.
- Une **couche de virtualisation** s'intercale entre **le stockage physique** et **les serveurs**.

### 14.2 RAID (*Redundant Array of Independent Disks*)
- Utilise **deux disques ou plus** pour créer un système **tolérant aux pannes** qui protège contre les **pannes de disque** et **augmente les performances**.
- Réalisable **par matériel ou par logiciel**, souvent utilisé avec des **serveurs réseau**.

| Niveau | Nom | Principe | Particularités |
|---|---|---|---|
| **RAID 0** | **Volume agrégé par bandes (striping)** | Les données sont **réparties sur tous les disques** (un grand système de fichiers virtuel) | **Pas de parité ni de tolérance aux pannes** (« pas une vraie forme de RAID ») ; **performances accrues** par équilibrage de charge |
| **RAID 1** | **Disque en miroir (mirroring)** | Les données sont **écrites simultanément sur deux disques** | Si un disque tombe en panne, **l'autre continue** ; à son remplacement, les données sont **recopiées** |
| **RAID 5** | **Bandes à parité répartie** | Comme le striping, mais **l'espace d'un disque est utilisé pour la parité** (correction d'erreurs), **répartie sur tous les disques** | **Un disque peut tomber en panne** ; la parité **reconstruit** les données manquantes |
| **RAID 1+0 (RAID 10)** | **Miroirs entrelacés** (*striped mirrors*) | Ensemble **en miroir (RAID 1)** puis **entrelacé (RAID 0)** | Hybride/imbriqué |
| **RAID 0+1** | **Bandes en miroir** | Ensemble **par bandes (RAID 0)** puis **mis en miroir (RAID 1)** | Hybride/imbriqué |

**Récapitulatif chiffré** *(ajout)* :

| Niveau | Disques min. | Capacité utile (n disques) | Tolérance aux pannes | Performance |
|---|---|---|---|---|
| **RAID 0** | 2 | **n** | **Aucune** | ★★★ |
| **RAID 1** | 2 | **n / 2** (la moitié) | **1 disque** | Lecture ★★ |
| **RAID 5** | 3 | **n − 1** | **1 disque** | Lecture ★★★, écriture ★ |
| **RAID 10** | 4 | **n / 2** | **≥ 1 disque** (1 par miroir) | ★★★ |

### 14.3 Les architectures de stockage : DAS, NAS, SAN

| | **DAS** (*Direct Attached Storage*) | **NAS** (*Network Attached Storage*) | **SAN** (*Storage Area Network*) |
|---|---|---|---|
| **Définition** | **Accès traditionnel** : stockage **directement attaché** au serveur | **Stockage de niveau fichier connecté au réseau** : fournit des **lecteurs ou dossiers partagés** | **Architecture** où **baies de disques, bibliothèques de bandes, juke-box optiques** **apparaissent comme des lecteurs connectés localement** sur un serveur |
| **Niveau d'accès** | Bloc (local) | **Fichier** | **Bloc** |
| **Protocoles** | — | **SMB / CIFS** (souvent) | **SCSI**, **iSCSI** ou **Fibre Channel** |
| **Particularités** | Simple, **non partagé** | Souvent **plusieurs disques en RAID**, géré par **interface Web** | **Toujours du RAID**, **disques de rechange**, **haut débit** et **redondance** |

### 14.4 LUN, Fibre Channel et iSCSI

| Notion | Description |
|---|---|
| **LUN** (*Logical Unit Number*) | **Référence logique** (un disque, une section de disque, une matrice entière ou une section de matrice) qui permet à un **SAN de diviser son stockage en éléments gérables**, **affectés à un ou plusieurs serveurs**. Sert d'**identifiant logique** pour **attribuer privilèges d'accès et de contrôle** |
| **Fibre Channel (FC)** | Technologie **gigabit** pour la **mise en réseau de stockage** ; **câblage en fibre optique** : **bande passante élevée**, **longues distances**, signaux **peu atténués** et **insensibles aux interférences électromagnétiques** ; utilise le **protocole FCP** qui permet d'émettre des **commandes SCSI** sur Fibre Channel |
| **iSCSI** (*Internet Small Computer System Interface*) | **Norme de réseau de stockage basée sur IP** : envoi de **commandes SCSI sur un réseau TCP/IP**, **port TCP 3260** ; fonctionne en **Gigabit Ethernet ou fibre** ; peut connecter un SAN à **plusieurs serveurs distants** ; la **carte réseau doit être dédiée à iSCSI** |

> 🔑 **FC vs iSCSI** *(ajout)* : **FC** = réseau **dédié, performant, coûteux** ; **iSCSI** = utilise le **réseau Ethernet/IP existant**, **moins cher**.

---

## 15. Les techniques de virtualisation du processeur

### 15.1 Rappel : l'architecture x86 et les anneaux de privilèges

- Le nom « **x86** » désigne une **série de microprocesseurs Intel** (cités : modèle **80286**, **PC à partir de 1982**, succédant aux **8086/8088**).
- L'architecture x86 fournit **quatre niveaux de privilèges** (**anneaux / rings**) pour l'accès au CPU :

| Anneau | Contenu | Privilège |
|---|---|---|
| **Ring 3** | **Applications** | **Minimum** |
| **Ring 2 et 1** | **Pilotes** de périphériques | Intermédiaire |
| **Ring 0** | **Noyau du système (OS)** | **Maximum** |

> 🔑 **Problème de la virtualisation x86 :** l'hyperviseur doit occuper le **ring 0** (privilèges maximum), mais l'**OS invité** croit lui aussi être en **ring 0**. Certaines **instructions privilégiées** ne sont pas « virtualisables » → d'où les **trois (ou quatre) techniques** ci-dessous.

### 15.2 Les méthodes pour exploiter le CPU physique

> Le cours annonce « **3 méthodes** » mais en liste **4** : **virtualisation complète (traduction binaire)**, **paravirtualisation**, **virtualisation matérielle**, et **virtualisation basée sur l'OS** (cloisonnement).

### 15.3 Virtualisation complète (*Full virtualization*)

- **1ᵉʳ type de virtualisation créé.**
- Exécution des instructions basée sur un **mélange de traduction binaire + exécution directe** :
  - le **code du noyau est traduit** pour **remplacer les instructions non virtualisables** par de nouvelles séquences d'instructions ayant l'effet voulu sur le matériel virtuel ;
  - le **code utilisateur est exécuté directement** sur le processeur → **haute performance**.
- Le **système invité n'a pas conscience** d'être virtualisé (il ne sait pas qu'il partage le matériel).
- Les OS virtualisés fonctionnent **de façon totalement indépendante** et sont vus comme des **ordinateurs à part entière** sur un réseau.
- **Limite :** l'hyperviseur a **ses propres besoins de traitement** → il peut **ralentir** les applications et **affecter les performances** du serveur.

### 15.4 Paravirtualisation

- Contrairement à la virtualisation complète, **les OS invités ont conscience** d'être virtualisés et **travaillent ensemble** comme **une unité cohésive**.
- L'hyperviseur **n'a pas besoin d'autant de puissance** de traitement.
- **Le contrôle est donné à un des OS virtualisés** : les autres, qui veulent accéder au matériel, **passent par une redirection via l'OS 1** (mécanisme de **pilotes**).
- **Inconvénient :** **dépendance** d'un OS virtualisé vis-à-vis d'un autre : **si l'OS 1 tombe en panne, l'OS 2 ne peut plus accéder au matériel**.
- L'OS invité est **modifié** pour émettre des **appels à l'hyperviseur (hypercalls)**.

### 15.5 Virtualisation assistée par le système d'exploitation (conteneurisation)

- Aussi appelée **conteneurisation** / **cloisonnement**.
- **Pas d'hyperviseur** : la **capacité de virtualisation fait partie de l'OS du serveur physique**, qui remplit toutes les tâches d'un hyperviseur.
- **Tous les serveurs virtuels doivent exécuter le même OS.**
- Technique d'**isolation** qui « **emprisonne** » l'exécution des applications.
- Le **noyau de l'OS hôte** permet l'exécution de **plusieurs espaces utilisateurs indépendants** ; **très peu ou pas de surcharge**, car on utilise le noyau de l'hôte.
- **Exemples :** **Oracle Solaris Zones**, **Linux LXC** (écrit « LCX » dans le support), **Docker**, **AIX WPAR**.

### 15.6 Virtualisation assistée par matériel (*Hardware-assisted*)

- **Type de virtualisation complète** où l'architecture du **microprocesseur** comporte des **instructions spéciales** pour faciliter la virtualisation.
- Ces instructions permettent de **configurer un contexte virtuel** : l'invité peut **exécuter des instructions privilégiées directement** sur le processeur, même virtualisé.
- **Sans** ces instructions, la **virtualisation complète reste possible** par des **techniques logicielles** comme la **recompilation dynamique** (l'hôte **recompile à la volée** les instructions privilégiées de l'invité pour les exécuter en mode non privilégié).

> 💡 *(Ajout)* Technologies matérielles : **Intel VT-x** et **AMD-V**.

---

## 16. Comparaison-synthèse

### 16.1 Position dans les anneaux de privilèges (schéma du cours)

| Technique | Ring 3 | Ring 1-2 | Ring 0 |
|---|---|---|---|
| **Full virtualization** | Applications | **OS invité (ring 1)** | **VMM** (hyperviseur) |
| **Paravirtualization** | Applications | — | **OS invité paravirtualisé** + **couche de virtualisation** |
| **Hardware assisted** | Applications (mode non-root) | — | **OS invité en ring 0 (mode non-root)** ; **VMM en mode root** (privilèges supérieurs) |

### 16.2 Tableau comparatif du cours

| Paramètre | **Virtualisation complète** | **Paravirtualisation** | **Virtualisation assistée par matériel** |
|---|---|---|---|
| **Génération** | **1ʳᵉ** | **2ᵉ** | **3ᵉ** |
| **Performance** | **Bonne** | **Meilleure dans certains cas** | **Correcte (moyenne)** |
| **Utilisée par** | VMware, Microsoft, KVM | VMware, Xen | VMware, Xen, Microsoft, Parallels |
| **Modification de l'OS invité** | **Non modifié** | **Modifié** (pour émettre des *hypercalls*) | **Non modifié** |
| **OS invité indépendant de l'hyperviseur ?** | **Oui** | **Non** (ex. XenLinux ne tourne que sur Xen) | **Oui** |
| **Technique** | **Exécution directe** (+ traduction binaire) | **Hypercalls** | **Sortie en mode root** sur instruction privilégiée |
| **Compatibilité** | **Excellente** | **Faible** | **Excellente** |

### 16.3 Comparaison avec la conteneurisation *(ajout)*

| | **Machine virtuelle (hyperviseur)** | **Conteneur (virtualisation par l'OS)** |
|---|---|---|
| **OS invité** | **Un OS complet par VM** (différents possibles) | **Partage le noyau de l'hôte** (même OS) |
| **Poids / démarrage** | **Lourd**, démarrage plus lent | **Léger**, démarrage très rapide |
| **Isolation** | **Forte** (matérielle) | **Plus faible** (niveau noyau) |
| **Surcharge** | Plus élevée | **Très faible ou nulle** |
| **Exemples** | VMware, Hyper-V, KVM, VirtualBox | Docker, LXC, Solaris Zones |

---

## 17. Avantages de la virtualisation

### 17.1 Optimisation de l'infrastructure et réduction des coûts

**Rendement d'un serveur** (graphique du cours) :
- **Sans virtualisation :** une seule charge de travail → **le serveur est utilisé à une petite fraction** de ses ressources disponibles.
- **Avec virtualisation :** **plusieurs charges de travail** (EV 1, EV 2, EV 3 = environnements virtuels) **se partagent** le serveur → **utilisation bien plus élevée**.

| Réduction | Explication |
|---|---|
| **Des coûts matériels** | **Moins de matériel** nécessaire |
| **De la facture énergétique** | **Moins de consommation électrique** et de **climatisation** |
| **Du TCO** (*Total Cost of Ownership*) | **Coût total de possession** |
| **Optimisation du ROI** (*Return on Investment*) | **Retour sur investissement** |

### 17.2 Amélioration de la qualité de service

| Apport | Explication |
|---|---|
| **Moins d'indisponibilité** des applications critiques | Les **fonctionnalités avancées** de la virtualisation **réduisent les interruptions** |
| **Approvisionnement instantané** | Mise en service d'un serveur **en quelques minutes**, par exemple **par clonage** d'une VM |
| **Répartition de charge dynamique** | Chaque application reçoit **les ressources dont elle a besoin**, même en cas de **forte activité** |
| **Mise en place de PRA** | Le système entier est géré comme des **fichiers indépendants du matériel** : le **Plan de Reprise d'Activité** est **facilité** |

### 17.3 Simplification de l'administration

```
   Réduction du nombre d'équipements
 + Sauvegarde instantanée par snapshot
 + Homogénéité de l'infrastructure
 = Moins de maintenance · simplification du travail · meilleure maîtrise de l'infrastructure
```

---

## 18. Remarques sur le support de cours

Quelques points à connaître pour éviter la confusion :

1. **« 3 méthodes » alors que 4 sont listées** (diapositive 85) : traduction binaire, paravirtualisation, assistée par matériel, **et** basée sur l'OS.
2. **x86** : le support dit que la série « a commencé avec le **80286** (1982) » en remplaçant les **8086/8088**. En réalité, la famille x86 **commence avec le 8086 (1978)** ; le **80286** est son **successeur**. *(Précision ajoutée.)*
3. **« LCX »** doit s'écrire **LXC** (Linux Containers).
4. **Type 1 vs type 2 :** les affirmations (« type 2 : pas de sécurité », « type 1 hautement sécurisé ») sont **des généralisations** : un type 2 n'est pas « sans sécurité », il est simplement **moins adapté** à un datacenter. **KVM** est classé en type 1 (c'est un module du noyau Linux : cas souvent **discuté**).
5. **Paravirtualisation :** la description (« un OS contrôle le matériel, les autres passent par lui ») correspond au **modèle Xen** (OS « privilégié » Dom0). Plus généralement, la paravirtualisation signifie que **l'OS invité est modifié pour coopérer avec l'hyperviseur** (*hypercalls*).
6. **Diapositive de paravirtualisation dupliquée** (diapositives 88 et 89 identiques).
7. **« Virtualisation assistée par le système d'exploitation »** : à comprendre comme **virtualisation au niveau du système d'exploitation** (conteneurs).
8. **Diapositive « Comparaison-synthèse »** : deux diapositives (schéma des anneaux, puis tableau), **en anglais** ; traduites ici.
9. **Test de connaissance et questions de réflexion** : **sans réponse** dans le support ; les réponses proposées sont **des ajouts**.
10. **Typographie / OCR :** plusieurs mots sont déformés dans le PDF (« virtualisaEon », « ahribuer », « Cehe ») ; sans incidence.
11. **Chiffre « 5 à 15 % »** d'utilisation des serveurs : **ordre de grandeur historique** (selon les études, souvent 10-20 %).
12. **Titre du fichier** : « Partie 1 – Virtualisation » ; il s'agit du **chapitre 1** d'un cours dont la **partie 2** est « Cloud Computing ».

---

## 19. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Origine** | **Mainframes des années 1960** (division logique des ressources) |
| **Problèmes initiaux** | **Prolifération de serveurs** · **sous-utilisation (5-15 %)** · rigidité · coût · énergie |
| **Virtualisation** | **Abstraction** des ressources : **un matériel → plusieurs VM** |
| **3 composants** | **Invité (guest) · Couche de virtualisation (hyperviseur) · Hôte (host)** |
| **VM** | **Ordinateur virtuel** avec **son propre OS** ; **isolée** |
| **Hyperviseur (VMM)** | **Logiciel** qui **alloue** CPU, mémoire, E/S aux OS invités |
| **4 caractéristiques** | **Compatibilité · Isolation · Encapsulation · Indépendance** |
| **Encapsulation** | **État d'une VM = un fichier** → copie, migration, snapshot |
| **Type 1** | **Bare metal**, **directement sur le matériel**, **datacenter** : Xen, ESXi, Hyper-V, KVM |
| **Type 2** | **Hosted**, **au-dessus d'un OS**, **poste client** : VirtualBox, VMware Workstation, QEMU |
| **5 domaines** | **Serveur · Réseau · Poste de travail · Application · Stockage** |
| **Serveur** | **1 serveur physique → plusieurs serveurs virtuels** ; risques : **VM sprawl, point unique de défaillance, licences** |
| **Poste de travail** | **VDI · RDS · DaaS** ; VDI **persistant / non persistant** |
| **Application** | **Package application + environnement**, **indépendant de l'OS** : ThinApp, App-V |
| **Réseau** | **NV** (overlay) · **SDN** (plan de contrôle ≠ plan de données) · **NFV** (fonctions réseau en logiciel) |
| **SDN vs NFV** | *Software in the network* vs *The network in software* |
| **Stockage** | Pool de disques → **volumes virtuels** |
| **RAID** | **0** (bandes, sans tolérance) · **1** (miroir) · **5** (parité répartie) · **10 / 0+1** (hybrides) |
| **DAS / NAS / SAN** | **Direct** / **Fichier sur réseau (SMB/CIFS)** / **Réseau de blocs (SCSI, FC, iSCSI)** |
| **LUN** | **Unité logique** d'un SAN affectée à un serveur |
| **iSCSI** | **SCSI sur TCP/IP**, **port 3260** |
| **Anneaux x86** | **Ring 3** applications · **Ring 1-2** pilotes · **Ring 0** noyau |
| **Virtualisation complète** | **Traduction binaire + exécution directe** ; invité **non modifié** et **inconscient** |
| **Paravirtualisation** | **OS invité modifié**, **hypercalls** ; dépendance entre OS |
| **Assistée par matériel** | **Instructions CPU dédiées** (Intel VT-x, AMD-V) ; invité **non modifié** |
| **Par l'OS (conteneurs)** | **Sans hyperviseur**, **même noyau**, **très léger** : Docker, LXC, Solaris Zones |
| **Avantages** | **Coûts** (matériel, énergie, TCO, ROI) · **qualité de service** (clonage, répartition de charge, PRA) · **administration** (snapshots) |

### Questions d'examen typiques
1. **Retracer l'évolution** : système centralisé → système réparti → virtualisation.
2. Quels **problèmes** la virtualisation résout-elle ? Pourquoi les serveurs sont-ils **sous-exploités** ?
3. **Définir la virtualisation**, une **VM** et un **hyperviseur** ; citer les **3 composants**.
4. Expliquer les **quatre caractéristiques** (compatibilité, isolation, encapsulation, indépendance).
5. **Comparer hyperviseurs de type 1 et de type 2** (position, performance, usage, coût) avec des exemples.
6. Citer les **cinq domaines** de la virtualisation.
7. **Virtualisation de serveurs :** principe, avantages et **inconvénients** (VM sprawl, point unique de défaillance…).
8. Expliquer **VDI, RDS et DaaS** ; différence **persistant / non persistant**.
9. **Virtualisation d'applications :** principe, avantages, exemples ; la comparer à celle des postes de travail.
10. **Définir NV, SDN et NFV** et les **comparer**.
11. **RAID :** décrire les niveaux **0, 1, 5, 10** (principe, tolérance aux pannes).
12. Différence entre **DAS, NAS et SAN** ; rôle des **LUN**, du **Fibre Channel** et de **iSCSI**.
13. **Anneaux de privilèges x86 :** pourquoi posent-ils problème à la virtualisation ?
14. Comparer **virtualisation complète, paravirtualisation et assistée par matériel** (génération, performance, modification de l'OS invité, compatibilité).
15. Qu'est-ce que la **virtualisation par conteneurs** ? Comparer avec une **VM**.
16. Citer les **avantages économiques** (TCO, ROI) et les apports en **qualité de service** (PRA, snapshot, clonage).
17. **Pourquoi la virtualisation est-elle la base du cloud computing ?**

---

*Fin du résumé — Chapitre 1 : Virtualisation.*
