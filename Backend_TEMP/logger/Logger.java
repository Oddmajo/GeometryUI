package utilities.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Logger
{
    // The actual output file we will write to
    protected String _theFilePath;
    protected BufferedWriter _writer;

    /*@author Ryan Billingsly
     */
    protected Logger _parentId;

    /*
     *@author Chris Alvin
     *@param The id of the parent logger
     *@return A default logger.
     *<modified by> Ryan Billingsly 9/6/2016
     * Added parentId initialization
     */

    public Logger()
    {
        _theFilePath = null;
        _parentId = null;
        open(new OutputStreamWriter(System.out));
    }

    /*
     *@author Chris Alvin
     *@param The id of the parent logger
     *@return A logger that has been assigned a file path
     *<modified by> Ryan Billingsly 9/6/2016
     * Added parentId initialization
     */
    public Logger(String f)
    {
        _theFilePath = f;
        _parentId = null;
        //
        // Create a Writer
        //
        FileWriter fstream = null;
        try  
        {
            fstream = new FileWriter(f);
        }
        catch (IOException e)
        {
            System.err.println("Output logging file initialization failed: " + _theFilePath);
            System.err.println("Error: " + e.getMessage());
        }

        open(fstream);
    }

    /*
     *@author Ryan Billingsly
     *@param The id of the parent logger
     *@return A logger.  This logger makes it possible to create a logger without
     * a file path but still store a parent logger.
     */

    public Logger(Logger id)
    {
        this();
        _parentId = id;
    }

    /*
     *@author Ryan Billingsly
     *@param The string that represents the filepath, and the id of the parent logger 
     *@return Creates a logger that will be associated as a pre-existing logger's child, if
     * the id for the parent currently exists in _loggers
     */

    public Logger(String f, Logger id)
    {
        this(f);
        _parentId = id;
    }
    /*
     *@author Chris Alvin
     *@param The id of the parent logger
     *@return A logger that opens a writer but has no filepath.
     *<modified by> Ryan Billingsly 9/6/2016
     * Added parentId initialization
     */
    public Logger(Writer writer)
    {
        _theFilePath = null;
        _parentId = null;
        open(writer);
    }

    //
    // Open the logging file
    //
    protected boolean open(Writer writer)
    {
        _writer = new BufferedWriter(writer);

        if (_writer == null)
        {
            System.err.println("Output logging file (BufferedWriter) initialization failed: " + _theFilePath);
        }

        return _writer != null;
    }

    //
    // Writing methods
    //
    public boolean write(String str)
    {
        try
        {
            _writer.write(str);
            if (_parentId != null)
                return _parentId.write(str);
        }
        catch (IOException ioe)
        {
            System.err.println("Logging problem: " + ioe.getMessage());
            return false;
        }

        return true;
    }

    //
    // Writing methods
    //
    public boolean writeln(String str)
    {
        return write(str + "\n");
    }

    //
    // Close this writer from logging
    //
    public void close() throws IOException
    {
        _writer.close();
    }
}
