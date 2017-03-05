package backend.ast.Descriptors.Relations;

import backend.ast.figure.components.triangles.Triangle;

public class AlgebraicSimilarTriangles extends SimilarTriangles
{
    public AlgebraicSimilarTriangles(Triangle t1, Triangle t2) {  super(t1, t2); }

    @Override
    public boolean isAlgebraic() { return true; }
    @Override
    public boolean isGeometric() { return false; }

    @Override
    public boolean equals(Object obj)
    {
        AlgebraicSimilarTriangles gst = (AlgebraicSimilarTriangles)obj;
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
    public String toString() { return "AlgebraicSimilar(" + getFirstTriangle().toString() + ", " + getSecondTriangle().toString() + ") " + justification; }
}
