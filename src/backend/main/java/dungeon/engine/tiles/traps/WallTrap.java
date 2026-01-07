package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Trap;

public class WallTrap extends Trap {
    public WallTrap(Coords coords) {
        super(coords,10, 1);
    }

    public WallTrap(Coords coords,int damage,int area) {
        super(coords,damage, area);
    }

    @Override
    public String toString() {
        return "walltrap";
    }
}
