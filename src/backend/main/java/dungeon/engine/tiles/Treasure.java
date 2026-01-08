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

    public int getAstarValue(){
        return ASTAR_VALUE;
    }

    public int getPlacementCost(){
        return PLACEMENT_COST;
    }

    /* --- ToString --- */

    @Override
    public String toString() {
        return "treasure";
    }
}
