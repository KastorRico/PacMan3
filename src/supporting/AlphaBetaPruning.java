package supporting;

<<<<<<< Updated upstream
public class AlphaBetaPruning {
    public AlphaBetaPruning(){

    }
=======
import java.util.ArrayDeque;
import java.util.ArrayList;

public class AlphaBetaPruning implements SearchPath{
    int value;
    ArrayDeque<Node> result;

    public AlphaBetaPruning(MainProcessTree tree) {
        result = new ArrayDeque<>();
       // result.add(tree.getRoot());
        alphabeta(tree.getRoot(), 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    }

    private int alphabeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || !node.haveChildren()) return node.value.getWeight();
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
    public ArrayDeque<Point> getPacmanWay() {
        ArrayDeque<Point> copyResult = new ArrayDeque<>();
        while (!result.isEmpty()) {
            copyResult.add(result.pop().value.getPacmanLocation());
        }
        result.clear();
        return copyResult;
    }


    @Override
    public Point getNextVisualPoint() {
        if(result.isEmpty()) return null;
        return result.pop().value.getPacmanLocation();
    }

    @Override
    public int getCountStepsToFind() {
        return 0;
    }

    @Override
    public int getCountStepsFromStartToFinish() {
        return 0;
    }
>>>>>>> Stashed changes
}
