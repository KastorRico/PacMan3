import supporting.Point;
import supporting.SearchPath;

import javax.swing.*;
import java.awt.*;

public class Ghost {
    static final int Ghost_ANIM_IMAGE = 5;
    private static final int ANIMATION_STEPS = 1;
    private static Image ghost;

    int animationCount = 0;
    int additionAnimationY = 0, additionAnimationX = 0;
    //SearchPath searchPath;
    Board board;
    public int ghost_x, ghost_y;
    private int directionGhostX, directionGhostY;
    private int ghostAnimPos = 0;

    public Ghost(Board board, Point startPosition) {
       // this.searchPath = searchPath;
        this.board = board;
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
       // Point point = searchPath.getNextVisualPoint();
      //  moveGhostTo(point.y, point.x);
    }

    private void moveGhostTo(int j, int i) {
        directionGhostX = (i - ghost_x);
        directionGhostY = (j - ghost_y);

        ghost_x = i;
        ghost_y = j;
    }
}
