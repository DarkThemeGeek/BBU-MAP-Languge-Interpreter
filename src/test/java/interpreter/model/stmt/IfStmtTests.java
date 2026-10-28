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
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTests {
    @Test
    void testNonBooleanIfConditionThrows() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();
        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))));
        
        // If condition is an integer (not boolean)
        IfStmt ifStmt = new IfStmt(new ValueExp(new IntValue(1)), new PrintStmt(new ValueExp(new IntValue(1))), new PrintStmt(new ValueExp(new IntValue(2))));
        assertThrows(InvalidTypeException.class, () -> ifStmt.execute(state));
    }

    @Test
    void testIfConditionWithWrongOperandTypes() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        // Logical expression but with wrong types
        interpreter.model.exp.LogicalExp badCond = new interpreter.model.exp.LogicalExp(
            interpreter.model.exp.LogicalOp.AND, 
            new ValueExp(new IntValue(1)), 
            new ValueExp(new BoolValue(true))
        );
        assertThrows(InvalidTypeException.class, () -> badCond.eval(symTable));
    }

    @Test
    void testIfConditionWithDivisionByZero() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp badExp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        assertThrows(interpreter.exceptions.DivisionByZeroException.class, () -> badExp.eval(symTable));
    }

    @Test
    void testIfConditionWithUndefinedVariable() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        VariableExp varExp = new VariableExp("undefinedVar");
        assertThrows(VariableNotDefinedException.class, () -> varExp.eval(symTable));
    }

    @Test
    void testIfWithBooleanConditionTrue() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();

        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))));
        
        IfStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(true)), new PrintStmt(new ValueExp(new IntValue(1))), new PrintStmt(new ValueExp(new IntValue(2))));
        ProgramState result = ifStmt.execute(state);
        assertNotNull(result);
    }

    @Test
    void testIfWithBooleanConditionFalse() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        MyStack<IStmt> exeStack = new MyStack<>();
        MyList<IValue> out = new MyList<>();

        ProgramState state = new ProgramState(symTable, exeStack, out, new PrintStmt(new ValueExp(new IntValue(0))));
        
        IfStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(false)), new PrintStmt(new ValueExp(new IntValue(1))), new PrintStmt(new ValueExp(new IntValue(2))));
        ProgramState result = ifStmt.execute(state);
        assertNotNull(result);
    }

    @Test
    void testToString() {
        IfStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(true)), new PrintStmt(new ValueExp(new IntValue(1))), new PrintStmt(new ValueExp(new IntValue(2))));
        assertNotNull(ifStmt.toString());
    }
}
