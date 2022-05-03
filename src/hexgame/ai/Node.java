package hexgame.ai;

import hexgame.Cell;

import java.util.ArrayList;

public class Node {
    private double inf = Double.longBitsToDouble(0x7ff0000000000000L);
    private Cell move;
    private Node parent;
    private int n;
    private int q;
    private int qRave;
    private int nRave;
    private ArrayList<Node> children;

    public Node(Cell move, Node parent) {
        this.move = move;
        this.parent = parent;
        this.n = 0;
        this.q = 0;
        this.nRave = 0;
        this.qRave = 0;
        this.children = new ArrayList<>();
    }

    public void addChildren(ArrayList<Node> children) {
        this.children.addAll(children);
    }

    public double value(int explore) {
        if (this.n == 0) {
            if (explore == 0) return 0;
            else return this.inf;

        } else {
            return (this.q / this.n + explore * Math.sqrt(2 * Math.log(this.parent.n) / this.n));
        }
    }

}
