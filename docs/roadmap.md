# Roadmap Technique – Dungeon Manager

**Dernière mise à jour :** 08/01/2026 (21:40)
**Statut :** Sprint 4 terminé / Sprint 5 demain.

---

## Légende des ID

| Préfixe | Catégorie        |
|:--------|:-----------------|
| DOC      | Documentation    |
| TECH     | Technologies        |
| TECH-PATT| Patterns         |
| TECH-AI  | Intelligence Artificielle |
| TECH-TILES| Murs/Traps    |
| TECH-HERO| Héros            |
| WEB      | Frontend Web     |
| CLI      | Interface Terminal (CLI) |
| CORR     | Corrections        |
| CLEAN    | Nettoyage de code  |

## Historique des Tâches Terminées

### Sprint 1 : Mise en place du projet et PoC de communication (05 Janvier)
*Installation des outils, gestion de projet et réalisation d'un Proof of Concept (PoC) de communication entre le backend et le frontend.*

| ID | Catégorie | Description de la Tâche | Assigné à | Priorité |
| :--- | :--- | :--- | :--- | :---: |
| **DOC-01** | Doc | Rédaction du Cahier des Charges initial | A. Denys | **Haute** |
| **DOC-02** | Doc | Création du Diagramme de Classe initial | E. Girres | Moyenne |
| **DOC-03** | Doc | Git Conventions | P. Musial | Moyenne |
| **DOC-04** | Doc | Roadmap initiale | A. Denys | Moyenne |
| **TECH-01** | Tech | PoC communication Backend <-> Frontend | K. Bui | **Critique** |
| **WEB-01** | Web | Mockup interface Web | A. Denys & P. Musial | **Haute** |


### Sprint 2 : Fondations & Entités (06 Janvier)
*Mise en place des briques élémentaires (Classes Moteur) et première maquette du Web et du CLI.*

| ID | Catégorie | Description de la Tâche | Assigné à | Priorité |
| :--- | :--- | :--- | :--- | :---: |
| **TECH-02** | Tech | Initialisation du Moteur de Jeu | P. Musial | **Critique** |
| **TECH-03** | Tech | Squad and Heroes | A. Denys | **Critique** |
| **TECH-04** | Tech | Coordonnées | P. Musial | **Critique** |
| **TECH-05** | Tech | Classe Game | A. Denys | **Haute** |
| **TECH-06** | Tech | Implémentation du mouvement des héros | A. Denys | **Haute** |
| **TECH-AI-01** | IA | Algorithme BFS | P. Musial | **Haute** |
| **TECH-AI-02** | IA | Algorithme DFS | P. Musial | Faible |
| **TECH-PATT-01** | Patterns | Factory Method - Creator pour les héros | A. Denys | Moyenne |
| **TECH-PATT-02** | Patterns | Builder Pattern pour HeroSquad | A. Denys | Moyenne |
| **TECH-PATT-03** | Patterns | Strategy Pattern pour les IA | P. Musial | **Haute** |
| **TECH-HERO-01** | Héros | Tank se soigne 1 fois du poison | A. Denys | Moyenne |
| **TECH-TILES-01** | Tech | Traps Mine et WallTrap | E. Girres | **Haute** |
| **TECH-TILES-02** | Tech | WoodWall et StoneWall | E. Girres | **Haute** |
| **WEB-02** | Web | Design HTML/CSS landing page | E. Girres | Moyenne |
| **WEB-03** | Web | Setup Map Editor Tool Interface | K. Bui | **Haute** |
| **WEB-04** | Web | Connexion backend-frontend | P. Musial & K. Bui | **Haute** |
| **CLI-01** | CLI | Ajouter la grille dans le terminal | E. Girres | **Haute** |
| **CLI-02** | CLI | Afficher le choix des actions | E. Girres | **Haute** |


### Sprint 3 : Algorithmes & Connexion (07 Janvier)
*Pièges, Money, Score, suite de l'implémentation du frontend, A\**

| ID | Catégorie | Description de la Tâche | Assigné à | Priorité |
| :--- | :--- | :--- | :--- | :---: |
| **TECH-07** | Tech | GameEngine, création du jeu | P. Musial | **Haute** |
| **TECH-08** | Tech | Serialisation | P. Musial | **Haute** |
| **TECH-09** | Tech | Logique de sauvegarde de parties | K. Bui | Moyenne |
| **TECH-10** | Tech | Fin de partie | P. Musial | Moyenne |
| **TECH-PATT-04** | Patterns | Visitor pattern pour Heal et Dégâts de zone | A. Denys | Moyenne |
| **TECH-PATT-05** | Patterns | Observer Pattern pour mise à jour du score | A. Denys | **Haute** |
| **TECH-AI-03** | IA | Algorithme A* | E. Girres | **Haute** |
| **TECH-TILES-03** | Tech | Piège Poison et modification Mine | A. Denys | Moyenne |
| **TECH-TILES-04** | Tech | Logique WallTrap | P. Musial | Moyenne |
| **TECH-TILES-05** | Tech | Coûts de placement | A. Denys | Moyenne |
| **WEB-05** | Web | Sélection d'IA | K. Bui | **Haute** |


### Sprint 4 : Intégration Majeure (08 Janvier)
*Finalisation de la grille, du cli et web, des règles de tour et refactoring global. Ajout de héros et du leaderboard*

| ID | Catégorie | Description de la Tâche | Assigné à | Priorité |
| :--- | :--- | :--- | :--- | :---: |
| **TECH-11** | Tech | Lier trésor et traps aux mouvements des héros | A. Denys | **Critique** |
| **TECH-12** | Tech | Verification que le prochain mouvement est possible | A. Denys | **Critique** |
| **TECH-13** | Tech | Implémentation du ScoreBoard | A. Denys | Moyenne |
| **TECH-AI-04** | IA | BFS refait, DFS supprimé | P. Musial | **Haute** |
| **TECH-HERO-02** | Héros | Implémentation du Dragon (Feu sur les Murs en bois) | A. Denys | Faible |
| **TECH-HERO-03** | Héros | Implémentation du Muggle (Moldu) | A. Denys | Faible |
| **WEB-06** | Web | ID pour gérer plusieurs games à la fois | K. Bui | **Haute** |
| **WEB-07** | Web | ScoreBoard template | K. Bui | Moyenne |
| **WEB-08** | Web | Gestion des vagues | K. Bui | Faible |
| **CLI-03** | CLI | Implementation de la simulation | E. Girres | **Critique** |
| **CLI-04** | CLI | Affichage score et money | E. Girres | Moyenne |
| **CLI-05** | CLI | EditGame dans un fichier spécifique | E. Girres | Faible |
| **CLI-06** | CLI | Affichage du leaderboard | P. Musial | Moyenne |
| **CORR-01** | Correctif | Correction BFS (ignore Traps), endGame, mouvement que si pas de héros sur la prochaine coordonnée | A. Denys & P. Musial | **Critique** |

---

## Sprint 5 : Finalisations (09 Janvier)
*Finalisation des fonctionnalités restantes, tests et documentation. Création de la vidéo de rendu*

| ID | Catégorie | Description de la Tâche | Assigné à | Priorité |
| :--- | :--- | :--- | :--- | :---: |
| **TECH-14** | Tech | Implémentation des vagues | P. Musial | Moyenne |
| **TECH-15** | Tech | Génération automatique de la grille | P. Musial | Moyenne |
| **DOC-05** | Doc | Rédaction de la ROADMAP | A. Denys | Moyenne |
| **DOC-06** | Doc | Création des diagrammes de séquence | A. Denys | **Haute** |
| **DOC-07** | Doc | Création du diagramme de classe | P. Musial & A. Denys | **Critique** |
| **DOC-08** | Doc | Rédaction du README | A. Denys | **Critique** |
| **DOC-09** | Doc | USAGE_IA.md | Toute l'équipe | **Haute** |
| **WEB-09** | Web | Affichage du leaderboard | K. Bui | Moyenne |
| **WEB-10** | Web | Affichage du score | K. Bui | Moyenne |
| **WEB-11** | Web | Empêcher de modifier la position du trésor et du point de départ | K. Bui | Faible |
| **CLI-07** | CLI | Gestion des vagues | E. Girres | Moyenne |
| **CORR-02** | Correctif | Correction du score | A. Denys | **Critique** |
| **CORR-03** | Correctif | Correction affichage des héros dans le WEB | K. Bui | **Critique** |
| **CORR-04** | Correctif | Correction Hero non affiché | P. Musial | Moyenne |
| **CORR-05** | Correctif | Correction score dans Terminal | E. Girres | Moyenne |
| **CORR-06** | Correctif | Leaderboard dans terminal | P. Musial & E. Girres | Moyenne |
| **CLEAN-01** | Nettoyage | Nettoyage du code | Toute l'équipe | Moyenne |