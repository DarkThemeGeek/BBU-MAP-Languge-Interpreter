package interpreter.model.exp;

import interpreter.exceptions.InvalidTypeException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicalExp(LogicalOp op,IExp left, IExp right) implements  IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) {
        if (!(left.eval(symTable) instanceof BoolValue(boolean n1)))
            throw new InvalidTypeException("first operand is not an integer");
        if (!(right.eval(symTable) instanceof BoolValue(boolean n2)))
            throw new InvalidTypeException("second operand is not an valid expression");
        return switch (op) {
            case EQUAL -> new BoolValue(n1 == n2);
            case NOT_EQUAL -> new BoolValue(n1 != n2);
            case AND -> new BoolValue(n1 && n2);
            case OR -> new BoolValue(n1 || n2);
        };

    }
    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
