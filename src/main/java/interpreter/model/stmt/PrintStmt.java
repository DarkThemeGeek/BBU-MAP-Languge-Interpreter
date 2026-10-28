package interpreter.model.stmt;

import interpreter.model.prgState.ProgramState;
import interpreter.model.adt.IList;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record PrintStmt(IExp exp) implements IStmt{

    @Override
    public ProgramState execute(ProgramState programState) {
        IList<IValue> out = programState.getOut();
        IValue value = exp.eval(programState.getSymTable());

        out.add(value);

        System.out.println(">>> PRINT: " + value.toString());

        return programState;
    }
    public String toString() {
        return "print(" + exp.toString() + ")";
    }

}
