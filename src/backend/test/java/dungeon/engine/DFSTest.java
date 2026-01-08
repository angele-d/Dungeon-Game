package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.AI.DFS;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.wall.StoneWall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    @Test
    void search() {
        Hero hero = new TheMemeMaker();
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        DFS dfs = new DFS(grid);
        Coords previousCoords = new Coords(0, 0);
        Coords coords = new Coords(0, 0);
        int i = 0;
        while (i < 100 && (coords != new Coords(0, 3) || coords != null)) {
            coords = dfs.search(coords, hero, previousCoords);
            i+=1;
        }
        assertEquals(new Coords(0, 3), coords);
    }

    @Test
    void searchWithWall() {
        Hero hero = new TheMemeMaker();
        Grid grid = new Grid();
        grid.setTile(new Treasure(new Coords(0, 3)));
        grid.setTile(new StoneWall(new Coords(0, 2)));
        DFS dfs = new DFS(grid);
        Coords previousCoords = new Coords(0, 0);
        Coords coords = new Coords(0, 0);
        int i = 0;
        while (i < 150 && (coords != new Coords(0, 3) || coords != null)) {
            coords = dfs.search(coords, hero, previousCoords);
            i+=1;
        }
        assertTrue(i < 150);
        assertEquals(new Coords(0, 3), coords);
//        coords = dfs.search(new Coords(0, 0), hero);
//        assertEquals(new Coords(1, 2), coords);
    }

    @Test
    void searchNoTreasure() {
        Hero hero = new TheMemeMaker();
        Grid grid = new Grid();
        DFS dfs = new DFS(grid);
        Coords coords = dfs.search(new Coords(0, 0), hero, null);
        assertEquals(null, coords);
    }
}