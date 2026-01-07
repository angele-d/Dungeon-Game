package dungeon.engine;

import dungeon.engine.tiles.Trap;
import dungeon.engine.tiles.Treasure;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int id; //TODO: implement unique ID generation
    private Grid grid;
    private Grid blueprint;
    private HeroSquad heroSquad;
    private int score;
    private int money;
    private int turn;
    private ArrayList<TurnListener> turnListeners;

    public Game(int id) {
        this.grid = new Grid();
        this.blueprint = grid.clone();
        this.id = id;
        this.heroSquad = new HeroSquad();
        this.score = 0;
        this.money = 500;
        this.turn = 0;
        this.turnListeners = new ArrayList<TurnListener>();
    }

    private Tile getTreasure() {
        for (Coords coords: grid.getGrid().keySet()) {
            if (grid.getTile(coords) instanceof Treasure) {
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

    public Game() {
        this.grid = new Grid();
        this.blueprint = grid.clone();
        this.id = 1;
        this.heroSquad = new HeroSquad();
        this.score = 0;
        this.money = 500;
        this.turn = 0;
    }

    /* --- Getters and Setters --- */

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    /* --- Game Methods --- */
    public void startNewGame(Coords coord) { // TODO : Place heroes in the starting point
        blueprint = grid.clone();
        //this.grid = new Grid();
        this.heroSquad = new HeroSquad();
        for (Hero hero : heroSquad.getHeroes()) {
            hero.setCoords(coord);
        }
        this.score = 0;
        this.money = 500;
        this.turn = 0;
    }

    public void startNewGame() {
        blueprint = grid.clone();
//        this.grid = new Grid();
        this.heroSquad = new HeroSquad();
        for (Hero hero : heroSquad.getHeroes()) {
            hero.setCoords(new Coords(0,0));
        }
        this.score = 0;
        this.money = 500;
        this.turn = 0;
    }

    public void endGame(){
        this.grid = blueprint.clone();
    }

    public void placementOnGrid(Tile tile) {
        int tileCost = tile.getPlacementCost();
        if(this.money >= tileCost) {
            this.subMoney(tileCost);
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
            // Movement
            Coords newCoords = hero.move(this);
            doMovement(hero, newCoords);
            //Trap Checking
            Tile currentTile = this.grid.getTile(hero.getCoords());
            if (currentTile instanceof Trap) {
                //TODO: Implement trap processing
                //((Trap) currentTile).process(heroSquad));
            }
        }
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void subMoney(int amount) {
        this.money -= amount;
    }

    public boolean isTerminated() {
        boolean result = true;
        Tile treasure = getTreasure();

        for (Hero hero: heroSquad.getHeroes()) {
            if (hero.getCoords() != treasure.getCoords() && hero.getHealth() != 0) {
                result = false;
                break;
            }
        }

        return result;
    }
}