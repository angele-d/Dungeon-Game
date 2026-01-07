package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public class Treasure extends Tile {
    public Treasure(Coords coords) {
        super(coords);
    }

    @Override
    public String toString() {
        return "treasure";
    }
}
