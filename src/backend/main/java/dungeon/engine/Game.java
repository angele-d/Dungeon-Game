package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.Observers.ScoreManager;
import dungeon.engine.Strategies.Strategy;
import dungeon.engine.tiles.Trap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public static final int POISON_DAMAGE_PER_TURN = 5;
    public static final int INITIAL_MONEY = 5000;
    private int id;
    private Grid grid;
    private Grid blueprint;
    private HeroSquad heroSquad;
    private int money;
    private int turn;
    private ArrayList<HeroTurnListener> heroTurnListeners;
    private ArrayList<FireTurnListener> fireTurnListeners;
    private ScoreManager scoreManager;
    private int wave;
    private int seed;
    private Strategy strategy;

/* --- Constructor --- */

    public Game(int id, int seed) {
        this.grid = new Grid(getId());
        this.blueprint = grid.clone();
        this.id = id;
        this.heroSquad = new HeroSquad();
        this.money = INITIAL_MONEY;
        this.turn = 0;
        this.heroTurnListeners = new ArrayList<HeroTurnListener>();
        this.fireTurnListeners = new ArrayList<FireTurnListener>();
        this.scoreManager = new ScoreManager();
        this.seed = seed;
    }

    public Game(int id) {
        this.id = id;
        this.grid = new Grid(getId());
        this.blueprint = grid.clone();
        this.heroSquad = new HeroSquad();
        this.money = INITIAL_MONEY;
        this.turn = 0;
        this.heroTurnListeners = new ArrayList<HeroTurnListener>();
        this.fireTurnListeners = new ArrayList<FireTurnListener>();
        this.scoreManager = new ScoreManager();
        this.seed = id;
    }

    public Game() {
        this.grid = new Grid();
        this.blueprint = grid.clone();
        this.id = 1;
        this.heroSquad = new HeroSquad();
        this.money = INITIAL_MONEY;
        this.turn = 0;
        this.scoreManager = new ScoreManager();
        this.heroTurnListeners = new ArrayList<HeroTurnListener>();
        this.fireTurnListeners = new ArrayList<FireTurnListener>();
    }

/* --- Getters and Setters --- */

    /** 
     * Gets the treasure tile from the grid.
     * @return Tile
     */
    private Tile getTreasure() {
        return grid.getTreasure();
    }

    /** 
     * Gets the starting point tile from the grid.
     * @return Tile
     */
    private Tile getStartingPoint() {
        return grid.getStartingPoint();
    }

    /** 
     * Gets the current grid.
     * @return Grid
     */
    public Grid getGrid() {
        return grid;
    }
    /** 
     * Sets the current grid.
     * @param grid
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /** 
     * Gets the game ID.
     * @return int
     */
    public int getId() {
        return id;
    }
    /** 
     * Sets the game ID.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /** 
     * Gets the current wave number.
     * @return int
     */
    public int getWave() {
        return wave;
    }

    /** 
     * Sets the strategy for hero movement.
     * @param strategy
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
        heroSquad.setStrategy(strategy);
    }

    /** 
     * Get the seed used for random generation.
     * @return int
     */
    public int getSeed() {
        return seed;
    }
    /** 
     * Sets the seed used for random generation.
     * @param seed
     */
    public void setSeed(int seed) {
        this.seed = seed;
    }

    /** 
     * Gets the current hero squad.
     * @return HeroSquad
     */
    public HeroSquad getHeroSquad() {
        return heroSquad;
    }
    /** 
     * Sets the current hero squad.
     * @param heroSquad
     */
    public void setHeroSquad(HeroSquad heroSquad) {
        this.heroSquad = heroSquad;
    }

    /** 
     * Gets the current amount of money.
     * @return int
     */
    public int getMoney() {
        return money;
    }
    /** 
     * Sets the current amount of money.
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /** 
     * Gets the current turn number.
     * @return int
     */
    public int getTurn() {
        return turn;
    }

    /** 
     * Gets the score manager.
     * @return ScoreManager
     */
    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    /** 
     * Gets the current score.
     * @return int
     */
    public int getScore() {
        return scoreManager.getScore();
    }
    /** 
     * Sets the current score.
     * @param score
     */
    public void setScore(int score) {
        scoreManager.setScore(score);
    }

/* --- Hero Turn Listeners --- */

    /** 
     * Adds a hero turn listener.
     * @param turnListener
     */
    public void addHeroTurnListener(HeroTurnListener turnListener) {
        this.heroTurnListeners.add(turnListener);
    }
    /** 
     * Removes a hero turn listener.
     * @param turnListener
     */
    public void removeHeroTurnListener(HeroTurnListener turnListener) {
        this.heroTurnListeners.remove(turnListener);
    }

    /** 
     * Gets the list of hero turn listeners. (for testing purposes)
     * @return List<HeroTurnListener>
     */
    public List<HeroTurnListener> getHeroTurnListeners() {
        return new ArrayList<>(heroTurnListeners);
    }

/* --- Fire Turn Listeners --- */

    /** 
     * Adds a fire turn listener.
     * @param turnListener
     */
    public void addFireTurnListener(FireTurnListener turnListener) {
        this.fireTurnListeners.add(turnListener);
    }
    /** 
     * Removes a fire turn listener.
     * @param turnListener
     */
    public void removeFireTurnListener(FireTurnListener turnListener) {
        this.fireTurnListeners.remove(turnListener);
    }

/* --- Game Methods --- */

    /** 
     * Starts the simulation by generating a new hero squad and resetting the turn counter.
     */
    public void startSimulation() {
        blueprint = grid.clone();
        this.scoreManager = new ScoreManager();
        this.heroSquad = generateNewSquad();
        this.turn = 0;
    }

    /** 
     * Generates a new hero squad based on the current wave and seed.
     * @return HeroSquad
     */
    private HeroSquad generateNewSquad() {
        Random randomGenerator = new Random(this.seed * this.wave);
        HeroSquad.Builder builder = new HeroSquad.Builder();
        Tile startingPoint = getStartingPoint();
        for (int i = 0; i < wave + 1; i++) {
            Hero randomHero;
            switch (randomGenerator.nextInt(3)) {
                case 0:
                    randomHero = new Tank();
                    break;
                case 1:
                    randomHero = new Dragon();
                    break;
                case 2:
                    randomHero = new Healer();
                    break;
                default:
                    randomHero = new Muggle();
                    break;
            }
            builder.addHero(randomHero);
        }
        heroSquad = builder.build();
        for (Hero hero : heroSquad.getHeroes()) {
            hero.setCoords(startingPoint.getCoords());
            hero.addObserver(this.scoreManager);
        }
        heroSquad.setStrategy(strategy);
        return heroSquad;
    }

    /** 
     * Advances the game to the next wave, resetting the turn counter and regenerating the hero squad.
     */
    public void nextWave() {
        wave += 1;
        this.turn = 0;
        this.grid = this.blueprint.clone();
        this.heroSquad = generateNewSquad();
    }

    /** 
     * Ends the simulation by resetting the grid to its blueprint state.
     */
    public void endSimulation() {
        this.grid = blueprint.clone();
    }

    /** 
     * Attempts to place a tile on the grid, deducting money based on placement cost.
     * @param tile
     * @return boolean
     */
    public boolean placementOnGrid(Tile tile) {
        int tileCost = tile.getPlacementCost();
        Tile currentTile = this.grid.getTile(tile.getCoords());
        int currentTileCost = currentTile.getPlacementCost();
        int totalCost = tileCost - currentTileCost; // Cost difference

        if (this.money >= totalCost) {
            this.subMoney(totalCost);
            return this.grid.setTile(tile);
        }
        return false;
    }

    /** 
     * Moves a hero to new coordinates.
     * @param hero
     * @param newCoords
     */
    public void doMovement(Hero hero, Coords newCoords) {
        hero.setCoords(newCoords);
    }

    /** 
     * Advances the game to the next turn, processing hero movements and effects.
     */
    public void nextTurn() {
        this.turn += 1;
        for (Hero hero : heroSquad.getHeroes()) {

            // Notify hero turn listeners for WallTrap
            ArrayList<HeroTurnListener> heroTurnListenerCopy = new ArrayList(heroTurnListeners);
            for (HeroTurnListener listener : heroTurnListenerCopy) {
                listener.onNewTurn(this);
            }

            // Movement + Effects
            Coords newCoords = hero.move(this);
            doMovement(hero, newCoords);

            // Trap Checking
            Tile currentTile = this.grid.getTile(hero.getCoords());
            if (currentTile instanceof Trap) {
                ((Trap) currentTile).activateTrap(this);
            }
        }

        // Notify fire turn listeners for WoodWall
        ArrayList<FireTurnListener> fireTurnListenerCopy = new ArrayList(fireTurnListeners);
        for (FireTurnListener listener : fireTurnListenerCopy) {
            listener.onNewTurn(this);
        }

        // Tick poison effect
        for (Hero hero : this.getHeroSquad().getHeroes()) {
            if (hero.getIsPoisoned()) {
                hero.applyDamage(POISON_DAMAGE_PER_TURN);
            }
        }
    }

    /** 
     * Subtracts money from the current amount.
     * @param amount
     */
    public void subMoney(int amount) {
        this.money -= amount;
    }

    /** 
     * Checks if the game is terminated, either by a hero reaching the treasure or all heroes being dead.
     * @return boolean
     */
    public boolean isGameTerminated() {
        Tile treasure = getTreasure();

        boolean isOneAlive = false;
        for (Hero hero : heroSquad.getHeroes()) {

            // Stop when one hero reaches the treasure
            if (hero.getCoords().equals(treasure.getCoords())) {
                return true;
            }

            // Check if at least one hero is alive
            isOneAlive = isOneAlive || hero.getHealth() != 0;
        }

        return !isOneAlive;
    }

    /** 
     * Checks if the current wave is terminated (all heroes dead).
     * @return boolean
     */
    public boolean isWaveTerminated() {
        // We need to verify if everyone is dead
        for (Hero hero : heroSquad.getHeroes()) {
            if (hero.getHealth() > 0) {
                return false;
            }
        }

        return true;
    }

    /** 
     * Checks if the simulation is ready to start (treasure and starting point exist and are connected).
     * @return boolean
     */
    public boolean isSimulationReady() {
        Tile treasure = getTreasure();
        Tile startingPoint = getStartingPoint();
        if (treasure != null && startingPoint != null) {
            BFS bfs = new BFS(getGrid());
            return bfs.search(startingPoint.getCoords(), null) != startingPoint.getCoords();
        }
        return false;
    }
}