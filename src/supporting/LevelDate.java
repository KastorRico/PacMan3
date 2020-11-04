package supporting;

import java.util.ArrayList;

public class LevelDate {
    Point pacmanLocation;
    ArrayList<Point> ghostsLocation;
    short[][] data;
    private int weight;

    public LevelDate(short[][] levelData, Point pacmanLocation, ArrayList<Point> ghostsLocation) {
        data = new short[levelData.length][levelData[0].length];
        for (int i = 0; i < levelData.length; i++)
            System.arraycopy(levelData[i], 0, data[i], 0, levelData[0].length);

        this.pacmanLocation = pacmanLocation;
        this.ghostsLocation = ghostsLocation;
        weight = heuristic();
    }

    public Point getPacmanLocation() {
        return pacmanLocation;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private int heuristic() {
        int val = 0;

        if (data[pacmanLocation.y][pacmanLocation.x] == 16)
            data[pacmanLocation.y][pacmanLocation.x] = 0;

        val += distanceToGhost(ghostsLocation.get(0));
        val += distanceToGhost(ghostsLocation.get(1));


        val += countOfDotsAroundPacman(14);
        val += 3 * countOfDotsAroundPacman(8);
        val += 7 * countOfDotsAroundPacman(4);
        val += 15 * countOfDotsAroundPacman(2);
        val += 31 * countOfDotsAroundPacman(1);
        val += -100 * countOfDotsAllMap();
        return val;
    }

    private int countOfDotsAllMap() {
        int val = 0;
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                if (data[i][j] == 16)
                    val++;
        return val;
    }

    private int countOfDotsAroundPacman(int radius) {
        int val = 0;
        for (int i = pacmanLocation.y - radius; i <= pacmanLocation.y + radius; i++)
            for (int j = pacmanLocation.x - radius; j <= pacmanLocation.x + radius; j++)
                if (i >= 0 && i < data.length && j >= 0 && j < data.length)
                    if (data[i][j] == 16) val++;
        return val;
    }

    private int distanceToGhost(Point ghost) {
        return Math.abs(pacmanLocation.x - ghost.x) + Math.abs(pacmanLocation.y - ghost.y);
    }
}
