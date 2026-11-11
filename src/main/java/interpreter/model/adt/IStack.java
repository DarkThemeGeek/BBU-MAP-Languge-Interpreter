package interpreter.model.adt;


import interpreter.exceptions.EmptyStackException;

import java.util.List;

public interface IStack<T> {
    void push(T v);
    T pop() throws EmptyStackException;
    boolean isEmpty();
    int size();
    T peek();
    //mutable list
    List<T> getStack() throws EmptyStackException;
    //immutable lst
    List<T> toList();
}
