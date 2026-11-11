package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StmtException;
import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepo;
import interpreter.repository.Repo;
import interpreter.view.old.ILogger;
import interpreter.view.old.Logger;

public class Controller {
    private IRepo repo;
    private boolean displayFlag;
    public final IRepo getRepo(){
        return repo;
    }
    public boolean isDisplayFlag() {
        return displayFlag;
    }
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag=displayFlag;
    }
    public Controller(boolean displayFlag) {
        repo =new Repo(null,"");
        this.displayFlag=displayFlag;
    }
    public Controller(Repo repo,boolean displayFlag){
        this.repo =repo;
        this.displayFlag=displayFlag;
    }

    public ProgramState oneStep() throws MyException {
        ProgramState state = repo.getProgramState();

        var stack = state.getExeStack();
        if (stack.isEmpty()) throw new StmtException("program state stack is empty");

        IStmt crtStmt = stack.pop();

        return crtStmt.execute(state);
    }
    public void allStep() throws MyException {
        ProgramState state = repo.getProgramState();
        if(displayFlag){
            repo.logProgramStateExec();
            // repo.logToConsole();
        }; // only if the display flag is on

        while (!state.getExeStack().isEmpty()) {

            oneStep();
            if(displayFlag){
                repo.logProgramStateExec();
              //  repo.logToConsole();
            };
        }
    }
    public void LoadProgram(IStmt program){
        repo.loadProgram(program);
    }


}
