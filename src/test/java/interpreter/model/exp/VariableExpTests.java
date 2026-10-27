package interpreter.model.exp;

import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableExpTests {
    @Test
    void testEvalDefinedVariable() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.add("x", new IntValue(42));
        VariableExp exp = new VariableExp("x");
        IValue result = exp.eval(symTable);
        assertEquals(42, ((IntValue) result).val());
    }

    @Test
    void testEvalUndefinedVariableThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        VariableExp exp = new VariableExp("y");
        assertThrows(VariableNotDefinedException.class, () -> exp.eval(symTable));
    }

    @Test
    void testEvalDifferentVariableTypes() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.add("a", new IntValue(100));
        symTable.add("b", new BoolValue(true));

        assertEquals(100, ((IntValue) new VariableExp("a").eval(symTable)).val());
        assertTrue(((BoolValue) new VariableExp("b").eval(symTable)).val());
    }

    @Test
    void testToString() {
        VariableExp exp = new VariableExp("myVar");
        assertEquals("myVar", exp.toString());
    }
}
