package backend.atoms.calculator;

import java.util.ArrayList;
import backend.ast.ASTException;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Segment;
import backend.atoms.components.AtomicRegion;
import backend.atoms.components.AtomicRegionException;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;

public class PrimitiveToRegionConverter
{

    //
    // Take the cycle-based representation and convert in into AtomicRegion objects.
    //
    public static ArrayList<AtomicRegion> Convert(PlanarGraph graph,
                                             ArrayList<Primitive> primitives, ArrayList<Circle> circles) throws Exception
    {
        ArrayList<MinimalCycle> cycles = new ArrayList<MinimalCycle>();
        ArrayList<Filament> filaments = new ArrayList<Filament>();

        for (Primitive primitive : primitives)
        {
            if (primitive != null && primitive instanceof MinimalCycle) 
            {
                MinimalCycle mincycleprim = (MinimalCycle) primitive;
                cycles.add(mincycleprim);
            }
            if (primitive != null && primitive instanceof Filament) 
            {
                Filament filprim = (Filament) primitive;
                filaments.add(filprim);
            }
        }
        
        //
        // Convert the filaments to atomic regions.
        //
        ArrayList<AtomicRegion> regions = new ArrayList<AtomicRegion>();
        if (!filaments.isEmpty())
        {
            ExceptionHandler.throwException(new Exception("A filament occurred in conversion to atomic regions."));
        }
        // why is this commented out? *****************************************************************************************
        // regions.AddRange(HandleFilaments(graph, circles, filaments));




        ComposeCycles(graph, cycles);

        // check to see what cycles were composed
        if (Utilities.ATOMIC_REGION_GEN_DEBUG)
        {
            String message = "Composed: ";
            for (MinimalCycle cycle : cycles)
            {
                message += "\t" + cycle.toString();
            }
            ExceptionHandler.throwException(new AtomicRegionException(message));
        }

        //
        // Convert all cycles (perimeters) to atomic regions
        //
        for (MinimalCycle cycle : cycles)
        {
            ArrayList<AtomicRegion> temp = cycle.ConstructAtomicRegions(circles, graph);
            for (AtomicRegion atom : temp)
            {
                if (!regions.contains(atom)) regions.add(atom);
            }
        }

        return regions;
    }

    //
    // A filament is a path from one node to another; it does not invoke a cycle.
    // In shaded-area problems this can only be accomplished with arcs of circles.
    //
    @SuppressWarnings("unused")
    private static ArrayList<AtomicRegion> HandleFilaments(PlanarGraph graph, ArrayList<Circle> circles, ArrayList<Filament> filaments) throws ASTException
    {
        ArrayList<AtomicRegion> atoms = new ArrayList<AtomicRegion>();

        for (Filament filament : filaments)
        {
            atoms.addAll(filament.extractAtomicRegions(graph, circles));
        }

        return atoms;
    }

    //
    // If a cycle has an edge that is EXTENDED, there exist two regions, one on each side of the segment; compose the two segments.
    //
    // Fixed point algorithm: while there exists a cycle with an extended segment, compose.
    private static void ComposeCycles(PlanarGraph graph, ArrayList<MinimalCycle> cycles)
    {
        for (int cycleIndex = HasComposableCycle(graph, cycles); cycleIndex != -1; cycleIndex = HasComposableCycle(graph, cycles))
        {
            // Get the cycle and remove it from the list.
            MinimalCycle thisCycle = cycles.get(cycleIndex);

            cycles.remove(cycleIndex);

            // Get the extended segment which is the focal segment of composition.
            Segment extendedSeg = thisCycle.GetExtendedSegment(graph);

            // Find the matching cycle that has the same Extended segment
            int otherIndex = GetComposableCycleWithSegment(graph, cycles, extendedSeg);
            MinimalCycle otherCycle = cycles.get(otherIndex);
            cycles.remove(otherIndex);

            // Compose the two cycles into a single cycle.
            MinimalCycle composed = thisCycle.Compose(otherCycle, extendedSeg);

            // Add the new, composed cycle
            cycles.add(composed);
        }
    }

    private static int HasComposableCycle(PlanarGraph graph, ArrayList<MinimalCycle> cycles)
    {
        for (int c = 0; c < cycles.size(); c++)
        {
            if (cycles.get(c).HasExtendedSegment(graph)) return c;
        }
        return -1;
    }

    private static int GetComposableCycleWithSegment(PlanarGraph graph,
                                                     ArrayList<MinimalCycle> cycles,
                                                     Segment segment)
    {
        for (int c = 0; c < cycles.size(); c++)
        {
            if (cycles.get(c).HasThisExtendedSegment(graph, segment)) return c;
        }

        return -1;
    }

}
