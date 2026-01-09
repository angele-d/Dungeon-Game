package dungeon.engine;

public record GameResult(int score, int gameId, int money) {

/* --- Getters --- */

    /** 
     * Gets the score of the game result.
     * @return int
     */
    public int getScore() {
        return score;
    }

    /** 
     * Gets the game ID of the game result.
     * @return int
     */
    public int getGameId() {
        return gameId;
    }

    /** 
     * Gets the money earned in the game result.
     * @return int
     */
    public int getMoney() {
        return money;
    }

/* --- Functions --- */

    /** 
     * Checks if this GameResult is equal to another.
     * @param other
     * @return boolean
     */
    public boolean isEqual(GameResult other) {
        return this.score == other.getScore() && this.gameId == other.getGameId() && this.money == other.getMoney();
    }

    /** 
     * Serializes the GameResult to a string representation.
     * @return String
     */
    public String serialized() {
        return "[" + String.valueOf(score) + "," + String.valueOf(gameId) + "," + String.valueOf(money) + "]";
    }

/* --- ToString --- */

    /** 
     * Converts the GameResult to a string representation.
     * @return String
     */
    @Override
    public String toString() {
        return String.valueOf(score) + " " + String.valueOf(gameId) + " " + String.valueOf(money);
    }

}
