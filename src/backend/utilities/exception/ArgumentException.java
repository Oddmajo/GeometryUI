package utilities.exception;

import logger.Logger;

public class ArgumentException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -6239694773154636856L;
    
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

    public ArgumentException(String message)
    {
        super(message);
    }

}
