package dungeon.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class HeroTest {
    @Test
    public void testCreateHero(){
        Dragon dragon = new Dragon();
        Tank tank = new Tank();
        Healer healer = new Healer();
        TheMemeMaker memeMarker = new TheMemeMaker();
        assertEquals(150, dragon.getHealth());
        assertEquals(200, tank.getHealth());
        assertEquals(150, healer.getHealth());
        assertEquals(150, memeMarker.getHealth());
    }

    @Test
    public void testHeroTakeDamage(){
        Dragon dragon = new Dragon();
        dragon.applyDamage(50);
        assertEquals(100, dragon.getHealth());
        dragon.applyDamage(200);
        assertEquals(0, dragon.getHealth());
    }

    @Test
    public void testPoisonedStatus(){
        Tank tank = new Tank();
        assertEquals(false, tank.getIsPoisoned());
        tank.setIsPoisoned(true);
        assertEquals(true, tank.getIsPoisoned());
        tank.setIsPoisoned(false);
        assertEquals(false, tank.getIsPoisoned());
    }

    @Test
    public void testDragonWallFireUses(){
        Dragon dragon = new Dragon();
        // Dragon should have 5 uses of wall fire
        for(int i = 0; i < 5; i++){
            assertTrue(dragon.getActionAvailable());
            dragon.setActionAvailable(false);
        }
        assertFalse(dragon.getActionAvailable());
    }

    @Test
    public void testDragonResetAction(){
        Dragon dragon = new Dragon();
        for(int i = 0; i < 5; i++){
            dragon.setActionAvailable(false);
        }
        assertFalse(dragon.getActionAvailable());
        dragon.resetAction();
        assertTrue(dragon.getActionAvailable());
    }

    @Test
    public void testDragonHealthCannotGoNegative(){
        Dragon dragon = new Dragon();
        dragon.applyDamage(300);
        assertEquals(0, dragon.getHealth());
    }

    @Test
    public void testDragonPartialDamage(){
        Dragon dragon = new Dragon();
        dragon.applyDamage(30);
        assertEquals(120, dragon.getHealth());
        dragon.applyDamage(20);
        assertEquals(100, dragon.getHealth());
    }
}
