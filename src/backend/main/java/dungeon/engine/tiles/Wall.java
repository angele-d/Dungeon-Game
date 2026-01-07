package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;
import jdk.jshell.spi.ExecutionControl;

public abstract class Wall extends Tile {

    /* --- Constructor --- */

    public Wall(Coords coords) {
        super(coords);
    }

    /* --- Abstract functions --- */

    public abstract int getAstarValue();

    public abstract int getPlacementCost();
    public abstract void setPlacementCost(int cost);

}
