package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class Empty extends Tile {

    private static final int PLACEMENT_COST = 0; //Useless but for consistency
    private static final int ASTAR_VALUE = 1;

    /* --- Constructor --- */

    public Empty(Coords coords) {
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
        return "empty";
    }
}
