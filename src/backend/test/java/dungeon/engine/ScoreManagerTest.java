package dungeon.engine;

import org.junit.jupiter.api.Test;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Observers.ScoreManager;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreManagerTest {

    private ScoreManager scoreManager;
    private Dragon dragon;

    @BeforeEach
    void setUp() {
        scoreManager = new ScoreManager();
        dragon = new Dragon();
        dragon.addObserver(scoreManager);
    }

    @Test
    void testDamageTakenIncreasesScore() {
        int initialScore = scoreManager.getScore();
        dragon.applyDamage(50);
        int updatedScore = scoreManager.getScore();
        assertEquals(updatedScore, initialScore + 50);
    }

    @Test
    void testHeroDeathIncreasesScore() {
        int initialScore = scoreManager.getScore();
        assertEquals(0, initialScore);
        dragon.applyDamage(200); // Assuming this will kill the dragon
        int updatedScore = scoreManager.getScore();
        assertEquals(updatedScore, 200 + 150);
    }

    @Test
    void testTreasureReachedDecreasesScore() {
        int initialScore = scoreManager.getScore();
        GameEvent treasureEvent = new GameEvent(GameEventType.TREASURE_REACHED, null, 0);
        scoreManager.update(treasureEvent);
        int updatedScore = scoreManager.getScore();
        assertEquals(updatedScore, initialScore - 300);
    }
}
