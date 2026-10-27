# Chapitre 2 — Politique de sécurité
**Établissement :** Institut Supérieur d'Informatique du Kef
**Enseignante :** Mounira Laabidi
**Public :** 3LGL (3ᵉ année Licence)
**Type de document :** Résumé d'étude avec explications, tableaux comparatifs et conseils d'examen

---

## Table des matières

1. [Plan du chapitre](#1-plan-du-chapitre)
2. [Définition](#2-définition)
3. [Objectifs](#3-objectifs)
4. [Étendue](#4-étendue)
5. [Implémentation](#5-implémentation)
6. [Domaine d'application et domaines de responsabilité](#6-domaine-dapplication-et-domaines-de-responsabilité)
7. [Périodicité](#7-périodicité)
8. [Les types de politiques de sécurité](#8-les-types-de-politiques-de-sécurité)
9. [Mise en place d'une politique de sécurité (8 étapes)](#9-mise-en-place-dune-politique-de-sécurité-8-étapes)
10. [Quelques normes](#10-quelques-normes)
11. [Structure type d'une politique de sécurité (ajout)](#11-structure-type-dune-politique-de-sécurité-ajout)
12. [Remarques sur le support de cours](#12-remarques-sur-le-support-de-cours)
13. [Fiche de révision rapide](#13-fiche-de-révision-rapide)

---

## 1. Plan du chapitre

1. **Définition, objectif, étendue, implémentation, domaine d'application, domaines de responsabilité, périodicité**
2. **Les types de politique de sécurité**
3. **Mise en place d'une politique de sécurité**
4. **Quelques normes**

> 🔗 **Lien avec le chapitre 1 :** le chapitre 1 présentait les **menaces** et le **cycle de la sécurité**. Ce chapitre répond à la question : **comment une organisation formalise-t-elle sa défense ?** → par une **politique de sécurité**.

---

## 2. Définition

> La **politique de sécurité** est un **ensemble de principes et de règles** définissant **la manière dont une organisation protège ses actifs informationnels** contre les **menaces** et les **vulnérabilités**.
> Elle sert de **cadre** pour **orienter les actions et décisions** en matière de sécurité.

| Mot clé | Signification |
|---|---|
| **Principes et règles** | Ce qui est **attendu** et **interdit** |
| **Actifs informationnels** | Données, systèmes, réseaux, matériels, applications |
| **Cadre** | Référence commune pour **décider** et **agir** |

> 💡 *(Ajout)* Une politique de sécurité est un **document de référence** approuvé par la **direction** : il dit **QUOI** protéger et **POURQUOI** ; les **procédures** détaillent **COMMENT** le faire.

---

## 3. Objectifs

> **Objectif principal :** garantir la **confidentialité**, l'**intégrité** et la **disponibilité** des informations. Cela inclut la **protection contre** les **accès non autorisés**, les **pertes de données** et les **cyberattaques**.

Les cinq principes (déjà vus au chapitre 1) :

| # | Principe | Définition |
|---|---|---|
| 1 | **Confidentialité** | Seules les **personnes autorisées** accèdent aux informations |
| 2 | **Intégrité** | Données **ni modifiées ni altérées** sans autorisation |
| 3 | **Disponibilité** | Informations et systèmes **accessibles et utilisables** quand nécessaire |
| 4 | **Authentification** | **Vérifier l'identité** des utilisateurs et systèmes avant l'accès |
| 5 | **Traçabilité** | **Enregistrer et surveiller** les actions pour **détecter les anomalies** |

```
              Confidentialité
                   ▲
                  ╱ ╲
              Intégrité ─ Disponibilité        (triade CID)
```

---

## 4. Étendue

La politique de sécurité **couvre tous les systèmes d'information** de l'organisation :

| Domaine couvert | Exemples *(ajout)* |
|---|---|
| **Les données** | Fichiers, bases de données, sauvegardes |
| **Les infrastructures** | Serveurs, réseaux, postes de travail, locaux |
| **Les utilisateurs** | Employés, administrateurs, stagiaires |

> ⚠️ Elle doit **également prendre en compte les tiers** ayant accès aux informations de l'organisation (prestataires, sous-traitants, partenaires, fournisseurs de cloud…).

---

## 5. Implémentation

L'implémentation de la politique de sécurité nécessite **quatre volets** :

| # | Volet | Description | Exemples |
|---|---|---|---|
| 1 | **Analyse des risques** | **Identifier** les menaces potentielles et les vulnérabilités | Inventaire des actifs, scan de vulnérabilités |
| 2 | **Formation et sensibilisation** | **Éduquer** les employés sur les pratiques de sécurité | Sessions de formation, campagnes anti-phishing |
| 3 | **Mesures techniques** | Mettre en place des **contrôles techniques** | **Pare-feu, antivirus, cryptage** |
| 4 | **Procédures et protocoles** | Établir des **processus clairs** pour la **gestion des incidents** de sécurité | Procédure de réponse à un incident |

> 🔑 **Trois dimensions** *(lecture)* : **analyse** (comprendre les risques) → **humain** (formation) → **technique** (outils) → **organisationnel** (procédures).

---

## 6. Domaine d'application et domaines de responsabilité

### 6.1 Domaine d'application

La politique s'applique à :
- **tous les employés**,
- **les contractuels**,
- **les partenaires**

ayant accès aux **systèmes d'information**. Elle couvre aussi **tous les aspects des opérations**, des **technologies utilisées** et des **pratiques de gestion des données**.

### 6.2 Domaines de responsabilité

| Acteur | Responsabilité |
|---|---|
| **Direction** | **Élaboration et approbation** de la politique |
| **Responsable de la sécurité** (RSSI) | **Mise en œuvre** et **gestion quotidienne** des mesures de sécurité |
| **Employés** | **Respect des règles** et **participation à la sensibilisation** |
| **DPO** (Délégué à la protection des données) | Veiller à la **conformité** avec les **réglementations sur la protection des données** |

```
 Direction ──approuve──► Politique ──► RSSI ──met en œuvre──► Employés ──respectent──►
                                         │
                                         └── DPO : conformité réglementaire (données personnelles)
```

> 💡 *(Ajout)* **RSSI** = *Responsable de la Sécurité des Systèmes d'Information* (en anglais **CISO**). Le soutien de la **direction** est la condition n°1 de réussite : sans engagement du sommet, la politique reste lettre morte.

---

## 7. Périodicité

- La politique doit être **revue et mise à jour régulièrement**, **idéalement une fois par an**, ou **plus fréquemment** en cas de **changement significatif** dans l'environnement :
  - **technologique**,
  - **réglementaire**,
  - **opérationnel**.
- Des **audits réguliers** permettent d'assurer la **conformité** et l'**efficacité** des mesures en place.

| Déclencheur de révision | Exemples *(ajout)* |
|---|---|
| **Technologique** | Passage au **cloud**, nouvelle application, nouvelle menace |
| **Réglementaire** | Nouvelle **loi** ou **norme** |
| **Opérationnel** | **Fusion**, nouveau site, télétravail |
| **Incident majeur** | Violation de données, ransomware |

---

## 8. Les types de politiques de sécurité

Le cours présente **9 types** de politiques :

| # | Politique | Objet | Contenu typique |
|---|---|---|---|
| 1 | **Sécurité de l'information** | **Gestion et protection des informations** dans l'organisation | **Classification des données**, **contrôles d'accès**, **gestion des incidents** |
| 2 | **Gestion des accès** | **Qui** a accès à **quelles ressources** et **sous quelles conditions** | **Authentification**, **gestion des mots de passe**, **révocation des accès** |
| 3 | **Sécurité des réseaux** | Protéger les **infrastructures réseau** contre **intrusions et attaques** | **Pare-feu, IDS, VPN** |
| 4 | **Sécurité physique** | **Sécurité des locaux et équipements** | **Contrôle d'accès physique, vidéosurveillance, protection contre les catastrophes** |
| 5 | **Sécurité des applications** | **Normes de développement et d'utilisation** des logiciels pour qu'ils soient **sécurisés** et sans vulnérabilités | Développement sécurisé, tests |
| 6 | **Continuité d'activité** | **Plans et procédures** en cas de **sinistre** ou d'**incident majeur** pour **assurer la continuité** et la **récupération des données** | PCA / PRA *(ajout)* |
| 7 | **Gestion des incidents** | **Étapes** de **détection, analyse et réponse** à un incident ; **minimiser les impacts** et **apprendre** des incidents | Procédure d'incident, retour d'expérience |
| 8 | **Sensibilisation et formation** | **Former** les employés aux **risques** et **meilleures pratiques** | Programmes de formation |
| 9 | **Gestion des données** | **Collecte, stockage, traitement et destruction** des données, en respectant la **protection des données personnelles** | Durées de conservation, suppression sécurisée |

> 📌 *« Ces politiques peuvent être **adaptées** en fonction des **besoins spécifiques** et des **exigences réglementaires** de chaque organisation. »*

### Lien avec les niveaux de sécurisation du chapitre 1 *(ajout)*

| Niveau (chap. 1) | Politique correspondante (chap. 2) |
|---|---|
| Sécurité **physique** | Politique de sécurité **physique** |
| Sécurité **réseau** | Politique de sécurité des **réseaux** |
| Sécurité des **applications** | Politique de sécurité des **applications** |
| Sécurité des **données** | Politique de **gestion des données** · de sécurité de l'**information** |
| Sécurité **organisationnelle** | **Gestion des accès** · **sensibilisation** · **incidents** · **continuité** |

> 💡 **Astuce :** PCA = *Plan de Continuité d'Activité* (garder l'activité **pendant** la crise) ; PRA = *Plan de Reprise d'Activité* (**redémarrer** les systèmes **après** la crise).

---

## 9. Mise en place d'une politique de sécurité (8 étapes)

> La mise en place d'une politique efficace est un **processus structuré** en **8 étapes clés**.

### Étape 1 — Évaluation des besoins
| Action | Description |
|---|---|
| **Analyse des actifs** | Identifier les **actifs informationnels** (données, systèmes, réseaux) et **évaluer leur valeur** |
| **Identification des menaces et vulnérabilités** | Analyser les **menaces potentielles** et les **faiblesses existantes** |

### Étape 2 — Définition des objectifs de sécurité
- Déterminer les objectifs de **confidentialité, intégrité et disponibilité**.
- **Établir des priorités** selon les résultats de l'**analyse des risques**.

### Étape 3 — Rédaction de la politique
- **Élaboration du contenu :** définir les **règles**, les **responsabilités** et les **procédures**. Inclure les sections sur :
  - la **gestion des accès**,
  - la **sécurité physique**,
  - la **sécurité des réseaux et des applications**,
  - la **gestion des incidents**.
- **Langage clair et concis :** accessible pour être **compris par tous**.

### Étape 4 — Validation et approbation
- **Soumettre le projet** aux **parties prenantes** (direction, responsables de la sécurité, DPO).
- **Apporter des modifications** selon les retours.

### Étape 5 — Communication et sensibilisation
- **Diffusion :** communiquer la politique à **tous les employés** et parties concernées.
- **Formation :** organiser des **sessions de formation**.

### Étape 6 — Mise en œuvre
- **Application des mesures :** mettre en place les **contrôles techniques** (pare-feu, authentification) et les **procédures** décrites.
- **Ressources nécessaires :** s'assurer que les **ressources humaines et technologiques** sont en place.

### Étape 7 — Surveillance et évaluation
- **Suivi des incidents :** évaluer régulièrement les **incidents** et la **conformité** à la politique.
- **Audits :** réaliser des **audits réguliers** pour identifier **lacunes** et **axes d'amélioration**.

### Étape 8 — Révision et mise à jour
- **Périodicité :** révision **annuelle** ou lors de **changements significatifs**.
- **Feedback continu :** encourager les **retours des employés**.

### Schéma récapitulatif

```
① Évaluation des besoins ─► ② Objectifs de sécurité ─► ③ Rédaction ─► ④ Validation
        ▲                                                                     │
        │                                                                     ▼
⑧ Révision et mise à jour ◄─ ⑦ Surveillance et évaluation ◄─ ⑥ Mise en œuvre ◄─ ⑤ Communication
                                                                                    et sensibilisation
```

| Phase | Étapes |
|---|---|
| **Préparer** (comprendre) | ① Besoins · ② Objectifs |
| **Écrire et valider** | ③ Rédaction · ④ Validation |
| **Déployer** | ⑤ Communication · ⑥ Mise en œuvre |
| **Contrôler et améliorer** | ⑦ Surveillance · ⑧ Révision |

> 🔑 **Conclusion du cours :** *« En suivant ces étapes, une organisation peut établir une **politique de sécurité robuste** qui **protège efficacement ses actifs** tout en étant **adaptable aux évolutions** des menaces et des technologies. »*
> 💡 *(Ajout)* Ces 8 étapes suivent la logique **PDCA** (Planifier — Faire — Vérifier — Agir) de la **roue de Deming** : étapes 1-4 = **Plan**, 5-6 = **Do**, 7 = **Check**, 8 = **Act**.

---

## 10. Quelques normes

Le cours présente **8 normes et référentiels** importants :

| # | Norme / référentiel | Nature | Objet |
|---|---|---|---|
| 1 | **ISO/IEC 27001** | Norme (système de management) | Cadre pour **établir, mettre en œuvre, maintenir et améliorer** un **SMSI** (*Système de Management de la Sécurité de l'Information*) : protéger les informations de manière **systématique et efficace** |
| 2 | **ISO/IEC 27002** | Guide de bonnes pratiques | **Complémentaire à la 27001** : **lignes directrices** et **meilleures pratiques de contrôle** ; **détaille les mesures de sécurité** que les organisations peuvent adopter |
| 3 | **NIST SP 800-53** | Catalogue de contrôles (USA) | **Contrôles de sécurité** pour les **systèmes d'information fédéraux américains** ; souvent **adopté par d'autres organisations** pour sa robustesse |
| 4 | **PCI DSS** (*Payment Card Industry Data Security Standard*) | Standard sectoriel | **Sécurité des données des cartes de paiement** : exigences pour **protéger les informations des cartes de crédit** et sécuriser les transactions |
| 5 | **GDPR / RGPD** (*General Data Protection Regulation*) | **Loi** (pas une norme technique) | **Obligations légales** de protection des **données personnelles en Europe** ; influence les politiques de sécurité |
| 6 | **CIS Controls** | Bonnes pratiques | Série de **meilleures pratiques de cybersécurité** : **recommandations concrètes** pour améliorer la sécurité |
| 7 | **ISO 22301** | Norme | **Continuité des activités** : établir, mettre en œuvre et maintenir des systèmes pour **assurer la continuité en cas d'incident** |
| 8 | **COBIT** (*Control Objectives for Information and Related Technologies*) | Cadre de gouvernance | **Gouvernance et management des TI** : atteindre les **objectifs de sécurité** en **alignant IT et business** |

> *Ces normes offrent des **lignes directrices et des meilleures pratiques** pour **renforcer la posture de sécurité** et **se conformer aux exigences réglementaires**.*

### Ce qu'il faut retenir sur la distinction 27001 / 27002

| | **ISO/IEC 27001** | **ISO/IEC 27002** |
|---|---|---|
| **Rôle** | **Exigences** du SMSI (on peut être **certifié**) | **Guide** de bonnes pratiques (**non certifiable**) |
| **Question** | **Quoi** mettre en place (système de management) | **Comment** mettre en œuvre les contrôles |

### Classement par domaine *(ajout)*

| Domaine | Normes |
|---|---|
| **Management de la sécurité (SMSI)** | ISO 27001, ISO 27002 |
| **Contrôles techniques / bonnes pratiques** | NIST SP 800-53, CIS Controls |
| **Secteur du paiement** | PCI DSS |
| **Protection des données personnelles (loi)** | RGPD |
| **Continuité d'activité** | ISO 22301 |
| **Gouvernance IT** | COBIT |

### Précisions complémentaires *(ajout — à vérifier selon la version en vigueur)*
- **ISO/IEC 27001 et 27002** ont été révisées en **2022** (la 27002:2022 regroupe **93 contrôles** en **4 thèmes** : organisationnel, humain, physique, technologique).
- **RGPD** : en vigueur depuis **mai 2018** ; sanctions pouvant atteindre **20 M€ ou 4 % du chiffre d'affaires mondial**.
- **Contexte tunisien** : on citera aussi la **loi organique n° 2004-63** sur la **protection des données personnelles** et l'**ANSI** (*Agence Nationale de la Sécurité Informatique*).

---

## 11. Structure type d'une politique de sécurité (ajout)

> ⚠️ **Section ajoutée** : le support décrit le contenu et la démarche, mais pas un plan type. Voici un exemple de structure **pour comprendre à quoi ressemble concrètement** le document.

| Rubrique | Contenu |
|---|---|
| **1. Objet** | Pourquoi cette politique existe |
| **2. Périmètre / champ d'application** | Systèmes, personnes et tiers concernés |
| **3. Principes de sécurité** | **Confidentialité, intégrité, disponibilité, authentification, traçabilité** |
| **4. Rôles et responsabilités** | Direction, RSSI, employés, DPO |
| **5. Règles par domaine** | Accès, réseau, physique, applications, données, incidents, continuité |
| **6. Sanctions** | Conséquences en cas de non-respect |
| **7. Conformité** | Lois, normes (RGPD, ISO 27001, PCI DSS…) |
| **8. Gestion des exceptions** | Qui peut autoriser une dérogation |
| **9. Révision** | Fréquence, responsable, historique des versions |
| **10. Approbation** | Signature de la **direction** |

### Exemple d'extrait de règle (*politique de mots de passe*)
- Longueur minimale de **12 caractères** ; mots de passe **uniques** par service.
- **Authentification multifacteur (MFA)** obligatoire pour les accès à distance.
- Interdiction de **partager** ou de **noter** les mots de passe.
- **Révocation immédiate** des accès au départ d'un employé.

---

## 12. Remarques sur le support de cours

1. **Métadonnées du PDF** : le fichier porte le titre interne « L'algèbre de Boole et les fonctions logiques » (reste d'un ancien document) ; le **contenu** est bien le chapitre 2 de **sécurité informatique**.
2. **Coquilles** : « **étendu** » au lieu de **étendue** dans le plan ; « **revocation** » au lieu de **révocation** (accent manquant) ; sans incidence sur le fond.
3. **Répétition** : la diapositive **Objectifs** reprend les **5 principes du chapitre 1** (confidentialité, intégrité, disponibilité, authentification, traçabilité), car **ce sont les objectifs que la politique doit atteindre**.
4. **« Étendue » vs « Domaine d'application »** : proches mais distincts : l'**étendue** concerne **ce qui est protégé** (données, infrastructures, utilisateurs, tiers) ; le **domaine d'application** concerne **qui doit appliquer** la politique (employés, contractuels, partenaires).
5. **Domaines de responsabilité** : le cours cite 4 acteurs ; selon l'organisation, d'autres rôles existent (**administrateur système**, **propriétaire de l'information**, **auditeur interne**, **comité de sécurité**) *(remarque ajoutée)*.
6. **Normes** : le RGPD est une **loi** (règlement européen), pas une norme technique : le cours le précise. Le cours **ne détaille pas** les versions ni le contenu des contrôles.
7. **Pas de cas pratique** dans le support : les exemples de la section 11 sont **ajoutés** pour la compréhension.

---

## 13. Fiche de révision rapide

| Concept | Résumé en une ligne |
|---|---|
| **Politique de sécurité** | **Principes et règles** définissant **comment une organisation protège ses actifs informationnels** |
| **Rôle** | **Cadre** qui oriente les actions et décisions de sécurité |
| **Objectif principal** | Garantir **confidentialité, intégrité, disponibilité** (+ authentification, traçabilité) |
| **Étendue** | **Données, infrastructures, utilisateurs** + **tiers** |
| **Implémentation (4)** | **Analyse des risques · Formation/sensibilisation · Mesures techniques · Procédures/protocoles** |
| **Domaine d'application** | **Employés, contractuels, partenaires** |
| **Responsabilités** | **Direction** (approuve) · **RSSI** (met en œuvre) · **Employés** (respectent) · **DPO** (conformité données) |
| **Périodicité** | **Au moins une fois par an**, ou à chaque **changement significatif** ; **audits réguliers** |
| **9 types** | Information · Accès · Réseaux · Physique · Applications · **Continuité** · **Incidents** · Sensibilisation · Données |
| **8 étapes de mise en place** | **Besoins → Objectifs → Rédaction → Validation → Communication → Mise en œuvre → Surveillance → Révision** |
| **Rédaction** | Règles, responsabilités, procédures ; **langage clair et concis** |
| **Validation** | Direction + responsables sécurité + **DPO** |
| **Communication** | **Diffusion** + **formation** |
| **ISO/IEC 27001** | **SMSI** (système de management) — **certifiable** |
| **ISO/IEC 27002** | **Guide de bonnes pratiques** de contrôles (complète la 27001) |
| **NIST SP 800-53** | **Catalogue de contrôles** (systèmes fédéraux US) |
| **PCI DSS** | Sécurité des **données de cartes de paiement** |
| **RGPD (GDPR)** | **Loi européenne** sur les **données personnelles** |
| **CIS Controls** | **Bonnes pratiques** concrètes de cybersécurité |
| **ISO 22301** | **Continuité d'activité** |
| **COBIT** | **Gouvernance IT**, alignement IT/business |

### Questions d'examen typiques
1. **Définir la politique de sécurité** et préciser son **rôle**.
2. Quels sont les **objectifs** d'une politique de sécurité ? Rappeler les **5 principes**.
3. Que couvre l'**étendue** d'une politique ? Pourquoi **inclure les tiers** ?
4. Citer les **quatre volets de l'implémentation** (analyse des risques, formation, mesures techniques, procédures).
5. À qui s'applique la politique ? Quelles sont les **responsabilités** de la direction, du RSSI, des employés et du **DPO** ?
6. **Quelle périodicité** de révision et pourquoi ? Quel est le rôle des **audits** ?
7. Citer et expliquer **cinq types** de politiques de sécurité (accès, réseaux, continuité, incidents, gestion des données…).
8. Décrire les **8 étapes de mise en place** d'une politique de sécurité.
9. Pourquoi utiliser un **langage clair et concis** ? Qui doit **valider** la politique ?
10. Quelle est la différence entre **ISO 27001** et **ISO 27002** ?
11. Que sont **PCI DSS**, **RGPD**, **NIST SP 800-53**, **CIS Controls**, **ISO 22301**, **COBIT** ? Donner pour chacun le **domaine**.
12. Pourquoi le RGPD n'est-il **pas une norme technique** ?
13. Rédiger un **plan type** de politique de sécurité (objet, périmètre, rôles, règles, sanctions, révision).

---

*Fin du résumé — Chapitre 2 : Politique de sécurité.*
