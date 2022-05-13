package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;

import java.util.Random;

public class AiRandom {
    private final Board BOARD;

    public AiRandom(Board board) {
        this.BOARD = board;
    }

    public Cell getBestMove() {
        if (BOARD.numberOfMoves == 1) {
            return new Cell(-1, -1, 1);
        }
        Random random = new Random();
        int x = random.nextInt(11), y = random.nextInt(11);
        while (BOARD.board[x][y] != 0) {
            x = random.nextInt(11);
            y = random.nextInt(11);
        }
        return new Cell(x, y, 1);
    }
}
