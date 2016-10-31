package backend.ast.Descriptors;

import backend.ast.figure.components.Segment;
import backend.ast.figure.components.Triangle;

public class Altitude extends Descriptor
{
	private Triangle triangle;
	private Segment segment;
	
	public Triangle getTriangle()
	{
		return triangle;
	}
	public Segment getSegment()
	{
		return segment;
	}
	
	public Altitude(Triangle tri, Segment alt)
	{
		super();
		triangle = tri;
		segment = alt;
	}
	
	@Override
	public int GetHashCode()
	{
		return super.GetHashCode();
	}
	@Override
	public boolean StructurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof Altitude)
		{
			Altitude alt = (Altitude)obj;
			return triangle.StructurallyEquals(alt.triangle) && segment.StructurallyEquals(alt.segment);
		}
		
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof Altitude)
		{
			Altitude alt = (Altitude)obj;
			return triangle.Equals(alt.triangle) && segment.Equals(alt.segment) && super.Equals(obj);
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Altitude(" + segment.toString() + ", " + triangle.toString() + ")";
	}
}
