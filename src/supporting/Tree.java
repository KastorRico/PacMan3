package supporting;

import java.util.ArrayList;

public class Tree {
    private int numOfLevels = 0;
    public Node tree;
    public Tree(short[][] leveldate, Point Pacman, ArrayList<Point> ghosts){
        generateVariations(leveldate,Pacman,ghosts);
    }
    private void generateVariations(short[][] leveldate, Point pacman, ArrayList<Point> ghosts){
        if(pacman.x+1<leveldate.length) {
            new LevelDate(leveldate,new Point(pacman.x+1, pacman.y),ghosts);
            generateFirstLeftGhost(leveldate,new Point(pacman.x+1, pacman.y),ghosts);
            generateFirstRightGhost(leveldate,new Point(pacman.x+1, pacman.y),ghosts);
            generateFirstTopGhost(leveldate,new Point(pacman.x+1, pacman.y),ghosts);
            generateFirstBottomGhost(leveldate,new Point(pacman.x+1, pacman.y),ghosts);
        }
        if(pacman.x-1>=0){
            new LevelDate(leveldate,new Point(pacman.x-1, pacman.y),ghosts);
        }
        if(pacman.y+1<leveldate.length){
            new LevelDate(leveldate,new Point(pacman.x, pacman.y+1),ghosts);
        }
        if(pacman.y-1>=0){
            new LevelDate(leveldate,new Point(pacman.x, pacman.y-1),ghosts);
        }


    }

    private void generateFirstRightGhost(short[][] leveldate, Point point, ArrayList<Point> ghosts) {
        ArrayList<Point> newGhosts = new ArrayList<Point>();
        if(ghosts.get(0).x+1<leveldate.length) {
            newGhosts.add(new Point(ghosts.get(0).x+1,ghosts.get(0).y));
            secondGhost(leveldate, point, ghosts, newGhosts);
            newGhosts.remove(1);
        }
    }
    private void generateFirstLeftGhost(short[][] leveldate, Point point, ArrayList<Point> ghosts) {
        ArrayList<Point> newGhosts = new ArrayList<Point>();
        if(ghosts.get(0).x-1<leveldate.length) {
            newGhosts.add(new Point(ghosts.get(0).x-1,ghosts.get(0).y));
            secondGhost(leveldate, point, ghosts, newGhosts);
        }
    }

    private void secondGhost(short[][] leveldate, Point point, ArrayList<Point> ghosts, ArrayList<Point> newGhosts) {
        if(ghosts.get(1).x+1<leveldate.length) {
            newGhosts.add(new Point(ghosts.get(1).x+1,ghosts.get(1).y));
            new LevelDate(leveldate,point,newGhosts);
        }
        newGhosts.remove(1);
        if(ghosts.get(1).x-1>=0){
            newGhosts.add(new Point(ghosts.get(1).x-1,ghosts.get(1).y));
            new LevelDate(leveldate,point,newGhosts);
        }
        newGhosts.remove(1);
        if(ghosts.get(1).y+1<leveldate.length){
            newGhosts.add(new Point(ghosts.get(1).x,ghosts.get(1).y+1));
            new LevelDate(leveldate,point,newGhosts);
        }
        newGhosts.remove(1);
        if(ghosts.get(1).y-1>=0){
            newGhosts.add(new Point(ghosts.get(1).x,ghosts.get(1).y-1));
            new LevelDate(leveldate,point,newGhosts);
        }
    }

    private void generateFirstTopGhost(short[][] leveldate, Point point, ArrayList<Point> ghosts) {
        ArrayList<Point> newGhosts = new ArrayList<Point>();
        if(ghosts.get(0).x+1<leveldate.length) {
            newGhosts.add(new Point(ghosts.get(0).x+1,ghosts.get(0).y));
            secondGhost(leveldate, point, ghosts, newGhosts);
            newGhosts.remove(1);
        }
    }
    private void generateFirstBottomGhost(short[][] leveldate, Point point, ArrayList<Point> ghosts) {
        ArrayList<Point> newGhosts = new ArrayList<Point>();
        if(ghosts.get(0).x+1<leveldate.length) {
            newGhosts.add(new Point(ghosts.get(0).x+1,ghosts.get(0).y));
            secondGhost(leveldate, point, ghosts, newGhosts);
            newGhosts.remove(1);
        }
    }
}
