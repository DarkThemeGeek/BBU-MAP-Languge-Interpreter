package interpreter.model.exp;

import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.NotAllocateOnHeapException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadHeapExpTests {
    @Test
    void testReadHeapReturnsValue() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        int address = heap.allocate(new IntValue(42));
        symTable.add("v", new RefValue(address, new IntType()));

        ReadHeapExp exp = new ReadHeapExp(new VariableExp("v"));
        IValue result = exp.eval(symTable, heap);

        assertEquals(new IntValue(42), result);
    }

    @Test
    void testReadHeapNonRefThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();

        ReadHeapExp exp = new ReadHeapExp(new ValueExp(new IntValue(5)));
        assertThrows(InvalidTypeException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void testReadHeapUnallocatedAddressThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        symTable.add("v", new RefValue(99, new IntType()));

        ReadHeapExp exp = new ReadHeapExp(new VariableExp("v"));
        assertThrows(NotAllocateOnHeapException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void testToString() {
        ReadHeapExp exp = new ReadHeapExp(new VariableExp("v"));
        assertEquals("readHeap(v)", exp.toString());
    }
}
