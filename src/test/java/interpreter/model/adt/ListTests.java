package interpreter.model.adt;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListTests {
    @Test
    void testAddGetSize() {
        IList<Integer> list = new MyList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.add(10);
        list.add(20);
        list.add(30);

        assertFalse(list.isEmpty());
        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testGetWithNegativeIndexThrows() {
        IList<String> list = new MyList<>();
        list.add("test");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void testGetWithIndexOutOfBoundsThrows() {
        IList<String> list = new MyList<>();
        list.add("test");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(100));
    }

    @Test
    void testGetFromEmptyListThrows() {
        IList<Integer> list = new MyList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    void testGetList() {
        IList<String> list = new MyList<>();
        list.add("hello");
        list.add("world");

        List<String> internalList = list.getList();
        assertEquals(2, internalList.size());
        assertEquals("hello", internalList.get(0));
        assertEquals("world", internalList.get(1));
    }
}
