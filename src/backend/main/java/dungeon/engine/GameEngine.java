package dungeon.engine;

import dungeon.engine.tiles.Empty;

import java.util.HashMap;
import java.util.Map;

public class GameEngine {
    private static GameEngine gameEngine;

    private Map<Integer, Game> games;

    private GameEngine() {
        games = new HashMap<Integer, Game>();
    }

    public static GameEngine getInstance() {
        if (gameEngine != null) {
            return gameEngine;
        } else {
            gameEngine = new GameEngine();
            return gameEngine;
        }
    }

    public Grid placeTile(int gameId, Coords coords, String type) {
        Game game = games.get(gameId);
        if (game != null) {
            Tile tile;
            switch (type) {
                case "empty":
                    tile = new Empty(coords);
            }

//            game.placementOnGrid(tile);
        }
        return null;
    }
}
