package dungeon.engine;

public class GameResults {
    private final int score;
    private final int gameId;
    private final int money;
    
    /* --- Constructor --- */

    public GameResults(int score, int gameId, int money) {
        this.score = score;
        this.gameId = gameId;
        this.money = money;
    }

    /* --- Getters --- */

    public int getScore() {
        return score;
    }

    public int getGameId() {
        return gameId;
    }

    public int getMoney() {
        return money;
    }

}
