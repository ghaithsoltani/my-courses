# Chapitre 1 — Rappel : Notions de base Python
## Python pour les Sciences de Données
**Enseignante :** Dr. Abir Gorrab
**Année universitaire :** 2026-2027
**Type de document :** Résumé d'étude avec explications, schémas, code Python et conseils d'examen

---

## Table des matières

**Partie 1 — Présentation et bases**
1. [Présentation de Python](#1-présentation-de-python)
2. [Atouts de Python](#2-atouts-de-python)
3. [Un langage polyvalent, utilisé par les géants du numérique](#3-un-langage-polyvalent-utilisé-par-les-géants-du-numérique)
4. [Lecture : la saisie des données au clavier (`input()`)](#4-lecture--la-saisie-des-données-au-clavier-input)
5. [Écriture : la fonction `print()`](#5-écriture--la-fonction-print)
6. [Transtypage](#6-transtypage)
7. [Importation de fonctions (modules)](#7-importation-de-fonctions-modules)
8. [Applications (Partie 1)](#8-applications-partie-1)

**Partie 2 — Structures de contrôle**
9. [Plan de la partie 2](#9-plan-de-la-partie-2)
10. [Indentation des blocs d'instructions](#10-indentation-des-blocs-dinstructions)
11. [Structures conditionnelles : if - elif - else](#11-structures-conditionnelles--if---elif---else)
12. [Structures itératives : la boucle `while`](#12-structures-itératives--la-boucle-while)
13. [Structures itératives : la boucle `for`](#13-structures-itératives--la-boucle-for)
14. [Application (Partie 2)](#14-application-partie-2)
15. [Ruptures de séquences : `break`, `continue`, `else`](#15-ruptures-de-séquences--break-continue-else)
16. [Boucle « Répéter... jusqu'à » en Python](#16-boucle--répéter-jusquà--en-python)
17. [Contrôle des erreurs : les exceptions](#17-contrôle-des-erreurs--les-exceptions)

**Synthèse**
18. [Remarques sur le support de cours](#18-remarques-sur-le-support-de-cours)
19. [Fiche de révision rapide](#19-fiche-de-révision-rapide)

---

# PARTIE 1 — PRÉSENTATION ET BASES

## 1. Présentation de Python

### Le langage Python

| Caractéristique | Détail |
|---|---|
| **Créateur** | **Développé en 1989 par Guido van Rossum** |
| **Nature** | Langage de programmation **orienté objet**, de **haut niveau** |
| **Syntaxe** | **Très simple et concise** → **facile à apprendre** |
| **Proximité** | Proche du **« langage algorithmique »** |
| **Coût / disponibilité** | **Gratuit**, disponible sur la plupart des plateformes (**Windows, Mac, Linux**) |
| **Puissance** | Muni de **nombreuses bibliothèques de fonctions** |

### Les deux catégories de bibliothèques

| Catégorie | Exemples |
|---|---|
| **Bibliothèques « standard » Python** | Incluses avec Python (ex. `math`, `random`) |
| **Bibliothèques additionnelles** | **NumPy** (calcul scientifique) · **Pygame** (jeux vidéo en 2D) · **Matplotlib** (graphiques) |

---

## 2. Atouts de Python

| Atout | Explication |
|---|---|
| **Utilisé par les géants de l'informatique** | Microsoft, YouTube… |
| **Riche en outils** | **Plusieurs frameworks l'adoptent** |
| **Supporte l'OO** | Programmation **orientée objet** |
| **Simplicité et lisibilité du code** | |
| **Open Source** | **Gratuit** |
| **Multi-plateformes** | |
| **Puissant en production** | |

---

## 3. Un langage polyvalent, utilisé par les géants du numérique

### Polyvalence
> *« Nous pouvons presque tout faire avec Python grâce à ses bibliothèques variées. »*

- Utilisé par de **grands acteurs** dans le monde : **la NASA, Google, YouTube, Mozilla**…
- **De plus en plus utilisé** dans la **recherche**, l'**enseignement** et l'**industrie**.

### Exemples d'usage par domaine

| Domaine | Organisations | Technologie associée |
|---|---|---|
| **Web** | **Google** (pur Python et **Django**), **YouTube, Mozilla, Yahoo** | **Django*** |
| **Web (via Plone)** | **eBay, Nokia** | **Plone**** |
| **Scientifique** | **la NASA** | pur Python et **Plone** |

> \* **Django** est un **Framework** (plateforme de développement Web) **écrit en Python**.
> \*\* **Plone** est un **CMS (système de gestion de contenu)** basé sur Python *(précision ajoutée)*.

---

## 4. Lecture : la saisie des données au clavier (`input()`)

### Principe de la fonction `input()`

| Étape | Comportement |
|---|---|
| **Interruption** | Provoque une **interruption dans le programme courant** |
| **Saisie** | L'utilisateur est **invité à entrer des données au clavier** et à **terminer avec `<Enter>`** |
| **Reprise** | L'exécution du programme **se poursuit**, et la fonction **fournit en retour les valeurs entrées** |
| **Stockage** | Ces valeurs peuvent être **stockées dans des variables** |

> ⚠️ **Point essentiel** *(souligné par l'exemple du cours)* : **`input()` renvoie toujours une chaîne de caractères (`str`)**, **quel que soit** ce que l'utilisateur tape.

### Exemple du cours

```python
>>> print('entrer un entier')
>>> n = input()
>>> type(n)
```
→ Même si l'utilisateur tape un nombre, **`type(n)` donnera `<class 'str'>`** : il faut **convertir** (voir [section 6](#6-transtypage)) pour obtenir un entier utilisable dans des calculs.

```python
n = int(input('Donner un entier\n'))
```
→ *(Forme usuelle, combinant saisie et conversion en une ligne — pratique courante utilisée plus loin dans le cours, voir [section 17](#17-contrôle-des-erreurs--les-exceptions)).*

---

## 5. Écriture : la fonction `print()`

### 5.1 Affichage basique

> **`print`** est une **fonction de sortie** : elle **affiche à l'écran** (à l'attention de l'utilisateur) une **donnée** ou le **contenu d'une variable**.

```python
>>> print("texte")                       # affiche le message entre ' '
>>> print('texte')
>>> print(variable)                      # affiche la valeur de la variable
>>> print("texte1", var1, var2, ...)     # on peut afficher plusieurs choses sur une ligne
```

### 5.2 Écriture formatée : `format()`

> La méthode **`format()`** permet une **meilleure organisation** de l'affichage des variables.

```python
>>> x = 32
>>> nom = 'John'
>>> print('{} a {} ans'.format(nom, x))
John a 32 ans
```

### 5.3 Les options de `print()`

| Option | Rôle | Comportement par défaut |
|---|---|---|
| **`end = "..."`** | Caractère(s) placé(s) **entre deux affichages** (`print`), remplaçant le **saut de ligne par défaut** | `end = "\n"` |
| **`sep = "..."`** | Caractère(s) pour **séparer deux éléments** d'un **même affichage** | `sep = ""` |

### Exemples

```python
>>> print("bonjour ", "au revoir", end = "*****")
>>> print("Salut !")
```
→ Remplace le **saut de ligne** entre les deux `print` par `*****`.

```python
>>> print("Bonjour", "Au revoir", "Salut", sep = "****")
```
→ Sépare **chaque élément** de l'affichage par `****`.

> 💡 *(Ajout)* Résultat attendu du premier exemple : `bonjour  au revoir*****Salut !` (les deux `print` s'enchaînent sur la **même ligne**, séparés par `*****` au lieu d'un retour à la ligne).

---

## 6. Transtypage

> **Transtypage = conversion de données entre les différents types.**

### Schéma des types de base (du cours)

```
                    int
                  ↗  ↑  ↖
              bool ←——→ str
                  ↖  ↓  ↗
                   float
```
→ **Conversions possibles dans toutes les directions** entre **`int`, `bool`, `str` et `float`**, selon le contexte.

### Exemples du cours (vérifiés)

| Expression | Résultat |
|---|---|
| `"3" * 5` | **`"33333"`** *(répétition d'une chaîne, pas une multiplication numérique)* |
| `int("3") * 5` | **`15`** *(conversion en entier, puis multiplication numérique)* |
| `str(3.14) + " radians"` | **`"3.14 radians"`** *(conversion en chaîne puis concaténation)* |
| `int(11.8)` | **`11`** *(troncature, pas d'arrondi)* |
| `float("1.602176")` | **`1.602176`** |

### Fonctions de conversion à retenir

| Fonction | Rôle |
|---|---|
| **`int(x)`** | Convertit `x` en **entier** (tronque les décimales d'un float) |
| **`float(x)`** | Convertit `x` en **nombre décimal** |
| **`str(x)`** | Convertit `x` en **chaîne de caractères** |
| **`bool(x)`** | Convertit `x` en **booléen** |

> 🔑 **Piège classique** *(ajout)* : `"3" * 5` donne `"33333"` (la chaîne `"3"` est **répétée 5 fois**), **pas** `15`. C'est l'erreur type d'un débutant qui confond `+`/`*` sur les chaînes et sur les nombres.

> 💡 **À tester dans l'interpréteur** *(note du cours)* : ces exemples sont à **essayer directement** en ligne de commande Python pour bien observer les résultats.

---

## 7. Importation de fonctions (modules)

### Pourquoi importer ?
- On a parfois besoin d'utiliser des **fonctions de Python qui ne sont pas chargées par défaut**.
- Ces fonctions sont stockées dans des **programmes Python** appelés **modules** ou **bibliothèques**.

### Deux modules cités en exemple

| Module | Contenu |
|---|---|
| **`math`** | **Fonctions mathématiques usuelles** |
| **`random`** | **Plusieurs types de générateurs de nombres pseudo-aléatoires** |

### Comment importer un module ? Deux façons

**Première façon : importer le module entier**

```python
# import du module de mathématique (création d'un point d'accès)
import math

# pour utiliser la fonction sqrt, on préfixe du nom du module et d'un point
racine = math.sqrt(2)
```

**Deuxième façon : importer une fonction précise du module**

```python
# import de la fonction sqrt du module math
from math import sqrt

racine = sqrt(2)

# Pour importer toutes les fonctions de math, écrire :
# from math import *
```

### Comparaison des deux approches

| | **`import math`** | **`from math import sqrt`** |
|---|---|---|
| **Appel de la fonction** | **`math.sqrt(2)`** (préfixé) | **`sqrt(2)`** (direct) |
| **Avantage** | Évite les **conflits de noms** entre modules | Code **plus court** |
| **`from math import *`** | Importe **toutes** les fonctions du module — **déconseillé en général** *(risque de conflits de noms, précision ajoutée)* | |

---

## 8. Applications (Partie 1)

### Application 1 — Volume d'un cône

> Écrire un programme qui, à partir de la **saisie d'un rayon et d'une hauteur**, calcule puis affiche le **volume d'un cône** suivant cette formule : **Π × R² × h / 3**.

**Proposition de solution *(ajout, non fournie dans le support)* :**

```python
import math

R = float(input("Entrer le rayon : "))
h = float(input("Entrer la hauteur : "))

volume = math.pi * R**2 * h / 3
print("Le volume du cône est : {}".format(volume))
```

| Point d'attention | Explication |
|---|---|
| **`float(input(...))`** | **`input()` renvoie une chaîne** ; il faut la **convertir** pour calculer |
| **`math.pi`** | **Constante π** fournie par le module `math` |
| **`R**2`** | Syntaxe Python pour **la puissance** (`**`, pas `^`) |

### Application 2 — Division entière vs division réelle

> Les expressions suivantes sont-elles **équivalentes** ?
> ```python
> >>> 9.5 / 2.5
> >>> int(9.5) / int(2.5)
> >>> int(9.5 / 2.5)
> ```

**Résolution *(ajout)* :**

| Expression | Calcul | Résultat |
|---|---|---|
| `9.5 / 2.5` | Division **réelle** directe | **`3.8`** |
| `int(9.5) / int(2.5)` | `int(9.5)=9`, `int(2.5)=2` → `9 / 2` | **`4.5`** |
| `int(9.5 / 2.5)` | `9.5/2.5 = 3.8` → tronqué | **`3`** |

> 🔑 **Conclusion : NON, les trois expressions ne sont PAS équivalentes.** L'**ordre des opérations** (convertir **avant** ou **après** la division) change **complètement** le résultat, car `int()` **tronque** (ne pas arrondir) **avant même que la division n'ait lieu** dans les deux derniers cas.

---

# PARTIE 2 — STRUCTURES DE CONTRÔLE

## 9. Plan de la partie 2

1. **Indentation des blocs d'instructions**
2. **Structures conditionnelles**
3. **Structures itératives**
   - La boucle `while`
   - La boucle `for`
4. **Ruptures de séquences**
   - Instruction `break`
   - Instruction `continue`
   - Instruction `else`
5. **Contrôle des erreurs**

---

## 10. Indentation des blocs d'instructions

### 10.1 Introduction

| Point | Détail |
|---|---|
| **Nature** | Python est un **langage de programmation structurée** |
| **Conséquence pédagogique** | Python **force** à écrire du **code lisible**, et à prendre de **bonnes habitudes** conservées dans d'autres langages |
| **Structures de contrôle reconnues** | **1) Structures conditionnelles** · **2) Structures itératives** |
| **Délimitation des blocs** | Les blocs (**fonctions, `if`, boucles `for`/`while`**, etc.) sont définis par leur **indentation** : l'**indentation démarre** le bloc, la **désindentation le termine** |

### 10.2 Blocs d'instructions composées

> **Instruction composée = une ligne d'en-tête + un bloc d'instructions indenté** par rapport à la ligne d'en-tête.

| Règle | Détail |
|---|---|
| **Ligne d'en-tête** | Contient une instruction spécifique (**`if`, `elif`, `else`, `while`, `def`**...) et se **termine par un double point `:`** |
| **Fin de bloc** | Le **retour au niveau d'indentation** de la ligne d'en-tête indique qu'on est **sorti du bloc** |
| **Imbrication** | Les blocs peuvent être **imbriqués à plusieurs niveaux** |
| **Lisibilité** | Avec l'indentation, le **découpage est visuel** |

### 10.3 Règles précises de l'indentation

| Règle | Détail |
|---|---|
| **Définition** | **Espacer les lignes de code** par rapport au **bord gauche** de la fenêtre de saisie |
| **Obligation** | Cette indentation est **obligatoire en Python** |
| **Taille par défaut** | Réglée **automatiquement sur 4 espaces** |
| **Proportionnalité** | La taille de l'espacement doit être **proportionnelle au niveau d'imbrication** |
| **Avantage** | Entraîne une **grande lisibilité et légèreté du code** (**absence d'accolades, de points-virgules**...) |

> 🔑 **Comparaison avec d'autres langages** *(ajout)* : là où **Java ou C** utilisent des **accolades `{ }`** pour délimiter un bloc, **Python utilise uniquement l'indentation** — mélanger espaces et tabulations, ou mal indenter, provoque une **`IndentationError`**.

---

## 11. Structures conditionnelles : if - elif - else

### 11.1 Schéma de la structure complète

```
if condition1 :
    Bloc exécuté si condition1 est vrai.

elif condition2 :                 ⎫
    Bloc exécuté si condition1    ⎬  optionnel, répétable
    est faux et condition2 est vrai. ⎭

else :                            ⎫
    Bloc exécuté si aucune        ⎬  optionnel
    condition n'est vraie.        ⎭
```

### 11.2 Remarques essentielles

| Remarque | Détail |
|---|---|
| **Forme complète** | L'exemple illustre la forme **complète** de la structure conditionnelle |
| **`elif` et `else`** | Sont **facultatifs** |
| **Tests multiples** | On peut **cascader plusieurs parties `elif`** |
| **Les deux-points `:`** | **Attention**, ils sont **primordiaux** |
| **Délimitation du bloc** | C'est **l'indentation** qui délimite le bloc d'instructions |
| **Conditions** | Ce sont des **expressions logiques booléennes**, évaluées à **`True`** ou **`False`** |

### Exemple *(ajout, pour illustrer la structure)*

```python
note = 12

if note >= 16:
    print("Très bien")
elif note >= 14:
    print("Bien")
elif note >= 10:
    print("Passable")
else:
    print("Insuffisant")
```

---

## 12. Structures itératives : la boucle `while`

### 12.1 Schéma de la boucle (avec organigramme)

```python
while condition :
    Bloc d'instructions.
    Exécuté tant que la
    condition est vraie.
```

```
          ┌──────────┐
          │ condition│──Faux──► (sortie)
          └────┬─────┘
             Vrai
               │
               ▼
        ┌──────────────┐
        │Bloc d'instru- │
        │ctions          │──┐
        └──────────────┘  │
               ▲              │
               └──────────────┘
         (retour tester la condition)
```

### 12.2 Points d'attention

| Point | Détail |
|---|---|
| **Principe** | **Exécution du bloc tant que la condition est vérifiée** |
| **Syntaxe** | **Ne pas oublier l'indentation et le `:`** |
| **Risque de boucle infinie** | Si le **test d'arrêt n'est jamais vérifié** → **il faut s'assurer que la condition puisse, à un moment, devenir fausse** |

### Exemple *(ajout)*

```python
i = 0
while i < 5:
    print(i)
    i = i + 1        # indispensable pour éviter une boucle infinie
```

---

## 13. Structures itératives : la boucle `for`

### 13.1 Schéma de la boucle (avec organigramme)

```python
for variable in séquence :
    Bloc d'instructions.
    Exécuté avec variable valant
    tour à tour chacune des
    valeurs de séquence.
```

```
         variable = valeur suivante
                   │
                   ▼
            ┌──────────────┐
            │ fin de        │──Vrai──► (sortie)
            │ séquence ?    │
            └──────┬────────┘
                 Faux
                   │
                   ▼
           ┌──────────────┐
           │Bloc           │
           │d'instructions │──┐
           └──────────────┘  │
                   ▲            │
                   └────────────┘
```

### 13.2 Objets itérables (conteneurs)

> Plus généralement, cette boucle est utilisée avec des **objets itérables (les conteneurs)** : **intervalle, liste, chaîne, tuple, ensemble, dictionnaire, tableau**...

- On parcourt en premier temps les éléments de l'objet **intervalle** avec la fonction **`range`**.

### 13.3 La fonction `range` : construire une suite d'entiers

> En Python, la fonction **`range`** **crée un itérateur**, qui est une sorte de **distributeur d'entiers consécutifs**.

| Forme | Intervalle parcouru |
|---|---|
| **`range(n)`** | Les entiers consécutifs dans **`[0, n[`** |
| **`range(m, n)`** | Les entiers consécutifs dans **`[m, n[`** |
| **`range(m, n, s)`** | Les entiers consécutifs dans **`[m, n[`** **avec un pas de `s`** |

### Exemples *(ajout, pour illustrer)*

```python
for i in range(5):          # 0, 1, 2, 3, 4
    print(i)

for i in range(2, 7):       # 2, 3, 4, 5, 6
    print(i)

for i in range(0, 10, 2):   # 0, 2, 4, 6, 8
    print(i)
```

---

## 14. Application (Partie 2)

> Écrire un script (**triangle**) qui affiche un **triangle rempli d'étoiles**, s'étendant sur un **nombre de lignes fourni en donnée**, et se présentant comme dans cet exemple :
> ```
> *
> **
> ***
> ****
> *****
> ```

**Proposition de solution *(ajout, non fournie dans le support)* :**

```python
n = int(input("Entrer le nombre de lignes : "))

for i in range(1, n + 1):
    print("*" * i)
```

| Ligne | Explication |
|---|---|
| `"*" * i` | **Répétition de chaîne** (voir [transtypage](#6-transtypage)) : affiche **`i` étoiles** sur la ligne |
| `range(1, n + 1)` | Parcourt **1, 2, …, n** (inclus), grâce au **`+1`** (rappel : `range` exclut la borne supérieure) |

---

## 15. Ruptures de séquences : `break`, `continue`, `else`

### 15.1 Instruction `break` — Interrompre une boucle

| Point | Détail |
|---|---|
| **Effet** | **Rupture du flux normal** des instructions |
| **Comportement** | **Sortie immédiate** du bloc d'instructions de la boucle **`for`** ou **`while`** en cours |

### 15.2 Instruction `continue` — Court-circuiter une boucle

| Point | Détail |
|---|---|
| **Effet** | **Rupture du flux normal** des instructions |
| **Comportement** | **Passage à l'itération suivante** de la boucle `for` ou `while` en cours |
| **Reprise** | **Reprise à la ligne de l'en-tête** de la boucle |

### 15.3 Comparaison `break` vs `continue`

| | **`break`** | **`continue`** |
|---|---|---|
| **Action** | **Quitte complètement** la boucle | **Saute** seulement l'itération en cours |
| **Suite du programme** | Continue **après** la boucle | Continue **dans** la boucle (itération suivante) |

### Exemples *(ajout, pour illustrer)*

```python
# break : s'arrête dès qu'on trouve 5
for i in range(10):
    if i == 5:
        break
    print(i)          # affiche 0, 1, 2, 3, 4

# continue : saute les nombres pairs
for i in range(6):
    if i % 2 == 0:
        continue
    print(i)           # affiche 1, 3, 5
```

### 15.4 Instruction `else` d'une boucle — Traitement alternatif

> Le bloc **`else`** d'une boucle `for` (ou `while`) est exécuté **si la boucle se termine normalement** (c'est-à-dire **sans `break`**).

### Exemple du cours : test de nombre premier

```python
n = 10
for i in range(2, n // 2 + 1):
    if n % i == 0:
        print('non premier')
        break
else:  # du for et pas du if
    print('premier')
```

| Élément | Explication |
|---|---|
| **`n // 2 + 1`** | **Division entière** (`//`), on ne teste que jusqu'à la **moitié** de `n` |
| **`n % i == 0`** | Si **`n` est divisible par `i`** → ce n'est **pas un nombre premier** |
| **`break`** | Dès qu'un diviseur est trouvé, on **sort immédiatement** |
| **`else` du `for`** *(pas du `if` !)* | S'exécute **uniquement si la boucle s'est terminée sans `break`** → **tous les tests ont échoué** → le nombre **est premier** |

> 🔑 **Piège classique :** l'`else` ici est **associé au `for`**, pas au `if` qui le précède (même s'ils sont au même niveau d'indentation visuel). Le commentaire du cours **`# du for et pas du if`** insiste bien là-dessus.

> ⚠️ **Avec n = 10 :** `range(2, 6)` = `2, 3, 4, 5` → dès `i=2`, `10 % 2 == 0` → **« non premier »** s'affiche, et le `break` empêche l'exécution du `else`. *(Vérification du résultat, ajout.)*

---

## 16. Boucle « Répéter... jusqu'à » en Python

### Le problème
Python **ne possède pas** nativement de structure **« repeat...until »** (contrairement à certains langages comme Pascal). On la **simule** avec une boucle `while` infinie et un `break` conditionnel.

### Construction (schéma du cours)

```python
while 1 :              # while True
    Bloc instructions
    if condition :
        break
```

| Élément | Explication |
|---|---|
| **`while 1`** | Équivaut à **`while True`** : une **boucle infinie** par défaut |
| **Bloc instructions** | Exécuté **au moins une fois**, à chaque passage |
| **`if condition: break`** | **Condition de sortie** : dès qu'elle est vraie, on **arrête la boucle** |

> 🔑 **Différence avec `while` classique** *(ajout)* : une boucle `while condition` teste **avant** d'exécuter le bloc (peut ne **jamais s'exécuter**) ; cette construction **« repeat...until »** garantit que le bloc s'exécute **au moins une fois**, la condition étant testée **à l'intérieur**, après une première exécution.

---

## 17. Contrôle des erreurs : les exceptions

### 17.1 Pourquoi gérer les erreurs ?

> Afin de rendre les applications **plus robustes**, il est nécessaire de **gérer les erreurs d'exécution** des parties sensibles du code.

### 17.2 Le mécanisme des exceptions

> Le mécanisme des exceptions **sépare** :
> - d'un côté, la **séquence d'instructions à exécuter lorsque tout se passe bien** ;
> - d'un autre côté, **une ou plusieurs séquences d'instructions à exécuter en cas d'erreur**.

### 17.3 Syntaxe générale

```python
try:
    ...     # séquence normale d'exécution
except:
    ...     # traitement de l'exception
else:
    ...     # clause exécutée en l'absence d'erreur
```

| Bloc | Rôle |
|---|---|
| **`try`** | Contient la **séquence normale** à tenter |
| **`except`** | Exécuté **si une erreur survient** dans le `try` |
| **`else`** | Exécuté **uniquement si aucune erreur ne s'est produite** dans le `try` |

### 17.4 Quelques exceptions prédéfinies

| Exception | Se produit quand… |
|---|---|
| **`IndexError`** | On cherche à **accéder à un élément d'une liste**, **en dehors des limites** de celle-ci |
| **`NameError`** | On **évoque une variable** dont le **nom n'existe pas** |
| **`SyntaxError`** | **Python échoue à lire** une expression dont la **syntaxe est erronée** |
| **`TypeError`** | Une **opération est appliquée à un objet** qui **n'est pas du type adéquat** |
| **`ZeroDivisionError`** | Un **calcul arithmétique** conduit à une **division par 0** |

### 17.5 Exemple complet du cours

```python
>>> while 1 :
        try :
            n = int(input('Donner un entier \n'))
        except :
            print('Type incorrect')
        else :
            if n > 0 :
                break
```

**Trace d'exécution donnée par le cours :**

```
Donner un entier
a
Type incorrect
Donner un entier
-1
Type incorrect
Donner un entier
5
```

> ⚠️ **Incohérence du support** *(voir [section 18](#18-remarques-sur-le-support-de-cours))* : la trace affichée montre `'Type incorrect'` après la saisie de **`-1`**, alors que `-1` **se convertit sans erreur** en `int` (le `TypeError`/`ValueError` ne devrait se produire **que pour `'a'`**, qui n'est pas un nombre). Avec `-1`, le code devrait passer dans le bloc **`else`**, où la condition `n > 0` est **fausse** (donc **ni `break` ni message d'erreur**, la boucle continue silencieusement). L'analyse correcte est donnée ci-dessous.

### Déroulement **correct** de cet exemple *(analyse ajoutée)*

| Saisie | Que se passe-t-il ? | Message affiché |
|---|---|---|
| **`'a'`** | `int('a')` lève une **`ValueError`** → bloc `except` | **`Type incorrect`** |
| **`-1`** | `int('-1')` réussit → bloc `else` → `n > 0` est **`False`** → **aucun `break`**, la boucle **continue** | *(aucun message)* |
| **`5`** | `int('5')` réussit → bloc `else` → `n > 0` est **`True`** → **`break`** | *(fin de boucle, aucun message)* |

> 🔑 **Ce que montre réellement cet exemple :**
> 1. le **`try/except`** protège contre une **saisie non numérique** ;
> 2. le **`else`** ne s'exécute **que si la conversion a réussi** ;
> 3. la boucle **`while 1`** + le **`break`** conditionnel réalisent bien le **« répéter...jusqu'à »** vu en [section 16](#16-boucle--répéter-jusquà--en-python) : on **répète la demande** **jusqu'à ce qu'un entier strictement positif soit saisi**.

---

## 18. Remarques sur le support de cours

1. **Deux fichiers pour un seul chapitre** : le chapitre 1 est **scindé en deux PDF** (Part 1 : 13 pages — présentation et E/S de base ; Part 2 : 18 pages — structures de contrôle). Ce résumé **réunit les deux parties**.
2. **Nombreuses diapositives « images »** : plusieurs diapositives du support (saisies d'écran de code, schémas d'organigrammes) ne contiennent **pas de texte extractible** ; leur contenu a été **retranscrit fidèlement** dans ce résumé à partir d'une lecture visuelle des diapositives.
3. **Exceptions en Python 3** : le support utilise la syntaxe **`except:`** (sans préciser le type d'exception) — en pratique, et en **Python 3** **il est recommandé de préciser le type** (ex. `except ValueError:`) pour **éviter de masquer des erreurs inattendues** *(bonne pratique ajoutée, non mentionnée dans le support)*.
4. **Incohérence dans l'exemple d'exceptions** (section 17.5) : la **trace d'exécution** donnée dans le support affiche *« Type incorrect »* après la saisie de **`-1`**, ce qui est **incorrect du point de vue du code montré** (`int('-1')` ne lève **pas** d'exception). L'analyse correcte est détaillée dans ce résumé, en section 17.
5. **`while 1` vs `while True`** : équivalents en Python (`1` est **« truthy »**), mais l'écriture moderne et **recommandée** est **`while True:`**, plus explicite *(remarque de bonne pratique ajoutée)*.
6. **Module Plone** : cité par le support (eBay, Nokia, la NASA) mais **non expliqué** — c'est un **système de gestion de contenu (CMS)** écrit en Python ; précision ajoutée en section 3.
7. **Les deux applications pratiques** (volume du cône, triangle d'étoiles) sont **des énoncés sans solution** dans le support : les **solutions proposées** dans ce résumé (sections 8 et 14) sont des **ajouts pédagogiques**, à vérifier/adapter.
8. **Encodage des caractères spéciaux** : le texte extrait montre parfois des caractères mal encodés (ex. « châ ıne », « réglée automati quement ») dus à l'extraction PDF ; cela n'affecte pas le contenu pédagogique.
9. **`Type(n)` vs `type(n)`** : le support écrit `>>> Type(n)` avec une majuscule (diapositive 7, Part 1) ; en Python, la fonction s'écrit avec un **t minuscule : `type(n)`** (coquille du support).

---

## 19. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Python** | Langage **orienté objet**, **haut niveau**, créé en **1989** par **Guido van Rossum** |
| **Atouts** | **Gratuit, multiplateforme, lisible, riche en bibliothèques**, utilisé par de grands acteurs |
| **`input()`** | **Interrompt** le programme, **attend une saisie**, renvoie **toujours une chaîne (`str`)** |
| **`print()`** | Fonction de **sortie** ; options **`end`** (fin d'affichage) et **`sep`** (séparateur) |
| **`format()`** | Écriture **formatée** : `'{} a {} ans'.format(nom, x)` |
| **Transtypage** | Conversion entre types : **`int()`, `float()`, `str()`, `bool()`** |
| **Piège** | `"3" * 5` → **`"33333"`** (répétition), pas une multiplication |
| **Modules** | **`math`** (fonctions mathématiques), **`random`** (nombres pseudo-aléatoires) |
| **Importer** | **`import math`** (préfixé : `math.sqrt`) ou **`from math import sqrt`** (direct : `sqrt`) |
| **Indentation** | **Obligatoire**, **4 espaces** par défaut, délimite les **blocs** (pas d'accolades) |
| **Instruction composée** | **Ligne d'en-tête + `:`** + **bloc indenté** |
| **`if / elif / else`** | `elif` et `else` **facultatifs** ; conditions = **expressions booléennes** (`True`/`False`) |
| **`while`** | Exécute **tant que** la condition est vraie ; **risque de boucle infinie** |
| **`for`** | Parcourt un **objet itérable** (intervalle, liste, chaîne, tuple, ensemble, dictionnaire…) |
| **`range(n)`** | Entiers de **`[0, n[`** |
| **`range(m, n)`** | Entiers de **`[m, n[`** |
| **`range(m, n, s)`** | Entiers de **`[m, n[`** avec un **pas `s`** |
| **`break`** | **Sort immédiatement** de la boucle |
| **`continue`** | **Passe à l'itération suivante** |
| **`else` d'une boucle** | S'exécute **seulement si pas de `break`** (boucle terminée normalement) |
| **Répéter...jusqu'à** | `while 1: ... if condition: break` |
| **Exceptions** | `try` (normal) / `except` (erreur) / `else` (si pas d'erreur) |
| **5 exceptions courantes** | **IndexError, NameError, SyntaxError, TypeError, ZeroDivisionError** |

### Questions d'examen typiques
1. Qui a créé Python et en quelle année ? Citer **trois atouts** de ce langage.
2. Citer des **organisations** qui utilisent Python et dans **quel domaine**.
3. Que renvoie **`input()`** ? Pourquoi faut-il parfois **convertir** sa valeur de retour ?
4. Quelle est la différence entre les options **`end`** et **`sep`** de `print()` ? Donner un exemple.
5. **Transtypage :** prédire le résultat de `"3" * 5`, `int("3") * 5`, `int(11.8)`.
6. Citer les **deux façons d'importer** un module et leurs **différences**.
7. **Résoudre** : `9.5/2.5`, `int(9.5)/int(2.5)`, `int(9.5/2.5)` sont-ils équivalents ? Justifier.
8. Pourquoi l'**indentation** est-elle **obligatoire** en Python ? Quelle est sa **taille par défaut** ?
9. Décrire la structure **`if / elif / else`** : que faut-il préciser sur le `:` et les parties optionnelles ?
10. Expliquer le **risque de boucle infinie** avec `while`. Comment l'éviter ?
11. Que renvoient **`range(5)`**, **`range(2,7)`** et **`range(0,10,2)`** ?
12. Différence entre **`break`** et **`continue`** ; donner un exemple de chacun.
13. Expliquer le rôle du **`else` d'une boucle `for`** ; pourquoi dit-on qu'il est associé **au `for` et pas au `if`** ?
14. Comment **simuler** une boucle « répéter...jusqu'à » en Python ?
15. Écrire la **syntaxe générale** d'une gestion d'exceptions (`try/except/else`) et citer **trois exceptions prédéfinies**.
16. **Écrire** un programme qui calcule le **volume d'un cône** à partir d'un rayon et d'une hauteur saisis.
17. **Écrire** un script qui affiche un **triangle d'étoiles** sur `n` lignes.

---

*Fin du résumé — Chapitre 1 : Rappel, Notions de base Python (Parties 1 et 2).*
