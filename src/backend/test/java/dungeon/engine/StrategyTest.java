package dungeon.engine;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.Strategies.AstarStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import dungeon.engine.AI.BFS;
import dungeon.engine.tiles.Treasure;

public class StrategyTest {
    
    private Grid grid;
    
    @BeforeEach
    void setUp() {
        grid = new Grid();
    }
    
    @Test
    void testBFSFindsDirectPath() {
        Hero hero = new TheMemeMaker();
        grid.setTile(new Treasure(new Coords(0, 3)));
        BFS bfs = new BFS(grid);
        
        Coords next = bfs.search(new Coords(0, 0), hero);
        assertEquals(new Coords(0, 1), next);
        
        next = bfs.search(new Coords(0, 1), hero);
        assertEquals(new Coords(0, 2), next);
        
        next = bfs.search(new Coords(0, 2), hero);
        assertEquals(new Coords(0, 3), next);
    }
    
    @Test
    void testBFSReturnsNullWhenNoTreasure() {
        Hero hero = new TheMemeMaker();
        BFS bfs = new BFS(grid);
        
        Coords next = bfs.search(new Coords(0, 0), hero);
        assertNull(next);
    }

    
    @Test
    void testBFSFromTreasureLocation() {
        Hero hero = new TheMemeMaker();
        Coords treasureCoords = new Coords(3, 3);
        grid.setTile(new Treasure(treasureCoords));
        BFS bfs = new BFS(grid);
        
        Coords next = bfs.search(treasureCoords, hero);
        assertEquals(treasureCoords, next);
    }

    
    @Test
    void testBFSConsistency() {
        Hero hero = new TheMemeMaker();
        // Test that BFS returns same result for same input
        grid.setTile(new Treasure(new Coords(4, 4)));
        BFS bfs = new BFS(grid);
        
        Coords start = new Coords(2, 2);
        Coords result1 = bfs.search(start, hero);
        Coords result2 = bfs.search(start, hero);
        
        assertEquals(result1, result2, "BFS should be deterministic");
    }

    @Test
    void testStrategySwitch() {
        Game game = GameEngine.getInstance().newGame();
        GameEngine.getInstance().placeTile(game.getId(), new Coords(0, 0), "startingpoint");
        GameEngine.getInstance().placeTile(game.getId(), new Coords(1, 1), "treasure");

        assertEquals(GameEngine.getInstance().isSimulationReady(game.getId()).get("result"), "true");

        GameEngine.getInstance().startSimulation(game.getId());
        GameEngine.getInstance().changeAI(game.getId(), "Astar");

        for (Hero hero: GameEngine.getInstance().getGame(game.getId()).getHeroSquad().getHeroes()) {
            assertTrue(hero.strategy instanceof AstarStrategy);
        }
    }
}
