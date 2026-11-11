package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.prgState.ProgramState;

public interface IStmt {
    ProgramState execute(ProgramState programState) throws MyException;
    public String toString();
}
