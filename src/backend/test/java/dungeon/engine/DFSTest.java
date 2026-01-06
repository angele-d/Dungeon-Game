package dungeon.engine;

import dungeon.engine.AI.DFS;
import dungeon.engine.tiles.Treasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DFSTest {

    @Test
    void search() {
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        DFS dfs = new DFS(grid);
        Coords coords = dfs.search(new Coords(0, 0));
        assertEquals(coords, new Coords(0, 1));
        coords = dfs.search(new Coords(0, 1));
        assertEquals(coords, new Coords(0, 2));
        coords = dfs.search(new Coords(0, 2));
        assertEquals(coords, new Coords(0, 3));
    }

    @Test
    void searchNoTreasure() {
        Grid grid = new Grid();
        DFS dfs = new DFS(grid);
        Coords coords = dfs.search(new Coords(0, 0));
        assertEquals(null, coords);
    }
}