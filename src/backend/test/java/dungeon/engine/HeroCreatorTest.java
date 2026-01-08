package dungeon.engine;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import dungeon.engine.Creators.DragonCreator;
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
    public void testDragonCreator() {
        HeroCreator creator = new DragonCreator();
        Hero hero = creator.createHero();
        assertTrue(hero instanceof Dragon);
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
        HeroCreator dragonCreator = new DragonCreator();
        HeroCreator tankCreator = new TankCreator();
        HeroCreator memeMakerCreator = new TheMemeMakerCreator();

        Hero healer = healerCreator.createHero();
        Hero dragon = dragonCreator.createHero();
        Hero tank = tankCreator.createHero();
        Hero memeMaker = memeMakerCreator.createHero();

        assertTrue(healer instanceof Healer);
        assertTrue(dragon instanceof Dragon);
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
