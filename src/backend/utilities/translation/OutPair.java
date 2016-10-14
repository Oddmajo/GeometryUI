package utilities.translation;

import utilities.Pair;

public class OutPair<K, V> extends Pair<K, V>
{
    public OutPair(K key, V value)
    {
        super(key, value);
    }

    public OutPair()
    {
        super(null, null);
    }
    
    public void set(K k, V value) { _key = k; _value = value; }
}
