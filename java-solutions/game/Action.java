package game;

public class Action {
    protected ActionType type;
    protected final Cell value;

    public Action(ActionType type, Cell value) {
        this.type = type;
        this.value = value;
    }

    public Cell getValue() {
        return value;
    }

    public ActionType getType() {
        return type;
    }

    public String getMessage() {
        switch (type) {
            case MOVE:
                Move move = (Move) this;
                return String.format("Move(%s, %d, %d)", value, move.getRow() + 1, move.getCol() + 1);
            case CONCEDE:
                return value + " concedes";
            case TRY_DRAW:
                return value + " suggests draw";
            case DENY_DRAW:
                return value + " denied draw";
            case ACCEPT_DRAW:
                return value + " accepted draw";
            default:
                return null;
        }
    }
}
