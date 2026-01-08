package dungeon.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeon.engine.tiles.traps.PoisonTrap;
import org.junit.jupiter.api.Test;


public class PoisonTrapTest {

	@Test
	void testPoisonTrapInitialization() {
		Coords coords = new Coords(2, 3);
		int damage = 20;
		int area = 2;

		PoisonTrap poisonTrap = new PoisonTrap(coords, damage, area);

		assertEquals(damage, poisonTrap.getDamage());
		assertEquals(area, poisonTrap.getAreaRadius());
		assertEquals(coords, poisonTrap.getCoords());
	}

	@Test
	void testPoisonTrapZeroValues() {
		Coords coords = new Coords(0, 0);
		PoisonTrap poisonTrap = new PoisonTrap(coords);

		assertEquals(0, poisonTrap.getDamage());
		assertEquals(0, poisonTrap.getAreaRadius());
	}

	@Test
	void testPoisonTrapActivateTrapAppliesDamageAndPoison() {
		Dragon dwarf = new Dragon();
		dwarf.setCoords(new Coords(5, 5));

		HeroSquad squad = new HeroSquad.Builder()
			.addHero(dwarf)
			.build();

		Game game = new Game();
		game.setHeroSquad(squad);

		PoisonTrap poisonTrap = new PoisonTrap(new Coords(5, 5), 30, 1);

		int initialHealth = dwarf.getHealth();
		assertFalse(dwarf.getIsPoisoned());

		poisonTrap.activateTrap(game);

		// AreaDamageVisitor
		assertEquals(initialHealth - 30, dwarf.getHealth());
		// PoisonVisitor
		assertTrue(dwarf.getIsPoisoned());
	}

	@Test
	void testPoisonTrapActivateTrapRespectsAreaRadius() {
		Dragon inRange = new Dragon();
		inRange.setCoords(new Coords(1, 0)); // distance 1

		Dragon outOfRange = new Dragon();
		outOfRange.setCoords(new Coords(3, 0)); // distance 3

		HeroSquad squad = new HeroSquad.Builder()
			.addHero(inRange)
			.addHero(outOfRange)
			.build();

		Game game = new Game();
		game.setHeroSquad(squad);

		PoisonTrap poisonTrap = new PoisonTrap(new Coords(0, 0), 40, 2);

		int inRangeInitialHealth = inRange.getHealth();
		int outOfRangeInitialHealth = outOfRange.getHealth();

		poisonTrap.activateTrap(game);

		// In range: reduced damage (40 * 80% = 32) and poisoned
		assertEquals(inRangeInitialHealth - 32, inRange.getHealth());
		assertTrue(inRange.getIsPoisoned());

		// Out of range: no damage, not poisoned
		assertEquals(outOfRangeInitialHealth, outOfRange.getHealth());
		assertFalse(outOfRange.getIsPoisoned());
	}

	@Test
	void testPoisonTrapActivateTrapTankSpecialBehavior() {
		Tank tank = new Tank();
		tank.setCoords(new Coords(0, 0));

		HeroSquad squad = new HeroSquad.Builder()
			.addHero(tank)
			.build();

		Game game = new Game();
		game.setHeroSquad(squad);

		PoisonTrap poisonTrap = new PoisonTrap(new Coords(0, 0), 20, 1);

		int initialHealth = tank.getHealth();
		assertTrue(tank.getActionAvailable());
		assertFalse(tank.getIsPoisoned());

		// First activation: Tank within radius, has action available -> lose action but not poisoned
		poisonTrap.activateTrap(game);
		assertEquals(initialHealth - 20, tank.getHealth());
		assertFalse(tank.getActionAvailable());
		assertFalse(tank.getIsPoisoned());

		// Second activation: no action available -> becomes poisoned
		poisonTrap.activateTrap(game);
		assertTrue(tank.getIsPoisoned());
	}
}
