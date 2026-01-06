package dungeon.engine;

public abstract class Hero {

    public static final int MOVE_DISTANCE = 1;
    public boolean isPoisoned = false;

    public Hero() {
        // Common initialization for all heroes
    }

    /* --- Abstract Functions --- */

    public abstract void applyDamage(int damage);

    public abstract void doAction();

    public abstract boolean isActionAvailable();

    /* --- Getters and Setters --- */

    public boolean getIsPoisoned() {
        return isPoisoned;
    }
    public void setIsPoisoned(boolean status) {
        isPoisoned = status;
    }

    /* --- Functions --- */

    public void move(){
        // Common movement logic for all heroes (expect Healer -> see Healer.java)
        // TODO: Implement common movement logic
    }
}
