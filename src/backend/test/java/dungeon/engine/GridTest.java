package dungeon.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeon.engine.tiles.StartingPoint;
import dungeon.engine.tiles.Treasure;
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
            public int getPlacementCost() {
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
        assertEquals(100, grid.getGrid().size()); // 10x10 grid
    }

    @Test
    void testGridGeneration() {
        Grid grid = new Grid();
        boolean treasureFound = false;
        boolean startPositionFound = false;
        for (Tile tile: grid.getGrid().values()) {
            if (tile instanceof Treasure) {
                treasureFound = true;
            } else if (tile instanceof StartingPoint) {
                startPositionFound = true;
            }
        }
        assertTrue(startPositionFound);
        assertTrue(treasureFound);
    }

    @Test
    void testMultipleGridGeneration() {
        for (int i=0; i<10; i++) {
            Grid grid = new Grid(i);
            boolean treasureFound = false;
            boolean startPositionFound = false;
            for (Tile tile : grid.getGrid().values()) {
                if (tile instanceof Treasure) {
                    treasureFound = true;
                } else if (tile instanceof StartingPoint) {
                    startPositionFound = true;
                }
            }
            assertTrue(startPositionFound);
            assertTrue(treasureFound);
        }
    }
}