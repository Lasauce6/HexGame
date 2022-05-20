package hexgame.graphics;

import hexgame.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndPanel extends JPanel implements ActionListener {
    private final Client client;
    private final Board board;
    private final int player;
    private final Font font = new Font("arial", Font.BOLD, 50);
    private final JButton buttonNewGame = new JButton();
    private final JButton buttonQuit = new JButton();

    /**
     * Constructeur du panel
     * @param player le joueur qui a gagné
     * @param client le client
     * @param board le plateau du jeu
     */
    public EndPanel(int player, Client client, Board board) {
        super();
        this.client = client;
        this.board = board;
        this.player = player;
        setLayout(null);
        setOpaque(true);
        buttonNewGame.addActionListener(this);
        buttonQuit.addActionListener(this);
    }

    /**
     * Affiche le menu de fin
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        g.setColor(Color.BLACK);
        setTitle(g);
        setButtonNewGame();
        setButtonQuit();
    }

    /**
     * Affiche le titre/qui a gagné
     * @param g the <code>Graphics</code> object to protect
     */
    private void setTitle(Graphics g) {
        String title;
        if (player == 1) title = "Le joueur rouge a gagné !!!";
        else title = "Le joueur bleu a gagné !!!";
        g.drawString(title, getWidth() / 2 - 325, 100);
    }

    /**
     * Affiche le bouton Nouvelle Partie
     */
    private void setButtonNewGame() {
        buttonNewGame.setBounds(getWidth() / 2 - 140, 150, 300, 100);
        buttonNewGame.setFont(new Font("arial", Font.PLAIN, 24));
        buttonNewGame.setText("Nouvelle partie");
        this.add(buttonNewGame);
    }

    /**
     * Affiche le bouton Quitter
     */
    private void setButtonQuit() {
        buttonQuit.setBounds(getWidth() / 2 - 140, 275, 300, 100);
        buttonQuit.setFont(new Font("arial", Font.PLAIN, 24));
        buttonQuit.setText("Quitter");
        this.add(buttonQuit);
    }

    /**
     * Effectue l'action voulue en fonction du bouton pressé
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonNewGame) client.menu(board);
        else client.close();
    }
}
