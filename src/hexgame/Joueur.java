package hexgame;

/**
 * Classe Joueur
 */
public class Joueur {

    /**
     * le nom du joueur
     */
    private String nom_;

    /**
     * la classe union du joueur
     */
    private ClasseUnion ClasseUnion_;

    /**
     * la condition de victoire du joueur
     */
	private boolean fini_;

    /**
     * vrai si le joueur doit faire un chemin vertical, faux sinon
     */
    private boolean direction_;

    /**
     * constructeur de la classe joueur
     * @param nom le nom du joueur
     * @param direction la direction du joueur
     */
    public Joueur(String nom, boolean direction) {
        nom_ = nom;
        direction_ = direction; // true vertical
		fini_ = false;
		ClasseUnion_ = new ClasseUnion();
    }


    /**
     * Accesseur de l'attribut direction
     * @return la direction
     * @see #direction_
     */
    public boolean getdirection(){
        return direction_;
    }

    /**
     * Accesseur de l'attribut nom
     * @return le nom
     * @see #nom_
     */
    public String getNom_() {
        return nom_;
    }

    /**
     * setteur de l'attribut nom
     * @see #nom_
     */
    public void setnom(String  nom) {
        nom_=nom;
    }

    /**
     * Accesseur de l'attribut fini
     * @return la condition de victoire
     * @see #fini_
     */
    public boolean fini(){
        return fini_;
    }

    /**
     * Méthode qui ajoute un pion dans la structure ClasseUnion du joueur
     * @param c1 le numéro de la case où est posé le pion
     */
	public void ajoutePion(int c1){
		ClasseUnion_.unionAdjacentes(c1);
	}

    /**
     * stocke dans l'attribut fini si il existe un chemin entre les deux extrémités du plateau
     * @see ClasseUnion#existeCheminCotes()
     */
    public void existeCheminCotes(){
        fini_ = ClasseUnion_.existeCheminCotes();
    }

    /**
     * Accesseur de l'attribut ClasseUnion
     * @return la classe union
     * @see #ClasseUnion_
     */
    public ClasseUnion getClasseUnion(){
        return ClasseUnion_;
    }
}
