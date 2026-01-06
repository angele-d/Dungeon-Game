package dungeon.engine;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class HeroCreatorTest {
    
    @Test
    public void testHealerCreator() {
        HeroCreator creator = new HealerCreator();
        Hero hero = creator.createHero();
        assertTrue(hero instanceof Healer);
    }

    @Test
    public void testDwarfCreator() {
        HeroCreator creator = new DwarfCreator();
        Hero hero = creator.createHero();
        assertTrue(hero instanceof Dwarf);
    }

    @Test
    public void testTankCreator() {
        HeroCreator creator = new TankCreator();
        Hero hero = creator.createHero();
        assertTrue(hero instanceof Tank);
    }

    @Test
    public void testTheMemeMakerCreator() {
        HeroCreator creator = new TheMemeMakerCreator();
        Hero hero = creator.createHero();
        assertTrue(hero instanceof TheMemeMaker);
    }

    @Test
    public void testDifferentCreators(){
        HeroCreator healerCreator = new HealerCreator();
        HeroCreator dwarfCreator = new DwarfCreator();
        HeroCreator tankCreator = new TankCreator();
        HeroCreator memeMakerCreator = new TheMemeMakerCreator();

        Hero healer = healerCreator.createHero();
        Hero dwarf = dwarfCreator.createHero();
        Hero tank = tankCreator.createHero();
        Hero memeMaker = memeMakerCreator.createHero();

        assertTrue(healer instanceof Healer);
        assertTrue(dwarf instanceof Dwarf);
        assertTrue(tank instanceof Tank);
        assertTrue(memeMaker instanceof TheMemeMaker);
        assertNotSame(tank, memeMaker);
    }
}
