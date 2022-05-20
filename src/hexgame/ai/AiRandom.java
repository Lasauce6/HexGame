package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;

import java.util.Random;

/**
 * Un joueur IA random
 */
public class AiRandom {
    private final Board board;
    private final int player;

    public AiRandom(Board board, int player) {
        this.board = board;
        this.player = player;
    }

    /**
     * Renvoi le meilleur coup (pas vraiment vu qu'aléatoire)
     * @return une cellule représentant le meilleur coup
     */
    public Cell getBestMove() {
        if (board.numberOfMoves == 1) {
            return new Cell(-1, -1, 1);
        }
        Random random = new Random();
        int x = random.nextInt(11), y = random.nextInt(11);
        while (board.board[x][y] != 0) {
            x = random.nextInt(11);
            y = random.nextInt(11);
        }
        return new Cell(x, y, player);
    }
}
