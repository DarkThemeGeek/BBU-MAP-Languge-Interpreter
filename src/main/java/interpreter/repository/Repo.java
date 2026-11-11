package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepoException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.IStmt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{
    private List<ProgramState> savedProgramStates;
    private ProgramState currentProgramState;
    private final String logFilePath;

    public Repo(ProgramState programState, String logFilePath) {
        currentProgramState=programState;
        this.logFilePath = logFilePath;
        savedProgramStates=new ArrayList<>();
    }
    public void updateState(ProgramState programState){
        savedProgramStates.add(currentProgramState);
        currentProgramState=programState;
    }

    public ProgramState getProgramState() {
        return currentProgramState;
    }

    @Override
    public void loadProgram(IStmt program) {
        savedProgramStates=new ArrayList<>();
        //creating a program state
        currentProgramState=new ProgramState(program);
    }
    @Override
    public void logProgramStateExec() throws MyException {
        try (var logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true)))) { // append
            logFile.println(getProgramState().toLogString());
        } catch (IOException e) {
            throw new RepoException("cannot write the log file " + logFilePath
                    + ": " + e.getMessage());
        }
    }

    @Override
    public void logToConsole() {
        IO.println(currentProgramState.toString());
    }

    public final List<ProgramState> getSavedProgramStates(List<ProgramState> savedProgramStates) {
        return savedProgramStates;
    }
}
