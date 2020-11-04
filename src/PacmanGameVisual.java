import supporting.*;
import supporting.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PacmanGameVisual extends JFrame {
    final short[][] levelData = {
            {0, 0, 0, 5, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 5, 0, 0, 0, 0, 0, 15, 0, 3, 10, 10, 6, 0},
            {0, 7, 0, 5, 0, 7, 0, 15, 0, 0, 0, 5, 0, 0, 5, 0},
            {0, 5, 0, 13, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 5, 0},
            {0, 5, 0, 0, 0, 5, 0, 0, 0, 15, 0, 5, 0, 11, 12, 0},
            {0, 5, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0},
            {10, 8, 10, 10, 10, 12, 0, 15, 0, 0, 0, 9, 10, 10, 10, 10},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0},
            {0, 5, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0},
            {0, 5, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0},
            {0, 5, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0},
            {0, 1, 14, 0, 0, 0, 11, 2, 2, 14, 0, 0, 0, 11, 4, 0},
            {0, 5, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 5, 0},
            {0, 9, 10, 10, 14, 0, 0, 9, 12, 0, 0, 11, 10, 10, 12, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
<<<<<<< Updated upstream
    Pacman pacman;
    Ghost ghost;
    Random random = new Random();
=======
    private Pacman pacman;
    private Ghost ghost1,ghost2;
    private Random random = new Random();
>>>>>>> Stashed changes

    public PacmanGameVisual() {
        Graph mainGraph = buildGraphOnMatrix(levelData);
        Point randomEnd = searchEmptyPoint(levelData);
        levelData[randomEnd.y][randomEnd.x] = 16;
        Point randomStart = searchEmptyPoint(levelData);
        ghost = new Ghost(new AStar(mainGraph, randomStart, randomEnd), new Point(1,1), randomEnd);
        pacman = new Pacman(new AStar(mainGraph, randomStart, randomEnd), randomStart, randomEnd);
        initUI();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var ex = new PacmanGameVisual();
            ex.setVisible(true);
        });
    }

    private Point searchEmptyPoint(short[][] levelData) {
        Point randomPoint;
        do {
            randomPoint = new Point(random.nextInt(15), random.nextInt(15));
            if (levelData[randomPoint.y][randomPoint.x] != 0)
                continue;
            break;
        } while (true);
        return randomPoint;
    }

    private Graph buildGraphOnMatrix(short[][] levelData) {
        Graph graph = new Graph(levelData.length * levelData[0].length);
        for (int y = 0; y < levelData.length; y++) {
            for (int x = 0; x < levelData[y].length; x++) {
                if (levelData[y][x] == 0) {
                    if (x + 1 < levelData[y].length && levelData[y][x + 1] == 0 )
                        graph.addEdge(new Point(x, y), new Point(x + 1, y));
                    if (y + 1 < levelData.length && levelData[y + 1][x] == 0)
                        graph.addEdge(new Point(x, y), new Point(x, y + 1));
                }
            }
        }
        return graph;
    }

    private void initUI() {
        Board board = new Board(levelData, pacman,ghost);
        add(board);
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize((board.screenData[0].length + 1) * Board.BLOCK_SIZE, (board.screenData.length + 2) * Board.BLOCK_SIZE);
        setLocationRelativeTo(null);
    }
}
