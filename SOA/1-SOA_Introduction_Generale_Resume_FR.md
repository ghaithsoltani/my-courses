# SOA — Introduction générale (Architecture Orientée Services)
**Cours :** SOA — Services web
**Support :** adapté par H. Hmida du cours de M. Baron (ISAE-ENSMA)
**Type de document :** Résumé d'étude avec explications, schémas, tableaux comparatifs et conseils d'examen

---

## Table des matières

1. [Ressources bibliographiques](#1-ressources-bibliographiques)
2. [Avant la SOA : les problèmes](#2-avant-la-soa--les-problèmes)
3. [Vers une architecture SOA](#3-vers-une-architecture-soa)
4. [Évolution des applications : monolithique → SOA → microservices](#4-évolution-des-applications--monolithique--soa--microservices)
5. [SOA : généralités et paradigme « Chercher, Publier, Consommer »](#5-soa--généralités-et-paradigme--chercher-publier-consommer-)
6. [Les huit aspects d'un service](#6-les-huit-aspects-dun-service)
7. [Architecture monolithique vs SOA](#7-architecture-monolithique-vs-soa)
8. [Solutions technologiques pour une SOA](#8-solutions-technologiques-pour-une-soa)
9. [Un point sur OSGi](#9-un-point-sur-osgi)
10. [Les services web : réponse à la SOA](#10-les-services-web--réponse-à-la-soa)
11. [Les deux familles de services web](#11-les-deux-familles-de-services-web)
12. [Services web étendus (SOAP)](#12-services-web-étendus-soap)
13. [Services web REST](#13-services-web-rest)
14. [SOAP vs REST : comparaison (ajout)](#14-soap-vs-rest--comparaison-ajout)
15. [Les fournisseurs de services web](#15-les-fournisseurs-de-services-web)
16. [Plateformes de développement](#16-plateformes-de-développement)
17. [Organisation du cours : la métaphore de l'auto-école](#17-organisation-du-cours--la-métaphore-de-lauto-école)
18. [Outils nécessaires](#18-outils-nécessaires)
19. [Remarques sur le support de cours](#19-remarques-sur-le-support-de-cours)
20. [Fiche de révision rapide](#20-fiche-de-révision-rapide)

---

## 1. Ressources bibliographiques

| Ouvrage | Auteur(s) | Éditeur / Édition |
|---|---|---|
| ***SOA Principles of Service Design*** | **Thomas Erl** | Prentice Hall — juillet 2007 — 608 p. |
| ***Le guide de l'architecte du SI*** | **X. Fournier-Morel, P. Grosjean**, … | Dunod — octobre 2006 — 302 p. |
| ***Service-Oriented Architecture (SOA) : Concepts …*** | **Thomas Erl** | Prentice Hall — août 2005 — 792 p. |

> 💡 **Thomas Erl** est la référence mondiale sur la SOA : ses « principes de conception de services » inspirent les huit aspects vus en [section 6](#6-les-huit-aspects-dun-service).

---

## 2. Avant la SOA : les problèmes

### Le découpage classique en 3 tiers
**Présentation – Métier – Données** : il **facilite le travail de la maîtrise d'œuvre (MOE)** mais **favorise le cloisonnement en silos applicatifs indépendants** (**blocs monolithiques**).

### Problèmes

| Problème | Explication |
|---|---|
| **Silos applicatifs** | Chaque application est un **bloc fermé** |
| **Fonctions redondantes** | **Une version de chaque fonction pour chaque application** |
| **Pas de mutualisation** | Peu de **réutilisation** entre projets |

### Conséquences

| Conséquence | Détail |
|---|---|
| **Développement coûteux** | On **redéveloppe** les mêmes fonctions |
| **Interconnexions redondantes** | Une liaison **pour chaque couple d'applications** |
| **Complexité de l'architecture** | Un **réseau de liens** difficile à comprendre |
| **Maintenance difficile**, voire **impossible** | Souvent **remplacement prématuré** (« swap précoce ») |

```
  Application A        Application B        Application C
 ┌────────────┐       ┌────────────┐       ┌────────────┐
 │ Présentation│      │ Présentation│      │ Présentation│
 │ Métier (x) │       │ Métier (x') │      │ Métier (x'')│   ← même fonction, 3 versions
 │ Données    │       │ Données    │       │ Données    │
 └────────────┘       └────────────┘       └────────────┘
        silos indépendants, liens point à point
```

---

## 3. Vers une architecture SOA

### Rappel : une application distribuée
Un ensemble de **composants** qui :
- **collaborent** pour exécuter des tâches communes ;
- sont **distants géographiquement** ;
- sont **interconnectés via un réseau** ;
- sont **hétérogènes**.

### Solutions éprouvées
**DCOM, CORBA, EJB, RMI, .NET Remoting…** *(voir le chapitre RPC)*.

### Leurs faiblesses

| Faiblesse | Explication |
|---|---|
| **Formats de données spécifiques** | Chaque technologie a **sa propre représentation** |
| **Interopérabilité limitée** | Seulement **si les composants utilisent la même solution** |
| **Protocole de transport spécifique** | **Nécessite une configuration réseau** (ports, pare-feu) |

> 🔑 **Idée directrice :** il faut des échanges **indépendants** du langage, de la plateforme et du transport → les **services web** reposant sur les **standards du Web**.

---

## 4. Évolution des applications : monolithique → SOA → microservices

**Exemple fil rouge du cours : une application de covoiturage.**

| Architecture | Description |
|---|---|
| **Monolithique** (« une grosse application ») | **Tout dans un seul bloc** : interfaces web, **connecteurs** (SMS, e-mail, paiement), base de données, API REST/SOAP |
| **Orientée Services (SOA)** | Application **découpée en services techniques** (connecteur SMS, connecteur e-mail, connecteur paiement **PayPal**, interfaces web, base de données, API REST/SOAP) **reliés par un bus d'intégration** : l'**ESB** (*Enterprise Service Bus*) |
| **Microservices** | Évolution de la SOA *(« sera vue plus tard, trop tôt pour une comparaison »)* |

```
 Monolithique              SOA                              Microservices
┌─────────────┐     ┌───SMS──┐ ┌─EMAIL─┐ ┌─PayPal─┐         services très petits,
│ web + SMS + │     └───┬────┘ └───┬───┘ └───┬────┘         autonomes, déployés
│ email + paie│   ═════╧══════════╧═════════╧═════ ESB       séparément, sans bus central
│ + BD + API  │     ┌──web──┐ ┌──BD──┐ ┌─API REST/SOAP─┐
└─────────────┘     └───────┘ └──────┘ └───────────────┘
```

> 💡 **Comparaison anticipée** *(ajout)* :
> | | **Monolithique** | **SOA** | **Microservices** |
> |---|---|---|---|
> | **Découpage** | Aucun | **Services métier/techniques** | **Très fin** (une fonction = un service) |
> | **Communication** | Appels internes | **Messages**, **ESB / orchestration** | **API légères** (REST, messages), pas de bus lourd |
> | **Déploiement** | Tout d'un coup | **Par service** | **Indépendant par service** |
> | **Technologies** | Un seul langage | Hétérogène | **Très hétérogène** |

---

## 5. SOA : généralités et paradigme « Chercher, Publier, Consommer »

### Définition
> **SOA** = *Service Oriented Architecture* = **Architecture Orientée Services**.
> Le **service** (ou **composant**) est le **fondement** de ce **modèle d'interaction entre applications**.

### Le triangle SOA

```
                      Annuaire / Entrepôt
                      ▲                 ▲
          ② Chercher │                 │ ① Publier
   (un contrat est    │                 │ (via le contrat)
    retourné)         │                 │
  Consommateur ──────┘                 └────── Fournisseur
   du service  ──────── ③ Consommer ─────────►  du service
              (messages conformes au contrat)
```

| # | Action | Description |
|---|---|---|
| **1** | **Publier** | Le **fournisseur** publie son service **via le contrat** dans l'annuaire |
| **2** | **Chercher** | Le **consommateur** cherche un service répondant à ses **exigences** ; un **contrat** lui est **retourné** |
| **3** | **Consommer** | Le consommateur **envoie des messages** (en **respectant le contrat**) au fournisseur |

> 🔗 **Lien avec le chapitre WSDL :** le **contrat** est le document **WSDL**, l'**annuaire** est **UDDI**, et les **messages** sont des messages **SOAP**.

---

## 6. Les huit aspects d'un service

> **Définition :** *« Un service est un **composant logiciel distribué**, exposant les **fonctionnalités à forte valeur ajoutée** d'un domaine métier. »* (XEBIA Blog, 2009)

| # | Aspect | En une phrase |
|---|---|---|
| 1 | **Contrat standardisé** | Un accord **explicite** entre fournisseur et consommateur |
| 2 | **Couplage lâche** | Échanges **par messages**, faible dépendance |
| 3 | **Abstraction** | Fonctionnement en **boîte noire** |
| 4 | **Réutilisabilité** | Conçu **pour être réutilisé** |
| 5 | **Autonomie** | Dispose de **tout ce qu'il faut** pour s'exécuter |
| 6 | **Sans état** (*stateless*) | Ne conserve **pas d'état** entre deux appels |
| 7 | **Découvrabilité** | **Trouvable** dans un annuaire |
| 8 | **Composabilité** | **Assemblable** avec d'autres services |

### 6.1 Contrat standardisé

Contrat entre **fournisseur** et **consommateur**. **Trois types** à distinguer :

| Type de contrat | Contenu | Exemple *(ajout)* |
|---|---|---|
| **Syntaxique** | **Opérations**, **messages d'entrée**, **messages de sortie**… | Signature de `addPerson` |
| **Sémantique** | **Règles et contraintes d'usage** | « Le montant doit être positif » |
| **Qualité de service (QoS)** | **Temps de réponse attendu**, **procédures en cas de panne**, **temps de reprise** après interruption | « Réponse en < 2 s, disponibilité 99,9 % » |

S'appuie sur des **standards d'interopérabilité** (exemple : **WSDL**).

### 6.2 Couplage lâche

- L'échange fournisseur ↔ consommateur se fait **à travers des messages** (**couplage lâche** vis-à-vis de l'environnement).
- Un **bus / une orchestration** évite que les services **aient besoin de connaître les autres services** *(point remis en cause dans l'architecture microservices)*.

| | **Couplage fort** | **Couplage lâche** |
|---|---|---|
| **Schéma** | Les services **s'appellent directement** (maillage : S1↔S2↔S5↔S3↔S4) | Un **moteur d'orchestration** appelle les services **1, 2, 3, 4** |
| **Conséquence** | Un changement se **propage partout** | Un service **se remplace** sans toucher les autres |

*(L'orchestration est étudiée dans le dernier cours ; exemple d'outil cité : **OpenESB**.)*

### 6.3 Abstraction

- Le contrat **ne contient que les informations pertinentes à l'invocation**.
- Service en **« boîte noire »** : **seul le contrat** est connu ; le **fonctionnement interne** (**logique métier, implémentation**) **n'est pas visible**.
- Il faut assurer la **prédictabilité** : **aucune variation** dans le comportement et la réponse à une requête donnée.

### 6.4 Réutilisabilité et découvrabilité

- Un service doit être **accessible depuis un entrepôt/annuaire** pour faciliter sa **découverte**.
- Le **fournisseur** **dépose et met à jour** ses services dans l'annuaire.
- Le service est enrichi de **métadonnées** pour aider la **recherche**.
- Standards : **UDDI, ebXML**.
- **Gouvernance SOA :** un service est défini **avec l'intention d'être réutilisé**.

### 6.5 Autonomie et absence d'état

| Aspect | Exigence | Intérêt |
|---|---|---|
| **Autonomie** | Disposer de **toutes les informations nécessaires** à son exécution ; **ne dépendre d'aucun service externe** (couplage lâche) | Garantit la **prédictabilité** |
| **Sans état** | Ne pas conserver d'**état de session** | **Maintenance :** un service avec état **complique la composition** · **Performance :** **gourmand en ressources** système |

### 6.6 Composabilité

- Un service fonctionne de façon **modulaire** et non intégrée.
- On **décompose un service complexe en sous-services plus simples**, **autonomes**.
- On s'inscrit dans une **logique de composition** par **orchestration** (**couplage lâche**).
- L'orchestration **favorise l'indépendance** et **empêche qu'un service appelle directement un autre**.

> 💡 *(Ajout)* **Orchestration** = un **chef d'orchestre central** pilote l'enchaînement des services (ex. **BPEL**). **Chorégraphie** = chaque service réagit aux événements des autres, **sans chef central**.

---

## 7. Architecture monolithique vs SOA

| Critère | **Architecture monolithique** | **Architecture orientée services** |
|---|---|---|
| **Portée** | **Intra-entreprise** | **Entre entreprises** |
| **Langages** | **Limitée à un sous-ensemble** de langages | **Indépendance** du langage de programmation |
| **Style** | **Procédurale** | **Pilotée par les messages** |
| **Transport** | **Protocole propriétaire** | **Possibilité de choisir** le protocole de transport |
| **Couplage** | **Fortement couplé** | **Faiblement couplé** |
| **Traitement** | **Efficace** | **Plus lourd** (messages, sérialisation) |
| **Déploiement** | **De l'ensemble** | **Par partie** |

> ⚖️ **À retenir :** la SOA gagne en **interopérabilité, flexibilité et réutilisation**, mais **perd en performance brute** (traitement plus lourd). C'est un **compromis**.

---

## 8. Solutions technologiques pour une SOA

Plusieurs technologies conviennent :

| Famille | Exemples |
|---|---|
| **Services web** | **SOAP** et **REST** |
| **Technologies « Push »** | **WebSocket**, **SSE** (*Server-Sent Events*) |
| **Publish-Subscribe** | **RabbitMQ**… |
| **Frameworks asynchrones** | **Vert.x, RxJava, Node.js**… |

**Technologies spécifiques à des domaines :**

| Domaine | Technologie |
|---|---|
| **Internet des objets (IoT)** | **MQTT** |
| **Composants dynamiques Java** | **OSGi** (Equinox, Felix…) |
| **Robotique** | **ROS** |

> 🔑 **Ces technologies ne sont pas exclusives** : elles peuvent être **utilisées conjointement**.

---

## 9. Un point sur OSGi

**OSGi** = *Open Service Gateway Initiative*.

- **Spécification** de l'**OSGi Alliance** (osgi.org).
- Longtemps utilisé dans l'**embarqué** ; aujourd'hui dans des **serveurs** (GlassFish, Spring DM) et des **applications** (**Eclipse**).

| Concept OSGi | Signification |
|---|---|
| **Dynamique** | Un module peut être **installé, arrêté, mis à jour, désinstallé** à chaud |
| **Découvrabilité** | Un **registre des services** |
| **Abstraction** | **Gestion détaillée des classes à exposer** (ce qu'un module rend visible) |

> 💡 OSGi applique les **principes de la SOA à l'intérieur d'une JVM** (modules et services locaux).

---

## 10. Les services web : réponse à la SOA

| Caractéristique | Explication |
|---|---|
| **Basés sur les protocoles et langages du Web** | **HTTP, XML, TCP/IP** ; **aucune configuration réseau particulière** nécessaire |
| **Auto-suffisants** | Contiennent **toutes les informations d'utilisation** : **Chercher / publier / consommer** = **annuaire, contrat de fonctionnement, et un client** pour les consommer |
| **Modulaires** | Une application est **décomposée en un ensemble de services**, avec **orchestration** |
| **Définis par des standards** | **OASIS, W3C, WS-I, IETF** |

**Poster « Web Services Standards Overview »** (innoQ) : le support renvoie à une carte des **dizaines de spécifications** (messagerie, sécurité, fiabilité, transactions, gestion, métadonnées…) → *montre la richesse (et la lourdeur) de la pile SOAP*.

---

## 11. Les deux familles de services web

| Famille | Fondements |
|---|---|
| **Services web « étendus » (SOAP)** | S'appuient sur **UDDI / WSDL / SOAP** : **UDDI** = annuaire · **WSDL** = contrat · **SOAP** = consommer |
| **Services web REST** | Défini par la **thèse de Roy Fielding (2000)** ; utilise **directement HTTP** (sans enveloppe SOAP) ; l'**URI** nomme et identifie une **ressource** ; les **méthodes HTTP (POST, GET, PUT, DELETE)** réalisent les opérations **CRUD** |

---

## 12. Services web étendus (SOAP)

### 12.1 Fonctionnement (schéma du cours)

```
                    Annuaire UDDI
                   ▲             ▲
   Interrogation   │             │  Publier (WSDL)
   (chercher des   │             │
   contrats WSDL)  │             │
 Consommateur ─────┘             └───── Fournisseur
   du service  ═══ Consommer (message SOAP) ═══► du service
```

| Étape | Description |
|---|---|
| **Chercher** | Le consommateur **interroge l'annuaire UDDI** pour trouver des **contrats WSDL** selon des critères |
| **Publier** | Le fournisseur **publie le document WSDL**, qui sert de **contrat du service web** |
| **Consommer** | Un **message SOAP** est envoyé pour **invoquer** le service |

### Exemple de message SOAP (du cours)

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:hel="http://helloworldwebservice.lisi.ensma.fr/">
   <soapenv:Header/>
   <soapenv:Body>
      <hel:simpleHelloWorld/>
   </soapenv:Body>
</soapenv:Envelope>
```

| Élément | Rôle |
|---|---|
| **`Envelope`** | Enveloppe qui **contient tout le message** |
| **`Header`** | **En-tête** (sécurité, transactions… ici vide) |
| **`Body`** | **Corps** : l'appel de l'opération **`simpleHelloWorld`** (sans paramètre) du service **HelloWorld** |

> 🔗 C'est le service **HelloWorld** décrit en **WSDL** dans le chapitre suivant.

### 12.2 Pile des standards des services web étendus

| Couche | Standards | Rôle |
|---|---|---|
| **Orchestration** | **BPEL** (langages de processus métier, BPL) | Enchaîner les services |
| **Qualité de service** | **WS-Security** (sécurité), **WS-RM** (fiabilité), **WS-Transactions** (transactions) | Sécuriser, fiabiliser |
| **Découverte & description** | **WSDL, UDDI** | Contrat et annuaire |
| **Message** | **SOAP 1.1 et 1.2** | Format d'échange |
| **Transport** | **HTTP, SMTP, FTP, BEEP** | Acheminement |

---

## 13. Services web REST

### 13.1 Caractéristiques

| Point | Détail |
|---|---|
| **Usage** | Exploités pour les **architectures orientées données (DOA)** |
| **Statut** | **REST n'est pas un standard** : aucune spécification W3C |
| **Nature** | Un **style d'architecture** basé sur une compréhension du Web |
| **S'appuie sur** | Le protocole **HTTP**, les **URL**, les **formats de fichiers**, la **sécurisation via SSL** |

### 13.2 Pile des protocoles et langages REST

| Couche | Standards / outils | Rôle |
|---|---|---|
| **Orchestration** | **BPEL** | Enchaîner |
| **Qualité de service / sécurité** | **HTTP Basic, SSL/TLS** | Sécuriser |
| **Découverte & description** | **WADL, ATOM, RAML, Swagger…** | Décrire l'API |
| **Message** | **MIME types** (Text, **JSON**, **XML**…) | Format des données |
| **Transport** | **HTTP, FTP…** | Acheminement |

### 13.3 Correspondance CRUD ↔ HTTP *(ajout)*

| Opération **CRUD** | Méthode HTTP | Exemple |
|---|---|---|
| **C**reate (créer) | **POST** | `POST /personnes` |
| **R**ead (lire) | **GET** | `GET /personnes/12` |
| **U**pdate (modifier) | **PUT** | `PUT /personnes/12` |
| **D**elete (supprimer) | **DELETE** | `DELETE /personnes/12` |

> 🔑 **Principe REST :** une **ressource** (une personne, une commande…) est identifiée par une **URI** ; on la manipule avec les **verbes HTTP** ; la représentation (JSON, XML…) est indiquée par le **type MIME**.

---

## 14. SOAP vs REST : comparaison (ajout)

| Critère | **Services web étendus (SOAP)** | **Services web REST** |
|---|---|---|
| **Nature** | **Protocole** normalisé (W3C) | **Style d'architecture** (pas de standard) |
| **Annuaire** | **UDDI** | Aucun standard (documentation de l'API) |
| **Contrat** | **WSDL** | **WADL, RAML, Swagger/OpenAPI** (optionnels) |
| **Message** | **Enveloppe SOAP en XML** | **Direct sur HTTP** (JSON, XML, texte…) |
| **Opérations** | **Nommées** dans le message (RPC) | **Verbes HTTP** sur des **ressources** (URI) |
| **Transport** | HTTP, **SMTP, FTP, BEEP** | **HTTP** (surtout) |
| **Sécurité** | **WS-Security** | **SSL/TLS**, HTTP Basic |
| **Fiabilité / transactions** | **WS-RM, WS-Transactions** | À gérer soi-même |
| **Légèreté / performance** | **Plus lourd** | **Plus léger et rapide** |
| **Outils de test** | **SoapUI** | **cURL, Postman** |
| **Usage typique** | **Entreprise**, systèmes **formels** | **Web public**, **mobile**, **microservices** |

---

## 15. Les fournisseurs de services web

**Deux types de fournisseurs :**

| Type | Description |
|---|---|
| **« Orientés Web »** (public) | Services **ouverts au public** sur Internet |
| **« Entreprise »** (privé) | Services **internes** ou entre partenaires |

**Grands noms du Web** qui exposent leurs services : **Amazon, eBay, Facebook, Flickr, Google, Instagram, Twitter, Météo-France, Microsoft…**

**« Oui mais… »**
- **Pratiquement tous** exploitent l'architecture **REST** (**besoin de performance**).
- **Certains (comme Google)** ont **arrêté** les services web étendus.
- **eBay** propose **encore** des services web étendus.

> 💡 **Conséquence :** SOAP reste **présent en entreprise**, mais **REST domine le Web public**. Les deux sont à connaître.

---

## 16. Plateformes de développement

- La **grande majorité** des plateformes offrent un **support des services web** (outils et API) : **.NET**, **Java**, **PHP, C++, Python**…
- Les outils permettent de :
  - **manipuler des messages SOAP** ;
  - **manipuler des données XML** ;
  - **mapping XML ↔ classes** (**marshalling / unmarshalling**) ;
  - **accéder à la couche HTTP**.
- **Dans ce cours : la plateforme Java** — **outillée, gratuite, accessible, légère, respectueuse des standards**.

---

## 17. Organisation du cours : la métaphore de l'auto-école

Le cours se découpe comme un **permis de conduire** :

| **« Code » = concepts SOA** (la théorie) | **« Conduite » = développer** (la pratique) |
|---|---|
| **Connaître les normes, standards et techniques** | **Programmer un service web** depuis un **contrat** ou depuis une **classe Java** |
| **Spécifier le contrat** d'un service web | **Déployer** un service web |
| **Appeler** un service web | **Programmer un client** |
| **Identifier les phases** de programmation serveur/client et de déploiement | **Mettre en application une architecture microservices** |
| **Connaître les bonnes pratiques** des architectures microservices | |

> 📌 Les technologies Java utilisées sont basées sur **Jakarta EE**. **Les principes restent les mêmes** avec **Spring, .NET**, etc.

---

## 18. Outils nécessaires

| Outil | Usage |
|---|---|
| **Java 8 (minimum)** | Plateforme de développement |
| **Maven 3** | Gestion de projet et dépendances |
| **SoapUI** | **Tester les services web étendus (SOAP)** |
| **Docker et Docker-Compose** | Conteneurs (**droits administrateur requis**) |
| **Eclipse, IntelliJ ou NetBeans** | **IDE** |
| **cURL ou Postman** | **Tester les services web REST** |

---

## 19. Remarques sur le support de cours

1. **Fichier d'origine :** le PDF provient de **« 01-introsoa.pptx »** (28 diapositives).
2. **Microservices :** la diapositive d'évolution cite les **microservices** comme *« trop tôt pour une comparaison »*, et plusieurs diapositives disent que le couplage lâche par bus/orchestration est **« remis en cause dans l'architecture microservices »**. Le tableau de la section 4 est **un ajout** pour comprendre cette évolution.
3. **« Sans état » :** le support dit qu'un service doit être sans état « de façon à minimiser la consommation de ressources » et que *l'état* rend la **composition complexe** ; cela concerne l'état **conservé entre les appels** (pas l'état des données en base).
4. **« Swap précoce »** : à comprendre comme **remplacement prématuré** de l'application.
5. **« DOA »** : *Data Oriented Architecture* (architecture orientée données), à ne pas confondre avec SOA.
6. **REST « pas un standard »** : exact, mais il **s'appuie sur des standards** (HTTP, URL, MIME) ; des **descriptions optionnelles** existent (WADL, RAML, Swagger/OpenAPI).
7. **Pile REST :** le support y indique aussi **BPEL** en orchestration, ce qui est **rare en pratique** avec REST (utilisé surtout avec SOAP).
8. **Fournisseurs :** les affirmations (Google a arrêté les services SOAP, eBay en propose encore) sont **historiques** ; à vérifier selon le service et la date.
9. **Typographie :** « ensembles » (au singulier : *l'ensemble*), « Etudié » (*étudié*) : sans incidence sur le fond.
10. **Lien avec la suite :** ce chapitre est l'**introduction** ; le suivant (**WSDL**) détaille le **contrat**, puis viennent **SOAP**, **REST** et l'**orchestration**.

---

## 20. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Avant la SOA** | **Silos applicatifs** monolithiques : **redondance, coûts, complexité, maintenance difficile** |
| **Technologies antérieures** | DCOM, CORBA, EJB, RMI, .NET Remoting : **formats et protocoles spécifiques**, **interopérabilité limitée** |
| **SOA** | **Architecture Orientée Services** : le **service** est le **fondement** de l'interaction |
| **Paradigme** | **Publier → Chercher → Consommer** |
| **Service** | **Composant logiciel distribué** exposant des **fonctionnalités à forte valeur ajoutée** |
| **8 aspects** | **Contrat standardisé · Couplage lâche · Abstraction · Réutilisabilité · Autonomie · Sans état · Découvrabilité · Composabilité** |
| **3 types de contrat** | **Syntaxique · Sémantique · Qualité de service** |
| **Couplage lâche** | Échanges **par messages** ; **bus / orchestration** |
| **Abstraction** | **Boîte noire** ; **prédictabilité** |
| **Découvrabilité** | **Annuaire + métadonnées** (UDDI, ebXML) |
| **Sans état** | **Moins de ressources**, **composition plus simple** |
| **Composabilité** | **Décomposer** en sous-services ; **orchestration** |
| **Monolithique vs SOA** | **Fort couplage, efficace, déploiement global** vs **faible couplage, plus lourd, déploiement par partie, indépendance du langage** |
| **Technologies SOA** | **Services web (SOAP/REST)**, **WebSocket/SSE**, **Pub-Sub (RabbitMQ)**, **Vert.x/RxJava/Node.js** ; **MQTT** (IoT), **OSGi**, **ROS** |
| **OSGi** | Modules **dynamiques**, **registre de services**, **abstraction** ; utilisé dans **Eclipse** |
| **Services web** | **HTTP + XML + TCP/IP** ; **auto-suffisants, modulaires, normalisés** (OASIS, W3C, WS-I, IETF) |
| **SOAP (étendus)** | **UDDI** (annuaire) + **WSDL** (contrat) + **SOAP** (consommer) |
| **Pile SOAP** | BPEL · WS-Security/WS-RM/WS-Transactions · WSDL/UDDI · SOAP 1.1/1.2 · HTTP/SMTP/FTP/BEEP |
| **REST** | **Style d'architecture** (Fielding, **2000**) ; **URI + méthodes HTTP (CRUD)** |
| **Pile REST** | BPEL · HTTP Basic/SSL-TLS · WADL/ATOM/RAML/Swagger · MIME (JSON, XML) · HTTP/FTP |
| **Fournisseurs** | **Orientés Web (public)** vs **Entreprise (privé)** ; **REST domine** |
| **Plateforme du cours** | **Java** (Jakarta EE) |
| **Outils** | **Java 8+, Maven 3, SoapUI, Docker/Compose, IDE, cURL/Postman** |

### Questions d'examen typiques
1. Quels **problèmes** posait l'architecture **3-tiers en silos** ? Quelles en étaient les **conséquences** ?
2. Quelles sont les **faiblesses** des solutions **CORBA, EJB, RMI…** ?
3. **Définir la SOA** et expliquer le paradigme **Chercher / Publier / Consommer** (avec schéma).
4. **Définir un service** et citer les **huit aspects** d'un service.
5. Quels sont les **trois types de contrat** d'un service ? Donner un exemple de chacun.
6. Expliquer le **couplage lâche** et le rôle de l'**orchestration**.
7. Que signifie **abstraction** (**boîte noire**) et **prédictabilité** ?
8. Pourquoi un service doit-il être **autonome** et **sans état** ?
9. **Comparer l'architecture monolithique et la SOA** (portée, couplage, traitement, déploiement).
10. Citer des **technologies** adaptées à la SOA (services web, Pub-Sub, MQTT, OSGi, ROS…).
11. Qu'est-ce qu'**OSGi** ? Quels concepts de la SOA applique-t-il ?
12. Pourquoi les **services web** répondent-ils bien aux besoins de la SOA ?
13. **Comparer services web étendus (SOAP) et REST** : standards, annuaire, contrat, message, performance.
14. Quels sont les rôles de **UDDI, WSDL, SOAP** ? Que contient une **enveloppe SOAP** ?
15. **REST** est-il un standard ? Que signifie **CRUD** et comment le réalise-t-on avec **HTTP** ?
16. Pourquoi **la plupart des fournisseurs** utilisent-ils **REST** aujourd'hui ?
17. Quels **outils** pour tester un service **SOAP** ? un service **REST** ?

---

*Fin du résumé — SOA : Introduction générale.*
