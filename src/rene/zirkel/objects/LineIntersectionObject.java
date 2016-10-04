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
 
 
 package rene.zirkel.objects;

import rene.zirkel.construction.Construction;
import rene.zirkel.structures.Coordinates;

// file: IntersectionObject.java

public class LineIntersectionObject extends IntersectionObject {
	public LineIntersectionObject(final Construction c,
			final PrimitiveLineObject P1, final PrimitiveLineObject P2) {
		super(c, P1, P2);
		validate();
	}

	@Override
	public void updateCircleDep() {
		((PrimitiveLineObject) P1).addDep(this);
		((PrimitiveLineObject) P2).addDep(this);
	}

	@Override
	public void validate() {
		if (!P1.valid() || !P2.valid())
			Valid = false;
		else
			Valid = true;
		if (!Valid)
			return;
		final Coordinates c = PrimitiveLineObject.intersect(
				(PrimitiveLineObject) P1, (PrimitiveLineObject) P2);
		if (c == null) {
			Valid = false;
			return;
		}
		setXY(c.X, c.Y);
		if (Restricted) {
			if (!((PrimitiveLineObject) P1).contains(X, Y))
				Valid = false;
			else if (!((PrimitiveLineObject) P2).contains(X, Y))
				Valid = false;
		}
		if ((P1 instanceof TwoPointLineObject&&((TwoPointLineObject) P1).getP1().is3D())||(P1 instanceof TwoPointLineObject&&((TwoPointLineObject) P1).getP2().is3D())||(P2 instanceof TwoPointLineObject&&((TwoPointLineObject) P2).getP1().is3D())||(P2 instanceof TwoPointLineObject&&((TwoPointLineObject) P2).getP2().is3D())) {
        	try {	// Dibs intersection 3D
            	double a1=((TwoPointLineObject) P1).getP1().getX3D();
            	double b1=((TwoPointLineObject) P1).getP1().getY3D();
            	double c1=((TwoPointLineObject) P1).getP1().getZ3D();
            	double a2=((TwoPointLineObject) P1).getP2().getX3D();
            	double b2=((TwoPointLineObject) P1).getP2().getY3D();
            	double c2=((TwoPointLineObject) P1).getP2().getZ3D();
            	double a3=((TwoPointLineObject) P2).getP1().getX3D();
            	double b3=((TwoPointLineObject) P2).getP1().getY3D();
            	double c3=((TwoPointLineObject) P2).getP1().getZ3D();
            	double a4=((TwoPointLineObject) P2).getP2().getX3D();
            	double b4=((TwoPointLineObject) P2).getP2().getY3D();
            	double c4=((TwoPointLineObject) P2).getP2().getZ3D();
            	double det =(a2-a1)*(b3-b4)+(b2-b1)*(a4-a3);
            	double dets=(b3-b1)*(a4-a3)-(a3-a1)*(b4-b3);
            	double dett=(a2-a1)*(b3-b1)-(b2-b1)*(a3-a1);
            	double s=0.0;
            	double t=0.0;
            	if (Math.abs(det)<1e-12) {
            		det =(b2-b1)*(c3-c4)+(c2-c1)*(b4-b3);
            		dets=(c3-c1)*(b4-b3)-(b3-b1)*(c4-c3);
            		dett=(b2-b1)*(c3-c1)-(c2-c1)*(b3-b1);
            	}
            	s=dets/det;
        		t=dett/det;
            	if (Math.abs((c2-c1)*s-(c4-c3)*t-c3+c1)<1e-12) {
            		setX3D(a1+s*(a2-a1));
            		setY3D(b1+s*(b2-b1));
            		setZ3D(c1+s*(c2-c1));
    				setIs3D(true);
    				setSuperHidden(false);
            	}
            	else setSuperHidden(true);
        	} catch (final Exception ex) {
            }	
        }
	}
}
