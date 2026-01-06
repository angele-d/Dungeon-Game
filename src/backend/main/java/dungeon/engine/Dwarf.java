package dungeon.engine;

public class Dwarf extends Hero {
    
    private int health;
    private boolean actionAvailable;

    public Dwarf() {
        super();
        health = 150;
        actionAvailable = true;
        // Dwarf-specific initialization
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void applyDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    @Override
    public boolean isActionAvailable() {
        return actionAvailable;
    }

    @Override
    public void doAction() {
        if(isActionAvailable()){
            // TODO: Implement Wall breaking action
        }
        actionAvailable = false;
    }

    @Override
    public void resetAction() {
        actionAvailable = true;
    }
}