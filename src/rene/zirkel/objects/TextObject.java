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
import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;


import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.expression.ExpressionString;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.MainGraphics;

public class TextObject extends ConstructionObject implements MoveableObject {

    protected double X, Y;
    Vector T;
    static Count N=new Count();
    double C, R, W, H;
    protected Expression EX, EY;
    protected boolean Fixed;
    protected boolean DoShow; // for replays

    public TextObject(final Construction c, final double x, final double y) {
        super(c);
        X=x;
        Y=y;
        T=new Vector();
        setColor(ColorIndex, SpecialColor);
        Valid=true;
        setLines("<txt>");
    }

    @Override
    public void setDefaults() {
        // setShowName(Global.getParameter("options.text.shownames",false));
        // setShowValue(Global.getParameter("options.text.showvalues",false));
        setShowName(true);
        setShowValue(true);
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

        setShowName(true);
        setShowValue(true);
        setColor(Global.getParameter("options.text.color", 0), Global.getParameter("options.text.pcolor", (ExpressionColor) null, this));
        setColorType(Global.getParameter("options.text.colortype", 0));
    }

    @Override
    public boolean nearto(final int x, final int y, final ZirkelCanvas zc) {

        if (!displays(zc)) {
            return false;
        }
        return C<=x&&R<=y&&x<=C+W&&y<=R+H;
    }

    @Override
    public String getTag() {
        return "Text";
    }

    @Override
    public int getN() {
        return N.next();
    }

    @Override
    public void updateText() {
        setText(getLines(), true);
    }

    @Override
    public void move(final double x, final double y) {
        X=x;
        Y=y;
    }

    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {
        C=zc.col(X);
        R=zc.row(Y);

//                if (true) {
//                    Enumeration e=T.elements();
//                    String txt="";
//                    while (e.hasMoreElements()) {
//                        String s = ((ExpressionString) e.nextElement()).evaluate();
//                        txt=txt+s+"coucou\n";
//                    }
//                    g.drawLaTeXString(txt,C,R);
//                    return;
//                }






        if (mustHide(zc)) {
            return;
        }
        final boolean hidden=Hidden;
        if (DoShow) {
            Hidden=false;
        }
        g.setColor(this);
        setFont(g);
        if (DoShow) {
            Hidden=hidden;
        }
        FontMetrics fm=g.getFontMetrics();
        W=H=fm.getHeight();
        final Enumeration e=T.elements();
        double r=R;
        while (e.hasMoreElements()) {
            String s=((ExpressionString) e.nextElement()).evaluate();
            boolean bold=false, large=false;
            if (s.startsWith("***")) {
                s=s.substring(3);
                bold=large=true;
            } else if (s.startsWith("**")) {
                s=s.substring(2);
                large=true;
            } else if (s.startsWith("*")) {
                s=s.substring(1);
                bold=true;
            }
            if (isStrongSelected()) {
                ((MainGraphics) g).drawMarkerRect(C, r, 10, 10);
                g.setColor(this);
            }
            g.setFont(large, bold);
//            r+=g.drawLaTeXString(s, C, r);
            
            r += g.drawStringExtended(s, C, r);

            W=Math.max(W, ((MainGraphics) g).getW());
            // W=Math.max(W, fm.stringWidth(s));


        }
        H=r-R;
    }

    @Override
    public boolean mustHide(final ZirkelCanvas zc) {
        return super.mustHide(zc)&&!(Valid&&DoShow);
    }

    @Override
    public void validate() {
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
    public String getLines() {
        String S="";
        final Enumeration e=T.elements();
        while (e.hasMoreElements()) {
            final String s=((ExpressionString) e.nextElement()).toString();
            S=S+s;
            if (e.hasMoreElements()) {
                S=S+"\n";
            }
        }
        return S;
    }

    @Override
    public void setLines(String o) {
        if (o.equals("")) {
            o="<txt>";
        }
        final Vector w=new Vector();
        try {
            final BufferedReader in=new BufferedReader(new StringReader(o));
            while (true) {
                final String s=in.readLine();
                if (s==null) {
                    break;
                }
                w.addElement(new ExpressionString(s, this));
            }
            in.close();
        } catch (final Exception e) {
        }
        T=w;
        updateText();
    }

    @Override
    public String getDisplayValue() {
        return "("+roundDisplay(X)+","+roundDisplay(Y)+")";
    }

    @Override
    public double getX() {
        return X;
    }

    @Override
    public double getY() {
        return Y;
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
        if (Fixed) {
            xml.printArg("fixed", "true");
        }
    }

    public boolean moveable() {
        return !Fixed;
    }

    @Override
    public boolean fixed() {
        return Fixed;
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

    public void setDoShow(final boolean flag) {
        DoShow=flag;
    }

    @Override
    public void snap(final ZirkelCanvas zc) {
        final double d=zc.getGridSize()/2;
        X=Math.round(X/d)*d;
        Y=Math.round(Y/d)*d;
    }

    @Override
    public void translate() {
        if (Fixed) {
            try {
                setFixed(EX.toString(), EY.toString());
                EX.translate();
                EY.translate();
            } catch (final Exception e) {
            }
        }
    }

    @Override
    public Enumeration depending() {
        super.depending();
        final Enumeration e=T.elements();
        while (e.hasMoreElements()) {
            ((ExpressionString) e.nextElement()).addDep(this);
        }
        if (Fixed) {
            if (EX!=null) {
                EX.addDep(this);
            }
            if (EY!=null) {
                EY.addDep(this);
            }
        }
        return DL.elements();
    }

    @Override
    public boolean canDisplayName() {
        return false;
    }
    double oldx, oldy, startx, starty;

    public void startDrag(final double x, final double y) {
        oldx=X;
        oldy=Y;
        startx=x;
        starty=y;
    }

    public void dragTo(final double x, final double y) {
        move(oldx+(x-startx), oldy+(y-starty));
    }

    public double getOldX() {
        return oldx;
    }

    public double getOldY() {
        return oldy;
    }
}
