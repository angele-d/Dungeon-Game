package dungeon.engine;

public class DwarfCreator implements HeroCreator {
    @Override
    public Hero createHero() {
        return new Dwarf();
    }
}
