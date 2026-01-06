package dungeon.engine;

import java.util.HashMap;
import java.util.Map;


public class Grid {
    int SIZE = 15;
    private Map<Coords, Tile> grid;

    public Grid() {
        this.grid = new HashMap<>();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                grid.put(new Coords(x, y), new Tile());
            }
        }
    }

    public Tile getTile(Coords coords) {
        return this.grid.get(coords);
    }

    public void setTile(Coords coords, Tile tile) {
        this.grid.put(coords, tile);
    }

    public Map<Coords, Tile> getGrid() {
        return this.grid;
    }
}
