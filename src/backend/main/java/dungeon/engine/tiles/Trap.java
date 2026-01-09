package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;
import dungeon.engine.Game;

public abstract class Trap extends Tile {
    private int damage;
    private int areaRadius;

/* --- Constructor --- */

    public Trap(Coords coords, int damage, int areaRadius) {
        super(coords);
        this.damage = damage;
        this.areaRadius = areaRadius;
    }

/* --- Getters and Setters --- */

    /** 
     * Get the damage of the trap
     * @return int
     */
    public int getDamage() {
        return damage;
    }
    /** 
     * Set the damage of the trap (for testing purposes)
     * @param damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /** 
     * Get the area radius of the trap
     * @return int
     */
    public int getAreaRadius() {
        return areaRadius;
    }
    /** 
     * Set the area radius of the trap (for testing purposes)
     * @param areaRadius
     */
    public void setAreaRadius(int areaRadius) {
        this.areaRadius = areaRadius;
    }

/* --- Abstract functions --- */

    public abstract int getAstarValue();

    public abstract void activateTrap(Game game);

    public abstract int getPlacementCost();

/* --- ToString --- */

    /** 
     * String representation of the trap
     * @return String
     */
    @Override
    public String toString() {
        return "trap";
    }
}
