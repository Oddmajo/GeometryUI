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
	public int getHashCode()
	{
		return super.getHashCode();
	}
	@Override
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof Altitude)
		{
			Altitude alt = (Altitude)obj;
			return triangle.StructurallyEquals(alt.triangle) && segment.structurallyEquals(alt.segment);
		}
		
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof Altitude)
		{
			Altitude alt = (Altitude)obj;
			return triangle.equals(alt.triangle) && segment.equals(alt.segment) && super.equals(obj);
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Altitude(" + segment.toString() + ", " + triangle.toString() + ")";
	}
}
