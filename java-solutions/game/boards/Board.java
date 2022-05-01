package game.boards;

import game.*;

public abstract class Board {
    BoardCellType cellType;

    protected Board(BoardCellType cellType) {
        this.cellType = cellType;
    }

    public BoardCellType getCellType() {
        return cellType;
    }

    abstract public int getHeight();
    abstract public int getWidth();
    abstract public Cell getCell(int row, int col);
    abstract public Cell getTurn();
    abstract public Action getLastAction();
    abstract public Position getPosition();
    abstract public Move getBoardMove(Move move);
    abstract public boolean checkWin();
    abstract public boolean checkDraw();
    abstract public void makeAction(Action action);
    abstract public String getConsoleView();
}
