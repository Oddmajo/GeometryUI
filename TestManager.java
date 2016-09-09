/*
 * iTutor – an intelligent tutor of mathematics

Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of

students)

This program is free software: you can redistribute it and/or modify it under

the terms of the GNU Affero General Public License as published by the Free

Software Foundation, either version 3 of the License, or (at your option) any

later version.

This program is distributed in the hope that it will be useful, but WITHOUT

ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS

FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more

details.
 */
/**
 * @author Tom_Nielsen
 *
 */

package iTutor;
import org.junit.runner.JUnitCore;
import java.lang.Class;
import java.util.ArrayList;


public abstract class TestManager
{
    private static ArrayList<Class> testClasses = new ArrayList<Class>();
    public void addClass(Class c)
    {
        if(!testClasses.contains(c))
        {
            testClasses.add(c);
        }
    }
    public static void run()
    {
        for(Class test : testClasses)
        {
            JUnitCore.runClasses(test);
        }
    }
    
}
