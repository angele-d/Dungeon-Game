package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.tiles.Treasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    @Test
    void search() {
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0));
        assertEquals(coords, new Coords(0, 1));
        coords = bfs.search(new Coords(0, 1));
        assertEquals(coords, new Coords(0, 2));
        coords = bfs.search(new Coords(0, 2));
        assertEquals(coords, new Coords(0, 3));
    }

    @Test
    void searchNoTreasure() {
        Grid grid = new Grid();
        BFS bfs = new BFS(grid);
        Coords coords = bfs.search(new Coords(0, 0));
        assertNull(coords);
    }
}