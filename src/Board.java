import supporting.Minimax;
import supporting.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    static final int BLOCK_SIZE = 24;
    static final int POINT_SIZE = 6;
    final Color dotColor = new Color(192, 192, 0);

    private final int startCountOfDots = 32;
    private int countOfDots;
    private final int deltaLevelDots = 1;

    private static final int DEFAULT_COUNT_OF_LIVE = 3;
    private static final int DEFAULT_COUNT_OF_GHOSTS = 2;
    private int countOfLife;

    public Timer timer;
    public short[][] screenData;
    public short[][] emptyScreenData;
    Pacman pacman;
    ArrayList<Ghost> ghosts = new ArrayList<>();
    private Random random = new Random();
    private Dimension d;
    private Color mazeColor;
    private int level = 0;
    private int scope = 0;

    private Point PACMAN_START = new Point(0, 0);

    public Board(short[][] screenData) {
        this.emptyScreenData = screenData.clone();
        this.pacman = new Pacman(new Minimax(), this,  PACMAN_START);
        countOfLife = DEFAULT_COUNT_OF_LIVE;

        ghosts = new ArrayList<>(DEFAULT_COUNT_OF_GHOSTS);
        for (int i = 0; i < DEFAULT_COUNT_OF_GHOSTS; i++)
            ghosts.add(new Ghost(new Minimax(), this, new Point(15, 15)));//searchEmptyPoint(screenData)));

        levelUp();
        initVariables();
        initBoard();
    }

    private void levelUp() {
        level++;
        countOfDots = startCountOfDots + level * deltaLevelDots;

        screenData = emptyScreenData.clone();
        for(int i = 0; i < countOfDots; i++){
            Point dotPoint = searchEmptyPoint(screenData);
            screenData[dotPoint.x][dotPoint.y] = 16;
        }
    }

    private void initBoard() {
        setFocusable(true);
        setBackground(Color.black);
        pacman.initPacmanImages();
    }

    private boolean checkLevelData() {

        if(screenData[pacman.pacman_x][pacman.pacman_y] % 16 == 0){
            screenData[pacman.pacman_x][pacman.pacman_y] = 0;
        scope++;}

        int countOfDots = 0;
        for (int i = 0; i < screenData.length; i++)
            for (int j = 0; j < screenData[0].length; j++)
                if (screenData[i][j] % 16 == 0)
                    countOfDots++;
        return countOfDots == 0;
    }

    private void initVariables() {
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(400, 400);

        timer = new Timer(500, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();
    }


    private void drawMaze(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(mazeColor);
        g2d.drawRect(0, 0, screenData[0].length * BLOCK_SIZE, screenData.length * BLOCK_SIZE);
        for (int i = 0; i < screenData.length; i++) {
            for (int j = 0; j < screenData[i].length; j++) {
                int x = j * BLOCK_SIZE;
                int y = i * BLOCK_SIZE;
                g2d.setColor(mazeColor);

                if ((screenData[i][j] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i][j] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i][j] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i][j] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i][j] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillOval(x + BLOCK_SIZE / 2 - POINT_SIZE / 2, y + BLOCK_SIZE / 2 - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(checkLevelData())
            levelUp();
        if(isPacmanDie())
            pacmanDie();

        doDrawing(g);
    }

    private boolean isPacmanDie() {
        for(Ghost ghost: ghosts)
            if(pacman.pacman_x == ghost.ghost_x && pacman.pacman_y == ghost.ghost_y)
                return true;
        return false;
    }

    private void pacmanDie() {
        countOfLife--;
        if(countOfLife <= 0) stop(); // END GAME

        ghosts = new ArrayList<>(DEFAULT_COUNT_OF_GHOSTS);
        for (int i = 0; i < DEFAULT_COUNT_OF_GHOSTS; i++)
            ghosts.add(new Ghost(new Minimax(), this, searchEmptyPoint(screenData)));

        pacman.pacman_x = PACMAN_START.x;
        pacman.pacman_y = PACMAN_START.y;
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);

        drawMaze(g2d);
        pacman.step(g2d);
        ghosts.forEach(ghost -> ghost.step(g2d));
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private Point searchEmptyPoint(short[][] levelData) {
        Point randomPoint;
        do {
            randomPoint = new Point(random.nextInt(15), random.nextInt(15));
            if (levelData[randomPoint.x][randomPoint.y] != 0)
                continue;
            break;
        } while (true);
        return randomPoint;
    }

    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_PAUSE || key == KeyEvent.VK_ESCAPE)
                stop();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}