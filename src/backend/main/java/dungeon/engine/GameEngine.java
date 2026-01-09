package dungeon.engine;

import dungeon.engine.Strategies.*;
import dungeon.engine.tiles.wall.*;
import dungeon.engine.tiles.traps.*;
import dungeon.engine.tiles.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameEngine {
    private static GameEngine gameEngine;
    private final LeaderBoard leaderboard = new LeaderBoard();
    public Map<Integer, Game> games;

/* --- Constructor --- */

    private GameEngine() {
        games = new HashMap<Integer, Game>();
        for (String saveFile : Objects.requireNonNull(SaveManager.listSaveFiles())) {
            Game game = new Game();
            try {
                SaveManager.load(game, saveFile);
            } catch (IOException e) {
                System.out.println("Failed to load save file: " + saveFile);
            }
            games.put(game.getId(), game);
        }
        updateLeaderboard();
    }

/* --- Singleton --- */

    /** 
     * Gets the singleton instance of the GameEngine.
     * @return GameEngine
     */
    public static GameEngine getInstance() {
        if (gameEngine != null) {
            return gameEngine;
        } else {
            gameEngine = new GameEngine();
            return gameEngine;
        }
    }

/* --- Getters ---- */

    /** 
     * Gets the leaderboard.
     * @return LeaderBoard
     */
    public LeaderBoard getLeaderBoard() {
        return leaderboard;
    }

    /** 
     * Gets a game by its ID.
     * @param gameId
     * @return Game
     */
    public Game getGame(Integer gameId) {
        Game game;
        if (games.containsKey(gameId)) {
            game = games.get(gameId);
        } else {
            game = newGame(gameId);
        }
        return game;
    }

    /** 
     * Advances the game to the next wave.
     * @param gameId
     * @return Game
     * @throws Exception
     */
    public Game nextWave(Integer gameId) throws Exception {
        Game game;
        if (games.containsKey(gameId)) {
            game = games.get(gameId);
            game.nextWave();
            return game;
        } else {
            throw new Exception("Game doesn't exist");
        }
    }

    /** 
     * Gets the statistics of a game.
     * @param gameId
     * @return Map<String, String>
     */
    public Map<String, String> getGameStats(int gameId) {
        Map<String, String> result = new HashMap<String, String>();
        Game game = games.get(gameId);
        result.put("grid", game.getGrid().serialized().toString());
        result.put("heroes", game.getHeroSquad().serialized().toString());
        result.put("money", Integer.toString(game.getMoney()));
        result.put("score", Integer.toString(game.getScore()));
        result.put("wave", Integer.toString(game.getWave()));
        return result;
    }

/* --- Game Initialization --- */

    /** 
     * Places a tile on the game's grid.
     * @param gameId
     * @param coords
     * @param type
     * @return Map<String, String>
     */
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

    /** 
     * Changes the AI strategy for all heroes in the game.
     * @param gameId
     * @param type
     * @return Map<String, String>
     */
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
        game.setStrategy(strategy);
        String result = "true";
        for (Hero hero: game.getHeroSquad().getHeroes()) {
            if (!hero.getStrategy().equals(strategy)) {
                result = "false";
                break;
            }
        }
        return Map.of("result", result);
    }

    /** 
     * Checks if the simulation is ready to start.
     * @param gameId
     * @return Map<String, String>
     */
    public Map<String, String> isSimulationReady(Integer gameId) {
        Game game = games.get(gameId);

        Map<String, String> result = new HashMap<String, String>();
        result.put("result", String.valueOf(game.isSimulationReady()));
        return result;
    }

    /** 
     * Starts the simulation for the game.
     * @param gameId
     * @return Map<String, String>
     */
    public Map<String, String> startSimulation(Integer gameId) {
        Game game = games.get(gameId);
        if (game != null) {
            game.startSimulation();
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("grid", game.getGrid().serialized().toString());
        result.put("heroes", game.getHeroSquad().serialized().toString());
        result.put("money", String.valueOf(game.getMoney()));
        result.put("score", String.valueOf(game.getScore()));
        return result;
    }

    /** 
     * Updates the leaderboard with current game results.
     */
    public void updateLeaderboard() {
        for (Game game : games.values()) {
            GameResult gameResult = new GameResult(game.getScore(), game.getId(), game.getMoney());
            if (!leaderboard.getResults().contains(gameResult)) {
                leaderboard.addResults(gameResult);
            }
        }
    }

/* --- Game Management --- */

    /** 
     * Creates a new game with a unique ID.
     * @return Game
     */
    public Game newGame() {
        int id = 0;
        while (games.containsKey(id)) {
            id++;
        }
        return newGame(id);
    }

    /** 
     * Creates a new game with a specific ID.
     * @param gameId
     * @return Game
     */
    public Game newGame(int gameId) {
        if (!games.containsKey(gameId)) {
            Game game = new Game(gameId);
            games.put(gameId, game);
            return game;
        } else {
            return null;
        }
    }

    /** 
     * Loads a saved game from a file.
     * @param gameId
     * @param loadId
     */
    public void loadGame(int gameId, int loadId) {
        Game game = games.get(gameId);
        System.out.println("Loading game " + gameId);
        try {
            SaveManager.load(game, "save" + String.valueOf(loadId) + ".json");
        } catch (IOException e) {
            System.err.println("Error loading game" + String.valueOf(loadId) + ".json");
        }
        game.setId(gameId);
    }

    /** 
     * Ends the game simulation and returns the final statistics.
     * @param gameId
     * @return Map<String, String>
     */
    public Map<String, String> endGame(int gameId) {
        Map<String, String> result;
        Game game = games.get(gameId);
        result = getGameStats(gameId);
        game.endSimulation();
        return result;
    }

    /** 
     * Checks if the game is terminated.
     * @param gameId
     * @return boolean
     */
    public boolean isGameTerminated(int gameId) {
        Game game = games.get(gameId);
        if (game != null) {
            boolean terminated = game.isGameTerminated();
            if (terminated) {
                GameResult gameResult = new GameResult(game.getScore(), game.getId(), game.getMoney());
                leaderboard.addResults(gameResult);
                updateLeaderboard();

                try {
                    SaveManager.save(game);
                } catch (IOException e) {
                    System.out.println("Failed to save game" + String.valueOf(gameId));
                }
            }
            return terminated;
        }
        // Game not found
        return false;
    }

    /**
     * Returns if game is being simulated
     * @return bool
     */
    public boolean isSimulationRunning(int gameId) {
        if (games.containsKey(gameId)) {
            Game game = games.get(gameId);
            return game.isSimulationRunning();
        } else {
            return false;
        }
    }

    /** 
     * Checks if the wave is terminated.
     * @param gameId
     * @return boolean
     */
    public boolean isWaveTerminated(int gameId) {
        Game game = games.get(gameId);
        if (game != null) {
            boolean terminated = game.isWaveTerminated();
            if (terminated) {
                try {
                    nextWave(game.getId());
                    return true;
                } catch (Exception e) {
                    System.out.println("Failed to go next wave" + String.valueOf(gameId));
                }
            }
        }
        // Game not found
        return false;
    }

    /** 
     * Advances the game to the next turn.
     * @param gameId
     * @return Map<String, String>
     */
    public Map<String, String> nextTurn(Integer gameId) {
        Game game = games.get(gameId);
        if (game != null) {
            game.nextTurn();
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("grid", game.getGrid().serialized().toString());
        result.put("heroes", game.getHeroSquad().serialized().toString());
        result.put("score", String.valueOf(game.getScore()));
        return result;
    }

    /** 
     * Gets the leaderboard as a serialized string.
     * @return Map<String, String>
     */
    public Map<String, String> getLeaderBoardString() {
        String leaderboardString = "[";
        for (GameResult gameResult : getLeaderBoard().getResults()) {
            leaderboardString = leaderboardString + gameResult.serialized() + ",";
        }
        leaderboardString = leaderboardString.substring(0, leaderboardString.length() - 1) + "]";
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", leaderboardString);
        return result;
    }
}
