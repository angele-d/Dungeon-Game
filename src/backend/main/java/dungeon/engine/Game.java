package dungeon.engine;

public class Game {

    private int id; 
    private Grid grid;
    private HeroSquad heroSquad;
    private int score;
    private int money;
    private int turn;

    public Game() {
        this.grid = new Grid();
        this.id = 0;
        this.heroSquad = new HeroSquad();
        this.score = 0;
        this.money = 500;
        this.turn = 0;
    }

    /* --- Getters and Setters --- */

    public Grid getGrid() {
        return grid;
    }

    public int getId() {
        return id;
    }

    public HeroSquad getHeroSquad() {
        return heroSquad;
    }

    public int getScore() {
        return score;
    }

    public int getMoney() {
        return money;
    }

    public int getTurn() {
        return turn;
    }

    /* --- Game Methods --- */
    public void startNewGame() {
        this.grid = new Grid();
        this.heroSquad = new HeroSquad();
        this.score = 0;
        this.money = 500;
        this.turn = 0;
    }

    public void placementOnGrid(Tile tile) {
        this.grid.setTile(tile);
    }

    public void doMovement(Hero hero, Coords newCoords) {
        hero.setCoords(newCoords);
    }

    public void nextTurn() {
        this.turn += 1;
        for (Hero hero : heroSquad.getHeroes()) {
            // Movement
            Coords newCoords = hero.move(this);
            doMovement(hero, newCoords);
            // Trap checking
            // TODO: Implement trap checking
        }
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void subMoney(int amount) {
        this.money -= amount;
    }
}