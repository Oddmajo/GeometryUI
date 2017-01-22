package backend.ast.figure;

import backend.ast.figure.components.Point;

public interface Containable
{
    boolean pointLiesOn(Point pt);
    boolean pointLiesInside(Point pt);
    boolean PointLiesInOrOn(Point pt);
    boolean contains(Polygonalizable p);
}
