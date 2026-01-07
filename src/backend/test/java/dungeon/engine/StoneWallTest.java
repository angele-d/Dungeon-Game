package dungeon.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.tiles.wall.StoneWall;

class StoneWallTest {

    @Test
    void testStoneWallCreation() {
        Coords coords = new Coords(1, 1);

        StoneWall stoneWall = new StoneWall(coords);

        assertNotNull(stoneWall, "StoneWall ne doit pas être null");
    }

    @Test
    void testStoneWallIsWall() {
        StoneWall stoneWall = new StoneWall(new Coords(0, 0));

        assertTrue(stoneWall instanceof dungeon.engine.tiles.Wall,
                "StoneWall doit être une instance de Trap");
    }
}
