package dungeon.engine;

public class TheMemeMarker extends Hero {
    
    private int health;

    public TheMemeMarker() {
        super();
        health = 150;
        // TheMemeMarker-specific initialization
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
