package supporting;
import java.util.ArrayList;

public class Minimax {
    private final int GLOBALDEPTH = 4;
    private ArrayList<Node> result = new ArrayList<>();

    public Minimax() {
    }

    public ArrayList<Node> findStrategy(MainProcessTree tree) {
        helpToFind(GLOBALDEPTH, tree.tree, true);
        ArrayList<Node> copyResult = new ArrayList<>();
        for(int i = result.size();i>=0;i--)
            copyResult.add(result.get(i));
        result.clear();
        return copyResult;
    }

    private int helpToFind(int depth, Node node, boolean maxPlay) {
        Node root = node;
        if(depth == 0) return node.value;
        if (maxPlay) {
            node.value = Integer.MIN_VALUE;
            for(Node n:node.list){
                node.value = max(node.value,helpToFind(depth - 1, n, true));
            }
            result.add(node);

        } else {
            node.value = Integer.MAX_VALUE;
            for(Node n:node.list){
                node.value = min(node.value,helpToFind(depth - 1, n, true));
            }
            result.add(node);
        }
        return 0;
    }

    private int min(int value1, int value2) {
        if (value1<value2) return value1;
        else return value2;
    }

    private int max(int value1, int value2) {
        if (value1>value2) return value1;
        else return value2;
    }
}
