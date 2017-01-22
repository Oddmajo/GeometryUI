package backendTest.astTest.figure.delegates.pointLiesOnTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.math.MathUtilities;
import backendTest.astTest.figure.EquationSegment;
import backendTest.astTest.figure.delegates.GenericTester;

/*
 * Simple test for epsilon-based point equality
 *  
 * @author C. Alvin
 */
public class PointsLiesOnSegmentTest extends GenericTester
{
    public PointsLiesOnSegmentTest() { super("PointLiesOnSegment Test"); }
    
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

    @Test
    public void runTest()
    {
        //
        // Run tests
        //
        _testErrorMap.put("equals", testSegmentEquality());
        _testErrorMap.put("on", testPointOnSegment());
        _testErrorMap.put("not on", testPointNotOnSegment());

        // Report; save the fact we ran these tests once
        _passedAllTests = report();
    }        
    private int testSegmentEquality()
    {
        int errors = 0;
        int xFixed = Math.abs(_rng.nextInt(10));
        int yFixed = Math.abs(_rng.nextInt(10));

        int xChange = Math.abs(_rng.nextInt(10));
        int yChange = Math.abs(_rng.nextInt(10));

        final double LOCAL_EPSILON = MathUtilities.EPSILON / 10;
        
        //
        // X updates, Y updates
        //
        double x = xChange - MathUtilities.EPSILON * 2;
        double y = yChange - MathUtilities.EPSILON * 2;
        for (int i = 0; i < 20; i++)
        {
            Point fixedPt = new Point("", xFixed, yFixed);

            Segment fixedSeg = new Segment(fixedPt, new Point("", xChange, yChange));
            Segment changingSeg = new Segment(fixedPt, new Point("", x, y));
            
            // Due to imprecision of double-precision floating point numbers, do not check exactly around EPSILON-distance away
            if (i != 10)
            {
                // Interval of inequality
                if (i < 10)
                {
                    if (fixedSeg.equals(changingSeg))
                    {
                        emitError("Expected segment inequality: " + fixedSeg + " " + changingSeg);
                        errors++;
                    }
                }
                // Interval of equality
                else
                {
                    if (!fixedSeg.equals(changingSeg))
                    {
                        emitError("Expected segment equality: " + fixedSeg + " " + changingSeg);
                        errors++;
                    }
                }
            }
            x += LOCAL_EPSILON;
            y += LOCAL_EPSILON;
        }

        return errors;
    }

    //
    // This test randomly verifies points < EPSILON coordinate distance from the segment
    //
    private int testPointOnSegment()
    {
        int errors = 0;

        // Randomly generate a point exactly on the segment
        int leftX = Math.abs(_rng.nextInt(10));
        int rightX = leftX + Math.abs(_rng.nextInt(10)) + 1;
        int y = Math.abs(_rng.nextInt(10));

        // This is a horizontal line
        Point p1 = new Point("", leftX, y);
        Point p2 = new Point("", rightX, y);
        
        Segment fixedHoriz = new Segment(p1, p2);
        final int NUM_TEST_POINTS = 1000;
        for (int i = 0; i < NUM_TEST_POINTS; i++)
        {
            // A point on the line
            double randX = leftX + _rng.nextDouble() * (rightX - leftX);

            // Direct on
            Point onPt = new Point("", randX, y);
            if (!fixedHoriz.pointLiesOn(onPt))
            {
                emitError(fixedHoriz + " should contain " + onPt);
                errors++;
            }
            
            // Direct on: under EPSILON distance away
            Point onPtEpsilon = new Point("", randX, y + MathUtilities.EPSILON * Math.random());
            if (!fixedHoriz.pointLiesOn(onPtEpsilon))
            {
                emitError(fixedHoriz + " should contain " + onPtEpsilon);
                errors++;
            }
        }

        return errors;
    }
    
    //
    // This test randomly verifies points > EPSILON coordinate distance from the segment
    //
    private int testPointNotOnSegment()
    {
        int errors = 0;

        // Randomly generate a point exactly on the segment
        int leftX = Math.abs(_rng.nextInt(10));
        int rightX = leftX + Math.abs(_rng.nextInt(10)) + 1;
        int y = Math.abs(_rng.nextInt(10));

        // This is a horizontal line
        Point p1 = new Point("", leftX, y);
        Point p2 = new Point("", rightX, y);
        
        Segment fixedHoriz = new Segment(p1, p2);
        final int NUM_TEST_POINTS = 1000;
        for (int i = 0; i < NUM_TEST_POINTS; i++)
        {
            // A point on the line
            double randX = leftX + _rng.nextDouble() * (rightX - leftX);

            // > EPSILON distance away (below)
            Point notOnPtEpsilon = new Point("", randX, y - MathUtilities.EPSILON * (1 + Math.random()));
            if (!fixedHoriz.pointLiesOn(notOnPtEpsilon))
            {
                emitError(fixedHoriz + " (-) should not contain " + notOnPtEpsilon);
                errors++;
            }
            
            // > EPSILON distance away (above)
            notOnPtEpsilon = new Point("", randX, y + MathUtilities.EPSILON * (1 + Math.random()));
            if (!fixedHoriz.pointLiesOn(notOnPtEpsilon))
            {
                emitError(fixedHoriz + " (+) should contain " + notOnPtEpsilon);
                errors++;
            }
        }

        return errors;
    }
}