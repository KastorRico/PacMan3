package supporting;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Stack;

public interface SearchPath {
    ArrayDeque<Point> findPacmanStrategy(MainProcessTree tree);
    ArrayDeque<List<Point>> findGhostStrategy(MainProcessTree tree);
}

