package game;

import game.boards.Board;

public final class Position {
    private final Board board;
    private final int maxRow;
    private final int maxCol;

    public Position(final Board board) {
        this.board = board;
        if (board.getCellType() == BoardCellType.HEX) {
            maxRow = 2 * board.getHeight() - 1;
            maxCol = 2 * board.getWidth() - 1;
        } else {
            maxRow = board.getHeight();
            maxCol = board.getWidth();
        }
    }

    public boolean isValid(Action action) {
        switch (action.getType()) {
            case MOVE:
                Move move = board.getBoardMove((Move) action);
                return 0 <= move.getRow() && move.getRow() < board.getHeight()
                        && 0 <= move.getCol() && move.getCol() < board.getWidth()
                        && board.getCell(move.getRow(), move.getCol()) == Cell.E
                        && getTurn() == move.getValue();
            case CONCEDE:
                return true;
            case TRY_DRAW:
                return board.getLastAction().getType() != ActionType.DENY_DRAW;
            default:
                return board.getLastAction().getType() == ActionType.TRY_DRAW;
        }
    }

    public GameStatus getStatus() {
        if (board.getLastAction().getType() == ActionType.CONCEDE) {
            return getTurn() == Cell.X ? GameStatus.WIN : GameStatus.LOSS;
        } else if (board.checkWin()) {
            return getTurn() == Cell.X ? GameStatus.LOSS : GameStatus.WIN;
        } else if (board.getLastAction().getType() == ActionType.ACCEPT_DRAW || board.checkDraw()) {
            return GameStatus.DRAW;
        } else if (board.getLastAction().getType() == ActionType.TRY_DRAW) {
            return GameStatus.DRAW_SUGGESTED;
        }
        return GameStatus.IN_PROCESS;
    }

    public String showBoard() {
        return board.getConsoleView();
    }

    public Cell getTurn() {
        return board.getTurn();
    }

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxCol() {
        return maxCol;
    }
}
