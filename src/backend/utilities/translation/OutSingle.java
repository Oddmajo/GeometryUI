package backend.utilities.translation;

public class OutSingle<V>
{
    protected V _value;
    
    public OutSingle(V value)
    {
        _value = value;
    }

    public OutSingle()
    {
        this(null);
    }
    
    public void set(V value) { _value = value; }
    
    public V get() { return _value; }
}
