package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;



public record VariableExp (String variableName) implements  IExp{

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable)  {
        if(!symTable.containsKey(variableName)){
            throw new VariableNotDefinedException("variable" + variableName +"is not defined");
        }
        return symTable.get(variableName);
    }

    public IType typecheck(IDictionary<String, IType> typeEnv) {
        return typeEnv.get(variableName);
    }
}
