package supporting;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final int V;
    private Map<Point, Bag<Point>> adj;

    public Graph(int V) {
        this.V = V;
        adj = new HashMap<>(V);
    }

    public Graph(Graph graph){
        V = graph.V;
        adj = new HashMap<>(V);
        adj.putAll(graph.adj);
    }

    public void addEdge(Point v, Point w) {
        if (adj.get(v) == null)
            adj.put(v, new Bag<>());
        adj.get(v).add(w);
        if (adj.get(w) == null)
            adj.put(w, new Bag<>());
        adj.get(w).add(v);
    }

    public Iterable<Point> adj(Point v) {
        return adj.get(v);
    }

    public int V() {
        return V;
    }
}
