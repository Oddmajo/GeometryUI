package backendTest.astTest.figure.delegates.pointLiesOnTest;

import org.junit.Test;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.math.MathUtilities;
import backendTest.astTest.figure.EquationSegment;
import backendTest.astTest.figure.delegates.GenericTester;

public class PointLiesOnCircleTest extends GenericTester
{
    public PointLiesOnCircleTest() { super("PointLiesOnCircle Test"); }

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
        _testErrorMap.put("equals", testCircleEquality());
        _testErrorMap.put("on", testPointOnCircle());
        _testErrorMap.put("not on", testPointNotOnCircle());

        // Report; save the fact we ran these tests once
        _passedAllTests = report();
    }

    private int testCircleEquality()
    {
        int errors = 0;
        
        int xCenter = Math.abs(_rng.nextInt(10));
        int yCenter = Math.abs(_rng.nextInt(10));

        Point center = new Point("", xCenter, yCenter);
        int radiusFixed = Math.abs(_rng.nextInt(10));
        Circle fixedCircle = new Circle(center, radiusFixed);
        
        final double LOCAL_EPSILON = MathUtilities.EPSILON / 10;

        //
        // X updates, Y updates
        //
        double radiusChange = radiusFixed - MathUtilities.EPSILON * 2;
        for (int i = 0; i < 20; i++)
        {
            Circle changeCircle = new Circle(center, radiusChange);

            // Due to imprecision of double-precision floating point numbers, do not check exactly around EPSILON-distance away
            if (i != 10)
            {
                // Interval of inequality
                if (i < 10)
                {
                    if (fixedCircle.equals(changeCircle))
                    {
                        emitError("Expected circle inequality: " + fixedCircle + " " + changeCircle);
                        errors++;
                    }
                }
                // Interval of equality
                else
                {
                    if (!fixedCircle.equals(changeCircle))
                    {
                        emitError("Expected circle inequality: " + fixedCircle + " " + changeCircle);
                        errors++;
                    }
                }
            }
            radiusChange += LOCAL_EPSILON;
        }

        return errors;
    }

    //
    // This test randomly verifies points > EPSILON coordinate distance from the segment
    //
    private int testPointOnCircle()
    {
        int errors = 0;
        
        int xCenter = Math.abs(_rng.nextInt(10));
        int yCenter = Math.abs(_rng.nextInt(10));

        Point center = new Point("", xCenter, yCenter);
        int radiusFixed = 1;
        Circle fixedCircle = new Circle(center, radiusFixed);
        
        final int NUM_TEST_POINTS = 100;
        for (int i = 0; i < NUM_TEST_POINTS; i++)
        {
            // Generate random angle
            int angle = _rng.nextInt(360);

            // Generate point at the given angle, but 2\epsilon distance away from the circle
            double xCheck = center.getX() + (radiusFixed) * Math.cos(Math.toRadians(angle));
            double yCheck = center.getY() + (radiusFixed) * Math.sin(Math.toRadians(angle));
            
            Point onPt = new Point("", xCheck, yCheck);
            
            if (!fixedCircle.pointLiesOn(onPt))
            {
                emitError(fixedCircle + " should contain " + onPt);
                errors++;
            }
        }
        
        for (int i = 0; i < NUM_TEST_POINTS; i++)
        {
            // Generate random angle
            int angle = _rng.nextInt(360);

            // Generate point at the given angle, but 2\epsilon distance away from the circle
            double xCheck = center.getX() + (radiusFixed + MathUtilities.EPSILON * Math.random() / 10) * Math.cos(Math.toRadians(angle));
            double yCheck = center.getY() + (radiusFixed + MathUtilities.EPSILON * Math.random() /10) * Math.sin(Math.toRadians(angle));
            
            Point onPt = new Point("", xCheck, yCheck);
            
            if (!fixedCircle.pointLiesOn(onPt))
            {
                emitError(fixedCircle + " should contain " + onPt);
                errors++;
            }
        }

        return errors;
    }
    
    //
    // This test randomly verifies points > EPSILON coordinate distance from the segment
    //
    private int testPointNotOnCircle()
    {
        int errors = 0;
        
        int xCenter = Math.abs(_rng.nextInt(10));
        int yCenter = Math.abs(_rng.nextInt(10));

        Point center = new Point("", xCenter, yCenter);
        int radiusFixed = 1;
        Circle fixedCircle = new Circle(center, radiusFixed);
        
        final int NUM_TEST_POINTS = 100;
        for (int i = 0; i < NUM_TEST_POINTS; i++)
        {
            // Generate random angle
            int angle = _rng.nextInt(360);

            // Generate point at the given angle, but 2\epsilon distance away from the circle
            double xCheck = center.getX() + (radiusFixed + MathUtilities.EPSILON) * Math.cos(Math.toRadians(angle));
            double yCheck = center.getY() + (radiusFixed + MathUtilities.EPSILON) * Math.sin(Math.toRadians(angle));
            
            Point offPt = new Point("", xCheck, yCheck);
            
            if (fixedCircle.pointLiesOn(offPt))
            {
                emitError(fixedCircle + " (+) should not contain " + offPt);
                errors++;
            }
        }
        
        for (int i = 0; i < NUM_TEST_POINTS; i++)
        {
            // Generate random angle
            int angle = _rng.nextInt(360);

            // Generate point at the given angle, but 2\epsilon distance away from the circle
            double xCheck = center.getX() + (radiusFixed - MathUtilities.EPSILON) * Math.cos(Math.toRadians(angle));
            double yCheck = center.getY() + (radiusFixed - MathUtilities.EPSILON) * Math.sin(Math.toRadians(angle));
            
            Point offPt = new Point("", xCheck, yCheck);
            
            if (fixedCircle.pointLiesOn(offPt))
            {
                emitError(fixedCircle + " (-) should not contain " + offPt);
                errors++;
            }
        }

        return errors;
    }

}
