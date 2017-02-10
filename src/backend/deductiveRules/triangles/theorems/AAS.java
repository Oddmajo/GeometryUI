package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentTriangles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.exception.NotImplementedException;

public class AAS extends Theorem
{
    private static final String NAME = "AAS Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.AAS);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AAS(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.AAS;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceAAS());

        return deductions;
    }

//  A
   //      /\ 
   //     /  \
   //    /    \
   //   /______\
   //  B        C      
   //
   // In order for two triangles to be congruent, we require the following:
   //    Triangle(A, B, C), Triangle(D, E, F),
   //    Congruent(Angle(A, B, C), Angle(D, E, F)),
   //    Congruent(Segment(C, A), Segment(F, D)),
   //    Congruent(Angle(A, C, B), Angle(D, F, E)) -> Congruent(Triangle(A, B, C), Triangle(D, E, F)),
   //
    public Set<Deduction> deduceAAS()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();      
        Set<CongruentAngles> congruentAngles = _qhg.getCongruentAngles();
        Set<Triangle> triangles = _qhg.getTriangles();
        
        // get lists 
        Object[] triangleList = triangles.toArray();
        Object[] congruentAngleList = congruentAngles.toArray();

        for (CongruentSegments css : congruentSegments)
        {
            // Check all combinations of triangles to see if they are congruent
            // This congruence must include the new segment congruence
            for (int i = 0; i < triangleList.length - 1; i++)
            {
                for (int j = i + 1; j < triangleList.length; j++)
                {
                    for (int m = 0; m < congruentAngleList.length - 1; m++)
                    {
                        for (int n = m + 1; n < congruentAngleList.length; n++)
                        {
                            deductions.addAll(deduceAAS(triangleList[i], triangleList[j], congruentAngleList[m], congruentAngleList[n], css));
                        }
                    }
                }
            }
        }

        return deductions;
    }

   //
   // Checks for ASA given the 5 values
   //
   private static Set<Deduction> deduceAAS(Object triList, Object triList2,
                              Object caList, Object caList2, CongruentSegments css)
   {
       HashSet<Deduction> deductions = new HashSet<Deduction>();
       
       // change objects to actual types
       Triangle triangleList = (Triangle) triList;
       Triangle triangleList2 = (Triangle) triList2;
       CongruentAngles congruentAngleList = (CongruentAngles) caList;
       CongruentAngles congruentAngleList2 = (CongruentAngles) caList2;

       //
       // All congruence pairs must minimally relate the triangles
       //
       if (!congruentAngleList.LinksTriangles(triangleList, triangleList2)) return deductions;
       if (!congruentAngleList2.LinksTriangles(triangleList, triangleList2)) return deductions;
       if (!css.LinksTriangles(triangleList, triangleList2)) return deductions;

       // Is this angle an 'extension' of the actual triangle angle? If so, acquire the normalized version of
       // the angle, using only the triangle vertices to represent the angle
       Angle angle1Tri1 = triangleList.NormalizeAngle(triangleList._angleBelongs(congruentAngleList));
       Angle angle1Tri2 = triangleList2.NormalizeAngle(triangleList2._angleBelongs(congruentAngleList));

       Angle angle2Tri1 = triangleList.NormalizeAngle(triangleList._angleBelongs(congruentAngleList2));
       Angle angle2Tri2 = triangleList2.NormalizeAngle(triangleList2._angleBelongs(congruentAngleList2));

       // The angles for each triangle must be distinct
       if (angle1Tri1.equals(angle2Tri1) || angle1Tri2.equals(angle2Tri2)) return deductions;

       Segment segTri1 = triangleList.GetSegment(css);
       Segment segTri2 = triangleList2.GetSegment(css);

       // ASA situations
       if (segTri1.isIncludedSegment(angle1Tri1, angle2Tri1)) return deductions;
       if (segTri2.isIncludedSegment(angle1Tri2, angle2Tri2)) return deductions;

       // The segments for each triangle must be corresponding
       if (segTri1.equals(triangleList.OtherSide(angle1Tri1)) && segTri2.equals(triangleList2.OtherSide(angle2Tri2))) return deductions;
       if (segTri1.equals(triangleList.OtherSide(angle2Tri1)) && segTri2.equals(triangleList2.OtherSide(angle1Tri2))) return deductions;

       //
       // Construct the corrsesponding points between the triangles
       //
       List<Point> triangleOne = new ArrayList<Point>();
       List<Point> triangleTwo = new ArrayList<Point>();

       triangleOne.add(angle1Tri1.getVertex());
       triangleTwo.add(angle1Tri2.getVertex());

       triangleOne.add(angle2Tri1.getVertex());
       triangleTwo.add(angle2Tri2.getVertex());

       // We know the segment endpoint mappings above, now acquire the opposite point
       triangleOne.add(triangleList.otherPoint(angle1Tri1.getVertex(), angle2Tri1.getVertex()));
       triangleTwo.add(triangleList2.otherPoint(angle1Tri2.getVertex(), angle2Tri2.getVertex()));

       //
       // Construct the new clauses: congruent triangles and CPCTC
       //
       GeometricCongruentTriangles gcts = new GeometricCongruentTriangles(new Triangle(triangleOne), new Triangle(triangleTwo));

       // Hypergraph
       List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
       antecedent.add(triangleList);
       antecedent.add(triangleList2);
       antecedent.add(congruentAngleList);
       antecedent.add(congruentAngleList2);
       antecedent.add(css);

       deductions.add(new Deduction(antecedent, gcts, ANNOTATION));

       // Add all the corresponding parts as new congruent clauses
       // CongruentTriangles.GenerateCPCTC() was removed, Dr. Alvin needs to look at this - Drew
       //deductions.addAll(CongruentTriangles.GenerateCPCTC(gcts, triangleOne, triangleTwo));
       ExceptionHandler.throwException(new NotImplementedException("AAS Theorem: CongruentTriangles.GenerateCPCTC() was commented out"));
       

       return deductions;
   }

}
