package interpreter.model.stmt;

import interpreter.model.prgState.ProgramState;

public interface IStmt {
    ProgramState execute(ProgramState programState);
    public String toString();
}
