package interpreter.model.stmt;


import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StmtException;
import interpreter.exceptions.VariableAlreadyDefinedException;
import interpreter.model.exp.IExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record NewStmt(String varName, IExp exp) implements IStmt { // S11
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symTable = state.getSymTable();
        if (!symTable.containsKey(varName))
            throw new VariableAlreadyDefinedException("new: " + varName + " is not declared");
        if (!(symTable.get(varName) instanceof RefValue ref))
            throw new StmtException("new: " + varName + " is not a reference");
        IValue value = exp.eval(symTable, state.getHeap());
        if (!value.getType().equals(ref.locationType()))
            throw new InvalidTypeException("new: " + exp + " has type " + value.getType()
                    + ", expected " + ref.locationType());
        int address = state.getHeap().allocate(value);
        symTable.add(varName, new RefValue(address, ref.locationType()));
        return state;
    }
    @Override public String toString() { return "new(" + varName + ", " + exp + ")"; }
}
