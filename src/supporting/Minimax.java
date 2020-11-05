package supporting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Minimax implements SearchPath {
    private final int GLOBAL_DEPTH = 3;
    private ArrayList<Node> result = new ArrayList<>();

    public Minimax() {
    }

    @Override
    public ArrayDeque<Point> findPacmanStrategy(MainProcessTree tree) {
        helpToFind(GLOBAL_DEPTH, tree.getRoot(), true);

        ArrayDeque<Point> deque = new ArrayDeque();

        findPacmanPoint(deque, tree.getRoot());
        return deque;
    }

    @Override
    public ArrayDeque<List<Point>> findGhostStrategy(MainProcessTree tree) {
        helpToFind(GLOBAL_DEPTH, tree.getRoot(), true);
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

    private void findPoints(Stack<Point> res, Node answerNode) {
        res.push(answerNode.value.pacmanLocation);
        if (answerNode.parent != null && answerNode.parent.parent != null)
            findPoints(res, answerNode.parent.parent);
    }

    private int helpToFind(int depth, Node node, boolean maxPlay) {
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
    }
}