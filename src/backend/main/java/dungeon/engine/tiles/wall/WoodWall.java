package dungeon.engine.tiles.wall;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Wall;

public class WoodWall extends Wall {

    private int placementCost = 50;
    private int aStarValue = 50;

    /* --- Constructor --- */

    public WoodWall(Coords coords) {
        super(coords);
    }

    /* --- Getters and Setters --- */

    public int getAstarValue(){
        return aStarValue;
    }

    public int getPlacementCost(){
        return placementCost;
    }
    public void setPlacementCost(int cost){
        this.placementCost = cost;
    }
}
