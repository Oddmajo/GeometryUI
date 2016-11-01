/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.parameters;

/**
 * All functionality related to user-defined parameters and debugging.
 * 
 * @author Chris Alvin
 * @author Drew W
 *
 */
public final class Utilities
{

    public static boolean OVERRIDE_DEBUG = false;

    public static final boolean DEBUG                       = OVERRIDE_DEBUG && true;
    public static final boolean CONSTRUCTION_DEBUG          = OVERRIDE_DEBUG && true;   // Generating clauses when analyzing input figure
    public static final boolean PEBBLING_DEBUG              = OVERRIDE_DEBUG && false;   // Hypergraph edges and pebbled nodes
    public static final boolean PROBLEM_GEN_DEBUG           = OVERRIDE_DEBUG && true;   // Generating the actual problems
    public static final boolean BACKWARD_PROBLEM_GEN_DEBUG  = OVERRIDE_DEBUG && true;   // Generating backward problems
    public static final boolean ATOMIC_REGION_GEN_DEBUG     = OVERRIDE_DEBUG && true;   // Generating atomic regions
    public static final boolean SHADED_AREA_SOLVER_DEBUG    = OVERRIDE_DEBUG && true;   // Solving a shaded area problem.
    public static final boolean FIGURE_SYNTHESIZER_DEBUG    = true;   // Solving a shaded area problem.

    // If the user specifies that an axiom, theorem, or definition is not to be used.
    public static final boolean RESTRICTING_AXS_DEFINITIONS_THEOREMS = true;
}
