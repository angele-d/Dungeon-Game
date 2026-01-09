package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import dungeon.engine.Visitors.AreaDamageVisitor;
import dungeon.engine.tiles.Trap;
import dungeon.engine.Game;
import dungeon.engine.Hero;

public class Mine extends Trap {

    private static final int PLACEMENT_COST = 100; // FIXME: Change placementCost value
    private static final int ASTAR_VALUE = 5;

    /* --- Constructor --- */

    public Mine(Coords coords) {
        super(coords, 50, 2);
    }

    public Mine(Coords coords, int damage, int areaRadius) {
        super(coords, damage, areaRadius);
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
        AreaDamageVisitor areaDamageVisitor = new AreaDamageVisitor(this.getCoords(), this.getDamage(),
                this.getAreaRadius());
        for (Hero hero : game.getHeroSquad().getHeroes()) {
            hero.accept(areaDamageVisitor);
        }
    }

    /* --- ToString --- */

    @Override
    public String toString() {
        return "mine";
    }
}
