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

// file: IntersectionObject.java

import java.util.Enumeration;

import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;
import rene.zirkel.expression.Expression;

/**
 * This is the parent class of various intersection objects. Intersections are
 * points. Problems are multiple intersections and restrictions to keep one of
 * them away or close to some other point. The most problematic intersections
 * are between objects of type PointonObject.
 * 
 * In case of two possible intersection points, there is also the option to
 * switch from one object to the other in automatic tracks. This allows to run
 * through all possible states of a construction.
 * 
 * @author Rene
 */

public class IntersectionObject extends PointObject {
	protected ConstructionObject P1, P2;
	private static Count N = new Count();
	protected Expression Away;
	protected boolean StayAway = true;
	protected boolean First;
	protected boolean Switched;
	protected boolean Restricted;
	protected boolean Alternate;

	public IntersectionObject(final Construction c,
			final ConstructionObject p1, final ConstructionObject p2) {
		super(c, 0, 0);
		Moveable = false;
		P1 = p1;
		P2 = p2;
		updateText();
		First = true;
		Switched = false;
		Restricted = true;
		Alternate = false;
	}

	@Override
	public String getTag() {
		return "Intersection";
	}

	@Override
	public int getN() {
		return N.next();
	}

	@Override
	public boolean nearto(final int x, final int y, final ZirkelCanvas zc) {
		if (!displays(zc))
			return false;
		final double c = zc.col(X), r = zc.row(Y);
		final int size = (int) zc.selectionSize();
		Value = Math.abs(x - c) + Math.abs(y - r);
		return Value <= size * 5;
	}

	@Override
	public void updateText() {
		try {
			setText(text2(Global.name("text.intersection"), P1.getName(), P2
					.getName()));
		} catch (final Exception e) {
		}
	}

	public void setFirst(final boolean flag) {
		First = flag;
	}

	public boolean isFirst() {
		return First;
	}

	@Override
	public void validate() {
		if (!P1.valid() || !P2.valid())
			Valid = false;
		else
			Valid = true;
	}

	public void validate(final double x, final double y) {
	}

	@Override
	public void printArgs(final XmlWriter xml) {
		xml.printArg("first", P1.getName());
		xml.printArg("second", P2.getName());
		if (getAway() != null) {
			if (StayAway)
				xml.printArg("awayfrom", getAway().getName());
			else
				xml.printArg("closeto", getAway().getName());
		}
		printType(xml);
		if (!Restricted)
			xml.printArg("valid", "true");
		if (Alternate)
			xml.printArg("alternate", "true");
	}

	public String away() {
		if (getAway() != null)
			return getAway().getName();
		else
			return "";
	}

	public boolean stayAway() {
		return StayAway;
	}

	public boolean setAway(final String s, final boolean flag) {
		Away = null;
		if (s.equals(""))
			return true;
		if (Cn == null)
			return true;
		Away = new Expression("@\"" + s + "\"", Cn, this);
		StayAway = flag;
		return getAway() != null;
	}

	public boolean setAway(final String s) {
		return setAway(s, true);
	}

	@Override
	public Enumeration depending() {
		super.depending();
		return depset(P1, P2);
	}

	@Override
	public void translate() {
		P1 = P1.getTranslation();
		P2 = P2.getTranslation();
		if (getAway() != null) {
			setAway(getAway().getName(), StayAway);
			Away.translate();
		}
	}

	public boolean isSwitchable() {
		return false;
	}

	/**
	 * Check, if the other intersection is already visible and defined. In this
	 * case, we want to keep the intersection different from the other
	 * intersection point.
	 */
	public void autoAway() {
		if (!autoAway(P1, P2))
			autoAway(P2, P1);
	}

	boolean autoAway(final ConstructionObject o1, final ConstructionObject o2) {
		if (o1 instanceof CircleObject) {
			final PointObject p1 = ((CircleObject) o1).getP2();
			if (p1.isHidden())
				return false;
			if (p1.dependsOn(o2) && !nearto(p1)) {
				setAway(p1.getName());
				return true;
			} else if (o2 instanceof CircleObject) {
				final PointObject p2 = ((CircleObject) o2).getP2();
				if (p2.isHidden())
					return false;
				if (p1 == p2 && !nearto(p1)) {
					setAway(p1.getName());
					return true;
				}
				return false;
			} else if (o2 instanceof PrimitiveLineObject) {
				final Enumeration en = ((PrimitiveLineObject) o2).points();
				while (en.hasMoreElements()) {
					final ConstructionObject oo = (ConstructionObject) en
					.nextElement();
					if (oo instanceof PointObject) {
						final PointObject o = (PointObject) oo;
						if (o.isHidden())
							return false;
						if (p1 == o && !nearto(p1)) {
							setAway(p1.getName());
							return true;
						}
					}
				}
			}
		} else if (o1 instanceof TwoPointLineObject) {
			final PointObject p1 = ((TwoPointLineObject) o1).getP1();
			if (!p1.isHidden() && p1.dependsOn(o2) && !nearto(p1)) {
				setAway(p1.getName());
				return true;
			}
			final PointObject p2 = ((TwoPointLineObject) o1).getP2();
			if (!p2.isHidden() && p2.dependsOn(o2) && !nearto(p2)) {
				setAway(p2.getName());
				return true;
			}
		}
		return false;
	}

	public void switchBack() {
		if (Switched)
			First = !First;
		Switched = false;
	}

	public void doSwitch() {
		Switched = !Switched;
		First = !First;
	}

	public boolean isSwitched() {
		return Switched;
	}

	public boolean isRestricted() {
		return Restricted;
	}

	public void setRestricted(final boolean flag) {
		Restricted = flag;
	}

	public PointObject getAway() {
		return getPointObject(Away);
	}

	public void setAlternate(final boolean flag) {
		Alternate = flag;
	}

	public boolean isAlternate() {
		return Alternate;
	}

	/**
	 * Returns, if this intersection can alternate between two states, like
	 * CircleIntersection and LineCircleIntersection. Used by the dialog.
	 */
	public boolean canAlternate() {
		return false;
	}

        public ConstructionObject getP1(){
            return P1;
        }

        public ConstructionObject getP2(){
            return P2;
        }
}

