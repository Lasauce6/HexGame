package org.hexgame;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class hexgame {
    private hexgame() {
        initGame();
        createAndShowGUI();
    }

    public static void main(String[] args) {
        new hexgame();
    }

    //constants and global variables
    final static Color COLOURBACK =  Color.CYAN;
    final static Color COLOURCELL =  Color.WHITE;
    final static Color COLOURGRID =  Color.BLACK;
    final static Color COLOURONE = Color.RED;
    final static Color COLOURONETXT = Color.BLACK;
    final static Color COLOURTWO = Color.BLUE;
    final static Color COLOURTWOTXT = Color.BLACK;
    final static int EMPTY = 0;
    final static int BSIZE = 11; //board size.
    final static int HEXSIZE = 50;	//hex size in pixels
    final static int BORDERX = 50;
    final static int BORDERY = 100;
    final static int SCRSIZE = HEXSIZE * (BSIZE + 1); //screen size (vertical dimension).

    int[][] board = new int[BSIZE][BSIZE];

    void initGame() {
        hexmech.setHeight(HEXSIZE);
        hexmech.setBorders(BORDERX, BORDERY);

        for (int i=0; i<BSIZE; i++) {
            for (int j=0; j<BSIZE; j++) {
                board[i][j] = EMPTY;
            }
        }

        //set up board here
        board[0][0] = 'A';
        board[4][4] = -(int)'B';
    }

    private void createAndShowGUI() {
        DrawingPanel panel = new DrawingPanel();


        //JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Hex Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();
        content.add(panel);
        //this.add(panel);  -- cannot be done in a static context
        //for hexes in the FLAT orientation, the height of a 10x10 grid is 1.1764 * the width. (from h / (s+t))
        frame.setSize(HEXSIZE * 15 , SCRSIZE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    class DrawingPanel extends JPanel {

        public DrawingPanel() {
            setBackground(COLOURBACK);

            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
        }

        public void paintComponent(@NotNull Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            super.paintComponent(g2);

            hexmech.drawBoard(g2);
            //draw grid
            int spacing = HEXSIZE - 7;
            for (int i = 0; i < BSIZE; i++) {
                for (int j = 0; j < BSIZE; j++){
                    hexmech.drawHex(i * spacing + j * (spacing / 2), j  * (spacing - 7), g2);
                }

            }
            for (int i = 0; i < BSIZE; i++) {
                for (int j = 0; j < BSIZE; j++) {
                    hexmech.fillHex(i * spacing + j * (spacing / 2), j * (spacing - 7), board[i][j], g2);
                }
            }


        }

        class MyMouseListener extends MouseAdapter {
            public void mouseClicked(@NotNull MouseEvent e) {
                Point p = new Point(hexmech.pxToHex(e.getX(), e.getY()));
                if (p.x < 0 || p.y < 0 || p.x >= BSIZE || p.y >= BSIZE) return;

                board[p.x][p.y] = 'A';
                repaint();
            }
        } //end of MyMouseListener class
    } // end of DrawingPanel class
}