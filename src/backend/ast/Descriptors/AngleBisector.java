package backend.ast.Descriptors;

import backend.ast.figure.components.Angle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.Pair;

public class AngleBisector extends Bisector
{
	private Angle angle;
	private Segment bisector;
	
	public Angle getAngle()
	{
		return angle;
	}
	public Segment getBisector()
	{
		return bisector;
	}
	
	public AngleBisector(Angle a, Segment b)
	{
		super();
		angle = a;
		bisector =b;
	}
	
	public Pair<Angle, Angle> getBisectedAngles()
	{
		Point vertex = angle.GetVertex();
		Point interiorPt = angle.IsOnInterior(bisector.getPoint1()) ? bisector.getPoint1() : bisector.getPoint2();
		Point exteriorPt1 = angle.getRay1().OtherPoint(vertex);
		Point exteriorPt2 = angle.getRay2().OtherPoint(vertex);
		
		return new Pair<Angle, Angle>(new Angle(interiorPt, vertex, exteriorPt1) , new Angle(interiorPt, vertex, exteriorPt2));
	}
	
	@Override
	public int GetHashCode()
	{
		return super.GetHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof AngleBisector)
		{
			AngleBisector ab = (AngleBisector)obj;
			return angle.equals(ab.angle) && bisector.equals(ab.bisector) && super.equals(obj);
		}
		
		return false;
	}
	
	@Override
	public boolean StructurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof AngleBisector)
		{
			AngleBisector ab = (AngleBisector)obj;
			return angle.StructurallyEquals(ab.angle) && bisector.StructurallyEquals(ab.bisector);
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return "AngleBisector(" + angle.toString() + ", " + bisector.toString() + ")";
	}
	
	@Override
	 public String toPrettyString()
     {
         return angle.toPrettyString() + " is bisected by " + bisector.toPrettyString() + ".";
     }
	
}
