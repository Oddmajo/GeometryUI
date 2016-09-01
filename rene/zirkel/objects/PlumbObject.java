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

// file: PlumbObject.java

import java.util.Enumeration;

import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;

public class PlumbObject extends PrimitiveLineObject implements MoveableObject {
	PrimitiveLineObject L;
	static Count N = new Count();
	protected boolean Restricted = false;

	public PlumbObject(final Construction c, final PrimitiveLineObject l,
			final PointObject p) {
		super(c);
		P1 = p;
		L = l;
		validate();
		updateText();
	}

	@Override
	public String getTag() {
		return "Plumb";
	}

	@Override
	public int getN() {
		return N.next();
	}

	@Override
	public void updateText() {
		setText(text2(Global.name("text.plumb"), P1.getName(), L.getName()));
	}

	@Override
	public void validate() {
		if (!P1.valid() || !L.valid())
			Valid = false;
		else {
			Valid = true;
			X1 = P1.getX();
			Y1 = P1.getY();
			DX = -L.getDY();
			DY = L.getDX();
			if (L instanceof SegmentObject
					&& ((SegmentObject) L).getLength() == 0) {
				Valid = false;
			}
			if (Restricted) {
				if (!L.contains(X1, Y1))
					Valid = false;
			}
		}
	}

	@Override
	public void printArgs(final XmlWriter xml) {
		xml.printArg("point", P1.getName());
		xml.printArg("line", L.getName());
		if (!Restricted)
			xml.printArg("valid", "true");
		super.printArgs(xml);
	}

	public boolean isRestricted() {
		return Restricted;
	}

	public void setRestricted(final boolean flag) {
		Restricted = flag;
	}

	public boolean canRestrict() {
		return (L instanceof SegmentObject) || (L instanceof RayObject);
	}

	@Override
	public void setMainParameter(boolean b) {
		MainParameter = true;
	}

	@Override
	public Enumeration depending() {
		super.depending();
		return depset(P1, L);
	}

	@Override
	public void translate() {
		P1 = (PointObject) P1.getTranslation();
		L = (PrimitiveLineObject) L.getTranslation();
	}

	@Override
	public boolean contains(final PointObject p) {
		return p == P1;
	}

	@Override
	public boolean hasUnit() {
		return false;
	}

	public void dragTo(final double x, final double y) {
		P1.move(x1 + (x - x3), y1 + (y - y3));
	}

	@Override
	public void move(final double x, final double y) {
	}

	public boolean moveable() {
		if (P1.moveable()) {
			return true;
		}
		return false;
	}

	double x1, y1, x2, y2, x3, y3;

	public void startDrag(final double x, final double y) {
		x1 = P1.getX();
		y1 = P1.getY();
		x3 = x;
		y3 = y;
	}

	public double getOldX() {
		return 0;
	}

	public double getOldY() {
		return 0;
	}

	@Override
	public void snap(final ZirkelCanvas zc) {
		if (moveable()) {
			P1.snap(zc);
		}
	}

        public PrimitiveLineObject getL(){
            return L;
        }

}
