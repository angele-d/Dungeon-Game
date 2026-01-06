package dungeon.engine.strategies;

import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Hero;
import dungeon.engine.AI.BFS;
import dungeon.engine.Strategy;

public class BFSStrategy extends Strategy {

    @Override
    public Coords move(Game game, Hero hero) {
        BFS bfs = new BFS(game.getGrid());
        return bfs.search(hero.getCoords());
    }
}
