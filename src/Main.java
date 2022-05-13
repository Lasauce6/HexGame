import hexgame.Board;
import hexgame.graphics.Client;

public class Main {
    /**
     * Le menu principal
     */
    public static void main(String[] args) {
        Board board = new Board();
        Client client = new Client();
        client.menu(board);
    }
}