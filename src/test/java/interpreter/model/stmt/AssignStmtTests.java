package interpreter.model.stmt;

import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VariableExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.MyException;

class AssignStmtTests {
    @Test
    void testAssignmentToUndeclaredVariableThrows() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new NoOperationStmt(new PrintStmt(new ValueExp(new IntValue(0)))), new MyDictionary<StringValue, BufferedReader>());
        
        AssignStmt assignStmt = new AssignStmt("x", new ValueExp(new IntValue(5)));
        assertThrows(VariableNotDefinedException.class, () -> assignStmt.execute(state));
    }

    @Test
    void testAssignmentTypeMismatchThrows() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))), new MyDictionary<StringValue, BufferedReader>());
        
        // Declare x as int
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt("x", new IntType());
        declStmt.execute(state);

        
        // Try to assign boolean to int
        AssignStmt assignStmt = new AssignStmt("x", new ValueExp(new BoolValue(true)));
        assertThrows(InvalidTypeException.class, () -> assignStmt.execute(state));
    }

    @Test
    void testAssignmentWithWrongOperandTypeInExpression() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))), new MyDictionary<StringValue, BufferedReader>());
        
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt("x", new IntType());
        declStmt.execute(state);
        
        // Expression has wrong operand type
        ArithExp badExp = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(3)));
        assertThrows(InvalidTypeException.class, () -> badExp.eval(symTable));
    }

    @Test
    void testAssignmentWithDivisionByZero() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))), new MyDictionary<StringValue, BufferedReader>());
        
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt("x", new IntType());
        declStmt.execute(state);
        
        ArithExp badExp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        assertThrows(interpreter.exceptions.DivisionByZeroException.class, () -> badExp.eval(symTable));
    }

    @Test
    void testAssignmentWithUndefinedVariableInExpression() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))), new MyDictionary<StringValue, BufferedReader>());
        
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt("x", new IntType());
        declStmt.execute(state);
        
        VariableExp badExp = new VariableExp("y");
        assertThrows(VariableNotDefinedException.class, () -> badExp.eval(symTable));
    }

    @Test
    void testSuccessfulAssignment() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))), new MyDictionary<StringValue, BufferedReader>());
        
        VariableDeclarationStmt declStmt = new VariableDeclarationStmt("x", new IntType());
        declStmt.execute(state);
        
        AssignStmt assignStmt = new AssignStmt("x", new ValueExp(new IntValue(42)));
        ProgramState result = assignStmt.execute(state);
        assertNotNull(result);
        assertEquals(42, ((IntValue) symTable.get("x")).val());
    }

    @Test
    void testToString() {
        AssignStmt stmt = new AssignStmt("x", new ValueExp(new IntValue(5)));
        assertNotNull(stmt.toString());
    }
}
