package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class Treasure extends Tile {

    private static final int PLACEMENT_COST = 0;
    private static final int ASTAR_VALUE = 0;

/* --- Constructor --- */

    public Treasure(Coords coords) {
        super(coords);
    }
    
/* --- Getters and Setters --- */

    /** 
     * Get the A* value of the treasure
     * @return int
     */
    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    /** 
     * Get the placement cost of the treasure
     * @return int
     */
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

    
/* --- ToString --- */

    /** 
     * String representation of the treasure
     * @return String
     */
    @Override
    public String toString() {
        return "treasure";
    }
}
