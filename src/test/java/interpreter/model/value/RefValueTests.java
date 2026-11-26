package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefValueTests {
    @Test
    void testRefValue() {
        RefValue refValue = new RefValue(1, new IntType());
        assertEquals(1, refValue.address());
        assertEquals(new IntType(), refValue.locationType());
        assertEquals("(1, int)", refValue.toString());
    }

    @Test
    void testRefValueGetType() {
        RefValue refValue = new RefValue(2, new IntType());
        IType type = refValue.getType();
        assertNotNull(type);
        assertTrue(type instanceof RefType);
        assertEquals(new RefType(new IntType()), type);
    }

    @Test
    void testRefValueEquality() {
        RefValue v1 = new RefValue(1, new IntType());
        RefValue v2 = new RefValue(1, new IntType());
        RefValue v3 = new RefValue(2, new IntType());
        RefValue v4 = new RefValue(1, new RefType(new IntType()));

        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1, v3);
        assertNotEquals(v1, v4);
    }
}
