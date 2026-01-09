package dungeon.engine.AI;

public class NodeValue {

    private NodeValue parent;
    private Node node;
    private int value; // f = g + h

/* --- Constructor --- */

    public NodeValue(NodeValue parent, Node node, int value) {
        this.parent = parent;
        this.node = node;
        this.value = value;
    }

/* --- Getters --- */

    /** 
     * Get the value of the node
     * @return int
     */
    public int getValue() {
        return value;
    }

    /** 
     * Get the parent NodeValue
     * @return NodeValue
     */
    public NodeValue getParent() {
        return parent;
    }

    /** 
     * Get the node
     * @return Node
     */
    public Node getNode() {
        return node;
    }
}
