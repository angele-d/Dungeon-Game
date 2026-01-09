package dungeon.engine.Visitors;

import dungeon.engine.Coords;
import dungeon.engine.Hero;

public class AreaDamageVisitor implements HeroVisitor {
    private Coords epicenter;
    private int damage;
    private int areaRadius; //0 = center, 1 = adjacent, etc.
    private final int DAMAGE_DIMINUTION_PERCENTAGE = 20;

/* --- Constructor --- */

    public AreaDamageVisitor(Coords epicenter, int damage, int areaRadius) {
        this.epicenter = epicenter;
        this.damage = damage;
        this.areaRadius = areaRadius;
    }

    public AreaDamageVisitor(Coords coords){
        this.epicenter = coords;
        this.damage = 10;
        this.areaRadius = 1;
    }

/* --- Functions --- */

    /** 
     * Visit a hero and apply area damage
     * @param hero
     */
    @Override
    public void visit(Hero hero) {
        Coords heroCoords = hero.getCoords();
        int distance = Math.abs(heroCoords.x() - epicenter.x()) + Math.abs(heroCoords.y() - epicenter.y());
        if (distance == 0) {
            // Direct hit
            hero.applyDamage(damage);
        }
        else if (distance <= areaRadius) {
            // Area damage with diminution
            hero.applyDamage(damage * (100 - DAMAGE_DIMINUTION_PERCENTAGE) / 100);
        }
    }
}
