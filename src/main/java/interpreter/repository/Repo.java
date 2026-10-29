package interpreter.repository;

import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.IStmt;

import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{
    private List<ProgramState> savedProgramStates;
    private ProgramState currentProgramState;

    public Repo(ProgramState programState) {
        currentProgramState=programState;
        savedProgramStates=new ArrayList<>();
    }
    public void UpdateState(ProgramState programState){
        savedProgramStates.add(currentProgramState);
        currentProgramState=programState;
    }

    public ProgramState getProgramState() {
        return currentProgramState;
    }

    @Override
    public void LoadProgram(IStmt program) {
        savedProgramStates=new ArrayList<>();
        //creating a program state
        currentProgramState=new ProgramState(new MyDictionary<>(),new MyStack<>(),new MyList<>(),program);
    }

    public final List<ProgramState> getSavedProgramStates(List<ProgramState> savedProgramStates) {
        return savedProgramStates;
    }
}
