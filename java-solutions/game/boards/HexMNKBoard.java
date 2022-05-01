package game.boards;

import game.BoardCellType;
import game.Move;

public class HexMNKBoard extends MNKBoard {
    private static final int[] hexRowDelta = {1, 0, 1, -1, 0, -1};
    private static final int[] hexColDelta = {0, 1, 1, 0, -1, -1};

    public HexMNKBoard(int size, int scoreToWin) {
        super(size, size, BoardCellType.HEX, scoreToWin, hexRowDelta, hexColDelta);
    }

    @Override
    public Move getBoardMove(Move move) {
        int d1 = move.getRow();
        int d2 = move.getCol();
        if ((d1 + d2 - field.length + 1) % 2 == 0) {
            int row = (d1 + d2 - field.length + 1) / 2;
            int col = d2 - row;
            return new Move(row, col, move.getValue());
        } else {
            return new Move(-1, -1, move.getValue());
        }
    }

    @Override
    public String getConsoleView() {
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int d2 = 1; d2 <= 2 * width - 1; d2++) {
            sb.append(d2 / 10);
        }
        sb.append(System.lineSeparator()).append("   ");
        for (int d2 = 1; d2 <= 2 * width - 1; d2++) {
            sb.append(d2 % 10);
        }
        sb.append(System.lineSeparator()).append(System.lineSeparator());
        for (int d1 = 1; d1 <= 2 * width - 1; d1++) {
            sb.append(d1 / 10).append(d1 % 10).append(' ');
            for (int d2 = 1; d2 <= 2 * width - 1; d2++) {
                int row = (d1 + d2 - width - 1) / 2;
                int col = d2 - row - 1;
                if ((d1 + d2 - width + 1) % 2 == 0 && correctCell(row, col)) {
                    sb.append(CELL_TO_STRING.get(field[row][col]));
                } else {
                    sb.append(' ');
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
