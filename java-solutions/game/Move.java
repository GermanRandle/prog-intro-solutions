package game;

public class Move extends Action {
    private final int row;
    private final int col;

    public Move(int row, int col, Cell value) {
        super(ActionType.MOVE, value);
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String getMessage() {
        return String.format("Move(%s, %d, %d)", value, row + 1, col + 1);
    }
}
