package backend.ast.figure;

import backend.ast.figure.components.polygon.Polygon;

//
// The intent of this class is to house all polygon-based representation of shapes.
// That is, given a legitimate shape, return a polygon approximation to the shape.
//
public interface Polygonalizable
{
    public static final int NUM_SEGS_TO_APPROX_ARC = 72;
    
    Polygon getPolygon();
}
