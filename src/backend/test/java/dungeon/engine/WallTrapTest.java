package dungeon.engine;

import dungeon.engine.tiles.wall.StoneWall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import dungeon.engine.tiles.traps.WallTrap;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

class WallTrapTest {

    private Game game;
    private Grid grid;
    private HeroSquad squad;

    @BeforeEach
    void setUp() {
        game = mock(Game.class);
        grid = mock(Grid.class);
        squad = mock(HeroSquad.class);

        when(game.getGrid()).thenReturn(grid);
        when(game.getHeroSquad()).thenReturn(squad);
    }

    @Test
    void testWallTrapInitialization() {
        Coords coords = new Coords(1, 2);
        int damage = 15;
        int area = 3;

        WallTrap wallTrap = new WallTrap(coords, damage, area);

        assertEquals(damage, wallTrap.getDamage(), "Les dégâts du WallTrap sont incorrects");
        assertEquals(area, wallTrap.getAreaRadius(), "La zone du WallTrap est incorrecte");
    }

    @Test
    void testWallTrapZeroValues() {
        Coords coords = new Coords(0, 0);

        WallTrap wallTrap = new WallTrap(coords, 0, 0);

        assertEquals(0, wallTrap.getDamage());
        assertEquals(0, wallTrap.getAreaRadius());
    }

    @Test
    void shouldRegisterTurnListenerOnActivateTrap() {
        // Given
        Coords trapCoords = new Coords(2, 3);
        WallTrap trap = new WallTrap(trapCoords, 0, 0);

        // When
        trap.activateTrap(game);

        // Then
        verify(game, times(1)).addTurnListener(trap);
        verifyNoMoreInteractions(game);
    }

    @Test
    void shouldTriggerTrapWhenTileIsFreeOnNewTurn() {
        // Given
        Coords trapCoords = new Coords(1, 1);
        WallTrap trap = new WallTrap(trapCoords, 0, 0);
        // One hero not on trap coords
        Hero hero = mock(Hero.class);
        when(hero.getCoords()).thenReturn(new Coords(0, 0));
        when(squad.getHeroes()).thenReturn(new ArrayList<>(List.of(hero)));

        // When
        trap.onNewTurn(game);

        // Then
        // Expect a StoneWall placed at trap coords and listener deregistered
        verify(grid, times(1)).setTile(argThat(tile -> tile instanceof StoneWall && tile.getCoords().equals(trapCoords)));
        verify(game, times(1)).removeTurnListener(trap);
    }

    @Test
    void shouldNotTriggerTrapWhenHeroIsOnTrapTile() {
        // Given
        Coords trapCoords = new Coords(3, 4);
        WallTrap trap = new WallTrap(trapCoords, 0, 0);
        Hero heroOnTrap = mock(Hero.class);
        when(heroOnTrap.getCoords()).thenReturn(trapCoords);
        when(squad.getHeroes()).thenReturn(new ArrayList<>(List.of(heroOnTrap)));

        // When
        trap.onNewTurn(game);

        // Then
        verify(grid, never()).setTile(any());
        verify(game, never()).removeTurnListener(trap);
    }

    @Test
    void shouldPlaceWallAndUnsubscribeExactlyOnceWhenMultipleHeroesNotOnTrap() {
        // Given
        Coords trapCoords = new Coords(0, 7);
        WallTrap trap = new WallTrap(trapCoords, 0, 0);
        Hero hero1 = mock(Hero.class);
        Hero hero2 = mock(Hero.class);
        when(hero1.getCoords()).thenReturn(new Coords(0, 0));
        when(hero2.getCoords()).thenReturn(new Coords(1, 1));
        when(squad.getHeroes()).thenReturn(new ArrayList<>(List.of(hero1, hero2)));

        // When
        trap.onNewTurn(game);

        // Then
        InOrder inOrder = inOrder(grid, game);
        inOrder.verify(grid, times(1)).setTile(argThat(tile -> tile instanceof StoneWall && tile.getCoords().equals(trapCoords)));
        inOrder.verify(game, times(1)).removeTurnListener(trap);
        // Game getters are legitimate interactions; only ensure no extra grid calls
        verifyNoMoreInteractions(grid);
    }

    @Test
    void shouldHandleMultipleWallTrapsIndependently() {
        // Given
        WallTrap trapA = new WallTrap(new Coords(2, 2), 0, 0);
        WallTrap trapB = new WallTrap(new Coords(5, 5), 0, 0);

        Hero hero = mock(Hero.class);
        // Hero away from both traps
        when(hero.getCoords()).thenReturn(new Coords(0, 0));
        when(squad.getHeroes()).thenReturn(new ArrayList<>(List.of(hero)));

        // When
        trapA.onNewTurn(game);
        trapB.onNewTurn(game);

        // Then
        verify(grid, times(1)).setTile(argThat(tile -> tile instanceof StoneWall && tile.getCoords().equals(new Coords(2, 2))));
        verify(grid, times(1)).setTile(argThat(tile -> tile instanceof StoneWall && tile.getCoords().equals(new Coords(5, 5))));
        verify(game, times(1)).removeTurnListener(trapA);
        verify(game, times(1)).removeTurnListener(trapB);
    }

    @Test
    void shouldNotInterfereWhenOneTrapBlockedAndAnotherFree() {
        // Given
        WallTrap blockedTrap = new WallTrap(new Coords(1, 0), 0, 0);
        WallTrap freeTrap = new WallTrap(new Coords(7, 7), 0, 0);

        Hero hero = mock(Hero.class);
        // Hero standing on blocked trap coords
        when(hero.getCoords()).thenReturn(new Coords(1, 0));
        when(squad.getHeroes()).thenReturn(new ArrayList<>(List.of(hero)));

        // When
        blockedTrap.onNewTurn(game);
        freeTrap.onNewTurn(game);

        // Then
        // BlockedTrap should do nothing
        verify(grid, never()).setTile(argThat(tile -> tile instanceof StoneWall && tile.getCoords().equals(new Coords(1, 0))));
        verify(game, never()).removeTurnListener(blockedTrap);

        // FreeTrap should place its wall and unsubscribe
        verify(grid, times(1)).setTile(argThat(tile -> tile instanceof StoneWall && tile.getCoords().equals(new Coords(7, 7))));
        verify(game, times(1)).removeTurnListener(freeTrap);
    }
}
