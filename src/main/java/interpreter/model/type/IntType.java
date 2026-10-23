package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public class IntType implements IType {
    public static final IntType INSTANCE=new IntType();
    private IntType(){};

    @Override public IValue defaultValue() { return new IntValue(0); }
    @Override public String toString() { return "int"; }

}