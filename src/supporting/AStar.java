package supporting;

import java.util.*;

public class AStar implements SearchPath {
    PriorityQueue<NodeWeigh> mainQueue;
    NodeWeigh nowNode, oldNode;
    private List<Point> marked;
    private Graph graph;
    private Point finish;
    private int steps = 0;
    private NodeWeigh root;
    private Queue<Point> path;

    public AStar(Graph graph, Point start, Point finish) {
        root = new NodeWeigh(null, start, heuristic(start, finish) + heuristic(finish, start));
        this.graph = graph;
        this.finish = finish;
        marked = new ArrayList<>();
        marked.add(start);
        mainQueue = new PriorityQueue<>();
        mainQueue.add(root);
        nowNode = getNextNode();
        addNewPoints(nowNode);
    }

    public NodeWeigh getNextNode() {
        steps++;
        return mainQueue.poll();
    }

    public void addNewPoints(NodeWeigh nodeTo) {
        if (nodeTo == null || nodeTo.getValue().equals(finish))
            return;
        for (Point point : graph.adj(nodeTo.getValue())) {
            if (!marked.contains(point)) {
                marked.add(point);
                NodeWeigh newNode = new NodeWeigh(nodeTo, point, heuristic(point, finish) + heuristic(point, root.getValue()));
                mainQueue.add(newNode);
                nodeTo.addChildren(newNode);
            }
        }
    }

    public Queue<Point> getPathToNextPoint(NodeWeigh start, NodeWeigh nodeTo) {
        if (start.getValue().equals(finish))
            return null;
        Queue<Point> resultStart = new LinkedList<>();
        Queue<Point> resultFinish = new LinkedList<>();
        NodeWeigh tempStart = new NodeWeigh(start);
        NodeWeigh tempFinish = new NodeWeigh(nodeTo);
        while (!tempStart.equals(tempFinish)) {
            if (tempStart.compareToId(tempFinish) < 0) {
                resultFinish.offer(tempFinish.getValue());
                tempFinish = tempFinish.getFather();
            } else if (tempStart.compareToId(tempFinish) > 0) {
                tempStart = tempStart.getFather();
                resultStart.add(tempStart.getValue());
            }
        }
        List<Point> list = (List) resultFinish;
        Collections.reverse(list);
        resultStart.addAll(list);
        return resultStart;
    }

    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    @Override
    public Point getNextVisualPoint() {
        if (path == null || path.isEmpty()) {
            oldNode = nowNode;
            nowNode = getNextNode();
            path = getPathToNextPoint(oldNode, nowNode);
            addNewPoints(nowNode);
            if (oldNode.getValue().equals(finish))
                return null;
        }
        return path.poll();
    }

    @Override
    public int getCountStepsToFind() {
        while (!nowNode.getValue().equals(finish)) {
            nowNode = getNextNode();
            addNewPoints(nowNode);
        }
        return steps;
    }

    @Override
    public int getCountStepsFromStartToFinish() {
        int res = 0;
        NodeWeigh tempNode = new NodeWeigh(nowNode);
        while (!tempNode.equals(root)){
            res++;
            tempNode = tempNode.getFather();
        }
        return res;
    }
}
