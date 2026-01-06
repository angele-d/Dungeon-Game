package dungeon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroTest {
    @Test
    public void testCreateHero(){
        Dwarf dwarf = new Dwarf();
        Tank tank = new Tank();
        Healer healer = new Healer();
        TheMemeMarker memeMarker = new TheMemeMarker();
        assertEquals(0, dwarf.getHealth());
        assertEquals(0, tank.getHealth());
        assertEquals(0, healer.getHealth());
        assertEquals(0, memeMarker.getHealth());
    }
}
