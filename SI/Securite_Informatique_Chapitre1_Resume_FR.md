# Chapitre 1 — Aspects généraux de la sécurité informatique
**Établissement :** Institut Supérieur d'Informatique du Kef
**Enseignante :** Mounira Laabidi
**Public :** 3LGL (3ᵉ année Licence)
**Type de document :** Résumé d'étude avec explications, tableaux comparatifs et conseils d'examen

---

## Table des matières

1. [Plan du chapitre](#1-plan-du-chapitre)
2. [Définition de la sécurité informatique](#2-définition-de-la-sécurité-informatique)
3. [Les principes de la sécurité](#3-les-principes-de-la-sécurité)
4. [Nécessité de la sécurité](#4-nécessité-de-la-sécurité)
5. [Les cinq niveaux de sécurisation](#5-les-cinq-niveaux-de-sécurisation)
6. [Niveau 1 — Sécurité physique](#6-niveau-1--sécurité-physique)
7. [Niveau 2 — Sécurité réseau](#7-niveau-2--sécurité-réseau)
8. [Niveau 3 — Sécurité des applications](#8-niveau-3--sécurité-des-applications)
9. [Niveau 4 — Sécurité des données](#9-niveau-4--sécurité-des-données)
10. [Niveau 5 — Sécurité organisationnelle](#10-niveau-5--sécurité-organisationnelle)
11. [Les menaces : vue d'ensemble](#11-les-menaces--vue-densemble)
12. [Menace 1 — Les malwares](#12-menace-1--les-malwares)
13. [Menace 2 — Le phishing](#13-menace-2--le-phishing)
14. [Menace 3 — Les attaques DDoS](#14-menace-3--les-attaques-ddos)
15. [Menace 4 — Les exploits de vulnérabilité](#15-menace-4--les-exploits-de-vulnérabilité)
16. [Menace 5 — Les menaces internes](#16-menace-5--les-menaces-internes)
17. [Le cycle de la sécurité](#17-le-cycle-de-la-sécurité)
18. [L'évaluation des risques en détail](#18-lévaluation-des-risques-en-détail)
19. [Remarques sur le support de cours](#19-remarques-sur-le-support-de-cours)
20. [Fiche de révision rapide](#20-fiche-de-révision-rapide)

---

## 1. Plan du chapitre

1. **La sécurité** : définition, principes, nécessité, niveaux de sécurisation
2. **Les menaces**
3. **Le cycle de la sécurité**

---

## 2. Définition de la sécurité informatique

> **La sécurité informatique** désigne l'**ensemble des mesures et des pratiques** visant à **protéger** les **systèmes informatiques**, les **réseaux**, les **données** et les **informations** contre :
> - les **accès non autorisés**,
> - les **cyberattaques**,
> - les **pertes**,
> - les **dommages**.

**Ce qu'on protège :** systèmes · réseaux · données · informations.
**Contre quoi :** accès non autorisés · cyberattaques · pertes · dommages.

---

## 3. Les principes de la sécurité

Le cours retient **5 principes**, dont les **trois premiers** forment la célèbre **triade CID** (*CIA* en anglais) :

| # | Principe | Définition | Question à se poser |
|---|---|---|---|
| 1 | **Confidentialité** | Assurer que **seules les personnes autorisées** peuvent accéder aux informations | *Qui peut lire ?* |
| 2 | **Intégrité** | Garantir que les données ne sont **ni modifiées ni altérées sans autorisation** | *Les données sont-elles exactes et intactes ?* |
| 3 | **Disponibilité** | S'assurer que les informations et systèmes sont **accessibles et utilisables quand nécessaire** | *Le service est-il accessible ?* |
| 4 | **Authentification** | **Vérifier l'identité** des utilisateurs et des systèmes **avant** de leur accorder l'accès | *Qui êtes-vous ?* |
| 5 | **Traçabilité** | **Enregistrer et surveiller** les actions des utilisateurs pour **détecter d'éventuelles anomalies** | *Qui a fait quoi, et quand ?* |

### La triade CID (schéma du cours)

```
              Confidentialité
                   ▲
                  ╱ ╲
                 ╱   ╲
                ╱ SÉCU╲
               ╱ RITÉ  ╲
              ▼─────────▼
         Intégrité     Disponibilité
```

> 💡 **Exemples pour mémoriser** *(ajout)* :
> - **Confidentialité** violée : fuite de mots de passe.
> - **Intégrité** violée : modification d'un montant dans une base de données.
> - **Disponibilité** violée : attaque DDoS qui rend un site inaccessible.

---

## 4. Nécessité de la sécurité

| Besoin | Explication |
|---|---|
| **Protection des données** | Prévenir les **violations de données**, sources de **pertes financières** et d'**atteinte à la réputation** |
| **Conformité légale** | Respecter les **lois et régulations** sur la protection des données |
| **Continuité des activités** | Protéger les opérations commerciales contre les **interruptions dues aux cyberattaques** |
| **Confiance des utilisateurs** | Renforcer la **confiance** des clients et des partenaires envers les systèmes et services |

---

## 5. Les cinq niveaux de sécurisation

| # | Niveau | Idée | Exemples du cours |
|---|---|---|---|
| 1 | **Sécurité physique** | Protéger **serveurs et équipements** contre les accès non autorisés | Contrôles d'accès, surveillance |
| 2 | **Sécurité réseau** | Sécuriser les **communications** | **Pare-feux, VPN, systèmes de détection d'intrusion** |
| 3 | **Sécurité des applications** | Appliquer de **bonnes pratiques de développement** | **Tests de sécurité**, mises à jour régulières |
| 4 | **Sécurité des données** | Protéger l'**information elle-même** | **Chiffrement**, **sauvegardes régulières**, **gestion des accès** |
| 5 | **Sécurité organisationnelle** | Agir sur l'**humain et les processus** | **Formation des employés**, **politiques de sécurité**, **audits réguliers** |

> 🔑 **Défense en profondeur** *(ajout)* : ces cinq niveaux se **complètent** ; si l'un est contourné, les autres limitent les dégâts. La sécurité est aussi forte que son **maillon le plus faible**.

---

## 6. Niveau 1 — Sécurité physique

### 6.1 Objectifs

1. **Protection des équipements** : prévenir le **vol**, le **sabotage** ou les **dommages** aux serveurs, ordinateurs et autres matériels.
2. **Intégrité des données** : éviter que les données soient **altérées ou supprimées** par un **accès physique non autorisé**.
3. **Continuité des services** : garder les systèmes **opérationnels** en cas de **menace physique** (incendie, inondation, etc.).

### 6.2 Éléments de la sécurité physique

**① Contrôles d'accès physiques**

| Moyen | Description |
|---|---|
| **Badges et cartes d'accès** | Cartes **magnétiques** ou badges **RFID** pour limiter l'accès aux zones sensibles (salles de serveurs) |
| **Biométrie** | **Empreintes digitales**, **reconnaissance faciale** : accès renforcé |
| **Clés physiques** | Pour les locaux sensibles, avec une **gestion stricte** des clés |

**② Surveillance et détection**

| Moyen | Description |
|---|---|
| **Vidéosurveillance** | Caméras **dans et autour** des installations : surveiller les accès et **dissuader** |
| **Détection de mouvement** | **Capteurs** qui alertent le personnel en cas d'**intrusion** |
| **Alarmes** | Se **déclenchent** lors d'accès non autorisés |

**③ à ⑥ Autres éléments (cités sans détail dans le support)**

| Élément | Explication *(ajout)* |
|---|---|
| **Barrières physiques** | Clôtures, portes renforcées, serrures, murs : **empêcher l'accès** |
| **Environnement physique contrôlé** | **Climatisation**, **détection d'incendie**, protection contre l'**humidité/inondation**, **onduleurs** |
| **Gestion des visiteurs** | **Registre**, **badges visiteurs**, **accompagnement** obligatoire |
| **Équipement de sécurité** | **Extincteurs**, **coffres-forts**, **armoires verrouillables**, câbles antivol |

---

## 7. Niveau 2 — Sécurité réseau

### 7.1 Objectifs

1. **Protection des données** : assurer **confidentialité et intégrité** des données transmises.
2. **Prévention des intrusions** : **bloquer les accès non autorisés** aux systèmes et informations.
3. **Maintien de la disponibilité** : garantir que les **services réseau** restent opérationnels.

### 7.2 Éléments de la sécurité réseau

**① Pare-feu (*firewall*)**
- **Fonctionnement :** **barrière** entre le **réseau interne** et l'**extérieur**, qui **filtre le trafic** selon des **règles de sécurité**.
- **Types :** **matériels**, **logiciels**, et **pare-feux de nouvelle génération (NGFW)** avec des fonctions avancées comme l'**inspection approfondie des paquets (DPI)**.

**② Systèmes de détection et de prévention d'intrusion (IDS/IPS)**

| | **IDS** (Detection) | **IPS** (Prevention) |
|---|---|---|
| **Rôle** | **Surveille** le trafic et **détecte** les activités suspectes ou malveillantes | **Détecte ET agit** |
| **Action** | **Alerte** | **Bloque** le trafic malveillant |

**③ VPN (Réseau Privé Virtuel)**
- **Fonctionnement :** **chiffre les connexions à distance** pour sécuriser les données échangées sur des **réseaux publics**.
- **Utilisation :** permet aux employés d'accéder aux **ressources de l'entreprise** de façon sécurisée **en déplacement**.

**④ Chiffrement**
- **Transmission sécurisée :** protocoles **SSL/TLS** pour chiffrer les données **en transit** (empêche l'**interception**).
- **Stockage sécurisé :** chiffrement des données **au repos** sur les serveurs.

**⑤ à ⑧ Autres éléments (cités sans détail dans le support)**

| Élément | Explication *(ajout)* |
|---|---|
| **Contrôle d'accès** | Qui peut accéder à quoi : **comptes, mots de passe, MFA, droits** |
| **Mise à jour et gestion des correctifs** | Appliquer les **patchs** pour corriger les vulnérabilités connues |
| **Segmentation du réseau** | Diviser le réseau en **zones** (VLAN, DMZ) pour **limiter la propagation** d'une attaque |
| **Surveillance et journalisation** | **Logs** et supervision pour détecter les anomalies et enquêter |

### 7.3 Importance de la sécurité réseau

| Enjeu | Explication |
|---|---|
| **Prévention des pertes de données** | Protège les informations critiques contre **fuites et accès non autorisés** |
| **Protection contre les cyberattaques** | Réduit les risques de **ransomwares, attaques DDoS, phishing** |
| **Conformité réglementaire** | Respect des exigences **légales et réglementaires** |
| **Confiance des clients** | Montre un **engagement envers la sécurité** |

---

## 8. Niveau 3 — Sécurité des applications

### 8.1 Définition
> La sécurité des applications concerne la **protection des logiciels et des applications** contre les **menaces et vulnérabilités** **tout au long de leur cycle de vie**. Elle vise à garantir que les applications **ne compromettent pas** la **confidentialité, l'intégrité et la disponibilité** des données qu'elles traitent.

### 8.2 Objectifs

1. **Protection des données sensibles** : informations traitées (surtout **données personnelles et financières**).
2. **Prévention des vulnérabilités** : **identifier et corriger** les failles **avant** qu'elles ne soient exploitées.
3. **Conformité aux réglementations** : lois et normes de protection des données.

### 8.3 Éléments

| Élément | Explication *(ajout)* |
|---|---|
| **Sécurisation du cycle de développement** | Intégrer la sécurité **dès la conception** (*Secure SDLC*, revues de code) |
| **Analyse de vulnérabilités et tests de sécurité** | **Scanners**, **tests de pénétration**, analyse statique/dynamique |
| **Contrôles d'accès et authentification** | Gérer **identités, rôles, droits**, mots de passe, MFA |
| **Chiffrement** | Protéger les données **stockées et transmises** |
| **Gestion des sessions** | Sessions **sécurisées** (expiration, jetons non devinables) |
| **Protection contre les menaces courantes** | Injection SQL, XSS, etc. (voir [section 15](#15-menace-4--les-exploits-de-vulnérabilité)) |
| **Logging et surveillance** | **Journaliser** les événements importants pour détecter les abus |

---

## 9. Niveau 4 — Sécurité des données

### 9.1 Définition
> La sécurité des données regroupe les **pratiques, technologies et politiques** pour protéger les données contre **accès non autorisés, modifications, destructions et fuites**. Elle concerne les données **stockées (au repos)** et les données **en transit (en mouvement)**.

### 9.2 Objectifs (triade CID)

1. **Confidentialité** · 2. **Intégrité** · 3. **Disponibilité** (voir [section 3](#3-les-principes-de-la-sécurité)).

### 9.3 Éléments

| Élément | Explication *(ajout)* |
|---|---|
| **Chiffrement** | Rendre les données **illisibles** sans clé |
| **Contrôles d'accès** | Limiter l'accès selon le **besoin d'en connaître** (principe du moindre privilège) |
| **Sauvegarde et restauration** | **Copies régulières**, testées, conservées **hors site** |
| **Surveillance et journalisation** | **Suivre** les accès aux données sensibles |
| **Sensibilisation et formation** | Former les utilisateurs aux bonnes pratiques |
| **Protection physique** | Sécuriser les **supports de stockage** et les locaux |

### 9.4 Importance

1. **Protection contre les violations de données** : réduit le risque d'**accès non autorisé et de fuites**.
2. **Réputation de l'organisation** : maintient la **confiance** des clients et partenaires.
3. **Réduction des pertes financières** : diminue les coûts liés aux violations (**amendes**, **frais de récupération**).
4. **Conformité aux exigences légales** : évite **sanctions et conséquences juridiques**.

---

## 10. Niveau 5 — Sécurité organisationnelle

### 10.1 Définition
> La sécurité organisationnelle désigne l'ensemble des **mesures, politiques et pratiques** mises en place dans une organisation pour protéger ses **actifs, informations et employés** contre les **menaces internes et externes**. Elle couvre les aspects **humains, procéduraux et techniques**.

### 10.2 Objectifs

1. **Protection des informations sensibles** : données critiques et confidentielles.
2. **Prévention des incidents de sécurité** : minimiser les incidents **intentionnels ou accidentels**.
3. **Culture de la sécurité** : chaque membre **comprend son rôle** dans la sécurité.

### 10.3 Éléments

| Élément | Explication *(ajout)* |
|---|---|
| **Politiques de sécurité** | Règles écrites : mots de passe, usage des équipements, etc. |
| **Sensibilisation et formation** | Réduire l'**erreur humaine** (phishing, mots de passe) |
| **Gestion des identités et des accès (IAM)** | Créer, modifier, révoquer les **comptes et droits** |
| **Surveillance et audit** | **Contrôles réguliers** de conformité |
| **Gestion des incidents** | **Procédures** pour réagir à un incident |
| **Gestion des risques** | **Identifier, évaluer, traiter** les risques (voir [section 18](#18-lévaluation-des-risques-en-détail)) |
| **Sécurité physique** | Lien avec le niveau 1 |

### 10.4 Importance

| Enjeu | Explication |
|---|---|
| **Protection des actifs** | Ressources **matérielles et immatérielles** |
| **Confiance des clients** | Engagement visible envers la sécurité |
| **Conformité légale** | Respect des **réglementations et normes** |
| **Prévention des pertes financières** | Moins d'**amendes** et de **pertes opérationnelles** |

---

## 11. Les menaces : vue d'ensemble

| # | Menace | Description |
|---|---|---|
| 1 | **Malware** | **Logiciels malveillants** : virus, vers, ransomware… qui endommagent les systèmes |
| 2 | **Phishing** | **Fraude** visant à obtenir des **informations sensibles** via des **e-mails trompeurs** |
| 3 | **Attaques DDoS** | **Surcharge** d'un système/réseau pour le **rendre indisponible** |
| 4 | **Exploits de vulnérabilité** | Utilisation de **failles de sécurité** dans les logiciels ou systèmes |
| 5 | **Menaces internes** | Risques venant d'**employés malveillants ou négligents** |

### Lien avec la triade CID *(ajout)*

| Menace | Principe principalement visé |
|---|---|
| Malware (spyware, trojan) | **Confidentialité** |
| Ransomware | **Disponibilité** (et intégrité) |
| Phishing | **Confidentialité** (vol d'identifiants) |
| DDoS | **Disponibilité** |
| Exploits (injection SQL…) | **Confidentialité / Intégrité** |
| Menace interne | **Les trois** |

---

## 12. Menace 1 — Les malwares

| # | Type | Description |
|---|---|---|
| 1 | **Virus** | S'**attache à des fichiers ou programmes légitimes** et **se propage quand ils sont exécutés** ; peut endommager les fichiers et ralentir le système |
| 2 | **Vers (*Worms*)** | **Se reproduit et se propage de façon autonome** à travers un réseau, souvent en **exploitant des vulnérabilités** |
| 3 | **Chevaux de Troie (*Trojan*)** | **Se déguise en logiciel légitime** pour pousser à l'installation ; une fois installé, peut **donner accès aux attaquants** |
| 4 | **Ransomware** | **Chiffre les données** de l'utilisateur et **exige une rançon** pour la clé de déchiffrement ; peut **paralyser les entreprises** |
| 5 | **Spyware** | **Surveille l'activité** de l'utilisateur **sans son consentement** (identifiants, habitudes de navigation…) |
| 6 | **Adware** | Pas toujours malveillant : affiche des **publicités intrusives** et peut **suivre la navigation** pour cibler les annonces |
| 7 | **Rootkits** | **Cachent la présence** d'autres malwares et permettent à l'attaquant d'accéder au système **discrètement** |
| 8 | **Botnets** | **Réseau de machines infectées**, **contrôlées à distance**, utilisé pour des attaques comme le **DDoS** |

### Différence virus / ver / cheval de Troie

| | Virus | Ver | Cheval de Troie |
|---|---|---|---|
| **Besoin d'un hôte** | ✅ Oui (fichier/programme) | ❌ Non (**autonome**) | Se fait passer pour un programme utile |
| **Propagation** | Quand le fichier est **exécuté** | **Seul**, via le **réseau** | **Ne se réplique pas** : l'utilisateur l'installe |
| **Danger** | Dégâts sur fichiers | Surcharge, propagation rapide | **Porte dérobée** pour l'attaquant |

---

## 13. Menace 2 — Le phishing

> **Phishing (hameçonnage)** : technique de **fraude** qui consiste à **tromper** la victime pour obtenir des **informations sensibles** ou la pousser à agir.

### 13.1 Les six types de phishing

| # | Type | Description | Exemple du cours |
|---|---|---|---|
| 1 | **Phishing par e-mail** | E-mails qui semblent venir d'une **source fiable** (banque, entreprise) pour inciter à cliquer sur un **lien malveillant** ou à fournir des informations | E-mail « de votre banque » demandant de **vérifier votre compte** via un lien |
| 2 | **Spear phishing** | Phishing **ciblé** : message **personnalisé** (infos récupérées sur les **réseaux sociaux**) | E-mail d'un **collègue** demandant de **valider une facture** |
| 3 | **Whaling** | Variante du spear phishing ciblant des **cadres supérieurs** (*C-suite*) | Faux e-mail d'un **dirigeant** demandant une **action urgente sur des fonds** |
| 4 | **Smishing** (par SMS) | **Messages texte** pour pousser à cliquer ou à donner des informations | SMS « de votre opérateur » demandant de **confirmer vos informations** |
| 5 | **Vishing** (vocal) | **Ingénierie sociale par téléphone** | Appel d'un faux **support technique** demandant vos **identifiants** |
| 6 | **Phishing sur réseaux sociaux** | **Faux profils** ou messages pour obtenir des informations ou faire cliquer | Message direct d'un **faux compte d'entreprise** demandant de **vérifier votre compte** |

> 💡 **Moyen mnémotechnique :** **Smishing = SMS** · **Vishing = Voice** · **Whaling = baleine** (grosse cible) · **Spear = lance** (attaque précise).

### 13.2 Comment reconnaître une tentative de phishing

| # | Indice | Explication |
|---|---|---|
| 1 | **Adresse e-mail suspecte** | Vérifier l'expéditeur : adresses **similaires mais légèrement différentes** |
| 2 | **Langage d'urgence** | Créer un sentiment d'**urgence** pour agir **sans réfléchir** |
| 3 | **Liens suspects** | **Survoler** les liens avant de cliquer pour voir la **vraie destination** |
| 4 | **Demandes d'informations personnelles** | Méfiance si on demande des **informations sensibles** par e-mail ou message |
| 5 | **Fautes d'orthographe ou de grammaire** | Fréquentes dans les messages de phishing |

### 13.3 Protection contre le phishing

| # | Mesure | Détail |
|---|---|---|
| 1 | **Vérification des sources** | Ne pas cliquer sur des liens d'expéditeurs inconnus ; **vérifier sur le site officiel** |
| 2 | **Outils de sécurité** | **Antivirus** et **filtres anti-phishing** |
| 3 | **Mise à jour régulière** | OS, logiciels et applications **à jour** |
| 4 | **Sensibilisation et formation** | S'éduquer et former ses collègues |
| 5 | **Authentification multifacteur (MFA)** | **Couche supplémentaire** : même avec le mot de passe volé, l'accès reste difficile |

---

## 14. Menace 3 — Les attaques DDoS

### 14.1 Définition
> **DDoS (*Distributed Denial of Service*)** : tentative malveillante de **rendre un service, site web ou ressource indisponible** en le **surchargeant de trafic** provenant de **plusieurs sources**. Généralement via un **réseau d'ordinateurs compromis** appelé **botnet**, qui envoie **simultanément** un grand nombre de requêtes.

### 14.2 Fonctionnement

| Étape | Description |
|---|---|
| **1. Botnet** | Ensemble d'appareils (ordinateurs, serveurs, **IoT**) **contrôlés par un attaquant**, souvent **infectés par un malware**, agissant de façon **coordonnée** |
| **2. Lancement** | L'attaquant envoie une **commande** : tous les appareils envoient des requêtes à la **cible** (**HTTP**, **ping ICMP**, **requêtes DNS**…) |
| **3. Surcharge** | La cible reçoit **tellement de trafic** qu'elle **ne peut plus répondre** aux requêtes **légitimes** → **interruption de service** |

```
   Attaquant ──commande──► [ Botnet : PC, serveurs, objets IoT … ]
                                  │ │ │ │ │ │
                                  ▼ ▼ ▼ ▼ ▼ ▼    (trafic massif)
                               ┌───────────┐
                               │   CIBLE   │ ✗ ne répond plus aux utilisateurs légitimes
                               └───────────┘
```

### 14.3 Types d'attaques DDoS

| Type | Objectif | Exemples |
|---|---|---|
| **Volumétriques** | **Saturer la bande passante** de la cible avec un grand volume de données | **UDP Flood**, **ICMP Flood** |
| **Par amplification** | Exploiter des **protocoles mal configurés** pour générer un **trafic énorme à partir d'une petite requête** | **DNS amplification**, **NTP amplification** |
| **De protocole** | **Épuiser les ressources des serveurs** en exploitant des **failles de traitement** des protocoles | **SYN Flood**, **Slowloris** |
| **Applicatives** | Perturber des **applications spécifiques** avec des requêtes conçues pour les **submerger** | Requêtes HTTP massives *(ajout)* |

### 14.4 Conséquences

| Conséquence | Explication |
|---|---|
| **Indisponibilité du service** | Utilisateurs légitimes **sans accès** → **perte de revenus** |
| **Dommages à la réputation** | **Perte de confiance** des clients |
| **Coûts financiers** | Investissements en **solutions de mitigation** et de **réponse aux incidents** |

> 💡 *(Ajout)* **Défenses courantes :** **pare-feu / filtrage**, **limitation de débit** (*rate limiting*), **CDN** et services **anti-DDoS** (« scrubbing »), **répartition de charge**, **sur-dimensionnement** de la bande passante.

---

## 15. Menace 4 — Les exploits de vulnérabilité

### 15.1 Définition
> Un **exploit** est un **morceau de code ou un ensemble de techniques** qui tire parti d'une **faille (vulnérabilité)** dans un **logiciel, un système d'exploitation ou un matériel**. Il permet d'exécuter des **actions non autorisées** : **accès non autorisé**, **exécution de code malveillant**, **vol d'informations**.

### 15.2 Types de vulnérabilités

| Vulnérabilité | Description |
|---|---|
| **Débordement de tampon** (*buffer overflow*) | Un programme **écrit plus de données qu'un tampon ne peut en contenir**, permettant d'**écraser des données adjacentes** en mémoire |
| **Injection SQL** | L'attaquant **interfère avec les requêtes SQL** de l'application → accès à des **données non autorisées** |
| **Cross-Site Scripting (XSS)** | Injection de **scripts malveillants** dans des **pages web vues par d'autres utilisateurs** |
| **Vulnérabilités de configuration** | **Mauvaise configuration** : **mots de passe par défaut** non changés, **permissions excessives** |
| **Zero-day** | Vulnérabilité **inconnue du développeur** au moment de son exploitation : **très ciblée et très dangereuse** |

#### Exemple d'injection SQL et de protection *(ajout)*

```sql
-- Requête construite par concaténation (VULNÉRABLE) :
SELECT * FROM users WHERE login = '$login' AND pwd = '$pwd';
-- Si l'attaquant saisit  ' OR '1'='1  comme login, la condition devient toujours vraie.
```
```java
// Protection : requête PARAMÉTRÉE (PreparedStatement en Java)
PreparedStatement ps = cnx.prepareStatement(
    "SELECT * FROM users WHERE login = ? AND pwd = ?");
ps.setString(1, login);
ps.setString(2, pwd);
ResultSet rs = ps.executeQuery();
```
*(Les entrées de l'utilisateur sont traitées comme des **données** et jamais comme du **code SQL**.)*

### 15.3 Fonctionnement d'un exploit (4 étapes)

| Étape | Description |
|---|---|
| **1. Recherche de vulnérabilités** | Identifier les failles avec des **outils de scan** ou en **analysant le code** |
| **2. Développement de l'exploit** | Créer le **code malveillant** qui tire parti de la faille |
| **3. Exécution de l'exploit** | Lancer contre la cible : **paquets malformés**, **injection de code**, **ingénierie sociale** |
| **4. Prise de contrôle / extraction** | **Accès non autorisé**, **commandes arbitraires**, **vol d'informations** |

### 15.4 Conséquences

| Conséquence | Explication |
|---|---|
| **Accès non autorisé** | Accès **complet** aux systèmes |
| **Perte de données** | Données **corrompues ou volées** → pertes financières, atteinte à la réputation |
| **Interruption de service** | **Défaillances système**, services indisponibles |
| **Propagation de malware** | Installation de **malwares** → risques de **cyberattaques à grande échelle** |

### 15.5 Prévention et protection

| Mesure | Détail |
|---|---|
| **Mises à jour régulières** | Corriger les **vulnérabilités connues** |
| **Audits de sécurité** | **Identifier et corriger** les vulnérabilités |
| **Tests de pénétration** | Évaluer la sécurité contre des **exploits potentiels** |
| **Formation des utilisateurs** | Sensibiliser à l'**ingénierie sociale** et aux bonnes pratiques |

---

## 16. Menace 5 — Les menaces internes

Le support cite cette menace dans la liste des menaces (section 11) sans diapositives dédiées :

> **Menaces internes** : risques provenant d'**employés malveillants ou négligents** qui compromettent la sécurité.

| Type *(ajout)* | Exemple |
|---|---|
| **Malveillant** | Employé mécontent qui **vole ou détruit** des données |
| **Négligent** | Employé qui **clique sur un lien de phishing**, perd un support non chiffré, partage son mot de passe |

> 🔑 **Parades :** **gestion des identités et des accès (IAM)**, **moindre privilège**, **journalisation et audit**, **formation** (voir niveau 5 — sécurité organisationnelle).

---

## 17. Le cycle de la sécurité

Le cycle de la sécurité informatique comporte **5 étapes** :

| # | Étape | Description |
|---|---|---|
| 1 | **Évaluation des risques** | **Identifier et évaluer** les vulnérabilités et menaces potentielles |
| 2 | **Mise en place de mesures de sécurité** | **Implémenter des contrôles** pour réduire les risques (**pare-feu, antivirus, formations**) |
| 3 | **Surveillance** | **Surveiller en continu** systèmes et réseaux pour **détecter les incidents** |
| 4 | **Réponse aux incidents** | Avoir un **plan d'action** pour **réagir rapidement** en cas de violation |
| 5 | **Révision et amélioration** | **Analyser les incidents** pour en tirer des **leçons** et **améliorer** politiques et procédures |

```
      ┌──────────────────────────────────────────────┐
      ▼                                              │
 ① Évaluation ─► ② Mesures ─► ③ Surveillance ─► ④ Réponse ─► ⑤ Révision
   des risques    de sécurité                    aux incidents   et amélioration
```

> 🔑 **Un cycle, pas une ligne droite :** la sécurité est un **processus continu** (proche de la roue de Deming *PDCA*). Après l'étape 5, on **recommence** l'évaluation des risques *(ajout : analogie PDCA)*.

---

## 18. L'évaluation des risques en détail

Le cours détaille l'étape ① en **7 sous-étapes** (méthode structurée) :

### 18.1 Identification des actifs
- **Cataloguer** les actifs : **matériels, logiciels, données, services**.
- **Évaluer la valeur** de chaque actif selon la **confidentialité, l'intégrité et la disponibilité**.

### 18.2 Identification des menaces
- **Catégoriser** les menaces :

| Catégorie | Exemples |
|---|---|
| **Naturelles** | **Inondations, tremblements de terre, incendies** |
| **Humaines** | **Cyberattaques, erreurs humaines, sabotage** |
| **Technologiques** | **Pannes de matériel, défaillances de logiciels** |

- **Exemples spécifiques :** attaques **DDoS**, **malware** (virus, ransomware), **vol de données**.

### 18.3 Identification des vulnérabilités
- **Analyser les systèmes** pour trouver les failles potentielles.
- Utiliser des **outils de scan de vulnérabilités** (logiciels, configurations).
- **Réviser les politiques de sécurité** : lacunes dans les politiques et procédures.

### 18.4 Évaluation des risques

**Probabilité** (est-ce que cela va se produire ?)

| Niveau | Critère |
|---|---|
| **Élevée** | **Fréquence élevée** d'incidents similaires dans le passé |
| **Moyenne** | **Quelques incidents** passés, mais pas fréquents |
| **Faible** | **Très peu ou pas** d'incidents passés |

**Impact** (quelles seraient les conséquences ?)

| Niveau | Critère |
|---|---|
| **Critique** | **Perturbation majeure** des opérations, **perte significative de données** |
| **Élevé** | **Dommages financiers notables**, atteinte à la réputation |
| **Modéré** | **Perturbation limitée**, impacts **gérables** |

### 18.5 Priorisation des risques
- Utiliser des **matrices de risque** qui **croisent probabilité et impact**.
- **Réévaluation continue** à mesure que l'environnement et les menaces évoluent.

**Exemple de matrice de risque** *(ajout)* :

| Probabilité \ Impact | **Modéré** | **Élevé** | **Critique** |
|---|---|---|---|
| **Élevée** | 🟠 Moyen | 🔴 Élevé | 🔴 **Très élevé** |
| **Moyenne** | 🟡 Faible | 🟠 Moyen | 🔴 Élevé |
| **Faible** | 🟢 Très faible | 🟡 Faible | 🟠 Moyen |

> 💡 **Risque = Probabilité × Impact** (ou : menace × vulnérabilité × valeur de l'actif). On traite **d'abord** les risques **les plus élevés**.

### 18.6 Mise en œuvre des mesures de mitigation

| Stratégie | Exemples |
|---|---|
| **Contrôles techniques** | **Pare-feu, antivirus, mises à jour de sécurité** |
| **Contrôles organisationnels** | **Politiques de sécurité**, **formations** des employés |
| **Plan de réponse aux incidents** | **Plan d'action** préparé à l'avance |

### 18.7 Suivi et révision
- **Surveillance continue** : systèmes de surveillance pour détecter les menaces **en temps réel**.
- **Révision périodique** : **mettre à jour** l'évaluation des risques et les mesures de sécurité selon les changements ou **nouvelles menaces**.

### 18.8 Schéma récapitulatif

```
Actifs ─► Menaces ─► Vulnérabilités ─► Évaluation (probabilité × impact)
                                              │
                                              ▼
                              Priorisation (matrice de risque)
                                              │
                                              ▼
                        Mitigation (technique / organisationnelle / plan d'incident)
                                              │
                                              ▼
                               Suivi et révision (retour à l'étape 1)
```

---

## 19. Remarques sur le support de cours

1. **Métadonnées du PDF** : le fichier porte le titre interne « L'algèbre de Boole et les fonctions logiques » (reste d'un ancien document) ; le **contenu** est bien le chapitre 1 de **sécurité informatique**.
2. **Cinq principes, trois piliers** : le cours liste **5 principes** (confidentialité, intégrité, disponibilité, authentification, traçabilité) ; la **triade classique CID/CIA** n'en comprend que **3**. L'authentification et la traçabilité complètent le modèle (on parle parfois de **AAA** : *Authentication, Authorization, Accounting*).
3. **Éléments sans détail** : plusieurs listes sont données **sans explication** dans le support (barrières physiques, contrôle d'accès, segmentation, etc.). Les explications fournies dans ce résumé sont marquées **« ajout »**.
4. **Menaces internes** : citées comme **5ᵉ menace** mais **sans diapositives dédiées**.
5. **Cycle en 5 étapes vs évaluation en 7 sous-étapes** : le cycle (5 étapes) **inclut** une évaluation des risques elle-même découpée en **7 sous-étapes** (actifs → suivi) ; la dernière sous-étape « Suivi et révision » **recoupe** les étapes 3 et 5 du cycle.
6. **Redondances** : « sécurité physique » apparaît comme **niveau 1** et aussi comme **élément** des niveaux 4 et 5 ; « sensibilisation et formation » revient dans plusieurs niveaux (c'est volontaire : **le facteur humain** est transversal).
7. **Phishing — fautes d'orthographe** : c'est un **indice** utile mais **de moins en moins fiable** (les attaques modernes, parfois générées par IA, sont bien rédigées) *(remarque ajoutée)*.
8. **Pas de chiffres ni de normes** : le chapitre est **général** ; les normes (ISO 27001, RGPD…) ne sont **pas détaillées** ici.

---

## 20. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Sécurité informatique** | Mesures et pratiques de **protection** des systèmes, réseaux, données contre accès non autorisés, cyberattaques, pertes, dommages |
| **5 principes** | **Confidentialité · Intégrité · Disponibilité** (CID) + **Authentification · Traçabilité** |
| **Nécessité** | **Protéger les données** · **conformité légale** · **continuité des activités** · **confiance** |
| **5 niveaux** | **Physique · Réseau · Applications · Données · Organisationnelle** |
| **Sécurité physique** | Badges, **biométrie**, clés, **vidéosurveillance**, détection de mouvement, alarmes |
| **Sécurité réseau** | **Pare-feu** (NGFW/DPI), **IDS/IPS**, **VPN**, **SSL/TLS**, segmentation, correctifs |
| **IDS vs IPS** | IDS : **détecte** · IPS : **détecte et bloque** |
| **Sécurité des applications** | Protection **tout au long du cycle de vie** : développement sécurisé, tests, authentification, sessions |
| **Sécurité des données** | **Chiffrement**, contrôles d'accès, **sauvegardes**, données **au repos** et **en transit** |
| **Sécurité organisationnelle** | **Politiques**, formation, **IAM**, audit, gestion des incidents et des risques |
| **5 menaces** | **Malware · Phishing · DDoS · Exploits · Menaces internes** |
| **8 malwares** | Virus · Ver · Trojan · **Ransomware** · Spyware · Adware · Rootkit · Botnet |
| **6 phishings** | E-mail · **Spear** · **Whaling** · **Smishing** (SMS) · **Vishing** (voix) · Réseaux sociaux |
| **Reconnaître le phishing** | Adresse suspecte · urgence · liens suspects · demandes d'infos · fautes |
| **Se protéger du phishing** | Vérifier les sources · antivirus/filtres · mises à jour · formation · **MFA** |
| **DDoS** | Surcharge par un **botnet** → **indisponibilité** |
| **4 types de DDoS** | **Volumétrique** · **Amplification** · **Protocole** (SYN flood) · **Applicatif** |
| **Exploit** | Code/technique qui **exploite une faille** |
| **Vulnérabilités** | **Buffer overflow · Injection SQL · XSS · Configuration · Zero-day** |
| **4 étapes d'un exploit** | Recherche → Développement → Exécution → Prise de contrôle |
| **Prévention des exploits** | **Mises à jour · audits · tests de pénétration · formation** |
| **Cycle de la sécurité** | **Évaluation → Mesures → Surveillance → Réponse → Révision** |
| **Évaluation des risques** | Actifs → Menaces → Vulnérabilités → **Probabilité × Impact** → Priorisation → Mitigation → Suivi |
| **Probabilité** | **Élevée / Moyenne / Faible** |
| **Impact** | **Critique / Élevé / Modéré** |
| **Menaces par nature** | **Naturelles · Humaines · Technologiques** |

### Questions d'examen typiques
1. **Définir la sécurité informatique** et citer ses **cinq principes**.
2. Expliquer la **triade CID** avec un exemple de violation pour chaque principe.
3. Pourquoi la sécurité est-elle **nécessaire** ? Citer quatre raisons.
4. Citer les **cinq niveaux de sécurisation** et un exemple de mesure pour chacun.
5. Quels sont les **éléments de la sécurité physique** ? (accès, surveillance, barrières…)
6. Quelle est la **différence entre IDS et IPS** ? Quel est le rôle d'un **pare-feu** et d'un **VPN** ?
7. **Définir et distinguer** virus, ver, cheval de Troie, ransomware, spyware, rootkit, botnet.
8. Citer les **types de phishing** (spear, whaling, smishing, vishing…) et **comment s'en protéger**.
9. Expliquer le **fonctionnement d'une attaque DDoS** et citer ses **quatre types** avec exemples.
10. Qu'est-ce qu'un **exploit** ? Citer cinq **types de vulnérabilités** (injection SQL, XSS, zero-day…).
11. Décrire les **quatre étapes** d'un exploit et les **mesures de prévention**.
12. Décrire le **cycle de la sécurité** (5 étapes).
13. Expliquer la **méthode d'évaluation des risques** : actifs, menaces, vulnérabilités, **probabilité, impact**, priorisation.
14. Construire une **matrice de risque** et expliquer comment prioriser les risques.

---

*Fin du résumé — Chapitre 1 : Aspects généraux de la sécurité informatique.*
