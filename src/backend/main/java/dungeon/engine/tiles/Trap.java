package dungeon.engine.tiles;

import dungeon.engine.Coords;
import dungeon.engine.Tile;

public abstract class Trap extends Tile {
    private int damage;
    private int area;

    public Trap(Coords coords,int damage,int area) {
        super(coords);
        this.damage = damage;
        this.area = area;
    }

    public int getDamage() {
        return damage;
    }

    public int getArea() {
        return area;
    }

    public abstract int getAstarValue();

}
