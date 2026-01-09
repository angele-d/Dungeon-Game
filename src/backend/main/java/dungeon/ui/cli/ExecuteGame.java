package dungeon.ui.cli;

import dungeon.engine.GameEngine;
import dungeon.engine.Game;
import java.util.Scanner;

public class ExecuteGame {
    public static void execute_game(Game game, int size, String legendString,
            int strategy_AI, Scanner scanner) { // TODO: wave and game(treasure taken)
        int end = 0;
        int round = 1;
        int wave = 1;
        GameEngine.getInstance().changeAI(game.getId(), getTypeAI(strategy_AI));
        GameEngine.getInstance().startSimulation(game.getId());
        System.out.println("======================== Wave " + wave + " ! ========================");
        while (end == 0) {
            if (GameEngine.getInstance().isGameTerminated(game.getId())){
                end = 1;
                System.out.println("\n");
                System.out.println("This is the end !");
                System.out.println("\n");
                GameEngine.getInstance().endGame(game.getId());
            } else if (GameEngine.getInstance().isWaveTerminated(game.getId())){
                wave++;
                if(menuWave(scanner)) {
                    System.out.println("======================== Wave " + wave + " ! ========================");
                } else {
                    end = 1;
                }
            }else {
                System.out.println("===================== Round " + round + " ! =====================");
                GameEngine.getInstance().nextTurn(game.getId());
                game = GameEngine.getInstance().getGame(game.getId());
                PrintGrid.print_grid(game, size, legendString, 0);
            }
            System.out.println("Your score : " + game.getScore());
            sleepHalfSecond();
            round++;
        }
    }

    public static void sleepHalfSecond() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String getTypeAI(int strategy_AI) {
        switch (strategy_AI) {
            case 1:
                return "BFS";
            case 2:
                return "Astar";
            default:
                return "BFS";
        }
    }

    public static boolean menuWave(Scanner scanner){
        System.out.print("Would you have the next wave ? (yes/no)");
        System.out.print("\n");
        String action_player = scanner.next().trim();
        if (action_player == "no") {
            return false;
        }
        return true;
    }

}