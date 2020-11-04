import supporting.Point;
import supporting.SearchPath;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Pacman {
    static final int PACMAN_ANIM_IMAGE = 4;
    private static final int ANIMATION_STEPS = 5;
    private static Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private static Image pacman3up, pacman3down, pacman3left, pacman3right;
    private static Image pacman4up, pacman4down, pacman4left, pacman4right;

    int animationCount = 0;
    int additionAnimationY = 0, additionAnimationX = 0;
    //SearchPath searchPath;
    ArrayDeque<Point> searchPath = new ArrayDeque<>();
    Board board;
    public int pacman_x, pacman_y;
    private int directionPacmanX, directionPacmanY;
    private int pacmanAnimPos = 0;

    public Pacman(Board board, Point start) {
        initPacmanImages();
      //  this.searchPath = searchPath;
        this.board = board;
        pacman_x = start.x;
        pacman_y = start.y;
    }

    void initPacmanImages() {
        pacman1 = new ImageIcon("images/pacman.png").getImage();
        pacman2up = new ImageIcon("images/up1.png").getImage();
        pacman3up = new ImageIcon("images/up2.png").getImage();
        pacman4up = new ImageIcon("images/up3.png").getImage();
        pacman2down = new ImageIcon("images/down1.png").getImage();
        pacman3down = new ImageIcon("images/down2.png").getImage();
        pacman4down = new ImageIcon("images/down3.png").getImage();
        pacman2left = new ImageIcon("images/left1.png").getImage();
        pacman3left = new ImageIcon("images/left2.png").getImage();
        pacman4left = new ImageIcon("images/left3.png").getImage();
        pacman2right = new ImageIcon("images/right1.png").getImage();
        pacman3right = new ImageIcon("images/right2.png").getImage();
        pacman4right = new ImageIcon("images/right3.png").getImage();
    }

    void animationMovePacman() {
        //Point point = searchPath.getNextVisualPoint();
        Point point = searchPath.pop();
        if (point == null)
            board.stop();
        else
            movePacmanTo(point.y, point.x);
    }

    private void movePacmanTo(int j, int i) {
        directionPacmanX = (i - pacman_x);
        directionPacmanY = (j - pacman_y);

        pacman_x = i;
        pacman_y = j;
    }

    public void drawPacmanEating(Graphics2D g2d) {
        additionAnimationX = -1 * directionPacmanX * (ANIMATION_STEPS - animationCount) * (Board.BLOCK_SIZE / ANIMATION_STEPS);
        additionAnimationY = -1 * directionPacmanY * (ANIMATION_STEPS - animationCount) * (Board.BLOCK_SIZE / ANIMATION_STEPS);
        pacmanAnimPos++;
        pacmanAnimPos %= PACMAN_ANIM_IMAGE;
        if (directionPacmanX == -1) {
            drawPacmanEatingLeft(g2d, pacman_x * Board.BLOCK_SIZE + additionAnimationX, pacman_y * Board.BLOCK_SIZE + additionAnimationY);
        } else if (directionPacmanX == 1) {
            drawPacmanEatingRight(g2d, pacman_x * Board.BLOCK_SIZE + additionAnimationX, pacman_y * Board.BLOCK_SIZE + additionAnimationY);
        } else if (directionPacmanY == -1) {
            drawPacmanEatingUp(g2d, pacman_x * Board.BLOCK_SIZE + additionAnimationX, pacman_y * Board.BLOCK_SIZE + additionAnimationY);
        } else {
            drawPacmanEatingDown(g2d, pacman_x * Board.BLOCK_SIZE + additionAnimationX, pacman_y * Board.BLOCK_SIZE + additionAnimationY);
        }
    }

    private void drawPacmanEatingUp(Graphics2D g2d, int x, int y) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2up, x, y, board);
                break;
            case 2:
                g2d.drawImage(pacman3up, x, y, board);
                break;
            case 3:
                g2d.drawImage(pacman4up, x, y, board);
                break;
            default:
                g2d.drawImage(pacman1, x, y, board);
                break;
        }
    }

    private void drawPacmanEatingDown(Graphics2D g2d, int x, int y) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2down, x, y, board);
                break;
            case 2:
                g2d.drawImage(pacman3down, x, y, board);
                break;
            case 3:
                g2d.drawImage(pacman4down, x, y, board);
                break;
            default:
                g2d.drawImage(pacman1, x, y, board);
                break;
        }
    }

    private void drawPacmanEatingLeft(Graphics2D g2d, int x, int y) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2left, x, y, board);
                break;
            case 2:
                g2d.drawImage(pacman3left, x, y, board);
                break;
            case 3:
                g2d.drawImage(pacman4left, x, y, board);
                break;
            default:
                g2d.drawImage(pacman1, x, y, board);
                break;
        }
    }

    private void drawPacmanEatingRight(Graphics2D g2d, int x, int y) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2right, x, y, board);
                break;
            case 2:
                g2d.drawImage(pacman3right, x, y, board);
                break;
            case 3:
                g2d.drawImage(pacman4right, x, y, board);
                break;
            default:
                g2d.drawImage(pacman1, x, y, board);
                break;
        }
    }

    public void step(Graphics2D g2d) {
        drawPacmanEating(g2d);
        animationCount %= ANIMATION_STEPS;
        if (animationCount++ == 0) {
            animationMovePacman();
        }
    }
}
