
package dungeon.engine;

import dungeon.engine.Strategies.BFSStrategy;
import dungeon.engine.tiles.StartingPoint;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.wall.StoneWall;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void testGameInitialization() {
        Game game = new Game();
        assertEquals(500, game.getMoney());
    }

    @Test
    void testGameTurn() {
        Game game = new Game();
        int initialTurn = game.getTurn();
        game.nextTurn();
        assertEquals(initialTurn + 1, game.getTurn());
    }

    @Test
    void testGameMoneyManagement() {
        Game game = new Game();
        int initialMoney = game.getMoney();
        game.subMoney(50);
        assertEquals(initialMoney - 50, game.getMoney());
    }

    @Test
    void testGameHeroSquad() {
        Game game = new Game();
        HeroSquad squad = game.getHeroSquad();
        assertEquals(0, squad.getSquadSize());
    }

    @Test
    void testGameScoreManagement() {
        Game game = new Game();
        int initialScore = game.getScore();
        assertEquals(0, initialScore);
        game.setScore(10);
        assertEquals(10, game.getScore());
    }

    @Test
    void testReinitializeGame() {
        Game game = new Game();
        game.setScore(50);
        game.subMoney(30);
        game.nextTurn();
        game.getGrid().setTile(new StartingPoint(new Coords(0, 0)));
        game.getGrid().setTile(new Treasure(new Coords(1, 1)));
        game.startSimulation();
        assertEquals(470, game.getMoney()); // Money persists through simulation start
        assertEquals(0, game.getScore()); // Score resets
        assertEquals(0, game.getTurn()); // Turn resets
    }

    @Test
    void testPlacementOnGrid() {
        Game game = new Game();
        Grid grid = game.getGrid();
        Coords coords = new Coords(1, 1);
        Tile tile = new StoneWall(coords);
        int initialMoney = game.getMoney();
        game.placementOnGrid(tile);
        assertEquals(tile, grid.getTile(coords));
        assertEquals(initialMoney - tile.getPlacementCost(), game.getMoney());
    }

    @Test
    void testIsSimulationReady() {
        Game game = new Game();
        Hero hero = new Muggle();
        hero.setCoords(new Coords(0, 0));
        game.getHeroSquad().addHero(hero);
        game.getHeroSquad().setStrategy(new BFSStrategy());
        game.setScore(50);
        game.subMoney(30);
        game.nextTurn();
        game.getGrid().setTile(new StartingPoint(new Coords(0, 0)));
        assertFalse(game.isSimulationReady());
        game.getGrid().setTile(new Treasure(new Coords(1, 1)));
        assertTrue(game.isSimulationReady());
    }

    @Test
    void testScoreManagerObservesHeroesAfterSimulationStart(){
        Game game = new Game();
        game.getGrid().setTile(new StartingPoint(new Coords(0, 0)));
        game.getGrid().setTile(new Treasure(new Coords(5, 5)));
        
        // Start simulation - this should add ScoreManager as observer to all heroes
        game.startSimulation();
        
        // Initial score should be 0
        assertEquals(0, game.getScore());
        
        // Get a hero and apply damage to it
        Hero hero = game.getHeroSquad().getHeroes().get(0);
        int initialHealth = hero.getHealth();
        
        // Apply damage - score should increase
        hero.applyDamage(10);
        assertEquals(10, game.getScore());
        
        // Apply more damage
        hero.applyDamage(5);
        assertEquals(15, game.getScore());
        
        // Kill the hero - score should increase by remaining health + 200 (death bonus)
        int remainingHealth = hero.getHealth();
        hero.applyDamage(initialHealth); // Apply enough damage to kill
        int expectedScore = 15 + remainingHealth + 200; // Previous damage + fatal damage + death bonus
        assertEquals(expectedScore, game.getScore());
    }
}