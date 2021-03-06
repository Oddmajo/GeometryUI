package backend.ast.Descriptors;

public abstract class Bisector extends Descriptor
{
	public Bisector()
	{
		super();
	}
	
	@Override
	public int getHashCode()
	{
		//change this is the object is no longer immutable
		return super.getHashCode();
	}
	
	//this should be overriden
	@Override
	public boolean structurallyEquals(Object obj)
	{
		if(obj !=null && obj instanceof Bisector)
		{
			Bisector b = (Bisector)obj;
			
			//this makes it seem like there is no point to having the cast
			return super.structurallyEquals(obj);
		}
		
		//if the null check and the instance of is not meet then it probablly should return false
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj !=null && obj instanceof Bisector)
		{
			Bisector b = (Bisector)obj;
			
			//this makes it seem like there is no point to having the cast
			return super.equals(obj);
		}
		
		//if the null check and the instance of is not meet then it probablly should return false
		return false;
	}
}
