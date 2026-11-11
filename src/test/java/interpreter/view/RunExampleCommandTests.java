package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.exp.ValueExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RunExampleCommandTests {
    @TempDir
    Path tempDir;

    private Controller controllerFor(ProgramState state) {
        Repo repo = new Repo(state, tempDir.resolve("log.txt").toString());
        return new Controller(repo, false);
    }

    private String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return captured.toString(StandardCharsets.UTF_8);
    }

    @Test
    void testExecuteRunsExample() {
        Controller controller = controllerFor(ProgramState.of(Example.ex1));
        RunExampleCommand command = new RunExampleCommand("1", "example 1", controller);

        command.execute();

        ProgramState state = controller.getRepo().getProgramState();
        assertEquals(1, state.getOut().size());
        assertEquals(2, ((IntValue) state.getOut().get(0)).val());
    }

    @Test
    void testExecuteTwiceRunsExampleOnlyOnce() {
        Controller controller = controllerFor(ProgramState.of(Example.ex1));
        RunExampleCommand command = new RunExampleCommand("1", "example 1", controller);
        command.execute();

        String output = captureOutput(command::execute);

        ProgramState state = controller.getRepo().getProgramState();
        assertEquals(1, state.getOut().size());
        assertTrue(output.contains("already been executed"));
    }

    @Test
    void testExecuteCatchesMyExceptionAndPrintsMessage() {
        Controller controller = controllerFor(ProgramState.of(
                new AssignStmt("x", new ValueExp(new IntValue(1)))));
        RunExampleCommand command = new RunExampleCommand("1", "bad example", controller);

        String output = captureOutput(command::execute);

        assertTrue(output.contains("Exception:"));
        assertTrue(output.contains("message:"));
    }

    @Test
    void testCommandExposesKeyAndDescription() {
        Controller controller = controllerFor(ProgramState.of(Example.ex1));
        RunExampleCommand command = new RunExampleCommand("3", "example 3", controller);

        assertEquals("3", command.getKey());
        assertEquals("example 3", command.getDescription());
    }

    @Test
    void testExitCommandExposesKeyAndDescription() {
        ExitCommand command = new ExitCommand("0", "exit");

        assertEquals("0", command.getKey());
        assertEquals("exit", command.getDescription());
    }
}
