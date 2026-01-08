package dungeon.engine.AI;

import dungeon.engine.Coords;
import dungeon.engine.Grid;
import dungeon.engine.Node;
import dungeon.engine.NodeValue;
import dungeon.engine.tiles.Treasure;

import java.util.*;

public class Astar {

    private Grid grid;

    public Astar(Grid grid) {
        this.grid = grid;

    }

    @Override
    public String toString() {
        return "Astar";
    }

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
            if (grid.getTile(currNode.getCoords()) instanceof Treasure) {
                return Treasure(currValue);
            }
            for (Coords neighboor: grid.getNeighborsCoords(currValue.getNode().getCoords())) {

                    Node neighborNode = new Node(neighboor, currNode);
                    int newCost = currValue.getValue()
                                + grid.getTile(neighboor).getAstarValue();

                    if (!bestCost.containsKey(neighboor) || newCost < bestCost.get(neighboor)) {
                        bestCost.put(neighboor, newCost);
                        value.add(new NodeValue(currValue, neighborNode, newCost));
                    }

            }
        }
        return null;
    }

    public Coords Treasure(NodeValue curr){
        while (curr.getParent() != null && curr.getParent().getParent() != null) {
            curr = curr.getParent();
        }
        return curr.getNode().getCoords();
    }
        
       
}
