package dungeon.engine;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Visitors.HeroVisitor;

public class Tank extends Hero {

    private int health;
    private boolean actionAvailable;
    private static final int MAX_HEALTH = 200;

/* --- Constructor --- */

    public Tank() {
        super();
        health = MAX_HEALTH;
        actionAvailable = true;
        // Tank-specific initialization
    }

/* --- Getters and Setters --- */

    /**
     * Gets the current health of the Tank. 
     * @return int
     */
    public int getHealth() {
        return health;
    }
    /** 
     * Sets the current health of the Tank.
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /** 
     * Gets the maximum health of the Tank.
     * @return int
     */
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    /** 
     * Gets whether the Tank has an action available.
     * @return boolean
     */
    public boolean getActionAvailable() {
        return actionAvailable;
    }
    /** 
     * Sets the availability of the Tank's action.
     * @param status
     */
    public void setActionAvailable(boolean status) {
        actionAvailable = status;
    }

/* --- Functions --- */

    /** 
     * Applies damage to the Tank.
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
     * Resets the Tank's action.
     */
    @Override
    public void resetAction() {
        actionAvailable = true;
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
     * Returns the string representation of the Tank.
     * @return String
     */
    @Override
    public String toString() {
        return "Tank";
    }
}
