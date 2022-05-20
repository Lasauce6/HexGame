package hexgame.graphics;

import hexgame.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Affiche le menu en Online
 */
public class TournoiPanel extends JPanel implements ActionListener {
    private final Client client;
    private final Board board;
    private final JTextField channel1 = new JTextField();
    private final JTextField channel2 = new JTextField();
    private final JRadioButton level1 = new JRadioButton("1");
    private final JRadioButton level2 = new JRadioButton("2");
    private final JRadioButton level3 = new JRadioButton("3");
    private final JRadioButton red = new JRadioButton("Rouge");
    private final JRadioButton blue = new JRadioButton("Bleu");
    private final JButton buttonPlay = new JButton();
    private final JButton buttonReturn = new JButton();

    /**
     * Le constructeur du panel
     * @param client le client
     * @param board le plateau de jeu
     */
    public TournoiPanel(Client client, Board board) {
        super();
        this.client = client;
        this.board = board;
        setLayout(null);
        setOpaque(true);
        buttonPlay.addActionListener(this);
        buttonReturn.addActionListener(this);
    }

    /**
     * Affiche le menu du Tournoi
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        title(g);
        setChannels();
        setDifficulty();
        setPlayer();
        setButtonPlay();
        setButtonReturn();
    }

    /**
     * Affiche le titre du menu
     * @param g the <code>Graphics</code> object to protect
     */
    private void title(Graphics g) {
        g.setFont(new Font("arial", Font.BOLD, 50));
        String title = "Tournoi";
        g.drawString(title, getWidth() / 2 - 95, 100);
    }

    /**
     * Affiche les différents champs pour les channels
     */
    private void setChannels() {
        JLabel label1 = new JLabel("Nom du channel d'envoi :");
        label1.setBounds(getWidth() / 2 - 220, 130, 210, 50);
        label1.setFont(new Font("arial", Font.PLAIN, 14));
        JLabel label2 = new JLabel("Nom du channel de réception :");
        label2.setBounds(getWidth() / 2 - 220, 200, 210, 50);
        label2.setFont(new Font("arial", Font.PLAIN, 14));
        channel1.setBounds(getWidth() / 2 , 130, 240, 50);
        channel1.setEditable(true);
        channel2.setBounds(getWidth() / 2 , 200, 240, 50);
        channel2.setEditable(true);
        this.add(label1);
        this.add(label2);
        this.add(channel1);
        this.add(channel2);
    }

    /**
     * Affiche le choix de difficulté
     */
    private void setDifficulty() {
        JLabel diff = new JLabel("Niveau de difficulté :");
        diff.setBounds(getWidth() / 2 - 200, 270, 190, 50);
        diff.setFont(new Font("arial", Font.PLAIN, 14));
        level1.setBounds(getWidth() / 2, 270, 65, 50);
        level1.setFont(new Font("arial", Font.PLAIN, 16));
        level1.setSelected(true);
        level2.setBounds(getWidth() / 2 + 80, 270, 65, 50);
        level2.setFont(new Font("arial", Font.PLAIN, 16));
        level3.setBounds(getWidth() / 2 + 160, 270, 65, 50);
        level3.setFont(new Font("arial", Font.PLAIN, 16));
        ButtonGroup bg = new ButtonGroup();
        bg.add(level1);
        bg.add(level2);
        bg.add(level3);
        this.add(level1);
        this.add(level2);
        this.add(level3);
        this.add(diff);
    }

    /**
     * Affiche le choix de joueur
     */
    private void setPlayer() {
        JLabel player = new JLabel("Couleur du joueur :");
        player.setBounds(getWidth() / 2 - 200, 330, 190, 50);
        player.setFont(new Font("arial", Font.PLAIN, 14));
        red.setBounds(getWidth() / 2, 330, 120, 50);
        red.setFont(new Font("arial", Font.PLAIN, 16));
        blue.setBounds(getWidth() / 2 + 130, 330, 120, 50);
        blue.setFont(new Font("arial", Font.PLAIN, 16));
        blue.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(red);
        bg.add(blue);
        this.add(red);
        this.add(blue);
        this.add(player);
    }

    /**
     * Affiche le bouton pour lancer la partie
     */
    private void setButtonPlay() {
        buttonPlay.setBounds(getWidth() / 2 - 140, 400, 300, 100);
        buttonPlay.setFont(new Font("arial", Font.PLAIN, 24));
        buttonPlay.setText("Lancer la partie");
        this.add(buttonPlay);
    }

    /**
     * Affiche le bouton Retour
     */
    private void setButtonReturn() {
        buttonReturn.setBounds(getWidth() / 2 - 140, 525, 300, 100);
        buttonReturn.setFont(new Font("arial", Font.PLAIN, 24));
        buttonReturn.setText("Retour");
        this.add(buttonReturn);
    }

    /**
     * Effectue l'action voulue en fonction du bouton pressé
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonPlay) {
            if (level1.isSelected()) {
                if (blue.isSelected()) client.gameTournament(channel1.getText(), channel2.getText(), 1, -1, board);
                else client.gameTournament(channel1.getText(), channel2.getText(), 1, 1, board);
            } else if (level2.isSelected()) {
                if (blue.isSelected()) client.gameTournament(channel1.getText(), channel2.getText(), 2, -1, board);
                else client.gameTournament(channel1.getText(), channel2.getText(), 2, 1, board);
            } else {
                if (blue.isSelected()) client.gameTournament(channel1.getText(), channel2.getText(), 3, -1, board);
                else client.gameTournament(channel1.getText(), channel2.getText(), 3, 1, board);
            }
        }
        else client.menu(board);
    }
}
