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

/* --- Constructors --- */

    public Grid() {
        this(1);
    }

    public Grid(Grid grid) {
        this.grid = Map.copyOf(grid.getGrid());
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

/* --- Getters and Setters --- */

    /** 
     * Gets the tile at the specified coordinates.
     * @param coords
     * @return Tile
     */
    public Tile getTile(Coords coords) {
        return this.grid.get(coords);
    }
    /** 
     * Sets the tile at the specified coordinates.
     * @param tile
     * @return boolean
     */
    public boolean setTile(Tile tile) {
        if (grid.get(tile.getCoords()) instanceof Treasure || grid.get(tile.getCoords()) instanceof StartingPoint) {
            return false;
        }
        this.grid.put(tile.getCoords(), tile);
        return true;
    }

    /** 
     * Gets the treasure tile in the grid.
     * @return Tile
     */
    public Tile getTreasure() {
        for (Coords coords : grid.keySet()) {
            if (getTile(coords) instanceof Treasure) {
                return getTile(coords);
            }
        }
        return null;
    }

    /** 
     * Gets the starting point tile in the grid.
     * @return Tile
     */
    public Tile getStartingPoint() {
        for (Coords coords : getGrid().keySet()) {
            if (getTile(coords) instanceof StartingPoint) {
                return getTile(coords);
            }
        }
        return null;
    }

    /** 
     * Gets the size of the grid.
     * @return int
     */
    public int getSize() {
        return SIZE;
    }

    /** 
     * Gets the entire grid as a map of coordinates to tiles.
     * @return Map<Coords, Tile>
     */
    public Map<Coords, Tile> getGrid() {
        return this.grid;
    }

    /** 
     * Gets the coordinates of neighboring tiles.
     * @param coords
     * @return ArrayList<Coords>
     */
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

/* --- Functions --- */    

    /** 
     * Clones the grid.
     * @return Grid
     */
    public Grid clone() {
        Grid newGrid = new Grid(this);
        return newGrid;
    }

    /** 
     * Serializes the grid to a 2D array representation.
     * @return ArrayList<ArrayList<String>>
     */
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
