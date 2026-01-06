package dungeon.engine;

public abstract class Hero {

    public static final int MOVE_DISTANCE = 1;
    public boolean isPoisoned = false;
    public Coords coords;
    public Strategy strategy;

    public Hero() {
        // Common initialization for all heroes
    }

    /* --- Abstract Functions --- */

    public abstract void applyDamage(int damage);

    public abstract void doAction();

    public abstract boolean isActionAvailable();

    public abstract void resetAction();

    /* --- Getters and Setters --- */

    public boolean getIsPoisoned() {
        return isPoisoned;
    }
    public void setIsPoisoned(boolean status) {
        isPoisoned = status;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }
    public Coords getCoords() {
        return coords;
    }

    /* --- Functions --- */

    public void move(Game game) {
        // Common movement logic for all heroes (expect Healer -> see Healer.java)
        // FIXME: Check: Implement common movement logic
        strategy.move(game.getGrid(), this);
    }
}
