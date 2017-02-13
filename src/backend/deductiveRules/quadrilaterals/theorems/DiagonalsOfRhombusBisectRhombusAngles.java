package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;

public class DiagonalsOfRhombusBisectRhombusAngles extends Theorem
{

    private static final String NAME = "Diagonals Of Rhombi Bisect Rhombus Angles";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.DIAGONALS_OF_RHOMBUS_BISECT_ANGLES_OF_RHOMBUS);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public DiagonalsOfRhombusBisectRhombusAngles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.DIAGONALS_OF_RHOMBUS_BISECT_ANGLES_OF_RHOMBUS;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }
    
//  A __________  B
    //   |          |
    //   |          |
    //   |          |
    // D |__________| C
    //
    // Rhombus(A, B, C, D) -> AngleBisector(Angle(A, B, C), Segment(B, D))
    //                        AngleBisector(Angle(A, D, C), Segment(B, D))
    //                        AngleBisector(Angle(B, A, D), Segment(A, C))
    //                        AngleBisector(Angle(B, C, D), Segment(A, C))
    //
    public static Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Rhombus> rhoms = _qhg.getRhombuses();
        HashSet<Strengthened> strengs = _qhg.getStrengthenedRhombuses();
        
        for(Rhombus rhom : rhoms)
        {
            deductions.addAll(deduceToTheorem(rhom, rhom));
        }
        
        for(Strengthened streng : strengs)
        {
            deductions.addAll(deduceToTheorem((Rhombus)streng.getStrengthened(), streng));
        }
        return deductions;
    }

    private static Set<Deduction> deduceToTheorem(Rhombus rhombus, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // For hypergraph
        ArrayList<GroundedClause> antecedent = Utilities.MakeList(original);

        // deduce this rhombus diagonals ONLY if the original figure has the diagonals drawn.
        if (rhombus.TopLeftDiagonalIsValid())
        {
            AngleBisector ab1 = new AngleBisector(rhombus.topLeftAngle, rhombus.getTLBRDiagonal());
            AngleBisector ab2 = new AngleBisector(rhombus.bottomRightAngle, rhombus.getTLBRDiagonal());
            deductions.add(new Deduction(antecedent, ab1, ANNOTATION));
            deductions.add(new Deduction(antecedent, ab2, ANNOTATION));
        }

        if (rhombus.BottomRightDiagonalIsValid())
        {
            AngleBisector ab1 = new AngleBisector(rhombus.topRightAngle, rhombus.getBLTRDiagonal());
            AngleBisector ab2 = new AngleBisector(rhombus.bottomLeftAngle, rhombus.getBLTRDiagonal());
            deductions.add(new Deduction(antecedent, ab1, ANNOTATION));
            deductions.add(new Deduction(antecedent, ab2, ANNOTATION));
        }

        return deductions;
    }

}
