<<<<<<< Updated upstream
=======
import supporting.AlphaBetaPruning;
import supporting.MainProcessTree;
import supporting.Point;

>>>>>>> Stashed changes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
<<<<<<< Updated upstream
=======
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
>>>>>>> Stashed changes

public class Board extends JPanel implements ActionListener {
    static final int BLOCK_SIZE = 24;
    static final int POINT_SIZE = 6;
    final Color dotColor = new Color(192, 192, 0);
    Dimension d;
    Color mazeColor;
    Timer timer;
    public short[][] screenData;
    Pacman pacman;
<<<<<<< Updated upstream
    Ghost ghost;

    public Board(short[][] screenData, Pacman pacman, Ghost ghost) {
        this.screenData = screenData.clone();
        this.pacman = pacman;
        this.ghost =ghost;
        pacman.setBoard(this);
        ghost.setBoard(this);
=======
    ArrayList<Ghost> ghosts = new ArrayList<>();
    ArrayList<Point> dots = new ArrayList<>();
    private Random random = new Random();
    private Dimension d;
    private Color mazeColor;
    private int level = 0;
    private int scope = 0;

    private Point PACMAN_START = new Point(0, 0);

    public Board(short[][] screenData) {
        this.emptyScreenData = screenData.clone();
        this.pacman = new Pacman(this,  PACMAN_START);
        countOfLife = DEFAULT_COUNT_OF_LIVE;

        ghosts = new ArrayList<>(DEFAULT_COUNT_OF_GHOSTS);
        ArrayList<Point> ghosts_start = new ArrayList<>();
        Point ghost_start = null;
        for (int i = 0; i < DEFAULT_COUNT_OF_GHOSTS; i++) {
            ghost_start = searchEmptyPoint(screenData);
            ghosts_start.add(ghost_start);
            ghosts.add(new Ghost(this, ghost_start));
        }
        MainProcessTree tree = new MainProcessTree(screenData,PACMAN_START,ghosts_start,dots);
        int tr = 0;
        while (tr < 5){
            //pacman.searchPath = new AlphaBetaPruning(tree);
            AlphaBetaPruning abp = new AlphaBetaPruning(tree);
            pacman.searchPath.addAll(abp.getPacmanWay());
            tr++;
            tree = new MainProcessTree(screenData,pacman.searchPath.getLast(),ghosts_start,dots);
        }
        levelUp();
>>>>>>> Stashed changes
        initVariables();
        initBoard();
    }

<<<<<<< Updated upstream
    public Board(short[][] screenData) {
        this.screenData = screenData.clone();
=======
    private void levelUp() {
        level++;
        countOfDots = startCountOfDots + level * deltaLevelDots;
        dots.clear();
        screenData = emptyScreenData.clone();
        for(int i = 0; i < countOfDots; i++){
            Point dotPoint = searchEmptyPoint(screenData);
            dots.add(dotPoint);
            screenData[dotPoint.x][dotPoint.y] = 16;
        }
>>>>>>> Stashed changes
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        pacman.initPacmanImages();
        ghost.initGhostImages();
    }

    private void initVariables() {
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(400, 400);

        timer = new Timer(50, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();
    }


    private void drawMaze(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(mazeColor);
        g2d.drawRect(0, 0, screenData[0].length*BLOCK_SIZE, screenData.length * BLOCK_SIZE);
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
                    dots.add(new Point(i,j));
                    g2d.fillOval(x + BLOCK_SIZE / 2 - POINT_SIZE / 2, y + BLOCK_SIZE / 2 - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

<<<<<<< Updated upstream
=======
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
            ghosts.add(new Ghost( this, searchEmptyPoint(screenData)));

        pacman.pacman_x = PACMAN_START.x;
        pacman.pacman_y = PACMAN_START.y;
    }

>>>>>>> Stashed changes
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);

        drawMaze(g2d);
        pacman.step(g2d);
<<<<<<< Updated upstream
        ghost.step(g2d);
=======
        //ghosts.forEach(ghost -> ghost.step(g2d));
>>>>>>> Stashed changes
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