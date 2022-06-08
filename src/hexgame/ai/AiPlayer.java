package hexgame.ai;

import hexgame.Board;
import hexgame.Cell;

import java.util.ArrayList;
import java.util.Random;

public class AiPlayer {
    private final Board board;
    private final int aiPlayer;
    private Cell lastPlayed = null;
    private Cell next = null;
    private int sens = 1;
    private final ArrayList<Cell> played;
    private final ArrayList<Cell> toPlay;
    private final ArrayList<Cell> last;

    public AiPlayer(Board board, int aiPlayer) {
        this.board = board;
        this.aiPlayer = aiPlayer;
        played = new ArrayList<>();
        toPlay = new ArrayList<>();
        last = new ArrayList<>();
    }

    public Cell getBestMove() {
        Cell move;
        Cell lastReceived = board.lastMoveTournament;
        if (board.numberOfMoves == 0) {
            move = new Cell(4, 4, aiPlayer);
            lastPlayed = move;
            played.add(move);
            last.add(move);
            return move;
        } else if (board.numberOfMoves == 1) {
            if (board.board[4][4] == 0) {
                move = new Cell(4, 4, aiPlayer);
                lastPlayed = move;
                played.add(move);
                last.add(move);
                return move;
            } else {
                lastPlayed = new Cell(6, 6, aiPlayer);
                played.add(lastPlayed);
                last.add(lastPlayed);
                return new Cell(-1, -1, aiPlayer);
            }
        } else if (board.numberOfMoves == 2 && lastReceived.r() == -1 && lastReceived.c() == -1) {
            if (board.board[4][4] == 0) move = new Cell(4, 4, aiPlayer);
            else move = new Cell(6, 6, aiPlayer);
            played.remove(lastPlayed);
            lastPlayed = move;
            last.add(move);
            played.add(move);
            return move;
        } else {
            if (next != null && board.board[next.r()][next.c()] == 0) {
                move = next;
                next = null;
                return move;
            } else if (next != null && board.board[next.r()][next.c()] == (aiPlayer == 1 ? -1 : 1)) {
                toPlay.add(played.get(played.size() - 2));
            }

            move = checkLastPlayed();
            if (move != null) {
                played.add(move);
                return move;
            }

            if (isReady()) {
                for (int i = 0; i < toPlay.size(); i++) {
                    move = toPlay.get(i);
                    if (board.board[move.r()][move.c()] == 0) {
                        toPlay.remove(move);
                        lastPlayed = move;
                        played.add(move);
                        return move;
                    }
                }
            }

            Cell temp;
            if (last.size() > 1) temp = last.get(last.size() - 2);
            else temp = last.get(0);

            move = getMostValuedMove(temp, sens);
            if (move != null) toPlay.add(move);

            if (!isReady()) {
                if (aiPlayer == 1 && sens == 1) move = getMostValuedMove(new Cell(temp.r() + 1, temp.c(), aiPlayer), sens);
                else if (aiPlayer == 1 && sens == -1) move = getMostValuedMove(new Cell(temp.r() - 1, temp.c(), aiPlayer), sens);
                else if (aiPlayer == -1 && sens == 1) move = getMostValuedMove(new Cell(temp.r(), temp.c() + 1, aiPlayer), sens);
                else move = getMostValuedMove(new Cell(temp.r(), temp.c() - 1, aiPlayer), sens);
            } else move = null;
            if (move != null) {
                sens = sens == 1 ? -1 : 1;
                lastPlayed = move;
                played.add(lastPlayed);
                last.add(lastPlayed);
                return move;
            }
        }
        for (int i = 0; i < played.size(); i++) {
            move = getMostValuedMove(played.get(played.size() - i - 1), sens);
            if (move != null) {
                lastPlayed = move;
                played.add(lastPlayed);
                last.add(lastPlayed);
                return move;
            }
        }
        return getCellRdm();
    }
    private boolean isReady() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++)
                if (aiPlayer == 1) {
                    if (board.board[0][i] == 1 && board.board[10][j] == 1) return true;
                } else {
                    if (board.board[i][0] == -1 && board.board[j][10] == -1) return true;
                }
        }
        return false;
    }

    private Cell checkLastPlayed() {
        if (toPlay.size() > 0) {
            for (Cell cell : toPlay) {
                ArrayList<Cell> adj = board.getAdjacents(cell);
                adj.removeIf(c -> c.player() == aiPlayer);
                if (board.board[cell.r()][cell.c()] == (aiPlayer == 1 ? -1 : 1)) {
                    toPlay.remove(cell);
                    if (aiPlayer == 1) {
                        adj.removeIf(c -> c.r() == cell.r() + 1);
                        adj.removeIf(c -> c.r() == cell.r() - 1);
                        for (Cell c : adj) {
                            if (c.player() == 0 && c.r() == cell.r() && board.board[c.r() - 1][c.c()] == 0) {
                                next = new Cell(c.r() - 1, c.c(), aiPlayer);
                                return new Cell(c.r(), c.c(), aiPlayer);
                            }
                        }
                    } else {
                        adj.removeIf(c -> c.c() == cell.c() + 1);
                        adj.removeIf(c -> c.c() == cell.c() - 1);
                        for (Cell c : adj) {
                            if (c.player() == 0 && c.c() == cell.c() && board.board[c.r()][c.c() - 1] == 0) {
                                next = new Cell(c.r(), c.c() - 1, aiPlayer);
                                return new Cell(c.r(), c.c(), aiPlayer);
                            }
                        }
                    }
                } else if (board.board[cell.r()][cell.c()] == 0) {
                    if (aiPlayer == 1) {
                        for (Cell c : adj) {
                            if (c.player() == -1) {
                                toPlay.remove(cell);
                                return cell;
                            }
                        }
                    } else {
                        for (Cell c : adj) {
                            if (c.player() == 1) {
                                toPlay.remove(cell);
                                return cell;
                            }
                        }

                    }
                }
            }
        }
        return null;
    }

    private Cell getMostValuedMove(Cell c, int sens) {
        Cell move = null;
        if (c != null) {
            ArrayList<Cell> adjacents = board.getAdjacents(c);
            if (aiPlayer == -1) {
                int max = c.c();
                if (sens == 1) adjacents.removeIf(cell -> cell.c() + 1 == c.c());
                else adjacents.removeIf(cell -> cell.c() - 1 == c.c());
                for (Cell adjacent : adjacents) {
                    if (sens == 1) {
                        if (adjacent.player() == 0 && adjacent.c() > max) {
                            max = adjacent.c();
                            move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                        } else if (adjacent.player() == 0 && adjacent.c() == max && move == null) {
                            move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                        }
                    } else {
                        if (adjacent.player() == 0 && adjacent.c() < max) {
                            max = adjacent.c();
                            move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                        } else if (adjacent.player() == 0 && adjacent.c() == max && move == null) {
                            move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                        }
                    }

                }
            } else {
                int max = c.r();
                if (sens == 1) adjacents.removeIf(cell -> cell.r() + 1 == c.r());
                else adjacents.removeIf(cell -> cell.r() - 1 == c.r());
                for (Cell adjacent : adjacents) {
                    if (sens == 1) {
                        if (adjacent.player() == 0 && adjacent.r() > max) {
                            max = adjacent.r();
                            move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                        } else if (adjacent.player() == 0 && adjacent.r() == max && move == null) {
                            move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                        }
                    } else {
                        if (adjacent.player() == 0 && adjacent.r() < max) {
                            max = adjacent.r();
                            move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                        } else if (adjacent.player() == 0 && adjacent.r() == max && move == null) {
                            move = new Cell(adjacent.r(), adjacent.c(), aiPlayer);
                        }
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
