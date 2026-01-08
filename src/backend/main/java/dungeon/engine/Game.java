package dungeon.engine;

import dungeon.engine.AI.BFS;
import dungeon.engine.Observers.ScoreManager;
import dungeon.engine.tiles.StartingPoint;
import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.Treasure;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int id; //TODO: implement unique ID generation
    private Grid grid;
    private Grid blueprint;
    private HeroSquad heroSquad;
    private int money;
    private int turn;
    private ArrayList<TurnListener> turnListeners;
    private ScoreManager scoreManager;

    /* --- Constructor --- */

    public Game(int id) {
        this.grid = new Grid();
        this.blueprint = grid.clone();
        this.id = id;
        this.heroSquad = new HeroSquad();
        this.money = 500;
        this.turn = 0;
        this.turnListeners = new ArrayList<TurnListener>();
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
        this.turnListeners = new ArrayList<TurnListener>();
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

    public void addTurnListener(TurnListener turnListener) {
        this.turnListeners.add(turnListener);
    }
    public void removeTurnListener(TurnListener turnListener) {
        this.turnListeners.remove(turnListener);
    }

    public List<TurnListener> getTurnListeners() {
        return new ArrayList<>(turnListeners);
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

    public void setId(int id) {
        this.id = id;
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

    /* --- Game Methods --- */

    public void startSimulation() {
        blueprint = grid.clone();
        HeroSquad.Builder builder = new HeroSquad.Builder();
        builder.addHero(new Dwarf())
                .addHero(new Healer())
                .addHero(new Tank())
                .addHero(new TheMemeMaker());
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
            for(TurnListener listener : turnListeners) {
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
    }

    public void subMoney(int amount) {
        this.money -= amount;
    }

    public boolean isTerminated() {
        Tile treasure = getTreasure();

        boolean isOneAlive = false;
        for (Hero hero: heroSquad.getHeroes()) {
            if (hero.getCoords().equals(treasure.getCoords())) {
                return true;
            }
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