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

public class Secant {
	public static double compute(final Evaluator F, double a, double b,
			final double eps) throws ConstructionException {
		double ay = F.evaluateF(a), by = F.evaluateF(b);
		double c = (a + b) / 2;
		if (ay * by > eps)
			throw new ConstructionException("");
		while (Math.abs(b - a) > eps) {
			final double cy = F.evaluateF(c);
			if (Math.abs(cy) < eps)
				return c;
			if (cy * ay > 0) {
				if (Math.abs(ay - cy) < eps) {
					a = c;
					ay = cy;
					c = (a + b) / 2;
				} else {
					final double d = a - ay * (a - c) / (ay - cy);
					if (d > b || d < a) {
						a = c;
						ay = cy;
						c = (a + b) / 2;
					} else {
						a = c;
						ay = cy;
						c = d;
					}
				}
			} else {
				final double d = a - ay * (a - c) / (ay - cy);
				b = c;
				by = cy;
				c = d;
			}
		}
		return c;
	}
}
