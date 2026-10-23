package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.IntType;

public record IntValue(int val) implements IValue {
    @Override public IType getType() { return  IntType.INSTANCE; }
    @Override public String toString() { return Integer.toString(val); }
}
