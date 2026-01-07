package dungeon.engine;

import dungeon.engine.Visitors.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HeroVisitorTest {
    
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
        Dwarf dwarf = new Dwarf();
        int initialHealth = dwarf.getHealth();
        dwarf.applyDamage(20);
        HealVisitor healVisitor = new HealVisitor();
        dwarf.accept(healVisitor);
        int expectedHealth = (initialHealth - 20) + ((initialHealth - 20) * 10 / 100);
        assertEquals(expectedHealth, dwarf.getHealth());
    }

    @Test
    void testHealVisitorDoesNotExceedMaxHealth() {
        Dwarf dwarf = new Dwarf();
        dwarf.applyDamage(1);

        HealVisitor healVisitor = new HealVisitor();
        dwarf.accept(healVisitor);

        assertEquals(dwarf.getMaxHealth(), dwarf.getHealth());
    }

    @Test
    void testHealVisitorRecoversPartialHealth() {
        Dwarf dwarf = new Dwarf();
        dwarf.applyDamage(100); // health = 50

        HealVisitor healVisitor = new HealVisitor();
        dwarf.accept(healVisitor);

        assertEquals(55, dwarf.getHealth());
    }

    @Test
    void testHealVisitorStacksAcrossVisits() {
        Dwarf dwarf = new Dwarf();
        dwarf.applyDamage(60); // health = 90

        HealVisitor healVisitor = new HealVisitor();
        dwarf.accept(healVisitor); // 90 -> 99
        dwarf.accept(healVisitor); // 99 -> 108

        assertEquals(108, dwarf.getHealth());
    }

    @Test
    void testAreaDamageVisitorDirectHitFullDamage() {
        Dwarf dwarf = new Dwarf();
        dwarf.setCoords(new Coords(0, 0));

        AreaDamageVisitor visitor = new AreaDamageVisitor(new Coords(0, 0), 50, 2);
        dwarf.accept(visitor);

        assertEquals(100, dwarf.getHealth());
    }

    @Test
    void testAreaDamageVisitorReducedDamageWithinRadius() {
        Dwarf dwarf = new Dwarf();
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
        Dwarf dwarf = new Dwarf();
        dwarf.setCoords(new Coords(0, 0));

        AreaDamageVisitor visitor = new AreaDamageVisitor(new Coords(0, 0));
        dwarf.accept(visitor);

        assertEquals(140, dwarf.getHealth());
    }

}
