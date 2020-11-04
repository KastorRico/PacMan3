package supporting;

import java.util.ArrayList;

public class Minimax implements SearchPath {
    private final int GLOBAL_DEPTH = 3;
    private ArrayList<Node> result = new ArrayList<>();

    public Minimax() {
    }

    @Override
    public Point findStrategy(MainProcessTree tree) {
        helpToFind(GLOBAL_DEPTH, tree.getRoot(), true);

        Node answerNode = tree.getRoot().childrenList.get(0).childrenList.get(0).childrenList.get(0);
        for (Node node : tree.getRoot().childrenList)
            for (Node node1 : node.childrenList)
                for (Node node2 : node1.childrenList)
                    if (node2.value.getWeight() == tree.getRoot().value.getWeight())
                        answerNode = node2;

        /*result.add(answerNode);
        result.add(answerNode.parent.parent);
        ArrayList<Point> copyResult = new ArrayList<>();
        for (int i = result.size() - 1; i >= 0; i--)
            copyResult.add(result.get(i).value.getPacmanLocation());
        System.out.println("Result" + result.get(1).value.pacmanLocation + ", width: " + result.get(1).value.getWeight());
        result.clear();*/
        System.out.println("Result" + answerNode.parent.parent.value.pacmanLocation);
        return answerNode.parent.parent.value.pacmanLocation;
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