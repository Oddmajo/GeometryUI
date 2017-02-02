package backend.deductiveRules.quadrilaterals.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class RectangleDefinition extends Definition
{
    private static final String NAME = "Definition of Rectangle";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.RECTANGLE_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public RectangleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.RECTANGLE_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromRectangle());
        deductions.addAll(deduceToRectangle());

        return deductions;
    }

    //  A ________________  B
    //   |                |
    //   |                |
    //   |                |
    // D |________________| C
    //
    // Rectangle(A, B, C, D) -> 4 Right Angles
    //
    public Set<Deduction> deduceFromRectangle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Rectangle> rectangles = _qhg.getRectangles();
        Set<Strengthened> strengs = _qhg.getStrengthenedRectangles();

        for (Rectangle rectangle : rectangles)
        {
            deductions.addAll(deduceFromRectangle(rectangle, rectangle));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceFromRectangle((Rectangle)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private HashSet<Deduction> deduceFromRectangle(Rectangle rectangle, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Determine the parallel opposing sides and output that.
        //
        List<Strengthened> newRightAngles = new ArrayList<Strengthened>();
        for (Angle rectAngle : rectangle.getAngles())
        {
            newRightAngles.add(new Strengthened(rectAngle, new RightAngle(rectAngle)));
        }

        // For hypergraph
        for (Strengthened streng : newRightAngles)
        {
            deductions.add(new Deduction(original, streng, ANNOTATION));
        }

        return deductions;
    }

    //  A ________________  B
    //   |                |
    //   |                |
    //   |                |
    // D |________________| C
    //
    // RightAngle(B, A, D), Parallelogram(A, B, C, D) -> Rectangle(A, B, C, D)
    //
    public Set<Deduction> deduceToRectangle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Parallelogram> parallelograms = _qhg.getParallelograms();
        Set<Strengthened> parallelStrengs = _qhg.getStrengthenedParallelograms();
        Set<RightAngle> rightAngles = _qhg.getRightAngles();
        Set<Strengthened> rightStrengs = _qhg.getStrengthenedRightAngles();
        
        for (Parallelogram parallelogram : parallelograms)
        {
            for (RightAngle rightAngle : rightAngles)
            {            
                deductions.addAll(deduceToRectangle(parallelogram, rightAngle, parallelogram, rightAngle));
            }
            
            for (Strengthened rightStreng : rightStrengs)
            {            
                deductions.addAll(deduceToRectangle(parallelogram, (RightAngle)rightStreng.getStrengthened(), parallelogram, rightStreng));
            }
        }

        for (Strengthened parallelStreng : parallelStrengs)
        {
            for (RightAngle rightAngle : rightAngles)
            {            
                deductions.addAll(deduceToRectangle((Parallelogram)parallelStreng.getStrengthened(), rightAngle, parallelStreng, rightAngle));
            }
            
            for (Strengthened rightStreng : rightStrengs)
            {            
                deductions.addAll(deduceToRectangle((Parallelogram)parallelStreng.getStrengthened(),
                                                    (RightAngle)rightStreng.getStrengthened(), parallelStreng, rightStreng));
            }
        }
        
        return deductions;
    }

    private HashSet<Deduction> deduceToRectangle(Parallelogram parallelogram, RightAngle ra,
                                                        GroundedClause originalPara, GroundedClause originalRightAngle)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Does this right angle apply to this quadrilateral?
        if (!parallelogram.HasAngle(ra)) return deductions;

        //
        // Create the new Rectangle object
        //
        Strengthened newRectangle = new Strengthened(parallelogram, new Rectangle(parallelogram));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(originalPara);
        antecedent.add(originalRightAngle);

        deductions.add(new Deduction(antecedent, newRectangle, ANNOTATION));

        return deductions;
    }
}
