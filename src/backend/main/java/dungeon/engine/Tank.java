package dungeon.engine;

public class Tank extends Hero {
    
    private int health;
    private boolean actionAvailable;
    private static final int MAX_HEALTH = 200;

    public Tank() {
        super();
        health = MAX_HEALTH;
        actionAvailable = true;
        // Tank-specific initialization
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
        return actionAvailable;
    }

    @Override
    public void doAction() {
        if(isActionAvailable()){
            // Poison Cure Action
            if(getIsPoisoned()){
                setIsPoisoned(false);
                actionAvailable = false;
            }
        }
    }

    @Override
    public void resetAction() {
        actionAvailable = true;
    }

    public void accept(HeroVisitor visitor) {
        visitor.visit(this);
    }
}
