package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class Empty extends Tile {

    private int placementCost = 0; //Useless but for consistency
    private int aStarValue = 1;

    /* --- Constructor --- */

    public Empty(Coords coords) {
        super(coords);
    }

    /* --- Getters and Setters --- */

    public int getAstarValue(){
        return aStarValue;
    }

    public int getPlacementCost(){
        return placementCost;
    }
}
