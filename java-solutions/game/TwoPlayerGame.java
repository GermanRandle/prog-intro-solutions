package game;

import game.boards.Board;
import game.players.Player;

public class TwoPlayerGame {
    private final Board board;
    private final Player player1;
    private final Player player2;

    public TwoPlayerGame(final Board board, final Player player1, final Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public GameStatus play(final boolean log) {
        System.out.println("The game has started");
        while (!board.getPosition().getStatus().isFinished) {
            if (board.getPosition().getTurn() == Cell.X) {
                if (!makeAction(player1, log)) {
                    return GameStatus.LOSS;
                }
            } else {
                if (!makeAction(player2, log)) {
                    return GameStatus.WIN;
                }
            }
        }
        return board.getPosition().getStatus();
    }

    private boolean makeAction(final Player player, final boolean log) {
        final Action action = player.makeAction(board.getPosition());
        if (!board.getPosition().isValid(action)) {
            System.out.println("Turn is illegal, you have lost");
            return false;
        }
        board.makeAction(action);
        if (log) {
            System.out.println();
            System.out.println("Player: " + (player == player1 ? "1" : "2"));
            System.out.println(action.getMessage());
        }
        return true;
    }
}
