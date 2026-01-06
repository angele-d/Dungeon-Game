package dungeon.engine;

public class Healer extends Hero {
    
    private int health;
    private boolean actionAvailable;
    private static final int HEAL_PERCENTAGE = 20;

    public Healer() {
        super();
        health = 150;
        actionAvailable = true;
        // Healer-specific initialization
    }
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void applyDamage(int damage) {
        health -= damage;
        if(health < 0){
            health = 0;
        }
    }

    @Override
    public boolean isActionAvailable() {
        return actionAvailable;
    }

    @Override
    public void doAction() {
        if(isActionAvailable()){
            // TODO: Heal all heroes in the zone
        }
        actionAvailable = false;
    }

    @Override
    public Coords move(Game game) {
        // Healer-specific movement logic
        // FIXME: CCheck: Copy Paste from Hero
        Coords newCoords = strategy.move(game, this);
        health = Math.min(150, health + (health * HEAL_PERCENTAGE) / 100);
        return newCoords;
    }

    @Override
    public void resetAction() {
        actionAvailable = true;
    }
}