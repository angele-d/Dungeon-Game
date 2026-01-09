package dungeon.engine.tiles.wall;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Wall;

public class StoneWall extends Wall {

    private static final int PLACEMENT_COST = 300;
    private static final int ASTAR_VALUE = 200000;

/* --- Constructor --- */

    public StoneWall(Coords coords) {
        super(coords);
    }

/* --- Getters and Setters --- */

    /** 
     * Get the A* value of the stone wall
     * @return int
     */
    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    /** 
     * Get the placement cost of the stone wall
     * @return int
     */
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

/* --- ToString --- */

    /** 
     * String representation of the stone wall
     * @return String
     */
    @Override
    public String toString() {
        return "stonewall";
    }
}
