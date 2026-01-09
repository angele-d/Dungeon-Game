package dungeon.engine.Creators;

import dungeon.engine.Hero;
import dungeon.engine.Tank;

public class TankCreator implements HeroCreator {
    
    /** 
     * Create a Tank hero
     * @return Hero
     */
    @Override
    public Hero createHero() {
        return new Tank();
    }
}
