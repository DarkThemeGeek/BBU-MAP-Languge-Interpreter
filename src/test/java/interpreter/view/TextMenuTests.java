package interpreter.view;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TextMenuTests {
    private String runMenuAndCaptureOutput(TextMenu menu, String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));
            // show() loops forever; once the scripted input is exhausted it throws
            assertThrows(NoSuchElementException.class, menu::show);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return captured.toString(StandardCharsets.UTF_8);
    }

    @Test
    void testCommandsArePrintedInInsertionOrder() {
        TextMenu menu = new TextMenu();
        List<String> executed = new ArrayList<>();
        menu.addCommand(new Command("1", "first example") {
            @Override public void execute() { executed.add("first"); }
        });
        menu.addCommand(new Command("2", "second example") {
            @Override public void execute() { executed.add("second"); }
        });

        String output = runMenuAndCaptureOutput(menu, "1\n");

        assertEquals(List.of("first"), executed);
        int first = output.indexOf("first example");
        int second = output.indexOf("second example");
        assertTrue(first >= 0, "first command should be printed");
        assertTrue(second > first, "commands should be printed in insertion order");
    }

    @Test
    void testInvalidOptionPrintsMessage() {
        TextMenu menu = new TextMenu();
        menu.addCommand(new Command("1", "first example") {
            @Override public void execute() { fail("no command should be executed"); }
        });

        String output = runMenuAndCaptureOutput(menu, "99\n");

        assertTrue(output.contains("Invalid option"));
    }

    @Test
    void testCommandWithSameKeyIsReplaced() {
        TextMenu menu = new TextMenu();
        menu.addCommand(new Command("1", "old description") {
            @Override public void execute() { }
        });
        menu.addCommand(new Command("1", "new description") {
            @Override public void execute() { }
        });

        String output = runMenuAndCaptureOutput(menu, "");

        assertFalse(output.contains("old description"));
        assertTrue(output.contains("new description"));
    }

    @Test
    void testCommandExposesKeyAndDescription() {
        Command command = new Command("7", "description") {
            @Override public void execute() { }
        };
        assertEquals("7", command.getKey());
        assertEquals("description", command.getDescription());
    }
}
