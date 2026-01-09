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

    /** 
     * String representation of the wall
     * @return String
     */
    @Override
    public String toString() {
        return "wall";
    }
}
