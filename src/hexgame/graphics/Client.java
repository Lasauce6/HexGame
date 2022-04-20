package hexgame.graphics;

import hexgame.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Client {
    private final Panel panel;
    private final JFrame hexFrame = new JFrame("HexGame");

    public Client(Board board) {
        panel = new Panel();
        panel.setBoard(board);
        double[] c = panel.coordinate(board.getSize() - 1, board.getSize() - 1);
        c[0] += Panel.size * Math.sqrt(3) / 2 + Panel.gap;
        c[1] += Panel.size + Panel.gap;
        panel.setBounds(0, 0, (int) Math.round(c[0]), (int) Math.round(c[1]));
        hexFrame.setLayout(null);
        hexFrame.setResizable(false);
        hexFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        hexFrame.setPreferredSize(new Dimension((int) Math.round(c[0]), (int) Math.round(c[1])));
        hexFrame.pack();
        hexFrame.setVisible(true);
        hexFrame.add(panel);
    }

    public void close() {
        hexFrame.dispatchEvent(new WindowEvent(hexFrame, WindowEvent.WINDOW_CLOSING));
    }
    public void repaint() {
        panel.repaint();
    }
}
