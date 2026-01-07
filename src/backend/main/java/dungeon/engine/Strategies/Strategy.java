package dungeon.engine.Strategies;

import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Hero;

public abstract class Strategy {
    public abstract Coords move(Game game, Hero hero);
}
