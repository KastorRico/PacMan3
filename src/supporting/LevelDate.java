package supporting;

import java.util.ArrayList;

public class LevelDate {
    private Point pacmanLocation;
    private ArrayList<Point> ghostsLocation;
    private double weight;
    private short[][] data;

    public LevelDate(short[][] levelData, Point pacmanLocation, ArrayList<Point> ghostsLocation) {
        data = levelData;
        this.pacmanLocation = pacmanLocation;
        this.ghostsLocation = ghostsLocation;
        weight = heuristic();
    }

    private double heuristic() {
        return 0;
    }
}
