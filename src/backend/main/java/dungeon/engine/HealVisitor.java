package dungeon.engine;

public class HealVisitor implements HeroVisitor {
    private static final int ZONE_HEAL_PERCENTAGE = 10; // Percentage of health to heal for allies

    @Override
    public void visit(Hero hero) {
        // Heal allies except Healer
        if(!(hero instanceof Healer)){
            int allyHealth = hero.getHealth();
            hero.setHealth(Math.min(allyHealth + (allyHealth * ZONE_HEAL_PERCENTAGE) / 100, hero.getMaxHealth()));
        }
    }
}