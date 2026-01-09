package dungeon.engine;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Visitors.HealVisitor;
import dungeon.engine.Visitors.HeroVisitor;

public class Healer extends Hero {

    private int health;
    private static final int PERSONAL_HEAL_PERCENTAGE = 20;
    private static final int MAX_HEALTH = 150;

/* --- Constructor --- */

    public Healer() {
        super();
        health = MAX_HEALTH;
        // Healer-specific initialization
    }

/* --- Getters and Setters --- */

    /** 
     * Gets the current health of the Healer.
     * @return int
     */
    public int getHealth() {
        return health;
    }
    /** 
     * Sets the current health of the Healer.
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /** 
     * Gets the maximum health of the Healer.
     * @return int
     */
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    /** 
     * Gets whether the Healer has an action available.
     * @return boolean
     */
    public boolean getActionAvailable() {
        return false;
    }
    /** 
     * Sets the availability of the Healer's action.
     * @param status
     */
    public void setActionAvailable(boolean status) {
        // Do nothing
    }
    
/* --- Functions --- */

    /** 
     * Applies damage to the Healer.
     * @param damage
     */
    @Override
    public void applyDamage(int damage) {
        if (getHealth() > 0) {
            health -= damage;
            notifyObservers(new GameEvent(GameEventType.DAMAGE_TAKEN, this, damage));
        }
        if (health <= 0) {
            health = 0;
            notifyObservers(new GameEvent(GameEventType.HERO_DEATH, this, 0));
        }
    }

    /** 
     * Resets the Healer's action.
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

    /** 
     * Moves the Healer according to its strategy.
     * @param game
     * @return Coords
     */
    @Override
    public Coords move(Game game) {
        if(health <= 0) {
            return this.getCoords(); // Dead heroes don't move
        }
        // Healer-specific movement logic

        // Basic movement
        Coords newCoords = basicMove(game);

        // Personal healing ability
        health = Math.min(MAX_HEALTH, health + (health * PERSONAL_HEAL_PERCENTAGE) / 100);

        // Zone healing ability
        HealVisitor healVisitor = new HealVisitor();
        for (Hero ally : game.getHeroSquad().getHeroes()) {
            ally.accept(healVisitor);
        }

        return newCoords;
    }

/* --- toString --- */

    /** 
     * String representation of the Healer.
     * @return String
     */
    @Override
    public String toString() {
        return "Healer";
    }
}