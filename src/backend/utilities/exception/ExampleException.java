package backend.utilities.exception;

import backend.utilities.logger.Logger;

public class ExampleException extends Loggable
{
    
    private static int loggerID;

    /**
     * 
     */
    private static final long serialVersionUID = -8955931145939787009L;

    @Override
    int getLoggerID()
    {
        return loggerID;
    }

    @Override
    boolean setLoggerID(int id)
    {
        if (id > ExceptionHandler.DEFAULT_LOGGER_ID)
        {
            loggerID = id;
            return true;
        }
        return false;
    }

    @Override
    boolean setLoggerID(Logger logger)
    {
        int id = logger.getLoggerId();
        if (id > ExceptionHandler.DEFAULT_LOGGER_ID)
        {
            loggerID = id;
            return true;
        }
        return false;
    }

}
