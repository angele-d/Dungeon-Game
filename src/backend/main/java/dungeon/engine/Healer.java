package dungeon.engine;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Visitors.HealVisitor;
import dungeon.engine.Visitors.HeroVisitor;

public class Healer extends Hero {
    
    private int health;
    private static final int PERSONAL_HEAL_PERCENTAGE = 20;
    private static final int MAX_HEALTH = 150;

    public Healer() {
        super();
        health = MAX_HEALTH;
        // Healer-specific initialization
    }

    /* --- Getters and Setters --- */
    
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public boolean getActionAvailable() {
        return false;
    }
    public void setActionAvailable(boolean status) {
        // Do nothing   
    }

    /* --- Functions --- */

    @Override
    public void applyDamage(int damage) {
        health -= damage;
        notifyObservers(new GameEvent(GameEventType.DAMAGE_TAKEN, this, damage));
        if(health <= 0){
            health = 0;
            notifyObservers(new GameEvent(GameEventType.HERO_DEATH, this, 0));
        }
    }

    @Override
    public void resetAction() {
        // No action to reset
    }

    public void accept(HeroVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Coords move(Game game) {
        // Healer-specific movement logic

        // Basic movement
        Coords newCoords = basicMove(game);

        // Personal healing ability
        health = Math.min(MAX_HEALTH, health + (health * PERSONAL_HEAL_PERCENTAGE) / 100);
        
        // Zone healing ability
        HealVisitor healVisitor = new HealVisitor();
        for(Hero ally : game.getHeroSquad().getHeroes()){
            ally.accept(healVisitor);
        }

        return newCoords;
    }
}