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
    public HashSet<Equation> getGeneralEqs() { return _generalEqs; }

    private HashSet<SegmentEquation> _segmentEqs;
    public HashSet<SegmentEquation> getSegmentEqs() { return _segmentEqs; }
    private HashSet<GeometricSegmentEquation> _geometricSegmentEqs;
    public HashSet<GeometricSegmentEquation> getGeometricSegmentEqs() { return _geometricSegmentEqs; }
    private HashSet<AlgebraicSegmentEquation> _algebraicSegmentEqs;
    public HashSet<AlgebraicSegmentEquation> getAlgebraicSegmentEqs() { return _algebraicSegmentEqs; }
    
    private HashSet<AngleEquation> _angleEqs;
    public HashSet<AngleEquation> getAngleEqs() { return _angleEqs; }
    private HashSet<AlgebraicAngleEquation> _algebraicAngleEqs;
    public HashSet<AlgebraicAngleEquation> getAlgebraicAngleEqs() { return _algebraicAngleEqs; }
    private HashSet<GeometricAngleEquation> _geometricAngleEqs;
    public HashSet<GeometricAngleEquation> getGeometricAngleEqs() { return _geometricAngleEqs; }
    
    private HashSet<ArcEquation> _arcEqs;
    public HashSet<ArcEquation> getArcEqs() { return _arcEqs; }
    private HashSet<GeometricArcEquation> _geomtricArcEqs;
    public HashSet<GeometricArcEquation> getGeometricArcEqs() { return _geomtricArcEqs; }
    private HashSet<AlgebraicArcEquation> _algebraicArcEqs;
    public HashSet<AlgebraicArcEquation> getAlgebraicArcEqs() { return _algebraicArcEqs; }
    
    private HashSet<AngleArcEquation> _angleArcEqs;
    public HashSet<AngleArcEquation> getAngleArcEqs() { return _angleArcEqs; }
    private HashSet<GeometricAngleArcEquation> _geometricAngleArcEqs;
    public HashSet<GeometricAngleArcEquation> getGeometricAngleArcEqs() { return _geometricAngleArcEqs; }
    private HashSet<AlgebraicAngleArcEquation> _algebraicAngleArcEqs;
    public HashSet<AlgebraicAngleArcEquation> getAlgebraicAngleArcEqs() { return _algebraicAngleArcEqs; }

    private HashSet<Equation> _geometricEqs;
    public HashSet<Equation> getGeometricEqs() { return _geometricEqs; }
    private HashSet<Equation> _algebraicEqs;
    public HashSet<Equation> getAlgebraicEqs() { return _algebraicEqs; }
    

    public EquationQueryHandler()
    {
        _generalEqs = new HashSet<Equation>();

        _segmentEqs = new HashSet<SegmentEquation>();
        _geometricSegmentEqs = new HashSet<GeometricSegmentEquation>();
        _algebraicSegmentEqs = new HashSet<AlgebraicSegmentEquation>();

        _angleEqs = new HashSet<AngleEquation>();
        _algebraicAngleEqs = new HashSet<AlgebraicAngleEquation>();
        _geometricAngleEqs = new HashSet<GeometricAngleEquation>();
        
        _arcEqs = new HashSet<ArcEquation>();
        _geomtricArcEqs = new HashSet<GeometricArcEquation>();
        _algebraicArcEqs = new HashSet<AlgebraicArcEquation>();
        
        _angleArcEqs = new HashSet<AngleArcEquation>();
        _geometricAngleArcEqs = new HashSet<GeometricAngleArcEquation>();
        _algebraicAngleArcEqs = new HashSet<AlgebraicAngleArcEquation>();

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
        if (eq instanceof SegmentEquation)
        {
            _segmentEqs.add((SegmentEquation)eq);

            if (eq instanceof GeometricSegmentEquation)
            {
                _geometricEqs.add(eq);
                _geometricSegmentEqs.add((GeometricSegmentEquation) eq);
            }
            else if (eq instanceof AlgebraicSegmentEquation)
            {
                _algebraicEqs.add(eq);
                _algebraicSegmentEqs.add((AlgebraicSegmentEquation) eq);
            }
        }
        else if (eq instanceof AngleEquation)
        {
            _angleEqs.add((AngleEquation)eq);
            
            if (eq instanceof GeometricAngleEquation)
            {
                _geometricEqs.add(eq);
                _geometricAngleEqs.add((GeometricAngleEquation) eq);
            }
            else if (eq instanceof AlgebraicAngleEquation)
            {
                _algebraicEqs.add(eq);
                _algebraicAngleEqs.add((AlgebraicAngleEquation) eq);
            }
        }
        else if (eq instanceof ArcEquation)
        {
            _arcEqs.add((ArcEquation)eq);
            
            if (eq instanceof GeometricAngleEquation)
            {
                _geometricEqs.add(eq);
                _geomtricArcEqs.add((GeometricArcEquation) eq);
            }
            else if (eq instanceof AlgebraicAngleEquation)
            {
                _algebraicEqs.add(eq);
                _algebraicArcEqs.add((AlgebraicArcEquation) eq);
            }
        }
        else if (eq instanceof AngleArcEquation)
        {
            _angleArcEqs.add((AngleArcEquation)eq);
            
            if (eq instanceof GeometricAngleEquation)
            {
                _geometricEqs.add(eq);
                _geometricAngleArcEqs.add((GeometricAngleArcEquation) eq);
            }
            else if (eq instanceof AlgebraicAngleEquation)
            {
                _algebraicEqs.add(eq);
                _algebraicAngleArcEqs.add((AlgebraicAngleArcEquation) eq);
            }
        }
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
    public GeometricSegmentEquation getGeometricSegmentEquation(GeometricSegmentEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (GeometricSegmentEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_geometricSegmentEqs), eq.getTemplate()), eq);
    }
    
    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public AlgebraicSegmentEquation getAlgebraicSegmentEquation(AlgebraicSegmentEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (AlgebraicSegmentEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_algebraicSegmentEqs), eq.getTemplate()), eq);
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
    public AlgebraicAngleEquation getAlgebraicAngleEquation(AlgebraicAngleEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (AlgebraicAngleEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_algebraicAngleEqs), eq.getTemplate()), eq);
    }
    
    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public GeometricAngleEquation getGeometricAngleEquation(GeometricAngleEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (GeometricAngleEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_geometricAngleEqs), eq.getTemplate()), eq);
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
    public GeometricArcEquation getGeometricArcEquation(GeometricArcEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (GeometricArcEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_geomtricArcEqs), eq.getTemplate()), eq);
    }
    
    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public AlgebraicArcEquation getAlgebraicArcEquation(AlgebraicArcEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (AlgebraicArcEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_algebraicArcEqs), eq.getTemplate()), eq);
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
    public GeometricAngleArcEquation getGeometricAngleArcEquation(GeometricAngleArcEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (GeometricAngleArcEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_geometricAngleArcEqs), eq.getTemplate()), eq);
    }
    
    //
    // @param eq : User provides a 'replica' of the desired equation
    // @return the object in the hypergraph corresponding to eq
    //
    public AlgebraicAngleArcEquation getAlgebraicAngleArcEquation(AlgebraicAngleArcEquation eq)
    {
        //
        // (1) Acquire the set of equations via template and (2) filter
        //
        return (AlgebraicAngleArcEquation)filterToFind(getEquationsByTemplate(new HashSet<Equation>(_algebraicAngleArcEqs), eq.getTemplate()), eq);
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