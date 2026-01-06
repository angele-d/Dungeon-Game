package dungeon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;;

public class HeroSquadTest {
    
    @Test
    void testCreateSquad() {
        HeroSquad squad = new HeroSquad();
        assertEquals(0, squad.getSquadSize());
    }

    @Test
    void addHeroToSquad() {
        HeroSquad squad = new HeroSquad();
        Healer healer = new Healer();
        squad.addHero(healer);
        assertEquals(1, squad.getSquadSize());
    }
}
