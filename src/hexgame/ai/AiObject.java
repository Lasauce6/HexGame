package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class AiObject {
    private final Board board;
    private final int aiPlayer;
    private Cell lastPlayed = null;
    private final ArrayList<Cell> played;

    public AiObject(Board board, int aiPlayer) {
        this.board = board;
        this.aiPlayer = aiPlayer;
        played = new ArrayList<>();
    }

    public Cell getBestMove() {
        Cell move;
        Cell lastReceived = board.lastMoveTournament;
        if (board.numberOfMoves < 2) {
            move = getBeginningCell();
            lastPlayed = move;
            played.add(lastPlayed);
            return move;
        } else if (board.numberOfMoves == 2 && lastReceived.r() == -1 && lastReceived.c() == -1) {
            move = getBeginningCell();
            played.remove(lastPlayed);
            lastPlayed = move;
            played.add(lastPlayed);
            return move;

        } else {
            move = getMostValuedMove(lastPlayed);
            if (move != null) {
                lastPlayed = move;
                played.add(lastPlayed);
                return move;
            }
        }
        for (int i = 0; i < played.size(); i++) {
            move = getMostValuedMove(played.get(played.size() - i - 1));
            if (move != null) {
                lastPlayed = move;
                played.add(lastPlayed);
                return move;
            }
        }
        return getCellRdm();
    }
    private Cell getBeginningCell() {
        Cell move;
        if (aiPlayer == -1 && board.board[5][0] == 0) move = new Cell(5, 0, aiPlayer);
        else if (aiPlayer == 1 && board.board[0][5] == 0) move = new Cell(0, 5, aiPlayer);
        else if (aiPlayer == -1) move = new Cell(6, 0, aiPlayer);
        else move = new Cell(0, 6, aiPlayer);
        return move;
    }

    private Cell getMostValuedMove(Cell c) {
        Cell move = null;
        if (c != null) {
            ArrayList<Cell> adjacents = board.getAdjacents(c);
            if (aiPlayer == -1) {
                int max = c.c();
                for (Cell adjacent : adjacents) {
                    if (adjacent.player() == 0 && adjacent.c() > max) {
                        max = adjacent.c();
                        move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                    } else if (adjacent.player() == 0 && adjacent.c() == max && move == null) {
                        move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                    }
                }
            } else {
                int max = c.r();
                for (Cell adjacent : adjacents) {
                    if (adjacent.player() == 0 && adjacent.r() > max) {
                        max = adjacent.r();
                        move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                    } else if (adjacent.player() == 0 && adjacent.r() == max && move == null) {
                        move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                    }
                }
            }
        }
        return move;
    }

    private Cell getCellRdm() {
        Random rdm = new Random();
        Cell move = new Cell(rdm.nextInt(11), rdm.nextInt(11), aiPlayer);
        while (board.board[move.r()][move.c()] != 0) {
            move = new Cell(rdm.nextInt(11), rdm.nextInt(11), aiPlayer);
        }
        lastPlayed = move;
        played.add(lastPlayed);
        return move;
    }
}
