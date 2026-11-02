# Chapitre 4 — Services de la sécurité informatique
## Authentification · Cryptographie · Signature numérique · Certificats et PKI
**Établissement :** Institut Supérieur d'Informatique du Kef
**Enseignante :** Mounira Laabidi
**Cours :** Sécurité informatique — 3LGL
**Type de document :** Résumé d'étude avec explications, schémas, exemples chiffrés et conseils d'examen

---

## Table des matières

**Partie A — Authentification**
1. [Plan du chapitre](#1-plan-du-chapitre)
2. [Identification et authentification](#2-identification-et-authentification)
3. [Les quatre moyens d'authentification](#3-les-quatre-moyens-dauthentification)
4. [Les mots de passe](#4-les-mots-de-passe)
5. [Le mot de passe unique (OTP)](#5-le-mot-de-passe-unique-otp)
6. [Authentification par objet](#6-authentification-par-objet)
7. [Authentification par caractère : la biométrie](#7-authentification-par-caractère--la-biométrie)
8. [Authentification par savoir-faire](#8-authentification-par-savoir-faire)

**Partie B — Cryptographie**
9. [Terminologie et classification](#9-terminologie-et-classification)
10. [Crypto-système, cryptanalyse et principes de Kerckhoffs](#10-crypto-système-cryptanalyse-et-principes-de-kerckhoffs)
11. [Chiffrement symétrique](#11-chiffrement-symétrique)
12. [DES (Data Encryption Standard)](#12-des-data-encryption-standard)
13. [Triple-DES](#13-triple-des)
14. [AES (Advanced Encryption Standard)](#14-aes-advanced-encryption-standard)
15. [Chiffrement asymétrique](#15-chiffrement-asymétrique)
16. [Scénarios d'utilisation, chiffrement mixte et clés de session](#16-scénarios-dutilisation-chiffrement-mixte-et-clés-de-session)
17. [RSA](#17-rsa)
18. [Avantages et inconvénients de l'asymétrique](#18-avantages-et-inconvénients-de-lasymétrique)

**Partie C — Intégrité, signature, certificats**
19. [Fonctions de hachage](#19-fonctions-de-hachage)
20. [Signature numérique](#20-signature-numérique)
21. [Certificats numériques et standards PKCS](#21-certificats-numériques-et-standards-pkcs)
22. [Gestion des clés publiques et PKI](#22-gestion-des-clés-publiques-et-pki)
23. [Certificats X.509](#23-certificats-x509)
24. [Performances : quelques chiffres](#24-performances--quelques-chiffres)

**Synthèse**
25. [Quel service de sécurité par quel mécanisme ? (ajout)](#25-quel-service-de-sécurité-par-quel-mécanisme--ajout)
26. [Remarques sur le support de cours](#26-remarques-sur-le-support-de-cours)
27. [Fiche de révision rapide](#27-fiche-de-révision-rapide)

---

# PARTIE A — AUTHENTIFICATION

## 1. Plan du chapitre

Le plan annonce : **Définition · Authentification · Confidentialité · Intégrité · Non-répudiation · Disponibilité · Services de sécurité vis-à-vis des attaques**.

> ⚠️ **Ce que couvre réellement ce PDF (113 diapositives)** :
> - l'**authentification** (diapositives 3 à 18),
> - la **cryptographie** (diapos 19 à 113) : chiffrement **symétrique** (DES, 3DES, AES), **asymétrique** (RSA), **hachage**, **signature numérique**, **certificats**, **PKI** et **X.509**.
>
> La **confidentialité**, l'**intégrité** et la **non-répudiation** sont donc traitées **à travers leurs mécanismes cryptographiques**. La **disponibilité** et les **services vis-à-vis des attaques** annoncés au plan **ne sont pas détaillés** dans ce fichier.

### Rappel : les services de sécurité

| Service | Définition | Mécanisme principal (voir section 25) |
|---|---|---|
| **Authentification** | Prouver l'**identité** | Mot de passe, OTP, biométrie, signature, certificat |
| **Confidentialité** | Seuls les **autorisés** lisent | **Chiffrement** |
| **Intégrité** | Données **non altérées** | **Hachage**, **signature** |
| **Non-répudiation** | On ne peut **nier** avoir envoyé/signé | **Signature numérique** |
| **Disponibilité** | Service **accessible** | (hors PDF) |

---

## 2. Identification et authentification

- **S'authentifier = apporter la preuve de son identité.**
- L'authentification vient **après** une phase d'**identification**, qui consiste à **établir l'identité annoncée** par l'utilisateur.
- On parle souvent de la phase **I/A** (identification – authentification).

### Les deux phases

| Phase | Principe | Exemple |
|---|---|---|
| **Identification** | Faire correspondre une **identité numérique** à une **personne**. Chaque personne ayant accès au SI doit recevoir **un identifiant unique** (1 personne = 1 identifiant) | Login « Dupond » |
| **Authentification** | Repose **toujours sur un challenge** lancé par le **système de destination** au **client** pour qu'il **prouve son identité** : *« Qui êtes-vous ? Prouvez-le. »* | Mot de passe, clé privée, empreinte digitale… |

> 🔑 **La réussite du challenge valide l'association client ↔ identifiant** : *« Mon mot de passe est toto » → « OK, c'est DUPOND »*.

```
 Client (Dupond)                            Système
     │──── identifiant : "Dupond" ─────────►│   (identification)
     │◄─── "Qui êtes-vous ? Prouvez-le." ───│   (challenge)
     │──── "Mon mot de passe est ..." ─────►│   (preuve)
     │◄─── "OK, c'est DUPOND" ──────────────│   (authentification réussie)
```

---

## 3. Les quatre moyens d'authentification

| Moyen | Principe | Exemple |
|---|---|---|
| **Le secret** (*ce que je sais*) | Une information connue de moi seul | **Mot de passe** |
| **L'objet** (*ce que je possède*) | Un dispositif physique | **Jeton** (token), carte |
| **Le caractère** (*ce que je suis*) | Une caractéristique physique | **Biométrie** |
| **Le savoir-faire** (*ce que je sais faire*) | Un geste propre à la personne | **Signature manuscrite** |

> 🔐 **La combinaison de plusieurs méthodes d'authentification permet de mettre en place des solutions d'authentification FORTE** (ex. carte + code PIN = **double facteur**).

---

## 4. Les mots de passe

### 4.1 Généralités
- Le mot de passe reste le **mécanisme de déverrouillage le plus répandu** : il **ne nécessite pas d'ajout de dispositif technique**.
- **Risques principaux :** la **divulgation** et la **faiblesse**.

### 4.2 Bonnes pratiques

| Pratique | Explication |
|---|---|
| **Domaine d'utilisation le plus restreint possible** | **Un mot de passe différent par usage** (limite le risque de divulgation) |
| **Résister aux techniques de craquage connues** | Voir tableau ci-dessous |

**Temps de craquage cités par le cours :**

| Mot de passe | Temps pour le craquer |
|---|---|
| **8 caractères alphabétiques** [A-Z] | **≈ 1 heure** |
| **10 caractères alphabétiques** | **≈ 1 mois** |
| **8 caractères alphanumériques** [A-Za-z0-9] | **≈ 1 mois** |

> 💡 *(Explication ajoutée)* : 26⁸ ≈ 2 × 10¹¹ combinaisons, alors que 26¹⁰ ≈ 1,4 × 10¹⁴ et 62⁸ ≈ 2,2 × 10¹⁴ : environ **700 fois plus**, d'où le passage d'**1 heure à ~1 mois**. **Plus l'alphabet est grand et le mot de passe long, plus c'est solide.**

### 4.3 Fabriquer et retenir un mot de passe fort

| Méthode | Principe | Exemple du cours |
|---|---|---|
| **Phonétique** | Utiliser les **sons** de chaque syllabe pour fabriquer une phrase facile à retenir | « j'ai acheté huit cd pour cent euros cet après-midi » → **`ght8CD%E7am`** |
| **Premières lettres** | Garder les **premières lettres** d'une phrase (citation, chanson…), **en ne mettant pas que des minuscules** | « un tiens vaut mieux que deux tu l'auras » → **`1TvmQ2tl'@`** |

### 4.4 Problèmes liés aux mots de passe

**① Gestion :** création, modification, déblocage, suppression → **stockage**, **transfert sur le réseau**, **rejet** (réutilisation frauduleuse), **mémorisation difficile**, **charge pour l'administrateur**.

**② Verrouillage de compte :** la plupart des systèmes **bloquent temporairement** un compte après plusieurs échecs.
- ✅ Un pirate a du mal à s'infiltrer ainsi.
- ⚠️ **En contrepartie**, il peut **détourner ce mécanisme** pour **bloquer tous les comptes** → **déni de service**.

**③ Stockage chiffré :** les mots de passe sont stockés **chiffrés** dans un fichier ou une base. Si le pirate **obtient ce fichier**, il peut tenter de **casser** les mots de passe **hors ligne**.

### 4.5 Techniques de craquage

| Technique | Description |
|---|---|
| **Force brute** | **Tester tous les mots de passe possibles**. Plus il y a de combinaisons, plus le temps est long. *« Ajouter un caractère à un mot de passe alphanumérique double le temps »* (selon le cours) |
| **Dictionnaire** | Tester une série de mots issus d'un **dictionnaire** (noms, marques…) |
| **Hybride** | **Force brute + dictionnaire** (mots du dictionnaire avec variations : chiffres, majuscules…) |
| **Attaque par rejet** (*replay*) | **Réutiliser** un mot de passe (ou une authentification) intercepté |

### 4.6 Moyens d'obtenir les mots de passe

| Moyen | Description |
|---|---|
| **Keyloggers** | Logiciels installés sur le poste qui **enregistrent les frappes clavier** |
| **Ingénierie sociale** | Exploiter la **naïveté** : se faire passer pour un **administrateur réseau**, ou appeler le **support** pour **réinitialiser** un mot de passe en prétextant l'**urgence** |
| **Espionnage** | **Observer** l'utilisateur (*shoulder surfing*) |

---

## 5. Le mot de passe unique (OTP)

> **OTP (*One Time Password*)** : le mot de passe n'est **utilisable qu'une seule fois**.

| Avantage | Explication |
|---|---|
| **Protection contre l'oubli** | Rien à retenir |
| **Protection contre le rejet** (*replay*) | Un OTP intercepté est **déjà périmé** |
| **Non prédictible** | Si l'**algorithme est gardé secret** |

L'OTP peut reposer sur des **jetons / authentifieurs / tokens**.

### Les trois techniques d'OTP

| Technique | Fonctionnement |
|---|---|
| **Asynchrone** (challenge/réponse) | Le **serveur envoie un challenge** ; l'utilisateur possède une **calculatrice** qui le transforme en mot de passe qu'il saisit ; le **serveur refait l'opération** et **compare** |
| **Synchrone dépendant du temps** | Le mot de passe est **fonction du temps**, généré à **intervalle régulier** ; le « challenge » est la **date et l'heure** |
| **Synchrone indépendant du temps** | Un **compteur interne** de la calculatrice est **incrémenté à chaque utilisation** ; le serveur est **synchronisé** sur ce compteur et **refuse** tout code **antérieur** |

---

## 6. Authentification par objet

### 6.1 Exemples d'OTP / objets

| Moyen | Description |
|---|---|
| **Liste à biffer / TAN** (*Transaction Authentication Number*) | Saisir un OTP **pris dans une liste de codes** fournie (**banque**) |
| **Carte matricielle** (*matrix card*) | Saisir un OTP **lu sur une carte matricielle** fournie |
| **SMS** | L'utilisateur **reçoit l'OTP sur son téléphone** |

### 6.2 Cartes d'identité

| Variante | Technologies | Problèmes |
|---|---|---|
| **Sans mot de passe** | **Code-barres, carte magnétique, puce, clé électronique** | **Copie**, **vol**, **liaison lecteur ↔ serveur**, **rejet** |
| **Avec mot de passe** (**double facteur**) | Carte + code | Liaison lecteur ↔ serveur, rejet ; en cas de **copie ou vol**, le pirate doit « simplement » **résoudre un problème de mot de passe** |

---

## 7. Authentification par caractère : la biométrie

> La **biométrie** mesure les **caractéristiques physiques** d'un individu : **empreintes digitales**, **forme du visage**, **ADN**…

### 7.1 Les cinq critères d'un attribut biométrique

| Critère | Signification |
|---|---|
| **Unicité** | Varie **assez d'une personne à l'autre** pour être **unique** |
| **Robustesse** | **Permanent tout au long de la vie** |
| **Quantifiabilité** | **Mesurable** et quantifiable |
| **Acceptabilité** | **Accepté par la population** (les empreintes sont parfois associées à la criminalité) |
| **Universalité** | **Proportion de la population** qui possède l'attribut (certaines personnes n'ont pas d'empreintes) |

### 7.2 Caractéristiques utilisables

**Iris** · **empreintes digitales ou de la main** · **lèvres** · **cartographie des veines** · **visage** · **oreille** (et échos du conduit auditif) · **spectre d'absorption de la peau** · **voix** · **ADN**.

### 7.3 Marché (graphiques du cours)

- Un graphique montre la **croissance du marché biométrique** de **2009 à 2017** (de ≈ 2,6 à ≈ 10,9 milliards de dollars, croissance annuelle ≈ **20 %**).
- Un diagramme circulaire montre la **répartition par technologie** : les **empreintes digitales** dominent (**≈ 49 %**), devant le **visage** (≈ 15 %), le **middleware** (≈ 12 %) et la **main** (≈ 10 %) ; l'**iris**, la **voix**, la **signature** et la **frappe clavier** sont minoritaires.
- Un troisième graphique montre l'**augmentation des capteurs d'empreintes** (ventes cumulées).

*(Source citée : International Biometry Group, 2010. Chiffres historiques, lus sur les graphiques.)*

> ⚠️ **Limites** *(ajout)* : une empreinte **ne se change pas** si elle est compromise ; risque de **faux positifs / faux négatifs** ; enjeux de **vie privée**.

---

## 8. Authentification par savoir-faire

- Reconnaissance de **ce que l'utilisateur sait faire**.
- **Exemples :** **signature** manuscrite, **geste**.

---

# PARTIE B — CRYPTOGRAPHIE

> *« Outils de cryptographie et d'authentification »* : techniques d'authentification · cryptage symétrique · cryptage asymétrique · intégrité d'un message · PKI.

## 9. Terminologie et classification

| Terme | Définition |
|---|---|
| **Chiffrement** | Transformation, à l'aide d'une **clé de chiffrement**, d'un **message en clair** en un message **incompréhensible** sans la **clé de déchiffrement** (*encryption*) |
| **Chiffre** | Anciennement **code secret** ; par extension, l'**algorithme** utilisé |
| **Cryptogramme** | **Message chiffré** |
| **Décrypter** | **Retrouver le message clair sans posséder la clé** (les anglophones « cassent » les codes) |
| **Cryptographie** | « **Écriture secrète** » ; science visant à **créer des cryptogrammes** (chiffrer) |
| **Cryptanalyse** | Science qui **analyse les cryptogrammes** pour les **décrypter** |
| **Cryptologie** | Science regroupant **cryptographie + cryptanalyse** |

> 💡 **Nuance :** *déchiffrer* = retrouver le clair **avec** la clé ; *décrypter* = le retrouver **sans** la clé.

### Classification

```
                        Cryptologie
                  ┌────────┴────────┐
            Cryptographie       Cryptanalyse
        ┌─────────┼───────────┐
   À clé symétrique   À clé publique   Protocoles
   ┌───┴───┐
 Par bloc  Par flux
```

### Définition de la cryptographie
> **Science mathématique** permettant d'effectuer des opérations sur un **texte intelligible** afin d'assurer **une ou plusieurs propriétés** de la sécurité de l'information : **authentification · confidentialité · intégrité · non-répudiation**.

---

## 10. Crypto-système, cryptanalyse et principes de Kerckhoffs

### 10.1 Définition d'un crypto-système

Un crypto-système est un **5-uplet (P, C, K, E, D)** :

| Symbole | Signification |
|---|---|
| **P** | Ensemble fini de **textes clairs** (*Plain text*) |
| **C** | Ensemble fini de **textes chiffrés** (*Cipher text*) |
| **K** | **Espace de clés** (ensemble fini de clés possibles) |
| **E** | Fonctions de **chiffrement** : pour chaque k ∈ K, une fonction **eₖ : P → C** |
| **D** | Fonctions de **déchiffrement** : pour chaque k, une fonction **dₖ : C → P** |

> ✅ **Condition fondamentale :** **dₖ(eₖ(x)) = x** pour tout x ∈ P (on retrouve le message d'origine).

### 10.2 La cryptanalyse

- **Principes et méthodes** pour trouver un **message clair** à partir d'un message chiffré **sans connaître la clé**.
- Connaissant **C = E(P, K)** mais pas **K**, l'objectif est de trouver **P ou K**.
- Les attaques sont classées selon la **connaissance de l'intrus** :

| Type d'attaque | Ce que connaît l'attaquant |
|---|---|
| **Texte chiffré uniquement** | Seulement **C et E** |
| **Texte clair connu** | **E, C** et **quelques paires** clair/chiffré avec K |
| **Texte clair choisi** | **E, C**, et **P a été choisi par l'intrus** |

### 10.3 Les principes de Kerckhoffs (1883)

Auguste **Kerckhoffs** (*La cryptographie militaire*, 1883) a énoncé les axiomes de la cryptographie :

1. Le système doit être **matériellement, sinon mathématiquement, indéchiffrable** ;
2. La **clé** doit pouvoir être **communiquée et retenue sans notes écrites**, et être **changée** au gré des correspondants ;
3. Il doit être **applicable à la correspondance télégraphique** ;
4. Il doit être **portatif** et son maniement **n'exiger pas plusieurs personnes** ;
5. Il doit être **d'usage facile** : ni tension d'esprit, ni longue série de règles.

> 🔑 **Version moderne (principe de Kerckhoffs)** *(ajout)* : **la sécurité d'un système ne doit reposer que sur le secret de la clé, pas sur le secret de l'algorithme**. C'est exactement ce que demandait l'appel d'offres du DES (voir section 12).

---

## 11. Chiffrement symétrique

### 11.1 Principe
- Appelé aussi **cryptage à clé secrète** : la **même clé**, tenue secrète, sert au **chiffrement et au déchiffrement**.
- Le chiffrement s'effectue en **additionnant (XOR) la clé au message coupé en blocs**, avec **plusieurs permutations**, **itérées** plusieurs fois.
- La clé doit être échangée par un **canal sécurisé** (ex. **IPSec**).
- **Algorithmes :** **DES, Triple-DES, AES, IDEA, Blowfish**.
- **Qualités :** **simples à mettre en œuvre**, exécution **très rapide**.

```
 Émetteur                                                      Récepteur
 Texte clair ──► [Chiffrement] ──► texte chiffré ──► [Déchiffrement] ──► Texte clair
                      ▲            (Internet, vu par               ▲
                      │             le cryptanalyste)              │
                    Clé ───── transmission par canal sécurisé ───── Clé
```

### 11.2 Deux catégories

| | **Chiffrement par flux** (*stream*) | **Chiffrement par bloc** (*block*) |
|---|---|---|
| **Principe** | Traite les éléments d'entrée **de façon continue**, **un élément de sortie à la fois** | Le texte est **divisé en blocs de taille fixe** ; **un bloc traité à la fois** |
| **Clé** | **Aussi longue que le flux** de données | Même fonction et **même clé** pour tous les blocs |
| **Disponibilité des données** | **Pas besoin d'attendre** le bloc entier | Le **bloc doit être entièrement disponible** avant traitement |
| **Usage** | **Communication en temps réel** | Usage général |
| **Implémentation** | En général **matérielle** | En général **logicielle** |

---

## 12. DES (Data Encryption Standard)

### 12.1 Histoire
- Jusqu'aux années **1970**, seuls les **militaires** avaient des algorithmes à clé secrète fiables.
- Face aux besoins civils, le **NBS** (*National Bureau of Standards*) lance un **appel d'offres le 15 mai 1973** pour un système dont :
  - l'algorithme repose sur une **clé relativement petite**, servant au chiffrement **et** au déchiffrement ;
  - il est **facile à implémenter** (logiciel et matériel) et **très rapide** ;
  - le chiffrement a un **haut niveau de sûreté, uniquement lié à la clé** et **non à la confidentialité de l'algorithme**.
- **IBM** propose **Lucifer** (fin 1974) ; avec la **NSA**, cela donne le **DES** (**1975**, publié **1977**), l'algorithme le plus utilisé au monde dans le dernier quart du XXᵉ siècle.

### 12.2 Caractéristiques

| Caractéristique | Valeur |
|---|---|
| **Type** | **Chiffrement par blocs** à clés symétriques |
| **Taille de bloc** | **64 bits** |
| **Taille de clé** | **56 bits effectifs** |
| **Structure** | **Produit de transpositions et de substitutions** |
| **Boîtes** | **P-Box** (permutation) et **S-Box** (substitution) |
| **Implémentation** | Facile **en matériel** |
| **Rondes** | **16 itérations** |

> ⚠️ La **conception des S-Boxes** est restée **secrète** ; ce sont des **tables définissant des permutations**.

### 12.3 Principe : 4 phases

| Phase | Description |
|---|---|
| **1. Préparation** | Texte découpé en **blocs de 64 bits**. **Diversification de la clé K** : on fabrique **16 sous-clés K₁…K₁₆ de 48 bits** |
| **2. Permutation initiale** | Pour chaque bloc x de 64 bits, on calcule une permutation **y = P(x)** ; y = **G₀D₀** (G₀ : 32 bits de **gauche**, D₀ : 32 bits de **droite**) |
| **3. Itération (16 tours)** | De Gᵢ₋₁Dᵢ₋₁ on calcule GᵢDᵢ : **Gᵢ = Dᵢ₋₁** et **Dᵢ = Gᵢ₋₁ ⊕ f(Dᵢ₋₁, Kᵢ)** |
| **4. Permutation finale** | On applique l'**inverse de la permutation initiale** : **Z = P⁻¹(G₁₆D₁₆)** = bloc chiffré de 64 bits |

```
 64-bit Input ─► [ Permutation initiale ] ─► G₀ | D₀
                                              │
                     Clé 56 bits ─► [Génération des sous-clés] ─► K₁ … K₁₆ (48 bits)
                                              │
                         Tour 1 … Tour 16 :  Gᵢ = Dᵢ₋₁ ;  Dᵢ = Gᵢ₋₁ ⊕ f(Dᵢ₋₁, Kᵢ)
                                              │
                      [ Permutation initiale inverse ] ─► 64-bit Output
```

> 🔑 **Particularité (réseau de Feistel)** *(ajout)* : le **déchiffrement** utilise **le même algorithme**, avec les **sous-clés dans l'ordre inverse** (K₁₆ … K₁). Le cours dit : *« pour le déchiffrement, il suffit de faire l'opération inverse »*.

### 12.4 Détail des étapes

**Étape 1 — Permutation initiale :** les bits sont **réordonnés** selon une table (ex. **le bit n° 1 devient le bit 58**) ; simple en matériel. La permutation finale est l'**inverse**.

**Étape 2 — Sous-clés (*round keys*)** générées à partir de la clé de 56 bits :
1. **Division** de la clé en **deux segments** ;
2. **Rotation** de chaque segment de **1 ou 2 bits à gauche** ;
3. **Sélection de 24 bits** dans chaque segment → **48 bits** par sous-clé.

**Étape 3 — Un tour (*one DES round*)** : Lᵢ₊₁ = Rᵢ ; Rᵢ₊₁ = Lᵢ ⊕ f(Rᵢ, Kᵢ). Le **schéma est symétrique** pour le chiffrement et le déchiffrement.

### 12.5 La fonction f(R, K) en trois temps

| # | Étape | Description |
|---|---|---|
| ① | **Expansion (table E)** | Les **32 bits** de R sont étendus à **48 bits** : le bloc est divisé en **segments de 4 bits**, chacun **élargi avec les bits de ses voisins** pour atteindre **6 bits** |
| ② | **XOR avec la sous-clé Kᵢ** | Le résultat de 48 bits est **additionné (XOR)** à la sous-clé |
| ③ | **Substitution (S-Boxes)** | Les 48 bits sont **découpés en 8 blocs Bᵢ de 6 bits** ; **8 S-Boxes** (une par Bᵢ) transforment chaque bloc de **6 bits en 4 bits** → **32 bits** |
| ④ | **Permutation P** | Les 32 bits subissent une **permutation** (ex. le **bit 1 va en position 16**) ; le résultat est la sortie de **f** |

### 12.6 Fonctionnement d'une S-Box

- Chaque S-Box a **4 lignes** et **16 colonnes**.
- Pour **Bᵢ = b₁b₂b₃b₄b₅b₆** :
  - **ligne r = b₁b₆** (les bits **extrêmes**),
  - **colonne c = b₂b₃b₄b₅** (les 4 bits **du milieu**).
- La valeur trouvée (codée sur **4 bits**) est la sortie **Sᵢ(Bᵢ)**.

**Exemple du cours :** bloc **`001001`**
- **r** = b₁b₆ = **01** = **1** ;
- **c** = b₂b₃b₄b₅ = **0100** = **4** ;
- La S-Box donne la valeur **14** = **`1110`** → **6 bits → 4 bits**.

### 12.7 Limite du DES
- Les progrès de la **cryptanalyse** et de l'**électronique** ont rendu la **clé de 56 bits** trop courte : elle se retrouve en **quelques heures** par **force brute** à partir d'un texte clair connu.

---

## 13. Triple-DES

- **3-DES** lancé comme **nouveau standard en 1999**.
- Utilise **2 ou 3 clés**.
- **Niveau de sécurité satisfaisant** ; permet de **continuer à utiliser les S-Box/P-Box** (matériel et logiciel) **en attendant la migration vers l'AES**.

| Variante | Description |
|---|---|
| **DES-EEE3** | **3 chiffrements** DES avec **3 clés différentes** |
| **DES-EDE3** | **1 clé différente pour chacune** des 3 opérations : **chiffrement – déchiffrement – chiffrement** |
| **DES-EEE2 / DES-EDE2** | **2 clés** : la **même** pour la 1ʳᵉ et la 3ᵉ opération, une **autre** pour la 2ᵉ (déchiffrement) |

> 💡 *(Ajout)* **E = Encrypt, D = Decrypt**. Le motif EDE permet la **compatibilité** avec le DES simple (si les trois clés sont identiques).

---

## 14. AES (Advanced Encryption Standard)

### 14.1 Historique
- **1997** : le **NIST** lance un appel à projets pour élaborer l'**AES**, destiné à **remplacer le DES**.

### 14.2 Principe
- **Succession de fonctions** appliquées en **10, 12 ou 14 itérations** (tours).
- Les **données et la clé** sont codées sur **128 bits** (pour l'AES-128).

> 💡 *(Ajout)* **AES-128 → 10 tours ; AES-192 → 12 ; AES-256 → 14.** Blocs de **128 bits** dans tous les cas.

### 14.3 Étapes d'un tour (chiffrement)

```
 PLAINTEXT ─► AddRoundKey ─► [ SubBytes ─► ShiftRows ─► MixColumns ─► AddRoundKey ] × (Nr−1)
                              ─► SubBytes ─► ShiftRows ─► AddRoundKey ─► CIPHERTEXT
                                 (dernier tour : sans MixColumns)
```

| Fonction | Rôle |
|---|---|
| **AddRoundKey** | **Addition binaire (XOR)** des octets de la donnée avec les octets de la **clé de tour** |
| **SubBytes** | **Substitution** des octets selon une **S-Box prédéfinie** |
| **ShiftRows** | **Décalage circulaire** des lignes : la ligne i est décalée de **i−1 octets** |
| **MixColumns** | Chaque octet est un **polynôme** ; on **multiplie** les colonnes par **03 (x+1), 02 (x) ou 01 (1)** |

Le **déchiffrement** applique les fonctions **inverses** : **InvShiftRows, InvSubBytes, InvMixColumns**, dans l'ordre inverse.

### 14.4 Génération (expansion) de la clé
- Les **quatre vecteurs** de la matrice clé sont des **Words de 32 bits**.
- On calcule le **5ᵉ vecteur (Wᵢ)** : on prend **Wᵢ₋₁**, on applique **RotWord** (**décalage des 4 octets** du vecteur vers le haut), puis **SubBytes**.

### 14.5 Comparaison DES / 3DES / AES

| | **DES** | **3DES** | **AES** |
|---|---|---|---|
| **Année** | 1975-77 | 1999 | 2001 (concours lancé en 1997) *(ajout)* |
| **Bloc** | 64 bits | 64 bits | **128 bits** |
| **Clé** | **56 bits** | 112 ou 168 bits | **128 / 192 / 256 bits** |
| **Tours** | 16 | 3 × 16 | **10 / 12 / 14** |
| **Statut** | **Obsolète** (cassable) | Transitoire, **déconseillé aujourd'hui** | **Standard actuel** |

---

## 15. Chiffrement asymétrique

### 15.1 Définition
- Aussi appelé **cryptographie à clé publique / à paire de clés**.
- Une **révolution** dans l'histoire de la cryptographie.

### 15.2 Les deux clés

| Clé | Qui la connaît ? | Usage |
|---|---|---|
| **Clé publique (Kpu)** | **Tout le monde** | **Chiffrer** des messages ou **vérifier une signature** |
| **Clé privée (Kpr)** | **Son propriétaire seul** | **Déchiffrer** des messages ou **créer une signature** |

- Si on **chiffre avec l'une**, le **déchiffrement se fait uniquement avec l'autre**.
- **Impossible de retrouver la clé privée à partir de la clé publique.**
- **Clés de grande taille** (ex. RSA : 1024, 2048…).

### 15.3 Fonction à sens unique avec trappe
- **Kpr** : clé privée ; **Kpu** : clé publique.
- **Y = f_Kpr(X)** : **facile** à calculer si on connaît Kpr et X.
- **X = f_Kpu⁻¹(Y)** : **facile** si on connaît Kpu et Y, mais **impossible** si Y est connu et Kpu **non** connue *(selon le texte du cours)*.

### 15.4 Usages

| Usage | Service assuré |
|---|---|
| **Chiffrement / déchiffrement** | **Confidentialité** |
| **Signature numérique** | **Authentification** et **non-répudiation** |
| **Distribution de clés** | Se mettre d'accord sur une **clé de session** |

### 15.5 Caractéristiques
- **Deux clés** : publique / privée ; **traitement relativement lent** ; exemple : **RSA** (Rivest, Shamir, Adleman) ; usage : **authentification, signature, échange de clés**.

---

## 16. Scénarios d'utilisation, chiffrement mixte et clés de session

### 16.1 Scénario 1 — Confidentialité

```
 Émetteur                                                Récepteur
 Texte clair ─► [Chiffrement avec la CLÉ PUBLIQUE du récepteur] ─► Internet ─► [Déchiffrement avec la CLÉ PRIVÉE du récepteur] ─► Texte clair
```
→ **Seul le récepteur** (détenteur de la clé privée) peut lire.

### 16.2 Scénario 2 — Authenticité de l'émetteur et non-répudiation d'envoi

```
 Émetteur                                                Récepteur
 Texte clair ─► [Chiffrement avec la CLÉ PRIVÉE de l'émetteur] ─► Internet ─► [Déchiffrement avec la CLÉ PUBLIQUE de l'émetteur] ─► Texte clair
```
→ **Tout le monde** peut vérifier que **seul l'émetteur** a pu produire ce message : **authenticité** et **non-répudiation**. *(Mais pas de confidentialité : la clé publique est connue de tous.)*

### 16.3 Comparaison des deux scénarios

| | **Confidentialité** | **Authenticité / non-répudiation** |
|---|---|---|
| **Clé de chiffrement** | **Publique du récepteur** | **Privée de l'émetteur** |
| **Clé de déchiffrement** | **Privée du récepteur** | **Publique de l'émetteur** |
| **Qui peut lire ?** | Le récepteur seul | **Tout le monde** |
| **Service** | **Confidentialité** | **Authentification + non-répudiation** |

### 16.4 Chiffrement mixte (hybride)

Pour combiner **rapidité du symétrique** et **commodité de l'asymétrique** :

1. On génère une **clé aléatoire de chiffrement symétrique** (**clé de session**).
2. On **chiffre le message** (long) avec cette **clé symétrique** → **texte chiffré**.
3. On **chiffre la clé de session** avec la **clé publique du destinataire** → **clé de session chiffrée**.
4. On envoie **les deux**.
5. Le destinataire **déchiffre la clé de session** avec sa **clé privée**, puis **le message** avec la clé de session.

```
 Texte clair ──[clé de session aléatoire (symétrique)]──► Texte chiffré ──────────────┐
 Clé de session ──[clé publique du destinataire]──► Clé de session chiffrée ──────────┤──► envoi
                                                                                       ▼
 Destinataire : clé privée → retrouve la clé de session → déchiffre le texte
```

### 16.5 Distribution des clés de session (schéma PGP-like du cours)

Étapes illustrées (de A vers B) : **rédaction → compression → chiffrement du message** avec une **clé de session** → **chiffrement de la clé de session** avec la **clé publique de B** → **message final** (message chiffré + clé de session chiffrée) → B **déchiffre la clé de session** avec sa **clé privée** → **déchiffre le message** → **lecture**.

> 🔑 *« La paire de clés de B sert uniquement à chiffrer la clé de session. »*

---

## 17. RSA

### 17.1 Présentation
- Développé par **Rivest, Shamir & Adleman** au **MIT en 1977**, publié en **1978**.
- Algorithme **asymétrique le plus connu et le plus utilisé** : **chiffrement** et **signature électronique**.
- Utilise des **entiers très grands** (**1024+ bits**).
- **Sécurité** : repose sur la **difficulté de factoriser** de grands entiers.
- **Diffie-Hellman** : algorithme pour **l'échange et la distribution de clés symétriques**.

### 17.2 Algorithme (génération des clés)

| # | Étape |
|---|---|
| 1 | Choisir **deux nombres premiers** **p** et **q** |
| 2 | Calculer **n = p × q** |
| 3 | Calculer **φ(n) = (p−1)(q−1)** |
| 4 | Choisir **e** tel que **pgcd(φ(n), e) = 1** et **1 < e < φ(n)** (en général **e petit**) |
| 5 | Calculer **d = e⁻¹ mod φ(n)** , c.-à-d. **d·e ≡ 1 (mod φ(n))** |
| 6 | **Clé publique : Kpu = {e, n}** |
| 7 | **Clé privée : Kpr = {d, n}** |

### 17.3 Chiffrement et déchiffrement

| Opération | Formule | Qui ? |
|---|---|---|
| **Chiffrer** un message **M < n** | **C = Mᵉ mod n** | L'émetteur, avec la **clé publique** du récepteur |
| **Déchiffrer** C | **M = Cᵈ mod n** | Le récepteur, avec sa **clé privée** |

### 17.4 Exemple du cours (vérifié)

| Donnée | Valeur |
|---|---|
| **p, q** | 17, 11 |
| **n = p × q** | **187** |
| **φ(n) = 16 × 10** | **160** |
| **e** | **7** |
| **d** (7·d ≡ 1 mod 160) | **23** *(7 × 23 = 161 = 160 + 1 ✓)* |
| **Clé publique** | **(7, 187)** |
| **Clé privée** | **(23, 187)** |

**Chiffrement du message M = 88 :** C = 88⁷ mod 187 = **11**
**Déchiffrement :** M = 11²³ mod 187 = **88** ✅

```
 plaintext 88 ──[ 88^7 mod 187 ]──► ciphertext 11 ──[ 11^23 mod 187 ]──► plaintext 88
                 KU = (7, 187)                         KR = (23, 187)
```

### 17.5 Pourquoi ça marche : fonction et théorème d'Euler, preuve

- **Fonction d'Euler φ(n)** : nombre d'entiers **premiers avec n**.
  - n **premier** ⇒ **φ(n) = n − 1** ;
  - **n = p·q** (p, q premiers) ⇒ **φ(n) = (p−1)(q−1)**.
- **Théorème d'Euler :** si **a et n sont premiers entre eux**, alors **a^φ(n) ≡ 1 (mod n)**.
- **Preuve :** D(E(M)) = (Mᵉ mod n)ᵈ mod n = Mᵉᵈ mod n. Comme **e·d ≡ 1 (mod φ(n))**, on a **e·d = z·φ(n) + 1**. Donc Mᵉᵈ = (M^φ(n))ᶻ · M ≡ 1ᶻ · M = **M (mod n)** *(pour des blocs de taille < n)*.

### 17.6 Sécurité et difficultés d'implémentation

- Pour **casser RSA**, il « suffit » de **factoriser n** : on obtient **p et q**, donc **φ(n)**, donc **d** à partir de e. Mais **factoriser de très grands nombres** est **très difficile**.
- **Deux difficultés pratiques :**
  1. **Générer de grands nombres premiers** (p et q) ;
  2. **Élever un nombre à de très grandes puissances** (modulo n).
- **Standard RSA : PKCS #1.**

---

## 18. Avantages et inconvénients de l'asymétrique

| Avantages | Inconvénients |
|---|---|
| **Pas besoin de canal sûr** pour transmettre la clé | **Généralement ≈ 10 fois plus lent** que le symétrique *(selon le cours)* |
| **Plusieurs fonctions de sécurité** : confidentialité, **authentification**, **non-répudiation** | **Difficile sur équipements de faible puissance** (cartes bancaires, stations mobiles) : **clés longues**, **opérations modulo n** complexes |

> ✅ **Solution :** utiliser le **cryptage asymétrique pour échanger les clés de session** d'un algorithme **symétrique** → **chiffrement mixte** (section 16.4).

### Symétrique vs asymétrique

| Critère | **Symétrique** | **Asymétrique** |
|---|---|---|
| **Clés** | **Une seule** (secrète) | **Deux** (publique / privée) |
| **Échange de clé** | Nécessite un **canal sûr** | **Pas nécessaire** |
| **Vitesse** | **Rapide** | **Lente** |
| **Services** | Confidentialité | Confidentialité, **authentification**, **non-répudiation** |
| **Exemples** | DES, 3DES, **AES**, IDEA, Blowfish | **RSA**, Diffie-Hellman |
| **Usage type** | Chiffrer les **données** | Échanger les **clés**, **signer** |

---

# PARTIE C — INTÉGRITÉ, SIGNATURE, CERTIFICATS

## 19. Fonctions de hachage

### 19.1 Définition
> Une **fonction de hachage** extrait une **empreinte** qui **caractérise les données**. L'empreinte a **toujours une taille fixe**, **indépendamment de la taille des données**.

| Élément | Description |
|---|---|
| **Entrée** | Message **M** de **contenu et taille arbitraires** |
| **Sortie** | **h = H(M)** de **taille fixe** (16 ou 20 octets selon l'algorithme) |
| **Noms** | **Condensat**, **empreinte**, **fingerprint**, **message digest** |
| **Calcul** | **Facile et rapide** (plus rapide que le chiffrement symétrique) |
| **Usages** | **Checksums**, **intégrité** |
| **Exemples** | **MD5**, **SHA**… (digest de **128, 160… bits**) |

### 19.2 Propriétés

| Propriété | Définition |
|---|---|
| **Irréversible** (sens unique) | Connaissant **y = h(x)**, il est **pratiquement infaisable** de retrouver **x** (complexité ≈ **2ⁿ**, n = nombre de bits du digest) |
| **Résistance faible à la collision** | Connaissant x et y = h(x), il est **infaisable de trouver x' ≠ x tel que h(x') = h(x)** |
| **Résistance forte à la collision** | Il est **infaisable de trouver deux valeurs distinctes x et x'** telles que **h(x') = h(x)** |

### 19.3 Vérification de l'intégrité

```
 Émetteur : texte ──[Hachage]──► empreinte ──┐
                                             ├──► envoi (texte + empreinte)
 Récepteur : texte reçu ──[Hachage]──► empreinte RECALCULÉE
                                        =? empreinte REÇUE
   • égales      → le texte reçu est INTÈGRE
   • différentes → le texte reçu est ALTÉRÉ
```

### 19.4 Exemples

| Algorithme | Particularités |
|---|---|
| **MD5** (*Message Digest 5*) | Développé en **1991** ; empreinte de **128 bits** ; traite l'entrée par **blocs de 512 bits** |
| **SHA-1** (*Secure Hash Algorithm*) | Empreinte de **160 bits** ; **plus fort que MD5** |

> ⚠️ *(Ajout)* **Aujourd'hui, MD5 et SHA-1 sont considérés comme cassés** (collisions démontrées) : on utilise **SHA-256 / SHA-3**. Pour **stocker des mots de passe**, on utilise des fonctions **lentes et salées** (bcrypt, Argon2, PBKDF2).

> 🔑 **Attention :** un **hachage seul** garantit l'**intégrité** contre les erreurs, mais **pas l'authenticité** : un attaquant peut modifier le message **et** recalculer l'empreinte. D'où la **signature**.

---

## 20. Signature numérique

### 20.1 Principe
- Le **hash** du message est **chiffré avec la clé privée de l'émetteur** ; la **clé publique** sert à **vérifier** la signature.

**Notations :** M (message), H (hachage), (Kpr, Kpu) (clés de l'émetteur), E/D (chiffrement avec Kpr / déchiffrement avec Kpu).
Le récepteur reçoit **(M, E_Kpr(H(M)))** et vérifie : **H(M) = D_Kpu(E_Kpr(H(M)))**.

### 20.2 Génération de la signature

```
 Texte clair ─► [Fonction de hachage] ─► Empreinte ─► [Chiffrement asymétrique avec la clé PRIVÉE du signataire] ─► SIGNATURE NUMÉRIQUE
```

### 20.3 Vérification de la signature

```
 Texte clair reçu ─► [Hachage] ─► Empreinte RECALCULÉE ─────────────┐
                                                                    =?
 Signature reçue ─► [Déchiffrement avec la clé PUBLIQUE de l'émetteur] ─► Empreinte REÇUE ┘

   • égales      → signature CORRECTE
   • différentes → signature INCORRECTE
```

**Transmission :** le message, la **signature** (hash chiffré) et les **informations sur les algorithmes** utilisés. Si les résultats sont égaux : **émetteur authentifié, message intègre, et l'émetteur ne peut pas répudier l'envoi**.

### 20.4 Services assurés

| Service | Assuré par la signature |
|---|---|
| **Intégrité** du message | ✅ |
| **Authentification** | ✅ |
| **Non-répudiation** | ✅ |
| Génération d'une **clé de chiffrement symétrique** pour la **confidentialité** | ✅ (dans le cadre d'un échange de clés) |

### 20.5 Signature numérique vs signature manuscrite

| | **Manuscrite** | **Numérique** |
|---|---|---|
| **Authentification du signataire** | ✅ | ✅ |
| **Non-répudiation** | ✅ | ✅ |
| **Intégrité des données** | ❌ | ✅ **(seule la signature numérique l'assure)** |

---

## 21. Certificats numériques et standards PKCS

### 21.1 Certificat
- Un **certificat** est un **message signé avec la clé privée d'une entité** : l'**autorité de certification (CA)**.
- Il peut être **vérifié avec la clé publique de la CA**.
- Les CA peuvent être organisées **en arbre** : il suffit de **faire confiance à une seule** (la racine) pour vérifier **n'importe quel certificat** de la hiérarchie.
- **Définition :** *structure de données liant différents éléments au moyen de la signature d'une autorité de confiance.*

### 21.2 Message sécurisé complet : signature + enveloppe numérique (schéma du cours)

À l'envoi, l'émetteur :
1. calcule le **condensat** du texte (algorithme de hachage) ;
2. le **signe** avec sa **clé privée** → **signature numérique (sceau)** ;
3. génère une **clé DES aléatoire pour chaque message** (clé de session) et **chiffre le message** ;
4. **chiffre cette clé de session** avec la **clé publique du destinataire** → **enveloppe numérique** (avec un **padding OAEP**) ;
5. joint son **certificat** ;
6. envoie le **message**.

### 21.3 Les standards PKCS (*Public Key Cryptography Standards*, RSA Labs)

Standards de la société RSA : **format de stockage des clés privées**, **format et extensions des certificats**, **requête d'obtention d'un certificat**, etc.

| Standard | Objet |
|---|---|
| **PKCS#1** | **RSA Encryption Standard** |
| **PKCS#3** | **Diffie-Hellman Key Agreement Standard** |
| **PKCS#5** | **Password-Based Encryption Standard** |
| **PKCS#6** | Extended-Certificate Syntax Standard |
| **PKCS#7** | **Cryptographic Message Syntax Standard** |
| **PKCS#8** | **Private-Key Information Syntax Standard** |
| **PKCS#9** | Selected Attribute Types |
| **PKCS#10** | **Certification Request Syntax Standard** |
| **PKCS#11** | **Cryptographic Token Interface Standard** |
| **PKCS#12** | **Personal Information Exchange Syntax Standard** |

---

## 22. Gestion des clés publiques et PKI

### 22.1 Problèmes de la cryptographie asymétrique

| Problème | Détail |
|---|---|
| **Distribution des clés** | **Obtenir** la clé publique d'une autre entité ; **distribuer** sa propre clé publique |
| **Révocation** | **Révoquer** une clé publiée ; **déterminer si une clé publiée est valide** |

### 22.2 L'attaque « Mallet » (homme du milieu sur les clés publiques)

1. **Alice** envoie sa clé publique à **Bob**.
2. **Mallet** **intercepte la clé** et la **remplace par la sienne**.
3. Mallet peut alors **déchiffrer tout le trafic** et **générer des signatures falsifiées**.

### 22.3 La solution : l'autorité de certification (CA)

1. Alice **envoie sa clé publique à la CA** ;
2. Alice **prouve qu'elle détient la clé privée** correspondante ;
3. La CA **vérifie l'identité d'Alice** ;
4. La CA **signe l'ensemble (clé publique + identité) avec sa clé privée** : le document signé est un **certificat**.

→ Quand **Bob** reçoit le certificat d'Alice, il est **sûr que la clé certifiée est celle d'Alice**.

### 22.4 L'infrastructure à clé publique (PKI) — standard X.509

```
        Publie / publie la révocation
   CA ─────────────────────────────────► Répertoire (annuaire)
   ▲
   │ Demande / Émet
   │
 Utilisateur final        (variante avec RA : Utilisateurs ─► RA ─► CA)
```

**Limites du modèle à CA seule :** la CA doit **vérifier les détails de chaque utilisateur** ; **risques pour la sécurité de la CA**.

**Autorité d'enregistrement (RA) :**
- **Intermédiaire** entre le **détenteur de clé** et la **CA** ;
- **Vérifie les requêtes** des utilisateurs et les **transmet à la CA** ;
- Le **niveau de vérification** dépend de la **politique de certification (CPS)**.

### 22.5 Composants d'une PKI

| Principaux | Complémentaires |
|---|---|
| **Autorité de certification (CA)** | Base de données |
| **Autorité d'enregistrement (RA)** | **Serveur d'horodatage** |
| **Annuaire de publication** | **Serveurs HTTP, SMTP, POP** |
| **Administrateurs** | |

### 22.6 Modèle de confiance dans X.509
- **Infrastructure hiérarchique** (arbre de CA).
- **Co-certification** : possibilité de **certification entre deux CA appartenant à des arbres différents** (ex. **CA1 co-certifie CA2**).

---

## 23. Certificats X.509

### 23.1 Généralités
- **Principal format** de certificats ; norme **ITU-T X.509** = **ISO/IEC 9594-8**.
- **Versions :** **1988 : v1** · **1993 : v2** (= v1 + 2 nouveaux champs) · **1996 : v3** (= v2 + **extensions**).

### 23.2 Structure d'un certificat X.509

| Champ | Version | Contenu |
|---|---|---|
| **Version** | V1 | Numéro de version |
| **Serial Number** | V1 | Numéro de série (unique pour la CA) |
| **Signature Algorithm Identifier** | V1 | Algorithme utilisé pour **signer** (par l'émetteur) |
| **Issuer** | V1 | **Nom X.500 de la CA** émettrice |
| **Validity** | V1 | **Dates de début et fin** de validité |
| **Subject** | V1 | **Nom X.500 du détenteur** |
| **SubjectPublicKeyInformation** | V1 | **Algorithme** et **clé publique** du détenteur |
| **IssuerUniqueIdentifier** | V2 | Identifie **de façon unique la clé utilisée par la CA** pour signer (si la CA a changé de clé) |
| **SubjectUniqueIdentifier** | V2 | **Différencie plusieurs clés publiques** du même détenteur émises par la même CA |
| **Extensions** | V3 | Informations supplémentaires |
| **Signature digitale de la CA** | — | Signature **de tous les champs précédents** avec la **clé privée de la CA** |

### 23.3 Les extensions (v3)

**Pourquoi ?** Le concept d'origine **lie l'identité à une clé publique** ; de nouvelles situations demandent **d'autres informations**. Les extensions sont des **blocs de données** qui permettent d'**ajouter des champs sans modifier la définition ASN.1** du certificat. Chaque extension a : **identificateur · flag critique (1 ou 0) · valeur**.

> 🔑 **Flag critique :** si une extension est **critique** et que l'application **ne la comprend pas**, elle doit **rejeter** le certificat.

**4 catégories :**

| Catégorie | Extensions |
|---|---|
| **① Information sur la clé et la politique de sécurité** | **Key Usage** · **Extended Key Usage** · **Private Key Usage Period** · **Certificate Policies** |
| **② Information sur le détenteur et l'émetteur** | **Alternative Name** (General Name) · **Subject Directory Attributes** |
| **③ Contraintes sur le chemin de certification** | **Basic Constraints** · **Name Constraints** · **Policy Constraints** |
| **④ Révocation** | **CRL Distribution Points** · **Freshest CRL** |

**① Information sur la clé et la politique**

| Extension | Rôle |
|---|---|
| **Key Usage** | Utilisation de la clé certifiée : **digitalSignature, nonRepudiation, keyEncipherment, keyAgreement, keyCertSign / cRLSign** |
| **Extended Key Usage** | Autres usages : **ServerAuthentication, clientAuthentication, codeSigning, emailProtection, timeStamping** |
| **Private Key Usage Period** | **Dates de début et fin** de **validité de la clé privée**. Ex. : *une signature peut être valide 10-20 ans, mais la clé privée ne doit être utilisée que 1 ou 2 ans* |
| **Certificate Policies** | Politique de la CA sous laquelle le certificat a été émis. X.509 **délègue à la politique de la CA** la **sémantique de confiance**. Les politiques **protègent la CA de toute responsabilité** (exemple de clause de **non-garantie** de Verisign cité par le cours) |

**② Information sur le détenteur et l'émetteur**

| Extension | Rôle |
|---|---|
| **Alternative Name** (Subject / Issuer) | Autres formes de nom : **e-mail (rfc822)**, **nom DNS**, **URL (URI)**, **adresse IP**, adresse X.400, nom EDI, **OID**, etc. |
| **Subject Directory Attributes** | **Attributs** du sujet : **rôle**, **appartenance à un groupe**, **autorisation**, **numéro de téléphone**… |

**③ Contraintes sur le chemin de certification**

| Extension | Rôle |
|---|---|
| **Basic Constraints** | Précise si le certificat émis est **un certificat de CA** ou non ; si oui, définit une **distance de certification** (profondeur maximale) |
| **Name Constraints** | Utilisé dans les certificats de CA : **espace de noms** dans lequel **tous les sujets suivants** du chemin doivent se trouver |
| **Policy Constraints** | **Identification explicite de la politique** de sécurité |

**④ Révocation**

| Extension | Rôle |
|---|---|
| **CRL Distribution Points** | Identifie les **points de distribution de la CRL** (*Certificate Revocation List*) |
| **Freshest CRL** | Identifie la **CRL contenant les informations les plus récentes** |

---

## 24. Performances : quelques chiffres

Benchmarks du cours (**Pentium II à 233 MHz, API BSAFE 4.0 de RSA**) :

| Opération | Débit |
|---|---|
| **DES : chiffrement** | **3 241 Ko/s** |
| **DES : déchiffrement** | **3 333 Ko/s** |
| **MD5 : création d'un condensat** | **36 250 Ko/s** |
| **SHA : création d'un condensat** | **36 250 Ko/s** |
| **RSA : chiffrement** | **4,23 Ko/s** |
| **RSA : déchiffrement** | **2,87 Ko/s** |

**Lecture :**
- **Hachage ≈ 11 × plus rapide que le DES** ;
- **DES ≈ 700 à 1 100 × plus rapide que RSA** ;
- → d'où le **chiffrement mixte** : RSA pour la **clé de session**, DES/AES pour les **données**.

> ⚠️ *Cohérence :* le cours dit « ≈ 10 fois plus lent » (section 18) alors que ce benchmark montre un facteur **bien supérieur (≈ 1000)** ; le « ×10 » est un ordre de grandeur **optimiste**, le facteur réel dépend de l'implémentation et de la taille de clé.

---

## 25. Quel service de sécurité par quel mécanisme ? (ajout)

> ⚠️ **Section ajoutée** pour relier les trois parties du chapitre.

| Service | Mécanisme(s) | Détail |
|---|---|---|
| **Authentification** (identité d'un utilisateur) | **Mot de passe, OTP, jeton, biométrie, savoir-faire** | Facteurs : secret / objet / caractère / savoir-faire |
| **Authentification** (identité d'une machine/entité) | **Signature numérique + certificat X.509** | Prouver que la clé publique appartient bien à l'entité |
| **Confidentialité** | **Chiffrement symétrique** (AES) **+ asymétrique** (RSA pour la clé de session) | Chiffrement mixte |
| **Intégrité** | **Fonction de hachage** (SHA-256…), **signature numérique** | Hash seul : erreurs ; signature : aussi les attaques |
| **Non-répudiation** | **Signature numérique** (clé privée de l'émetteur) | Preuve vérifiable par tous |
| **Gestion de la confiance** | **PKI : CA, RA, certificats, CRL** | Lie identité et clé publique |

```
 Message ─► [Hachage] ─► [Signature (clé privée)]   → intégrité + authentification + non-répudiation
         └► [Chiffrement symétrique (clé de session)] ─► clé de session ─► [RSA (clé publique du destinataire)]   → confidentialité
         + Certificat X.509 de l'émetteur (vérifié via la PKI)
```

---

## 26. Remarques sur le support de cours

1. **Plan vs contenu** : le plan annonce **disponibilité** et **services vis-à-vis des attaques**, **non traités** dans ce PDF (qui s'arrête aux extensions X.509 et à la révocation).
2. **Terme « crypter / décrypter »** : usage courant, mais **déchiffrer** (avec la clé) ≠ **décrypter** (sans la clé).
3. **Formule du DES** : le support écrit « *Dᵢ₋₁ = Gᵢ₋₁ XOR f(Dᵢ₋₁, Kᵢ)* » ; il faut lire **Dᵢ = Gᵢ₋₁ ⊕ f(Dᵢ₋₁, Kᵢ)**.
4. **Clé DES** : on parle de **56 bits** ; en réalité, la clé fait **64 bits dont 8 bits de parité**, soit **56 effectifs**.
5. **Principes de Kerckhoffs** : la liste classique en compte **6** ; le support en détaille **5** *(le principe moderne essentiel est « seule la clé est secrète »)*.
6. **« Ajouter un caractère double le temps de craquage »** : c'est **imprécis** ; ajouter un caractère **multiplie** le temps par la **taille de l'alphabet** (≈ 62 pour l'alphanumérique), comme le montre l'exemple 8 → 10 caractères.
7. **RSA « entiers premiers entre eux »** : il faut **p et q premiers** (distincts). Les **clés de 1024 bits** citées sont **aujourd'hui insuffisantes** : minimum **2048 bits**.
8. **« RSA 10 fois plus lent »** : voir [section 24](#24-performances--quelques-chiffres) (≈ 1000 ×).
9. **Chiffres obsolètes** : DES, MD5, SHA-1, benchmarks Pentium II (≈ 1998) ; à citer comme **repères historiques**.
10. **Typographie** : « Tripe-DES », « Diffie-Helman », « Cypher », « Varisign » : sans incidence sur le fond. La date « 15/10/2025 » sur certaines diapositives est la date de **génération du support**.
11. **Aucun exemple de calcul DES/AES complet** : seuls l'exemple de S-Box et l'exemple RSA (p=17, q=11) sont chiffrés ; ils sont **vérifiés** dans ce résumé.

---

## 27. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Identification** | Annoncer qui on est (**identifiant unique**) |
| **Authentification** | **Prouver** son identité (**challenge**) |
| **4 moyens** | **Secret · Objet · Caractère · Savoir-faire** |
| **Authentification forte** | **Combiner plusieurs** facteurs |
| **Mot de passe** | Risques : **divulgation** et **faiblesse** ; **un mot de passe par usage** ; long + alphabet large |
| **Attaques sur mdp** | **Force brute · Dictionnaire · Hybride · Rejet (replay)** ; **keyloggers · ingénierie sociale · espionnage** |
| **Verrouillage de compte** | Protège de la force brute, mais permet un **déni de service** |
| **OTP** | **Valable une seule fois** : asynchrone / synchrone temps / synchrone compteur |
| **Exemples d'OTP** | **TAN · carte matricielle · SMS** |
| **Biométrie (5 critères)** | **Unicité · robustesse · quantifiabilité · acceptabilité · universalité** |
| **Cryptologie** | **Cryptographie + cryptanalyse** |
| **Crypto-système** | **(P, C, K, E, D)** avec **dₖ(eₖ(x)) = x** |
| **Attaques cryptanalytiques** | **Texte chiffré seul · clair connu · clair choisi** |
| **Kerckhoffs** | Seule la **clé** est secrète |
| **Symétrique** | **1 clé**, rapide, **canal sûr** pour l'échange : DES, 3DES, **AES**, IDEA, Blowfish |
| **Flux vs bloc** | Flux : **continu, temps réel, matériel** · Bloc : **blocs fixes, logiciel** |
| **DES** | **Bloc 64 bits, clé 56 bits, 16 tours**, Feistel, S-Box/P-Box ; **cassé** |
| **3DES** | **2 ou 3 clés** : EEE3, EDE3, EEE2, EDE2 |
| **AES** | **Bloc 128 bits, 10/12/14 tours** : AddRoundKey, SubBytes, ShiftRows, MixColumns |
| **Asymétrique** | **Clé publique / privée**, **lent**, sans canal sûr : **RSA**, Diffie-Hellman |
| **Confidentialité (asym.)** | Chiffrer avec la **publique du récepteur** |
| **Signature (asym.)** | Chiffrer avec la **privée de l'émetteur** |
| **Chiffrement mixte** | **Clé de session symétrique** chiffrée avec la **publique du destinataire** |
| **RSA (clés)** | **n = p·q ; φ = (p−1)(q−1) ; e premier avec φ ; d = e⁻¹ mod φ** ; **Kpu = (e, n), Kpr = (d, n)** |
| **RSA (opérations)** | **C = Mᵉ mod n ; M = Cᵈ mod n** |
| **Exemple RSA** | p=17, q=11, n=187, φ=160, e=7, d=23 ; **88 → 11 → 88** |
| **Sécurité RSA** | **Difficulté de factoriser n** |
| **Hachage** | **Empreinte de taille fixe**, **irréversible**, **résistante aux collisions** ; MD5 (128 bits), SHA-1 (160 bits) |
| **Intégrité** | **Comparer empreinte reçue et recalculée** |
| **Signature numérique** | **Hash chiffré avec la clé privée** → **intégrité + authentification + non-répudiation** |
| **Signature manuscrite vs numérique** | Seule la numérique assure l'**intégrité** |
| **Certificat** | **Clé publique + identité signées par une CA** |
| **Attaque Mallet** | Remplacer la clé publique en transit → **la CA l'évite** |
| **PKI** | **CA + RA + annuaire + administrateurs** ; X.509 ; **CRL** pour la révocation |
| **X.509** | **v1 (1988), v2 (1993), v3 (1996, extensions)** ; champs : version, série, algorithme, émetteur, validité, sujet, clé publique, extensions, signature |
| **Extensions (4 familles)** | **Clé/politique · sujet/émetteur · chemin de certification · révocation** |
| **Key Usage** | digitalSignature, nonRepudiation, keyEncipherment, keyAgreement, keyCertSign, cRLSign |
| **PKCS** | Standards RSA : **#1 RSA · #3 DH · #5 mdp · #7 messages · #10 requête de certificat · #12 échange personnel** |
| **Performances** | **Hachage > symétrique >>> asymétrique** |

### Questions d'examen typiques
1. **Différence entre identification et authentification** ; schéma du **challenge**.
2. Citer les **quatre moyens d'authentification** avec un exemple chacun ; qu'est-ce que l'**authentification forte** ?
3. **Mots de passe :** risques, bonnes pratiques, **méthodes de création** (phonétique, premières lettres), **techniques de craquage**.
4. Pourquoi le **verrouillage de compte** peut-il provoquer un **déni de service** ?
5. **OTP :** définition, avantages et **trois techniques**.
6. **Biométrie :** les **cinq critères** d'un attribut ; exemples de caractéristiques.
7. **Terminologie :** chiffrement, décryptage, cryptographie, cryptanalyse, cryptologie.
8. Définir un **crypto-système (P, C, K, E, D)** ; condition **dₖ(eₖ(x)) = x**.
9. **Principes de Kerckhoffs** et leur version moderne.
10. **Chiffrement symétrique :** principe, avantages, inconvénients ; **flux vs bloc**.
11. **DES :** histoire, caractéristiques, **4 phases**, **fonction f** (expansion, XOR, S-Box, P), **exemple de S-Box** ; pourquoi est-il **obsolète** ?
12. **3DES** (variantes) et **AES** (étapes d'un tour, nombre de tours).
13. **Chiffrement asymétrique :** principe, **deux scénarios** (confidentialité / authenticité), **chiffrement mixte**.
14. **RSA :** génération des clés, chiffrement/déchiffrement, **exemple chiffré**, **preuve**, sécurité.
15. **Fonction de hachage :** définition, **propriétés**, **vérification d'intégrité** ; MD5/SHA-1.
16. **Signature numérique :** génération, vérification, **services assurés** ; comparaison avec la **signature manuscrite**.
17. **Certificat et CA :** quel problème résolvent-ils (**attaque Mallet**) ?
18. **PKI :** rôle de la **CA**, de la **RA**, de l'annuaire ; **modèle de confiance** ; **co-certification**.
19. **Structure d'un certificat X.509** et **extensions** (Key Usage, Basic Constraints, CRL Distribution Points…).
20. Comparer **symétrique et asymétrique** ; justifier le **chiffrement mixte** avec les **benchmarks**.

---

*Fin du résumé — Chapitre 4 : Services de la sécurité informatique (authentification et cryptographie).*
