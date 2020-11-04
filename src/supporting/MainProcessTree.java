package supporting;

import java.util.ArrayList;

public class MainProcessTree {
    private static final int NUM_OF_LEVELS = 0;

    private short[][] screenData;
    private Point pacmanPosition;
    private ArrayList<Point> ghosts;
    private Node root;

    public MainProcessTree(short[][] screenData, Point pacmanPosition, ArrayList<Point> ghosts,ArrayList<Point> dots) {
        this.screenData = screenData;
        this.pacmanPosition = pacmanPosition;
        this.ghosts = new ArrayList<>();
        this.ghosts.addAll(ghosts);
        root = new Node(new LevelDate(screenData, pacmanPosition, ghosts,dots), null);
        generateVariations(screenData, pacmanPosition, ghosts,dots);
    }

    public Node getRoot() {
        return root;
    }

    private void generateVariations(short[][] screenData, Point pacman,ArrayList<Point> ghosts,ArrayList<Point> dots) {
        if (pacman.x + 1 < screenData.length) {
            Node newNode = new Node(new LevelDate(screenData, new Point(pacman.x + 1, pacman.y), ghosts, dots), root);
            root.addChildren(newNode);
            addLevelDataGhosts(newNode,dots);
        }
        if (pacman.x - 1 >= 0) {
            Node newNode = new Node(new LevelDate(screenData, new Point(pacman.x - 1, pacman.y), ghosts, dots), root);
            root.addChildren(newNode);
            addLevelDataGhosts(newNode,dots);
        }
        if (pacman.y + 1 < screenData.length) {
            Node newNode = new Node(new LevelDate(screenData, new Point(pacman.x, pacman.y + 1), ghosts, dots), root);
            root.addChildren(newNode);
            addLevelDataGhosts(newNode,dots);
        }
        if (pacman.y - 1 >= 0) {
            Node newNode = new Node(new LevelDate(screenData, new Point(pacman.x, pacman.y - 1), ghosts, dots), root);
            root.addChildren(newNode);
            addLevelDataGhosts(newNode,dots);
        }

        for (Node node : root.childrenList)
            for (Node node1 : node.childrenList)
                for (Node node2 : node1.childrenList) {
                    if (pacman.x + 1 < screenData.length) {
                        node2.addChildren(new Node(new LevelDate(screenData, new Point(pacman.x + 1, pacman.y), ghosts, dots), root));
                    }
                    if (pacman.x - 1 >= 0) {
                        node2.addChildren(new Node(new LevelDate(screenData, new Point(pacman.x - 1, pacman.y), ghosts, dots), root));
                    }
                    if (pacman.y + 1 < screenData.length) {
                        node2.addChildren(new Node(new LevelDate(screenData, new Point(pacman.x, pacman.y + 1), ghosts, dots), root));
                    }
                    if (pacman.y - 1 >= 0) {
                        node2.addChildren(new Node(new LevelDate(screenData, new Point(pacman.x, pacman.y - 1), ghosts, dots), root));
                    }
                }
    }


    private void addLevelDataGhosts(Node node,ArrayList<Point> dots) {
        if (ghosts.get(0).x + 1 < screenData.length) {
            moveFirstGhost(ghosts.get(0).x + 1, ghosts.get(0).y, node,dots);
        }
        if (ghosts.get(0).x - 1 >= 0) {
            moveFirstGhost(ghosts.get(0).x + 1, ghosts.get(0).y, node, dots);
        }
        if (ghosts.get(0).y + 1 < screenData.length) {
            moveFirstGhost(ghosts.get(0).x, ghosts.get(0).y + 1, node, dots);
        }
        if (ghosts.get(0).y - 1 >= 0) {
            moveFirstGhost(ghosts.get(0).x, ghosts.get(0).y - 1, node, dots);
        }
    }

    private void moveFirstGhost(int ghostX, int ghostY, Node node, ArrayList<Point> dots) {
        Point firstGhostPosition = new Point(ghostX, ghostY);
        moveSecondGhost(firstGhostPosition, node,dots);
    }

    private void moveSecondGhost(Point firstGhostPosition, Node node,ArrayList<Point> dots) {
        if (ghosts.get(1).x + 1 < screenData.length) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(ghosts.get(1).x + 1, ghosts.get(1).y));
            node.addChildren(new Node(new LevelDate(screenData, pacmanPosition, list, dots), node));
        }
        if (ghosts.get(1).x - 1 >= 0) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(ghosts.get(1).x - 1, ghosts.get(1).y));
            node.addChildren(new Node(new LevelDate(screenData, pacmanPosition, list, dots), node));

        }
        if (ghosts.get(1).y + 1 < screenData.length) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(ghosts.get(1).x, ghosts.get(1).y + 1));
            node.addChildren(new Node(new LevelDate(screenData, pacmanPosition, list, dots), node));

        }
        if (ghosts.get(1).y - 1 >= 0) {
            ArrayList<Point> list = new ArrayList();
            list.add(firstGhostPosition);
            list.add(new Point(ghosts.get(1).x, ghosts.get(1).y - 1));
            node.addChildren(new Node(new LevelDate(screenData, pacmanPosition, list, dots), node));
        }
    }
}
