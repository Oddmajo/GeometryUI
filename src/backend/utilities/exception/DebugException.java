package backend.utilities.exception;

import backend.utilities.logger.Logger;

public class DebugException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -3641246013313120034L;
    
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

    public DebugException()
    {
        // TODO Auto-generated constructor stub
    }

    public DebugException(String arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public DebugException(Throwable cause)
    {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public DebugException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public DebugException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
