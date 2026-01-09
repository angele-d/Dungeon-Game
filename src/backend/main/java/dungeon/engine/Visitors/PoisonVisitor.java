package dungeon.engine.Visitors;

import dungeon.engine.Coords;
import dungeon.engine.Hero;
import dungeon.engine.Tank;

public class PoisonVisitor implements HeroVisitor {
    private Coords epicenter;
    private int areaRadius;

/* --- Constructor --- */

    public PoisonVisitor(Coords epicenter, int areaRadius) {
        this.epicenter = epicenter;
        this.areaRadius = areaRadius;
    }

    public PoisonVisitor(Coords coords){
        this.epicenter = coords;
        this.areaRadius = 0;
    }

/* --- Functions --- */

    /** 
     * Visit a hero and apply poison effect
     * @param hero
     */
    @Override
    public void visit(Hero hero) {
        Coords heroCoords = hero.getCoords();
        int distance = Math.abs(heroCoords.x() - epicenter.x()) + Math.abs(heroCoords.y() - epicenter.y());
        if (distance <= areaRadius) {
            // Apply poison effect
            if(!(hero instanceof Tank))
                hero.setIsPoisoned(true);
            else{
                if(hero.getActionAvailable())
                    hero.setActionAvailable(false);
                else
                    hero.setIsPoisoned(true);
            }
        }
    }   
}
