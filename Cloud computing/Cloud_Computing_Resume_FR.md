# Cloud Computing — Résumé complet du cours
**Cours :** Cloud Computing (les diapositives sont intitulées « Chapitre 2 » dans le PDF ; « Partie 2 »)
**Auteurs des diapositives :** Dr Afef Bohli & Dr Naourez Mejri
**Type de document :** Résumé d'étude avec explications, tableaux comparatifs et conseils d'examen

---

## Table des matières

1. [Introduction et vocabulaire](#1-introduction-et-vocabulaire)
2. [Origines et historique](#2-origines-et-historique)
3. [Le cloud est-il vraiment nouveau ?](#3-le-cloud-est-il-vraiment-nouveau-)
4. [Définitions clés](#4-définitions-clés)
5. [IT classique vs Cloud](#5-it-classique-vs-cloud)
6. [La définition du NIST et le point de vue économique](#6-la-définition-du-nist-et-le-point-de-vue-économique)
7. [Les trois piliers du modèle Cloud](#7-les-trois-piliers-du-modèle-cloud)
8. [Les caractéristiques essentielles (5)](#8-les-caractéristiques-essentielles-5)
9. [Approvisionnement de ressources](#9-approvisionnement-de-ressources)
10. [Virtualisation](#10-virtualisation)
11. [Multi-tenancy (multi-locataires)](#11-multi-tenancy-multi-locataires)
12. [Modèles de services : SaaS, PaaS, IaaS](#12-modèles-de-services--saas-paas-iaas)
13. [Modèles de déploiement](#13-modèles-de-déploiement)
14. [Architecture de référence et acteurs du Cloud](#14-architecture-de-référence-et-acteurs-du-cloud)
15. [SLA — Service Level Agreement](#15-sla--service-level-agreement)
16. [Avantages et inconvénients](#16-avantages-et-inconvénients)
17. [Sécurité du cloud](#17-sécurité-du-cloud)
18. [Conclusion](#18-conclusion)
19. [Fiche de révision rapide](#19-fiche-de-révision-rapide)

---

## 1. Introduction et vocabulaire

Le terme anglais *cloud computing* est très utilisé. En français, on trouve aussi :

- *informatique en nuage* / *informatique dans le nuage*
- *informatique virtuelle*
- *informatique dématérialisée*
- *stockage dans les nuages* / *stockage à distance*
- *nuagique*
- *infonuagique* (Québec)

Tous ces termes désignent la même idée : utiliser des ressources informatiques (calcul, stockage, applications) **hébergées à distance et accessibles via Internet**.

> Le cours commence par la question « Utilisez-vous le cloud computing ? » — la réponse est presque toujours **oui** (Gmail, Google Drive, Dropbox, Office 365, Facebook, ...).

---

## 2. Origines et historique

### Les trois « ruptures » de l'informatique

| Décennie | Rupture | Ce qui a changé |
|---|---|---|
| **1990** | Le **PC** (ordinateur personnel) | Le multi-fenêtrage (Windows) et la souris |
| **2000** | **Internet** (le Web, la toile) | La connectivité mondiale |
| **2010** | Le **Cloud Computing** (le nuage informatique) | L'informatique « dans le nuage » |

### Chronologie historique

| Année | Événement |
|---|---|
| **1960** | John McCarthy : *« computation may someday be organized as a public utility »* — la vision d'origine |
| **1990** | **Grid computing** : accéder à une puissance de calcul aussi simplement qu'à un réseau électrique |
| **1999** | **Salesforce.com** — pionnier ; livre des applications d'entreprise (CRM) via un simple site web |
| **2002** | **Amazon Web Services (AWS)** — services de stockage et de calcul |
| **2006** | **Amazon EC2** (Elastic Compute Cloud) — service web commercial permettant aux petites entreprises et aux particuliers de louer des ordinateurs pour exécuter leurs propres applications |
| **2006** | **Google Docs** |
| **2009** | Tournant décisif : arrivée d'applications d'entreprise basées sur un navigateur (Google Apps, etc.) |
| **2009** | **Microsoft Windows Azure** — Microsoft entre dans le cloud computing |
| **2010** | Annonce d'**Office 365** |

---

## 3. Le cloud est-il vraiment nouveau ?

| **NON** (pas nouveau) | **OUI** (nouveau) |
|---|---|
| La *technologie* n'est pas nouvelle | Nouveau **modèle de consommation et de prestation de service** |
| « Cloud Computing » est un mot à la mode (**marketing**) pour reconditionner d'anciennes technologies qui existent depuis des décennies | Le **self-service** et l'**auto-gestion** sont nouveaux |

**À retenir :** les *technologies* sont anciennes (virtualisation, grilles, hébergement) ; le *modèle économique* (à la demande, paiement à l'usage, libre-service) est la vraie innovation.

---

## 4. Définitions clés

| Terme | Signification |
|---|---|
| **Grid computing** (calcul en grille) | Environnements de ressources mises en commun pour l'exécution de **tâches de calcul** (ex. traitement d'images) plutôt que de processus de longue durée (serveur web, serveur de mail) |
| **Utility computing** (informatique utilitaire) | Environnements de ressources mises en commun pour l'hébergement de **processus de longue durée** ; se concentre sur les **niveaux de service** avec la **quantité optimale de ressources** |
| **Cloud computing** | Variété de services disponibles sur Internet offrant des fonctionnalités de calcul sur l'**infrastructure du prestataire**. L'infrastructure peut être hébergée sur une grille ou un environnement utilitaire, mais cela est **sans importance pour l'utilisateur**. Les données dans le cloud sont souvent une part importante du service (« data inside », comme « Intel inside »). |

### Le cloud computing nécessite 3 composants principaux
1. **Clients légers** (équipements légers / navigateur)
2. **Grid computing** — relier des équipements pour former une grande infrastructure et exploiter les ressources non utilisées
3. **Utility computing** — **paiement à l'utilisation** sur des serveurs partagés

---

## 5. IT classique vs Cloud

| Critère | IT classique | Cloud |
|---|---|---|
| Approvisionnement | **Manuel** | **Automatique** |
| Matériel | **Dédié** | **Partagé** |
| Capacité | **Fixe** | **Élastique** |
| Paiement | Payer pour la **capacité** | Payer pour l'**utilisation** |
| Frais | Frais de ressources **et** d'exploitation (investissement lourd, CAPEX) | Frais d'**exploitation** uniquement (OPEX) |
| Gestion | Par les **administrateurs système** | Via des **APIs** |

---

## 6. La définition du NIST et le point de vue économique

### Définition du NIST (référence officielle)
> Le Cloud Computing est un **modèle** permettant un **accès simple, à la demande**, à un **ensemble de ressources informatiques partagées et configurables**.

- Cela signifie la **délocalisation** de l'infrastructure informatique.
- **Ressources** = équipements réseau, serveurs, stockage, applications et services.
- Elles peuvent être **rapidement approvisionnées et libérées** avec un **effort de gestion minimal** ou une interaction minimale avec le prestataire.

### Point de vue économique
- Le cloud computing est essentiellement une **offre commerciale d'abonnements économiques à des services externes**.
- **Basculement de tendance :** au lieu d'obtenir de la puissance de calcul par *acquisition* de matériel et de logiciel, le consommateur se sert de la puissance **mise à disposition par un ou plusieurs fournisseurs via Internet**.

---

## 7. Les trois piliers du modèle Cloud

Le cours organise le modèle cloud en trois familles :

1. **Caractéristiques essentielles** (ce qui fait qu'un service est « cloud »)
2. **Modèles de services** (SaaS, PaaS, IaaS — *ce qui* est offert)
3. **Modèles de déploiement** (privé, public, hybride, communautaire — *où* et *pour qui*)

---

## 8. Les caractéristiques essentielles (5)

| # | Caractéristique | Signification |
|---|---|---|
| 1 | **Accès en libre-service à la demande** | Le consommateur alloue des ressources **automatiquement**, **sans interaction humaine** avec le fournisseur |
| 2 | **Accès ubiquitaire au réseau** | Disponible **de n'importe où** avec une connexion Internet, avec **n'importe quelle plateforme** (PC, téléphone, tablette) |
| 3 | **Mise en commun des ressources** | Les ressources sont extraites d'un **réservoir commun** |
| 4 | **Élasticité rapide** | Les ressources **augmentent et diminuent rapidement** selon la demande du client — **totalement automatique** |
| 5 | **Service mesuré en permanence** | Les services sont **facturés** ; les utilisateurs **paient seulement ce qu'ils utilisent** ; les services peuvent être **annulés à tout moment** |

---

## 9. Approvisionnement de ressources

L'approvisionnement consiste à faire correspondre la **capacité** disponible à la **demande** réelle. Les diapositives utilisent quatre graphiques (courbe de demande vs ligne de capacité) :

| Situation | Ce qui se passe | Conséquence |
|---|---|---|
| **Approvisionnement** (capacité ≈ pic de demande) | La capacité est fixée au pic | Grande zone de **ressources non utilisées** |
| **Sur-approvisionnement** | Capacité très supérieure à la demande | **Sous-utilisation** → argent gaspillé |
| **Sous-approvisionnement** | Capacité inférieure aux pics de demande | **Perte de revenus** et **utilisateurs perdus** (ils partent si le service est lent ou indisponible) |
| **Cloud** | La capacité **suit la courbe de demande** (élasticité) | Presque **aucun gaspillage** et aucune pénurie |

**Idée clé :** avec l'IT classique, il faut **deviner** la demande future ; avec le cloud, l'**élasticité** adapte automatiquement la capacité, évitant à la fois le gaspillage (sur-approvisionnement) et la perte de clients (sous-approvisionnement).

---

## 10. Virtualisation

### Définition
**Virtualisation = abstraction des ressources informatiques :** technique qui **cache les caractéristiques physiques** des ressources informatiques à la manière dont les systèmes, les applications ou les utilisateurs finaux interagissent avec elles.

> C'est un **ingrédient indispensable** du Cloud Computing.

### Trois objectifs

| Objectif | Signification |
|---|---|
| **Abstraction** | Simplifier l'utilisation de la ressource sous-jacente |
| **Réplication** | Créer **plusieurs instances** de la ressource |
| **Isolation** | Séparer les utilisations des clients vis-à-vis des ressources sous-jacentes (et des autres clients) |

---

## 11. Multi-tenancy (multi-locataires)

| Tenant unique | Multi-tenant |
|---|---|
| Chaque client a **sa propre instance de logiciel** | **Plusieurs clients (locataires)** partagent la **même application**, sur le **même matériel**, avec le **même mécanisme de stockage de données** |
| Nécessite un **ensemble dédié de ressources** pour un seul client | Analogue à des utilisateurs exécutant diverses applications sur le même système d'exploitation |
| Plus coûteux | **Les coûts de gestion et de matériel sont partagés** entre les locataires |

**Points clés**
- Le multi-tenant est la **technologie fondamentale** que le cloud utilise pour partager les ressources informatiques **de manière rentable et en toute sécurité** entre locataires (entreprises, organisations, ...).
- Le cloud utilise la **virtualisation pour isoler les tenants**.
- Un tenant **ne peut pas accéder aux données des autres tenants**.

---

## 12. Modèles de services : SaaS, PaaS, IaaS

### Pour qui ?

| Modèle | Utilisateur cible | Son besoin |
|---|---|---|
| **SaaS** — Software as a Service | **Utilisateur final** | Seulement *utiliser* l'application |
| **PaaS** — Platform as a Service | **Développeur d'applications** | Une API/plateforme **sans implémenter** leur fonctionnement interne |
| **IaaS** — Infrastructure as a Service | **Administrateur système** | Pas besoin d'**acheter des machines** s'il peut les **louer** |

### La pile : qui gère quoi ?

| Couche | Sur site (on-premises) | IaaS | PaaS | SaaS |
|---|---|---|---|---|
| Applications | Vous | Vous | Vous | **Fournisseur** |
| Données | Vous | Vous | Vous | **Fournisseur** |
| Runtime (environnement d'exécution) | Vous | Vous | **Fournisseur** | **Fournisseur** |
| Middleware | Vous | Vous | **Fournisseur** | **Fournisseur** |
| Système d'exploitation | Vous | Vous | **Fournisseur** | **Fournisseur** |
| Virtualisation | Vous | **Fournisseur** | **Fournisseur** | **Fournisseur** |
| Serveurs | Vous | **Fournisseur** | **Fournisseur** | **Fournisseur** |
| Stockage | Vous | **Fournisseur** | **Fournisseur** | **Fournisseur** |
| Réseau | Vous | **Fournisseur** | **Fournisseur** | **Fournisseur** |

> L'analogie « **Pizza as a Service** » utilisée dans le cours :
> - **Sur site** = *Fait maison* (vous faites tout)
> - **IaaS** = *Take & Bake* (vous achetez la pâte, vous cuisez à la maison)
> - **PaaS** = *Pizza livrée* (vous fournissez seulement la table, les boissons...)
> - **SaaS** = *Dîner au restaurant* (le restaurant fait tout)

### Contrôle vs rentabilité
- **IaaS** → **plus de contrôle**, moins de réduction des coûts
- **SaaS** → **moins de contrôle**, **plus de réduction des coûts**
- **PaaS** → position intermédiaire

### 12.1 SaaS — Software as a Service
- **Premier** service cloud, le **plus utilisé et connu**.
- Applications livrées **à la demande** aux utilisateurs finaux sur Internet, généralement via un **navigateur**.
- Le service comprend **le logiciel, le matériel et le support**.
- **Mises à jour, correctifs et maintenance** sont effectués par le **fournisseur**, pas par l'utilisateur final.
- Les utilisateurs accèdent au service via un dispositif autorisé.
- **Exemples :** Gmail, Facebook, Dropbox, Office 365, Picasa.

### 12.2 PaaS — Platform as a Service
- **Plateforme de développement** destinée aux développeurs : outils et services pour rendre le **codage et le déploiement de SaaS rapides et efficaces**.
- Fournit tout le nécessaire pour **construire et livrer des applications et services web**, le tout disponible sur Internet.
- Délivre une plateforme informatique incluant **système d'exploitation, environnement d'exécution du langage, base de données et serveur web**.
- Permet de créer des applications web **rapidement, sans la complexité d'acheter et de maintenir** logiciels et infrastructure → **développement rapide à faible coût**.
- **Exemples :** Google App Engine, OpenShift (Red Hat), Bluemix (IBM), Azure Cloud (Microsoft), Heroku.

**Caractéristiques du PaaS**
- Services pour **développer, tester, déployer, héberger et maintenir** des applications dans le même **IDE**.
- **Outils web de création d'interface utilisateur** pour créer, modifier, tester et déployer différents scénarios d'UI.
- **Architecture multi-locataire** : plusieurs utilisateurs simultanés utilisent la même application de développement.
- **Évolutivité intégrée** des logiciels déployés, y compris **équilibrage de charge et basculement (failover)**.
- **Intégration** aux services web et bases de données via des normes communes.
- Outils pour gérer la **facturation et les abonnements**.

### 12.3 IaaS — Infrastructure as a Service
- Délivre l'infrastructure cloud — **serveurs, stockage, réseaux et systèmes d'exploitation — comme un service à la demande**.
- **Service entièrement externalisé :** plutôt que d'acheter serveurs, logiciels, espace de centre de données ou équipements réseau, les clients **achètent ces ressources à la demande**.
- **Caractéristiques :**
  - Ressources distribuées **en tant que service**
  - Ressources informatiques **évolutives**
  - **Coût variable**, modèle de tarification **de type service public**
- **Exemples :** Amazon EC2, Rackspace, FlexScale, Google Cloud (Compute Engine).

### Synthèse comparative

| | SaaS | PaaS | IaaS |
|---|---|---|---|
| Fournit | Application prête à l'emploi | Plateforme de développement / d'exécution | Serveurs virtuels, stockage, réseau |
| Pour | Utilisateurs finaux | Développeurs | Admins système / équipes IT |
| Vous gérez | Rien (juste utiliser) | Vos applications + données | OS, middleware, runtime, applications, données |
| Exemples | Gmail, Office 365, Dropbox | Heroku, App Engine, Azure | Amazon EC2, Rackspace |

---

## 13. Modèles de déploiement

**Quatre** modèles d'hébergement — à choisir **selon ses propres besoins** :

1. **Cloud privé**
2. **Cloud public**
3. **Cloud hybride**
4. **Cloud communautaire**

### 13.1 Cloud privé
- L'ensemble des ressources est **exclusivement mis à disposition d'un seul client**.
- Une même infrastructure peut accueillir **plusieurs clouds privés virtuels** appartenant à différents utilisateurs, chacun y accédant via **son propre réseau**.
- Peut être géré **par l'entreprise elle-même** (*on-premises*) ou par un **prestataire externe** (*off-premises*, « cloud privé virtuel »).
- Deux formes : **cloud privé interne** et **cloud privé externe**.

| Avantages | Inconvénients |
|---|---|
| **Maîtrise** de l'infrastructure | **Investissements forts** (construction et maintien en condition opérationnelle — *MCO*) |
| Sécurisation **adaptée** à l'entreprise | Nécessité de **maintenir des compétences en interne** |
| **Couplage facilité** au système d'information (SI) | **Élasticité moindre** (par paliers) |
| **Rentabilisation** de l'infrastructure existante | **Scalabilité limitée** (capacity planning) |
| **Faible impact** sur les équipes | **Standardisation** des technologies requise, parfois difficile |

### 13.2 Cloud public
- Les utilisateurs accèdent aux services cloud via l'**Internet public**, **sans savoir précisément où** sont hébergées leurs données ni où sont exécutés leurs traitements.
- Les ressources et bases de données peuvent être hébergées dans **n'importe quel datacenter** du prestataire et **passer d'un datacenter à l'autre** pour optimiser les capacités du prestataire.
- C'est le **modèle phare** du cloud ; idéal notamment pour les sites institutionnels.

| Avantages | Inconvénients |
|---|---|
| **Tarification avantageuse** (mutualisation, pas de coût de setup) | **Contraintes de l'externalisation** : contractualisation, sécurité, réversibilité, impact organisationnel |
| **Forte élasticité** (paiement à l'usage) | **Couplage sécurisé** au SI difficile |
| **Scalabilité infinie** | **Personnalisation inexistante** |

### 13.3 Cloud communautaire
- Permet à **plusieurs entreprises ou organisations de partager des ressources** cloud **exclusivement dédiées** à ces organisations.
- Géré par les **organisations membres** ou par un **prestataire externe**.
- Offre la sécurité et les ressources dédiées d'un cloud privé, **à moindre coût**, avec un gage d'**indépendance** vis-à-vis d'un prestataire de cloud public.
- En général, il s'agit de sociétés d'un **même groupe**.

| Avantages | Inconvénients |
|---|---|
| Adaptation aux besoins du client (**objectifs communs**) | **Maintien des compétences** en interne |
| **Réduction des coûts** par la mutualisation | **Pérennisation** de la structure ? |
| **Mise en commun** des compétences | |

### 13.4 Cloud hybride
- **Associe des clouds privés et publics** (et des infrastructures).
- Une partie des données/infrastructures est gérée **en interne** (dans ses locaux ou chez un prestataire) et **communique avec des ressources cloud**.
- Permet de **différencier le lieu de traitement** des données : les **données sensibles/stratégiques** restent **dans les murs de l'entreprise**, les autres vont vers un **cloud public** plus rentable et plus performant.
- Le cloud public peut aussi **absorber un pic d'activité** lorsque les capacités de l'entreprise sont dépassées (*cloud bursting*).
- Variantes : hybride **privé-public** et hybride **privé-privé**.

| Avantages | Inconvénients |
|---|---|
| **Réversibilité simplifiée** | **Compatibilité technique** à garantir |
| **Scalabilité infinie** | **Adhérence technique** entre les deux types de cloud complexe à maintenir |
| **Tarification avantageuse** | |
| **Rentabilisation** de l'infrastructure existante | |

### Comparaison des modèles de déploiement

| Critère | Privé | Public | Communautaire | Hybride |
|---|---|---|---|---|
| Utilisateurs | Un seul client | Tout le monde | Groupe d'organisations | Mixte |
| Contrôle / sécurité | ★★★ | ★ | ★★ | ★★ (selon le placement des données) |
| Coût | Élevé | Faible | Moyen | Moyen |
| Élasticité | Limitée | Forte | Moyenne | Forte |
| Personnalisation | Élevée | Aucune | Moyenne | Moyenne |

*(Les étoiles sont ma lecture simplifiée des avantages/inconvénients des diapositives, pour réviser rapidement.)*

### Exemples (diapositive « Exemples »)

| | **Privé** | **Ouvert / Public** |
|---|---|---|
| **Interne** | SaaS : espaces collaboratifs · PaaS : développement web (ViFiB) · IaaS : serveurs virtuels à la demande (ViFiB, VMware) | SaaS : CMS · PaaS : Free Cloud Alliance · IaaS : IBM, VMware, Free Cloud Alliance |
| **Externe** | SaaS : Google Apps, Salesforce CRM, Amazon · PaaS : Oracle PaaS · IaaS : location d'infrastructures virtuelles (OVH, Amazon) | SaaS : Facebook, LinkedIn, Google+ · PaaS : Windows Azure, plateforme mobile (Kawet) · IaaS : Google Drive, Dropbox, SFR, Orange |

---

## 14. Architecture de référence et acteurs du Cloud

L'architecture de référence (de type NIST) décrit l'**écosystème des acteurs du cloud**.

### 14.1 Les cinq acteurs

| Acteur | Rôle |
|---|---|
| **Cloud Consumer** (consommateur) | **Consomme** les services fournis par les providers et/ou les brokers |
| **Cloud Provider** (fournisseur) | **Rend les services disponibles** : déploiement et mise à disposition ; **orchestration** des services sur son IT interne ; **gestion et support** pour les clients ; **sécurité et confidentialité** |
| **Cloud Auditor** (auditeur) | **Organisme indépendant** qui évalue les providers sur la **performance**, la **sécurité** et le **respect des contraintes légales** (vie privée, ...) |
| **Cloud Broker** (courtier) | **Intermédiaire** entre le provider et le consumer (voir ci-dessous) |
| **Cloud Carrier** (transporteur) | Fournit la **connectivité/le transport** des services : via un réseau public, une liaison sécurisée ou dédiée, ou un transport physique ; s'engage sur un **niveau de service** auprès du consumer ou du provider |

### 14.2 L'intérieur du Cloud Provider (couches)

```
 Couche de services :     SaaS
                           PaaS
                            IaaS
 Couche d'abstraction et de contrôle des ressources  (virtualisation OS / réseau / stockage — hyperviseur)
 Couche des ressources physiques                     (Matériel : serveurs, switches, baies de disques + Infrastructure)

 Transversal :  Orchestration des services · Gestion des services cloud
                (Support métier, Provisioning/Configuration, Portabilité/Interopérabilité)
                Sécurité · Confidentialité
```

Correspondance du **modèle NIST** avec des services types :

| Couche NIST | Exemples de services |
|---|---|
| **SaaS** | Messagerie, bureautique collaborative, CRM, ERP |
| **PaaS** | SGBD, serveur d'applications, IAM (gestion des identités et des accès) |
| **IaaS** | Stockage, serveur logique, services réseau ; virtualisation OS/réseau/stockage |
| Abstraction des ressources | Couche de virtualisation (hyperviseur, réseau virtuel, stockage virtuel) |
| Ressources physiques | Serveurs, switches, baies de disques (matériel / infrastructure) |

### 14.3 Le Cloud Broker — trois fonctions

| Fonction | Signification |
|---|---|
| **Intermédiation** (médiation) | Améliorer/adapter le service aux **spécificités du consumer** |
| **Agrégation** | **Combiner plusieurs services** pour en fournir un nouveau **à valeur ajoutée** |
| **Arbitrage** | **Choisir de façon opportuniste** les services selon des critères (performance, coûts, ...) — ex. niveaux Initial / Bronze / Gold |

### 14.4 Le Cloud Auditor — trois audits

| Audit | Porte sur |
|---|---|
| **Audit de sécurité** | Localisation, chiffrement, sécurité des locaux, configurations réseau, OS, ... |
| **Audit d'impact sur la vie privée** | Confidentialité des données, sujets comme PRISM, gag order, offshore, ... |
| **Audit de performance** | Latence, capacité de stockage, capacité de calcul, disponibilité, qualité de service, ... |

---

## 15. SLA — Service Level Agreement

- Un **SLA** est la **formalisation d'un accord négocié entre deux parties** — un **contrat entre clients et fournisseurs**.
- Il **met par écrit** les attentes des parties sur :
  - le **contenu** des prestations,
  - leurs **modalités d'exécution**,
  - les **responsabilités** des parties,
  - les **garanties**, c'est-à-dire la **qualité de service**.
- Il peut spécifier les niveaux de **disponibilité**, de **performance**, d'**opération** ou tout autre attribut (ex. la **facturation**), voire les **pénalités** (financières ou autres) en cas de manquement.

---

## 16. Avantages et inconvénients

### Avantages

| Avantage | Explication |
|---|---|
| **Réduction des coûts** | Baisse du **coût total de possession (TCO)** ; facilité d'augmenter ou diminuer les ressources |
| **Souplesse / flexibilité** | S'adapte rapidement aux besoins |
| **Moins d'efforts de gestion IT** | Le fournisseur gère l'infrastructure |
| **Recentrage sur le cœur de métier** | L'entreprise se concentre sur ce qu'elle fait de mieux |
| **Évolutivité** | Augmenter la capacité avec la croissance |
| **Élasticité** | Montée/descente en charge automatique |
| **Mobilité / accessibilité** | Accès de n'importe où |

### Inconvénients

**1. Perte de la maîtrise de son informatique** (confiée à un ou plusieurs tiers)
- On ne peut pas toujours **exporter les données** d'un service cloud ; la **réversibilité** (ou les coûts de sortie associés) n'est pas toujours prise en compte dans le projet.
- Le client est souvent **« piégé »** par son prestataire : changement des termes du contrat ou des CGU, augmentation du prix, besoin d'accéder à ses données en local... → **enfermement propriétaire** (*vendor lock-in*).
- **Richard Stallman** (initiateur du projet GNU) considère l'informatique dans le nuage comme **« un piège »** : les utilisateurs perdent le contrôle de leurs applications. Il y voit un concept publicitaire sans intérêt, rejoignant **Larry Ellison** (fondateur d'Oracle), pour qui il s'agit d'un **phénomène de mode**.

**2. Problèmes de sécurisation des données** (voir section suivante).

---

## 17. Sécurité du cloud

Principales préoccupations listées dans le cours :

| Domaine | Question / risque |
|---|---|
| **Gestion des identités** | Gestion des identifiants et des accès |
| **Confidentialité** | Secret, sécurité et confidentialité des données |
| **Sécurité physique et du personnel** | Sécurité du datacenter et des équipes |
| **Perte de maîtrise** | Informatique confiée à des tiers |
| **Confiance dans le prestataire** | Dépendance ou liberté ? **Pérennité** de l'hébergeur ? |
| **Disponibilité** | Garantie à 99 % ? À combien ? Quel contrat ? |
| **Maturité des applications** | Certaines applications sont peu ou pas adaptées à l'interface web |
| **Sécurité des applications** | Risques face aux **cyberattaques** |
| **Contrôle des applications** | Qui contrôle ce qui s'exécute ? |
| **Questions juridiques** | **Localisation des données ou du datacenter**, dépendante des législations territoriales |
| **Aspects contractuels** | Les clauses concernent principalement **disponibilité, sécurité, confidentialité et support** |

> 💡 **Astuce :** une disponibilité de 99 % paraît élevée mais autorise environ **3,65 jours d'indisponibilité par an**. 99,9 % autorise ~8,8 heures/an. C'est pourquoi le chiffre du **SLA** compte.

---

## 18. Conclusion

- Le cloud computing est un **acteur important de l'industrie des IT**.
- Une **véritable valeur commerciale** peut être réalisée par des clients de **toutes dimensions**.
- Les solutions de cloud computing sont **simples à acquérir**, ne nécessitent **pas de contrats à long terme** et sont plus faciles à **faire évoluer ou diminuer** selon les besoins.
- Des services de **planification et de migration** adéquats sont nécessaires pour assurer une **mise en œuvre réussie**.
- Les clouds **publics et privés peuvent être déployés ensemble** pour exploiter le meilleur des deux (hybride).
- Les **services de surveillance** assurent que les clients obtiennent le meilleur de leur environnement cloud.

---

## 19. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| Cloud (NIST) | Accès à la demande à un ensemble de ressources partagées et configurables, effort de gestion minimal |
| Technologie non nouvelle | C'est le **modèle** (libre-service, paiement à l'usage) qui est nouveau |
| 5 caractéristiques | Libre-service à la demande · Accès réseau ubiquitaire · Mise en commun des ressources · Élasticité rapide · Service mesuré |
| Virtualisation | Abstraction + Réplication + Isolation |
| Multi-tenancy | Plusieurs clients partagent une application/un matériel, isolés les uns des autres |
| SaaS | Logiciel prêt à l'emploi → utilisateurs finaux (Gmail, Office 365) |
| PaaS | Plateforme de dev → développeurs (Heroku, App Engine, Azure) |
| IaaS | Infrastructure virtuelle → admins système (EC2, Rackspace) |
| Cloud privé | Un seul client ; contrôle maximal, coût élevé |
| Cloud public | Partagé ; peu cher, scalabilité infinie, pas de personnalisation |
| Cloud communautaire | Groupe d'organisations avec objectifs communs |
| Cloud hybride | Privé + public ; données sensibles en interne, pics sur le public |
| 5 acteurs | Consumer · Provider · Auditor · Broker · Carrier |
| Fonctions du broker | Intermédiation · Agrégation · Arbitrage |
| Audits de l'auditor | Sécurité · Impact vie privée · Performance |
| SLA | Contrat définissant qualité de service, responsabilités, pénalités |
| Risque principal | **Vendor lock-in**, sécurité, juridique (localisation des données), disponibilité |
| Sur-approvisionnement | Ressources gaspillées (trop de capacité) |
| Sous-approvisionnement | Perte de revenus et d'utilisateurs (pas assez de capacité) |

### Questions d'examen typiques
1. Définir le cloud computing (NIST) et expliquer s'il est « vraiment nouveau ».
2. Citer et expliquer les **5 caractéristiques essentielles**.
3. Différencier **grid computing**, **utility computing** et **cloud computing**.
4. Comparer **SaaS / PaaS / IaaS** : utilisateur cible, ce que gère le fournisseur, exemples.
5. Expliquer la **virtualisation** et le **multi-tenancy** et pourquoi ils sont essentiels au cloud.
6. Comparer les clouds **privé, public, communautaire et hybride** (avantages et inconvénients).
7. Décrire les **cinq acteurs** de l'architecture de référence ; détailler le **Cloud Broker** et le **Cloud Auditor**.
8. Qu'est-ce qu'un **SLA** et que contient-il ?
9. Expliquer **sur-approvisionnement vs sous-approvisionnement** et comment le cloud résout le problème.
10. Discuter des **inconvénients** : vendor lock-in, sécurité, questions juridiques.

---

*Fin du résumé Cloud Computing.*
