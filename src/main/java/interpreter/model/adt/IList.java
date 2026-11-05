package interpreter.model.adt;

import java.util.List;

public interface IList<T> {
    void add(T v);
    T get(int index);
    int size();
    boolean isEmpty();
    //mutable list
    List<T> getList();
    //imutable list
    List<T> toList();
}
