package dungeon.engine;

public class Tank extends Hero {
    
    private int health;
    private boolean actionAvailable;

    public Tank() {
        super();
        health = 200;
        actionAvailable = true;
        // Tank-specific initialization
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
        if(health < 0){
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
            // TODO: Implement Poison Healing action
        }
        actionAvailable = false;
    }
}
