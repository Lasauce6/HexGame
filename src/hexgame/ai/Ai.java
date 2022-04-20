package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;

public class Ai {
    private final Board board;
    private final int boardSize;

    public Ai(Board board, int boardSize) {
        this.board = board;
        this.boardSize = boardSize;
    }

    public Cell getBestMove(int player) {

        return new Cell(0, 0, player);
    }
}
