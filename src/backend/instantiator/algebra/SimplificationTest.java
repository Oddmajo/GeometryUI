package backend.instantiator.algebra;

import backend.instantiator.algebra.Simplification;
import backend.equations.*;
import backend.instantiator.GenericRule;
import backend.utilities.Pair;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.list.Utilities;
import backend.equations.operations.*; 

import java.util.ArrayList;
import java.util.List;
/*
 * NOTES FROM RYAN:  
 * 1) Simplify apparently doesn't work with my operations, even though they derive from Equation...
 * 
 */
@SuppressWarnings("unused")
public class SimplificationTest 
{

    public static void main(String[] args) throws CloneNotSupportedException, ArgumentException
    {
        List<NumericValue> testList = new ArrayList<NumericValue>();
        for (int i = -100; i < 100; i++)
        {
            testList.add(new NumericValue(i));
        }
        long testsPassed = 0, totalTests = 0;
        /*
        System.out.println("\t---NUMERIC VALUE TESTS---");

        for (int i = 0; i < 200; i++)
        {
            String test = testList.get(i).toString();
            String expectedResult = i - 100 + ".0";
            if (test.equals(expectedResult))
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("toString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            String test = testList.get(i).toPrettyString();
            String expectedResult = i - 100 + "";
            if (test.equals(expectedResult))
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("toPrettyString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            double test = testList.get(i).getDoubleValue();
            double expectedResult = i - 100.0;
            if (test == expectedResult)
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("getDoubleValue() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;


        for (int i = 0; i < 200; i++)
        {
            int test = testList.get(i).getIntValue();
            int expectedResult = i - 100;
            if (test == expectedResult)
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("getIntValue() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                boolean test = testList.get(i).containsClause(testList.get(j));
                boolean expectedResult = (i == j) ? true : false; 
                if (test == expectedResult)
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }

        System.out.println("containsClause() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        System.out.println("\n\t---ADDITION TESTS---");

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Addition add = new Addition(testList.get(i), testList.get(j));
                String test = add.toString();
                String expectedResult = "(" + (i-100.0) + " + " + (j-100.0) + ")"; 
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("toString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;


        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Addition add = new Addition(testList.get(i), testList.get(j));
                String test = add.toPrettyString();
                String expectedResult = (i-100) + " + " + (j-100); 
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("toPrettyString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Addition add = new Addition(testList.get(i), testList.get(j));
                boolean test = add.containsClause(testList.get(i));
                boolean expectedResult = (((NumericValue)add.getLeftExp()).getIntValue() == ((i)-100)  || ((NumericValue)add.getLeftExp()).getIntValue() == ((j)-100)) ? true : false ; 
                if (test == expectedResult)
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("containsClause() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        //System.out.println("Simplification of 2+3: " + Simplification.simplify((additionEquation)));

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                for (int k = 0; k < 200; k++)
                {
                    Addition add = new Addition(testList.get(i), testList.get(j));
                    Addition expectedResult = new Addition(add.getLeftExp() == testList.get(k) ? testList.get(j) : testList.get(i), add.getRightExp() == testList.get(k)? testList.get(j) : testList.get(j));         
                    add.substitute(testList.get(k), testList.get(j));
                    if (add.equals(expectedResult))
                        testsPassed++;
                    else
                    {
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + add);
                    } 
                    totalTests++;
                }
            }
        }
        System.out.println("substitute() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {

                Addition eq = new Addition(testList.get(i), testList.get(j));
                String test = "Simplification of " + eq.toPrettyString() + ":";
                if (!test.equals(null))
                    testsPassed++;
                else
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: ERROR 404" + "; found " + test);
                totalTests++;
            }
        }
        System.out.println("simplify() tests \"passed\": " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;


        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                for (int k = 0; k < 200; k++)
                {
                    Addition eq = new Addition(testList.get(i), testList.get(j));
                    Addition eq2 = new Addition (eq, testList.get(k));
                    String test = eq2.toString();
                    String expectedResult = "(" + eq.toString() + " + " + (k-100) + ".0)";
                    if (test.equals(expectedResult))
                    {
                        String test2 = eq2.toPrettyString();
                        String expectedResult2 = eq.toPrettyString() + " + " + (k-100);
                        if (test2.equals(expectedResult2))
                            testsPassed++;
                        else
                            System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult2 + "; found " + test2);
                    }
                    else
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                    totalTests++;
                }
            }
        }
        System.out.println("Multi-clause tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        System.out.println("\n\t---SUBTRACTION TESTS---");

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Subtraction subtract = new Subtraction(testList.get(i), testList.get(j));
                String test = subtract.toString();
                String expectedResult = "(" + (i-100.0) + " - " + (j-100.0) + ")"; 
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("toString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Subtraction subtract = new Subtraction(testList.get(i), testList.get(j));
                String test = subtract.toPrettyString();
                String expectedResult = (i-100) + " - " + (j-100); 
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("toPrettyString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;


        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Subtraction subtract = new Subtraction(testList.get(i), testList.get(j));
                boolean test = subtract.containsClause(testList.get(i));
                boolean expectedResult = (((NumericValue)subtract.getLeftExp()).getIntValue() == ((i)-100)  || ((NumericValue)subtract.getLeftExp()).getIntValue() == ((j)-100)) ? true : false ; 
                if (test == expectedResult)
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("containsClause() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        //System.out.println("Simplification of 2+3: " + Simplification.simplify((subtract)));

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                for (int k = 0; k < 200; k++)
                {
                    Subtraction subtract = new Subtraction(testList.get(i), testList.get(j));
                    Subtraction expectedResult = new Subtraction(subtract.getLeftExp() == testList.get(k) ? testList.get(j) : testList.get(i), subtract.getRightExp() == testList.get(k)? testList.get(j) : testList.get(j));         
                    subtract.substitute(testList.get(k), testList.get(j));
                    if (subtract.equals(expectedResult))
                        testsPassed++;
                    else
                    {
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + subtract);
                    } 
                    totalTests++;
                }
            }
        }
        System.out.println("substitute() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                for (int k = 0; k < 200; k++)
                {
                    Subtraction eq = new Subtraction(testList.get(i), testList.get(j));
                    Subtraction eq2 = new Subtraction (eq, testList.get(k));
                    String test = eq2.toString();
                    String expectedResult = "(" + eq.toString() + " - " + (k-100) + ".0)";
                    if (test.equals(expectedResult))
                    {
                        String test2 = eq2.toPrettyString();
                        String expectedResult2 = eq.toPrettyString() + " - " + (k-100);
                        if (test2.equals(expectedResult2))
                            testsPassed++;
                        else
                            System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult2 + "; found " + test2);
                    }
                    else
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                    totalTests++;
                }
            }
        }
        System.out.println("Multi-clause tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        System.out.println("\n\t---MULTIPLICATION TESTS---");

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Multiplication multiply = new Multiplication(testList.get(i), testList.get(j));
                String test = multiply.toString();
                String expectedResult = "(" + (i-100.0) + " * " + (j-100.0) + ")"; 
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("toString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Multiplication multiply = new Multiplication(testList.get(i), testList.get(j));
                String test = multiply.toPrettyString();
                String expectedResult = (i-100) + " * " + (j-100); 
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("toPrettyString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;


        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Multiplication multiply = new Multiplication(testList.get(i), testList.get(j));
                boolean test = multiply.containsClause(testList.get(i));
                boolean expectedResult = (((NumericValue)multiply.getLeftExp()).getIntValue() == ((i)-100)  || ((NumericValue)multiply.getLeftExp()).getIntValue() == ((j)-100)) ? true : false ; 
                if (test == expectedResult)
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("containsClause() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;



        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                for (int k = 0; k < 200; k++)
                {
                    Multiplication multiply = new Multiplication(testList.get(i), testList.get(j));
                    Multiplication expectedResult = new Multiplication(multiply.getLeftExp() == testList.get(k) ? testList.get(j) : testList.get(i), multiply.getRightExp() == testList.get(k)? testList.get(j) : testList.get(j));         
                    multiply.substitute(testList.get(k), testList.get(j));
                    if (multiply.equals(expectedResult))
                        testsPassed++;
                    else
                    {
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + multiply);
                    } 
                    totalTests++;
                }
            }
        }
        System.out.println("substitute() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                for (int k = 0; k < 200; k++)
                {
                    Multiplication eq = new Multiplication(testList.get(i), testList.get(j));
                    Multiplication eq2 = new Multiplication (eq, testList.get(k));
                    String test = eq2.toString();
                    String expectedResult = "(" + eq.toString() + " * " + (k-100) + ".0)";
                    if (test.equals(expectedResult))
                    {
                        String test2 = eq2.toPrettyString();
                        String expectedResult2 = eq.toPrettyString() + " * " + (k-100);
                        if (test2.equals(expectedResult2))
                            testsPassed++;
                        else
                            System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult2 + "; found " + test2);
                    }
                    else
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                    totalTests++;
                }
            }
        }
        System.out.println("Multi-clause tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;


        System.out.println("\n\t---EQUATION TESTS---");

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Equation eq = new Equation(testList.get(i), testList.get(j));
                String test = eq.toString();
                String expectedResult = "(" + (i-100.0) + " = " + (j-100.0) + ")"; 
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("toString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Equation eq = new Equation(testList.get(i), testList.get(j));
                String test = eq.toPrettyString();
                String expectedResult = (i-100) + " = " + (j-100); 
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("toPrettyString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;


        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                Equation eq = new Equation(testList.get(i), testList.get(j));
                boolean test = eq.containsClause(testList.get(i));
                boolean expectedResult = (((NumericValue)eq.lhs).getIntValue() == ((i)-100)  || ((NumericValue)eq.lhs).getIntValue() == ((j)-100)) ? true : false ; 
                if (test == expectedResult)
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("containsClause() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;



        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                for (int k = 0; k < 200; k++)
                {
                    Equation eq = new Equation(testList.get(i), testList.get(j)); 
                    Equation expectedResult = new Equation(eq.lhs == testList.get(k) ? testList.get(j) : testList.get(i), eq.lhs == testList.get(k)? testList.get(j) : testList.get(j));  
                    eq.substitute(testList.get(k), testList.get(j));
                    if (eq.equals(expectedResult))
                        testsPassed++;
                    else
                    {
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + eq);
                    } 
                    totalTests++;
                }
            }
        }
        System.out.println("substitute() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {

                Equation eq = new Equation(testList.get(i), testList.get(j));
                String test = "Simplification of " + eq.toPrettyString() + ": " + Simplification.simplify((eq));
                if (!test.equals(null))
                    testsPassed++;
                else
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: ERROR 404" + "; found " + test);
                totalTests++;

            }
        }
        System.out.println("simplify() tests \"passed\": " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {
                for (int k = 0; k < 200; k++)
                {
                    Equation eq = new Equation(testList.get(i), testList.get(j));
                    Equation eq2 = new Equation (eq, testList.get(k));
                    String test = eq2.toString();
                    String expectedResult = "(" + eq.toString() + " = " + (k-100) + ".0)";
                    if (test.equals(expectedResult))
                    {
                        String test2 = eq2.toPrettyString();
                        String expectedResult2 = eq.toPrettyString() + " = " + (k-100);
                        if (test2.equals(expectedResult2))
                            testsPassed++;
                        else
                            System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult2 + "; found " + test2);
                    }
                    else
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                    totalTests++;
                }
            }
        }
        System.out.println("Multi-clause tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0; 
         */

        System.out.println("\n\t---VARIABLE TESTS---");
        List<Character> testChars = new ArrayList<Character>();
        for  (int i = 0; i < 26; i++)
        {
            char x = (char) (i + 97);
            testChars.add(x);
        }

        System.out.println(testChars);


        for (int i = 0; i < 200; i++)
        {
            ArrayList<Character> sample = new ArrayList<Character>();
            sample.add('x');
            sample.add('y');
            NumericValue value = new NumericValue(i-100, sample);
            String test = value.toString();
            String expectedResult = i - 100 + ".0xy";
            if (test.equals(expectedResult))
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("toString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            ArrayList<Character> sample = new ArrayList<Character>();
            sample.add('x');
            sample.add('y');
            NumericValue value = new NumericValue(i-100, sample);
            String test = value.toPrettyString();
            String expectedResult = i - 100 + "xy";
            if (test.equals(expectedResult))
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("toPrettyString() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            ArrayList<Character> sample = new ArrayList<Character>();
            sample.add('x');
            sample.add('y');
            NumericValue value = new NumericValue(i-100, sample);
            double test = value.getDoubleValue();
            double expectedResult = i - 100.0;
            if (test == expectedResult)
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("getDoubleValue() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;


        for (int i = 0; i < 200; i++)
        {
            ArrayList<Character> sample = new ArrayList<Character>();
            sample.add('x');
            sample.add('y');
            NumericValue value = new NumericValue(i-100, sample);
            int test = value.getIntValue();
            int expectedResult = i - 100;
            if (test == expectedResult)
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("getIntValue() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            ArrayList<Character> sample = new ArrayList<Character>();
            sample.add('x');
            sample.add('y');
            NumericValue value = new NumericValue(i-100, sample);
            List<Character> t1 = value.getVariables();
            String test = "";
            for (char c: t1)
                test += c;
            String expectedResult = "xy";
            if (test.equals(expectedResult))
                testsPassed++;
            else
            {
                System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
            }
            totalTests++;
        }

        System.out.println("getVariables() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            ArrayList<Character> sample = new ArrayList<Character>();
            sample.add('x');
            sample.add('y');
            NumericValue eq = new NumericValue(testList.get(i), sample);
            for (int j = 0; j < 200; j++)
            {
                boolean test = eq.containsClause(testList.get(i));
                boolean expectedResult = eq.getIntValue() == ((i)-100)  || eq.getIntValue() == ((j)-100) ? true : false ;
                if (test == expectedResult)
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }

        System.out.println("containsClause() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            ArrayList<Character> sample = new ArrayList<Character>();
            sample.add('x');
            sample.add('y');
            NumericValue eq = new NumericValue(testList.get(i), sample);
            for (int j = 0; j < 26; j++)
            {
                char x = (char) (j + 97);
                boolean test = eq.containsVariable(x);
                boolean expectedResult;
                if (x == 'x' || x == 'y')
                    expectedResult = true;
                else
                    expectedResult = false;
                if (test == expectedResult)
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                } 
                totalTests++;
            }
        }
        System.out.println("containsVariable() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

        for (int i = 0; i < 200; i++)
        {
            ArrayList<Character> sample = new ArrayList<Character>();
            sample.add('x');
            sample.add('y');
            
            for (int j = 0; j < 200; j++)
            {
                NumericValue eq = new NumericValue(testList.get(i), sample);
                NumericValue expectedResult = new NumericValue(testList.get(j), sample);  
                eq.substitute(testList.get(i).getDoubleValue(), testList.get(j).getDoubleValue());
                if (eq.equals(expectedResult))
                    testsPassed++;
                else
                {
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + eq);
                } 
                totalTests++;

            }
        }
        System.out.println("substitute() tests passed: " + testsPassed + "/" + totalTests);
        testsPassed = 0;
        totalTests = 0;

    }

}

