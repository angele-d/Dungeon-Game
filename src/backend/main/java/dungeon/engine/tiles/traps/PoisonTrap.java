package dungeon.engine.tiles.traps;

import dungeon.engine.tiles.Trap;
import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.Visitors.AreaDamageVisitor;
import dungeon.engine.Visitors.PoisonVisitor;

public class PoisonTrap extends Trap {
    
    private int placementCost = 100; // FIXME: Change placementCost value 
    private int aStarValue = 7;

    /* --- Constructor --- */

    public PoisonTrap(Coords coords, int damage, int areaRadius) {
        super(coords, damage, areaRadius);
    }
    
    public PoisonTrap(Coords coords) {
        super(coords, 0, 0);
    }

    @Override
    public String toString() { return "poisontrap"; }

    /* --- Getters and Setters --- */

    public int getAstarValue() {
        return aStarValue;
    }

    public int getPlacementCost() {
        return placementCost;
    }
    public void setPlacementCost(int cost) {
        this.placementCost = cost;
    }

    /* --- Functions --- */

    @Override
    public void activateTrap(Game game) {
        // Implementation for poison effect can be added here  
        AreaDamageVisitor areaDamageVisitor = new AreaDamageVisitor(this.getCoords(), this.getDamage(), this.getAreaRadius());
        PoisonVisitor poisonVisitor = new PoisonVisitor(this.getCoords(), this.getAreaRadius());
        for (var hero : game.getHeroSquad().getHeroes()) {
            // Apply area damage
            hero.accept(areaDamageVisitor);
            // Apply poison status effect
            hero.accept(poisonVisitor);
        }
    }
}
