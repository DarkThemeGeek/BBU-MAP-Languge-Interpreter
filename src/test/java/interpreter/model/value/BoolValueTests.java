package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoolValueTests {
    @Test
    void testBoolValueTrue() {
        BoolValue boolValue = new BoolValue(true);
        assertTrue(boolValue.val());
        assertEquals("true", boolValue.toString());

        IType type = boolValue.getType();
        assertNotNull(type);
        assertTrue(type instanceof BoolType);
    }

    @Test
    void testBoolValueFalse() {
        BoolValue boolValue = new BoolValue(false);
        assertFalse(boolValue.val());
        assertEquals("false", boolValue.toString());
    }

    @Test
    void testBoolValueEquality() {
        BoolValue boolValue1 = new BoolValue(true);
        BoolValue boolValue2 = new BoolValue(true);
        BoolValue boolValue3 = new BoolValue(false);

        assertEquals(boolValue1, boolValue2);
        assertEquals(boolValue1.hashCode(), boolValue2.hashCode());
        assertNotEquals(boolValue1, boolValue3);
    }

    @Test
    void testBoolValueGetTypeEquality() {
        BoolValue boolValue = new BoolValue(true);
        assertEquals(new BoolType(), boolValue.getType());
    }
}
