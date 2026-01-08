package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeaderBoardTest {

    @Test
    public void testGameResultEquals() {
        GameResult gameResult1 = new GameResult(1, 1, 1);
        GameResult gameResult2 = new GameResult(1, 1, 1);
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.addResults(gameResult1);
        leaderBoard.addResults(gameResult2);
        assertTrue(leaderBoard.getResults().contains(gameResult1));
        assertTrue(leaderBoard.getResults().contains(gameResult2));
    }

    @Test
    public void testEngineLeaderboardUpdate() {
        GameResult gameResult1 = new GameResult(1, 1, 1);
        GameResult gameResult2 = new GameResult(1, 1, 1);
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.addResults(gameResult1);
        leaderBoard.addResults(gameResult2);
        assertTrue(leaderBoard.getResults().contains(gameResult1));
        assertTrue(leaderBoard.getResults().contains(gameResult2));
    }

    @Test
    public void testEngineLeaderboard() {
        GameEngine gameEngine = GameEngine.getInstance();
        System.out.println(gameEngine.games.toString());
        System.out.println(gameEngine.getLeaderBoard().getResults().toString());

    }
}
