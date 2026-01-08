package dungeon.engine.Creators;

import dungeon.engine.Hero;
import dungeon.engine.Muggle;

public class MuggleCreator implements HeroCreator {    
    @Override
    public Hero createHero() {
        return new Muggle();
    }
}