package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;
import jdk.jshell.spi.ExecutionControl;

public abstract class Wall extends Tile {

    public Wall(Coords coords) {
        super(coords);
    }
}
