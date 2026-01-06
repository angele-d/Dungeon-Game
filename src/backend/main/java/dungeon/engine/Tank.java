package dungeon.engine;

public class Tank extends Hero {
    
    private int health;

    public Tank() {
        super();
        health = 200;
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
}
