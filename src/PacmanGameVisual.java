import supporting.*;
import supporting.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PacmanGameVisual extends JFrame {
    final short[][] levelData = {
            {0, 0, 0, 9, 8, 12, 0, 0, 0, 0, 0, 0, 1, 32, 32, 32},
            {0, 7, 0, 0, 0, 0, 0, 3, 6, 0, 7, 0, 9, 8, 8, 32},
            {0, 13, 0, 7, 0, 3, 2, 32, 4, 0, 5, 0, 0, 0, 0, 1},
            {0, 0, 0, 13, 0, 9, 8, 8, 4, 0, 13, 0, 3, 6, 0, 1},
            {10, 14, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 9, 12, 0, 9},
            {0, 0, 0, 3, 2, 2, 6, 0, 5, 0, 7, 0, 0, 0, 0, 0},
            {0, 15, 0, 9, 8, 8, 12, 0, 13, 0, 1, 2, 14, 0, 7, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 4, 0, 0, 5, 0},
            {6, 0, 7, 0, 3, 2, 6, 0, 7, 0, 1, 4, 0, 3, 12, 0},
            {5, 0, 5, 0, 1, 32, 12, 0, 13, 0, 9, 4, 0, 5, 0, 0},
            {12, 0, 5, 0, 1, 4, 0, 0, 0, 0, 0, 13, 0, 5, 0, 3},
            {0, 0, 5, 0, 9, 12, 0, 7, 0, 7, 0, 0, 0, 5, 0, 1},
            {0, 11, 12, 0, 0, 0, 0, 13, 0, 9, 10, 14, 0, 5, 0, 9},
            {0, 0, 0, 0, 3, 6, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0},
            {0, 15, 0, 11, 8, 8, 14, 0, 3, 2, 6, 0, 11, 8, 14,0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 32, 4, 0, 0, 0, 0, 0}
    };

    private Pacman pacman;
    private Ghost ghost;
    private Random random = new Random();

    public PacmanGameVisual() {
        initUI();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var ex = new PacmanGameVisual();
            ex.setVisible(true);
        });
    }

    private void initUI() {
        Board board = new Board(levelData);
        add(board);
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize((board.screenData[0].length + 1) * Board.BLOCK_SIZE, (board.screenData.length + 2) * Board.BLOCK_SIZE);
        setLocationRelativeTo(null);
    }
}
