package supporting;

import java.util.ArrayList;

public class LevelDate {
    private Point pacmanLocation;
    private ArrayList<Point> ghostsLocation;
    private double weight;
    private short[][] data;
    public LevelDate(short[][] leveldata,Point pacmanLocation,ArrayList<Point> ghostsLocation){
        data = leveldata;
        this.pacmanLocation = pacmanLocation;
        this.ghostsLocation = ghostsLocation;
        weight = heuristic();
    }

    private double heuristic(){
     return 0;
    }
}
