package hexgame;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;


public class Board {
    private static final int size = 11;
    public final int[][] board;
    public int numberOfMoves;

    public Board(int[][] board, int numberOfMoves) {
        this.board = board;
        this.numberOfMoves = numberOfMoves;
    }

    public Board() {
        this(new int[size][size], 0);
    }

    public Board(Board board) {
        this.board = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board.board[i], 0, this.board[i], 0, size);
        }
        this.numberOfMoves = board.numberOfMoves;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board1 = (Board) o;

        if (numberOfMoves != board1.numberOfMoves) return false;
        return Arrays.deepEquals(board, board1.board);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(board);
        result = 31 * result + numberOfMoves;
        return result;
    }

    public int get(@NotNull Cell cell) {
        return board[cell.getR()][cell.getC()];
    }

    public void move(@NotNull Move move, int player) {
        numberOfMoves++;
        if (move.isSwap()) {
            int [][] temp = new int[size][size];
            for (int i = 0; i < size; i++) System.arraycopy(board[i], 0, temp[i], 0, size);
            for (int i = 0; i < size; i++) for (int j = 0; j < size; j++) {
                if (temp[j][i] == 0) board[i][j] = 0;
                else board[i][j] = 3 - temp[j][i];
            }
        }
        else if (move.getNewCell() != null) {
            board[move.getNewCell().getR()][move.getNewCell().getC()] = player;
        }
    }

    public void undo(@NotNull Cell cell) {
        int row = cell.getR();
        int col = cell.getC();
        if (board[row][col] == 1 || board[row][col] == 2) {
            board[row][col] = 0;
        }
    }

    public boolean isSwapAvailable() {
        return numberOfMoves == 1;
    }

    public ArrayList<Cell> getAdjacents(@NotNull Cell cell) {
        ArrayList<Cell> result = new ArrayList<>();
        for (int r = cell.getR() - 1; r <= cell.getR() + 1; r++) if (r >= 0 && r < size) {
            int min = (r <= cell.getR() ?- 1:0);
            int max = (r <= cell.getR() ?+ 1:0);
            for (int c = cell.getC() + min; c <= cell.getC() + max; c++) if (c >= 0 && c < size) if (!new Cell(r, c).equals(cell)) {
                result.add(new Cell(r, c));
            }
        }
        return result;
    }

    public int win() {
        boolean[][] mark = new boolean[size][size];
        for (int i = 0; i< size; i++){
            if (!mark[0][i] && board[0][i] == 1) {
                dfs(new Cell(0, i), mark, 1);
            }
        }
        for (int i = 0; i < size; i++) {
            if (mark[size - 1][i]) return 1;
        }

        mark = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            if(!mark[i][0] && board[i][0] == 2) {
                dfs(new Cell(i, 0), mark, 2);
            }
        }
        for (int i = 0; i < size; i++) {
            if (mark[i][size - 1]) return 2;
        }
        return 0;
    }

    /*
    https://www.redblobgames.com/grids/hexagons/#range-obstacles
    function hex_reachable(start, movement):
    var visited = set() # set of hexes
    add start to visited
    var fringes = [] # array of arrays of hexes
    fringes.append([start])

    for each 1 < k ≤ movement:
        fringes.append([])
        for each hex in fringes[k-1]:
            for each 0 ≤ dir < 6:
                var neighbor = hex_neighbor(hex, dir)
                if neighbor not in visited and not blocked:
                    add neighbor to visited
                    fringes[k].append(neighbor)

    return visited
     */

    private void dfs(Cell cell, boolean[][] mark, int player) {
        mark[cell.getR()][cell.getC()] = true;
        ArrayList<Cell> cells = getAdjacents(cell);
        for (Cell c:cells) if (!mark[c.getR()][c.getC()] && get(c) == player)
            dfs(c, mark, player);
    }
}
