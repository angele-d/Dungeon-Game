package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class Treasure extends Tile {

    private int placementCost = 0;
    private int aStarValue = 0;

    /* --- Constructor --- */

    public Treasure(Coords coords) {
        super(coords);
    }

    /* --- Getters and Setters --- */

    public int getAstarValue(){
        return aStarValue;
    }

    public int getPlacementCost(){
        return placementCost;
    }

    @Override
    public String toString() {
        return "treasure";
    }
}
