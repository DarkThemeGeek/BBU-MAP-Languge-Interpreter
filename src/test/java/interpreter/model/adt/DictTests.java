package interpreter.model.adt;

import interpreter.exceptions.NotAKeyException;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DictTests {
    @Test
    void testAddAndGet() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.add("Hello", 1);
        dict.add("Something", 2);

        assertEquals(1, dict.get("Hello"));
        assertEquals(2, dict.get("Something"));
        assertEquals(2, dict.size());
        assertFalse(dict.isEmpty());
    }

    @Test
    void testContainsKey() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        assertFalse(dict.containsKey("missing"));

        dict.add("key", 42);
        assertTrue(dict.containsKey("key"));
    }

    @Test
    void testRemove() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.add("a", 1);
        dict.add("b", 2);

        assertEquals(2, dict.size());
        dict.remove("a");
        assertEquals(1, dict.size());
        assertFalse(dict.containsKey("a"));
        assertTrue(dict.containsKey("b"));
    }

    @Test
    void testGetWithMissingKeyThrows() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.add("existing", 10);
        assertThrows(NotAKeyException.class, () -> dict.get("missing"));
    }

    @Test
    void testIsEmpty() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        assertTrue(dict.isEmpty());
        dict.add("x", 1);
        assertFalse(dict.isEmpty());
    }

    @Test
    void testGetMap() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.add("hello", 5);

        Map<String, Integer> map = dict.getMap();
        assertEquals(1, map.size());
        assertEquals(5, map.get("hello"));
    }

    @Test
    void testKeySet() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.add("a", 1);
        dict.add("b", 2);

        Set<String> keys = dict.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("a"));
        assertTrue(keys.contains("b"));
    }

    @Test
    void testCopy() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.add("a", 1);
        dict.add("b", 2);

        IDictionary<String, Integer> copy = dict.copy();
        assertEquals(2, copy.size());
        assertEquals(1, copy.get("a"));
        assertEquals(2, copy.get("b"));

        // copy should be independent
        copy.add("c", 3);
        assertEquals(2, dict.size());
        assertEquals(3, copy.size());
        assertFalse(dict.containsKey("c"));
        assertTrue(copy.containsKey("c"));
    }

    @Test
    void testToString() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.add("test", 123);
        assertNotNull(dict.toString());
    }
}
