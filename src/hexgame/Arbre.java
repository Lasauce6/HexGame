package hexgame;

/**
 * Classe Arbre.
 * Représente la structure d'un arbre.
 */
public class Arbre {
    /**
     * le pere de l'arbre, null si on se trouve à la racine
     */
    private Arbre pere_;

    /**
     * l'élément stocké
     */
    private int elt_;

    /**
     * Constructeur de la classe hexgame.Arbre
     * @param pere le pere de l'arbre
     * @param elt l'élément de l'arbre
     */
    public Arbre(Arbre pere, int elt) {
        elt_ = elt;
        pere_ = pere;
    }

    /**
     * Retourne l'attribut pere de l'arbre
     * @return le pere
     * @see #pere_
     */
    public Arbre getPere_() {
        return pere_;
    }

    /**
     * Mutateur de l'attribut pere
     * @param pere le nouveau pere
     * @see #pere_
     */
    public void setPere_(Arbre pere) {
        pere_ = pere;
    }

    /**
     * Accesseur de l'attribut elt
     * @return l'élément
     * @see #elt_
     */
    public int getElt_() {
        return elt_;
    }
}
