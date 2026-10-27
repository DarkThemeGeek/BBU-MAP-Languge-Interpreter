package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntValueTests {
    @Test
    void testIntValue() {
        IntValue intValue = new IntValue(42);
        assertEquals(42, intValue.val());
        assertEquals("42", intValue.toString());

        IType type = intValue.getType();
        assertNotNull(type);
        assertTrue(type instanceof IntType);
    }

    @Test
    void testIntValueZero() {
        IntValue intValue = new IntValue(0);
        assertEquals(0, intValue.val());
        assertEquals("0", intValue.toString());
    }

    @Test
    void testIntValueNegative() {
        IntValue intValue = new IntValue(-5);
        assertEquals(-5, intValue.val());
        assertEquals("-5", intValue.toString());
    }

    @Test
    void testIntValueEquality() {
        IntValue intValue1 = new IntValue(10);
        IntValue intValue2 = new IntValue(10);
        IntValue intValue3 = new IntValue(20);

        assertEquals(intValue1, intValue2);
        assertEquals(intValue1.hashCode(), intValue2.hashCode());
        assertNotEquals(intValue1, intValue3);
    }

    @Test
    void testIntValueGetTypeEquality() {
        IntValue intValue = new IntValue(5);
        assertEquals(new IntType(), intValue.getType());
    }
}
