package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class StartingPoint extends Tile {

    public StartingPoint(Coords coords) {
        super(coords);
    }

    public int getAstarValue(){
        return 1;
    }

    @Override
    public int getPlacementCost() {
        return 0;
        //TODO price
    }
}
