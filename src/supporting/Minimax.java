package supporting;

import java.util.ArrayDeque;
import java.util.List;

public class Minimax implements SearchPath {
    private final int GLOBAL_DEPTH = 3;

    public Minimax() {
    }

    @Override
    public ArrayDeque<Point> findPacmanStrategy(MainProcessTree tree) {
        helpToFind(tree.getRoot(), true);

        ArrayDeque<Point> deque = new ArrayDeque();

        findPacmanPoint(deque, tree.getRoot());
        return deque;
    }

    @Override
    public ArrayDeque<List<Point>> findGhostStrategy(MainProcessTree tree) {
        helpToFind(tree.getRoot(), true);
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

    private int helpToFind(Node node, boolean maxPlay) {
        if (!node.haveChildren())
            return node.value.getWeight();
        if (maxPlay) {
            node.value.setWeight(Integer.MIN_VALUE);
            for (Node n : node.childrenList) {
                node.value.setWeight(Math.max(node.value.getWeight(), helpToFind(n, false)));
            }

        } else {
            node.value.setWeight(Integer.MAX_VALUE);
            for (Node n : node.childrenList) {
                node.value.setWeight(Math.min(node.value.getWeight(), helpToFind(n, true)));
            }
        }
        return node.value.getWeight();
    }
}