
package dungeon.engine;

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
        game.addScore(10);
        assertEquals(initialScore + 10, game.getScore());
    }

    @Test
    void testReinitializeGame(){
        Game game = new Game();
        game.addScore(50);
        game.subMoney(30);
        game.nextTurn();
        game.startNewGame();
        assertEquals(500, game.getMoney());
        assertEquals(0, game.getScore());
        assertEquals(0, game.getTurn());
    }
}