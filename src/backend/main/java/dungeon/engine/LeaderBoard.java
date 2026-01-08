package dungeon.engine;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {


    private final List<GameResult> results = new ArrayList<>();

    public void addResults(GameResult result) {
        results.add(result);
    }

    /* --- Getters --- */

    public List<GameResult> getResults() {
        return List.copyOf(results);
    }

}
