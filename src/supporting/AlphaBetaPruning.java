package supporting;

import java.util.ArrayDeque;
import java.util.List;

public class AlphaBetaPruning implements SearchPath {

    @Override
    public ArrayDeque<Point> findPacmanStrategy(MainProcessTree tree) {
        helpToFind(tree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        ArrayDeque<Point> deque = new ArrayDeque();

        findPacmanPoint(deque, tree.getRoot());
        return deque;
    }

    @Override
    public ArrayDeque<List<Point>> findGhostStrategy(MainProcessTree tree) {
        helpToFind(tree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        ArrayDeque<List<Point>> deque = new ArrayDeque();
        findGhostPoint(deque, tree.getRoot());
        return deque;
    }

    private void findGhostPoint(ArrayDeque<List<Point>> deque, Node nodeSearch) {
        for (Node node : nodeSearch.childrenList)
            for (Node node1 : node.childrenList) {
                if (node1.value.getWeight() == nodeSearch.value.getWeight()) {
                    deque.push(node1.value.ghostsLocation);
                    if (!node1.childrenList.isEmpty()) {
                        findGhostPoint(deque, node1);
                        return;
                    } else return;
                }
            }
    }

    private void findPacmanPoint(ArrayDeque<Point> deque, Node nodeSearch) {
        for (Node node : nodeSearch.childrenList)
            for (Node node1 : node.childrenList) {
                if (node1.value.getWeight() == nodeSearch.value.getWeight()) {
                    deque.push(node1.parent.value.pacmanLocation);
                    if (!node1.childrenList.isEmpty()) {
                        findPacmanPoint(deque, node1);
                        return;
                    } else return;
                }
            }
    }

    private int helpToFind(Node node, int alpha, int beta, boolean maxPlay) {
        if (!node.haveChildren()) return node.value.getWeight();
        if (maxPlay) {
            node.value.setWeight(Integer.MIN_VALUE);
            for (Node child : node.childrenList) {
                node.value.setWeight(Math.max(node.value.getWeight(), helpToFind(child, alpha, beta, false)));
                alpha = Math.max(alpha, node.value.getWeight());
                if (alpha >= beta) break;
            }
        } else {
            node.value.setWeight(Integer.MAX_VALUE);
            for (Node child : node.childrenList) {
                node.value.setWeight(Math.min(node.value.getWeight(), helpToFind(child, alpha, beta, true)));
                beta = Math.min(beta, node.value.getWeight());
                if (beta <= alpha) break;
            }
        }
        return node.value.getWeight();
    }
}
       /*
        if (!node.haveChildren())
            return node.value.getWeight();
        if (maxPlay) {
            node.value.setWeight(Integer.MIN_VALUE);
            for (Node n : node.childrenList) {
                node.value.setWeight(Math.max(node.value.getWeight(), helpToFind(depth - 1, n, false)));
            }

        } else {
            node.value.setWeight(Integer.MAX_VALUE);
            for (Node n : node.childrenList) {
                node.value.setWeight(Math.min(node.value.getWeight(), helpToFind(depth - 1, n, true)));
            }
        }
        return node.value.getWeight();
    }*/


/*{
    int value;
    ArrayList<Node> result;

    public AlphaBetaPruning(MainProcessTree tree) {
        result = new ArrayList<>();
        alphabeta(tree.getRoot(), 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
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

}
*/