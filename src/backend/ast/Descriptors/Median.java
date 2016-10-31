package backend.ast.Descriptors;

import backend.ast.figure.components.Segment;
import backend.ast.figure.components.Triangle;

public class Median extends Descriptor
{
	private Segment medianSegment;
	private Triangle theTriangle;
	
	public Segment getMedianSegment()
	{
		return medianSegment;
	}
	public Triangle getTheTriangle()
	{
		return theTriangle;
	}
	
	public Median(Segment segment, Triangle thatTriangle)
	{
		medianSegment = segment;
		theTriangle = thatTriangle;
	}
	
	@Override
	public int GetHashCode()
	{
		return super.GetHashCode();
	}
	@Override 
	public boolean StructurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof Median)
		{
			Median med = (Median)obj;
			return theTriangle.StructurallyEquals(med.theTriangle) && medianSegment.StructurallyEquals(med.medianSegment);
		}
		
		//if the check fails then we should probably return false
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof Median)
		{
			Median med = (Median)obj;
			return theTriangle.Equals(med.theTriangle) && medianSegment.Equals(med.medianSegment) && super.Equals(obj);
		}
		
		//if the check fails then we should probably return false
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Median(" + medianSegment.toString() + ", " + theTriangle.toString() + ") " + justification;
	}
}
