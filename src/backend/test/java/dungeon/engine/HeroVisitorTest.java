package dungeon.engine;

import dungeon.engine.Visitors.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HeroVisitorTest {
    
    /* HealVisitor */

    @Test
    void testHealVisitorOnHealer() {
        Healer healer = new Healer();
        int initialHealth = healer.getHealth();
        HealVisitor healVisitor = new HealVisitor();
        healer.accept(healVisitor);
        assertEquals(initialHealth, healer.getHealth());
    }

    @Test
    void testHealVisitorOnOtherHero() {
        Dragon dwarf = new Dragon();
        int initialHealth = dwarf.getHealth();
        dwarf.applyDamage(20);
        HealVisitor healVisitor = new HealVisitor();
        dwarf.accept(healVisitor);
        int expectedHealth = (initialHealth - 20) + ((initialHealth - 20) * 10 / 100);
        assertEquals(expectedHealth, dwarf.getHealth());
    }

    @Test
    void testHealVisitorDoesNotExceedMaxHealth() {
        Dragon dwarf = new Dragon();
        dwarf.applyDamage(1);

        HealVisitor healVisitor = new HealVisitor();
        dwarf.accept(healVisitor);

        assertEquals(dwarf.getMaxHealth(), dwarf.getHealth());
    }

    @Test
    void testHealVisitorRecoversPartialHealth() {
        Dragon dwarf = new Dragon();
        dwarf.applyDamage(100); // health = 50

        HealVisitor healVisitor = new HealVisitor();
        dwarf.accept(healVisitor);

        assertEquals(55, dwarf.getHealth());
    }

    @Test
    void testHealVisitorStacksAcrossVisits() {
        Dragon dwarf = new Dragon();
        dwarf.applyDamage(60); // health = 90

        HealVisitor healVisitor = new HealVisitor();
        dwarf.accept(healVisitor); // 90 -> 99
        dwarf.accept(healVisitor); // 99 -> 108

        assertEquals(108, dwarf.getHealth());
    }

    /* AreaDamageVisitor */

    @Test
    void testAreaDamageVisitorDirectHitFullDamage() {
        Dragon dwarf = new Dragon();
        dwarf.setCoords(new Coords(0, 0));

        AreaDamageVisitor visitor = new AreaDamageVisitor(new Coords(0, 0), 50, 2);
        dwarf.accept(visitor);

        assertEquals(100, dwarf.getHealth());
    }

    @Test
    void testAreaDamageVisitorReducedDamageWithinRadius() {
        Dragon dwarf = new Dragon();
        dwarf.setCoords(new Coords(1, 0)); // Manhattan distance 1 from epicenter

        AreaDamageVisitor visitor = new AreaDamageVisitor(new Coords(0, 0), 50, 2);
        dwarf.accept(visitor);

        assertEquals(110, dwarf.getHealth()); // 50 * 80% = 40 damage
    }

    @Test
    void testAreaDamageVisitorNoDamageOutsideRadius() {
        Tank tank = new Tank();
        tank.setCoords(new Coords(3, 0)); // distance 3, radius 2

        AreaDamageVisitor visitor = new AreaDamageVisitor(new Coords(0, 0), 50, 2);
        tank.accept(visitor);

        assertEquals(tank.getMaxHealth(), tank.getHealth());
    }

    @Test
    void testAreaDamageVisitorDefaultDamageAndRadius() {
        Dragon dwarf = new Dragon();
        dwarf.setCoords(new Coords(0, 0));

        AreaDamageVisitor visitor = new AreaDamageVisitor(new Coords(0, 0));
        dwarf.accept(visitor);

        assertEquals(140, dwarf.getHealth());
    }

    /* PoisonVisitor */

    @Test
    void testPoisonVisitorPoisonsHeroWithinRadius() {
        Dragon dwarf = new Dragon();
        dwarf.setCoords(new Coords(1, 0)); // distance 1 from epicenter

        PoisonVisitor visitor = new PoisonVisitor(new Coords(0, 0), 2);
        assertFalse(dwarf.getIsPoisoned());

        dwarf.accept(visitor);

        assertTrue(dwarf.getIsPoisoned());
    }

    @Test
    void testPoisonVisitorDoesNotPoisonOutsideRadius() {
        Dragon dwarf = new Dragon();
        dwarf.setCoords(new Coords(3, 0));

        PoisonVisitor visitor = new PoisonVisitor(new Coords(0, 0), 2);

        dwarf.accept(visitor);

        assertFalse(dwarf.getIsPoisoned());
    }

    @Test
    void testPoisonVisitorTankLosesActionBeforePoisoned() {
        Tank tank = new Tank();
        tank.setCoords(new Coords(0, 0)); // distance 0

        PoisonVisitor visitor = new PoisonVisitor(new Coords(0, 0), 1);

        // Initially: has action, not poisoned
        assertTrue(tank.getActionAvailable());
        assertFalse(tank.getIsPoisoned());

        // First visit: in radius, has action -> lose action, still not poisoned
        tank.accept(visitor);
        assertFalse(tank.getActionAvailable());
        assertFalse(tank.getIsPoisoned());

        // Second visit: still in radius, no action -> now poisoned
        tank.accept(visitor);
        assertTrue(tank.getIsPoisoned());
    }

    @Test
    void testPoisonVisitorDefaultConstructorPoisonsOnlyOnExactTile() {
        Dragon dwarf = new Dragon();
        dwarf.setCoords(new Coords(0, 0));

        // Default constructor: radius = 0
        PoisonVisitor visitor = new PoisonVisitor(new Coords(0, 0));

        dwarf.accept(visitor);

        assertTrue(dwarf.getIsPoisoned());
    }
}
