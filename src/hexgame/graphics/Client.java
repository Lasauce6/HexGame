package hexgame.graphics;

import hexgame.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Définie un client
 */
public class Client {
    private final JFrame frame = new JFrame("HexGame"); // La fenêtre de jeu

    public Client() {
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1155, 730));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Affiche le menu principal
     * @param board le plateau de jeu
     */
    public void menu(Board board) {
        MenuPanel panel = new MenuPanel(this, board);
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    /**
     * Affiche le menu en ligne
     * @param board le plateau de jeu
     */
    public void menuOnline(Board board) {
        MenuOnlinePanel panel = new MenuOnlinePanel(this, board);
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    /**
     * Démarre une partie 1v1
     * @param board le plateau de jeu
     */
    public void game1v1(Board board) {
        GamePanel panel = new GamePanel(this, board);
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    public void menuAi(Board board) {
        AiPanel panel = new AiPanel(this, board);
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    /**
     * Démarre une partie contre l'ia
     * @param board le plateau de jeu
     */
    public void gameVsAi(int diff, Board board) {
        GamePanel panel = new GamePanel(this, board);
        panel.ISAI = true;
        panel.AILEVEL = diff;
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    public void gameEnd(int player, Board board) {
        EndPanel panel = new EndPanel(player, this, board);
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    /**
     * Ferme la fenêtre
     */
    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
