package hexgame.ai;

import java.util.HashSet;

public class EvaluationNode {
    HashSet<EvaluationNode> redNeighbours;
    HashSet<EvaluationNode> blueNeighbours;
    int row;
    int column;


    EvaluationNode(int row, int column) {
        this.row = row;
        this.column = column;
        redNeighbours = new HashSet<>();
        blueNeighbours = new HashSet<>();
    }

    public static void buildEvaluationBoard(int[][] pieces, EvaluationNode[][] nodesArray) {
        // Initially creates all the EvaluationNodes without their neighbours
        for (int i = 0; i < nodesArray.length; i++)
            for (int j = 0; j < nodesArray.length; j++)
                nodesArray[i][j] = new EvaluationNode(i, j);

        // Builds the neighbours of each EvaluationNode
        for (int i = 0; i < nodesArray.length; i++)
            for (int j = 0; j < nodesArray.length; j++) {
                if (pieces[i][j] != 0)
                    continue;
                nodesArray[i][j].redNeighbours = nodesArray[i][j].getNeighbours(1, new HashSet<>(), nodesArray, pieces);
                nodesArray[i][j].redNeighbours.remove(nodesArray[i][j]);
                nodesArray[i][j].blueNeighbours = nodesArray[i][j].getNeighbours(2, new HashSet<EvaluationNode>(), nodesArray, pieces);
                nodesArray[i][j].blueNeighbours.remove(nodesArray[i][j]);
            }
    }


    private HashSet<EvaluationNode> getNeighbours(int color, HashSet<EvaluationNode> piecesVisited, EvaluationNode[][] nodesArray, int[][] pieces) {
        // If the current piece has been visited already,
        // returns an empty HashSet
        if (piecesVisited.contains(this))
            return new HashSet<>();
        HashSet<EvaluationNode> returnValue = new HashSet<>();
        if (pieces[row][column] == color)
            piecesVisited.add(this);

        // Considers all the neighbours of the current piece.
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (a + b == 0)
                    continue;
                //    O--O--O
                //    | /| /|
                //    |/ |/ |
                //    O--O--O
                //    | /| /|
                //    |/ |/ |
                //    O--O--O
                if (row + a < 0 || row + a == nodesArray.length || column + b < 0 || column + b == nodesArray.length)
                    continue;

                // If the current neighbour is empty,
                // adds it to the neighbours list.
                if (pieces[row + a][column + b] == 0)
                    returnValue.add(nodesArray[row + a][column + b]);
                    // If the current neighbour is a piece of
                    // the same colour,
                    // adds all of its neighbours to the neighbours list.
                else if (pieces[row + a][column + b] == color) {
                    returnValue.addAll(nodesArray[row + a][column + b].getNeighbours(color, piecesVisited, nodesArray, pieces));
                }
                // If the current neighbour is a piece of
                // the opposing colour, ignores it.


            }
        }
        return returnValue;
    }

    public int hashCode() {
        return row * 100 + column;
    }

    public boolean equals(Object other) {
        EvaluationNode otherNode = (EvaluationNode) other;
        return row == otherNode.row && column == otherNode.column;
    }
}
