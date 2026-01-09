package dungeon.engine;

import org.junit.jupiter.api.Test;

import dungeon.engine.tiles.*;
import dungeon.engine.tiles.traps.*;
import dungeon.engine.tiles.wall.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

class SaveManagerTest {
    @Test
    void testSaveGame() throws IOException {
        Game game = new Game();
        game.subMoney(-30000);
        Coords coords = new Coords(0, 1);
        Tile tile = new Treasure(coords);
        game.placementOnGrid(tile);

        coords = new Coords(0, 2);
        tile = new StartingPoint(coords);
        game.placementOnGrid(tile);

        coords = new Coords(0, 3);
        tile = new Mine(coords);
        game.placementOnGrid(tile);

        coords = new Coords(0, 4);
        tile = new PoisonTrap(coords);
        game.placementOnGrid(tile);

        coords = new Coords(0, 5);
        tile = new WallTrap(coords);
        game.placementOnGrid(tile);

        coords = new Coords(0, 6);
        tile = new StoneWall(coords);
        game.placementOnGrid(tile);

        coords = new Coords(0, 7);
        tile = new WoodWall(coords);
        game.placementOnGrid(tile);

        SaveManager.save(game);

        Game game2 = new Game();
        SaveManager.load(game2, "save1.json");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                coords = new Coords(i, j);
                assertEquals(game.getGrid().getTile(coords).getClass(), game2.getGrid().getTile(coords).getClass());
            }
        }

        assertEquals(game.getId(), game2.getId());
    }
}
