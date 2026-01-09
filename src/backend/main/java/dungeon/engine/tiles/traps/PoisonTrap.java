package dungeon.engine.tiles.traps;

import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.Empty;
import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Visitors.AreaDamageVisitor;
import dungeon.engine.Visitors.PoisonVisitor;

public class PoisonTrap extends Trap {

    private static final int PLACEMENT_COST = 100;
    private static final int ASTAR_VALUE = 7;

    /* --- Constructor --- */

    public PoisonTrap(Coords coords, int damage, int areaRadius) {
        super(coords, damage, areaRadius);
    }

    public PoisonTrap(Coords coords) {
        super(coords, 0, 0);
    }

    /* --- Getters and Setters --- */

    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

    /* --- Functions --- */

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

    @Override
    public String toString() {
        return "poisontrap";
    }
}
