package dungeon.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroTest {
    @Test
    public void testCreateHero(){
        Dwarf dwarf = new Dwarf();
        Tank tank = new Tank();
        Healer healer = new Healer();
        TheMemeMarker memeMarker = new TheMemeMarker();
        assertEquals(150, dwarf.getHealth());
        assertEquals(200, tank.getHealth());
        assertEquals(150, healer.getHealth());
        assertEquals(150, memeMarker.getHealth());
    }
}
