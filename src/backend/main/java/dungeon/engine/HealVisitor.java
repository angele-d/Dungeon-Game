package dungeon.engine;

public class HealVisitor implements HeroVisitor {
    private static final int ZONE_HEAL_PERCENTAGE = 10; // Percentage of health to heal for allies

    @Override
    public void visit(Dwarf dwarf) {
        int allyHealth = dwarf.getHealth();
        dwarf.setHealth(Math.min(allyHealth + (allyHealth * ZONE_HEAL_PERCENTAGE) / 100, dwarf.getMaxHealth()));
    }

    @Override
    public void visit(Healer healer) {
        // Healers do not heal other healers
    }

    @Override
    public void visit(Tank tank) {
        int allyHealth = tank.getHealth();
        tank.setHealth(Math.min(allyHealth + (allyHealth * ZONE_HEAL_PERCENTAGE) / 100, tank.getMaxHealth()));
    }

    @Override
    public void visit(TheMemeMaker memeMaker) {
        int allyHealth = memeMaker.getHealth();
        memeMaker.setHealth(Math.min(allyHealth + (allyHealth * ZONE_HEAL_PERCENTAGE) / 100, memeMaker.getMaxHealth()));
    }
}