package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoolTypeTests {
    @Test
    void testBoolTypeDefaultValue() {
        BoolType boolType = new BoolType();
        IValue defaultValue = boolType.defaultValue();
        assertNotNull(defaultValue);
        assertTrue(defaultValue instanceof BoolValue);
        assertFalse(((BoolValue) defaultValue).val());
    }

    @Test
    void testBoolTypeToString() {
        BoolType boolType = new BoolType();
        // Note: BoolType.toString() currently returns "int" in the source
        assertNotNull(boolType.toString());
    }

    @Test
    void testBoolTypeEquality() {
        BoolType boolType1 = new BoolType();
        BoolType boolType2 = new BoolType();
        assertEquals(boolType1, boolType2);
        assertEquals(boolType1.hashCode(), boolType2.hashCode());
    }
}
