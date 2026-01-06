package dungeon.engine;

public class TankCreator implements HeroCreator {
    @Override
    public Hero createHero() {
        return new Tank();
    }   
}
