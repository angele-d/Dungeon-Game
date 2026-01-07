package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Trap;

public class Mine extends Trap {
    public Mine(Coords coords) {
        super(coords, 50, 2);
    }

    public Mine(Coords coords, int damage, int area) {
        super(coords, damage, area);
    }

    @Override
    public String toString() {
        return "mine";
    }
}
