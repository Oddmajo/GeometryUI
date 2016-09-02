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
package rene.zirkel.structures;

// file: Coordinates.java
public class Coordinates4 {

    public double X, Y, X1, Y1, X2, Y2, X3, Y3;

    public Coordinates4(final double x, final double y,
            final double x1, final double y1,
            final double x2, final double y2,
            final double x3, final double y3) {
        X=x;
        Y=y;
        X1=x1;
        Y1=y1;
        X2=x2;
        Y2=y2;
        X3=x3;
        Y3=y3;

    }
}
