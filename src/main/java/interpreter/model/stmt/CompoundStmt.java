package interpreter.model.stmt;

import interpreter.model.prgState.ProgramState;

public record CompoundStmt(IStmt firstStmt, IStmt secondStmt)implements  IStmt {
    @Override
    public ProgramState execute(ProgramState programState) {
        firstStmt.execute(programState);
        secondStmt.execute(programState);
        return programState;
    }

    @Override
    public String toString() {
        return firstStmt.toString() + " ; "+secondStmt.toString();
    }
}
