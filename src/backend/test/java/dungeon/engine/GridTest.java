package dungeon.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GridTest {
    
    @Test
    void testSetAndGetTile() {
        Grid grid = new Grid();
        Coords coords = new Coords(2, 3);
        Tile tile = new Tile(coords) {
            public int getAstarValue() {
                return 0;
            }
        };
        grid.setTile(tile);
        Tile retrievedTile = grid.getTile(coords);
        assertEquals(tile, retrievedTile);
    }

    @Test
    void testGetNeighborsCoords() {
        Grid grid = new Grid();
        Coords coords = new Coords(3, 3);
        var neighbors = grid.getNeighborsCoords(coords);
        assertEquals(4, neighbors.size());
        assertEquals(true, neighbors.contains(new Coords(2, 3)));
        assertEquals(true, neighbors.contains(new Coords(4, 3)));
        assertEquals(true, neighbors.contains(new Coords(3, 2)));
        assertEquals(true, neighbors.contains(new Coords(3, 4)));
    }

    @Test
    void testGetNeighborsCoordsAtEdge() {
        Grid grid = new Grid();
        Coords coords = new Coords(0, 0);
        var neighbors = grid.getNeighborsCoords(coords);
        assertEquals(2, neighbors.size());
        assertEquals(true, neighbors.contains(new Coords(1, 0)));
        assertEquals(true, neighbors.contains(new Coords(0, 1)));
    }

    @Test
    void testGetGridSize() {
        Grid grid = new Grid();
        assertEquals(64, grid.getGrid().size()); // 8x8 grid
    }
}