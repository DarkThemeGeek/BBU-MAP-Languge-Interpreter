package interpreter.model.stmt;

import interpreter.exceptions.*;
import interpreter.model.exp.IExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record WriteHeapStmt(String varName, IExp exp) implements IStmt { // S11
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symTable = state.getSymTable();
        var heap= state.getHeap();
        if (!symTable.containsKey(varName))
            throw new VariableNotDefinedException("wH: " + varName + " is not declared");
        if (!(symTable.get(varName) instanceof RefValue ref))
            throw new InvalidTypeException("wH: " + varName + " is not a reference");
        if(!heap.isDefined(ref.address()))
            throw new NotAllocateOnHeapException("wh: "+ varName + "is not allocated on the heap");
        IValue value = exp.eval(symTable, state.getHeap());
        if (!value.getType().equals(ref.locationType()))
            throw new InvalidTypeException("wH: " + exp + " has type " + value.getType()
                    + ", expected " + ref.locationType());

        heap.update(ref.address(),value);
        return state;
    }
    @Override public String toString() { return "writeHeap(" + varName + ", " + exp + ")"; }
}
