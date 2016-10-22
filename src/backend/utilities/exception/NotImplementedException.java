package backend.utilities.exception;

public class NotImplementedException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -280997203104197434L;

    public NotImplementedException()
    {
        super("Method is not implemented");
    }
}
