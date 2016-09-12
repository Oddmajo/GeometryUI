package utilities.logger;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import utilities.IdFactory;

//
// Factory design patter for all logging channels in this project
//
public class LoggerFactory
{
    private static IdFactory _ids;

    public static final int DEBUG_OUTPUT_ID = 0;
    public static final int MATLAB_RECORDER_OUTPUT_ID = 1;

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
     */
    static 
    {
        _ids = new IdFactory(MATLAB_RECORDER_OUTPUT_ID + 1);
        _loggers = new ArrayList<Logger>();
        _loggers.add(new Logger());                     //Will this replace debug?

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

    // A constructor of sorts
    protected static int addLogger(Writer writer)
    {
        return addLogger(new Logger(writer));
    }

    protected static int addLogger(Logger logger)
    {
        _loggers.add(logger);

        return _ids.getNextId();
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
     * @return the next available id for a new logger
     */
    protected static int addLogger(Logger logger, Logger parentId)
    {
        if (logger.equals(parentId))
        {
            logger._parentId = null;
        }
        _loggers.add(logger);
        return _ids.getCurrentId();
    }

    /*
     * Logging to System.out
     * @author Chris Alvin
     * @modified Ryan Billingsly
     * @date 9-6-2016
     * Modified so that the method will now add the logger to the list automatically
     */
    public static Logger buildLogger()
    {
        Logger logger = new Logger();
        addLogger(logger);
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
        addLogger(logger);
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
        addLogger(logger, parentId);
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
