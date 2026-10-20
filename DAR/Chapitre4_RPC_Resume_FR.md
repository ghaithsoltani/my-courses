# Chapitre 4 — RPC : Remote Procedure Calls (appel de procédure à distance)
**Cours :** Développement des systèmes répartis
**Enseignant :** Mahran Farhat
**Type de document :** Résumé d'étude avec explications, schémas, tableaux comparatifs et conseils d'examen

---

## Table des matières

1. [Plan du chapitre](#1-plan-du-chapitre)
2. [Solutions middleware](#2-solutions-middleware)
3. [Les applications distribuées](#3-les-applications-distribuées)
4. [Panorama des technologies d'accès](#4-panorama-des-technologies-daccès)
5. [Introduction au RPC](#5-introduction-au-rpc)
6. [Définition du RPC](#6-définition-du-rpc)
7. [Pourquoi les RPC ?](#7-pourquoi-les-rpc-)
8. [Principe de fonctionnement](#8-principe-de-fonctionnement)
9. [Les talons (stubs) : client et serveur](#9-les-talons-stubs--client-et-serveur)
10. [Déroulement détaillé d'un appel RPC (points A à E)](#10-déroulement-détaillé-dun-appel-rpc-points-a-à-e)
11. [Rôle des modules : talons et transport](#11-rôle-des-modules--talons-et-transport)
12. [Problèmes courants](#12-problèmes-courants)
13. [Types de pannes et mécanismes de reprise](#13-types-de-pannes-et-mécanismes-de-reprise)
14. [Représentation des données](#14-représentation-des-données)
15. [Désignation et liaison](#15-désignation-et-liaison)
16. [Points forts du RPC](#16-points-forts-du-rpc)
17. [Points faibles du RPC](#17-points-faibles-du-rpc)
18. [Exemple de code (ajout)](#18-exemple-de-code-ajout)
19. [Remarques sur le support de cours](#19-remarques-sur-le-support-de-cours)
20. [Fiche de révision rapide](#20-fiche-de-révision-rapide)

---

## 1. Plan du chapitre

1. Solutions **middleware**
2. **Applications distribuées**
3. **Introduction** au RPC
4. **Définition** du RPC
5. **Principe de fonctionnement**
6. **Problèmes courants**
7. **Types de pannes**
8. **Représentation des données**
9. **Points forts et points faibles**

> 📌 Le support du cours (cours, TP, projets) est partagé sur Google Drive par l'enseignant.

---

## 2. Solutions middleware

Deux applications sur **deux machines** communiquent via **TCP/IP ou UDP**. Pour cela, on utilise une **technologie d'accès** (un *middleware*) :

```
 ┌────────────┐      TCP/IP ou UDP      ┌────────────┐
 │ Machine 1  │ ◄─────────────────────► │ Machine 2  │
 │ Application1│                        │ Application2│
 └────────────┘                         └────────────┘
```

| Technologie d'accès | Particularité |
|---|---|
| **Sockets** | Bas niveau, flux d'octets |
| **RMI** (Java) | Objets distribués, **Java uniquement** |
| **CORBA** | **Multi-langages** |
| **EJB** (J2EE) | Composants métier distribués |
| **Web Services** (HTTP + XML) | **Multi-langages, multi-plateformes** |

> 💡 *Middleware* = couche logicielle **intermédiaire** entre les applications et le réseau, qui **masque la complexité** des communications.

---

## 3. Les applications distribuées

### Définition

> Une **application distribuée** est une application dont les **classes sont réparties sur plusieurs machines différentes**. On peut y **invoquer des méthodes à distance** : il est possible d'appeler les méthodes d'un **objet qui n'est pas situé sur la machine locale**.

### Architecture type (schéma du cours : deux entreprises)

Chaque entreprise possède une **architecture en couches** :

| Couche | Rôle |
|---|---|
| **Client HTTP** | Le navigateur de l'utilisateur |
| **Couche web** | Interface web (accessible en **HTTP**) |
| **Couche service** | Expose les services — **reliée aux middlewares** (RMI, CORBA, EJB, Web Services) |
| **Couche métier** | Logique de l'application |
| **Couche DAO** | Accès aux données (*Data Access Object*) |
| **SGBD** | Base de données |

→ Les **deux entreprises** communiquent **entre leurs couches service**, via un middleware, par-dessus **TCP/IP ou UDP**.

---

## 4. Panorama des technologies d'accès

### 4.1 RPC — Remote Procedure Calls
- Déjà dans le **langage C**, on pouvait faire de l'**invocation à distance** avec RPC.
- RPC est orienté **« structure de données »** : il **ne suit pas le modèle orienté objet**.

### 4.2 RMI — Remote Method Invocation
- **Middleware** permettant d'utiliser des **objets sur des JVM distantes**.
- Va **plus loin que RPC** : il envoie non seulement les **données** d'un objet, mais aussi ses **méthodes**, en partie grâce à la **sérialisation** des objets.
- Sert à créer des applications distribuées **développées en Java**.

### 4.3 CORBA — Common Object Request Broker Architecture
- **Standard d'objets répartis**, **non lié à Java**.
- Conçu par l'**OMG** (*Object Management Group*), un **consortium de 700 entreprises** (dont Sun).
- **Grand intérêt :** fonctionne avec **plusieurs langages**.
- **Inconvénient :** **plus lourd à mettre en place**.

### 4.4 EJB — Enterprise Java Beans
- Avec les serveurs d'applications **J2EE**, Sun crée les **EJB** : composants métier **distribués**.
- Technologie **dédiée aux applications J2EE**.
- Les EJB **utilisent RMI et CORBA**.

### 4.5 Web Services (protocole SOAP = HTTP + XML)
- Nés du développement du **web** et de **XML**.
- Permettent des applications distribuées **multi-langages et multi-plateformes**.
- **Plus faciles à mettre en œuvre.**
- Exploitent deux concepts : **XML** et **HTTP**.

### 4.6 Comparaison

| Technologie | Langages | Orienté objet ? | Complexité | Cible |
|---|---|---|---|---|
| **RPC** | **C** (à l'origine) | ❌ Non (structures de données) | Faible | Appel de fonctions distantes |
| **RMI** | **Java uniquement** | ✅ Oui (sérialisation, méthodes) | Moyenne | Applications Java distribuées |
| **CORBA** | **Multi-langages** | ✅ Oui | **Élevée** (lourd) | Systèmes hétérogènes |
| **EJB** | Java (J2EE) | ✅ Composants | Moyenne à élevée | Composants métier d'entreprise |
| **Web Services** | **Multi-langages, multi-plateformes** | Indifférent | **Faible** (XML + HTTP) | Interopérabilité sur le web |

> 🔑 **Point commun, à retenir :** **toutes ces technologies de création d'applications collaboratives s'appuient sur les SOCKETS.**

---

## 5. Introduction au RPC

### 5.1 L'appel de procédure local

La **primitive de base** d'interaction entre éléments logiciels est l'**appel d'une procédure ou d'une fonction**, par exemple :

```c
int resultat;
resultat = calculPuissance(2, 3);
printf("2 à la puissance 3 = %d\n", resultat);
```

> `calculPuissance` est appelée **localement** : son code est **intégré dans l'exécutable** compilé, ou **chargé dynamiquement** au lancement (**librairie dynamique**).

### 5.2 Les sockets TCP et UDP

- Communication par **envoi et réception de blocs de données**.
- **Bien plus bas niveau** qu'un appel de fonction.

### 5.3 Schéma de principe

- L'**unité centrale (UC)** de la machine **M1** ne peut pas exécuter d'opérations sur la **mémoire centrale (MC)** de **M2**.
- Depuis le **processus P1** (machine M1), on a besoin d'**exécuter un morceau de code sur M2**, éventuellement dans le cadre du **processus P2**.
- On a aussi besoin de **transmettre des données** de P1 vers P2.
- Éventuellement, P1 souhaite **récupérer des données ou un résultat**, ou un **acquittement** à la fin.

### 5.4 Principe général de communication avec RPC

> **Remote Procedure Call :** depuis un processus **P1**, sur la machine **M1**, on exécute la procédure **`foo`**, avec les paramètres **`param`**, sur une autre machine **M2** (éventuellement par un processus **P2**). On récupère éventuellement un résultat **`res`**.

```
 M1 / P1                                   M2 / P2
   res = foo(M2, P2, param)  ───────────►  exécute foo(param)
                             ◄───────────  renvoie res
```

---

## 6. Définition du RPC

> **Remote Procedure Call (RPC)** est un **modèle de haut niveau** pour les communications **client-serveur**. Il dote les développeurs de **mécanismes familiers** pour la construction de **systèmes distribués**.

| Mots clés | Sens |
|---|---|
| **Haut niveau** | On manipule des **procédures**, pas des octets/sockets |
| **Client-serveur** | Un **appelant** (client) et un **appelé** (serveur) |
| **Mécanismes familiers** | L'appel **ressemble** à un appel local |

---

## 7. Pourquoi les RPC ?

- Le **client** a besoin d'un **moyen simple** d'invoquer le serveur et d'obtenir des services.
- Les RPC permettent aux clients de communiquer avec les serveurs en invoquant des procédures **de la même façon** qu'une invocation conventionnelle dans un langage de haut niveau.
- Un RPC est **conçu sur une invocation locale de procédure**, mais la procédure invoquée est **exécutée dans un autre processus**, généralement **sur une autre station**.

---

## 8. Principe de fonctionnement

### 8.1 Exemple : sans RPC (avec des sockets)

Application client/serveur :
1. Un client veut **calculer une puissance** (ex. 3 puissance 2) ; le serveur sait le faire.
2. Le **client construit une requête** sous forme d'un **tableau d'octets structuré** et l'envoie au serveur **via une socket**.
3. Le **serveur décode la requête**, **exécute le service** requis et **renvoie le résultat** (tableau d'octets structuré) au client **via une socket**.

### 8.2 L'idée du RPC

- « De manière abstraite », le client a **appelé un service de calcul** sur le serveur via un mécanisme de communication à base de sockets.
- **Idée :** pouvoir **appeler directement ce service** (une fonction C) sur le serveur **presque aussi facilement que s'il était local**.

> 🎯 **Idée générale : pouvoir appeler « presque » aussi facilement une fonction sur un élément distant que localement.**

### 8.3 Principes

| Principe | Description |
|---|---|
| **Deux côtés** | **Appelant (client)** et **appelé (serveur)** : l'appelé **offre à des éléments distants** la possibilité d'appeler **une ou plusieurs de ses fonctions** |
| **Côté client** | Le client **appelle localement** la fonction sur un **élément spécial** qui **relaie** la demande vers le serveur |
| **Côté serveur** | Un **élément spécial** **appelle la fonction** et **renvoie le résultat** au client |
| **Éléments spéciaux** | **Talons (ou *stubs*)** |

### 8.4 Appel de procédure à distance : synthèse

- **Outil de base** pour réaliser une **architecture client/serveur**.
- **Service** : procédure que le client peut **faire exécuter à distance** par le serveur.
- **But :** une **forme et un effet identiques** à ceux d'un **appel local**, **sans se préoccuper de la localisation** de la procédure, **ni du traitement des pannes**.
- **Mise en œuvre :** **sockets**, **RPC Sun**.
- **Problèmes courants :** **pannes indépendantes** client/serveur, **problèmes réseau**, **temps de réponse**.

---

## 9. Les talons (stubs) : client et serveur

| | **Talon client** (*client stub*) | **Talon serveur** (*server stub / skeleton*) |
|---|---|---|
| **Côté** | Appelant | Appelé |
| **Ressemble à** | La **vraie procédure** (même signature) | Un **appelant local** de la vraie procédure |
| **Rôle** | **Collecte et empaquette** les arguments (*marshalling*), les envoie | **Réceptionne et dépaquette** (*unmarshalling*), **appelle** la vraie procédure, **empaquette** le résultat |
| **Transparence** | Le client **croit appeler une fonction locale** | La procédure **croit être appelée localement** |

```
   Client                                                      Serveur
 ┌────────┐   ┌─────────────┐   ┌──────────┐   ┌─────────────┐   ┌────────────┐
 │ appel  │──►│ talon client│──►│ transport│──►│ talon serveur│──►│ procédure  │
 │ local  │◄──│ (stub)      │◄──│ (réseau) │◄──│ (skeleton)  │◄──│ réelle     │
 └────────┘   └─────────────┘   └──────────┘   └─────────────┘   └────────────┘
```

---

## 10. Déroulement détaillé d'un appel RPC (points A à E)

Schéma du cours : le client et le serveur ont chacun un **service RPC (talon)** et un **protocole de communication**, séparés par le **réseau**. Cinq points repérés : **A, B, C, D, E**.

```
        CLIENT                     RÉSEAU                    SERVEUR
 Appelant ──appel──► [Talon] A ───────────────────────► B [Talon] ──appel──► Appelé (C)
                                                                                │
 Appelant ◄─return── [Talon] E ◄─────────────────────── D [Talon] ◄──return────┘
```

### 10.1 Côté de l'appelant (au point **A**)

1. Le client fait un **appel procédural** vers la **procédure talon client** : transmission de **tous les arguments**.
2. Le talon **collecte les arguments** et les **assemble dans un message** → **empaquetage** (*parameter marshalling*).
3. Un **identificateur** est **généré pour le RPC**.
4. Un **délai de garde** est **armé** (temporisation).
5. ⚠️ **Problème :** déterminer **l'adresse du serveur**.
6. Le talon **transmet les données au protocole de transport** pour **émission sur le réseau**.

### 10.2 Côté de l'appelé (points **B**, **C**, **D**)

**Au point B**
1. Le protocole de transport **délivre le message** au **service de RPC** (**talon serveur / skeleton**).
2. Le talon **désassemble les arguments** → **dépaquetage** (*unmarshalling*).
3. L'**identificateur de RPC est enregistré**.

**Au point C**
4. L'appel est **transmis à la procédure distante** requise pour **être exécuté**.

**Au point D**
5. Le retour de la procédure **redonne la main** au service RPC et lui transmet les **paramètres résultats**.
6. Les arguments de retour sont **empaquetés dans un message**.
7. **Un autre délai de garde est armé**.
8. Le talon **transmet les données au protocole de transport** pour émission.

### 10.3 Retour côté de l'appelant (point **E**)

1. L'appel est transmis au **service de RPC** (point E).
2. Les **arguments de retour sont dépaquetés**.
3. Le **délai de garde armé en A est désarmé**.
4. Un **message d'acquittement** avec l'**identificateur du RPC** est envoyé au talon serveur (le délai de garde armé en D peut alors être **désarmé**).
5. Les **résultats sont transmis à l'appelant** lors du **retour de procédure**.

### 10.4 Tableau récapitulatif

| Point | Où | Action principale |
|---|---|---|
| **A** | Client | **Empaquetage** des arguments, **identifiant RPC**, **délai de garde** armé, envoi |
| **B** | Serveur | **Réception**, **dépaquetage**, enregistrement de l'identifiant |
| **C** | Serveur | **Exécution** de la procédure |
| **D** | Serveur | **Empaquetage** du résultat, **délai de garde** armé, envoi |
| **E** | Client | **Réception**, **dépaquetage**, **désarmement** du délai, **acquittement**, retour à l'appelant |

> 🔑 **Notions clés :** *marshalling / unmarshalling*, **identificateur de RPC** (pour reconnaître requêtes/réponses et **détecter les doublons**), **délai de garde** (*timeout*), **acquittement**.

---

## 11. Rôle des modules : talons et transport

| Module | Rôles |
|---|---|
| **Talons** | **Empaquetage / dépaquetage** des paramètres et résultats · **Conversion de données** · **Gestion des processus exécutants** (côté serveur) · **Gestion des erreurs sémantiques** |
| **Modules de communication (transport)** | **Assemblage et transmission** des paramètres et résultats empaquetés · **Gestion des erreurs de transport** |

---

## 12. Problèmes courants

### Défaillances

| Défaillance | Description |
|---|---|
| **Congestion** du réseau ou du serveur | Réponses lentes ou perdues |
| **Panne du client** | Le client s'arrête avant/pendant l'appel |
| **Panne du serveur** | Le serveur s'arrête avant/pendant l'exécution |
| **Erreur de communication** | Perte, duplication ou altération de messages |

### Problèmes de sécurité

| Problème | Solution/Besoin |
|---|---|
| **Authentification du client** | Le serveur doit savoir qui appelle |
| **Authentification du serveur** | Le client doit s'assurer qu'il parle au bon serveur |
| **Confidentialité des échanges** | Chiffrement |
| **Performance** | Coût de l'empaquetage et du réseau |

---

## 13. Types de pannes et mécanismes de reprise

### 13.1 Panne du serveur = attente du client

- Le client **attend** une réponse qui ne vient pas.
- **Horloge (délai) de garde** : après expiration, le client **sait** qu'il y a un problème.
- Le **client décide de la stratégie de reprise**.
- ⚠️ **Risque : exécuter plusieurs fois la même procédure** (si le client relance alors que le serveur avait déjà exécuté).

### 13.2 Serveur orphelin = panne du client

- Le client tombe en panne **pendant** que le serveur travaille pour lui → le serveur devient **« orphelin »**.
- Conséquences :
  - **travaux inutiles** réalisés ;
  - **risque de confusion** du client (à son redémarrage, il peut recevoir une réponse à un ancien appel) ;
  - **états inconsistants**.

### 13.3 Mécanisme de reprise : nombre de relances

En cas de **dépassement de délai**, on choisit une **sémantique d'appel** :

| Sémantique | Signification | Quand l'utiliser *(ajout)* |
|---|---|---|
| **Indéfini** (*maybe*) | Aucune garantie : l'appel **a pu être exécuté 0 ou 1 fois** | Opérations non critiques |
| **Au moins une fois** (*at-least-once*) | Le client **relance** jusqu'à obtenir une réponse → l'opération est exécutée **1 fois ou plus** | Opérations **idempotentes** (lecture, « fixer la valeur à 5 ») |
| **Au plus une fois** (*at-most-once*) | L'opération est exécutée **0 ou 1 fois** (le serveur **détecte les doublons** grâce à l'**identificateur de RPC**) | Opérations **non idempotentes** (virement, incrément) |
| **Exactement une fois** (*exactly-once*) | Exécutée **une et une seule fois** | **Idéal**, mais **très difficile** à garantir (nécessite transactions/journalisation) |

> 💡 *(Ajout)* Une opération est **idempotente** si la répéter ne change pas le résultat (ex. `lire(x)`, `x = 5`). Elle ne l'est pas pour `x = x + 1` ou « débiter 100 € ».

---

## 14. Représentation des données

### Problème

- Une **conversion est nécessaire** si le client et le serveur n'utilisent **pas le même codage** et ont des **formats internes différents** (ex. *little endian* vs *big endian*, tailles d'entiers, jeux de caractères…).
- **Dans le réseau, le passage de paramètres se fait uniquement par valeur** → les **autres modes** (par référence…) doivent être **émulés**.

### Solutions

| Solution | Principe |
|---|---|
| **Solution normalisée** | **Syntaxe abstraite de transfert** (format défini par une norme) |
| **Représentation externe commune** | Tous convertissent vers un **format commun** — exemple : **XDR** de Sun |
| **Représentation locale du client** | Le **client envoie dans son format**, le **serveur convertit** |
| **Choix parmi n formats** | Le client choisit un format parmi plusieurs, le **serveur convertit** |
| **Négociation client/serveur** | Ils **se mettent d'accord** sur le format avant l'échange |

---

## 15. Désignation et liaison

### Objets à désigner
- Le **site d'exécution**, le **serveur**, la **procédure**.
- On vise une **désignation globale, indépendante de la localisation**.

### Désignation

| Type | Description |
|---|---|
| **Statique** | La **localisation du serveur est connue à la compilation** |
| **Dynamique** | **Non connue à la compilation** (trouvée à l'exécution, via un annuaire par exemple) |

### Liaison (*binding*) client ↔ serveur

| Type de liaison | Quand a-t-elle lieu ? |
|---|---|
| **Liaison statique** | Établie **une fois pour toutes** (à la compilation/au démarrage) |
| **Liaison au premier appel** | Établie **au premier appel**, puis **réutilisée** |
| **Liaison à chaque appel** | **Recalculée à chaque appel** (plus souple, plus coûteux) |

---

## 16. Points forts du RPC

| Point fort | Explication |
|---|---|
| **Abstraction** | Les **détails de la communication sont cachés** |
| **Facilité de mise au point** | Une application peut être **mise au point sur un seul site**, puis **déployée sur plusieurs sites** |
| **Portabilité** | Résulte de l'usage d'un **langage de haut niveau**, **indépendant** du système de communication |
| **Outils de génération** | Des **générateurs de code** (talons) **facilitent la mise en œuvre** |

---

## 17. Points faibles du RPC

| Point faible | Explication |
|---|---|
| **Structure d'application statique** | **Pas de création dynamique de serveur**, pas de **redéploiement** entre sites |
| **Pas de passage de paramètres par référence** | Seulement **par valeur** |
| **Communication synchrone uniquement** | Le client **attend** la réponse |
| **Pas de persistance** | La persistance des données **n'est pas assurée** ; il faut la réaliser **explicitement** (sauvegarde dans des fichiers) |

### Mécanismes plus évolués

Pour remédier à ces limites existent :

| Famille | Exemples |
|---|---|
| **Objets répartis** | **Java RMI**, **CORBA** |
| **Bus à messages** (*Message Oriented Middleware*, MOM) | Files de messages (communication **asynchrone**) |
| **Composants répartis** | **EJB**, **CORBA CCM**, **.NET** |

---

## 18. Exemple de code (ajout)

> ⚠️ **Section ajoutée** : les diapositives ne contiennent qu'un exemple d'appel local (`calculPuissance`). Voici l'illustration du **RPC** par un exemple Java simplifié, qui montre le rôle du **talon client** (à comparer avec l'approche « sockets à la main »). *(Code non exécuté ici, à tester dans votre environnement.)*

**Interface partagée :**
```java
public interface Calcul {
    int calculPuissance(int base, int exposant);
}
```

**Talon client (*stub*) : cache la socket derrière un appel « local » :**
```java
import java.io.*;
import java.net.*;

public class CalculStub implements Calcul {
    private final String host; private final int port;
    public CalculStub(String host, int port) { this.host = host; this.port = port; }

    public int calculPuissance(int base, int exposant) {
        try (Socket s = new Socket(host, port);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream  in  = new DataInputStream(s.getInputStream())) {
            out.writeUTF("calculPuissance");   // identifie la procédure
            out.writeInt(base);                // empaquetage (marshalling)
            out.writeInt(exposant);
            out.flush();
            return in.readInt();               // dépaquetage du résultat
        } catch (IOException e) {
            throw new RuntimeException("Panne serveur / réseau", e);
        }
    }
}
```

**Côté client : l'appel ressemble à un appel local :**
```java
Calcul c = new CalculStub("localhost", 7000);
int res = c.calculPuissance(3, 2);     // → 9
```

**Talon serveur (*skeleton*) :**
```java
try (ServerSocket serv = new ServerSocket(7000)) {
    while (true) {
        try (Socket s = serv.accept();
             DataInputStream  in  = new DataInputStream(s.getInputStream());
             DataOutputStream out = new DataOutputStream(s.getOutputStream())) {
            String proc = in.readUTF();               // dépaquetage
            if (proc.equals("calculPuissance")) {
                int base = in.readInt(), exp = in.readInt();
                out.writeInt((int) Math.pow(base, exp)); // exécution + empaquetage du résultat
                out.flush();
            }
        }
    }
}
```

| Élément du code | Notion du cours |
|---|---|
| `CalculStub` | **Talon client** |
| `writeUTF/writeInt` | **Empaquetage** (*marshalling*) |
| Boucle `accept()` + lecture | **Talon serveur** (*skeleton*) |
| `readInt` | **Dépaquetage** (*unmarshalling*) |
| `try/catch IOException` | **Gestion de panne** (client doit décider de la stratégie de reprise) |
| `DataOutputStream` | **Représentation commune** des données (format normalisé Java) |

> 💡 Les outils de RPC (**rpcgen** pour Sun RPC, **IDL** pour CORBA, **rmic** pour RMI) **génèrent automatiquement** ces talons : c'est le point fort « **outils de génération** ».

---

## 19. Remarques sur le support de cours

1. **Numéro du chapitre** : le fichier s'appelle « ch4 » ; le titre est **« RPC : Remote Procedure Calls »** (dans la série *Développement des systèmes répartis*, après Threads et Sockets).
2. **Exemple de calcul** : le texte dit « calculer **3 à la puissance de 2** » alors que le code montre `calculPuissance(2, 3)` et affiche « **2 à la puissance 3** » : deux exemples différents (résultats 9 et 8). Sans incidence sur le principe.
3. **« Entreprise Java Beans »** : le nom correct est **Enterprise JavaBeans** (EJB).
4. **« Les EJB utilisent RMI et CORBA »** : plus précisément, les EJB reposent sur **RMI** (et le protocole **RMI-IIOP**, compatible CORBA).
5. **« Web Services (SOAP = HTTP + XML) »** : simplification : SOAP est un protocole de messages **XML**, **généralement transporté par HTTP** (d'autres transports existent).
6. **« RPC orienté structure de données, pas orienté objet »** : on parle de **paradigme procédural** ; les RPC « orientés objets » sont **RMI** et **CORBA** (objets répartis).
7. **« Mise en œuvre : sockets, RPC Sun »** : *RPC Sun* (ONC RPC) utilise **XDR** pour la représentation des données et **rpcgen** pour générer les talons.
8. **Fautes de frappe** : « sous orme », « dé communication », « crée » au lieu de « crée/créé », « qi relayera » : sans incidence sur le fond.
9. **« Types de pannes »** : l'intitulé couvre à la fois les **pannes** (client/serveur) et les **mécanismes de reprise** (nombre de relances).

---

## 20. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **RPC** | Appel de **procédure à distance** : exécuter une procédure sur une **autre machine** comme si elle était **locale** |
| **Modèle** | **Haut niveau**, communication **client-serveur** |
| **Idée clé** | Appeler « **presque** aussi facilement » une fonction distante que locale |
| **Application distribuée** | Classes **réparties** sur plusieurs machines ; **invocation de méthodes à distance** |
| **Technologies d'accès** | **Sockets · RPC · RMI · CORBA · EJB · Web Services** |
| **RPC vs RMI** | RPC : **procédural** (données) · RMI : **objets** + sérialisation, **Java** |
| **CORBA** | **Multi-langages**, standard **OMG**, **lourd** |
| **EJB** | Composants métier **J2EE** (RMI/CORBA) |
| **Web Services** | **HTTP + XML**, multi-langages, multi-plateformes, **simples** |
| **Base commune** | Tout repose sur les **sockets** |
| **Talons (stubs)** | **Talon client** (empaquette, envoie) · **Talon serveur / skeleton** (dépaquette, appelle) |
| **Marshalling** | **Empaquetage** des paramètres dans un message |
| **Unmarshalling** | **Dépaquetage** du message |
| **Identificateur de RPC** | Identifie un appel (doublons, réponses, acquittements) |
| **Délai de garde** | **Timeout** armé à l'envoi, désarmé à la réponse |
| **Points A–E** | **A** envoi client · **B** réception serveur · **C** exécution · **D** envoi résultat · **E** réception client |
| **Panne serveur** | Le client **attend** → délai de garde → **stratégie de reprise** (risque d'**exécutions multiples**) |
| **Serveur orphelin** | Panne du **client** : **travaux inutiles**, **états inconsistants** |
| **Sémantiques d'appel** | **Indéfini · Au moins une fois · Au plus une fois · Exactement une fois** |
| **Représentation des données** | **XDR** (Sun) ; syntaxe de transfert normalisée ; conversion par le serveur ; négociation |
| **Passage de paramètres** | **Par valeur uniquement** (autres modes émulés) |
| **Désignation** | **Statique** (connue à la compilation) / **Dynamique** |
| **Liaison** | **Statique** · **au premier appel** · **à chaque appel** |
| **Points forts** | Abstraction · facilité de mise au point · portabilité · outils de génération |
| **Points faibles** | Structure **statique** · **pas de références** · **synchrone** · **pas de persistance** |
| **Évolutions** | **Objets répartis** (RMI, CORBA) · **MOM** (messages) · **composants** (EJB, CCM, .NET) |

### Questions d'examen typiques
1. **Définir une application distribuée** et citer les **technologies d'accès** (RPC, RMI, CORBA, EJB, Web Services).
2. **Comparer RPC et RMI**. Pourquoi RPC n'est-il pas « orienté objet » ?
3. Quels sont les atouts et les limites de **CORBA** ? De quel consortium vient-il ?
4. **Définir le RPC** et expliquer **pourquoi** on l'utilise (comparaison avec l'usage direct des **sockets**).
5. Expliquer le **principe de fonctionnement** : rôle du **client**, du **serveur** et des **talons**.
6. Décrire **pas à pas** un appel RPC (**points A à E**) : empaquetage, identificateur, délai de garde, dépaquetage, acquittement.
7. Quel est le rôle des **talons** et des **modules de transport** ?
8. Citer les **problèmes courants** (défaillances, sécurité) d'un RPC.
9. Que se passe-t-il en cas de **panne du serveur** ? Et de **panne du client** (**serveur orphelin**) ?
10. Expliquer les **quatre sémantiques d'appel** (indéfini, au moins une fois, au plus une fois, exactement une fois) et leurs **risques**.
11. **Problème de représentation des données** : pourquoi, et quelles **solutions** (XDR, négociation…) ?
12. **Désignation et liaison** : différences statique / dynamique, premier appel / chaque appel.
13. Citer les **points forts** et **points faibles** du RPC ; quelles **technologies** y remédient ?
14. Pourquoi le **passage par référence** est-il impossible avec un RPC classique ?

---

*Fin du résumé — Chapitre 4 : RPC.*
