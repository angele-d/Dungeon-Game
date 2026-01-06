package dungeon.engine;

public abstract class Strategy {
    private Strategy strategy;
    public abstract Coords move(Game game, Hero hero);
}
