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

public class Romberg {
	/**
	 * Summiere f(x),f(x+h),...,f(x+n*h)
	 */
	private static double sumUp(final Evaluator F, final double x,
			final double h, final int n) throws ConstructionException {
		double sum = F.evaluateF(x);

		for (int i = 1; i <= n; i++) {
			sum = sum + F.evaluateF(x + i * h);
		}
		return sum;
	}

	/**
	 * Romberg-Verfahren.
	 * 
	 * @param a
	 *            ,b = Intervall-Grenzen
	 * @param F
	 *            = Funktion, die integriert wird
	 * @param nstart
	 *            = Anzahl der ersten Unterteilungen
	 * @param eps
	 *            = Relative Genauigkeit zum Abbruch
	 * @param maxiter
	 *            = Maximale Iterationen
	 * @return Integral oder RuntimeException
	 */
	static public double compute(final Evaluator F, final double a,
			final double b, final int nstart, final double eps,
			final int maxiter) throws ConstructionException {
		// Ergebnisse der Trapezregel mit Schrittweite h/2^i
		final double t[] = new double[maxiter];
		int n = nstart;

		// Anfangsschrittweite
		double h = (b - a) / n;
		// Berechne Trapezregel dazu und d[0]
		double tlast = t[0] = (F.evaluateF(a) + F.evaluateF(b) + 2 * sumUp(F, a
				+ h, h, n - 2))
				* h / 2;
		// Bisheriges Ergebnis festhalten
		double old = t[0];

		// Halbiere Schrittweite bis Genauigkeit erreicht
		for (int i = 1; i < maxiter; i++) { // Halbiere Schrittweite:
			h = h / 2;
			n = n * 2;

			// Berechne Trapezregel (unter Verwendung des
			// letzten Ergebnisses:
			t[i] = tlast / 2 + sumUp(F, a + h, 2 * h, n / 2 - 1) * h;
			tlast = t[i];

			// Update der t[i]:
			double q = 4;
			for (int j = i - 1; j >= 0; j--) {
				t[j] = t[j + 1] + (t[j + 1] - t[j]) / (q - 1);
				q = q * 4;
			}

			// Abbruch-Kriterium
			final double res = t[0];
			if (Math.abs((res - old) / res) < eps)
				return res;
			old = res;
		}

		// Bei ?berschreiten der Interationsgrenze:
		return tlast;
	}

}
