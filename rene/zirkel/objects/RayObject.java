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

import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.MainGraphics;
import rene.zirkel.structures.Coordinates;


public class RayObject extends TwoPointLineObject {
	static Count N = new Count();

	public RayObject(final Construction c, final PointObject p1,
			final PointObject p2) {
		super(c, p1, p2);
		validate();
		updateText();
	}

	@Override
	public String getTag() {
		return "Ray";
	}

	@Override
	public int getN() {
		return N.next();
	}

	@Override
	public void setDefaults() {
		setShowName(Global.getParameter("options.line.shownames", false));
		setShowValue(Global.getParameter("options.line.showvalues", false));
		setColor(Global.getParameter("options.line.color", 0));
		setColorType(Global.getParameter("options.line.colortype", 0));
		setHidden(Cn.Hidden);
		setObtuse(Cn.Obtuse);
		setSolid(Cn.Solid);
		setLarge(Global.getParameter("options.line.large", false));
		setBold(Global.getParameter("options.line.bold", false));
	}

	@Override
	public void setTargetDefaults() {
		setShowName(Global.getParameter("options.line.shownames", false));
		setShowValue(Global.getParameter("options.line.showvalues", false));
		setColor(Global.getParameter("options.line.color", 0));
		setColorType(Global.getParameter("options.line.colortype", 0));
		setLarge(Global.getParameter("options.line.large", false));
		setBold(Global.getParameter("options.line.bold", false));
	}

	@Override
	public void updateText() {
		setText(text2(Global.name("text.ray"), P1.getName(), P2.getName()));
	}

	@Override
	public void validate() {
//        if ((P1==null)) {
//            System.out.println(this.getName()+" : "+P2.getName());
//        }
//        if ((P2==null)) {
//            System.out.println(this.getName()+" : "+P1.getName());
//        }

		if (!P1.valid() || !P2.valid()) {
			Valid = false;
			return;
		} else {
			Valid = true;
			X1 = P1.getX();
			Y1 = P1.getY();
			X2 = P2.getX();
			Y2 = P2.getY();
			// compute normalized vector in the direction of the line:
			DX = X2 - X1;
			DY = Y2 - Y1;
			R = Math.sqrt(DX * DX + DY * DY);
			if (R < 1e-10) {
				Valid = false;
				return;
			}
			DX /= R;
			DY /= R;
		}
	}

	@Override
	public void paint(final MyGraphics g, final ZirkelCanvas zc) {
		if (!Valid || mustHide(zc))
			return;
		// compute middle of the screen:
		final double xm = (zc.minX() + zc.maxX()) / 2, ym = (zc.minY() + zc
				.maxY()) / 2;
		// compute distance from middle to line:
		final double d = (xm - X1) * DY - (ym - Y1) * DX;
		// compute point with minimal distance
		final double x = xm - d * DY, y = ym + d * DX;
		// compute size of the screen
		final double a = Math.max(zc.maxX() - zc.minX(), zc.maxY() - zc.minY());
		if (Math.abs(d) > a)
			return;
		// compute distance from closest point to source
		final double b = (x - X1) * DX + (y - Y1) * DY;
		// compute the two visible endpoints
		k1 = b - a;
		k2 = b + a;
		k12valid = true;
		if (k1 < 0)
			k1 = 0;
		if (k1 >= k2)
			return;
		if (Partial && !zc.showHidden() && Dep != null) {
			final double dd = (zc.maxX() - zc.minX()) / 20;
			double dmin = -dd, dmax = R + dd;
			for (int i = 0; i < NDep; i++) {
				if (!Dep[i].valid() || Dep[i].mustHide(zc))
					continue;
				final double s = project(Dep[i].getX(), Dep[i].getY());
				if (s - dd < dmin)
					dmin = s - dd;
				else if (s + dd > dmax)
					dmax = s + dd;
			}
			if (k1 < dmin)
				k1 = dmin;
			if (k2 > dmax)
				k2 = dmax;
		}
		final double c1 = zc.col(X1 + k1 * DX), c2 = zc.col(X1 + k2 * DX), r1 = zc
		.row(Y1 + k1 * DY), r2 = zc.row(Y1 + k2 * DY);
		// paint:
		if (isStrongSelected() && g instanceof MainGraphics) {
			((MainGraphics) g).drawMarkerLine(c1, r1, c2, r2);
		}
		g.setColor(this);
		if (visible(zc)) {
			if (tracked())
				zc.UniversalTrack.drawTrackLine(this, c1, r1, c2, r2);
			g.drawLine(c1, r1, c2, r2, this);
		}
		final String s = getDisplayText();
		if (!s.equals("")) {
			g.setLabelColor(this);
			DisplaysText = true;
			double c = -b + a / 5;
			if (c < -a / 5)
				c = -a / 5;
			else if (c > a / 5)
				c = a / 5;
			if (c < -b + a / 10)
				c = -b + a / 10;
			if (KeepClose) {
				final double side = (YcOffset < 0) ? 1 : -1;
				drawLabel(g, s, zc, X1 + XcOffset * (X2 - X1), Y1 + XcOffset
						* (Y2 - Y1), side * DX, side * DY, 0, 0);
			} else
				drawLabel(g, s, zc, x + c * DX, y + c * DY, DX, DY, XcOffset,
						YcOffset);
		}
	}

	@Override
	public boolean canKeepClose() {
		return true;
	}

	@Override
	public void setKeepClose(final double x, final double y) {
		KeepClose = true;
		XcOffset = (x - X1) / R * DX + (y - Y1) / R * DY;
		YcOffset = (x - X1) / R * DY - (y - Y1) / R * DX;
	}

	@Override
	public String getDisplayValue() {

		return Global.getLocaleNumber(R, "lengths");
	}

	//
	// public String getDisplayValue ()
	// { return ""+round(R,ZirkelCanvas.LengthsFactor);
	// }

	@Override
	public boolean nearto(final int c, final int r, final ZirkelCanvas zc) {
		if (!displays(zc))
			return false;
		// compute point at c,r
		final double x = zc.x(c), y = zc.y(r);
		// compute distance from line
		double d = (x - X1) * DY - (y - Y1) * DX;
		// compute offset
		final double o = (x - X1) * DX + (y - Y1) * DY, o1 = (X2 - X1) * DX
		+ (Y2 - Y1) * DY;
		if (o1 > 0) {
			if (o < 0)
				d = Math.sqrt((x - X1) * (x - X1) + (y - Y1) * (y - Y1));
		} else {
			if (o > 0)
				d = Math.sqrt((x - X1) * (x - X1) + (y - Y1) * (y - Y1));
		}
		// test, if on visible part
		final double s = project(x, y);
		if (s < k1 || s > k2)
			return false;
		// scale in screen coordinates
		Value = Math.abs(zc.col(zc.minX() + d) - zc.col(zc.minX()));
		return Value < zc.selectionSize() * 2;
	}

	@Override
	public void printArgs(final XmlWriter xml) {
		xml.printArg("from", P1.getName());
		xml.printArg("to", P2.getName());
		super.printArgs(xml);
	}

	@Override
	public boolean contains(final double x, final double y) {
		final double a = (x - X1) * DX + (y - Y1) * DY;
		if (a < 1e-9)
			return false;
		return true;
	}

	@Override
	public double project(final double x, final double y) {
		final double h = super.project(x, y);
		if (h < 0)
			return 0;
		return h;
	}

	@Override
	public boolean equals(final ConstructionObject o) {
		if (!(o instanceof RayObject) || !o.valid())
			return false;
		final RayObject l = (RayObject) o;
		return equals(X1, l.X1) && equals(Y1, l.Y1) && equals(DX, l.DX)
		&& equals(DY, l.DY);
	}

	public static Coordinates intersect(final PrimitiveLineObject l1,
			final PrimitiveLineObject l2)
	// compute the intersection coordinates of two lines
	{
		final double det = -l1.DX * l2.DY + l1.DY * l2.DX;
		if (Math.abs(det) < 1e-10)
			return null;
		final double a = (-(l2.X1 - l1.X1) * l2.DY + (l2.Y1 - l1.Y1) * l2.DX)
		/ det;
		return new Coordinates(l1.X1 + a * l1.DX, l1.Y1 + a * l1.DY);
	}

	public static Coordinates intersect(final PrimitiveLineObject l,
			final PrimitiveCircleObject c)
	// compute the intersection coordinates of a line with a circle
	{
		double x = c.getX(), y = c.getY();
		final double r = c.getR();
		final double d = (x - l.X1) * l.DY - (y - l.Y1) * l.DX;
		if (Math.abs(d) > r + 1e-10)
			return null;
		x -= d * l.DY;
		y += d * l.DX;
		double h = r * r - d * d;
		if (h > 0)
			h = Math.sqrt(h);
		else
			h = 0;
		return new Coordinates(x + h * l.DX, y + h * l.DY, x - h * l.DX, y - h
				* l.DY);
	}

}
