package instantiator.algebra;

import equations.*;
import instantiator.EdgeAggregator;
import instantiator.GenericRule;
import hypergraph.*;

import java.util.ArrayList;
import java.util.List;
import utilities.Pair;
import utilities.exception.ArgumentException;
import utilities.exception.ExceptionHandler;

public class TransitiveCongruentTriangles extends GenericRule
{
    /*
    private static String NAME = "Transitivity of Congruent Triangles";
    private static EdgeAnnotation annotation = new EdgeAnnotation(NAME, EngineUIBridge.JustificationSwitch.TRANSITIVE_CONGRUENT_TRIANGLES);

    // Congruences imply equations: AB \cong CD -> AB = CD
    private static List<GeometricCongruentTriangles> candidateGeoCongruentTriangles = new List<GeometricCongruentTriangles>();
    private static List<AlgebraicCongruentTriangles> candidateAlgCongruentTriangles = new List<AlgebraicCongruentTriangles>();

    // Resets all saved data.
    public static void clear()
    {
        candidateGeoCongruentTriangles.clear();
        candidateAlgCongruentTriangles.clear();
    }

    //
    // Implements transitivity with equations
    // Congruent(Triangle(A, B, C), Triangle(D, E, F)), Congruent(Triangle(L, M, N), Triangle(D, E, F)) -> Congruent(Triangle(A, B, C), Triangle(L, M, N))
    //
    // This includes CongruentSegments and CongruentAngles
    //
    // Generation of new equations is restricted to the following rules; let G be Geometric and A algebraic
    //     G + G -> A
    //     G + A -> A
    //     A + A -X> A  <- Not allowed
    //
    public static List<EdgeAggregator> instantiate(GroundedClause clause)
    {
        annotation.active = EngineUIBridge.JustificationSwitch.TRANSITIVE_CONGRUENT_TRIANGLES;

        List<EdgeAggregator> newGrounded = new List<EdgeAggregator>();

        if (clause instanceof GeometricCongruentTriangles)
        {
            GeometricCongruentTriangles newGCTS = (GeometricCongruentTriangles) clause;

            if (newGCTS.IsReflexive()) return newGrounded;

            for (GeometricCongruentTriangles oldGCTS : candidateGeoCongruentTriangles)
            {
                newGrounded.addRange(InstantiateTransitive(oldGCTS, newGCTS));
            }

            for (AlgebraicCongruentTriangles oldACTS : candidateAlgCongruentTriangles)
            {
                newGrounded.addRange(InstantiateTransitive(oldACTS, newGCTS));
            }

            candidateGeoCongruentTriangles.add(newGCTS);
        }
        else if (clause instanceof AlgebraicCongruentTriangles)
        {
            AlgebraicCongruentTriangles newACTS = (AlgebraicCongruentTriangles) clause;

            if (newACTS.IsReflexive()) return newGrounded;

            for (GeometricCongruentTriangles oldGCTS : candidateGeoCongruentTriangles)
            {
                newGrounded.addRange(InstantiateTransitive(oldGCTS, newACTS));
            }

            candidateAlgCongruentTriangles.Add(newACTS);
        }

        return newGrounded;
    }

    public static List<EdgeAggregator> instantiateTransitive(CongruentTriangles cts1, CongruentTriangles cts2)
    {
        List<EdgeAggregator> newGrounded = new List<EdgeAggregator>();

        Dictionary<Point, Point> firstTriangleCorrespondence = cts1.hasTriangle(cts2.ct1);
        Dictionary<Point, Point> secondTriangleCorrespondence = cts1.hasTriangle(cts2.ct2);

        // Same Congruence
        if (firstTriangleCorrespondence != null && secondTriangleCorrespondence != null) return newGrounded;

        // No relationship between congruences
        if (firstTriangleCorrespondence == null && secondTriangleCorrespondence == null) return newGrounded;

        // Acquiring the triangle that links the congruences
        Triangle linkTriangle = firstTriangleCorrespondence != null ? cts2.ct1 : cts2.ct2;
        List<Point> linkPts = linkTriangle.points;

        Dictionary<Point, Point> otherCorrGCTSpts = cts1.otherTriangle(linkTriangle);
        Dictionary<Point, Point> otherCorrCTSpts = cts2.otherTriangle(linkTriangle);

        // Link the other triangles together in a new congruence
        Dictionary<Point, Point> newCorrespondence = new Dictionary<Point,Point>();
        for (Point linkPt : linkPts)
        {
            Point otherGpt;
            if (!otherCorrGCTSpts.TryGetValue(linkPt, out otherGpt)) ExceptionHandler.throwException(new ArgumentException("Something strange happened in Triangle correspondence."));

            Point otherCpt;
            if (!otherCorrCTSpts.TryGetValue(linkPt, out otherCpt)) ExceptionHandler.throwException(new ArgumentException("Something strange happened in Triangle correspondence."));

            newCorrespondence.Add(otherGpt, otherCpt);
        }

        List<Point> triOne = new List<Point>(); 
        List<Point> triTwo = new List<Point>();
        for (Pair<Point, Point> pair : newCorrespondence)
        {
            triOne.add(pair.Key);
            triTwo.add(pair.Value);
        }

        //
        // Create the new congruence
        //
        AlgebraicCongruentTriangles acts = new AlgebraicCongruentTriangles(new Triangle(triOne), new Triangle(triTwo));

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(cts1);
        antecedent.add(cts2);

        newGrounded.add(new EdgeAggregator(antecedent, acts, annotation));

        return newGrounded;
    }
    */
}
