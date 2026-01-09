
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


---
## Interaction #2

### Contexte

Création d'une maquette d'interface Web pour le projet. 

### Prompt

```md
Agis en tant que Développeur Web Senior et Designer UI/UX.
Génère une interface web complète pour le jeu "Dungeon Manager" en utilisant uniquement HTML5, CSS3 (Flexbox/Grid) et JavaScript ES6 (Vanilla, sans framework).

### 1. Objectif
Créer une interface de prototype fonctionnelle permettant de :
- Dessiner un donjon sur une grille.
- Visualiser les contrôles de simulation.
- Voir les statistiques et les logs de la partie.

### 2. Design Visuel (Thème Dark Fantasy)
- Fond général : Très sombre (#1a1a2e).
- Police : Sans-serif, moderne, lisible (ex: 'Roboto' ou 'Inter').
- Couleurs sémantiques :
  - Mur Pierre (Indestructible) : Gris ardoise (#64748b).
  - Mur Bois (Destructible) : Marron bois (#854d0e).
  - Héros : Bleu cyan (#06b6d4).
  - Trésor : Or brillant (#f59e0b).
  - Piège Mine : Rouge alerte (#ef4444).
  - Piège Déclenchement : Violet sombre (#6b21a8).
  - Sol vide : Gris foncé (#334155).

### 3. Structure de la page (Layout)
L'interface doit être divisée en 3 colonnes ou zones principales :

A. Sidebar Gauche : "La Boîte à Outils"
- Un titre "Construction".
- Une palette de boutons radio ou boutons cliquables pour sélectionner l'outil actuel :
  - "Sélection" (Curseur)
  - "Mur Pierre"
  - "Mur Bois"
  - "Mine" (Piège)
  - "Mur à déclenchement" (Piège)
  - "Gomme"
- Une section "Légende" simple montrant le code couleur.

B. Zone Centrale : "Le Donjon"
- Un Header avec le Titre du jeu et le Score actuel (ex: "Score: 0").
- Un conteneur pour la grille (Grille dynamique générée en JS, par exemple 20x20 cases).
- La grille doit être réactive : au survol de la souris, la case s'éclaire légèrement.

C. Sidebar Droite : "Contrôle & Infos"
- Section "Simulation" : Boutons "Start Vague", "Pause", "Reset".
- Section "Stats Héros" : Un tableau simple listant les héros vivants, leurs PV et leur statut.
- Section "Journal de combat" : Une zone de texte défilante (log) pour afficher les événements (ex: "Héros 1 a pris 10 dégâts").

### 4. Fonctionnalités JavaScript attendues
- Générer la grille 20x20 dynamiquement au chargement via une boucle JS.
- Gérer le clic sur une case : La case doit changer de couleur/classe CSS selon l'outil sélectionné dans la barre de gauche.
- Empêcher de dessiner sur les cases "Départ" (0,0) et "Trésor" (19,19) qui sont fixes.
- Simuler l'ajout d'une ligne dans le "Journal de combat" quand on clique sur le bouton "Start Vague".

### 5. Contraintes Techniques
- Tout le code doit être contenu dans un seul fichier index.html (avec <style> et <script>) OU séparé proprement si tu préfères.
- Utilise des variables CSS pour les couleurs.
- Code propre, commenté et indenté.
```

### Réponse IA

Voir dans `Archives/mockups/proposition_1` pour:
- Le code source HTML/CSS/JS généré en 1 fichier
- Une capture d'écran de la maquette générée

### Décision

**Modifié:**  
- Deuxième prompt envoyé à Stitch (voir [Interaction #3](#interaction-3))
- Mix des 2 propositions et retraitement pour dynamiser la page

### Remarques

L'IA a fourni une maquette statique intéressante, manquant de certaines fonctionnalités. Un mix avec un deuxième prompt a permis d'avoir un résultat plus satisfaisant.

---
## Interaction #3

### Contexte

Création d'une maquette d'interface Web pour le projet. 

### Prompt

```txt
Fais une interface de jeu de type donjon en grille, avec des murs en pierre, bois, des pièges en trappe (représentés par une icone), des joueurs (représentés par une icone (4 max, healer, tank, nain, fou).

Le design doit être épuré et inclure un bouton pour passer au tour suivant, afficher le type d'IA choisi, ainsi que l'argent restant.

Il faut inclure un aperçu de ce qui est placé sur la grille, chaque tuile avec son nombre de tuile placées sur la grille.

Choisis un design plutôt épuré, style new, et essaie d'utiliser du contenu material design. 

Le résultat doit être en html/css/js
```

### Réponse IA

Voir dans `Archives/mockups/proposition_2` pour:
- Le code source HTML/CSS/JS généré en 1 fichier
- Une capture d'écran de la maquette générée

### Décision

**Modifié:**
- Premier prompt envoyé à Stitch (voir [Interaction #2](#interaction-2))
- Mix des 2 propositions et retraitement pour dynamiser la page

### Remarques

L'IA a fourni une maquette statique intéressante, manquant de certaines fonctionnalités. Un mix avec un premier prompt a permis d'avoir un résultat plus satisfaisant.


---
## Interaction #4

### Contexte

Correction de l'IA Astar

### Prompt

```txt
Il y a des cases où un hero ne peut pas aller avec la fonction iswalkable, je veux que mon Astar prenne ça en compte et abandonne les chemins qui ne mènent pas au trésor.


Utilise le code que je te fournis et réécrit entièrement la fonction search.



Code :

package dungeon.engine.AI;

import dungeon.engine.Coords;
import dungeon.engine.Grid;
import dungeon.engine.Hero;
import dungeon.engine.HeroSquad;
import dungeon.engine.tiles.StartingPoint;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.Wall;

import java.util.*;

public class Astar {

    private Grid grid;

    /* --- Constructor --- */

    public Astar(Grid grid) {
        this.grid = grid;

    }

    /* --- Functions --- */

    public boolean isOccupied(Coords neighbor, HeroSquad heroSquad){
        if(heroSquad == null) return false;

        // Heroes can be more than one in Treasure and StartingPoint tiles
        if(grid.getTile(neighbor) instanceof Treasure || grid.getTile(neighbor) instanceof StartingPoint){
            return false;
        }

        for(Hero hero : heroSquad.getHeroes()){
            if(hero.getCoords().equals(neighbor)){
                // Check if the hero is alive
                if(hero.getHealth() > 0)
                    return true;
            }
        }

        return false;
    }

    public boolean isWalkable(Coords neighbor, HeroSquad heroSquad){
        // Check if a wall is there
        if (grid.getTile(neighbor) instanceof Wall){
            return false;
        }
        // Check if an other hero is there
        if(isOccupied(neighbor, heroSquad)){
            return false;
        }
        
        return true;
    }

    public Coords search(Coords start, HeroSquad heroSquad) {
        PriorityQueue<NodeValue> value = new PriorityQueue<>(
            Comparator.comparingInt(NodeValue::getValue)
        );
        Map<Coords, Integer> bestCost = new HashMap<>();
        bestCost.put(start, 0);
        Node startNode = new Node(start, null);
        NodeValue startValue = new NodeValue(null, startNode, 0);
        value.add(startValue);

        while (!value.isEmpty()) {
            NodeValue currValue = value.poll();
            Node currNode = currValue.getNode();

            // Treasure found
            if (grid.getTile(currNode.getCoords()) instanceof Treasure) {
                return treasureFound(currValue);
            }

            // Explore neighbors
            for (Coords neighbor: grid.getNeighborsCoords(currValue.getNode().getCoords())) {

                    int newCost;
                    if(isOccupied(neighbor, heroSquad)){
                        newCost = 200000;
                    }
                    else{
                        newCost = currValue.getValue() + grid.getTile(neighbor).getAstarValue();
                    }
                    Node neighborNode = new Node(neighbor, currNode);
                    
                    if (!bestCost.containsKey(neighbor) || newCost < bestCost.get(neighbor)) {
                        bestCost.put(neighbor, newCost);
                        value.add(new NodeValue(currValue, neighborNode, newCost));
                    }
            }
        }
        return start;
    }

    public Coords treasureFound(NodeValue curr){
        while (curr.getParent() != null && curr.getParent().getParent() != null) {
            curr = curr.getParent();
        }
        return curr.getNode().getCoords();
    }

    /* --- toString --- */

    @Override
    public String toString() {
        return "Astar";
    }        
       
}
```

### Réponse IA


Parfait 👍
Je te **réécris entièrement `search`** en utilisant **ton `isWalkable`**, et en faisant en sorte que :

* les cases **non franchissables sont ignorées**
* les chemins bloqués sont **abandonnés**
* l’A* **ne part que sur des chemins menant potentiellement au trésor**

Je **ne touche pas** à tes autres méthodes.

---

##### ✅ Nouvelle version de `search` (complète)

```java
public Coords search(Coords start, HeroSquad heroSquad) {

    PriorityQueue<NodeValue> openList = new PriorityQueue<>(
        Comparator.comparingInt(NodeValue::getValue)
    );

    Map<Coords, Integer> bestCost = new HashMap<>();

    Node startNode = new Node(start, null);
    NodeValue startValue = new NodeValue(null, startNode, 0);

    openList.add(startValue);
    bestCost.put(start, 0);

    while (!openList.isEmpty()) {

        NodeValue currentValue = openList.poll();
        Node currentNode = currentValue.getNode();
        Coords currentCoords = currentNode.getCoords();

        // 🎯 Trésor trouvé
        if (grid.getTile(currentCoords) instanceof Treasure) {
            return treasureFound(currentValue);
        }

        // 🔍 Exploration des voisins
        for (Coords neighbor : grid.getNeighborsCoords(currentCoords)) {

            // ❌ Case non franchissable → on abandonne ce chemin
            if (!isWalkable(neighbor, heroSquad)) {
                continue;
            }

            int newCost = currentValue.getValue()
                        + grid.getTile(neighbor).getAstarValue();

            // Si on a déjà trouvé un meilleur chemin vers cette case
            if (bestCost.containsKey(neighbor)
                && newCost >= bestCost.get(neighbor)) {
                continue;
            }

            bestCost.put(neighbor, newCost);

            Node neighborNode = new Node(neighbor, currentNode);
            NodeValue neighborValue = new NodeValue(currentValue, neighborNode, newCost);

            openList.add(neighborValue);
        }
    }

    // 🚫 Aucun chemin vers un trésor
    return start; // ou null si tu préfères
}
```

---

##### 🔥 Ce qui a changé (important)

###### ❌ AVANT

```java
if (isOccupied(neighbor, heroSquad)) {
    newCost = 200000;
}
```

➡️ Le chemin continuait quand même

---

###### ✅ MAINTENANT

```java
if (!isWalkable(neighbor, heroSquad)) {
    continue;
}
```

➡️ **Le chemin est abandonné immédiatement**

---

##### 🧠 Pourquoi ton A* fonctionne maintenant correctement

✔️ Pas de murs
✔️ Pas de cases occupées
✔️ Pas de coûts artificiels
✔️ Les chemins sans issue meurent naturellement
✔️ Le premier pas retourné mène **forcément vers un trésor atteignable**

---

##### ✨ Amélioration possible (optionnelle)

Si tu veux être encore plus propre :

* retourner `null` si aucun trésor n’est atteignable
* ajouter une heuristique (Manhattan) pour accélérer
* éviter de revisiter une case déjà traitée (closed set)

Si tu veux, dis-moi et je t’améliore ça sans changer ton API 👌


### Décision

Vérification et utilisation de cette version.
Essaie avec des tests.

### Remarques

L'IA a bien adapté l'algorithme souhaité. Le code est utilisable.


---
