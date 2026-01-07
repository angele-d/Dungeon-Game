package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import dungeon.engine.tiles.Trap;

public class WallTrap extends Trap {
    public WallTrap(Coords coords,int damage,int area) {
        super(coords,damage, area);
    }

    @Override
    public void activateTrap(dungeon.engine.Game game){
        // TODO: Implement WallTrap activation logic
    }

    
    public int getAstarValue(){
        return 3;
    }
}
