package dungeon.ui.cli;

import dungeon.engine.Coords;
import dungeon.engine.GameEngine;
import dungeon.engine.Observers.ScoreManager;
import dungeon.engine.Game;

public class ExecuteGame {
public static void execute_game(Game game, int size, Coords dep_hero, ScoreManager score, String legendString) {
        int end = 0;
        int round = 1;
        GameEngine.getInstance().startSimulation(game.getId());

        while (end == 0){
            if(GameEngine.getInstance().isGameTerminated(game.getId())){
                end = 1;
                System.out.println("\n");
                System.out.println("This is the end !");
                System.out.println("\n");
                GameEngine.getInstance().endGame(game.getId());
            } else {
                System.out.println("===================== Round " + round + " ! =====================");
                GameEngine.getInstance().nextTurn(game.getId());
                game = GameEngine.getInstance().getGame(game.getId());
                PrintGrid.print_grid(game, size, legendString);
            }
            System.out.println("Your score : " + score.getScore());
            sleepHalfSecond();
            round ++;
        }
    }
    public static void sleepHalfSecond() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}