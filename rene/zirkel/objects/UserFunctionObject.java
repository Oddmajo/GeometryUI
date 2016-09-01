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
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.util.Enumeration;
import java.util.StringTokenizer;

import eric.JEricPanel;

import rene.dialogs.Warning;
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
import rene.zirkel.expression.InvalidException;
import rene.zirkel.graphics.MyGraphics;
import eric.bar.JProperties;
import eric.bar.JPropertiesBar;
import rene.zirkel.expression.ExpressionColor;



/**
 * @author Rene
 * 
 *         This class is for function of several variables. Those functions
 *         cannot be drawn at all.
 */
public class UserFunctionObject extends ConstructionObject implements
MoveableObject, DriverObject, Evaluator {
	static Count N = new Count();
	Expression EY = null;
	double X[] = { 0 };
	String Var[] = { "x" };
	protected double Xpos, Ypos;
	protected boolean Fixed;
	protected Expression EXpos, EYpos;
	String LASTE = "";

	public UserFunctionObject(final Construction c) {
		super(c);
		validate();
		updateText();
		N.reset();
	}

	@Override
	public void setDefaults() {
		setShowName(Global.getParameter("options.text.shownames", false));
		setShowValue(Global.getParameter("options.text.showvalues", false));
		setColor(Global.getParameter("options.text.color", 0), Global
				.getParameter("options.text.pcolor", (ExpressionColor) null, this));
		setColorType(Global.getParameter("options.text.colortype", 0));
		setHidden(Cn.Hidden);
		setObtuse(Cn.Obtuse);
		setSolid(Cn.Solid);
		setLarge(Cn.LargeFont);
		setBold(Cn.BoldFont);
		setPartial(Cn.Partial);
	}

	@Override
	public void setTargetDefaults() {
		setShowName(Global.getParameter("options.text.shownames", false));
		setShowValue(Global.getParameter("options.text.showvalues", false));
		setColor(Global.getParameter("options.text.color", 0), Global
				.getParameter("options.text.pcolor", (ExpressionColor) null, this));
		setColorType(Global.getParameter("options.text.colortype", 0));
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
		setText(getDisplayValue());
	}

	public boolean isValid() {
		return Valid;
	}

	@Override
	public void validate() {
		if (EY != null) {
			Valid = EY.isValid();
		} else {
			Valid = false;
		}
		if (Fixed && EXpos != null && EXpos.isValid()) {
			try {
				Xpos = EXpos.getValue();
			} catch (final Exception e) {
				Valid = false;
				return;
			}
		}
		if (Fixed && EYpos != null && EYpos.isValid()) {
			try {
				Ypos = EYpos.getValue();
			} catch (final Exception e) {
				Valid = false;
				return;
			}
		}
	}

	public void setExpressions(final String t, final String ey) {
		final StringTokenizer tok = new StringTokenizer(t);
		Var = new String[tok.countTokens()];
		X = new double[tok.countTokens()];
		int i = 0;
		while (tok.hasMoreTokens()) {
			Var[i++] = tok.nextToken();
		}
		EY = new Expression(ey, getConstruction(), this, Var);
		validate();
	}

	@Override
	public String getEY() {
		if (EY != null) {
			return EY.toString();
		} else {
			return "0";
		}
	}

	double C, R, W, H;

	@Override
	public void paint(final MyGraphics g, final ZirkelCanvas zc) {
		if (!Valid || mustHide(zc)) {
			return;
		}
		final FontMetrics fm = g.getFontMetrics();
		H = fm.getHeight();
		C = zc.col(Xpos);
		R = zc.row(Ypos);
		g.setColor(this);
		setFont(g);
		final String s = AngleObject.translateToUnicode(getDisplayValue());
		g.drawString(s, C, R);
		R -= H;
		W = fm.stringWidth(s);
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

	@Override
	public String getDisplayValue() {
		String s = "";
		if (showName()) {
			if (getAlias() != null) {
				s = getAlias() + " : ";
			}
			s = s + getName() + "(" + Var[0];
			for (int i = 1; i < Var.length; i++) {
				s = s + "," + Var[i];
			}
			s = s + ")";
			if (showValue()) {
				s = s + "=";
			}
		}
		if (showValue()) {
			s = s
			+ ((EY == null) ? "" : JProperties.Point_To_Comma(EY
					.toString(), Cn, true));
		}
		return s;
	}

	@Override
	public boolean nearto(final int cc, final int rr, final ZirkelCanvas zc) {
		if (!displays(zc)) {
			return false;
		}
		return C <= cc && R <= rr && cc <= C + W && rr <= R + H;
	}

	public boolean EditAborted;



	@Override
	public void printArgs(final XmlWriter xml) {
		xml.printArg("f", EY.toString());
		if (Fixed && EXpos != null && EXpos.isValid()) {
			xml.printArg("x", EXpos.toString());
		} else {
			xml.printArg("x", "" + Xpos);
		}
		if (Fixed && EYpos != null && EYpos.isValid()) {
			xml.printArg("y", EYpos.toString());
		} else {
			xml.printArg("y", "" + Ypos);
		}
		if (Fixed) {
			xml.printArg("fixed", "true");
		}
		xml.printArg("var", getVar());
	}

	@Override
	public void translate() {
		try {
			EY = new Expression(EY.toString(), getConstruction(), this, Var);
			final ConstructionObject O = getTranslation();
			setTranslation(this);
			if (Fixed) {
				try {
					setFixed(EXpos.toString(), EYpos.toString());
					EXpos.translate();
					EYpos.translate();
				} catch (final Exception e) {
				}
			}
			validate();
			setTranslation(O);
		} catch (final Exception e) {
			System.out.println();
			System.out.println(getName());
			System.out.println(e);
			e.printStackTrace();
		}
	}

	@Override
	public void setFixed(final boolean flag) {
		Fixed = flag;
		if (!Fixed) {
			EXpos = EYpos = null;
		}
		updateText();
	}

	@Override
	public void setFixed(final String x, final String y) {
		Fixed = true;
		EXpos = new Expression(x, getConstruction(), this);
		EYpos = new Expression(y, getConstruction(), this);
		updateText();
	}

	@Override
	public boolean fixed() {
		return Fixed;
	}

	@Override
	public String getEXpos() {
		if (EXpos != null) {
			return EXpos.toString();
		} else {
			return "" + round(Xpos);
		}
	}

	@Override
	public String getEYpos() {
		if (EYpos != null) {
			return EYpos.toString();
		} else {
			return "" + round(Ypos);
		}
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
		addDepending(EY);
		if (Fixed) {
			addDepending(EXpos);
			addDepending(EYpos);
		}
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

	public double evaluateF(final double x, final double y)
	throws ConstructionException {
		X[0] = x;
		X[1] = y;
		for (int i = 2; i < X.length; i++) {
			X[i] = 0;
		}
		try {
			return EY.getValue();
		} catch (final Exception e) {
			throw new ConstructionException("");
		}
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
	public boolean isFilledForSelect() {
		return false;
	}

	public String getVar() {
		String vars = Var[0];
		for (int i = 1; i < Var.length; i++) {
			vars = vars + " " + Var[i];
		}
		return vars;
	}

	public void dragTo(final double x, final double y) {
		move(oldx + (x - startx), oldy + (y - starty));
	}

	@Override
	public void move(final double x, final double y) {
		Xpos = x;
		Ypos = y;
	}

	double oldx, oldy, startx, starty;

	public boolean moveable() {
		return !Fixed;
	}

	public void startDrag(final double x, final double y) {
		oldx = Xpos;
		oldy = Ypos;
		startx = x;
		starty = y;
	}

	public double getOldX() {
		return oldx;
	}

	public double getOldY() {
		return oldy;
	}

	@Override
	public double getX() {
		return Xpos;
	}

	@Override
	public double getY() {
		return Ypos;
	}

	@Override
	public boolean isDriverObject() {
		return true;
	}

	public boolean somethingChanged() {
		return (!EY.toString().equals(LASTE));
	}

	public void clearChanges() {
		LASTE = EY.toString();
	}
}
