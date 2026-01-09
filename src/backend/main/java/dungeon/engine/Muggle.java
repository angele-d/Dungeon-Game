package dungeon.engine;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Visitors.HeroVisitor;

public class Muggle extends Hero {

    private int health;
    private static final int MAX_HEALTH = 150;

/* --- Constructor --- */

    public Muggle() {
        super();
        health = MAX_HEALTH;
        // TheMemeMaker-specific initialization
    }

/* --- Getters and Setters --- */

    /** 
     * Gets the current health of the Muggle.
     * @return int
     */
    public int getHealth() {
        return health;
    }
    /** 
     * Sets the current health of the Muggle.
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /** 
     * Gets the maximum health of the Muggle.
     * @return int
     */
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    /** 
     * Gets whether the Muggle has an action available.
     * @return boolean
     */
    public boolean getActionAvailable() {
        return false;
    }
    /** 
     * Sets the availability of the Muggle's action.
     * @param status
     */
    public void setActionAvailable(boolean status) {
        // Do nothing
    }
    
/* --- Functions --- */

    /** 
     * Applies damage to the Muggle.
     * @param damage
     */
    @Override
    public void applyDamage(int damage) {
        health -= damage;
        notifyObservers(new GameEvent(GameEventType.DAMAGE_TAKEN, this, damage));
        if (health <= 0) {
            health = 0;
            notifyObservers(new GameEvent(GameEventType.HERO_DEATH, this, 0));
        }
    }

    /** 
     * Resets the Muggle's action.
     */
    @Override
    public void resetAction() {
        // No action to reset
    }

    /** 
     * Accepts a visitor.
     * @param visitor
     */
    public void accept(HeroVisitor visitor) {
        visitor.visit(this);
    }
    
/* --- toString --- */

    /** 
     * Returns the string representation of the Muggle.
     * @return String
     */
    @Override
    public String toString() {
        return "Muggle";
    }
}
