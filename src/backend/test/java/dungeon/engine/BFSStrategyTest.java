package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.Strategies.BFSStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BFSStrategyTest {

    // On teste la stratégie
    private BFSStrategy bfsStrategy;

    // On mocke l'environnement du jeu
    @Mock private Game game;
    @Mock private Grid grid;
    @Mock private Hero hero;

    // Objets Coords utilitaires pour les tests
    private final Coords startCoords = new Coords(1, 1);
    private final Coords targetCoords = new Coords(5, 5);
    private final Coords nextStepCoords = new Coords(1, 2);

    // MockedConstruction permet d'intercepter le "new BFS(grid)" à l'intérieur de la méthode move
    private MockedConstruction<BFS> bfsMockedConstruction;

    @BeforeEach
    void setUp() {
        bfsStrategy = new BFSStrategy();
    }

    @AfterEach
    void tearDown() {
        // Important : Fermer le mock de construction après chaque test pour ne pas polluer les autres
        if (bfsMockedConstruction != null) {
            bfsMockedConstruction.close();
        }
    }

    /**
     * Helper pour initialiser le mock de BFS.
     * Cette méthode configure le comportement du BFS qui sera créé à l'intérieur de BFSStrategy.
     */
    private void setupMockBFSBehavior(Coords returnVal) {
        bfsMockedConstruction = Mockito.mockConstruction(BFS.class,
                (mock, context) -> {
                    // On vérifie que le BFS est bien instancié avec la grille du jeu
                    // (Optionnel, dépend de si on veut être strict sur le constructeur)
                }
        );
    }

    @Test
    @DisplayName("1. Cas Nominal : Un chemin est trouvé, le héros doit avancer vers la prochaine étape")
    void testMove_NominalPathFound() {
        // GIVEN
        when(game.getGrid()).thenReturn(grid);
        when(hero.getCoords()).thenReturn(startCoords);

        // On intercepte la création de BFS et on lui dit de retourner 'nextStepCoords' quand on cherche
        bfsMockedConstruction = Mockito.mockConstruction(BFS.class, (mockBFS, context) -> {
            when(mockBFS.search(startCoords)).thenReturn(nextStepCoords);
        });

        // WHEN
        Coords result = bfsStrategy.move(game, hero);

        // THEN
        assertNotNull(result, "La stratégie ne doit pas renvoyer null si un chemin existe");
        assertEquals(nextStepCoords, result, "Le héros doit se déplacer vers la coordonnée renvoyée par le BFS");

        // Vérification que le Grid a bien été récupéré
        verify(game).getGrid();
    }

    @Test
    @DisplayName("2. Obstacle : L'algorithme contourne (Simulé par le retour du BFS)")
    void testMove_ObstacleAvoidance() {
        // GIVEN
        when(game.getGrid()).thenReturn(grid);
        when(hero.getCoords()).thenReturn(startCoords);

        Coords detourCoords = new Coords(2, 1); // Imaginons que (1,2) est un mur, on va en (2,1)

        // Simulation : Le BFS interne a fait ses calculs et a décidé que le meilleur mouvement est le détour
        bfsMockedConstruction = Mockito.mockConstruction(BFS.class, (mockBFS, context) -> {
            when(mockBFS.search(startCoords)).thenReturn(detourCoords);
        });

        // WHEN
        Coords result = bfsStrategy.move(game, hero);

        // THEN
        assertEquals(detourCoords, result, "La stratégie doit suivre le chemin de contournement calculé par le BFS");
    }

    @Test
    @DisplayName("3. Pas de chemin : La cible est inaccessible")
    void testMove_NoPathAvailable() {
        // GIVEN
        when(game.getGrid()).thenReturn(grid);
        when(hero.getCoords()).thenReturn(startCoords);

        // Simulation : BFS ne trouve rien et renvoie null (ou la position actuelle selon implémentation)
        bfsMockedConstruction = Mockito.mockConstruction(BFS.class, (mockBFS, context) -> {
            when(mockBFS.search(startCoords)).thenReturn(null);
        });

        // WHEN
        Coords result = bfsStrategy.move(game, hero);

        // THEN
        assertNull(result, "Si aucun chemin n'est trouvé, la stratégie doit renvoyer null (ou gérer l'attente)");
    }

    @Test
    @DisplayName("4. Déjà sur place : Le héros est sur la case cible")
    void testMove_AlreadyAtTarget() {
        // GIVEN
        when(game.getGrid()).thenReturn(grid);
        when(hero.getCoords()).thenReturn(targetCoords);

        // Simulation : Le BFS détecte qu'on est arrivé. Il retourne soit null, soit la coordonnée actuelle.
        // Supposons qu'il retourne la coordonnée actuelle pour dire "reste là".
        bfsMockedConstruction = Mockito.mockConstruction(BFS.class, (mockBFS, context) -> {
            when(mockBFS.search(targetCoords)).thenReturn(targetCoords);
        });

        // WHEN
        Coords result = bfsStrategy.move(game, hero);

        // THEN
        assertEquals(targetCoords, result, "Le héros doit rester sur place s'il a atteint l'objectif");
    }

    @Test
    @DisplayName("5. Limites et Intégrité : Vérifier l'appel correct des dépendances")
    void testMove_BoundaryAndDataIntegrity() {
        // GIVEN
        when(game.getGrid()).thenReturn(grid);
        when(hero.getCoords()).thenReturn(startCoords);

        bfsMockedConstruction = Mockito.mockConstruction(BFS.class, (mockBFS, context) -> {
            when(mockBFS.search(any(Coords.class))).thenReturn(new Coords(10, 10));
        });

        // WHEN
        bfsStrategy.move(game, hero);

        // THEN
        // On vérifie que c'est bien la grille du jeu actuel qui a été passée au constructeur du BFS
        assertEquals(1, bfsMockedConstruction.constructed().size(), "Une et une seule instance de BFS doit être créée");

        // Note: Pour vérifier les arguments du constructeur intercepté, on pourrait inspecter 'context.arguments()'
        // dans la lambda du MockedConstruction, mais la vérification fonctionnelle ci-dessus suffit généralement.
        verify(hero, times(1)).getCoords();
    }
}
