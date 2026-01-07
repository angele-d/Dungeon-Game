package dungeon.engine;

public abstract class Tile {
    private Coords coords;

    public Tile(Coords coords) {
        this.coords = coords;
    }

    /* --- Getters and Setters --- */

    public Coords getCoords() {
        return coords;
    }
    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    /* --- Abstract functions --- */

    public abstract int getAstarValue();

    public abstract int getPlacementCost();
}
