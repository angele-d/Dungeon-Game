package dungeon.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.tiles.Wall;

class WallTest {

    // Implémentation minimale pour tester Wall
    private static class TestWall extends Wall {
        public TestWall(Coords coords) {
            super(coords);
        }
        public int getAstarValue() {
        return 0; // valeur factice pour le test
    }
    }

    @Test
    void testWallInitialization() {
        Coords coords = new Coords(3, 4);
        Wall wall = new TestWall(coords);

        assertNotNull(wall, "Le mur ne doit pas être null");
    }
}
