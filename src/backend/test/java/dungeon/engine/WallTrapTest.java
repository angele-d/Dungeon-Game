package dungeon.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.tiles.traps.WallTrap;

class WallTrapTest {

    @Test
    void testWallTrapInitialization() {
        Coords coords = new Coords(1, 2);
        int damage = 15;
        int area = 3;

        WallTrap wallTrap = new WallTrap(coords, damage, area);

        assertEquals(damage, wallTrap.getDamage(), "Les dégâts du WallTrap sont incorrects");
        assertEquals(area, wallTrap.getArea(), "La zone du WallTrap est incorrecte");
    }

    @Test
    void testWallTrapZeroValues() {
        Coords coords = new Coords(0, 0);

        WallTrap wallTrap = new WallTrap(coords, 0, 0);

        assertEquals(0, wallTrap.getDamage());
        assertEquals(0, wallTrap.getArea());
    }
}
