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

import eric.bar.JPropertiesBar;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.util.Enumeration;

import eric.JEricPanel;

import rene.dialogs.Warning;
import rene.gui.ButtonAction;
import rene.gui.Global;
import rene.gui.IconBar;
import rene.gui.MyLabel;
import rene.gui.TextFieldAction;
import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.expression.InvalidException;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.MainGraphics;





public class FixedAngleObject extends PrimitiveLineObject implements
MoveableObject, SimulationObject, InsideObject {

	protected PointObject P2;
	static Count N = new Count();
	double A, A1, A2, AA;
	Expression E;
	boolean Filled = false;
	boolean Inverse = false;
	boolean EditAborted = false;
	boolean Dragable = false;
	boolean Reduced = false;
	public static final int NORMALSIZE = 1, SMALL = 0, LARGER = 2, LARGE = 3,
	RECT = 4;
	protected int DisplaySize = NORMALSIZE;

	public FixedAngleObject(final Construction c, final PointObject p1,
			final PointObject p2, final double x, final double y) {
		super(c);
		P1 = p1;
		P2 = p2;
		init(c, x, y);
		Unit = Global.getParameter("unit.angle", "°");
	}

	@Override
	public void setDefaults() {
		setShowName(Global.getParameter("options.angle.shownames", false));
		setShowValue(Global.getParameter("options.angle.showvalues", false));
		setColor(Global.getParameter("options.angle.color", 0), Global
				.getParameter("options.angle.pcolor", (ExpressionColor) null, this));
		setColorType(Global.getParameter("options.angle.colortype", 0));
		setFilled(Global.getParameter("options.angle.filled", false));
		setHidden(Cn.Hidden);
		setObtuse(Global.getParameter("options.angle.obtuse", false));
		setSolid(Cn.Solid);
		setLarge(Cn.LargeFont);
		setBold(Cn.BoldFont);
		setPartial(Cn.Partial);
	}

	@Override
	public void setTargetDefaults() {
		setShowName(Global.getParameter("options.angle.shownames", false));
		setShowValue(Global.getParameter("options.angle.showvalues", false));
		setColor(Global.getParameter("options.angle.color", 0), Global
				.getParameter("options.angle.pcolor", (ExpressionColor) null, this));
		setColorType(Global.getParameter("options.angle.colortype", 0));
		setFilled(Global.getParameter("options.angle.filled", false));
		setObtuse(Global.getParameter("options.angle.obtuse", false));
	}

	public void init(final Construction c, final double x, final double y,
			final boolean invert) {
		double dx = P1.getX() - P2.getX(), dy = P1.getY() - P2.getY();
		A1 = Math.atan2(dy, dx);
		dx = x - P2.getX();
		dy = y - P2.getY();
		A2 = Math.atan2(dy, dx);
		A = A2 - A1;
		if (A < 0) {
			A += 2 * Math.PI;
		} else if (A > 2 * Math.PI) {
			A -= 2 * Math.PI;
		}
		if (Inverse && Obtuse) {
			A = 2 * Math.PI - A;
		}
		if (invert && !Obtuse) {
			if (A > Math.PI) {
				A = 2 * Math.PI - A;
				Inverse = true;
			} else {
				Inverse = false;
			}
		}
		E = new Expression(""
				+ round(A / Math.PI * 180, ZirkelCanvas.EditFactor), c, this);
		validate();
		setColor(ColorIndex, SpecialColor);
		updateText();
	}

	public void init(final Construction c, final double x, final double y) {
		init(c, x, y, true);
	}

	double xx[] = new double[4], yy[] = new double[4];

	@Override
	public void paint(final MyGraphics g, final ZirkelCanvas zc) {
		if (!Valid || mustHide(zc)) {
			return;
		}
		// compute middle of the screen:
		final double xm = (zc.minX() + zc.maxX()) / 2, ym = (zc.minY() + zc
				.maxY()) / 2;
		// compute distance from middle to line:
		final double d = (xm - X1) * DY - (ym - Y1) * DX;
		// compute point with minimal distance
		final double x = xm - d * DY, y = ym + d * DX;
		// compute size of the screen
		final double a = Math.max(zc.maxX() - zc.minX(), zc.maxY() - zc.minY());
		if (Math.abs(d) > a) {
			return;
		}
		// compute distance from closest point to source
		final double b = (x - X1) * DX + (y - Y1) * DY;
		// compute the two visible endpoints
		double k1 = b - a;
		final double k2 = b + a;
		if (k1 < 0) {
			k1 = 0;
		}
		if (k1 >= k2) {
			return;
		}
		double c1 = zc.col(X1 + k1 * DX);
		final double c2 = zc.col(X1 + k2 * DX);
		double r1 = zc.row(Y1 + k1 * DY);
		final double r2 = zc.row(Y1 + k2 * DY);
		// paint:
		g.setColor(this);
		if (!Reduced) {
			g.drawLine(c1, r1, c2, r2, this);
		}
		final double R = zc.col(getDisplaySize(zc)) - zc.col(0);
		c1 = zc.col(X1) - R;
		r1 = zc.row(Y1) - R;
		final String s = AngleObject.translateToUnicode(getDisplayText());
		double DA = (A / Math.PI * 180);
		if (DA < 0) {
			DA += 360;
		} else if (DA >= 360) {
			DA -= 360;
		}
		if (isStrongSelected() && g instanceof MainGraphics) {
			((MainGraphics) g).drawMarkerArc(c1 + R, r1 + R, R, A1 / Math.PI
					* 180, DA);
		}
		g.setColor(this);
		if (Filled) {
			if (Math.abs(DA - 90) < 0.0000001) {

				final double dx1 = Math.cos(A1), dy1 = Math.sin(A1), dx2 = Math
				.cos(A1 + DA / 180 * Math.PI), dy2 = Math.sin(A1 + DA
						/ 180 * Math.PI);
				final double dx3 = dx1 + dx2, dy3 = dy1 + dy2;
				xx[0] = c1;
				yy[0] = r1;
				xx[0] = c1 + R;
				yy[0] = r1 + R;
				xx[1] = c1 + R + R * dx1;
				yy[1] = r1 + R - R * dy1;
				xx[2] = c1 + R + R * dx3;
				yy[2] = r1 + R - R * dy3;
				xx[3] = c1 + R + R * dx2;
				yy[3] = r1 + R - R * dy2;
				g.fillPolygon(xx, yy, 4, false, getColorType() != THICK, this);
				g.setColor(this);
				g.drawLine(xx[1], yy[1], xx[2], yy[2]);
				g.drawLine(xx[2], yy[2], xx[3], yy[3]);
			} else {
				g.fillArc(c1, r1, 2 * R + 1, 2 * R + 1, A1 / Math.PI * 180, DA,
						Selected || getColorType() == NORMAL,
						getColorType() != THICK, true, this);
			}

		} else {
			if (DisplaySize == RECT) {
				final double dx1 = Math.cos(A1), dy1 = Math.sin(A1), dx2 = Math
				.cos(A1 + DA / 180 * Math.PI), dy2 = Math.sin(A1 + DA
						/ 180 * Math.PI);
				g.drawLine(c1 + R + R * dx1, r1 + R - R * dy1, c1 + R + R
						* (dx1 + dx2), r1 + R - R * (dy1 + dy2));
				g.drawLine(c1 + R + R * (dx1 + dx2), r1 + R - R * (dy1 + dy2),
						c1 + R + R * dx2, r1 + R - R * dy2);
			} else {

				if (Math.abs(DA - 90) < 0.0000001) {
					final double dx1 = Math.cos(A1), dy1 = Math.sin(A1), dx2 = Math
					.cos(A1 + DA / 180 * Math.PI), dy2 = Math.sin(A1
							+ DA / 180 * Math.PI);
					final double dx3 = dx1 + dx2, dy3 = dy1 + dy2;
					xx[1] = c1 + R + R * dx1;
					yy[1] = r1 + R - R * dy1;
					xx[2] = c1 + R + R * dx3;
					yy[2] = r1 + R - R * dy3;
					xx[3] = c1 + R + R * dx2;
					yy[3] = r1 + R - R * dy2;
					g.setColor(this);
					g.drawLine(xx[1], yy[1], xx[2], yy[2]);
					g.drawLine(xx[2], yy[2], xx[3], yy[3]);
				} else {
					g.drawCircleArc(c1 + R, r1 + R, R, A1 / Math.PI * 180, DA,
							this);
				}

			}
		}
		if (!s.equals("")) {
			g.setLabelColor(this);
			setFont(g);
			DisplaysText = true;
			final double dx = Math.cos(A1 + A / 2), dy = Math.sin(A1 + A / 2);
			// if (s.equals("90"+getUnit())||Name.startsWith(".")) {
			// if (KeepClose) {
			// double dof=Math.sqrt(XcOffset*XcOffset+YcOffset*YcOffset);
			// TX1=zc.col(X1+dof*dx)-3;
			// TY1=zc.row(Y1+dof*dy)-3;
			// TX2=TX1+9;
			// TY2=TY1+9;
			// g.drawRect(zc.col(X1+dof*dx)-1,
			// zc.row(Y1+dof*dy)-1, 3, 3);
			// } else {
			// TX1=zc.col(X1+zc.dx(R*AngleObject.LabelScale)*dx+XcOffset)-3;
			// TY1=zc.row(Y1+zc.dy(R*AngleObject.LabelScale)*dy+YcOffset)-3;
			// TX2=TX1+9;
			// TY2=TY1+9;
			// g.drawRect(zc.col(X1+zc.dx(R*AngleObject.LabelScale)*dx+XcOffset)-1,
			// zc.row(Y1+zc.dy(R*AngleObject.LabelScale)*dy+YcOffset)-1, 3, 3);
			// }
			// } else {
			if (KeepClose) {
				final double dof = Math.sqrt(XcOffset * XcOffset + YcOffset
						* YcOffset);
				drawCenteredLabel(g, s, zc, X1 + dof * dx, Y1 + dof * dy, 0, 0);
			} else {
				drawCenteredLabel(g, s, zc, X1
						+ zc.dx(R * AngleObject.LabelScale) * dx, Y1
						+ zc.dy(R * AngleObject.LabelScale) * dy, XcOffset,
						YcOffset);
			}
			// }
		}
	}

	@Override
	public boolean canKeepClose() {
		return true;
	}

	@Override
	public void setKeepClose(final double x, final double y) {
		KeepClose = true;
		XcOffset = x - X1;
		YcOffset = y - Y1;
	}

	double getDisplaySize(final ZirkelCanvas zc) {
		double R = zc.dx(12 * zc.pointSize());
		if (DisplaySize == SMALL || DisplaySize == RECT) {
			R /= 2;
		} else if (DisplaySize == LARGER) {
			R *= 2;
		} else if (DisplaySize == LARGE) {
			final double dx = P1.getX() - X1, dy = P1.getY() - Y1;
			R = Math.sqrt(dx * dx + dy * dy);
		}
		return R;
	}

	@Override
	public String getTag() {
		return "Angle";
	}

	@Override
	public int getN() {
		return N.next();
	}

	@Override
	public void updateText() {
		if (E == null || !E.isValid()) {
			return;
		}
		setText(text3(Global.name("text.fixedangle"), P1.getName(), P2
				.getName(), E.toString()));
	}

	@Override
	public String getDisplayValue() {
		return Global.getLocaleNumber(A / Math.PI * 180, "angles");
	}

	// public String getDisplayValue() {
	// if (ZirkelCanvas.AnglesFactor<=2) {
	// return ""+(int) (A/Math.PI*180+0.5);
	// } else {
	// return ""+round(A/Math.PI*180, ZirkelCanvas.AnglesFactor);
	// }
	// }

	@Override
	public void validate() {
		if (!P1.valid() || !P2.valid()) {
			Valid = false;
			return;
		} else {
			X1 = P2.getX();
			Y1 = P2.getY();
			final double dx = P1.getX() - X1, dy = P1.getY() - Y1;
			if (Math.sqrt(dx * dx + dy * dy) < 1e-9) {
				Valid = false;
				return;
			}
			A1 = Math.atan2(dy, dx);
			boolean Negative = false;
			try {
				if (E != null && E.isValid()) {
					A = E.getValue() / 180 * Math.PI;
				}
				if (Obtuse) {
					if (Inverse) {
						A = -A;
					}
				} else {
					if (Inverse && Math.sin(A) > 0) {
						A = -A;
					}
					if (!Inverse && Math.sin(A) < 0) {
						A = -A;
					}
				}
				if (A < 0) {
					Negative = true;
				}
				while (A < 0) {
					A = A + 2 * Math.PI;
				}
				while (A >= 2 * Math.PI) {
					A = A - 2 * Math.PI;
				}
				A2 = A1 + A;
				DX = Math.cos(A2);
				DY = Math.sin(A2);
				AA = A;
			} catch (final Exception e) {
				Valid = false;
				return;
			}
			if (!Obtuse && A > Math.PI) {
				A1 = A2;
				A = 2 * Math.PI - A;
				A2 = A1 + A;
			} else if (Obtuse && Negative) {
				A1 = A2;
				A = 2 * Math.PI - A;
				A2 = A1 + A;
			}
			Valid = true;
		}
	}

	public double getLength() {
		return A;
	}

	@Override
	public void setFixed(final String s) {
		E = new Expression(s, getConstruction(), this);
		updateText();
	}

	@Override
	public void round() {
		try {
			setFixed(round(E.getValue(), ZirkelCanvas.AnglesFactor) + "");
			validate();
		} catch (final Exception e) {
		}
	}

	public void setEditFixed(final String s) {
		E = new Expression(s, getConstruction(), this);
		updateText();
	}

	@Override
	public boolean canFix() {
		return true;
	}

	@Override
	public void printArgs(final XmlWriter xml) {
		xml.printArg("first", P1.getName());
		xml.printArg("root", P2.getName());
		if (DisplaySize == SMALL) {
			xml.printArg("display", "small");
		}
		if (DisplaySize == LARGE) {
			xml.printArg("display", "large");
		}
		if (DisplaySize == LARGER) {
			xml.printArg("display", "larger");
		}
		if (DisplaySize == RECT) {
			xml.printArg("display", "rectangle");
		}
		if (Filled) {
			xml.printArg("filled", "true");
		}
		if (E.isValid()) {
			xml.printArg("fixed", E.toString());
		} else {
			xml.printArg("fixed", "" + A / Math.PI * 180);
		}
		if (!Obtuse) {
			xml.printArg("acute", "true");
		}
		if (Inverse) {
			xml.printArg("inverse", "true");
		}
		if (Reduced) {
			xml.printArg("reduced", "true");
		}
		if (Dragable) {
			xml.printArg("dragable", "true");
		}
		super.printArgs(xml);
	}



	@Override
	public boolean nearto(final int c, final int r, final ZirkelCanvas zc) {
		if (!Valid && P2.valid()) {
			return P2.nearto(c, r, zc);
		}
		if (!displays(zc)) {
			return false;
		}
		// compute point at c,r
		final double x = zc.x(c), y = zc.y(r);
		// compute distance from x,y
		final double d = (x - X1) * DY - (y - Y1) * DX;
		final double e = (x - X1) * DX + (y - Y1) * DY;
		// scale in screen coordinates
		Value = Math.abs(zc.col(zc.minX() + d) - zc.col(zc.minX()));
		if (!Reduced && Value < zc.selectionSize() && e > 0) {
			return true;
		}
		final double dx = zc.x(c) - X1, dy = zc.y(r) - Y1;
		final double size = zc.dx(zc.selectionSize());
		final double rd = getDisplaySize(zc);
		final double rr = Math.sqrt(dx * dx + dy * dy);
		boolean near;
		if (Filled || DisplaySize == RECT) {
			near = (rr < rd + size);
		} else {
			near = (Math.abs(rr - rd) < size);
		}
		if (!near) {
			return false;
		}
		if (rd < size) {
			return near;
		}
		double a = Math.atan2(dy, dx);
		if (a < 0) {
			a += 2 * Math.PI;
		}
		final double cc = 0.05;
		if (A1 - cc < a && A2 + cc > a) {
			return true;
		}
		a = a - 2 * Math.PI;
		if (A1 - cc < a && A2 + cc > a) {
			return true;
		}
		a = a + 4 * Math.PI;
		if (A1 - cc < a && A2 + cc > a) {
			return true;
		}
		return false;
	}

	@Override
	public void setDisplaySize(final int i) {
		DisplaySize = i;
	}

	@Override
	public int getDisplaySize() {
		return DisplaySize;
	}

	@Override
	public Enumeration depending() {
		super.depending();
		depset(P1, P2);
		final Enumeration e = E.getDepList().elements();
		while (e.hasMoreElements()) {
			DL.add((ConstructionObject) e.nextElement());
		}
		return DL.elements();
	}

	@Override
	public void translate() {
		P1 = (PointObject) P1.getTranslation();
		P2 = (PointObject) P2.getTranslation();
		try {
			setFixed(E.toString());
			E.translate();
		} catch (final Exception e) {
		}
	}

	@Override
	public String getE() {
		return E.toString();
	}

	@Override
	public double getValue() throws ConstructionException {
		if (!Valid) {
			throw new InvalidException("exception.invalid");
		} else {
			return A / Math.PI * 180;
		}
	}

	@Override
	public void setFilled(final boolean flag) {
		Filled = flag;
	}

	@Override
	public boolean isFilled() {
		return Filled;
	}

	@Override
	public boolean isFilledForSelect() {
		return false;
	}

	@Override
	public boolean contains(final double x, final double y) {
		final double a = (x - X1) * DX + (y - Y1) * DY;
		if (a < 1e-9) {
			return false;
		}
		return true;
	}

	@Override
	public double project(final double x, final double y) {
		final double h = super.project(x, y);
		if (h < 0) {
			return 0;
		}
		return h;
	}

	@Override
	public Enumeration points() {
		super.depending();
		return depset(P2);
	}

	@Override
	public void move(final double x, final double y) {
		init(getConstruction(), x, y, true);
	}

	public boolean moveable() {
		return Dragable; // E.isNumber();
	}

	@Override
	public boolean isFixed() {
		return true;
	}

	public boolean getInverse() {
		return Inverse;
	}

	public void setInverse(final boolean inverse) {
		Inverse = inverse;
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

	public boolean isReduced() {
		return Reduced;
	}

	public void setReduced(final boolean f) {
		Reduced = f;
	}

	public boolean fixedByNumber() {
		return (E != null && E.isNumber());
	}

	// For the simulate function:
	/**
	 * Set the simulation value, remember the old value.
	 */
	public void setSimulationValue(final double x) {
		A = x / 180 * Math.PI;
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

	public void startDrag(final double x, final double y) {
	}

	public void dragTo(final double x, final double y) {
		move(x, y);
	}

	public double getOldX() {
		return 0;
	}

	public double getOldY() {
		return 0;
	}

	public double containsInside(final PointObject P) {
		final double dx = P.getX() - X1, dy = P.getY() - Y1;
		double a = Math.atan2(dy, dx);
		if (a < A1) {
			a += 2 * Math.PI;
		}
		final double c = 1e-5;
		if (a > A1 && a < A1 + A) {
			return 1;
		} else if (a > A1 - c && a < A1 + A + c) {
			return 0.5;
		}
		return 0;
	}

	public boolean keepInside(final PointObject P) {
		if (containsInside(P) > 0) {
			return true;
		}
		final double x = P.getX(), y = P.getY();
		final double x1 = X1, y1 = Y1;
		double xmin = x1, ymin = y1, dmin = 1e20;
		double x2 = X1 + Math.cos(A1), y2 = Y1 + Math.sin(A1);
		double dx = x2 - x1, dy = y2 - y1;
		double r = dx * dx + dy * dy;
		double h = dx * (x - x1) / r + dy * (y - y1) / r;
		if (h < 0) {
			h = 0;
		}
		double xh = x1 + h * dx, yh = y1 + h * dy;
		double dist = Math.sqrt((x - xh) * (x - xh) + (y - yh) * (y - yh));
		if (dist < dmin) {
			dmin = dist;
			xmin = xh;
			ymin = yh;
		}
		x2 = X1 + Math.cos(A2);
		y2 = Y1 + Math.sin(A2);
		dx = x2 - x1;
		dy = y2 - y1;
		r = dx * dx + dy * dy;
		h = dx * (x - x1) / r + dy * (y - y1) / r;
		if (h < 0) {
			h = 0;
		}
		xh = x1 + h * dx;
		yh = y1 + h * dy;
		dist = Math.sqrt((x - xh) * (x - xh) + (y - yh) * (y - yh));
		if (dist < dmin) {
			dmin = dist;
			xmin = xh;
			ymin = yh;
		}
		P.move(xmin, ymin);
		return false;
	}
	public PointObject getP1() {
		return P1;
	}
	public PointObject getP2() {
		return P2;
	}
}
