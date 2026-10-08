# Les Sockets en Java — Résumé complet du cours
**Cours :** Développement des systèmes répartis
**Enseignant :** Mahran Farhat
**Type de document :** Résumé d'étude avec explications, schémas, code et conseils d'examen

---

## Table des matières

1. [Plan du chapitre](#1-plan-du-chapitre)
2. [Rappel sur les réseaux](#2-rappel-sur-les-réseaux)
3. [Introduction aux sockets](#3-introduction-aux-sockets)
4. [Les numéros de ports](#4-les-numéros-de-ports)
5. [Sockets en mode connecté (TCP)](#5-sockets-en-mode-connecté-tcp)
6. [Sockets en mode non connecté (UDP)](#6-sockets-en-mode-non-connecté-udp)
7. [Comparaison TCP vs UDP](#7-comparaison-tcp-vs-udp)
8. [Le paquetage java.net](#8-le-paquetage-javanet)
9. [Adressage IP en Java : InetAddress](#9-adressage-ip-en-java--inetaddress)
10. [Exemples de code complets](#10-exemples-de-code-complets)
11. [Remarques sur le support de cours](#11-remarques-sur-le-support-de-cours)
12. [Fiche de révision rapide](#12-fiche-de-révision-rapide)

---

## 1. Plan du chapitre

1. Rappel sur les réseaux
2. Introduction aux sockets
3. Sockets en mode **connecté**
4. Sockets en mode **non connecté**
5. Adressage IP en Java

---

## 2. Rappel sur les réseaux

### 2.1 Le modèle de référence OSI (7 couches)

| N° | Couche | Rôle selon la norme ISO |
|---|---|---|
| **7** | **Application** | Interprétation des données, protocoles applicatifs (HTTP, FTP, SMTP, POP, etc.) |
| **6** | **Présentation** | Mise en forme des informations |
| **5** | **Session** | Gestion de sessions |
| **4** | **Transport** | Transfert **de bout en bout**, **contrôle de flux** (transfert fiable entre deux applications) |
| **3** | **Réseau** | **Routage**, contrôle de congestion |
| **2** | **Liaison de données** | Transfert local des trames, gestion d'accès au médium, détection et correction d'erreurs de transmission |
| **1** | **Physique** | Connexion au réseau, transmission des données binaires sur un support physique |

> 💡 **Astuce mnémotechnique (de 7 à 1) :** **A**pplication · **P**résentation · **S**ession · **T**ransport · **R**éseau · **L**iaison · **P**hysique.

### 2.2 Correspondance OSI ↔ TCP/IP

| Couches OSI | N° TCP/IP | Couche TCP/IP |
|---|---|---|
| 7 Application, 6 Présentation, 5 Session | **4** | **Application** |
| 4 Transport | **3** | **Transport** |
| 3 Réseau | **2** | **Internet** |
| 2 Liaison de données, 1 Physique | **1** | **Réseau** (accès réseau) |

→ Le modèle **TCP/IP a 4 couches** : les 3 couches hautes d'OSI sont fusionnées en une seule (Application), et les 2 couches basses aussi (Réseau).

### 2.3 Principe de la communication en couches

- **Chaque couche a un rôle particulier.**
- Pour communiquer avec une autre entité, une couche **utilise les services de sa couche locale inférieure**.
- Une couche d'une entité communique avec **la couche de même niveau** d'une autre entité en respectant un **protocole de communication**.

### 2.4 Données échangées entre couches : le PDU

- **PDU** (*Protocol Data Unit*) = trames, paquets, segments ou messages.
- **Taille bornée.**
- Structure en deux parties :

> **PDU = PCI + SDU**

| Élément | Signification |
|---|---|
| **SDU** | *Service Data Unit* : **données de la couche supérieure** à transmettre |
| **PCI** | *Protocol Control Information* : **données de contrôle** de la communication entre couches (l'« en-tête ») |

### 2.5 Unités d'échange entre les couches (schéma)

Entre la **Machine A** et la **Machine B** :

| Couche | Unité échangée |
|---|---|
| Application | **Message** |
| Transport | **Paquet** (segment) |
| Réseau | **Datagramme** |
| Liaison de données | **Trame** |
| Réseau physique | Bits sur le support |

### 2.6 Couche réseau : IP (Internet Protocol)

- **Identification des machines** dans les réseaux (adresses IP).
- **Recherche des routes** à travers le réseau pour atteindre une machine distante.
- **Contrôle de congestion** dans le réseau.

### 2.7 Couche transport : TCP et UDP

| | **TCP** (*Transmission Control Protocol*) | **UDP** (*User Datagram Protocol*) |
|---|---|---|
| **Mode** | **Orienté connexion** | **Datagramme** (sans connexion) |
| **Fonctionnement** | **Connexion virtuelle directe et fiable** entre 2 applications | **Envoi de paquets** de données |
| **Caractéristiques** | Point à point, **bidirectionnel**, **fiable**, **conserve le séquencement** | **Pas de gestion de l'ordre d'arrivée**, **pas de gestion des paquets perdus** |
| **Rôle ajouté à IP** | **TSAP** : socket = **(@IP + port)** | Ajoute à IP l'information de service : le **port** |

**Protocoles de transport Internet (TCP et UDP) :**
- Communication **de bout en bout** entre deux systèmes.
- **Transparence** des systèmes intermédiaires (les **routeurs** ne sont pas visibles).

---

## 3. Introduction aux sockets

### 3.1 Définition

> Une **socket** est une **interface** qui permet à une application de **se connecter à la pile réseau d'un hôte**. Une fois connectée, l'application peut échanger des données avec d'autres processus de manière **bidirectionnelle**.

Schématiquement : *Application → interface socket → pile réseau (OS) → réseau IP → pile réseau (OS) → interface socket → Application*.

### 3.2 Modèle Client/Serveur (C/S)

- Mode de communication où **un hôte établit une communication avec un autre hôte qui fournit un service**.
- Échange : le **client** envoie une requête (*send request*) → le **serveur** exécute l'**opération** → il renvoie une réponse (*send reply*).
- Les règles de cet échange forment le **« protocole d'application »**.

---

## 4. Les numéros de ports

Les numéros de ports sont attribués selon une norme de l'**IANA** (*Internet Assigned Numbers Authority*) :

| Plage | Nom | Usage |
|---|---|---|
| **0 à 1023** | **Ports reconnus / réservés** (*Well Known Ports*) | En général réservés aux **processus système (démons)** ou aux programmes exécutés par des **utilisateurs privilégiés**. Un administrateur peut toutefois lier des services aux ports de son choix |
| **1024 à 49151** | **Ports enregistrés** (*Registered Ports*) | Services enregistrés auprès de l'IANA |
| **49152 à 65535** | **Ports dynamiques et/ou privés** (*Dynamic and/or Private Ports*) | Usage libre / temporaire |

> 💡 Un port est codé sur **16 bits** → 65 536 ports (0 à 65535). Exemples classiques : HTTP = 80, HTTPS = 443, FTP = 21, SMTP = 25. *(Exemples ajoutés, non cités dans les diapositives.)*

---

## 5. Sockets en mode connecté (TCP)

### 5.1 Principe : étapes du client et du serveur

| **Client** | **Serveur** (en écoute du canal) |
|---|---|
| 1. **Crée une socket** | 1. **Crée une socket** |
| 2. **Se connecte au serveur** en donnant l'**adresse socket distante** (adresse IP du serveur + numéro de port du service). Le **système d'exploitation attribue automatiquement** un numéro de **port local** au client | 2. **Associe une adresse socket** (adresse Internet + numéro de port) au service : **« binding »** |
| 3. **Lit et/ou écrit** (envoi et réception de messages) sur la socket | 3. Se met **à l'écoute** des connexions entrantes |
| 4. **Ferme la socket** | 4. Pour **chaque connexion entrante** : <br>① **Accepte** la connexion (une **nouvelle socket** est créée avec les mêmes caractéristiques que la socket d'origine → permet de lancer un **thread ou processus fils** pour gérer la connexion) <br>② **Lit et/ou écrit** sur la nouvelle socket <br>③ **Ferme** la socket créée |

### 5.2 Séquence d'échange (schéma)

```
 SERVEUR                              CLIENT
 ───────                              ──────
 Crée un socket
 Attend une connexion  ◄──────────── demande une connexion
 Accepte la demande    ────────────► création de socket
 Échange de données    ◄──────────► Échange de données
 Fermer connexion                     Fermer connexion
```

### 5.3 Classes Java utilisées pour TCP

| Classe | Rôle |
|---|---|
| **`InetAddress`** | Codage des **adresses IP** |
| **`Socket`** | Socket en **mode connecté** |
| **`ServerSocket`** | Socket d'**attente de connexion**, **côté serveur** |

### 5.4 La classe `Socket`

**Constructeurs :**

```java
public Socket(InetAddress address, int port) throws IOException
```
Crée une socket locale et la **connecte** à un port distant d'une machine distante identifiée par le couple **adresse/port**.

```java
public Socket(String address, int port) throws IOException, UnknownHostException
```
Idem, mais avec le **nom de la machine** au lieu de son adresse IP. Lève **`UnknownHostException`** si le service de noms (DNS) ne parvient pas à identifier la machine.

**Émission / réception de données** (via les flux associés à la socket) :

| Méthode | Rôle |
|---|---|
| `OutputStream getOutputStream()` | Flux de **sortie** → **envoyer** des données |
| `InputStream getInputStream()` | Flux d'**entrée** → **recevoir** des données |
| `void close()` | **Ferme** la socket et **rompt la connexion** avec la machine distante |

**Méthodes « get » :**

| Méthode | Retourne |
|---|---|
| `int getPort()` | Le **port distant** avec lequel la socket est connectée |
| `InetAddress getAddress()` | L'**adresse IP de la machine distante** |
| `int getLocalPort()` | Le **port local** sur lequel la socket est liée |

### 5.5 La classe `ServerSocket`

- Socket d'**attente de connexion**, **côté serveur uniquement**.

**Constructeur :**
```java
public ServerSocket(int port) throws IOException
```
- Crée une **socket d'écoute** (attente de connexions de clients), **liée au port** passé en paramètre.
- L'exception est levée notamment si ce **port est déjà lié** à une autre socket.

**Méthodes :**

| Méthode | Rôle |
|---|---|
| `Socket accept() throws IOException` | **Attend la connexion** d'un client distant. Quand la connexion est faite, **retourne une socket de service** permettant de communiquer avec ce client. **Bloquant** |
| `void setSoTimeout(int timeout) throws SocketException` | Fixe le **temps maximum d'attente** sur un `accept()`. Si le temps est écoulé, `accept()` lève **`SocketTimeoutException`**. **Par défaut : attente infinie** |

> 🔑 **Distinction importante :** la `ServerSocket` sert **uniquement à écouter** ; chaque client est ensuite servi par une **`Socket` de service** retournée par `accept()`.

### 5.6 Exemple d'utilisation (schéma du cours)

**Client :**
1. `Socket(@ip, PORT)`
2. `in = new BufferedReader(new InputStreamReader(socket.getInputStream()))`
3. `out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()))`
4. `out.println(...); out.flush(); in.readLine();`
5. `socket.close()`

**Serveur (« serv ») :**
1. `ServerSocket(PORT)`
2. `accept()` → **nouveau socket** et éventuellement **nouveau thread**
3. `in = new BufferedReader(new InputStreamReader(connexion.getInputStream()))`
4. `out = new PrintWriter(new BufferedOutputStream(connexion.getOutputStream()))`
5. `in.readLine(); out.println(...); out.flush();`
6. `connexion.close()`

Le **transfert de données** se fait dans les deux sens entre les étapes 4 (client) et 5 (serveur). Voir le code complet en [section 10](#10-exemples-de-code-complets).

---

## 6. Sockets en mode non connecté (UDP)

### 6.1 Principe : étapes du client et du serveur

| **Client (émetteur)** | **Serveur (récepteur)** |
|---|---|
| 1. **Crée une socket** | 1. **Crée une socket** |
| 2. **Associe une adresse socket** au service (IP + port) : **« binding »** — *opération nécessaire seulement si le processus va **recevoir** des données* | 2. **Associe une adresse socket** au service : **« binding »** (**opération nécessaire**) |
| 3. **Lit et/ou écrit** (envoi/réception de messages) sur la socket | 3. **Lit et/ou écrit** sur la socket |

> Le client et le serveur ont **les mêmes fonctions** pour transférer les données :

| Fonction | Rôle |
|---|---|
| `socket()` | **Créer** une socket client / serveur |
| `bind()` | **Lier** le client / serveur à une adresse socket (IP + port) |
| `send()` | **Envoyer** des données (client et serveur) |
| `receive()` | **Recevoir** des données (client et serveur) |
| `close()` | **Fermer** la socket client / serveur |

### 6.2 Classes Java utilisées pour UDP

| Classe | Rôle |
|---|---|
| **`InetAddress`** | Codage des adresses IP |
| **`DatagramPacket`** | **Paquet de données** envoyé via une socket sans connexion |
| **`DatagramSocket`** | **Socket en mode non connecté** (UDP) |

### 6.3 La classe `DatagramPacket`

**Constructeur pour RECEVOIR :**
```java
public DatagramPacket(byte[] buf, int length)
```
- Crée un paquet pour **recevoir** des données (tableau d'octets).
- Les données reçues seront placées dans `buf`.
- `length` = **taille maximale à lire** : ne pas dépasser la taille du tableau (en général `length = buf.length`).
- Variante avec un **offset** pour ne pas commencer au début du tableau.

**Constructeur pour ENVOYER :**
```java
public DatagramPacket(byte[] buf, int length, InetAddress address, int port)
```

| Paramètre | Signification |
|---|---|
| `buf` | Contient les **données à envoyer** |
| `length` | **Longueur** des données à envoyer (ne pas dépasser la taille de `buf`) |
| `address` | **Adresse IP** de la machine destinataire |
| `port` | **Numéro de port distant** (sur la machine destinataire) |

**Méthodes « get » :**

| Méthode | Paquet à envoyer | Paquet reçu |
|---|---|---|
| `InetAddress getAddress()` | Adresse de la machine **destinataire** | Adresse de la machine **qui a envoyé** le paquet |
| `int getPort()` | Port **destinataire** sur la machine distante | Port utilisé par le programme **distant** pour envoyer |
| `byte[] getData()` | Données contenues dans le paquet | idem |
| `int getLength()` | Longueur des données **à envoyer** | Longueur des données **reçues** |

**Méthodes « set » :**

| Méthode | Rôle |
|---|---|
| `void setAddress(InetAddress adr)` | Positionne l'**adresse IP du destinataire** |
| `void setPort(int port)` | Positionne le **port destinataire** |
| `void setData(byte[] data)` | Positionne les **données à envoyer** |
| `void setLength(int length)` | Positionne la **longueur** des données à envoyer |

### 6.4 La classe `DatagramSocket`

**Constructeurs :**

| Constructeur | Comportement |
|---|---|
| `DatagramSocket() throws SocketException` | Crée une socket liée à un **port quelconque libre** |
| `DatagramSocket(int port) throws SocketException` | Crée une socket liée au **port local précisé**. Exception notamment si le **port est déjà occupé** |

**Émission / réception :**

| Méthode | Rôle |
|---|---|
| `void send(DatagramPacket p) throws IOException` | **Envoie** le paquet. Le destinataire est identifié par le couple **@IP/port** contenu dans le paquet |
| `void receive(DatagramPacket p) throws IOException` | **Reçoit** un paquet. **Bloquant** tant qu'aucun paquet n'est reçu. À l'arrivée, **les attributs de `p` sont modifiés** |

**Autres méthodes :**

| Méthode | Rôle |
|---|---|
| `void close()` | **Ferme** la socket et **libère le port** |
| `int getLocalPort()` | Retourne le **port local** sur lequel la socket est liée |

### 6.5 Exemple d'utilisation (schéma du cours)

| **Client** | **Serveur** |
|---|---|
| `DatagramSocket()` | `DatagramSocket(port)` |
| `DatagramPacket(..)` (envoi) | `DatagramPacket(...)` (réception) |
| `sock.send()` | `sock.receive()` ⛔ *appel pouvant être bloquant* |
| `DatagramPacket(..)` (réception) | `DatagramPacket(...)` (réponse) |
| `sock.receive()` ⛔ *appel pouvant être bloquant* | `sock.send()` |

*(Rouge = appel pouvant être bloquant ; blanc = appel non bloquant. Dans le schéma, seul `receive()` est bloquant.)*

### 6.6 Critique des sockets UDP

| Avantages | Inconvénients |
|---|---|
| **Simple à programmer** (et à comprendre) | **Pas fiable** (pas de garantie de livraison ni d'ordre) |
| | **Ne permet d'envoyer que des tableaux de bytes** (il faut sérialiser soi-même) |

---

## 7. Comparaison TCP vs UDP

| Critère | **TCP (mode connecté)** | **UDP (mode datagramme)** |
|---|---|---|
| **Classes Java** | `Socket`, `ServerSocket` | `DatagramSocket`, `DatagramPacket` |
| **Connexion** | **Oui** (établie avant l'échange) | **Non** |
| **Fiabilité** | **Fiable** | **Non fiable** |
| **Ordre des données** | **Conservé** (séquencement) | **Non garanti** |
| **Paquets perdus** | Gérés | **Non gérés** |
| **Échange de données** | **Flux** (`InputStream` / `OutputStream`) | **Paquets** (tableaux d'octets) |
| **Côté serveur** | `ServerSocket` + `accept()` (une socket par client) | Une seule `DatagramSocket` |
| **Binding** | Serveur (client : port local attribué par l'OS) | **Nécessaire côté serveur** ; côté client seulement s'il doit recevoir |
| **Complexité** | Plus élevée | **Simple** |
| **Usages typiques** *(ajout)* | Web, mail, transfert de fichiers | Streaming, jeux, DNS |

---

## 8. Le paquetage `java.net`

Hiérarchie des classes (toutes héritent de `java.lang.Object`) :

```
java.lang.Object
 ├── InetAddress
 │     ├── Inet4Address
 │     └── Inet6Address
 ├── SocketAddress
 │     └── InetSocketAddress
 ├── DatagramPacket
 ├── DatagramSocket
 ├── Socket
 └── ServerSocket
```

---

## 9. Adressage IP en Java : `InetAddress`

- L'adresse IP est fournie par des **objets de la classe `InetAddress`**.
- La classe **encapsule l'accès au serveur de noms DNS**.

### Méthodes à retenir

| Méthode | Rôle |
|---|---|
| `getAddress()` | Retourne l'adresse IP sous forme de **tableau de `byte`** |
| `getLocalHost()` | Retourne un objet `InetAddress` contenant l'**adresse IP de l'hôte** (**pas** 127.0.0.1) |
| `getHostName()` | Retourne une **chaîne** : l'**adresse symbolique** (nom) de l'objet |
| `InetAddress.getByName(chaîne)` | Récupère l'adresse IP sous forme d'objet `InetAddress` à partir d'un **nom symbolique** |
| `toString()` | Affiche l'objet sous la forme **`nom symbolique/adresse IP`** |

### Signatures (méthodes statiques)

```java
public static InetAddress getLocalHost() throws UnknownHostException
public static InetAddress getByName(String host) throws UnknownHostException
public static InetAddress[] getAllByName(String host) throws UnknownHostException
```

> `getAllByName` renvoie **toutes les adresses IP** associées à un nom (un site peut en avoir plusieurs).

---

## 10. Exemples de code complets

> ⚠️ **Section ajoutée** : les diapositives présentent ces exemples sous forme de schémas. Voici des programmes complets et fonctionnels qui suivent exactement ces schémas, pour mieux comprendre.

### 10.1 TCP — Serveur d'écho (avec un thread par client)

```java
import java.io.*;
import java.net.*;

public class ServeurTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket serv = new ServerSocket(5000);          // 1. socket d'écoute (binding)
        System.out.println("Serveur en attente sur le port 5000...");
        while (true) {
            Socket connexion = serv.accept();                // 2. bloquant : attend un client
            new Thread(() -> servir(connexion)).start();     // 3. un thread par client
        }
    }

    static void servir(Socket connexion) {
        try (connexion;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(connexion.getInputStream()));
             PrintWriter out = new PrintWriter(
                     new BufferedOutputStream(connexion.getOutputStream()))) {
            String ligne = in.readLine();                    // lire la requête
            out.println("Echo : " + ligne);                  // répondre
            out.flush();                                     // IMPORTANT : vider le tampon
        } catch (IOException e) {
            e.printStackTrace();
        }                                                    // close() automatique
    }
}
```

### 10.2 TCP — Client

```java
import java.io.*;
import java.net.*;

public class ClientTCP {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 5000);   // connexion au serveur
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(
                     new BufferedOutputStream(socket.getOutputStream()))) {
            out.println("Bonjour serveur");
            out.flush();
            System.out.println(in.readLine());                // affiche : Echo : Bonjour serveur
        }
    }
}
```

### 10.3 UDP — Serveur

```java
import java.net.*;

public class ServeurUDP {
    public static void main(String[] args) throws Exception {
        DatagramSocket sock = new DatagramSocket(6000);       // binding sur le port 6000
        byte[] buf = new byte[1024];
        DatagramPacket recu = new DatagramPacket(buf, buf.length);
        sock.receive(recu);                                   // BLOQUANT jusqu'à l'arrivée d'un paquet
        String msg = new String(recu.getData(), 0, recu.getLength());
        System.out.println("Reçu : " + msg);

        byte[] rep = ("Echo : " + msg).getBytes();
        DatagramPacket reponse = new DatagramPacket(
                rep, rep.length, recu.getAddress(), recu.getPort()); // renvoyer à l'expéditeur
        sock.send(reponse);
        sock.close();
    }
}
```

### 10.4 UDP — Client

```java
import java.net.*;

public class ClientUDP {
    public static void main(String[] args) throws Exception {
        DatagramSocket sock = new DatagramSocket();           // port local libre quelconque
        byte[] data = "Bonjour".getBytes();
        InetAddress ip = InetAddress.getByName("localhost");
        sock.send(new DatagramPacket(data, data.length, ip, 6000));

        byte[] buf = new byte[1024];
        DatagramPacket recu = new DatagramPacket(buf, buf.length);
        sock.receive(recu);                                   // BLOQUANT
        System.out.println(new String(recu.getData(), 0, recu.getLength()));
        sock.close();
    }
}
```

### 10.5 InetAddress

```java
InetAddress local = InetAddress.getLocalHost();
System.out.println(local);                    // nom/adresse
InetAddress site = InetAddress.getByName("www.example.com");
System.out.println(site.getHostName());
for (InetAddress a : InetAddress.getAllByName("www.example.com"))
    System.out.println(a.getHostAddress());
```

---

## 11. Remarques sur le support de cours

1. **Titre du fichier** : le PDF s'appelle « ch2-1 » ; c'est le chapitre **Les sockets**.
2. **Coquilles dans les diapositives** (sans incidence sur le fond) :
   - `getLocalhost()` s'écrit en réalité **`getLocalHost()`** (avec un **H** majuscule) ;
   - `byte[] getData` s'écrit **`getData()`** ;
   - `int setLength(...)` : la méthode retourne en fait **`void`** ;
   - `public close()` : il manque le type de retour → **`public void close()`** ;
   - `InetAdress` s'écrit **`InetAddress`** (deux **d**) ;
   - « socket en mode non connecté » : le schéma parle d'un « nouveau thread » éventuel côté serveur — c'est valable en **TCP**, pas en UDP.
3. **Bon réflexe en TCP :** toujours appeler **`flush()`** après avoir écrit sur un `PrintWriter` bufferisé, sinon les données peuvent ne pas partir.
4. **Bon réflexe en UDP :** pour lire le message reçu, utiliser **`getLength()`** (et non la taille complète du tableau) : `new String(p.getData(), 0, p.getLength())`.
5. **Pour servir plusieurs clients en TCP :** un **thread par connexion acceptée** (lien direct avec le chapitre *Threads*).

---

## 12. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| Modèle OSI | **7 couches** : Application, Présentation, Session, Transport, Réseau, Liaison, Physique |
| Modèle TCP/IP | **4 couches** : Application, Transport, Internet, Réseau |
| PDU | **PDU = PCI + SDU** (en-tête de contrôle + données de la couche supérieure) |
| IP | Identifie les machines, **route**, contrôle la congestion |
| TCP | **Connecté, fiable, ordonné**, bidirectionnel, point à point |
| UDP | **Datagramme**, **non fiable**, pas d'ordre, simple |
| Socket | **Interface** entre l'application et la pile réseau ; adresse = **(@IP + port)** |
| Ports | **0–1023** réservés · **1024–49151** enregistrés · **49152–65535** dynamiques |
| `Socket` | Socket **TCP** côté client (et socket de service côté serveur) |
| `ServerSocket` | Socket d'**écoute** TCP (serveur) · `accept()` retourne une `Socket` |
| `accept()` | **Bloquant** · timeout avec `setSoTimeout()` (défaut : infini) |
| `DatagramSocket` | Socket **UDP** · `send()` / `receive()` (bloquant) |
| `DatagramPacket` | Paquet UDP : **données + adresse + port** |
| `InetAddress` | Représente une **adresse IP** ; encapsule le **DNS** |
| Binding | Associer **adresse IP + port** à une socket |
| Exceptions | `IOException` · `UnknownHostException` · `SocketException` · `SocketTimeoutException` |

### Questions d'examen typiques
1. Citer les **7 couches OSI** et dire à quelles couches TCP/IP elles correspondent.
2. Que signifie **PDU = PCI + SDU** ?
3. Comparer **TCP** et **UDP** (fiabilité, ordre, connexion, usages).
4. Qu'est-ce qu'une **socket** ? Qu'est-ce qu'un **TSAP** ?
5. Donner les **plages de ports** définies par l'IANA.
6. Décrire les **étapes d'un client et d'un serveur TCP**. Pourquoi `accept()` crée-t-elle une **nouvelle socket** ?
7. Quelle est la différence entre **`Socket`** et **`ServerSocket`** ?
8. Comment éviter qu'un `accept()` **bloque indéfiniment** ? (`setSoTimeout` → `SocketTimeoutException`)
9. Quelle est la différence entre les **deux constructeurs** de `DatagramPacket` (réception / envoi) ?
10. Pourquoi `receive()` est-il dit **bloquant** ?
11. Quels sont les **avantages et inconvénients** des sockets UDP ?
12. À quoi servent `getLocalHost()`, `getByName()` et `getAllByName()` ?
13. **Écrire** un client et un serveur TCP d'écho (et leurs équivalents UDP).

---

*Fin du résumé — Les sockets en Java.*
