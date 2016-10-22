/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.utilities.exception;

import backend.utilities.logger.Logger;

/**
 * A GeometryException class that inherits from Exception.
 * @author Drew W
 *
 */
public class GeometryException extends Exception
{
    
    /**
     * Default serialVersionUID
     * @author Drew Whitmire
     */
    private static final long serialVersionUID = 1L;
    
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
    public static void setLoggerID(int id)
    {
        if (id > ExceptionHandler.DEFAULT_LOGGER_ID)
        {
            loggerID = id;
        }
    }

    public static void setLoggerID(Logger logger)
    {
        loggerID = logger.getLoggerId();
    }
    
    /**
     * Default Constructor
     * Constructs a new GeometryException with null as its detail message
     * @author Drew Whitmire
     */
    public GeometryException()
    {
        super();
    }

    /**
     * Constructs a new GeometryException with the specified detail message.
     * @param message   the detail message. Can be retrieved by the Throwable.getMessage() method.
     * @author Drew Whitmire
     */
    public GeometryException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new GeometryException with the specified cause.
     * @param cause     the cause. Can be retrieved by the Throwable.getCause() method.
     * @author Drew Whitmire
     */
    public GeometryException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new GeometryException with the specified detail message and cause.
     * @param message   the detail message. Can be retrieved by the Throwable.getMessage() method.
     * @param cause     the cause. Can be retrieved by the Throwable.getCause() method.
     * @author Drew Whitmire
     */
    public GeometryException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructs a new GeometryException with the specified detail message, cause, suppression enabled or disabled, 
     * and writable stack trace enabled or disabled.
     * @param message   the detail message. Can be retrieved by the Throwable.getMessage() method.
     * @param cause     the cause. Can be retrieved by the Throwable.getCause() method.
     * @param enableSuppression     whether or not suppression is enabled or disabled
     * @param writableStackTrace    whether or not the stack trace should be writable
     * @author Drew Whitmire
     */
    public GeometryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
