package hexgame;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static final int size = 11;
    public int[][] board;
    public int numberOfMoves;

    public Board(int[][] board, int numberOfMoves) {
        this.board = board;
        this.numberOfMoves = numberOfMoves;
    }

    public Board() {
        this(new int[size][size], 0);
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

    public void move(Cell cell, int player) {
        numberOfMoves++;
        if (cell != null) {
            board[cell.getR()][cell.getC()] = player;
        }
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
}
