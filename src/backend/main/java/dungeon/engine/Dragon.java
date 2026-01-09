package dungeon.engine;

import dungeon.engine.Observers.GameEvent;
import dungeon.engine.Observers.GameEventType;
import dungeon.engine.Visitors.HeroVisitor;
import dungeon.engine.tiles.wall.WoodWall;

import java.util.ArrayList;

public class Dragon extends Hero {

    private int health;
    private int wallFireUses;
    private final int MAX_WALL_FIRE_USES = 5;
    private static final int MAX_HEALTH = 150;

/* --- Constructor --- */

    public Dragon() {
        super();
        health = MAX_HEALTH;
        wallFireUses = 0;
        // Dragon-specific initialization
    }

/* --- Getters and Setters --- */

    /** 
     * Gets the current health of the Dragon.
     * @return int
     */
    public int getHealth() {
        return health;
    }
    /** 
     * Sets the current health of the Dragon.
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /** 
     * Gets the maximum health of the Dragon.
     * @return int
     */
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    /** 
     * Checks if the Dragon can use its wall fire ability.
     * @return boolean
     */
    public boolean getActionAvailable() {
        return wallFireUses < MAX_WALL_FIRE_USES;
    }
    /** 
     * Sets the availability of the Dragon's wall fire ability.
     * @param status
     */
    public void setActionAvailable(boolean status) {
        if (status == false) { // use wall fire
            wallFireUses++;
        } else { // reset uses
            wallFireUses = 0;
        }
    }

/* --- Functions --- */

    /** 
     * Applies damage to the Dragon and notifies observers of damage taken and death.
     * @param damage
     */
    @Override
    public void applyDamage(int damage) {
        health -= damage;
        notifyObservers(new GameEvent(GameEventType.DAMAGE_TAKEN, this, damage));
        if (health <= 0) {
            health = 0;
            notifyObservers(new GameEvent(GameEventType.HERO_DEATH, this, 0));
        }
    }

    /** 
     * Resets the Dragon's wall fire ability usage.
     */
    @Override
    public void resetAction() {
        setActionAvailable(true);
    }

    /** 
     * Accepts a visitor for the Dragon.
     * @param visitor
     */
    public void accept(HeroVisitor visitor) {
        visitor.visit(this);
    }

    /** 
     * Moves the Dragon and interacts with neighboring wood walls.
     * @param game
     * @return Coords
     */
    @Override
    public Coords move(Game game) {
        // Dragon-specific movement logic

        // Basic movement
        Coords newCoords = basicMove(game);

        // Fire neighbor wood walls if action available
        ArrayList<Coords> neighbors = game.getGrid().getNeighborsCoords(newCoords);
        for (Coords coord : neighbors) {
            Tile tile = game.getGrid().getTile(coord);
            if (tile instanceof WoodWall && getActionAvailable()) {
                game.addFireTurnListener((FireTurnListener) tile);
                setActionAvailable(false); // increase uses
            }
        }

        return newCoords;
    }

/* --- toString --- */

    /** 
     * Returns the string representation of the Dragon.
     * @return String
     */
    @Override
    public String toString() {
        return "Dragon";
    }
}