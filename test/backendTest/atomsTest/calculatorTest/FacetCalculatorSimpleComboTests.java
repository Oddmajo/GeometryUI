package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.calculator.Filament;
import backend.atoms.calculator.IsolatedPoint;
import backend.atoms.calculator.MinimalCycle;
import backend.atoms.calculator.Primitive;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.parameters.Utilities;

public class FacetCalculatorSimpleComboTests
{

    @Test public void simpleFacetCalculator_Filament_Cycle_1_Test()
    {
        System.out.print("\nRunning simpleFacetCalculator_Filament_Cycle_1_Test...");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 1, 1);
        Point point2 = new Point("point2", 2, 2);
        Point point3 = new Point("point3", 3, 3);
        Point point4 = new Point("point4", 5, 1);
        Point point5 = new Point("point5", 7, 3);
        Point point6 = new Point("point6", 5, 5);
        
        // add the points to the graph
        //System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        graph.addNode(point5);
        graph.addNode(point6);
        
        // add edges
        graph.addUndirectedEdge(point1, point2, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point3, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point4, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point6, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point5, point6, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point5, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        //System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
//        System.out.println("Primitives: ");
//        for (Primitive prim : calc.GetPrimitives())
//        {
//            if (prim instanceof MinimalCycle)
//            {
//                MinimalCycle mincycle = (MinimalCycle) prim;
//                System.out.println("\t" + mincycle.toString());
//            }
//            else if (prim instanceof Filament)
//            {
//                Filament fil = (Filament) prim;
//                System.out.println("\t" +fil.toString());
//            }
//            
//        }
        assert(calc.GetPrimitives().get(0).toString().equals("Filament { point1(1.000, 1.000), point2(2.000, 2.000), point3(3.000, 3.000) }"));
        assert(calc.GetPrimitives().get(1).toString().equals("Cycle { point3(3.000, 3.000), point4(5.000, 1.000), point5(7.000, 3.000), point6(5.000, 5.000) }"));
        
        System.out.println("Done");
    }
    
    @Test public void simpleFacetCalculator_Filament_Cycle_2_Test()
    {
        System.out.print("\nRunning simpleFacetCalculator_Filament_Cycle_2_Test...");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 9, 1);
        Point point2 = new Point("point2", 8, 2);
        Point point3 = new Point("point3", 3, 3);
        Point point4 = new Point("point4", 5, 1);
        Point point5 = new Point("point5", 7, 3);
        Point point6 = new Point("point6", 5, 5);
        
        // add the points to the graph
        //System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        graph.addNode(point5);
        graph.addNode(point6);
        
        // add edges
        graph.addUndirectedEdge(point1, point2, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point5, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point4, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point6, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point5, point6, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point5, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        //System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
//        System.out.println("Primitives: ");
//        for (Primitive prim : calc.GetPrimitives())
//        {
//            if (prim instanceof MinimalCycle)
//            {
//                MinimalCycle mincycle = (MinimalCycle) prim;
//                System.out.println("\t" + mincycle.toString());
//            }
//            else if (prim instanceof Filament)
//            {
//                Filament fil = (Filament) prim;
//                System.out.println("\t" +fil.toString());
//            }
//            
//        }
        assert(calc.GetPrimitives().get(0).toString().equals("Cycle { point3(3.000, 3.000), point4(5.000, 1.000), point5(7.000, 3.000), point6(5.000, 5.000) }"));
        assert(calc.GetPrimitives().get(1).toString().equals("Filament { point5(7.000, 3.000), point2(8.000, 2.000), point1(9.000, 1.000) }"));
        
        System.out.println("Done");
    }
    
    @Test public void simpleFacetCalculator_Filament_Cycle_IsoPoint_Test()
    {
        System.out.print("\nRunning simpleFacetCalculator_Filament_Cycle_IsoPoint_Test...");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 9, 1);
        Point point2 = new Point("point2", 8, 2);
        Point point3 = new Point("point3", 3, 3);
        Point point4 = new Point("point4", 5, 1);
        Point point5 = new Point("point5", 7, 3);
        Point point6 = new Point("point6", 5, 5);
        Point point7 = new Point("point7", 8, 5);
        Point point8 = new Point("point8", 1, 5);
        Point point9 = new Point("point9", 1, 1);
        Point point10 = new Point("point10", 5, 3);
        
        // add the points to the graph
        //System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        graph.addNode(point5);
        graph.addNode(point6);
        graph.addNode(point7);
        graph.addNode(point8);
        graph.addNode(point9);
        graph.addNode(point10);
        
        // add edges
        graph.addUndirectedEdge(point1, point2, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point5, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point4, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point6, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point5, point6, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point5, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        //System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
//        System.out.println("Primitives: ");
//        for (Primitive prim : calc.GetPrimitives())
//        {
//            if (prim instanceof MinimalCycle)
//            {
//                MinimalCycle mincycle = (MinimalCycle) prim;
//                System.out.println("\t" + mincycle.toString());
//            }
//            else if (prim instanceof Filament)
//            {
//                Filament fil = (Filament) prim;
//                System.out.println("\t" +fil.toString());
//            }
//            else if (prim instanceof IsolatedPoint)
//            {
//                IsolatedPoint isopoint = (IsolatedPoint) prim;
//                System.out.println("\t" + isopoint.toString());
//            }
//            
//        }
        
        assert(calc.GetPrimitives().get(0).toString().equals("Point { point9(1.000, 1.000) }"));
        assert(calc.GetPrimitives().get(1).toString().equals("Point { point8(1.000, 5.000) }"));
        assert(calc.GetPrimitives().get(2).toString().equals("Cycle { point3(3.000, 3.000), point4(5.000, 1.000), point5(7.000, 3.000), point6(5.000, 5.000) }"));
        assert(calc.GetPrimitives().get(3).toString().equals("Point { point10(5.000, 3.000) }"));
        assert(calc.GetPrimitives().get(4).toString().equals("Filament { point5(7.000, 3.000), point2(8.000, 2.000), point1(9.000, 1.000) }"));
        assert(calc.GetPrimitives().get(5).toString().equals("Point { point7(8.000, 5.000) }"));
        
        System.out.println("Done");
    }
    
    @Test public void simpleFacetCalculator_FilamentBetweenCycles_IsoPoint_Test()
    {
        System.out.print("\nRunning simpleFacetCalculator_FilamentBetweenCycles_IsoPoint_Test...");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 7, 1);
        Point point2 = new Point("point2", 7, 3);
        Point point3 = new Point("point3", 8, 4);
        Point point4 = new Point("point4", 5, 3);
        Point point5 = new Point("point5", 4, 2);
        Point point6 = new Point("point6", 2, 2);
        Point point7 = new Point("point7", 1, 4);
        Point point8 = new Point("point8", 3, 5);
        Point point9 = new Point("point9", 7, 6);
        Point point10 = new Point("point10", 9, 6);
        Point point11 = new Point("point11", 11, 6);
        Point point12 = new Point("point12", 11, 4);
        Point point13 = new Point("point13", 10, 3);
        Point point14 = new Point("point14", 10, 2);
        Point point15 = new Point("point15", 10, 1);
        Point point16 = new Point("point16", 11, 0);
        Point point17 = new Point("point17", 12, 1);
        Point point18 = new Point("point18", 13, 2);
        Point point19 = new Point("point19", 12, 3);
        Point point20 = new Point("point20", 12, 5);
        Point point21 = new Point("point21", 11, 2);
        Point point22 = new Point("point22", 10, 5);
        Point point23 = new Point("point23", 6, 4);
        Point point24 = new Point("point24", 6, 2);
        
        // add the points to the graph
        //System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        graph.addNode(point5);
        graph.addNode(point6);
        graph.addNode(point7);
        graph.addNode(point8);
        graph.addNode(point9);
        graph.addNode(point10);
        graph.addNode(point11);
        graph.addNode(point12);
        graph.addNode(point13);
        graph.addNode(point14);
        graph.addNode(point15);
        graph.addNode(point16);
        graph.addNode(point17);
        graph.addNode(point18);
        graph.addNode(point19);
        graph.addNode(point20);
        graph.addNode(point21);
        graph.addNode(point22);
        graph.addNode(point23);
        graph.addNode(point24);
        
        // add edges
        graph.addUndirectedEdge(point7, point8, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point7, point6, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point6, point5, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point5, point4, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point8, point4, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point2, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point3, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point14, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point15, point14, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point15, point16, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point15, point17, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point16, point17, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point17, point19, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point17, point18, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point18, point19, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point19, point13, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point13, point14, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point13, point12, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point19, point12, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point12, point11, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point11, point10, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point10, point9, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        //System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
//        System.out.println("Primitives: ");
//        for (Primitive prim : calc.GetPrimitives())
//        {
//            if (prim instanceof MinimalCycle)
//            {
//                MinimalCycle mincycle = (MinimalCycle) prim;
//                System.out.println("\t" + mincycle.toString());
//            }
//            else if (prim instanceof Filament)
//            {
//                Filament fil = (Filament) prim;
//                System.out.println("\t" +fil.toString());
//            }
//            else if (prim instanceof IsolatedPoint)
//            {
//                IsolatedPoint isopoint = (IsolatedPoint) prim;
//                System.out.println("\t" + isopoint.toString());
//            }
//            
//        }
        
        assert(calc.GetPrimitives().get(0).toString().equals("Cycle { point7(1.000, 4.000), point6(2.000, 2.000), point5(4.000, 2.000), point4(5.000, 3.000), point8(3.000, 5.000) }"));
        assert(calc.GetPrimitives().get(1).toString().equals("Filament { point4(5.000, 3.000), point2(7.000, 3.000) }"));
        assert(calc.GetPrimitives().get(2).toString().equals("Point { point24(6.000, 2.000) }"));
        assert(calc.GetPrimitives().get(3).toString().equals("Point { point23(6.000, 4.000) }"));
        assert(calc.GetPrimitives().get(4).toString().equals("Filament { point1(7.000, 1.000), point2(7.000, 3.000), point3(8.000, 4.000), point14(10.000, 2.000) }"));
        assert(calc.GetPrimitives().get(5).toString().equals("Filament { point9(7.000, 6.000), point10(9.000, 6.000), point11(11.000, 6.000), point12(11.000, 4.000) }"));
        assert(calc.GetPrimitives().get(6).toString().equals("Cycle { point15(10.000, 1.000), point16(11.000, 0.000), point17(12.000, 1.000) }"));
        assert(calc.GetPrimitives().get(7).toString().equals("Cycle { point15(10.000, 1.000), point17(12.000, 1.000), point19(12.000, 3.000), point13(10.000, 3.000), point14(10.000, 2.000) }"));
        assert(calc.GetPrimitives().get(8).toString().equals("Cycle { point13(10.000, 3.000), point19(12.000, 3.000), point12(11.000, 4.000) }"));
        assert(calc.GetPrimitives().get(9).toString().equals("Point { point22(10.000, 5.000) }"));
        assert(calc.GetPrimitives().get(10).toString().equals("Point { point21(11.000, 2.000) }"));
        assert(calc.GetPrimitives().get(11).toString().equals("Cycle { point17(12.000, 1.000), point18(13.000, 2.000), point19(12.000, 3.000) }"));
        assert(calc.GetPrimitives().get(12).toString().equals("Point { point20(12.000, 5.000) }"));
        
        System.out.println("Done");
    }

}
