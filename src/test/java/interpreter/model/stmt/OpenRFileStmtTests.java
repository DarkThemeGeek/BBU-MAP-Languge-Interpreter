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

class OpenRFileStmtTests {
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

    private ProgramState newState() {
        return new ProgramState(new PrintStmt(new ValueExp(new StringValue(""))));
    }

    @Test
    void testOpenExistingFile() throws MyException {
        Path file = writeFile("input.txt", "42\n");
        ProgramState state = newState();

        ProgramState result = new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        assertSame(state, result);
        assertTrue(state.getFileTable().containsKey(new StringValue(file.toString())));
        assertEquals(1, state.getFileTable().size());
    }

    @Test
    void testOpenAlreadyOpenFileThrows() throws MyException {
        Path file = writeFile("input.txt", "42\n");
        ProgramState state = newState();
        OpenRFileStmt openStmt = new OpenRFileStmt(new ValueExp(new StringValue(file.toString())));
        openStmt.execute(state);

        assertThrows(StmtException.class, () -> openStmt.execute(state));
    }

    @Test
    void testOpenNonExistentFileThrows() {
        ProgramState state = newState();
        Path missing = tempDir.resolve("missing.txt");

        assertThrows(StmtException.class,
                () -> new OpenRFileStmt(new ValueExp(new StringValue(missing.toString()))).execute(state));
        assertTrue(state.getFileTable().isEmpty());
    }

    @Test
    void testOpenWithNonStringExpressionThrows() {
        ProgramState state = newState();

        assertThrows(StmtException.class,
                () -> new OpenRFileStmt(new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void testToString() {
        OpenRFileStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue("input.txt")));
        assertNotNull(stmt.toString());
    }
}
