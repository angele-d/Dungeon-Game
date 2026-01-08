package dungeon.engine.AI;

import dungeon.engine.*;
import dungeon.engine.tiles.Empty;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.wall.WoodWall;

import java.util.*;

public class BFS {

    private Grid grid;

    public BFS(Grid grid) {
        this.grid = grid;

    }

    @Override
    public String toString() {
        return "BFS";
    }

    public Coords search(Coords start, Hero hero) {
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
                    if ((grid.getTile(neighboor) instanceof Empty || grid.getTile(neighboor) instanceof Treasure) || (hero instanceof Dwarf && hero.getActionAvailable() && grid.getTile(neighboor) instanceof WoodWall)) {
                        visited.add(neighboor);
                        queue.add(new Node(neighboor, curr));
                    }

                }
            }
        }
        return null;
    }
}
