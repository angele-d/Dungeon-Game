package dungeon.engine.tiles.traps;

import dungeon.engine.Coords;
import dungeon.engine.Visitors.AreaDamageVisitor;
import dungeon.engine.tiles.Trap;
import dungeon.engine.Game;
import dungeon.engine.Hero;

public class Mine extends Trap {
    
    /* --- Constructor --- */
    
    public Mine(Coords coords,int damage,int areaRadius) {
        super(coords, damage, areaRadius);
    }

    /* --- Functions --- */

    @Override
    public void activateTrap(Game game){
        AreaDamageVisitor areaDamageVisitor = new AreaDamageVisitor(this.getCoords(), this.getDamage(), this.getAreaRadius());
        for(Hero hero : game.getHeroSquad().getHeroes()){
            hero.accept(areaDamageVisitor);
        }
    }

    /* A* pathfinding value for the Mine trap */

    public int getAstarValue(){
        return 5;
    }
}
