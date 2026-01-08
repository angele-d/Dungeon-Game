package dungeon.engine.AI;

import dungeon.engine.*;
import dungeon.engine.tiles.StartingPoint;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.Wall;

import java.util.*;

public class BFS {

    private Grid grid;

    /* --- Constructor --- */

    public BFS(Grid grid) {
        this.grid = grid;

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

    public boolean isWalkable(Coords neighbor, HeroSquad heroSquad){
        // Check if a wall is there
        if (grid.getTile(neighbor) instanceof Wall){
            return false;
        }
        // Check if an other hero is there
        if(isOccupied(neighbor, heroSquad)){
            return false;
        }
        
        return true;
    }

    public Coords search(Coords start, HeroSquad heroSquad) {
        Queue<Node> queue = new LinkedList<Node>();
        Set<Coords> visited = new HashSet<Coords>();
        queue.add(new Node(start, null));
        visited.add(start);
        
        while (!queue.isEmpty()) {
            Node curr = queue.poll();

            // Treasure found
            if (grid.getTile(curr.getCoords()) instanceof Treasure) {
                // Backtrack to find first move
                while (curr.getParent() != null && curr.getParent().getParent() != null) {
                    curr = curr.getParent();
                }
                return curr.getCoords();
            }

            // Explore neighbors
            for (Coords neighbor: grid.getNeighborsCoords(curr.getCoords())) {
                if (!visited.contains(neighbor)) {
                    // Check if the tile is walkable
                    if (isWalkable(neighbor, heroSquad)){
                        visited.add(neighbor);
                        queue.add(new Node(neighbor, curr));
                    }
                }
            }
        }
        return null;
    }
}
