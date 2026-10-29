package interpreter.model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T>{
    private List<T> list;
    public MyList(){
        this.list =  new ArrayList<>();
    }
    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public List<T> getList() {
        return list;
    }
    public String toString(){return list.toString();}
}
