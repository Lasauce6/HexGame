package hexgame.graphics;

import hexgame.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Affiche le menu en Online
 */
public class MenuOnlinePanel extends JPanel implements ActionListener {
    private final Client client;
    private final Board board;
    private final Font font = new Font("arial", Font.BOLD, 50);
    private final JButton buttonOnline = new JButton();
    private final JButton buttonTournoi = new JButton();
    private final JButton buttonReturn = new JButton();

    public MenuOnlinePanel(Client client, Board board) {
        super();
        this.client = client;
        this.board = board;
        setLayout(null);
        setOpaque(true);
        buttonOnline.addActionListener(this);
        buttonTournoi.addActionListener(this);
        buttonReturn.addActionListener(this);
    }

    /**
     * Affiche le menu Online
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        g.setColor(Color.BLACK);
        title(g);
        online();
        tournoi();
        retour();
    }

    /**
     * Affiche le titre du menu
     * @param g the <code>Graphics</code> object to protect
     */
    public void title(Graphics g) {
        String title = "Online Menu";
        g.drawString(title, getWidth() / 2 - 140, 100);
    }

    /**
     * Affiche le bouton En ligne
     */
    public void online() {
        buttonOnline.setBounds(getWidth() / 2 - 140, 150, 300, 100);
        buttonOnline.setText("En ligne");
        this.add(buttonOnline);
    }

    /**
     * Affiche le bouton Tournoi
     */
    public void tournoi() {
        buttonTournoi.setBounds(getWidth() / 2 - 140, 275, 300, 100);
        buttonTournoi.setText("Tournoi");
        this.add(buttonTournoi);
    }

    /**
     * Affiche le bouton Retour
     */
    public void retour() {
        buttonReturn.setBounds(getWidth() / 2 - 140, 400, 300, 100);
        buttonReturn.setText("Retour");
        this.add(buttonReturn);
    }

    /**
     * Effectue l'action en fonction du bouton pressé
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonOnline) {
            System.out.println("ONLINE");
        } else if (e.getSource() == buttonTournoi) {
            System.out.println("TOURNOI");
        } else {
            client.menu(board);
        }
    }
}
