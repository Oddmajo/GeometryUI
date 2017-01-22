package backend.ast.Descriptors;

import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;

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
	public int getHashCode()
	{
		return super.getHashCode();
	}
	@Override 
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof Median)
		{
			Median med = (Median)obj;
			return theTriangle.structurallyEquals(med.theTriangle) && medianSegment.structurallyEquals(med.medianSegment);
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
			return theTriangle.equals(med.theTriangle) && medianSegment.equals(med.medianSegment) && super.equals(obj);
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
