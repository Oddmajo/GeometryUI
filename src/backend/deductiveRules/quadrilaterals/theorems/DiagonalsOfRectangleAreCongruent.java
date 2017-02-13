package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class DiagonalsOfRectangleAreCongruent extends Theorem
{
    private static final String NAME = "Diagonals Of Rectangle Are Congruent Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.DIAGONALS_OF_RECTANGLE_ARE_CONGRUENT);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public DiagonalsOfRectangleAreCongruent(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.DIAGONALS_OF_RECTANGLE_ARE_CONGRUENT;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //  A ________________  B
    //   |                |
    //   |                |
    //   |                |
    // D |________________| C
    //
    // Rectangle(A, B, C, D) -> Congruent(Segment(A, C), Segment(B, D))
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Rectangle> rectangles = _qhg.getRectangles();   
        Set<Strengthened> strengs = _qhg.getStrengthenedRectangles();

        for (Rectangle r : rectangles)
        {
            deductions.addAll(deduceTheorem(r, r));
        }
        
        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceTheorem((Rectangle)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static Set<Deduction> deduceTheorem(Rectangle rectangle, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //Instantiate this rectangle ONLY if the original figure has the rectangle diagonals drawn.
        if (rectangle.getDiagonalIntersection() == null) return deductions;

        // Determine the CongruentSegments opposing sides and output that.
        GeometricCongruentSegments gcs = new GeometricCongruentSegments(rectangle.getBLTRDiagonal(),
                rectangle.getBLTRDiagonal());

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, gcs, ANNOTATION));

        return deductions;
    }

}
