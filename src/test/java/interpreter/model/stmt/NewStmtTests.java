package interpreter.model.stmt;

import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.exp.ValueExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewStmtTests {
    @Test
    void testNewAllocatesAndUpdatesSymTable() throws MyException {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getSymTable().add("v", new RefValue(0, new IntType()));

        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        RefValue ref = (RefValue) state.getSymTable().get("v");
        assertEquals(1, ref.address());
        assertEquals(new IntType(), ref.locationType());
        assertEquals(new IntValue(20), state.getHeap().get(ref.address()));
    }

    @Test
    void testNewUndeclaredVariableThrows() {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        NewStmt stmt = new NewStmt("v", new ValueExp(new IntValue(20)));
        assertThrows(VariableNotDefinedException.class, () -> stmt.execute(state));
    }

    @Test
    void testNewNonRefVariableThrows() {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getSymTable().add("v", new IntValue(0));
        NewStmt stmt = new NewStmt("v", new ValueExp(new IntValue(20)));
        assertThrows(InvalidTypeException.class, () -> stmt.execute(state));
    }

    @Test
    void testNewTypeMismatchThrows() {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getSymTable().add("v", new RefValue(0, new IntType()));
        NewStmt stmt = new NewStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(InvalidTypeException.class, () -> stmt.execute(state));
        assertTrue(state.getHeap().getContent().isEmpty());
    }

    @Test
    void testToString() {
        NewStmt stmt = new NewStmt("v", new ValueExp(new IntValue(20)));
        assertEquals("new(v, 20)", stmt.toString());
    }
}
