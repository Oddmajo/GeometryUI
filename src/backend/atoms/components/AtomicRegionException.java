package backend.atoms.components;

import backend.utilities.exception.ExceptionHandler;
import backend.utilities.logger.Logger;

public class AtomicRegionException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 2388096528591001388L;
    
    /**
     * static logger ID
     * @author Drew Whitmire
     */
    public static int loggerID; 
    
    /**
     * @return the loggerID
     */
    public static int getLoggerID()
    {
        return loggerID;
    }

    /**
     * @param id    set the loggerID to the given int
     */
    public static boolean setLoggerID(int id)
    {
        if (id > ExceptionHandler.DEFAULT_LOGGER_ID)
        {
            loggerID = id;
            return true;
        }
        return false;
    }

    public static void setLoggerID(Logger logger)
    {
        loggerID = logger.getLoggerId();
    }

    public AtomicRegionException()
    {
        // TODO Auto-generated constructor stub
    }

    public AtomicRegionException(String arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public AtomicRegionException(Throwable arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public AtomicRegionException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    public AtomicRegionException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
    {
        super(arg0, arg1, arg2, arg3);
        // TODO Auto-generated constructor stub
    }

}
