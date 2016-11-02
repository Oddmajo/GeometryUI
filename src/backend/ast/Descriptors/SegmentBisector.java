package backend.ast.Descriptors;

import backend.ast.figure.components.Segment;

public class SegmentBisector extends Bisector
{
	private Intersection bisected;
	private Segment bisector;
	
	public Intersection getBisected()
	{
		return bisected;
	}
	public Segment getBisector()
	{
		return bisector;
	}
	
	public SegmentBisector(Intersection b , Segment bisec)
	{
		super();
		bisected = b;
		bisector = bisec;
	}
	
	//segmentbisector has a specific order associated with the intersection segments
	@Override
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof SegmentBisector)
		{
			SegmentBisector b = (SegmentBisector)obj;
			
			//the bisector segment
			if(!bisector.structurallyEquals(b.bisector))
			{
				return false;
			}
			
			//the intersection points
			if(!bisected.intersect.structurallyEquals(b.bisected.intersect))
			{
				return false;
			}
			
			//the bisected segments
			return bisected.OtherSegment(bisector).structurallyEquals(b.bisected.OtherSegment(b.bisector));
		}
		
		//if the null check fails or it isn't an instance of then it probably should return false
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof SegmentBisector)
		{
			SegmentBisector b = (SegmentBisector)obj;
			return bisector.equals(b.bisector) && bisected.equals(b.bisected) && super.equals(obj);
		}
		//if the null check fails or it isn't an instance of then it probably should return false
		return false;
	}
	@Override
	public String toString()
    {
        return "SegmentBisector(" + bisector.toString() + " Bisects(" + bisected.OtherSegment(bisector) + ") at " + bisected.intersect + ")";
    }

	@Override
    public String toPrettyString()
    {
        return bisector.toPrettyString() + " bisects " + bisected.OtherSegment(bisector).toPrettyString() + " at " + bisected.intersect.toPrettyString() + ".";
    }
	
}
