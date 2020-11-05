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

        this.ghostsLocation = new ArrayList<>();
        this.ghostsLocation.addAll(ghostsLocation);

        this.pacmanLocation = pacmanLocation;
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

        val += 8 * distanceToGhost(ghostsLocation.get(0));
        val += 8 * distanceToGhost(ghostsLocation.get(1));


        val += countOfDotsAroundPacman(14);
        val += 2 * countOfDotsAroundPacman(8);
        val += 4 * countOfDotsAroundPacman(4);
        val += 8 * countOfDotsAroundPacman(2);
        val += 16 * countOfDotsAroundPacman(1);

        val += -16 * countOfDotsAllMap();
        System.out.println();
        return val;
    }

    private int countOfDotsAllMap() {
        int val = 0;
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                if (data[i][j] == 16)
                    val++;
        System.out.println("countOfDotsAllMap = " + val);
        return val;
    }

    private int countOfDotsAroundPacman(int radius) {
        int val = 0;
        for (int i = pacmanLocation.y - radius; i <= pacmanLocation.y + radius; i++)
            for (int j = pacmanLocation.x - radius; j <= pacmanLocation.x + radius; j++)
                if (i >= 0 && i < data.length && j >= 0 && j < data.length)
                    if (data[i][j] == 16) val++;
                System.out.println("countOfDotsAroundPacman(r = " + radius + ") = " + val);
        return val;
    }

    private int distanceToGhost(Point ghost) {
        return Math.abs(pacmanLocation.x - ghost.x) + Math.abs(pacmanLocation.y - ghost.y);
    }
}
