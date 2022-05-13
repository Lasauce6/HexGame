package hexgame.graphics;

import hexgame.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Le menu principal
 */
public class MenuPanel  extends JPanel implements ActionListener {
    private final Client client;
    private final Board board;
    private final Font font = new Font("arial", Font.BOLD, 50);
    private final JButton button1v1  = new JButton();
    private final JButton buttonVsAi = new JButton();
    private final JButton buttonOnline = new JButton();
    private final JButton buttonQuit = new JButton();

    /**
     * Constructeur du panel
     * @param client le client
     * @param board le plateau de jeu
     */
    public MenuPanel(Client client, Board board) {
        super();
        this.client = client;
        this.board = board;
        setLayout(null);
        setOpaque(true);
        button1v1.addActionListener(this);
        buttonVsAi.addActionListener(this);
        buttonOnline.addActionListener(this);
        buttonQuit.addActionListener(this);
    }

    /**
     * Affiche le menu
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        g.setColor(Color.BLACK);
        title(g);
        play1v1();
        playVsAi();
        online();
        quit();
    }

    /**
     * Affiche le titre
     * @param g the <code>Graphics</code> object to protect
     */
    private void title(Graphics g) {
        String title = "Hex Game";
        g.drawString(title, getWidth() / 2 - 125, 100);
    }

    /**
     * Affiche le bouton 1v1
     */
    private void play1v1() {
        button1v1.setBounds(getWidth() / 2 - 140, 150, 300, 100);
        button1v1.setText("1v1");
        this.add(button1v1);
    }

    /**
     * Affiche le bouton Vs Ordi
     */
    private void playVsAi() {
        buttonVsAi.setBounds(getWidth() / 2 - 140, 275, 300, 100);
        buttonVsAi.setText("Vs Ordi");
        this.add(buttonVsAi);
    }

    /**
     * Affiche le bouton Online
     */
    private void online() {
        buttonOnline.setBounds(getWidth() / 2 - 140, 400, 300, 100);
        buttonOnline.setText("Online");
        this.add(buttonOnline);
    }

    /**
     * Affiche le bouton Quit
     */
    private void quit() {
        buttonQuit.setBounds(getWidth() / 2 - 140, 525, 300, 100);
        buttonQuit.setText("Quitter");
        this.add(buttonQuit);
    }

    /**
     * Effectue l'action en fonction du bouton pressé
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1v1) {
            client.game1v1(board);
        } else if (e.getSource() == buttonVsAi) {
            client.menuAi(board);
        } else if (e.getSource() == buttonOnline) {
            client.menuOnline(board);
        } else {
            client.close();
        }
    }
}
