package dungeon.engine.tiles.traps;

import dungeon.engine.*;
import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.wall.StoneWall;

public class WallTrap extends Trap implements HeroTurnListener {

    private static final int PLACEMENT_COST = 150; // #FIXME: Change placementCost value
    private static final int ASTAR_VALUE = 3;

    /* --- Constructor --- */

    public WallTrap(Coords coords) {
        super(coords, 10, 1);
    }

    public WallTrap(Coords coords, int damage, int area) {
        super(coords, damage, area);
    }

    /* --- Getters and Setters --- */

    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    /* --- Functions --- */

    @Override
    public void activateTrap(Game game) {
        game.addHeroTurnListener(this);

        // WallTrap already changed into StoneWall on onNewTurn
    }

    @Override
    public void onNewTurn(Game game) {
        boolean available = true;
        for (Hero hero : game.getHeroSquad().getHeroes()) {
            if (hero.getCoords().equals(getCoords())) {
                available = false;
                break;
            }
        }
        if (available) {
            Tile tile = new StoneWall(this.getCoords());
            game.getGrid().setTile(tile);
            game.removeHeroTurnListener(this);
        }
    }

    /* --- ToString --- */

    public String toString() {
        return "walltrap";
    }
}
