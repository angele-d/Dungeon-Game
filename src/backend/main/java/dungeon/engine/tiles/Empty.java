package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class Empty extends Tile {
    public static String type = "empty";

    public Empty(Coords coords) {
        super(coords);
    }

}
