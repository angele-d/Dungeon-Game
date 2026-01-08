package dungeon.engine;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    
    private final List<GameResults> results = new ArrayList<>();

    public void addResults(GameResults result) {
        results.add(result);
    }

    /* --- Getters --- */

    public List<GameResults> getResults() {
        return List.copyOf(results);
    }

}
