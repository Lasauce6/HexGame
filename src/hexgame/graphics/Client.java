package hexgame.graphics;

import hexgame.Board;
import hexgame.Cell;
import hexgame.Move;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class Client {

    public final Panel panel;
    private static Board board;

    public Client(Board board) {
        Client.board = board;
        panel = new Panel();
        MouseListener ml = new MouseListener();
        addMouseListener(ml);
        panel.setBoard(board);
        double[] c = panel.coordinate(board.getSize() - 1, board.getSize() - 1);
        c[0] += Panel.size * Math.sqrt(3) / 2 + Panel.gap;
        c[1] += Panel.size + Panel.gap;
        panel.setBounds(0, 0, (int) Math.round(c[0]), (int) Math.round(c[1]));
        JFrame frame = new JFrame("HexGame");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension((int) Math.round(c[0]), (int) Math.round(c[1])));
        frame.pack();
        frame.setVisible(true);
        frame.add(panel);
    }

    public void repaint() {
        panel.repaint();
    }

    public static class MouseListener extends MouseAdapter {
        public void mouseClicked(@NotNull MouseEvent e) {
            Cell hex = Panel.pxToHex(e.getX(), e.getY());
            Move move = new Move(hex);
            if (board.numberOfMoves % 2 == 0) board.move(move, 2);
            else board.move(move, 1);
        }
    }
}
