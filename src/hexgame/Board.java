package hexgame;

import java.util.ArrayList;

/**
 * Défini le plateau de jeu
 */
public class Board {
    private static final int SIZE = 11; // Taille du plateau
    public int[][] board; // Plateau -1 pour bleu 1 pour rouge et 0 pour une case vide
    public int numberOfMoves; // Nombre de mouvements faits
    public Cell[][] cellBoard = new Cell[SIZE][SIZE]; // Plateau rempli de cellules
    public Cell lastMove;
    public Cell lastMoveTournament;


    /**
     * Initialisation de la classe Board
     * @param board tableau
     * @param numberOfMoves nombre de mouvements
     */
    public Board(int[][] board, int numberOfMoves) {
        this.board = board;
        this.numberOfMoves = numberOfMoves;
        for (int c = 0; c < SIZE; c++) {
            for (int r = 0; r < SIZE; r++) {
                cellBoard[r][c] = new Cell(r, c);
            }
        }
    }

    /**
     * Initialisation de la classe Board
     */
    public Board() {
        this(new int[SIZE][SIZE], 0);
    }

    /**
     * @return la taille du plateau
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Effectue un tour et remplis board et cellBoard
     * @param cell la cellule jouée
     */
    public void move(Cell cell) {
        if (cell != null) {
            numberOfMoves++;
            board[cell.r()][cell.c()] = cell.player();
            cellBoard[cell.r()][cell.c()] = cell;
            lastMove = cell;
            lastMoveTournament = cell;
        }
    }

    /**
     * Effectue le coup SWAP
     */
    public void swap() {
        numberOfMoves++;
        board[lastMove.r()][lastMove.c()] = 0;
        cellBoard[lastMove.r()][lastMove.c()] = new Cell(lastMove.r(), lastMove.c());
        int dif = lastMove.r() + lastMove.c() - 10;
        board[lastMove.r() - dif][lastMove.c() - dif] = 1;
        cellBoard[lastMove.r() - dif][lastMove.c() - dif] = new Cell(lastMove.r() - dif, lastMove.c() - dif, 1);
        lastMove = new Cell(lastMove.r() - dif, lastMove.c() - dif, 1);
        lastMoveTournament = new Cell(-1, -1, 1);
    }

    /**
     * Donne une liste de cellules adjacentes a la cellule indiquée
     * @param cell la cellule initiale
     * @return la liste des cellules adjacentes
     */
    public ArrayList<Cell> getAdjacents(Cell cell) {
        ArrayList<Cell> result =  new ArrayList<>();
        if (cell.r() - 1 >= 0) result.add(cellBoard[cell.r() - 1][cell.c()]);
        if (cell.c() - 1 >= 0) result.add(cellBoard[cell.r()][cell.c() - 1]);
        if (cell.r() - 1 >= 0 && cell.c() - 1 >= 0) result.add(cellBoard[cell.r() - 1][cell.c() - 1]);
        if (cell.r() + 1 < 11) result.add(cellBoard[cell.r() + 1][cell.c()]);
        if (cell.c() + 1 < 11) result.add(cellBoard[cell.r()][cell.c() + 1]);
        if (cell.r() + 1 < 11 && cell.c() + 1 < 11) result.add(cellBoard[cell.r() + 1][cell.c() + 1]);

        return result;
    }

    /**
     * Vérifie si une personne a gagné.
     * @return le numéro de la personne gagnante
     */
    public int win() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][0] == -1 && board[j][SIZE - 1] == -1 && path(cellBoard[i][0], cellBoard[j][SIZE - 1], new ArrayList<>())) return -1;
                if (board[0][i] == 1 && board[SIZE - 1][j] == 1 && path(cellBoard[0][i], cellBoard[SIZE - 1][j], new ArrayList<>())) return 1;
            }
        }
        return 0;
    }

    /**
     * Vérifie s'il existe un chemin entre deux cellules
     * @param start la cellule de début
     * @param end la cellule de fin
     * @param visited la liste des cellules déjà visitées pour l'appel récursif
     * @return vrai ou faux si le chemin est possible
     */
    public boolean path(Cell start, Cell end, ArrayList<Cell> visited) {
        if(start.equals(end)) return true;
        if(start.player() != end.player()) return false;

        visited.add(start);
        for(Cell c : getAdjacents(start)) {
            if(!visited.contains(c)) {
                if(path(c, end, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
}
