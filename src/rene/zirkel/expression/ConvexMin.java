/* 
 
Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

This file is part of C.a.R. software.

    C.a.R. is a free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, version 3 of the License.

    C.a.R. is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 
 */
 
 
 package rene.zirkel.expression;

import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.Evaluator;

public class ConvexMin {
	public static double computeMin(final Evaluator F, double a, double b,
			final double eps) throws ConstructionException {
		final double lambda = (Math.sqrt(5) - 1) / 2;
		double x2 = lambda * a + (1 - lambda) * b;
		double y2 = F.evaluateF(x2);
		double x3 = (1 - lambda) * a + lambda * b;
		double y3 = F.evaluateF(x3);
		while (b - a > eps) {
			if (y2 < y3) {
				b = x3;
				x3 = x2;
				y3 = y2;
				x2 = lambda * a + (1 - lambda) * b;
				y2 = F.evaluateF(x2);
			} else {
				a = x2;
				x2 = x3;
				y2 = y3;
				x3 = (1 - lambda) * a + lambda * b;
				y3 = F.evaluateF(x3);
			}
		}
		return (a + b) / 2;
	}

	public static double computeMax(final Evaluator F, double a, double b,
			final double eps) throws ConstructionException {
		final double lambda = (Math.sqrt(5) - 1) / 2;
		double x2 = lambda * a + (1 - lambda) * b;
		double y2 = F.evaluateF(x2);
		double x3 = (1 - lambda) * a + lambda * b;
		double y3 = F.evaluateF(x3);
		while (b - a > eps) {
			if (y2 > y3) {
				b = x3;
				x3 = x2;
				y3 = y2;
				x2 = lambda * a + (1 - lambda) * b;
				y2 = F.evaluateF(x2);
			} else {
				a = x2;
				x2 = x3;
				y2 = y3;
				x3 = (1 - lambda) * a + lambda * b;
				y3 = F.evaluateF(x3);
			}
		}
		return (a + b) / 2;
	}
}
