# WSDL : Décrire et configurer les services web — Résumé complet du cours
**Cours :** SOA — Services web étendus
**Chapitre :** WSDL (*Web Service Description Language*) — fichier « 03-WSDL »
**Support :** adapté par H. Hmida du cours de M. Baron (ISAE-ENSMA)
**Type de document :** Résumé d'étude avec explications, schémas, code XML et conseils d'examen

---

## Table des matières

1. [Plan du cours et ressources](#1-plan-du-cours-et-ressources)
2. [Généralités sur WSDL](#2-généralités-sur-wsdl)
3. [Où trouver des documents WSDL](#3-où-trouver-des-documents-wsdl)
4. [Où WSDL est-il utilisé ? (le triangle SOA)](#4-où-wsdl-est-il-utilisé--le-triangle-soa)
5. [Les concepts d'un document WSDL](#5-les-concepts-dun-document-wsdl)
6. [WSDL par l'exemple : le service HelloWorld](#6-wsdl-par-lexemple--le-service-helloworld)
7. [Organisation d'un document WSDL](#7-organisation-dun-document-wsdl)
8. [WSDL par l'exemple : le carnet d'adresses (Notebook)](#8-wsdl-par-lexemple--le-carnet-dadresses-notebook)
9. [L'élément `<types>`](#9-lélément-types)
10. [L'élément `<message>`](#10-lélément-message)
11. [Les éléments `<portType>` et `<operation>`](#11-les-éléments-porttype-et-operation)
12. [Les quatre modèles d'opération](#12-les-quatre-modèles-dopération)
13. [L'élément `<binding>`](#13-lélément-binding)
14. [Les éléments `<service>` et `<port>`](#14-les-éléments-service-et-port)
15. [Autre chose que SOAP : binding HTTP GET / POST](#15-autre-chose-que-soap--binding-http-get--post)
16. [Outils](#16-outils)
17. [WSDL 1.1 vs WSDL 2.0 (ajout)](#17-wsdl-11-vs-wsdl-20-ajout)
18. [Remarques sur le support de cours](#18-remarques-sur-le-support-de-cours)
19. [Fiche de révision rapide](#19-fiche-de-révision-rapide)

---

## 1. Plan du cours et ressources

### Plan du cours
1. Généralités WSDL
2. WSDL par l'exemple : service **HelloWorld**
3. Organisation d'un document WSDL
4. Élément **Type**
5. Élément **Message**
6. Éléments **PortType** et **Operation**
7. Élément **Binding**
8. **Binding SOAP**
9. Éléments **Service** et **Port**
10. **Binding HTTP GET & POST**

### Ressources citées

| Catégorie | Références |
|---|---|
| **Spécifications W3C** | WSDL 1.1 (`w3.org/TR/wsdl`), WSDL 2.0 (`w3.org/TR/wsdl20/`), `w3.org/2002/ws/desc/`, XML Schema (`w3.org/XML/Schema`), RELAX NG (`relaxng.org`) |
| **Articles** | IBM developerWorks (ws-intwsdl, ws-whichwsdl, j-jws20), O'Reilly (chapitre 6), W3Schools |
| **Livres** | *Services Web avec SOAP, WSDL, UDDI, ebXML* (J.-M. Chauvet, Eyrolles, 2003) · *Understanding Web Services : XML, WSDL…* (E. Newcomer, Addison-Wesley, 2002) · *WSDL 100 Success Secrets* (K. Allen, 2008) |

---

## 2. Généralités sur WSDL

> **WSDL = *Web Service Description Language***

- Langage **basé sur XML** qui permet de **décrire un service web**.
- Fournit une description **indépendante du langage** et de la **plate-forme**.
- **Comparaison :** WSDL est assez semblable à l'**IDL de CORBA** (langage de description d'interfaces).
- **Spécification du W3C** :
  - **WSDL 1.1** : `http://www.w3.org/TR/wsdl`
  - **WSDL 2.0** : `http://www.w3.org/TR/wsdl20/`

### À partir d'un document WSDL, on peut…

| Génération | Résultat |
|---|---|
| **Générer un client** | Code pour **appeler** le service web (*stub* client) |
| **Générer le code serveur** | **Squelette** pour **implémenter** le service web |

> 🔑 **Idée clé :** le WSDL est le **« contrat »** d'un service web : il dit **quoi** (opérations, messages), **comment** (protocole, format) et **où** (adresse).

---

## 3. Où trouver des documents WSDL

| Fournisseur | Exemple de WSDL | Remarque |
|---|---|---|
| **Amazon Associates Web Service** | `…/AWSECommerceService/AWSECommerceService.wsdl` | Création d'un **compte** nécessaire pour l'invocation |
| **eBay** | `…/webservices/finding/latest/FindingService.wsdl` | Compte nécessaire |
| **NOAA** (météo, États-Unis) | `…/forecasts/xml/DWMLgen/wsdl/ndfdXML.wsdl` | Service météo |
| **WebserviceX.NET** | `…/convertMetricWeight.asmx?wsdl` · `…/GenericNAICS.asmx?wsdl` | Services divers (conversion, etc.) |

> 💡 **Astuce :** pour les services .NET (`.asmx`), on obtient le WSDL en ajoutant **`?wsdl`** à l'URL du service.

---

## 4. Où WSDL est-il utilisé ? (le triangle SOA)

Le schéma met en scène **deux entreprises** et un **annuaire UDDI** :

- **Entreprise A** = **consommatrice** de services web
- **Entreprise B** = **fournisseur** de services web
- **Annuaire UDDI** = registre de services

```
                         Annuaire UDDI
                        ╱             ╲
          ② A interroge ╱               ╲ ① B publie ses services
            l'annuaire                    (transmet ses WSDL)
                      ╱                   ╲
            Entreprise A ───────────────── Entreprise B
          (consommatrice)  ④ messages SOAP   (fournisseur)
                           ⑤ réponses SOAP
        ③ A télécharge les documents WSDL
```

### Les 5 étapes

| # | Étape |
|---|---|
| **1** | **B publie** ses services web (qu'elle implémente) dans un **annuaire UDDI** — elle transmet ses **WSDL** |
| **2** | **A interroge** l'annuaire UDDI pour obtenir la **liste des services web** répondant à ses exigences |
| **3** | **A télécharge** les **documents WSDL** qui décrivent les services web |
| **4** | **A envoie des messages SOAP** **conformes au WSDL** pour **invoquer** les services de B |
| **5** | **B répond** en retournant des **messages SOAP conformes au WSDL** |

> 🔑 **Rôle du WSDL :** c'est lui qui permet à A de **savoir comment** appeler le service de B (opérations, formats, adresse) **sans connaître son implémentation**.

---

## 5. Les concepts d'un document WSDL

| Concept | Définition | Équivalent Java |
|---|---|---|
| **Donnée** (*data/type*) | Information **typée** | Type / classe |
| **Message** (*message*) | **Regroupe** un ensemble de données | Ensemble de **paramètres** |
| **Opération** (*operation*) | **Action** fournie par le service web | **Méthode** |
| **Type de port** (*portType*) | **Ensemble d'opérations** | **Interface** |
| **Binding** | Définit, pour un type de port, le **protocole** de transmission et le **format des données** | Implémentation du protocole |
| **Port** | Définit **où** le service est localisé (**adresse**) et le **binding** à utiliser | Point d'accès (URL) |
| **Service** | **Ensemble de ports** | Le service lui-même |

---

## 6. WSDL par l'exemple : le service HelloWorld

### Spécification

- Le service **HelloWorld** fournit **deux opérations** :
  - **`makeHello`** : prend en paramètre **une chaîne** et retourne **une chaîne** ;
  - **`simpleHello`** : **sans paramètre** d'entrée, retourne **une chaîne**.
- Accès par des **messages SOAP**.
- Protocole de transport : **HTTP**.
- Style : **RPC**.

### Document WSDL complet

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<definitions name="HelloWorldService"
    targetNamespace="http://www.helloworldwebservice.com/"
    xmlns="http://schemas.xmlsoap.org/wsdl/"
    xmlns:tns="http://www.helloworldwebservice.com/"
    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
    xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/">

  <types/>

  <message name="makeHelloWorld">
    <part name="value" type="xsd:string"/>
  </message>
  <message name="makeHelloWorldResponse">
    <part name="helloWorldResult" type="xsd:string"/>
  </message>
  <message name="simpleHelloWorld"/>
  <message name="simpleHelloWorldResponse">
    <part name="helloWorldResult" type="xsd:string"/>
  </message>

  <portType name="HelloWorldService">
    <operation name="makeHelloWorld">
      <input  message="tns:makeHelloWorld"/>
      <output message="tns:makeHelloWorldResponse"/>
    </operation>
    <operation name="simpleHelloWorld">
      <input  message="tns:simpleHelloWorld"/>
      <output message="tns:simpleHelloWorldResponse"/>
    </operation>
  </portType>

  <binding name="HelloWorldPortBinding" type="tns:HelloWorld">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" style="rpc"/>
    <operation name="makeHelloWorld">
      <soap:operation soapAction=""/>
      <input>  <soap:body use="literal" namespace="http://www.helloworldwebservice.com/"/> </input>
      <output> <soap:body use="literal" namespace="http://www.helloworldwebservice.com/"/> </output>
    </operation>
    <operation name="simpleHelloWorld">
      <soap:operation soapAction=""/>
      <input>  <soap:body use="literal" namespace="http://www.helloworldwebservice.com/"/> </input>
      <output> <soap:body use="literal" namespace="http://www.helloworldwebservice.com/"/> </output>
    </operation>
  </binding>

  <service name="HelloWorldService">
    <port name="HelloWorldPort" binding="tns:HelloWorldPortBinding">
      <soap:address location="TODO"/>
    </port>
  </service>
</definitions>
```

### Lecture guidée du document

| Bloc | Ce qu'il fait ici |
|---|---|
| `<definitions>` | **Racine** ; déclare le **namespace cible** (`tns`) et les préfixes (`xsd`, `soap`) |
| `<types/>` | **Vide** : seuls des types de base (`xsd:string`) sont utilisés |
| `<message>` ×4 | 2 messages par opération (**requête** + **réponse**) ; `simpleHelloWorld` n'a **aucun `<part>`** (pas de paramètre) |
| `<portType>` | Regroupe les **2 opérations** (**interface abstraite**) |
| `<binding>` | **SOAP sur HTTP**, style **RPC**, encodage **literal** |
| `<service>` / `<port>` | Un **port** lie le **binding** à une **adresse** (`soap:address location`) |

---

## 7. Organisation d'un document WSDL

### 7.1 Les éléments (WSDL 1.1)

| Élément | Cardinalité | Rôle |
|---|---|---|
| **`<definitions>`** | 1 (racine) | Racine du document WSDL |
| **`<types>`** | **Optionnel, un seul** | **Définition des types** de données (sous forme de **XML Schema**) |
| **`<message>`** | **Plusieurs** | Décrit les messages à transmettre (paramètre d'opération, valeur de retour, exception…) |
| **`<portType>`** | **Plusieurs** | Ensemble d'**opérations** ; chacune a 0 ou plusieurs messages en **entrée**, en **sortie** ou de **fautes** |
| **`<binding>`** | **Plusieurs** | **Lie** un `portType` à un **protocole** (SOAP, HTTP) |
| **`<service>`** | **Plusieurs** | Regroupe les **ports** (relation entre binding et **URL**) |

### 7.2 Deux parties : abstraite et concrète

| | **Partie abstraite** | **Partie concrète** |
|---|---|---|
| **Décrit** | Les **messages** et les **opérations** disponibles | Le **protocole** et l'**encodage** à utiliser |
| **Éléments** | `<types>` · `<message>` · `<portType>` | `<binding>` · `<service>` (avec `<port>`) |
| **Question** | **QUOI ?** | **COMMENT et OÙ ?** |
| **Dépend du protocole ?** | **Non** | **Oui** |

> 🔑 **Plusieurs parties concrètes** peuvent être proposées pour **une même partie abstraite**.
> **Motivation de cette séparation :** la **réutilisabilité de la partie abstraite** (même interface, plusieurs protocoles et plusieurs adresses).

### 7.3 Schéma du cours

```
                Port Type 1  (Operation Op1, Operation Op2)          ← PARTIE ABSTRAITE
 ─────────────────────────────────────────────────────────────────
     ABCBinding (SOAP/HTTP)         DEFBinding (SOAP/SMTP)          ← PARTIE CONCRÈTE
        │            │                    │                            (protocoles différents)
     Port ABC     Port DEF            Port GHI …                      (localisations différentes)
   URL http://   URL http://          URL …
        └─────── Service 1 ─────┘    Service 2        Service 3
                         WSDL Document
```

→ **Un même Port Type** est proposé via **deux bindings** (SOAP/HTTP et SOAP/SMTP) ; chaque binding peut avoir **plusieurs ports** (URL différentes) regroupés dans des **services**.

---

## 8. WSDL par l'exemple : le carnet d'adresses (Notebook)

### Spécification du service *Notebook*

| Opération | Entrée | Sortie |
|---|---|---|
| **`addPerson`** (version 1) | **Un objet `Person`** | **Booléen** (état de la création) |
| **`addPerson`** (version 2, surchargée) | **Trois chaînes** : `name`, `address`, `birthyear` | **Aucune** (sans retour) |
| **`getPersonByName`** (`getPerson` dans le WSDL) | Une **chaîne** (le nom) | Un objet **`Person`** |
| **`getPersons`** | **Aucune** | Un **tableau** d'objets `Person` |

- Accès par **messages SOAP**, protocole **HTTP**, style **RPC**.

---

## 9. L'élément `<types>`

### Rôle
- Contient la **définition des types** utilisés pour décrire la **structure des messages**.
- Système de typage généralement **XML Schema (XSD)**, mais d'autres sont autorisés (**RELAX NG**, par exemple).
- **Facultatif** si les messages n'utilisent que des **types de base** (Integer, Boolean…).
- Pour les **structures complexes** (comme `Person`), on emploie un **XML Schema**.

### Exemple 1 : types définis *dans* le WSDL

```xml
<types>
  <xsd:schema targetNamespace="http://www.notebookwebservice.com/">
    <xsd:complexType name="person">
      <xsd:sequence>
        <xsd:element name="address"   type="xs:string" minOccurs="0"/>
        <xsd:element name="birthyear" type="xs:string" minOccurs="0"/>
        <xsd:element name="name"      type="xs:string" minOccurs="0"/>
      </xsd:sequence>
    </xsd:complexType>

    <xsd:complexType name="personArray" final="#all">
      <xsd:sequence>
        <xsd:element name="item" type="tns:person"
                     minOccurs="0" maxOccurs="unbounded" nillable="true"/>
      </xsd:sequence>
    </xsd:complexType>
  </xsd:schema>
</types>
```

| Élément | Signification |
|---|---|
| `person` | Une personne = **adresse**, **année de naissance**, **nom** (3 chaînes, **optionnelles** : `minOccurs="0"`) |
| `personArray` | **Tableau de personnes** : élément `item` répété (`maxOccurs="unbounded"`), `nillable="true"` (peut être nul) |
| `final="#all"` | Le type **ne peut pas être dérivé** (ni étendu ni restreint) |

### Exemple 2 : types **importés** depuis un fichier XSD

```xml
<types>
  <xsd:schema>
    <xsd:import namespace="http://www.notebookwebservice.com/"
                schemaLocation="NotebookService_schema1.xsd"/>
  </xsd:schema>
</types>
```

→ Le fichier `NotebookService_schema1.xsd` contient les mêmes définitions (`person`, `personArray`) dans un `<xs:schema>` autonome.

### Pourquoi séparer le schéma du WSDL ?

| Avantage | Explication |
|---|---|
| **Réutilisation** | Les mêmes types servent à **plusieurs WSDL** |
| **Légèreté** | Le document WSDL **n'est pas alourdi** |
| **Hébergement indépendant** | L'adresse de l'hôte du schéma **peut différer** de celle du WSDL |

> 📌 Dans la suite du cours, on **privilégie la séparation** des types et du document WSDL.

---

## 10. L'élément `<message>`

### Principe
- Décrit les **messages échangés** : **paramètres d'entrée**, **paramètres de sortie**, **exceptions**.
- Chaque `<message>` a un **nom** (attribut `name`) et est constitué d'un ensemble d'éléments **`<part>`**.
- **Un `<part>` ≈ un paramètre** d'opération (plusieurs paramètres → plusieurs `<part>`).
- Un `<part>` est défini par un **nom** (`name`) et un **type** (`type`).

### Exemple : messages du service Notebook

```xml
<message name="addPersonWithComplexType">
  <part name="newPerson" type="tns:person"/>              <!-- 1 seule partie, type complexe -->
</message>
<message name="addPersonWithComplexTypeResponse">
  <part name="addPersonWithComplexTypeResult" type="xsd:boolean"/>
</message>

<message name="addPersonWithSimpleType">                  <!-- 3 parties = 3 paramètres -->
  <part name="name"      type="xsd:string"/>
  <part name="address"   type="xsd:string"/>
  <part name="birthyear" type="xsd:string"/>
</message>

<message name="getPerson">
  <part name="personName" type="xsd:string"/>
</message>
<message name="getPersonResponse">
  <part name="getPersonResult" type="tns:person"/>
</message>

<message name="getPersons"/>                               <!-- aucun paramètre -->
<message name="getPersonsResponse">
  <part name="getPersonsResult" type="tns:personArray"/>   <!-- tableau de personnes -->
</message>
```

| Message | Rôle | Parties |
|---|---|---|
| `addPersonWithComplexType` | **Appel** de `addPerson` (version objet) | **1** |
| `addPersonWithComplexTypeResponse` | **Résultat** | **1** (booléen) |
| `addPersonWithSimpleType` | **Appel** de `addPerson` (version 3 chaînes) | **3** |
| `getPerson` / `getPersonResponse` | Appel / résultat de `getPerson` | 1 / 1 |
| `getPersons` / `getPersonsResponse` | Appel (**0 partie**) / résultat | 0 / 1 |

> 🔑 Une partie peut **pointer vers un type** défini dans `<types>` (ex. `tns:person`) ou utiliser un **type de base** XSD (`xsd:string`, `xsd:boolean`).

---

## 11. Les éléments `<portType>` et `<operation>`

### `<portType>` ≈ interface Java
- **Regroupement d'opérations**.
- Identifié par un **nom** (`name`).
- Composé de sous-éléments **`<operation>`**.

### `<operation>` ≈ méthode Java
- Identifiée par un **nom** (`name`).
- Ses paramètres sont décrits par une **liste de messages**.

### Sous-éléments d'une opération

| Sous-élément | Rôle |
|---|---|
| **`<input>`** | Message **transmis au service** |
| **`<output>`** | Message **produit par le service** |
| **`<fault>`** | **Message d'erreur** (très proche des **exceptions**) |

Chaque sous-élément a deux attributs :
- **`name`** : nom explicite (**optionnel**) ;
- **`message`** : **référence** à un message défini précédemment.

> 📌 **Surcharge** autorisée **sous condition** : les messages `<input>` et/ou `<output>` doivent être **différents**.

### Exemple : portType du service Notebook

```xml
<portType name="NotebookService">
  <operation name="addPerson">                              <!-- surchargée -->
    <input  message="tns:addPersonWithComplexType"/>
    <output message="tns:addPersonWithComplexTypeResponse"/>
  </operation>

  <operation name="addPerson" parameterOrder="name address birthyear">
    <input  message="tns:addPersonWithSimpleType"/>         <!-- one-way : pas de output -->
  </operation>

  <operation name="getPerson">
    <input  message="tns:getPerson"/>
    <output message="tns:getPersonResponse"/>
  </operation>

  <operation name="getPersons">
    <input  message="tns:getPersons"/>
    <output message="tns:getPersonsResponse"/>
  </operation>
</portType>
```

- **`addPerson`** est **surchargée** (deux messages d'entrée différents).
- **`parameterOrder="name address birthyear"`** : permet de **fixer l'ordre des paramètres**.

---

## 12. Les quatre modèles d'opération

| Modèle | Sens | Messages utilisés | Exemple du cours |
|---|---|---|---|
| **One-way** (envoi) | Le client envoie un message et **n'attend pas de réponse** | **`<input>` seul** | `addPerson` (3 chaînes) |
| **Request/Response** (question–réponse) | Le client envoie un message, **une réponse est retournée** | `<input>` + `<output>` (+ `<fault>` possible) | `addPerson` (objet) → booléen |
| **Notification** | Le **service envoie** un message au client | **`<output>` seul** | `personStatus` → `trackingInformation` |
| **Solicit-response** (sollicitation–réponse) | Le **client reçoit** un message du service et **répond** | `<output>` + `<input>` + `<fault>` | `clientQuery` : output `bandWithRequest`, input `bandwidthInfo` |

```xml
<!-- One-way -->
<operation name="addPerson" parameterOrder="name address birthyear">
  <input message="tns:addPersonWithSimpleType"/>
</operation>

<!-- Request/Response -->
<operation name="addPerson">
  <input  message="tns:addPersonWithComplexType"/>
  <output message="tns:addPersonWithComplexTypeResponse"/>
</operation>

<!-- Notification -->
<operation name="personStatus">
  <output message="trackingInformation"/>
</operation>

<!-- Solicit-response -->
<operation name="clientQuery">
  <output message="bandWithRequest"/>
  <input  message="bandwidthInfo"/>
  <fault  message="faultMessage"/>
</operation>
```

> 💡 **Astuce :** c'est **l'ordre** des éléments qui détermine le modèle : **input d'abord** = le client initie (one-way / request-response) ; **output d'abord** = le service initie (notification / solicit-response).

| Qui initie ? | Sans réponse | Avec réponse |
|---|---|---|
| **Le client** | **One-way** | **Request/Response** |
| **Le service** | **Notification** | **Solicit-response** |

---

## 13. L'élément `<binding>`

### Rôle
- Réalise la **partie concrète** d'un `<portType>`.
- Attributs : un **nom** (`name`) et le **portType** associé (`type`).
- Décrit **précisément le protocole** pour manipuler le portType :
  - **SOAP 1.1 et 1.2** ;
  - **HTTP GET & POST** (par exemple pour le transfert d'**images**) ;
  - **MIME**.
- **Plusieurs `<binding>`** possibles → un même `portType` peut être appelé **de différentes manières**.
- La **structure du `<binding>` dépend du protocole** utilisé.

### Structure générale (sans précision de protocole)

```xml
<binding name="NamePortBinding" type="tns:portType">
  <!-- Décrit le protocole à utiliser -->
  <operation name="operation1">
    <!-- Action du protocole sur l'opération -->
    <input>  <!-- Action du protocole sur les messages d'entrée  --> </input>
    <output> <!-- Action du protocole sur les messages de sortie --> </output>
    <fault>  <!-- Action du protocole sur les messages d'erreur  --> </fault>
  </operation>
</binding>
```

> 📌 Le **schéma XML de WSDL ne décrit pas** les sous-éléments de `binding`, `operation`, `input`, `output` et `fault` : ils sont **spécifiques aux protocoles** (extensions `soap:`, `http:`, `mime:`).

### Binding SOAP (d'après l'exemple HelloWorld)

| Élément | Rôle |
|---|---|
| `<soap:binding transport="…/soap/http" style="rpc"/>` | Déclare **SOAP sur HTTP** et le **style** (`rpc` ou `document`) |
| `<soap:operation soapAction=""/>` | Valeur de l'en-tête HTTP **SOAPAction** |
| `<soap:body use="literal" namespace="…"/>` | Comment les **parties du message** sont placées dans le **corps SOAP** (`use="literal"` ou `"encoded"`) |

---

## 14. Les éléments `<service>` et `<port>`

- Un **`<service>`** définit l'**ensemble des points d'entrée** du service web, en regroupant des éléments **`<port>`**.
- Un **`<port>`** spécifie **une adresse pour un binding donné**.
- Un port est défini par **deux attributs** :
  - **`name`** : nom du port ;
  - **`binding`** : nom du **binding** (défini avant).
- Le **corps** du `<port>` est **spécifique au protocole** :
  - pour **SOAP** : **`<soap:address location="…"/>`** donne l'**URI** du port ;
  - pour **HTTP** : **`<http:address location="…"/>`**.
- On peut donc **appeler un service à des endroits différents** (plusieurs éléments `<port>`).

### Exemple : le service Notebook

```xml
<service name="NotebookService">
  <port name="NoteBookPort" binding="tns:NoteBookPortBinding">
    <soap:address location="http://localhost:8080/notebookwebservice/notebook"/>
  </port>
</service>
```

→ Le portType est **accessible en SOAP/HTTP** à l'URL `http://localhost:8080/notebookwebservice/notebook`.

---

## 15. Autre chose que SOAP : binding HTTP GET / POST

Un WSDL peut décrire un service qui **ne passe pas par SOAP** : l'exemple du W3C retourne **une image GIF ou JPEG**.

### Partie abstraite

```xml
<message name="m1">
  <part name="part1" type="xsd:string"/>
  <part name="part2" type="xsd:int"/>
  <part name="part3" type="xsd:string"/>
</message>
<message name="m2">
  <part name="image" type="xsd:binary"/>
</message>

<portType name="pt1">
  <operation name="o1">
    <input  message="tns:m1"/>
    <output message="tns:m2"/>
  </operation>
</portType>
```

### Service : trois ports pour trois bindings

```xml
<service name="service1">
  <port name="port1" binding="tns:b1"><http:address location="http://example.com/"/></port>
  <port name="port2" binding="tns:b2"><http:address location="http://example.com/"/></port>
  <port name="port3" binding="tns:b3"><http:address location="http://example.com/"/></port>
</service>
```

### Les trois bindings du même `portType`

| Binding | Verbe | Encodage de l'entrée | Sortie |
|---|---|---|---|
| **b1** | **GET** | **`<http:urlReplacement/>`** : les paramètres sont **insérés dans le chemin** de l'URL (`o1/A(part1)B(part2)/(part3)`) | `image/gif` ou `image/jpeg` |
| **b2** | **GET** | **`<http:urlEncoded/>`** : paramètres en **query string** (`?part1=…&part2=…`) | `image/gif` ou `image/jpeg` |
| **b3** | **POST** | `<mime:content type="application/x-www-form-urlencoded"/>` : paramètres dans le **corps** de la requête | `image/gif` ou `image/jpeg` |

> 🔑 **À retenir :** **GET** → paramètres dans **l'URL** (urlReplacement ou urlEncoded) ; **POST** → paramètres dans **le corps** (`application/x-www-form-urlencoded`). La sortie est typée avec **`mime:content`**.
> C'est la **séparation abstrait/concret** en action : **une seule interface**, **trois façons** de l'invoquer.

---

## 16. Outils

| Besoin | Outils cités |
|---|---|
| **Construire** un document WSDL | **VS Code**, **Notepad++** (simples éditeurs de texte puisqu'il s'agit d'XML) · **Eclipse** · **NetBeans** · **IntelliJ** · **Visual Studio** · … (tous les environnements qui manipulent des services web) |
| **Valider et manipuler** un document WSDL | **SoapUI** |

> 💡 *(Ajout)* **SoapUI** permet de **charger un WSDL**, de **générer automatiquement des requêtes SOAP** pour chaque opération, de les envoyer au service et d'écrire des tests.

---

## 17. WSDL 1.1 vs WSDL 2.0 (ajout)

> ⚠️ **Section ajoutée** : le support cite les deux versions du W3C mais détaille le **WSDL 1.1**. Voici les principales différences, utiles pour l'examen.

| WSDL 1.1 (cours) | WSDL 2.0 |
|---|---|
| `<definitions>` | **`<description>`** |
| `<message>` + `<part>` | **Supprimés** : les types sont référencés directement (XML Schema) |
| `<portType>` | **`<interface>`** |
| `<port>` | **`<endpoint>`** |
| 4 modèles d'opération (one-way, request-response, notification, solicit-response) | **Patrons d'échange de messages** (MEP) : *in-only*, *in-out*, etc. |
| Pas d'héritage de portType | **Héritage d'interfaces** possible |
| Surcharge d'opérations autorisée | **Surcharge interdite** |
| Bindings SOAP 1.1/1.2, HTTP, MIME | Bindings SOAP 1.2, **HTTP (REST)** plus complets |

---

## 18. Remarques sur le support de cours

Quelques incohérences à connaître (elles n'empêchent pas la compréhension, mais peuvent faire perdre du temps lors d'un TP) :

1. **HelloWorld — binding** : `<binding … type="tns:HelloWorld">` référence un portType nommé **`HelloWorld`**, alors que le portType s'appelle **`HelloWorldService`** → il faut écrire `type="tns:HelloWorldService"`.
2. **HelloWorld — adresse** : `soap:address location="TODO"` est un **espace réservé** à remplacer par l'URL réelle du service.
3. **Notebook — nom d'opération** : la spécification parle de **`getPersonByName`**, mais le WSDL utilise **`getPerson`**.
4. **Notebook — préfixes XSD** : le document déclare `xmlns:xs` mais utilise aussi `xsd:` → il faut déclarer **les deux préfixes** (ou en utiliser un seul).
5. **Notebook — binding / service** : le service référence `tns:NoteBookPortBinding` (« B » majuscule dans *NoteBook*) alors que l'exemple de `<portType>` s'appelle `NotebookService` ; la structure du binding est donnée avec un **placeholder** (`type="tns:portType"`).
6. **`parameterOrder`** : l'attribut s'utilise normalement avec le style **RPC**, pour ordonner les paramètres d'une opération ; la version « one-way » de `addPerson` montre bien l'usage.
7. **`xsd:binary`** (exemple GIF/JPEG) : ce type **n'existe pas** dans XML Schema ; on utiliserait **`xsd:base64Binary`** ou **`xsd:hexBinary`**.
8. **`faultMessasge`** : coquille pour **`faultMessage`**.
9. **Notification et solicit-response** : ces deux modèles sont **définis par la norme WSDL 1.1** mais **rarement supportés** dans la pratique (les profils d'interopérabilité WS-I ne les autorisent pas).
10. **« Binding SOAP »** annoncé au plan : il n'a pas de diapositive dédiée ; il est illustré par l'**exemple HelloWorld** (section 6) — résumé en [section 13](#13-lélément-binding).
11. **Version** : le cours détaille **WSDL 1.1** ; WSDL 2.0 est cité en référence (voir [section 17](#17-wsdl-11-vs-wsdl-20-ajout)).

---

## 19. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **WSDL** | Langage **XML** de **description** d'un service web, **indépendant** du langage et de la plate-forme |
| **Comparaison** | Similaire à l'**IDL de CORBA** |
| **Spécification** | **W3C** : WSDL **1.1** (cours) et **2.0** |
| **Génération de code** | À partir du WSDL : **client** (stub) et **squelette serveur** |
| **UDDI** | **Annuaire** où le fournisseur **publie** ses WSDL et où le consommateur les **découvre** |
| **Flux SOA** | ① B publie → ② A interroge → ③ A télécharge le WSDL → ④ A invoque (SOAP) → ⑤ B répond (SOAP) |
| **Racine** | `<definitions>` |
| **Partie abstraite** | `<types>` + `<message>` + `<portType>` → **QUOI ?** |
| **Partie concrète** | `<binding>` + `<service>`/`<port>` → **COMMENT et OÙ ?** |
| **Pourquoi séparer ?** | **Réutilisabilité** de la partie abstraite (plusieurs protocoles / adresses) |
| **`<types>`** | Types en **XML Schema** (RELAX NG possible) ; facultatif si types de base ; peut être **importé** (`xsd:import`) |
| **`<message>` / `<part>`** | Message = ensemble de **parties** ; une partie ≈ **un paramètre** (`name` + `type`) |
| **`<portType>`** | ≈ **interface Java** : regroupe des `<operation>` |
| **`<operation>`** | ≈ **méthode Java** ; contient `<input>`, `<output>`, `<fault>` |
| **Surcharge** | Autorisée si les messages `input`/`output` **diffèrent** |
| **4 modèles** | **One-way** (input) · **Request/Response** (input + output) · **Notification** (output) · **Solicit-response** (output + input) |
| **`<fault>`** | Message d'**erreur** ≈ **exception** |
| **`<binding>`** | **Réalise** un `portType` pour un **protocole** (SOAP 1.1/1.2, HTTP GET/POST, MIME) |
| **Binding SOAP** | `soap:binding` (transport, **style rpc/document**), `soap:operation` (**soapAction**), `soap:body` (**use literal/encoded**) |
| **`<service>`** | Ensemble de **ports** |
| **`<port>`** | **Binding + adresse** (`soap:address location` / `http:address location`) |
| **Binding HTTP** | **GET** : `urlReplacement` ou `urlEncoded` · **POST** : `application/x-www-form-urlencoded` · sortie typée avec **`mime:content`** |
| **Outils** | **VS Code, Notepad++, Eclipse, NetBeans, IntelliJ, Visual Studio** · validation/test : **SoapUI** |

### Questions d'examen typiques
1. **Définir WSDL** et expliquer son rôle dans une architecture orientée services (**triangle fournisseur / consommateur / annuaire UDDI**).
2. Citer les **5 étapes** d'interaction entre l'entreprise A et l'entreprise B.
3. Citer les **sept concepts** d'un document WSDL et leur **équivalent Java**.
4. Quelle est la **différence entre partie abstraite et partie concrète** ? Pourquoi les séparer ?
5. Citer les **éléments racine et fils** d'un document WSDL 1.1 et leur **cardinalité**.
6. À quoi sert `<types>` ? Quand peut-il être **omis** ? Pourquoi **importer** un schéma XSD ?
7. Expliquer `<message>` et `<part>` ; donner les messages d'une opération à **trois paramètres**.
8. Différence entre `<portType>` et `<binding>` ; entre `<port>` et `<service>`.
9. Citer et illustrer les **quatre modèles d'opération** ; qui initie l'échange dans chacun ?
10. Dans quel cas peut-on **surcharger** une opération ? À quoi sert `parameterOrder` ?
11. **Écrire le WSDL** d'un service `HelloWorld` (deux opérations) et d'un service avec **type complexe** (`Person`).
12. Expliquer les éléments `soap:binding`, `soap:operation`, `soap:body` (**style rpc**, **use literal**).
13. Comment décrire un service **HTTP GET/POST** (retour d'image) ? Différence entre **urlReplacement**, **urlEncoded** et **POST**.
14. Quels **outils** pour construire et tester un WSDL ?

---

*Fin du résumé — WSDL : Décrire et configurer les services web.*
