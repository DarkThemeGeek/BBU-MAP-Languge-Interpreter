package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntTypeTests {
    @Test
    void testIntTypeDefaultValue() {
        IntType intType = new IntType();
        IValue defaultValue = intType.defaultValue();
        assertNotNull(defaultValue);
        assertTrue(defaultValue instanceof IntValue);
        assertEquals(0, ((IntValue) defaultValue).val());
    }

    @Test
    void testIntTypeToString() {
        IntType intType = new IntType();
        assertEquals("int", intType.toString());
    }

    @Test
    void testIntTypeEquality() {
        IntType intType1 = new IntType();
        IntType intType2 = new IntType();
        assertEquals(intType1, intType2);
        assertEquals(intType1.hashCode(), intType2.hashCode());
    }

    @Test
    void testIntTypeNotEqualToBoolType() {
        IntType intType = new IntType();
        BoolType boolType = new BoolType();
        assertNotEquals(intType, boolType);
    }
}
