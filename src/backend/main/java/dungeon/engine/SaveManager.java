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

    

    private record DataToSave(int size, Map<Coords, NamedTiles> grid, int score, int money) {}
    private record DataToGet(int size, Map<String, NamedTiles> grid, int score, int money) {}

    static public void save(Game game) throws IOException {

        Grid grid = game.getGrid();

        Map<Coords, NamedTiles> detailedGrid = new HashMap<>();

        int size = grid.SIZE;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Coords coords = new Coords(i, j);
                Tile tile = grid.getTile(coords);
                detailedGrid.put(coords, new NamedTiles(tile));
            }
        }

        DataToSave save = new DataToSave(size, detailedGrid, game.getScore(), game.getMoney());

        Gson gson = new Gson();
        String json = gson.toJson(save);

        Path dir = Path.of("saves");
        Files.createDirectories(dir);

        Path path = dir.resolve("save1.json");
        Files.writeString(path, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    static public void load(Game game, String filename) throws IOException {
        Path dir = Path.of("saves");
        if(!Files.exists(dir)) {
            return;
        }

        Path save =  dir.resolve(filename);
        if(!save.toFile().exists()) {
            return;
        }

        String json = Files.readString(save);

        Gson gson = new Gson();
        DataToGet data = gson.fromJson(json, DataToGet.class);

        Map<String, NamedTiles> gridString = data.grid;


        int SIZE = data.size;
        int score = data.score;
        int money = data.money;

        Map<String, Function<Coords, ? extends Tile>> tileRegistry = Map.of(
                "Empty", Empty::new,
                "Treasure", Treasure::new
        );

        Grid grid = new Grid();
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                Coords coords = new Coords(i, j);
                grid.setTile(tileRegistry.get(gridString.get(coords.toString()).getType()).apply(coords));
            }
        }

        game.setGrid(grid);
        game.setScore(score);
        game.setMoney(money);



    }
}