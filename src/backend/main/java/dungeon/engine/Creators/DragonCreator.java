package dungeon.engine.Creators;

import dungeon.engine.Dragon;
import dungeon.engine.Hero;

public class DragonCreator implements HeroCreator {
    
    /** 
     * Create a Dragon hero
     * @return Hero
     */
    @Override
    public Hero createHero() {
        return new Dragon();
    }
}
