package interpreter.model.stmt;

import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.MyException;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VariableExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.CompStmt;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTests {
    @Test
    void testWhileFalseDoesNotPushBody() throws MyException {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getExeStack().pop(); // clear initial stmt
        WhileStmt whileStmt = new WhileStmt(new ValueExp(new BoolValue(false)), new PrintStmt(new ValueExp(new IntValue(1))));

        whileStmt.execute(state);

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testWhileTruePushesSelfAndBody() throws MyException {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        state.getExeStack().pop(); // clear initial stmt
        IStmt body = new PrintStmt(new ValueExp(new IntValue(1)));
        WhileStmt whileStmt = new WhileStmt(new ValueExp(new BoolValue(true)), body);

        whileStmt.execute(state);

        assertEquals(2, state.getExeStack().size());
        assertSame(body, state.getExeStack().pop());
        assertSame(whileStmt, state.getExeStack().pop());
    }

    @Test
    void testWhileNonBooleanConditionThrows() {
        ProgramState state = ProgramState.of(new PrintStmt(new ValueExp(new IntValue(0))));
        WhileStmt whileStmt = new WhileStmt(new ValueExp(new IntValue(1)), new PrintStmt(new ValueExp(new IntValue(1))));
        assertThrows(InvalidTypeException.class, () -> whileStmt.execute(state));
    }

    @Test
    void testWhileExecutesWithController() throws MyException {
        // int v; v=2; while(v>0) { print(v); v=v-1 }
        IStmt program = new CompStmt(new VariableDeclarationStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new WhileStmt(new RelationalExp(RelOp.GT, new VariableExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VariableExp("v")),
                                        new AssignStmt("v", new interpreter.model.exp.ArithExp(
                                                interpreter.model.exp.ArithOp.SUB,
                                                new VariableExp("v"),
                                                new ValueExp(new IntValue(1))))))));

        interpreter.controller.Controller controller = new interpreter.controller.Controller(false);
        controller.LoadProgram(program);
        controller.allStep();

        ProgramState state = controller.getRepo().getProgramState();
        assertEquals(2, state.getOut().size());
        assertEquals(2, ((IntValue) state.getOut().get(0)).val());
        assertEquals(1, ((IntValue) state.getOut().get(1)).val());
    }

    @Test
    void testToString() {
        WhileStmt stmt = new WhileStmt(new ValueExp(new BoolValue(true)), new PrintStmt(new ValueExp(new IntValue(1))));
        assertEquals("(while (true) print(1))", stmt.toString());
    }
}
