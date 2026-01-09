package dungeon.engine.tiles.traps;

import dungeon.engine.*;
import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.wall.StoneWall;

public class WallTrap extends Trap implements HeroTurnListener {

    private static final int PLACEMENT_COST = 500;
    private static final int DEFAULT_DAMAGE = 50;
    private static final int DEFAULT_AREA = 1;
    private static final int ASTAR_VALUE = 3;


/* --- Constructor --- */

    public WallTrap(Coords coords) {
        super(coords, DEFAULT_DAMAGE, DEFAULT_AREA);
    }

    public WallTrap(Coords coords, int damage, int area) {
        super(coords, damage, area);
    }


/* --- Getters and Setters --- */

    /** 
     * Get the placement cost of the wall trap
     * @return int
     */
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

    /** 
     * Get the A* value of the wall trap
     * @return int
     */
    public int getAstarValue() {
        return ASTAR_VALUE;
    }

/* --- Functions --- */

    /** 
     * Activate the wall trap
     * @param game
     */
    @Override
    public void activateTrap(Game game) {
        game.addHeroTurnListener(this);

        // WallTrap already changed into StoneWall on onNewTurn
    }

    /** 
     * Handle new turn event
     * @param game
     */
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

    /** 
     * String representation of the wall trap
     * @return String
     */
    public String toString() {
        return "walltrap";
    }
}
