package dungeon.engine.Strategies;

import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Hero;
import dungeon.engine.HeroSquad;
import dungeon.engine.AI.BFS;
import dungeon.engine.Grid;

public class BFSStrategy extends Strategy {

    /** 
     * Create BFS instance (for testing purposes)
     * @param grid
     * @return BFS
     */
    protected BFS createBFS(Grid grid) {
        return new BFS(grid);
    }

    /** 
     * Move the hero using BFS pathfinding
     * @param game
     * @param hero
     * @return Coords
     */
    @Override
    public Coords move(Game game, Hero hero) {
        BFS bfs = createBFS(game.getGrid());
        return bfs.search(hero.getCoords(), game.getHeroSquad());
    }

    /** 
     * Move the hero using BFS pathfinding (for testing purposes)
     * @param bfs
     * @param heroSquad
     * @param hero
     * @return Coords
     */
    public Coords moveWithBFS(BFS bfs, HeroSquad heroSquad, Hero hero) {
        return bfs.search(hero.getCoords(), heroSquad);
    }
}
