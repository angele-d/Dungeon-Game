
package dungeon.engine;

import dungeon.engine.tiles.wall.StoneWall;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GameTest {
 
    @Test
    void testGameInitialization() {
        Game game = new Game();
        assertEquals(500, game.getMoney());    
    }

    @Test
    void testGameTurn(){
        Game game = new Game();
        int initialTurn = game.getTurn();
        game.nextTurn();
        assertEquals(initialTurn + 1, game.getTurn());
    }

    @Test
    void testGameMoneyManagement(){
        Game game = new Game();
        int initialMoney = game.getMoney();
        game.subMoney(50);
        assertEquals(initialMoney - 50, game.getMoney());
    }

    @Test
    void testGameHeroSquad(){
        Game game = new Game();
        HeroSquad squad = game.getHeroSquad();
        assertEquals(0, squad.getSquadSize());
    }    

    @Test
    void testGameScoreManagement(){
        Game game = new Game();
        int initialScore = game.getScore();
        assertEquals(0, initialScore);
        game.setScore(10);
        assertEquals(10, game.getScore());
    }

    @Test
    void testReinitializeGame(){
        Game game = new Game();
        game.setScore(50);
        game.subMoney(30);
        game.nextTurn();
        game.startNewGame();
        assertEquals(500, game.getMoney());
        assertEquals(0, game.getScore());
        assertEquals(0, game.getTurn());
    }

    @Test
    void testPlacementOnGrid(){
        Game game = new Game();
        Grid grid = game.getGrid();
        Coords coords = new Coords(1, 1);
        Tile tile = new StoneWall(coords);
        int initialMoney = game.getMoney();
        game.placementOnGrid(tile);
        assertEquals(tile, grid.getTile(coords));
        assertEquals(initialMoney - tile.getPlacementCost(), game.getMoney());
    }
}