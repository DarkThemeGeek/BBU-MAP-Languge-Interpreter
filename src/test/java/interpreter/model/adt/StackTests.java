package interpreter.model.adt;

import interpreter.exceptions.EmptyStackException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StackTests {
    @Test
    void testPushPopAndSize() {
        IStack<Integer> stack = new MyStack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertFalse(stack.isEmpty());
        assertEquals(3, stack.size());

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.size());

        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void testPeek() {
        IStack<String> stack = new MyStack<>();
        stack.push("first");
        stack.push("second");

        assertEquals("second", stack.peek());
        assertEquals(2, stack.size());
        // peek should not remove
        assertEquals("second", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void testPopFromEmptyStackThrows() {
        IStack<Object> stack = new MyStack<>();
        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    void testGetStack() {
        IStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        List<Integer> stackList = stack.getStack();
        assertEquals(3, stackList.size());
        // stack is LIFO, but getStack returns stream.toList() of the deque
        assertNotNull(stackList);
    }

    @Test
    void testGetStackFromEmptyThrows() {
        IStack<Integer> stack = new MyStack<>();
        assertThrows(EmptyStackException.class, stack::getStack);
    }

    @Test
    void testToString() {
        IStack<String> stack = new MyStack<>();
        stack.push("a");
        stack.push("b");
        assertNotNull(stack.toString());
        assertTrue(stack.toString().contains("b") || stack.toString().contains("a"));
    }
}
