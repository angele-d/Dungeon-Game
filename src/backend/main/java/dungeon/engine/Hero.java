package dungeon.engine;

public abstract class Hero {

    public Hero() {
        // Common initialization for all heroes
    }

    public abstract void applyDamage(int damage);

    public abstract void doAction();

    public abstract boolean isActionAvailable();

    public void move(){
        // Common movement logic for all heroes (expect Healer -> see Healer.java)
        // TODO: Implement common movement logic
    }
}
