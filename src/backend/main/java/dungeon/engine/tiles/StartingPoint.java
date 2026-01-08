package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class StartingPoint extends Tile {

    public static final int PLACEMENT_COST = 0; //Useless but for consistency
    public static final int ASTAR_VALUE = 1;

    /* --- Constructor --- */

    public StartingPoint(Coords coords) {
        super(coords);
    }

    /* --- Getters and Setters --- */

    public int getAstarValue(){
        return ASTAR_VALUE;
    }

    @Override
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

    /* --- ToString --- */

    @Override
    public String toString() {
        return "startingpoint";
    }
}
