package dungeon.engine.Visitors;

import dungeon.engine.Hero;

public interface HeroVisitor {
    void visit(Hero hero);
}
