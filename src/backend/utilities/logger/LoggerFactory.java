package backend.utilities.logger;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
//import utilities.IdFactory;

//
// Factory design patter for all logging channels in this project
//
public class LoggerFactory
{
    private static IdFactory _ids;

    public static final int DEBUG_OUTPUT_ID = 0;
    public static final int MATLAB_RECORDER_OUTPUT_ID = 1;
    public static final int DEFAULT_OUTPUT_ID = 2; // added by Drew Whitmire
    public static final int EXCEPTION_OUTPUT_ID = 3;

    // The set of loggers
    protected static ArrayList<Logger> _loggers;

    // A default logger where output goes to die
    protected static Logger _deathLogger;

    // This will be a static class
    protected LoggerFactory() {}

    /*A constructor of sorts
     * @author Chris Alvin
     * <modified by> Ryan Billingsly 9/6/2016
     * Made this a static declaration in place of initialize(Logger debug)
     * <p>
     * @modified Drew Whitmire
     * added default logger and made default exception logger a child of the default logger
     * I added file paths just to test.  They need to be updated in the future to be generic to
     * work for anybody.
     */
    static 
    {
        _ids = new IdFactory(EXCEPTION_OUTPUT_ID + 1);
        _loggers = new ArrayList<Logger>();
        buildLogger(new Logger());                     // debug logger
        buildLogger(new Logger());                     // matlab logger
        _loggers.add(new Logger("C:\\Users\\Drew W\\Documents\\bradley\\iTutor\\DefaultLog.txt"));                     // default logger
        _loggers.add(new Logger("C:\\Users\\Drew W\\Documents\\bradley\\iTutor\\DefaultExceptionLog.txt", getLogger(DEFAULT_OUTPUT_ID)));                     // exception default logger
        // Initializing the null output stream
        _deathLogger = new Logger(new NullOutputStreamWriter());
    }

    /*
     * Acquire a given logger based on a key value
     * If an id is not recognized return the null logger
     * @author Chris Alvin
     * <modified> 9/5/2016
     * <by> Ryan Billingsly
     * Now handles negative input appropriately 
     */
    public static Logger getLogger(int id)
    {
        if (id >= _loggers.size() || id <= 0) return _deathLogger;

        return _loggers.get(id);
    }

    /**
     * @modified Drew Whitmire
     * @param logger
     * @return the position of the logger in the array
     */
    protected static int addLogger(Logger logger)
    {
        _loggers.add(logger);

        _ids.getNextId();
        
        return _loggers.indexOf(logger);
    }

    // A constructor of sorts
    protected static int addLogger(Writer writer)
    {
        return addLogger(new Logger(writer));
    }



    /*
     * This will validate that the ID provide is associated with an existing logger
     * NOTE: This only checks and makes sure a logger is not its own parent.  
     * This DOES NOT check any circularity with other nodes.
     * THINKING OUT LOUD - Does it need to?  When we add a logger, it must pass a logger
     * as a parentId, but that logger must either a) currently exist on the tree; or
     * b) the logger must have been created in the addLogger function call
     * (i.e. addLogger(someLogger, new Logger());).  In either case, the parent logger
     * would exist in the tree and therefore could not generate circularity at all.
     * @author Ryan Billingsly
     * @param The logger being added to the list, and the ID of the parent
     * 
     * @modified Drew Whitmire
     * @date 9/24/2016
     * @return the logger id for a new logger
     */
    protected static int addLogger(Logger logger, Logger parentId)
    {
        if (logger.equals(parentId))
        {
            logger._parentId = null;
        }
        else 
        {
            logger._parentId = parentId;
        }
        _loggers.add(logger);
        _ids.getNextId();
        
        return _loggers.indexOf(logger);
    }

    /*
     * Logging to System.out
     * @author Chris Alvin
     * @modified Ryan Billingsly
     * @date 9-6-2016
     * Modified so that the method will now add the logger to the list automatically
     * 
     * @modified Drew Whitmire
     * @date 9/24/2016
     * Sets the loggerId
     */
    public static Logger buildLogger()
    {
        Logger logger = new Logger(_loggers.get(0));
        logger.setLoggerId(addLogger(logger));
        return logger;
    }
    
    public static Logger buildLogger(Logger parentId)
    {
        Logger logger = new Logger(parentId);
        logger.setLoggerId(addLogger(logger));
        return logger;
    }
    

    /*
     * Logging to a particular file
     * @author Chris Alvin
     * @modified Ryan Billingsly
     * @date 9-6-2016
     * Modified so that the method will now add the logger to the list automatically
     */
    public static Logger buildLogger(String filePath)
    {
        Logger logger = new Logger(filePath);
        logger.setLoggerId(addLogger(logger));
        return logger;
    }

    /*
     * @author Ryan Billingsly
     * @param the intended filepath as a string, the id for the parent logger
     * @returns a newly constructed logger that is added to the tree of loggers.
     *  This logger will have a parent logger assigned, assuming the parent logger
     *  is a valid logger.
     */
    public static Logger buildLogger(String filePath, Logger parentId)
    {
        Logger logger = new Logger(filePath, parentId);
        logger.setLoggerId(addLogger(logger));
        return logger;
    }

    // A constructor of sorts
    // "Wouldn't this be a deconstructor of sorts?"  -Ryan Billingsly
    public static void close()
    {
        try
        {
            _deathLogger.close();

            for (Logger logger : _loggers)
            {
                try
                {
                    if (logger != null) logger.close();
                }
                catch (IOException ioe)
                {
                    System.err.println(ioe.getMessage());
                    ioe.printStackTrace();
                }
            }
        }
        catch (IOException ioe)
        {
            System.err.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}
