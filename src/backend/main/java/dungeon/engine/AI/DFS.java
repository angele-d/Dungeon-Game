package dungeon.engine.AI;

import dungeon.engine.*;
import dungeon.engine.tiles.Empty;
import dungeon.engine.tiles.Treasure;
import dungeon.engine.tiles.wall.WoodWall;

import java.util.*;

public class DFS {

    private Grid grid;

    public DFS(Grid grid) {
        this.grid = grid;

    }

    public ArrayList<Coords> search(Coords start, Hero hero, ArrayList<Coords> previousCoords) {
        Deque<Node> stack = new ArrayDeque<>();
        Set<Coords> visited = new HashSet<Coords>();
        stack.push(new Node(start, null));
        visited.add(start);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            if (grid.getTile(curr.getCoords()) instanceof Treasure) {
                while (curr.getParent() != null && curr.getParent().getParent() != null ) {
                    if (curr.getParent().getParent().getParent() != null){
                        curr = curr.getParent();
                    } else {
                        ArrayList<Coords> result = new ArrayList<Coords>();
                        result.add(curr.getCoords());
                        return result;
                    }

                }
                ArrayList<Coords> result = new ArrayList<Coords>();
                result.add(curr.getCoords());
                result.add(curr.getParent().getCoords());
                return result;
            }
            for (Coords neighboor: grid.getNeighborsCoords(curr.getCoords())) {

                if (!visited.contains(neighboor) && !previousCoords.contains(neighboor)) {
                    if ((grid.getTile(neighboor) instanceof Empty || grid.getTile(neighboor) instanceof Treasure) || (hero instanceof Dwarf && hero.getActionAvailable() && grid.getTile(neighboor) instanceof WoodWall)) {
                        visited.add(neighboor);
                        stack.push(new Node(neighboor, curr));
                    }
                }
            }
        }
        return null;
    }
}
