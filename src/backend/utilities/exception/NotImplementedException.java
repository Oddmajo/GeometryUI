package backend.utilities.exception;

import backend.utilities.logger.Logger;

public class NotImplementedException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -280997203104197434L;
    
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
    public static void setLoggerID(int id)
    {
        if (id > ExceptionHandler.DEFAULT_LOGGER_ID)
        {
            loggerID = id;
        }
    }

    public static void setLoggerID(Logger logger)
    {
        loggerID = logger.getLoggerId();
    }

    public NotImplementedException()
    {
        super("Method is not implemented");
    }
    public NotImplementedException(String s)
    {
        super(s);
    }
}
