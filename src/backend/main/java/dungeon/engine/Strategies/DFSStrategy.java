package dungeon.engine.Strategies;

import dungeon.engine.AI.DFS;
import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Hero;

public class DFSStrategy extends Strategy {

    @Override
    public Coords move(Game game, Hero hero) {
        DFS dfs = new DFS(game.getGrid());
        return dfs.search(hero.getCoords());
    }
}
