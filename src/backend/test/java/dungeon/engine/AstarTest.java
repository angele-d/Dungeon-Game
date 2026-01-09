package dungeon.engine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dungeon.engine.tiles.*;
import dungeon.engine.tiles.wall.*;
import dungeon.engine.AI.Astar;

class AstarTest {

    private Grid grid;
    private Astar astar;
    private HeroSquad squad;
    private Dragon dragon;

    @BeforeEach
    void setUp() {
        // Initialisation de base
        grid = new Grid(1); // Seed 1 par défaut
        astar = new Astar(grid);
        squad = new HeroSquad();
        
        // Création d'un dragon pour les tests d'occupation
        dragon = new Dragon();
        dragon.setCoords(new Coords(0, 0));
    }

    @Test
    void testIsWalkable_StoneWallBlocksPath() {
        Coords wallPos = new Coords(1, 1);
        grid.setTile(new StoneWall(wallPos));

        // Vérifie qu'un mur de pierre n'est pas traversable
        assertFalse(astar.isWalkable(wallPos, squad), "Un StoneWall ne devrait pas être walkable");
    }

    @Test
    void testIsOccupied_DragonBlocksPath() {
        Coords pos = new Coords(2, 2);
        dragon.setCoords(pos);
        dragon.setHealth(100);
        squad.addHero(dragon);

        // La case est occupée par un dragon vivant
        assertTrue(astar.isOccupied(pos, squad), "La case devrait être occupée par le dragon vivant");

        // Si le dragon meurt, la case devient libre
        dragon.setHealth(0);
        assertFalse(astar.isOccupied(pos, squad), "Un dragon mort ne devrait pas bloquer le chemin");
    }

    @Test
    void testIsOccupied_TreasureException() {
        Coords treasurePos = new Coords(3, 3);
        grid.setTile(new Treasure(treasurePos));
        
        dragon.setCoords(treasurePos);
        dragon.setHealth(150);
        squad.addHero(dragon);

        // Selon ta règle dans Astar.java : Treasure et StartingPoint ne sont jamais "occupied"
        assertFalse(astar.isOccupied(treasurePos, squad), 
            "Un trésor ne doit jamais être considéré comme occupé, même si un héros est dessus");
    }

    @Test
    void testSearch_SimplePath() {
        // On nettoie le chemin direct
        Coords start = new Coords(0, 0);
        Coords target = new Coords(2, 0);
        grid.setTile(new Treasure(target));
        grid.setTile(new Empty(new Coords(1, 0)));

        Coords move = astar.search(start, squad);

        // Le premier pas vers (2,0) est (1,0)
        assertEquals(new Coords(1, 0), move, "Le premier mouvement devrait être (1,0)");
    }

    @Test
    void testSearch_AvoidsStoneWall() {
        // Configuration:
        // S(0,0) | W(1,0) | T(2,0)
        // E(0,1) | E(1,1) | E(2,1)
        
        Coords start = new Coords(0, 0);
        grid.setTile(new StoneWall(new Coords(1, 0))); // Obstacle direct
        grid.setTile(new Treasure(new Coords(2, 0)));
        
        // S'assurer que le chemin de contournement est vide
        grid.setTile(new Empty(new Coords(0, 1)));
        grid.setTile(new Empty(new Coords(1, 1)));

        Coords move = astar.search(start, squad);

        // Doit contourner par le bas (0,1)
        assertEquals(new Coords(0, 1), move, "L'Astar doit contourner le mur de pierre");
    }

    @Test
    void testSearch_BlockedByOtherHero() {
        Coords start = new Coords(0, 0);
        grid.setTile(new Treasure(new Coords(2, 0)));
        
        // Un autre dragon bloque le chemin en (1,0)
        Dragon ally = new Dragon();
        ally.setCoords(new Coords(1, 0));
        ally.setHealth(100);
        squad.addHero(ally);
        
        // Chemin alternatif dégagé
        grid.setTile(new Empty(new Coords(0, 1)));

        Coords move = astar.search(start, squad);

        // Ne doit pas aller en (1,0) car l'allié bloque
        assertNotEquals(new Coords(1, 0), move, "L'IA ne doit pas marcher sur un allié");
        assertEquals(new Coords(0, 1), move, "L'IA doit contourner l'allié");
    }
}