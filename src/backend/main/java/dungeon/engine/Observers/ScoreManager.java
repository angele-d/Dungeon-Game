package dungeon.engine.Observers;

import java.lang.Math;

public class ScoreManager implements DeathObserver, DamageObserver, TreasureReachedObserver {

    private int score;

    /* Getters and Setters */

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /* --- Functions --- */

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
