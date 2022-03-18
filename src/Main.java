import hexgame.Joueur;
import hexgame.Plateau;
import java.util.*;
import javax.swing.JOptionPane;

import java.util.Scanner;

public class Main {
    public static void main(String [] args) {
        boolean fin=false;
        int choose;
        Scanner saisieUtilisateur = new Scanner(System.in);
        System.out.print("\033[2J");
        while(!fin) {
//            System.out.println("---------------------------------------------------------");
//            System.out.println(" Veuillez saisir votre choix ?");
//            System.out.println("---------------------------------------------------------");
//            System.out.println(" 1 - Lancer une partie 1v1 ");
//            System.out.println(" 2 - Lancer une partie contre un ordinateur en mode Attaquant + Défensif ");
//            System.out.println(" 3 - Faire des tests ");
//            System.out.println(" 4 - Quitter le jeu ");
//            System.out.println("---------------------------------------------------------");
//            System.out.println("Choix : ");
//            choose = saisie(saisieUtilisateur);
            choose = Integer.parseInt(JOptionPane.showInputDialog("""
                    Veuillez saisir votre choix :
                    1 - Lancer une partie 1v1
                    2 - Lancer une partie contre un ordinateur en mode Attaquant + Défensif
                    3 - Faire des tests
                    4 - Quitter le jeu
                    """));
//            System.out.println("---------------------------------------------------------");
            while(choose<1||choose>4) {

//                System.out.print("\033[2J");
//                System.out.println("---------------------------------------------------------");
//                System.out.println(" Veuillez resaisir votre choix ?");
//                System.out.println("---------------------------------------------------------");
//                System.out.println(" 1 - Lancer une partie 1v1 ");
//                System.out.println(" 2 - Lancer une partie contre un ordinateur en mode Attaquant + Défensif ");
//                System.out.println(" 3 - Faire des tests ");
//                System.out.println(" 4 - Quitter le jeu ");
//                System.out.println("---------------------------------------------------------");
//                System.out.println("Choix : ");
//                choose = saisie(saisieUtilisateur);
                choose = Integer.parseInt(JOptionPane.showInputDialog("""
                        Veuillez saisir un choix valide :
                        1 - Lancer une partie de 1v1
                        2 - Lancer une partie contre un ordinateur en mode Attaquant + Défensif
                        3 - Faire des tests
                        4 - Quitter le jeu"""));
//                System.out.println("---------------------------------------------------------");
            }
            switch (choose) {
                case 1 -> {
                    Plateau plateau = new Plateau();
                    Joueur joueura = new Joueur("default", true);
                    Joueur joueurb = new Joueur("default", false);
                    String nom1, nom2;
                    System.out.println(" Saisir les prénoms des deux joueurs");
                    System.out.println("---------------------------------------------------------");
//                    nom1 = saisie2(saisieUtilisateur);
                    nom1 = JOptionPane.showInputDialog("Entrer le nom du premier joueur");
//                    nom2 = saisie2(saisieUtilisateur);
                    nom2 = JOptionPane.showInputDialog("Entrer le nom du deuxième joueur");
                    joueura.setnom(nom1);
                    joueurb.setnom(nom2);
                    System.out.println("---------------------------------------------------------");
                    System.out.println(nom1 + " devra jouer a la vertical, il commencera la partie.");
                    System.out.println(nom2 + " devra jouer a la horizontalement, il commencera la partie.");
                    System.out.println("---------------------------------------------------------");
                    System.out.println(" A vous de jouer ! Nous vous souhaitons bonne chance ! :D ");
                    joueDeuxHumains(joueura, joueurb, plateau);
                }
                case 2 -> {
                    Plateau plateau2 = new Plateau();
                    Joueur joueura2 = new Joueur("default", true);
                    String nom;
                    System.out.println(" Saisir le prénom du joueur");
                    System.out.println("---------------------------------------------------------");
//                    nom = saisie2(saisieUtilisateur);
                    nom = JOptionPane.showInputDialog("Entrer le nom du joueur");
                    joueura2.setnom(nom);
                    System.out.print("\033[2J");
                    System.out.println("---------------------------------------------------------");
                    System.out.println(nom + " s'est à vous de jouer ! Nous vous souhaitons bonne chance ! :D ");
                    joueOrdiHumain(joueura2, plateau2);
                }
                case 3 -> {
                    Plateau plateau3 = new Plateau();
                    Joueur joueura3 = new Joueur("default", true);
                    Joueur joueurb3 = new Joueur("default", false);
                    effectuertest(joueura3, joueurb3, plateau3);
                    System.out.print("\033[2J");
                }
                case 4 -> fin = true;
            }
        }
    }

    public static void effectuertest(Joueur joueura, Joueur joueurb, Plateau plateau) {
        System.out.print("\033[2J");
        System.out.println("---------------------------------------------------------");
        int choose;
        Scanner saisieUtilisateur = new Scanner(System.in);
        boolean fin = false;
        int x,y;
        boolean res;
        int res2;
        int x1,y1,x2,y2,c1,c2;
        ArrayList<Integer> composante = new ArrayList<Integer>();
        while(!fin)
        {
            do{
                System.out.println("---------------------------------------------------------");
                System.out.println(" Veuillez saisir votre choix ?");
                System.out.println("---------------------------------------------------------");
                System.out.println(" 1 - ajouterPion ");
                System.out.println(" 2 - afficheComposante ");
                System.out.println(" 3 - ExisteCheminCases ");
                System.out.println(" 4 - ExisteCheminCotes ");
                System.out.println(" 5 - RelieComposantes ");
                System.out.println(" 6 - calculDistance ");
                System.out.println(" 7 - Stopper les tests");
                System.out.println("---------------------------------------------------------");
                System.out.println("Choix : ");
//                choose = saisie(saisieUtilisateur);
                choose = Integer.parseInt(JOptionPane.showInputDialog(""));
                System.out.println("---------------------------------------------------------");
            }while(choose<0||choose>8);

            switch (choose) {
                case 1 -> {
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Veuillez saisir les coordonnées x et y ");
//                    x = saisie(saisieUtilisateur);
                    x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                    y = saisie(saisieUtilisateur);
                    y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    System.out.println("---------------------------------------------------------");
                    while (!verificationCoordonnees(x, y) || !(plateau.estDispo(x, y) == 'o')) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
//                        x = saisie(saisieUtilisateur);
                        x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                        y = saisie(saisieUtilisateur);
                        y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    }
                    int z = x * 11 + y;
                    joueura.ajoutePion(z);
                    plateau.setDispo(x, y, 'A');
                    plateau.cliAfficher();
                }
                case 2 -> {
                    plateau.cliAfficher();
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Veuillez saisir les coordonnées x et y pour la première case");
//                    x = saisie(saisieUtilisateur);
                    x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                    y = saisie(saisieUtilisateur);
                    y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    System.out.println("---------------------------------------------------------");
                    while (!verificationCoordonnees(x, y)) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
//                        x = saisie(saisieUtilisateur);
                        x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                        y = saisie(saisieUtilisateur);
                        y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    }
                    c1 = x * 11 + y;
                    composante = joueura.getClasseUnion().afficheComposante(c1);
                    System.out.println("Voici la liste :");
                    for (Integer integer : composante) {
                        y2 = integer % 11;
                        x2 = (integer - y2) / 11;
                        System.out.println(x2 + "-" + y2);
                    }
                }
                case 3 -> {
                    plateau.cliAfficher();
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Veuillez saisir les coordonnées x et y pour la première case");
//                    x = saisie(saisieUtilisateur);
                    x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                    y = saisie(saisieUtilisateur);
                    y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    System.out.println("---------------------------------------------------------");
                    while (!verificationCoordonnees(x, y)) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
//                        x = saisie(saisieUtilisateur);
                        x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                        y = saisie(saisieUtilisateur);
                        y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    }
                    c1 = x * 11 + y;
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Veuillez saisir les coordonnées x et y pour la seconde case");
//                    x = saisie(saisieUtilisateur);
                    x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                    y = saisie(saisieUtilisateur);
                    y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    System.out.println("---------------------------------------------------------");
                    while (!verificationCoordonnees(x, y)) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
//                        x = saisie(saisieUtilisateur);
                        x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                        y = saisie(saisieUtilisateur);
                        y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    }
                    c2 = x * 11 + y;
                    res = joueura.existeCheminCase(c1, c2);
                    System.out.println("ExisteCheminCases ? " + res);
                }
                case 4 -> {
                    plateau.cliAfficher();
                    res = joueura.existeCheminCotes2();
                    System.out.println("ExisteCheminCotes ? " + res);
                }
                case 5 -> {
                    plateau.cliAfficher();
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Veuillez saisir les coordonnées x et y pour la première case");
//                    x = saisie(saisieUtilisateur);
                    x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                    y = saisie(saisieUtilisateur);
                    y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    System.out.println("---------------------------------------------------------");
                    while (!verificationCoordonnees(x, y)) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
//                        x = saisie(saisieUtilisateur);
                        x = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                        y = saisie(saisieUtilisateur);
                        y = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    }
                    c1 = x * 11 + y;
                    res = joueura.getClasseUnion().relieComposantes(c1);
                    if (res) {
                        System.out.println("le pion " + x + "-" + y + " relie bien plus de deux composantes.");
                    } else {
                        System.out.println("le pion " + x + "-" + y + " relie moins de deux composantes.");
                    }
                }
                case 6 -> {
                    plateau.cliAfficher();
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Veuillez saisir les coordonnées x et y pour la première case");
//                    x1 = saisie(saisieUtilisateur);
                    x1 = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                    y1 = saisie(saisieUtilisateur);
                    y1 = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    System.out.println("---------------------------------------------------------");
                    while (!verificationCoordonnees(x1, y1)) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
//                        x1 = saisie(saisieUtilisateur);
                        x1 = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                        y1 = saisie(saisieUtilisateur);
                        y1 = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    }
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Veuillez saisir les coordonnées x et y pour la seconde case");
//                    x2 = saisie(saisieUtilisateur);
                    x2 = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                    y2 = saisie(saisieUtilisateur);
                    y2 = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    System.out.println("---------------------------------------------------------");
                    while (!verificationCoordonnees(x2, y2)) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
//                        x2 = saisie(saisieUtilisateur);
                        x2 = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de x"));
//                        y2 = saisie(saisieUtilisateur);
                        y2 = Integer.parseInt(JOptionPane.showInputDialog("Entrer la valeur de y"));
                    }
                    res2 = plateau.calculDistance(x1, y1, x2, y2, 'A', joueura);
                    System.out.println("Longueur entre " + x1 + " " + y1 + " et " + x2 + " " + y2 + " est de : " + res2 + ".");
                }
                case 7 -> fin = true;
            }
        }

    }

    public static boolean verificationCoordonnees(int x,int y){
        return x>=-1&&x<11 && y>=-1&&y<11;
    }

    public static int saisie(Scanner scan) {
        int a;
            while(!scan.hasNextInt()) {
                System.out.println("Un entier est attendu");
                scan.next();
            }
            a = scan.nextInt();
        return a;
    }

    public static String saisie2(Scanner scan) {
        String a;
        a = scan.next();
        return a;
    }

public static int evaluerPionZ(Joueur ordi, Joueur joueur, Plateau plateau, int pion) {
        int estimation = 50;
        int distancexmin = 100;
        int distanceymin=100;
        ArrayList<Integer> bordx = new ArrayList<Integer>();
        ArrayList<Integer> bordy = new ArrayList<Integer>();
        ArrayList<Integer> bordxadver = new ArrayList<Integer>();
        ArrayList<Integer> bordyadver = new ArrayList<Integer>();
        int i;
        int y = pion%11;
        int x = (pion-y)/11;
        int y2;
        int x2;
        if(ordi.getdirection()) {
            for(i=0; i<11;++i) {
                if(plateau.getPlateau_()[i/11][i]=='o')
                    bordx.add(i);
            }
            for(i=110;i<121;++i) {
                if(plateau.getPlateau_()[i/11][i%11]=='o')
                    bordy.add(i);
            }
            i=0;
            while(i<111) {
                if(plateau.getPlateau_()[i/11][i%11]=='o') {
                    bordxadver.add(i);
                }
                i=i+11;
            }
            i=10;
            while(i<121) {
                if(plateau.getPlateau_()[i/11][i%11]=='o')
                    bordyadver.add(i);
                i=i+11;
            }
        } else {
            i=0;
            while(i<111) {
                if(plateau.getPlateau_()[i/11][i%11]=='o') {
                    bordx.add(i);
                }
                i=i+11;
            }
            i=10;
            while(i<121) {
                if(plateau.getPlateau_()[i/11][i%11]=='o')
                    bordy.add(i);
                i=i+11;
            }
            for(i=0; i<11;++i) {
                if(plateau.getPlateau_()[i/11][i]=='o')
                    bordxadver.add(i);
            }
            for(i=110;i<121;++i) {
                if(plateau.getPlateau_()[i/11][i%11]=='o')
                    bordyadver.add(i);
            }
        }
        i=0;
        while(i<bordx.size()) {
            y2=bordx.get(i)%11;
            x2=(bordx.get(i)-y2)/11;
            if(distancexmin > plateau.calculDistance(x,y,x2,y2,'B',ordi))
                distancexmin = plateau.calculDistance(x,y,x2,y2,'B',ordi);
            ++i;
        }
        i=0;
        while(i<bordy.size()) {
            y2=bordy.get(i)%11;
            x2=(bordy.get(i)-y2)/11;
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
        int distancexminadver=100;
        int distanceyminadver=100;
        int distanceadver;
        i=0;
        while(i<bordxadver.size()) {
            y2=bordxadver.get(i)%11;
            x2=(bordxadver.get(i)-y2)/11;
            if(distancexminadver > plateau.calculDistance(x,y,x2,y2,'A',joueur))
                distancexminadver = plateau.calculDistance(x,y,x2,y2,'A',joueur);
            ++i;
        }
        i=0;
        while(i<bordyadver.size()) {
            y2=bordyadver.get(i)%11;
            x2=(bordyadver.get(i)-y2)/11;
            if(distanceyminadver > plateau.calculDistance(x,y,x2,y2,'A',joueur))
                distanceyminadver = plateau.calculDistance(x,y,x2,y2,'A',joueur);
            ++i;
        }
        distanceadver = distanceyminadver + distancexminadver;
        if(distanceadver==1) {
            estimation=2;
        } else {
            if(estimation!=1) {
                if(distanceadver<distance+4) {
                    estimation=estimation+distanceadver*3-12;
                    if(plateau.getPlateau_()[x][y]!='A')
                        estimation=estimation-1;
                }
            }
        }
        //System.out.println("estimation !!!"+ distance + " advers : "+ distanceadver);
        return estimation;
   }

     public static int pionleplusavantageux(Joueur ordi, Joueur joueur, Plateau plateau) {
        char nom = 'B';//ordi.getnom();
        int estimationmin=Integer.MAX_VALUE;
        int estim=Integer.MAX_VALUE;
        ArrayList<Integer> list = new ArrayList<Integer>();
        int i;
        //System.out.println("estimation !!!");
        for(i=1; i<121;i++) {
            if(plateau.getPlateau_()[i/11][i%11]=='o'&&plateau.getPlateau_()[i/11][i%11]!='B'&&plateau.getPlateau_()[i/11][i%11]!='A') {
                //System.out.println("le pion est : "+plateau.getPlateau_()[i/11][i%11]);
                //System.out.println("pion  : "+i);
                estim= evaluerPionZ(ordi, joueur, plateau, i);
                //System.out.println("estimation x : "+i/11+" y : "+ i%11+ " estimation "+estim+"estimin "+estimationmin);
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
        ArrayList<Integer> bordx = new ArrayList<Integer>();
        ArrayList<Integer> bordy = new ArrayList<Integer>();
        int distancebut1=100;
        int distancebut2=100;
        int distance =100;
        int j;
        int x2,y2,x,y;
        for(i=0; i<11;++i) {
            if(plateau.getPlateau_()[i/11][i]=='o')
                bordx.add(i);
        }
        for(i=110;i<121;++i) {
            if(plateau.getPlateau_()[i/11][i%11]=='o')
                bordy.add(i);
        }
        for(i=0;i<list.size();++i) {
            y=list.get(i)%11;
            x=(list.get(i)-y)/11;            
            for(j=0;j<bordx.size();++j) {
                y2=bordx.get(j)%11;
                x2=(bordx.get(j)-y2)/11;
                if(distancebut1 > plateau.calculDistance(x,y,x2,y2,'A',joueur))
                    distancebut1 = plateau.calculDistance(x,y,x2,y2,'A',joueur);
            }
            for(j=0;j<bordy.size();++j) {
                y2=bordy.get(j)%11;
                x2=(bordy.get(j)-y2)/11;
                if(distancebut2 > plateau.calculDistance(x,y,x2,y2,'A',joueur))
                    distancebut2 = plateau.calculDistance(x,y,x2,y2,'A',joueur);
            }
            if(distance>distancebut1+distancebut2) {
                distance=distancebut1+distancebut2;
                pion = list.get(i);
            }
        }
       // System.out.println("FINAL : estimation x : "+pion/11+" y : "+ pion%11+ "estimin "+estimationmin);
        System.out.println("Le Bot vient d'ajouter le pion : "+pion/11+"-"+pion%11+".");
        return pion;
    }

    public static void joueOrdiHumain(Joueur joueura, Plateau plateau) {
       Joueur joueurb = new Joueur("Ordinateur",false);
       int pion;
       int x,y,z;          
        pion=5*11+5;
        joueurb.ajoutePion(pion);
        joueurb.existeCheminCotes();
        plateau.setDispo(pion/11,pion%11,'B');
        plateau.cliAfficher();
        while( !joueura.fini() && !joueurb.fini()) {
            System.out.println("---------------------------------------------------------" );
            System.out.println(joueura.getNom_()+", c'est à vous de jouer, veuillez saisir les coordonnées x et y ");
            Scanner saisieUtilisateur = new Scanner(System.in);
            x = saisie(saisieUtilisateur);
            y = saisie(saisieUtilisateur);
            System.out.println("---------------------------------------------------------");
            while( !verificationCoordonnees(x,y) || !( plateau.estDispo(x,y)=='o')) {
                System.out.println("---------------------------------------------------------" );
                System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
                x = saisie(saisieUtilisateur);
                y = saisie(saisieUtilisateur);
            }
            z= x*11 + y;
            joueura.ajoutePion(z);
            joueura.existeCheminCotes();
            plateau.setDispo(x,y,'A');
            if( !joueura.fini() ) {
                System.out.print(String.format("\033[2J"));
                pion=pionleplusavantageux(joueurb, joueura, plateau);
                plateau.setDispo(pion/11,pion%11,'B');
                pion = (pion%11)*11+((pion-pion%11)/11);
                joueurb.ajoutePion(pion);
                joueurb.existeCheminCotes();

            }
            System.out.println("---------------------------------------------------------");
            plateau.cliAfficher();
        }
        if(joueura.fini())
        {
            System.out.println(joueura.getNom_()+" a gagné !! ");
        } else {
            System.out.println(joueurb.getNom_()+" a gagné !! ");
        }
    }

    public static void joueDeuxHumains(Joueur joueura, Joueur joueurb, Plateau plateau){
        System.out.print(String.format("\033[2J"));
        System.out.println("---------------------------------------------------------" );
        plateau.cliAfficher();
        while( !joueura.fini() && !joueurb.fini()) {
            System.out.println("---------------------------------------------------------" );
            System.out.println(joueura.getNom_()+", c'est à vous de jouer, veuillez saisir les coordonnées x et y ");
            int x,y;
            Scanner saisieUtilisateur = new Scanner(System.in);
            x = saisie(saisieUtilisateur);
            y = saisie(saisieUtilisateur);
            while( !verificationCoordonnees(x,y) || !( plateau.estDispo(x,y)=='o')) {
                System.out.println("---------------------------------------------------------" );
                System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
                x = saisie(saisieUtilisateur);
                y = saisie(saisieUtilisateur);
            }
            int z= x*11 + y;
            joueura.ajoutePion(z);
            joueura.existeCheminCotes();
            plateau.setDispo(x,y,'A');
            System.out.print(String.format("\033[2J"));
            System.out.println("---------------------------------------------------------" );
            plateau.cliAfficher();
            if( !joueura.fini() ) {
                System.out.println("---------------------------------------------------------" );
                System.out.println(joueurb.getNom_()+", c'est à vous de jouer, veuillez saisir les coordonnées x et y ");
                x = saisie(saisieUtilisateur);
                y = saisie(saisieUtilisateur);
                while(!verificationCoordonnees(x,y) || !( plateau.estDispo(x,y)=='o')) {
                    System.out.println("---------------------------------------------------------" );
                    System.out.println("La case n'est pas disponible veuillez redonner des coordonnées !!!");
                    x = saisie(saisieUtilisateur);
                    y = saisie(saisieUtilisateur);
                }
                z= x + y*11;
                joueurb.ajoutePion(z);
                joueurb.existeCheminCotes();
                plateau.setDispo(x,y,'B');
                System.out.print(String.format("\033[2J"));
                System.out.println("---------------------------------------------------------" );
                plateau.cliAfficher();
            }
        }
        System.out.println("---------------------------------------------------------" );
        if(joueura.fini()) {
            System.out.println(joueura.getNom_()+" a gagné !! ");
        } else {
            System.out.println(joueurb.getNom_()+" a gagné !! ");
        }
    }
}

