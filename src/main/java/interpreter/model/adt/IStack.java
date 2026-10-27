package interpreter.model.adt;


import java.util.List;

public interface IStack<T> {
    void push(T v);
    T pop() ;
    boolean isEmpty();
    int size();
    T peek();
    List<T> getStack();
}
