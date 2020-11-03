package supporting;

import java.util.ArrayList;

public class AlphaBetaPruning {
    int value;
    ArrayList<Node> result;
    public AlphaBetaPruning() {
        result = new ArrayList<Node>();
        alphabeta(origin,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,true);
    }

    private int alphabeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || node.hasChildren()) return node.value.weight;
        if (maximizingPlayer) {
            node.value = Integer.MIN_VALUE;
            for (Node child : node.list) {
                node.value = Math.max(value, alphabeta(child, depth - 1, alpha, beta, false));
                alpha = (int) Math.max(alpha, value);
                if(alpha>=beta) break;
            }
            result.add(node);
            return value;
        } else {
            value = Integer.MAX_VALUE;
            for (Node child : node.list) {
                node.value = Math.min(node.value, alphabeta(child, depth - 1, alpha, beta, true));
                beta = Math.min(beta,node.value);
                if(beta<=alpha) break;
            }
            result.add(node);
            return node.value;
        }
        return 0;
    }
    
}
