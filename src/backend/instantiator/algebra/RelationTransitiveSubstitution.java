package instantiator.algebra;

import backend.equations.*;
import backend.instantiator.*;

import java.util.ArrayList;
import java.util.List;

public class RelationTransitiveSubstitution extends GenericRule
{
    /*
    //private static read-only string NAME = "Relation Transitive Substitution";

    // Transitivity of Parallel Lines
    private static List<GeometricParallel> geoParallel = new ArrayList<GeometricParallel>();
    private static List<AlgebraicParallel> algParallel = new ArrayList<AlgebraicParallel>();

    // Transitivity of Similar Triangles
    private static List<GeometricSimilarTriangles> geoSimilarTriangles = new ArrayList<GeometricSimilarTriangles>();
    private static List<AlgebraicSimilarTriangles> algSimilarTriangles = new ArrayList<AlgebraicSimilarTriangles>();

    // Resets all saved data.
    public static void clear()
    {
        geoParallel.clear();
        algParallel.clear();

        geoSimilarTriangles.clear();
        algSimilarTriangles.clear();
    }

    //
    // Implements transitivity with Relations (Parallel, Similar)
    // Relation(A, B), Relation(B, C) -> Relation(A, C)
    //
    // Generation of new relations is restricted to the following rules; let G be Geometric and A algebriac
    //     G + G -> A
    //     G + A -> A
    //     A + A -X> A  <- Not allowed
    //
    public static List<EdgeAggregator> instantiate(GroundedClause clause)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // Do we have appropriate clauses?
        if (!(clause instanceof Parallel) && !(clause instanceof SimilarTriangles)) return newGrounded;

        // Has this clause been generated before?
        // Since generated clauses will eventually be instantiated as well, this will reach a fixed point and stop.
        // Uniqueness of clauses needs to be handled by the class calling this
        if (clauseHasBeenDeduced(clause)) return newGrounded;

        // A reflexive expression provides no information of interest or consequence.
        if (clause.isReflexive()) return newGrounded;

        //
        // Process the clause
        //
        if (clause instanceof Parallel)
        {
            newGrounded.addAll(handleNewParallelRelation(clause instanceof Parallel));
        }
        else if (clause instanceof SimilarTriangles)
        {
            newGrounded.addAll(handleNewSimilarTrianglesRelation(clause instanceof SimilarTriangles));
        }

        // Add the new clause to the right list for later combining
        addToAppropriateList(clause);

        return newGrounded;
    }

    //
    // Generate all new relationships from a Geometric, Congruent Pair of Parallel Segments
    //
    private static List<EdgeAggregator> handleNewParallelRelation(Parallel parallel)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricParallel gp : geoParallel)
        {
            newGrounded.addAll(createTransitiveParallelSegments(gp, parallel));
        }

        if (parallel instanceof GeometricParallel)
        {
            // New transitivity? G + A -> A
            for (AlgebraicParallel ap : algParallel)
            {
                newGrounded.addAll(createTransitiveParallelSegments(ap, parallel));
            }
        }

        return newGrounded;
    }

    //
    // For generation of transitive Parallel Lines
    //
    private static List<EdgeAggregator> createTransitiveParallelSegments(Parallel parallel1, Parallel parallel2)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // If there is a deduction relationship between the given congruences, do not perform another substitution
        // CTA: remove?
        if (parallel1.hasGeneralPredecessor(parallel2))
        {
            return newGrounded;
        }

        int numSharedExps = parallel1.sharesNumClauses(parallel2);
        switch (numSharedExps)
        {
            case 0:
                // Nothing is shared: do nothing
                break;

            case 1:
                // Expected case to create a new congruence relationship
                return Parallel.createTransitiveParallel(parallel1, parallel2);

            case 2:
                // This is either reflexive or the exact same congruence relationship (which shouldn't happen)
                break;

            default:

                throw new Exception("Parallel Statements may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
        }

        return newGrounded;
    }


    //
    // Generate all new relationships from a Geoemetric, Congruent Pair of SimilarTriangles Segments
    //
    private static List<EdgeAggregator> handleNewSimilarTrianglesRelation(SimilarTriangles simTris)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricSimilarTriangles gsts : geoSimilarTriangles)
        {
            newGrounded.addAll(CreateTransitiveSimilarTriangles(gsts, simTris));
        }

        if (simTris instanceof GeometricSimilarTriangles)
        {
            // New transitivity? G + A -> A
            for (AlgebraicSimilarTriangles asts : algSimilarTriangles)
            {
                newGrounded.addAll(createTransitiveSimilarTriangles(asts, simTris));
            }
        }

        return newGrounded;
    }

    //
    // For generation of transitive SimilarTriangles Lines
    //
    private static List<EdgeAggregator> createTransitiveSimilarTriangles(SimilarTriangles simTris1, SimilarTriangles simTris2)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // If there is a deduction relationship between the given congruences, do not perform another substitution
        // CTA: remove?
        if (simTris1.hasGeneralPredecessor(simTris2))
        {
            return newGrounded;
        }

        int numSharedExps = simTris1.sharesNumTriangles(simTris2);
        switch (numSharedExps)
        {
            case 0:
                // Nothing is shared: do nothing
                break;

            case 1:
                // Expected case to create a new congruence relationship
                return SimilarTriangles.createTransitiveSimilarTriangles(simTris1, simTris2);

            case 2:
                // This is either reflexive or the exact same congruence relationship (which shouldn't happen)
                break;

            default:

                throw new Exception("Similar Triangles may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
        }

        return newGrounded;
    }

    //
    // Add the new grounded clause to the correct list.
    //
    private static void addToAppropriateList(GroundedClause c)
    {
        if (c instanceof GeometricParallel)
        {
            geoParallel.add((GeometricParallel) c);
        }
        else if (c instanceof AlgebraicParallel)
        {
            algParallel.add((AlgebraicParallel) c);
        }
        else if (c instanceof GeometricSimilarTriangles)
        {
            geoSimilarTriangles.add((GeometricSimilarTriangles) c);
        }
        else if (c instanceof AlgebraicSimilarTriangles)
        {
            algSimilarTriangles.add((AlgebraicSimilarTriangles) c);
        }
    }

    //
    // Add the new grounded clause to the correct list.
    //
    private static boolean clauseHasBeenDeduced(GroundedClause c)
    {
        if (c instanceof GeometricParallel)
        {
            return geoParallel.contains((GeometricParallel) c);
        }
        else if (c instanceof AlgebraicParallel)
        {
            return algParallel.contains((AlgebraicParallel) c);
        }
        else if (c instanceof GeometricSimilarTriangles)
        {
            return geoSimilarTriangles.contains((GeometricSimilarTriangles) c);
        }
        else if (c instanceof AlgebraicSimilarTriangles)
        {
            return algSimilarTriangles.contains((AlgebraicSimilarTriangles) c);
        }

        return false;
    }
    */
}
