package game.boards;

import game.BoardCellType;

public class SquareMNKBoard extends MNKBoard {
    private static final int[] squareRowDelta = {1, 0, 1, -1, -1, 0, -1, 1};
    private static final int[] squareColDelta = {0, 1, 1, 1, 0, -1, -1, -1};

    public SquareMNKBoard(int height, int width, int scoreToWin) {
        super(height, width, BoardCellType.SQUARE, scoreToWin, squareRowDelta, squareColDelta);
    }
}
