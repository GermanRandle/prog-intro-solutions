package game.players;

import game.Action;
import game.ActionType;
import game.Move;
import game.Position;

public class SequentialPlayer extends Player {
    @Override
    public Action makeOrdinaryAction(Position position) {
        for (int r = 0; r < position.getMaxRow(); r++) {
            for (int c = 0; c < position.getMaxCol(); c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }

    @Override
    public Action answerDraw(Position position) {
        return new Action(ActionType.DENY_DRAW, position.getTurn());
    }
}
