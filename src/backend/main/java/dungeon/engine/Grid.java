package dungeon.engine;

import dungeon.engine.tiles.Empty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Grid {
    int SIZE = 8;
    private Map<Coords, Tile> grid;

    public Grid() {
        this.grid = new HashMap<>();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                grid.put(new Coords(x, y), new Empty(new Coords(x, y)));
            }
        }
    }

    public Tile getTile(Coords coords) {
        return this.grid.get(coords);
    }

    public void setTile(Tile tile) {
        this.grid.put(tile.getCoords(), tile);
    }

    public Map<Coords, Tile> getGrid() {
        return this.grid;
    }

    public ArrayList<Coords> getNeighborsCoords(Coords coords) {
        ArrayList<Coords> neighbors = new ArrayList<>();
        if (coords.x() > 0) {
            neighbors.add(new Coords(coords.x() - 1, coords.y()));
        }
        if (coords.x() + 1 < SIZE - 1) {
            neighbors.add(new Coords(coords.x() + 1, coords.y()));
        }
        if (coords.y() > 0) {
            neighbors.add(new Coords(coords.x(), coords.y() - 1));
        }
        if (coords.y() + 1 < SIZE - 1) {
            neighbors.add(new Coords(coords.x(), coords.y() + 1));
        }
        return neighbors;
    }
}
