package dungeon.engine;

public abstract class Strategy {
    public abstract Coords move(Game game, Hero hero);
}
