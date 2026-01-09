package dungeon.engine.Creators;

import dungeon.engine.Hero;

public interface HeroCreator {

    /** 
     * Create a hero
     * @return Hero
     */
    Hero createHero();
}