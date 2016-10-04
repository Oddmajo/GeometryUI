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
import java.awt.Rectangle;
import java.util.Enumeration;
import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.expression.InvalidException;
import rene.zirkel.graphics.MainGraphics;
import rene.zirkel.graphics.MyGraphics;


/**
 * @author Rene Class for segments, derived from LineObject, TwoPointLineObject.
 *         Segments override various methods from lines. They have a length.
 *         Also the length can be fixed.
 */
public class SegmentObject extends TwoPointLineObject {

	static Count N = new Count();
	protected boolean Is3D; //Dibs : 3D ?
	protected double X3D1, Y3D1, Z3D1, X3D2, Y3D2, Z3D2, DX3D, DY3D, DZ3D; //Dibs
	protected boolean Fixed = false; // fixed length?
	protected boolean Fixed3D = false; // fixed length?
	Expression E; // expression to fix the length.
	Expression E3D;
	boolean ExpressionFailed; // invalid expression?
	public boolean Arrow; // draw as arrow.
	int code_symbol = 0;

	public SegmentObject(final Construction c, final PointObject p1,
			final PointObject p2) {
		super(c, p1, p2);
		if (p1.is3D()&&p2.is3D()) Is3D=true;
		Arrow = false;
		validate();
		updateText();
		Unit = Global.getParameter("unit.length", "");
	}

	@Override
        public String getCDPDisplayValue(){
	    	return Global.getLocaleNumber(R, "lengths");
        }

	@Override
	public String getTag() {
		return "Segment";
	}

	@Override
	public int getN() {
		return N.next();
	}

	// public void setDefaults ()
	// { Arrow=Cn.Vectors;
	// super.setDefaults();
	// }
	@Override
	public void setDefaults() {
		//Arrow = Cn.Vectors; //plus nécessaire
		setShowName(Global.getParameter("options.segment.shownames", false));
		setShowValue(Global.getParameter("options.segment.showvalues", false));
		setColor(Global.getParameter("options.segment.color", 0), Global
				.getParameter("options.segment.pcolor", (ExpressionColor) null, this));
		setColorType(Global.getParameter("options.segment.colortype", 0));
		setHidden(Cn.Hidden);
		setObtuse(Cn.Obtuse);
		setSolid(Cn.Solid);
		setLarge(Global.getParameter("options.segment.large", false));
		setBold(Global.getParameter("options.segment.bold", false));
	}

	@Override
	public void setTargetDefaults() {
		setShowName(Global.getParameter("options.segment.shownames", false));
		setShowValue(Global.getParameter("options.segment.showvalues", false));
		setColor(Global.getParameter("options.segment.color", 0), Global
				.getParameter("options.segment.pcolor", (ExpressionColor) null, this));
		setColorType(Global.getParameter("options.segment.colortype", 0));
		setLarge(Global.getParameter("options.segment.large", false));
		setBold(Global.getParameter("options.segment.bold", false));
	}

	@Override
	public void updateText() {
		if (!Fixed) {
			setText(text2(Global.name("text.segment"), P1.getName(), P2
					.getName()));
		} else {
			if (E == null) {
				setText(text3(Global.name("text.segment.fixed"), P1.getName(),
						P2.getName(), "" + round(R)));
			} else {
				setText(text3(Global.name("text.segment.fixed"), P1.getName(),
						P2.getName(), "\"" + E.toString() + "\""));
			}
		}
	}

	@Override
	public void validate() {
		ExpressionFailed = false;
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
			// System.out.println("X1="+X1+" Y1="+Y1+" | X2="+X2+" Y2="+Y2+" R="+R);
			if (P1.is3D()&&P2.is3D()) Is3D=true; // Dibs last updated
			if (Is3D) {					//Dibs
				X3D1 = P1.getX3D();
				Y3D1 = P1.getY3D();
				Z3D1 = P1.getZ3D();
				X3D2 = P2.getX3D();
				Y3D2 = P2.getY3D();
				Z3D2 = P2.getZ3D();
				DX3D = X3D2-X3D1;
				DY3D = Y3D2-Y3D1;
				DZ3D = Z3D2-Z3D1;
				R3D = Math.sqrt(DX3D * DX3D + DY3D * DY3D + DZ3D * DZ3D);
			}

			// if fixed, move the moveable endpoint.
			if (Fixed && E != null&&!is3D()) {
				try {
					final double FixedR = E.getValue();
					// System.out.println(R+" "+FixedR);
					if (FixedR < 1e-8) {
						R = 0;
						ExpressionFailed = true;
						Valid = false;
						return;
					}
					boolean movefirst = P1.moveableBy(this), movesecond = P2
					.moveableBy(this);
					if (P2.getBound() != null) {
						final ConstructionObject bound = P2.getBound();
						if (bound instanceof RayObject) {
							if (((RayObject) bound).getP1() == P1) {
								movesecond = true;
							}
						}
					} else if (P1.getBound() != null) {
						final ConstructionObject bound = P1.getBound();
						if (bound instanceof RayObject) {
							if (((RayObject) bound).getP1() == P2) {
								movefirst = true;
								movesecond = false;
							}
						}
					}
					if (movesecond) {
						if (R < 1e-10) {
							P2.move(X1 + FixedR, Y1);
						} else {
							P2.move(X1 + FixedR * DX / R, Y1 + FixedR * DY / R);
						}
						P1.setUseAlpha(false);
						// System.out.println("Move "+P2.getName());
					} else if (movefirst) {
						if (R < 1e-10) {
							P1.move(X2 - FixedR, Y2);
						} else {
							P1.move(X2 - FixedR * DX / R, Y2 - FixedR * DY / R);
						}
						P2.setUseAlpha(false);
						// System.out.println("Move "+P1.getName());
					} else {
						Fixed = false; // no moveable endpoint!
					}
					if (Fixed) {
						X1 = P1.getX();
						Y1 = P1.getY();
						X2 = P2.getX();
						Y2 = P2.getY();
						DX = X2 - X1;
						DY = Y2 - Y1;
						R = Math.sqrt(DX * DX + DY * DY);
						P2.movedBy(this);
						P1.movedBy(this);
					}
				} catch (final Exception e) {
					ExpressionFailed = true;
					Valid = false;
					R = 0;
					R3D=0;
					return;
				}
			}
			else if (Fixed3D && E3D != null) { // pour plus tard...
				try {
					final double FixedR3D = E3D.getValue();
					if (FixedR3D < 1e-8) {
						R3D = 0;
						ExpressionFailed = true;
						Valid = false;
						return;
					}
					boolean movefirst = P1.moveableBy(this), movesecond = P2
					.moveableBy(this);
					if (P2.getBound() != null) {
						final ConstructionObject bound = P2.getBound();
						if (bound instanceof RayObject) {
							if (((RayObject) bound).getP1() == P1) {
								movesecond = true;
							}
						}
					} else if (P1.getBound() != null) {
						final ConstructionObject bound = P1.getBound();
						if (bound instanceof RayObject) {
							if (((RayObject) bound).getP1() == P2) {
								movefirst = true;
								movesecond = false;
							}
						}
					}
					if (movesecond) {
						if (R3D < 1e-10) {
							P2.move3D(X3D1 + FixedR3D, Y3D1, Z3D1);
						} else {
							P2.move3D(X3D1 + FixedR3D * DX3D / R3D, Y3D1 + FixedR3D * DY3D / R3D, Z3D1 + FixedR3D * DZ3D / R3D);
						}
						P1.setUseAlpha(false);
						// System.out.println("Move "+P2.getName());
					} else if (movefirst) {
						if (R < 1e-10) {
							P1.move3D(X3D2 - FixedR3D, Y3D2, Z3D2);
						} else {
							P1.move3D(X3D2 - FixedR3D * DX3D / R3D, Y3D2 - FixedR3D * DY3D / R3D, Z3D2 - FixedR3D * DZ3D / R3D);
						}
						P2.setUseAlpha(false);
						// System.out.println("Move "+P1.getName());
					} else {
						Fixed3D = false; // no moveable endpoint!
					}
					if (Fixed3D) {
						X3D1 = P1.getX3D();
						Y3D1 = P1.getY3D();
						Z3D1 = P1.getZ3D();
						X3D2 = P2.getX3D();
						Y3D2 = P2.getY3D();
						Z3D2 = P2.getZ3D();
						DX3D = X3D2-X3D1;
						DY3D = Y3D2-Y3D1;
						DZ3D = Z3D2-Z3D1;
						R3D = Math.sqrt(DX3D * DX3D + DY3D * DY3D + DZ3D * DZ3D);
						P2.movedBy(this);
						P1.movedBy(this);
					}
				} catch (final Exception e) {
					ExpressionFailed = true;
					Valid = false;
					R = 0;
					R3D=0;
					return;
				}
			}
			// See of the length is too small.
			if (R < 1e-10) {
				R = 0;
				DX = 1;
				DY = 0;
			} else {
				DX /= R;
				DY /= R;
			}
			if (R3D < 1e-10) {
				R3D = 0;
				DX3D = 1;
				DY3D = 0;
				DZ3D=0;
			} else {
				DX3D /= R3D;
				DY3D /= R3D;
			}
		}
	}

	@Override
	public void paint(final MyGraphics g, final ZirkelCanvas zc) {
		if (!Valid || mustHide(zc)) {
			return;
		}
		final double c1 = zc.col(X1), r1 = zc.row(Y1), c2 = zc.col(X2), r2 = zc
		.row(Y2);
		if (visible(zc)) {
			if (isStrongSelected() && g instanceof MainGraphics) {
				((MainGraphics) g).drawMarkerLine(c1, r1, c2, r2);
			}
			g.setColor(this);
			if (tracked()) {
				zc.UniversalTrack.drawTrackLine(this, c1, r1, c2, r2);
			}
			g.drawLine(c1, r1, c2, r2, this);

			if (code_symbol > 0) {
				// length of the tick mark :
				final double rr = 7*Cn.getOne();
				// distance between two marks :
				final double dd = 3*Cn.getOne();
				// oblique de la marque :
				final double ob = 2*Cn.getOne();

				final double cM = (c1 + c2) / 2, rM = (r1 + r2) / 2;
				final double A = c2 - cM, B = r2 - rM;
				final double sqrt2 = Math.sqrt(B * B + A * A);
				final double xx1 = -(rr * B) / sqrt2 + cM - ob * A / sqrt2;
				final double yy1 = (rr * A) / sqrt2 + rM - ob * B / sqrt2;
				final double xx2 = (rr * B) / sqrt2 + cM + ob * A / sqrt2;
				final double yy2 = -(rr * A) / sqrt2 + rM + ob * B / sqrt2;
				final double xt = dd * A / sqrt2, yt = dd * B / sqrt2;
				switch (code_symbol) {
				case 1:
					g.drawLine(xx1, yy1, xx2, yy2, this);
					break;
				case 2:
					g.drawLine(xx1 - xt, yy1 - yt, xx2 - xt, yy2 - yt, this);
					g.drawLine(xx1 + xt, yy1 + yt, xx2 + xt, yy2 + yt, this);
					break;
				case 3:
					g.drawLine(xx1 - 2 * xt, yy1 - 2 * yt, xx2 - 2 * xt, yy2
							- 2 * yt, this);
					g.drawLine(xx1, yy1, xx2, yy2, this);
					g.drawLine(xx1 + 2 * xt, yy1 + 2 * yt, xx2 + 2 * xt, yy2
							+ 2 * yt, this);
					break;
				case 4:
					g.drawLine(xx1 - 3 * xt, yy1 - 3 * yt, xx2 - 3 * xt, yy2
							- 3 * yt, this);
					g.drawLine(xx1 - xt, yy1 - yt, xx2 - xt, yy2 - yt, this);
					g.drawLine(xx1 + xt, yy1 + yt, xx2 + xt, yy2 + yt, this);
					g.drawLine(xx1 + 3 * xt, yy1 + 3 * yt, xx2 + 3 * xt, yy2
							+ 3 * yt, this);
					break;
				case 5:
					g.drawLine(xx1 - 2 * xt, yy1 - 2 * yt, xx2 + 2 * xt, yy2
							+ 2 * yt, this);
					g.drawLine(xx1 + 2 * xt, yy1 + 2 * yt, xx2 - 2 * xt, yy2
							- 2 * yt, this);
					break;
				case 6:
					g.drawCircle(cM, rM, 2 * dd, this);
					break;
				}
			}

			if (Arrow) // draw as arrow!
			{
				final double a = Math.PI * 0.9;
				final double r = zc.dx(Cn.getOne()*Global.getParameter(
						"arrowsize", 15)); // 10 pixel on the screen
				final double[] cols = new double[3];
				cols[0] = c2;
				cols[1] = zc
				.col(X2 + (DX * Math.cos(a) + DY * Math.sin(a)) * r);
				cols[2] = zc.col(X2 + (DX * Math.cos(-a) + DY * Math.sin(-a))
						* r);
				final double[] rows = new double[3];
				rows[0] = r2;
				rows[1] = zc.row(Y2 + (-DX * Math.sin(a) + DY * Math.cos(a))
						* r);
				rows[2] = zc.row(Y2 + (-DX * Math.sin(-a) + DY * Math.cos(-a))
						* r);
				g.fillPolygon(cols, rows, 3, true, false, this);
			}
		}
		final String s = getDisplayText();
		if (!s.equals("")) {
			g.setLabelColor(this);
			setFont(g);
			DisplaysText = true;
			if (KeepClose) {
				final double side = (YcOffset < 0) ? 1 : -1;
				drawLabel(g, s, zc, X1 + XcOffset * (X2 - X1), Y1 + XcOffset
						* (Y2 - Y1), side * DX, side * DY, 0, 0);
			} else {
				drawLabel(g, s, zc, (X1 + X2) / 2, (Y1 + Y2) / 2, DX, DY,
						XcOffset, YcOffset);

			}
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

	// public String getDisplayValue ()
	// { return
	// eric.JGlobals.fixDecimal(""+round(R,ZirkelCanvas.LengthsFactor));
	// }
	
	@Override
	public String getDisplayValue() {
		// return ""+round(R,ZirkelCanvas.LengthsFactor);
		if (!Is3D) return Global.getLocaleNumber(R, "lengths");
	    else return Global.getLocaleNumber(R3D, "lengths");
	}

        @Override
    public boolean isInRect(Rectangle r, ZirkelCanvas zc){
        return ((r.contains(zc.col(P1.getX()),zc.row(P1.getY())))&&
                (r.contains(zc.col(P2.getX()),zc.row(P2.getY()))));
    }

	/**
	 * see, if a point is on the segment or near to it.
	 */
	@Override
	public boolean nearto(final int c, final int r, final ZirkelCanvas zc) {
		if (ExpressionFailed && P1.valid()) {
			return P1.nearto(c, r, zc);
		}
		if (ExpressionFailed && P2.valid()) {
			return P2.nearto(c, r, zc);
		}
		if (!displays(zc)) {
			return false;
		}
		// compute point at c,r
		final double x = zc.x(c), y = zc.y(r);
		// compute distance from line
		double d = (x - X1) * DY - (y - Y1) * DX;
		// compute offset
		final double o = (x - X1) * DX + (y - Y1) * DY, o1 = (X2 - X1) * DX
		+ (Y2 - Y1) * DY;
		if (o1 > 0) {
			if (o > o1) {
				d = Math.sqrt((x - X2) * (x - X2) + (y - Y2) * (y - Y2));
			} else if (o < 0) {
				d = Math.sqrt((x - X1) * (x - X1) + (y - Y1) * (y - Y1));
			}
		} else {
			if (o < o1) {
				d = Math.sqrt((x - X2) * (x - X2) + (y - Y2) * (y - Y2));
			} else if (o > 0) {
				d = Math.sqrt((x - X1) * (x - X1) + (y - Y1) * (y - Y1));
			}
		}
		// scale in screen coordinates
		Value = Math.abs(zc.col(zc.minX() + d) - zc.col(zc.minX())) * 0.9;
		return Value < zc.selectionSize();
	}

	/**
	 * true, if the segment is too small.
	 */
	@Override
	public boolean onlynearto(final int c, final int r, final ZirkelCanvas zc) {
		return R < zc.dx(3 *zc.pointSize());
	}

	@Override
	public void printArgs(final XmlWriter xml) {
		xml.printArg("from", P1.getName());
		xml.printArg("to", P2.getName());
		if (Is3D) {
			xml.printArg("is3D", "true");
		}
		if (Fixed && E != null) {
			xml.printArg("fixed", E.toString());
		}
//		if (Arrow) {
//			xml.printArg("arrow", "true");
//		} //plus nécessaire
		if (code_symbol > 0) {
			xml.printArg("code_symbol", "" + code_symbol);
		}
		super.printArgs(xml);
	}

	@Override
	public double getLength() {
		return R;
	}
	
	@Override
	public double getLength3D() {
		return R3D;
	}
	
	public boolean is3D() {
    	return Is3D;
    }


	@Override
	public boolean fixed() {
		return Fixed;
	}
	
	@Override
	public boolean fixed3D() {
		return Fixed3D;
	}

	@Override
	public void setFixed(final boolean flag, final String s)
	throws ConstructionException {
		if (!flag || s.equals("")) {
			Fixed = false;
			E = null;
		} else {
			E = new Expression(s, getConstruction(), this);
			if (!E.isValid()) {
				throw new ConstructionException(E.getErrorText());
			}
			Fixed = true;
		}
		updateText();
	}
	
	@Override
	public void setFixed3D(final boolean flag, final String s)
	throws ConstructionException {
		if (!flag || s.equals("")) {
			Fixed3D = false;
			E3D = null;
		} else {
			E3D = new Expression(s, getConstruction(), this);
			if (!E3D.isValid()) {
				throw new ConstructionException(E.getErrorText());
			}
			Fixed3D = true;
		}
		updateText();
	}
	

	@Override
	public void round() {
		try {
			setFixed(true, getDisplayValue());
			validate();
		} catch (final Exception e) {
		}
	}

	/**
	 * @return Segment can be fixed in length.
	 */
	@Override
	public boolean canFix() {
		return P1.moveableBy(this) || P2.moveableBy(this);
	}

	@Override
	public boolean contains(final double x, final double y) {
		final double a = (x - X1) * DX + (y - Y1) * DY;
		if (a < -1e-9 || a > R + 1e-9) {
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
		if (h > R) {
			return R;
		}
		return h;
	}

	/**
	 * @return true, if equal.
	 */
	@Override
	public boolean equals(final ConstructionObject o) {
		if (!(o instanceof SegmentObject) || !o.valid()) {
			return false;
		}
		final SegmentObject l = (SegmentObject) o;
		return (equals(X1, l.X1) && equals(X2, l.X2) && equals(Y1, l.Y1) && equals(
				Y2, l.Y2))
				|| (equals(X1, l.X2) && equals(Y1, l.Y2) && equals(X2, l.X1) && equals(
						Y2, l.Y1));
	}



	public boolean isValidFix() {
		return E != null && E.isValid();
	}

	@Override
	public String getStringLength() {
		if (E != null) {
			return E.toString();
		} else {
			return "" + round(R);
		}
	}
	
	public String getStringLength3D() {
		if (E3D != null) {
			return E3D.toString();
		} else {
			return "" + round(R3D);
		}
	}

	public int getSegmentCode() {
		return code_symbol;
	}

	public void setSegmentCode(final int i) {
		code_symbol = i;
	}

	@Override
	public double getValue() throws ConstructionException {
		if (!Valid) {
			throw new InvalidException("exception.invalid");
		} else {
			if (!P1.is3D()||!P2.is3D()) return R;
			else return R3D;
		}
	}
	
	public double getValue3D() throws ConstructionException {
		if (!Valid) {
			throw new InvalidException("exception.invalid");
		} else {
			return R3D;
		}
	}

	@Override
	public void translate() {
		super.translate();
		try {
			setFixed(Fixed, E.toString());
			E.translate();
		} catch (final Exception e) {
			Fixed = false;
		}
	}

	@Override
	public Enumeration depending() {
		if (!Fixed &&!Fixed3D) {
			return super.depending();
		} else {
			if (E!= null)  {
			super.depending();
			final Enumeration e = E.getDepList().elements();
			while (e.hasMoreElements()) {
				DL.add((ConstructionObject) e.nextElement());
				}
			}
			if (E3D!=null) {
					final Enumeration f = E3D.getDepList().elements();
					while (f.hasMoreElements()) {
						DL.add((ConstructionObject) f.nextElement());
					}
				}
			return DL.elements();
		}
	}


	public void setArrow(final boolean arrow) {
		Arrow = arrow;
	}

	public boolean isArrow() {
		return Arrow;
	}

	@Override
	public void project(final PointObject P) {
		final double h = project(P.getX(), P.getY());
		P.setXY(getX() + h * getDX(), getY() + h * getDY());
		P.setA(h / getLength());
	}

	@Override
	public void project(final PointObject P, final double alpha) {
		final double d = alpha * getLength();
		P.setXY(getX() + d * getDX(), getY() + d * getDY());
	}

	@Override
	public boolean moveable() {
		if (!Fixed && P1.moveable() && P2.moveable()) {
			return true;
		}
		return false;
	}
        
        @Override
        public void setFixed(final boolean bool) {
            if (bool) {
                E = new Expression(this.getStringLength(), getConstruction(), this);
            }
            Fixed=bool;
        }
}
