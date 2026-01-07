package dungeon.engine;

import dungeon.engine.strategies.*;
import dungeon.engine.tiles.wall.*;
import dungeon.engine.tiles.traps.*;
import dungeon.engine.tiles.*;

import java.util.ArrayList;
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

    public Map<String, String> placeTile(int gameId, Coords coords, String type) {
        Game game = games.get(gameId);
        if (game != null) {
            Tile tile;
            switch (type) {
                case "empty":
                    tile = new Empty(coords);
                    break;
                case "treasure":
                    tile = new Treasure(coords);
                    break;
                case "woodwall":
                    tile = new WoodWall(coords);
                    break;
                case "stonewall":
                    tile = new StoneWall(coords);
                    break;
                case "mine":
                    tile = new Mine(coords);
                    break;
                case "walltrap":
                    tile = new WallTrap(coords);
                    break;
                default:
                    tile = new Empty(coords);
                    break;
            }

            game.placementOnGrid(tile);
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("grid", game.getGrid().serialized().toString());
        return result;
    }

    public Map<String, String> changeAI(Integer gameId, String type) {
        Game game = games.get(gameId);

        Strategy strategy;
        switch (type) {
            case "BFS":
                strategy = new BFSStrategy();
                break;
            case "DFS":
                strategy = new DFSStrategy();
                break;
            default:
                strategy = new BFSStrategy();
                break;
        }

        game.getHeroSquad().setStrategy(strategy);
        return Map.of("result", "true");
    }

    public Map<String, String> launchGame(Integer game_id) {
        Game game = games.get(game_id);
        if (game != null) {
            game.startNewGame();
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("grid", game.getGrid().serialized().toString());
        result.put("heros", game.getHeroSquad().serialized().toString());
        return result;
    }

    public Map<String, String> nextTurn(Integer game_id) {
        Game game = games.get(game_id);
        if (game != null) {
            game.startNewGame();
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("grid", game.getGrid().serialized().toString());
        result.put("heros", game.getHeroSquad().serialized().toString());
        return result;
    }
}
