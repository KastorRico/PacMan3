package supporting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NodeWeigh implements Comparable<NodeWeigh>{
    static int freeId = 0;
    private int id;
    private List<NodeWeigh> children;
    private int weigh;
    private NodeWeigh father;
    private Point value;

    public NodeWeigh(NodeWeigh father, Point value, int weigh) {
        this.children = new ArrayList<>();
        this.father = father;
        this.value = value;
        this.weigh = weigh;
        id = freeId++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeWeigh)) return false;
        NodeWeigh node = (NodeWeigh) o;
        return id == node.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public NodeWeigh(NodeWeigh node) {
        this.children = node.getChildren();
        this.father = node.getFather();
        this.value = node.getValue();
        this.weigh = node.getWeigh();
        this.id = node.getId();
    }

    public int getWeigh() {
        return weigh;
    }

    public List<NodeWeigh> getChildren() {
        return children;
    }

    public void addChildren(NodeWeigh newChildren) {
        children.add(newChildren);
    }

    public int getId(){
        return id;
    }

    public NodeWeigh getFather() {
        return father;
    }

    public Point getValue() {
        return value;
    }

    public void setValue(Point value) {
        this.value = value;
    }

    public int compareToId(NodeWeigh that) {return Integer.compare(this.getId(), that.getId());}

    @Override
    public int compareTo(NodeWeigh that) {
        return Integer.compare(this.getWeigh(), that.weigh);
    }
}
