package dungeon.engine;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    
    private final Set<GameResult> results = new HashSet<>();

    public void addResults(GameResult result) {
        results.add(result);
    }

    /* --- Getters --- */

    public List<GameResult> getResults() {
        return new ArrayList<>(results);
    }

}
