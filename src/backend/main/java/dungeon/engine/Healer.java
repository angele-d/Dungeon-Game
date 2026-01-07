package dungeon.engine;

public class Healer extends Hero {
    
    private int health;
    private static final int PERSONAL_HEAL_PERCENTAGE = 20;
    private static final int MAX_HEALTH = 150;

    public Healer() {
        super();
        health = MAX_HEALTH;
        // Healer-specific initialization
    }

    /* --- Getters and Setters --- */
    
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    /* --- Functions --- */

    @Override
    public void applyDamage(int damage) {
        health -= damage;
        if(health < 0){
            health = 0;
        }
    }

    @Override
    public boolean isActionAvailable() {
        return false;
    }

    @Override
    public void doAction() {
        // Do nothing
    }

    @Override
    public void resetAction() {
        // No action to reset
    }

    public void accept(HeroVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Coords move(Game game) {
        // Healer-specific movement logic

        // Classic movement
        Coords newCoords = strategy.move(game, this);

        // Personal healing ability
        health = Math.min(MAX_HEALTH, health + (health * PERSONAL_HEAL_PERCENTAGE) / 100);
        
        // Zone healing ability
        HealVisitor healVisitor = new HealVisitor();
        for(Hero ally : game.getHeroSquad().getHeroes()){
            ally.accept(healVisitor);
        }

        return newCoords;
    }
}