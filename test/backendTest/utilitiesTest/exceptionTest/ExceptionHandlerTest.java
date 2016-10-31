/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backendTest.utilitiesTest.exceptionTest;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

import backend.ast.ASTException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.exception.GeometryException;
import backend.utilities.exception.TriangleException;
import backend.utilities.logger.LoggerFactory;

/**
 * A test class that tests the functionality of the ExceptionHandler class
 * using JUnit tests and a main to show the string output of the static 
 * methods.
 * @author Drew W
 *
 */
public class ExceptionHandlerTest
{   
    
    // 
    /**
     * This is to visually see that the outputs for throwException is standardized
     * for String, GeometryException, and Exception arguments.
     * <p>
     * loggerID that is being logged so can also be checked 
     * @param args      the main method takes no input.
     * @author Drew Whitmire
     * @throws IOException 
     */
    @Test public void ExceptionToLoggerTest() throws IOException 
    {
        // build the GeometryException Logger
        System.out.print("Running ExceptionToLoggerTest...");
        LoggerFactory.initialize();
        
        // test the String argument
        ExceptionHandler.throwException("this is what a string exception looks like");
        
        // test the GeometryException argument
        GeometryException geoexcept = new GeometryException("this what a geometry exception looks like");
        ExceptionHandler.throwException(geoexcept);
        
        // test the TriangleException argument, a subclass of GeometryException
        TriangleException triexcept = new TriangleException("This is what a triangle exception looks like");
        ExceptionHandler.throwException(triexcept);
        
        // another GeometryException
        GeometryException geoexception = new GeometryException("This is a test of logging a GeometryException to a logger");
        ExceptionHandler.throwException(geoexception);
        
        // ASTExceptionT test
        ASTException astexception = new ASTException("This is what an AST exception looks like");
        ExceptionHandler.throwException(astexception);
        
        // default logger test
        LoggerFactory.getLogger(LoggerFactory.DEFAULT_OUTPUT_ID).writeln("This is a test");
        

        
        // close the Loggers
        LoggerFactory.close();
        
        System.out.println("Done");
    }
}
