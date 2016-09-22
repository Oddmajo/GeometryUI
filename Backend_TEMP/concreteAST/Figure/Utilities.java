package PolyID;

import java.util.ArrayList;

public class Utilities<T>
{
    public static final double EPSILON = 0.000001;
    public static boolean CompareValues(double a, double b)
    {
        return Math.abs(a - b) < EPSILON;
    }
    public static boolean GreaterThan(double x, int i)
    {
        return x > i;
    }
    public static boolean LessThan(double x, int i)
    {
        return x < i;
    }
    public static void AddUniqueStructurally(ArrayList<Figure> figures, Figure f)
    {
        for (Figure figure : figures)
        {
            if (figure.StructurallyEquals(f)) return;
        }
        figures.add(f);
        
    }

 // Makes a list containing a single element
    public static <T> ArrayList<T> MakeList(T obj)
    {
        ArrayList<T> l = new ArrayList<T>();

        l.add(obj);

        return l;
    }
}
