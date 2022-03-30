package org.hexgame;

import java.awt.*;

public class hexmech {
    private static int BORDERS = 50;	// Nombre de pixels pour la bordure
    private static int r = 0;   // Longueur d'un côté divisé par deux


    public static void setBorders(int b){
        BORDERS=b;
    }

    public static void setHeight(int height) {
        r = height / 2;
    }

    /**
     * Créé un hexagone
     * @param x0 position x de l'hexagone
     * @param y0 position y de l'hexagone
     * @return un Polygon en forme d'hexagone
     */
    public static Polygon hex (int x0, int y0) {

        int y = y0 + BORDERS + 50;
        int x = x0 + BORDERS;

        if (r == 0) {
            System.out.println("ERROR: la longueur des hexagones n'a pas été définie");
            return new Polygon();
        }

        int[] cx = new int[6], cy = new int[6];

        for(int k = 0; k < 6; k++){
            double angle = ((double)k / 6) * 2 * Math.PI + (Math.PI * 30 / 180.0);
            cx[k] = (int) (r * Math.cos(angle)) + x;
            cy[k] = (int) (r * Math.sin(angle)) + y;
        }
        return new Polygon(cx, cy, 6);
    }

    /**
     * Cette fonction dessine un hexagone.
     * La couleur initiale de l'hexagone est définie par hexgame.COLOURCELL
     * @param i coordonnée de x
     * @param j coordonnée de y
     * @param g2 l'objet Graphics2D où l'on doit dessiner
     */
    public static void drawHex(int i, int j, Graphics2D g2) {
        Polygon poly = hex(i, j);
        g2.setColor(hexgame.COLOURCELL);
        g2.fillPolygon(poly);
        g2.setColor(hexgame.COLOURGRID);
        g2.drawPolygon(poly);
    }

    /***
     * Affiche un hexagone rempli en fonction des coordonnées i et j
     * La couleur dépends de si n est positif ou négatif
     * La couleur est en fonction de hexgame.COLOURONE et hexgame.COLOURTWO
     * la valeur de n est dessinnée dans l'hexagone.
     * @param i coordonnée x
     * @param j coordonnée y
     * @param n ce qui doit être affiché dans l'hexagone
     * @param g2 l'élément graphique où l'on doit afficher
     */
    public static void fillHex(int i, int j, int n, Graphics2D g2) {
        char c;
        if (n < 0) {
            g2.setColor(hexgame.COLOURONE);
            g2.fillPolygon(hex(i, j));
            g2.setColor(hexgame.COLOURONETXT);
            c = (char)(-n);
            g2.drawString("" + c, i + 45, j + r + BORDERS + 4 + 25);
        }
        if (n > 0) {
            g2.setColor(hexgame.COLOURTWO);
            g2.fillPolygon(hex(i, j));
            g2.setColor(hexgame.COLOURTWOTXT);
            c = (char)n;
            g2.drawString("" + c, i + 45, j + r + BORDERS + 4 + 25);
        }
    }

    /**
     * Cette fonction transforme les coordonnées en pixels sur l'écran
     * en un point représentant un hexagone sur le plateau
     * @param mx coordonnée de x
     * @param my coordonnée de y
     * @return un Point représentant un hexagone sur le plateau
     */
    public static Point pxToHex(int mx, int my) {
        Point p = new Point(-1,-1);

        int x = -1, y = -1;
        int spacing = hexgame.HEXSIZE - 7;

        for (int i = 0; i < hexgame.BSIZE; i++) {
            for (int j = 0; j < hexgame.BSIZE; j++) {
                int i1 = i * spacing + j * (spacing / 2);
                if (mx > (i1 - hexgame.HEXSIZE / 2) + BORDERS
                && mx < (i1 + hexgame.HEXSIZE / 2) + BORDERS
                && my > ((j * (spacing - 7)) - hexgame.HEXSIZE / 2) + BORDERS + 50
                && my < ((j * (spacing - 7)) + hexgame.HEXSIZE / 2) + BORDERS + 50) {
                    x = i;
                    y = j;
                }
            }
        }

        p.x = x;
        p.y = y;
        return p;
    }
}