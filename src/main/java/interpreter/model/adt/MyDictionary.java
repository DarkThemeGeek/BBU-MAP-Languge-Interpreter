package interpreter.model.adt;


import interpreter.exceptions.NotAKeyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<TKey,TValue> implements  IDictionary<TKey,TValue>{
    private Map<TKey,TValue> map;

    public MyDictionary (){
        map=new HashMap<>();
    }

    @Override
    public Map<TKey, TValue> getMap() {
        return map;
    }

    @Override
    public TValue get(TKey key) {
        if (!map.containsKey(key)) {
            throw new NotAKeyException("Wrong key"+key);
        }
        return map.get(key);
    }

    @Override
    public void add(TKey key, TValue value) {
            map.put(key,value);
    }

    @Override
    public boolean containsKey(TKey key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(TKey key) {
        map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public IDictionary<TKey,TValue> copy() {
        IDictionary<TKey, TValue> newDict = new MyDictionary<>();
        for (TKey key : map.keySet()) {
            newDict.add(key, map.get(key));
        }
        return newDict;
    }

    @Override
    public Set<TKey> keySet() {
        return map.keySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
