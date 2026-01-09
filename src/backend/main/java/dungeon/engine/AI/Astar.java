package dungeon.engine.AI;

import dungeon.engine.Coords;
import dungeon.engine.Grid;
import dungeon.engine.Hero;
import dungeon.engine.HeroSquad;
import dungeon.engine.tiles.StartingPoint;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.Wall;

import java.util.*;

public class Astar {

    private Grid grid;

    /* --- Constructor --- */

    public Astar(Grid grid) {
        this.grid = grid;

    }

    /* --- Functions --- */

    /** 
     * Check if a tile is occupied by a hero
     * @param neighbor
     * @param heroSquad
     * @return boolean
     */
    public boolean isOccupied(Coords neighbor, HeroSquad heroSquad) {
        if (heroSquad == null)
            return false;

        // Heroes can be more than one in Treasure and StartingPoint tiles
        if (grid.getTile(neighbor) instanceof Treasure || grid.getTile(neighbor) instanceof StartingPoint) {
            return false;
        }

        for (Hero hero : heroSquad.getHeroes()) {
            if (hero.getCoords().equals(neighbor)) {
                // Check if the hero is alive
                if (hero.getHealth() > 0)
                    return true;
            }
        }

        return false;
    }

    /** 
     * Check if a tile is walkable
     * @param neighbor
     * @param heroSquad
     * @return boolean
     */
    public boolean isWalkable(Coords neighbor, HeroSquad heroSquad) {
        // Check if a wall is there
        if (grid.getTile(neighbor) instanceof Wall) {
            return false;
        }
        // Check if an other hero is there
        if (isOccupied(neighbor, heroSquad)) {
            return false;
        }

        return true;
    }

    /** 
     * Search for the best path to the treasure
     * @param start
     * @param heroSquad
     * @return Coords
     */
    public Coords search(Coords start, HeroSquad heroSquad) {

        PriorityQueue<NodeValue> openList = new PriorityQueue<>(
                Comparator.comparingInt(NodeValue::getValue));

        Map<Coords, Integer> bestCost = new HashMap<>();

        Node startNode = new Node(start, null);
        NodeValue startValue = new NodeValue(null, startNode, 0);

        openList.add(startValue);
        bestCost.put(start, 0);

        while (!openList.isEmpty()) {

            NodeValue currentValue = openList.poll();
            Node currentNode = currentValue.getNode();
            Coords currentCoords = currentNode.getCoords();

            if (grid.getTile(currentCoords) instanceof Treasure) {
                return treasureFound(currentValue);
            }

            for (Coords neighbor : grid.getNeighborsCoords(currentCoords)) {

                if (!isWalkable(neighbor, heroSquad)) {
                    continue;
                }

                int newCost = currentValue.getValue()
                        + grid.getTile(neighbor).getAstarValue();

                if (bestCost.containsKey(neighbor)
                        && newCost >= bestCost.get(neighbor)) {
                    continue;
                }

                bestCost.put(neighbor, newCost);

                Node neighborNode = new Node(neighbor, currentNode);
                NodeValue neighborValue = new NodeValue(currentValue, neighborNode, newCost);

                openList.add(neighborValue);
            }
        }

        return start;
    }

    /** 
     * Find the coordinates of the first step towards the treasure
     * @param curr
     * @return Coords
     */
    public Coords treasureFound(NodeValue curr) {
        while (curr.getParent() != null && curr.getParent().getParent() != null) {
            curr = curr.getParent();
        }
        return curr.getNode().getCoords();
    }

    /* --- toString --- */

    /** 
     * toString method
     * @return String
     */
    @Override
    public String toString() {
        return "Astar";
    }

}
