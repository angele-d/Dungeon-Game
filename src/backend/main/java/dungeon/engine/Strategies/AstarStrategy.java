package dungeon.engine.strategies;

import dungeon.engine.AI.Astar;
import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Hero;
import dungeon.engine.Strategy;

public class AstarStrategy extends Strategy {

    @Override
    public Coords move(Game game, Hero hero) {
        Astar astar = new Astar(game.getGrid());
        return astar.search(hero.getCoords());
    }
}
