package supporting;
import java.util.ArrayList;

public class Minimax {
    private final int GLOBALDEPTH = 4;
    private ArrayList<Node> result = new ArrayList<>();

    public Minimax() {
    }

    public ArrayList<Point> findStrategy(MainProcessTree tree) {
        helpToFind(GLOBALDEPTH, tree.getRoot(), true);
        ArrayList<Point> copyResult = new ArrayList<>();
        for (int i = result.size(); i >= 0; i--)
            copyResult.add(result.get(i).value.getPacmanLocation());
        result.clear();
        return copyResult;
    }

    private int helpToFind(int depth, Node node, boolean maxPlay) {
        if (depth == 0) return node.value.getWeight();
        if (maxPlay) {
            node.value.setWeight(Integer.MIN_VALUE);
            for (Node n : node.childrenList) {
                node.value.setWeight(Math.max(node.value.getWeight(), helpToFind(depth - 1, n, true)));
            }
            result.add(node);

        } else {
            node.value.setWeight(Integer.MAX_VALUE);
            for (Node n : node.childrenList) {
                node.value.setWeight(Math.min(node.value.getWeight(), helpToFind(depth - 1, n, true)));
            }
        }
        return 0;
    }
}