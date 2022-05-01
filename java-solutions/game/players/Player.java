package game.players;

import game.Action;
import game.GameStatus;
import game.Position;

public abstract class Player {
    protected abstract Action makeOrdinaryAction(Position position);
    protected abstract Action answerDraw(Position position);

    public Action makeAction(final Position position) {
        System.out.println(position.showBoard());
        if (position.getStatus() == GameStatus.DRAW_SUGGESTED) {
            return answerDraw(position);
        } else {
            return makeOrdinaryAction(position);
        }
    }
}
