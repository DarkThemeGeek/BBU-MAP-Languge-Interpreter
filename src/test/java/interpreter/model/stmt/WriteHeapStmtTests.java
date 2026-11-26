package interpreter.model.stmt;

import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.NotAllocateOnHeapException;
import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.exp.ValueExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WriteHeapStmtTests {
    @Test
    void testWriteHeapUpdatesValue() throws MyException {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getSymTable().add("v", new RefValue(0, new IntType()));
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);

        RefValue ref = (RefValue) state.getSymTable().get("v");
        assertEquals(new IntValue(30), state.getHeap().get(ref.address()));
    }

    @Test
    void testWriteHeapUndeclaredVariableThrows() {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        assertThrows(VariableNotDefinedException.class, () -> stmt.execute(state));
    }

    @Test
    void testWriteHeapNonRefVariableThrows() {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getSymTable().add("v", new IntValue(0));
        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        assertThrows(InvalidTypeException.class, () -> stmt.execute(state));
    }

    @Test
    void testWriteHeapNotAllocatedThrows() {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getSymTable().add("v", new RefValue(99, new IntType()));
        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        assertThrows(NotAllocateOnHeapException.class, () -> stmt.execute(state));
    }

    @Test
    void testWriteHeapTypeMismatchThrows() throws MyException {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getSymTable().add("v", new RefValue(0, new IntType()));
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(InvalidTypeException.class, () -> stmt.execute(state));

        RefValue ref = (RefValue) state.getSymTable().get("v");
        assertEquals(new IntValue(20), state.getHeap().get(ref.address()));
    }

    @Test
    void testToString() {
        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        assertEquals("writeHeap(v, 30)", stmt.toString());
    }
}
