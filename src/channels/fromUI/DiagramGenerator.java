package channels.fromUI;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class DiagramGenerator
{
    /** 
     * This method will purge the diagram of any components, and then create a default diagram for testing purposes.
     *      
     *      o ____ o ____ o
     */
    public static Diagram premade_Midpoint()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", 0.0, 0.0);
        Point B = new Point("B", 2.0, 0.0);
        Point C = new Point("C", 1.0, 0.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        
        Segment one = new Segment(A,B);
        
        d.addSegment(one);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      ________/__________
     *             /          
     *      ______/____________
     *           /
     */
    public static Diagram premade_ParallelTransversal()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", -2.0, 1.0);
        Point B = new Point("B", 2.0, 1.0);
        
        Point C = new Point("C", -2.0, -1.0);
        Point D = new Point("D", 2.0, -1.0);
        
        Point E = new Point("E", 1.0, 2.0);
        Point F = new Point("F", -1.0, -2.0);
        
        d.addPoint(A);
        d.addPoint(B);
        
        d.addPoint(C);
        d.addPoint(D);
        
        d.addPoint(E);
        d.addPoint(F);
        
        Segment top = new Segment(A, B);
        Segment bottom = new Segment(C, D);
        Segment trans = new Segment(E, F);
        
        d.addSegment(top);
        d.addSegment(bottom);
        d.addSegment(trans);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      o______________o
     *      \              /\
     *       \            /  \
     *        \          /    \
     *         \        /      \
     *          \      /        \
     *           \    /          \
     *            \  /            \
     *            o\/______________\o
     */
    public static Diagram premade_ParallelogramDiagonal()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", -1.0, 1.0);
        Point B = new Point("B", 0.0, -1.0);
        Point C = new Point("C", 1.0, 1.0);
        Point D = new Point("D", 2.0, -1.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        d.addPoint(D);
        
        Segment left = new Segment(A, B);
        Segment bottom = new Segment(B, D);
        Segment right = new Segment(D, C);
        Segment top = new Segment(C, A);
        Segment diagonal = new Segment(C, B);
        
        d.addSegment(left);
        d.addSegment(bottom);
        d.addSegment(right);
        d.addSegment(top);
        d.addSegment(diagonal);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, and then create a default diagram for testing purposes.
     * 
     *           o
     *          /\
     *         /  \
     *       o/____\o
     *       /\    /\
     *      /   \/   \
     *     /   /  \   \
     *    /  /      \  \
     *   / /          \ \
     * o//______________\\o
     */
    public static Diagram premade_Triangles()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", 0.0, 0.0);
        Point B = new Point("B", 4.0, 0.0);
        Point C = new Point("C", 2.0, 4.0);
        Point D = new Point("D", 1.0, 2.0);
        Point E = new Point("E", 3.0, 2.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        d.addPoint(D);
        d.addPoint(E);
        
        Segment one = new Segment(A,B);
        Segment two = new Segment(A,C);
        Segment three = new Segment(B,C);
        Segment four = new Segment(D,E);
        Segment five = new Segment(A,E);
        Segment six = new Segment(D,B);
        
        d.addSegment(one);
        d.addSegment(two);
        d.addSegment(three);
        d.addSegment(four);
        d.addSegment(five);
        d.addSegment(six);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      \
     *       \
     *        \
     *   ______\______
     */
    public static Diagram premade_SegmentBisector()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 4.0, 0.0);
        
        Point c = new Point("C", 1.0, 2.0);
        Point m = new Point("M", 2.0, 0.0);
        
        d.addPoint(a);
        d.addPoint(b);
        
        d.addPoint(c);
        d.addPoint(m);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);

        d.addSegment(ab);
        d.addSegment(cm);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      \
     *       \
     *        \
     *   ______\______
     *          \
     *           \ 
     *            \
     */
    public static Diagram premade_ThroughSegmentBisector()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 4.0, 0.0);
        Point c = new Point("C", 1.0, 2.0);
        Point d = new Point("D", 3.0, -2.0);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        diagram.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment cd = new Segment(c, d);

        diagram.addSegment(ab);
        diagram.addSegment(cd);
        
        return diagram;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *         |
     *         |
     *         |
     *   ______|______
     */
    public static Diagram premade_PerpendicularBisector()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 4.0, 0.0);
        Point c = new Point("C", 2.0, 1.0);
        Point m = new Point("M", 2.0, 0.0);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        d.addPoint(m);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);

        d.addSegment(ab);
        d.addSegment(cm);
        
        return d;
    }
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *          C
     *          | 
     *          |
     *          |
     *   A______|M
     */
    public static Diagram premade_Perpendicular()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point c = new Point("C", 1.0, 1.0);
        Point m = new Point("M", 1.0, 0.0);
        
        d.addPoint(a);
        d.addPoint(c);
        d.addPoint(m);
        
        Segment am = new Segment(a, m);
        Segment cm = new Segment(c, m);

        d.addSegment(am);
        d.addSegment(cm);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *         |
     *         |
     *         |
     *   ______|______
     *         |
     *         |
     *         |
     *         |
     */
    public static Diagram premade_ThroughPerpendicularBisector()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 4.0, 0.0);
        Point c = new Point("C", 2.0, 1.0);
        Point m = new Point("M", 2.0, -1.0);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        d.addPoint(m);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);

        d.addSegment(ab);
        d.addSegment(cm);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      _________|__________
     *               |        
     *      _________|__________
     *               |
     */
    public static Diagram premade_PerpendicularTransversal()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", -2.0, 1.0);
        Point B = new Point("B", 2.0, 1.0);
        
        Point C = new Point("C", -2.0, -1.0);
        Point D = new Point("D", 2.0, -1.0);
        
        Point E = new Point("E", 0.0, 2.0);
        Point F = new Point("F", 0.0, -2.0);
        
        d.addPoint(A);
        d.addPoint(B);
        
        d.addPoint(C);
        d.addPoint(D);
        
        d.addPoint(E);
        d.addPoint(F);
        
        Segment top = new Segment(A, B);
        Segment bottom = new Segment(C, D);
        Segment trans = new Segment(E, F);
        
        d.addSegment(top);
        d.addSegment(bottom);
        d.addSegment(trans);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *    _________________
     *    
     *    _________________
     *    
     *    _________________
     */
    public static Diagram premade_TransitiveParallels()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 2.0, 0.0);
        
        Point c = new Point("C", 0.0, 2.0);
        Point d = new Point("D", 2.0, 2.0);
        
        Point e = new Point("E", 0.0, 4.0);
        Point f = new Point("F", 2.0, 4.0);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        
        diagram.addPoint(c);
        diagram.addPoint(d);
        
        diagram.addPoint(e);
        diagram.addPoint(f);
        
        Segment ab = new Segment(a, b);
        Segment cd = new Segment(c, d);
        Segment ef = new Segment(e, f);

        diagram.addSegment(ab);
        diagram.addSegment(cd);
        diagram.addSegment(ef);
        
        return diagram;
    }
    
    //-------------------------------------- Angles --------------------------------------
  
    
    /**
     *       D    C
     *       /   /
     *      /  /
     *     / /
     *    //  
     *  B/_______A
     *  mABC = 30*
     *  mCBD = 30*
     */
    public static Diagram premade_AngleBisector()
    {
        Diagram diagram = new Diagram();
        
        
        Point a = new Point("A", 1.0, 0.0);
        Point b = new Point("B", 0.0, 0.0);
        Point c = new Point("C", Math.sqrt(3.0)/2.0, .5);
        Point d = new Point("D", .5, Math.sqrt(3.0)/2.0); 
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        diagram.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment cb = new Segment(c, b);
        Segment db = new Segment(d, b);

        diagram.addSegment(ab);
        diagram.addSegment(cb);
        diagram.addSegment(db);
        
        return diagram;
    }
    
    /**
     *     /              /
     *    /              /
     *   /              /
     *  /____          /_____
     *  Angle:          Angle:
     *  Pi/4            Pi/4
     */
    public static Diagram premade_TwoEqualAngles()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 1.0, 1.0);
        Point b = new Point("B", 0.0, 0.0);
        Point c = new Point("C", 1.0, 0.0);
        
        Point d = new Point("D", 3.0, 1.0); 
        Point e = new Point("E", 2.0, 0.0);
        Point f = new Point("F", 3.0, 0.0);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        
        diagram.addPoint(d);
        diagram.addPoint(e);
        diagram.addPoint(f);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        
        Segment de = new Segment(d, e);
        Segment ef = new Segment(e, f);

        diagram.addSegment(ab);
        diagram.addSegment(bc);
        
        diagram.addSegment(de);
        diagram.addSegment(ef);
        
        return diagram;
    }
    
    /**
     *   C        D
     *   |       /
     *   |     /
     *   |   /
     *   | /  
     *  B/_______A
     */
    public static Diagram premade_Complementary()
    {
        Diagram diagram = new Diagram();
        
        // RightAngle(A, B, C), Angle(A, B, D) + Angle(D, B, C) = 90 -> Complementary(Angle(A, B, D), Angle(D, B, C))
        Point a = new Point("A", 1.0, 0.0);
        Point b = new Point("B", 0.0, 0.0);
        Point c = new Point("C", 0.0, 1.0);
        Point d = new Point("D", .5, Math.sqrt(3.0)/2.0); 
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        diagram.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment cb = new Segment(c, b);
        Segment db = new Segment(d, b);
   
        diagram.addSegment(ab);
        diagram.addSegment(cb);
        diagram.addSegment(db);
        
        return diagram;
    }
    
    /**
     *  |              |
     *  |              |
     *  |              | 
     *  |____          |_____
     */
    public static Diagram premade_TwoRightAngles()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 0.0, 1.0);
        Point b = new Point("B", 0.0, 0.0);
        Point c = new Point("C", 1.0, 0.0);
        
        Point d = new Point("D", 2.0, 1.0); 
        Point e = new Point("E", 2.0, 0.0);
        Point f = new Point("F", 3.0, 0.0);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        
        diagram.addPoint(d);
        diagram.addPoint(e);
        diagram.addPoint(f);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        
        Segment de = new Segment(d, e);
        Segment ef = new Segment(e, f);

        diagram.addSegment(ab);
        diagram.addSegment(bc);
        
        diagram.addSegment(de);
        diagram.addSegment(ef);
        
        return diagram;
    }
    
    /** 
     * This method will purge the diagram of any components, and then create a default diagram for testing purposes.
     *      
     *      o ____ o ____ o ____ o
     */
    public static Diagram premade_StraightAngles()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", 0.0, 0.0);
        Point B = new Point("B", 1.0, 0.0);
        Point C = new Point("C", 2.0, 0.0);
        Point D = new Point("D", 3.0, 0.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        d.addPoint(D);
        
        Segment one = new Segment(A,D);
        Segment two = new Segment(B,C);
        
        d.addSegment(one);
        d.addSegment(two);
        
        return d;
    }
    
    
    /**
     *  C     
     *  |    G
     *  |   /
     *  |  /
     *  | /
     * D|/___F___________E
     *  |   /
     *  |  /
     *  | /
     *  |/_______________
     *  A                B
     */
    public static Diagram premade_CongruentRealtions1()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 4.0, 0.0);
        Point c = new Point("C", 0.0, 4.0);

        Point d = new Point("D", 0.0, 2.0); 
        Point e = new Point("E", 4.0, 2.0);
        
        Point f = new Point("F", 1.0, 2.0);
        
        Point g = new Point("G", 1.0, 4.0);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        
        diagram.addPoint(d);
        diagram.addPoint(e);
        
        diagram.addPoint(f);
        
        diagram.addPoint(g);
        
        
        Segment ab = new Segment(a, b);
        Segment ac = new Segment(a, c);
        Segment de = new Segment(d, e);
        Segment af = new Segment(a, f);
        Segment dg = new Segment(d, g);

        diagram.addSegment(ab);
        diagram.addSegment(ac);
        diagram.addSegment(de);
        diagram.addSegment(af);
        diagram.addSegment(dg);
        
        
        return diagram;
    }
    
    /**
     *           F       L
     *          /        /
     * ________/        /_______
     * D       E        J       K
     * 
     *          C         I
     *          /        /
     * ________/        /_______
     * A       B        G       H
     */
    public static Diagram premade_CongruentRealtions2()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 2.0, 0.0);
        Point c = new Point("C", 3.0, 1.0);

        Point d = new Point("D", 0.0, 4.0); 
        Point e = new Point("E", 2.0, 4.0);
        Point f = new Point("F", 3.0, 5.0);
        
        Point g = new Point("G", 4.0, 0.0);
        Point h = new Point("H", 6.0, 0.0);
        Point i = new Point("I", 5.0, 1.0);

        Point j = new Point("J", 4.0, 4.0); 
        Point k = new Point("K", 6.0, 4.0);
        Point l = new Point("L", 5.0, 5.0);

        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        
        diagram.addPoint(d);
        diagram.addPoint(e);
        diagram.addPoint(f);
        
        diagram.addPoint(g);
        diagram.addPoint(h);
        diagram.addPoint(i);
        
        diagram.addPoint(j);
        diagram.addPoint(k);
        diagram.addPoint(l);
        
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        
        Segment de = new Segment(d, e);
        Segment ef = new Segment(e, f);
        
        Segment gh = new Segment(g, h);
        Segment gi = new Segment(g, i);
        
        Segment jk = new Segment(j, k);
        Segment jl = new Segment(j, l);

        diagram.addSegment(ab);
        diagram.addSegment(bc);
        
        diagram.addSegment(de);
        diagram.addSegment(ef);
        
        diagram.addSegment(gh);
        diagram.addSegment(gi);
        
        diagram.addSegment(jk);
        diagram.addSegment(jl);
        
        return diagram;
    }
    
    
    
    /**
     * D         E
     * |         |
     * |         |
     * |_________|
     * A         B
     */
    public static Diagram premade_CongruentSupplementary()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", 0.0, 0.0);
        Point B = new Point("B", 2.0, 0.0);
        Point D = new Point("D", 0.0, 1.0);
        Point E = new Point("E", 2.0, 1.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(D);
        d.addPoint(E);
        
        Segment ab = new Segment(A,B);
        Segment ad = new Segment(A,D);
        Segment be = new Segment(B,E);
        
        d.addSegment(ab);
        d.addSegment(ad);
        d.addSegment(be);
        
        return d;
    }
    
    //-------------------------------------- Triangles --------------------------------------
    
    /**
     *      /|             
     *     / |             
     *    /  |            
     *   /   |           
     *  /____|          
     */
    public static Diagram premade_RightTriangle()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 1.0, 1.0);
        Point b = new Point("B", 0.0, 0.0);
        Point c = new Point("C", 1.0, 0.0);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);

        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment ac = new Segment(a, c);
        
        diagram.addSegment(ab);
        diagram.addSegment(bc);
        diagram.addSegment(ac);
        
        return diagram;
    }
    /**
     *      /|              /|
     *     / |             / |
     *    /  |            /  | 
     *   /   |           /   |
     *  /____|          /____| 
     */
    public static Diagram premade_CongruentRightTriangles()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 1.0, 1.0);
        Point b = new Point("B", 0.0, 0.0);
        Point c = new Point("C", 1.0, 0.0);
        
        Point d = new Point("D", 3.0, 1.0); 
        Point e = new Point("E", 2.0, 0.0);
        Point f = new Point("F", 3.0, 0.0);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        
        diagram.addPoint(d);
        diagram.addPoint(e);
        diagram.addPoint(f);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment ac = new Segment(a, c);
        
        Segment de = new Segment(d, e);
        Segment ef = new Segment(e, f);
        Segment df = new Segment(d, f);

        diagram.addSegment(ab);
        diagram.addSegment(bc);
        diagram.addSegment(ac);
        
        diagram.addSegment(de);
        diagram.addSegment(ef);
        diagram.addSegment(df);
        
        return diagram;
    }
    
    /** 
     * 
     * 
     *                        /|
     *                       / |
     *      /|              /  |
     *     / |             /   |
     *    /  |            /    |
     *   /   |           /     |
     *  /____|          /______| 
     */
    public static Diagram premade_SimilarRightTriangles()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 1.0, 1.0);
        Point b = new Point("B", 0.0, 0.0);
        Point c = new Point("C", 1.0, 0.0);
        
        Point d = new Point("D", 4.0, 2.0); 
        Point e = new Point("E", 2.0, 0.0);
        Point f = new Point("F", 4.0, 0.0);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        
        diagram.addPoint(d);
        diagram.addPoint(e);
        diagram.addPoint(f);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment ac = new Segment(a, c);
        
        Segment de = new Segment(d, e);
        Segment ef = new Segment(e, f);
        Segment df = new Segment(d, f);

        diagram.addSegment(ab);
        diagram.addSegment(bc);
        diagram.addSegment(ac);
        
        diagram.addSegment(de);
        diagram.addSegment(ef);
        diagram.addSegment(df);
        
        return diagram;
    }
    
    public static Diagram premade_EquilateralTriangle()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 3.0, Math.sqrt(27.0));
        Point c = new Point("C", 6.0, 0.0);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        
        Segment ab = new Segment(a, b);
        Segment ac = new Segment(a, c);
        Segment cb = new Segment(c, b);

        d.addSegment(ab);
        d.addSegment(ac);
        d.addSegment(cb);
        
        return d;
    }
    
    public static Diagram premade_IsoscelesTriangle()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 1.0, 1.0);
        Point c = new Point("C", 2.0, 0.0);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        
        Segment ab = new Segment(a, b);
        Segment ac = new Segment(a, c);
        Segment cb = new Segment(c, b);

        d.addSegment(ab);
        d.addSegment(ac);
        d.addSegment(cb);
        
        return d;
    }
    
    
    /**
     *     B ---------V---------A
     *      \          \       /
     *           \      \     /
     *               \   \  /
     *                  \ \/
     *                     C
     *
     */
    public static Diagram premade_TriangleWithMedian()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 2.0);
        Point b = new Point("B", 6.0, 2.0);
        Point c = new Point("C", 4.0, 0.0);
        Point v = new Point("V", 3.0, 2.0);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        d.addPoint(v);
        
        Segment ab = new Segment(a, b);
        Segment ac = new Segment(a, c);
        Segment cb = new Segment(c, b);
        Segment vc = new Segment(v, c);

        d.addSegment(ab);
        d.addSegment(ac);
        d.addSegment(cb);
        d.addSegment(vc);
        
        return d;
    }
    
    /**  
     *     /|\
     *    / | \       
     *   /  |  \
     *  /___|___\
     */
    public static Diagram premade_IsoscelesTriangleWithAngleBisectorAsPerpendicularBisector()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 1.0, 1.0);
        Point c = new Point("C", 2.0, 0.0);
        Point m = new Point("M", 1.0, 0.0);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        d.addPoint(m);
        
        Segment ab = new Segment(a, b);
        Segment ac = new Segment(a, c);
        Segment cb = new Segment(c, b);
        Segment bm = new Segment(b, m);

        d.addSegment(ab);
        d.addSegment(ac);
        d.addSegment(cb);
        d.addSegment(bm);
        
        return d;
    }
    
    /**
     *      /|             
     *     / |             
     *    /  |            
     *   / \ |           
     *  /___\|          
     */
    public static Diagram premade_RightTriangleWithAltitude()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 1.0, 1.0);
        Point b = new Point("B", 0.0, 0.0);
        Point c = new Point("C", 1.0, 0.0);
        Point m = new Point("M", 0.5, 0.5);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        diagram.addPoint(m);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment ac = new Segment(a, c);
        Segment cm = new Segment(c, m);

        diagram.addSegment(ab);
        diagram.addSegment(bc);
        diagram.addSegment(ac);
        diagram.addSegment(cm);
        

        
        return diagram;
    }
    
    /**    
     *      /\
     *     /  \
     *    /____\       
     *   /      \
     *  /________\
     */
    public static Diagram premade_ProportionalTriangles()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 4.0, 4.0);
        Point c = new Point("C", 8.0, 0.0);
        Point x = new Point("X", 2.0, 2.0);
        Point y = new Point("Y", 6.0, 2.0);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        d.addPoint(x);
        d.addPoint(y);
      
        Segment ab = new Segment(a, b);
        Segment ac = new Segment(a, c);
        Segment cb = new Segment(c, b);
        
        Segment xy = new Segment(x, y);
  
        d.addSegment(ab);
        d.addSegment(ac);
        d.addSegment(cb);
        d.addSegment(xy);
        
        return d;
    }
    
    //-------------------------------------- Quadrilaterals --------------------------------------
    
    
    /**
     *    ______
     *   /      \
     *  /________\
     */
    public static Diagram premade_IsoscelesTrapezoid()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 1.0, 2.0);
        Point c = new Point("C", 3.0, 2.0);
        Point d = new Point("D", 4.0, 0.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);
        
        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        return di;
    }
    
    /**
     * 
     *       /\
     *      /  \
     *      \  /
     *       \/
     */
    public static Diagram premade_Kite()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", -1.0, 2.0);
        Point c = new Point("C", 0.0, 5.0);
        Point d = new Point("D", 1.0, 2.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);
        
        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        return di;
    }
    
    /**
     *     ______________
     *    /             /
     *   /             /
     *  /_____________/
     */
    public static Diagram premade_Parallelogram()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 2.0, 3.0);
        Point c = new Point("C", 7.0, 3.0);
        Point d = new Point("D", 5.0, 0.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);
        
        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        return di;
    }
    
    
    /**
     *   _____________
     *  |             |
     *  |             |
     *  |_____________|
     */
    public static Diagram premade_Rectangle()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 0.0, 3.0);
        Point c = new Point("C", 5.0, 3.0);
        Point d = new Point("D", 5.0, 0.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);

        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        return di;
    }
    
    /**
     *     ______
     *    /     /       
     *   /     /        
     *  /_____/
     */
    public static Diagram premade_Rhombus()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 3.0, 4.0);
        Point c = new Point("C", 8.0, 4.0);
        Point d = new Point("D", 5.0, 0.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);

        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        return di;
    }
    
    /**
     *   ______
     *  |      |
     *  |      |
     *  |______|
     */
    public static Diagram premade_Square()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 0.0, 1.0);
        Point c = new Point("C", 1.0, 1.0);
        Point d = new Point("D", 1.0, 0.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);
        
        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        return di;
    }
    
    /**
     *      
     *      o______________o
     *      \\             /\
     *       \   \        /  \
     *        \     \    /    \
     *         \      \ /      \
     *          \      /\       \
     *           \    /     \    \
     *            \  /         \  \
     *            o\/_____________\\o
     */
    public static Diagram premade_ParallelogramBisectors()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", -1.0, 1.0);
        Point B = new Point("B", 0.0, -1.0);
        Point C = new Point("C", 1.0, 1.0);
        Point D = new Point("D", 2.0, -1.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        d.addPoint(D);
        
        Segment left = new Segment(A, B);
        Segment bottom = new Segment(B, D);
        Segment right = new Segment(D, C);
        Segment top = new Segment(C, A);
        Segment diagonal1 = new Segment(C, B);
        Segment diagonal2 = new Segment(A, D);
        
        d.addSegment(left);
        d.addSegment(bottom);
        d.addSegment(right);
        d.addSegment(top);
        d.addSegment(diagonal1);
        d.addSegment(diagonal2);
        
        return d;
    }
    
    /**
     * 
     *       /\
     *      /\/\
     *      \/\/
     *       \/
     */
    public static Diagram premade_KiteDiagonals()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", -1.0, 2.0);
        Point c = new Point("C", 0.0, 5.0);
        Point d = new Point("D", 1.0, 2.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);
        
        Segment db = new Segment(d, b);
        Segment ac = new Segment(a, c);
        
        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        di.addSegment(db);
        di.addSegment(ac);
        
        return di;
    }
    
    /**
     *   _____________
     *  |  \       /  |
     *  |      x      |
     *  |__/_______\__|
     */
    public static Diagram premade_RectangleDiagonals()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 0.0, 3.0);
        Point c = new Point("C", 5.0, 3.0);
        Point d = new Point("D", 5.0, 0.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);
        
        Segment db = new Segment(d, b);
        Segment ac = new Segment(a, c);

        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        di.addSegment(db);
        di.addSegment(ac);
        
        return di;
    }
    
    /**
     *     ______
     *    /     /       
     *   /  x  /        
     *  /_____/
     */
    public static Diagram premade_RhombusDiagonals()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 3.0, 4.0);
        Point c = new Point("C", 8.0, 4.0);
        Point d = new Point("D", 5.0, 0.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);
        
        Segment db = new Segment(d, b);
        Segment ac = new Segment(a, c);

        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        di.addSegment(db);
        di.addSegment(ac);
        
        return di;
    }
    
    /**
     *    ______
     *   /______\
     *  /________\
     */
    public static Diagram premade_TrapezoidWithMedian()
    {
        Diagram di = new Diagram();
        
        Point a = new Point("A", 0.0, 0.0);
        Point b = new Point("B", 1.0, 2.0);
        Point c = new Point("C", 3.0, 2.0);
        Point d = new Point("D", 4.0, 0.0);
        
        Point x = new Point("X", 0.5, 1.0);
        Point y = new Point("Y", 3.5, 1.0);
        
        di.addPoint(a);
        di.addPoint(b);
        di.addPoint(c);
        di.addPoint(d);
        
        di.addPoint(x);
        di.addPoint(y);
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment cd = new Segment(c, d);
        Segment da = new Segment(d, a);
        
        Segment xy = new Segment(x, y);

        di.addSegment(ab);
        di.addSegment(bc);
        di.addSegment(cd);
        di.addSegment(da);
        
        di.addSegment(xy);
        
        return di;
    }
}
