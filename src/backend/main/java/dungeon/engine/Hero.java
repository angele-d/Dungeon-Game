package dungeon.engine;

import java.util.ArrayList;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Observers.GameObserver;
import dungeon.engine.Strategies.BFSStrategy;
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
        this.strategy = new BFSStrategy();
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

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    /* --- Functions --- */

    public void reachTreasure(){
        notifyObservers(new GameEvent(GameEventType.TREASURE_REACHED, this, 0));
    }

    public Coords basicMove(Game game) {
        // Basic movement logic for all heroes
        Coords newCoords;
        do{
            newCoords = strategy.move(game, this);
        } while(game.getGrid().getTile(newCoords) instanceof Wall); // If Wall, try again

        // Check if newCoords is Treasure
        if (game.getGrid().getTile(newCoords) instanceof Treasure) {
            reachTreasure();
        }
        
        return newCoords;
    }

    public Coords move(Game game) {
        return basicMove(game);
    }

    /* --- GameObserver methods --- */

    public void addObserver(GameObserver observer) {
        if(!gameObservers.contains(observer))
            gameObservers.add(observer);
    }

    protected void notifyObservers(GameEvent event) {
        for(GameObserver observer : gameObservers){
            observer.update(event);
        }
    }
}
