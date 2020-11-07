import supporting.AlphaBetaPruning;
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
    private static final int DEFAULT_COUNT_OF_LIVE = 3;
    private static final int DEFAULT_COUNT_OF_GHOSTS = 2;
    final Color dotColor = new Color(192, 192, 0);
    private final int startCountOfDots = 120;
    private final int deltaLevelDots = 1;
    public Timer timer;
    public short[][] screenData;
    public short[][] emptyScreenData;
    Pacman pacman;
    ArrayList<Ghost> ghosts = new ArrayList<>();
    private int countOfDots;
    private int countOfLife;
    private Random random = new Random();
    private Dimension d;
    private Color mazeColor;
    private int level = 0;
    private int scope = 0;

    private Point PACMAN_START = new Point(7, 8);

    public Board(short[][] screenData) {
        this.emptyScreenData = screenData.clone();

        levelUp();
        this.pacman = new Pacman(new AlphaBetaPruning(), this, PACMAN_START);
        countOfLife = DEFAULT_COUNT_OF_LIVE;

        ghosts = new ArrayList<>(DEFAULT_COUNT_OF_GHOSTS);
        ghosts.add(new Ghost(new Minimax(), this, new Point(15, 15)));
        ghosts.add(new Ghost(new Minimax(), this, new Point(3, 15)));

        initVariables();
        initBoard();
    }

    private void levelUp() {
        level++;
        countOfDots = startCountOfDots + level * deltaLevelDots;

        screenData = emptyScreenData.clone();
        for (int i = 0; i < countOfDots; i++) {
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
        if (screenData[pacman.pacmanY][pacman.pacmanX] == 16) {
            screenData[pacman.pacmanY][pacman.pacmanX] = 0;
            scope++;
        }

        int countOfDots = 0;
        for (int i = 0; i < screenData.length; i++)
            for (int j = 0; j < screenData[0].length; j++)
                if (screenData[i][j] == 16)
                    countOfDots++;
        return countOfDots == 0;
    }

    private void initVariables() {
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(400, 400);

        timer = new Timer(20, this);
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
                if (screenData[i][j] == 0 || (screenData[i][j] & 16) != 0) {
                    Color roads = new Color(255, 110, 0);
                    g2d.setColor(roads);
                    g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                    g2d.setColor(mazeColor);
                }

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

        if (isPacmanDie())
            pacmanDie();
        if (checkLevelData())
            levelUp();

        doDrawing(g);
    }

    private boolean isPacmanDie() {
        for (Ghost ghost : ghosts)
            if ((pacman.pacmanX == ghost.ghostX && pacman.pacmanY == ghost.ghostY) ||
                    (ghost.ghostYOld == pacman.pacmanY && ghost.ghostXOld == pacman.pacmanX && ghost.ghostX == pacman.pacmanXOld && ghost.ghostY == pacman.pacmanYOld))
                return true;
        return false;
    }

    private void pacmanDie() {
        countOfLife--;
        if (countOfLife <= 0) stop(); // END GAME

        ghosts.get(0).ghostX = 15;
        ghosts.get(0).ghostY = 15;
        ghosts.get(0).pointList.clear();
        ghosts.get(1).ghostX = 1;
        ghosts.get(1).ghostY = 15;
        ghosts.get(1).pointList.clear();

        pacman.pacmanX = PACMAN_START.x;
        pacman.pacmanY = PACMAN_START.y;
        pacman.pointList.clear();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

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
            randomPoint = new Point(random.nextInt(16), random.nextInt(16));
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