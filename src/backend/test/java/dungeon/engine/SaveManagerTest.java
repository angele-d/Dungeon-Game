package dungeon.engine;

import org.junit.jupiter.api.Test;

import dungeon.engine.tiles.Treasure;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

class SaveManagerTest {
    @Test
    void testSaveGame() throws IOException {
        Game game = new Game();
        game.subMoney(300);
        Coords coords = new Coords(1, 2);
        Tile tile = new Treasure(coords);
        game.placementOnGrid(tile);
        SaveManager.save(game);

        Game game2 = new Game();
        SaveManager.load(game2, "save1.json");
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                coords = new Coords(i, j);
                assertEquals(game.getGrid().getTile(coords).getClass(), game2.getGrid().getTile(coords).getClass());
            }
        }
    }
}
