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

// file: SegmentObject.java

import java.util.Enumeration;

import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;

public class TwoPointLineObject extends PrimitiveLineObject implements
MoveableObject {
	protected PointObject P2, P1;
	double X2, Y2, R, R3D;

	public TwoPointLineObject(final Construction c, final PointObject p1,
			final PointObject p2) {
		super(c);
		P1 = p1;
		P2 = p2;
	}

	public void setP1P2(final PointObject p1, final PointObject p2) {
		P1 = p1;
		P2 = p2;
	}

	public PointObject getP2() {
		return P2;
	}
	
	public PointObject getP1() {
		return P1;
	}
	
        @Override
	public Enumeration secondaryParams() {
		DL.reset();
		return depset(P1, P2);
	}

	@Override
	public Enumeration depending() {
		super.depending();
		return depset(P1, P2);
	}

	public double getLength() {
		return R;
	}
	
	public double getLength3D() {
		return R3D;
	}

	@Override
	public void translate() {
		P1 = (PointObject) P1.getTranslation();
		P2 = (PointObject) P2.getTranslation();
	}

	@Override
	public boolean contains(final PointObject p) {
		return P1 == p || P2 == p;
	}

	

	public void dragTo(final double x, final double y) {
		// System.out.println(getName()+" : dragTo !");
		P1.move(x1 + (x - x3), y1 + (y - y3));
		P2.move(x2 + (x - x3), y2 + (y - y3));
	}

	@Override
	public void move(final double x, final double y) {
	}

	public boolean moveable() {
		if (P1.moveable() && P2.moveable())
			return true;
		return false;
	}

	double x1, y1, x2, y2, x3, y3;

	public void startDrag(final double x, final double y) {
		x1 = P1.getX();
		y1 = P1.getY();
		x2 = P2.getX();
		y2 = P2.getY();
		x3 = x;
		y3 = y;
	}

	@Override
	public void snap(final ZirkelCanvas zc) {
		if (moveable()) {
			P1.snap(zc);
			P2.snap(zc);
		}
	}

	public double getOldX() {
		return 0;
	}

	public double getOldY() {
		return 0;
	}

}
