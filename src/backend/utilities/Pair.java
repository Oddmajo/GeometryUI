package utilities;

public class Pair<K, V>
{
    protected K _key;
    protected V _value;

    public static <K, V> Pair<K, V> createPair(K _key, V _value)
    {
        return new Pair<K, V>(_key, _value);
    }

    public Pair(K _key, V _value)
    {
        this._key = _key;
        this._value = _value;
    }

    public K getKey()
    {
        return _key;
    }

    public V getValue()
    {
        return _value;
    }
    
    public K first()
    {
        return _key;
    }

    public V second()
    {
        return _value;
    }

}