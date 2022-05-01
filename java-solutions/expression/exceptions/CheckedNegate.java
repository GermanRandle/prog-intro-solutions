package expression.exceptions;

import expression.Negate;
import expression.UltraExpression;

public class CheckedNegate extends Negate {
    public CheckedNegate(UltraExpression arg) {
        super(arg);
    }

    @Override
    protected int doOperation(int x) {
        if (-((long) x) > Integer.MAX_VALUE) {
            throw new OverflowException("Negate overflow", x);
        }
        return super.doOperation(x);
    }
}
