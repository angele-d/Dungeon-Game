package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.Strategies.BFSStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("1. Cas Nominal : Un chemin est trouvé, le héros doit avancer vers la prochaine étape")
    void testMove_NominalPathFound() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(startCoords);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, Hero h) {
                return nextStepCoords;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, hero);

        assertNotNull(result, "La stratégie ne doit pas renvoyer null si un chemin existe");
        assertEquals(nextStepCoords, result, "Le héros doit se déplacer vers la coordonnée renvoyée par le BFS");
    }

    @Test
    @DisplayName("2. Obstacle : L'algorithme contourne (Simulé par le retour du BFS)")
    void testMove_ObstacleAvoidance() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(startCoords);

        Coords detourCoords = new Coords(2, 1);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, Hero h) {
                return detourCoords;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, hero);

        assertEquals(detourCoords, result, "La stratégie doit suivre le chemin de contournement calculé par le BFS");
    }

    @Test
    @DisplayName("3. Pas de chemin : La cible est inaccessible")
    void testMove_NoPathAvailable() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(startCoords);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, Hero h) {
                return null;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, hero);

        assertNull(result, "Si aucun chemin n'est trouvé, la stratégie doit renvoyer null (ou gérer l'attente)");
    }

    @Test
    @DisplayName("4. Déjà sur place : Le héros est sur la case cible")
    void testMove_AlreadyAtTarget() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(targetCoords);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, Hero h) {
                return targetCoords;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, hero);

        assertEquals(targetCoords, result, "Le héros doit rester sur place s'il a atteint l'objectif");
    }

    @Test
    @DisplayName("5. Limites et Intégrité : Vérifier le passage des coordonnées au BFS")
    void testMove_BoundaryAndDataIntegrity() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(startCoords);

        final Coords returned = new Coords(10, 10);

        BFS testBFS = new BFS(null) {
            @Override
            public Coords search(Coords start, Hero h) {
                // ensure the start coordinate passed is the hero's coordinate
                assertEquals(startCoords, start, "Le BFS doit recevoir les coordonnées actuelles du héros");
                return returned;
            }
        };

        Coords result = bfsStrategy.moveWithBFS(testBFS, hero);
        assertEquals(returned, result);
    }
}
