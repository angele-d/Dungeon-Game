package dungeon.engine.Creators;

import dungeon.engine.Dwarf;
import dungeon.engine.Hero;

public class DwarfCreator implements HeroCreator {
    @Override
    public Hero createHero() {
        return new Dwarf();
    }
}
