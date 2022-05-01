package game.boards;

import game.*;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard extends TicTacToeBoard {
    protected static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0"
    );
    protected int[] rowDelta;
    protected int[] colDelta;
    protected int scoreToWin;

    public MNKBoard(int height, int width, BoardCellType cellType, int scoreToWin, int[] rowDelta, int[] colDelta) {
        super(height, width, cellType);
        this.scoreToWin = scoreToWin;
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
    }

    protected int getMax(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int val : array) {
            max = Math.max(max, val);
        }
        return max;
    }

    protected boolean correctCell(int row, int col) {
        return row >= 0 && col >= 0 && row < height && col < width;
    }

    public Move getBoardMove(Move move) {
        return move;
    }

    @Override
    public boolean checkWin() {
        if (lastAction.getType() != ActionType.MOVE) {
            return false;
        }
        Move lastMove = getBoardMove((Move) lastAction);
        int[] streaks = new int[rowDelta.length / 2];
        boolean[] canContinue = new boolean[rowDelta.length];
        Arrays.fill(streaks, 1);
        Arrays.fill(canContinue, true);
        for (int i = 1; i < scoreToWin; i++) {
            for (int dir = 0; dir < rowDelta.length; dir++) {
                int row = lastMove.getRow() + rowDelta[dir] * i;
                int col = lastMove.getCol() + colDelta[dir] * i;
                if (correctCell(row, col) && field[row][col] == lastMove.getValue() && canContinue[dir]) {
                    streaks[dir % (rowDelta.length / 2)]++;
                } else {
                    canContinue[dir] = false;
                }
            }
        }
        return getMax(streaks) >= scoreToWin;
    }

    @Override
    public void makeAction(final Action action) {
        if (action.getType() == ActionType.MOVE) {
            final Move move = getBoardMove((Move) action);
            field[move.getRow()][move.getCol()] = move.getValue();
            emptyCells--;
        }
        lastAction = action;
        turn = (turn == Cell.X ? Cell.O : Cell.X);
    }

    @Override
    public String getConsoleView() {
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int col = 1; col <= width; col++) {
            sb.append(col / 10);
        }
        sb.append(System.lineSeparator()).append("   ");
        for (int col = 1; col <= width; col++) {
            sb.append(col % 10);
        }
        sb.append(System.lineSeparator()).append(System.lineSeparator());
        for (int row = 1; row <= height; row++) {
            sb.append(row / 10).append(row % 10).append(' ');
            for (int col = 1; col <= width; col++) {
                sb.append(CELL_TO_STRING.get(field[row - 1][col - 1]));
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
