package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.IStmt;

import java.util.List;

public interface IRepo {
    public ProgramState getProgramState();
    public void loadProgram(IStmt program);
    public void updateState(ProgramState programState);
    public void logProgramStateExec() throws MyException;
    public List<ProgramState> getSavedProgramStates(List<ProgramState> savedProgramStates);
}
