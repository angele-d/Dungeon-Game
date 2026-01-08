package dungeon.engine;

import dungeon.engine.Strategies.*;
import dungeon.engine.tiles.wall.*;
import dungeon.engine.tiles.traps.*;
import dungeon.engine.tiles.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameEngine {
    private static GameEngine gameEngine;
    private final LeaderBoard leaderboard = new LeaderBoard();

    private Map<Integer, Game> games;


    /* --- Constructor --- */

    private GameEngine() {
        games = new HashMap<Integer, Game>();
        for (String saveFile: SaveManager.listSaveFiles()) {
            Game game = new Game();
            try {
                SaveManager.load(game, saveFile);
            } catch (IOException e) {}
            games.put(game.getId(), game);
        }
    }

    /* --- Singleton --- */

    public static GameEngine getInstance() {
        if (gameEngine != null) {
            return gameEngine;
        } else {
            gameEngine = new GameEngine();
            return gameEngine;
        }
    }

    /* --- Getters ---- */

    public LeaderBoard getLeaderBoard() {
        return leaderboard;
    }

    public Game getGame(Integer gameId) {
        Game game;
        if (games.containsKey(gameId)) {
            game = games.get(gameId);
        } else {
            game = newGame(gameId);
        }
        return game;
    }

    public Map<String, String> getGameStats(int gameId) {
        Map<String, String> result = new HashMap<String, String>();
        Game game = games.get(gameId);
        result.put("grid", game.getGrid().serialized().toString());
        result.put("heroes", game.getHeroSquad().serialized().toString());
        result.put("money", Integer.toString(game.getMoney()));
        result.put("score", Integer.toString(game.getScore()));
        return result;
    }

    /* --- Game Initialization --- */

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
                case "startingpoint":
                    tile = new StartingPoint(coords);
                    break;
                case "woodwall":
                    tile = new WoodWall(coords);
                    break;
                case "stonewall":
                    tile = new StoneWall(coords);
                    break;
                case "poisontrap":
                    tile = new PoisonTrap(coords);
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
        result.put("money", String.valueOf(game.getMoney()));
        return result;
    }

    public Map<String, String> changeAI(Integer gameId, String type) {
        Game game = games.get(gameId);

        Strategy strategy;
        switch (type) {
            case "BFS":
                strategy = new BFSStrategy();
                break;
            case "Astar":
                strategy = new AstarStrategy();
                break;
            default:
                strategy = new BFSStrategy();
                break;
        }

        game.getHeroSquad().setStrategy(strategy);
        return Map.of("result", "true");
    }

    public Map<String, String> isSimulationReady(Integer gameId) {
        Game game = games.get(gameId);

        Map<String, String> result = new HashMap<String, String>();
        result.put("result", String.valueOf(game.isSimulationReady()));
        return result;
    }

    public Map<String, String> startSimulation(Integer gameId) {
        Game game = games.get(gameId);
        if (game != null) {
            game.startSimulation();
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("grid", game.getGrid().serialized().toString());
        result.put("heroes", game.getHeroSquad().serialized().toString());
        result.put("money", String.valueOf(game.getMoney()));
        return result;
    }

    public void updateLeaderboard() {
        for (Game game : games.values()) {
            GameResult gameResult = new GameResult(game.getScore(), game.getId(), game.getMoney());
            if (!leaderboard.getResults().contains(gameResult)) {
                leaderboard.addResults(gameResult);
            }
        }
    }

    /* --- Game Management --- */

    public Game newGame() {
        int id = 0;
        while (games.containsKey(id)) {
            id++;
        }
        return newGame(id);
    }

    public Game newGame(int gameId) {
         if (!games.containsKey(gameId)) {
             Game game = new Game(gameId);
             games.put(gameId, game);
             return game;
         } else {
             return null;
         }
    }

    public Map<String, String> endGame(int gameId) {
        Map<String, String> result;
        Game game = games.get(gameId);
        result = getGameStats(gameId);
        GameResult gameResult = new GameResult(game.getScore(), game.getId(), game.getMoney());
        leaderboard.addResults(gameResult);
        game.endSimulation();
        return result;
    }

    public boolean isGameTerminated(int gameId) {
        Game game = games.get(gameId);
        if (game != null) {
            return game.isTerminated();
        }
        // Game not found
        return false;
    }

    public Map<String, String> nextTurn(Integer gameId) {
        Game game = games.get(gameId);
        if (game != null) {
            game.nextTurn();
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("grid", game.getGrid().serialized().toString());
        result.put("heroes", game.getHeroSquad().serialized().toString());
        return result;
    }

    public Map<String, String> getLeaderBoardString() {
        String leaderboardString = "[";
        for (GameResult gameResult: getLeaderBoard().getResults()) {
            leaderboardString = leaderboardString + gameResult.serialized()+",";
        }
        leaderboardString = leaderboardString.substring(0, leaderboardString.length()-1)+"]";
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", leaderboardString);
        return result;
    }
}
