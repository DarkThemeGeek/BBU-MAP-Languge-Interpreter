package interpreter.model.adt;


import java.util.List;

public interface IStack<T> {
    void push(T v);
    T pop() ;
    boolean isEmpty();
    int size();
    T peek();
    //mutable list
    List<T> getStack();
    //immutable lst
    List<T> toStack();
}
