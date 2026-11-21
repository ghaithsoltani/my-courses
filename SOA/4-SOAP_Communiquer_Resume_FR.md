# SOAP : Communiquer — Résumé complet du cours
**Cours :** SOA — Services web étendus
**Chapitre :** SOAP (*Simple Object Access Protocol*) — fichier « 04-soap »
**Support :** adapté par H. Hmida du cours de M. Baron (ISAE-ENSMA)
**Type de document :** Résumé d'étude avec explications, schémas, code XML/HTTP et conseils d'examen

---

## Table des matières

1. [Plan du cours et ressources](#1-plan-du-cours-et-ressources)
2. [Généralités SOAP](#2-généralités-soap)
3. [Comparaison avec RMI, DCOM et CORBA](#3-comparaison-avec-rmi-dcom-et-corba)
4. [Où est utilisé SOAP ? (le triangle SOA)](#4-où-est-utilisé-soap--le-triangle-soa)
5. [Do you speak SOAP ?](#5-do-you-speak-soap-)
6. [Concepts d'un message SOAP](#6-concepts-dun-message-soap)
7. [SOAP par l'exemple : le service HelloWorld](#7-soap-par-lexemple--le-service-helloworld)
8. [Structure d'un message SOAP](#8-structure-dun-message-soap)
9. [L'enveloppe SOAP](#9-lenveloppe-soap)
10. [L'en-tête SOAP (header)](#10-len-tête-soap-header)
11. [Le corps SOAP (body)](#11-le-corps-soap-body)
12. [Corps SOAP par l'exemple : le service Notebook](#12-corps-soap-par-lexemple--le-service-notebook)
13. [SOAP transporté par HTTP](#13-soap-transporté-par-http)
14. [Traitement des messages SOAP (architecture logicielle)](#14-traitement-des-messages-soap-architecture-logicielle)
15. [L'outil SoapUI](#15-loutil-soapui)
16. [SOAP 1.1 vs SOAP 1.2 (ajout)](#16-soap-11-vs-soap-12-ajout)
17. [Remarques sur le support de cours](#17-remarques-sur-le-support-de-cours)
18. [Fiche de révision rapide](#18-fiche-de-révision-rapide)

---

## 1. Plan du cours et ressources

### Plan du cours
1. **Généralités SOAP**
2. **SOAP par l'exemple :** service **HelloWorld**
3. **Structure d'un message SOAP**
4. **En-tête d'un message SOAP** (*header*)
5. **Corps d'un message SOAP** (*body*)
6. **SOAP et le transport HTTP**
7. **Outil SoapUI**

### Ressources citées

| Catégorie | Références |
|---|---|
| **Spécifications / articles** | `w3.org/TR/soap/` · IBM developerWorks (x-soapbx2) · `soapui.org` · Wikipédia FR « SOAP » |
| **Cours en ligne** | W3Schools (soap) · apiacoa.org (PDF SOAP) · javapassion.com (SOAP Basics) · mbaron.developpez.com |
| **Livres** | *Programming Web Services with SOAP* (Snell, Tidwell, Kulchenko — O'Reilly, déc. 2001) · *Java and SOAP* (Englander — O'Reilly, mai 2002) · *Understanding Web Services: WSDL, SOAP…* (Newcomer — Addison-Wesley, mai 2002) |

> 🔗 **Lien avec le chapitre précédent :** après **WSDL** (le *contrat*), ce chapitre traite **SOAP**, le **protocole de communication** utilisé pour **invoquer** les opérations décrites dans le WSDL.

---

## 2. Généralités SOAP

### Définition
> **SOAP est un protocole de communication entre applications, basé sur le langage XML.**

| Point | Détail |
|---|---|
| **Acronyme d'origine** | **Simple Object Access Protocol** *(le nom complet est aujourd'hui peu utilisé, SOAP étant devenu un nom propre)* |
| **Porté par** | **Microsoft et IBM** |
| **Spécification** | **W3C** : SOAP **1.1** (2000) et SOAP **1.2** |
| **Comparable à** | Les protocoles **« RPC »** *(voir le chapitre RPC)* |

### Objectifs visés

| Objectif | Portée |
|---|---|
| **Communication intra-entreprise** | Entre applications d'une **même entreprise** (**intranet**) |
| **Échanges interentreprises** | Entre **applications et services web** de différentes organisations |

---

## 3. Comparaison avec RMI, DCOM et CORBA

| Critère | **RMI** | **DCOM** | **CORBA** | **SOAP** |
|---|---|---|---|---|
| **Défini par** | **SUN** | **Microsoft** | **OMG** | **W3C** |
| **Plate-forme** | **Multi** | **Win32** (Windows uniquement) | **Multi** | **Multi** |
| **Langage de développement** | **Java** | **C++, VB, VJ** | **Multi** | **Multi** |
| **Langage de définition** | **Java** | **ODL** | **IDL** | **WSDL** |
| **Transport** | **TCP, HTTP, IIOP** | **IP/IPX** | **GIOP, IIOP** | **HTTP, HTTPR, SMTP** |
| **Transaction** | **Non** | **Oui** | **Oui** | **Oui** |
| **Sécurité** | **SSL, JAAS** | **?** (non précisé) | **SSL** | **SSL** |

> 🔑 **Le grand atout de SOAP :** il est **multi-langages, multi-plateformes**, et s'appuie sur **HTTP**, un transport **universel** (contrairement à IIOP ou IP/IPX). C'est ce qui en a fait un standard largement adopté.

---

## 4. Où est utilisé SOAP ? (le triangle SOA)

*(Même schéma que dans le chapitre WSDL — rappel dans le contexte de SOAP.)*

```
                         Annuaire UDDI
                        ╱             ╲
          ② A interroge ╱               ╲ ① B publie ses services
            l'annuaire                    web (transmet les WSDL)
                      ╱                   ╲
            Entreprise A ───────────────── Entreprise B
          (consommatrice)  ④ messages SOAP   (fournisseur)
                           ⑤ réponses SOAP
        ③ A télécharge les documents WSDL
```

| # | Étape |
|---|---|
| **1** | **B publie** ses services web qu'elle implémente dans un **annuaire UDDI** (transmet les **WSDL**) |
| **2** | **A interroge l'annuaire UDDI** pour obtenir une liste de services web répondant à ses **exigences** |
| **3** | **A télécharge les documents** qui décrivent les services web (**WSDL**) |
| **4** | **A envoie des messages SOAP conformes aux WSDL** pour **invoquer** les services web de B |
| **5** | **B répond** en retournant des **messages SOAP conformes aux WSDL** |

> 🔑 **Rôle précis de SOAP dans ce schéma :** c'est le **format des messages** échangés aux étapes **4 et 5** — le **contrat (WSDL)** dit *quoi* appeler, **SOAP** est *comment* on l'appelle concrètement.

---

## 5. Do you speak SOAP ?

### Prérequis pour lire et écrire du SOAP
- **XML**
- **XML Schema**

### Le pour (avantages de connaître le format bas niveau)

| Avantage | Explication |
|---|---|
| **Débogage** | Utile pour **débugger une application** |
| **Tests** | Utile pour réaliser des **tests via SoapUI** |
| **Interception** | **Intercepter les messages bas niveau SOAP** (via des **handlers**) |

### Le contre

| Limite | Explication |
|---|---|
| **Abstraction fournie par les API** | Les **API** (JAX-WS, par exemple) fournissent une **abstraction des messages SOAP** : en pratique, on **manipule rarement le XML à la main** |

> 💡 *(Ajout)* En développement courant, on utilise des **outils de génération** (à partir du WSDL) qui créent des **classes Java** : le développeur appelle une **méthode Java normale**, et c'est l'**API** qui construit le message SOAP en coulisses. Connaître la structure XML reste utile pour **déboguer** ou **écrire des tests**.

---

## 6. Concepts d'un message SOAP

| Concept | Explication |
|---|---|
| **Requête / réponse** | Les messages SOAP servent à **envoyer** (requête) et **recevoir** (réponse) des informations d'un récepteur |
| **Récepteurs intermédiaires** | Un message SOAP peut être transmis à **plusieurs récepteurs intermédiaires** avant d'être reçu par le **récepteur final** (**pattern « chaîne de responsabilité »**) |
| **Gestion des erreurs** | Le format SOAP peut contenir des **messages spécifiques** correspondant à des **erreurs identifiées par le récepteur** |
| **Transport** | Un message SOAP est **véhiculé** vers le récepteur via un **protocole de transport** (**HTTP, SMTP**…) |

> 🔗 **« Chaîne de responsabilité »** *(patron de conception, ajout)* : chaque intermédiaire **traite une partie** du message (souvent dans l'**en-tête**) puis **transmet** le reste au suivant, jusqu'au récepteur final. C'est développé en [section 10](#10-len-tête-soap-header).

---

## 7. SOAP par l'exemple : le service HelloWorld

> 🔗 Reprend le service **HelloWorld** du chapitre WSDL (deux opérations : `makeHelloWorld` et `simpleHelloWorld`).

### 7.1 Requête : appel de `makeHelloWorld` (avec un paramètre)

```xml
<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:hel="http://helloworldwebservice.com/">
    <soapenv:Header/>
    <soapenv:Body>
        <hel:makeHelloWorld>
            <value>Mickael BARON</value>
        </hel:makeHelloWorld>
    </soapenv:Body>
</soapenv:Envelope>
```
→ **Message SOAP pour appeler l'opération `makeHelloWorld`**, contenant **un paramètre `value`**.

### 7.2 Requête : appel de `simpleHelloWorld` (sans paramètre)

```xml
<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:hel="http://helloworldwebservice.com/">
    <soapenv:Header/>
    <soapenv:Body>
        <hel:simpleHelloWorld/>
    </soapenv:Body>
</soapenv:Envelope>
```
→ **Message SOAP pour appeler l'opération `simpleHelloWorld`**, **ne contenant pas de paramètre**.

### 7.3 Réponses

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Body>
        <ns2:makeHelloWorldResponse xmlns:ns2="http://helloworldwebservice.com/">
            <helloWorldResult>Hello World to Mickael BARON</helloWorldResult>
        </ns2:makeHelloWorldResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Body>
        <ns2:simpleHelloWorldResponse xmlns:ns2="http://helloworldwebservice.com/">
            <helloWorldResult>Hello World to everybody</helloWorldResult>
        </ns2:simpleHelloWorldResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

> 🔑 **Les réponses sont sensiblement identiques** : le nom de l'opération est suivi de **`Response`**, et le résultat est placé dans un élément enfant (ici **`helloWorldResult`**).

### 7.4 Ce qu'on observe déjà

| Observation | Explication |
|---|---|
| **Pas de `Header`** dans les réponses | Le header est **facultatif** ; ici il n'est présent que côté requête |
| **Préfixe `ns2` au lieu de `hel`** | Le préfixe de namespace est **arbitraire** ; seul le **namespace lui-même** (l'URI) compte |
| **Paramètre `value` vs élément vide** | Une opération **sans paramètre** d'entrée a un **élément auto-fermant** (`<hel:simpleHelloWorld/>`) |

---

## 8. Structure d'un message SOAP

> **Un message SOAP est un document XML** constitué d'une **enveloppe** composée de **deux parties** :
> 1. un **en-tête (header)**, **facultatif** ;
> 2. un **corps (body)**.

```
 ┌───────────────────────────────────┐
 │         Enveloppe SOAP             │
 │  ┌───────────────────────────────┐ │
 │  │   En-tête SOAP (facultatif)   │ │
 │  └───────────────────────────────┘ │
 │  ┌───────────────────────────────┐ │
 │  │    Corps du message SOAP       │ │
 │  │           (body)               │ │
 │  └───────────────────────────────┘ │
 └───────────────────────────────────┘
```

---

## 9. L'enveloppe SOAP

| Point | Détail |
|---|---|
| **Racine** | L'enveloppe est la **racine** d'un message SOAP, identifiée par la balise **`<soapenv:Envelope>`** |
| **Namespaces obligatoires** | La spécification impose que la balise et les sous-balises soient **explicitement associées à un namespace** |
| **Deux namespaces définis par SOAP** | **`SOAP-ENV`** (ou `soapenv`) : `http://schemas.xmlsoap.org/soap/envelope/` · **`SOAP-ENC`** : `http://schemas.xmlsoap.org/soap/encoding/` |
| **Requête = réponse** | La **requête et la réponse ont la même structure** |

### Squelette requête / réponse

```xml
<!-- Message SOAP d'une REQUÊTE -->
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope">
    <SOAP-ENV:Body>
        <!-- Contenu de la Requête -->
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>

<!-- Message SOAP d'une RÉPONSE -->
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope">
    <SOAP-ENV:Body>
        <!-- Contenu de la Réponse -->
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

## 10. L'en-tête SOAP (header)

### 10.1 Rôle
- L'en-tête sert à **transmettre des informations supplémentaires** sur le **même message**.
- Défini par la balise **`<SOAP-ENV:Header>`**.
- **Facultatif**.
- Doit être **placé avant le corps**.

### 10.2 Usages de l'en-tête

| Usage | Exemple |
|---|---|
| **Authentifier l'émetteur** | Jeton, identifiants |
| **Contexte d'une transaction** | Identifiant de transaction distribuée |
| **Identification pour certains transports** | Pour **FTP** par exemple, l'en-tête peut identifier l'**émetteur du message** |

### 10.3 Transmission par chaîne de récepteurs intermédiaires

Un message SOAP peut **transiter par plusieurs intermédiaires** avant le traitement par le **récepteur final** (**pattern « Chaîne de responsabilité »**).

```
                   Émetteur du message SOAP
                            │
       Header : {balise1 (Actor=next), balise2, balise3}
                            │
                            ▼
                  Récepteur intermédiaire n°1
         ── traite balise1, la retire, transmet le reste ──
                            │
          Header restant : {balise2 (Actor=next), balise3}
                            │
                            ▼
                  Récepteur intermédiaire n°2
         ── traite balise2, la retire, transmet le reste ──
                            │
               Header restant : {balise3 (Actor=next)}
                            │
                            ▼
                 Récepteur final du message SOAP
                     ── traite balise3 ──
```

### 10.4 Attributs conventionnels pour la chaîne

| Attribut | Rôle |
|---|---|
| **`SOAP-ENV:Actor`** | Indique **à qui s'adresse** cet élément d'en-tête (ex. **`http://schemas.xmlsoap.org/soap/actor/next`** = « au **prochain** récepteur ») |
| **`SOAP-ENV:mustUnderstand`** | Si à **`1`**, le récepteur **doit comprendre et traiter** cet élément, sinon il doit **renvoyer une erreur** |

> 🔑 **Principe :** chaque **récepteur intermédiaire** ne s'occupe **que des éléments d'en-tête qui lui sont destinés** (`Actor="…/next"`), les **traite**, les **retire**, puis **transmet le reste** au maillon suivant. Le **contenu du corps**, lui, **reste inchangé tout au long de la chaîne**.

---

## 11. Le corps SOAP (body)

### 11.1 Rôle général

L'élément **`<SOAP-ENV:Body>`** peut contenir :

| Contenu | Cas |
|---|---|
| **Une erreur** en réponse à une requête | Élément **`<SOAP-ENV:Fault>`** |
| **Des informations adressées au destinataire** | Respectant un **encodage déterminé** |

> **L'encodage des informations est précisé par les *bindings* du document WSDL** *(voir le chapitre WSDL)* :
> - attribut **`style`** : **`Document`** ou **`RPC`** ;
> - attribut **`use`** : **`encoded`** ou **`literal`**.

> 📌 *« Pour faire simple, nous utiliserons les services web dans le cadre de l'appel à une procédure distante »* (style **RPC**).

### 11.2 Conventions du style RPC

> **L'objectif visé par SOAP** a été de fournir un **mécanisme standardisé pour l'appel de procédures distantes (RPC)**. Les informations adressées au destinataire doivent donc respecter certaines **conventions** :

**Pour l'appel (requête) :**
1. L'opération est représentée par une **structure** (*struct*).
2. Le **nom de la structure** est celui de l'**opération à appeler**.
3. Chaque **paramètre** de l'opération est un **sous-élément** de la structure.
4. Si un paramètre est un **type complexe** (ex. `Person`), une **nouvelle structure** est définie, contenant **à son tour des sous-éléments**.

**Pour le résultat (réponse) :**
1. Le résultat est **également** représenté par une **structure**.
2. Le **nom de la structure** est celui de l'**opération suivi de `Response`**.
3. Les paramètres (de sortie) sont **également structurés**.

---

## 12. Corps SOAP par l'exemple : le service Notebook

> 🔗 Reprend le service **Notebook** (carnet d'adresses) du chapitre WSDL.

### 12.1 Appel avec un paramètre de type complexe

```xml
<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:not="http://notebookwebservice.mickaelbaron.fr/">
    <soapenv:Header/>
    <soapenv:Body>
        <not:addPersonWithComplexType>
            <newPerson>
                <address>Poitiers</address>
                <birthyear>17081976</birthyear>
                <name>BARON Mickael</name>
            </newPerson>
        </not:addPersonWithComplexType>
    </soapenv:Body>
</soapenv:Envelope>
```
→ **Message SOAP pour appeler l'opération `addPersonWithComplexType`** ; le **paramètre de type complexe** (`newPerson`) est défini dans une **structure**.

### 12.2 Appel avec trois paramètres simples

```xml
<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:not="http://notebookwebservice.mickaelbaron.fr/">
    <soapenv:Header/>
    <soapenv:Body>
        <not:addPersonWithSimpleType>
            <name>BARON Mickael</name>
            <address>Poitiers</address>
            <birthyear>17081976</birthyear>
        </not:addPersonWithSimpleType>
    </soapenv:Body>
</soapenv:Envelope>
```
→ **Message SOAP pour appeler `addPersonWithSimpleType`**, avec **trois paramètres** directement en sous-éléments.

### 12.3 Réponses correspondantes

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Body>
        <ns2:addPersonWithComplexTypeResponse
             xmlns:ns2="http://notebookwebservice.mickaelbaron.fr/">
            <addPersonWithComplexTypeResult>true</addPersonWithComplexTypeResult>
        </ns2:addPersonWithComplexTypeResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Body>
        <ns2:addPersonWithSimpleTypeResponse
             xmlns:ns2="http://notebookwebservice.mickaelbaron.fr/">
            <addPersonWithSimpleTypeResult>true</addPersonWithSimpleTypeResult>
        </ns2:addPersonWithSimpleTypeResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

> 🔑 **Messages SOAP pour la réponse**, puisque les noms des opérations sont **suivis de `Response`**.
> **Les paramètres de sortie suivent la même convention que les paramètres d'entrée** (structure nommée, sous-éléments).

### 12.4 Table récapitulative des 4 messages

| Message | Opération | Nombre de paramètres | Structure racine du body |
|---|---|---|---|
| Requête 1 | `addPersonWithComplexType` | **1** (objet `Person`) | `<not:addPersonWithComplexType>` → `<newPerson>` |
| Requête 2 | `addPersonWithSimpleType` | **3** (chaînes) | `<not:addPersonWithSimpleType>` |
| Réponse 1 | `addPersonWithComplexTypeResponse` | 1 (booléen) | `<ns2:addPersonWithComplexTypeResponse>` → `<addPersonWithComplexTypeResult>` |
| Réponse 2 | `addPersonWithSimpleTypeResponse` | 1 (booléen) | `<ns2:addPersonWithSimpleTypeResponse>` → `<addPersonWithSimpleTypeResult>` |

---

## 13. SOAP transporté par HTTP

### 13.1 Principe

- SOAP utilise un **protocole de transport** pour véhiculer les messages de l'**émetteur** au **récepteur** : **HTTP, SMTP, FTP, POP3, NNTP**.
- Le modèle **requête/réponse de SOAP** convient **parfaitement** au modèle **requête/réponse de HTTP**.

```
 ┌───────────────────────────────────┐
 │           En-tête HTTP             │
 ├───────────────────────────────────┤
 │             Corps HTTP             │
 │  ┌───────────────────────────────┐ │
 │  │   En-tête SOAP (facultatif)   │ │   (contenu étudié précédemment)
 │  ├───────────────────────────────┤ │
 │  │  Corps du message SOAP (body)  │ │
 │  └───────────────────────────────┘ │
 │          Enveloppe SOAP             │
 └───────────────────────────────────┘
      Requête / Réponse HTTP
```

→ Le message SOAP (enveloppe) est simplement **placé dans le corps** de la requête ou de la réponse **HTTP**.

### 13.2 Requête SOAP sur HTTP (exemple)

```http
POST http://localhost:8080/NotebookWebService/notebook HTTP/1.1
Content-Type: text/xml;charset=UTF-8
SOAPAction: ""
User-Agent: Jakarta Commons-HttpClient/3.1
Host: localhost:8080
Content-Length: 459

<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:not="http://notebookwebservice.mickaelbaron.fr/">
    <soapenv:Header/>
    <soapenv:Body>
        <not:addPersonWithComplexType>
            <newPerson>
                <address>Poitiers</address>
                <birthyear>17081976</birthyear>
                <name>BARON Mickael</name>
            </newPerson>
        </not:addPersonWithComplexType>
    </soapenv:Body>
</soapenv:Envelope>
```

**Points clés :**

| Point | Détail |
|---|---|
| **Méthode** | **POST** (toujours, quelle que soit l'opération appelée) |
| **En-tête `SOAPAction`** | **Nécessaire** (peut être vide `""`, ou contenir l'URI de l'action) |
| **`Content-Type`** | `text/xml;charset=UTF-8` |

### 13.3 Réponse SOAP sur HTTP (exemple)

```http
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: text/xml;charset=utf-8
Transfer-Encoding: chunked
Date: Sun, 13 Dec 2009 12:00:33 GMT

<?xml version='1.0' encoding='UTF-8'?>
<soapenv:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Body>
        <ns2:addPersonWithComplexTypeResponse
             xmlns:ns2="http://notebookwebservice.mickaelbaron.fr/">
            <addPersonWithComplexTypeResult>true</addPersonWithComplexTypeResult>
        </ns2:addPersonWithComplexTypeResponse>
    </soapenv:Body>
</soapenv:Envelope>
```

**Points clés :**

| Point | Détail |
|---|---|
| **Codes retour HTTP exploités** | **SOAP exploite les codes retour HTTP** |
| **Code 2xx** | Message **SOAP reçu** (traitement réussi) |
| **Code 500** | Message en **erreur** : le **corps SOAP doit contenir un `Fault`** |

---

## 14. Traitement des messages SOAP (architecture logicielle)

Schéma du cours : le parcours d'un message SOAP **à l'intérieur du serveur**, depuis la réception HTTP jusqu'à l'exécution du code métier.

```
①  Réception d'un message SOAP via le transport HTTP
                        │
             [ Couche de transport HTTP ]  ◄── « Conteneur Web »
                        │
②  La partie HTTP est nettoyée (on ne garde que l'enveloppe SOAP)
                        │
                 [   API SOAP   ]            ◄── « Framework service web »
                        │
③  Invocation de la méthode en fonction de l'opération SOAP
                        │
     [ Couche d'accès aux implémentations des services web ]
                        │
          ┌──────┬──────┬──────┬──────┐
         Java    .NET   C++    PHP        ← différents langages
```

| Étape | Qui agit ? | Ce qui se passe |
|---|---|---|
| **① Réception** | **Conteneur Web** | Le message SOAP **arrive via HTTP** (ex. un serveur d'applications) |
| **② Nettoyage** | **Couche de transport HTTP** | La **partie HTTP est retirée** : il ne reste que l'**enveloppe SOAP** |
| **③ Invocation** | **API SOAP** (framework service web) | **Invocation de la méthode** correspondant à l'**opération SOAP** demandée |
| **Exécution** | **Couche d'accès aux implémentations** | Appel du **code métier réel**, quel que soit son **langage** (Java, .NET, C++, PHP…) |

> 🔑 **Ce schéma montre concrètement l'intérêt de SOAP :** le message est **indépendant du langage** d'implémentation du service — la **même requête SOAP** peut être traitée par un service écrit en **Java, .NET, C++ ou PHP**.

---

## 15. L'outil SoapUI

### 15.1 Présentation
- **SoapUI** est un **outil pour tester des services web** (`www.soapui.org`).
- Disponible en **standalone** ou **intégré** dans des environnements de développement : **Eclipse, IntelliJ, NetBeans, Maven**…
- **Peut s'utiliser pour n'importe quelle plateforme de développement.**

### 15.2 Fonctionnalités

| Fonctionnalité | Description |
|---|---|
| **Support des deux familles** | **Services web étendus (WSDL + SOAP + UDDI)** ou **REST** |
| **Inspecter** des services web | Explorer les opérations disponibles |
| **Invoquer** des services web | Envoyer des requêtes et voir les réponses |
| **Développer** des services web | Aide à la construction |
| **Simuler des services web** | Via des **bouchons (mocks)** |
| **Tests de qualité** | **Temps de réponse**, performance |

### 15.3 Interface (schéma du cours)

| Zone de l'interface | Contenu |
|---|---|
| **Panneau de gauche** | **Les projets** regroupant les **appels aux services web** |
| **Panneau central/droit** | **Les messages SOAP** (requête et réponse) |
| **Panneau du bas** | **Les logs de SoapUI** |

> 💡 *(Ajout)* Dans un projet SoapUI, on **importe un WSDL** ; l'outil **génère automatiquement** une requête SOAP **pour chaque opération** du service, qu'il suffit alors de **remplir et d'envoyer**.

---

## 16. SOAP 1.1 vs SOAP 1.2 (ajout)

> ⚠️ **Section ajoutée** : le cours cite les deux versions en référence mais détaille surtout la **structure générale**, compatible avec les deux. Voici les différences utiles pour l'examen.

| | **SOAP 1.1** | **SOAP 1.2** |
|---|---|---|
| **Statut** | Note **W3C** (2000) | **Recommandation W3C** (standard à part entière) |
| **Namespace de l'enveloppe** | `.../soap/envelope/` | `.../soap-envelope` *(différent)* |
| **`Content-Type` HTTP** | `text/xml` | **`application/soap+xml`** |
| **`SOAPAction`** | **Obligatoire** en en-tête HTTP séparé | **Facultatif**, devient un **paramètre** du `Content-Type` |
| **Gestion des erreurs** | `<Fault>` avec `faultcode`, `faultstring` | `<Fault>` restructuré (`Code`, `Reason`, plus **normalisé**) |
| **`mustUnderstand` / `Actor`** | `Actor` | Renommé **`role`** |

---

## 17. Remarques sur le support de cours

1. **Acronyme SOAP :** officiellement, depuis **SOAP 1.2**, le sigle **n'est plus développé** (ce n'est plus formellement un acronyme) ; le cours rappelle l'origine historique *Simple Object Access Protocol*.
2. **« HTTPR »** (tableau de comparaison, section 3) : désigne **HTTP Reliable** (une extension pour la **fiabilité du transport**), peu utilisée en pratique aujourd'hui.
3. **Sécurité DCOM « ? »** : le support **laisse volontairement un point d'interrogation** — DCOM utilisait principalement l'**authentification Windows (NTLM)**, moins standardisée que SSL.
4. **`SOAPAction: ""`** dans l'exemple HTTP : une **chaîne vide** est une valeur **valide** ; certains frameworks l'utilisent systématiquement même si plusieurs opérations existent (c'est alors **le corps du message** qui détermine l'opération réellement appelée).
5. **Style RPC uniquement :** le cours **simplifie volontairement** en se concentrant sur le style **RPC** (*« pour faire simple »*) ; le style **Document**, plus flexible mais plus complexe, **n'est pas détaillé**.
6. **Pas d'exemple de `<Fault>` dans ce PDF** : le concept est **mentionné** (section 11, section 13) mais **aucun exemple XML** de message d'erreur n'est montré *(contrairement à ce qu'on pourrait attendre)*.
7. **Namespace Notebook :** `http://notebookwebservice.mickaelbaron.fr/` (du cours de référence M. Baron, ISAE-ENSMA), différent du `http://www.notebookwebservice.com/` utilisé dans le chapitre WSDL — **même service**, **URI de namespace différente** selon la source.

---

## 18. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **SOAP** | Protocole de **communication entre applications**, basé sur **XML** |
| **Acronyme** | *Simple Object Access Protocol* |
| **Porté par** | **Microsoft et IBM** ; normalisé par le **W3C** |
| **Comparable à** | Les protocoles **RPC** |
| **Objectifs** | Communication **intranet** et **interentreprises** |
| **Face à RMI/DCOM/CORBA** | SOAP = **multi-plateforme, multi-langage**, transport **HTTP** |
| **Rôle dans le triangle SOA** | Format des **messages 4 et 5** (invocation / réponse) |
| **Prérequis** | **XML**, **XML Schema** |
| **Message SOAP** | Sert à **envoyer/recevoir** ; peut transiter par des **intermédiaires** ; transporté par **HTTP, SMTP**… |
| **Structure** | **Enveloppe** = **en-tête (facultatif)** + **corps** |
| **Enveloppe** | Balise racine **`<soapenv:Envelope>`** ; namespaces **SOAP-ENV** et **SOAP-ENC** |
| **En-tête** | Infos **supplémentaires** (authentification, transaction) ; **avant le corps** ; attributs **`Actor`** et **`mustUnderstand`** |
| **Chaîne de responsabilité** | Chaque intermédiaire traite **les éléments qui lui sont adressés** (`Actor=".../next"`) |
| **Corps** | Contient soit une **erreur (`Fault`)**, soit les **données** (encodage précisé par le **binding WSDL** : `style` RPC/Document, `use` encoded/literal) |
| **Convention RPC — requête** | Structure nommée **comme l'opération** ; paramètres = sous-éléments ; type complexe = sous-structure |
| **Convention RPC — réponse** | Structure nommée **opération + `Response`** ; résultat structuré |
| **Transport** | **HTTP (POST)**, SMTP, FTP, POP3, NNTP |
| **Requête HTTP** | **POST** + en-tête **`SOAPAction`** obligatoire + `Content-Type: text/xml` |
| **Réponse HTTP** | Codes **2xx** = succès ; code **500** = erreur, corps contient `Fault` |
| **Traitement serveur** | Réception HTTP → **nettoyage** de la partie HTTP → **API SOAP** → invocation de la **méthode métier** (Java, .NET, C++, PHP…) |
| **SoapUI** | Outil de **test** de services web (SOAP et REST) : inspecter, invoquer, développer, simuler (mocks), tester la qualité |
| **SOAP 1.1 vs 1.2** | 1.2 = **recommandation W3C**, `Content-Type` dédié, `SOAPAction` facultatif, `Fault` restructuré, `Actor`→`role` |

### Questions d'examen typiques
1. **Définir SOAP** et expliquer d'où vient son nom.
2. Comparer **SOAP, RMI, DCOM et CORBA** selon : qui l'a défini, la plate-forme, le langage, le transport.
3. Expliquer le **rôle de SOAP** dans le triangle **fournisseur / consommateur / annuaire UDDI**.
4. Quels sont les **prérequis** pour lire et écrire du SOAP ? Quels sont les **avantages et limites** de les connaître ?
5. Que peut-on dire des **messages SOAP** (requête/réponse, intermédiaires, erreurs, transport) ?
6. Décrire la **structure générale** d'un message SOAP (enveloppe, en-tête, corps).
7. À quoi servent les **namespaces `SOAP-ENV` et `SOAP-ENC`** ?
8. À quoi sert l'**en-tête SOAP** ? Expliquer les attributs **`Actor`** et **`mustUnderstand`** avec un exemple de **chaîne de récepteurs**.
9. Que peut contenir le **corps SOAP** ? Quelle est la différence entre les attributs **`style`** et **`use`** du binding WSDL ?
10. Expliquer les **conventions RPC** pour représenter une **requête** et une **réponse** SOAP (exemple avec un type complexe).
11. **Écrire** le message SOAP pour appeler une opération à **trois paramètres simples**, puis sa **réponse**.
12. Comment SOAP est-il **transporté par HTTP** ? Quelle **méthode HTTP** est utilisée ? Quel est le rôle de **`SOAPAction`** ?
13. Comment un serveur **exploite-t-il les codes retour HTTP** pour SOAP ? Que doit contenir le corps en cas d'**erreur 500** ?
14. Décrire les **étapes de traitement** d'un message SOAP côté serveur (réception, nettoyage, invocation).
15. Quelles sont les **fonctionnalités de SoapUI** ?

---

*Fin du résumé — SOAP : Communiquer.*
