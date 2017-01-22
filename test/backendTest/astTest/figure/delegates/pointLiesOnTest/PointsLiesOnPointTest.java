package backendTest.astTest.figure.delegates.pointLiesOnTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.utilities.math.MathUtilities;
import backendTest.astTest.figure.delegates.GenericTester;

/*
 * Simple test for epsilon-based point equality
 *  
 * @author C. Alvin
 */
public class PointsLiesOnPointTest extends GenericTester
{
    //
    // > 0: Test has been run with errors
    // == 0: Test passed with zero errors
    // < 0: Test has not been run
    //
    protected static int _passedAllTests = -1;

    public boolean passed() { return PointsLiesOnPointTest.passedTests(); }
    public boolean completed() { return PointsLiesOnPointTest.completedTests(); }

    public static boolean passedTests() { return _passedAllTests == 0; }
    public static boolean completedTests() { return _passedAllTests >= 0; }

    public PointsLiesOnPointTest() { super("PointLiesOnPoint Test"); }

    @Test
    public void runTest()
    {
        // Check if we've run these tests before; prevent redundancy
        if (completed()) return;

        //
        // Run tests
        //
        _testErrorMap.put("epsilon-based structural point equality", testPointEquality());

        // Report; save the fact we ran these tests once
        _passedAllTests = report();
    }        

    private int testPointEquality()
    {
        int errors = 0;
        int xCoord = Math.abs(_rng.nextInt(10));
        int yCoord = Math.abs(_rng.nextInt(10));

        // Unnamed / unnamed test
        errors += pointCheck(new Point("", xCoord, yCoord), "");

        // Unnamed / named 
        errors += pointCheck(new Point("", xCoord, yCoord), "");

        // Named / unnamed 
        errors += pointCheck(new Point("", xCoord, yCoord), "");

        // Named / unnamed 
        errors += pointCheck(new Point("", xCoord, yCoord), "");

        return errors;
    }

    private int pointCheck(Point fixed, String name)
    {
        int errors = 0;

        if (!fixed.equals(fixed))
        {
            emitError("Point equality is not reflexive: " + fixed.toString());
            errors++;
        }

        final double LOCAL_EPSILON = MathUtilities.EPSILON / 10;

        //
        // X updates, Y is fixed
        //
        double x = fixed.getX() - MathUtilities.EPSILON * 2;
        double y = fixed.getY();
        for (int i = 0; i < 20; i++)
        {
            Point copy = new Point(name, x, y);

            // Due to imprecision of double-precision floating point numbers, do not check exactly around EPSILON-distance away
            if (i != 10)
            {
                // Interval of inequality
                if (i < 10)
                {
                    if (fixed.equals(copy))
                    {
                        emitError("Expected point inequality (X): " + fixed.getX() + " " + copy.getX());
                        errors++;
                    }
                }
                // Interval of equality
                else
                {
                    if (!fixed.equals(copy))
                    {
                        emitError("Expected point equality (X): " + fixed.getX() + " " + copy.getX());
                        errors++;
                    }
                }
            }
            x += LOCAL_EPSILON;
        }

        //
        // X updates, Y is fixed
        //
        x = fixed.getX();
        y = fixed.getY() - MathUtilities.EPSILON * 2;
        for (int i = 0; i < 20; i++)
        {
            Point copy = new Point(name, x, y);

            // Due to imprecision of double-precision floating point numbers, do not check exactly around EPSILON-distance away
            if (i != 10)
            {
                // Interval of inequality
                if (i < 10)
                {
                    if (fixed.equals(copy))
                    {
                        emitError("Expected point inequality (Y): " + fixed.getY() + " " + copy.getY());
                        errors++;
                    }
                }
                // Interval of equality
                else
                {
                    if (!fixed.equals(copy))
                    {
                        emitError("Expected point equality (Y): " + fixed.getY() + " " + copy.getY());
                        errors++;
                    }
                }
            }
            y += LOCAL_EPSILON;
        }

        //
        // X updates, Y updates (both positive)
        //
        x = fixed.getX() - MathUtilities.EPSILON * 2;
        y = fixed.getY() - MathUtilities.EPSILON * 2;
        for (int i = 0; i < 20; i++)
        {
            Point copy = new Point(name, x, y);

            // Due to imprecision of double-precision floating point numbers, do not check exactly around EPSILON-distance away
            if (i != 10)
            {
                // Interval of inequality
                if (i < 10)
                {
                    if (fixed.equals(copy))
                    {
                        emitError("Expected point inequality (X+, Y+): " + fixed.getY() + " " + copy.getY());
                        errors++;
                    }
                }
                // Interval of equality
                else
                {
                    if (!fixed.equals(copy))
                    {
                        emitError("Expected point equality (X+, Y+): " + fixed.getY() + " " + copy.getY());
                        errors++;
                    }
                }
            }
            x += LOCAL_EPSILON;
            y += LOCAL_EPSILON;
        }

        //
        // X updates, Y updates (x increase, y decrease)
        //
        x = fixed.getX() - MathUtilities.EPSILON * 2;
        y = fixed.getY() + MathUtilities.EPSILON * 2;
        for (int i = 0; i < 20; i++)
        {
            Point copy = new Point(name, x, y);

            // Due to imprecision of double-precision floating point numbers, do not check exactly around EPSILON-distance away
            if (i != 10)
            {
                // Interval of inequality
                if (i < 10)
                {
                    if (fixed.equals(copy))
                    {
                        emitError("Expected point inequality (X+, Y-): " + fixed.getY() + " " + copy.getY());
                        errors++;
                    }
                }
                // Interval of equality
                else
                {
                    if (!fixed.equals(copy))
                    {
                        emitError("Expected point equality (X+, Y-): " + fixed.getY() + " " + copy.getY());
                        errors++;
                    }
                }
            }
            x += LOCAL_EPSILON;
            y -= LOCAL_EPSILON;
        }

        //
        // X updates, Y updates (x decrease, y increase)
        //
        x = fixed.getX() + MathUtilities.EPSILON * 2;
        y = fixed.getY() - MathUtilities.EPSILON * 2;
        for (int i = 0; i < 20; i++)
        {
            Point copy = new Point(name, x, y);

            // Due to imprecision of double-precision floating point numbers, do not check exactly around EPSILON-distance away
            if (i != 10)
            {
                // Interval of inequality
                if (i < 10)
                {
                    if (fixed.equals(copy))
                    {
                        emitError("Expected point inequality (X-, Y+): " + fixed.getY() + " " + copy.getY());
                        errors++;
                    }
                }
                // Interval of equality
                else
                {
                    if (!fixed.equals(copy))
                    {
                        emitError("Expected point equality (X-, Y+): " + fixed.getY() + " " + copy.getY());
                        errors++;
                    }
                }
            }
            x -= LOCAL_EPSILON;
            y += LOCAL_EPSILON;
        }

        return errors;
    }
}