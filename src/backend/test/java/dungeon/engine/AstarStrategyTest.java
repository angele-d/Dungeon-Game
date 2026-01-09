package dungeon.engine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dungeon.engine.tiles.*;
import dungeon.engine.tiles.wall.StoneWall;
import dungeon.engine.Strategies.AstarStrategy;

class AstarStrategyTest {

    private Game game;
    private AstarStrategy strategy;
    private Dragon dragon;

    @BeforeEach
    void setUp() {
        // On initialise un jeu avec une graine fixe
        game = new Game(123); 
        strategy = new AstarStrategy();
        
        // On crée notre héros (Dragon)
        dragon = new Dragon();
        dragon.setStrategy(strategy);
    }

    @Test
    void testMove_ReturnsNextStepTowardsTreasure() {
        // 1. Configurer un scénario simple
        Coords startPos = new Coords(0, 0);
        Coords treasurePos = new Coords(2, 0);
        
        dragon.setCoords(startPos);
        game.getGrid().setTile(new Treasure(treasurePos));
        game.getGrid().setTile(new Empty(new Coords(1, 0)));

        // 2. Exécuter la stratégie
        Coords nextCoords = strategy.move(game, dragon);

        // 3. Vérifier que la stratégie a bien utilisé Astar pour trouver le premier pas
        assertNotNull(nextCoords, "La stratégie doit retourner une coordonnée");
        assertEquals(new Coords(1, 0), nextCoords, "La stratégie devrait diriger le héros vers (1,0)");
    }

    @Test
    void testMove_IntegratesWithHeroMovement() {
        // Ce test vérifie que la méthode move du héros utilise bien la stratégie
        Coords startPos = new Coords(5, 5);
        dragon.setCoords(startPos);
        
        // On place un trésor juste à côté
        Coords treasurePos = new Coords(5, 6);
        game.getGrid().setTile(new Treasure(treasurePos));

        // On appelle la méthode move du Hero (qui appelle strategy.move interne)
        Coords result = dragon.move(game);

        assertEquals(treasurePos, result, "Le dragon devrait avoir bougé sur le trésor via sa stratégie");
    }

    @Test
    void testMove_StuckWhenSurrounded() {
        // Scénario où le héros est enfermé de murs de pierre
        Coords startPos = new Coords(1, 1);
        dragon.setCoords(startPos);
        
        // Entourer de murs
        game.getGrid().setTile(new StoneWall(new Coords(0, 1)));
        game.getGrid().setTile(new StoneWall(new Coords(2, 1)));
        game.getGrid().setTile(new StoneWall(new Coords(1, 0)));
        game.getGrid().setTile(new StoneWall(new Coords(1, 2)));

        Coords nextCoords = strategy.move(game, dragon);

        // Si aucun chemin n'est possible, Astar renvoie la position actuelle
        assertEquals(startPos, nextCoords, "Le héros doit rester sur place s'il est enfermé");
    }
}