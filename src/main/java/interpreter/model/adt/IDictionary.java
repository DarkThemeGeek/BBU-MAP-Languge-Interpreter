package interpreter.model.adt;

import java.util.Map;
import java.util.Set;

public interface IDictionary<TKey,TValue>  {
    public Map<TKey, TValue> getMap();
    TValue get(TKey key);
    void add(TKey key, TValue value);
    boolean containsKey(TKey key);
    void remove(TKey key);
    int size();
    boolean isEmpty();
    IDictionary<TKey, TValue> copy();
    Set<TKey> keySet();

}
