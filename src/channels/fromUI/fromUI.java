package channels.fromUI;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
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
        Construction uiRepresentation = zc.getConstruction();
        Diagram backendRepresentation = translateToDiagram(uiRepresentation);
        
        System.out.println(backendRepresentation.toString());
        return false;
    }
    
    private static Diagram translateToDiagram(Construction C)
    {
        Diagram D = new Diagram();
        
        for(int i = 0; i < C.V.size(); i++)
        {
            ConstructionObject co = (ConstructionObject) C.V.get(i);
            
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
                    D.addPoint(fromUITranslate.translatePoint(point));
                }
            }
            
            if(co instanceof PrimitiveLineObject)
            {
                if(co instanceof TwoPointLineObject)
                {
                    if(co instanceof SegmentObject)
                    {
                        //Do something
                        //This is extended by
                        //  VectorObject
                        if(co instanceof VectorObject)
                        {
                            //Do something
                        }
                        else
                        {
                            SegmentObject segment = (SegmentObject) co;
                            D.addSegment(fromUITranslate.translateSegment(segment));
                        }
                    }
                    else if(co instanceof RayObject)
                    {
                        //Do something
                    }
                    else if(co instanceof LineObject)
                    {
                        //Do something
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
}
