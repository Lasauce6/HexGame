import hexgame.Board;
import hexgame.Cell;
import hexgame.graphics.Client;

import javax.swing.*;

public class Main {
    /**
     * Le menu principal
     */
    public static void main(String[] args) {
        Board board = new Board();
        Client client = new Client();
        client.menu(board);
    }

    public void win(int player) {
        if (player == 1) {
            JOptionPane.showMessageDialog(null,
                    "Le joueur rouge à gagné",
                    "Victoire",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (player == -1) {
            JOptionPane.showMessageDialog(null,
                    "Le joueur bleu à gagné",
                    "Victoire",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


}