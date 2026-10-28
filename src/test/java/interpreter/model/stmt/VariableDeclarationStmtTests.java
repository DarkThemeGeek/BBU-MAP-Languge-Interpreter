package interpreter.model.stmt;

import interpreter.exceptions.VariableAlreadyDefinedException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableDeclarationStmtTests {
    @Test
    void testRedeclaredVariableThrows() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))));
        
        VariableDeclarationStmt declStmt1 = new VariableDeclarationStmt(new IntType(), "x");
        declStmt1.execute(state);
        
        VariableDeclarationStmt declStmt2 = new VariableDeclarationStmt(new IntType(), "x");
        assertThrows(VariableAlreadyDefinedException.class, () -> declStmt2.execute(state));
    }

    @Test
    void testDeclarationOfIntVariable() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))));
        
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt(new IntType(), "x");
        ProgramState result = declStmt.execute(state);
        assertNotNull(result);
        assertTrue(symTable.containsKey("x"));
    }

    @Test
    void testDeclarationOfBoolVariable() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))));
        
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt(new BoolType(), "flag");
        declStmt.execute(state);
        assertTrue(symTable.containsKey("flag"));
    }

    @Test
    void testEquality() {
        VariableDeclarationStmt stmt1 = new VariableDeclarationStmt(new IntType(), "x");
        VariableDeclarationStmt stmt2 = new VariableDeclarationStmt(new IntType(), "x");
        assertEquals(stmt1, stmt2);
    }

    @Test
    void testToString() {
        VariableDeclarationStmt stmt = new VariableDeclarationStmt(new IntType(), "var");
        assertNotNull(stmt.toString());
    }
}
