package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTests {
    @Test
    void testStringValue() {
        StringValue stringValue = new StringValue("hello");
        assertEquals("hello", stringValue.val());
        assertEquals("hello", stringValue.toString());

        IType type = stringValue.getType();
        assertNotNull(type);
        assertTrue(type instanceof StringType);
    }

    @Test
    void testStringValueEmpty() {
        StringValue stringValue = new StringValue("");
        assertEquals("", stringValue.val());
        assertEquals("", stringValue.toString());
    }

    @Test
    void testStringValueEquality() {
        StringValue value1 = new StringValue("test");
        StringValue value2 = new StringValue("test");
        StringValue value3 = new StringValue("other");

        assertEquals(value1, value2);
        assertEquals(value1.hashCode(), value2.hashCode());
        assertNotEquals(value1, value3);
    }

    @Test
    void testStringValueGetTypeEquality() {
        StringValue stringValue = new StringValue("test");
        assertEquals(new StringType(), stringValue.getType());
    }
}
