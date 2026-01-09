package dungeon.engine;

import java.util.ArrayList;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Observers.GameObserver;
import dungeon.engine.Strategies.Strategy;
import dungeon.engine.Visitors.HeroVisitor;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.Wall;

public abstract class Hero {

    public static final int MOVE_DISTANCE = 1;
    public boolean isPoisoned = false;
    public Coords coords;
    public Strategy strategy;
    public ArrayList<GameObserver> gameObservers = new ArrayList<>();

/* --- Constructor --- */

    public Hero() {
        // Common initialization for all heroes
        this.strategy = null;
    }

/* --- Abstract Functions --- */

    public abstract int getHealth();

    public abstract void setHealth(int health);

    public abstract int getMaxHealth();

    public abstract void applyDamage(int damage);

    public abstract boolean getActionAvailable();

    public abstract void setActionAvailable(boolean status);

    public abstract void resetAction();

    public abstract void accept(HeroVisitor visitor);

/* --- Getters and Setters --- */

    /** 
     * Gets whether the Hero is poisoned.
     * @return boolean
     */
    public boolean getIsPoisoned() {
        return isPoisoned;
    }
    /** 
     * Sets whether the Hero is poisoned.
     * @param status
     */
    public void setIsPoisoned(boolean status) {
        isPoisoned = status;
    }

    /** 
     * Sets the coordinates of the Hero.
     * @param coords
     */
    public void setCoords(Coords coords) {
        this.coords = coords;
    }
    /** 
     * Gets the coordinates of the Hero.
     * @return Coords
     */
    public Coords getCoords() {
        return coords;
    }

    /** 
     * Sets the strategy of the Hero.
     * @param strategy
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    /** 
     * Gets the strategy of the Hero.
     * @return Strategy
     */
    public Strategy getStrategy() {
        return this.strategy;
    }

/* --- Functions --- */

    /** 
     * Notifies observers that the Hero has reached the treasure.
     */
    public void reachTreasure() {
        notifyObservers(new GameEvent(GameEventType.TREASURE_REACHED, this, 0));
    }

    /** 
     * Moves the Hero based on its strategy, avoiding walls and handling treasure.
     * @param game
     * @return Coords
     */
    public Coords basicMove(Game game) {
        // Basic movement logic for all heroes
        Coords newCoords;
        do {
            newCoords = strategy.move(game, this);
        } while (game.getGrid().getTile(newCoords) instanceof Wall); // If Wall, try again

        // Check if newCoords is Treasure
        if (game.getGrid().getTile(newCoords) instanceof Treasure) {
            reachTreasure();
        }

        return newCoords;
    }

    /** 
     * Moves the Hero according to its strategy.
     * @param game
     * @return Coords
     */
    public Coords move(Game game) {
        return basicMove(game);
    }
    
/* --- GameObserver methods --- */

    /** 
     * Adds an observer to the Hero.
     * @param observer
     */
    public void addObserver(GameObserver observer) {
        if (!gameObservers.contains(observer))
            gameObservers.add(observer);
    }

    /** 
     * Notifies all observers of a game event.
     * @param event
     */
    protected void notifyObservers(GameEvent event) {
        for (GameObserver observer : gameObservers) {
            observer.update(event);
        }
    }
}
