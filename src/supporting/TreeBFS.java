package supporting;

import java.util.*;

public class TreeBFS implements SearchPath {
    ArrayDeque<Node> deque;
    Node nowNode, oldNode;
    private List<Point> marked;
    private Graph graph;
    private Point finish;
    private int steps = 0;
    private Node root;
    private Queue<Point> path;

    public TreeBFS(Graph graph, Point start, Point finish) {
        this.graph = graph;
        this.finish = finish;
        root = new Node(null, start);
        marked = new ArrayList<>();
        marked.add(start);
        deque = new ArrayDeque<>();
        deque.push(root);
        nowNode = getNextNode();
        addNewPoints(nowNode);
    }

    public Point getFinish() {
        return finish;
    }

    @Override
    public int getCountStepsToFind() {
        while (!nowNode.value.equals(finish)) {
            nowNode = getNextNode();
            addNewPoints(nowNode);
        }
        return steps;
    }

    @Override
    public int getCountStepsFromStartToFinish() {
        int res = 0;
        Node tempNode = new Node(nowNode);
        while (!tempNode.equals(root)){
            res++;
            tempNode = tempNode.getFather();
        }
        return res;
    }

    public Node getNextNode() {
        steps++;
        return deque.poll();
    }

    public Queue<Point> getPathToNextPoint(Node start, Node nodeTo) {
        if (start.value.equals(finish))
            return null;
        Queue<Point> resultStart = new LinkedList<>();
        Queue<Point> resultFinish = new LinkedList<>();
        Node tempStart = new Node(start);
        Node tempFinish = new Node(nodeTo);
        while (!tempStart.equals(tempFinish)) {
            if (tempStart.compareTo(tempFinish) < 0) {
                resultFinish.offer(tempFinish.value);
                tempFinish = tempFinish.father;
            } else if (tempStart.compareTo(tempFinish) > 0) {
                tempStart = tempStart.father;
                resultStart.add(tempStart.value);
            }
        }
        List<Point> list = (List) resultFinish;
        Collections.reverse(list);
        resultStart.addAll(list);
        return resultStart;
    }

    public void addNewPoints(Node nodeTo) {
        if (nodeTo == null || nodeTo.value.equals(finish))
            return;
        for (Point point : graph.adj(nodeTo.value)) {
            if (!marked.contains(point)) {
                marked.add(point);
                Node newNode = new Node(nodeTo, point);
                deque.addLast(newNode);
                nodeTo.addChildren(newNode);
            }
        }
    }

    @Override
    public Point getNextVisualPoint() {
        if (path == null || path.isEmpty()) {
            oldNode = nowNode;
            nowNode = getNextNode();
            path = getPathToNextPoint(oldNode, nowNode);
            addNewPoints(nowNode);
            if(oldNode.value.equals(finish))
                return null;
        }
        return path.poll();
    }
}
