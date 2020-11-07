import supporting.MainProcessTree;
import supporting.Point;
import supporting.SearchPath;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost {
    static final int Ghost_ANIM_IMAGE = 1;
    private static final int ANIMATION_STEPS = 5;
    private static int freeId = 0;
    private static Image ghost;
    public int ghost_x, ghost_y;
    int animationCount = 0;
    int additionAnimationY = 0, additionAnimationX = 0;
    SearchPath searchPath;
    Board board;
    ArrayDeque<List<Point>> pointList;
    private int id;
    private int directionGhostX, directionGhostY;
    private int ghostAnimPos = 0;
    private Random random = new Random();

    public Ghost(SearchPath searchPath, Board board, Point startPosition) {
        this.searchPath = searchPath;
        this.board = board;
        id = freeId++;
        ghost_x = startPosition.x;
        ghost_y = startPosition.y;
        initGhostImages();
    }

    private void initGhostImages() {
        ghost = new ImageIcon("images/ghost.png").getImage();
    }

    public void step(Graphics2D g2d) {
        drawGhost(g2d);
        animationCount %= ANIMATION_STEPS;
        if (animationCount++ == 0) {
            animationMoveGhost();
        }
    }

    public void drawGhost(Graphics2D g2d) {
        additionAnimationX = -1 * directionGhostX * (ANIMATION_STEPS - animationCount) * (Board.BLOCK_SIZE / ANIMATION_STEPS);
        additionAnimationY = -1 * directionGhostY * (ANIMATION_STEPS - animationCount) * (Board.BLOCK_SIZE / ANIMATION_STEPS);
        ghostAnimPos++;
        ghostAnimPos %= Ghost_ANIM_IMAGE;
        g2d.drawImage(ghost, ghost_x * Board.BLOCK_SIZE + additionAnimationX, ghost_y * Board.BLOCK_SIZE + additionAnimationY, board);
    }

    void animationMoveGhost() {
        Point p = randomStep();
        if (getRandomBoolean(85)) {//%
            if (pointList == null || pointList.isEmpty()) {
                ArrayList<Point> listGhost = new ArrayList<>();
                listGhost.add(new Point(board.ghosts.get(0).ghost_x, board.ghosts.get(0).ghost_y));
                listGhost.add(new Point(board.ghosts.get(1).ghost_x, board.ghosts.get(1).ghost_y));
                pointList = searchPath.findGhostStrategy(
                        new MainProcessTree(
                                board.screenData,
                                new Point(board.pacman.pacman_x, board.pacman.pacman_y),
                                listGhost));
            }
            pointList.pollLast().get(id);
        }
        moveGhostTo(p.y, p.x);
    }

    private Point randomStep() {
        List<Point> possiblePoint = new ArrayList();
        if (checkPosition(ghost_x - 1, ghost_y, board.screenData))
            possiblePoint.add(new Point(ghost_x - 1, ghost_y));
        if (checkPosition(ghost_x + 1, ghost_y, board.screenData))
            possiblePoint.add(new Point(ghost_x + 1, ghost_y));
        if (checkPosition(ghost_x, ghost_y - 1, board.screenData))
            possiblePoint.add(new Point(ghost_x, ghost_y - 1));
        if (checkPosition(ghost_x, ghost_y + 1, board.screenData))
            possiblePoint.add(new Point(ghost_x, ghost_y + 1));

        return possiblePoint.get(random.nextInt(possiblePoint.size()));
    }


    private boolean checkPosition(int x, int y, short[][] screenData) {
        return (x < screenData.length && x >= 0 && y < screenData.length && y >= 0 && (screenData[y][x] == 0 || screenData[y][x] == 16));
    }

    public boolean getRandomBoolean(int p) {
        return (random.nextInt(100) + 1) < p;
    }

    private void moveGhostTo(int j, int i) {
        directionGhostX = (i - ghost_x);
        directionGhostY = (j - ghost_y);

        ghost_x = i;
        ghost_y = j;
    }
}
