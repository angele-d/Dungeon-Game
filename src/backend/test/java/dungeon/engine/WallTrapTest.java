package dungeon.engine;

import dungeon.engine.tiles.traps.WallTrap;
import dungeon.engine.tiles.wall.StoneWall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTrapTest {

    private Game game;
    private Grid grid;
    private HeroSquad squad;

    @BeforeEach
    void setUp() {
        game = new Game();
        grid = game.getGrid();
        squad = new HeroSquad();
        game.setHeroSquad(squad);
    }

    @Test
    void testWallTrapInitialization() {
        Coords coords = new Coords(1, 2);

        WallTrap wallTrap = new WallTrap(coords, 15, 3);

        assertEquals(15, wallTrap.getDamage());
        assertEquals(3, wallTrap.getAreaRadius());
    }

    @Test
    void shouldRegisterTurnListenerOnActivateTrap() {
        Coords trapCoords = new Coords(2, 3);
        WallTrap trap = new WallTrap(trapCoords, 0, 0);

        trap.activateTrap(game);

        assertTrue(game.getTurnListeners().contains(trap));
    }

    @Test
    void shouldTriggerTrapWhenTileIsFreeOnNewTurn() {

        Coords trapCoords = new Coords(1, 1);
        WallTrap trap = new WallTrap(trapCoords, 0, 0);

        Hero hero = new Tank();
        hero.setCoords(new Coords(0, 0));
        squad.addHero(hero);

        game.addTurnListener(trap);

        trap.onNewTurn(game);

        Tile placed = grid.getTile(trapCoords);
        assertTrue(placed instanceof StoneWall);
        assertEquals(trapCoords, placed.getCoords());
        assertFalse(game.getTurnListeners().contains(trap));
    }

    @Test
    void shouldNotTriggerTrapWhenHeroIsOnTrapTile() {
        Coords trapCoords = new Coords(3, 4);
        WallTrap trap = new WallTrap(trapCoords, 0, 0);
        Hero heroOnTrap = new Tank();
        heroOnTrap.setCoords(trapCoords);
        squad.addHero(heroOnTrap);

        game.addTurnListener(trap);

        trap.onNewTurn(game);

        Tile tileAtTrap = grid.getTile(trapCoords);
        assertFalse(tileAtTrap instanceof StoneWall);
        assertTrue(game.getTurnListeners().contains(trap));
    }

    @Test
    void shouldPlaceWallAndUnsubscribeExactlyOnceWhenMultipleHeroesNotOnTrap() {
        Coords trapCoords = new Coords(0, 7);
        WallTrap trap = new WallTrap(trapCoords, 0, 0);
        Hero hero1 = new Tank();
        hero1.setCoords(new Coords(0, 0));
        Hero hero2 = new Tank();
        hero2.setCoords(new Coords(1, 1));
        squad.addHero(hero1);
        squad.addHero(hero2);

        game.addTurnListener(trap);

        trap.onNewTurn(game);

        Tile placed = grid.getTile(trapCoords);
        assertTrue(placed instanceof StoneWall);
        assertEquals(trapCoords, placed.getCoords());
        assertFalse(game.getTurnListeners().contains(trap));
    }

    @Test
    void shouldHandleMultipleWallTrapsIndependently() {
        WallTrap trapA = new WallTrap(new Coords(2, 2), 0, 0);
        WallTrap trapB = new WallTrap(new Coords(5, 5), 0, 0);

        Hero hero = new Tank();
        hero.setCoords(new Coords(0, 0));
        squad.addHero(hero);

        game.addTurnListener(trapA);
        game.addTurnListener(trapB);

        trapA.onNewTurn(game);
        trapB.onNewTurn(game);

        Tile first = grid.getTile(new Coords(2, 2));
        Tile second = grid.getTile(new Coords(5, 5));
        assertTrue(first instanceof StoneWall);
        assertTrue(second instanceof StoneWall);
        assertFalse(game.getTurnListeners().contains(trapA));
        assertFalse(game.getTurnListeners().contains(trapB));
    }

    @Test
    void shouldNotInterfereWhenOneTrapBlockedAndAnotherFree() {
        WallTrap blockedTrap = new WallTrap(new Coords(1, 0), 0, 0);
        WallTrap freeTrap = new WallTrap(new Coords(7, 7), 0, 0);

        Hero hero = new Tank();
        hero.setCoords(new Coords(1, 0));
        squad.addHero(hero);

        game.addTurnListener(blockedTrap);
        game.addTurnListener(freeTrap);

        blockedTrap.onNewTurn(game);
        freeTrap.onNewTurn(game);

        Tile blockedTile = grid.getTile(new Coords(1, 0));
        assertFalse(blockedTile instanceof StoneWall);
        assertTrue(game.getTurnListeners().contains(blockedTrap));

        Tile freeTile = grid.getTile(new Coords(7, 7));
        assertTrue(freeTile instanceof StoneWall);
        assertFalse(game.getTurnListeners().contains(freeTrap));
    }
}
