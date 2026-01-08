package dungeon.ui.cli;

import dungeon.engine.GameEngine;
import dungeon.engine.GameResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeaderBoard {
    /*
    * Handles LeaderBoard integration in CLI
    */
    public static int showLeaderBoard(Scanner scanner, int DISPLAY_SIZE) {
        /*
         * Shows the leaderboard menu
         * Returns -1 if the user intends to leave, or the game id
         */
        List<GameResult> leaderboard = GameEngine.getInstance().getLeaderBoard().getResults();
        boolean run = true;
        int page = 0;
        int result = -1;
        while (run) {
            System.out.print("Games "+page*DISPLAY_SIZE+" to "+(page+1)*DISPLAY_SIZE);
            String message = generateLeaderboard(leaderboard, page, DISPLAY_SIZE);
            System.out.print("Choose an action:\n(p)revious (n)ext (c)lose\nOr write a game id");
            String action = scanner.next();
            switch (action) {
                case "p":
                    if (page > 0) {
                        page--;
                    }
                    break;
                case "n":
                    if (page*DISPLAY_SIZE < leaderboard.size()) {
                        page++;
                    }
                    break;
                case "c":
                    run = false;
                    result = -1;
                    break;
                default:
                    try {
                        int id = Integer.parseInt(action);
                        result = id;
                    } catch (NumberFormatException e) {

                    }
            }
        }
        return result;
    }

    private static String generateLeaderboard(List<GameResult> leaderboard, int page, int DISPLAY_SIZE) {
        /*
        Returns the formatted string for
         */

        String result="";
        for (int i = page*DISPLAY_SIZE; i < leaderboard.size(); i++) {
            result = leaderboard.get(i).toString() + "\n";
        }
        return result;
    }
}
