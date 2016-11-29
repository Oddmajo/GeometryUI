package backend.hypergraph;

import java.util.HashSet;
import java.util.Set;

import backend.symbolicAlgebra.equations.*;
import backend.utilities.exception.ExceptionHandler;

//
// @author C. Alvin
//
// An encapsulated form of a quick lookup mechanism for equations
//
//
public final class EquationQueryHandler
{
    //
    // Equation containers
    //
    private HashSet<Equation> _generalEqs;

    private HashSet<SegmentEquation> _segmentEqs;
    private HashSet<AngleEquation> _angleEqs;
    private HashSet<ArcEquation> _arcEqs;
    private HashSet<AngleArcEquation> _angleArcEqs;
    
    private HashSet<Equation> _geometricEqs;
    private HashSet<Equation> _algebraicEqs;
    
    public EquationQueryHandler()
    {
        _generalEqs = new HashSet<Equation>();

        _segmentEqs = new HashSet<SegmentEquation>();
        _angleEqs = new HashSet<AngleEquation>();
        _arcEqs = new HashSet<ArcEquation>();
        _angleArcEqs = new HashSet<AngleArcEquation>();
        
        _geometricEqs = new HashSet<Equation>();
        _algebraicEqs = new HashSet<Equation>();
    }

    //
    // @param eq : new equation to classify accordingly
    //
    public void add(Equation eq)
    {
        _generalEqs.add(eq);

        //
        // Algebraic / Geomtric
        //
        if (eq.isAlgebraic()) _algebraicEqs.add(eq);
        else if (eq.isGeometric()) _geometricEqs.add(eq);
        else ExceptionHandler.throwException("Unexpected: equation is neither algebraic nor geometric."); 

        //
        // Classify based on equation type 
        //
        if (eq instanceof SegmentEquation) _segmentEqs.add((SegmentEquation)eq);
        else if (eq instanceof AngleEquation) _angleEqs.add((AngleEquation)eq);
        else if (eq instanceof ArcEquation) _arcEqs.add((ArcEquation)eq);
        else if (eq instanceof AngleArcEquation) _angleArcEqs.add((AngleArcEquation)eq);
    }

    
    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public SegmentEquation getSegmentEquation(SegmentEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (SegmentEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_segmentEqs), eq.getTemplate()), eq);
    }
    
    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public AngleEquation getAngleEquation(AngleEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (AngleEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_angleEqs), eq.getTemplate()), eq);
    }

    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public ArcEquation getArcEquation(ArcEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (ArcEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_arcEqs), eq.getTemplate()), eq);
    }
    
    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public AngleArcEquation getAngleArcEquation(AngleArcEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (AngleArcEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_angleArcEqs), eq.getTemplate()), eq);
    }
    
    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public Equation getGeneralEquation(Equation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return filterToFind(getEquationsByTemplate(new HashSet<Equation>(_generalEqs), eq.getTemplate()), eq);
    }
    
    //
    // We want to return equations based on the user-defined template
    // A template is dictated solely by the multipliers.
    //     For example, 2AM = AB        has template <2 ; 1>
    //                  x + y = z + w   has template <1, 1 ; 1, 1 >
    //
    private static Set<Equation> getEquationsByTemplate(Set<Equation> equations, Equation.EquationTemplate template)
    {
        Set<Equation> eqs = new HashSet<Equation>();
        
        for (Equation eq : equations)
        {
            if (template.satisfies(eq))
            {
                eqs.add(eq);
            }
        }
        
        return eqs;
    }
    
    //
    // Of the many, find the one equation we seek
    //
    private static Equation filterToFind(Set<Equation> candidates, Equation that)
    {
        for (Equation candidate : candidates)
        {
            if (candidate.equals(that)) return candidate;
        }
        
        return null;
    }
    
}