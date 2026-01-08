package dungeon.engine;

public class GameResult {
    private final int score;
    private final int gameId;
    private final int money;
    
    /* --- Constructor --- */

    public GameResult(int score, int gameId, int money) {
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

    public boolean isEqual(GameResult other) {
        return this.score == other.getScore() && this.gameId == other.getGameId() && this.money == other.getMoney();
    }

    @Override
    public String toString() {
        return "["+String.valueOf(score)+String.valueOf(gameId)+String.valueOf(money)+"]";
    }

}
