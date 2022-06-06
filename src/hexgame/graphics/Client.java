package hexgame.graphics;

import hexgame.Board;
import hexgame.network.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Définie un client
 */
public class Client {
    private final JFrame frame = new JFrame("HexGame"); // La fenêtre de jeu

    /**
     * Le constructeur du Client
     */
    public Client() {
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
     * Affiche le menu du tournoi
     * @param board le plateau de jeu
     */
    public void menuTournoi(Board board) {
        TournoiPanel panel = new TournoiPanel(this, board);
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    /**
     * Affiche le menu de l'IA
     * @param board le plateau de jeu
     */
    public void menuAi(Board board) {
        AiPanel panel = new AiPanel(this, board);
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

    /**
     * Démarre une partie contre l'IA
     * @param diff la difficulté de l'IA
     * @param aiPlayer le joueur de l'IA
     * @param board le plateau de jeu
     */
    public void gameVsAi(int diff, int aiPlayer, Board board) {
        GamePanel panel = new GamePanel(this, board, aiPlayer, diff);
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    /**
     * Démarre une partie de tournoi
     * @param nameChan1 le channel 1
     * @param nameChan2 le channel 2
     * @param diff la difficulté de l'IA
     * @param aiPlayer le joueur de l'IA
     * @param board le plateau de jeu
     */
    public void gameTournament(String nameChan1, String nameChan2, int diff, int aiPlayer, Board board) {
        Channel channel1 = new Channel(nameChan1);
        Channel channel2 = new Channel(nameChan2);
        GamePanel panel = new GamePanel(this, board, aiPlayer, diff, channel1, channel2);
        panel.setBounds(0, 0, 1155, 730);
        frame.setContentPane(panel);
        frame.repaint();
    }

    /**
     * Affiche le joueur qui a gagné
     * @param player le joueur gagnant
     * @param board le plateau de jeu
     */
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
