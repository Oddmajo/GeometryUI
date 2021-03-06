package backend.ast.Descriptors;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
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
		Point vertex = angle.getVertex();
		Point interiorPt = angle.IsOnInterior(bisector.getPoint1()) ? bisector.getPoint1() : bisector.getPoint2();
		Point exteriorPt1 = angle.getRay1().getNonOrigin();
		Point exteriorPt2 = angle.getRay2().getNonOrigin();
		
		return new Pair<Angle, Angle>(new Angle(interiorPt, vertex, exteriorPt1) , new Angle(interiorPt, vertex, exteriorPt2));
	}
	
	@Override
	public int getHashCode()
	{
		return super.getHashCode();
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
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof AngleBisector)
		{
			AngleBisector ab = (AngleBisector)obj;
			return angle.structurallyEquals(ab.angle) && bisector.structurallyEquals(ab.bisector);
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
