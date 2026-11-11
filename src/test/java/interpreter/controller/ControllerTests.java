package interpreter.controller;

import interpreter.exceptions.FileException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StmtException;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.IntValue;
import interpreter.view.Example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTests {
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

    @Test
    void testRunExample1() throws MyException {
        Controller controller = new Controller(false);

        controller.LoadProgram(Example.ex1);
        controller.allStep();

        ProgramState state = controller.getRepo().getProgramState();
        assertNotNull(state);

        assertFalse(state.getOut().isEmpty());
        assertEquals(2, ((IntValue) state.getOut().get(0)).val());
    }

    @Test
    void testRunExample2() throws MyException {

        Controller controller = new Controller(false);

        controller.LoadProgram(Example.ex2);
        controller.allStep();

        ProgramState state = controller.getRepo().getProgramState();

        assertNotNull(state);
        assertFalse(state.getOut().isEmpty());
        assertEquals(18, ((IntValue) state.getOut().get(0)).val());
    }

    @Test
    void testRunExample3() throws MyException {
        Controller controller = new Controller(false);

        controller.LoadProgram(Example.ex3);
        controller.allStep();

        ProgramState state = controller.getRepo().getProgramState();

        assertNotNull(state);
        assertFalse(state.getOut().isEmpty());
        assertEquals(2, ((IntValue) state.getOut().get(0)).val());
    }

    @Test
    void testRunExample4() throws MyException {
        Path file = writeFile("test.in", "10\n20\n");
        Controller controller = new Controller(false);

        controller.LoadProgram(Example.fileExample(file.toString()));
        controller.allStep();

        ProgramState state = controller.getRepo().getProgramState();
        assertNotNull(state);
        assertEquals(2, state.getOut().size());
        assertEquals(10, ((IntValue) state.getOut().get(0)).val());
        assertEquals(20, ((IntValue) state.getOut().get(1)).val());
    }

    @Test
    void testRunExample4WithEmptyFile() throws MyException {
        Path file = writeFile("empty.in", "");
        Controller controller = new Controller(false);

        controller.LoadProgram(Example.fileExample(file.toString()));
        controller.allStep();

        ProgramState state = controller.getRepo().getProgramState();
        assertNotNull(state);
        assertEquals(2, state.getOut().size());
        assertEquals(0, ((IntValue) state.getOut().get(0)).val());
        assertEquals(0, ((IntValue) state.getOut().get(1)).val());
    }

    @Test
    void testRunExample5() throws MyException {
        Controller controller = new Controller(false);

        controller.LoadProgram(Example.ex5);
        controller.allStep();

        ProgramState state = controller.getRepo().getProgramState();
        assertNotNull(state);
        assertEquals(1, state.getOut().size());
        assertEquals(5, ((IntValue) state.getOut().get(0)).val());
    }

    @Test
    void testEmptyExecutionStackThrowsException() throws MyException {
        Controller controller = new Controller(false);
        controller.LoadProgram(Example.ex1);
        controller.allStep();
        assertThrows(StmtException.class, () -> controller.oneStep());
    }

    @Test
    void testControllerWithEmptyStackExceptionMessage() {
        StmtException ex = new StmtException("program state stack is empty");
        assertEquals("program state stack is empty", ex.getMessage());
    }
}
