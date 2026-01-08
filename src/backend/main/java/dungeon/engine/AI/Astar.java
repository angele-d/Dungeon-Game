package dungeon.engine.AI;

import dungeon.engine.Coords;
import dungeon.engine.Grid;
import dungeon.engine.tiles.Treasure;

import java.util.*;

public class Astar {

    private Grid grid;

    /* --- Constructor --- */

    public Astar(Grid grid) {
        this.grid = grid;

    }

    @Override
    public String toString() {
        return "Astar";
    }

    /* --- Functions --- */

    public Coords search(Coords start) {
        PriorityQueue<NodeValue> value = new PriorityQueue<>(
            Comparator.comparingInt(NodeValue::getValue)
        );
        Map<Coords, Integer> bestCost = new HashMap<>();
        bestCost.put(start, 0);
        Node startNode = new Node(start, null);
        NodeValue startValue = new NodeValue(null, startNode, 0);
        value.add(startValue);

        while (!value.isEmpty()) {
            NodeValue currValue = value.poll();
            Node currNode = currValue.getNode();

            // Treasure found
            if (grid.getTile(currNode.getCoords()) instanceof Treasure) {
                return treasureFound(currValue);
            }

            // Explore neighbors
            for (Coords neighbor: grid.getNeighborsCoords(currValue.getNode().getCoords())) {

                    Node neighborNode = new Node(neighbor, currNode);
                    int newCost = currValue.getValue() + grid.getTile(neighbor).getAstarValue();
                    
                    if (!bestCost.containsKey(neighbor) || newCost < bestCost.get(neighbor)) {
                        bestCost.put(neighbor, newCost);
                        value.add(new NodeValue(currValue, neighborNode, newCost));
                    }
            }
        }
        return null;
    }

    public Coords treasureFound(NodeValue curr){
        while (curr.getParent() != null && curr.getParent().getParent() != null) {
            curr = curr.getParent();
        }
        return curr.getNode().getCoords();
    }
        
       
}
