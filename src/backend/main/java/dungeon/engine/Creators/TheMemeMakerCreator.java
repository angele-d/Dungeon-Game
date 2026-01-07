package dungeon.engine.Creators;

import dungeon.engine.Hero;
import dungeon.engine.TheMemeMaker;

public class TheMemeMakerCreator implements HeroCreator {    
    @Override
    public Hero createHero() {
        return new TheMemeMaker();
    }
}