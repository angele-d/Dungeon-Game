package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import dungeon.engine.Visitors.AreaDamageVisitor;
import dungeon.engine.tiles.Trap;
import dungeon.engine.Game;
import dungeon.engine.Hero;

public class Mine extends Trap {

    private int placementCost = 100; // FIXME: Change placementCost value
    private int aStarValue = 5;

    /* --- Constructor --- */
    public Mine(Coords coords) {
        super(coords, 50, 2);
    }

    public Mine(Coords coords,int damage,int areaRadius) {
        super(coords, damage, areaRadius);
    }

    /* --- Getters and Setters --- */

    public int getAstarValue(){
        return aStarValue;
    }

    public int getPlacementCost(){
        return placementCost;
    }
    public void setPlacementCost(int cost){
        this.placementCost = cost;
    }


    /* --- Functions --- */

    @Override
    public void activateTrap(Game game){
        AreaDamageVisitor areaDamageVisitor = new AreaDamageVisitor(this.getCoords(), this.getDamage(), this.getAreaRadius());
        for(Hero hero : game.getHeroSquad().getHeroes()){
            hero.accept(areaDamageVisitor);
        }
    }

    @Override
    public String toString() {
        return "mine";
    }
}
