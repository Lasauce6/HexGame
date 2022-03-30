package hexgame;

import java.util.*;
import graphics.*;

/**
 * Classe Plateau.
 * Défini le plateau de jeu sur lequel repose les pions
 */
public class Plateau {

    /**
     * tableau à double entrée contenant les pions
     */
    private final char[][] plateau_;

    /**
     * Contructeur de la classe Plateau
     */
    public Plateau() {
        plateau_ = new char[11][11];
        for(int i = 0; i<11; ++i) {
            for(int j= 0; j<11; ++j) {
                plateau_[i][j] = 'o';
            }
        }
    }

    /**
     * Accesseur de l'attribut plateau
     * @return le plateau
     * @see #plateau_
     */
    public char[][] getPlateau_() {
        return plateau_;
    }

    /**
     * Retourne la valeur du propriétaire de la case
     * @param i l'indice de la ligne
     * @param j l'indice de la colonne
     * @return o si la case est disponible sinon un char réprésentant un joueur
     */
    public char estDispo(int i, int j){
        return plateau_[i][j];
    }

    /**
     * Attribue une case à un joueur
     * @param i l'indice de la ligne
     * @param j l'indice de la colonne
     * @param nom le caractère représentant le joueur
     */
    public void setDispo(int i, int j, char nom){
            plateau_[i][j] = nom;
    }

    /**
     * Méthode qui affiche le plateau en CLI
     */
    public void cliAfficher() {
        int w;
        int j;
        int k;
        StringBuilder build = new StringBuilder();
        System.out.println("|0|1|2|3|4|5|6|7|8|9|10|");
        for(int i=0; i<11; i++) {
            k=i;
            if(k==10)
                --k;
            for(w=0; w<k; w++)
                build.append(" ");
            build.append(i).append("|");
            for(j=0; j<11; j++) {
                build.append(plateau_[i][j]);
                build.append("|");

            }
            build.append("\n");
        }
        System.out.println(build);
    }

    public void Afficher() {

    }

    /**
     * Calcul le plus court chemin entre deux pions d'un même joueur
     * @param x1 indice de la ligne du pion 1
     * @param y1 indice de la colonne du pion 1
     * @param x2 indice de la ligne du pion 2
     * @param y2 indice de la colonne du pion 2
     * @param nom le caractère représentant le joueur
     * @param j le joueur
     * @return la distance entre deux pion
     * @see Joueur
     */
    public int calculDistance(int x1, int y1,int x2,int y2, char nom, Joueur j) {
        boolean[] marqueur = new boolean[123];
        boolean trouve = false;
        int pion = 0;
        for(int q = 0; q < 123; ++q)
            marqueur[q]=false;
        LinkedList<Integer> fileEnCours = new LinkedList<>();
        LinkedList<Integer> fileAvenir = new LinkedList<>();
        fileEnCours.add(coordToCase(x1, y1));
        marqueur[x1 * 11 + y1] = true;
        int i;
        int s;
       do {
            while(!fileEnCours.isEmpty()&&!trouve) {
                s = fileEnCours.poll();
                for( int v : voisin(s /11,s%11)) {//pour tout les voisins d'une case
                    if(v < 122 && (!marqueur[v] && (plateau_[v/11][v%11] == 'o'|| plateau_[v/11][v%11] == nom ))) {//il n'a pas déjà été visité
                        if(plateau_[v/11][v%11] == nom) {
                            fileEnCours.add(v);
                        } else {
                            fileAvenir.add(v);
                        }
                        marqueur[v] = true;
                        if(v == x2*11+y2) {//on vérifie qu'il appartient à la composante d'arrivée
                            trouve = true;//c'est le cas donc on a trouvé le plus court chemin
                        }
                    }
                }
            }
           if(!trouve) {
                fileEnCours.addAll(fileAvenir);
                fileAvenir.clear();
                pion++;
           }
        } while(!fileEnCours.isEmpty() && !trouve);

        if(j.getClasseUnion().existeCheminCases(x1 * 11 + y1, x2 * 11+y2))
            pion = 0;
        // Regarde si la case départ != nom
        if(plateau_[x1][y1] != nom) {
            pion++;
        }
        // Regarde si la case arrive != nom
        if(plateau_[x2][y2] != nom) {
            pion++;
        }
        // Regarde si la case départ != nom & que la case deb = case fin
        if((x1==x2)&&(y1==y2)&&plateau_[x2][y2] != nom)
            pion = 1;

        // Regarde si la case départ == nom & que la case deb = case fin

        if((x1 == x2) && (y1 == y2) && plateau_[x2][y2] == nom)
            pion = 0;

        if(!trouve) {
            pion = Integer.MAX_VALUE;
        }
       return pion;
    }

    /**
     * Retourne la liste des voisins d'un pion
     * @param x indice de la ligne
     * @param y indice de la colonne
     * @return la liste des voisins
     */
    public ArrayList<Integer> voisin(int x, int y) {
        ArrayList<Integer> v = new ArrayList<>();
        int nouvellecoor;
        if (y != 0) {
            nouvellecoor = x * 11 + (y - 1);
            v.add(nouvellecoor);
        }
        if(y != 10) {
            nouvellecoor = x * 11 + (y + 1);
            v.add(nouvellecoor);
        }
        if(y != 10 && x != 0) {
            nouvellecoor = (x-1) * 11 + (y + 1);
            v.add(nouvellecoor);
        }
        if(y != 0 && x != 10) {
            nouvellecoor = (x+1) * 11 + (y - 1);
            v.add(nouvellecoor);
        }
        if(x != 0) {
            nouvellecoor = (x-1)* 11 + y;
            v.add(nouvellecoor);
        }
        if(x != 10) {
            nouvellecoor = (x+1) * 11 + y;
            v.add(nouvellecoor);
        }
        return v;
    }

    /**
     * convertit une coordonnée en numéro de case
     * @param x indice de la ligne
     * @param y indice de la colonne
     * @return le numéro de la case
     */
    public int coordToCase(int x, int y){
        return x * 11 + y;
    }
}
