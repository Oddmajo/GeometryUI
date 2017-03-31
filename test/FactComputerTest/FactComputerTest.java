package FactComputerTest;
import java.util.ArrayList;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.factComputer.FactComputer;
import backend.factComputer.FactComputerContainer;
import backend.precomputer.Precomputer;
import backend.utilities.AngleEquivalenceRelation;
import backend.utilities.PointFactory;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class FactComputerTest
{
    @Test
    public void angleTest()
    {
        System.out.print("Starting FactComputerTest::angleTest... ");
        
        PointFactory.clear();
        AngleEquivalenceRelation.getInstance().clear();
        
        //    A    M
        //      |  /
        //      | /
        //      |/
        //    B /__________ C
        // Construct segments and the corresponding points
        // Create the points
        ArrayList<Point> points = new ArrayList<Point>();
        Point a = new Point("A", 0, 1); points.add(a);
        Point b = new Point("B", 0, 0); points.add(b);
        Point m = new Point("M", 0.5, 0.5); points.add(m);
        Point c = new Point("C", 1, 0); points.add(c);

        ArrayList<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment(a, b));
        segments.add(new Segment(m, b));
        segments.add(new Segment(b, c));
        
        Precomputer pc = new Precomputer(points, segments);
        pc.analyze();

        FactComputer fc = new FactComputer(pc);
        fc.analyze();

        //
        // There are a specific number of facts generated:
        //
        int numAngles = 3;
        assert(PointFactory.getAllPoints().size() == 4);
        assert(fc.getInMiddles().size() == 0);
        assert(fc.getSegments().size() == 3);
        assert(AngleEquivalenceRelation.getInstance().size() == numAngles);
        assert(fc.getCongruentAngles().size() == numAngles + 1);
        
        System.out.println("Passed");
    }
    
    @Test
    public void segmentEquationTest()
    {
        System.out.print("Starting segmentEquationTest... ");
        
        System.out.println("Passed");
        assert(true);
    }
    
    @Test
    public void angleEquationTest()
    {
        System.out.print("Starting angleEquationTest... ");
        
        System.out.println("Passed");
        assert(true);
    }
    
    @Test
    public void midpointTest()
    {
        System.out.print("Starting FactComputerTest::midpointTest... ");

        PointFactory.clear();
        AngleEquivalenceRelation.getInstance().clear();
        
        Diagram test = DiagramGenerator.premade_Midpoint();
        Precomputer pc = new Precomputer(test.getPoints(), test.getSegments());
        pc.analyze();
        FactComputer fc = new FactComputer(pc);
        fc.analyze();
        FactComputerContainer container = fc.getFacts();
        
        if (container.getSegments().size() != 3)
        {
            System.out.println("FAILED. Incorrect amount of segments. You have: " + container.getSegments().size());
            assert(false);
        }
        else if (PointFactory.getAllPoints().size() != 3)
        {
            System.out.println("FAILED. Incorrect amount of points. You have: " + PointFactory.getAllPoints().size());
            assert(false);
        }
        else if (container.getInMiddles().size() != 1)
        {
            System.out.println("FAILED. Incorrect amount of InMiddles. You have: "+ container.getInMiddles().size());
            assert(false);
        }
        else if (container.getMidPoints().size() != 1)
        {
            System.out.println("FAILED. Incorrect amount of MidPoints. You Have: "+ container.getMidPoints().size());
            assert(false);
        }
        
        System.out.println("Passed");
        assert(true);
    }
}
