package dungeon.engine.strategies;

import dungeon.engine.AI.DFS;
import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Hero;
import dungeon.engine.Strategy;

public class DFSStrategy extends Strategy {

    @Override
    public Coords move(Game game, Hero hero) {
        DFS dfs = new DFS(game.getGrid());
        return dfs.search(hero.getCoords());
    }
}
