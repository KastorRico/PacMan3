package supporting;

import java.util.ArrayList;

public class LevelDate {
    private Point pacmanLocation;
    private ArrayList<Point> ghostsLocation;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private int weight;
    private short[][] data;

    public LevelDate(short[][] levelData, Point pacmanLocation, ArrayList<Point> ghostsLocation) {
        data = levelData;
        this.pacmanLocation = pacmanLocation;
        this.ghostsLocation = ghostsLocation;
        weight = heuristic();
    }

    private int heuristic() {
        return 0;
    }
}
