package hexgame.ai;

import hexgame.Board;
import java.util.Arrays;

public class Ai {

    private Board board;
    private final int boardSize;

    public Ai(Board board, int boardSize) {
        this.board = board;
        this.boardSize = boardSize;
    }

    /*
    public int[] GetBestMove(int theCol, int MoveCount) {
        int i, j, i_b, j_b, ff = 0, i_q = 0, j_q = 0, cc, pp0, pp1, mm;
        int[] vv = new int[(boardSize - 1) * boardSize + boardSize];
        // int[][][] Bridge = new int[boardSize][][];
        double mmp;

        if (MoveCount > 0) ff = 190 / (MoveCount * MoveCount);
        mm = 20000;
        for (i = 0; i < boardSize; i++) {
            for (j = 0; j < boardSize; j++) {
                if (board.board[i][j] != 0) {
                    i_q += 2 * i + 1 - boardSize;
                    j_q += 2 * j + 1 - boardSize;
                }
            }
        }
        i_q = sign(i_q);
        j_q = sign(j_q);

        for (i = 0; i < boardSize; i++) {
            for (j = 0; j < boardSize; j++) {
                if (board.board[i][j] == 0) {
                    mmp = Math.random() * (49 - 3 * 16);
                    mmp += (Math.abs(i - 5) + Math.abs(j - 5) ) * ff;
                    mmp += 8 * (i_q * (i - 5) + j_q * (j - 5)) / (MoveCount + 1);
                    // for (int kk = 0; kk < 4; kk++) mmp -= Bridge[i][j][kk];
                    pp0 = Pot[i][j][0] + Pot[i][j][1];
                    pp1 = Pot[i][j][2] + Pot[i][j][3];
                    mmp += pp0 + pp1;
                    if ((pp0 <= 268) || (pp1 <= 268)) mmp -= 400; //140+128
                    vv[i * boardSize + j] = mmp;
                    if (mmp < mm) {
                        mm = mmp;
                        i_b = i;
                        j_b = j;
                    }
                }
            }
        }
        mm += 108;
        for (i = 0; i < boardSize; i++) {
            for (j = 0; j < boardSize; j++) {
                if (vv[i * boardSize + j] < mm) {
                    if (theCol < 0) { //red
                        if ((i > 3) && (i < boardSize - 1) && (j > 0) && (j < 3)) {
                            if (board.board[i - 1][j + 2] == -theCol) {
                                cc = CanConnectFarBorder(i - 1, j + 2, -theCol);
                                if (cc < 2) {
                                    i_b = i;
                                    if (cc < -1) { i_b--; cc++; }
                                    j_b = j - cc;
                                    mm = vv[i * boardSize + j];
                                }
                            }
                        }
                        if ((i > 0) && (i < boardSize - 1) && (j == 0)) {
                            if ((board.board[i - 1][j + 2] == -theCol) && (board.board[i - 1][j] == 0) && (board.board[i - 1][j + 1] == 0) && (board.board[i][j + 1] == 0) && (board.board[i + 1][j] == 0))
                            { i_b = i; j_b = j; mm = vv[i * boardSize + j]; }
                        }
                        if ((i > 0) && (i < boardSize - 4) && (j < boardSize - 1) && (j > boardSize - 4)) {
                            if (board.board[i + 1][j - 2] == -theCol) {
                                cc = CanConnectFarBorder(i + 1, j - 2, -theCol);
                                if (cc < 2) {
                                    i_b = i;
                                    if (cc < -1) {
                                        i_b++; cc++;
                                    }
                                    j_b = j + cc;
                                    mm = vv[i * boardSize + j];
                                }
                            }
                        }
                        if ((i > 0) && (i < boardSize - 1) && (j == boardSize - 1)) {
                            if ((board.board[i + 1][j - 2] == -theCol) && (board.board[i + 1][j] == 0) && (board.board[i + 1][j - 1] == 0) && (board.board[i][j - 1] == 0) && (board.board[i - 1][j] == 0)) {
                                i_b = i;
                                j_b = j;
                                mm = vv[i * boardSize + j];
                            }
                        }
                    } else {
                        if ((j > 3) && (j < boardSize - 1) && (i > 0) && (i < 3)) {
                            if (board.board[i + 2][j - 1] == -theCol) {
                                cc = CanConnectFarBorder(i + 2, j - 1, -theCol);
                                if (cc < 2) {
                                    j_b = j;
                                    if (cc < -1) { j_b--; cc++; }
                                    i_b = i - cc;
                                    mm = vv[i * boardSize + j];
                                }
                            }
                        }
                        if ((j > 0) && (j < boardSize - 1) && (i == 0)) {
                            if ((board.board[i + 2][j - 1] == -theCol) && (board.board[i][j - 1] == 0) && (board.board[i + 1][j - 1] == 0) && (board.board[i + 1][j] == 0) && (board.board[i][j + 1] == 0)) {
                                i_b = i;
                                j_b = j;
                                mm = vv[j];
                            }
                        }
                        if ((j > 0) && (j < boardSize - 4) && (i < boardSize - 1) && (i > boardSize - 4)) {
                            if (board.board[i - 2][j + 1] == -theCol) {
                                cc = CanConnectFarBorder(i - 2, j + 1, -theCol);
                                if (cc < 2) {
                                    j_b = j;
                                    if (cc < -1) { j_b++; cc++; }
                                    i_b = i + cc;
                                    mm = vv[i * boardSize + j];
                                }
                            }
                        }
                        if ((j > 0) && (j < boardSize - 1) && (i == boardSize - 1)) {
                            if ((board.board[i - 2][j + 1] == -theCol) && (board.board[i][j + 1] == 0) && (board.board[i - 1][j + 1] == 0) && (board.board[i - 1][j] == 0) && (board.board[i][j - 1] == 0)) {
                                i_b = i;
                                j_b = j;
                                mm = vv[i * boardSize + j];
                            }
                        }
                    }
                }
            }
        }
        return new int[] {i_b, j_b};

        MakeMove(i_b, j_b, false);
        IsRunning = false;
        if (theCol < 0) {
            if ((Pot[i_b][j_b][2] > 140) || (Pot[i_b][j_b][3] > 140)) { WritePot(false); return; }
            window.document.OptionsForm.Msg.value=" Red has won !";
            Blink(-2);
        } else {
            if ((Pot[i_b][j_b][0] > 140) || (Pot[i_b][j_b][1] > 140)) { WritePot(false); return; }
            window.document.OptionsForm.Msg.value=" Blue has won !";
            Blink(-2);
        }
        IsOver=true;
    }
    */

    private int sign(int x) {
        return Integer.compare(x, 0);
    }
}
