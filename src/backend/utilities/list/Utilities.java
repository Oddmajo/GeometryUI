/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package backend.utilities.list;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Angle;
import backend.ast.figure.components.Point;

/**
 * The Utilities class from the GeometryTutor project, converted from C#
 * <p>
 * Currently, I only have the booleans at the beginning and the following methods:
 *      AddUnique
 *      AddUniqueList
 *      makeList
 * The rest are commented out to be converted to Java : the future.
 * <p>
 * I have attempted to convert many of the functions, but I think some are done
 * incorrectly.  I have left them commented out for sanity's sake and to get the
 * Facet Identification package going.  I will convert the methods as needed.
 * @author Chris Alvin
 * @author Drew W
 *
 */
public class Utilities
{


	//
	// Given a list, remove duplicates
	//

	public static <T extends backend.ast.GroundedClause> List<T> RemoveDuplicates(List<T> list)
	{
		List<T> cleanList = new ArrayList<T>();

		for (int i = 0; i < list.size() - 1; i++)
		{
			if (list.get(i) != null)
			{
				for (int j = i + 1; j < list.size(); j++)
				{
					if (list.get(j) != null && isDuplicate(list.get(i), list.get(j)))
					{
						list.set(j, null);
					}
				}
			}
		}

		for (T t : list)
		{
			if (t != null)
			{
				cleanList.add(t);
			}
		}

		return cleanList;
	}



	private static <T extends backend.ast.GroundedClause> boolean isDuplicate(T clause1, T clause2)
	{
		if (clause1 instanceof Angle)
		{
			//Do not want to remove angles that are different but have coinciding rays (would cause problems when trying to find
			//inscribed and central angles inside circles)
			Angle angle = (Angle)clause1;
			return angle.equalRays(clause2);
		}
		else return clause1.StructurallyEquals(clause2);
	}


	//
	// Given a list of grounded clauses, add a new value which is structurally unique.
	//
	public static <T extends backend.ast.GroundedClause> int StructuralIndex(List<T> list, T t)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).StructurallyEquals(t)) return i;
		}

		return -1;
	}


	//
	// Given a list of grounded clauses, add a new value which is structurally unique.
	//

	public static <T extends backend.ast.GroundedClause> boolean HasStructurally(List<T> list, T t)
	{
		return StructuralIndex(list, t) != -1;
	}


	//
	// Given a list of grounded clauses, get the structurally unique.
	//
	public static <T extends backend.ast.GroundedClause> T GetStructurally(List<T> list, T t)
	{
		for (T oldT : list)
		{
			if (oldT.StructurallyEquals(t)) return oldT;
		}

		return null;
	}


	//
	// Given a list of grounded clauses, add a new value which is structurally unique.
	//
	public static <T extends backend.ast.GroundedClause> boolean addStructurallyUnique(List<T> list, T t)
	{
		if (HasStructurally(list, t)) return false;

		list.add(t);

		return true;
	}

//	//
//	// Given a list of grounded clauses, add a new value which is structurally unique.
//	// This has been commented out because it is handled by a previous method
//	//
//
////	public static <T extends ast.GroundedClause> int StructuralIndex(List<Pair<T, A>> list, T t) 
////	{
////		for (int i = 0; i < list.size(); i++)
////		{
////			if (list.get[i].StructurallyEquals(t)) return i;
////		}
////
////		return -1;
////	}


	// Given a sorted list, insert the element from the front to the back.
	public static void InsertAscendingOrdered(List<Integer> list, int value)
	{
		// Special Cases
		if (list.isEmpty())
		{
			list.add(value);
			return;
		}

		if (value > list.get(list.size() - 1))
		{
			list.add(value);
			return;
		}

		// General Case
		for (int i = 0; i < list.size(); i++)
		{
			if (value < list.get(i))
			{
				list.add(i, value);
				return;
			}
		}
	}

//	// Acquire the index of the clause : the hypergraph based only on structure
//
//	public static int StructuralIndex(Hypergraph.Hypergraph<ast.GroundedClause, Hypergraph.EdgeAnnotation> graph, ast.GroundedClause g)
//	{
//		//
//		// Handle general case
//		//
//		List<Hypergraph.HyperNode<ast.GroundedClause, Hypergraph.EdgeAnnotation>> vertices = graph.vertices;
//
//		for (int v = 0; v < vertices.size(); v++)
//		{
//			if (vertices[v].data.StructurallyEquals(g)) return v;
//
//			if (vertices[v].data is ast.Strengthened)
//			{
//				if ((vertices[v].data as ast.Strengthened).strengthened.StructurallyEquals(g)) return v;
//			}
//		}
//
//
//		//
//		// Handle strengthening by seeing if the clause is found without a 'strengthening' component
//		//
//		ast.Strengthened streng = g as ast.Strengthened;
//		if (streng != null)
//		{
//			int index = StructuralIndex(graph, streng.strengthened);
//			if (index != -1) return index;
//		}
//
//		return -1;
//	}


//	//
//	// Acquires the hypergraph index value of the given nodes using structural equality
//	//
//
//	public static List<Integer> CollectGraphIndices(Hypergraph.Hypergraph<ast.GroundedClause, Hypergraph.EdgeAnnotation> graph, List<ast.GroundedClause> clauses)
//	{
//		List<int> indices = new ArrayList<int>();
//
//		for (ast.GroundedClause gc : clauses)
//		{
//			int index = Utilities.StructuralIndex(graph, gc);
//			if (index != -1)
//			{
//				indices.add(index);
//			}
//			else
//			{
//				System.Diagnostics.Debug.WriteLine("We expect to find the given node (we did not): " + gc.toString());
//			}
//		}
//
//		return indices;
//	}



	// Ensure uniqueness of additions
	public static void addUniqueStructurally(ArrayList<Figure> figures, Figure f)
	{
		for (Figure figure : figures)
		{
			if (figure.StructurallyEquals(f)) return;
		}
		figures.add(f);
	}


	// Makes a list containing a single element
	public static <T> ArrayList<T> makeList(T obj)
	{
		ArrayList<T> l = new ArrayList<T>();

		l.add(obj);

		return l;
	}

	// Makes a list containing a single element
	public static <T> boolean addUnique(List<T> list, T obj)
	{
		if (list.contains(obj)) return false;

		list.add(obj);
		return true;
	}


	// Makes a list containing a single element
	public static <T> void AddUniqueList(List<T> list, List<T> objList)
	{
		for (T o : objList)
		{
			addUnique(list, o);
		}
	}
}
