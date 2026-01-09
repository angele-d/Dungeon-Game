package dungeon.engine.AI;

import java.util.ArrayList;

import dungeon.engine.Coords;

public class Node {
    private ArrayList<Node> children;
    private Coords coords;
    private Node parent;

    public Node(Coords coords, Node parent) {
        this.coords = coords;
        children = new ArrayList<>();
        this.parent = parent;
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

    public Node getParent() {
        return parent;
    }
}