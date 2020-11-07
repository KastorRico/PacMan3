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
    static final int Ghost_ANIM_IMAGE = 4;
    private static final int ANIMATION_STEPS = 5;
    private static int freeId = 0;
    private static Image ghost_left1, ghost_left2, ghost_left3, ghost_right1, ghost_right2, ghost_right3;
    private static Image ghost_up1, ghost_up2, ghost_up3, ghost_down1, ghost_down2, ghost_down3;
    public int ghostX, ghostY;
    public int ghostXOld, ghostYOld;
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
        ghostX = startPosition.x;
        ghostY = startPosition.y;
        initGhostImages();
    }

    private void initGhostImages() {
        ghost_left1 = new ImageIcon("images/ghost_left1.png").getImage();
        ghost_left2 = new ImageIcon("images/ghost_left2.png").getImage();
        ghost_left3 = new ImageIcon("images/ghost_left3.png").getImage();
        ghost_right1 = new ImageIcon("images/ghost_right1.png").getImage();
        ghost_right2 = new ImageIcon("images/ghost_right2.png").getImage();
        ghost_right3 = new ImageIcon("images/ghost_right3.png").getImage();
        ghost_up1 = new ImageIcon("images/ghost_up1.png").getImage();
        ghost_up2 = new ImageIcon("images/ghost_up2.png").getImage();
        ghost_up3 = new ImageIcon("images/ghost_up3.png").getImage();
        ghost_down1 = new ImageIcon("images/ghost_down1.png").getImage();
        ghost_down2 = new ImageIcon("images/ghost_down2.png").getImage();
        ghost_down3 = new ImageIcon("images/ghost_down3.png").getImage();
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

        if (directionGhostX == -1)
            drawGhostLeft(g2d, ghostX * Board.BLOCK_SIZE + additionAnimationX, ghostY * Board.BLOCK_SIZE + additionAnimationY);
        else if (directionGhostX == 1)
            drawGhostRight(g2d, ghostX * Board.BLOCK_SIZE + additionAnimationX, ghostY * Board.BLOCK_SIZE + additionAnimationY);
        else if (directionGhostY == -1)
            drawGhostUp(g2d, ghostX * Board.BLOCK_SIZE + additionAnimationX, ghostY * Board.BLOCK_SIZE + additionAnimationY);
        else
            drawGhostDown(g2d, ghostX * Board.BLOCK_SIZE + additionAnimationX, ghostY * Board.BLOCK_SIZE + additionAnimationY);

    }

    private void drawGhostUp(Graphics2D g2d, int x, int y) {
        switch (ghostAnimPos) {
            case 1:
                g2d.drawImage(ghost_up1, x, y, board);
                break;
            case 2:
                g2d.drawImage(ghost_up2, x, y, board);
                break;
            case 3:
                g2d.drawImage(ghost_up3, x, y, board);
                break;
        }
    }

    private void drawGhostDown(Graphics2D g2d, int x, int y) {
        switch (ghostAnimPos) {
            case 1:
                g2d.drawImage(ghost_down1, x, y, board);
                break;
            case 2:
                g2d.drawImage(ghost_down2, x, y, board);
                break;
            default:
                g2d.drawImage(ghost_down3, x, y, board);
                break;
        }
    }

    private void drawGhostLeft(Graphics2D g2d, int x, int y) {
        switch (ghostAnimPos) {
            case 1:
                g2d.drawImage(ghost_left1, x, y, board);
                break;
            case 2:
                g2d.drawImage(ghost_left2, x, y, board);
                break;
            default:
                g2d.drawImage(ghost_left3, x, y, board);
                break;
        }
    }

    private void drawGhostRight(Graphics2D g2d, int x, int y) {
        switch (ghostAnimPos) {
            case 1:
                g2d.drawImage(ghost_right1, x, y, board);
                break;
            case 2:
                g2d.drawImage(ghost_right2, x, y, board);
                break;
            default:
                g2d.drawImage(ghost_right3, x, y, board);
                break;
        }
    }


    void animationMoveGhost() {
        if (pointList == null || pointList.isEmpty()) {
            ArrayList<Point> listGhost = new ArrayList<>();
            listGhost.add(new Point(board.ghosts.get(0).ghostX, board.ghosts.get(0).ghostY));
            listGhost.add(new Point(board.ghosts.get(1).ghostX, board.ghosts.get(1).ghostY));
            pointList = searchPath.findGhostStrategy(
                    new MainProcessTree(
                            board.screenData,
                            new Point(board.pacman.pacmanX, board.pacman.pacmanY),
                            listGhost,
                            board.pacman.map));
        }
        Point p = pointList.pollLast().get(id);

        if (getRandomBoolean(15)) {//%
            p = randomStep();
            pointList.clear();
        }

        moveGhostTo(p.y, p.x);
    }

    private Point randomStep() {
        List<Point> possiblePoint = new ArrayList();
        if (checkPosition(ghostX - 1, ghostY, board.screenData))
            possiblePoint.add(new Point(ghostX - 1, ghostY));
        if (checkPosition(ghostX + 1, ghostY, board.screenData))
            possiblePoint.add(new Point(ghostX + 1, ghostY));
        if (checkPosition(ghostX, ghostY - 1, board.screenData))
            possiblePoint.add(new Point(ghostX, ghostY - 1));
        if (checkPosition(ghostX, ghostY + 1, board.screenData))
            possiblePoint.add(new Point(ghostX, ghostY + 1));
        possiblePoint.add(new Point(ghostX, ghostY));

        return possiblePoint.get(random.nextInt(possiblePoint.size()));
    }


    private boolean checkPosition(int x, int y, short[][] screenData) {
        return (x < screenData.length && x >= 0 && y < screenData.length && y >= 0 && (screenData[y][x] == 0 || screenData[y][x] == 16));
    }

    public boolean getRandomBoolean(int p) {
        return (random.nextInt(100) + 1) <= p;
    }

    private void moveGhostTo(int j, int i) {
        directionGhostX = (i - ghostX);
        directionGhostY = (j - ghostY);
        ghostXOld = ghostX;
        ghostYOld = ghostY;
        ghostX = i;
        ghostY = j;
    }
}
