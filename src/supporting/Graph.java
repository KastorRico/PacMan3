package supporting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    private final int V;
    private Map<Point, Bag<Point>> adj;

    /**
     * Створюємо порожній граф розмірності V
     * ініціалізуємо масив порожніми списками типу supporting.Bag
     *
     * @param V - кількість вершин
     */
    public Graph(int V) {
        this.V = V;
        adj = new HashMap<>(V);
    }

    public Graph(Graph graph){
        V = graph.V;
        adj = new HashMap<>(V);
        adj.putAll(graph.adj);
    }

    /**
     * додаємо ребро між двома вершинами
     *
     * @param v - вершина
     * @param w - вершина
     */
    public void addEdge(Point v, Point w) {
        if (adj.get(v) == null)
            adj.put(v, new Bag<>());
        adj.get(v).add(w);
        if (adj.get(w) == null)
            adj.put(w, new Bag<>());
        adj.get(w).add(v);
    }

    /**
     * ітератор по вершинам суміжним з вершиною v
     *
     * @param v - вершина
     * @return - ітератор по мішку суміжних з v вершин
     */
    public Iterable<Point> adj(Point v) {
        return adj.get(v);
    }

    public int V() {
        return V;
    }
}
