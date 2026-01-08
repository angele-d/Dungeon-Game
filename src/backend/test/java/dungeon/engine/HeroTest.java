package dungeon.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroTest {
    @Test
    public void testCreateHero(){
        Dragon dwarf = new Dragon();
        Tank tank = new Tank();
        Healer healer = new Healer();
        TheMemeMaker memeMarker = new TheMemeMaker();
        assertEquals(150, dwarf.getHealth());
        assertEquals(200, tank.getHealth());
        assertEquals(150, healer.getHealth());
        assertEquals(150, memeMarker.getHealth());
    }

    @Test
    public void testHeroTakeDamage(){
        Dragon dwarf = new Dragon();
        dwarf.applyDamage(50);
        assertEquals(100, dwarf.getHealth());
        dwarf.applyDamage(200);
        assertEquals(0, dwarf.getHealth());
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
}
