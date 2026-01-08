package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.Strategies.BFSStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BFSStrategyTest {

    private BFSStrategy bfsStrategy;

    private final Coords startCoords = new Coords(1, 1);
    private final Coords targetCoords = new Coords(5, 5);
    private final Coords nextStepCoords = new Coords(1, 2);

    @BeforeEach
    void setUp() {
        bfsStrategy = new BFSStrategy();
    }

    @Test
    void testMoveWhenPathFound() {
        Muggle hero = new Muggle();
        hero.setCoords(startCoords);
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, HeroSquad heroSquad) {
                return nextStepCoords;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, heroSquad, hero);

        assertNotNull(result);
        assertEquals(nextStepCoords, result);
    }

    @Test
    void testMoveObstacleAvoidance() {
        Muggle hero = new Muggle();
        hero.setCoords(startCoords);
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);

        Coords detourCoords = new Coords(2, 1);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, HeroSquad heroSquad) {
                return detourCoords;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, heroSquad, hero);

        assertEquals(detourCoords, result);
    }

    @Test
    void testMoveNoPathAvailable() {
        Muggle hero = new Muggle();
        hero.setCoords(startCoords);
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, HeroSquad heroSquad) {
                return null;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, heroSquad, hero);

        assertNull(result);
    }

    @Test
    void testMoveAlreadyAtTarget() {
        Muggle hero = new Muggle();
        hero.setCoords(targetCoords);
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, HeroSquad heroSquad) {
                return targetCoords;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, heroSquad, hero);

        assertEquals(targetCoords, result);
    }

    @Test
    void testMoveBoundaryAndDataIntegrity() {
        Muggle hero = new Muggle();
        hero.setCoords(startCoords);
        HeroSquad heroSquad = new HeroSquad();
        heroSquad.addHero(hero);

        final Coords returned = new Coords(10, 10);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, HeroSquad heroSquad) {
                // ensure the start coordinate passed is the hero's coordinate
                assertEquals(startCoords, start);
                return returned;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, heroSquad, hero);
        assertEquals(returned, result);
    }
}
