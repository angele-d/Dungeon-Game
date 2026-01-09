package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import dungeon.engine.Visitors.AreaDamageVisitor;
import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.Empty;
import dungeon.engine.Game;
import dungeon.engine.Hero;

public class Mine extends Trap {

    private static final int PLACEMENT_COST = 100;
    private static final int DEFAULT_DAMAGE = 500000000;
    private static final int ASTAR_VALUE = 5;

/* --- Constructor --- */

    public Mine(Coords coords) {
        super(coords, DEFAULT_DAMAGE, 2);
    }

    public Mine(Coords coords, int damage, int areaRadius) {
        super(coords, damage, areaRadius);
    }

/* --- Getters and Setters --- */

    /** 
     * Get the A* value of the mine
     * @return int
     */
    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    /** 
     * Get the placement cost of the mine
     * @return int
     */
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

    /* --- Functions --- */

    /** 
     * Activate the mine trap
     * @param game
     */
    @Override
    public void activateTrap(Game game) {
        AreaDamageVisitor areaDamageVisitor = new AreaDamageVisitor(this.getCoords(), this.getDamage(),
                this.getAreaRadius());
        for (Hero hero : game.getHeroSquad().getHeroes()) {
            hero.accept(areaDamageVisitor);
        }

        // Remove the mine from the game grid after activation
        Empty emptyTile = new Empty(this.getCoords());
        game.getGrid().setTile(emptyTile);
    }

/* --- ToString --- */

    /** 
     * String representation of the mine
     * @return String
     */
    @Override
    public String toString() {
        return "mine";
    }
}
