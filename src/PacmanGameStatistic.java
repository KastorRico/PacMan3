import supporting.*;

import java.time.Instant;
import java.util.Random;

public class PacmanGameStatistic {
    private static final int COUNT_ITERATION = 1000;
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
    Pacman pacman;
    Random random = new Random();
    long searchStepsDFS = 0, searchStepsBFS = 0, searchStepsAStar = 0, searchStepsGreedy = 0;
    long stepsDFS = 0, stepsBFS = 0, stepsAStar = 0, stepsGreedy = 0;


    public static void main(String[] args) {
        PacmanGameStatistic pacmanGameStatistic = new PacmanGameStatistic();
        pacmanGameStatistic.start();
    }

    private void start() {
        Graph mainGraph = buildGraphOnMatrix(levelData);
        long timeDFS, timeBFS, timeAStar, timeGreedy;

        long timeStart = Instant.now().toEpochMilli();
        for (int i = 0; i < COUNT_ITERATION; i++) {
            iterationDFS(mainGraph);
        }
        timeDFS = Instant.now().toEpochMilli() - timeStart;
        searchStepsDFS /= COUNT_ITERATION;
        stepsDFS /= COUNT_ITERATION;

        timeStart = Instant.now().toEpochMilli();
        for (int i = 0; i < COUNT_ITERATION; i++) {
            iterationBFS(mainGraph);
        }
        timeBFS = Instant.now().toEpochMilli() - timeStart;
        searchStepsBFS /= COUNT_ITERATION;
        stepsBFS /= COUNT_ITERATION;

        timeStart = Instant.now().toEpochMilli();
        for (int i = 0; i < COUNT_ITERATION; i++) {
            iterationAStar(mainGraph);
        }
        timeAStar = Instant.now().toEpochMilli() - timeStart;
        searchStepsAStar /= COUNT_ITERATION;
        stepsAStar /= COUNT_ITERATION;
        
        timeStart = Instant.now().toEpochMilli();
        for (int i = 0; i < COUNT_ITERATION; i++) {
            iterationGreedy(mainGraph);
        }
        timeGreedy = Instant.now().toEpochMilli() - timeStart;
        searchStepsGreedy /= COUNT_ITERATION;
        stepsGreedy /= COUNT_ITERATION;


        System.out.println("RESULT OF " + COUNT_ITERATION + " ITERATION:\n" +
                "DFS: Time - " + timeDFS + ", search steps - " + searchStepsDFS + ", steps - " + stepsDFS + "\n" +
                "BFS: Time - " + timeBFS + ", search steps - " + searchStepsBFS + ", steps - " + stepsBFS + "\n" +
                "AStar: Time - " + timeAStar + ", search steps - " + searchStepsAStar + ", steps - " + stepsAStar + "\n"+
                "Greedy: Time - " + timeGreedy + ", search steps - " + searchStepsGreedy + ", steps - " + stepsGreedy + "\n"
        		);
    }

    private void iterationDFS(Graph mainGraph) {
        Graph newGraph = new Graph(mainGraph);
        short[][] newLevelData = copyLevelData(levelData);
        Point randomEnd = searchEmptyPoint(newLevelData);
        newLevelData[randomEnd.y][randomEnd.x] = 16;
        Point randomStart = searchEmptyPoint(newLevelData);

        pacman = new Pacman(new TreeDFS(newGraph, randomStart, randomEnd),
                randomStart, randomEnd);
        Board board = new Board(newLevelData);
        pacman.setBoard(board);
        searchStepsDFS += pacman.getCountOfStepsToFind();
        stepsDFS += pacman.getSteps();
    }

    private void iterationBFS(Graph mainGraph) {
        Graph newGraph = new Graph(mainGraph);
        short[][] newLevelData = copyLevelData(levelData);
        Point randomEnd = searchEmptyPoint(newLevelData);
        newLevelData[randomEnd.y][randomEnd.x] = 16;
        Point randomStart = searchEmptyPoint(newLevelData);

        pacman = new Pacman(new TreeBFS(newGraph, randomStart, randomEnd),
                randomStart, randomEnd);
        Board board = new Board(newLevelData);
        pacman.setBoard(board);
        searchStepsBFS += pacman.getCountOfStepsToFind();
        stepsBFS += pacman.getSteps();
    }

    private void iterationAStar(Graph mainGraph) {
        Graph newGraph = new Graph(mainGraph);
        short[][] newLevelData = copyLevelData(levelData);
        Point randomEnd = searchEmptyPoint(newLevelData);
        newLevelData[randomEnd.y][randomEnd.x] = 16;
        Point randomStart = searchEmptyPoint(newLevelData);

        pacman = new Pacman(new AStar(newGraph, randomStart, randomEnd),
                randomStart, randomEnd);
        Board board = new Board(newLevelData);
        pacman.setBoard(board);
        searchStepsAStar += pacman.getCountOfStepsToFind();
        stepsAStar += pacman.getSteps();
    }

    private void iterationGreedy(Graph mainGraph) {
        Graph newGraph = new Graph(mainGraph);
        short[][] newLevelData = copyLevelData(levelData);
        Point randomEnd = searchEmptyPoint(newLevelData);
        newLevelData[randomEnd.y][randomEnd.x] = 16;
        Point randomStart = searchEmptyPoint(newLevelData);

        pacman = new Pacman(new GreedyAlgorithm(newGraph, randomStart, randomEnd),
                randomStart, randomEnd);
        Board board = new Board(newLevelData);
        pacman.setBoard(board);
        searchStepsGreedy += pacman.getCountOfStepsToFind();
        stepsGreedy += pacman.getSteps();
    }
    private Graph buildGraphOnMatrix(short[][] levelData) {
        Graph graph = new Graph(levelData.length * levelData[0].length);
        for (int y = 0; y < levelData.length; y++) {
            for (int x = 0; x < levelData[y].length; x++) {
                if (levelData[y][x] == 0) {
                    if (x + 1 < levelData[y].length && levelData[y][x + 1] == 0)
                        graph.addEdge(new Point(x, y), new Point(x + 1, y));
                    if (y + 1 < levelData.length && levelData[y + 1][x] == 0)
                        graph.addEdge(new Point(x, y), new Point(x, y + 1));
                }
            }
        }
        return graph;
    }

    private short[][] copyLevelData(short[][] array) {
        short[][] result = new short[array.length][array[0].length];
        for (int i = 0; i < array.length; i++)
            System.arraycopy(array[i], 0, result[i], 0, array[i].length);
        return result;
    }

    private Point searchEmptyPoint(short[][] levelData) {
        Point randomPoint;
        do {
            randomPoint = new Point(random.nextInt(15), random.nextInt(15));
            if (levelData[randomPoint.y][randomPoint.x] != 0)
                continue;

            levelData[randomPoint.y][randomPoint.x] = 16;
            break;
        } while (true);
        return randomPoint;
    }
}
