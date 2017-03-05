package backend.deductiveRules.algebra;

import backend.hypergraph.*;
import backend.utilities.Pair;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentTriangles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class TransitiveCongruentTriangles extends Axiom
{
    private static final String NAME = "Transitivity of Congruent Triangles";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.TRANSITIVE_CONGRUENT_TRIANGLES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public TransitiveCongruentTriangles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.TRANSITIVE_CONGRUENT_TRIANGLES;
    }
    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<>();
        deductions.addAll(duduceEquations());
        return deductions;
    }
    

    // Congruences imply equations: AB \cong CD -> AB = CD


    //
    // Implements transitivity with equations
    // Congruent(Triangle(A, B, C), Triangle(D, E, F)), Congruent(Triangle(L, M, N), Triangle(D, E, F)) -> Congruent(Triangle(A, B, C), Triangle(L, M, N))
    //
    // This includes CongruentSegments and CongruentAngles
    //
    // Generation of new equations instanceof restricted to the following rules; let G be Geometric and A algebriac
    //     G + G -> A
    //     G + A -> A
    //     A + A -X> A  <- Not allowed
    //
    public static ArrayList<Deduction> duduceEquations()
    {

        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        HashSet<GeometricCongruentTriangles> gcts = _qhg.getGeometricCongruentTriangles();
        HashSet<AlgebraicCongruentTriangles> acts = _qhg.getAlgebraicCongruentTriangles();
        
        
        for(GeometricCongruentTriangles gct : gcts)
        {
            if(!gct.isReflexive())
            {
                for (GeometricCongruentTriangles oldGCTS : gcts)
                {
                    deductions.addAll(deduceTransitive(oldGCTS, gct));
                }

                for (AlgebraicCongruentTriangles oldACTS : acts)
                {
                    deductions.addAll(deduceTransitive(oldACTS, gct));
                }
            }
        }

        return deductions;
    }

    public static ArrayList<Deduction> deduceTransitive(CongruentTriangles cts1, CongruentTriangles cts2)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        Dictionary<Point, Point> firstTriangleCorrespondence = cts1.HasTriangle(cts2.getFirstTriangle());
        Dictionary<Point, Point> secondTriangleCorrespondence = cts1.HasTriangle(cts2.getSecondTriangle());

        // Same Congruence
        if (firstTriangleCorrespondence != null && secondTriangleCorrespondence != null) return deductions;

        // No relationship between congruences
        if (firstTriangleCorrespondence == null && secondTriangleCorrespondence == null) return deductions;

        // Acquiring the triangle that links the congruences
        Triangle linkTriangle = firstTriangleCorrespondence != null ? cts2.getFirstTriangle() : cts2.getSecondTriangle();
        ArrayList<Point> linkPts = linkTriangle.getPoints();

        Dictionary<Point, Point> otherCorrGCTSpts = cts1.OtherTriangle(linkTriangle);
        Dictionary<Point, Point> otherCorrCTSpts = cts2.OtherTriangle(linkTriangle);

        // Link the other triangles together in a new congruence
        Dictionary<Point, Point> newCorrespondence = new Hashtable<Point,Point>();
        for (Point linkPt : linkPts)
        {
            Point otherGpt = otherCorrGCTSpts.get(linkPt);
            if (otherGpt == null) ExceptionHandler.throwException( new ArgumentException("Something strange happened in Triangle correspondence."));

            Point otherCpt = otherCorrCTSpts.get(linkPt);
            if (otherCpt == null) throw new ArgumentException("Something strange happened in Triangle correspondence.");

            newCorrespondence.put(otherGpt, otherCpt);
        }

        ArrayList<Point> triOne = new ArrayList<Point>(); 
        ArrayList<Point> triTwo = new ArrayList<Point>();
        for (Pair<Point, Point> pair : newCorrespondence)
        {
            triOne.add(pair.getKey());
            triTwo.add(pair.getValue());
        }

        //
        // Create the new congruence
        //
        AlgebraicCongruentTriangles acts = new AlgebraicCongruentTriangles(new Triangle(triOne), new Triangle(triTwo));

        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(cts1);
        antecedent.add(cts2);

        deductions.add(new Deduction(antecedent, acts, ANNOTATION));

        return deductions;
    }
}
