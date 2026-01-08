package dungeon.engine.AI;

import dungeon.engine.Coords;
import dungeon.engine.Grid;
import dungeon.engine.Hero;
import dungeon.engine.HeroSquad;
import dungeon.engine.tiles.StartingPoint;
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

    public boolean isOccupied(Coords neighbor, HeroSquad heroSquad){
        if(heroSquad == null) return false;

        // Heroes can be more than one in Treasure and StartingPoint tiles
        if(grid.getTile(neighbor) instanceof Treasure || grid.getTile(neighbor) instanceof StartingPoint){
            return false;
        }

        for(Hero hero : heroSquad.getHeroes()){
            if(hero.getCoords().equals(neighbor)){
                return true;
            }
        }

        return false;
    }

    public Coords search(Coords start, HeroSquad heroSquad) {
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

                    int newCost;
                    if(isOccupied(neighbor, heroSquad)){
                        newCost = 200000;
                    }
                    else{
                        newCost = currValue.getValue() + grid.getTile(neighbor).getAstarValue();
                    }
                    Node neighborNode = new Node(neighbor, currNode);
                    
                    if (!bestCost.containsKey(neighbor) || newCost < bestCost.get(neighbor)) {
                        bestCost.put(neighbor, newCost);
                        value.add(new NodeValue(currValue, neighborNode, newCost));
                    }
            }
        }
        return start;
    }

    public Coords treasureFound(NodeValue curr){
        while (curr.getParent() != null && curr.getParent().getParent() != null) {
            curr = curr.getParent();
        }
        return curr.getNode().getCoords();
    }
        
       
}
