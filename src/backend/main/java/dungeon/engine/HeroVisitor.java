package dungeon.engine;

public interface HeroVisitor {
    void visit(Dwarf dwarf);
    void visit(Healer healer);
    void visit(Tank tank);
    void visit(TheMemeMaker memeMaker);
}
