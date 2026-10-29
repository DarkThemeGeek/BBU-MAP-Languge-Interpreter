package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StmtException;
import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepo;
import interpreter.repository.Repo;
import interpreter.view.ILogger;
import interpreter.view.IView;
import interpreter.view.Logger;
import interpreter.view.View;

public class Controller {
    private IRepo repo;
    private ILogger logger;


    public boolean isDisplayFlag() {
        return logger.isDisplayFlag();
    }
    public void setDisplayFlag(boolean displayFlag) {
        this.logger.setDisplayFlag(displayFlag);
    }
    public Controller(boolean displayFlag) {
        repo =new Repo(null);
        logger=new Logger();
        this.logger.setDisplayFlag(displayFlag);
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
        logger.log(state.toString()); // only if the display flag is on

        while (!state.getExeStack().isEmpty()) {
            oneStep();
            logger.log(state.toString());
        }
    }
    public void LoadProgram(IStmt program){
        repo.LoadProgram(program);
    }


}
