package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTests {
    @Test
    void testRefTypeDefaultValue() {
        RefType refType = new RefType(new IntType());
        IValue defaultValue = refType.defaultValue();
        assertNotNull(defaultValue);
        assertTrue(defaultValue instanceof RefValue);
        RefValue refValue = (RefValue) defaultValue;
        assertEquals(0, refValue.address());
        assertEquals(new IntType(), refValue.locationType());
    }

    @Test
    void testRefTypeToString() {
        RefType refType = new RefType(new IntType());
        assertEquals("Ref(int)", refType.toString());
    }

    @Test
    void testRefTypeEquality() {
        RefType refType1 = new RefType(new IntType());
        RefType refType2 = new RefType(new IntType());
        RefType refType3 = new RefType(new BoolType());

        assertEquals(refType1, refType2);
        assertEquals(refType1.hashCode(), refType2.hashCode());
        assertNotEquals(refType1, refType3);
    }

    @Test
    void testRefTypeNotEqualToOtherTypes() {
        RefType refType = new RefType(new IntType());
        assertNotEquals(refType, new IntType());
        assertNotEquals(refType, new BoolType());
    }
}
