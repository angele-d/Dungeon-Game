package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class Empty extends Tile {

    public Empty(Coords coords) {
        super(coords);
    }

    public int getAstarValue(){
        return 1;
    }

}
