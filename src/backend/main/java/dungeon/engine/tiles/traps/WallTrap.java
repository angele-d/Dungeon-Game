package dungeon.engine.tiles.traps;

import dungeon.engine.*;
import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.wall.StoneWall;

public class WallTrap extends Trap implements TurnListener {

    private int placementCost = 150; // #FIXME: Change placementCost value
    private int aStarValue = 3;

    /* --- Constructor --- */
    public WallTrap(Coords coords) {
        super(coords,10, 1);
    }

    public WallTrap(Coords coords,int damage,int area) {
        super(coords,damage, area);
    }

    @Override
    public String toString() {
        return "walltrap";
    }

    /* --- Getters and Setters --- */

    public int getPlacementCost(){
        return placementCost;
    }
    public void setPlacementCost(int cost){
        this.placementCost = cost;
    }

    /* --- Functions --- */

    @Override
    public void activateTrap(Game game){
        game.addTurnListener(this);
    }

    @Override
    public void onNewTurn(Game game) {
        boolean available = true;
        for (Hero hero: game.getHeroSquad().getHeroes()) {
            if (hero.getCoords().equals(getCoords())) {
                available = false;
                break;
            }
        }
        if (available) {
            Tile tile = new StoneWall(this.getCoords());
            game.getGrid().setTile(tile);
            game.removeTurnListener(this);
        }
    }

    
    public int getAstarValue(){
        return aStarValue;
    }
}
