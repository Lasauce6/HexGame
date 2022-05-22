package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


public class AiPlayer {
    private final Board board;
    private final int aiPlayer;
    private AtomicReference<Float> bestAlpha;

    public AiPlayer(Board board, int aiPlayer) {
        this.board = board;
        this.aiPlayer = aiPlayer;
    }

    public Cell getBestMove() {
        Cell move = getMove();
        System.out.println(move);
        return new Cell(move.r(), move.c(), aiPlayer);
    }

    private Cell getMove() {
        Set<Cell> moves;
        Set<Cell> allMoves = new HashSet<>();
        Board boardCopy = new Board(board);
        for (int i = 0; i < boardCopy.getSize(); i++) {
            for (int j = 0; j < boardCopy.getSize(); j++) {
                if (boardCopy.cellBoard[i][j].player() == 0) {
                    allMoves.add(boardCopy.cellBoard[i][j]);
                }
            }
        }
        if(!allMoves.isEmpty()) {
            moves = new HashSet<>();
            allMoves.stream()
                    .map(cell -> boardCopy.getAdjacentsPlayer(cell, 0))
                    .forEach(moves::addAll);
        } else return new Cell(boardCopy.getSize() / 2, boardCopy.getSize() / 2, aiPlayer);

        Cell p = checkMoves(moves);
        if (p != null) return p;

        bestAlpha = new AtomicReference<>(Float.NEGATIVE_INFINITY);
        AtomicReference<Cell> bestMove = new AtomicReference<>(new Cell(-2, -2));
        Parallel.For(moves, move -> {
            Board boardCopy1 = new Board(board);
            boardCopy1.move(move);
            Set<Cell> newList = new HashSet<>(moves);
            newList.remove(move);
            newList.addAll(boardCopy1.getAdjacentsPlayer(move, 0));
            float aux = min_value(boardCopy1, newList, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, boardCopy1.numberOfMoves);

            if (aux > bestAlpha.get()) {
                bestMove.set(move);
                bestAlpha.set(aux);
            }
        });

        if(bestMove.get().equals(new Cell(-2, -2))) return moves.iterator().next();
        return bestMove.get();
    }

    private Cell checkMoves(Set<Cell> l) {
        List<Cell> winL = l.stream().parallel().filter(cell -> {
            Board boardCopy = new Board(board);
            boardCopy.move(new Cell(cell.r(), cell.c(), aiPlayer));
            return boardCopy.win() == aiPlayer;
        }).toList();

        return winL.isEmpty() ? null : winL.get(0);
    }

    private float max_value(Board board, Set<Cell> moves, float alpha, float beta, int numberOfMoves) {
        if(numberOfMoves <= 0) return Float.NEGATIVE_INFINITY;
        else {
            for(Cell move : moves) {
                Board boardCopy = new Board(board);
                boardCopy.move(new Cell(move.r(), move.c(), aiPlayer));

                if(boardCopy.win() == aiPlayer) return Float.POSITIVE_INFINITY;

                Set<Cell> newList = new HashSet<>(moves);
                newList.remove(move);
                newList.addAll(boardCopy.getAdjacentsPlayer(move, 0));
                alpha = Math.max(alpha, min_value(boardCopy, newList, alpha, beta, numberOfMoves));

                if(beta <= alpha) return beta;
            }
            return alpha;
        }
    }

    private float min_value(Board board, Set<Cell> moves, float alpha, float beta, int numberOfMoves) {
        for (Cell move : moves) {
            Board boardCopy = new Board(board);
            boardCopy.move(new Cell(move.r(), move.c(), aiPlayer == 1 ? -1 : 1));

            if (boardCopy.win() == (aiPlayer == 1 ? -1 : 1)) return Float.NEGATIVE_INFINITY;

            Set<Cell> newList = new HashSet<>(moves);
            newList.remove(move);
            newList.addAll(boardCopy.getAdjacentsPlayer(move, 0));
            beta = Math.min(beta, max_value(boardCopy, newList, alpha, beta, numberOfMoves - 1));

            if (beta < bestAlpha.get() || beta <= alpha) return alpha;
        }
        return beta;
    }
}