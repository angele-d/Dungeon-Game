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
    private int id; // TODO: implement unique ID generation
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
        this.money = 500;
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
        this.money = 500;
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
        this.money = 500;
        this.turn = 0;
        this.scoreManager = new ScoreManager();
        this.heroTurnListeners = new ArrayList<HeroTurnListener>();
        this.fireTurnListeners = new ArrayList<FireTurnListener>();
    }

    /* --- Getters and Setters --- */

    private Tile getTreasure() {
        return grid.getTreasure();
    }

    private Tile getStartingPoint() {
        return grid.getStartingPoint();
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getId() {
        return id;
    }

    public int getWave() {
        return wave;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
        heroSquad.setStrategy(strategy);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public HeroSquad getHeroSquad() {
        return heroSquad;
    }

    public void setHeroSquad(HeroSquad heroSquad) {
        this.heroSquad = heroSquad;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getTurn() {
        return turn;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public void setScore(int score) {
        scoreManager.setScore(score);
    }

    /* --- Hero Turn Listeners --- */

    public void addHeroTurnListener(HeroTurnListener turnListener) {
        this.heroTurnListeners.add(turnListener);
    }

    public void removeHeroTurnListener(HeroTurnListener turnListener) {
        this.heroTurnListeners.remove(turnListener);
    }

    public List<HeroTurnListener> getHeroTurnListeners() {
        return new ArrayList<>(heroTurnListeners);
    }

    /* --- Fire Turn Listeners --- */

    public void addFireTurnListener(FireTurnListener turnListener) {
        this.fireTurnListeners.add(turnListener);
    }

    public void removeFireTurnListener(FireTurnListener turnListener) {
        this.fireTurnListeners.remove(turnListener);
    }

    public List<FireTurnListener> getFireTurnListeners() {
        return new ArrayList<>(fireTurnListeners);
    }

    /* --- Game Methods --- */

    public void startSimulation() {
        blueprint = grid.clone();
        Random randomGenerator = new Random(this.seed * this.wave);
        HeroSquad.Builder builder = new HeroSquad.Builder();
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

        this.heroSquad = builder.build();
        System.out.println(strategy.toString());
        heroSquad.setStrategy(strategy);
        Tile startingPoint = getStartingPoint();
        this.scoreManager = new ScoreManager();
        for (Hero hero : heroSquad.getHeroes()) {
            hero.setCoords(startingPoint.getCoords());
            hero.addObserver(scoreManager);
        }
        this.turn = 0;
    }

    public void nextWave() {
        wave += 1;
    }

    public void endSimulation() {
        this.grid = blueprint.clone();
    }

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

    public void doMovement(Hero hero, Coords newCoords) {
        hero.setCoords(newCoords);
    }

    public void nextTurn() {
        this.turn += 1;
        for (Hero hero : heroSquad.getHeroes()) {

            // Notify hero turn listeners for WallTrap
            for (HeroTurnListener listener : heroTurnListeners) {
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
        for (FireTurnListener listener : fireTurnListeners) {
            listener.onNewTurn(this);
        }

        // Tick poison effect
        for (Hero hero : this.getHeroSquad().getHeroes()) {
            if (hero.getIsPoisoned()) {
                hero.applyDamage(POISON_DAMAGE_PER_TURN);
            }
        }
    }

    public void subMoney(int amount) {
        this.money -= amount;
    }

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

    public boolean isWaveTerminated() {
        Tile treasure = getTreasure();

        // We need to verify if everyone is dead
        for (Hero hero : heroSquad.getHeroes()) {
            if (hero.getHealth() > 0) {
                return false;
            }
        }

        return true;
    }

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