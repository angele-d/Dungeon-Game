package dungeon.engine.tiles.wall;

import dungeon.engine.Coords;
import dungeon.engine.FireTurnListener;
import dungeon.engine.Game;
import dungeon.engine.Tile;
import dungeon.engine.tiles.Empty;
import dungeon.engine.tiles.Wall;

public class WoodWall extends Wall implements FireTurnListener {

    private static final int PLACEMENT_COST = 150;
    private static final int ASTAR_VALUE = 150000;
    private int turnBeforeFlames = 2;

/* --- Constructor --- */

    public WoodWall(Coords coords) {
        super(coords);
    }

/* --- Getters and Setters --- */

    /** 
     * Get the A* value of the wood wall
     * @return int
     */
    public int getAstarValue() {
        return ASTAR_VALUE;
    }

    /** 
     * Get the placement cost of the wood wall
     * @return int
     */
    public int getPlacementCost() {
        return PLACEMENT_COST;
    }

/* --- TurnListener Implementation --- */

    /** 
     * Handle new turn event
     * @param game
     */
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

    /** 
     * toString representation of the wood wall
     * @return String
     */
    @Override
    public String toString() {
        return "woodwall";
    }
}
