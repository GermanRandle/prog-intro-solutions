package expression;

import java.math.BigDecimal;

public class Divide extends BinaryOperation {
    public Divide(UltraExpression first, UltraExpression second) {
        super("/", 2000, false, first, second);
    }

    @Override
    public int doOperation(int x, int y) {
        return x / y;
    }

    @Override
    public BigDecimal doOperation(BigDecimal x, BigDecimal y) {
        return x.divide(y);
    }

    @Override
    protected boolean needBracketsRight() {
        if (!(operands[1] instanceof BinaryOperation)) {
            return false;
        }
        return ((BinaryOperation) operands[1]).priority <= this.priority;
    }
}
