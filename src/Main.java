import hexgame.Board;
import hexgame.ai.Ai;
import hexgame.graphics.Client;

import javax.swing.*;

public class Main {
    /**
     * Le menu principal
     */
    public static void main(String[] args) throws InterruptedException {
        boolean fin=false;
        int choose;
        Main main = new Main();
        String[] options = {"1v1", "Vs ordi", "TEST", "Quitter"};
        while(!fin) {
            choose = JOptionPane.showOptionDialog(null,
                    "Veuillez saisir votre choix",
                    "Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[3]
                    );
            switch (choose) {
                case 0 -> fin = main.play1V1();
                case 1 -> fin = main.playVsAI();
                case 2 -> fin = main.start();
                case 3 -> fin = true;
            }
        }
    }

    private boolean start() throws InterruptedException {
        Board board = new Board();
        Client client = new Client(board);
        int turn = 0;

        return gameEnd(board, client);
    }

    /**
     * Lance une partie de 1v1
     * @return false si le joueur veut continuer de jouer true s'il veut quitter
     */
    private boolean play1V1() throws InterruptedException {
        Board board = new Board();
        Client client = new Client(board);
        Ai ai = new Ai(board, board.getSize());

        return gameEnd(board, client);
    }

    /**
     * Méthode appelée à la fin de chaque partie pour vérifier qui est le vainqueur
     * et pour demander s'il on veut continuer de jouer
     * @param board le plateau
     * @param client le client
     * @return false si le joueur veut continuer de jouer true s'il veut quitter
     */
    private boolean gameEnd(Board board, Client client) throws InterruptedException {
        while (board.win() == 0) {
            synchronized (this) {
                this.wait(100);
            }
        }

        client.close();

        String[] options = {"Nouvelle partie", "Quitter"};
        int choose = JOptionPane.showOptionDialog(null,
                "Veuillez saisir votre choix",
                "Fin de partie",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        return (choose == 1);
    }

    /**
     * Lance une partie contre l'IA
     * @return false si le joueur veut continuer de jouer true s'il veut quitter
     */
    private boolean playVsAI() {
        Board board = new Board();
        new Client(board);
        Ai ai = new Ai(board, board.getSize());

        while (board.win() == 0) {
            if (board.numberOfMoves % 2 == 0) {
                board.move(ai.getBestMove(-1));
            }
        }

        return true;
    }


}