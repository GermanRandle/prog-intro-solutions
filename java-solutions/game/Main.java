package game;

import game.boards.*;
import game.players.*;

public class Main {
    public static void main(String[] args) {
        final int m = 11;
        final int n = 11;
        final int k = 9;
        final GameStatus result = new TwoPlayerGame(
                new HexMNKBoard(m, k),
                new HumanPlayer(),
                new HumanPlayer()
        ).play(true);
        switch (result) {
            case WIN:
                System.out.println("First player won");
                break;
            case LOSS:
                System.out.println("Second player won");
                break;
            case DRAW:
                System.out.println("Draw");
                break;
            default:
                throw new AssertionError("Unknown result " + result);
        }
    }
}
