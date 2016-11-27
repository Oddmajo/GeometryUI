package backendTest.deductiveRulesTest.algebraTest;


import backend.utilities.logger.LoggerFactory;
import backend.utilities.test.TestManager;

public class SimplificationTest 
{
    public static void main(String[] args) throws Exception
    {
        LoggerFactory.initialize();
       
        TestManager.run();
         /*
        List<NumericValue> testList = new ArrayList<NumericValue>();
        for (int i = -100; i < 100; i++)
        {
            testList.add(new NumericValue(i));
        }
        long testsPassed = 0, totalTests = 0;

        System.out.print("Running NumericValue Tests...");

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

        if (totalTests == testsPassed)
            System.out.println("Done");
        else
            System.out.println("Failed");
        totalTests = testsPassed = 0;

        System.out.print("Running Addition Tests...");

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

        if (totalTests == testsPassed)
            System.out.println("Done");
        else
            System.out.println("Failed");
        totalTests = testsPassed = 0;

        System.out.print("Running Subtraction Tests...");


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

        if (totalTests == testsPassed)
            System.out.println("Done");
        else
            System.out.println("Failed");
        totalTests = testsPassed = 0;

        System.out.print("Running Multiplication Tests...");


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

        if (totalTests == testsPassed)
            System.out.println("Done");
        else
            System.out.println("Failed");
        totalTests = testsPassed = 0;

        System.out.print("Running Equation Tests...");
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


        for (int i = 0; i < 200; i++)
        {
            for (int j = 0; j < 200; j++)
            {

                Equation eq = new Equation(testList.get(i), testList.get(j));
                String expectedResult = eq.toString();
                String test = Simplification.simplify((eq)).toString();
                if (test.equals(expectedResult))
                    testsPassed++;
                else
                    System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expectedResult + "; found " + test);
                totalTests++;


            }
        }


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

        if (totalTests == testsPassed)
            System.out.println("Done");
        else
            System.out.println("Failed");
        totalTests = testsPassed = 0;

        System.out.print("Running Variable Tests...");

        List<Character> testChars = new ArrayList<Character>();
        for  (int i = 0; i < 26; i++)
        {
            char x = (char) (i + 97);
            testChars.add(x);
        }


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

                for (int k = 0; k < 26; k++)
                {
                    String expected = eq.toPrettyString(), result;
                    if (eq.containsVariable('x'))
                    {
                        expected = expected.replace('x', 'a');
                    }

                    eq.substitute('x', 'a');
                    result = eq.toPrettyString();
                    if (result.equals(expected))
                        testsPassed++;
                    else
                    {
                        System.out.println("Test " + (totalTests+1) + " failed.  Expected: " + expected + "; found " + result);
                    } 
                    totalTests++;
                }

            }
        }
        if (totalTests == testsPassed)
            System.out.println("Done");
        else
            System.out.println("Failed");
        totalTests = testsPassed = 0;

    */

        LoggerFactory.close();
    }
}

