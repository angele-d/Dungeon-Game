package dungeon.engine;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import dungeon.engine.Creators.DwarfCreator;
import dungeon.engine.Creators.HealerCreator;
import dungeon.engine.Creators.HeroCreator;
import dungeon.engine.Creators.TankCreator;
import dungeon.engine.Creators.TheMemeMakerCreator;

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

    @Test
    public void testMultipleHeroCreation(){
        HeroCreator tankCreator = new TankCreator();
        Hero tank1 = tankCreator.createHero();
        Hero tank2 = tankCreator.createHero();
        assertTrue(tank1 instanceof Tank);
        assertTrue(tank2 instanceof Tank);
        assertNotSame(tank1, tank2);
    }
}
