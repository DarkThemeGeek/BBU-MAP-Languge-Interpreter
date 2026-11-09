package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTests {
    @Test
    void testLessThan() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        RelationalExp expTrue = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(5)));
        assertTrue(((BoolValue) expTrue.eval(symTable)).val());

        RelationalExp expFalse = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(2)));
        assertFalse(((BoolValue) expFalse.eval(symTable)).val());
    }

    @Test
    void testLessThanOrEqual() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        RelationalExp expTrue = new RelationalExp(RelOp.LE, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(3)));
        assertTrue(((BoolValue) expTrue.eval(symTable)).val());

        RelationalExp expFalse = new RelationalExp(RelOp.LE, new ValueExp(new IntValue(4)), new ValueExp(new IntValue(3)));
        assertFalse(((BoolValue) expFalse.eval(symTable)).val());
    }

    @Test
    void testEqual() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        RelationalExp expTrue = new RelationalExp(RelOp.EQ, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(7)));
        assertTrue(((BoolValue) expTrue.eval(symTable)).val());

        RelationalExp expFalse = new RelationalExp(RelOp.EQ, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(8)));
        assertFalse(((BoolValue) expFalse.eval(symTable)).val());
    }

    @Test
    void testNotEqual() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        RelationalExp expTrue = new RelationalExp(RelOp.NE, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(2)));
        assertTrue(((BoolValue) expTrue.eval(symTable)).val());

        RelationalExp expFalse = new RelationalExp(RelOp.NE, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(2)));
        assertFalse(((BoolValue) expFalse.eval(symTable)).val());
    }

    @Test
    void testGreaterThan() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        RelationalExp expTrue = new RelationalExp(RelOp.GT, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(3)));
        assertTrue(((BoolValue) expTrue.eval(symTable)).val());

        RelationalExp expFalse = new RelationalExp(RelOp.GT, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(10)));
        assertFalse(((BoolValue) expFalse.eval(symTable)).val());
    }

    @Test
    void testGreaterThanOrEqual() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        RelationalExp expTrue = new RelationalExp(RelOp.GE, new ValueExp(new IntValue(5)), new ValueExp(new IntValue(5)));
        assertTrue(((BoolValue) expTrue.eval(symTable)).val());

        RelationalExp expFalse = new RelationalExp(RelOp.GE, new ValueExp(new IntValue(4)), new ValueExp(new IntValue(5)));
        assertFalse(((BoolValue) expFalse.eval(symTable)).val());
    }

    @Test
    void testNonIntegerFirstOperandThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        RelationalExp exp = new RelationalExp(RelOp.LT, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(2)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }

    @Test
    void testNonIntegerSecondOperandThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        RelationalExp exp = new RelationalExp(RelOp.EQ, new ValueExp(new IntValue(2)), new ValueExp(new BoolValue(false)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }

    @Test
    void testToString() {
        RelationalExp exp = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(2)));
        assertNotNull(exp.toString());
    }
}
