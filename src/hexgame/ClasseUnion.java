package hexgame;

import java.util.ArrayList;
/**
 * Structure Classe-Union
 */
public class ClasseUnion {
    /**
     * le tableau des classes
     */
    private Arbre[] classes_;

    /**
     * valeur fixe dans le cas où le joueur n'a pas posé de pion sur cette case
     */
    private static Arbre NON_ATTRIBUE = null;

    /**
     * valeur de la classe qui correspond à l'une des extrémité du plateau de jeu
     */
    private static int EXTREME1 = 121;

    /**
     * valeur de la classe qui correspond à l'autre extrémité du plateau de jeu
     */
    private static int EXTREME2 = 122;


    /**
     * Constructeur de la structure classe union
     */
    public ClasseUnion() {
        classes_ = new Arbre[123];
        for (int i = 0; i < 121; ++i)
            classes_[i] = NON_ATTRIBUE;
        classes_[EXTREME1] = new Arbre(null,EXTREME1);
        classes_[EXTREME2] = new Arbre(null,EXTREME2);
    }

    /**
     * réalise l'ensemble des unions entre le pion qui vient d'être posé et les pions adjacents à celui-ci
     * @param c1 le pion posé
     */
    public void unionAdjacentes(int c1){
		classes_[c1] = new Arbre(null,c1);
		if(c1>-1&&c1<11){
			union(c1,EXTREME1);
			if(!(c1 == 0)){
				if(classes_[c1+10]!=NON_ATTRIBUE)
				{
					union(c1,c1+10);
				}
			}
			if(classes_[c1+11]!=NON_ATTRIBUE)
			{
					union(c1,c1+11);
			}
		}else if (c1>109&&c1<121){
			union(c1,EXTREME2);
			if(!(c1 == 120)){
				if(classes_[c1-10]!=NON_ATTRIBUE)
				{
					union(c1,c1-10);
				}				
			}
			if(classes_[c1-11]!=NON_ATTRIBUE)
			{
					union(c1,c1-11);
			}	
		}else{
			if( (c1 % 11) == 0)
			{  
				
				if(classes_[c1-10]!=NON_ATTRIBUE)
				{
					union(c1,c1-10);
				}
				if(classes_[c1+1]!=NON_ATTRIBUE)
				{
					union(c1,c1+1);
				}
				
			}else if( (c1 % 11) ==10 )
			{
				if(classes_[c1-1]!=NON_ATTRIBUE)
				{
					union(c1,c1-1);
				}
				if(classes_[c1+10]!=NON_ATTRIBUE)
				{
					union(c1,c1+10);
				}
			}else
			{
				if(classes_[c1-10]!=NON_ATTRIBUE)
				{
					union(c1,c1-10);
				}
				if(classes_[c1-1]!=NON_ATTRIBUE)
				{
					union(c1,c1-1);
				}
				if(classes_[c1+1]!=NON_ATTRIBUE)
				{
					union(c1,c1+1);
				}
				if(classes_[c1+10]!=NON_ATTRIBUE)
				{
					union(c1,c1+10);
				}
			}
			if(classes_[c1-11]!=NON_ATTRIBUE)
			{
				union(c1,c1-11);
			}
			if(classes_[c1+11]!=NON_ATTRIBUE)
			{
				union(c1,c1+11);
			}	
		}	
    }

    /**
     * union entre deux pions
     * @param c1 pion 1
     * @param c2 pion 2
     */
    public void union(int c1, int c2) {
        c1 = classe(c1);
        c2 = classe(c2);
        if(c1 != c2){
            classes_[c1].setPere_(classes_[c2]);
        }
    }

    /**
     * retourne la classe d'un pion
     * @param c le pion
     * @return la classe de ce pion
     */
    public Integer classe(int c){
        Arbre ac = classes_[c];
        if(ac!=null){
            while(ac.getPere_()!=null) {
                ac = ac.getPere_();
            }
            return ac.getElt_();
        }
        else{
            return null;
        }
    }

    /**
     * teste s’il y a un chemin entre les deux extrémités du plateau de jeu
     * @return vrai si un chemin existe sinon faux
     */
    public boolean existeCheminCotes(){
        return classe(EXTREME1)==classe(EXTREME2);
    }

    /**
     * teste si il existe un chemin entre 2 cases
     * @param c1 une case
     * @param c2 l'autre case
     * @return vrai si il y a un chemin, faux sinon
     */
    public boolean existeCheminCases(int c1,int c2){
        boolean existe = false;
        if(classes_[c1] != NON_ATTRIBUE && classes_[c2] != NON_ATTRIBUE){
            if(classe(c1) == classe(c2))
                existe = true;
        }
        return existe;
    }

    /**
     * retourne si la liste des éléments qu'un pion relie quand il est posé sur le plateau est plus grand de 1
     * @param c1 le pion
     * @return vrai si au moins deux composantes sont reliés
     */
    public boolean relieComposantes(int c1){
        ArrayList<Integer> classesAdjacentes = new ArrayList<Integer>();
        if(c1>-1&&c1<11){// c1  est sur la première ligne
            classesAdjacentes.add(classe(EXTREME1));
            if(!(c1 == 0)){//c1 n'est pas la première case
                if(classes_[c1+10]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1+10))))
                {
                    classesAdjacentes.add(classe(c1+10));
                }
            }
            if(classes_[c1+11]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1+11))))
            {
                classesAdjacentes.add(classe(c1 + 11));
            }
        }else if (c1>109&&c1<121){//c1 est sur la dernière ligne
            classesAdjacentes.add(classe(EXTREME2));
            if(!(c1 == 120)){//c1 est la première case
                if(classes_[c1-10]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1-10))))
                {
                    classesAdjacentes.add(classe(c1 - 10));
                }
            }
            if(classes_[c1-11]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1-11))))
            {
                classesAdjacentes.add(classe(c1 - 11));
            }
        }else{//sinon
            if( (c1 % 11) == 0)//1ere colonne
            {

                if(classes_[c1-10]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1-10))))
                {
                    classesAdjacentes.add(classe(c1 - 10));
                }
                if(classes_[c1+1]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1+1))))
                {
                    classesAdjacentes.add(classe(c1 + 1));
                }

            }else if( (c1 % 11) ==10 )//dernière colonne
            {
                if(classes_[c1-1]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1-1))))
                {
                    classesAdjacentes.add(classe(c1 - 1));
                }
                if(classes_[c1+10]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1+10))))
                {
                    classesAdjacentes.add(classe(c1 + 10));
                }
            }else
            {
                if(classes_[c1-10]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1-10))))
                {
                    classesAdjacentes.add(classe(c1 - 10));
                }
                if(classes_[c1-1]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1-1))))
                {
                    classesAdjacentes.add(classe(c1 - 1));
                }
                if(classes_[c1+1]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1+1))))
                {
                    classesAdjacentes.add(classe(c1 + 1));
                }
                if(classes_[c1+10]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1+10))))
                {
                    classesAdjacentes.add(classe(c1 + 10));
                }
            }
            if(classes_[c1-11]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1-11))))
            {
                classesAdjacentes.add(classe(c1 - 11));
            }
            if(classes_[c1+11]!=NON_ATTRIBUE && !(classesAdjacentes.contains(classe(c1+11))))
            {
                classesAdjacentes.add(classe(c1 + 11));
            }
        }

        return (classesAdjacentes.size()>1);
    }

    /**
     * Méthode qui affiche tous les éléments d'une composante
     * @param x le pion
     * @return le tableau d'éléments
     */
    public ArrayList<Integer> afficheComposante(int x){
        Integer c = classe(x);
        ArrayList<Integer> composante = new ArrayList<Integer>();
        for(int i = 0; i<123;++i){
            if(i!=x && classe(i) == c) {
                composante.add(i);
            }
        }

        return composante;
    }
}
