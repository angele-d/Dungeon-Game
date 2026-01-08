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
        HeroSquad heroSquad = new HeroSquad();

        Coords result = astar.search(new Coords(0, 0), heroSquad);
        assertEquals(result, new Coords(0, 0));
    }

    @Test
    void findsSimpleTreasure() {
        Grid grid = new Grid();
        HeroSquad heroSquad = new HeroSquad();
        Treasure t = new Treasure(new Coords(1, 0));
        grid.setTile(t);

        Astar astar = new Astar(grid);
        Coords nextMove = astar.search(new Coords(0, 0), heroSquad);

        assertEquals(new Coords(1, 0), nextMove);
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
        HeroSquad heroSquad = new HeroSquad();

        Astar astar = new Astar(grid);
        Coords nextMove = astar.search(new Coords(0, 0), heroSquad);

        // L'IA doit éviter la mine
        assertNotEquals(new Coords(1, 0), nextMove);
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
        HeroSquad heroSquad = new HeroSquad();
        Coords nextMove = astar.search(new Coords(0, 0), heroSquad);

        // Le premier mouvement doit éviter la mine si possible
        assertNotEquals(new Coords(1, 0), nextMove);
    }

    @Test
    void returnsFirstStepTowardsTreasure() {
        Grid grid = new Grid();
        Treasure t = new Treasure(new Coords(3, 0));
        grid.setTile(t);

        Astar astar = new Astar(grid);
        HeroSquad heroSquad = new HeroSquad();
        Coords nextMove = astar.search(new Coords(0, 0), heroSquad);

        // Le premier pas doit être vers (1,0)
        assertEquals(new Coords(1, 0), nextMove);
    }

    @Test
    void multipleTreasures_returnsClosest() {
        Grid grid = new Grid();
        Treasure t1 = new Treasure(new Coords(5, 5));
        Treasure t2 = new Treasure(new Coords(1, 0));
        grid.setTile(t1);
        grid.setTile(t2);

        Astar astar = new Astar(grid);
        HeroSquad heroSquad = new HeroSquad();
        Coords nextMove = astar.search(new Coords(0, 0), heroSquad);

        // L'IA doit choisir le trésor le plus proche
        assertEquals(new Coords(1, 0), nextMove);
    }

    @Test
    void avoidsHeroBlocking() {
        Grid grid = new Grid();
        Hero hero1 = new Muggle();
        hero1.setCoords(new Coords(1, 0));
        Hero hero2 = new Muggle();
        hero2.setCoords(new Coords(0, 0));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero1);
        heroSquad.addHero(hero2);
        Treasure t = new Treasure(new Coords(2, 0));
        grid.setTile(t);

        Astar astar = new Astar(grid);
        Coords nextMove = astar.search(new Coords(0, 0), heroSquad);

        // The path should avoid the hero at (1,0)
        assertNotEquals(new Coords(1, 0), nextMove);
    }

    @Test
    void isOccupied() {
        Hero hero = new Muggle();
        hero.setCoords(new Coords(2, 2));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        Grid grid = new Grid();
        Astar astar = new Astar(grid);

        assertTrue(astar.isOccupied(new Coords(2, 2), heroSquad));
        assertFalse(astar.isOccupied(new Coords(0, 0), heroSquad));
    }
}
