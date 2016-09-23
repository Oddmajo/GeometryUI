package concreteAST.Figure;

public class NotImplementedException extends Exception
{
    public NotImplementedException()
    {
        super("Method is not implemented");
    }
}
