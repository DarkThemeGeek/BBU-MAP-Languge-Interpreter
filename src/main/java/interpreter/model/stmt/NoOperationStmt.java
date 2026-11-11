package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.prgState.ProgramState;

public record NoOperationStmt(IStmt stmt) implements IStmt {
    @Override
    public ProgramState execute(ProgramState programStmt) throws MyException {
        return programStmt;
    }
    @Override public String toString() {
        return stmt.toString();
    }
}
