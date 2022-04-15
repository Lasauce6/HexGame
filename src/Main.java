import hexgame.Board;
import hexgame.Joueur;
import hexgame.Plateau;
import hexgame.graphics.Client;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Main {

    /**
     * Le menu principal
     */
    public static void main(String [] args) {
        boolean fin=false;
        int choose;
        String[] options = {"1v1", "Vs ordi", "TEST", "Quitter"};
        while(!fin) {
            choose = JOptionPane.showOptionDialog(null,
                    "Veuillez saisir votre choix",
                    "Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[3]
                    );
            switch (choose) {
                case 0 -> {
                    Plateau plateau = new Plateau();
                    Joueur joueura = new Joueur("default", true);
                    Joueur joueurb = new Joueur("default", false);
                    String nom1, nom2;
                    nom1 = JOptionPane.showInputDialog("Entrer le nom du premier joueur");
                    nom2 = JOptionPane.showInputDialog("Entrer le nom du deuxième joueur");
                    joueura.setnom(nom1);
                    joueurb.setnom(nom2);
                    joueDeuxHumains(joueura, joueurb, plateau);
                }
                case 1 -> {
                    Plateau plateau2 = new Plateau();
                    Joueur joueura2 = new Joueur("default", true);
                    String nom;
                    nom = JOptionPane.showInputDialog("Entrer le nom du joueur");
                    joueura2.setnom(nom);
                    joueOrdiHumain(joueura2, plateau2);
                }
                case 2 -> {
                    start();
                    fin = true;
                }
                case 3 -> fin = true;
            }
        }
    }

    public static void start() {
        Board board = new Board();
        Client client = new Client(board);
        int turn = 0;
        client.repaint();
    }

    /**
     * Affiche le menu de choix des coordonnées
     * @param joueur le joueur
     * @return le choix fait par le joueur
     */
    public static int[] choixMenu(Joueur joueur) {
        String temp;
        int x, y;
        String[] choice = new String[11];
        for (int i = 0; i < choice.length; i++) {
            choice[i] = Integer.toString(i);
        }
        JComboBox<String> sx = new JComboBox<>(new DefaultComboBoxModel<>(choice));
        JComboBox<String> sy = new JComboBox<>(new DefaultComboBoxModel<>(choice));

        JPanel choicePanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc1 = new GridBagConstraints();
        JLabel label = new JLabel(joueur.getNom_() + "c'est à vous de jouer, veuillez choisir les coordonnées x et y");
        Font font = new Font("Courier", Font.BOLD,15);
        label.setFont(font);
        choicePanel.add(label, gbc1);
        gbc1.fill = GridBagConstraints.CENTER;
        gbc1.gridy++;

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        gbc2.insets = new Insets(4, 4, 4, 4);
        choicePanel.add(new JLabel("Valeur de x"), gbc2);
        gbc2.gridy++;
        choicePanel.add(sx, gbc2);
        gbc2.gridy++;
        choicePanel.add(new JLabel("Valeur de y"), gbc2);
        gbc2.gridy++;
        choicePanel.add(sy, gbc2);

        JOptionPane.showMessageDialog(null,
                choicePanel,
                "Choix des coordonnées",
                JOptionPane.PLAIN_MESSAGE);

        temp = (String) sx.getSelectedItem();
        assert temp != null;
        x = Integer.parseInt(temp);
        temp = (String) sy.getSelectedItem();
        assert temp != null;
        y = Integer.parseInt(temp);

        return new int[] {x, y};
    }


    public static int evaluerPionZ(Joueur ordi, Joueur joueur, Plateau plateau, int pion) {
        int estimation = 50;
        int distancexmin = 100;
        int distanceymin=100;
        ArrayList<Integer> bordx = new ArrayList<>();
        ArrayList<Integer> bordy = new ArrayList<>();
        ArrayList<Integer> bordxadver = new ArrayList<>();
        ArrayList<Integer> bordyadver = new ArrayList<>();
        int i;
        int y = pion % 11;
        int x = (pion - y) / 11;
        int y2;
        int x2;
        if(ordi.getdirection()) {
            for(i=0; i<11; ++i) {
                if(plateau.getPlateau_()[i / 11][i] == 'o')
                    bordx.add(i);
            }
            for(i=110; i<121; ++i) {
                if(plateau.getPlateau_()[i / 11][i % 11] == 'o')
                    bordy.add(i);
            }
            i = 0;
            while(i < 111) {
                if(plateau.getPlateau_()[i / 11][i % 11] == 'o') {
                    bordxadver.add(i);
                }
                i = i + 11;
            }
            i = 10;
            while(i < 121) {
                if(plateau.getPlateau_()[i / 11][i % 11] == 'o')
                    bordyadver.add(i);
                i = i + 11;
            }
        } else {
            i = 0;
            while(i < 111) {
                if(plateau.getPlateau_()[i / 11][i % 11] == 'o') {
                    bordx.add(i);
                }
                i = i + 11;
            }
            i = 10;
            while(i < 121) {
                if(plateau.getPlateau_()[i / 11][i % 11] == 'o')
                    bordy.add(i);
                i = i + 11;
            }
            for(i=0; i<11;++i) {
                if(plateau.getPlateau_()[i / 11][i] == 'o')
                    bordxadver.add(i);
            }
            for(i=110; i<121; ++i) {
                if(plateau.getPlateau_()[i / 11][i % 11] == 'o')
                    bordyadver.add(i);
            }
        }
        i = 0;
        while(i < bordx.size()) {
            y2 = bordx.get(i) % 11;
            x2 = (bordx.get(i) - y2) / 11;
            if(distancexmin > plateau.calculDistance(x, y, x2, y2,'B', ordi))
                distancexmin = plateau.calculDistance(x, y, x2, y2,'B', ordi);
            ++i;
        }
        i = 0;
        while(i < bordy.size()) {
            y2 = bordy.get(i) % 11;
            x2 = (bordy.get(i) - y2) / 11;
            if(distanceymin > plateau.calculDistance(x,y,x2,y2,'B',ordi))
                distanceymin = plateau.calculDistance(x,y,x2,y2,'B',ordi);
            ++i;
        }

        int distance = distanceymin+distancexmin;
        //System.out.println("distancex "+distancexmin+" distancey "+distanceymin);
        if( distance == 1 ) {
            estimation = 0;
        }
        else {
            estimation = estimation +distance;
            if(plateau.getPlateau_()[x][y]!='B')
                estimation=estimation-1;
        }
        int distancexminadver = 100;
        int distanceyminadver = 100;
        int distanceadver;
        i = 0;
        while(i < bordxadver.size()) {
            y2 = bordxadver.get(i) % 11;
            x2 = (bordxadver.get(i) - y2) / 11;
            if(distancexminadver > plateau.calculDistance(x, y, x2, y2,'A', joueur))
                distancexminadver = plateau.calculDistance(x, y, x2, y2,'A', joueur);
            ++i;
        }
        i = 0;
        while(i < bordyadver.size()) {
            y2 = bordyadver.get(i) % 11;
            x2 = (bordyadver.get(i) - y2) / 11;
            if(distanceyminadver > plateau.calculDistance(x, y, x2, y2,'A', joueur))
                distanceyminadver = plateau.calculDistance(x, y, x2, y2,'A', joueur);
            ++i;
        }
        distanceadver = distanceyminadver + distancexminadver;
        if(distanceadver == 1) {
            estimation = 2;
        } else {
            if(estimation != 1) {
                if(distanceadver < distance + 4) {
                    estimation = estimation + distanceadver * 3 - 12;
                    if(plateau.getPlateau_()[x][y]!='A')
                        estimation=estimation - 1;
                }
            }
        }
        return estimation;
   }

     public static int pionleplusavantageux(Joueur ordi, Joueur joueur, Plateau plateau) {
        int estimationmin = Integer.MAX_VALUE;
        int estim;
        ArrayList<Integer> list = new ArrayList<>();
        int i;
        for(i=1; i<121;i++) {
            if(plateau.getPlateau_()[i / 11][i % 11] == 'o'
                    && plateau.getPlateau_()[i / 11][i % 11] != 'B'
                    && plateau.getPlateau_()[i / 11][i % 11] != 'A') {

                estim= evaluerPionZ(ordi, joueur, plateau, i);

                if(estimationmin>estim) {
                    list.clear();
                    estimationmin=estim;
                    list.add(i);
                } else if(estimationmin==estim&&i/11<list.get(0)&&i/11>list.get(0)) {
                    list.add(i);
                }
            }
        }
        int pion = list.get(0);
        ArrayList<Integer> bordx = new ArrayList<>();
        ArrayList<Integer> bordy = new ArrayList<>();
        int distancebut1=100;
        int distancebut2=100;
        int distance =100;
        int j;
        int x2, y2, x, y;
        for(i=0; i<11; ++i) {
            if(plateau.getPlateau_()[i/11][i] == 'o')
                bordx.add(i);
        }
        for(i=110;i<121;++i) {
            if(plateau.getPlateau_()[i/11][i%11] == 'o')
                bordy.add(i);
        }
        for(i=0; i < list.size(); ++i) {
            y = list.get(i) % 11;
            x = (list.get(i) - y) / 11;
            for(j=0; j<bordx.size(); ++j) {
                y2 = bordx.get(j) % 11;
                x2 = (bordx.get(j) - y2) / 11;
                if(distancebut1 > plateau.calculDistance(x, y, x2, y2,'A', joueur))
                    distancebut1 = plateau.calculDistance(x, y, x2, y2,'A', joueur);
            }
            for(j=0; j<bordy.size(); ++j) {
                y2 = bordy.get(j) % 11;
                x2 = (bordy.get(j) - y2) / 11;
                if(distancebut2 > plateau.calculDistance(x, y, x2, y2,'A', joueur))
                    distancebut2 = plateau.calculDistance(x, y, x2, y2,'A', joueur);
            }
            if(distance > distancebut1 + distancebut2) {
                distance = distancebut1 + distancebut2;
                pion = list.get(i);
            }
        }
        return pion;
    }

    /**
     * Démare une partie contre l'ordi
     * @param joueura le joueur
     * @param plateau le plateau
     */
    public static void joueOrdiHumain(Joueur joueura, Plateau plateau) {
        Joueur joueurb = new Joueur("Ordinateur",false);
        int pion;
        int x, y, z;
        int[] choix;

        pion = 5 * 11 + 5;
        joueurb.ajoutePion(pion);
        joueurb.existeCheminCotes();
        plateau.setDispo(pion / 11,pion % 11,'B');

        plateau.cliAfficher();
        while(!joueura.fini() && !joueurb.fini()) {
            choix = choixMenu(joueura);
            x = choix[0];
            y = choix[1];

            while(!( plateau.estDispo(x, y) == 'o')) {
                JOptionPane.showMessageDialog(null,
                        "La case n'est pas disponible, merci d'en choisir une autre",
                        "Case indisponible",
                        JOptionPane.ERROR_MESSAGE);

                choix = choixMenu(joueura);
                x = choix[0];
                y = choix[1];
            }
            z = x * 11 + y;
            joueura.ajoutePion(z);
            joueura.existeCheminCotes();
            plateau.setDispo(x, y,'A');
            if(!joueura.fini()) {
                pion = pionleplusavantageux(joueurb, joueura, plateau);
                plateau.setDispo(pion/11,pion%11,'B');
                pion = (pion % 11) * 11 + ((pion - pion % 11) / 11);
                joueurb.ajoutePion(pion);
                joueurb.existeCheminCotes();

            }
            plateau.cliAfficher();
        }
        if(joueura.fini()) {
            JOptionPane.showMessageDialog(null,
                    joueura.getNom_() + " a gagné !!",
                    "Victoire",
                    JOptionPane.PLAIN_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null,
                    joueurb.getNom_() + " a gagné !!",
                    "Victoire",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Démare une partie humain contre humain
     * @param joueura le premier joueur
     * @param joueurb le deuxième joueur
     * @param plateau le plateau
     */
    public static void joueDeuxHumains(Joueur joueura, Joueur joueurb, Plateau plateau){
        int x, y;
        int[] choix;

        plateau.cliAfficher();
        while(!joueura.fini() && !joueurb.fini()) {
            choix = choixMenu(joueura);
            x = choix[0];
            y = choix[1];

            while(!( plateau.estDispo(x, y) == 'o')) {
                JOptionPane.showMessageDialog(null,
                        "La case n'est pas disponible, merci d'en choisir une autre",
                        "Case indisponible",
                        JOptionPane.ERROR_MESSAGE);

                choix = choixMenu(joueura);
                x = choix[0];
                y = choix[1];
            }
            int z = x * 11 + y;
            joueura.ajoutePion(z);
            joueura.existeCheminCotes();
            plateau.setDispo(x, y,'A');
            plateau.cliAfficher();

            if(!joueura.fini()) {
                choix = choixMenu(joueurb);
                x = choix[0];
                y = choix[1];

                while(!( plateau.estDispo(x, y) == 'o')) {
                    JOptionPane.showMessageDialog(null,
                            "La case n'est pas disponible, merci d'en choisir une autre",
                            "Case indisponible",
                            JOptionPane.ERROR_MESSAGE);

                    choix = choixMenu(joueurb);
                    x = choix[0];
                    y = choix[1];
                }
                z = x + y * 11;
                joueurb.ajoutePion(z);
                joueurb.existeCheminCotes();
                plateau.setDispo(x, y,'B');

                plateau.cliAfficher();
            }
        }
        if(joueura.fini()) {
            JOptionPane.showMessageDialog(null,
                    joueura.getNom_() + " a gagné !!",
                    "Victoire",
                    JOptionPane.PLAIN_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null,
                    joueurb.getNom_() + " a gagné !!",
                    "Victoire",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
}

