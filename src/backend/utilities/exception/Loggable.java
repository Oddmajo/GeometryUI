package utilities.exception;

import logger.Logger;

/**
 * @author Drew W
 *
 */
public abstract class Loggable extends Exception
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 3189254355623902648L;

    /**
     * Get the class's loggerID
     * @return
     */
    abstract int getLoggerID();

    /**
     * @param id    set the loggerID to the given int
     */
    abstract boolean setLoggerID(int id);

    abstract boolean setLoggerID(Logger logger);
}
