package interpreter.model.stmt;

import interpreter.exceptions.FileException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StmtException;
import interpreter.model.exp.ValueExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTests {
    @TempDir
    Path tempDir;

    private Path writeFile(String name, String content) throws MyException {
        try {
            Path file = tempDir.resolve(name);
            Files.writeString(file, content);
            return file;
        } catch (IOException e) {
            throw new FileException("could not create test file: " + e.getMessage());
        }
    }

    private ProgramState newStateWithIntVar(String varName) {
        ProgramState state = new ProgramState(new PrintStmt(new ValueExp(new StringValue(""))));
        state.getSymTable().add(varName, new IntValue(0));
        return state;
    }

    @Test
    void testCloseOpenFile() throws MyException {
        Path file = writeFile("input.txt", "42\n");
        ProgramState state = newStateWithIntVar("x");
        new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        ProgramState result = new CloseRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        assertSame(state, result);
        // the reader stays in the file table, but it must no longer be usable
        assertThrows(FileException.class,
                () -> new ReadFileStmt(new ValueExp(new StringValue(file.toString())), "x").execute(state));
    }

    @Test
    void testCloseUnopenedFileThrows() {
        ProgramState state = newStateWithIntVar("x");
        Path file = tempDir.resolve("never.txt");

        CloseRFileStmt closeStmt = new CloseRFileStmt(new ValueExp(new StringValue(file.toString())));
        assertThrows(StmtException.class, () -> closeStmt.execute(state));
    }

    @Test
    void testCloseWithNonStringExpressionThrows() {
        ProgramState state = newStateWithIntVar("x");

        CloseRFileStmt closeStmt = new CloseRFileStmt(new ValueExp(new IntValue(1)));
        assertThrows(StmtException.class, () -> closeStmt.execute(state));
    }

    @Test
    void testToString() {
        CloseRFileStmt stmt = new CloseRFileStmt(new ValueExp(new StringValue("input.txt")));
        assertNotNull(stmt.toString());
    }
}
