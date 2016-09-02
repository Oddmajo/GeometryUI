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

// file: PointObject.java
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.FontMetrics;
import java.util.Enumeration;


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

public class ExpressionObject extends ConstructionObject implements
        MoveableObject, SimulationObject, DriverObject {

    protected double X, Y;
    private static Count N=new Count();
    public Expression E;
    protected Expression EX, EY;
    protected boolean Fixed;
    String Prompt=Global.name("expression.value");
    protected double CurrentValue=0;
    protected boolean CurrentValueValid=true;
    protected boolean Slider=false;
    protected Expression SMin, SMax;
    private String LASTE="";
    private boolean isOwned=false; // tell if this expression belongs to a control

    public ExpressionObject(final Construction c, final double x, final double y) {
        super(c);
        X=x;
        Y=y;
        setColor(ColorIndex, SpecialColor);
        updateText();
        setFixed("0");
    }

    @Override
    public void setDefaults() {

        setShowName(Global.getParameter("options.text.shownames", false));
        setShowValue(true);
        // setShowValue(Global.getParameter("options.text.showvalues",false));
        setColor(Global.getParameter("options.text.color", 0), Global.getParameter("options.text.pcolor", (ExpressionColor) null, this));
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
        setColor(Global.getParameter("options.text.color", 0), Global.getParameter("options.text.pcolor", (ExpressionColor) null, this));
        setColorType(Global.getParameter("options.text.colortype", 0));
    }

    @Override
    public String getTag() {
        return "Expression";
    }

    @Override
    public int getN() {
        return N.next();
    }

    @Override
    public void updateText() {
        if (E!=null) {
            setText(text3(Global.name("text.expression"), E.toString(), ""
                    +roundDisplay(X), ""+roundDisplay(Y)));
        } else {
            setText(text3(Global.name("text.expression"), "", ""
                    +roundDisplay(X), ""+roundDisplay(Y)));
        }
    }
    public double C, R, W, H; // for the expression position
    public double SC, SR, SW, SH; // for the slider position in screen coord.
    public double SX, SY, SD; // for the slider scale in x,y-coordinates

    /**
     * Paint the expression. Use value, name (i.e. tag), or slider. Remember
     * slider position for nearto and drags.
     */
    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {
        DisplaysText=false;
        if (!Valid||mustHide(zc)) {
            return;
        }
        C=zc.col(X);
        R=zc.row(Y);
        setFont(g);
        final FontMetrics fm=g.getFontMetrics();
        if (isStrongSelected()&&g instanceof MyGraphics) {
            ((MainGraphics) g).drawMarkerRect(C-5, R-5, 10, 10);
        }
        g.setColor(this);
        String s="";
        if (showName()) // shows the tag with or without = ...
        {
            s=Prompt;
            if (showValue()) // =, if value shows and % is not surpressed
            {
                if (s.endsWith("_")&&s.length()>1) {
                    s=s.substring(0, s.length()-1);
                } else {
                    if (!s.equals("")) {
                        s=s+" = ";
                    }
                }
            } else {
                s=Prompt;
            }
        }
        if (showValue()) // show the value
        {
            try {
                E.getValue();
                double x=round(CurrentValue);
                if (Slider) {
                    x=round(CurrentValue, 100);
                }
                if (Math.abs(x-Math.round(x))<1e-10) {
                    s=s+Global.getLocaleNumber((int) x, "edit");
                    // s = s + (int) x;
                } else {
                    s=s+Global.getLocaleNumber(x, "edit");
                    // s = s + x;
                }
            } catch (final Exception e) {
                s=s+"???";
            }
        }
        s=s+Unit; // add optional unit
        s=AngleObject.translateToUnicode(s); // translate \a etc.
        // s=s.replace(".",",");

        if (s.equals("")) // nothing to show
        {
            if (!Slider) {
                s="-";
            }
        }
        W=fm.stringWidth(s);
        if (!s.equals("")) {
            setFont(g);
            R-=fm.getAscent();
            H=g.drawStringExtended(s, C, R);
            //W=((MyGraphics13) g).getW();//pb export eps et svg, il faut un double
        }
        if (Slider) // we draw an additional slider
        {
            final int coffset=(int) (4*zc.pointSize());
            R+=H;
            g.drawLine(C, R+coffset/2, C+10*coffset, R+coffset/2);

            final double d=getSliderPosition();
            final int size=coffset/4;
            final double rslider=C+d*10*coffset;
            final double cw=2*size+2;

            if (getColorType()==THICK) {
                g.fillOval(rslider-size-1, R+coffset/2-size-1, cw,
                        cw, true, false, this);
            } else {
                g.fillOval(rslider-size-1, R+coffset/2-size-1, cw,
                        cw, new Color(250, 250, 250));
                g.setColor(this);
                g.drawOval(rslider-size-1, R+coffset/2-size-1, cw,
                        cw);
            }
            // remember position
            SC=rslider-size;
            SR=R+coffset/2-size;
            SW=SH=2*size;
            SX=zc.x((int) C);
            SY=zc.y((int) R+coffset/2-size);
            SD=zc.x((int) C+10*coffset)-SX;

            R-=H;

        }
    }

    /**
     * Compute the relative position, the slider must be on according to
     * CurrentValue
     *
     * @return 0 <= position <= 1
     */
    public double getSliderPosition() {
        try {
            final double min=SMin.getValue();
            final double max=SMax.getValue();
            double x=CurrentValue;
            if (min>=max) {
                Valid=false;
                return 0;
            }
            if (x<min) {
                x=min;
            }
            if (x>max) {
                x=max;
            }
            return (x-min)/(max-min);
        } catch (final Exception e) {
            Valid=false;
            return 0;
        }
    }

    /**
     * Set the expression according to the relative value the slider is on. 0 <=
     * d <= 1.
     *
     * @param d
     */
    public void setSliderPosition(final double d) {
        try {
            final double min=SMin.getValue();
            final double max=SMax.getValue();
            if (min>=max) {
                Valid=false;
                return;
            }
            double value=min+d*(max-min);
            if (value<min) {
                value=min;
            }
            if (value>max) {
                value=max;
            }
            E.setValue(value); // kills expression and makes it a constant
        } catch (final Exception e) {
            Valid=false;
        }
    }

    @Override
    public boolean nearto(final int x, final int y, final ZirkelCanvas zc) {
        DragSlider=false;

        if (Valid&&!displays(zc)) {
            return false;
        }
        if (C<=x&&x<=C+W&&R<=y&&y<=R+H) {
            return true;
        }
        if (SC<=x&&SR<=y&&SC+SW>=x&&SR+SH>=y) {
            DragSlider=true;
            return true;
        }

        return false;
    }

    @Override
    public double getX() {
        return X;
    }

    @Override
    public double getY() {
        return Y;
    }

    public Expression getExp() {
        return E;
    }

    @Override
    public void move(final double x, final double y) {
        X=x;
        Y=y;
    }

    @Override
    public void snap(final ZirkelCanvas zc) {
        final double d=zc.getGridSize()/2;
        X=Math.round(X/d)*d;
        Y=Math.round(Y/d)*d;
    }

    @Override
    public void round() {
        move(round(X, ZirkelCanvas.LengthsFactor), round(Y,
                ZirkelCanvas.LengthsFactor));
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        if (Fixed&&EX!=null&&EX.isValid()) {
            xml.printArg("x", EX.toString());
        } else {
            xml.printArg("x", ""+X);
        }
        if (Fixed&&EY!=null&&EY.isValid()) {
            xml.printArg("y", EY.toString());
        } else {
            xml.printArg("y", ""+Y);
        }
        if (E!=null) {
            xml.printArg("value", E.toString());
        } else {
            xml.printArg("value", "");
        }
        xml.printArg("prompt", Prompt);
        if (Fixed) {
            xml.printArg("fixed", "true");
        }
        if (Slider) {
            xml.printArg("slider", "true");
            xml.printArg("min", SMin.toString());
            xml.printArg("max", SMax.toString());
        }
    }

    @Override
    public boolean equals(final ConstructionObject o) {
        return false;
    }

    @Override
    public void setExpression(final String expr, final Construction c)
            throws ConstructionException {
        E=new Expression(expr, c, this);
        updateText();
    }

    @Override
    public void setFixed(final String expr) {
        E=new Expression(expr, getConstruction(), this);
        updateText();
    }

    @Override
    public String getExpression() {
        if (E!=null) {
            return E.toString();
        } else {
            return "";
        }
    }

    // public Enumeration depending() {
    // DL.reset();
    // if (E != null) {
    // Enumeration e = E.getDepList().elements();
    // while (e.hasMoreElements()) {
    // DL.add((ConstructionObject) e.nextElement());
    // }
    // }
    // if (Fixed) {
    // Enumeration e;
    // if (EX != null) {
    // e = EX.getDepList().elements();
    // while (e.hasMoreElements()) {
    // DL.add((ConstructionObject) e.nextElement());
    // }
    // }
    // if (EY != null) {
    // e = EY.getDepList().elements();
    // while (e.hasMoreElements()) {
    // DL.add((ConstructionObject) e.nextElement());
    // }
    // }
    // }
    // return DL.elements();
    // }
    @Override
    public Enumeration depending() {
        DL.reset();
        addDepending(E);
        addDepending(SMin);
        addDepending(SMax);
        if (Fixed) {
            addDepending(EX);
            addDepending(EY);
        }
        return DL.elements();
    }

    public void addDepending(final Expression EE) {
        if (EE!=null) {
            final Enumeration e=EE.getDepList().elements();
            while (e.hasMoreElements()) {
                DL.add((ConstructionObject) e.nextElement());
            }
        }
    }

    @Override
    public double getValue() throws ConstructionException {
        if (!CurrentValueValid) {
            throw new InvalidException("");
        }
        return CurrentValue;
    }

    @Override
    public String getPrompt() {
        return Prompt;
    }

    @Override
    public void setPrompt(final String prompt) {
        Prompt=prompt;
    }

    @Override
    public void translate() {

        if (Slider) {
            SMin.translate();
            SMax.translate();
        } else {
            E.translate();
        }

        if (Fixed) {
            try {
                setFixed(EX.toString(), EY.toString());
                EX.translate();
                EY.translate();
            } catch (final Exception e) {
            }
        }
        updateText();
    }

    @Override
    public void validate() {
        double oldV;
        
        try {
            oldV=CurrentValue;
            CurrentValue=E.getValue();
            CurrentValueValid=true;
            if (oldV!=CurrentValue) {
                
            }
        } catch (final Exception e) {
            CurrentValueValid=false;
        }
        Valid=true;
        if (Fixed&&EX!=null&&EX.isValid()) {
            try {
                X=EX.getValue();
            } catch (final Exception e) {
                Valid=false;
                return;
            }
        }
        if (Fixed&&EY!=null&&EY.isValid()) {
            try {
                Y=EY.getValue();
            } catch (final Exception e) {
                Valid=false;
                return;
            }
        }



    }

    @Override
    public void setFixed(final boolean flag) {
        Fixed=flag;
        if (!Fixed) {
            EX=EY=null;
        }
        updateText();
    }

    @Override
    public void setFixed(final String x, final String y) {
        Fixed=true;
        EX=new Expression(x, getConstruction(), this);
        EY=new Expression(y, getConstruction(), this);
        updateText();
    }

    @Override
    public ConstructionObject copy(final double x, final double y) {
        try {
            final ExpressionObject o=(ExpressionObject) clone();
            setTranslation(o);

            if (Slider) {
                o.setSlider(SMin.toString(), SMax.toString());
            } else {
                o.setExpression(E.toString(), getConstruction());
            }

            o.translateConditionals();
            o.translate();
            o.setName();
            o.updateText();
            o.setBreak(false);
            o.setTarget(false);
            return o;
        } catch (final Exception e) {
            return null;
        }
    }

    public boolean moveable() {
        if (Slider) {
            return true;
        }
        return EX==null&&EY==null;
    }

    public void reset() {
        if (E!=null) {
            E.reset();
        }
    }

    @Override
    public boolean fixed() {
        return Fixed;
    }

    @Override
    public String getEX() {
        if (EX!=null) {
            return EX.toString();
        } else {
            return ""+round(X);
        }
    }

    @Override
    public String getEY() {
        if (EY!=null) {
            return EY.toString();
        } else {
            return ""+round(Y);
        }
    }

    public void setCurrentValue(final double x) {
        CurrentValue=x;
        CurrentValueValid=true;
    }
    // For the simulate function:
    private double OldE;

    /**
     * Set the simulation value, remember the old value.
     */
    public void setSimulationValue(final double x) {
        OldE=CurrentValue;
        CurrentValue=x;
    }

    /**
     * Reset the old value.
     */
    public void resetSimulationValue() {
        CurrentValue=OldE;
    }

    /**
     * Set the slider to min, max und step values.
     *
     * @param smin
     * @param smax
     * @param sstep
     */
    @Override
    public void setSlider(final String smin, final String smax) {
        Slider=true;
        SMin=new Expression(smin, getConstruction(), this);
        SMax=new Expression(smax, getConstruction(), this);
    }

    /**
     * Set or clear the slider.
     *
     * @param flag
     */
    @Override
    public void setSlider(final boolean flag) {
        Slider=flag;
    }
    double oldx, oldy, startx, starty;
    boolean DragSlider;

    public void startDrag(final double x, final double y) {
        oldx=X;
        oldy=Y;
        startx=x;
        starty=y;
    }

    public void dragTo(final double x, final double y) {
        if (DragSlider) {
            setSliderPosition((x-SX)/SD);
        } else {
            move(oldx+(x-startx), oldy+(y-starty));
        }
    }

    public double getOldX() {
        return oldx;
    }

    public double getOldY() {
        return oldy;
    }

    @Override
    public boolean isSlider() {
        return Slider;
    }

    public double getMinValue() {
        try {
            double d=SMin.getValue();
            return d;
        } catch (Exception ex) {
            return -5;
        }
    }

    public double getMaxValue() {
        try {
            double d=SMax.getValue();
            return d;
        } catch (Exception ex) {
            return -5;
        }
    }

    @Override
    public String getMin() {
        if (Slider) {
            return SMin.toString();
        } else {
            return ("-5");
        }
    }

    @Override
    public String getMax() {
        if (Slider) {
            return SMax.toString();
        } else {
            return ("5");
        }
    }

    @Override
    public String getDisplayValue() {

        String s="";
        try {
            E.getValue();
            double x=round(CurrentValue);
            if (Slider) {
                x=round(CurrentValue, 100);
            }
            if (Math.abs(x-Math.round(x))<1e-10) {

                s=s+(int) x;
            } else {
                s=s+x;
            }
        } catch (final Exception e) {
            s=s+"???";
        }
        s=s+Unit; // add optional unit
        return s;
    }

    @Override
    public String getEquation() {
        if (E==null) {
            return "???";
        } else {
            return E.toString();
        }
    }

    @Override
    public boolean isDriverObject() {
        return true;
    }

    public boolean somethingChanged() {
        return (!getEquation().equals(LASTE));
    }

    public void clearChanges() {
        LASTE=getEquation();
    }

    @Override
    public boolean isOwnedByControl() {
        return isOwned;
    }

    @Override
    public void setOwnedByControl(boolean b) {
        isOwned=b;
    }
}
