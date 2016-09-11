/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package exceptionHandlerTest;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import exceptionHandler.ExceptionHandler;
import exceptionHandler.GeometryException;

/**
 * A test class that tests the functionality of the ExceptionHandler class
 * using JUnit tests and a main to show the string output of the static 
 * methods.
 * @author Drew W
 *
 */
public class ExceptionHandlerTest
{
    /**
     * JUnit test for throwException with string input.
     * <p>Tests if string output is null or empty.
     * @author Drew Whitmire
     */
    @Test public void stringExceptionTest()
    {
        assertTrue(ExceptionHandler.throwException("test") != null);
        assertTrue(ExceptionHandler.throwException("test") != "");
    }
    
    
    /**
     * JUnit test for throwException with Exception input.
     * Tests if string output is null or empty.
     * @author Drew Whitmire
     */
    @Test public void exceptionExceptionTest()
    {
        Exception e = new Exception("test");
        
        assertTrue(ExceptionHandler.throwException(e) != null);
        assertTrue(ExceptionHandler.throwException(e) != "");
    }
    
    /**
     * JUnit test for throwException with GeometryException input.
     * Tests if string output is null or empty.
     * @author Drew Whitmire
     */
    @Test public void geometryExceptionTest()
    {
        GeometryException g = new GeometryException("test");
        
        assertTrue(ExceptionHandler.throwException(g) != null);
        assertTrue(ExceptionHandler.throwException(g) != "");
    }
    
    
    // 
    /**
     * This is to visually see that the outputs for throwException is standardized
     * for String, GeometryException, and Exception arguments.
     * @param args      the main method takes no input.
     * @author Drew Whitmire
     */
    public static void main(String[] args)
    {
        // test the String argument
        System.out.println(ExceptionHandler.throwException("this is what a string exception looks like"));
        
        // test the Exception argument
        try 
        {
            new ArrayList<Object>().get(0);
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println( ExceptionHandler.throwException(e) );
        }
        
        // test the GeometryException argument
        GeometryException geoexcept = new GeometryException("this what a geometry exception looks like");
        System.out.println( ExceptionHandler.throwException(geoexcept) );
    }
}
