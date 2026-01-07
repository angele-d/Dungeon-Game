package dungeon.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BuilderTest {
    
    @Test
    void testBuilderCreatesEmptyHeroList() {
        HeroSquad.Builder builder = new HeroSquad.Builder();
        // Assuming the Builder has a method to build the HeroSquad
        HeroSquad squad = builder.build();
        assertEquals(0, squad.getSquadSize());
    }

    @Test
    void testBuilderAddsHeroes() {
        HeroSquad.Builder builder = new HeroSquad.Builder();
        Healer healer = new Healer();
        Dwarf dwarf = new Dwarf();
        builder.addHero(healer);
        builder.addHero(dwarf);
        HeroSquad squad = builder.build();
        assertEquals(2, squad.getSquadSize());
    }

    @Test
    void testBuilderAddsSameHeroTypes() {
        HeroSquad.Builder builder = new HeroSquad.Builder();
        for (int i = 0; i < 5; i++) {
            builder.addHero(new Tank());
        }
        HeroSquad squad = builder.build();
        assertEquals(5, squad.getSquadSize());
    }

    @Test
    void testBuilderSupportsMethodChaining() {
        HeroSquad.Builder builder = new HeroSquad.Builder();
        HeroSquad squad = builder
            .addHero(new Healer())
            .addHero(new Dwarf())
            .addHero(new Tank())
            .build();
        assertEquals(3, squad.getSquadSize());
    }

    @Test
    void testBuilderCreatesHeroSquadInstance() {
        HeroSquad.Builder builder = new HeroSquad.Builder();
        HeroSquad squad = builder.build();
        assertEquals(HeroSquad.class, squad.getClass());
    }
}