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

// file: Functionbject.java
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.expression.InvalidException;
import rene.zirkel.graphics.FunctionPolygonFiller;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.PolygonDrawer;
import rene.zirkel.structures.Coordinates;


/**
 * @author Rene
 *
 *         Function objects are parametric or cartesian curves. For parametric
 *         functions, depending on a parameter t , x(t) and y(t) are computed
 *         and drawn on the screen. For cartesian functions, the parameter x
 *         will be used.
 *
 */
public class FunctionObject extends ConstructionObject implements
PointonObject, HeavyObject, DriverObject, Evaluator {

    Expression EX = null, EY = null;
    public Expression VarMin = null, VarMax = null, DVar = null;
    String LASTEX = "", LASTEY = "", LASTVarMin = "", LASTVarMax = "", LASTDVar = "";
    double X[] = { 0 };
    public String Var[] = { "x" };
    boolean Filled = false;
    Expression Center = null;
    protected int Type = 0;
    public final static int SQUARE = 0, DIAMOND = 1, CIRCLE = 2, DOT = 3,
    CROSS = 4, DCROSS = 5;
    protected boolean Special = false;
    // Vector RefreshList=new Vector();
    public Vector V = new Vector();
    double cx, cy, ww, wh;
    // this is a main object list which tells if the object needs to recompute :
    Vector DriverObjectList = new Vector();
    double c0, r0, c, r;
    private boolean isMinClosed, isMinOpen, isMaxClosed, isMaxOpen;

    /**
     * Functions are HeavyObjects which means we always must see if it really
     * neads to recompute. Computing a function can be time consuming, but once
     * it's computed, the paint method is fast enough.
     *
     * @param c
     */
    public FunctionObject(final Construction c) {
    	super(c);
	VarMin = new Expression("windowcx-windoww", c, this);
	VarMax = new Expression("windowcx+windoww", c, this);
	DVar = new Expression("0", c, this);
	validate();
	updateText();
	cx = c.getX();
	cy = c.getY();
	ww = c.getW();
	wh = c.getH();
	Type = CROSS;
    }

    @Override
    public void setDefaults() {
	setShowName(Global.getParameter("options.locus.shownames", false));
	setShowValue(Global.getParameter("options.locus.showvalues", false));
//		setColor(Global.getParameter("options.locus.color", 0), Global
//				.getParameter("options.locus.pcolor", (Color) null));
        setColor(Global.getParameter("options.locus.color", 0));
	setColorType(Global.getParameter("options.locus.colortype", 0));
	setFilled(Global.getParameter("options.locus.filled", false));
	setHidden(Cn.Hidden);
	setObtuse(Cn.Obtuse);
	setSolid(Cn.Solid);
	setLarge(Cn.LargeFont);
	setBold(Cn.BoldFont);
    }

    @Override
    public void setTargetDefaults() {
	setShowName(Global.getParameter("options.locus.shownames", false));
	setShowValue(Global.getParameter("options.locus.showvalues", false));
	setColor(Global.getParameter("options.locus.color", 0), Global.getParameter("options.locus.pcolor", (ExpressionColor) null, this));
	setColorType(Global.getParameter("options.locus.colortype", 0));
	setFilled(Global.getParameter("options.locus.filled", false));
	setHidden(Cn.Hidden);
	setObtuse(Cn.Obtuse);
	setSolid(Cn.Solid);
	setLarge(Cn.LargeFont);
	setBold(Cn.BoldFont);
    }

    /**
     * In order to see if the function must be recomputed or not, we have to
     * register, in the DriverObjectList, all objects the function depends on.
     * There are many possibilities for an object to be in this list : if it's
     * used in the def of the function, if it's in the min or max text box,
     * etc...
     *
     * @param c
     */
    @Override
    public void searchDependencies(final Construction c) {
	DriverObjectList.clear();
	if (RekValidating) {
	    return;
	} // should not happen!
	RekValidating = true;
	Enumeration e = c.elements();
	while (e.hasMoreElements()) {
	    ((ConstructionObject) e.nextElement()).setRekFlag(false);
	}
	final ConstructionObject oEX[] = EX.getDepList().getArray();
	final ConstructionObject oEY[] = EY.getDepList().getArray();
	final ConstructionObject oVarMin[] = VarMin.getDepList().getArray();
	final ConstructionObject oVarMax[] = VarMax.getDepList().getArray();
	final ConstructionObject oDVar[] = DVar.getDepList().getArray();

	for (final ConstructionObject element : oEX) {
	    recursiveSearchDependencies((ConstructionObject) element);
	}
	for (final ConstructionObject element : oEY) {
	    recursiveSearchDependencies((ConstructionObject) element);
	}
	for (final ConstructionObject element : oVarMin) {
	    recursiveSearchDependencies((ConstructionObject) element);
	}
	for (final ConstructionObject element : oVarMax) {
	    recursiveSearchDependencies((ConstructionObject) element);
	}
	for (final ConstructionObject element : oDVar) {
	    recursiveSearchDependencies((ConstructionObject) element);
	}

	e = c.elements();
	while (e.hasMoreElements()) {
	    final ConstructionObject oc = (ConstructionObject) e.nextElement();
	    if ((oc.isRekFlag()) && (oc.isDriverObject())) {
		DriverObjectList.addElement(oc);
	    }
	}
	RekValidating = false;
	NeedsRecompute = true;
    }

	/**
	 * Recursive method called by the searchDependencies method
	 *
	 * @param o
	 */
	public void recursiveSearchDependencies(final ConstructionObject o) {

		if (o.isRekFlag()) {
			return;
		}
		o.setRekFlag(true);
		final ConstructionObject d[] = o.getDepArray();
		for (final ConstructionObject element : d) {
			recursiveSearchDependencies(element);
		}
	}

	/**
	 * Time consuming method which is called only if it's really necessary :
	 */
    @Override
	public void compute() {
		// Empty the vector which contains the point set of the plot :
		V.clear();
		if (!Valid) {
			return;
		}

		// Initialisation of varmin, varmax and d (the step variable) :
		double varmin, varmax, d;
		try {
			varmin = VarMin.getValue();
			varmax = VarMax.getValue();
			d = DVar.getValue();
			if (varmin > varmax) {
				final double h = varmin;
				varmin = varmax;
				varmax = h;
			}
			if (d < 0) {
				d = -d;
			}

		} catch (final Exception e) {
			Valid = false;
			return;
		}

		// X[0] represents the 't' variable for parametric plots.
		// When you give X[0] a t value, EX.getValue() and EY.getValue()
		// automatically returns the x(t) and y(t) value.
		// If it's a cartesian function, X[0] represents the 'x' value
		// then EX.getValue() always returns x and EY.getValue() returns
		// the f(x) value.
		X[0] = varmin;

		// If the function may have discontinuity problems, computing is
		// slower, because it checks for each point if a segment will have
		// to be displayed or not :
		if (mayHaveDiscontinuityPb()) {
			if (d == 0) {
				try {
					// if the user leaves the step text box empty in the
					// properties bar, the step d will represents only one pixel
					// :
					d = new Expression("1/pixel", getConstruction(), this)
					.getValue();
				} catch (final Exception ex) {
				}
				// if the step d defined by user is too small, then correct it :
			} else if (d < (varmax - varmin) / 1000) {
				d = (varmax - varmin) / 1000;
			}

			double x1 = 0, y1 = 0, x2 = 0, y2 = 0, yM = 0, X0 = 0;
			final int nbsteps = (int) Math.round((varmax - varmin) / d) + 1;

			try {
				x1 = EX.getValue();
				y1 = EY.getValue();
				V.add(new Coordinates(x1, y1, false));
			} catch (final Exception ex) {
			}

			/*
			 * A(x1,y1=f(x1)) is the first point. B(x2,y2=f(x2)) is the second
			 * point. In order to know if a segment must join these two points,
			 * we compute the point M(xM,yM=f(xM)), where xM=(x1+x2)/2 :
			 */
			for (int i = 1; i < nbsteps; i++) {
				try {
					X0 = X[0];
					X[0] += d / 2;
					EX.getValue();
					yM = EY.getValue();
					X[0] += d / 2;
					x2 = EX.getValue();
					y2 = EY.getValue();

					// If A,M and B are on the same horizontal line
					// then segment must be drawn (true) :
					if ((y1 == yM) && (y2 == yM)) {
						V.add(new Coordinates(x2, y2, true));

						// if f(xM) is in open interval ]f(x1),f(x2)[ :
					} else if (((yM > y1) && (yM < y2))
							|| ((yM > y2) && (yM < y1))) {

						final double mm = Math.abs(y1 - yM) / Math.abs(y2 - yM);

						// This is a weird thing, but it seems to work in
						// lots of "basic" situations :
						// If the distance |yM-y1| (or |yM-y2|)
						// represents 10% of the distance |yM-y2| (or |yM-y1|)
						// then may be it's a discontinuity problem, so
						// don't draw the segment [AB] (false)
						if ((mm < 0.1) || (mm > 10)) {
							V.add(new Coordinates(x2, y2, false));
							// Otherwise draw the segment [AB] :
						} else {
							V.add(new Coordinates(x2, y2, true));
						}
						// if f(xM) is not in interval [f(x1),f(x2)], don't
						// draw the segment [AB] (false) :
					} else {
						V.add(new Coordinates(x2, y2, false));
					}

					x1 = x2;
					y1 = y2;
				} catch (final Exception ex) {
					try {
						X[0] = X0 + d;
						x1 = EX.getValue();
						y1 = EY.getValue();
						V.add(new Coordinates(x1, y1, false));
					} catch (final Exception ex1) {
					}
				}
			}
		} else {
			if (d == 0) {
				d = (varmax - varmin) / 100;
			} else if (d < (varmax - varmin) / 1000) {
				d = (varmax - varmin) / 1000;
			}
			final int nbsteps = (int) Math.round((varmax - varmin) / d) + 1;
			for (int i = 0; i < nbsteps; i++) {
				try {
					V.add(new Coordinates(EX.getValue(), EY.getValue()));
				} catch (final Exception ex) {
				}
				X[0] += d;
			}
		}
	}

	public void setNeedsToRecompte(final boolean n) {
		NeedsRecompute = n;
	}

	/**
	 * Check all objects the function depends on. If one of the have changed,
	 * this function return true.
	 *
	 * @return
	 */
    @Override
	public boolean needsToRecompute() {
		boolean needs = false;
		final Enumeration pl = DriverObjectList.elements();
		while (pl.hasMoreElements()) {
			final DriverObject oc = (DriverObject) pl.nextElement();
			if (oc.somethingChanged()) {
				// There is a "ClearList" which will be cleared
				// at the end of the Construction.computeHeavyObjects method :
				Global.addClearList(oc);
				needs = true;
			}
		}

		// Also needs to compute when user zoom or move figure in the window :
		if ((Cn.getX() != cx) || (Cn.getY() != cy) || (Cn.getW() != ww)
				|| (Cn.getH() != wh)) {
			cx = Cn.getX();
			cy = Cn.getY();
			ww = Cn.getW();
			wh = Cn.getH();
			needs = true;
		}

		if (NeedsRecompute) {
			NeedsRecompute = false;
			return true;
		}

		return needs;
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
	public String getTag() {
		return "Function";
	}

	@Override
	public int getN() {
		return N.next();
	}

	@Override
	public void updateText() {
		if (EX != null && EY != null) {
			setText(text2(Global.name("text.function"), EX.toString(), EY
					.toString()));
		} else {
			setText(text2(Global.name("text.function"), "", ""));
		}
	}

	@Override
	public void validate() {
		if (EX != null && EY != null) {
			Valid = EX.isValid() && EY.isValid() && VarMin.isValid()
			&& VarMax.isValid() && DVar.isValid();
		} else {
			Valid = false;
		}
	}

	public void setExpressions(final String t, String ex, final String ey) {

		final StringTokenizer tok = new StringTokenizer(t);
		Var = new String[tok.countTokens()];
		X = new double[tok.countTokens()];
		int i = 0;
		while (tok.hasMoreTokens()) {
			Var[i++] = tok.nextToken();
		}
		if (ex.equals("")) {
			ex = Var[0];
		}
		EX = new Expression(ex, getConstruction(), this, Var);
		EY = new Expression(ey, getConstruction(), this, Var);
		validate();
		searchDependencies(Cn);
	}

	public boolean isCartesian() {
		return EX.toString().equals("x");
	}

	public boolean mayHaveDiscontinuityPb() {
		final String Pbs[] = { "floor(", "ceil(", "tan(", "sign(" };
		// Continuity pbs are only checked for cartesian functions :
		if (EX.toString().equals("x")) {
			for (final String pb : Pbs) {
				if ((EY.toString().indexOf(pb) != -1)) {
					return true;
				}
			}
		}
		return false;
	}

	public void setRange(final String min, final String max, final String d) {
		VarMin = new Expression(min, getConstruction(), this);
		VarMax = new Expression(max, getConstruction(), this);
		DVar = new Expression(d, getConstruction(), this);
		searchDependencies(Cn);
	}

	@Override
	public String getEX() {
		if (EX != null) {
			return EX.toString();
		} else {
			return Var[0];
		}
	}

	@Override
	public String getEY() {
		if (EY != null) {
			return EY.toString();
		} else {
			return "0";
		}
	}

	FunctionPolygonFiller PF = null;
	double C1, C2;
	int C, R, W, H;

    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {
	if (!Valid || mustHide(zc)) {
	    return;
	}
	Coordinates C = null;
	Enumeration e = V.elements();
	double c, r;
	g.setColor(this);
        String s=getDisplayText();
	if (!s.equals("")) {
	    g.setLabelColor(this);
            setFont(g);
            int n = s.length();
            if(s.startsWith("$")){//crise de dollarophobie
		s= s.substring(1, n-1);
            }
            if(V.size()>5){
	    //coordonnées du point d'attache
            final Coordinates attache=(Coordinates) V.elementAt((int) Math.floor(V.size()/2+3));
            final Coordinates precedent=(Coordinates) V.elementAt((int) Math.floor(V.size()/2)+2);
            double Dx=attache.X-precedent.X;
            double Dy=attache.Y-precedent.Y;
            double norme=4*Math.sqrt(Dx*Dx+Dy*Dy);
            drawLabel(g, "$\\mathcal{C}_{"+s+"}$", zc, attache.X, attache.Y,0,0, Dy/norme, -Dx/norme);
            }
	}

	if (Special) {
	    if (Filled) {
		PF = new FunctionPolygonFiller(g, this, zc.getY(), zc.getY() + zc.getHeight());
		while (e.hasMoreElements()) {
		    C = (Coordinates) e.nextElement();
		    PF.add(zc.col(C.X), zc.row(C.Y));
		}
		PF.fillPolygon(zc.row(0));
		e = V.elements();
		g.setColor(this);
	    }

	    while (e.hasMoreElements()) {
		C = (Coordinates) e.nextElement();
		PointObject.drawPoint(g, zc, this, C.X, C.Y, Type);
	    }
	} else if (Tracked) {
	    zc.UniversalTrack.TrackIG.setColor(this);
	    zc.UniversalTrack.setActive(true);
	    final PolygonDrawer pd = new PolygonDrawer(false,g, this);
	    final PolygonDrawer pdt = new PolygonDrawer(false,zc.UniversalTrack.TrackIG, this);
	    if (e.hasMoreElements()) {
		C = (Coordinates) e.nextElement();
		c0 = zc.col(C.X);
		r0 = zc.row(C.Y);
		pd.startPolygon(c0, r0);
		pdt.startPolygon(c0, r0);
	    }
	    while (e.hasMoreElements()) {
		C = (Coordinates) e.nextElement();
		c = zc.col(C.X);
		r = zc.row(C.Y);
		if (Math.abs(pd.c() - c) < 1000 && Math.abs(pd.r() - r) < 1000) {
		    pd.drawTo(c, r);
		    pdt.drawTo(c, r);
		} else {
		    pd.finishPolygon();
		    pdt.finishPolygon();
		    pd.startPolygon(c, r);
		    pdt.startPolygon(c, r);
		}
	    }
	    pd.finishPolygon();
	    pdt.finishPolygon();
	} else {
	    if (Filled) {
		PF = new FunctionPolygonFiller(g, this, zc.getY(), zc.getY() + zc.getHeight());
		while (e.hasMoreElements()) {
		    C = (Coordinates) e.nextElement();
		    PF.add(zc.col(C.X), zc.row(C.Y));
		}
		PF.fillPolygon(zc.row(0));
		e = V.elements();
		g.setColor(this);
	    }
	    final PolygonDrawer pd = new PolygonDrawer(false,g, this);
		if (e.hasMoreElements()) {
		    C = (Coordinates) e.nextElement();
		    c0 = zc.col(C.X);
		    r0 = zc.row(C.Y);
		    pd.startPolygon(c0, r0);
		}
		while (e.hasMoreElements()) {
		    C = (Coordinates) e.nextElement();
		    c = zc.col(C.X);
		    r = zc.row(C.Y);
		    if (C.join) {
			pd.drawTo(c, r);
		    } else {
			pd.finishPolygon();
			pd.startPolygon(c, r);
		    }
		}
		pd.finishPolygon();
	}

	/***********************
	 * Painting the bounds *
	 ***********************/
	double w = 2*zc.pointSize()+2*zc.getOne(), x, y;

	if(!VarMin.toString().equals("windowcx-windoww")) {
	    final Coordinates coord0 = (Coordinates) V.firstElement();

	    if(isMinClosed) {
		x = zc.col(coord0.X) - zc.pointSize() - zc.getOne();
		y = zc.row(coord0.Y) - zc.pointSize() - zc.getOne();
		g.fillOval(x, y, w, w, this.getColor());
	    } else if(isMinOpen) {
		//final Coordinates coord1 = (Coordinates) V.elementAt(Math.min(0, V.size()));
                final Coordinates coord1 = (Coordinates) V.elementAt(V.size()==1?0:1);
		double dx = zc.col(coord0.X) - zc.col(coord1.X);
		double dy = zc.row(coord0.Y) - zc.row(coord1.Y);
		x = w/2*dx/(Math.sqrt(dx*dx+dy*dy)) + zc.col(coord0.X) - zc.pointSize() - zc.getOne();
		y = w/2*dy/(Math.sqrt(dx*dx+dy*dy)) + zc.row(coord0.Y) - zc.pointSize() - zc.getOne();

		double alpha = Math.atan2(coord0.Y-coord1.Y, coord0.X-coord1.X)*180/Math.PI;
		g.drawArc(x, y, w, w, alpha+90, 180);
	    }
	}
	if(!VarMax.toString().equals("windowcx+windoww")) {
	    final Coordinates coord0=(Coordinates) V.lastElement();

	    if(isMaxClosed) {
		w = 2*zc.pointSize()+2*zc.getOne();
		x = zc.col(coord0.X)-zc.pointSize()-zc.getOne();
		y = zc.row(coord0.Y)-zc.pointSize()-zc.getOne();
		g.fillOval(x, y, w, w, this.getColor());
	    } else if(isMaxOpen) {
		final Coordinates coord1 = (Coordinates) V.elementAt(Math.max(V.size()-2,0));
                double dx = zc.col(coord0.X) - zc.col(coord1.X);
		double dy = zc.row(coord0.Y) - zc.row(coord1.Y);
		x = w/2*dx/(Math.sqrt(dx*dx+dy*dy)) + zc.col(coord0.X) - zc.pointSize() - zc.getOne();
		y = w/2*dy/(Math.sqrt(dx*dx+dy*dy)) + zc.row(coord0.Y) - zc.pointSize() - zc.getOne();

		double alpha = Math.atan2(coord0.Y-coord1.Y, coord0.X-coord1.X)*180/Math.PI;
		g.drawArc(x, y, w, w, alpha+90, 180);
	    }
	}
    }

	@Override
	public double getValue() throws ConstructionException {
		if (!Valid) {
			throw new InvalidException("exception.invalid");
		}
		return X[0];
	}

	public double getValue(final String var) throws ConstructionException {
		if (!Valid) {
			throw new InvalidException("exception.invalid");
		}
		for (int i = 0; i < Var.length; i++) {
			if (var.equals(Var[i])) {
				return X[i];
			}
		}
		return X[0];
	}

	public double getIntegral() throws ConstructionException {
		return getSum();
	}

	@Override
	public String getDisplayValue() {
		if (getEX().equals(getVar())) {
			return EY.toString();
		} else {
			return "(" + EX.toString() + "," + EY.toString() + ")";
		}
	}

	// Mainly to select the track for delete
	@Override
	public boolean nearto(final int x, final int y, final ZirkelCanvas zc) {
		if (!displays(zc)) {
			return false;
		}
		final Enumeration e = V.elements();
		final double xx = zc.x(x), yy = zc.y(y);
		final double mymax = (7 / Cn.getPixel());
		if (Special) {
			Coordinates CS;
			while (e.hasMoreElements()) {
				CS = (Coordinates) e.nextElement();
				if ((Math.abs(CS.X - xx) < mymax)
						&& (Math.abs(CS.Y - yy) < mymax)) {
					return true;
				}
			}
		} else {
			double xA = 0, yA = 0, xB = 0, yB = 0;
			Coordinates CS0, CS1;
			if (e.hasMoreElements()) {
				CS0 = (Coordinates) e.nextElement();
				xA = CS0.X;
				yA = CS0.Y;
			}
			while (e.hasMoreElements()) {
				CS1 = (Coordinates) e.nextElement();
				xB = CS1.X;
				yB = CS1.Y;

				final double p1 = (xx - xA) * (xB - xA) + (yy - yA) * (yB - yA);
				final double p2 = (xx - xB) * (xA - xB) + (yy - yB) * (yA - yB);
				if ((p1 > 0) && (p2 > 0)) {
					final double aa = xB - xA, bb = yB - yA, cc = bb * xA - aa
					* yA;
					final double d = Math.abs(-bb * xx + aa * yy + cc)
					/ Math.sqrt(aa * aa + bb * bb);
					if (d < mymax) {
						return true;
					}
				}
				xA = xB;
				yA = yB;
			}
		}

		return false;
	}

	public boolean EditAborted;



    @Override
    public void printArgs(final XmlWriter xml) {
	xml.printArg("x", EX.toString());
	xml.printArg("y", EY.toString());
	xml.printArg("var", getVar());
	xml.printArg("min", "" + VarMin);
	xml.printArg("max", "" + VarMax);
	xml.printArg("d", "" + DVar);
	if (Special) {
	    xml.printArg("special", "true");
	}
	printType(xml);
	if (Filled) {
	    xml.printArg("filled", "true");
	}
	if (getCenter() != null) {
	    xml.printArg("center", getCenter().getName());
	}
        xml.printArg("color", ""+getColorIndex());
	if(isMinOpen || isMinClosed) {
	    xml.printArg("minbound", isMinOpen?"0":"1"); //0 = open ; 1 = closed
	}
	if(isMaxOpen || isMaxClosed) {
	    xml.printArg("maxbound", isMaxOpen?"0":"1");
	}
    }

	@Override
	public void setType(final int type) {
		Type = type;
	}

	@Override
	public int getType() {
		return Type;
	}

	public void printType(final XmlWriter xml) {
		if (Type != 0) {
			switch (Type) {
			case DIAMOND:
				xml.printArg("shape", "diamond");
				break;
			case CIRCLE:
				xml.printArg("shape", "circle");
				break;
			case DOT:
				xml.printArg("shape", "dot");
				break;
			case CROSS:
				xml.printArg("shape", "cross");
				break;
			case DCROSS:
				xml.printArg("shape", "dcross");
				break;
			}
		}

	}

	@Override
	public ConstructionObject copy(final double x, final double y) {

		final FunctionObject fo = new FunctionObject(getConstruction());
		fo.copyProperties(this);
		fo.setFilled(this.isFilled()); //bug fix concerning filled curves in macro constructions

		fo.EX = new Expression(EX.toString(), getConstruction(), fo, Var);
		fo.EY = new Expression(EY.toString(), getConstruction(), fo, Var);
		fo.VarMin = new Expression(VarMin.toString(), getConstruction(), fo);
		fo.VarMax = new Expression(VarMax.toString(), getConstruction(), fo);
		fo.DVar = new Expression(DVar.toString(), getConstruction(), fo);
		fo.Special = Special;
		final ConstructionObject O = getTranslation();
		fo.setTranslation(this);
		fo.EX.translate();
		fo.EY.translate();
		fo.VarMin.translate();
		fo.VarMax.translate();
		fo.DVar.translate();
		fo.translateConditionals();
		fo.X = X;
		fo.Var = Var;
		fo.validate();
		fo.setTranslation(O);
		fo.searchDependencies(Cn.getTranslation());
		return fo;
	}

	@Override
	public boolean onlynearto(final int x, final int y, final ZirkelCanvas zc) {
		return false;
		// return nearto(x,y,zc);
	}

	@Override
	public boolean equals(final ConstructionObject o) {
		return false;
	}

	@Override
	public Enumeration depending() {
		DL.reset();
		addDepending(EX);
		addDepending(EY);
		addDepending(VarMin);
		addDepending(VarMax);
		addDepending(DVar);
		return DL.elements();
	}

	public void addDepending(final Expression E) {
		if (E != null) {
			final Enumeration e = E.getDepList().elements();
			while (e.hasMoreElements()) {
				DL.add((ConstructionObject) e.nextElement());
			}
		}
	}

	@Override
	public boolean hasUnit() {
		return false;
	}

	public double evaluateF(final double x[]) throws ConstructionException {
		int n = x.length;
		if (n > X.length) {
			n = X.length;
		}
		for (int i = 0; i < n; i++) {
			X[i] = x[i];
		}
		for (int i = n; i < X.length; i++) {
			X[i] = 0;
		}
		try {
			return EY.getValue();
		} catch (final Exception e) {
			throw new ConstructionException("");
		}
	}

    @Override
	public double evaluateF(final double x) throws ConstructionException {
		X[0] = x;
		for (int i = 1; i < X.length; i++) {
			X[i] = 0;
		}
		try {
			return EY.getValue();
		} catch (final Exception e) {

			throw new ConstructionException("");
		}
	}

	@Override
	public int getDistance(final PointObject P) {
		double varmin, varmax, dvar;
		try {
			varmin = VarMin.getValue();
			varmax = VarMax.getValue();
			dvar = DVar.getValue();
			if (varmin > varmax) {
				final double h = varmin;
				varmin = varmax;
				varmax = h;
			}

			if (dvar < 0) {
				dvar = -dvar;
			}
			if (dvar == 0) {
				dvar = (varmax - varmin) / 100;
			} else if (dvar < (varmax - varmin) / 1000) {
				dvar = (varmax - varmin) / 1000;
			}

		} catch (final Exception e) {
			Valid = false;
			return Integer.MAX_VALUE;
		}

		try {
			// if it's a cartesian function, try to calculate the "real" coords
			// :
			if ((getEX().equals("x"))) {
				double x = (P.getX() < varmin) ? varmin : P.getX();
				x = (P.getX() > varmax) ? varmax : x;
				final double y = evaluateF(x);
				final double dd = Math.sqrt((P.getX() - x) * (P.getX() - x)
						+ (P.getY() - y) * (P.getY() - y));
				return (int) Math.round(dd * Cn.getPixel());
			}
		} catch (final Exception e) {
		}

		try {
			// if it's a parmetric curve and function is just plot with points :
			if ((!getEX().equals("x")) && (Special)) {
				if (P.haveBoundOrder()) {
					X[0] = P.getBoundOrder();
					final double dd = Math.sqrt((P.getX() - EX.getValue())
							* (P.getX() - EX.getValue())
							+ (P.getY() - EY.getValue())
							* (P.getY() - EY.getValue()));
					return (int) Math.round(dd * Cn.getPixel());
				} else {
					Coordinates CS;
					final Enumeration e = V.elements();
					double delta0 = 0, delta1 = 0, xx = 0, yy = 0;
					int i = 0;
					if (e.hasMoreElements()) {
						CS = (Coordinates) e.nextElement();
						delta0 = Math.abs(CS.X - P.getX())
						+ Math.abs(CS.Y - P.getY());
						xx = CS.X;
						yy = CS.Y;
					}

					while (e.hasMoreElements()) {
						i++;
						CS = (Coordinates) e.nextElement();
						delta1 = Math.abs(CS.X - P.getX())
						+ Math.abs(CS.Y - P.getY());
						if (delta1 < delta0) {
							delta0 = delta1;
							xx = CS.X;
							yy = CS.Y;

						}
					}
					final double dd = Math.sqrt((P.getX() - xx)
							* (P.getX() - xx) + (P.getY() - yy)
							* (P.getY() - yy));
					return (int) Math.round(dd * Cn.getPixel());

				}

			}

		} catch (final Exception e) {
			Valid = false;
			return Integer.MAX_VALUE;
		}

		// Otherwise, at Least get the approx coordinates on the polygon :
		if (needsToRecompute()) {
			compute();
		}
		final Enumeration e = V.elements();
		double x = 0, y = 0, x0 = 0, y0 = 0, dmin = 0;
		boolean started = false;
		while (e.hasMoreElements()) {
			final Coordinates c = (Coordinates) e.nextElement();
			final double x1 = c.X;
			final double y1 = c.Y;
			if (!started) {
				dmin = Math.sqrt((P.getX() - x1) * (P.getX() - x1)
						+ (P.getY() - y1) * (P.getY() - y1));
				x0 = x = x1;
				y0 = y = y1;
				started = true;
			} else {
				if (c.flag) {
					double h = (x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0);
					if (h < 1e-10) {
						h = 1e-10;
					}
					double g = (P.getX() - x0) * (x1 - x0) + (P.getY() - y0)
					* (y1 - y0);
					if (g < 0) {
						g = 0;
					}
					if (g > h) {
						g = h;
					}
					final double x2 = x0 + g / h * (x1 - x0), y2 = y0 + g / h
					* (y1 - y0);
					final double d = Math.sqrt((P.getX() - x2)
							* (P.getX() - x2) + (P.getY() - y2)
							* (P.getY() - y2));
					if (d < dmin) {
						dmin = d;
						x = x2;
						y = y2;
					}
				}
				x0 = x1;
				y0 = y1;
			}
		}
		if (started) {
			P.Valid = true;
			final double dd = Math.sqrt((P.getX() - x) * (P.getX() - x)
					+ (P.getY() - y) * (P.getY() - y));
			return (int) Math.round(dd * Cn.getPixel());

		} else {
			P.Valid = false;
		}
		return Integer.MAX_VALUE;
	}

    @Override
	public void project(final PointObject P) {
		double varmin, varmax, dvar;
		try {

			varmin = VarMin.getValue();
			varmax = VarMax.getValue();
			dvar = DVar.getValue();
			if (varmin > varmax) {
				final double h = varmin;
				varmin = varmax;
				varmax = h;
			}

			if (dvar < 0) {
				dvar = -dvar;
			}
			if (dvar == 0) {
				dvar = (varmax - varmin) / 100;
			} else if (dvar < (varmax - varmin) / 1000) {
				dvar = (varmax - varmin) / 1000;
			}

		} catch (final Exception e) {
			Valid = false;
			return;
		}

		try {
			// if P is a PointOn (a parmetric curve) and function is just plot
			// with points :
			if ((!getEX().equals("x")) && (P.isPointOnOrMagnet()) && (Special)) {
				if (P.haveBoundOrder()) {
					X[0] = P.getBoundOrder();
					P.setXY(EX.getValue(), EY.getValue());
					return;
				} else {
					Coordinates CS;
					final Enumeration e = V.elements();
					double delta0 = 0, delta1 = 0, xx = 0, yy = 0;
					int i = 0, k = 0;
					if (e.hasMoreElements()) {
						CS = (Coordinates) e.nextElement();
						delta0 = Math.abs(CS.X - P.getX())
						+ Math.abs(CS.Y - P.getY());
						xx = CS.X;
						yy = CS.Y;
					}

					while (e.hasMoreElements()) {
						i++;
						CS = (Coordinates) e.nextElement();
						delta1 = Math.abs(CS.X - P.getX())
						+ Math.abs(CS.Y - P.getY());
						if (delta1 < delta0) {
							k = i;
							delta0 = delta1;
							xx = CS.X;
							yy = CS.Y;

						}
					}
					P.setXY(xx, yy);
					P.setBoundOrder(varmin + k * dvar);
					return;
				}

			}

		} catch (final Exception e) {
			Valid = false;
			return;
		}

		try {
			// if P is a PointOn (a cartesian function), try to calculate the
			// "real" coords :
			if ((P.isPointOnOrMagnet()) && (getEX().equals("x"))) {
				double x = (P.getX() < varmin) ? varmin : P.getX();
				x = (P.getX() > varmax) ? varmax : x;
				final double y = evaluateF(x);
				P.move(x, y);
				return;
			}
		} catch (final Exception e) {
		}

		// Otherwise, at Least get the approx coordinates on the polygon :
		if (needsToRecompute()) {
			compute();
		}
		final Enumeration e = V.elements();
		double x = 0, y = 0, x0 = 0, y0 = 0, dmin = 0;
		boolean started = false;
		while (e.hasMoreElements()) {
			final Coordinates c = (Coordinates) e.nextElement();
			final double x1 = c.X;
			final double y1 = c.Y;
			if (!started) {
				dmin = Math.sqrt((P.getX() - x1) * (P.getX() - x1)
						+ (P.getY() - y1) * (P.getY() - y1));
				x0 = x = x1;
				y0 = y = y1;
				started = true;
			} else {
				if (c.flag) {
					double h = (x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0);
					if (h < 1e-10) {
						h = 1e-10;
					}
					double g = (P.getX() - x0) * (x1 - x0) + (P.getY() - y0)
					* (y1 - y0);
					if (g < 0) {
						g = 0;
					}
					if (g > h) {
						g = h;
					}
					final double x2 = x0 + g / h * (x1 - x0), y2 = y0 + g / h
					* (y1 - y0);
					final double d = Math.sqrt((P.getX() - x2)
							* (P.getX() - x2) + (P.getY() - y2)
							* (P.getY() - y2));
					if (d < dmin) {
						dmin = d;
						x = x2;
						y = y2;
					}
				}
				x0 = x1;
				y0 = y1;
			}
		}

		if (started) {
			P.setXY(x, y);
			P.Valid = true;
		} else {
			P.Valid = false;
		}

	}

	public double getSum() {
		double varmin, varmax, dvar;
		boolean reverse = false;
		final boolean parametric = !getEX().equals(getVar());
		try {
			varmin = VarMin.getValue();
			varmax = VarMax.getValue();
			dvar = DVar.getValue();
			if (varmin > varmax) {
				final double h = varmin;
				varmin = varmax;
				varmax = h;
				reverse = true;
			}
			if (dvar < 0) {
				dvar = -dvar;
			}
			if (dvar == 0) {
				dvar = (varmax - varmin) / 100;
			} else if (dvar < (varmax - varmin) / 1000) {
				dvar = (varmax - varmin) / 1000;
			}
		} catch (final Exception e) {
			Valid = false;
			return 0;
		}
		X[0] = varmin;
		double x0 = 0, y0 = 0;
		boolean started = false;
		double sum = 0;
		while (true) {
			try {
				final double x1 = EX.getValue();
				final double y1 = EY.getValue();
				if (parametric) {
					double x = 0, y = 0;
					if (getCenter() != null) {
						x = getCenter().getX();
						y = getCenter().getY();
					}
					if (started) {
						sum += ((x0 - x) * (y1 - y) - (y0 - y) * (x1 - x)) / 2;
					}
				} else {
					if (started) {
						if (Special) {
							if (reverse) {
								sum += (x1 - x0) * y1;
							} else {
								sum += (x1 - x0) * y0;
							}
						} else {
							sum += (x1 - x0) * (y0 + y1) / 2;
						}
					}
				}
				x0 = x1;
				y0 = y1;
				started = true;
			} catch (final Exception e) {
			}
			if (X[0] >= varmax) {
				break;
			}
			X[0] = X[0] + dvar;
			if (X[0] > varmax) {
				X[0] = varmax;
			}
		}
		return sum;
	}

	public double getLength() {
		double varmin, varmax, dvar;
		try {
			varmin = VarMin.getValue();
			varmax = VarMax.getValue();
			dvar = DVar.getValue();
			if (varmin > varmax) {
				final double h = varmin;
				varmin = varmax;
				varmax = h;
			}
			if (dvar < 0) {
				dvar = -dvar;
			}
			if (dvar == 0) {
				dvar = (varmax - varmin) / 100;
			} else if (dvar < (varmax - varmin) / 1000) {
				dvar = (varmax - varmin) / 1000;
			}
		} catch (final Exception e) {
			Valid = false;
			return 0;
		}
		X[0] = varmin;
		double x0 = 0, y0 = 0;
		boolean started = false;
		double sum = 0;
		while (true) {
			try {
				final double x1 = EX.getValue();
				final double y1 = EY.getValue();
				if (started) {
					sum += Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0)
							* (y1 - y0));
				}
				started = true;
				x0 = x1;
				y0 = y1;
			} catch (final Exception e) {
			}
			if (X[0] >= varmax) {
				break;
			}
			X[0] = X[0] + dvar;
			if (X[0] > varmax) {
				X[0] = varmax;
			}
		}
		return sum;
	}

	@Override
	public boolean isSpecial() {
		return Special;
	}

	@Override
	public void setSpecial(final boolean f) {
		Special = f;
	}

    @Override
    public void project(final PointObject P, final double alpha) {
	project(P);
    }

    @Override
    public boolean maybeTransparent() {
	return true;
    }

    @Override
    public boolean canDisplayName() {
	return true;
    }

    @Override
    public boolean showValue(){
	return false;
    }

    public void setCenter(final String s) {
    	if (Cn == null) {
	    return;
	}
	Center = new Expression("@\"" + s + "\"", Cn, this);
    }

    @Override
    public boolean isFilledForSelect() {
	return false;
    }

    public PointObject getCenter() {
	try {
	    return (PointObject) Center.getObject();
	} catch (final Exception e) {
	    return null;
	}
    }

    public String getVar() {
	String vars = Var[0];
	for (int i = 1; i < Var.length; i++) {
	    vars = vars + " " + Var[i];
	}
	return vars;
    }

    @Override
    public boolean canInteresectWith(final ConstructionObject o) {
	return true;
    }

    @Override
    public boolean isDriverObject() {
	return true;
    }

    @Override
    public boolean somethingChanged() {
	boolean b = !EX.toString().equals(LASTEX);
	b = b || !EY.toString().equals(LASTEY);
	return b;
    }

    @Override
    public void clearChanges() {
	LASTEX = EX.toString();
	LASTEY = EY.toString();
    }

    @Override
    public void repulse(final PointObject P) {
	project(P);
    }

    /******************************
     * Closed or Open Intervals ***
    *******************************/
    public void setMinClosed(boolean b) {
	isMinClosed = b;
    }
    public void setMinOpen(boolean b) {
	isMinOpen = b;
    }
    public boolean getMinClosed() {
	return isMinClosed;
    }
    public boolean getMinOpen() {
	return isMinOpen;
    }
    
    public void setMaxClosed(boolean b) {
	isMaxClosed = b;
    }
    public void setMaxOpen(boolean b) {
	isMaxOpen = b;
    }
    public boolean getMaxClosed() {
	return isMaxClosed;
    }
    public boolean getMaxOpen() {
	return isMaxOpen;
    }
}
