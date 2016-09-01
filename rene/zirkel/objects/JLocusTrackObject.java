/*

Copyright 2006 Eric Hakenholz

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

import java.util.Enumeration;
import java.util.Vector;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.PolygonDrawer;
import rene.zirkel.structures.Coordinates;

/**
 *
 * @author erichake
 */
public class JLocusTrackObject extends TrackObject implements HeavyObject {

    Coordinates C;
    PolygonDrawer pd;
    double c0, r0, c, r;
    ExpressionObject EO;
    Vector RefreshList=new Vector();
    Vector DriverObjectList=new Vector();
    boolean Special=false;
    double cx, cy, ww, wh;
    long time=0;

    public JLocusTrackObject(final Construction c, final ConstructionObject p,
            final ConstructionObject po[], final int pn,
            final ConstructionObject o, final PointObject pm) {

        super(c);
        P=p;
        PN=pn;
        for (int i=0; i<PN; i++) {
            PO[i]=po[i];
        }
        O=o;
        PM=pm;
        validate();
        updateText();
        cx=c.getX();
        cy=c.getY();
        ww=c.getW();
        wh=c.getH();
        searchDependencies(c);

    }

    @Override
    public void searchDependencies(final Construction c) {
        RefreshList.clear();
        DriverObjectList.clear();
        if (O instanceof SegmentObject) {
            searchDependencies(c, this, PM);
        } else if (O instanceof ExpressionObject) {
            searchDependencies(c, this, null);
        } else if (O instanceof RayObject) {
            searchDependencies(c, this, PM);
        } else if (O instanceof PrimitiveLineObject) {
            searchDependencies(c, this, PM);
        } else if (O instanceof PrimitiveCircleObject) {
            searchDependencies(c, this, PM);
        } else if (O instanceof QuadricObject) {
            searchDependencies(c, this, PM);
        } else if (O instanceof TrackObject) {
            searchDependencies(c, this, PM);
        } else if (O instanceof AreaObject) {
            searchDependencies(c, this, PM);
        } else if (O instanceof FunctionObject) {
            searchDependencies(c, this, PM);
        }
        NeedsRecompute=true;
    }

    public void searchDependencies(final Construction c,
            final ConstructionObject o, final ConstructionObject avoid) {

        if (o.RekValidating) {
            return;
        } // should not happen!
        o.RekValidating=true;
        Enumeration e=c.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).setRekFlag(false);
        }
        recursiveSearchDependencies(o, avoid);
        e=c.elements();

        while (e.hasMoreElements()) {
            final ConstructionObject oc=(ConstructionObject) e.nextElement();
            if (oc.isRekFlag()) {
                RefreshList.addElement(oc);
                if ((!(oc==P))&&(oc.isDriverObject())) {
                    DriverObjectList.addElement(oc);
                }
            }
        }

        o.RekValidating=false;
    }

    public void recursiveSearchDependencies(final ConstructionObject o,
            final ConstructionObject avoid) {

        if (o.isRekFlag()||o==avoid) {
            return;
        }
        o.setRekFlag(true);
        final ConstructionObject d[]=o.getDepArray();
        for (final ConstructionObject element : d) {
            recursiveSearchDependencies(element, avoid);
        }
    }

    @Override
    public boolean needsToRecompute() {

        boolean needs=false;
        final Enumeration pl=DriverObjectList.elements();
        while (pl.hasMoreElements()) {
            final DriverObject oc=(DriverObject) pl.nextElement();
            if (oc.somethingChanged()) {
                Global.addClearList(oc);
                // ConstructionObject oo=(ConstructionObject) oc;
                // System.out.println("needsRecompute :"+oo.getName());
                needs=true;
            }
        }

        if ((Cn.getX()!=cx)||(Cn.getY()!=cy)||(Cn.getW()!=ww)
                ||(Cn.getH()!=wh)) {
            cx=Cn.getX();
            cy=Cn.getY();
            ww=Cn.getW();
            wh=Cn.getH();
            needs=true;
        }

        if (NeedsRecompute) {
            NeedsRecompute=false;
            return true;
        }

        return needs;
    }

    public synchronized void refresh() {
        final Enumeration e=RefreshList.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject oc=(ConstructionObject) e.nextElement();
            oc.NeedsRecompute=true;
            oc.validate();
        }
    }
    private static double a=5, b=6, xmin=-b+0.0001;

    private static void setConstants(final Construction c) {
        final double dx=c.getW()*2;
        final double dy=c.getH();
        final double r=Math.sqrt(dx*dx+dy*dy);
        a=r;
        b=1.5*a;
        xmin=-b+(b-a)/1000;
    }

    public static double fline(final double x) {
        if (Math.abs(x)<a) {
            return x;
        } else {
            final double s=Math.signum(x);
            return s*(2*a-b)+((b-a)*(b-a))/(s*b-x);
        }
    }
    private Coordinates oldC=new Coordinates();

    @Override
    public void addCoordinates(final Vector v, final ConstructionObject p) {
        if (p.valid()) {
            oldC=new Coordinates(p.getX(), p.getY());
            v.addElement(oldC);
        } else {
            if (!Double.isNaN(oldC.X)) {
                oldC=new Coordinates();
                v.addElement(oldC);
            }
        }
    }

    public synchronized void docomputeCircle() {
        double dt;
        double a1;

        final PrimitiveCircleObject CO=(PrimitiveCircleObject) O;
        final double x=CO.getX(), y=CO.getY(), RO=CO.getR();

        if (CO.hasRange()) {
            a1=CO.getA1();
            final double a2=CO.getA2();
            double d=a2-a1;
            while (d<0) {
                d+=2*Math.PI;
            }
            while (d>=2*Math.PI) {
                d-=2*Math.PI;
            }
            dt=d*DMin;
            // I really don't understand why this is necessary for arc locuses :
            a1+=0.00000000001;
        } else {
            dt=2*Math.PI*DMin;
            a1=0;
        }

        final int nbsteps=(int) Math.round(1/DMin)+1;
        for (int i=0; i<nbsteps; i++) {
            PM.move(x+RO*Math.cos(a1+i*dt), y+RO
                    *Math.sin(a1+i*dt));
            refresh();
            addCoordinates(V, P);
        }

        // Try to do something with points inside circle... I will see later !

        // if (PM.isInside()) {
        // Special=true;
        // double rvar=r/nbsteps;
        // r=0;
        // for (int i=0; i<nbsteps; i++) {
        // PM.move(x+r*Math.cos(a1+i*dt), y+r*Math.sin(a1+i*dt));
        // refresh();
        // if (P.valid()) {
        // addCoordinates(V, P);
        // }
        // r+=rvar;
        // }
        // } else {
        //
        // for (int i=0; i<nbsteps; i++) {
        // PM.move(x+r*Math.cos(a1+i*dt), y+r*Math.sin(a1+i*dt));
        // refresh();
        // if (P.valid()) {
        // addCoordinates(V, P);
        // }
        // }
        // }

    }

    public synchronized void docomputeLine() {
        Cn.shouldSwitch(false);
        final PrimitiveLineObject l=(PrimitiveLineObject) O;
        // System.out.println("zc.DX="+zc.DX+"  zc.DY="+zc.DY);
        // System.out.println("dx="+Cn.getW()*2+"  dy="+Cn.getH());
        setConstants(Cn);
        final double da=-2*xmin*DMin;
        final double lx=l.getX(), ly=l.getY(), ldx=l.getDX(), ldy=l.getDY();
        double delta;
        final int nbsteps=(int) Math.round(1/DMin)+1;
        for (int i=0; i<nbsteps; i++) {
            delta=fline(xmin+i*da);
            PM.move(lx+ldx*delta, ly+ldy*delta);
            refresh();
            addCoordinates(V, P);
        }
    }

    public synchronized void docomputeRay() {
        Cn.shouldSwitch(false);
        final PrimitiveLineObject l=(PrimitiveLineObject) O;

        setConstants(Cn);
        final double da=-xmin*DMin;
        final double lx=l.getX(), ly=l.getY(), ldx=l.getDX(), ldy=l.getDY();
        double delta;
        final int nbsteps=(int) Math.round(1/DMin)+1;
        for (int i=0; i<nbsteps; i++) {
            delta=fline(i*da);
            PM.move(lx+ldx*delta, ly+ldy*delta);
            refresh();
            addCoordinates(V, P);
        }
    }

    public synchronized void docomputeSegments() {
        Cn.shouldSwitch(false);
        final PrimitiveLineObject l=(PrimitiveLineObject) O;
        final double r=((SegmentObject) l).getLength();
        final double lx=l.getX(), ly=l.getY(), ldx=r*l.getDX()*DMin, ldy=r
                *l.getDY()*DMin;


        final int nbsteps=(int) Math.round(1/(DMin))+1;

        for (int i=0; i<nbsteps; i++) {
            PM.move(lx+ldx*i, ly+ldy*i);
            refresh();
            addCoordinates(V, P);
        }
    }

    /**
     * Compute the locus of a point on conic The idea is to consider the first
     * point A of the conic (first of five points) then imagine a unit circle
     * centered on A, imagine a point B on this circle, then compute
     * intersections between line (AB) and the conic. This will give continuous
     * points set on the conic, then refresh to get the locus point.
     *
     * @param zc
     */
    public synchronized void docomputeQuadric() {
        Cn.shouldSwitch(false);
        final QuadricObject q=(QuadricObject) O;

        final double A=q.X[0], B=q.X[1], C=q.X[2], D=q.X[3], E=q.X[4], F=q.X[5];
        final double dt=Math.PI*DMin;
        // get the first of the five points defining the conic :
        final double X1=q.P[0].getX();
        final double Y1=q.P[0].getY();

        double M=0, N2=0, P1=0;
        final int nbsteps=(int) Math.round(1/DMin)+1;
        // System.out.println("*******************");
        for (int i=0; i<nbsteps; i++) {
            M=-Math.sin(i*dt+0.01);
            N2=Math.cos(i*dt+0.01);
            P1=-(M*X1+N2*Y1);

            double x1=0, x2=0, y1=0, y2=0;
            final double part1=-2*B*M*P1-C*N2*N2+D*M*N2+E
                    *N2*P1;
            final double part2=Math.abs(N2)
                    *Math.sqrt(-2*M*D*N2*C+4*P1*D*A*N2+4
                    *P1*M*B*C+4*E*M*N2*F-2*E*P1
                    *N2*C-2*E*P1*M*D-4*M*M*B*F
                    -4*P1*P1*A*B-4*A*N2*N2*F+N2
                    *N2*C*C+M*M*D*D+E*E*P1*P1);

            final double part3=2*A*N2*N2+2*B*M*M+(-2*E)*M
                    *N2;
            x1=(part1+part2)/part3;
            y1=(-M*x1-P1)/N2;
            x2=(part1-part2)/part3;
            y2=(-M*x2-P1)/N2;

            if (((x2-x1)/N2)<0) {
                final double c1=x1, r1=y1;
                x1=x2;
                y1=y2;
                x2=c1;
                y2=r1;
            }

            if ((Math.abs(X1-x1)<1e-10)&&(Math.abs(X1-x1)<1e-10)) {
                PM.move(x2, y2);
            } else {
                PM.move(x1, y1);
            }

            refresh();
            addCoordinates(V, P);
        }

    }

    public synchronized void docomputeExpression() {
        Cn.shouldSwitch(false);
        EO=(ExpressionObject) O;
        if (!EO.isSlider()) {
            return;
        }
        final int nbsteps=(int) Math.round(1/DMin)+1;
        double oldCursor=EO.getSliderPosition();
        for (int i=0; i<nbsteps; i++) {
            EO.setSliderPosition(DMin*i);
            refresh();
            addCoordinates(V, P);
        }
        EO.setSliderPosition(oldCursor);
    }

    public synchronized void docomputeTrack() {
        Cn.shouldSwitch(false);
        final JLocusTrackObject TR=(JLocusTrackObject) O;
        if (DMin!=TR.DMin) {
            TR.DMin=DMin;
            TR.compute();
        }
        final Enumeration e=TR.V.elements();
        while (e.hasMoreElements()) {
            C=(Coordinates) e.nextElement();
            PM.move(C.X, C.Y);
            refresh();
            addCoordinates(V, P);
        }
    }

    public synchronized void docomputeFunction() {
        Cn.shouldSwitch(false);
        final FunctionObject FO=(FunctionObject) O;
        final Enumeration e=FO.V.elements();
        while (e.hasMoreElements()) {
            C=(Coordinates) e.nextElement();
            PM.move(C.X, C.Y);
            refresh();
            addCoordinates(V, P);
        }
    }

    public synchronized void docomputeArea() {
        Cn.shouldSwitch(false);
        final AreaObject poly=(AreaObject) O;
        final int NVertex=poly.V.size();
        int nbsteps=(int) Math.round(1/(DMin*NVertex));
        if (nbsteps<2) {
            nbsteps=2;
        }
        double xA, xB, yA, yB;
        final PointObject ORIGIN=(PointObject) poly.V.get(0);
        PointObject A=ORIGIN;
        PointObject B=null;
        for (int n=1; n<NVertex; n++) {
            B=(PointObject) poly.V.get(n);
            xA=A.getX();
            yA=A.getY();
            xB=B.getX();
            yB=B.getY();
            for (int i=0; i<=nbsteps; i++) {
                PM.move(xA+i*(xB-xA)/nbsteps, yA+i*(yB-yA)
                        /nbsteps);
                refresh();
                addCoordinates(V, P);
            }
            A=B;
        }
        xA=A.getX();
        yA=A.getY();
        xB=ORIGIN.getX();
        yB=ORIGIN.getY();
        for (int i=0; i<=nbsteps; i++) {
            PM.move(xA+i*(xB-xA)/nbsteps, yA+i*(yB-yA)/nbsteps);
            refresh();
            addCoordinates(V, P);
        }

        // double r=((SegmentObject) l).getLength();
        // double lx=l.getX(), ly=l.getY(), ldx=r*l.getDX()*DMin,
        // ldy=r*l.getDY()*DMin;
        //
        // int nbsteps=(int) Math.round(1/DMin)+1;
        // for (int i=0; i<nbsteps; i++) {
        // PM.move(lx+ldx*i, ly+ldy*i);
        // refresh();
        // if (P.valid()) {
        // addCoordinates(V, P);
        // }
        // }
    }




    @Override
    public synchronized void compute(final ZirkelCanvas zc) {
        compute();
    }

    public synchronized void compute() {
        if (Fixed&&!StartFix) {
            return;
        }

        // System.out.println("compute track");

        V=new Vector();
        oldC=new Coordinates();
        StartFix=false;
        double x=0, y=0;
        if (PM!=null) {
            x=PM.getX();
            y=PM.getY();
        }

        Cn.clearSwitches();
        Cn.shouldSwitch(true);
        DontProject=true;

        // Running on a segment :
        if (O instanceof SegmentObject) {
            docomputeSegments();
            // Running on a conic :
        } else if (O instanceof QuadricObject) {
            docomputeQuadric();
            // Running on an track :
        } else if (O instanceof JLocusTrackObject) {
            docomputeTrack();
            // Running on an area :
        } else if (O instanceof AreaObject) {
            docomputeArea();
            // Running on an expression :
        } else if (O instanceof ExpressionObject) {
            docomputeExpression();
            // Running on a ray :
        } else if (O instanceof RayObject) {
            docomputeRay();
            // Running on a circle :
        } else if (O instanceof PrimitiveCircleObject) {
            docomputeCircle();
            // Running on a line :
        } else if (O instanceof PrimitiveLineObject) {
            docomputeLine();
        } else if (O instanceof FunctionObject) {
            docomputeFunction();
        }

        DontProject=false;
        // zc.getConstruction().shouldSwitch(false);
        // zc.getConstruction().clearSwitches();
        if (PM!=null) {
            PM.move(x, y);
            refresh();
        }

        Cn.dovalidate();

        // zc.dovalidate();

    }

    // public void validate() {
    // compute();
    // }
    // Paint the track
    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {

        if (!Valid||mustHide(zc)) {
            return;
        }
        Coordinates C;
        final Enumeration e=V.elements();
        double c, r;
        if (indicated()) {
            final boolean sel=P.indicated();
            P.setIndicated(true);
            g.setColor(P);
            P.setIndicated(sel);
        } else {
            g.setColor(this);
        }

        if (Special) {
            while (e.hasMoreElements()) {
                C=(Coordinates) e.nextElement();
                PointObject.drawPoint(g, zc, this, C.X, C.Y, Type);
            }
        } else {
            final PolygonDrawer pd=new PolygonDrawer(false,g, this);

            if (e.hasMoreElements()) {

                C=(Coordinates) e.nextElement();
                c0=zc.col(C.X);
                r0=zc.row(C.Y);
                pd.startPolygon(c0, r0);

            }

            while (e.hasMoreElements()) {
                C=(Coordinates) e.nextElement();
                c=zc.col(C.X);
                r=zc.row(C.Y);
                if (Math.abs(pd.c()-c)<1000&&Math.abs(pd.r()-r)<1000) {
                    pd.drawTo(c, r);
                } else {
                    pd.finishPolygon();
                    pd.startPolygon(c, r);
                }
            }

            pd.finishPolygon();
        }

    }

    @Override
    public boolean isSpecial() {
        return Special;
    }

    @Override
    public void setSpecial(final boolean f) {
        Special=f;
    }

    @Override
    public boolean equals(final ConstructionObject o) {
        if (!(o instanceof JLocusTrackObject)||!o.valid()) {
            return false;
        }
        JLocusTrackObject loc=(JLocusTrackObject) o;
        if (V.size()!=loc.V.size()) {
            return false;
        }
        final Enumeration e1=V.elements();
        final Enumeration e2=loc.V.elements();
        while (e1.hasMoreElements()) {
            Coordinates C1=(Coordinates) e1.nextElement();
            Coordinates C2=(Coordinates) e2.nextElement();
            if ((Math.abs(C1.X-C2.X)>1e-8)||Math.abs(C1.Y-C2.Y)>1e-8) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ConstructionObject copy(final double x, final double y) {
        PointObject newPM=null;
        if (PM!=null) {
            newPM=(PointObject) PM.getTranslation();
        }

        final JLocusTrackObject jl=new JLocusTrackObject(Cn.getTranslation(),
                P.getTranslation(), PO, PN, O.getTranslation(), newPM);
        jl.setTargetDefaults();
        return jl;
    }
    // public void translate() {
    // if (PM!=null) {
    // PM=(PointObject) PM.getTranslation();
    // }
    //
    // O=O.getTranslation();
    // P=P.getTranslation();
    //
    // searchDependencies(Cn.getTranslation());
    // }
}
