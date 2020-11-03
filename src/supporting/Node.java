package supporting;

import java.util.ArrayList;

public class Node {
    LevelDate value;
    public ArrayList<Node> childrenList;
    Node parent;

    public Node(LevelDate value, Node parent) {
        this.value = value;
        childrenList = new ArrayList<>();
        this.parent = parent;
    }

    public void addChildren(Node children){
        childrenList.add(children);
    }

    public boolean haveChildren(){
        return !childrenList.isEmpty();
    }
}
