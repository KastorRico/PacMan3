package supporting;

import java.util.ArrayList;

public class AlphaBetaPruning {
    int value;
    ArrayList<Node> result;

    public AlphaBetaPruning(MainProcessTree tree) {
        result = new ArrayList<>();
        alphabeta(tree.getRoot(), 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    }

    private int alphabeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || node.haveChildren()) return node.value.getWeight();
        if (maximizingPlayer) {
            node.value.setWeight(Integer.MIN_VALUE);
            for (Node child : node.childrenList) {
                node.value.setWeight(Math.max(node.value.getWeight(), alphabeta(child, depth - 1, alpha, beta, false)));
                alpha = Math.max(alpha, node.value.getWeight());
                if (alpha >= beta) break;
            }
            //result.add(node);
            return node.value.getWeight();
        } else {
            node.value.setWeight(Integer.MAX_VALUE);
            for (Node child : node.childrenList) {
                node.value.setWeight(Math.min(node.value.getWeight(), alphabeta(child, depth - 1, alpha, beta, true)));
                beta = Math.min(beta, node.value.getWeight());
                if (beta <= alpha) break;
            }
            result.add(node);
            return node.value.getWeight();
        }
    }

}
