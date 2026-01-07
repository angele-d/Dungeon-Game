package dungeon.engine.tiles.wall;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Wall;

public class StoneWall extends Wall {

    private int placementCost = 75;
    private int aStarValue = 100;

    /* --- Constructor --- */

    public StoneWall(Coords coords) {
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
