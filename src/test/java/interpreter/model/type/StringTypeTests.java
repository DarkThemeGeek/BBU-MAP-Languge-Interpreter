package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTests {
    @Test
    void testStringTypeDefaultValue() {
        StringType stringType = new StringType();
        IValue defaultValue = stringType.defaultValue();
        assertNotNull(defaultValue);
        assertTrue(defaultValue instanceof StringValue);
        assertEquals("", ((StringValue) defaultValue).val());
    }

    @Test
    void testStringTypeToString() {
        StringType stringType = new StringType();
        assertEquals("string", stringType.toString());
    }

    @Test
    void testStringTypeEquality() {
        StringType stringType1 = new StringType();
        StringType stringType2 = new StringType();
        assertEquals(stringType1, stringType2);
        assertEquals(stringType1.hashCode(), stringType2.hashCode());
    }

    @Test
    void testStringTypeNotEqualToOtherTypes() {
        StringType stringType = new StringType();
        IntType intType = new IntType();
        BoolType boolType = new BoolType();
        assertNotEquals(stringType, intType);
        assertNotEquals(stringType, boolType);
    }
}
