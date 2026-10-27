package interpreter.model.exp;

import interpreter.exceptions.DivisionByZeroException;
import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTests {
    @Test
    void testAddOperator() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(3)));
        IValue result = exp.eval(symTable);
        assertTrue(result instanceof IntValue);
        assertEquals(8, ((IntValue) result).val());
    }

    @Test
    void testSubOperator() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.SUB, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(4)));
        IValue result = exp.eval(symTable);
        assertEquals(6, ((IntValue) result).val());
    }

    @Test
    void testMulOperator() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(3)));
        IValue result = exp.eval(symTable);
        assertEquals(21, ((IntValue) result).val());
    }

    @Test
    void testDivOperator() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(2)));
        IValue result = exp.eval(symTable);
        assertEquals(5, ((IntValue) result).val());
    }

    @Test
    void testDivisionByZeroThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        assertThrows(DivisionByZeroException.class, () -> exp.eval(symTable));
    }

    @Test
    void testWrongOperandTypeFirstOperandThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(3)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable));
    }

    @Test
    void testWrongOperandTypeSecondOperandThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)), new ValueExp(new BoolValue(false)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable));
    }

    @Test
    void testBothOperandsWrongTypeThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.SUB, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable));
    }

    @Test
    void testWithVariableExp() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.add("x", new IntValue(5));
        symTable.add("y", new IntValue(7));
        ArithExp exp = new ArithExp(ArithOp.ADD, new VariableExp("x"), new VariableExp("y"));
        IValue result = exp.eval(symTable);
        assertEquals(12, ((IntValue) result).val());
    }

    @Test
    void testUndefinedVariableInArithExpThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ArithExp exp = new ArithExp(ArithOp.ADD, new VariableExp("x"), new ValueExp(new IntValue(3)));
        assertThrows(VariableNotDefinedException.class, () -> exp.eval(symTable));
    }

    @Test
    void testAllOperatorsWithVariables() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.add("a", new IntValue(20));
        symTable.add("b", new IntValue(4));

        assertEquals(24, ((IntValue) new ArithExp(ArithOp.ADD, new VariableExp("a"), new VariableExp("b")).eval(symTable)).val());
        assertEquals(16, ((IntValue) new ArithExp(ArithOp.SUB, new VariableExp("a"), new VariableExp("b")).eval(symTable)).val());
        assertEquals(80, ((IntValue) new ArithExp(ArithOp.MUL, new VariableExp("a"), new VariableExp("b")).eval(symTable)).val());
        assertEquals(5, ((IntValue) new ArithExp(ArithOp.DIV, new VariableExp("a"), new VariableExp("b")).eval(symTable)).val());
    }

    @Test
    void testToString() {
        ArithExp exp = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(2)));
        assertNotNull(exp.toString());
    }
}
