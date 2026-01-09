package dungeon.ui.cli;

import dungeon.engine.GameEngine;
import dungeon.engine.GameResult;

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
        System.out.println("==================== Leaderboard ====================");
        while (run) {
            System.out.print(
                    "Games " + page * DISPLAY_SIZE + " to " + (page + 1) * DISPLAY_SIZE + "\n     (Score  ID  Coin)");
            System.out.print("\n");
            System.out.print("\n");
            String result_leader = generateLeaderboard(leaderboard, page, DISPLAY_SIZE);
            System.out.print(result_leader);
            System.out.print("\n");
            System.out.print("\n");
            System.out.print("Choose an action:\n(p)revious (n)ext (c)lose\nOr write a game id");
            System.out.print("\n");
            System.out.print("\n");
            String action = scanner.next().trim();
            System.out.print("\n");
            switch (action) {
                case "p":
                    if (page > 0) {
                        page--;
                    }
                    break;
                case "n":
                    if ((page + 1) * DISPLAY_SIZE < leaderboard.size()) {
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
                        run = false;
                    } catch (NumberFormatException e) {

                    }
            }
        }
        System.out.print("\n");
        System.out.println("=====================================================");
        System.out.print("\n");
        return result;
    }

    private static String generateLeaderboard(List<GameResult> leaderboard, int page, int DISPLAY_SIZE) {
        /*
         * Returns the formatted string for
         */

        String result = "";
        for (int i = page * DISPLAY_SIZE; i < Math.min((page + 1) * DISPLAY_SIZE, leaderboard.size()); i++) {
            result += leaderboard.get(i).toString() + "\n";
        }
        return result;
    }
}
