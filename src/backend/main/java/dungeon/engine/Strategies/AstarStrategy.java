package dungeon.engine.Strategies;

import dungeon.engine.AI.Astar;
import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Hero;

public class AstarStrategy extends Strategy {

    /** 
     * Move the hero using A* pathfinding
     * @param game
     * @param hero
     * @return Coords
     */
    @Override
    public Coords move(Game game, Hero hero) {
        Astar astar = new Astar(game.getGrid());
        return astar.search(hero.getCoords(), game.getHeroSquad());
    }
}
