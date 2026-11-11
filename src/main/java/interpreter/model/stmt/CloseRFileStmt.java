package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StmtException;
import interpreter.model.exp.IExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt{
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StmtException("openRFile: " + exp + " is not a string");
        var fileTable = state.getFileTable();
        if (!fileTable.containsKey(fileName))
            throw new StmtException("openRFile: " + fileName + " is not open");
        try {
            // no try-with-resources: the file must stay open until closeRFile
            var fileReader=fileTable.get(fileName);
            fileReader.close();

        } catch (IOException e) {
            throw new StmtException("openRFile: cannot close " + fileName + ": " + e.getMessage());
        }
        return state;
    }
    @Override public String toString() { return "openRFile(" + exp + ")"; }
}
