package backend.utilities.exception;

import backend.utilities.logger.Logger;

/**
 * I was never able to get his idea to work in practice due to the 
 * conflicting nature of "abstract" or "interface" and "static"
 * <p>
 * These functions need to be static so they can be set for each child class,
 * but if they are static in the parent class then the static variable is the 
 * same for every child class.
 * <p>
 * This is currently and unused class
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
