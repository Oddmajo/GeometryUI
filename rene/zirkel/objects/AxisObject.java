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

import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.graphics.MyGraphics;

public class AxisObject extends PrimitiveLineObject {

	public AxisObject(final Construction c, final boolean xaxis) {
		super(c, (xaxis) ? "xAxis" : "yAxis");

		X1 = 0;
		Y1 = 0;
		if (xaxis) {
			DX = 1;
			DY = 0;

		} else {
			DX = 0;
			DY = 1;
		}
		updateText();
	}

	@Override
	public void setName() {

		// if (DX==1) {
		// Name="xaxis"+getN();
		// } else {
		// Name="yaxis"+getN();
		// }
		//

	}

	@Override
	public boolean nearto(final int c, final int r, final ZirkelCanvas zc) {
		if (!displays(zc)) {
			return false;
		}
		if (!zc.getAxis_show()) {
			return false;
		}
		// compute point at c,r
		final double x = zc.x(c), y = zc.y(r);
		// compute distance from x,y
		final double d = (x - X1) * DY - (y - Y1) * DX;
		// scale in screen coordinates
		Value = Math.abs(zc.col(zc.minX() + d) - zc.col(zc.minX()));
		return Value < zc.selectionSize() * 2;
	}

	// public static Coordinates intersect(PrimitiveLineObject l, FunctionObject
	// q) {
	//
	//
	//
	// return new Coordinates(0, 0);
	// }

	// public String getTag() {
	// return "Axis";
	// }
	@Override
	public void printArgs(final XmlWriter xml) {
		if (DX == 1) {
			xml.printArg("xaxis", "true");
		} else {
			xml.printArg("yaxis", "true");
		}
	}

	@Override
	public Enumeration depending() {
		DL.reset();

		return DL.elements();
	}

	public double getLength() {
		return 0;
	}

	@Override
	public void translate() {
		P1 = (PointObject) P1.getTranslation();
	}

	@Override
	public boolean contains(final PointObject p) {
		return false;
	}

	@Override
	public void paint(final MyGraphics g, final ZirkelCanvas zc) {
		if (!Valid || mustHide(zc)) {
			return;
		}

		// if (isStrongSelected()&&g instanceof MyGraphics13) {
		// ((MyGraphics13)
		// g).drawMarkerLine(zc.col(-5),zc.row(1),zc.col(5),zc.row(1));
		// }

		if (zc.getAxis_show()) {
			if (indicated()) {
				g.setColor(this);
				if (DX == 1) {
					g.drawLine(0, zc.row(0), zc.IW, zc.row(0), this);
				} else {
					g.drawLine(zc.col(0), 0, zc.col(0), zc.IH, this);
				}
			}
		}

	}

	@Override
	public void updateText() {
		if (DX == 1) {
			setText("X axis");
		} else {
			setText("Y axis");
		}
	}

	public void dragTo(final double x, final double y) {

	}

	@Override
	public void move(final double x, final double y) {
	}

	public boolean moveable() {
		return false;
	}

	double x1, y1, x2, y2, x3, y3;

	public void startDrag(final double x, final double y) {

	}

	@Override
	public void snap(final ZirkelCanvas zc) {

	}

	public double getOldX() {
		return 0;
	}

	public double getOldY() {
		return 0;
	}
}
