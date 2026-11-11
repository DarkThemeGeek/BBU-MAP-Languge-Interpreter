package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepoException;
import interpreter.model.prgState.ProgramState;
import interpreter.view.Example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RepoTests {
    @TempDir
    Path tempDir;

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
    void testLogProgramStateExecWritesFourSectionsInOrder() throws MyException {
        Path logFile = tempDir.resolve("log.txt");
        Repo repo = new Repo(ProgramState.of(Example.ex1), logFile.toString());

        repo.logProgramStateExec();

        assertTrue(Files.exists(logFile));
        String log;
        try {
            log = Files.readString(logFile);
        } catch (IOException e) {
            throw new RepoException("could not read test log file: " + e.getMessage());
        }
        assertSectionsInOrder(log);
    }

    @Test
    void testLogProgramStateExecAppends() throws MyException {
        Path logFile = tempDir.resolve("log.txt");
        Repo repo = new Repo(ProgramState.of(Example.ex1), logFile.toString());

        repo.logProgramStateExec();
        repo.logProgramStateExec();

        String log;
        try {
            log = Files.readString(logFile);
        } catch (IOException e) {
            throw new RepoException("could not read test log file: " + e.getMessage());
        }
        assertEquals(2, countOccurrences(log, "ExeStack:"));
        assertEquals(2, countOccurrences(log, "FileTable:"));
    }

    @Test
    void testLogProgramStateExecToInvalidPathThrowsRepoException() {
        Path invalidPath = tempDir.resolve("missing-dir").resolve("log.txt");
        Repo repo = new Repo(ProgramState.of(Example.ex1), invalidPath.toString());

        assertThrows(RepoException.class, repo::logProgramStateExec);
    }

    @Test
    void testGetProgramStateReturnsCurrentState() {
        ProgramState state = ProgramState.of(Example.ex1);
        Repo repo = new Repo(state, tempDir.resolve("log.txt").toString());
        assertSame(state, repo.getProgramState());
    }

    @Test
    void testLoadProgramReplacesCurrentState() {
        Repo repo = new Repo(ProgramState.of(Example.ex1), tempDir.resolve("log.txt").toString());
        repo.loadProgram(Example.ex5);

        ProgramState state = repo.getProgramState();
        assertNotNull(state);
        assertEquals(Example.ex5.toString(), state.getExeStack().toList().get(0).toString());
    }

    private int countOccurrences(String text, String token) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(token, index)) != -1) {
            count++;
            index += token.length();
        }
        return count;
    }
}
