package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTests {
    @Test
    void testEvalIntValue() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ValueExp exp = new ValueExp(new IntValue(123));
        IValue result = exp.eval(symTable);
        assertTrue(result instanceof IntValue);
        assertEquals(123, ((IntValue) result).val());
    }

    @Test
    void testEvalBoolValue() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        ValueExp exp = new ValueExp(new BoolValue(false));
        IValue result = exp.eval(symTable);
        assertTrue(result instanceof BoolValue);
        assertFalse(((BoolValue) result).val());
    }

    @Test
    void testToString() {
        ValueExp exp1 = new ValueExp(new IntValue(42));
        assertEquals("42", exp1.toString());

        ValueExp exp2 = new ValueExp(new BoolValue(true));
        assertEquals("true", exp2.toString());
    }
}
