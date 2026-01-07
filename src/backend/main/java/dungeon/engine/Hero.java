package dungeon.engine;

import dungeon.engine.Visitors.HeroVisitor;

public abstract class Hero {

    public static final int MOVE_DISTANCE = 1;
    public boolean isPoisoned = false;
    public Coords coords;
    public Strategy strategy;

    public Hero() {
        // Common initialization for all heroes
    }

    /* --- Abstract Functions --- */

    public abstract int getHealth();
    public abstract void setHealth(int health);

    public abstract int getMaxHealth();

    public abstract void applyDamage(int damage);

    public abstract void doAction();

    public abstract boolean isActionAvailable();

    public abstract void resetAction();

    public abstract void accept(HeroVisitor visitor);

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

    public Coords move(Game game) {
        // Common movement logic for all heroes (except Healer -> see Healer.java)
        return strategy.move(game, this);
    }
}
