package interpreter.model.exp;

import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicalExpTests {
    @Test
    void testEqualOperator() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        LogicalExp exp = new LogicalExp(LogicalOp.EQUAL, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true)));
        IValue result = exp.eval(symTable);
        assertTrue(result instanceof BoolValue);
        assertTrue(((BoolValue) result).val());
    }

    @Test
    void testNotEqualOperator() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        LogicalExp exp = new LogicalExp(LogicalOp.NOT_EQUAL, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        IValue result = exp.eval(symTable);
        assertTrue(((BoolValue) result).val());
    }

    @Test
    void testAndOperator() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        LogicalExp exp1 = new LogicalExp(LogicalOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertTrue(((BoolValue) exp1.eval(symTable)).val());

        LogicalExp exp2 = new LogicalExp(LogicalOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertFalse(((BoolValue) exp2.eval(symTable)).val());

        LogicalExp exp3 = new LogicalExp(LogicalOp.AND, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false)));
        assertFalse(((BoolValue) exp3.eval(symTable)).val());
    }

    @Test
    void testOrOperator() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        LogicalExp exp1 = new LogicalExp(LogicalOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertTrue(((BoolValue) exp1.eval(symTable)).val());

        LogicalExp exp2 = new LogicalExp(LogicalOp.OR, new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false)));
        assertFalse(((BoolValue) exp2.eval(symTable)).val());

        LogicalExp exp3 = new LogicalExp(LogicalOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertTrue(((BoolValue) exp3.eval(symTable)).val());
    }

    @Test
    void testWrongOperandTypeFirstOperandThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        LogicalExp exp = new LogicalExp(LogicalOp.AND, new ValueExp(new IntValue(1)), new ValueExp(new BoolValue(true)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable));
    }

    @Test
    void testWrongOperandTypeSecondOperandThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        LogicalExp exp = new LogicalExp(LogicalOp.OR, new ValueExp(new BoolValue(false)), new ValueExp(new IntValue(0)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable));
    }

    @Test
    void testBothOperandsWrongTypeThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        LogicalExp exp = new LogicalExp(LogicalOp.EQUAL, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(2)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable));
    }

    @Test
    void testWithVariableExp() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.add("b1", new BoolValue(true));
        symTable.add("b2", new BoolValue(false));
        LogicalExp exp = new LogicalExp(LogicalOp.OR, new VariableExp("b1"), new VariableExp("b2"));
        assertTrue(((BoolValue) exp.eval(symTable)).val());
    }

    @Test
    void testUndefinedVariableInLogicalExpThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        LogicalExp exp = new LogicalExp(LogicalOp.AND, new VariableExp("flag"), new ValueExp(new BoolValue(true)));
        assertThrows(VariableNotDefinedException.class, () -> exp.eval(symTable));
    }

    @Test
    void testAllOperators() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.add("t", new BoolValue(true));
        symTable.add("f", new BoolValue(false));

        assertTrue(((BoolValue) new LogicalExp(LogicalOp.EQUAL, new VariableExp("t"), new ValueExp(new BoolValue(true))).eval(symTable)).val());
        assertTrue(((BoolValue) new LogicalExp(LogicalOp.NOT_EQUAL, new VariableExp("t"), new VariableExp("f")).eval(symTable)).val());
        assertFalse(((BoolValue) new LogicalExp(LogicalOp.AND, new VariableExp("t"), new VariableExp("f")).eval(symTable)).val());
        assertTrue(((BoolValue) new LogicalExp(LogicalOp.OR, new VariableExp("t"), new VariableExp("f")).eval(symTable)).val());
    }

    @Test
    void testToString() {
        LogicalExp exp = new LogicalExp(LogicalOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertNotNull(exp.toString());
    }
}
