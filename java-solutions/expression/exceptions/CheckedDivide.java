package expression.exceptions;

import expression.Divide;
import expression.UltraExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(UltraExpression first, UltraExpression second) {
        super(first, second);
    }

    @Override
    public int doOperation(int x, int y) {
        if (y == 0) {
            throw new DivisionByZeroException("Division by zero found");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("Division overflow", x, y);
        }
        return super.doOperation(x, y);
    }
}
