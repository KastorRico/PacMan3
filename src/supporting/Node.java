package supporting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node>{
    static int freeId = 0;
    private int id;
    List<Node> children;
    Node father;
    Point value;

    public Node(Node father, Point value) {
        this.children = new ArrayList<>();
        this.father = father;
        this.value = value;
        id = freeId++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Node(Node node) {
        this.children = node.getChildren();
        this.father = node.getFather();
        this.value = node.getValue();
        this.id = node.getId();
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChildren(Node newChildren) {
        children.add(newChildren);
    }

    public int getId(){
        return id;
    }

    public Node getFather() {
        return father;
    }

    public Point getValue() {
        return value;
    }

    public void setValue(Point value) {
        this.value = value;
    }

    @Override
    public int compareTo(Node that) {
        return Integer.compare(this.id, that.id);
    }
}
