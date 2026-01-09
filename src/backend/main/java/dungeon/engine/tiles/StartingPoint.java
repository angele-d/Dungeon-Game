package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class StartingPoint extends Tile {

    public static final int PLACEMENT_COST = 0; // Useless but for consistency
    public static final int ASTAR_VALUE = 1;

/* --- Constructor --- */

    public StartingPoint(Coords coords) {
        super(coords);
    }

/* --- Getters and Setters --- */

    /** 
     * Get the A* value of the starting point
     * @return int
     */
    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    /** 
     * Get the placement cost of the starting point
     * @return int
     */
    @Override
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

/* --- ToString --- */

    /** 
     * String representation of the starting point
     * @return String
     */
    @Override
    public String toString() {
        return "startingpoint";
    }
}
