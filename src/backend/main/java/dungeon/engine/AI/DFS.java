package dungeon.engine.AI;

import dungeon.engine.Coords;
import dungeon.engine.Grid;
import dungeon.engine.Node;
import dungeon.engine.tiles.Treasure;

import java.util.*;

public class DFS {

    private Grid grid;

    public DFS(Grid grid) {
        this.grid = grid;

    }

    public Coords search(Coords start) {
        Deque<Node> stack = new ArrayDeque<>();
        Set<Coords> visited = new HashSet<Coords>();
        stack.push(new Node(start, null));
        visited.add(start);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            if (grid.getTile(curr.getCoords()) instanceof Treasure) {
                while (curr.getParent() != null && curr.getParent().getParent() != null) {
                    curr = curr.getParent();
                }
                return curr.getCoords();
            }
            for (Coords neighboor: grid.getNeighborsCoords(curr.getCoords())) {

                if (!visited.contains(neighboor)) {
                    visited.add(neighboor);
                    stack.push(new Node(neighboor, curr));
                }
            }
        }
        return null;
    }
}
