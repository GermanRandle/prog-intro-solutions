package game.boards;

import game.*;

import java.util.Arrays;

public abstract class TicTacToeBoard extends Board {
    protected final Cell[][] field;
    protected final int height;
    protected final int width;
    protected Action lastAction;
    protected int emptyCells;
    protected Cell turn;
    private final Position position;

    public TicTacToeBoard(int height, int width, BoardCellType cellType) {
        super(cellType);
        field = new Cell[height][width];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        emptyCells = height * width;
        this.height = height;
        this.width = width;
        turn = Cell.X;
        lastAction = new Action(ActionType.NULL, Cell.E);
        position = new Position(this);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Cell getCell(int row, int col) {
        return field[row][col];
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Action getLastAction() {
        return lastAction;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean checkDraw() {
        return emptyCells == 0;
    }
}
