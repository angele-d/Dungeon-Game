package dungeon.engine;

public class HealerCreator implements HeroCreator {
    @Override
    public Hero createHero() {
        return new Healer();
    }
}
