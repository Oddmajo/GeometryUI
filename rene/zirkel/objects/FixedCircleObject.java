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

// file: CircleObject.java

import eric.bar.JPropertiesBar;
import java.awt.Frame;
import java.util.Enumeration;

import rene.dialogs.Warning;
import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.InvalidException;

public class FixedCircleObject extends PrimitiveCircleObject implements
MoveableObject, SimulationObject {

	Expression E;
	boolean EditAborted = false;
	boolean Dragable = false;

	public FixedCircleObject(final Construction c, final PointObject p1,
			final double x, final double y) {
		super(c, p1);
		init(c, x, y);
	}

	public void init(final Construction c, final double x, final double y) {

		E = new Expression(""
				+ Math.sqrt((x - M.getX()) * (x - M.getX()) + (y - M.getY())
						* (y - M.getY())), c, this);
		validate();
		updateText();
	}

	@Override
	public String getTag() {
		return "Circle";
	}

	@Override
	public void updateText() {
		if (E == null || !E.isValid()) {
			return;
		}
		setText(text2(Global.name("text.fixedcircle"), M.getName(), ""
				+ E.toString()));
	}

	@Override
	public void validate() {
		super.validate();
		if (!M.valid()) {
			Valid = false;
			return;
		}
		Valid = true;
		X = M.getX();
		Y = M.getY();
		if (E != null && !E.isValid()) {
			return;
		}
		try {
			if (E != null) {
				R = E.getValue();
			}
		} catch (final Exception e) {
			R = 0;
			Valid = false;
		}
		if (R < -1e-10) {
			Valid = false;
			return;
		}
	}

	@Override
	public void printArgs(final XmlWriter xml) {
		if (E.isValid()) {
			xml.printArg("fixed", E.toString());
		} else {
			xml.printArg("fixed", "" + R);
		}
		if (Dragable) {
			xml.printArg("dragable", "true");
		}
		super.printArgs(xml);
	}

	@Override
	public boolean canFix() {
		return true;
	}

	@Override
	public boolean fixed() {
		return true;
	}

	@Override
	public void setFixed(final String s) {
		E = new Expression(s, getConstruction(), this);
	}

	@Override
	public void round() {
		try {
			setFixed(round(E.getValue(), ZirkelCanvas.LengthsFactor) + "");
			validate();
		} catch (final Exception e) {
		}
	}



	@Override
	public boolean nearto(final int c, final int r, final ZirkelCanvas zc) {
		if (!Valid && M.valid()) {
			return M.nearto(c, r, zc);
		}
		return super.nearto(c, r, zc);
	}

	public boolean isValidFix() {
		return E.isValid();
	}

	@Override
	public void translate() {
		super.translate();
		try {
			setFixed(E.toString());
			E.translate();
		} catch (final Exception e) {
		}
	}

	@Override
	public String getStringLength() {
		return E.toString();
	}

	@Override
	public double getValue() throws ConstructionException {
		if (!Valid) {
			throw new InvalidException("exception.invalid");
		} else {
			return R;
		}
	}

	@Override
	public Enumeration depending() {
		super.depending();
		final Enumeration e = E.getDepList().elements();
		while (e.hasMoreElements()) {
			DL.add((ConstructionObject) e.nextElement());
		}
		return DL.elements();
	}

	@Override
	public void move(final double x, final double y) {
		init(getConstruction(), x, y);
	}

	public boolean moveable() {
		return Dragable || M.moveable();
	}

	@Override
	public boolean isFixed() {
		return true;
	}

	public boolean isEditAborted() {
		return EditAborted;
	}

	@Override
	public boolean isDragable() {
		return Dragable;
	}

	@Override
	public void setDragable(final boolean f) {
		Dragable = f;
	}

	public boolean fixedByNumber() {
		return (E != null && E.isNumber());
	}

	// For the simulate function:
	/**
	 * Set the simulation value, remember the old value.
	 */
	public void setSimulationValue(final double x) {
		R = x;
		final Expression OldE = E;
		E = null;
		validate();
		E = OldE;
	}

	/**
	 * Reset the old value.
	 */
	public void resetSimulationValue() {
		validate();
	}

	double x1, y1, x2, y2;

	public void startDrag(final double x, final double y) {
		x1 = M.getX();
		y1 = M.getY();
		x2 = x;
		y2 = y;
	}

	public void dragTo(final double x, final double y) {
		if (Dragable) {
			move(x, y);
		} else {
			M.move(x1 + (x - x2), y1 + (y - y2));
		}
	}

	public double getOldX() {
		return 0;
	}

	public double getOldY() {
		return 0;
	}

	@Override
	public void snap(final ZirkelCanvas zc) {
		if (moveable() && !Dragable) {
			M.snap(zc);
		}
	}
}
