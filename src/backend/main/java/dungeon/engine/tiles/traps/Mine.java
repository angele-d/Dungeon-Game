package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Trap;

public class Mine extends Trap {
    public Mine(Coords coords,int damage,int area) {
        super(coords, damage, area);
    }
}
