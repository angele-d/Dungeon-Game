package dungeon.engine;
import dungeon.engine.AI.Astar;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.traps.Mine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AstarTest {

    @Test
    void noTreasure_returnsNull() {
        Grid grid = new Grid();
        Astar astar = new Astar(grid);

        Coords result = astar.search(new Coords(0, 0));
        assertNull(result, "Aucune case avec trésor, doit retourner null");
    }

    @Test
    void findsSimpleTreasure() {
        Grid grid = new Grid();
        Treasure t = new Treasure(new Coords(1, 0));
        grid.setTile(t);

        Astar astar = new Astar(grid);
        Coords nextMove = astar.search(new Coords(0, 0));

        assertEquals(new Coords(1, 0), nextMove, "Doit trouver le trésor adjacent");
    }

    @Test
    void avoidsMineIfPossible() {
        Grid grid = new Grid();
        // On met une mine à (1,0)
        Mine mine = new Mine(new Coords(1, 0), 10, 1);
        grid.setTile(mine);
        // Trésor à droite mais accessible par bas
        Treasure t = new Treasure(new Coords(2, 1));
        grid.setTile(t);

        Astar astar = new Astar(grid);
        Coords nextMove = astar.search(new Coords(0, 0));

        // L'IA doit éviter la mine
        assertNotEquals(new Coords(1, 0), nextMove, "Doit éviter la mine");
    }

    @Test
    void choosesCheapestPath() {
        Grid grid = new Grid();
        // Chemin direct coûteux
        Mine mine = new Mine(new Coords(1, 0), 10, 1);
        grid.setTile(mine);
        // Trésor à (2,0)
        Treasure t = new Treasure(new Coords(2, 0));
        grid.setTile(t);

        Astar astar = new Astar(grid);
        Coords nextMove = astar.search(new Coords(0, 0));

        // Le premier mouvement doit éviter la mine si possible
        assertNotEquals(new Coords(1, 0), nextMove, "Doit choisir le chemin le moins coûteux");
    }

    @Test
    void returnsFirstStepTowardsTreasure() {
        Grid grid = new Grid();
        Treasure t = new Treasure(new Coords(3, 0));
        grid.setTile(t);

        Astar astar = new Astar(grid);
        Coords nextMove = astar.search(new Coords(0, 0));

        // Le premier pas doit être vers (1,0)
        assertEquals(new Coords(1, 0), nextMove, "Doit retourner le premier pas vers le trésor");
    }

    @Test
    void multipleTreasures_returnsClosest() {
        Grid grid = new Grid();
        Treasure t1 = new Treasure(new Coords(5, 5));
        Treasure t2 = new Treasure(new Coords(1, 0));
        grid.setTile(t1);
        grid.setTile(t2);

        Astar astar = new Astar(grid);
        Coords nextMove = astar.search(new Coords(0, 0));

        // L'IA doit choisir le trésor le plus proche
        assertEquals(new Coords(1, 0), nextMove, "Doit aller vers le trésor le plus proche");
    }
}
