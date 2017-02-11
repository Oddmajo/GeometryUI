package backend.ast.Descriptors.Relations;

import backend.ast.figure.components.triangles.Triangle;

public class GeometricSimilarTriangles extends SimilarTriangles
{

    public GeometricSimilarTriangles(Triangle t1, Triangle t2)
    {
        super(t1, t2);
        // TODO Auto-generated constructor stub
    }

    public boolean IsAlgebraic() { return false; }
    public boolean IsGeometric() { return true; }

    @Override
    public boolean equals(Object obj)
    {
        GeometricSimilarTriangles gst = (GeometricSimilarTriangles)obj;
        if (gst == null) return false;
        return super.equals(obj);
    }

    @Override
    public int getHashCode()
    {
        //Change this if the objest is no longer immutable!!!
        return super.getHashCode();
    }

    @Override
    public String toString() { return "GeometricSimilar(" + getFirstTriangle().toString() + ", " + getSecondTriangle().toString() + ") " + justification; }

    @Override
    public String toPrettyString() { return getFirstTriangle().toPrettyString() + " is similar to " + getSecondTriangle().toPrettyString() + "."; }
}

