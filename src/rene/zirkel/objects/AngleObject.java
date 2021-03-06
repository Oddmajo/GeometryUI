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

// file: Circle3Object.java
import eric.bar.JPropertiesBar;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.FocusEvent;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import eric.JEricPanel;

import rene.dialogs.Warning;
import rene.gui.Global;
import rene.gui.IconBar;
import rene.gui.IconBarListener;
import rene.gui.MyLabel;
import rene.gui.TextFieldAction;
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
import rene.zirkel.graphics.PolygonDrawer;

public class AngleObject extends ConstructionObject implements InsideObject {

    protected PointObject P1, P2, P3;
    static Count N=new Count();
    double A, A1, A2, A3D; // Dibs : A3D est l'angle 3D
    double X, Y;
    boolean Fixed;
    Expression E;
    boolean Filled=false;
    final static double LabelScale=0.66;
    public static final int NORMALSIZE=1, SMALL=0, LARGER=2, LARGE=3,
            RECT=4;
    protected int DisplaySize=NORMALSIZE;

    public AngleObject(final Construction c, final PointObject p1,
            final PointObject p2, final PointObject p3) {
        super(c);
        P1=p1;
        P2=p2;
        P3=p3;
        validate();
        setColor(ColorIndex, SpecialColor);
        updateText();
        Unit=Global.getParameter("unit.angle", "°");
        double xx=P1.getX()+P3.getX()-2*P2.getX();
        double yy=P1.getY()+P3.getY()-2*P2.getY();
        final double ll=Math.max(Math.sqrt(xx*xx+yy*yy),0.000001);
        xx=xx/ll;
        yy=yy/ll;
        XcOffset=xx*25/c.getPixel();
        YcOffset=yy*25/c.getPixel();
    }

    public AngleObject(final Construction c) {
        super(c);
    }

    @Override
    public void setDefaults() {
        setShowName(Global.getParameter("options.angle.shownames", false));
        setShowValue(Global.getParameter("options.angle.showvalues", false));
        setColor(Global.getParameter("options.angle.color", 0), Global.getParameter("options.angle.pcolor", (ExpressionColor) null, this));
        setColorType(Global.getParameter("options.angle.colortype", 0));
        setFilled(Global.getParameter("options.angle.filled", false));
        setHidden(Cn.Hidden);
        setObtuse(Global.getParameter("options.angle.obtuse", false));
        setSolid(Global.getParameter("options.angle.solid", false));
        setLarge(Cn.LargeFont);
        setBold(Cn.BoldFont);
        setPartial(Cn.Partial);
    }

    @Override
    public void setTargetDefaults() {
        setShowName(Global.getParameter("options.angle.shownames", false));
        setShowValue(Global.getParameter("options.angle.showvalues", false));
        setColor(Global.getParameter("options.angle.color", 0), Global.getParameter("options.angle.pcolor", (ExpressionColor) null, this));
        setColorType(Global.getParameter("options.angle.colortype", 0));
        setFilled(Global.getParameter("options.angle.filled", false));
        setObtuse(Global.getParameter("options.angle.obtuse", false));
        setSolid(Global.getParameter("options.angle.solid", false));
    }

    @Override
    public String getTag() {
        return "Angle";
    }

    @Override
    public int getN() {
        return N.next();
    }

    @Override
    public void updateText() {
        if (!Fixed||E==null) {
            setText(text3(Global.name("text.angle"), P1.getName(),
                    P2.getName(), P3.getName()));
        } else {
            setText(text4(Global.name("text.angle.fixed"), P1.getName(), P2.getName(), P3.getName(), "\""+E.toString()+"\""));
        }
    }

    @Override
    public String getDisplayValue() {
        // if (ZirkelCanvas.AnglesFactor<=2) {
        // return ""+(int) (A/Math.PI*180+0.5);
        // } else {
        // return ""+round(A/Math.PI*180, ZirkelCanvas.AnglesFactor);
        // }
    	if (P1.is3D()&&P2.is3D()&&P3.is3D()) return Global.getLocaleNumber(A3D/Math.PI*180, "angles");
    	else return Global.getLocaleNumber(A/Math.PI*180, "angles");
    }

    @Override
    public boolean nearto(final int x, final int y, final ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        final double dx=zc.x(x)-X, dy=zc.y(y)-Y;
        final double size=zc.dx(zc.selectionSize());
        final double rd=getDisplaySize(zc), r=Math.sqrt(dx*dx+dy*dy);
        boolean near;
        Value=Math.abs(r-rd);
        if (Filled||DisplaySize==RECT) {
            near=(r<rd+size);
            if (near) {
                Value=0;
            }
        } else {
            near=(Math.abs(r-rd)<size);
        }
        if (!near) {
            return false;
        }
        if (rd<size) {
            return near;
        }
        double a=Math.atan2(dy, dx);
        if (a<0) {
            a+=2*Math.PI;
        }
        final double c=0.05;
        if (a<A1) {
            a+=2*Math.PI;
        }
        return a>A1-c&&a<A1+A+c;
    }

    @Override
    public void validate() {
        if (P1==null) {
            return;
        }
        if (!P1.valid()||!P2.valid()||!P3.valid()) {
            Valid=false;
            return;
        } else {
            X=P2.getX();
            Y=P2.getY();
            double dx=P1.getX()-X, dy=P1.getY()-Y;
            if (Math.sqrt(dx*dx+dy*dy)<1e-9) {
                Valid=false;
                return;
            }
            A1=Math.atan2(dy, dx);
            if (A1<0) {
                A1+=2*Math.PI;
            }
            dx=P3.getX()-X;
            dy=P3.getY()-Y;
            if (Math.sqrt(dx*dx+dy*dy)<1e-9) {
                Valid=false;
                return;
            }
            A2=Math.atan2(dy, dx);
            if (A2<0) {
                A2+=2*Math.PI;
            }
            A=A2-A1;
            if (A<0) {
                A=A+2*Math.PI;
            }
            if (P1.is3D()&&P2.is3D()&&P3.is3D()) {
            	double dx13D=P1.getX3D()-P2.getX3D(), dy13D=P1.getY3D()-P2.getY3D(), dz13D=P1.getZ3D()-P2.getZ3D();
            	double dx23D=P3.getX3D()-P2.getX3D(), dy23D=P3.getY3D()-P2.getY3D(), dz23D=P3.getZ3D()-P2.getZ3D();
            	double n1=Math.sqrt(dx13D*dx13D+dy13D*dy13D+dz13D*dz13D);
            	double n2=Math.sqrt(dx23D*dx23D+dy23D*dy23D+dz23D*dz23D);
            	if (n1<1e-9||n2<1e-9) {
            		Valid=false;
            		return;
            		}
            	double pscal=dx13D*dx23D+dy13D*dy23D+dz13D*dz23D;
            	A3D=Math.acos(pscal/n1/n2);
            	}
            Valid=true;
            if (Fixed) {
                double FixedAlpha=0;
                try {
                    FixedAlpha=E.getValue()/180*Math.PI;
                } catch (final Exception e) {
                    return;
                }
                if (P3.moveableBy(this)) {
                    dx=P3.getX()-X;
                    dy=P3.getY()-Y;
                    double r=Math.sqrt(dx*dx+dy*dy);
                    if (r<1e-9) {
                        r=1e-9;
                    }
                    P3.move(X+Math.cos(A1+FixedAlpha)*r, Y+Math.sin(A1+FixedAlpha)*r);
                    A2=A1+FixedAlpha;
                } else {
                    Fixed=false;
                }
                if (Fixed) {
                    A=FixedAlpha;
                    P3.movedBy(this);
                    P1.movedBy(this);
                }
            } else if (!Obtuse&&A>Math.PI) {
                A1=A2;
                A=2*Math.PI-A;
                A2=A1+A;
            }
        }
    }
    double x[]=new double[4], y[]=new double[4];
    double x3D[]= new double[4], y3D[]= new double[4], z3D[]= new double[4];
    double xx3D, yy3D, zz3D;
    double xx, yy, zz;

    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {
    	if (zc.is3D()&&P1.is3D()&&P2.is3D()&&P3.is3D()) { // angle 3D
    	//final double xx1=P1.getX()-P2.getX();
        //final double yy1=P1.getY()-P2.getY();
        //final double xx2=P3.getX()-P2.getX();
        //final double yy2=P3.getY()-P2.getY();
        double xx3D1=P1.getX3D()-P2.getX3D();
        double yy3D1=P1.getY3D()-P2.getY3D();
        double zz3D1=P1.getZ3D()-P2.getZ3D();
        double d3D1=Math.sqrt(xx3D1*xx3D1+yy3D1*yy3D1+zz3D1*zz3D1);
        double xx3D2=P3.getX3D()-P2.getX3D();
        double yy3D2=P3.getY3D()-P2.getY3D();
        double zz3D2=P3.getZ3D()-P2.getZ3D();
        double d3D2=Math.sqrt(xx3D2*xx3D2+yy3D2*yy3D2+zz3D2*zz3D2);
        xx3D1/=d3D1;      // on normalise
        yy3D1/=d3D1;
        zz3D1/=d3D1;
        xx3D2/=d3D2;
        yy3D2/=d3D2;
        zz3D2/=d3D2;
        final double x3DP2=P2.getX3D();
        final double y3DP2=P2.getY3D();
        final double z3DP2=P2.getZ3D();
        final double xO=zc.getConstruction().find("O").getX();
        final double yO=zc.getConstruction().find("O").getY();
        final double deltaxX=zc.getConstruction().find("X").getX()-xO;
        final double deltaxY=zc.getConstruction().find("Y").getX()-xO;
        final double deltaxZ=zc.getConstruction().find("Z").getX()-xO;
        final double deltayX=zc.getConstruction().find("X").getY()-yO;
        final double deltayY=zc.getConstruction().find("Y").getY()-yO;
        final double deltayZ=zc.getConstruction().find("Z").getY()-yO;
        double R3D=getDisplaySize(zc);
        if (!Valid||mustHide(zc)) {
            return;
        }
        final double R=zc.col(getDisplaySize(zc))-zc.col(0);
        final double c1=zc.col(X)-R, r1=zc.row(Y)-R;
        // paint:
        double DA=(A2-A1)/Math.PI*180;
        if (DA<0) {
            DA+=360;
        } else if (DA>=360) {
            DA-=360;
        }

        if (visible(zc)) {
            if (isStrongSelected()&&g instanceof MainGraphics) {
                ((MainGraphics) g).drawMarkerArc(c1+R, r1+R, R, A1/Math.PI*180, DA);
            }
            if (Filled) {
                if (DisplaySize==RECT) { // Dibs : sert à rien ?

                    final double dx1=Math.cos(A1), dy1=Math.sin(A1), dx2=Math.cos(A1+DA/180*Math.PI), dy2=Math.sin(A1+DA/180*Math.PI);
                    double dx3=dx1+dx2, dy3=dy1+dy2;
                    if (DA>180) {
                        dx3=-dx3;
                        dy3=-dy3;
                    }
                    if (Selected||getColorType()!=THIN) {
                        g.setColor(this);
                        g.drawLine(c1+R+R*dx1, r1+R-R*dy1, c1+R+R*dx3, r1+R-R*dy3);
                        g.drawLine(c1+R+R*dx3, r1+R-R*dy3, c1+R+R*dx2, r1+R-R*dy2);
                    }
                    x[0]=c1+R;
                    y[0]=r1+R;
                    x[1]=c1+R+R*dx1;
                    y[1]=r1+R-R*dy1;
                    x[2]=c1+R+R*dx3;
                    y[2]=r1+R-R*dy3;
                    x[3]=c1+R+R*dx2;
                    y[3]=r1+R-R*dy2;
                    g.fillPolygon(x, y, 4, false,
                            getColorType()!=THICK, this);
                     } else {    								// angle 3D rempli
                		if (Math.abs(A3D-Math.PI/2)<0.00002) { // angle 3D droit
                			try {
                				R3D/=1.4;
                				x3D[0]=x3DP2;
                            	y3D[0]=y3DP2;
                            	z3D[0]=z3DP2;
                            	x3D[1]=x3DP2+R3D*xx3D1;
                            	y3D[1]=y3DP2+R3D*yy3D1;
                            	z3D[1]=z3DP2+R3D*zz3D1;
                            	x3D[2]=x3DP2+R3D*(xx3D1+xx3D2);
                            	y3D[2]=y3DP2+R3D*(yy3D1+yy3D2);
                            	z3D[2]=z3DP2+R3D*(zz3D1+zz3D2);
                            	x3D[3]=x3DP2+R3D*xx3D2;
                            	y3D[3]=y3DP2+R3D*yy3D2;
                            	z3D[3]=z3DP2+R3D*zz3D2;
                            	x[0]=xO+x3D[0]*deltaxX+y3D[0]*deltaxY+z3D[0]*deltaxZ;
                                y[0]=yO+x3D[0]*deltayX+y3D[0]*deltayY+z3D[0]*deltayZ;
                                x[1]=xO+x3D[1]*deltaxX+y3D[1]*deltaxY+z3D[1]*deltaxZ;
                                y[1]=yO+x3D[1]*deltayX+y3D[1]*deltayY+z3D[1]*deltayZ;
                                x[2]=xO+x3D[2]*deltaxX+y3D[2]*deltaxY+z3D[2]*deltaxZ;
                                y[2]=yO+x3D[2]*deltayX+y3D[2]*deltayY+z3D[2]*deltayZ;
                                x[3]=xO+x3D[3]*deltaxX+y3D[3]*deltaxY+z3D[3]*deltaxZ;
                                y[3]=yO+x3D[3]*deltayX+y3D[3]*deltayY+z3D[3]*deltayZ;
                                x[0]=zc.col(x[0]); y[0]=zc.row(y[0]);
                                x[1]=zc.col(x[1]); y[1]=zc.row(y[1]);
                                x[2]=zc.col(x[2]); y[2]=zc.row(y[2]);
                                x[3]=zc.col(x[3]); y[3]=zc.row(y[3]);
                            } catch (final Exception e) {
                            	System.out.println("exception angle");
                            }
                			g.fillPolygon(x, y, 4, false, getColorType()!=THICK,
                                    this);
                    			g.setColor(this);
                    			g.drawLine(x[1], y[1], x[2], y[2]);
                    			g.drawLine(x[2], y[2], x[3], y[3]);
                		}
                		else { // pas droit
                			g.setColor(this);
                    		final PolygonDrawer pd=new PolygonDrawer(true,g, this);
                    		final double h=zc.dx(zc.getOne());
                    		double pvectx1=yy3D1*zz3D2-zz3D1*yy3D2;  // Objectif : obtenir le "bon vecteur normal" à P2P1
                    		double pvecty1=zz3D1*xx3D2-xx3D1*zz3D2;
                    		double pvectz1=xx3D1*yy3D2-yy3D1*xx3D2;
                    		//final double norme1=Math.sqrt(pvectx1*pvectx1+pvecty1*pvecty1+pvectz1*pvectz1);
                    		//pvectx1/=norme1; pvecty1/=norme1; pvectz1/=norme1;
                    		double pvectx2=pvecty1*zz3D1-pvectz1*yy3D1;
                    		double pvecty2=pvectz1*xx3D1-pvectx1*zz3D1;
                    		double pvectz2=pvectx1*yy3D1-pvecty1*xx3D1;
                    		final double norme2=Math.sqrt(pvectx2*pvectx2+pvecty2*pvecty2+pvectz2*pvectz2);
                    		pvectx2/=norme2; pvecty2/=norme2; pvectz2/=norme2;
                    		//final double ux=xx3D1/d3D1, uy=yy3D1/d3D1, uz=zz3D1/d3D1;
                    		double alpha=0;
                    		boolean valid=false;
                    		int compteur=0;
                    		ArrayList<Double> cx= new ArrayList<Double>(), cy=new ArrayList<Double>();
                    		cx.add(0.0); cy.add(0.0); //juste pour coordonner des indices
                            while (alpha<=A3D) {
                                try {
                                	xx3D=x3DP2+R3D*(Math.cos(alpha)*xx3D1+Math.sin(alpha)*pvectx2);
                                	yy3D=y3DP2+R3D*(Math.cos(alpha)*yy3D1+Math.sin(alpha)*pvecty2);
                                	zz3D=z3DP2+R3D*(Math.cos(alpha)*zz3D1+Math.sin(alpha)*pvectz2);
                                	xx=xO+xx3D*deltaxX+yy3D*deltaxY+zz3D*deltaxZ;
                                    yy=yO+xx3D*deltayX+yy3D*deltayY+zz3D*deltayZ;
                                    final double c=zc.col(xx), r=zc.row(yy);
                                    cx.add(c); cy.add(r);
                                    if (valid) {
                                        pd.drawTo(c, r);
                                        compteur++;
                                    } else {

                                        pd.startPolygon(c, r);
                                    }
                                    valid=true;
                                } catch (final RuntimeException e) {
                                	System.out.println("runtime exception");
                                    valid=false;
                                }
                                alpha+=h;
                            }
                            pd.finishPolygon();
                            final double ccx[]= new double[compteur+2], ccy[]= new double[compteur+2];
                            ccx[0]=zc.col(xO+x3DP2*deltaxX+y3DP2*deltaxY+z3DP2*deltaxZ);
                            ccy[0]=zc.row(yO+x3DP2*deltayX+y3DP2*deltayY+z3DP2*deltayZ);
                            for (int i=1; i<compteur+2; i++) {
                            	ccx[i]= cx.get(i);ccy[i]= cy.get(i);
                            }
                            g.fillPolygon(ccx, ccy, compteur+2, false, getColorType()!=THICK,
                                    this);
                		}
                		// fin bloc pas droit
                	
                }
  
            } else {   // 3D pas rempli
                g.setColor(this);
                if (DisplaySize==RECT) {
                    final double dx1=Math.cos(A1), dy1=Math.sin(A1), dx2=Math.cos(A1+DA/180*Math.PI), dy2=Math.sin(A1+DA/180*Math.PI);
                    g.drawLine(c1+R+R*dx1, r1+R-R*dy1, c1+R+R*(dx1+dx2), r1+R-R*(dy1+dy2));
                    g.drawLine(c1+R+R*(dx1+dx2), r1+R-R*(dy1+dy2), c1+R+R*dx2, r1+R-R*dy2);
                } else {
                	if (Math.abs(A3D-Math.PI/2)<0.00002) {

                		try {
            				R3D/=1.4;
            				x3D[0]=x3DP2;
                        	y3D[0]=y3DP2;
                        	z3D[0]=z3DP2;
                        	x3D[1]=x3DP2+R3D*xx3D1;
                        	y3D[1]=y3DP2+R3D*yy3D1;
                        	z3D[1]=z3DP2+R3D*zz3D1;
                        	x3D[2]=x3DP2+R3D*(xx3D1+xx3D2);
                        	y3D[2]=y3DP2+R3D*(yy3D1+yy3D2);
                        	z3D[2]=z3DP2+R3D*(zz3D1+zz3D2);
                        	x3D[3]=x3DP2+R3D*xx3D2;
                        	y3D[3]=y3DP2+R3D*yy3D2;
                        	z3D[3]=z3DP2+R3D*zz3D2;
                        	x[0]=xO+x3D[0]*deltaxX+y3D[0]*deltaxY+z3D[0]*deltaxZ;
                            y[0]=yO+x3D[0]*deltayX+y3D[0]*deltayY+z3D[0]*deltayZ;
                            x[1]=xO+x3D[1]*deltaxX+y3D[1]*deltaxY+z3D[1]*deltaxZ;
                            y[1]=yO+x3D[1]*deltayX+y3D[1]*deltayY+z3D[1]*deltayZ;
                            x[2]=xO+x3D[2]*deltaxX+y3D[2]*deltaxY+z3D[2]*deltaxZ;
                            y[2]=yO+x3D[2]*deltayX+y3D[2]*deltayY+z3D[2]*deltayZ;
                            x[3]=xO+x3D[3]*deltaxX+y3D[3]*deltaxY+z3D[3]*deltaxZ;
                            y[3]=yO+x3D[3]*deltayX+y3D[3]*deltayY+z3D[3]*deltayZ;
                            x[0]=zc.col(x[0]); y[0]=zc.row(y[0]);
                            x[1]=zc.col(x[1]); y[1]=zc.row(y[1]);
                            x[2]=zc.col(x[2]); y[2]=zc.row(y[2]);
                            x[3]=zc.col(x[3]); y[3]=zc.row(y[3]);
                        } catch (final Exception e) {
                        	System.out.println("exception angle");
                        }
                			//g.setColor(this);
                			g.drawLine(x[1], y[1], x[2], y[2]);
                			g.drawLine(x[2], y[2], x[3], y[3]);
                    } else {
                    	final PolygonDrawer pd=new PolygonDrawer(true,g, this);
                		final double h=zc.dx(zc.getOne());
                		double pvectx1=yy3D1*zz3D2-zz3D1*yy3D2;  // Objectif : obtenir le "bon vecteur normal" à P2P1
                		double pvecty1=zz3D1*xx3D2-xx3D1*zz3D2;
                		double pvectz1=xx3D1*yy3D2-yy3D1*xx3D2;
                		//final double norme1=Math.sqrt(pvectx1*pvectx1+pvecty1*pvecty1+pvectz1*pvectz1);
                		//pvectx1/=norme1; pvecty1/=norme1; pvectz1/=norme1;
                		double pvectx2=pvecty1*zz3D1-pvectz1*yy3D1;
                		double pvecty2=pvectz1*xx3D1-pvectx1*zz3D1;
                		double pvectz2=pvectx1*yy3D1-pvecty1*xx3D1;
                		final double norme2=Math.sqrt(pvectx2*pvectx2+pvecty2*pvecty2+pvectz2*pvectz2);
                		pvectx2/=norme2; pvecty2/=norme2; pvectz2/=norme2;
                		//final double ux=xx3D1/d3D1, uy=yy3D1/d3D1, uz=zz3D1/d3D1;
                		double alpha=0;
                		boolean valid=false;
                        while (alpha<=A3D) {
                            try {
                            	xx3D=x3DP2+R3D*(Math.cos(alpha)*xx3D1+Math.sin(alpha)*pvectx2);
                            	yy3D=y3DP2+R3D*(Math.cos(alpha)*yy3D1+Math.sin(alpha)*pvecty2);
                            	zz3D=z3DP2+R3D*(Math.cos(alpha)*zz3D1+Math.sin(alpha)*pvectz2);
                            	xx=xO+xx3D*deltaxX+yy3D*deltaxY+zz3D*deltaxZ;
                                yy=yO+xx3D*deltayX+yy3D*deltayY+zz3D*deltayZ;
                                final double c=zc.col(xx), r=zc.row(yy);
                                if (valid) {
                                    pd.drawTo(c, r);
                                } else {

                                    pd.startPolygon(c, r);
                                }
                                valid=true;
                            } catch (final RuntimeException e) {
                            	System.out.println("runtime exception");
                                valid=false;
                            }
                            alpha+=h;
                        }
                        pd.finishPolygon();
                		//g.setColor(this);
                    }
                }

            }
        }
        final String s=translateToUnicode(getDisplayText());
        if (!s.equals("")) {
            g.setLabelColor(this);
            setFont(g);
            DisplaysText=true;
            final double dx=Math.cos(A1+A/2), dy=Math.sin(A1+A/2);
            // if (s.equals("90"+getUnit()) || Name.startsWith("."))
            // {
            //
            // if (KeepClose)
            // { double d=Math.sqrt(XcOffset*XcOffset+YcOffset*YcOffset);
            // TX1=zc.col(X+d*dx)-3;
            // TY1=zc.row(Y+d*dy)-3;
            // TX2=TX1+9;
            // TY2=TY1+9;
            // g.drawRect(zc.col(X+d*dx)-1,
            // zc.row(Y+d*dy)-1,3,3);
            // }
            // else
            // { TX1=zc.col(X+zc.dx(R*LabelScale)*dx+XcOffset)-3;
            // TY1=zc.row(Y+zc.dy(R*LabelScale)*dy+YcOffset)-3;
            // TX2=TX1+9;
            // TY2=TY1+9;
            // g.drawRect(zc.col(X+zc.dx(R*LabelScale)*dx+XcOffset)-1,
            // zc.row(Y+zc.dy(R*LabelScale)*dy+YcOffset)-1,3,3);
            // }
            // }
            // else
            // {

            if (KeepClose) {
                final double d=Math.sqrt(XcOffset*XcOffset+YcOffset*YcOffset);
                drawCenteredLabel(g, s, zc, X+d*dx, Y+d*dy, 0, 0);
            } else {
                drawCenteredLabel(g, s, zc, X+zc.dx(R*LabelScale)*dx, Y+zc.dy(R*LabelScale)*dy, XcOffset, YcOffset);
            }
            // }
        	}
    	}
    	else { // angle 2D

        if (!Valid||mustHide(zc)) {
            return;
        }
        final double R=zc.col(getDisplaySize(zc))-zc.col(0);
        final double c1=zc.col(X)-R, r1=zc.row(Y)-R;
        // paint:
        double DA=(A2-A1)/Math.PI*180;
        if (DA<0) {
            DA+=360;
        } else if (DA>=360) {
            DA-=360;
        }

        if (visible(zc)) {
            if (isStrongSelected()&&g instanceof MainGraphics) {
                ((MainGraphics) g).drawMarkerArc(c1+R, r1+R, R, A1/Math.PI*180, DA);
            }
            if (Filled) {
                if (DisplaySize==RECT) {

                    final double dx1=Math.cos(A1), dy1=Math.sin(A1), dx2=Math.cos(A1+DA/180*Math.PI), dy2=Math.sin(A1+DA/180*Math.PI);
                    double dx3=dx1+dx2, dy3=dy1+dy2;
                    if (DA>180) {
                        dx3=-dx3;
                        dy3=-dy3;
                    }
                    if (Selected||getColorType()!=THIN) {
                        g.setColor(this);
                        g.drawLine(c1+R+R*dx1, r1+R-R*dy1, c1+R+R*dx3, r1+R-R*dy3);
                        g.drawLine(c1+R+R*dx3, r1+R-R*dy3, c1+R+R*dx2, r1+R-R*dy2);
                    }
                    x[0]=c1+R;
                    y[0]=r1+R;
                    x[1]=c1+R+R*dx1;
                    y[1]=r1+R-R*dy1;
                    x[2]=c1+R+R*dx3;
                    y[2]=r1+R-R*dy3;
                    x[3]=c1+R+R*dx2;
                    y[3]=r1+R-R*dy2;
                    g.fillPolygon(x, y, 4, false,
                            getColorType()!=THICK, this);
                } else {
                		if (Math.abs(DA-90)<0.0000001) {

                			final double dx1=Math.cos(A1), dy1=Math.sin(A1), dx2=Math.cos(A1+DA/180*Math.PI), dy2=Math.sin(A1+DA/180*Math.PI);
                			final double dx3=dx1+dx2, dy3=dy1+dy2;
                			x[0]=c1;
                			y[0]=r1;
                			x[0]=c1+R;
                			y[0]=r1+R;
                			x[1]=c1+R+R*dx1;
                			y[1]=r1+R-R*dy1;
                			x[2]=c1+R+R*dx3;
                			y[2]=r1+R-R*dy3;
                			x[3]=c1+R+R*dx2;
                			y[3]=r1+R-R*dy2;
                			g.fillPolygon(x, y, 4, false, getColorType()!=THICK,
                                this);
                			g.setColor(this);
                			g.drawLine(x[1], y[1], x[2], y[2]);
                			g.drawLine(x[2], y[2], x[3], y[3]);
                		} else {
                			g.fillArc(c1, r1, 2*R, 2*R, A1/Math.PI*180, DA,
                					Selected||getColorType()!=THIN,
                					getColorType()!=THICK, true, this);
                		}	
                }
            } else {
                g.setColor(this);
                if (DisplaySize==RECT) {
                    final double dx1=Math.cos(A1), dy1=Math.sin(A1), dx2=Math.cos(A1+DA/180*Math.PI), dy2=Math.sin(A1+DA/180*Math.PI);
                    g.drawLine(c1+R+R*dx1, r1+R-R*dy1, c1+R+R*(dx1+dx2), r1+R-R*(dy1+dy2));
                    g.drawLine(c1+R+R*(dx1+dx2), r1+R-R*(dy1+dy2), c1+R+R*dx2, r1+R-R*dy2);
                } else {
                    if (Math.abs(DA-90)<0.0000001) {

                        final double dx1=Math.cos(A1), dy1=Math.sin(A1), dx2=Math.cos(A1+DA/180*Math.PI), dy2=Math.sin(A1+DA/180*Math.PI);
                        final double dx3=dx1+dx2, dy3=dy1+dy2;
                        x[1]=c1+R+R*dx1;
                        y[1]=r1+R-R*dy1;
                        x[2]=c1+R+R*dx3;
                        y[2]=r1+R-R*dy3;
                        x[3]=c1+R+R*dx2;
                        y[3]=r1+R-R*dy2;
                        g.setColor(this);
                        g.drawLine(x[1], y[1], x[2], y[2]);
                        g.drawLine(x[2], y[2], x[3], y[3]);
                    } else {
                        g.drawCircleArc(c1+R, r1+R, R, A1/Math.PI*180,
                                DA, this);
                    }
                }

            }
        }
        final String s=translateToUnicode(getDisplayText());
        if (!s.equals("")) {
            g.setLabelColor(this);
            setFont(g);
            DisplaysText=true;
            final double dx=Math.cos(A1+A/2), dy=Math.sin(A1+A/2);
            // if (s.equals("90"+getUnit()) || Name.startsWith("."))
            // {
            //
            // if (KeepClose)
            // { double d=Math.sqrt(XcOffset*XcOffset+YcOffset*YcOffset);
            // TX1=zc.col(X+d*dx)-3;
            // TY1=zc.row(Y+d*dy)-3;
            // TX2=TX1+9;
            // TY2=TY1+9;
            // g.drawRect(zc.col(X+d*dx)-1,
            // zc.row(Y+d*dy)-1,3,3);
            // }
            // else
            // { TX1=zc.col(X+zc.dx(R*LabelScale)*dx+XcOffset)-3;
            // TY1=zc.row(Y+zc.dy(R*LabelScale)*dy+YcOffset)-3;
            // TX2=TX1+9;
            // TY2=TY1+9;
            // g.drawRect(zc.col(X+zc.dx(R*LabelScale)*dx+XcOffset)-1,
            // zc.row(Y+zc.dy(R*LabelScale)*dy+YcOffset)-1,3,3);
            // }
            // }
            // else
            // {

            if (KeepClose) {
                final double d=Math.sqrt(XcOffset*XcOffset+YcOffset*YcOffset);
                drawCenteredLabel(g, s, zc, X+d*dx, Y+d*dy, 0, 0);
            } else {
                drawCenteredLabel(g, s, zc, X+zc.dx(R*LabelScale)*dx, Y+zc.dy(R*LabelScale)*dy, XcOffset, YcOffset);
            }
            // }
        	}
    	}
    }

    @Override
    public boolean canKeepClose() {
        return true;
    }

    @Override
    public void setKeepClose(final double x, final double y) {
        KeepClose=true;
        XcOffset=x-X;
        YcOffset=y-Y;
    }

    double getDisplaySize(final ZirkelCanvas zc) {
        double R=zc.dx(12*zc.pointSize());
        if (DisplaySize==SMALL||DisplaySize==RECT) {
            R/=2;
        } else if (DisplaySize==LARGER) {
            R*=2;
        } else if (DisplaySize==LARGE) {
            final double dx=P1.getX()-X, dy=P1.getY()-Y;
            R=Math.sqrt(dx*dx+dy*dy);
        }
        return R;
    }

    public double getLength() {
        return A;
    }

    @Override
    public boolean fixed() {
        return Fixed;
    }

    @Override
    public void setFixed(final boolean flag) {
        Fixed=flag;
        updateText();
    }

    @Override
    public void setFixed(final String s) {
        Fixed=true;
        E=new Expression(s, getConstruction(), this);
        updateText();
    }

    @Override
    public boolean canFix() {
        return P3.moveableBy(this);
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        if (P1!=null) {
            xml.printArg("first", P1.getName());
            xml.printArg("root", P2.getName());
            xml.printArg("second", P3.getName());
        }
        if (DisplaySize==SMALL) {
            xml.printArg("display", "small");
        }
        if (DisplaySize==NORMALSIZE) {
            xml.printArg("display", "normalsize");
        }
        if (DisplaySize==LARGE) {
            xml.printArg("display", "large");
        }
        if (DisplaySize==LARGER) {
            xml.printArg("display", "larger");
        }
        if (DisplaySize==RECT) {
            xml.printArg("display", "rectangle");
        }
        if (Filled) {
            xml.printArg("filled", "true");
        }
        if (Fixed&&E!=null) {
            xml.printArg("fixed", E.toString());
        }
        if (!Obtuse) {
            xml.printArg("acute", "true");
        }
        super.printArgs(xml);
    }


    @Override
    public void setDisplaySize(final int i) {
        DisplaySize=i;
    }

    @Override
    public int getDisplaySize() {
        return DisplaySize;
    }

    @Override
    public Enumeration depending() {
        super.depending();
        if (P1==null) {
            return DL.elements();
        }
        if (!Fixed) {
            return depset(P1, P2, P3);
        } else {
            depset(P1, P2, P3);
            final Enumeration e=E.getDepList().elements();
            while (e.hasMoreElements()) {
                DL.add((ConstructionObject) e.nextElement());
            }
            return DL.elements();
        }
    }

    @Override
    public boolean equals(final ConstructionObject o) {
        if (!(o instanceof AngleObject)||!o.valid()) {
            return false;
        }
        final AngleObject l=(AngleObject) o;
        return equals(X, l.X)&&equals(Y, l.Y)&&equals(A1, l.A1)&&equals(A2, l.A2);
    }
    public static char Translation[]={'a', '\u03B1', 'A', '\u0391', 'b',
        '\u03B2', 'B', '\u0392', 'c', '\u03B3', 'C', '\u0393', 'd',
        '\u03B4', 'D', '\u0394', 'e', '\u03B5', 'E', '\u0395', 'f',
        '\u03D5', 'F', '\u03A6', 'g', '\u03B3', 'G', '\u0393', 'h',
        '\u03B7', 'H', '\u0397', 'i', '\u03B9', 'I', '\u0399', 'k',
        '\u03BA', 'K', '\u039A', 'l', '\u03BB', 'L', '\u039B', 'm',
        '\u03BC', 'M', '\u039C', 'n', '\u03BD', 'N', '\u039D', 'o',
        '\u03BF', 'O', '\u03A9', 'p', '\u03C0', 'P', '\u03A0', 'q',
        '\u03C7', 'Q', '\u03A7', 'r', '\u03C1', 'R', '\u03A1', 's',
        '\u03C3', 'S', '\u03A3', 't', '\u03C4', 'T', '\u03A4', 'u',
        '\u03C5', 'U', '\u03A5', 'v', '\u03C8', 'V', '\u03A8', 'w',
        '\u03C9', 'W', '\u03A9', 'x', '\u03BE', 'X', '\u039E', 'y',
        '\u03C7', 'Y', '\u03A7', 'z', '\u03B6', 'Z', '\u0396',};

    public static String translateToUnicode(final String s) {
        if (s.startsWith("$")) {
            return s;
        }
        if (s.indexOf('\\')<0) {
            return s;
        }
        final StringBuffer b=new StringBuffer();
        for (int i=0; i<s.length(); i++) {
            char c=s.charAt(i);
            if (c!='\\') {
                b.append(c);
            } else {
                i++;
                if (i<s.length()) {
                    c=s.charAt(i);
                    if (c=='0') {
                        int n=0;
                        i++;
                        while (i<s.length()) {
                            final char ch=s.charAt(i);
                            if (ch>='0'&&ch<='9') {
                                n=n*16+(int) (ch-'0');
                            } else if (ch>='A'&&ch<='F') {
                                n=n*16+(int) (ch-'A'+10);
                            } else {
                                break;
                            }
                            i++;
                        }
                        if (n>0) {
                            c=(char) n;
                            b.append(c);
                        }
                        i--;
                        continue;
                    }
                    int j=0;
                    for (j=0; j<Translation.length; j+=2) {
                        if (Translation[j]==c) {
                            b.append(Translation[j+1]);
                            break;
                        }
                    }
                    if (j>=Translation.length) {
                        b.append(c);
                    }
                }
            }
        }
        return b.toString();
    }

    @Override
    public void translate() {
        P1=(PointObject) P1.getTranslation();
        P2=(PointObject) P2.getTranslation();
        P3=(PointObject) P3.getTranslation();
        if (Fixed) {
            try {
                setFixed(E.toString());
                E.translate();
            } catch (final Exception e) {
                Fixed=false;
            }
        }
    }

    @Override
    public String getE() {
        if (Fixed&&E!=null) {
            return E.toString();
        } else {
        	if (P1.is3D()&&P2.is3D()&&P3.is3D()) return ""+round(A3D/Math.PI*180);
        	else return ""+round(A/Math.PI*180);
        }
    }

    @Override
    public double getValue() throws ConstructionException {
        if (!Valid) {
            throw new InvalidException("exception.invalid");
        } else {
            if (P1.is3D()&&P2.is3D()&&P3.is3D()) return A3D/Math.PI*180;
            else return A/Math.PI*180;
        }
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
    public boolean maybeTransparent() {
        return true;
    }

    @Override
    public boolean isFilledForSelect() {
        return false;
    }

    public double containsInside(final PointObject P) {
        final double dx=P.getX()-X, dy=P.getY()-Y;
        double a=Math.atan2(dy, dx);
        if (a<0) {
            a+=2*Math.PI;
        }
        if (a<A1) {
            a+=2*Math.PI;
        }
        final double c=1e-5;
        if (a>A1&&a<A1+A) {
            return 1;
        } else if (a>A1-c&&a<A1+A+c) {
            return 0.5;
        }
        return 0;
    }

    public boolean keepInside(final PointObject P) {
        if (containsInside(P)>0) {
            return true;
        }
        final double x=P.getX(), y=P.getY();
        final double x1=P2.getX(), y1=P2.getY();
        double xmin=x1, ymin=y1, dmin=1e20;
        double x2=P1.getX(), y2=P1.getY();
        double dx=x2-x1, dy=y2-y1;
        double r=dx*dx+dy*dy;
        double h=dx*(x-x1)/r+dy*(y-y1)/r;
        if (h<0) {
            h=0;
        }
        double xh=x1+h*dx, yh=y1+h*dy;
        double dist=Math.sqrt((x-xh)*(x-xh)+(y-yh)*(y-yh));
        if (dist<dmin) {
            dmin=dist;
            xmin=xh;
            ymin=yh;
        }
        x2=P3.getX();
        y2=P3.getY();
        dx=x2-x1;
        dy=y2-y1;
        r=dx*dx+dy*dy;
        h=dx*(x-x1)/r+dy*(y-y1)/r;
        if (h<0) {
            h=0;
        }
        xh=x1+h*dx;
        yh=y1+h*dy;
        dist=Math.sqrt((x-xh)*(x-xh)+(y-yh)*(y-yh));
        if (dist<dmin) {
            dmin=dist;
            xmin=xh;
            ymin=yh;
        }
        P.move(xmin, ymin);
        return false;
    }
    
    public PointObject getP1() {
    	return P1;
    }
    
    public PointObject getP2() {
    	return P2;
    }
    
    public PointObject getP3() {
    	return P3;
    }
}


