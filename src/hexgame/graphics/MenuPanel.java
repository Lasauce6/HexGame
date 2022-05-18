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
    private final JButton buttonTournament = new JButton();
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
        buttonTournament.addActionListener(this);
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
        setTitle(g);
        setButton1v1();
        setButtonVsAi();
        setButtonTournament();
        setButtonQuit();
    }

    /**
     * Affiche le titre
     * @param g the <code>Graphics</code> object to protect
     */
    private void setTitle(Graphics g) {
        String title = "Hex Game";
        g.drawString(title, getWidth() / 2 - 125, 100);
    }

    /**
     * Affiche le bouton 1v1
     */
    private void setButton1v1() {
        button1v1.setBounds(getWidth() / 2 - 140, 150, 300, 100);
        button1v1.setFont(new Font("arial", Font.PLAIN, 24));
        button1v1.setText("1v1");
        this.add(button1v1);
    }

    /**
     * Affiche le bouton Vs Ordi
     */
    private void setButtonVsAi() {
        buttonVsAi.setBounds(getWidth() / 2 - 140, 275, 300, 100);
        buttonVsAi.setFont(new Font("arial", Font.PLAIN, 24));
        buttonVsAi.setText("Vs Ordi");
        this.add(buttonVsAi);
    }

    /**
     * Affiche le bouton Tournoi
     */
    private void setButtonTournament() {
        buttonTournament.setBounds(getWidth() / 2 - 140, 400, 300, 100);
        buttonTournament.setFont(new Font("arial", Font.PLAIN, 24));
        buttonTournament.setText("Tournoi");
        this.add(buttonTournament);
    }

    /**
     * Affiche le bouton Quit
     */
    private void setButtonQuit() {
        buttonQuit.setBounds(getWidth() / 2 - 140, 525, 300, 100);
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
        if (e.getSource() == button1v1) {
            client.game1v1(board);
        } else if (e.getSource() == buttonVsAi) {
            client.menuAi(board);
        } else if (e.getSource() == buttonTournament) {
            client.menuTournoi(board);
        } else {
            client.close();
        }
    }
}
