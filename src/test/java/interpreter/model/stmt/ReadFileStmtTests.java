package interpreter.model.stmt;

import interpreter.exceptions.FileException;
import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.VariableNotDefinedException;
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

class ReadFileStmtTests {
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
    void testReadIntegerFromFile() throws MyException {
        Path file = writeFile("numbers.txt", "42\n");
        ProgramState state = newStateWithIntVar("x");
        new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        ProgramState result = new ReadFileStmt(new ValueExp(new StringValue(file.toString())), "x").execute(state);

        assertSame(state, result);
        assertEquals(42, ((IntValue) state.getSymTable().get("x")).val());
    }

    @Test
    void testReadMultipleLinesSequentially() throws MyException {
        Path file = writeFile("numbers.txt", "10\n20\n");
        ProgramState state = newStateWithIntVar("x");
        new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);
        ReadFileStmt readStmt = new ReadFileStmt(new ValueExp(new StringValue(file.toString())), "x");

        readStmt.execute(state);
        assertEquals(10, ((IntValue) state.getSymTable().get("x")).val());

        readStmt.execute(state);
        assertEquals(20, ((IntValue) state.getSymTable().get("x")).val());
    }

    @Test
    void testReadPastEndOfFileGivesZero() throws MyException {
        Path file = writeFile("empty.txt", "");
        ProgramState state = newStateWithIntVar("x");
        new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        new ReadFileStmt(new ValueExp(new StringValue(file.toString())), "x").execute(state);

        assertEquals(0, ((IntValue) state.getSymTable().get("x")).val());
    }

    @Test
    void testReadIntoUndeclaredVariableThrows() throws MyException {
        Path file = writeFile("numbers.txt", "42\n");
        ProgramState state = new ProgramState(new PrintStmt(new ValueExp(new StringValue(""))));
        new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        ReadFileStmt readStmt = new ReadFileStmt(new ValueExp(new StringValue(file.toString())), "missing");
        assertThrows(VariableNotDefinedException.class, () -> readStmt.execute(state));
    }

    @Test
    void testReadIntoNonIntegerVariableThrows() throws MyException {
        Path file = writeFile("numbers.txt", "42\n");
        ProgramState state = new ProgramState(new PrintStmt(new ValueExp(new StringValue(""))));
        state.getSymTable().add("s", new StringValue(""));
        new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        ReadFileStmt readStmt = new ReadFileStmt(new ValueExp(new StringValue(file.toString())), "s");
        assertThrows(InvalidTypeException.class, () -> readStmt.execute(state));
    }

    @Test
    void testReadWithNonStringPathThrows() {
        ProgramState state = newStateWithIntVar("x");

        ReadFileStmt readStmt = new ReadFileStmt(new ValueExp(new IntValue(1)), "x");
        assertThrows(InvalidTypeException.class, () -> readStmt.execute(state));
    }

    @Test
    void testReadFromUnopenedFileThrows() {
        ProgramState state = newStateWithIntVar("x");

        ReadFileStmt readStmt = new ReadFileStmt(new ValueExp(new StringValue(tempDir.resolve("never.txt").toString())), "x");
        assertThrows(FileException.class, () -> readStmt.execute(state));
    }

    @Test
    void testReadNonNumericContentThrows() throws MyException {
        Path file = writeFile("text.txt", "not a number\n");
        ProgramState state = newStateWithIntVar("x");
        new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        ReadFileStmt readStmt = new ReadFileStmt(new ValueExp(new StringValue(file.toString())), "x");
        assertThrows(FileException.class, () -> readStmt.execute(state));
    }
}
