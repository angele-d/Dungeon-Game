package dungeon.engine;

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

    @Test
    void removeHeroFromSquad() {
        HeroSquad squad = new HeroSquad();
        Dwarf dwarf = new Dwarf();
        squad.addHero(dwarf);
        squad.removeHero(dwarf);
        assertEquals(0, squad.getSquadSize()); 
    }

    @Test
    void getHeroesFromSquad() {
        HeroSquad squad = new HeroSquad();
        Tank tank = new Tank();
        squad.addHero(tank);
        assertEquals(1, squad.getHeroes().size());
    }
}
