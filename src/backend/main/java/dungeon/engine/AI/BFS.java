package dungeon.engine.AI;

import dungeon.engine.Coords;
import dungeon.engine.Grid;
import dungeon.engine.Node;
import dungeon.engine.tiles.Treasure;

import java.util.*;

public class BFS {

    private Grid grid;

    public BFS(Grid grid) {
        this.grid = grid;

    }

    public Coords search(Coords start) {
        Queue<Node> queue = new LinkedList<Node>();
        Set<Coords> visited = new HashSet<Coords>();
        queue.add(new Node(start, null));
        visited.add(start);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (grid.getTile(curr.getCoords()) instanceof Treasure) {
                while (curr.getParent() != null && curr.getParent().getParent() != null) {
                    curr = curr.getParent();
                }
                return curr.getCoords();
            }
            for (Coords neighboor: grid.getNeighborsCoords(curr.getCoords())) {

                if (!visited.contains(neighboor)) {
                    visited.add(neighboor);
                    queue.add(new Node(neighboor, curr));
                }
            }
        }
        return null;
    }
}
