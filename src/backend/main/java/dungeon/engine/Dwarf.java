package dungeon.engine;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Visitors.HeroVisitor;

public class Dwarf extends Hero {
    
    private int health;
    private boolean actionAvailable;
    private static final int MAX_HEALTH = 150;

    public Dwarf() {
        super();
        health = MAX_HEALTH;
        actionAvailable = true;
        // Dwarf-specific initialization
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
        return actionAvailable;
    }
    public void setActionAvailable(boolean status) {
        actionAvailable = status;
    }

    /* --- Functions --- */

    @Override
    public void applyDamage(int damage) {
        health -= damage;
        notifyObservers(new GameEvent(GameEventType.DAMAGE_TAKEN, this, damage));
        if (health <= 0) {
            health = 0;
            notifyObservers(new GameEvent(GameEventType.HERO_DEATH, this, 0));
        }
    }

    @Override
    public void doAction() {
        if(getActionAvailable()){
            // TODO: Implement Wall breaking action
        }
        setActionAvailable(false);
    }

    @Override
    public void resetAction() {
        setActionAvailable(true);
    }

    public void accept(HeroVisitor visitor) {
        visitor.visit(this);
    }
}