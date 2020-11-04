package supporting;

import java.util.ArrayList;

public class LevelDate {
    Point pacmanLocation;
    ArrayList<Point> ghostsLocation;
    short[][] data;
    private int weight;
    private int radius = 3;
    public LevelDate(short[][] levelData, Point pacmanLocation, ArrayList<Point> ghostsLocation) {
        data = levelData.clone();
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

        if(data[pacmanLocation.x][pacmanLocation.y] % 16 == 0)
            data[pacmanLocation.x][pacmanLocation.y] = 0;

        val += distanceToGhost(ghostsLocation.get(0));
        val += distanceToGhost(ghostsLocation.get(1));

        val += 5 * countOfDotsAroundPacman();
        val += -1 * 15 * countOfDotsAllMap();
        return val;
    }

    private int countOfDotsAllMap() {
        int val = 0;
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                if (data[i][j] % 16 == 0)
                    val++;
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
