package dungeon.engine.tiles;

import dungeon.engine.Coords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    // Implémentation minimale pour tester Wall
    private static class TestWall extends Wall {
        public TestWall(Coords coords) {
            super(coords);
        }
    }

    @Test
    void testWallInitialization() {
        Coords coords = new Coords(3, 4);
        Wall wall = new TestWall(coords);

        assertNotNull(wall, "Le mur ne doit pas être null");
    }
}
