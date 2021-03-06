package hexgame.graphics;

import hexgame.Board;
import hexgame.Cell;
import hexgame.ai.AiPlayer;
import hexgame.ai.AiRandom;
import hexgame.network.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Définie la fenêtre
 */
public class GamePanel extends JPanel {
    private final boolean isAi;
    private final boolean tournament;
    private boolean smart;
    private Channel channel1 = null;
    private Channel channel2 = null;
    public Cell lastReceived = null;
    private int aiLevel = 0;
    private int aiPlayer = 0;
    private AiRandom aiRandom = null;
    private AiPlayer ai = null;
    private final static int SIZE = 40; // La taille initiale de la fenêtre
    private final static int GAP = 10;
    private final Board board; // Le plateau
    private final Client client;
    private final Color [] colors = {Color.WHITE, new Color(255,67,46), new Color(83,187,244)}; // Les couleurs utilisées

    /**
     * Le constructeur du Panel pour une partie sans IA
     * @param client le client
     * @param board le plateau de jeu
     */
    public GamePanel(Client client, Board board) {
        super();
        this.isAi = false;
        this.tournament = false;
        this.board = board;
        this.client = client;
        setLayout(null);
        setOpaque(true);
        MouseListener ml = new MouseListener();
        addMouseListener(ml);
    }

    /**
     * Le constructeur du Panel pour une partie avec l'IA
     * @param client le client
     * @param board le plateau de jeu
     * @param aiPlayer le camp de l'IA
     * @param aiLevel le niveau de l'IA
     */
    public GamePanel(Client client, Board board, int aiPlayer, int aiLevel) {
        super();
        this.isAi = true;
        this.tournament = false;
        this.board = board;
        this.client = client;
        this.aiRandom = new AiRandom(board, aiPlayer);
        this.ai = new AiPlayer(board, aiPlayer);
        this.aiPlayer = aiPlayer;
        this.aiLevel = aiLevel;
        this.smart = true;
        setLayout(null);
        setOpaque(true);
        MouseListener ml = new MouseListener();
        addMouseListener(ml);
        if (this.aiPlayer == -1)  {
            if (aiLevel == 1) this.board.move(aiRandom.getBestMove());
            else if (aiLevel == 2) {
                this.board.move(ai.getBestMove());
                smart = !smart;
            }
            else this.board.move(ai.getBestMove());
        }
    }

    /**
     * Le constructeur du Panel pour une partie en mode tournoi
     * @param client le client
     * @param board le plateau de jeu
     * @param aiPlayer le camp de l'IA
     * @param aiLevel le niveau de l'IA
     * @param channel1 le channel 1
     * @param channel2 le channel 2
     */
    public GamePanel(Client client, Board board, int aiPlayer, int aiLevel, Channel channel1, Channel channel2) {
        super();
        this.isAi = true;
        this.tournament = true;
        this.board = board;
        this.client = client;
        this.aiRandom = new AiRandom(board, aiPlayer);
        this.ai = new AiPlayer(board, aiPlayer);
        this.aiPlayer = aiPlayer;
        this.aiLevel = aiLevel;
        this.channel1 = channel1;
        this.channel2 = channel2;
        this.lastReceived = null;
        this.smart = true;
        setLayout(null);
        setOpaque(true);
        this.channel1.connect();
        this.channel2.connect();
    }

    /**
     * Permet de tourner les hexagones dans le bon sens
     * @param point la valeur des points de l'hexagone
     * @param center le centre de l'hexagone
     * @return la valeur des points de l'hexagone tourné
     */
    private double[] rotate(double[] point, double[] center) {
        double[] res = new double[2];
        AffineTransform.getRotateInstance(Math.toRadians(60), center[0], center[1]).transform(point,0, res,0,1);
        return res;
    }

    /**
     * Permet d'afficher et de dessiner le plateau
     * Permet aussi d'envoyer/recevoir et jouer dans le mode tournoi
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBoarder((Graphics2D) g);
        int color;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                double[] coordinate = coordinate(i, j);
                if (board.cellBoard[board.getSize() - 1 - i][j].player() == -1) color = 2;
                else color = board.cellBoard[board.getSize() - 1 - i][j].player();
                paint((Graphics2D) g, coordinate[0], coordinate[1], colors[color]);
            }
        }
        if (!tournament) {
            if (board.numberOfMoves == 1) {
                swap(g);
            } else if (board.numberOfMoves == 2) {
                rmSwap();
            }
        }
        if (tournament) tournamentPlay();
    }

    /**
     * Permet d'envoyer/recevoir et jouer en mode tournoi
     */
    private void tournamentPlay() {
        if (board.numberOfMoves % 2 == 0 && aiPlayer == -1) {
            aiMove();
            if (board.lastMoveTournament.c() == -1 && board.lastMoveTournament.r() == -1) channel1.send("SWAP");
            else channel1.send(board.lastMoveTournament.c() + " " + board.lastMoveTournament.r());
        } else if (board.numberOfMoves % 2 != 0 && aiPlayer == 1) {
            aiMove();
            if (board.lastMoveTournament.c() == -1 && board.lastMoveTournament.r() == -1) channel1.send("SWAP");
            else channel1.send(board.lastMoveTournament.c() + " " + board.lastMoveTournament.r());
        } else {
            Cell move;
            String mes = this.channel2.getNext();
            String[] mesSplit = mes.split(" ");
            while (mesSplit.length < 2 && lastReceived == null && !mes.equals("SWAP") ||
                    lastReceived != null && Integer.parseInt(mesSplit[1]) == lastReceived.r() && Integer.parseInt(mesSplit[0]) == lastReceived.c()) {
                mes = this.channel2.getNext();
                mesSplit = mes.split(" ");
            }
            if (mes.equals("SWAP")) {
                move = new Cell(-1, -1, this.aiPlayer == 1 ? -1 : 1);
                board.swap();
            } else if (board.board[Integer.parseInt(mesSplit[1])][Integer.parseInt(mesSplit[0])] == 0) {
                move = new Cell(Integer.parseInt(mesSplit[1]), Integer.parseInt(mesSplit[0]), this.aiPlayer == 1 ? -1 : 1);
                board.move(move);
            } else {
                move = new Cell(Integer.parseInt(mesSplit[1]), Integer.parseInt(mesSplit[0]), this.aiPlayer == 1 ? -1 : 1);
                client.gameEnd(aiPlayer, new Board());
            }
            lastReceived = move;
        }
        if (board.win() != 0) {
            client.gameEnd(board.win(), new Board());
        }
        repaint();
    }

    /**
     * Permet d'afficher la bordure du plateau
     * @param g the <code>Graphics</code> object to protect
     */
    private void paintBoarder(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ArrayList<double[]> points = new ArrayList<>();
        points.add(new double[] {-SIZE * 1.5, 0});
        double[] temp = coordinate(0, board.getSize() - 1);
        temp[1] = 0;
        temp[0] += 1.5 * SIZE;
        points.add(temp);
        temp = coordinate(board.getSize() - 1, board.getSize() - 1);
        temp[0] += SIZE * Math.sqrt(3) / 2 + GAP + SIZE * 1.5;
        temp[1] += SIZE + GAP;
        points.add(temp);
        double bt = temp[1];
        temp = coordinate(board.getSize() - 1, 0);
        temp[1] = bt;
        temp[0] -= 1.5 * SIZE;
        points.add(temp);

        for (int i = 0; i < points.size(); i++) {
            int nx = (i + 1) % points.size();
            double[] mid = coordinate(board.getSize() / 2, board.getSize() / 2);
            int[] x = {(int) Math.round(points.get(i)[0]), (int) Math.round(points.get(nx)[0]), (int) Math.round(mid[0])};
            int[] y = {(int) Math.round(points.get(i)[1]), (int) Math.round(points.get(nx)[1]), (int) Math.round(mid[1])};
            g.setColor(colors[1 + (i % 2)]);
            g.fillPolygon(x, y, 3);
            drawBoarder(g, x, y, 3, 0.5);

        }
    }

    /**
     * Renvoi les coordonnées d'un hexagone
     * @param x n° de ligne de l'hexagone
     * @param y n° de colonne de l'hexagone
     * @return les coordonnées de l'hexagone
     */
    public double[] coordinate(int x, int y) {
        double [] res = new double[2];
        res[0] = GAP + (y + 1. / 2) * Math.sqrt(3) * SIZE;
        res[1] = GAP + SIZE + x * 1.5 * SIZE;
        res[0] += Math.sqrt(3) / 2 * x * SIZE;
        return res;
    }

    /**
     * Peint un hexagone
     * @param g the <code>Graphics</code> object to protect
     * @param cx coordonnée x de l'hexagone
     * @param cy coordonnée y de l'hexagone
     * @param color couleur de l'hexagone
     */
    private void paint(Graphics2D g, double cx, double cy, Color color) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        double[] now = {cx, cy - (double) GamePanel.SIZE};
        int[] x = new int[6];
        int[] y = new int[6];

        for (int i = 0; i < 6; i++) {
            x[i] = (int) Math.round(now[0]);
            y[i] = (int) Math.round(now[1]);
            now = rotate(now, new double[] {cx, cy});
        }
        g.setColor(color);
        g.fillPolygon(x, y, 6);
        drawBoarder(g, x, y, 6, 1);
    }

    /**
     * Affiche la bordure
     * @param g the <code>Graphics</code> object to protect
     * @param x les points x du dessin
     * @param y les points y du dessin
     * @param n le nombre de côtés
     * @param thickness l'épaisseur du trait
     */
    private void drawBoarder(Graphics2D g, int[] x, int[] y, int n, double thickness) {
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke((float) thickness));
        g.setColor(Color.BLACK);
        g.drawPolygon(x, y, n);
        g.setStroke(oldStroke);
    }

    /**
     * Donne la cellule à partir de coordonnées
     * @param cx coordonnées x du curseur
     * @param cy coordonnées y du curseur
     * @return la cellule (renvoi -1, -1 pour le coup SWAP)
     */
    private Cell pxToHex(double cx, double cy) {
        double[] c;
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                c = coordinate(x, y);
                if (cx > c[0] - 30 && cx < c[0] + 30 && cy > c[1] - 30 && cy < c[1] + 30) {
                    return new Cell(board.getSize() - x - 1, y, board.numberOfMoves % 2 == 0 ? -1 : 1);
                }
            }
        }
        if (board.numberOfMoves == 1) {
            if (cx > 990 && cx < 1099 && cy > 108 && cy < 169) {
                return new Cell(-1, -1, 0);
            }
        }
        return null;
    }

    /**
     * Affiche le bouton swap
     * @param g the <code>Graphics</code> object to protect
     */
    private void swap(Graphics g) {
        String title = "SWAP";
        Font font = new Font("arial", Font.BOLD, 30);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(title, 1000, 150);
        Rectangle2D box = g.getFontMetrics(font).getStringBounds(title, g);
        g.drawRect((int) box.getX() + 990,
                (int) box.getY() + 140,
                (int) box.getWidth() + 20,
                (int) box.getHeight() + 20);
    }

    /**
     * Enlève le bouton swap
     */
    private void rmSwap() {
        this.repaint();
    }

    /**
     * Effectue le coup de l'IA en fonction de son niveau
     */
    private void aiMove() {
        if (aiLevel == 1) {
            Cell move = aiRandom.getBestMove();
            if (move.c() == -1 && move.r() == -1) board.swap();
            else board.move(aiRandom.getBestMove());
        } else if (aiLevel == 2) {
            Cell move;
            if (smart) move = ai.getBestMove();
            else move = aiRandom.getBestMove();
            if (move.r() == -1 && move.c() == -1) board.swap();
            else board.move(move);
            smart = !smart;
        } else {
            Cell move = ai.getBestMove();
            if (move.r() == -1 && move.c() == -1) board.swap();
            else board.move(move);
        }
    }

    /**
     * Prise en charge de la souris
     */
    public class MouseListener extends MouseAdapter {
        /**
         * Colore le bon pixel lorsque l'on clique dessus
         * @param e the event to be processed
         */
        public void mouseClicked(MouseEvent e) {
            if (!tournament) {
                Cell hex = pxToHex(e.getX(), e.getY());
                if (hex != null) {
                    if (hex.r() == -1 && hex.c() == -1) {
                        board.swap();
                        if (isAi) aiMove();
                    } else if (board.board[hex.r()][hex.c()] == 0) {
                        board.move(hex);
                        if (isAi) aiMove();

                        if (board.win() != 0) {
                            client.gameEnd(board.win(), new Board());
                        }
                    }
                }
                repaint();
            }
        }
    }
}
