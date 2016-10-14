package utilities.exception;

import logger.Logger;

/**
 * @author Drew W
 *
 */
public abstract class LoggableException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 3357259088921397054L;
    
    private static int loggerID;

    public LoggableException()
    {
        // TODO Auto-generated constructor stub
    }

    public LoggableException(String arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public LoggableException(Throwable arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public LoggableException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    public LoggableException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
    {
        super(arg0, arg1, arg2, arg3);
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * Set the class's loggerID
     * @param id
     */
    public static void setLoggerID(int id)
    {
        if (id > ExceptionHandler.DEFAULT_LOGGER_ID)
        {
            loggerID = id;
        }
    }
    
    /**
     * Set the class's loggerID
     * @param logger
     */
    public static void setLoggerID(Logger logger)
    {
        loggerID = logger.getLoggerId();
    }
    
    
    /**
     * Get the class's loggerID
     * @return
     */
    public static int getLoggerID()
    {
        return loggerID;
    }

}
