# Chapitre 3 — Les menaces, les attaques et les intrusions
**Établissement :** Institut Supérieur d'Informatique du Kef
**Enseignante :** Mounira Laabidi
**Public :** 3LGL (3ᵉ année Licence)
**Type de document :** Résumé d'étude avec explications, tableaux comparatifs et conseils d'examen

> 🎓 **Nature de ce document :** résumé **pédagogique et défensif**. Il décrit les attaques au **niveau conceptuel** (comprendre pour mieux se défendre), comme dans le cours.

---

## Table des matières

1. [Plan du chapitre](#1-plan-du-chapitre)
2. [Définition](#2-définition)
3. [Les types de pertes (10 types)](#3-les-types-de-pertes-10-types)
4. [Le cycle d'une attaque (cyber kill chain)](#4-le-cycle-dune-attaque-cyber-kill-chain)
5. [Classification des attaques : vue d'ensemble](#5-classification-des-attaques--vue-densemble)
6. [Classification 1 — par méthode d'attaque](#6-classification-1--par-méthode-dattaque)
7. [Classification 2 — par objectif](#7-classification-2--par-objectif)
8. [Classification 3 — par méthode technique](#8-classification-3--par-méthode-technique)
9. [Classification 4 — par impact](#9-classification-4--par-impact)
10. [Classification 5 — par type d'acteur](#10-classification-5--par-type-dacteur)
11. [Conclusion du chapitre](#11-conclusion-du-chapitre)
12. [Défendre à chaque étape du cycle (ajout)](#12-défendre-à-chaque-étape-du-cycle-ajout)
13. [Remarques sur le support de cours](#13-remarques-sur-le-support-de-cours)
14. [Fiche de révision rapide](#14-fiche-de-révision-rapide)

---

## 1. Plan du chapitre

1. **Définition**
2. **Types de pertes**
3. **Cycle d'une attaque**
4. **Classification des attaques**

> 🔗 **Lien avec les chapitres précédents :** le chapitre 1 présentait les 5 menaces principales (malware, phishing, DDoS, exploits, menaces internes) ; le chapitre 2 la **politique de sécurité**. Ce chapitre **approfondit** : *quelles pertes ?* · *comment se déroule une attaque ?* · *comment les classer ?*

---

## 2. Définition

> Les **menaces, attaques et intrusions** désignent **toute action malveillante** visant à **compromettre la sécurité d'un système d'information**. Cela inclut des activités visant à **voler, endommager ou altérer** des **données** et des **systèmes**, souvent dans le but de :
> - tirer un **avantage financier**,
> - **espionner**,
> - ou causer des **perturbations**.

### Vocabulaire : menace, vulnérabilité, attaque, intrusion *(ajout)*

| Terme | Définition | Exemple |
|---|---|---|
| **Menace** | **Danger potentiel** qui pourrait exploiter une faiblesse | Groupe de cybercriminels, employé malveillant |
| **Vulnérabilité** | **Faiblesse** d'un système | Logiciel non mis à jour, mot de passe par défaut |
| **Attaque** | **Action concrète** exécutée pour exploiter une vulnérabilité | Injection SQL, phishing |
| **Intrusion** | **Accès non autorisé réussi** à un système | Accès obtenu au compte administrateur |
| **Risque** | **Probabilité × impact** d'une menace qui exploite une vulnérabilité | *(voir chapitre 1)* |

---

## 3. Les types de pertes (10 types)

> Les **types de perte** désignent les **différentes catégories de conséquences négatives** que peuvent subir les **systèmes**, les **données** ou les **utilisateurs** après une **violation de sécurité**.
> Ces pertes touchent les **ressources physiques, numériques et humaines**.
> Les **trois principales catégories** sont liées : aux **données**, à l'**intégrité des systèmes**, et à la **continuité des services ou des opérations**.

### 3.1 Tableau des 10 pertes

| # | Perte | Définition | Exemple du cours | Conséquences |
|---|---|---|---|---|
| 1 | **Confidentialité** | **Divulgation non autorisée** d'informations sensibles | **Piratage de base de données** : numéros de carte bancaire, identifiants, informations médicales volés | **Vol d'identité**, fraude, atteinte à la vie privée, dommages à la réputation |
| 2 | **Intégrité** | Données **modifiées, altérées ou corrompues sans autorisation**, **à l'insu** de l'utilisateur | Modification non autorisée d'un fichier/base ; malware qui altère les données | **Perte de confiance** dans les informations, **erreurs de décision**, corruption de fichiers essentiels |
| 3 | **Disponibilité** | **Accès interrompu** aux systèmes, données ou services | **Attaque DDoS** surchargeant un serveur ; panne après une attaque | Interruption des services en ligne, **perte de productivité**, perturbation des opérations |
| 4 | **Propriété** | Perte de **données, logiciels, brevets, ressources numériques critiques** (vol, piratage, erreur humaine) | **Vol de propriété intellectuelle** : algorithmes propriétaires, designs numériques | Dommages financiers, **pertes concurrentielles**, atteinte à la réputation |
| 5 | **Financière** | **Diminution des actifs financiers** (vol d'argent, amendes, pertes dues aux interruptions) | **Ransomware** : rançon pour récupérer ses fichiers ; **fraude en ligne** | **Rançon, remédiation**, perte de revenus, **amendes réglementaires**, coûts juridiques |
| 6 | **Réputation** | Atteinte à **l'image publique** (confiance des clients, partenaires, investisseurs) | **Fuite de données** de clients ; **piratage d'un site web** | **Perte de clients**, baisse des ventes, difficulté à attirer des partenaires, impact sur la **valorisation** |
| 7 | **Contrôle ou accès** | Accès **usurpé** ou **privilèges administrateur** acquis par un attaquant | Hacker qui prend un **compte administrateur** et contrôle un réseau | **Manipulation des données**, vol d'informations, **propagation de malwares** ou d'attaques supplémentaires |
| 8 | **Conformité (réglementaire)** | **Non-respect des lois, régulations et normes** de sécurité | **Non-respect du RGPD** lors d'une fuite de données personnelles | **Amendes et pénalités**, audits, atteinte à la réputation, coûts de mise en conformité |
| 9 | **Ressources humaines** | **Perte de compétences ou de talents** en cybersécurité/informatique | **Départ d'un membre clé** de l'équipe IT sans plan de transition ; démission d'un expert | **Moindre capacité** à gérer les risques, **incapacité à résoudre vite** les incidents, perte de **connaissances internes** |
| 10 | **Temps** | Temps nécessaire à **résoudre** l'incident, empêchant de travailler efficacement | **Réponse à un ransomware** ; **restauration** de systèmes après une intrusion | **Interruption des opérations**, retards dans les projets, coûts de résolution |

### 3.2 Regroupement des pertes

| Famille | Pertes concernées |
|---|---|
| **Triade CID** (données et systèmes) | **Confidentialité · Intégrité · Disponibilité** |
| **Propriétés et ressources critiques** | **Propriété · Financière · Ressources humaines · Temps** |
| **Image et légalité** | **Réputation · Conformité** |
| **Maîtrise du système** | **Contrôle / accès** |

> 🔑 **Un seul incident entraîne souvent plusieurs pertes** *(lecture)* : un **ransomware** provoque une perte de **disponibilité** (fichiers inaccessibles), **financière** (rançon), de **temps** (restauration), de **réputation** et peut-être de **conformité** (RGPD).

### 3.3 Ce que préconise le cours pour s'en prémunir

- **Politiques et outils de sécurité robustes.**
- **Pratiques de gestion des risques.**
- **Formation des utilisateurs** à la sécurité informatique.
- **Plans et stratégies de gestion des incidents** pour **minimiser les impacts**.

---

## 4. Le cycle d'une attaque (cyber kill chain)

> Le cycle d'une attaque suit des **étapes bien définies**, souvent représentées par le modèle de la **cyber kill chain** (« chaîne de frappe »). Le cours en distingue **9 étapes**.

### 4.1 Vue d'ensemble

| # | Étape | En une phrase |
|---|---|---|
| 1 | **Reconnaissance** | **Collecte d'informations** sur la cible |
| 2 | **Armes** (armement) | **Création ou acquisition** d'outils malveillants |
| 3 | **Livraison** | **Transmission** du malware à la cible |
| 4 | **Exploitation** | **Exploitation d'une vulnérabilité** pour pénétrer le système |
| 5 | **Installation** | Installation de **portes dérobées** pour **maintenir l'accès** |
| 6 | **Commandes et contrôle** (C2) | **Communication à distance** pour donner des instructions |
| 7 | **Actions sur les objectifs** | Réalisation de l'**objectif** (vol, destruction, etc.) |
| 8 | **Exfiltration** | **Extraction** des données sensibles |
| 9 | **Atténuation / Post-exploitation** | **Masquage des traces** pour éviter la détection |

```
 ① Reconnaissance ─► ② Armes ─► ③ Livraison ─► ④ Exploitation ─► ⑤ Installation
                                                                        │
 ⑨ Masquage des traces ◄─ ⑧ Exfiltration ◄─ ⑦ Actions sur objectifs ◄─ ⑥ Commande & contrôle
```

> 💡 **Moyen mnémotechnique :** *« **R**eprendre **A**vec **L**e **E**xtra **I**ntensif **C**ommande **A**vec **E**xtra **M**ayonnaise »* → **R**econnaissance, **A**rmes, **L**ivraison, **E**xploitation, **I**nstallation, **C**2, **A**ctions, **E**xfiltration, **M**asquage. *(Phrase inventée pour mémoriser l'ordre.)*

### 4.2 Détail de chaque étape

#### ① Reconnaissance (*Recon*)
Première étape : **collecter des informations** sur la cible, de façon **passive** ou **active**.

| Type | Description | Exemples |
|---|---|---|
| **Passive** | Informations **publiques**, **sans interagir directement** avec la cible | **Réseaux sociaux**, **site web** de l'entreprise, **bases de données publiques** |
| **Active** | **Interaction directe** avec la cible | **Scan de ports** du réseau, recherche de **vulnérabilités** spécifiques ; outils comme **Nmap** ou **Shodan** |

**Objectifs :**
- Identifier des **failles potentielles** ;
- Obtenir des informations sur les **employés** (nom, poste, e-mail) pour des attaques de **phishing** ;
- Repérer les **technologies**, **versions de logiciels** et **services en ligne** utilisés.

#### ② Armes (préparation de l'outil d'attaque)
- L'attaquant **prépare les outils** : **création de malware**, **acquisition de zero-day** (vulnérabilités non corrigées), **configuration d'infrastructures** (serveurs de commandement et de contrôle **C&C**).
- **Exemples d'outils :** malware, scripts d'exploitation, exploits de vulnérabilités, phishing, outils d'**ingénierie sociale**.
- **Objectif :** développer ou acquérir des outils pour **exploiter efficacement la cible**.

#### ③ Livraison (*Delivery*)
**Transmettre l'outil d'attaque** à la cible, selon plusieurs vecteurs :

| Vecteur | Description |
|---|---|
| **Phishing** | E-mail avec **lien malveillant** ou **pièce jointe infectée** |
| **Exploitation de vulnérabilité** | Faille d'un **service exposé sur Internet** pour livrer le malware |
| **USB malveillant** | **Clé USB infectée** branchée sur l'ordinateur cible |
| **DDoS** | **Requêtes massives** pour saturer les serveurs et perturber les services |

**Objectif :** introduire le **code malveillant** ou le **moyen d'accès** dans l'environnement de la cible.

#### ④ Exploitation
Une fois l'outil livré, l'attaquant **exploite la vulnérabilité ou l'accès** pour **prendre le contrôle** :
- **Exploitation de vulnérabilités** : faille utilisée pour exécuter du code malveillant, contrôler le système ou installer un malware.
- **Exécution du code malveillant** : compromettre le système, installer des **backdoors**, exécuter des **commandes à distance**.
- **Gain d'accès privilégié** : obtenir un accès **administrateur / root** (**élévation de privilèges**).

**Objectif :** acquérir un **accès direct** au système ou au réseau de la cible.

#### ⑤ Installation (porte dérobée)
- Installer des outils pour **maintenir un accès persistant** : **backdoors**, **rootkits**, qui permettent de **revenir à tout moment**, **même si la vulnérabilité initiale est corrigée**.
- **Exemples :** **cheval de Troie**, **botnet**, **logiciel espion**.
- **Objectif :** accès **durable et discret**.

#### ⑥ Commandes et contrôle (C2 / *Command and Control*)
- Établir un **canal de communication** avec les systèmes compromis pour **donner des instructions à distance** (via des **serveurs C&C**).
- **Exemple :** canal **crypté** pour exécuter des commandes, voler des informations ou **répandre le malware** à d'autres systèmes du réseau.
- **Objectif :** maintenir la communication et **contrôler à distance** les actions de l'attaque.

#### ⑦ Actions sur les objectifs
Réaliser l'**objectif final**, selon les intentions de l'attaquant :

| Action | Description |
|---|---|
| **Vol de données sensibles** | Données **personnelles, financières, confidentielles** (bases de données, cartes de crédit…) |
| **Ransomware** | **Chiffrement** des données et **demande de rançon** |
| **Sabotage** | **Corruption ou destruction** de données |
| **Espionnage** | **Surveillance ou enregistrement** des communications sensibles |
| **Exfiltration** | **Envoi des données volées** vers un serveur contrôlé par l'attaquant |

#### ⑧ Exfiltration (extraction des données)
- Si l'objectif est le **vol de données** : les **extraire** du réseau compromis et les **envoyer vers les serveurs de l'attaquant**.
- **Exemple :** **canaux cachés ou chiffrés** pour envoyer une base d'utilisateurs à un serveur contrôlé par l'attaquant.
- **Objectif :** exfiltrer des informations précieuses (bases de données, identifiants, documents confidentiels) **tout en évitant la détection**.

#### ⑨ Atténuation / Post-exploitation (masquage des traces)
- **Masquer sa présence** ou **effacer les traces** : **suppression des logs**, désinstallation de logiciels malveillants.
- **Exemples :** effacer les **journaux d'accès**, modifier les **paramètres de sécurité**, **obfuscation** du malware.
- **Objectif :** **dissimuler l'attaque** pour **prolonger l'accès sans être détecté**.

### 4.3 Résumé « objectif de chaque étape »

| Étape | Objectif |
|---|---|
| ① Reconnaissance | Connaître la cible |
| ② Armes | Se doter des bons outils |
| ③ Livraison | Introduire l'outil chez la cible |
| ④ Exploitation | Obtenir un accès direct |
| ⑤ Installation | Rester (persistance) |
| ⑥ C2 | Garder le contrôle à distance |
| ⑦ Actions sur objectifs | Atteindre le but final |
| ⑧ Exfiltration | Sortir les données |
| ⑨ Masquage | Ne pas être détecté |

---

## 5. Classification des attaques : vue d'ensemble

Les attaques peuvent être **classées de différentes manières** selon leur **méthode d'exécution**, leur **objectif** ou leur **nature**. Le cours retient **5 critères** :

| # | Critère | Catégories |
|---|---|---|
| 1 | **Méthode d'attaque** | Externes · Internes · Ingénierie sociale |
| 2 | **Objectif** | Vol de données · Perturbation des services · Exploitation de vulnérabilités · Sabotage/destruction · Espionnage/surveillance |
| 3 | **Méthode technique** | Interception (MitM) · Injection · Contournement (bypass) · Social engineering |
| 4 | **Impact** | Sur la **confidentialité** · l'**intégrité** · la **disponibilité** |
| 5 | **Type d'acteur** | **Opportunistes** · **Ciblées (APT)** |

---

## 6. Classification 1 — par méthode d'attaque

### 6.1 Attaques externes
Menées par des **acteurs extérieurs** à l'organisation : **pirates (hackers)** ou **groupes de cybercriminels**.

| Exemple | Description |
|---|---|
| **Phishing** | Fraude pour pousser les utilisateurs à **divulguer** mots de passe ou informations bancaires |
| **Exploitation de vulnérabilités** | Utilisation de **failles** de systèmes/logiciels pour **pénétrer un réseau** |

### 6.2 Attaques internes
Menées par des personnes ayant un **accès légitime** (**employés, prestataires, partenaires**…).

| Exemple | Description |
|---|---|
| **Fuite de données** | Un employé **copie des informations sensibles** sans autorisation et les **divulgue à des concurrents** |
| **Sabotage** | Un employé malveillant **endommage ou supprime** des données critiques |

### 6.3 Attaques par ingénierie sociale
Exploitent la **psychologie humaine** pour **manipuler** les individus.

| Exemple | Description |
|---|---|
| **Phishing** | E-mails/messages frauduleux pour obtenir des informations |
| **Vishing** (phishing vocal) | Escroquerie **par téléphone** |
| **Baiting** (appâtage) | Un **appât** (ex. **clé USB contenant un malware**) pour inciter à interagir avec le système compromis |

### 6.4 Comparaison

| | **Externes** | **Internes** | **Ingénierie sociale** |
|---|---|---|---|
| **Qui ?** | Acteurs **extérieurs** | Personnes **avec accès légitime** | Tout attaquant |
| **Point fort** | Techniques variées | **Connaît** le système, déjà **à l'intérieur** | Cible **l'humain** |
| **Difficulté de détection** | Moyenne | **Élevée** (accès légitime) | **Élevée** (pas de faille technique) |
| **Exemples** | Phishing, exploits | Fuite, sabotage | Phishing, vishing, baiting |

---

## 7. Classification 2 — par objectif

### 7.1 Vol de données
Voler des **informations sensibles** : informations personnelles, **secrets commerciaux**, **identifiants**, **données financières**.
- **Hacking de base de données** : accès non autorisé pour voler des informations personnelles.
- **Ransomware** : **cryptage** des données et **rançon** pour le décryptage.

### 7.2 Perturbation des services
**Perturber ou détruire** des services essentiels (affecte la **disponibilité**).
- **DoS / DDoS** : **saturation** d'un serveur ou réseau par un grand nombre de requêtes.
- **Virus et vers** : infectent les systèmes et peuvent causer **perturbations ou pannes**.

### 7.3 Exploitation de vulnérabilités
Exploiter des **failles** pour **prendre le contrôle** ou causer des dégâts.
- **Zero-day** : vulnérabilité **inconnue du fournisseur** du logiciel.
- **Injection SQL** : requêtes malveillantes envoyées à une application vulnérable pour **obtenir des informations ou exécuter du code**.

### 7.4 Sabotage ou destruction
**Détruire** des données/systèmes ou **altérer** des informations de manière **irréversible**.
- **Wiper malware** : conçu pour **effacer définitivement** des données.
- **Attaque sur les infrastructures critiques** : **centrales électriques**, **hôpitaux**…

### 7.5 Espionnage ou surveillance
**Surveiller** une cible, **voler des informations sensibles** ou recueillir des **renseignements stratégiques**.
- **Spyware** : logiciel espion qui collecte des informations **à l'insu** de l'utilisateur.
- **Attaques ciblées (APT)** : attaques **sophistiquées et prolongées** menées par des **acteurs étatiques** ou groupes organisés.

---

## 8. Classification 3 — par méthode technique

### 8.1 Attaques par interception (*Man-in-the-Middle*, MitM)
L'attaquant **intercepte** (et parfois **modifie**) la communication entre **deux parties** **sans qu'elles s'en aperçoivent**.

```
   Alice ──────────────► [ Attaquant (MitM) ] ──────────────► Bob
   (croit parler à Bob)   intercepte / modifie    (croit parler à Alice)
```

| Exemple | Description |
|---|---|
| **Sniffing** | **Capture des paquets** circulant sur un réseau pour obtenir des informations sensibles (**identifiants, mots de passe**) |
| **SSL stripping** | **Dégrader** une connexion **HTTPS en HTTP** pour intercepter les informations échangées |

### 8.2 Attaques par injection
**Injecter du code malveillant** dans une application pour **altérer son fonctionnement** ou **voler des informations**.

| Exemple | Description |
|---|---|
| **Injection SQL** | Commandes SQL malveillantes insérées dans un **champ de saisie** pour accéder à la **base de données** |
| **Injection de commandes système** | Exploiter des failles pour **exécuter des commandes système arbitraires** |

### 8.3 Attaques par contournement (*Bypass*)
**Contourner les mécanismes de sécurité** (ex. **authentification**) pour accéder aux ressources.

| Exemple | Description |
|---|---|
| **Brute-force** | Essayer **toutes les combinaisons possibles** de mots de passe |
| **Exploitation de sessions** | **Prendre le contrôle d'une session active** ou **voler un cookie de session** pour se faire passer pour un utilisateur authentifié |

### 8.4 Attaques par ingénierie sociale (*social engineering*)
Exploitent la **psychologie humaine**.

| Exemple | Description |
|---|---|
| **Phishing** | L'attaquant **se fait passer pour une entité de confiance** et demande des informations sensibles |
| **Vishing** | **La voix** pour convaincre la victime de donner des informations personnelles |
| **Impersonation** | **Se faire passer pour une personne de confiance** pour obtenir un accès non autorisé |

### 8.5 Récapitulatif

| Famille technique | Principe | Exemples |
|---|---|---|
| **Interception (MitM)** | S'**interposer** dans la communication | Sniffing, SSL stripping |
| **Injection** | Faire exécuter **du code/des commandes** par l'application | Injection SQL, injection de commandes |
| **Contournement** | **Passer outre** l'authentification | Brute-force, vol de session |
| **Social engineering** | Manipuler **l'humain** | Phishing, vishing, impersonation |

---

## 9. Classification 4 — par impact

Ce critère reprend la **triade CID** :

| Impact | Objectif de l'attaquant | Exemples du cours |
|---|---|---|
| **Confidentialité** | **Compromettre le secret** des informations (vol) | **Fuite de données** · **Écoute clandestine** (e-mails, appels) |
| **Intégrité** | **Altérer ou corrompre** les données (inutilisables ou incorrectes) | **Modification de fichiers** · **Ransomware** (cryptage rendant les données inaccessibles) |
| **Disponibilité** | Rendre un **service ou système indisponible** | **DDoS** · **Destruction physique** d'équipements |

> ⚠️ **Remarque** *(ajout)* : le **ransomware** est classé ici sous **l'intégrité** (les fichiers sont chiffrés/altérés), mais il touche **aussi la disponibilité** (fichiers inaccessibles) et souvent la **confidentialité** (double extorsion avec menace de publication). C'est un cas **multi-impacts**.

---

## 10. Classification 5 — par type d'acteur

### 10.1 Attaques opportunistes
- Agissent **sans planification spécifique** ; ciblent des **vulnérabilités accessibles à grande échelle** (attaques **automatisées**).
- **Exemples :** **ransomware de masse** (e-mails ou sites compromis) ; **scan de ports** (recherche de vulnérabilités communes à grande échelle).

### 10.2 Attaques ciblées (APT)
- **Advanced Persistent Threats** : attaques **sophistiquées et prolongées**, menées par des **groupes organisés** (**hacktivistes**, **États-nations**) pour des **objectifs stratégiques spécifiques**.
- **Exemples :** **espionnage industriel** (vol de propriété intellectuelle) ; **cyberattaque géopolitique** (perturber des **infrastructures critiques** ou voler des informations stratégiques d'un **gouvernement**).

### 10.3 Comparaison

| | **Opportunistes** | **Ciblées (APT)** |
|---|---|---|
| **Cible** | **N'importe qui** (grande échelle) | **Une organisation précise** |
| **Planification** | **Aucune / minimale** | **Longue et soigneuse** |
| **Durée** | Courte | **Prolongée** (persistance) |
| **Moyens** | Outils automatisés | **Sophistiqués** |
| **Acteurs** | Cybercriminels | **États-nations, groupes organisés, hacktivistes** |
| **Exemples** | Ransomware de masse, scan de ports | Espionnage industriel, cyberattaque géopolitique |

> 🔗 **APT et cycle d'attaque** *(lecture)* : une APT parcourt **toute la kill chain** et insiste sur **l'installation (persistance)**, le **C2** et le **masquage des traces** (étapes 5, 6 et 9).

---

## 11. Conclusion du chapitre

- Les attaques se classent selon **plusieurs critères** :
  - **méthode** (externe, interne, sociale),
  - **objectif** (vol de données, perturbation, sabotage),
  - **impact** (confidentialité, intégrité, disponibilité).
- **Chaque type d'attaque exige une stratégie de défense spécifique**, combinant :
  - des **outils techniques** : **pare-feu, chiffrement, détection d'intrusion** ;
  - des **pratiques humaines** : **sensibilisation à la sécurité, vérification des e-mails, gestion des accès**.

---

## 12. Défendre à chaque étape du cycle (ajout)

> ⚠️ **Section ajoutée** : le support présente les étapes de l'attaque ; voici, pour chaque étape, **comment la détecter ou la bloquer**. Le principe : **casser la chaîne le plus tôt possible**.

| Étape de l'attaque | Contre-mesures possibles |
|---|---|
| ① **Reconnaissance** | Limiter les **informations publiques** (sites, réseaux sociaux), **masquer les bannières/versions**, surveiller les **scans** |
| ② **Armes** | Difficile à contrer directement : **veille sur les menaces** (*threat intelligence*) |
| ③ **Livraison** | **Filtrage e-mail / anti-phishing**, **blocage des pièces jointes** dangereuses, **désactivation des ports USB**, pare-feu, anti-DDoS |
| ④ **Exploitation** | **Mises à jour / correctifs**, **durcissement** des configurations, **tests de pénétration**, principe du **moindre privilège** |
| ⑤ **Installation** | **Antivirus / EDR**, **liste blanche d'applications**, surveillance de l'intégrité des fichiers |
| ⑥ **C2** | **Filtrage du trafic sortant**, **IDS/IPS**, **analyse DNS**, segmentation du réseau |
| ⑦ **Actions sur objectifs** | **Sauvegardes hors ligne**, **chiffrement**, **contrôle d'accès**, **MFA** |
| ⑧ **Exfiltration** | **Prévention de perte de données (DLP)**, surveillance des **flux sortants** inhabituels |
| ⑨ **Masquage des traces** | **Journaux centralisés et protégés** (SIEM), **horodatage**, **droits limités** sur les logs |

### Défense en profondeur et lien avec les 5 niveaux du chapitre 1

| Niveau (chap. 1) | Étapes de la kill chain principalement couvertes |
|---|---|
| **Réseau** (pare-feu, IDS/IPS, VPN) | ③ Livraison · ⑥ C2 · ⑧ Exfiltration |
| **Applications** (correctifs, tests) | ④ Exploitation |
| **Données** (chiffrement, sauvegardes) | ⑦ Actions · ⑧ Exfiltration |
| **Organisationnel** (formation, politiques) | ① Reconnaissance · ③ Livraison (phishing) |
| **Physique** | ③ Livraison (USB) · accès aux équipements |

> 💡 **Pour aller plus loin** *(ajout)* : le référentiel **MITRE ATT&CK** décrit en détail les **tactiques et techniques** des attaquants et complète la *kill chain* (modèle de **Lockheed Martin**).

---

## 13. Remarques sur le support de cours

1. **Métadonnées du PDF** : le fichier porte le titre interne « Les systèmes combinatoires » (reste d'un ancien document) ; le **contenu** est bien le chapitre 3 de **sécurité informatique**.
2. **Kill chain classique vs cours** : le modèle original de **Lockheed Martin** compte **7 étapes** (reconnaissance, armement, livraison, exploitation, installation, C2, actions sur objectifs). Le cours en **ajoute 2** : **exfiltration** et **masquage des traces** (9 étapes).
3. **Titre de la 9ᵉ étape** : le support écrit tantôt « **Atténuation / Post-Exploitation** » (liste), tantôt « **L'Atténuation / Exfiltration Post-Exploitation** » (détail). Il s'agit bien de l'**étape de masquage des traces** ; « atténuation » est à comprendre comme **dissimulation**, pas comme « réduction des risques ».
4. **« Armes »** : traduction de **« weaponization »** (armement) ; « Reccy » désigne la **reconnaissance** (*recon*).
5. **Chevauchement des étapes 7 et 8** : l'**exfiltration** apparaît **comme exemple de l'étape 7** puis comme **étape 8** à part entière.
6. **DDoS comme moyen de « livraison »** : le cours le cite parmi les vecteurs de livraison, alors qu'un DDoS vise surtout la **disponibilité** (il ne livre pas de malware) ; à comprendre comme **vecteur de perturbation**.
7. **Ransomware classé en intégrité** : voir [section 9](#9-classification-4--par-impact) (il touche en fait plusieurs principes).
8. **Redondances voulues** : **phishing** et **vishing** reviennent dans les classifications « méthode d'attaque » et « méthode technique » ; c'est **normal** : une même attaque peut être classée selon **plusieurs critères**.
9. **Faute de frappe** : « ménaces » dans le nom du fichier ; sans incidence.

---

## 14. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Menace / attaque / intrusion** | Toute **action malveillante** visant à **compromettre** la sécurité d'un SI (vol, dommage, altération, espionnage, perturbation) |
| **10 pertes** | **Confidentialité · Intégrité · Disponibilité · Propriété · Financière · Réputation · Contrôle/accès · Conformité · Ressources humaines · Temps** |
| **Exemple de perte de propriété** | Vol de **propriété intellectuelle** (algorithmes, designs) |
| **Exemple de perte de conformité** | Non-respect du **RGPD** lors d'une fuite |
| **Exemple de perte RH** | Départ d'un expert cybersécurité **sans transition** |
| **Cyber kill chain (9 étapes)** | **Reconnaissance → Armes → Livraison → Exploitation → Installation → C2 → Actions → Exfiltration → Masquage des traces** |
| **Reconnaissance** | **Passive** (sources publiques) / **Active** (scan : Nmap, Shodan) |
| **Livraison (vecteurs)** | **Phishing · Vulnérabilité exposée · USB malveillant · DDoS** |
| **Installation** | **Backdoors, rootkits** : accès **persistant** même après correction de la faille |
| **C2** | **Canal (souvent chiffré)** pour piloter les machines compromises |
| **Actions sur objectifs** | **Vol · Ransomware · Sabotage · Espionnage · Exfiltration** |
| **5 critères de classification** | **Méthode · Objectif · Méthode technique · Impact · Type d'acteur** |
| **Par méthode** | **Externes · Internes · Ingénierie sociale** |
| **Par objectif** | **Vol de données · Perturbation · Exploitation de vulnérabilités · Sabotage · Espionnage** |
| **Par méthode technique** | **Interception (MitM) · Injection · Contournement · Social engineering** |
| **MitM** | **S'interposer** entre deux parties : **sniffing**, **SSL stripping** |
| **Injection** | **Injection SQL**, injection de **commandes système** |
| **Contournement** | **Brute-force**, **exploitation/vol de session (cookie)** |
| **Ingénierie sociale** | **Phishing · Vishing · Baiting · Impersonation** |
| **Par impact** | **Confidentialité** (fuite, écoute) · **Intégrité** (modification, ransomware) · **Disponibilité** (DDoS, destruction physique) |
| **Par acteur** | **Opportunistes** (grande échelle, automatisées) vs **Ciblées / APT** (sophistiquées, prolongées, États/groupes organisés) |
| **Wiper** | Malware qui **efface définitivement** les données |
| **Zero-day** | Vulnérabilité **inconnue du fournisseur** |
| **Défense** | **Outils techniques** (pare-feu, chiffrement, IDS) **+ pratiques humaines** (sensibilisation, vérification des e-mails, gestion des accès) |

### Questions d'examen typiques
1. **Définir** menaces, attaques et intrusions.
2. Citer et illustrer **cinq types de pertes** (avec exemple et conséquences).
3. Expliquer la **différence entre perte de confidentialité, d'intégrité et de disponibilité**.
4. Qu'est-ce qu'une **perte de contrôle** ? Une **perte de conformité** ? Une **perte de ressources humaines** ?
5. Décrire la **cyber kill chain** : les **9 étapes** dans l'ordre et l'**objectif** de chacune.
6. Différence entre **reconnaissance passive et active** ; citer des outils (Nmap, Shodan).
7. Citer les **vecteurs de livraison** d'un malware.
8. Pourquoi l'attaquant installe-t-il une **porte dérobée** ? À quoi sert le **C2** ?
9. En quoi consiste l'**exfiltration** ? Et le **masquage des traces** ?
10. Citer les **5 critères de classification** des attaques avec des exemples.
11. Différencier **attaques externes, internes et par ingénierie sociale**.
12. Expliquer une attaque **Man-in-the-Middle** (sniffing, SSL stripping).
13. Expliquer **injection**, **brute-force** et **exploitation de session**.
14. Différence entre **attaque opportuniste** et **APT** ; donner deux exemples de chaque.
15. Pour **chaque catégorie d'attaque**, proposer une **mesure de défense** adaptée.

---

*Fin du résumé — Chapitre 3 : Les menaces, les attaques et les intrusions.*
