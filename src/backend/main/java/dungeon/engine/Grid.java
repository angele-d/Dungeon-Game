package dungeon.engine;

import dungeon.engine.tiles.Empty;
import dungeon.engine.tiles.Treasure;

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


//    @Override
//    public String toString() {
//        return "";
//    }

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

    public Grid clone() {
        Grid newGrid = new Grid();
        for (Map.Entry<Coords, Tile> entry : this.grid.entrySet()) {
            newGrid.setTile(entry.getValue());
        }
        return newGrid;
    }

    public ArrayList serialized() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int col = 0; col < SIZE; col++) {
            ArrayList<String> grid_row = new ArrayList<>();
            for (int row = 0; row < SIZE; row++) {
                Tile tile = this.grid.get(new Coords(col, row));
                grid_row.add(tile.toString());
            }
            result.add(grid_row);
        }
        return result;
    }
}
