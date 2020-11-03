package supporting;

import javafx.scene.Node;

import java.util.ArrayList;

public class Minimax {
    private Tree tree;
    private final int GLOBALDEPTH = 4;
    private ArrayList<Node> result = new ArrayList<>();

    public Minimax(){}

    public ArrayList<Node> findStrategy(Tree tree){
        helpToFind(GLOBALDEPTH,tree.root);
        ArrayList<Node> copyResult = new ArrayList<>();
        copyResult.addAll(result);
        result.clear();
        return copyResult;
    }

    public Node helpToFind(int depth, Node node){
        /*
        if depth = 0 or node is a terminal node then
            return the heuristic value of node
        if maximizingPlayer then
          value := −∞
          for each child of node do
            value := max(value, minimax(child, depth − 1, FALSE))
          return value
        else (* minimizing player *)
            value := +∞
            for each child of node do
                value := min(value, minimax(child, depth − 1, TRUE))
            return value
        */
        Node root = node;
        if(node.list)
        if (root.value != null){
            return root.value;
        } else
            root.value = minimumValueOfNodes();
        return null;
    }
}
