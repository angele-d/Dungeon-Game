package dungeon.engine;

public class Dwarf extends Hero {
    
    private int health;

    public Dwarf() {
        super();
        health = 150;
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
}
