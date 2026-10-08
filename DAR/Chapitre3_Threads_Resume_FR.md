# Chapitre 3 — Les Threads en Java
**Cours :** Développement des systèmes répartis
**Enseignant :** Mahran Farhat
**Type de document :** Résumé d'étude avec explications, code et conseils d'examen

---

## Table des matières

1. [Pourquoi les threads ? (Motivation)](#1-pourquoi-les-threads--motivation)
2. [Processus vs Threads](#2-processus-vs-threads)
3. [Les threads en Java](#3-les-threads-en-java)
4. [Modèle d'exécution : mono-thread vs multi-thread](#4-modèle-dexécution--mono-thread-vs-multi-thread)
5. [Création d'un thread](#5-création-dun-thread)
6. [États et cycle de vie d'un thread](#6-états-et-cycle-de-vie-dun-thread)
7. [Principales méthodes d'un thread (actions)](#7-principales-méthodes-dun-thread-actions)
8. [Exercice 1 — Deux compteurs](#8-exercice-1--deux-compteurs)
9. [Synchronisation](#9-synchronisation)
10. [wait / notify / notifyAll](#10-wait--notify--notifyall)
11. [Le modèle Producteur / Consommateur](#11-le-modèle-producteur--consommateur)
12. [Exemple de la station de bus (notification perdue)](#12-exemple-de-la-station-de-bus-notification-perdue)
13. [Priorités et ordonnancement](#13-priorités-et-ordonnancement)
14. [Groupes de threads](#14-groupes-de-threads)
15. [Exercice 2 — Un producteur et deux consommateurs](#15-exercice-2--un-producteur-et-deux-consommateurs)
16. [Remarques importantes et corrections](#16-remarques-importantes-et-corrections)
17. [Fiche de révision rapide](#17-fiche-de-révision-rapide)

---

## 1. Pourquoi les threads ? (Motivation)

Question d'ouverture du cours : dans ce code, **les deux chiens aboient-ils en même temps ?**

```java
public class Chien {
    public void aboyer() { /* comportement normal d'un chien */ }
}

public class TestChien {
    public static void main(String[] args) {
        Chien c1 = new Chien();
        Chien c2 = new Chien();
        c1.aboyer();
        c2.aboyer();
    }
}
```

**Réponse : non.** Tout s'exécute dans le seul *thread principal* : `c2.aboyer()` ne démarre qu'après la fin de `c1.aboyer()`. Pour qu'ils aboient simultanément, il faut de la **concurrence**.

**La concurrence repose sur trois piliers :**

| Pilier | En Java |
|---|---|
| Définir des processus concurrents | Les **threads** (processus légers) |
| Communication entre eux | Synchrone (appel de méthode) / Asynchrone (envoi de message) |
| Synchronisation | `synchronized`, `wait()`, `notify()`, `notifyAll()` |

---

## 2. Processus vs Threads

Il existe deux grandes unités d'exécution en programmation concurrente : les **processus** et les **threads**. Leur exécution est gérée par l'**OS**.

| Critère | Processus (lourd) | Thread (processus léger) |
|---|---|---|
| Environnement | Son **propre environnement d'exécution** (ressources système) | **Partage les ressources** du processus père |
| Mémoire | Espace d'adressage propre | Espace d'adressage **partagé** |
| Existence | Indépendant | Vit **à l'intérieur** d'un processus (au moins 1 thread par processus) |
| Communication | Coûteuse | Peu coûteuse |
| Changement de contexte | Coûteux | Peu coûteux |
| Contrôle | Hors du contrôle de l'environnement d'exécution Java | Géré par le mécanisme interne de gestion multithread de la JVM |

**Points clés**

- En général : **un processus par application** (on peut toutefois faire coopérer des processus).
- La plupart des JVM tournent sur **un seul processus**, mais l'exécution d'une JVM est **multi-threadée** : les threads sont un aspect essentiel de Java.
- La faible surcharge rend les threads idéaux pour les **applications distribuées**, p. ex. un serveur multi-tâches avec **un thread par client à servir**.

---

## 3. Les threads en Java

- Java permet de *simuler* l'exécution de plusieurs programmes en même temps : c'est le **multithreading**. L'exécution de chaque programme s'appelle un **thread**.
- Le multithreading est **intégré au langage**.
- Créer un nouvel objet `Thread` crée un **fil d'exécution séparé qui possède sa propre pile**.

```java
Thread t = new Thread();   // créer un nouveau thread
t.start();                 // le démarrer
```

---

## 4. Modèle d'exécution : mono-thread vs multi-thread

### Processus mono-thread
- Le code s'exécute sur **un seul fil** : le *main thread*.
- **L'ordre des instructions est garanti.**
- Impossible de faire des choses « en parallèle ».

### Processus multi-thread
- **Partagés :** Code, Données (Data), Fichiers (File).
- **Propres à chaque thread :** ses **Registres** et sa **Pile** (Stack).
- Chaque thread possède son propre code à exécuter.
- **L'ordre global des instructions n'est PAS garanti** (ex. `thr.1, thr.2, thr.2, thr.1, thr.1, thr.2`).
- Il est possible de faire des choses « en parallèle ».

```
 Processus simple              Processus multithreadé
┌───────────────┐             ┌──────────────────────────┐
│ Code Data File│             │ Code        Data   File  │  ← partagés
├───────────────┤             ├────────────┬─────────────┤
│ Registres     │             │ Registres  │ Registres   │  ← propres à chaque thread
│ Pile          │             │ Pile       │ Pile        │
└───────────────┘             └────────────┴─────────────┘
                                 Thread 1     Thread 2
```

### Parallèle réel vs « pseudo-parallèle »
- Avec plusieurs piles d'appels, on a l'**impression** que plusieurs choses se passent en même temps.
- Sur un système **multiprocesseur**, c'est **réellement parallèle** ; sur un système **monoprocesseur**, le système « fait semblant » en alternant très rapidement entre les threads.

### Threads toujours présents dans un programme Java « simple »
1. Le **thread principal** (*main*)
2. Le **Garbage Collector**
3. La **file d'événements graphiques**

> Toute méthode d'un objet Java s'exécute dans **au moins un thread**.

---

## 5. Création d'un thread

### Méthode 1 — Étendre la classe `Thread`

- Le code à exécuter est placé dans `public void run()` (**redéfinition**).
- `start()` (héritée) démarre le thread.

```java
public class MonThread extends Thread {
    public void run() {
        System.out.println("bonjour !");
    }
}

public class Threads {
    public static void main(String[] args) {
        MonThread T = new MonThread();
        T.start();          // crée un nouveau fil d'exécution
    }
}
```

**Limite :** Java n'a que l'héritage simple → *comment hériter d'une autre classe ?* → utiliser la méthode 2.

### Méthode 2 — Implémenter l'interface `Runnable`

- `run()` est **obligatoire** (méthode de l'interface).
- On passe l'objet `Runnable` au constructeur de `Thread`.

```java
public class MonThreadRunnable implements Runnable {
    public void run() {
        System.out.println("bonjour from Runnable!");
    }
}

public class Threads {
    public static void main(String[] args) {
        MonThreadRunnable TR = new MonThreadRunnable();
        Thread t = new Thread(TR);
        t.start();
    }
}
```

### Comparaison

| | `extends Thread` | `implements Runnable` |
|---|---|---|
| Peut hériter d'une autre classe | ❌ Non | ✅ Oui |
| Séparation tâche / exécution | ❌ Mélangées | ✅ Propre |
| Recommandé | Cas simples | **Généralement préféré** |

### Évolution des piles : exemple

```java
public static void main(String[] args) {      // Étape 1
    Runnable r = new MaTache();
    Thread t = new Thread(r);
    t.start();                                // Étape 2 : une nouvelle pile est créée
    Chat c = new Chat();
}
```

| Étape | Pile du thread principal | Pile du thread `t` |
|---|---|---|
| 1 | `main()` | — |
| 2 | `main()` → `t.start()` | `run()` |
| 3 | `main()` → `Chien()` | `run()` → `go()` |

> ⚠️ `t.start()` crée la nouvelle pile et exécute `run()` dedans. Appeler directement `t.run()` ne crée **aucun** thread : c'est un simple appel de méthode dans le thread courant.

---

## 6. États et cycle de vie d'un thread

### Pourquoi c'est important
Dans un environnement **mono-thread**, si le thread se bloque en attente d'une ressource, **tout le programme s'arrête**. Avec **plusieurs threads**, les autres continuent de s'exécuter.

### Les 7 états (tels que présentés dans le cours)

| # | État | Comment on y entre | Comment on en sort |
|---|---|---|---|
| 1 | **Nouveau** (*new*) | `new Thread(a)` | `start()` → Prêt |
| 2 | **Prêt** (*runnable*) | `start()` appelée ; le thread est placé dans la **file d'attente prête** gérée par la JVM | L'ordonnanceur lui donne le CPU → En exécution |
| 3 | **En cours d'exécution** (*running*) | L'ordonnanceur lui alloue des cycles CPU | `yield()` ou **préemption** par un thread de priorité plus élevée → retour à Prêt |
| 4 | **Suspendu** (*suspended*) | `suspend()` (par lui-même ou par un autre thread) | `resume()` par un autre thread → Prêt |
| 5 | **Bloqué** (*blocked*) | `sleep()`, `wait()` (dans une méthode synchronisée), `join()` sur un thread non terminé, opération d'E/S bloquante (ex. lecture clavier) | Fin de l'événement/du délai → Prêt |
| 6 | **Suspendu-bloqué** | Un thread bloqué est suspendu | Fin de l'opération bloquante → Suspendu ; `resume()` avant → Bloqué |
| 7 | **Mort** (*dead*) | `run()` se termine ou `stop()` est appelée | Un thread mort **ne peut pas être relancé** |

### Cycle de vie simplifié (schéma du cours)

```
 Nouveau thread ──start()──► En marche (sous contrôle de l'ordonnanceur) ──fin de run()──► Terminé
                                  │  ▲
                      wait/sleep  │  │ (événement / délai écoulé)
                                  ▼  │
                             Pas en marche
```

- `start()` → le thread passe à l'état « en marche », soumis à l'**ordonnanceur de la JVM**.
- On quitte « en marche » par : la **terminaison de `run()`**, ou une suspension via :
  - `sleep(ms)` → exécution suspendue pour une durée donnée ;
  - `wait()` → attente d'un certain événement.

---

## 7. Principales méthodes d'un thread (actions)

| Méthode | Rôle |
|---|---|
| `start()` | Démarre l'exécution du thread (appelle `run()` dans une nouvelle pile) |
| `run()` | Code exécuté par le thread (souvent une boucle) |
| `yield()` | Redonne **explicitement** le contrôle à l'ordonnanceur |
| `sleep(long ms)` | Met le thread en attente pendant une durée (en ms) |
| `join()` | `t.join()` → attend la **fin** du thread `t` |
| `setPriority(int)` | Change la priorité ; plus la valeur est grande, plus le thread est favorisé |
| `stop()` | Termine le thread (→ mort) |
| `suspend()` | Suspend l'exécution (doit être relancé par `resume()`) |
| `resume()` | Relance un thread suspendu |

```java
t.join();   // attendre la fin de t
```

> ⚠️ **Note Java moderne :** `stop()`, `suspend()` et `resume()` sont **dépréciées** car dangereuses (objets laissés dans un état incohérent, risques d'interblocage). On utilise plutôt un **drapeau `volatile boolean`**, `interrupt()` ou `wait/notify`. Voir la [section 16](#16-remarques-importantes-et-corrections).

---

## 8. Exercice 1 — Deux compteurs

**Énoncé :** Écrire un programme avec deux threads, chacun étant un compteur.
- Le compteur 1 incrémente une valeur initiale `init` d'un incrément `inc` **positif**, pendant `Nit` itérations, avec une pause de « x » temps entre deux itérations.
- Le compteur 2 fait pareil avec un incrément **négatif**.
- Les temps de pause sont **différents**. Commenter le résultat.

**Solution :**

```java
class Compteur extends Thread {
    protected int count, inc, delay, Nit;

    public Compteur(int init, int inc, int delay, int Nit) {
        this.count = init;
        this.inc   = inc;
        this.delay = delay;
        this.Nit   = Nit;
    }

    public void run() {
        try {
            for (int i = 0; i < Nit; i++) {
                System.out.println(count + " ");
                count += inc;
                sleep(delay);
            }
        } catch (InterruptedException e) { }
    }
}

public class Threads {
    public static void main(String[] args) {
        new Compteur(0,  1,  30, 10).start();   // compteur rapide
        new Compteur(0, -1, 100, 10).start();   // compteur lent
    }
}
```

**Interprétation du résultat**
- Les deux threads s'exécutent **de façon concurrente** ; leurs affichages sont **entrelacés**.
- Le compteur 1 (pause de 30 ms) affiche environ **3 valeurs pour 1 valeur** du compteur 2 (pause de 100 ms).
- Le compteur 1 termine en premier (~300 ms contre ~1000 ms).
- L'entrelacement exact peut **changer d'une exécution à l'autre** → c'est l'ordonnanceur qui décide, pas le programmeur.

---

## 9. Synchronisation

### Le problème : un compte bancaire partagé

Deux personnes (threads `tata` et `toto`) accèdent au **même objet `Compte`** et retirent de l'argent.

**Scénario (situation de compétition / *race condition*) :**
1. `tata` appelle `retirerArgent(20)` → vérifie que `montant <= valeur` ✅
2. `toto` appelle `retirerArgent(20)` **avant que `tata` n'ait effectué le retrait** → vérifie aussi `montant <= valeur` ✅
3. Les deux retirent → le compte peut devenir **négatif (-20)**.

- Dans l'exemple, le **-20 est systématique** à cause de `sleep(700)` entre la vérification et le retrait.
- Même **sans `sleep()`**, le scénario reste possible, sans être systématique → c'est pourquoi les programmes multi-threadés sont **difficiles à déboguer**.

### La solution : `synchronized`

Le **test** (`montant > valeur`) et la **mise à jour** (`valeur = valeur - montant`) doivent être réalisés **par le même thread sans interruption** (opération *atomique* / *section critique*).

```java
public synchronized boolean retirerArgent(int montant, String nom) {
    // vérification du solde + retrait, exécutés de façon atomique
}
```

### Règles sur `synchronized`

- **Chaque objet Java possède un verrou (moniteur).**
- Avec `synchronized`, **un seul thread à la fois** peut exécuter la méthode sur cet objet ; les autres attendent.
- Utilisable aussi sur un **bloc** :

```java
public boolean retirerArgent(int amount, String nom) {
    synchronized (this) {          // "this" = l'objet verrouillé
        // instructions à synchroniser
    }
}
```

| Forme | Verrouille | Portée |
|---|---|---|
| Méthode `synchronized` | L'objet `this` | Toute la méthode |
| `synchronized (obj) { }` | L'objet `obj` | Seulement le bloc (plus fin, meilleures performances) |

---

## 10. `wait` / `notify` / `notifyAll`

Chaque objet Java possède un **wait-set** : l'ensemble des threads ayant exécuté `wait()` sur cet objet.

| Méthode | Comportement |
|---|---|
| `wait()` | Le thread appelant **relâche le verrou** et se met dans le wait-set de l'objet |
| `notify()` | Réveille **un seul** thread du wait-set |
| `notifyAll()` | Réveille **tous** les threads présents dans le wait-set à ce moment |

**Règles essentielles**

1. Ces trois méthodes doivent être appelées **dans un bloc/une méthode synchronisé(e)** verrouillé(e) par ce même objet (pour garantir la cohérence du wait-set).
2. ⚠️ **Un `notify()` n'est pas mémorisé.** Si le wait-set est vide au moment du `notify()`, la notification est **perdue** ; un thread qui appellerait `wait()` plus tard n'en profitera pas (voir l'exemple du bus).

---

## 11. Le modèle Producteur / Consommateur

### Problème
- Un thread **producteur** **écrit** dans un objet partagé.
- Un thread **consommateur** **lit** dans ce même objet.
- Si le producteur écrit plusieurs fois de suite sans que le consommateur lise → **des valeurs sont perdues**.
- Si le consommateur lit avant qu'une valeur soit écrite → **données invalides**.

→ Le producteur doit attendre que le consommateur ait lu ; le consommateur doit attendre que le producteur ait écrit.

```
Producteur ── écris ──► Objet partagé ── lis ──► Consommateur
          (doit attendre que le consommateur ait lu avant de réécrire)
```

### Version 1 — Objet partagé SANS bonne synchronisation (incorrecte)

```java
class SharedObject {
    int value = 0;
    boolean available = false;

    public synchronized void write(int i) {
        if (!available) {
            value = i;
            available = true;
            System.out.println("Ecriture de " + i);
        }
    }

    public synchronized int read() {
        if (available) {
            System.out.println("Lecture de " + value);
            available = false;
            return value;
        }
        return -1;
    }
}
```

**Pourquoi ce n'est pas valide**
- Si rien n'est disponible, `read()` renvoie `-1` → l'appelant doit faire de l'**attente active** avec `while (read() == -1) { }` (gaspillage de CPU).
- Attendre le changement de `available` **à l'intérieur** d'une méthode synchronisée échoue aussi : la méthode garde le verrou de l'objet, donc l'autre thread ne peut jamais entrer pour modifier `available`.

### Version 2 — Solution correcte avec `wait()` et `notifyAll()`

`wait()` suspend le thread (et **libère le verrou**) jusqu'à ce qu'un autre thread appelle `notifyAll()`.

```java
class SharedObject {
    int value = 0;
    boolean available = false;

    public synchronized void write(int i) throws InterruptedException {
        if (available) {
            System.out.println("Write en attente");
            wait();                 // attendre que le consommateur ait lu
        }
        value = i;
        available = true;
        System.out.println("Ecriture de " + i);
        notifyAll();                // notifier le consommateur
    }

    public synchronized int read() throws InterruptedException {
        if (!available) {
            System.out.println("Read en attente");
            wait();                 // attendre l'écriture du producteur
        }
        System.out.println("Lecture de " + value);
        notifyAll();                // notifier le producteur que la lecture est faite
        available = false;
        return value;
    }
}
```

> 💡 **Bonne pratique :** remplacer `if (available)` par `while (available)` (et `if (!available)` par `while (!available)`). Cela protège contre les *réveils intempestifs* (spurious wakeups) et contre plusieurs producteurs/consommateurs. Voir la [section 16](#16-remarques-importantes-et-corrections).

---

## 12. Exemple de la station de bus (notification perdue)

**Scénario :** des usagers arrivent à une station et attendent un bus. Le bus arrive, charge **tous les usagers présents**, puis repart. Un usager qui arrive trop tard **rate le bus**.

```java
class Station {
    public synchronized void attendreBus()     { wait(); }
    public synchronized void chargerUsagers()  { notifyAll(); }
}

class Usager extends Thread {
    private String nom;
    private Station s;
    private int heureArrivee;

    public Usager(String nom, Station s, int heureArrivee) {
        this.nom = nom; this.s = s; this.heureArrivee = heureArrivee;
    }
    public void run() {
        sleep(heureArrivee);
        System.out.println(nom + " arrive a la station");
        s.attendreBus();
        System.out.println(nom + " est monte dans le bus");
    }
}

class Bus extends Thread {
    private Station s;
    private int heureArrivee;

    public Bus(Station s, int heureArrivee) { this.s = s; this.heureArrivee = heureArrivee; }
    public void run() {
        sleep(heureArrivee);
        System.out.println("Bus arrive a la station");
        s.chargerUsagers();
        System.out.println("Bus repart de la station");
    }
}

class BusSimple {
    public static void main(String args[]) {
        Station Gare = new Station();
        Bus b7 = new Bus(Gare, 2000);
        Usager u[] = {
            new Usager("Philippe", Gare, 1500),
            new Usager("Patricia", Gare, 3000),
            new Usager("Yves",     Gare, 1000),
            new Usager("Mireille", Gare, 1000)
        };
        b7.start();
        for (int i = 0; i < u.length; i++) u[i].start();
    }
}
```

*(Pour simplifier le code, les diapositives font comme si `wait()` et `sleep()` ne lançaient aucune exception. Dans du vrai code, il faut gérer `InterruptedException`.)*

**Résultat de l'exécution**

```
Yves arrive a la station
Mireille arrive a la station
Philippe arrive a la station
Bus arrive a la station
Bus repart de la station
Yves est monte dans le bus
Mireille est monte dans le bus
Philippe est monte dans le bus
Patricia arrive a la station      ← le programme ne se termine jamais
```

**Explication**

| Temps (ms) | Événement |
|---|---|
| 1000 | Yves et Mireille arrivent → `wait()` |
| 1500 | Philippe arrive → `wait()` |
| 2000 | Le bus arrive → `notifyAll()` réveille les 3 usagers en attente, le bus repart |
| 3000 | **Patricia arrive → `wait()`… mais le `notifyAll()` a déjà eu lieu** |

→ `notifyAll()` n'est **pas mémorisé** : Patricia reste **bloquée indéfiniment** et empêche le programme de se terminer (il faut l'arrêter brutalement).

---

## 13. Priorités et ordonnancement

### Priorités
- Les threads peuvent avoir des **priorités d'exécution**, comprises entre `Thread.MIN_PRIORITY` et `Thread.MAX_PRIORITY`.
- Les priorités valent **« en moyenne »** : un thread de priorité plus élevée s'exécute **plus souvent**, pas forcément toujours en premier.
- `setPriority()` fixe la priorité.
- Deux threads de **même priorité** : par défaut **round robin** (à chacun son temps).

### Un thread cède le CPU lorsqu'il…
appelle `sleep()`, `wait()`, se bloque sur un `synchronized`, appelle `yield()` ou `stop()`.

### Règles d'ordonnancement

1. L'ordonnanceur s'assure que le thread de **plus haute priorité** (ou les threads, s'il y en a plusieurs d'égale priorité) est exécuté par le CPU.
2. Si un thread de plus haute priorité entre dans la file des threads prêts, le thread en cours est remis dans la file d'attente : il est **supplanté par préemption**.
3. Quand le thread en cours passe la main (`yield()`), est suspendu ou bloqué, l'ordonnanceur choisit dans la file prête le thread de **priorité la plus élevée** (l'un d'entre eux s'il y en a plusieurs).

---

## 14. Groupes de threads

Un `ThreadGroup` regroupe des threads afin d'**appliquer la même opération à tous** simultanément et de **structurer la sécurité**.

**Règles**
- Un thread **ne peut pas changer de groupe**.
- Un thread appartient au **groupe du thread qui l'a créé**.
- On obtient le groupe d'un thread avec `getThreadGroup()`.
- Constructeur permettant de créer un thread directement dans un groupe :

```java
public Thread(ThreadGroup group, Runnable runnable)
```

**Hiérarchie (arbre)**
```
System (groupe racine)
 └── main (groupe par défaut)
       └── thread "main" (exécute la méthode main())
```
- Le **groupe par défaut est `main`**.

---

## 15. Exercice 2 — Un producteur et deux consommateurs

**Énoncé :** Un producteur produit des objets qui sont consommés par **deux consommateurs différents**. Écrire les classes `TabObject`, `Producteur`, `Consommateur` et la classe principale. *(Suite en TP.)*

**Conception suggérée (déduite du contenu du cours)**

- `TabObject` : un **tampon** partagé (tableau) avec des méthodes `put()` et `get()` `synchronized`.
  - `put()` → `while (plein) wait();` puis ajout, puis `notifyAll()`.
  - `get()` → `while (vide) wait();` puis retrait, puis `notifyAll()`.
- `Producteur extends Thread` → boucle et appelle `put()`.
- `Consommateur extends Thread` → boucle et appelle `get()`.
- Classe principale : crée **un** `TabObject`, **un** producteur et **deux** consommateurs, puis démarre les trois.

Squelette :

```java
class TabObject {
    private final Object[] tab;
    private int count = 0, in = 0, out = 0;

    TabObject(int size) { tab = new Object[size]; }

    public synchronized void put(Object o) throws InterruptedException {
        while (count == tab.length) wait();
        tab[in] = o; in = (in + 1) % tab.length; count++;
        notifyAll();
    }

    public synchronized Object get() throws InterruptedException {
        while (count == 0) wait();
        Object o = tab[out]; out = (out + 1) % tab.length; count--;
        notifyAll();
        return o;
    }
}
```

> Avec **deux consommateurs**, `while` (et non `if`) et `notifyAll()` (et non `notify()`) sont **indispensables**.

---

## 16. Remarques importantes et corrections

Ces points dépassent ou **corrigent légèrement** les diapositives. Ils sont très utiles pour l'examen et pour de vrais projets.

1. **`start()` vs `run()`** — Toujours appeler `start()`. Appeler `run()` directement ne crée pas de thread.
2. **Méthodes dépréciées** — `stop()`, `suspend()`, `resume()` sont dépréciées depuis Java 1.2 (dangereuses). Utiliser un drapeau ou `interrupt()` :
   ```java
   private volatile boolean running = true;
   public void run() { while (running) { /* travail */ } }
   public void arreter() { running = false; }
   ```
3. **Les vrais états Java** — Depuis Java 5, `Thread.State` compte **6** valeurs : `NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING`, `TERMINATED`. Les « 7 états » des diapositives sont un **modèle conceptuel** de l'ancien Java.
4. **Toujours `while` avec `wait()`** — Revérifier la condition après le réveil :
   ```java
   while (!condition) wait();
   ```
5. **Ne pas ignorer `InterruptedException`** — Un `catch(InterruptedException e){}` vide (comme dans l'exercice 1) masque les demandes d'interruption. Mieux : `Thread.currentThread().interrupt();` ou un traitement approprié.
6. **`wait()` libère le verrou, `sleep()` non.** `sleep()` conserve les verrous détenus par le thread.
7. **`join()`** — À utiliser dans `main` quand il faut attendre la fin de tous les threads avant de continuer.
8. **Les priorités ne sont que des indications** — Le comportement dépend de l'OS ; ne jamais s'appuyer sur les priorités pour la correction du programme.
9. **Situation de compétition (*race condition*)** — Se produit quand deux threads accèdent à une donnée partagée et qu'au moins un la modifie, sans synchronisation.
10. **Interblocage (*deadlock*)** *(absent des diapositives)* — Deux threads détiennent chacun un verrou dont l'autre a besoin → ils attendent indéfiniment. On l'évite en acquérant toujours les verrous **dans le même ordre**.

---

## 17. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| Processus | Lourd ; mémoire et ressources propres |
| Thread | Léger ; partage la mémoire du processus ; pile et registres propres |
| Créer un thread (1) | `extends Thread` + redéfinir `run()` |
| Créer un thread (2) | `implements Runnable` + `new Thread(r)` |
| Démarrer | `t.start()` (pas `run()`) |
| Attendre la fin | `t.join()` |
| Faire une pause | `Thread.sleep(ms)` |
| Céder le CPU | `yield()` |
| Priorité | `setPriority()` entre `MIN_PRIORITY` et `MAX_PRIORITY` |
| Situation de compétition | Accès concurrent non protégé à une donnée partagée |
| Exclusion mutuelle | `synchronized` (méthode ou bloc) |
| Verrou | Chaque objet en possède un |
| `wait()` | Libère le verrou + attend dans le wait-set |
| `notify()` | Réveille un thread en attente |
| `notifyAll()` | Réveille tous les threads en attente |
| Notification perdue | `notify` **n'est pas mémorisé** |
| Producteur/Consommateur | `wait`/`notifyAll` sur un objet partagé |
| ThreadGroup | Arbre de groupes : `System` → `main` |

### Questions d'examen typiques
1. Quelle est la différence entre un processus et un thread ?
2. Quelles sont les deux façons de créer un thread en Java, et quand préférer `Runnable` ?
3. Pourquoi l'exemple du compte bancaire produit-il un solde négatif, et comment le corriger ?
4. Quelle différence entre `wait()`, `notify()` et `notifyAll()` ? Pourquoi doivent-elles être appelées dans un contexte synchronisé ?
5. Pourquoi Patricia reste-t-elle bloquée dans l'exemple du bus ?
6. Expliquer le cycle de vie d'un thread.
7. Écrire un producteur/consommateur avec un tampon partagé.

---

*Fin du résumé du chapitre 3 — Les threads en Java.*
