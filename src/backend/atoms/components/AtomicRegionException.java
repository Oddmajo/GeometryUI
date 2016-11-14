/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.components;

import backend.utilities.exception.ExceptionHandler;
import backend.utilities.logger.Logger;

public class AtomicRegionException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 2388096528591001388L;
    
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

    public AtomicRegionException()
    {
        // TODO Auto-generated constructor stub
    }

    public AtomicRegionException(String arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public AtomicRegionException(Throwable arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public AtomicRegionException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    public AtomicRegionException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
    {
        super(arg0, arg1, arg2, arg3);
        // TODO Auto-generated constructor stub
    }

}
