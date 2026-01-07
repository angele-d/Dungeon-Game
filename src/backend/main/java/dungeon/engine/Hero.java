package dungeon.engine;

import dungeon.engine.Strategies.Strategy;
import dungeon.engine.Visitors.HeroVisitor;

public abstract class Hero {

    public static final int MOVE_DISTANCE = 1;
    public static final int POISON_DAMAGE_PER_TURN = 5;
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

    public abstract boolean getActionAvailable();
    public abstract void setActionAvailable(boolean status);

    // FIXME: Delete doAction (all in Visitors) (check Dwarf before deleting)
    public abstract void doAction();

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

    public Coords basicMove(Game game) {
        // Basic movement logic for all heroes
        Coords newCoords = strategy.move(game, this);

        // Tick poison effect
        for(Hero hero : game.getHeroSquad().getHeroes()){
            if(hero.getIsPoisoned()){
                hero.applyDamage(POISON_DAMAGE_PER_TURN);
            }
        }
        
        return newCoords;
    }

    public Coords move(Game game) {
        return basicMove(game);
    }
}
