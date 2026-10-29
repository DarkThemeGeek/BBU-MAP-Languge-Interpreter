package interpreter.model.stmt;

import interpreter.model.prgState.ProgramState;

public record CompStmt(IStmt first, IStmt second)implements  IStmt {
    @Override
    public ProgramState execute(ProgramState state) {
        state.getExeStack().push(second);
        state.getExeStack().push(first);
        return state;
    }
    @Override public String toString() { return "(" + first + "; " + second + ")"; }
}
