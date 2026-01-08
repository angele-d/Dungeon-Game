package dungeon.engine;

public record GameResult (
    int score,
    int gameId,
    int money
) {

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

    @Override
    public String toString() {
        return "["+String.valueOf(score)+String.valueOf(gameId)+String.valueOf(money)+"]";
    }

}
