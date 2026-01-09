package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public abstract class Wall extends Tile {

    /* --- Constructor --- */

    public Wall(Coords coords) {
        super(coords);
    }

    /* --- Abstract functions --- */

    public abstract int getAstarValue();

    public abstract int getPlacementCost();

    /* --- ToString --- */

    @Override
    public String toString() {
        return "wall";
    }
}
