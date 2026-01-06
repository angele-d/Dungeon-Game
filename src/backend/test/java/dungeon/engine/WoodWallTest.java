package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WoodWallTest {

    @Test
    void testWoodWallCreation() {
        Coords coords = new Coords(2, 2);

        WoodWall woodWall = new WoodWall(coords);

        assertNotNull(woodWall, "WoodWall ne doit pas être null");
    }

    @Test
    void testWoodWallIsWall() {
        WoodWall woodWall = new WoodWall(new Coords(0, 0));

        assertTrue(woodWall instanceof dungeon.engine.tiles.Wall,
                "WoodWall doit être une instance de Trap");
    }
}
