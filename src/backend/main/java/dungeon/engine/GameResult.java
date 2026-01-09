package dungeon.engine;

public record GameResult(
        int score,
        int gameId,
        int money) {

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
        return String.valueOf(score) + " " + String.valueOf(gameId) + " " + String.valueOf(money);
    }

    public String serialized() {
        return "[" + String.valueOf(score) + "," + String.valueOf(gameId) + "," + String.valueOf(money) + "]";
    }

}
