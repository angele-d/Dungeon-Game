package dungeon;

public abstract class Hero {
    private int health;

    public Hero() {
        // Common initialization for all heroes
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
