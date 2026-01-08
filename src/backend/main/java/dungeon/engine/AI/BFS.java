package dungeon.engine.AI;

import dungeon.engine.*;
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

    public Coords search(Coords start, Hero hero) {
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
                    if (!(grid.getTile(neighbor) instanceof Wall)){
                        visited.add(neighbor);
                        queue.add(new Node(neighbor, curr));
                    }
                }
            }
        }
        return null;
    }
}
