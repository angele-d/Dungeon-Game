package dungeon.engine;

import dungeon.engine.tiles.Empty;
import dungeon.engine.tiles.StartingPoint;
import dungeon.engine.tiles.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Grid {
    int SIZE = 10;
    private Map<Coords, Tile> grid;

    public Grid() {
        this(1);
    }

    public Grid(int seed) {
        this.grid = new HashMap<>();
        Random generator = new Random(seed);
        Coords treasure = new Coords(generator.nextInt(SIZE), generator.nextInt(SIZE));
        Coords startPosition = new Coords(generator.nextInt(SIZE), generator.nextInt(SIZE));
        while (Math.abs(startPosition.x() - treasure.x()) < 2 || Math.abs(startPosition.y() - treasure.y()) < 2) {
            startPosition = new Coords(generator.nextInt(SIZE), generator.nextInt(SIZE));
        }
        grid.put(treasure, new Treasure(treasure));
        grid.put(startPosition, new StartingPoint(startPosition));
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (!grid.containsKey(new Coords(x, y))) {
                    grid.put(new Coords(x, y), new Empty(new Coords(x, y)));
                }
            }
        }
    }

    // @Override
    // public String toString() {
    // return "";
    // }

    public Tile getTile(Coords coords) {
        return this.grid.get(coords);
    }

    public Tile getTreasure() {
        for (Coords coords : grid.keySet()) {
            if (getTile(coords) instanceof Treasure) {
                return getTile(coords);
            }
        }
        return null;
    }

    public Tile getStartingPoint() {
        for (Coords coords : getGrid().keySet()) {
            if (getTile(coords) instanceof StartingPoint) {
                return getTile(coords);
            }
        }
        return null;
    }

    public void setTile(Tile tile) {
        this.grid.put(tile.getCoords(), tile);
    }

    public int getSize() {
        return SIZE;
    }

    public Map<Coords, Tile> getGrid() {
        return this.grid;
    }

    public ArrayList<Coords> getNeighborsCoords(Coords coords) {
        ArrayList<Coords> neighbors = new ArrayList<>();
        if (coords.x() > 0) {
            neighbors.add(new Coords(coords.x() - 1, coords.y()));
        }
        if (coords.x() + 1 < SIZE) {
            neighbors.add(new Coords(coords.x() + 1, coords.y()));
        }
        if (coords.y() > 0) {
            neighbors.add(new Coords(coords.x(), coords.y() - 1));
        }
        if (coords.y() + 1 < SIZE) {
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

    public ArrayList<ArrayList<String>> serialized() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int col = 0; col < SIZE; col++) {
            ArrayList<String> grid_row = new ArrayList<>();
            for (int row = 0; row < SIZE; row++) {
                Tile tile = this.grid.get(new Coords(col, row));
                grid_row.add('"' + tile.toString() + '"');
            }
            result.add(grid_row);
        }
        return result;
    }
}
