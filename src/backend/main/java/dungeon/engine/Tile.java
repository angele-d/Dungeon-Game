package dungeon.engine;

public abstract class Tile {
    private Coords coords;
    public static String type;

    public Tile(Coords coords) {
        this.coords = coords;
    }

    public Coords getCoords() {
        return coords;
    }

}
