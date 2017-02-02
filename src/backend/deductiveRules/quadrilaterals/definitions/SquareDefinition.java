package backend.deductiveRules.quadrilaterals.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.quadrilaterals.Square;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class SquareDefinition extends Definition
{
    private static final String NAME = "Definition of Square";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SQUARE_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public SquareDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.SQUARE_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromSquare());
        deductions.addAll(deduceToSquare());

        return deductions;
    }

    //  A __________  B
    //   |          |
    //   |          |
    //   |_         |
    // D |_|________| C
    //
    // Square(A, B, C, D) -> 4 Right Angles
    //
    public Set<Deduction> deduceFromSquare()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Square> squares = _qhg.getSquares();
        Set<Strengthened> strengs = _qhg.getStrengthenedSquares();

        for (Square square : squares)
        {
            deductions.addAll(deduceFromSquare(square, square));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceFromSquare((Square)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromSquare(Square square, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Determine the parallel opposing sides and output that.
        //
        List<Strengthened> newRightAngles = new ArrayList<Strengthened>();
        for (Angle angle : square.getAngles())
        {
            newRightAngles.add(new Strengthened(angle, new RightAngle(angle)));
        }

        // For hypergraph
        for (Strengthened rightAngle : newRightAngles)
        {
            deductions.add(new Deduction(original, rightAngle, ANNOTATION));
        }

        return deductions;
    }

    //  A __________  B
    //   |          |
    //   |          |
    //   |_         |
    // D |_|________| C
    //
    // Rhombus(A, B, C, D), RightAngle(A, D, C) -> Square(A, B, C, D)
    //
    public Set<Deduction> deduceToSquare()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Rhombus> rhombuses = _qhg.getRhombuses();
        Set<Strengthened> rhombusStrengs = _qhg.getStrengthenedRhombuses();
        Set<RightAngle> rightAngles = _qhg.getRightAngles();
        Set<Strengthened> rightStrengs = _qhg.getStrengthenedRightAngles();
        
        for (Rhombus rhombus : rhombuses)
        {
            for (RightAngle rightAngle : rightAngles)
            {            
                deductions.addAll(deduceToSquare(rhombus, rightAngle, rhombus, rightAngle));
            }
            
            for (Strengthened rightStreng : rightStrengs)
            {            
                deductions.addAll(deduceToSquare(rhombus, (RightAngle)rightStreng.getStrengthened(), rhombus, rightStreng));
            }
        }

        for (Strengthened rhombusStreng : rhombusStrengs)
        {
            for (RightAngle rightAngle : rightAngles)
            {            
                deductions.addAll(deduceToSquare((Rhombus)rhombusStreng.getStrengthened(), rightAngle, rhombusStreng, rightAngle));
            }
            
            for (Strengthened rightStreng : rightStrengs)
            {            
                deductions.addAll(deduceToSquare((Rhombus)rhombusStreng.getStrengthened(),
                                                 (RightAngle)rightStreng.getStrengthened(), rhombusStreng, rightStreng));
            }
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceToSquare(Rhombus rhombus, RightAngle ra,
                                                     GroundedClause originalRhom, GroundedClause originalRightAngle)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // Does this right angle apply to this quadrilateral?
        if (!rhombus.HasAngle(ra)) return deductions;

        //
        // Create the new Square object
        //
        Strengthened newSquare = new Strengthened(rhombus, new Square(rhombus));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(originalRhom);
        antecedent.add(originalRightAngle);

        deductions.add(new Deduction(antecedent, newSquare, ANNOTATION));

        return deductions;
    }
}
