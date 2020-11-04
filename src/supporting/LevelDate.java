package supporting;

import java.util.ArrayList;

public class LevelDate {
    public Point getPacmanLocation() {
        return pacmanLocation;
    }

    private Point pacmanLocation;
    private ArrayList<Point> ghostsLocation;
    private int weight;
    private short[][] data;
    private int radius = 3;

    public LevelDate(short[][] levelData, Point pacmanLocation, ArrayList<Point> ghostsLocation) {
        data = levelData;
        this.pacmanLocation = pacmanLocation;
        this.ghostsLocation = ghostsLocation;
        weight = heuristic();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private int heuristic() {
        int val = 0;
        val += -1 * distanceToGhost(ghostsLocation.get(0));
        val += -1 * distanceToGhost(ghostsLocation.get(1));


        val += countOfDotsAroundPacman();
        return val;
    }

    private int countOfDotsAroundPacman() {
        int val = 0;

        for (int i = pacmanLocation.x - radius; i <= pacmanLocation.x + radius; i++)
            for (int j = pacmanLocation.y - radius; j <= pacmanLocation.y + radius; j++)
                if (i >= 0 && i < data.length && j >= 0 && j < data.length)
                    if (data[i][j] % 16 == 0) val++;
        return val;
    }

    private int distanceToGhost(Point ghost) {
        return Math.abs(pacmanLocation.x - ghost.x) + Math.abs(pacmanLocation.y - ghost.y);
    }
}
