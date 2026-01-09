package dungeon.engine.Observers;

import dungeon.engine.Hero;

public class GameEvent {

    private GameEventType type;
    private Hero hero;
    private int damageAmount;

    /* --- Constructor --- */

    public GameEvent(GameEventType type, Hero hero, int damageAmount) {
        this.type = type;
        this.hero = hero;
        this.damageAmount = damageAmount;
    }

    /* --- Getters and Setters --- */

    /** 
     * Get the type of the game event
     * @return GameEventType
     */
    public GameEventType getType() {
        return type;
    }

    /** 
     * Get the hero involved in the event
     * @return Hero
     */
    public Hero getHero() {
        return hero;
    }

    /** 
     * Get the damage amount involved in the event
     * @return int
     */
    public int getDamageAmount() {
        return damageAmount;
    }
}
