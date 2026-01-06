package dungeon.engine;

public class TheMemeMakerCreator implements HeroCreator {    
    @Override
    public Hero createHero() {
        return new TheMemeMaker();
    }
}