package hexgame.graphics;

import hexgame.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AiPanel extends JPanel implements ActionListener {
    private final Client CLIENT;
    private final Board BOARD;
    private final Font FONT = new Font("arial", Font.BOLD, 50);
    private final JButton BUTTON1 = new JButton();
    private final JButton BUTTON2 = new JButton();
    private final JButton BUTTON3 = new JButton();

    public AiPanel(Client client, Board board) {
        super();
        this.CLIENT = client;
        this.BOARD = board;
        setLayout(null);
        setOpaque(true);
        BUTTON1.addActionListener(this);
        BUTTON2.addActionListener(this);
        BUTTON3.addActionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(FONT);
        g.setColor(Color.BLACK);
        title(g);
        level1();
        level2();
        level3();
    }

    private void title(Graphics g) {
        String title = "Choix de la difficulté";
        g.drawString(title, getWidth() / 2 - 275, 100);
    }

    private void level1() {
        BUTTON1.setBounds(getWidth() / 2 - 140, 150, 300, 100);
        BUTTON1.setText("Difficulté 1");
        this.add(BUTTON1);
    }

    private void level2() {
        BUTTON2.setBounds(getWidth() / 2 - 140, 275, 300, 100);
        BUTTON2.setText("Difficulté 2");
        this.add(BUTTON2);
    }

    private void level3() {
        BUTTON3.setBounds(getWidth() / 2 - 140, 400, 300, 100);
        BUTTON3.setText("Difficulté 3");
        this.add(BUTTON3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BUTTON1) {
            CLIENT.gameVsAi(1, BOARD);
        } else if (e.getSource() == BUTTON2) {
            CLIENT.gameVsAi(2, BOARD);
        } else {
            CLIENT.gameVsAi(3, BOARD);
        }
    }
}
