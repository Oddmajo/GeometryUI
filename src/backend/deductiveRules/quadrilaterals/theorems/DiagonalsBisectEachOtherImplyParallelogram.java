package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class DiagonalsBisectEachOtherImplyParallelogram extends Theorem
{
    private static final String NAME = "Diagonals Bisect Each Other Imply Parallelogram Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.DIAGONALS_BISECT_EACH_OTHER_IMPLY_PARALLELOGRAM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public DiagonalsBisectEachOtherImplyParallelogram(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.DIAGONALS_BISECT_EACH_OTHER_IMPLY_PARALLELOGRAM;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }
    
    //      A ________________ B
    //      /                /
    //     /                /
    //    /                /
    // D /________________/ C
    //
    // Parallelogram(A, B, C, D) -> SegmentBisector(Segment(A, C), Segment(B, D)), SegmentBisector(Segment(B, D), Segment(A, C)),
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Quadrilateral> quads = _qhg.getQuadrilaterals();      
        Set<SegmentBisector> segmentBisectors = _qhg.getSegmentBisectors();
        Set<Strengthened> strengs = _qhg.getStrengthenedSegmentBisectors();

        // get list
        Object[] segmentBisectorsList = segmentBisectors.toArray();
        Object[] strengsList = strengs.toArray();

        for (Quadrilateral q : quads)
        {
            for (int i = 0; i < segmentBisectorsList.length - 1; i++)
            {
                for (int j = i + 1; j < segmentBisectorsList.length; j++)
                {
                    deductions.addAll(deduceTheorem(q, (SegmentBisector) segmentBisectorsList[i], (SegmentBisector) segmentBisectorsList[j], 
                            (GroundedClause) segmentBisectorsList[i], (GroundedClause) segmentBisectorsList[j]));
                }
            }
            
            for (int i = 0; i < strengsList.length - 1; i++)
            {
                for (int j = i + 1; j < strengsList.length; j++)
                {
                    deductions.addAll(deduceTheorem(q, (SegmentBisector)((Strengthened)strengsList[i]).getStrengthened(), 
                            (SegmentBisector)((Strengthened)strengsList[j]).getStrengthened(), 
                            (GroundedClause) strengsList[i], (GroundedClause) strengsList[j]));
                }
            }
            
            for (SegmentBisector sb : segmentBisectors)
            {
                for (Strengthened streng : strengs)
                {
                    deductions.addAll(deduceTheorem(q, (SegmentBisector)streng.getStrengthened(), sb, streng, sb));
                }
            }
        }
        

        return deductions;
    }

    //     A _________________ B
    //      /                /
    //     /     \/         /
    //    /      /\        /
    // D /________________/ C
    //
    // Quadrilateral(A, B, C, D), SegmentBisector(Segment(A, C), Segment(B, D)),
    //                            SegmentBisector(Segment(B, D), Segment(A, C)) -> Parallelogram(A, B, C, D)
    //
    private static Set<Deduction> deduceTheorem(Quadrilateral quad, SegmentBisector sb1, SegmentBisector sb2, GroundedClause originalSB1, GroundedClause originalSB2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The two segment bisectors must intersect at the same point
        if (!sb1.getBisected().getIntersect().structurallyEquals(sb2.getBisected().getIntersect())) return deductions;

        // The bisectors must be part of the other segment bisector.
        if (!sb1.getBisected().HasSegment(sb2.getBisector())) return deductions;
        if (!sb2.getBisected().HasSegment(sb1.getBisector())) return deductions;

        // Do these segment bisectors define the diagonals of the quadrilateral?
        if (!sb1.getBisector().HasSubSegment(quad.getTLBRDiagonal()) && !sb2.getBisector().HasSubSegment(quad.getTLBRDiagonal())) return deductions;
        if (!sb1.getBisector().HasSubSegment(quad.getBLTRDiagonal()) && !sb2.getBisector().HasSubSegment(quad.getBLTRDiagonal())) return deductions;

        // Determine the CongruentSegments opposing sides and output that.
        Strengthened newParallelogram = new Strengthened(quad, new Parallelogram(quad));
        
        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(quad);
        antecedent.add(originalSB1);
        antecedent.add(originalSB2);

        deductions.add(new Deduction(antecedent, newParallelogram, ANNOTATION));

        return deductions;
    }

}
