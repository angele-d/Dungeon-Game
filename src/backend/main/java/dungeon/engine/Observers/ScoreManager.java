package dungeon.engine.Observers;

import java.lang.Math;

public class ScoreManager implements DeathObserver, DamageObserver, TreasureReachedObserver {

    private int score;

    /* Getters and Setters */

    /** 
     * Get the current score
     * @return int
     */
    public int getScore() {
        return score;
    }

    /** 
     * Set the current score
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /* --- Functions --- */

    /** 
     * Update the score based on the game event
     * @param event
     */
    @Override
    public void update(GameEvent event) {
        switch (event.getType()) {
            case DAMAGE_TAKEN:
                int preDamageHealth = event.getHero().getHealth() + event.getDamageAmount(); // Health before damage was
                                                                                             // applied
                score += Math.min(event.getDamageAmount(), preDamageHealth);
                break;
            case HERO_DEATH:
                score += 200;
                break;
            case TREASURE_REACHED:
                score -= 300;
                break;
        }
    }
}
