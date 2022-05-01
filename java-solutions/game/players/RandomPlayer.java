package game.players;

import game.Action;
import game.ActionType;
import game.Move;
import game.Position;

import java.util.Random;

public class RandomPlayer extends Player {
    private final Random random = new Random();

    @Override
    public Action makeOrdinaryAction(Position position) {
        while (true) {
            final Move move = new Move(
                    random.nextInt(position.getMaxRow()),
                    random.nextInt(position.getMaxCol()),
                    position.getTurn()
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }

    @Override
    public Action answerDraw(Position position) {
        if (random.nextInt(2) == 0) {
            return new Action(ActionType.ACCEPT_DRAW, position.getTurn());
        }
        return new Action(ActionType.DENY_DRAW, position.getTurn());
    }
}
