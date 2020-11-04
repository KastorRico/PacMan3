package supporting;

import java.util.ArrayList;

public class MainProcessTree {
    private static final int NUM_OF_LEVELS = 0;

    private short[][] screenData;
    private Point pacmanPosition;
    private ArrayList<Point> ghosts;
    private Node root;

    public MainProcessTree(short[][] screenData, Point pacmanPosition, ArrayList<Point> ghosts) {
        this.screenData = screenData;
        this.pacmanPosition = pacmanPosition;
        this.ghosts = new ArrayList<>();
        this.ghosts.addAll(ghosts);
        root = new Node(new LevelDate(screenData, pacmanPosition, ghosts), null);
        generateVariations(screenData, pacmanPosition, ghosts);
    }

    public Node getRoot() {
        return root;
    }

    private void generateVariations(short[][] screenData, Point pacman, ArrayList<Point> ghosts) {

        generateOneVariant(pacman.x - 1, pacman.y);
        generateOneVariant(pacman.x + 1, pacman.y);
        generateOneVariant(pacman.x, pacman.y - 1);
        generateOneVariant(pacman.x, pacman.y + 1);

        for (Node node : root.childrenList)
            for (Node node1 : node.childrenList) {
                Point pacmanPosition = node1.value.getPacmanLocation();
                if (checkPosition(pacmanPosition.x + 1, pacmanPosition.y)) {
                    node1.addChildren(new Node(new LevelDate(node1.value.data, new Point(pacmanPosition.x + 1, pacmanPosition.y), node1.value.ghostsLocation), node1));
                }
                if (checkPosition(pacmanPosition.x - 1, pacmanPosition.y)) {
                    node1.addChildren(new Node(new LevelDate(node1.value.data, new Point(pacmanPosition.x - 1, pacmanPosition.y), node1.value.ghostsLocation), node1));
                }
                if (checkPosition(pacmanPosition.x, pacmanPosition.y + 1)) {
                    node1.addChildren(new Node(new LevelDate(node1.value.data, new Point(pacmanPosition.x, pacmanPosition.y + 1), node1.value.ghostsLocation), node1));
                }
                if (checkPosition(pacmanPosition.x, pacmanPosition.y - 1)) {
                    node1.addChildren(new Node(new LevelDate(node1.value.data, new Point(pacmanPosition.x, pacmanPosition.y - 1), node1.value.ghostsLocation), node1));
                }
            }
    }

    private void generateOneVariant(int x, int y) {
        if (checkPosition(x, y)) {
            Node newNode = new Node(new LevelDate(screenData, new Point(x, y), ghosts), root);
            root.addChildren(newNode);
            addLevelDataGhosts(newNode);
        }
    }

    private boolean checkPosition(int x, int y) {
        return(x < screenData.length && x >= 0 && y < screenData.length && y >= 0 && (screenData[x][y] == 0 || screenData[x][y] == 16));
    }


    private void addLevelDataGhosts(Node node) {
        if (checkPosition(ghosts.get(0).x + 1, ghosts.get(0).y)) {
            moveFirstGhost(ghosts.get(0).x + 1, ghosts.get(0).y, node);
        }
        if (checkPosition(ghosts.get(0).x + 1, ghosts.get(0).y)) {
            moveFirstGhost(ghosts.get(0).x + 1, ghosts.get(0).y, node);
        }
        if (checkPosition(ghosts.get(0).x, ghosts.get(0).y + 1)) {
            moveFirstGhost(ghosts.get(0).x, ghosts.get(0).y + 1, node);
        }
        if (checkPosition(ghosts.get(0).x, ghosts.get(0).y - 1)) {
            moveFirstGhost(ghosts.get(0).x, ghosts.get(0).y - 1, node);
        }
    }

    private void moveFirstGhost(int ghostX, int ghostY, Node node) {
        Point firstGhostPosition = new Point(ghostX, ghostY);
        moveSecondGhost(firstGhostPosition, node);
    }

    private void moveSecondGhost(Point firstGhostPosition, Node node) {
        if (checkPosition(ghosts.get(1).x + 1, ghosts.get(1).y)) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(ghosts.get(1).x + 1, ghosts.get(1).y));
            node.addChildren(new Node(new LevelDate(node.value.data, node.value.getPacmanLocation(), list), node));
        }
        if (checkPosition(ghosts.get(1).x - 1, ghosts.get(1).y)) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(ghosts.get(1).x - 1, ghosts.get(1).y));
            node.addChildren(new Node(new LevelDate(node.value.data, node.value.getPacmanLocation(), list), node));
        }
        if (checkPosition(ghosts.get(1).x, ghosts.get(1).y + 1)) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(ghosts.get(1).x, ghosts.get(1).y + 1));
            node.addChildren(new Node(new LevelDate(node.value.data, node.value.getPacmanLocation(), list), node));

        }
        if (checkPosition(ghosts.get(1).x, ghosts.get(1).y - 1)) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(ghosts.get(1).x, ghosts.get(1).y - 1));
            node.addChildren(new Node(new LevelDate(node.value.data, node.value.getPacmanLocation(), list), node));
        }
    }
}
