package dungeon.engine.tiles.traps;

import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.Empty;
import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Visitors.AreaDamageVisitor;
import dungeon.engine.Visitors.PoisonVisitor;

public class PoisonTrap extends Trap {

    private static final int PLACEMENT_COST = 700;
    private static final int ASTAR_VALUE = 5;

/* --- Constructor --- */

    public PoisonTrap(Coords coords, int damage, int areaRadius) {
        super(coords, damage, areaRadius);
    }

    public PoisonTrap(Coords coords) {
        super(coords, 0, 0);
    }

/* --- Getters and Setters --- */

    /** 
     * Get the A* value of the poison trap
     * @return int
     */
    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    /** 
     * Get the placement cost of the poison trap
     * @return int
     */
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

/* --- Functions --- */

    /** 
     * Activate the poison trap
     * @param game
     */
    @Override
    public void activateTrap(Game game) {
        // Implementation for poison effect can be added here
        AreaDamageVisitor areaDamageVisitor = new AreaDamageVisitor(this.getCoords(), this.getDamage(),
                this.getAreaRadius());
        PoisonVisitor poisonVisitor = new PoisonVisitor(this.getCoords(), this.getAreaRadius());
        for (var hero : game.getHeroSquad().getHeroes()) {
            // Apply area damage
            hero.accept(areaDamageVisitor);
            // Apply poison status effect
            hero.accept(poisonVisitor);
        }

        // Remove the poison trap from the game grid after activation
        Empty emptyTile = new Empty(this.getCoords());
        game.getGrid().setTile(emptyTile);
    }

/* --- ToString --- */

    /** 
     * String representation of the poison trap
     * @return String
     */
    @Override
    public String toString() {
        return "poisontrap";
    }
}
