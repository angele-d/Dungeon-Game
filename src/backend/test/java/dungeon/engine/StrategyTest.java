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
        Hero hero = new Muggle();
        hero.setCoords(new Coords(0, 0));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        grid.setTile(new Treasure(new Coords(0, 3)));
        BFS bfs = new BFS(grid);

        Coords next = bfs.search(new Coords(0, 0), heroSquad);
        assertEquals(new Coords(0, 1), next);

        next = bfs.search(new Coords(0, 1), heroSquad);
        assertEquals(new Coords(0, 2), next);

        next = bfs.search(new Coords(0, 2), heroSquad);
        assertEquals(new Coords(0, 3), next);
    }

    @Test
    void testBFSReturnsStartWhenNoTreasure() {
        Hero hero = new Muggle();
        hero.setCoords(new Coords(0, 0));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        BFS bfs = new BFS(grid);

        Coords next = bfs.search(new Coords(0, 0), heroSquad);
        assertEquals(next, new Coords(0, 0));
    }

    @Test
    void testBFSFromTreasureLocation() {
        Hero hero = new Muggle();
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        Coords treasureCoords = new Coords(3, 3);
        grid.setTile(new Treasure(treasureCoords));
        BFS bfs = new BFS(grid);

        Coords next = bfs.search(treasureCoords, heroSquad);
        assertEquals(treasureCoords, next);
    }

    @Test
    void testBFSConsistency() {
        Hero hero = new Muggle();
        hero.setCoords(new Coords(2, 2));
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);
        // Test that BFS returns same result for same input
        grid.setTile(new Treasure(new Coords(4, 4)));
        BFS bfs = new BFS(grid);

        Coords start = new Coords(2, 2);
        Coords result1 = bfs.search(start, heroSquad);
        Coords result2 = bfs.search(start, heroSquad);

        assertEquals(result1, result2);
    }

    @Test
    void testStrategySwitch() {
        Game game = GameEngine.getInstance().newGame();
        Hero hero1 = new Muggle();
        hero1.setCoords(new Coords(0, 0));
        game.getHeroSquad().addHero(hero1);

        GameEngine.getInstance().placeTile(game.getId(), new Coords(0, 0), "startingpoint");
        GameEngine.getInstance().placeTile(game.getId(), new Coords(1, 1), "treasure");

        assertEquals(GameEngine.getInstance().isSimulationReady(game.getId()).get("result"), "true");

        GameEngine.getInstance().startSimulation(game.getId());
        GameEngine.getInstance().changeAI(game.getId(), "Astar");

        for (Hero hero : GameEngine.getInstance().getGame(game.getId()).getHeroSquad().getHeroes()) {
            assertInstanceOf(AstarStrategy.class, hero.strategy);
        }
    }
}
