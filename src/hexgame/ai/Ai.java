package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;

import java.util.Random;

/**
 * Intelligence artificielle utilisant MCTS
 * inspiré de <a href="https://github.com/masouduut94/MCTS-agent-cythonized">ce projet</a>
 */
public class Ai {
    private int EXPLORATION = 1;
    private final Board board;
    private final int boardSize;
    private Node root;
    private int runTime;
    private int nodeCount;
    private int numRollouts;

    public Ai(Board board, int boardSize) {
        this.board = board;
        this.boardSize = boardSize;
        this.root = new Node(null, null);
        this.runTime = 0;
        this.nodeCount = 0;
        this.numRollouts = 0;
    }

    void search() {
        this.numRollouts = 0;
        while (numRollouts < 5000) {

        }
    }

    private void selectNode() {
        Node node = this.root;

    }

    public Cell getBestMove(int player) {
        Random random = new Random();

        return new Cell(random.nextInt(11), random.nextInt(11), player);
    }
}
