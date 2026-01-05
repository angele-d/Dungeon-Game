
## Interaction #1

### Contexte

Création du Cahier des Charges pour le projet "Dungeon Manager".

### Prompt

Prompt envoyé à ChatGPT:

```
Peux-tu me réaliser un prompt à envoyer à Gemini pour réaliser le cahier des charges (liste des besoins fonctionnels et des besoins non fonctionnels) de ce projet. Si tu as une quelconque question sur le sujet, tu peux me demander
```

Prompt généré par ChatGPT, à envoyer à Gemini:

```
Tu es un ingénieur logiciel / analyste fonctionnel senior chargé de rédiger un cahier des charges pour un projet de jeu vidéo académique.

### Contexte du projet
Le projet s’inscrit dans le cadre de CodingWeek – Dungeon Manager.
Le joueur incarne le seigneur du donjon et non les héros : il construit un donjon (grille 2D), place des murs et des pièges, puis lance des vagues de héros contrôlés par des IA qui tentent d’atteindre un trésor.

Le jeu est une simulation tour par tour sur une grille 2D.
Le joueur cherche à maximiser un score tout en empêchant les héros d’atteindre le trésor.

Deux interfaces sont prévues :
- une interface terminal (TUI)
- une interface Web

L’architecture attendue est modulaire, inspirée MVC, avec un moteur de jeu commun.

---

### Règles du jeu (spécifiques à notre version)

*(Voir regles.md)*

---

### Travail demandé

Rédige un cahier des charges clair et structuré, comprenant exclusivement :

1. Besoins fonctionnels
- Présentés sous forme de liste structurée
- Formulés de manière testable
- Regroupés par grands thèmes (édition du donjon, simulation, héros, IA, score, interfaces, persistance…)
- En distinguant clairement ce qui relève du MVP et des fonctionnalités optionnelles

2. Besoins non fonctionnels
- Architecture logicielle
- Qualité du code
- Performances attendues
- Testabilité
- Ergonomie
- Maintenabilité
- Contraintes techniques (langage orienté objet, MVC, partage moteur/UI, etc.)

---

### Contraintes de forme
- Langue : français
- Ton : professionnel, académique
- Pas de code
- Pas de diagrammes
- Pas de reformulation du sujet : uniquement des besoins

Le résultat doit pouvoir être utilisé tel quel comme document de référence pour lancer le développement du projet.
```

### Réponse IA

Un fichier Cahier_Des_Charges.md a été généré avec le contenu demandé:
- Tableau des besoins fonctionnels
- Tableau des besoins non fonctionnels


### Décision

**Modifié:**


| Index | Raison |
| :--- | :--- |
| *REQ-EDIT-01*  | dimensions variables |
| *REQ-TRAP-01*  | précision sur l'apparence initiale |
| *REQ-HERO-03*  | passage en extension |
| *REQ-HERO-06*  | passage en MVP |
| *REQ-AI-04*  | précision sur le recalcul dynamique du chemin |
| *REQ-SIM-01* | Différenciation système d'activation de piège: après mouvement du héros concerné |
| *REQ-SIM-03*  | *REQ-SIM-03* devient *REQ-SIM-02* |
| *REQ-SCORE-01*  | précision sur le calcul du score |
| *REQ-SCORE-02*  | précision sur le bonus de score |
| *ARCH-01*  | précision sur l'indépendance du modèle vis-à-vis de l'UI |
| *PERF-01*  | Diminution de 10 à 3 héros |


**Rejeté:** 

| Index | Raison |
| :--- | :--- |
| *REQ-SIM-02*  | condition de défaite (hors sujet) |

**Gardé:**

Le reste des besoins proposés initialement par l'IA a été conservé sans ou avec de minimes modifications d'écriture.

### Remarques

L'IA n'avait pas compris que le rôle "Meme Maker" est une fonctionnalité MVP, et non une extension. J'ai corrigé cela.


