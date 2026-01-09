package dungeon.engine;

public abstract class Tile {
    private Coords coords;

/* --- Constructor --- */

    public Tile(Coords coords) {
        this.coords = coords;
    }

/* --- Getters and Setters --- */

    /**
     * Gets the coordinates of the tile.
     * @return
     */
    public Coords getCoords() {
        return coords;
    }
    /**
     * Sets the coordinates of the tile.
     * @param coords
     */
    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    /* --- Abstract functions --- */

    public abstract int getAstarValue();

    public abstract int getPlacementCost();
}
