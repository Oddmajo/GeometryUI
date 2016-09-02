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

// file: QuadricObject.java
import java.util.ArrayList;
import java.util.Enumeration;

import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.expression.Quartic;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.PolygonDrawer;
import rene.zirkel.structures.Complex;
import rene.zirkel.structures.CoordinatesXY;

public class QuadricObject extends ConstructionObject implements PointonObject,
        MoveableObject {

    public PointObject P[];
    static Count N=new Count();
    public double X[];

    public QuadricObject(final Construction c, final PointObject p[]) {
        super(c);
        P=p;
        validate();
        updateText();
    }

    @Override
    public void setDefaults() {
        setShowName(Global.getParameter("options.circle.shownames", false));
        setShowValue(Global.getParameter("options.circle.showvalues", false));
        setColor(Global.getParameter("options.circle.color", 0), Global.getParameter("options.circle.pcolor", (ExpressionColor) null, this));

        setColorType(Global.getParameter("options.circle.colortype", 0));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        setSolid(Cn.Solid);
        setLarge(Cn.LargeFont);
        setBold(Cn.BoldFont);
        setPartial(Cn.Partial);
    }

    @Override
    public void setTargetDefaults() {
        setShowName(Global.getParameter("options.circle.shownames", false));
        setShowValue(Global.getParameter("options.circle.showvalues", false));
        setColor(Global.getParameter("options.circle.color", 0), Global.getParameter("options.circle.pcolor", (ExpressionColor) null, this));
        setColorType(Global.getParameter("options.circle.colortype", 0));
    }

    @Override
    public String getTag() {
        return "Quadric";
    }

    @Override
    public int getN() {
        return N.next();
    }

    @Override
    public void updateText() {
        try {
            final String Names[]=new String[P.length];
            for (int i=0; i<P.length; i++) {
                Names[i]=P[i].getName();
            }
            setText(textAny(Global.name("text.quadric"), Names));
        } catch (final Exception e) {
        }
    }
    private static final double cub2=Math.pow(2, 1.0/3.0);

    public static ArrayList<CoordinatesXY> intersect(final QuadricObject quad1,
            final QuadricObject quad2) {
        ArrayList<CoordinatesXY> points=new ArrayList<CoordinatesXY>();

// coeffs de x^2, xy, y^2, x, y et Cte :
        double a=quad1.X[0], b=quad1.X[4], c=quad1.X[1], d=quad1.X[2], e=quad1.X[3], f=quad1.X[5];
        double aa=quad2.X[0], bb=quad2.X[4], cc=quad2.X[1], dd=quad2.X[2], ee=quad2.X[3], ff=quad2.X[5];

//                System.out.println("*************");
//        System.out.println("a="+a);
//        System.out.println("b="+b);
//        System.out.println("c="+c);
//        System.out.println("d="+d);
//        System.out.println("e="+e);
//        System.out.println("f="+f);
//        System.out.println("aa="+aa);
//        System.out.println("bb="+bb);
//        System.out.println("cc="+cc);
//        System.out.println("dd="+dd);
//        System.out.println("ee="+ee);
//        System.out.println("ff="+ff);


        double k1=-aa*b*bb*c+a*bb*bb*c+aa*aa*c*c+aa*b*b*cc-a*b*bb*cc-2*a*aa*c*cc+a*a*cc*cc;
        double k2=bb*bb*c*d-b*bb*cc*d-2*aa*c*cc*d+2*a*cc*cc*d-b*bb*c*dd+2*aa*c*c*dd+b*b*cc*dd-2*a*c*cc*dd-aa*bb*c*e+2*aa*b*cc*e-a*bb*cc*e-aa*b*c*ee+2*a*bb*c*ee-a*b*cc*ee;
        double k3=cc*cc*d*d-2*c*cc*d*dd+c*c*dd*dd-bb*cc*d*e-bb*c*dd*e+2*b*cc*dd*e+aa*cc*e*e+2*bb*c*d*ee-b*cc*d*ee-b*c*dd*ee-aa*c*e*ee-a*cc*e*ee+a*c*ee*ee+bb*bb*c*f-b*bb*cc*f-2*aa*c*cc*f+2*a*cc*cc*f-b*bb*c*ff+2*aa*c*c*ff+b*b*cc*ff-2*a*c*cc*ff;
        double k4=cc*dd*e*e-cc*d*e*ee-c*dd*e*ee+c*d*ee*ee+2*cc*cc*d*f-2*c*cc*dd*f-bb*cc*e*f+2*bb*c*ee*f-b*cc*ee*f-2*c*cc*d*ff+2*c*c*dd*ff-bb*c*e*ff+2*b*cc*e*ff-b*c*ee*ff;
        double k5=-cc*e*ee*f+c*ee*ee*f+cc*cc*f*f+cc*e*e*ff-c*e*ee*ff-2*c*cc*f*ff+c*c*ff*ff;


        double u1=k2/(4*k1);
        double u2=k2*k2/(4*k1*k1)-2*k3/(3*k1);
        double u3=k2*k2/(2*k1*k1)-4*k3/(3*k1);
        double u4=-k2*k2*k2/(k1*k1*k1)+4*k2*k3/(k1*k1)-8*k4/k1;



        double p1=k3*k3-3*k2*k4+12*k1*k5;
        double p2=2*k3*k3*k3-9*k2*k3*k4+27*k1*k4*k4+27*k2*k2*k5-72*k1*k3*k5;

//        System.out.println("*************");
//        System.out.println("k1="+k1);
//        System.out.println("k2="+k2);
//        System.out.println("k3="+k3);
//        System.out.println("k4="+k4);
//        System.out.println("k5="+k5);

        Complex q1=new Complex(-4*p1*p1*p1+p2*p2).sqrt();
        q1=Complex.plus(q1, new Complex(p2, 0));
        q1=q1.sqrt3();

//        System.out.println("q1="+q1.real()+" + "+q1.img()+" I");

        Complex r1=Complex.div(new Complex(cub2*p1), Complex.mult(new Complex(3*k1), q1));
        r1=Complex.plus(r1, Complex.div(q1, new Complex(3*cub2*k1)));

        Complex sa=Complex.plus(new Complex(u2), r1);
        sa=Complex.div(sa.sqrt(), 2);


        Complex sb=Complex.minus(new Complex(u3), r1);
        sb=Complex.minus(sb, Complex.div(new Complex(u4), Complex.mult(8, sa)));
        sb=Complex.div(sb.sqrt(), 2);

        Complex sc=Complex.minus(new Complex(u3), r1);
        sc=Complex.plus(sc, Complex.div(new Complex(u4), Complex.mult(8, sa)));
        sc=Complex.div(sc.sqrt(), 2);

        Complex[] X=new Complex[4];
        Complex cu1=new Complex(-u1);

        X[0]=Complex.minus(cu1, sa);
        X[0]=Complex.minus(X[0], sb);

        X[1]=Complex.minus(cu1, sa);
        X[1]=Complex.plus(X[1], sb);

        X[2]=Complex.plus(cu1, sa);
        X[2]=Complex.minus(X[2], sc);

        X[3]=Complex.plus(cu1, sa);
        X[3]=Complex.plus(X[3], sc);

//        System.out.println("*************");
//        System.out.println("x1 = "+X[0].real()+" + "+X[0].img()+" I");
//        System.out.println("x2 = "+X[1].real()+" + "+X[1].img()+" I");
//        System.out.println("x3 = "+X[2].real()+" + "+X[2].img()+" I");
//        System.out.println("x4 = "+X[3].real()+" + "+X[3].img()+" I");

        // Recherche des ordonnées des points d'intersection :

        double A=c, B, C, AA=cc, BB, CC;
        for (int i=0; i<4; i++) {
            if (Math.abs(X[i].img())>1E-10) {
                points.add(new CoordinatesXY());
            } else {
                B=b*X[i].real()+e;
                C=a*X[i].real()*X[i].real()+d*X[i].real()+f;
                BB=bb*X[i].real()+ee;
                CC=aa*X[i].real()*X[i].real()+dd*X[i].real()+ff;

                double denom=A*BB-B*AA;
                if (Math.abs(denom)<1E-20) {
                    points.add(new CoordinatesXY());
                } else {
                    double y=(C*AA-A*CC)/denom;//formula by Dominique Tournès
                    points.add(new CoordinatesXY(X[i].real(), y));
                }






//                System.out.println("*************");
//                System.out.println("x["+i+"]="+X[i].real());
//                System.out.println("y["+i+"]="+y);
//                System.out.println("A*BB-B*AA="+(A*BB-B*AA));

            }
        }

//        System.out.println("*************");
//        for (int i=0;i<points.size();i++){
//            CoordinatesXY coords=points.get(i);
//            System.out.println("point "+i+" : "+coords.X+"   "+coords.Y);
//        }


        return points;
    }

    @Override
    public void validate() {
        for (int i=0; i<P.length; i++) {
            if (!P[i].valid()) {
                Valid=false;
                return;
            }
        }
        Valid=true;

        // Baue Koeffizientenmatrix auf (x^2,y^2,x,y,xy,1):
        final double A[][]=new double[5][6];
        for (int i=0; i<5; i++) {
            final double x=P[i].getX(), y=P[i].getY();
            A[i][0]=x*x;
            A[i][1]=y*y;
            A[i][2]=x;
            A[i][3]=y;
            A[i][4]=x*y;
            A[i][5]=1;
            double sum=0;
            for (int j=0; j<6; j++) {
                sum+=A[i][j]*A[i][j];
            }
            sum=Math.sqrt(sum);
            for (int j=0; j<6; j++) {
                A[i][j]/=sum;
            }
        }

        // Gauﬂverfahren, um auf untere Dreiecksmatrix zu kommen
        int r=0;
        final int colindex[]=new int[6]; // Index der Stufe oder -1 (keine
        // Stufe)
        // Iteration ¸ber alle Spalten:
        for (int c=0; c<6; c++) {
            if (r>=5) // Schema schon fertig
            {
                colindex[c]=-1;
                continue;
            }
            // Berechne Pivotelement mit spaltenweiser Maximumssuche
            double max=Math.abs(A[r][c]);
            int imax=r;
            for (int i=r+1; i<5; i++) {
                final double h=Math.abs(A[i][c]);
                if (h>max) {
                    max=h;
                    imax=i;
                }
            }
            if (max>1e-13) { // Vertausche Zeilen:
                if (imax!=r) {
                    final double h[]=A[imax];
                    A[imax]=A[r];
                    A[r]=h;
                }
                // Mache restliche Spalte zu 0:
                for (int i=r+1; i<5; i++) {
                    final double lambda=A[i][c]/A[r][c];
                    for (int j=c+1; j<6; j++) {
                        A[i][j]-=lambda*A[r][j];
                    }
                }
                colindex[c]=r;
                r++;
            } else {
                colindex[c]=-1;
            }
        }
        // Berechne die x-Werte:
        X=new double[6];
        for (int j=5; j>=0; j--) {
            if (colindex[j]<0) {
                X[j]=1;
            } else {
                double h=0;
                final int i=colindex[j];
                for (int k=j+1; k<6; k++) {
                    h+=A[i][k]*X[k];
                }
                X[j]=-h/A[i][j];
            }
        }
        // Normalisiere
        double sum=0;
        for (int i=0; i<=5; i++) {
            sum+=Math.abs(X[i]);
        }
        if (sum<1e-10) {
            Valid=false;
        }
        for (int i=0; i<=5; i++) {
            X[i]/=sum;
            // Ce qui suit ressemble à un gag, pourtant il semble que l'epsilon au lieu de 0 en coeffs permet
            // de surmonter les effets de bord dans des cas particuliers (ex. hyperbole equilatère/parabole)
            // sans pour autant porter atteinte à la précision des coordonnées des points d'intersections
            // qui restent fiables à 1e-12, soit la précision maximale affichée du logiciel :
            X[i]=n(X[i]);
        }
    }

    private static double n(double x) {
        if (Math.abs(x)<1E-16) {
            double sign=(Math.signum(x)<0)?-1:1;
            return 1.0E-16*sign;
        }
        return x;
    }

    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {
        if (!Valid||mustHide(zc)) {
            return;
        }

        g.setColor(this);
        // Draw the lower part of the quadrik (minus the root):
        final double start=zc.minX();
        double x=start;
        final double end=zc.maxX();
        final double h=zc.dx(zc.getOne());
        boolean valid=false, ptext=false;
        double c0=0, r0=0;
        double ctext=20, rtext=20;

        final PolygonDrawer pd=new PolygonDrawer(true,g, this);

        // Draw the lower part of the quadric (plus the root):
        while (x<=end) {
            try {
                final double y=computeLower(x);
                final double c=zc.col(x), r=zc.row(y);
                if (valid) {//en ajoutant r>0, la ligne verticale disparaissait; elle est réapparue!
                    pd.drawTo(c, r);
                    if (!ptext&&r0-r>c-c0&&zc.isInside(x, y)) {
                        ctext=c;
                        rtext=r;
                        ptext=true;
                    }
                } else {

                    pd.startPolygon(c, r);
                }
                c0=c;
                r0=r;
                valid=true;
            } catch (final RuntimeException e) {

                valid=false;
            }
            x+=h;
        }
        pd.finishPolygon();
        // Draw the upper part of the quadric (plus the root):
        x=start-2*h;
        valid=false;
        while (x<=end+2*h) {
            try {
                
                final double y=computeUpper(x);
                final double c=zc.col(x), r=zc.row(y);

                if (valid) {
                    pd.drawTo(c, r);
                    // Try to find a position for the label:
                    if (!ptext&&r0-r>c-c0&&zc.isInside(x, y)) {
                        ctext=c;
                        rtext=r;
                        ptext=true;
                    }
                } else // left edge of quadric, connect with lower part
                {
                    try {
                        final double y1=computeLower(x);
                        if (x>=start-h&&x<=end+h) {
                            g.drawLine(c, zc.row(y1), c, r, this);
                        }
                    } catch (final RuntimeException e) {
                    }
                    pd.startPolygon(c, r);
                }
                c0=c;
                r0=r;
                valid=true;
            } catch (final RuntimeException e) // no points in that range
            {
                if (valid) // we just left the right edge of the quadric
                {
                    try {
                        final double y1=computeLower(x-h);
                        if (x-h>=start-h&&x-h<=end+h) {
                            g.drawLine(c0, zc.row(y1), c0, r0, this);
                        }
                    } catch (final RuntimeException ex) {
                    }
                }
                valid=false;
            }
            x+=h;
        }
        pd.finishPolygon();
        final String s=getDisplayText();
        if (!s.equals("")) {
            g.setLabelColor(this);
            setFont(g);
            DisplaysText=true;
            TX1=ctext+zc.col(XcOffset)-zc.col(0);
            TY1=rtext+zc.row(YcOffset)-zc.row(0);
            drawLabel(g, s);
        }
    }
    static public final String Tags[]={"x^2", "y^2", "x", "y", "xy"};

    // public String getDisplayValue() {
    // String s="";
    // for (int i=0; i<5; i++) {
    // s=s+helpDisplayValue(i==0, -X[i], Tags[i]);
    // }
    // return s+"="+roundDisplay(X[5]);
    // }
    @Override
    public String getDisplayValue() {
        String s="";
        s+="("+Global.getLocaleNumber(-X[0], "length");
        s+="*";
        s+=Tags[0]+")";
        for (int i=1; i<5; i++) {
            s+="+";
            s+="("+Global.getLocaleNumber(-X[i], "length");
            s+="*";
            s+=Tags[i]+")";

            // s=s+helpDisplayValue(i==0, -X[i], Tags[i]);
        }
        return s+"="+Global.getLocaleNumber(X[5], "length");
        // return s+"="+roundDisplay(X[5]);
    }

    @Override
    public String getEquation() {
        return getDisplayValue();
    }

    @Override
    public boolean nearto(final int cc, final int rr, final ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        final int size=(int) zc.selectionSize();
        final double start=zc.minX();
        double x=start;
        final double end=zc.maxX();
        final double h=zc.dx(zc.getOne());
        while (x<=end) {
            try {
                final double y=computeUpper(x);
                final double c=zc.col(x), r=zc.row(y);
                if (Math.abs(cc-c)<=size*3/2
                        &&Math.abs(rr-r)<=size*3/2) {
                    return true;
                }
            } catch (final Exception e) {
            }
            try {
                final double y=computeLower(x);
                final double c=zc.col(x), r=zc.row(y);
                if (Math.abs(cc-c)<=size*3/2
                        &&Math.abs(rr-r)<=size*3/2) {
                    return true;
                }
            } catch (final Exception e) {
            }
            x+=h;
        }
        return false;
    }

    public double computeUpper(final double x) {
        if (Math.abs(X[1])>1e-13) {
            final double p=(X[3]+x*X[4])/X[1], q=(X[0]*x*x+X[2]
                    *x+X[5])
                    /X[1];
            final double h=p*p/4-q;
            if (h<0) {
                throw new RuntimeException("");
            }
            return -p/2+Math.sqrt(h);
        } else {
            return -(X[0]*x*x+X[2]*x+X[5])/(X[3]+X[4]*x);
        }
    }

    public double computeLower(final double x) {
        if (Math.abs(X[1])>1e-13) {
            final double p=(X[3]+x*X[4])/X[1], q=(X[0]*x*x+X[2]
                    *x+X[5])
                    /X[1];
            final double h=p*p/4-q;
            if (h<0) {
                throw new RuntimeException("");
            }
            return -p/2-Math.sqrt(h);
        } else {
            throw new RuntimeException("");
        }
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        for (int i=0; i<P.length; i++) {
            xml.printArg("point"+(i+1), P[i].getName());
        }
    }

    @Override
    public Enumeration secondaryParams() {
        DL.reset();
        for (final PointObject element : P) {
            DL.add(element);
        }
        return DL.elements();
    }

    @Override
    public Enumeration depending() {
        DL.reset();
        for (final PointObject element : P) {
//                    System.out.println(element.getName());
            DL.add(element);
        }
        return DL.elements();
    }

    @Override
    public void translate() {
        for (int i=0; i<P.length; i++) {
            P[i]=(PointObject) P[i].getTranslation();
        }
    }

    @Override
    public ConstructionObject copy(final double x, final double y) {
        try {
            final QuadricObject o=(QuadricObject) clone();
            setTranslation(o);
            o.P=new PointObject[P.length];
            for (int i=0; i<P.length; i++) {
                o.P[i]=P[i];
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

    @Override
    public boolean onlynearto(final int x, final int y, final ZirkelCanvas zc) {
        return false;
    }

    @Override
    public boolean equals(final ConstructionObject o) {
        if (!(o instanceof QuadricObject)||!o.valid()) {
            return false;
        }
        try {
            for (int i=0; i<6; i++) {
                if (!equals(X[i], ((QuadricObject) o).X[i])) {
                    return false;
                }
            }
        } catch (final RuntimeException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasUnit() {
        return false;
    }

    public void keepBaricentricCoords(final PointObject P) {
        if (!P.isPointOn()) {
            return;
        }
        if (P.BarycentricCoordsInitialzed) {
            final PointObject AA=this.P[0];
            final PointObject BB=this.P[1];
            final PointObject CC=this.P[2];
            final double xa=AA.getX(), ya=AA.getY();
            final double xb=BB.getX(), yb=BB.getY();
            final double xc=CC.getX(), yc=CC.getY();

            final double xm=xa+P.Gx*(xb-xa)+P.Gy*(xc-xa);
            final double ym=ya+P.Gx*(yb-ya)+P.Gy*(yc-ya);

            P.move(xm, ym);
        } else {
            P.computeBarycentricCoords();
        }
    }

    @Override
    public int getDistance(final PointObject P) {
        final double a=X[0], b=X[1], c=X[2], d=X[3], e=X[4], r=X[5];
        final double xc=P.getX(), yc=P.getY();
        if (Math.abs(a*xc*xc+b*yc*yc+c*xc+d*yc+e*xc*yc
                +r)<1e-13) // close enough
        {
            return 0;
        }
        final double t[]=new double[5], s[]=new double[5];
        // Coefficients for fourth order polynomial for lambda (Lagrange factor)
        // Minimize (x-xc)^2+(y-yc)^2 with a*x^2+b*y^2+c*x+d*y+e*x*y+r=0
        // Computed with Maple
        t[0]=a*e*e*d*d-4*a*b*b*c*c+4*a*e*d*b
                *c-4*b*a*a*d*d+b*c*c*e*e-c
                *Math.pow(e, 3)*d+r*Math.pow(e, 4)-8*r*e*e*b
                *a+16*r*b*b*a*a;
        t[1]=8*b*b*c*c+8*a*a*d*d-8*e*d*b*c-8
                *a*d*c*e+8*r*e*e*b+8*a*b*c*c+8*b
                *a*d*d+8*r*e*e*a-32*r*b*b*a-32*r
                *b*a*a;
        t[2]=12*e*d*c+16*r*b*b-4*b*d*d-8*r*e*e
                +4*e*e*d*yc+16*b*b*xc*c-16*b*c*c
                -16*a*d*d-4*a*c*c+16*r*a*a+16*a*a
                *d*yc+4*xc*e*e*c-8*e*d*b*xc-8*e*yc
                *b*c-8*a*d*xc*e-8*a*yc*c*e+16*a*b
                *b*xc*xc-4*a*e*e*yc*yc+16*b*a*a*yc
                *yc-4*b*xc*xc*e*e+4*Math.pow(e, 3)*yc*xc
                +64*r*b*a-16*a*b*xc*e*yc;
        t[3]=-32*r*b+8*d*d+8*c*c+16*e*d*xc+8*e
                *e*yc*yc+8*xc*xc*e*e-32*r*a-32*b*xc
                *c+16*e*yc*c-32*a*d*yc-32*a*b*xc*xc
                -32*b*a*yc*yc;
        t[4]=16*b*yc*yc+16*d*yc+16*c*xc+16*xc*e*yc
                +16*r+16*a*xc*xc;
        final int k=Quartic.solve(t, s);
        // System.out.println(k+"Solutions found.");
        double dmin=1e30, xmin=xc, ymin=yc;
        for (int i=0; i<k; i++) // Choose closest solution of Lagrange
        // equation
        {
            final double l=s[i];
            // Solve for x,y when lambda is known.
            // Computed with Maple
            final double px=-(-e*d+4*b*l*xc-2*e*l*yc-4
                    *l*l*xc+2*b*c-2*l*c)
                    /(-e*e+4*b*a-4*b*l-4*l*a+4*l*l);
            final double py=-(2*a*d+4*a*l*yc-2*l*d-4*l
                    *l*yc-2*l*xc*e-c*e)
                    /(-e*e+4*b*a-4*b*l-4*l*a+4*l*l);
            final double dist=(px-xc)*(px-xc)+(py-yc)*(py-yc);
            if (dist<dmin) {
                dmin=dist;
                xmin=px;
                ymin=py;
            }
        }

        final double dd=Math.sqrt((P.getX()-xmin)*(P.getX()-xmin)
                +(P.getY()-ymin)*(P.getY()-ymin));
        return (int) Math.round(dd*Cn.getPixel());
    }

    public void project(final PointObject P) {
        keepBaricentricCoords(P);
        final double a=X[0], b=X[1], c=X[2], d=X[3], e=X[4], r=X[5];
        final double xc=P.getX(), yc=P.getY();
        if (Math.abs(a*xc*xc+b*yc*yc+c*xc+d*yc+e*xc*yc
                +r)<1e-13) // close enough
        {
            return;
        }
        final double t[]=new double[5], s[]=new double[5];
        // Coefficients for fourth order polynomial for lambda (Lagrange factor)
        // Minimize (x-xc)^2+(y-yc)^2 with a*x^2+b*y^2+c*x+d*y+e*x*y+r=0
        // Computed with Maple
        t[0]=a*e*e*d*d-4*a*b*b*c*c+4*a*e*d*b
                *c-4*b*a*a*d*d+b*c*c*e*e-c
                *Math.pow(e, 3)*d+r*Math.pow(e, 4)-8*r*e*e*b
                *a+16*r*b*b*a*a;
        t[1]=8*b*b*c*c+8*a*a*d*d-8*e*d*b*c-8
                *a*d*c*e+8*r*e*e*b+8*a*b*c*c+8*b
                *a*d*d+8*r*e*e*a-32*r*b*b*a-32*r
                *b*a*a;
        t[2]=12*e*d*c+16*r*b*b-4*b*d*d-8*r*e*e
                +4*e*e*d*yc+16*b*b*xc*c-16*b*c*c
                -16*a*d*d-4*a*c*c+16*r*a*a+16*a*a
                *d*yc+4*xc*e*e*c-8*e*d*b*xc-8*e*yc
                *b*c-8*a*d*xc*e-8*a*yc*c*e+16*a*b
                *b*xc*xc-4*a*e*e*yc*yc+16*b*a*a*yc
                *yc-4*b*xc*xc*e*e+4*Math.pow(e, 3)*yc*xc
                +64*r*b*a-16*a*b*xc*e*yc;
        t[3]=-32*r*b+8*d*d+8*c*c+16*e*d*xc+8*e
                *e*yc*yc+8*xc*xc*e*e-32*r*a-32*b*xc
                *c+16*e*yc*c-32*a*d*yc-32*a*b*xc*xc
                -32*b*a*yc*yc;
        t[4]=16*b*yc*yc+16*d*yc+16*c*xc+16*xc*e*yc
                +16*r+16*a*xc*xc;
        final int k=Quartic.solve(t, s);
        // System.out.println(k+"Solutions found.");
        double dmin=1e30, xmin=xc, ymin=yc;
        for (int i=0; i<k; i++) // Choose closest solution of Lagrange
        // equation
        {
            final double l=s[i];
            // Solve for x,y when lambda is known.
            // Computed with Maple
            final double px=-(-e*d+4*b*l*xc-2*e*l*yc-4
                    *l*l*xc+2*b*c-2*l*c)
                    /(-e*e+4*b*a-4*b*l-4*l*a+4*l*l);
            final double py=-(2*a*d+4*a*l*yc-2*l*d-4*l
                    *l*yc-2*l*xc*e-c*e)
                    /(-e*e+4*b*a-4*b*l-4*l*a+4*l*l);
            final double dist=(px-xc)*(px-xc)+(py-yc)*(py-yc);
            if (dist<dmin) {
                dmin=dist;
                xmin=px;
                ymin=py;
            }
        }
        P.move(xmin, ymin);
    }

    public void project(final PointObject P, final double alpha) {
        project(P);
    }

    public void dragTo(final double x, final double y) {
        for (int i=0; i<5; i++) {
            P[i].move(xd[i]+(x-x1), yd[i]+(y-y1));
        }
    }

    @Override
    public void move(final double x, final double y) {
    }

    public boolean moveable() {
        for (int i=0; i<5; i++) {
            if (!P[i].moveable()) {
                return false;
            }
        }
        return true;
    }
    double xd[], yd[], x1, y1;

    public void startDrag(final double x, final double y) {
        if (xd==null) {
            xd=new double[5];
            yd=new double[5];
        }
        for (int i=0; i<5; i++) {
            xd[i]=P[i].getX();
            yd[i]=P[i].getY();
        }
        x1=x;
        y1=y;
    }

    public double getOldX() {
        return 0;
    }

    public double getOldY() {
        return 0;
    }

    @Override
    public void snap(final ZirkelCanvas zc) {
        if (moveable()) {
            for (int i=0; i<5; i++) {
                P[i].snap(zc);
            }
        }
    }

    public boolean canInteresectWith(final ConstructionObject o) {
        return true;
    }

    public void repulse(final PointObject P) {
        project(P);
    }
}
