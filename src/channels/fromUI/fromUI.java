package channels.fromUI;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import mainNonUI.Main;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.IntersectionObject;
import rene.zirkel.objects.LineObject;
import rene.zirkel.objects.MidpointObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.RayObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.objects.TwoPointLineObject;
import rene.zirkel.objects.VectorObject;

public class FromUI
{
    public static boolean sendToBackend(ZirkelCanvas zc/*, ZirkelFrame zf*/)
    {
        Construction uiRepresentation = null;
        
        /* A boundary needs to be declared for handling of lines and rays
         * A boundary will have 4 lines. It might need 4 points in the future
         */
        Diagram boundary = createBoundary(zc);
        
        Diagram backendRepresentation = translateToDiagram(uiRepresentation, boundary);
        
        Main.receiveDiagram(backendRepresentation);
        return false;
    }
    
    private static Diagram translateToDiagram(Construction c, Diagram boundary)
    {
        Diagram D = new Diagram();
        
        //The following creates a frame around the diagram
        
        
        
        for(int i = 0; i < c.V.size(); i++)
        {
            ConstructionObject co = (ConstructionObject) c.V.get(i);
            
            if(co instanceof PointObject)
            {
                if(co instanceof IntersectionObject)
                {
                    //Do something
                    //This is extended by
                    //  AxisFunctionIntersectionObject
                    //  CircleIntersectionObject
                    //  LineCircleIntersectionObject
                    //  LineIntersectionObject
                    //  LineQuadraticIntersectionObject
                    //  PointonObjectIntersectionObject
                    //  QuadraticQuadraticIntersectionObject
                }
                else if(co instanceof MidpointObject)
                {
                    //Do something
                }
                else
                {
                    PointObject point = (PointObject) co;
                    D.addPoint(FromUITranslate.translatePoint(point));
                }
            }
            
            if(co instanceof PrimitiveLineObject)
            {
                if(co instanceof TwoPointLineObject)
                {
                    if(co instanceof SegmentObject)
                    {
                        
                        if(co instanceof VectorObject)
                        {
                            System.out.println("Vector Detected...Converting to Segment.");
                            SegmentObject segment = (SegmentObject) co;
                            D.addSegment(FromUITranslate.translateSegment(segment));
                        }
                        else
                        {
                            SegmentObject segment = (SegmentObject) co;
                            D.addSegment(FromUITranslate.translateSegment(segment));
                        }
                    }
                    else if(co instanceof RayObject)
                    {
                        //Do something
                    }
                    else if(co instanceof LineObject)
                    {
                        
                        LineObject line = (LineObject) co;
                        SegmentObject segment = new SegmentObject(null, ((PrimitiveLineObject) co).getP1(), ((TwoPointLineObject) co).getP2());
                        D.addSegment(FromUITranslate.translateSegment(segment));
                        D.addSegment(FromUITranslate.translateLine(line, boundary));
                        
                    }
                    else
                    {
                        //Do something
                    }
                }
                else
                {
                    //Do something
                    //This is extended by
                    //  AxisObject
                    //  FixedAngleObject
                    //  ParallelObject
                    //  PlumbObject
                }
            }
        }
        
        return D;
    }
    
    private static Diagram createBoundary(ZirkelCanvas ZC)
    {
        Diagram D = new Diagram();
        
        //Create the four corners
        Point bLeft = new Point("", ZC.minX(),ZC.minY());
        Point tLeft = new Point("", ZC.minX(), ZC.maxY());
        Point bRight = new Point("", ZC.maxX(), ZC.minY());
        Point tRight = new Point("", ZC.maxX(), ZC.maxY());
        
        //Add the four corners
        D.addPoint(bLeft);
        D.addPoint(bRight);
        D.addPoint(tRight);
        D.addPoint(tLeft);
        
        //Create and add the four sides
        D.addSegment(new Segment(bLeft, tLeft)); //Left
        D.addSegment(new Segment(bLeft, bRight)); //Bottom
        D.addSegment(new Segment(bRight, tRight)); //Right
        D.addSegment(new Segment(tLeft, tRight)); //Top
        
        return D;
    }
}
