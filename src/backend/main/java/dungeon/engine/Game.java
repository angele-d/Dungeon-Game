package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.Observers.ScoreManager;
import dungeon.engine.tiles.StartingPoint;
import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.Treasure;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static final int POISON_DAMAGE_PER_TURN = 5;
    private int id; //TODO: implement unique ID generation
    private Grid grid;
    private Grid blueprint;
    private HeroSquad heroSquad;
    private int money;
    private int turn;
    private ArrayList<HeroTurnListener> heroTurnListeners;
    private ArrayList<FireTurnListener> fireTurnListeners;
    private ScoreManager scoreManager;

    /* --- Constructor --- */

    public Game(int id) {
        this.grid = new Grid();
        this.blueprint = grid.clone();
        this.id = id;
        this.heroSquad = new HeroSquad();
        this.money = 500;
        this.turn = 0;
        this.heroTurnListeners = new ArrayList<HeroTurnListener>();
        this.fireTurnListeners = new ArrayList<FireTurnListener>();
        this.scoreManager = new ScoreManager();
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
        for (Coords coords: grid.getGrid().keySet()) {
            if (grid.getTile(coords) instanceof Treasure) {
                return grid.getTile(coords);
            }
        }
        return null;
    }

    private Tile getStartingPoint() {
        for (Coords coords: grid.getGrid().keySet()) {
            if (grid.getTile(coords) instanceof StartingPoint) {
                return grid.getTile(coords);
            }
        }
        return null;
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
        HeroSquad.Builder builder = new HeroSquad.Builder();
        builder.addHero(new Dragon())
                .addHero(new Healer())
                .addHero(new Tank())
                .addHero(new Muggle());
        this.heroSquad = builder.build();
        Tile startingPoint = getStartingPoint();
        for (Hero hero : heroSquad.getHeroes()) {
            hero.setCoords(startingPoint.getCoords());
        }
        this.scoreManager = new ScoreManager();
        this.money = 500;
        this.turn = 0;
    }

    public void endSimulation(){
        this.grid = blueprint.clone();
    }

    public void placementOnGrid(Tile tile) {
        int tileCost = tile.getPlacementCost();
        Tile currentTile = this.grid.getTile(tile.getCoords());
        int currentTileCost = currentTile.getPlacementCost();
        int totalCost = tileCost - currentTileCost; // Cost difference

        if(this.money >= totalCost) {
            this.subMoney(totalCost);
            this.grid.setTile(tile);
        }
    }

    public void doMovement(Hero hero, Coords newCoords) {
        hero.setCoords(newCoords);
    }

    public void nextTurn() {
        this.turn += 1;
        for (Hero hero : heroSquad.getHeroes()) {

            // Notify hero turn listeners for WallTrap
            for(HeroTurnListener listener : heroTurnListeners) {
                listener.onNewTurn(this);
            }

            // Movement + Effects 
            Coords newCoords = hero.move(this);
            doMovement(hero, newCoords);

            //Trap Checking
            Tile currentTile = this.grid.getTile(hero.getCoords());
            if (currentTile instanceof Trap) {
                ((Trap) currentTile).activateTrap(this);
            }
        }

        // Notify fire turn listeners for WoodWall
        for(FireTurnListener listener : fireTurnListeners) {
            listener.onNewTurn(this);
        }

        // Tick poison effect
        for(Hero hero : this.getHeroSquad().getHeroes()){
            if(hero.getIsPoisoned()){
                hero.applyDamage(POISON_DAMAGE_PER_TURN);
            }
        }
    }

    public void subMoney(int amount) {
        this.money -= amount;
    }

    public boolean isTerminated() {
        Tile treasure = getTreasure();

        boolean isOneAlive = false;
        for (Hero hero: heroSquad.getHeroes()) {

            // Stop when one hero reaches the treasure
            if(hero.getCoords().equals(treasure.getCoords())) {
                return true;
            }

            // Check if at least one hero is alive
            isOneAlive = isOneAlive || hero.getHealth() != 0;
        }

        return !isOneAlive;
    }

    public boolean isSimulationReady() {
        Tile treasure = getTreasure();
        Tile startingPoint = getStartingPoint();
        if (treasure != null && startingPoint != null) {
            BFS bfs = new BFS(getGrid());
            return bfs.search(startingPoint.getCoords(), null) != null;
        }
        return false;
    }
}