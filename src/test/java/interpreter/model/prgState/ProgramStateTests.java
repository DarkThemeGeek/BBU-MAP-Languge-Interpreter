package interpreter.model.prgState;

import interpreter.exceptions.FileException;
import interpreter.exceptions.MyException;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.OpenRFileStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.StringValue;
import interpreter.view.Example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ProgramStateTests {
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

    private void assertSectionsInOrder(String log) {
        int exeStack = log.indexOf("ExeStack:");
        int symTable = log.indexOf("SymTable:");
        int out = log.indexOf("Out:");
        int fileTable = log.indexOf("FileTable:");

        assertTrue(exeStack >= 0, "missing ExeStack section");
        assertTrue(symTable > exeStack, "SymTable must come after ExeStack");
        assertTrue(out > symTable, "Out must come after SymTable");
        assertTrue(fileTable > out, "FileTable must come after Out");
    }

    @Test
    void testToLogStringContainsFourSectionsInOrder() {
        ProgramState state = ProgramState.of(Example.ex1);
        assertSectionsInOrder(state.toLogString());
    }

    @Test
    void testToLogStringSectionsInOrderAfterExecution() throws MyException {
        ProgramState state = ProgramState.of(Example.ex1);
        IStmt stmt = state.getExeStack().pop();
        stmt.execute(state);
        assertSectionsInOrder(state.toLogString());
    }

    @Test
    void testToLogStringListsOpenFilesInFileTable() throws MyException {
        Path file = writeFile("input.txt", "42\n");
        ProgramState state = new ProgramState(new PrintStmt(new ValueExp(new StringValue(""))));
        new OpenRFileStmt(new ValueExp(new StringValue(file.toString()))).execute(state);

        String log = state.toLogString();
        assertSectionsInOrder(log);
        int fileTable = log.indexOf("FileTable:");
        assertTrue(log.substring(fileTable).contains(file.toString()));
    }

    @Test
    void testOfPutsProgramOnExecutionStack() {
        ProgramState state = ProgramState.of(Example.ex1);
        assertEquals(1, state.getExeStack().size());
        assertTrue(state.getSymTable().isEmpty());
        assertTrue(state.getOut().isEmpty());
        assertTrue(state.getFileTable().isEmpty());
    }
}
