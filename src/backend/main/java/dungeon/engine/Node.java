package dungeon.engine;
import java.util.ArrayList;


public class Node {
    private ArrayList<Node> children;
    private Coords coords;

    public Node(Coords coords) {
        this.coords = coords;
        children = new ArrayList<>();
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public Coords getCoords() {
        return coords;
    }
}
