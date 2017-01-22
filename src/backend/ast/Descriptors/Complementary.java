package backend.ast.Descriptors;

import backend.ast.figure.components.angles.Angle;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class Complementary extends AnglePairRelation
{
	public Complementary(Angle ang1, Angle ang2)
	{
		super(ang1,ang2);
		if(!Utilities.CompareValues(ang1.getMeasure() +  ang2.getMeasure(), 90))
		{
			ExceptionHandler.throwException(new ArgumentException("Complementary Angles must sum to 90: " + ang1 + " " + ang2));
		}
	}
	
	@Override
	public int getHashCode()
	{
		//change this if the object is no longer immutable
		return super.getHashCode();
	}
	
	@Override
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof Complementary)
		{
			Complementary supp = (Complementary)obj;
			return super.structurallyEquals(supp);
		}
		
		//if this fails the null check or the instaceof then it should probably return false
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof Complementary)
		{
			Complementary supp = (Complementary)obj;
			return super.equals(supp);
		}
		
		//if this fails the null check or the instaceof then it should probably return false
		return false;
	}
	
	@Override
	public String toString()
	{
        return "Complementary(" + angle1 + ", " + angle2 + "): " + justification;
	}
}
