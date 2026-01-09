package dungeon.engine.tiles.wall;

import dungeon.engine.Coords;
import dungeon.engine.FireTurnListener;
import dungeon.engine.Game;
import dungeon.engine.Tile;
import dungeon.engine.tiles.Empty;
import dungeon.engine.tiles.Wall;

public class WoodWall extends Wall implements FireTurnListener {

    private static final int PLACEMENT_COST = 50;
    private static final int ASTAR_VALUE = 150000;
    private int turnBeforeFlames = 2;

    /* --- Constructor --- */

    public WoodWall(Coords coords) {
        super(coords);
    }

    /* --- Getters and Setters --- */

    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

    /* --- TurnListener Implementation --- */

    @Override
    public void onNewTurn(Game game) {
        turnBeforeFlames--;
        if (turnBeforeFlames <= 0) {
            // Replace wood wall with empty tile
            Tile tile = new Empty(this.getCoords());
            game.getGrid().setTile(tile);
            game.removeFireTurnListener(this);
        }
    }

    /* --- ToString --- */

    @Override
    public String toString() {
        return "woodwall";
    }
}
