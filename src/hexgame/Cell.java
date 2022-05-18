package hexgame;

/**
 * Définie une cellule
 * @param r la ligne de la cellule
 * @param c la colonne de la cellule
 * @param player le joueur dans cette cellule (0 si aucun)
 */
public record Cell(int r, int c, int player) implements Comparable {
    /**
     * Constructeur sans définir de joueur
     * @param r la ligne de la cellule
     * @param c la colonne de la cellule
     */
    public Cell(int r, int c) {
        this(r, c, 0);
    }

    /**
     * Compare la cellule à une autre
     * @param other the object to be compared.
     */
    public int compareTo(Object other) {
        return this.player - ((Cell) other).player;
    }
}
