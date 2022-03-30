package org.hexgame;

import java.awt.*;

/* This is a companion class to hexgame.java. It handles all of the mechanics related to hexagon grids. */

public class hexmech {
  /* Helpful references:
http://www.codeproject.com/Articles/14948/Hexagonal-grid-for-games-and-other-projects-Part-1
http://weblogs.java.net/blog/malenkov/archive/2009/02/hexagonal_tile.html
http://www.tonypa.pri.ee/tbw/tut25.html
	 */

	/*
#define HEXEAST 0
#define HEXSOUTHEAST 1
#define HEXSOUTHWEST 2
#define HEXWEST 3
#define HEXNORTHWEST 4
#define HEXNORTHEAST 5
	 */


    private static int BORDERS = 50;	//default number of pixels for the border.

    private static int s = 0;	// length of one side
    private static int r = 0;	// radius of inscribed circle (centre to middle of each side). r= h/2
    private static int h = 0;	// height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.

    public static void setBorders(int b){
        BORDERS=b;
    }

    public static void setHeight(int height) {
        h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
        r = h / 2;			// r = radius of inscribed circle
        s = (int) (h / 1.73205);	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
    }

    /*********************************************************
     Name: hex()
     Parameters: (x0,y0) This point is normally the top left corner
     of the rectangle enclosing the hexagon.
     Returns: a polygon containing the six points.
     Called from: drawHex(), fillhex()
     Purpose: This function takes two points that describe a hexagon
     and calculates all six of the points in the hexagon.
     *********************************************************/
    public static Polygon hex (int x0, int y0) {

        int y = y0 + BORDERS + 50;
        int x = x0 + BORDERS;

        if (s == 0  || h == 0) {
            System.out.println("ERROR: size of hex has not been set");
            return new Polygon();
        }

        int[] cx = new int[6], cy = new int[6];

        for(int k = 0; k<6; k++){
            double angle = ((double)k / 6) * 2 * Math.PI + (Math.PI * 30 / 180.0);
            cx[k] = (int) (r * Math.cos(angle)) + x;
            cy[k] = (int) (r * Math.sin(angle)) + y;
        }
        return new Polygon(cx, cy, 6);
    }

    /********************************************************************
     Name: drawHex()
     Parameters: (i,j) : the x,y coordinates of the inital point of the hexagon
     g2: the Graphics2D object to draw on.
     Returns: void
     Calls: hex()
     Purpose: This function draws a hexagon based on the initial point (x,y).
     The hexagon is drawn in the colour specified in hexgame.COLOURELL.
     *********************************************************************/
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

    //This function changes pixel location from a mouse click to a hex grid location
    /*****************************************************************************
     * Name: pxToHex (pixel to hex)
     * Parameters: mx, my. These are the co-ordinates of mouse click.
     * Returns: point. A point containing the coordinates of the hex that is clicked in.
     If the point clicked is not a valid hex (ie. on the borders of the board, (-1,-1) is returned.
     * Function: This only works for hexes in the FLAT orientation. The POINTY orientation would require
     a whole other function (different math).
     It takes into account the size of borders.
     *****************************************************************************/

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