package dungeon.engine;

import dungeon.engine.AI.DFS;
import dungeon.engine.Strategies.DFSStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DFSStrategyTest {

    private DFSStrategy dfsStrategy;

    private final Coords startCoords = new Coords(1, 1);
    private final Coords targetCoords = new Coords(5, 5);
    private final Coords nextStepCoords = new Coords(1, 2);

    @BeforeEach
    void setUp() {
        dfsStrategy = new DFSStrategy();
    }

    @Test
    @DisplayName("1. Cas Nominal : Un chemin est trouvé, le héros doit avancer vers la prochaine étape")
    void testMove_NominalPathFound() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(startCoords);

        DFS testDFS = new DFS(null) {
            @Override
            public ArrayList<Coords> search(Coords start, Hero h, ArrayList<Coords> previousCoords) {
                return nextStepCoords;
            }
        };

        Coords result = dfsStrategy.moveWithDFS(testDFS, hero);

        assertNotNull(result, "La stratégie ne doit pas renvoyer null si un chemin existe");
        assertEquals(nextStepCoords, result, "Le héros doit se déplacer vers la coordonnée renvoyée par le DFS");
    }

    @Test
    @DisplayName("2. Obstacle : L'algorithme contourne (Simulé par le retour du DFS)")
    void testMove_ObstacleAvoidance() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(startCoords);

        Coords detourCoords = new Coords(2, 1);

        DFS testDFS = new DFS(null) {
            @Override
            public ArrayList<Coords> search(Coords start, Hero h, ArrayList<Coords> previousCoords) {
                return detourCoords;
            }
        };

        Coords result = dfsStrategy.moveWithDFS(testDFS, hero);

        assertEquals(detourCoords, result, "La stratégie doit suivre le chemin de contournement calculé par le DFS");
    }

    @Test
    @DisplayName("3. Pas de chemin : La cible est inaccessible")
    void testMove_NoPathAvailable() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(startCoords);

        DFS testDFS = new DFS(null) {
            @Override
            public ArrayList<Coords> search(Coords start, Hero h, ArrayList<Coords> previousCoords) {
                return null;
            }
        };

        Coords result = dfsStrategy.moveWithDFS(testDFS, hero);

        assertNull(result, "Si aucun chemin n'est trouvé, la stratégie doit renvoyer null (ou gérer l'attente)");
    }

    @Test
    @DisplayName("4. Déjà sur place : Le héros est sur la case cible")
    void testMove_AlreadyAtTarget() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(targetCoords);

        DFS testDFS = new DFS(null) {
            @Override
            public ArrayList<Coords> search(Coords start, Hero h, ArrayList<Coords> previousCoords) {
                return targetCoords;
            }
        };

        Coords result = dfsStrategy.moveWithDFS(testDFS, hero);

        assertEquals(targetCoords, result, "Le héros doit rester sur place s'il a atteint l'objectif");
    }

    @Test
    @DisplayName("5. Limites et Intégrité : Vérifier le passage des coordonnées au DFS")
    void testMove_BoundaryAndDataIntegrity() {
        TheMemeMaker hero = new TheMemeMaker();
        hero.setCoords(startCoords);

        final ArrayList<Coords> returned = new ArrayList<Coords>();
        returned.add(new Coords(6, 6));
        returned.add(new Coords(7, 7));

        DFS testDFS = new DFS(null) {
            @Override
            public ArrayList<Coords> search(Coords start, Hero h, ArrayList<Coords> previousCoords) {
                assertEquals(startCoords, start, "Le DFS doit recevoir les coordonnées actuelles du héros");
                return returned;
            }
        };

        Coords result = dfsStrategy.moveWithDFS(testDFS, hero);
        assertEquals(returned, result);
    }
}
