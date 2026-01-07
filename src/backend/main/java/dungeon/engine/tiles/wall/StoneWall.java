package dungeon.engine.tiles.wall;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Wall;

public class StoneWall extends Wall {
    public StoneWall(Coords coords) {
        super(coords);
    }
    public int getAstarValue(){
        return 100;
    }
}
