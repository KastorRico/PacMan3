package supporting;

import java.util.ArrayList;

public class MainProcessTree {
    private static final int NUM_OF_LEVELS = 1;

    private Node root;

    public MainProcessTree(short[][] screenData, Point pacmanPosition, ArrayList<Point> ghosts) {
        root = new Node(new LevelDate(screenData, pacmanPosition, ghosts), null);

        generateVariations(root, NUM_OF_LEVELS);
    }

    public Node getRoot() {
        return root;
    }

    private void generateVariations(Node nodeIn, int levels) {
        if(levels <= 0) return;
        generateOneVariant(nodeIn.value.pacmanLocation.x - 1, nodeIn.value.pacmanLocation.y, nodeIn);
        generateOneVariant(nodeIn.value.pacmanLocation.x + 1, nodeIn.value.pacmanLocation.y, nodeIn);
        generateOneVariant(nodeIn.value.pacmanLocation.x, nodeIn.value.pacmanLocation.y - 1, nodeIn);
        generateOneVariant(nodeIn.value.pacmanLocation.x, nodeIn.value.pacmanLocation.y + 1, nodeIn);

        for (Node node : nodeIn.childrenList)
            for (Node node1 : node.childrenList)
                generateVariations(node1, levels - 1);
    }

    private void generateOneVariant(int x, int y, Node node) {
        if (checkPosition(x, y, node.value.data)) {
            Node newNode = new Node(new LevelDate(node.value.data, new Point(x, y), node.value.ghostsLocation), node);
            node.addChildren(newNode);
            addLevelDataGhosts(newNode);
        }
    }

    private boolean checkPosition(int x, int y, short[][] screenData) {
        return(x < screenData.length && x >= 0 && y < screenData.length && y >= 0 && (screenData[y][x] == 0 || screenData[y][x] == 16));
    }


    private void addLevelDataGhosts(Node node) {
        if (checkPosition(node.value.ghostsLocation.get(0).x + 1, node.value.ghostsLocation.get(0).y, node.value.data)) {
            moveFirstGhost(node.value.ghostsLocation.get(0).x + 1, node.value.ghostsLocation.get(0).y, node);
        }
        if (checkPosition(node.value.ghostsLocation.get(0).x - 1, node.value.ghostsLocation.get(0).y, node.value.data)) {
            moveFirstGhost(node.value.ghostsLocation.get(0).x - 1, node.value.ghostsLocation.get(0).y, node);
        }
        if (checkPosition(node.value.ghostsLocation.get(0).x, node.value.ghostsLocation.get(0).y + 1, node.value.data)) {
            moveFirstGhost(node.value.ghostsLocation.get(0).x, node.value.ghostsLocation.get(0).y + 1, node);
        }
        if (checkPosition(node.value.ghostsLocation.get(0).x, node.value.ghostsLocation.get(0).y - 1, node.value.data)) {
            moveFirstGhost(node.value.ghostsLocation.get(0).x, node.value.ghostsLocation.get(0).y - 1, node);
        }
    }

    private void moveFirstGhost(int ghostX, int ghostY, Node node) {
        Point firstGhostPosition = new Point(ghostX, ghostY);
        moveSecondGhost(firstGhostPosition, node);
    }

    private void moveSecondGhost(Point firstGhostPosition, Node node) {
        if (checkPosition(node.value.ghostsLocation.get(1).x + 1, node.value.ghostsLocation.get(1).y, node.value.data)) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(node.value.ghostsLocation.get(1).x + 1, node.value.ghostsLocation.get(1).y));
            node.addChildren(new Node(new LevelDate(node.value.data, node.value.getPacmanLocation(), list), node));
        }
        if (checkPosition(node.value.ghostsLocation.get(1).x - 1, node.value.ghostsLocation.get(1).y, node.value.data)) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(node.value.ghostsLocation.get(1).x - 1, node.value.ghostsLocation.get(1).y));
            node.addChildren(new Node(new LevelDate(node.value.data, node.value.getPacmanLocation(), list), node));
        }
        if (checkPosition(node.value.ghostsLocation.get(1).x, node.value.ghostsLocation.get(1).y + 1, node.value.data)) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(node.value.ghostsLocation.get(1).x, node.value.ghostsLocation.get(1).y + 1));
            node.addChildren(new Node(new LevelDate(node.value.data, node.value.getPacmanLocation(), list), node));

        }
        if (checkPosition(node.value.ghostsLocation.get(1).x, node.value.ghostsLocation.get(1).y - 1, node.value.data)) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(node.value.ghostsLocation.get(1).x, node.value.ghostsLocation.get(1).y - 1));
            node.addChildren(new Node(new LevelDate(node.value.data, node.value.getPacmanLocation(), list), node));
        }
    }
}
