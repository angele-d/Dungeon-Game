package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;
import dungeon.engine.Game;

public abstract class Trap extends Tile {
    private int damage;
    private int areaRadius;

    /* --- Constructor --- */

    public Trap(Coords coords,int damage,int areaRadius) {
        super(coords);
        this.damage = damage;
        this.areaRadius = areaRadius;
    }

    /* --- Getters and Setters --- */

    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAreaRadius() {
        return areaRadius;
    }
    public void setAreaRadius(int areaRadius) {
        this.areaRadius = areaRadius;
    }

    public Coords getTrapCoords() {
        return this.getCoords();
    }
    public void setTrapCoords(Coords coords) {
        this.setCoords(coords);
    }

    @Override
    public String toString() {
        return "trap";
    }

    /* --- Abstract functions --- */

    public abstract int getAstarValue();

    public abstract void activateTrap(Game game);

    public abstract int getPlacementCost();
    public abstract void setPlacementCost(int cost);

}
