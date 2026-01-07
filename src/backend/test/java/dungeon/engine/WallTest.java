package dungeon.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.tiles.Wall;

class WallTest {

    // Minimal implementation of Wall for testing purposes
    private static class TestWall extends Wall {
        public TestWall(Coords coords) {
            super(coords);
        }
        public int getAstarValue() {
            return 0; //fake value for testing
        }
        public int getPlacementCost() {
            return 0; //fake value for testing
        }
        public void setPlacementCost(int cost) {
            //fake implementation for testing
        }
    }

    @Test
    void testWallInitialization() {
        Coords coords = new Coords(3, 4);
        Wall wall = new TestWall(coords);

        assertNotNull(wall);
    }
}
