package dungeon.engine.AI;

import java.util.ArrayList;

import dungeon.engine.Coords;

public class Node {
    private ArrayList<Node> children;
    private Coords coords;
    private Node parent;

/* --- Constructor --- */

    public Node(Coords coords, Node parent) {
        this.coords = coords;
        children = new ArrayList<>();
        this.parent = parent;
    }

/* --- Getters --- */

    /** 
     * Get the list of child nodes
     * @return ArrayList<Node>
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /** 
     * Get the coordinates of the node
     * @return Coords
     */
    public Coords getCoords() {
        return coords;
    }

    /** 
     * Get the parent node
     * @return Node
     */
    public Node getParent() {
        return parent;
    }
}