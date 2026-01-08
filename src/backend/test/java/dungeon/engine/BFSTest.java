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
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0), hero);
        assertEquals(new Coords(0, 1), coords);
        coords = bfs.search(new Coords(0, 1), hero);
        assertEquals(new Coords(0, 2), coords);
        coords = bfs.search(new Coords(0, 2), hero);
        assertEquals(new Coords(0, 3), coords);
    }

    @Test
    void searchWithWall() {
        Hero hero = new TheMemeMaker();
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        grid.setTile(new StoneWall(new Coords(0, 2)));
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0), hero);
        assertEquals(new Coords(1, 0), coords);
        coords = bfs.search(new Coords(1, 0), hero);
        assertEquals(new Coords(1, 1), coords);
        coords = bfs.search(new Coords(1, 1), hero);
        assertEquals(new Coords(1, 2), coords);
    }

    @Test
    void searchNoTreasure() {
        Hero hero = new TheMemeMaker();
        Grid grid = new Grid();
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0), hero);
        assertNull(coords);
    }
}