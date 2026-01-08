package dungeon.engine.tiles.wall;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Wall;

public class WoodWall extends Wall {

    private static final int PLACEMENT_COST = 50;
    private static final int ASTAR_VALUE = 150000;

    /* --- Constructor --- */

    public WoodWall(Coords coords) {
        super(coords);
    }

    /* --- Getters and Setters --- */

    public int getAstarValue(){
        return ASTAR_VALUE;
    }

    public int getPlacementCost(){
        return PLACEMENT_COST;
    }

    /* --- ToString --- */
    
    @Override
    public String toString() {
        return "woodwall";
    }
}
