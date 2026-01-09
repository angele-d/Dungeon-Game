package dungeon.engine.Creators;

import dungeon.engine.Healer;
import dungeon.engine.Hero;

public class HealerCreator implements HeroCreator {
    
    /** 
     * Create a Healer hero
     * @return Hero
     */
    @Override
    public Hero createHero() {
        return new Healer();
    }
}
