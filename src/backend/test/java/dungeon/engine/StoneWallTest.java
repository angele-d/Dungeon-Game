package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
