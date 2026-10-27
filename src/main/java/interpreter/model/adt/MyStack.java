package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.EmptyStackException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class MyStack<T> implements IStack<T> {
    private final Deque<T> stack = new ArrayDeque<>(); // java.util
    @Override public void push(T v) { stack.push(v); }
    @Override public T pop(){
        if (stack.isEmpty()) throw new EmptyStackException("Pop from an empty stack");
        return stack.pop();
    }
    @Override public boolean isEmpty() { return stack.isEmpty(); }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public List<T> getStack() {
        if (stack.isEmpty()) {
            throw new EmptyStackException("Nothing to get");
        }
        return stack.stream().toList();
    }

    @Override public String toString() { return stack.toString(); }

}
