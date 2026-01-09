package dungeon.engine;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {

    private final Set<GameResult> results = new HashSet<>();

/* --- Getters --- */

    /** 
     * Gets the list of game results in the leaderboard.
     * @return List<GameResult>
     */
    public List<GameResult> getResults() {
        return new ArrayList<>(results);
    }

/* --- Functions --- */

    /** 
     * Adds a game result to the leaderboard.
     * @param result
     */
    public void addResults(GameResult result) {
        results.add(result);
    }
}
