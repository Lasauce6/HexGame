package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;

import java.util.ArrayList;
import java.util.Random;

public class AiMedium {
    private final Board board;
    private final int aiPlayer;
    private Cell lastPlayed = null;

    public AiMedium(Board board, int aiPlayer) {
        this.board = board;
        this.aiPlayer = aiPlayer;
    }

    public Cell getBestMove() {
        if (board.numberOfMoves < 2) {
            return getCellRdm();
        } else {
            ArrayList<Cell> adjacents = board.getAdjacents(lastPlayed);
            for (Cell adjacent : adjacents) {
                if (adjacent.player() == 0) {
                    lastPlayed = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                    return new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                }
            }
        }
        return getCellRdm();
    }

    private Cell getCellRdm() {
        Random rdm = new Random();
        Cell move = new Cell(rdm.nextInt(11), rdm.nextInt(11), aiPlayer);
        while (board.board[move.r()][move.c()] != 0) {
            move = new Cell(rdm.nextInt(11), rdm.nextInt(11), aiPlayer);
        }
        lastPlayed = move;
        return move;
    }
}
