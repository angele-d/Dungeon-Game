package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MineTest {

    @Test
    void testMineInitialization() {
        Coords coords = new Coords(5, 5);
        int damage = 25;
        int area = 4;

        Mine mine = new Mine(coords, damage, area);

        assertEquals(damage, mine.getDamage(), "Les dégâts de la Mine sont incorrects");
        assertEquals(area, mine.getArea(), "La zone de la Mine est incorrecte");
    }

    @Test
    void testMineNegativeValues() {
        Coords coords = new Coords(2, 2);

        Mine mine = new Mine(coords, -10, -2);

        assertEquals(-10, mine.getDamage());
        assertEquals(-2, mine.getArea());
    }
}
