package hexgame;

/**
 * Définie une cellule
 * @param r la ligne de la cellule
 * @param c la colonne de la cellule
 * @param player le joueur dans cette cellule (0 si aucun)
 */
public record Cell(int r, int c, int player) {
    /**
     * Constructeur sans définir de joueur
     * @param r la ligne de la cellule
     * @param c la colonne de la cellule
     */
    public Cell(int r, int c) {
        this(r, c, 0);
    }
}
