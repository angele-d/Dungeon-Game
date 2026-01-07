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

    public GameEventType getType() {
        return type;
    }
    
    public Hero getHero() {
        return hero;
    }

    public int getDamageAmount() {
        return damageAmount;
    }
}
