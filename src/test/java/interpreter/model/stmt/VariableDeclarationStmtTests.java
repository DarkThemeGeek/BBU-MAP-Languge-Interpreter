package interpreter.model.stmt;

import interpreter.exceptions.VariableAlreadyDefinedException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.MyException;

class VariableDeclarationStmtTests {
    @Test
    void testRedeclaredVariableThrows() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))), new MyDictionary<StringValue, BufferedReader>());
        
        VariableDeclarationStmt declStmt1 = new VariableDeclarationStmt("x", new IntType());
        declStmt1.execute(state);
        
        VariableDeclarationStmt declStmt2 = new VariableDeclarationStmt("x", new IntType());
        assertThrows(VariableAlreadyDefinedException.class, () -> declStmt2.execute(state));
    }

    @Test
    void testDeclarationOfIntVariable() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))), new MyDictionary<StringValue, BufferedReader>());
        
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt("x", new IntType());
        ProgramState result = declStmt.execute(state);
        assertNotNull(result);
        assertTrue(symTable.containsKey("x"));
    }

    @Test
    void testDeclarationOfBoolVariable() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))), new MyDictionary<StringValue, BufferedReader>());
        
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt("flag", new BoolType());
        declStmt.execute(state);
        assertTrue(symTable.containsKey("flag"));
    }

    @Test
    void testEquality() {
        VariableDeclarationStmt stmt1 = new VariableDeclarationStmt("x", new IntType());
        VariableDeclarationStmt stmt2 = new VariableDeclarationStmt("x", new IntType());
        assertEquals(stmt1, stmt2);
    }

    @Test
    void testToString() {
        VariableDeclarationStmt stmt = new VariableDeclarationStmt("var", new IntType());
        assertNotNull(stmt.toString());
    }
}
