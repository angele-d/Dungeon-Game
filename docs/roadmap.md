## Roadmap

Sprint #1

Gestion de Projet, mise en place
Réalisation en équipe d'un PoC de la communication backend-frontend

Sprint #2

| ID                | Catégorie         | Description du Besoin                                                                                                                                 | Priorité |
|:------------------|:------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------| :---: |
| **REQ-EDIT-01**   | Édition Donjon    | Définition d'une grille 2D de dimensions fixes (15x15).                                                                                               | **MVP** |
| **REQ-EDIT-02**   | Édition Donjon    | Placement des points de départ (Entrée) et d'arrivée (Trésor).                                                                                        | **MVP** |
| **REQ-EDIT-03**   | Édition Donjon    | Placement de murs "Pierre" (Indestructibles/Bloquants).                                                                                               | **MVP** |
| **REQ-EDIT-05**   | Édition Donjon    | Validation algorithmique de l'existence d'au moins un chemin valide (faisable) du point de départ jusqu'au trésor avant simulation.                   | **MVP** |
| **REQ-AI-01**     | IA                | Algorithme **BFS (Breadth-First Search)** pour trouver le chemin le plus court sous coût pondéré.                                                     | **MVP** |
| **REQ-TRAP-04**   | Pièges            | **Trappes** : Tue instantanément le héros et transforme la case en trou infranchissable pour les tours suivants.                                      | Extension |
| **REQ-HERO-01**   | Héros             | Chaque héros possède des points de vie (PV)                                                                                                           | **MVP** |
| **REQ-HERO-02**   | Héros             | Le héros se déplace selon les 4 points cardinaux (N, S, E, O), sans rotation.                                                                         | **MVP** |
| **REQ-HERO-03**   | Héros             | Rôle **Tank** : Nombre de PV supérieur à la moyenne, immunisé aux effets d'étourdissement.                                                            | Extension |
| **REQ-HERO-04**   | Héros             | Rôle **Dwarf** : Capable de détruire un mur de type "Bois" s'il bloque son chemin, transformant la case en sol.                                       | Extension |
| **REQ-HERO-05**   | Héros             | Rôle **Healer** : regagne $X$ PV par tour et applique un buff de régénération aux héros adjacents.                                                    | Extension |
| **REQ-UI-01**     | Interface         | **Interface Terminal (TUI)** : Affichage de la grille en caractères ASCII/Unicode et saisie des commandes via clavier.                                | **MVP** |
| **REQ-UI-02**     | Interface         | **Interface Web** : Affichage graphique de la grille dans un navigateur et contrôles via souris/clavier.                                              | **MVP** |
| **REQ-UI-03**     | Interface         | Synchronisation : Les deux interfaces doivent permettre de visualiser l'état courant de la grille, la position des héros, les murs et le score actuel | **MVP** |

Sprint #3

| ID                | Catégorie      | Description du Besoin                                                                                                                                | Priorité  |
|:------------------|:---------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|
| **REQ-EDIT-04**   | Édition Donjon | Placement de murs "Bois" (Destructibles par les Dwarf).                                                                                              |  **MVP**  |
| **REQ-EDIT-06**   | Édition Donjon | Sauvegarde et chargement de la configuration du donjon dans un fichier.                                                                              |  **MVP**  |
| **REQ-TRAP-01**   | Pièges         | **Murs à déclenchement** : Invisible, devient un mur bloquant *après* le passage d'un héros, bloquant le chemin aux héros suivants.                  |  **MVP**  |
| **REQ-TRAP-02**   | Pièges         | **Mines** : Invisible, à usage unique, inflige des dégâts de zone (case active + adjacentes) lorsqu'un héros marche dessus.                          |  **MVP**  |
| **REQ-TRAP-03**   | Pièges         | **Poison** (Lac/Pluie) : Après activation au passage du héros, inflige des dégâts récurrents (DoT) jusqu'à la mort du héros ou son arrivée au trésor | Extension |
| **REQ-HERO-06**   | Héros          | Rôle **Meme Maker** : Comportement standard, déclenche un affichage spécifique (image/message) s'il atteint le trésor                                | Extension |
| **REQ-AI-03**     | IA             | Algorithme **A\* (A-Star)** pour une recherche de chemin heuristique optimisée.                                                                      | Extension |
| **REQ-AI-04**     | IA             | L'IA doit recalculer le chemin si la route est bloquée (ex: Mur à déclenchement) ou si un Dwarf a cassé un mur                                       |  **MVP**  |
| **REQ-SIM-01**    | Simulation     | Jeu au tour par tour : tous les héros effectuent une action, activant certains pièges directement                                                    |  **MVP**  |
| **REQ-SCORE-01**  | Score          | Le score augmente proportionnellement aux dégâts infligés aux héros                                                                                  |  **MVP**  |
| **REQ-SCORE-02**  | Score          | Le score doit prendre en compte le "temps de retard": plus les héros mettent de tours à atteindre l'objectif (ou mourir), plus le score est élevé    |  **MVP**  |

Sprint #4



Sprint #5