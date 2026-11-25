package interpreter.model.exp;

import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.MyException;

class LogicalExpTests {

    @Test
    void testAndOperator() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        LogicalExp exp1 = new LogicalExp(LogicalOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertTrue(((BoolValue) exp1.eval(symTable, heap)).val());

        LogicalExp exp2 = new LogicalExp(LogicalOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertFalse(((BoolValue) exp2.eval(symTable, heap)).val());

        LogicalExp exp3 = new LogicalExp(LogicalOp.AND, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false)));
        assertFalse(((BoolValue) exp3.eval(symTable, heap)).val());
    }

    @Test
    void testOrOperator() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        LogicalExp exp1 = new LogicalExp(LogicalOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertTrue(((BoolValue) exp1.eval(symTable, heap)).val());

        LogicalExp exp2 = new LogicalExp(LogicalOp.OR, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false)));
        assertFalse(((BoolValue) exp2.eval(symTable, heap)).val());

        LogicalExp exp3 = new LogicalExp(LogicalOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertTrue(((BoolValue) exp3.eval(symTable, heap)).val());
    }

    @Test
    void testWrongOperandTypeFirstOperandThrows() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        LogicalExp exp = new LogicalExp(LogicalOp.AND, new ValueExp(new IntValue(1)), new ValueExp(new BoolValue(true)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void testWrongOperandTypeSecondOperandThrows() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        LogicalExp exp = new LogicalExp(LogicalOp.OR, new ValueExp(new BoolValue(false)), new ValueExp(new IntValue(0)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void testBothOperandsWrongTypeThrows() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        LogicalExp exp = new LogicalExp(LogicalOp.AND, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(2)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void testWithVariableExp() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        symTable.add("b1", new BoolValue(true));
        symTable.add("b2", new BoolValue(false));
        LogicalExp exp = new LogicalExp(LogicalOp.OR, new VariableExp("b1"), new VariableExp("b2"));
        assertTrue(((BoolValue) exp.eval(symTable, heap)).val());
    }

    @Test
    void testUndefinedVariableInLogicalExpThrows() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        LogicalExp exp = new LogicalExp(LogicalOp.AND, new VariableExp("flag"), new ValueExp(new BoolValue(true)));
        assertThrows(VariableNotDefinedException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void testAllOperators() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        symTable.add("t", new BoolValue(true));
        symTable.add("f", new BoolValue(false));

        assertFalse(((BoolValue) new LogicalExp(LogicalOp.AND, new VariableExp("t"), new VariableExp("f")).eval(symTable, heap)).val());
        assertTrue(((BoolValue) new LogicalExp(LogicalOp.OR, new VariableExp("t"), new VariableExp("f")).eval(symTable, heap)).val());
    }

    @Test
    void testToString() {
        LogicalExp exp = new LogicalExp(LogicalOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertNotNull(exp.toString());
    }
}
