package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.wall.StoneWall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    @Test
    void search() {
        Hero hero = new TheMemeMaker();
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        hero.setCoords(new Coords(0, 0));
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0), heroSquad);
        assertEquals(new Coords(0, 1), coords);
        coords = bfs.search(new Coords(0, 1), heroSquad);
        assertEquals(new Coords(0, 2), coords);
        coords = bfs.search(new Coords(0, 2), heroSquad);
        assertEquals(new Coords(0, 3), coords);
    }

    @Test
    void searchWithWall() {
        Hero hero = new TheMemeMaker();
        hero.setCoords(new Coords(0, 0));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        grid.setTile(new StoneWall(new Coords(0, 2)));
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0), heroSquad);
        assertEquals(new Coords(1, 0), coords);
        coords = bfs.search(new Coords(1, 0), heroSquad);
        assertEquals(new Coords(1, 1), coords);
        coords = bfs.search(new Coords(1, 1), heroSquad);
        assertEquals(new Coords(1, 2), coords);
    }

    @Test
    void searchNoTreasure() {
        Hero hero = new TheMemeMaker();
        hero.setCoords(new Coords(0, 0));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        Grid grid = new Grid();
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0), heroSquad);
        assertEquals(coords, new Coords(0, 0));
    }

    @Test
    void searchWithHeroBlocking() {
        Hero hero1 = new TheMemeMaker();
        hero1.setCoords(new Coords(0, 2));
        Hero hero2 = new TheMemeMaker();
        hero2.setCoords(new Coords(0, 0));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero1);
        heroSquad.addHero(hero2);
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0), heroSquad);
        assertEquals(new Coords(1, 0), coords);
    }

    @Test
    void isOccupied() {
        Hero hero = new TheMemeMaker();
        hero.setCoords(new Coords(1, 1));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        Grid grid = new Grid();
        BFS bfs = new BFS(grid);
        assertTrue(bfs.isOccupied(new Coords(1, 1), heroSquad));
        assertFalse(bfs.isOccupied(new Coords(0, 0), heroSquad));
    }

    @Test
    void isWalkable() {
        Hero hero = new TheMemeMaker();
        hero.setCoords(new Coords(1, 1));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        Grid grid = new Grid();
        grid.setTile(new StoneWall(new Coords(0, 0)));
        BFS bfs = new BFS(grid);
        assertFalse(bfs.isWalkable(new Coords(0, 0), heroSquad));
        assertFalse(bfs.isWalkable(new Coords(1, 1), heroSquad));
        assertTrue(bfs.isWalkable(new Coords(0, 1), heroSquad));
    }
}