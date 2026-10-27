package interpreter.model.exp;

import interpreter.exceptions.*;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

import java.util.Map;

public record ArithExp(ArithOp op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable)  {

        if (!(left.eval(symTable) instanceof IntValue(int n1)))
            throw new InvalidTypeException("first operand is not an integer");

        if (!(right.eval(symTable) instanceof IntValue(int n2)))
            throw new InvalidTypeException("second operand is not an integer");

        return switch (op) {
            case ADD -> new IntValue(n1 + n2);
            case SUB -> new IntValue(n1 - n2);
            case MUL -> new IntValue(n1 * n2);
            case DIV -> {
                if (n2 == 0) throw new DivisionByZeroException("division by zero");
                yield new IntValue(n1 / n2);
            }


        };
    }
    @Override
    public String toString() { return "(" + left + " " + op + " " + right + ")"; }
}