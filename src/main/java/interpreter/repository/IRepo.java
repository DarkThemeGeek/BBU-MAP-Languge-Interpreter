package interpreter.repository;

import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.IStmt;

import java.util.List;

public interface IRepo {
    public ProgramState getProgramState();
    public void LoadProgram(IStmt program);
    public void UpdateState(ProgramState programState);
    public List<ProgramState> getSavedProgramStates(List<ProgramState> savedProgramStates);
}
