package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;

import java.math.BigInteger;
import java.util.*;

public class AiObject {
    private final int RED = 1, BLUE = -1, EMPTY = 0;
    private final int SIZE = 11;
    private final Board board;
    private final int[][] boardCopy;
    private final int aiPlayer;
    private final HashMap<BigInteger, Integer> lookUpTable;
    private EvaluationNode[][] nodes;

    public AiObject(Board board, int player) {
        this.board = board;
        this.aiPlayer = player;
        this.boardCopy = new int[SIZE + 2][SIZE + 2];
        for (int i = 1; i < boardCopy.length - 1; i++) {
            for (int j = 1; j < boardCopy.length - 1; j++) {
                boardCopy[i][j] = EMPTY;
            }
        }
        for (int i = 1; i < boardCopy.length - 1; i++) {
            boardCopy[i][0] = RED;
            boardCopy[0][i] = BLUE;
            boardCopy[i][boardCopy.length - 1] = RED;
            boardCopy[boardCopy.length -1 ][i] = BLUE;
        }
        lookUpTable = new HashMap<>();
    }

    public Cell getMove() {
        int numberOfMoves = board.numberOfMoves;
        if (numberOfMoves == 0) {
            boardCopy[boardCopy.length / 2][boardCopy.length / 2] = aiPlayer;
            return new Cell(boardCopy.length / 2 - 1, boardCopy.length / 2 - 1, aiPlayer);
        } else if (numberOfMoves == 1) {
            return new Cell(-1, -1, aiPlayer);
        } else {
            Cell lastMove = new Cell(SIZE - 1 - board.lastMove.c(), board.lastMove.r(), board.lastMove.player());
            boardCopy[lastMove.r() + 1][lastMove.c() + 1] = lastMove.player();
            Cell bestMove = getBestMove();
            boardCopy[bestMove.r()][bestMove.c()] = aiPlayer;
            return new Cell(bestMove.c() - 1, SIZE - 1 - bestMove.r() - 1, aiPlayer);
        }
    }

    private Cell getBestMove() {
        int bestValue = aiPlayer == RED ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestRow = -1;
        int bestColumn = -1;

        // Essaye tous les coups possibles et évalue s'il est bien ou pas
        for (int i = 1; i < boardCopy.length - 1; i++) {
            for (int j = 1; j < boardCopy.length - 1; j++) {
                if (boardCopy[i][j] != 0) continue;

                // Trouve une évaluation du coup avec un arbre
                boardCopy[i][j] = aiPlayer;
                int value = expand(1, bestValue, aiPlayer == RED ? BLUE : RED);
                boardCopy[i][j] = EMPTY;

                // Compare le dernier coup au meilleur coup et prends le meilleur des deux
                if (aiPlayer == RED && value > bestValue) {
                    bestValue = value;
                    bestRow = i;
                    bestColumn = j;
                } else if (aiPlayer == BLUE && value < bestValue) {
                    bestValue = value;
                    bestRow = i;
                    bestColumn = j;
                }
            }
        }
        return new Cell(bestRow, bestColumn, aiPlayer);
    }

    private int expand(int depth, int previousBest, int currentColor) {
        // On évalue la branche avec une evaluation de plateau au lieu de l'agrandir
        // si la profondeur est le maximum
        int MAX_DEPTH = 2;
        if (depth == MAX_DEPTH) return evaluate();
        int bestValue = currentColor == RED ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Prend tous les coups possibles à faire
        Iterator<Cell> iterator = getMoves().iterator();

        int BEAM_SIZE = 5;
        for (int i = 0; i < BEAM_SIZE && iterator.hasNext(); i++) {
            Cell nexMove = iterator.next();
            boardCopy[nexMove.r()][nexMove.c()] = currentColor;
            int value = expand(depth + 1, bestValue, currentColor == RED ? BLUE : RED);
            boardCopy[nexMove.r()][nexMove.c()] = EMPTY;

            // Compare le dernier coup avec le meilleur coup
            if (currentColor == RED && value > bestValue) bestValue = value;
            else if (currentColor == BLUE && value < bestValue) bestValue = value;

            // Si le coup actuel fait toute la branche alors on stoppe
            if (currentColor == RED && bestValue > previousBest ||
                    currentColor == BLUE && bestValue < previousBest)
                return bestValue;
        }

        // Si aucuns coups sont possibles à cette profondeur, on renvoie l'évaluation du plateau
        if (bestValue == Integer.MAX_VALUE || bestValue == Integer.MIN_VALUE) bestValue = evaluate();
        return bestValue;
    }

    private void calculateAB(int[][] redA, int[][] redB, int[][] blueA, int[][] blueB) {
        boolean found = true;
        while (found) {
            found = false;
            for (int j = 1; j < redA.length - 1; j++) {
                found = isFound(redA, found, j);
            }
        }

        found = true;
        while (found) {
            found = false;
            for (int j = redB.length - 2; j > 0; j--) {
                found = isFound(redB, found, j);
            }
        }

        found = true;
        while (found) {
            found = false;
            for (int i = 1; i < blueA.length - 1; i++) {
                for (int j = 1; j < blueA.length - 1; j++) {
                    found = isFound(blueA, found, i, j);
                }
            }
        }

        found = true;
        while (found) {
            found = false;
            for (int i = 1; i < blueB.length - 1; i++) {
                for (int j = blueB.length - 2; j > 0; j--) {
                    found = isFound(blueB, found, i, j);
                }
            }
        }
    }

    private boolean isFound(int[][] blueB, boolean found, int i, int j) {
        if (blueB[i][j] != 100000)
            return found;
        if (boardCopy[i][j] != 0)
            return found;

        int min = 100000;
        int secondMin = 100000;

        for (EvaluationNode next : nodes[i][j].blueNeighbours) {
            int number = blueB[next.row][next.column];
            if (number < secondMin) {
                secondMin = number;
                if (number < min) {
                    secondMin = min;
                    min = number;
                }
            }
        }
        if (secondMin < 100) {
            if (blueB[i][j] != secondMin + 1) {
                found = true;
                blueB[i][j] = secondMin + 1;
            }
        }
        return found;
    }

    private boolean isFound(int[][] redA, boolean found, int j) {
        for (int i = 1; i < redA.length - 1; i++) {
            if (redA[i][j] != 100000)
                continue;
            if (boardCopy[i][j] != 0)
                continue;

            int min = 100000;
            int secondMin = 100000;

            for (EvaluationNode next : nodes[i][j].redNeighbours) {
                int number = redA[next.row][next.column];
                if (number < secondMin) {
                    secondMin = number;
                    if (number < min) {
                        secondMin = min;
                        min = number;
                    }
                }
            }
            if (secondMin < 100) {
                if (redA[i][j] != secondMin + 1) {
                    found = true;
                    redA[i][j] = secondMin + 1;
                }
            }
        }
        return found;
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Cell> getMoves() {
        nodes = new EvaluationNode[boardCopy.length][boardCopy.length];
        EvaluationNode.buildEvaluationBoard(boardCopy, nodes);

        int[][] redA =  new int[boardCopy.length][boardCopy.length];
        int[][] redB =  new int[boardCopy.length][boardCopy.length];
        int[][] blueA =  new int[boardCopy.length][boardCopy.length];
        int[][] blueB =  new int[boardCopy.length][boardCopy.length];
        for (int i = 0; i < boardCopy.length; i++) {
            for (int j = 0; j < boardCopy.length; j++) {
                redA[i][j] = redB[i][j] = blueA[i][j] = blueB[i][j] = 100000;
            }
        }

        redA[0][0] = redA[redA.length - 1][0] = redB[0][redB.length - 1] = redB[redB.length - 1][redB.length - 1] = 0;
        blueA[0][0] = blueA[0][blueA.length - 1] = blueB[blueB.length - 1][0] = blueB[blueB.length -1][blueB.length - 1] = 0;

        calculateAB(redA, redB, blueA, blueB);
        ArrayList<Cell> cells = new ArrayList<>();

        for (int i = 1; i < boardCopy.length - 1; i++) {
            for (int j = 1; j < boardCopy.length - 1; j++) {
                if (boardCopy[i][j] != 0) continue;
                cells.add(new Cell(i, j, redA[i][j] + redB[i][j] + blueA[i][j] + blueB[i][j]));
            }
        }
        Collections.sort(cells);
        return cells;
    }

    /**
     * Evalue le plateau
     * @return la valeur du plateau
     */
    private int evaluate() {
        // Vérifie si le plateau a déjà été évalué
        BigInteger cellsString = cellsString();
        Integer cellsValue = lookUpTable.get(cellsString);
        if (cellsValue != null) return cellsValue;

        nodes = new EvaluationNode[boardCopy.length][boardCopy.length];
        EvaluationNode.buildEvaluationBoard(boardCopy, nodes);

        int[][] redA = new int[boardCopy.length][boardCopy.length];
        int[][] redB = new int[boardCopy.length][boardCopy.length];
        int[][] blueA = new int[boardCopy.length][boardCopy.length];
        int[][] blueB = new int[boardCopy.length][boardCopy.length];
        for (int i = 0; i < boardCopy.length; i++) {
            for (int j = 0; j < boardCopy.length; j++) {
                redA[i][j] = redB[i][j] = blueA[i][j] = blueB[i][j] = 100000;
            }
        }

        redA[0][0] = redA[redA.length - 1][0] = redB[0][redB.length - 1] = redB[redB.length - 1][redB.length - 1] = 0;
        blueA[0][0] = blueA[0][blueA.length - 1] = blueB[blueB.length - 1][0] = blueB[blueB.length - 1][blueB.length - 1] = 0;

        calculateAB(redA, redB, blueA, blueB);

        int redPotential = 100000;
        int bluePotential = 100000;
        int redMobility = 0;
        int blueMobility = 0;
        for (int i = 1; i < redA.length - 1; i++) {
            for (int j = 1; j < redA.length - 1; j++) {
                if (boardCopy[i][j] == 0) {
                    int temp = redA[i][j] + redB[i][j];
                    if (temp < redPotential) {
                        redPotential = temp;
                        redMobility = 1;
                    } else if (temp == redPotential)
                        redMobility++;
                    temp = blueA[i][j] + blueB[i][j];
                    if (temp < bluePotential) {
                        bluePotential = temp;
                        blueMobility = 1;
                    } else if (temp == bluePotential)
                        blueMobility++;
                }
            }
        }

        int result = 100 * (bluePotential - redPotential) - (blueMobility - redMobility);
        lookUpTable.put(cellsString, result);

        return result;
    }

    /**
     * Créé un BigInteger qui représente le plateau
     * @return un BigInteger
     */
    private BigInteger cellsString() {
        BigInteger value = new BigInteger(Integer.toString(boardCopy.length - 2));
        for (int i = 1; i < boardCopy.length - 1; i++) {
            for (int j = 1; j < boardCopy.length - 1; j++) {
                value = value.multiply(new BigInteger("3"));
                value = value.add(new BigInteger(Integer.toString(boardCopy[i][j])));
            }
        }
        return value;
    }
}
