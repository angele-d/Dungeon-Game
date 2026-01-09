package dungeon.engine;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import dungeon.engine.tiles.*;
import dungeon.engine.tiles.traps.*;
import dungeon.engine.tiles.wall.*;

public class SaveManager {

    private static class NamedTiles {
        public String type;

        public NamedTiles(Tile tile) {
            type = tile.getClass().getSimpleName();
        }

        public String getType() {
            return type;
        }
    }

    private record DataToSave(int id, int seed, int size, Map<Coords, NamedTiles> grid, int score, int money) {
    }

    private record DataToGet(int id, int seed, int size, Map<String, NamedTiles> grid, int score, int money) {
    }

/* --- Functions --- */
    
    /** 
     * Saves the current game state to a file.
     * @param game
     * @throws IOException
     */
    static public void save(Game game) throws IOException {

        Grid grid = game.getGrid();

        Map<Coords, NamedTiles> detailedGrid = new HashMap<>();

        int id = game.getId();
        int seed = game.getSeed();
        int size = grid.SIZE;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coords coords = new Coords(i, j);
                Tile tile = grid.getTile(coords);
                detailedGrid.put(coords, new NamedTiles(tile));
            }
        }

        DataToSave save = new DataToSave(id, seed, size, detailedGrid, game.getScore(), game.getMoney());

        Gson gson = new Gson();
        String json = gson.toJson(save);

        Path dir = Path.of("saves");
        Files.createDirectories(dir);

        Path path = dir.resolve("save" + String.valueOf(id) + ".json");
        Files.writeString(path, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /** 
     * Loads a game state from a file.
     * @param game
     * @param filename
     * @throws IOException
     */
    static public void load(Game game, String filename) throws IOException {
        Path dir = Path.of("saves");
        if (!Files.exists(dir)) {
            return;
        }

        Path save = dir.resolve(filename);
        if (!save.toFile().exists()) {
            return;
        }

        String json = Files.readString(save);

        Gson gson = new Gson();
        DataToGet data = gson.fromJson(json, DataToGet.class);

        Map<String, NamedTiles> gridString = data.grid;

        int id = data.id;
        int seed = data.seed;
        int SIZE = data.size;
        int score = data.score;
        int money = data.money;

        Map<String, Function<Coords, ? extends Tile>> tileRegistry = Map.of(
                "Empty", Empty::new,
                "Treasure", Treasure::new,
                "StartingPoint", StartingPoint::new,
                "WoodWall", WoodWall::new,
                "StoneWall", StoneWall::new,
                "Mine", Mine::new,
                "PoisonTrap", PoisonTrap::new,
                "WallTrap", WallTrap::new);

        Grid grid = new Grid();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Coords coords = new Coords(i, j);
                Tile tile = tileRegistry.get(gridString.get(coords.toString()).getType()).apply(coords);
                grid.putTile(tile);
            }
        }

        game.setGrid(grid);
        game.setScore(score);
        game.setMoney(money);
        game.setId(id);
        game.setSeed(seed);

    }

    /** 
     * Lists all save files in the "saves" directory. / Returns an empty list if the directory doesn't exist.
     * @return List<String>
     */
    public static java.util.List<String> listSaveFiles() {
        Path dir = Path.of("saves");
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            return java.util.List.of();
        }
        try (java.util.stream.Stream<Path> stream = Files.list(dir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .toList();
        } catch (IOException e) {
            // throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
        return null;
    }
}
