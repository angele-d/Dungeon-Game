package dungeon.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.tiles.traps.Mine;

class MineTest {

    @Test
    void testMineInitialization() {
        Coords coords = new Coords(5, 5);
        int damage = 25;
        int area = 4;

        Mine mine = new Mine(coords, damage, area);

        assertEquals(damage, mine.getDamage(), "Les dégâts de la Mine sont incorrects");
        assertEquals(area, mine.getAreaRadius(), "La zone de la Mine est incorrecte");
    }

    @Test
    void testMineZeroValues() {
        Coords coords = new Coords(0, 0);
        Mine mine = new Mine(coords, 0, 0);
        assertEquals(0, mine.getDamage());
        assertEquals(0, mine.getAreaRadius());
    }

    @Test
    void testMineCoordinates() {
        Coords coords = new Coords(10, 15);
        Mine mine = new Mine(coords, 30, 2);

        assertEquals(coords, mine.getCoords());
        assertEquals(10, mine.getCoords().x());
        assertEquals(15, mine.getCoords().y());
    }

    @Test
    void testMineSetDamage() {
        Mine mine = new Mine(new Coords(0, 0), 20, 1);
        mine.setDamage(50);

        assertEquals(50, mine.getDamage());
    }

    @Test
    void testMineSetAreaRadius() {
        Mine mine = new Mine(new Coords(0, 0), 20, 1);
        mine.setAreaRadius(5);

        assertEquals(5, mine.getAreaRadius());
    }

    @Test
    void testMineGetAstarValue() {
        Mine mine = new Mine(new Coords(0, 0), 25, 2);

        assertEquals(5, mine.getAstarValue());
    }

    @Test
    void testMineActivateTrapOnDirectHit() {
        Dragon dragon = new Dragon();
        dragon.setCoords(new Coords(5, 5));

        HeroSquad squad = new HeroSquad.Builder()
            .addHero(dragon)
            .build();

        Game game = new Game();
        game.setHeroSquad(squad);

        Mine mine = new Mine(new Coords(5, 5), 40, 1);
        int initialHealth = dragon.getHealth();
        
        mine.activateTrap(game);

        assertEquals(initialHealth - 40, dragon.getHealth());
    }

    @Test
    void testMineActivateTrapWithAreaDamage() {
        Dragon dragon = new Dragon();
        dragon.setCoords(new Coords(1, 0)); // distance 1 from mine

        HeroSquad squad = new HeroSquad.Builder()
            .addHero(dragon)
            .build();

        Game game = new Game();
        game.setHeroSquad(squad);

        Mine mine = new Mine(new Coords(0, 0), 100, 2);
        int initialHealth = dragon.getHealth();
        
        mine.activateTrap(game);

        assertEquals(initialHealth - 80, dragon.getHealth());
    }

    @Test
    void testMineActivateTrapMultipleHeroes() {
        Dragon dragon = new Dragon();
        dragon.setCoords(new Coords(5, 5)); // direct hit

        Tank tank = new Tank();
        tank.setCoords(new Coords(6, 5)); // distance 1, within radius 2

        HeroSquad squad = new HeroSquad.Builder()
            .addHero(dragon)
            .addHero(tank)
            .build();

        Game game = new Game();
        game.setHeroSquad(squad);

        Mine mine = new Mine(new Coords(5, 5), 50, 2);
        int dragonInitialHealth = dragon.getHealth();
        int tankInitialHealth = tank.getHealth();
        
        mine.activateTrap(game);

        // dragon takes direct hit: 150 - 50 = 100
        assertEquals(dragonInitialHealth - 50, dragon.getHealth());
        
        // Tank takes reduced damage: 200 - 40 (50 * 80%)
        assertEquals(tankInitialHealth - 40, tank.getHealth());
    }
}
