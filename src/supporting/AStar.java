package supporting;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    PriorityQueue<NodeWeigh> mainQueue;
    NodeWeigh nowNode;
    private List<Point> marked;
    private short[][] data;
    private Point finish;
    private NodeWeigh root;

    public AStar(short[][] data, Point start, Point finish) {
        root = new NodeWeigh(null, start, heuristic(start, finish) + heuristic(finish, start));
        this.data = data;
        this.finish = finish;
        marked = new ArrayList<>();
        marked.add(start);
        mainQueue = new PriorityQueue<>();
        mainQueue.add(root);
        nowNode = getNextNode();
        addNewPoints(nowNode);

        while (!nowNode.getValue().equals(finish)) {
            nowNode = getNextNode();
            addNewPoints(nowNode);
        }
    }

    public NodeWeigh getNextNode() {
        return mainQueue.poll();
    }

    public void addNewPoints(NodeWeigh nodeTo) {
        if (nodeTo == null || nodeTo.getValue().equals(finish))
            return;
        for (Point point : getPoints(nodeTo.getValue())) {
            if (!marked.contains(point)) {
                marked.add(point);
                NodeWeigh newNode = new NodeWeigh(nodeTo, point, heuristic(point, finish) + heuristic(point, root.getValue()));
                mainQueue.add(newNode);
                nodeTo.addChildren(newNode);
            }
        }
    }

    private List<Point> getPoints(Point value) {
        List<Point> res = new ArrayList<>();
        if (checkPosition(value.x - 1, value.y))
            res.add(new Point(value.x - 1, value.y));
        if (checkPosition(value.x + 1, value.y))
            res.add(new Point(value.x + 1, value.y));
        if (checkPosition(value.x, value.y - 1))
            res.add(new Point(value.x, value.y - 1));
        if (checkPosition(value.x, value.y + 1))
            res.add(new Point(value.x, value.y + 1));

        return res;
    }

    private boolean checkPosition(int x, int y) {
        return (x < data.length && x >= 0 && y < data.length && y >= 0 && (data[y][x] == 0 || data[y][x] == 16));
    }

    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public int getCountStepsFromStartToFinish() {
        int res = 0;
        NodeWeigh tempNode = new NodeWeigh(nowNode);
        while (!tempNode.equals(root)) {
            res++;
            tempNode = tempNode.getFather();
        }
        return res;
    }
}
