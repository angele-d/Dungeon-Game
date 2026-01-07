package dungeon.engine;

import dungeon.engine.Visitors.HeroVisitor;

public class TheMemeMaker extends Hero {
    
    private int health;
    private static final int MAX_HEALTH = 150;

    public TheMemeMaker() {
        super();
        health = MAX_HEALTH;
        // TheMemeMaker-specific initialization
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
        if(health < 0){
            health = 0;
        }
    }

    @Override
    public void doAction() {
        // Do nothing
    }

    @Override
    public void resetAction() {
        // No action to reset
    }

    public void accept(HeroVisitor visitor) {
        visitor.visit(this);
    }
}
