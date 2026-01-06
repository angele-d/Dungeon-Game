package dungeon.engine;

public class TheMemeMaker extends Hero {
    
    private int health;

    public TheMemeMaker() {
        super();
        health = 150;
        // TheMemeMaker-specific initialization
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
        return false;
    }

    @Override
    public void doAction() {
        // Do nothing
    }
}
