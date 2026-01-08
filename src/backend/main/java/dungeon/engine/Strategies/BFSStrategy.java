package dungeon.engine.Strategies;

import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Hero;
import dungeon.engine.AI.BFS;
import dungeon.engine.Grid;

public class BFSStrategy extends Strategy {

    // New: allow subclasses/tests to override how BFS is created
    protected BFS createBFS(Grid grid) {
        return new BFS(grid);
    }

    @Override
    public Coords move(Game game, Hero hero) {
        BFS bfs = createBFS(game.getGrid());
        return bfs.search(hero.getCoords(), hero);
    }

    // New: helper method for tests to call BFS directly without needing Game/Grid
    public Coords moveWithBFS(BFS bfs, Hero hero) {
        return bfs.search(hero.getCoords(), hero);
    }
}
