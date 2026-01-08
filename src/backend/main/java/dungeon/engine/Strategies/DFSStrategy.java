package dungeon.engine.Strategies;

import dungeon.engine.AI.DFS;
import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Grid;
import dungeon.engine.Hero;

import java.util.ArrayList;

public class DFSStrategy extends Strategy {
    private ArrayList<Coords> previousCoords;
    private ArrayList<Coords> currentPath;
    // Allow subclasses/tests to override how DFS is created
    protected DFS createDFS(Grid grid) {
        return new DFS(grid);
    }

    @Override
    public Coords move(Game game, Hero hero) {
        if (currentPath == null || currentPath.size() == 0) {
            DFS dfs = createDFS(game.getGrid());
            currentPath = dfs.search(hero.getCoords(), hero, previousCoords);
            previousCoords.add(hero.getCoords());
        }
        Coords result = currentPath.get(0);
        currentPath.remove(0);
        return result;
    }

    // Helper for tests to call DFS directly without needing to mock construction
    public Coords moveWithDFS(DFS dfs, Hero hero) {
        if (currentPath == null || currentPath.size() == 0) {
            currentPath = dfs.search(hero.getCoords(), hero, previousCoords);
            previousCoords.add(hero.getCoords());
        }
        Coords result = currentPath.get(0);
        currentPath.remove(0);
        return result;
    }
}
