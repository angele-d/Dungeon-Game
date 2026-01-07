package dungeon.engine.Observers;

public class ScoreManager implements DeathObserver {
    
    private int score;

    public int getScore() {
        return score;
    }

    @Override
    public void update(GameEvent event) {
        switch(event.getType()) {
            case DAMAGE_TAKEN:
                score += event.getDamageAmount();
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
