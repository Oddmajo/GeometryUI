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

// file: PrimitiveCircleObject.java
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.MainGraphics;
import rene.zirkel.structures.Coordinates;

public class PrimitiveCircleObject extends ConstructionObject implements
        PointonObject, InsideObject {

    protected double X, Y, R;
    static Count N=new Count();
    boolean Partial=false;
    PointObject Dep[]; // array for points, depending on the circle for partial
    // display
    int NDep; // number of points in Dep
    PointObject M; // The midpoint
    boolean Filled=false;
    String StartPtName=null, EndPtName=null; // Border points name for Arcs
    PointObject StartPt=null, EndPt=null; // Border points for Arcs
//    PointObject RealCorrespondingStartPt=null; // for 3 pts Arcs only
    boolean isArc3pts=false;
    double A1, A2, A;
    boolean Arc=true;
    // Seulement pour les droites hyperboliques :
    private double oldStartX=Double.NaN;
    private double oldStartY=Double.NaN;

    public PrimitiveCircleObject(final Construction c, final PointObject p) {
        super(c);
        setColor(ColorIndex, SpecialColor);
        M=p;
        Unit=Global.getParameter("unit.length", "");
    }

    public void validate() {
        super.validate();
        // Gestion de la continuité  des droites sur le cercle horizon :
        if (isDPLineObject()) {
            // Premier passage, mémorisation de l'extrémité Start de l'arc
            if (Double.isNaN(oldStartX)) {
                oldStartX=getStart().getX();
                oldStartY=getStart().getY();
            } else {
                final double d1=(oldStartX-getStart().getX())*(oldStartX-getStart().getX())
                        +(oldStartY-getStart().getY())*(oldStartY-getStart().getY());
                final double d2=(oldStartX-getEnd().getX())*(oldStartX-getEnd().getX())
                        +(oldStartY-getEnd().getY())*(oldStartY-getEnd().getY());
                // Si Start et End se sont échangés, on remet dans l'ordre (cela fonctionne
                // car ce sont des arcs 180°) :
                if (d2<d1) {
                    PointObject P=StartPt;
                    StartPt=EndPt;
                    EndPt=P;
                }
                oldStartX=getStart().getX();
                oldStartY=getStart().getY();
            }
        }
    }

    public void setMR(final PointObject p1, final double r) {
        M=p1;
        R=r;
    }

    @Override
    public void setDefaults() {

        setShowName(Global.getParameter("options.circle.shownames", false));
        setShowValue(Global.getParameter("options.circle.showvalues", false));

        setColor(Global.getParameter("options.circle.color", 0), Global.getParameter("options.circle.pcolor", (ExpressionColor) null, this));
        setColorType(Global.getParameter("options.circle.colortype", 0));
        setFilled(Global.getParameter("options.circle.filled", false));
        setSolid(Global.getParameter("options.circle.solid", false));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        // setSolid(Cn.Solid);
        setLarge(Cn.LargeFont);
        setBold(Cn.BoldFont);
        setPartial(Cn.Partial);
        Cn.updateCircleDep();
    }

    @Override
    public void setTargetDefaults() {
        if (isDPLineObject()) {
            setShowName(Global.getParameter("options.line.shownames", false));
            setShowValue(Global.getParameter("options.line.showvalues", false));
            setColor(Global.getParameter("options.line.color", 0), Global.getParameter("options.line.pcolor", (ExpressionColor) null, this));
            setColorType(Global.getParameter("options.line.colortype", 0));
            setFilled(Global.getParameter("options.line.filled", false));
            setSolid(Global.getParameter("options.line.solid", false));
        } else if (isDPSegmentObject()) {
            setShowName(Global.getParameter("options.segment.shownames", false));
            setShowValue(Global.getParameter("options.segment.showvalues", false));
            setColor(Global.getParameter("options.segment.color", 0), Global.getParameter("options.segment.pcolor", (ExpressionColor) null, this));
            setColorType(Global.getParameter("options.segment.colortype", 0));
            setFilled(Global.getParameter("options.segment.filled", false));
            setSolid(Global.getParameter("options.segment.solid", false));
        } else {
            setShowName(Global.getParameter("options.circle.shownames", false));
            setShowValue(Global.getParameter("options.circle.showvalues", false));
            setColor(Global.getParameter("options.circle.color", 0), Global.getParameter("options.circle.pcolor", (ExpressionColor) null, this));
            setColorType(Global.getParameter("options.circle.colortype", 0));
            setFilled(Global.getParameter("options.circle.filled", false));
            setSolid(Global.getParameter("options.circle.solid", false));
        }
    }

    @Override
    public String getTag() {
        return "Circle";
    }

    @Override
    public int getN() {
        return N.next();
    }

    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {


        if (!Valid||mustHide(zc)) {
            return;
        }
        final double c1=zc.col(X-R), c2=zc.col(X+R), r1=zc.row(Y+R), r2=zc.row(Y-R), r=(r2-r1)/2;
        double ssa=1/Math.sqrt(2), ssb=-ssa;
        // paint:

        if (!zc.showHidden()&&Dep!=null&&NDep>0&&Partial
                &&!hasRange()) // partial display
        {

            // System.out.println("partial display");
            for (int i=0; i<NDep; i++) {
                if (!Dep[i].valid()
                        ||((!zc.isPreview())&&(Dep[i].mustHide(zc)))) {
                    continue;
                }

                double A=Math.atan2(Dep[i].getY()-Y, Dep[i].getX()-X);
                if (A<0) {
                    A+=2*Math.PI;
                }
                final double a=A/Math.PI*180;
                if (visible(zc)) {

                    if (isStrongSelected()&&g instanceof MainGraphics) {
                        ((MainGraphics) g).drawMarkerArc((c1+c2)/2.0,
                                (r1+r2)/2.0, r, a-10, 20);
                    }
                    g.setColor(this);
                    g.drawCircleArc(c1+r, r1+r, r, a-10, 20, this);
                }
                if (i==0) {
                    final String s=getDisplayText();
                    if (!s.equals("")) {
                        g.setLabelColor(this);
                        DisplaysText=true;
                        final double sx=Math.cos(A-0.1);
                        final double sy=Math.sin(A-0.1);
                        drawLabel(g, s, zc, X+sx*R, Y+sy*R, sy, -sx,
                                XcOffset, YcOffset);
                    }
                }
            }
        } else {
            if (hasRange()) // arc display
            {
                computeSOE();
                if (visible(zc)) {
                    if (isStrongSelected()&&g instanceof MainGraphics) {
                        ((MainGraphics) g).drawMarkerArc((c1+c2)/2.0,
                                (r1+r2)/2.0, r, A1/Math.PI*180, A
                                /Math.PI*180);
                    }
                    g.setColor(this);
                    if (Filled) {
                        g.fillArc(c1, r1, c2-c1, r2-r1, A1/Math.PI*180,
                                A/Math.PI*180, Selected
                                ||(getColorType()!=THIN),
                                getColorType()!=THICK, Arc, this);
                    } else if (visible(zc)) {
                        g.drawCircleArc(c1+r, r1+r, r, A1/Math.PI*180,
                                A/Math.PI*180, this);
                    }
                }
                ssa=Math.cos(A1+A/2);
                ssb=Math.sin(A1+A/2);
            } else if (Filled) {
                if (visible(zc)) {
                    if (isStrongSelected()&&g instanceof MainGraphics) {
                        ((MainGraphics) g).drawMarkerArc((c1+c2)/2.0,
                                (r1+r2)/2.0, r, 0, 360);
                    }
                    g.setColor(this);
                    g.fillOval(c1, r1, c2-c1, r2-r1, Indicated||Selected
                            ||(getColorType()==NORMAL),
                            getColorType()!=THICK, this);
                }
            } else // full unfilled display
            {
                if (visible(zc)) {
                    if (isStrongSelected()&&g instanceof MainGraphics) {
                        ((MainGraphics) g).drawMarkerArc((c1+c2)/2.0,
                                (r1+r2)/2.0, r, 0, 360);
                    }
                    g.setColor(this);
                    if (tracked()) {
                        zc.UniversalTrack.drawTrackCircle(this, c1+r, r1+r,
                                r);
                    }
                    g.drawCircle(c1+r, r1+r, r, this);
                }
            }
            final String s=getDisplayText();
            if (!s.equals("")) {
                g.setLabelColor(this);
                DisplaysText=true;
                drawLabel(g, s, zc, X+ssa*R, Y+ssb*R, -ssa, ssb,
                        XcOffset, YcOffset);
            }
        }
    }

    @Override
    public String getDisplayValue() {
        // return ""+round(R,ZirkelCanvas.LengthsFactor);
        return Global.getLocaleNumber(R, "lengths");
    }

    @Override
    public String getEquation() {
        return "(x"+helpDisplayNumber(false, -X)+")^2+"+"(y"
                +helpDisplayNumber(false, -Y)+")^2="
                +helpDisplayNumber(true, R*R);
    }

    @Override
    public boolean isInRect(Rectangle r, ZirkelCanvas zc) {
        double Xest=M.getX()+getR(), Yest=M.getY();
        double Xnord=M.getX(), Ynord=M.getY()+getR();
        double Xouest=M.getX()-getR(), Youest=M.getY();
        double Xsud=M.getX(), Ysud=M.getY()-getR();

        boolean b=r.contains(zc.col(Xest), zc.row(Yest));
        b=b&&r.contains(zc.col(Xnord), zc.row(Ynord));
        b=b&&r.contains(zc.col(Xouest), zc.row(Youest));
        b=b&&r.contains(zc.col(Xsud), zc.row(Ysud));

        return b;
    }

    @Override
    public boolean nearto(final int c, final int r, final ZirkelCanvas zc) {
        return nearto(c, r, false, zc);
    }

    @Override
    public boolean nearto(final int c, final int r, final boolean ignorefill,
            final ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        final double x=zc.x(c)-X, y=zc.y(r)-Y;
        if (!ignorefill&&Filled) {
            final double d=Math.sqrt(x*x+y*y);
            if (d<R) {
                Value=0;
            }
            return d<R;
        } else if (hasRange()) {
            computeSOE();
            double a=Math.atan2(y, x);
            if (a<0) {
                a+=2*Math.PI;
            }
            a-=A1;
            if (a<0) {
                a+=2*Math.PI;
            }
            final double d=Math.abs(Math.sqrt(x*x+y*y)-R);
            if (a<=A+0.01) {
                Value=Math.abs(zc.col(zc.minX()+d)-zc.col(zc.minX()));
            }
            return Value<zc.selectionSize()&&a<=A+0.01;
        } // partial display:
        else if (!zc.showHidden()&&NDep>0&&Partial) {
            double d=Math.abs(Math.sqrt(x*x+y*y)-R);
            Value=Math.abs(zc.col(zc.minX()+d)-zc.col(zc.minX()));
            if (Math.abs(zc.col(zc.minX()+d)-zc.col(zc.minX()))>=zc.selectionSize()) {
                return false;
            }
            d=Math.PI/18;
            double a=Math.atan2(y, x);
            if (a<0) {
                a+=2*Math.PI;
            }
            for (int i=0; i<NDep; i++) {
                if (!Dep[i].valid()||Dep[i].mustHide(zc)) {
                    continue;
                }
                double A=Math.atan2(Dep[i].getY()-Y, Dep[i].getX()-X);
                if (A<0) {
                    A+=2*Math.PI;
                }
                double h=a-A;
                if (h>2*Math.PI) {
                    h-=2*Math.PI;
                }
                if (h<-2*Math.PI) {
                    h+=2*Math.PI;
                }
                if (Math.abs(h)<d) {
                    return true;
                }
            }
            return false;
        } else {
            final double d=Math.abs(Math.sqrt(x*x+y*y)-R);
            Value=Math.abs(zc.col(zc.minX()+d)-zc.col(zc.minX()));
            return Math.abs(zc.col(zc.minX()+d)-zc.col(zc.minX()))<zc.selectionSize();
        }
    }

    @Override
    public boolean onlynearto(final int c, final int r, final ZirkelCanvas zc) {
        if (R<zc.dx(3*zc.pointSize())) {
            return true;
        }
        if (hasRange()) {
            double A1=Math.atan2(getStart().getY()-Y, getStart().getX()-X);
            if (A1<0) {
                A1+=2*Math.PI;
            }
            double A2=Math.atan2(getEnd().getY()-Y, getEnd().getX()-X);
            if (A2<0) {
                A2+=2*Math.PI;
            }
            double A=A2-A1;
            if (A>=2*Math.PI) {
                A-=2*Math.PI;
            }
            if (A<0) {
                A+=2*Math.PI;
            }
            if (!Obtuse&&A>Math.PI) {
                A1=A2;
                A=2*Math.PI-A;
                A2=A1+A;
            }
            if (A*R<zc.dx(6*zc.pointSize())) {
                return true;
            }
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

    @Override
    public double getR() {
        return R;
    }

    public static Coordinates intersect(final PrimitiveCircleObject c1,
            final PrimitiveCircleObject c2) {
        double dx=c2.X-c1.X, dy=c2.Y-c1.Y;
        final double r=Math.sqrt(dx*dx+dy*dy);
        if (r>c1.R+c2.R+1e-10) {
            return null;
        }
        if (r<=1e-10) {
            return new Coordinates(c1.X, c1.Y, c1.X, c1.Y);
        }
        final double l=(r*r+c1.R*c1.R-c2.R*c2.R)/(2*r);
        dx/=r;
        dy/=r;
        final double x=c1.X+l*dx, y=c1.Y+l*dy;
        double h=c1.R*c1.R-l*l;
        if (h<-1e-10) {
            return null;
        }
        if (h<0) {
            h=0;
        } else {
            h=Math.sqrt(h);
        }
        return new Coordinates(x+h*dy, y-h*dx, x-h*dy, y+h*dx);
    }

    @Override
    public boolean equals(final ConstructionObject o) {
        if (!(o instanceof PrimitiveCircleObject)||!o.valid()) {
            return false;
        }
        final PrimitiveCircleObject l=(PrimitiveCircleObject) o;
        return equals(X, l.X)&&equals(Y, l.Y)&&equals(R, l.R);
    }

    @Override
    public void setPartial(final boolean flag) {
        if (flag==Partial) {
            return;
        }
        Partial=flag;
        if (flag) // depending objects no longer needed
        {
            Dep=new PointObject[16];
            NDep=0;
        } else {
            Dep=null;
        }
    }

    /**
     * Add a point that depends on the circle. Dep is used for partial display.
     *
     * @param p
     */
    public void addDep(final PointObject p) {
        if (!Partial||hasRange()||Dep==null||NDep>=Dep.length) {
            return;
        }
        Dep[NDep++]=p;
    }

    @Override
    public void clearCircleDep() {
        NDep=0;
    }

    @Override
    public boolean isPartial() {
        return Partial;
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        xml.printArg("midpoint", M.getName());
        if (Partial) {
            xml.printArg("partial", "true");
        }
        if (Filled) {
            xml.printArg("filled", "true");
        }
        if (getStart()!=null) {
            xml.printArg("start", getStart().getName());
        }
        if (getEnd()!=null) {
            xml.printArg("end", getEnd().getName());
        }
        if (!Obtuse) {
            xml.printArg("acute", "true");
        }
        if (!Arc) {
            xml.printArg("chord", "true");
        }
        super.printArgs(xml);
    }

    /**
     * Need to setup the Dep array.
     */
    @Override
    public ConstructionObject copy(final double x, final double y) {
        final PrimitiveCircleObject o=(PrimitiveCircleObject) super.copy(0, 0);
        if (o.Partial) {
            o.Dep=new PointObject[16];
            o.NDep=0;
        } else {
            o.Dep=null;
        }
        return o;
    }

    // public void setDefaults ()
    // { super.setDefaults();
    // setPartial(Cn.Partial);
    // }
    /**
     * A circle depends on its midpoint at least. Other circles depen on more
     * points! No circle depends on Start and End.
     */
    @Override
    public Enumeration depending() {
        super.depending();
        DL.add(M);
        return DL.elements();
    }

    /**
     * A circle will mark the midpoint as secondary parameter.
     */
    @Override
    public Enumeration secondaryParams() {
        DL.reset();
        return depset(M);
    }

    @Override
    public void toggleHidden() {
        if (Hidden) {
            Hidden=false;
        } else {
            if (Partial) {
                setPartial(false);
                Hidden=true;
            } else {
                setPartial(true);
            }
        }
    }

    public PointObject getP1() {
        return M;
    }

    @Override
    public void setFilled(final boolean flag) {
        Filled=flag;
    }

    @Override
    public boolean isFilled() {
        return Filled;
    }

    @Override
    public boolean isFilledForSelect() {
        return Filled;
    }

    @Override
    public void translate() {
        M=(PointObject) M.getTranslation();
        if (hasRange()) {
            StartPt=(PointObject) StartPt.getTranslation();
            EndPt=(PointObject) EndPt.getTranslation();
            StartPtName=StartPt.getName();
            EndPtName=EndPt.getName();
        }
    }

    public void setRange(final String s1, final String s2) {
        StartPtName=s1;
        EndPtName=s2;
        setRange((PointObject) Cn.find(StartPtName), (PointObject) Cn.find(EndPtName));
    }

    public void setRange(PointObject p1, PointObject p2) {
        StartPt=p1;
        EndPt=p2;
    }

    public PointObject getStart() {
        if ((StartPt==null)&&(StartPtName!=null)) {
            StartPt=(PointObject) Cn.find(StartPtName);
        }
        return StartPt;
    }

    public PointObject getEnd() {
        if ((EndPt==null)&&(EndPtName!=null)) {
            EndPt=(PointObject) Cn.find(EndPtName);
        }
        return EndPt;
    }

    public double getA1() {
        return A1;
    }

    public double getA2() {
        return A2;
    }

    public boolean hasRange() {
        return getStart()!=null&&getEnd()!=null;
    }

    public void clearRange() {
        StartPt=EndPt=null;
        StartPtName=EndPtName=null;
    }

    @Override
    public boolean maybeTransparent() {
        return true;
    }

    @Override
    public boolean locallyLike(final ConstructionObject o) {
        if (!(o instanceof PrimitiveCircleObject)) {
            return false;
        }
        return (equals(X, ((PrimitiveCircleObject) o).X)
                &&equals(Y, ((PrimitiveCircleObject) o).Y)&&equals(R,
                ((PrimitiveCircleObject) o).R));
    }

    public boolean getArc() {
        return Arc;
    }

    public void setArc(final boolean flag) {
        Arc=flag;
    }

    public void detectArc3Pts() {
        /* L'arc défini par trois points est issue d'une macro-construction.
         * Par nécessité, les extrémités de l'arc sont des points construits
         * qui "Sautent" en s'échangeant lorsque le cercle support change de
         * "courbure". La formule en x se trouvant par exemple dans le getStart
         * de l'arc est : if(a(C,A,B)<180,x(A),x(C))
         * Pour tester le produit vectoriel OA^OC, il faut récupérer les "vraies"
         * extrémités A et C, à savoir les points qui ont servi à créer l'arc.
         * Cela se fait par une regExp.
         */
        if (hasRange()) {
            String st=getStart().getEX();
            String reg="\\Qif(a(\\E[^,]*,[^,]*,[^\\)]*\\Q)<180,x(\\E([^\\)]*)\\Q),x(\\E([^\\)]*)\\)\\)";
            Matcher m=Pattern.compile(reg).matcher(st);
//            if (m.find()) {
//                PointObject RealStartPt=(PointObject) Cn.find(m.group(1));
//                PointObject RealEndPt=(PointObject) Cn.find(m.group(2));
//                if (getStart().equals(RealStartPt)) {
//                    RealCorrespondingStartPt=StartPt;
//                } else {
//                    RealCorrespondingStartPt=EndPt;
//                }
//                isArc3pts=true;
//            System.out.println(RealCorrespondingStartPt.getName()+" "+getStart().getName());
//            }
            isArc3pts=m.find();
        }

    }

// Pour les arcs de cercles, calcule l'angle A=SOE avec S=start et E=end
    public double computeSOE() {
        A1=Math.atan2(getStart().getY()-Y, getStart().getX()-X);
        if (A1<0) {
            A1+=2*Math.PI;
        }
        A2=Math.atan2(getEnd().getY()-Y, getEnd().getX()-X);
        if (A2<0) {
            A2+=2*Math.PI;
        }
        if (A2<A1) {
            A2+=2*Math.PI;
        }
        A=A2-A1;



        if (!Obtuse&&A>Math.PI+1e-10) {
            A1=A2;
            if (A1>=2*Math.PI) {
                A1-=2*Math.PI;
            }
            A=2*Math.PI-A;
            A2=A1+A;


        }
        if (Partial) {
            A1-=10/180.0*Math.PI;
            A+=20/180.0*Math.PI;
        }
        return A;
    }

    // Pour les arcs de cercles, calcule l'angle A=SOP avec S=start et P=le point passé en param
    public double computeSOP(PointObject P) {
        double AS=Math.atan2(getStart().getY()-Y, getStart().getX()-X);
        if (AS<0) {
            AS+=2*Math.PI;
        }
        double AP=Math.atan2(P.getY()-Y, P.getX()-X);
        if (AP<0) {
            AP+=2*Math.PI;
        }
        if (AP<AS) {
            AP+=2*Math.PI;
        }
        double AA=AP-AS;
        if (!Obtuse&&AA>Math.PI+1e-10) {
            AA=2*Math.PI-AA;
        }
        if (Partial) {
            AA+=20/180.0*Math.PI;
        }
        return AA;
    }

    /**
     * Test, if the projection of (x,y) to the arc contains that point.
     */
    public boolean contains(final double x, final double y) {
        if (!hasRange()) {
            return true;
        }
        computeSOE();
        double a=Math.atan2(y-Y, x-X);
        if (a<0) {
            a+=2*Math.PI;
        }
        double d=a-A1;
        if (d<0) {
            d+=2*Math.PI;
        }
        return d<A+0.0001;
    }

    public void project(final PointObject P) {

        double dx=P.getX()-getX(), dy=P.getY()-getY();
        final double r=Math.sqrt(dx*dx+dy*dy);
        double X=0, Y=0;
        if (r<1e-10) {
            X=getX()+getR();
            Y=getY();
        } else {
            X=getX()+dx/r*getR();
            Y=getY()+dy/r*getR();
        }
        double Alpha=Math.atan2(P.getY()-getY(), P.getX()-getX());
        if (hasRange()&&getStart()!=P&&getEnd()!=P) {
            if (Alpha<0) {
                Alpha+=2*Math.PI;
            }
            computeSOE();
            final double a1=getA1(), a2=getA2();
            if (Alpha<a1) {
                Alpha+=2*Math.PI;
            }
            if (Alpha>a2) {
                if (2*Math.PI-(Alpha-a1)<Alpha-a2) {
                    Alpha=a1;
                } else {
                    Alpha=a2;
                }
            }
            X=getX()+getR()*Math.cos(Alpha);
            Y=getY()+getR()*Math.sin(Alpha);
        }
        P.setXY(X, Y);
        P.setA(Alpha);

        if (hasRange()&&(StartPt!=P)&&(EndPt!=P)&&(P.isPointOn())) {
            double soe=computeSOE();
            double sop=computeSOP(P);
            if (soe!=0) {
                P.setAlpha(sop/soe);
//                if (isArc3pts&&!StartPt.equals(RealCorrespondingStartPt)) {
//                    P.setAlpha(1-sop/soe);
//                }
            }
        }
    }

    @Override
    public int getDistance(final PointObject P) {
        final double dx=P.getX()-getX(), dy=P.getY()-getY();
        final double r=Math.sqrt(dx*dx+dy*dy);
        double X=0, Y=0;
        if (r<1e-10) {
            X=getX()+getR();
            Y=getY();
        } else {
            X=getX()+dx/r*getR();
            Y=getY()+dy/r*getR();
        }
        double Alpha=Math.atan2(P.getY()-getY(), P.getX()-getX());
        if (hasRange()&&getStart()!=P&&getEnd()!=P) {
            if (Alpha<0) {
                Alpha+=2*Math.PI;
            }
            computeSOE();
            final double a1=getA1(), a2=getA2();
            if (Alpha<a1) {
                Alpha+=2*Math.PI;
            }
            if (Alpha>a2) {
                if (2*Math.PI-(Alpha-a1)<Alpha-a2) {
                    Alpha=a1;
                } else {
                    Alpha=a2;
                }
            }
            X=getX()+getR()*Math.cos(Alpha);
            Y=getY()+getR()*Math.sin(Alpha);
        }
        final double d=Math.sqrt((P.getX()-X)*(P.getX()-X)
                +(P.getY()-Y)*(P.getY()-Y));
        return (int) Math.round(d*Cn.getPixel());
    }

    public double det(PointObject M, PointObject N) {
        double determinant=(M.getX()-X)*(N.getY()-Y)-(M.getY()-Y)*(N.getX()-X);
        return determinant;
    }

    public void project(final PointObject P, final double alpha) {

        if (hasRange()&&(StartPt!=P)&&(EndPt!=P)&&(P.isPointOn())) {
            detectArc3Pts();
            double soe=computeSOE();
            double k=P.getAlpha();
//            if (isArc3pts&&!StartPt.equals(RealCorrespondingStartPt)) {
//                k=1-k;
//            }
            double sop=soe*k;
            // Rotation du vecteur OS d'angle sop :
            PointObject start=getStart();
            double xx=(start.getX()-getX())*Math.cos(sop)-(start.getY()-getY())*Math.sin(sop);
            double yy=(start.getX()-getX())*Math.sin(sop)+(start.getY()-getY())*Math.cos(sop);
            double unit=getR()/(Math.sqrt((start.getX()-getX())*(start.getX()-getX())+(start.getY()-getY())*(start.getY()-getY())));
            double x0=getX()+xx*unit, y0=getY()+yy*unit;
//            if (isArc3pts&&!StartPt.equals(RealCorrespondingStartPt)) {
//
//            }
            // Gestion de la continuité du déplacement d'un point sur objet sur un arc de cercle
            // Si les extrémités de l'arc s'échangent, il faut un coef. barycentrique égal à
            // 1-k :
            if (isArc3pts) {
                sop=soe*(1-k);
                xx=(start.getX()-getX())*Math.cos(sop)-(start.getY()-getY())*Math.sin(sop);
                yy=(start.getX()-getX())*Math.sin(sop)+(start.getY()-getY())*Math.cos(sop);
                unit=getR()/(Math.sqrt((start.getX()-getX())*(start.getX()-getX())+(start.getY()-getY())*(start.getY()-getY())));
                double x1=getX()+xx*unit, y1=getY()+yy*unit;
                double d0=(P.getX()-x0)*(P.getX()-x0)+(P.getY()-y0)*(P.getY()-y0);
                double d1=(P.getX()-x1)*(P.getX()-x1)+(P.getY()-y1)*(P.getY()-y1);
                if (d0>d1) {
                    x0=x1;
                    y0=y1;
                }
            }



            // Coordonnées du point P :
            P.setXY(x0, y0);

            if (!Obtuse) {
                // S'il s'agit d'un arc180, il faut que Start,P et End
                // soient sur l'arc dans le même ordre, on étudie donc
                // le produit des déterminants :
                if (det(getStart(), getEnd())*det(getStart(), P)<0) {
                    k=-k;
                    P.setAlpha(k);
                    sop=soe*k;

                    // Rotation du vecteur OS d'angle sop :
                    start=getStart();
                    xx=(start.getX()-getX())*Math.cos(sop)-(start.getY()-getY())*Math.sin(sop);
                    yy=(start.getX()-getX())*Math.sin(sop)+(start.getY()-getY())*Math.cos(sop);

                    // Coordonnées du point P :
                    P.setXY(getX()+xx*unit, getY()+yy*unit);
                }
            }
//            System.out.println("**** detSOE="+det(getStart(), getEnd())+"  :  detSOP="+det(getStart(), P));
//            System.out.println("k="+k);


        } else {
            final double dx=P.getX()-getX(), dy=P.getY()-getY();
            final double r=Math.sqrt(dx*dx+dy*dy);
            double X=0, Y=0;
            if (r<1e-10) {
                X=getX()+getR();
                Y=getY();
            } else {
                X=getX()+dx/r*getR();
                Y=getY()+dy/r*getR();
            }
            if (hasRange()&&getStart()!=P&&getEnd()!=P) {
                double Alpha=P.getAlpha();
                if (Alpha<0) {
                    Alpha+=2*Math.PI;
                }
                if (Alpha>=2*Math.PI) {
                    Alpha-=2*Math.PI;
                }
                computeSOE();
                final double a1=getA1(), a2=getA2();
                if (Alpha<a1) {
                    Alpha+=2*Math.PI;
                }
                if (Alpha>a2) {
                    if (2*Math.PI-(Alpha-a1)<Alpha-a2) {
                        Alpha=a1;
                    } else {
                        Alpha=a2;
                    }
                }
                P.setA(Alpha);
                X=getX()+getR()*Math.cos(Alpha);
                Y=getY()+getR()*Math.sin(Alpha);
            } else {
                X=getX()+getR()*Math.cos(alpha);
                Y=getY()+getR()*Math.sin(alpha);
            }
            P.setXY(X, Y);
        }
    }

    public double containsInside(final PointObject P) {
        final double dx=P.getX()-X, dy=P.getY()-Y;
        final double r=Math.sqrt(dx*dx+dy*dy);
        if (r<R*(1-1e-10)) {
            return 1;
        }
        if (r<R*(1+1e-10)) {
            return 0.5;
        }
        return 0;
    }

    public boolean keepInside(final PointObject P) {
        final double dx=P.getX()-X, dy=P.getY()-Y;
        final double r=Math.sqrt(dx*dx+dy*dy);
        double f=1;
        if (Filled&&ColorType==THIN) {
            f=0.9999;
        }
        if (r<R*f||R<1e-10) {
            return true;
        }
        P.setXY(X+dx/r*(R*f), Y+dy/r*(R*f));
        return false;
    }

    public boolean canInteresectWith(final ConstructionObject o) {
        return true;
    }

    public void repulse(final PointObject P) {
        project(P);
    }
}
