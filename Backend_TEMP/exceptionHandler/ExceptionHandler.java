/*
iTutor � an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package exceptionHandler;

import logger.LoggerFactory;
import concreteAST.ConcreteASTException;
import exceptionHandler.GeometryException;

/**
 * The ExceptionHandler Class.
 * Contains static methods that take String, Exception, and GeometryException arguments as input.
 * <p>
 * These static methods return strings containing the exception message 
 * and the stack trace to be logged with a Logger.
 * @author Drew W.
 */
public class ExceptionHandler
{
    private static final int DEFUALT_LOGGER_ID = 3;
    /////////////////// Constructor //////////////////////////////////////////

    /**
     * The ExceptionHandler constructor.
     * @author Drew Whitmire
     */
    protected ExceptionHandler() {}

    /////////////////// Static Methods //////////////////////////////////////////

    /**
     * Static method to throw Exceptions to the default Logger with String input.
     * @param message   the input exception message of type String to be logged
     * @return  the exception message and the stack trace as a string
     * @author Drew Whitmire
     */
    public static String throwException(String message)
    {
        // variables for logging
        Class loggerClass = null;
        int loggerID = -1;
        // concatenate the message string to the stack trace and return it
        String output = "EXCEPTION: (" + message.getClass().getSimpleName() + ") " + message;

        // loop through the Stack Trace array, add each to the output string
        // check if any of the classes from the stack trace have loggers, 
        // starting in and working out
        for (int i = 1; i < Thread.currentThread().getStackTrace().length; i++)
        {
            // get current stack trace 
            output += "\n\tat " + Thread.currentThread().getStackTrace()[i];

            // get stack trace class and check if there's a logger for it
            // if a loggerID hasn't already been found
            if (loggerID != -1)
            {
                loggerClass = Thread.currentThread().getStackTrace()[i].getClass();

                if (loggerClass.isInstance(new GeometryException())) 
                {
                    loggerID = GeometryException.loggerID;
                }
                // other checks to come
            }
        }

        // write to the logger if one was found
        if (loggerID != -1) {
            LoggerFactory.getLogger(loggerID).writeln(output);
        }
        else // write to the default logger
        {
            LoggerFactory.getLogger(DEFUALT_LOGGER_ID).writeln(output);
        }
        
        return output += "\n\tloggerID = " + loggerID;
    }


    /**
     * Static method to throw Exceptions to the default Logger with an
     * Exception input.
     * @param exception the input Exception to be logged
     * @return  the exception message and the stack trace as a string
     * @author Drew Whitmire
     */
    public static String throwException(Exception except)
    {
        // variables for logging
        Class loggerClass = null;
        int loggerID = -1;
        
        // check if the exception inherits from a packages Exception
        if (new GeometryException().getClass().isInstance(except))
        {
            loggerID = GeometryException.loggerID;
        }
        else if(new ConcreteASTException().getClass().isInstance(except) )
        {
            loggerID = ConcreteASTException.loggerID;
        }
        // more checks to come

        // get the message and stack trace from the exception
        // turn it into a string and write to the default logger
        String output = "EXCEPTION: (" + except.getClass().getSimpleName() + ") " + except.getMessage();

        // loop through the Stack Trace array and print each one
        for (int i = 1; i < Thread.currentThread().getStackTrace().length; i++)
        {
            output += "\n\tat " + Thread.currentThread().getStackTrace()[i];

            // get stack trace class and check if there's a logger for it
            // if a loggerID hasn't already been found
            if (loggerID != -1)
            {
                loggerClass = Thread.currentThread().getStackTrace()[i].getClass();

                if (loggerClass.isInstance(new GeometryException())) 
                {
                    loggerID = GeometryException.loggerID;
                }
                else if(new ConcreteASTException().getClass().isInstance(except) )
                {
                    loggerID = ConcreteASTException.loggerID;
                }
                // other checks to come
            }
        }

        // write to the logger if one was found
        if (loggerID != -1) {
            LoggerFactory.getLogger(loggerID).writeln(output);
        }
        else // write to the default logger
        {
            LoggerFactory.getLogger(DEFUALT_LOGGER_ID).writeln(output);
        }

        return output += "\n\tloggerID = " + loggerID;
    }

    /**
     * Static method to throw Exceptions to a Logger with a
     * GeometryException input.
     * <p>
     * This method is currently unnecessary, it is handled by the 
     * throwException method that takes an Exception argument.
     * @param geoexception  the input GeometryException to be logged
     * @return  the exception message and the stack trace as a string
     * @author Drew Whitmire
     */
    /*
    public static String throwException(GeometryException geoexcept)
    {
        // get the message and stack trace from the geometry exception
        // turn the GeometryException message into a string and add to the output
        String output = "EXCEPTION: (" + geoexcept.getClass().getSimpleName() + ") " + geoexcept.getMessage();

        // loop through the Stack Trace array and add each one to the output
        // skip the first trace (the getStackTrace()'s trace)
        for (int i = 1; i < Thread.currentThread().getStackTrace().length; i++)
        {
            output += "\n\tat " + Thread.currentThread().getStackTrace()[i];
        }
        return output;
    }
     */

}
