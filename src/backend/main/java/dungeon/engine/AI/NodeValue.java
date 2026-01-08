package dungeon.engine.AI;

public class NodeValue {

    private NodeValue parent;
    private Node node;
    private int value; // f = g + h

    public NodeValue(NodeValue parent, Node node, int value) {
        this.parent = parent;
        this.node = node;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public NodeValue getParent(){
        return parent;
    }

    public Node getNode() {
        return node;
    }
}
