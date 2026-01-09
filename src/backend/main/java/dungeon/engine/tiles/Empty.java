package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class Empty extends Tile {

    private static final int PLACEMENT_COST = 0; // Useless but for consistency
    private static final int ASTAR_VALUE = 1;

/* --- Constructor --- */

    public Empty(Coords coords) {
        super(coords);
    }

/* --- Getters and Setters --- */

    /** 
     * Get the A* value of the empty tile
     * @return int
     */
    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    /** 
     * Get the placement cost of the empty tile
     * @return int
     */
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

/* --- ToString --- */

    /** 
     * String representation of the empty tile
     * @return String
     */
    @Override
    public String toString() {
        return "empty";
    }
}
