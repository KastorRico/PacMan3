import supporting.Point;
import supporting.SearchPath;

import javax.swing.*;
import java.awt.*;

public class Ghost {
    static final int Ghost_ANIM_IMAGE = 5;
    private static final int ANIMATION_STEPS = 1;
    private static Image ghost;

    int animationCount = 0;
    int ghostSteps = 0;
    int additionAnimationY = 0, additionAnimationX = 0;
    supporting.Point startPosition, finishPosition;
    SearchPath searchPath;
    Board board;
    private int Ghost_x, Ghost_y;
    private int directionGhostX, directionGhostY;
    private int GhostAnimPos = 0;

    public Ghost(SearchPath searchPath, supporting.Point startPosition, supporting.Point finishPosition) {
        this.searchPath = searchPath;
        this.startPosition = startPosition;
        this.finishPosition = finishPosition;
        init();
        initGhostImages();
    }

    void setBoard(Board board) {
        this.board = board;
    }

    void init() {
        moveGhostTo(startPosition.y, startPosition.x);
    }

    void initGhostImages() {
        ghost = new ImageIcon("images/ghost.png").getImage();
    }

    void animationMoveGhost() {
        Point point = searchPath.getNextVisualPoint();
        if (point == null)
            board.stop();
        else
            moveGhostTo(point.y, point.x);
    }

    private void moveGhostTo(int j, int i) {
        directionGhostX = (i - Ghost_x);
        directionGhostY = (j - Ghost_y);

        Ghost_x = i;
        Ghost_y = j;
    }

    public void drawGhost(Graphics2D g2d) {
        //additionAnimationX = -1 * directionGhostX * (ANIMATION_STEPS - animationCount) * (Board.BLOCK_SIZE / ANIMATION_STEPS);
        //additionAnimationY = -1 * directionGhostY * (ANIMATION_STEPS - animationCount) * (Board.BLOCK_SIZE / ANIMATION_STEPS);
        GhostAnimPos++;
        GhostAnimPos %= Ghost_ANIM_IMAGE;
        g2d.drawImage(ghost, Ghost_x * Board.BLOCK_SIZE + additionAnimationX,Ghost_y * Board.BLOCK_SIZE + additionAnimationY, board);
    }


    public void step(Graphics2D g2d) {
        drawGhost(g2d);
        animationCount %= ANIMATION_STEPS;
        if (animationCount++ == 0) {
            animationMoveGhost();
        }
    }

    public int getCountOfStepsToFind() {
        return searchPath.getCountStepsToFind();
    }

    public long getSteps() {
        return searchPath.getCountStepsFromStartToFinish();
    }
}
