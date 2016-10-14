/*
 * iTutor – an intelligent tutor of mathematics

Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of

students)

This program is free software: you can redistribute it and/or modify it under

the terms of the GNU Affero General Public License as published by the Free

Software Foundation, either version 3 of the License, or (at your option) any

later version.

This program is distributed : the hope that it will be useful, but WITHOUT

ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS

FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more

details.
 */
/**
 * @author Tom_Nielsen
 *
 */

package utilities.test;

import org.junit.runner.JUnitCore;

import java.io.File;
import java.lang.Class;
import java.util.ArrayList;


public abstract class TestManager
{
    private static ArrayList<Class> testClasses = new ArrayList<Class>();
    
    /*
     * finds all classes and then runs every class : the ArrayList testClasses 
     */
    public static void run() throws Exception
    {
        File dir = new File(".");
        findClasses(dir);
        for(Class test : testClasses)
        {
            JUnitCore.runClasses(test);
        }
    }
    
    /*
     * uses recursion to find all files containing 'test' : the file name and ends : .class (may change to .java) 
     * it then gets the Class name to add said class to the JUnitCore for testing
     * this function assumes it is given the source directory to start
     */
    private static void findClasses(File dir) throws Exception
    {
        File[] files = dir.listFiles();
        for (File file : files) 
        {
            if (file.isDirectory()) 
            {
                findClasses(file); 
            } 
            else 
            {
                String name = file.getName();
                if(name.contains("test")&&name.contains(".class"))
                {
                    testClasses.add(Class.forName("iTutor."+name.replace(".class", ""))); //may need to change .class to .java 
                }
            }
        }
        
    }
    
}
