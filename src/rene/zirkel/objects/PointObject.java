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
// with addons by Dibs for 3D
package rene.zirkel.objects;

// file: PointObject.java
import eric.GUI.pipe_tools;
import eric.GUI.palette.PaletteManager;
import eric.JPointName;
import eric.JZirkelCanvas;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;
import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.graphics.MainGraphics;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.structures.MagnetObj;

public class PointObject extends ConstructionObject implements MoveableObject,
        DriverObject {

    protected double X, Y;
    protected double X3D, Y3D, Z3D;
    protected boolean Is3D; //Dibs : the point is 3D
    protected boolean BarycentricCoordsInitialzed=false;
    protected double Gx=0, Gy=0; // Barycentric coords, if it's inside a
    // polygon.
    protected double Alpha; // parameter relative zu object
    protected boolean AlphaValid=false; // Alpha is valid
    protected boolean UseAlpha=false; // Use Alpha at all
    protected boolean Moveable, Fixed;
    protected boolean Fixed3D;         //Dibs
    // private static Count N=new Count();
    private static JPointName PointLabel=new JPointName();
    protected int Type=0;
    public double RDP2=16;
    public final static int SQUARE=0, DIAMOND=1, CIRCLE=2, DOT=3,
            CROSS=4, DCROSS=5;
    public static int MaxType=3;
    protected Expression EX, EY;
    protected Expression EX3D, EY3D, EZ3D;
    // private ConstructionObject Bound=null; // point is on a line etc.
    private boolean Later; // bound is later in construction
    private String LaterBind="";
    private boolean KeepInside; // keep point inside bound
    private boolean DontUpdate=false;
    private double Increment=0; // increment to keep on grid
    private ConstructionObject Bound=null; // point is on a line etc.
    private double BoundOrder=Double.NaN; // Only for points on parametric
    // curves made with "points only"
    protected ConstructionObject MovedBy;
    private double LASTX=Double.NaN, LASTY=Double.NaN;
    private final Vector magnetObjects=new Vector();
    Expression magnetRayExp=null;
    private int CurrentMagnetRay=Integer.MIN_VALUE;
    private ConstructionObject CurrentMagnetObject=null;
    public ConstructionObject VirtualBound=null;

    // The object that may have moved this point
    public PointObject(final Construction c, final double x, final double y) {
        super(c);
        X=x;
        Y=y;
        Is3D=false;
        X3D=0;
        Y3D=0;
        Z3D=0;
        Moveable=true;
        Fixed=false;
        Fixed3D=false;
        setColor(ColorIndex, SpecialColor);
        setShowName(false);
        updateText();
        Type=0;
        setMagnetRayExp("20");
    }

    public PointObject(final Construction c, final double x, final double y,
            final ConstructionObject bound) {

        this(c, x, y);
        Bound=bound;
        initAlpha();
	updateText();
    }

    public PointObject(final Construction c, final String name) {
        super(c, name);
        X=0;
        Y=0;
        Is3D=false;
        X3D=0;
        Y3D=0;
        Z3D=0;
        Moveable=true;
        Fixed=false;
        Fixed3D=false;
        setColor(ColorIndex, SpecialColor);
        updateText();
        Type=0;
        setMagnetRayExp("20");
    }


    public void initAlpha() {
        if ((Bound!=null)&&(Bound instanceof PrimitiveCircleObject)) {
            PrimitiveCircleObject circ=(PrimitiveCircleObject) Bound;
            if (circ.hasRange()&&(circ.StartPt!=this)&&(circ.EndPt!=this)&&(isPointOn())) {
                circ.detectArc3Pts();
                double soe=circ.computeSOE();
                double sop=circ.computeSOP(this);
                if (soe!=0) {
                    setAlpha(sop/soe);
//                    if (circ.isArc3pts&&!circ.StartPt.equals(circ.RealCorrespondingStartPt)) {
//                        setAlpha(1-sop/soe);
//                    }
                }
            }
        }
    }

    public static void setPointLabel(final JPointName jpl) {
        PointLabel=jpl;
    }

    @Override
    public void setName() {
        if ((!SuperHidden)&&(!Hidden)) {
            Name=PointLabel.getBetterName(Cn, false);
        } else {
            Name=JPointName.getGenericName(Cn);
        }
    }

    @Override
    public void setNameWithNumber(String n) {
        Name="";
        if (Cn!=null) {
            ConstructionObject o=Cn.find(n);
            if (o!=null) {
                while (o!=null&&o!=this) {
                    Name=JPointName.getGenericName(Cn);
                    n=Name;
                    Name="";
                    o=Cn.find(n);
                }
                Name=n;
            } else {
                Name=n;
            }
        } else {
            Name=n;
        }
    }

    @Override
    public String getTag() {
        if (Bound==null) {
            return "Point";
        } else {
            return "PointOn";
        }
    }

    @Override
    public int getN() {
        return N.next();
    }

    // public void setDefaults ()
    // { super.setDefaults();
    // Type=Cn.DefaultType;
    // }
    @Override
    public void setDefaults() {
        setShowName(Global.getParameter("options.point.shownames", false));
        setShowValue(Global.getParameter("options.point.showvalues", false));
        setColor(Global.getParameter("options.point.color", 0), Global.getParameter("options.point.pcolor", (ExpressionColor) null, this));
        setColorType(Global.getParameter("options.point.colortype", 0));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        setSolid(Cn.Solid);
        setLarge(Global.getParameter("options.point.large", false));
        setBold(Global.getParameter("options.point.bold", false));
        Type=Cn.DefaultType;



    }

    @Override
    public void setTargetDefaults() {
        setShowName(Global.getParameter("options.point.shownames", false));
        setShowValue(Global.getParameter("options.point.showvalues", false));
        setColor(Global.getParameter("options.point.color", 0), Global.getParameter("options.point.pcolor", (ExpressionColor) null, this));
        setColorType(Global.getParameter("options.point.colortype", 0));
        setLarge(Global.getParameter("options.point.large", false));
        setBold(Global.getParameter("options.point.bold", false));
        Type=Cn.DefaultType;
    }
    private double Delta;

    @Override
    public double changedBy() {
        return Delta;
    }

    @Override
    synchronized public void validate() {
        if (DontUpdate) {
            return;
        }

        updateMagnetObjects();
        followMagnetObject();

        // magnet();
        // System.out.println(getName()+" : validate !");

        MovedBy=null;
        Delta=0.0;
        Valid=true;
        if (Fixed3D&&EX3D!=null&&EX3D.isValid()) {
            try {
                X3D=EX3D.getValue();
            } catch (final Exception e) {
                Valid=false;
                return;
            }
        }
        if (Fixed3D&&EY3D!=null&&EY3D.isValid()) {
            try {
                Y3D=EY3D.getValue();
            } catch (final Exception e) {
                Valid=false;
                return;
            }
        }
        if (Fixed3D&&EZ3D!=null&&EZ3D.isValid()) {
            try {
                Z3D=EZ3D.getValue();
            } catch (final Exception e) {
                Valid=false;
                return;
            }
        }
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
        if (Bound!=null&&!Bound.isInConstruction()) {
            Bound=null;
        }
        if (Bound!=null&&!Bound.valid()) {
            Valid=false;
            return;
        }
        if (Increment>1e-4) {
            X=Math.floor(X/Increment+0.5)*Increment;
            Y=Math.floor(Y/Increment+0.5)*Increment;
        }
        if (Bound!=null) {
            final double x=X, y=Y;
            if (KeepInside&&Bound instanceof InsideObject) {
                ((InsideObject) Bound).keepInside(this);
            } else if (!KeepInside&&Bound instanceof PointonObject) {
                if (!AlphaValid||!UseAlpha) {
                    project(Bound);
                } else {
                    project(Bound, Alpha);
                }
            }
            if (Later) {
                Delta=Math.sqrt((x-X)*(x-X)+(y-Y)*(y-Y));
            }
        }
        if (getConstruction().is3D()&&getBound()!=null) { // Dibs : 2D -> 3D
        	if (getBound() instanceof AreaObject) {
        			AreaObject surface= (AreaObject) getBound();
        			if (((AreaObject)surface).V.size()>2&&((PointObject) surface.V.get(0)).is3D()&&((PointObject) surface.V.get(1)).is3D()&&((PointObject) surface.V.get(2)).is3D()) {
        				try {
        					double x0=((PointObject) surface.V.get(0)).getX3D();
            				double y0=((PointObject) surface.V.get(0)).getY3D();
            				double z0=((PointObject) surface.V.get(0)).getZ3D();
            				double x1=((PointObject) surface.V.get(1)).getX3D();
            				double y1=((PointObject) surface.V.get(1)).getY3D();
            				double z1=((PointObject) surface.V.get(1)).getZ3D();
            				double x2=((PointObject) surface.V.get(2)).getX3D();
            				double y2=((PointObject) surface.V.get(2)).getY3D();
            				double z2=((PointObject) surface.V.get(2)).getZ3D();
            				double x_O=getConstruction().find("O").getX();
            				double x_X=getConstruction().find("X").getX();
            				double x_Y=getConstruction().find("Y").getX();
            				double x_Z=getConstruction().find("Z").getX();
            				double y_O=getConstruction().find("O").getY();
            				double y_X=getConstruction().find("X").getY();
            				double y_Y=getConstruction().find("Y").getY();
            				double y_Z=getConstruction().find("Z").getY();
            				double coeffa=(x1-x0)*(x_X-x_O)+(y1-y0)*(x_Y-x_O)+(z1-z0)*(x_Z-x_O);
            				double coeffb=(x2-x0)*(x_X-x_O)+(y2-y0)*(x_Y-x_O)+(z2-z0)*(x_Z-x_O);
            				double coeffc=(x1-x0)*(y_X-y_O)+(y1-y0)*(y_Y-y_O)+(z1-z0)*(y_Z-y_O);
            				double coeffd=(x2-x0)*(y_X-y_O)+(y2-y0)*(y_Y-y_O)+(z2-z0)*(y_Z-y_O);
            				double coeffe=getX()-x_O-x0*(x_X-x_O)-y0*(x_Y-x_O)-z0*(x_Z-x_O);
            				double coefff=getY()-y_O-x0*(y_X-y_O)-y0*(y_Y-y_O)-z0*(y_Z-y_O);
            				double alpha1=(coeffe*coeffd-coefff*coeffb)/(coeffa*coeffd-coeffb*coeffc);
            				double beta1=(coeffa*coefff-coeffc*coeffe)/(coeffa*coeffd-coeffb*coeffc);
            				setX3D(x0+alpha1*(x1-x0)+beta1*(x2-x0));
            				setY3D(y0+alpha1*(y1-y0)+beta1*(y2-y0));
            				setZ3D(z0+alpha1*(z1-z0)+beta1*(z2-z0));
            				setFixed("x(O)+("+X3D+")*(x(X)-x(O))+("+Y3D+")*(x(Y)-x(O))+("+Z3D+")*(x(Z)-x(O))", "y(O)+("+X3D+")*(y(X)-y(O))+("+Y3D+")*(y(Y)-y(O))+("+Z3D+")*(y(Z)-y(O))");
            				AlphaValid=false;
            				setIs3D(true);
        	            	} catch (final Exception eBary) {
        	            }
        			}
        		}
        	
        else if (getBound() instanceof TwoPointLineObject) {
    		TwoPointLineObject ligne= (TwoPointLineObject) getBound();
			if (((PointObject) ligne.getP1()).is3D()&&((PointObject) ligne.getP2()).is3D()) {
				try {
					double x1=((PointObject) ligne.getP1()).getX3D();
					double y1=((PointObject) ligne.getP1()).getY3D();
    				double z1=((PointObject) ligne.getP1()).getZ3D();
    				double x2=((PointObject) ligne.getP2()).getX3D();
    				double y2=((PointObject) ligne.getP2()).getY3D();
    				double z2=((PointObject) ligne.getP2 ()).getZ3D();
    				double x_O=getConstruction().find("O").getX();
    				double x_X=getConstruction().find("X").getX();
    				double x_Y=getConstruction().find("Y").getX();
    				double x_Z=getConstruction().find("Z").getX();
    				double alpha1=(getX()-x_O-x1*(x_X-x_O)-y1*(x_Y-x_O)-z1*(x_Z-x_O))/((x2-x1)*(x_X-x_O)+(y2-y1)*(x_Y-x_O)+(z2-z1)*(x_Z-x_O));
    				if (x1==x2&&y1==y2) {
    					double y_O=getConstruction().find("O").getY();
        				double y_X=getConstruction().find("X").getY();
        				double y_Y=getConstruction().find("Y").getY();
        				double y_Z=getConstruction().find("Z").getY();
    					alpha1=(getY()-y_O-x1*(y_X-y_O)-y1*(y_Y-y_O)-z1*(y_Z-y_O))/((x2-x1)*(y_X-y_O)+(y2-y1)*(y_Y-y_O)+(z2-z1)*(y_Z-y_O));
    				}
    				setX3D(x1+alpha1*(x2-x1));
    				setY3D(y1+alpha1*(y2-y1));
    				setZ3D(z1+alpha1*(z2-z1));
    				setFixed("x(O)+("+X3D+")*(x(X)-x(O))+("+Y3D+")*(x(Y)-x(O))+("+Z3D+")*(x(Z)-x(O))", "y(O)+("+X3D+")*(y(X)-y(O))+("+Y3D+")*(y(Y)-y(O))+("+Z3D+")*(y(Z)-y(O))");
    				//setMoveable(true);
                                //AlphaValid=false;
    				setIs3D(true);
	            	} catch (final Exception eBary) {
	            		}

					}
			}
        else if (getBound() instanceof QuadricObject) {
    		QuadricObject quadrique= (QuadricObject) getBound();
    		if (quadrique.getP()[0].is3D()&&quadrique.getP()[1].is3D()&&quadrique.getP()[2].is3D()&&quadrique.getP()[3].is3D()&&quadrique.getP()[4].is3D()) {
    			try {
					double x0=quadrique.getP()[0].getX3D();
    				double y0=quadrique.getP()[0].getY3D();
    				double z0=quadrique.getP()[0].getZ3D();
    				double x1=quadrique.getP()[1].getX3D();
    				double y1=quadrique.getP()[1].getY3D();
    				double z1=quadrique.getP()[1].getZ3D();
    				double x2=quadrique.getP()[2].getX3D();
    				double y2=quadrique.getP()[2].getY3D();
    				double z2=quadrique.getP()[2].getZ3D();
    				double x_O=getConstruction().find("O").getX();
    				double x_X=getConstruction().find("X").getX();
    				double x_Y=getConstruction().find("Y").getX();
    				double x_Z=getConstruction().find("Z").getX();
    				double y_O=getConstruction().find("O").getY();
    				double y_X=getConstruction().find("X").getY();
    				double y_Y=getConstruction().find("Y").getY();
    				double y_Z=getConstruction().find("Z").getY();
    				double coeffa=(x1-x0)*(x_X-x_O)+(y1-y0)*(x_Y-x_O)+(z1-z0)*(x_Z-x_O);
    				double coeffb=(x2-x0)*(x_X-x_O)+(y2-y0)*(x_Y-x_O)+(z2-z0)*(x_Z-x_O);
    				double coeffc=(x1-x0)*(y_X-y_O)+(y1-y0)*(y_Y-y_O)+(z1-z0)*(y_Z-y_O);
    				double coeffd=(x2-x0)*(y_X-y_O)+(y2-y0)*(y_Y-y_O)+(z2-z0)*(y_Z-y_O);
    				double coeffe=getX()-x_O-x0*(x_X-x_O)-y0*(x_Y-x_O)-z0*(x_Z-x_O);
    				double coefff=getY()-y_O-x0*(y_X-y_O)-y0*(y_Y-y_O)-z0*(y_Z-y_O);
    				double alpha1=(coeffe*coeffd-coefff*coeffb)/(coeffa*coeffd-coeffb*coeffc);
    				double beta1=(coeffa*coefff-coeffc*coeffe)/(coeffa*coeffd-coeffb*coeffc);
    				setX3D(x0+alpha1*(x1-x0)+beta1*(x2-x0));
    				setY3D(y0+alpha1*(y1-y0)+beta1*(y2-y0));
    				setZ3D(z0+alpha1*(z1-z0)+beta1*(z2-z0));
    				setFixed("x(O)+("+X3D+")*(x(X)-x(O))+("+Y3D+")*(x(Y)-x(O))+("+Z3D+")*(x(Z)-x(O))", "y(O)+("+X3D+")*(y(X)-y(O))+("+Y3D+")*(y(Y)-y(O))+("+Z3D+")*(y(Z)-y(O))");
    				//AlphaValid=false;
    				setIs3D(true);
	            	} catch (final Exception eBary) {
	            }
    			
    		}
    	}
        }
        
    }


    @Override
    public Boolean is2DObject(){
        return !Is3D;
    }

    @Override
    public void updateText() {
        if (Bound!=null) {
            setText(text1(Global.name("text.boundedpoint"), Bound.getName()));
            //setText(Global.Loc("objects.boundedpoint"));
        } else if (EX!=null&&EY!=null) {
//            setText(text2(Global.name("text.point"), "\""+EX+"\"", "\""+EY+"\""));
            setText(Global.Loc("objects.point"));
        } else {
//            setText(text2(Global.name("text.point"), ""+round(X), ""+round(Y)));
            setText(Global.Loc("objects.point"));
        }
    }
    static double x[]=new double[4], y[]=new double[4];

    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {
        DisplaysText=false;
        if (!Valid||mustHide(zc)) {
            return;
        }
        final double size=drawPoint(g, zc, this, X, Y, Type);
        if (tracked()) {
            zc.UniversalTrack.drawTrackPoint(this, X, Y, Type);
        }
        final String s=AngleObject.translateToUnicode(getDisplayText());
        if (!s.equals("")) {
            g.setLabelColor(this);
            DisplaysText=true;
            setFont(g);
            final double d=Math.sqrt(XcOffset*XcOffset+YcOffset*YcOffset);
            if (!KeepClose||d<1e-10) {
                TX1=zc.col(X+XcOffset)+2*size;
                TY1=zc.row(Y+YcOffset)+2*size;
                drawLabel(g, s);
            } else {
                drawPointLabel(g, s, zc, X, Y, YcOffset/d, -XcOffset/d, 0,
                        0);
            }
        }
    }

    static public double drawPoint(final MyGraphics g, final ZirkelCanvas zc,
            final ConstructionObject o, final double X, final double Y,
            final int type) {
        double size=zc.pointSize();

        final double r=zc.col(X), c=zc.row(Y);
        final double un=zc.getOne();
//        if (size<1) {
//            size=1;
//        }
        // System.out.println(size);
        if (o.visible(zc)) {
            if (o.isStrongSelected()&&g instanceof MainGraphics) {
                ((MainGraphics) g).drawMarkerLine(r, c, r, c);
            }
            g.setColor(o);
            switch (type) {
                case SQUARE:
                    final double sx=r-size-un,
                     sy=c-size-un,
                     sw=2*(size+un);
                    if (o.getColorType()==THICK) {
                        g.fillRect(sx, sy, sw, sw, true, false, o);
                    } else {
                        g.fillRect(sx, sy, sw, sw, new Color(255, 255, 255));
                    }
                    g.setColor(o);
                    g.drawRect(sx, sy, sw, sw);
                    break;
                case DIAMOND:
                    final double dx=r-size-un,
                     dy=c-size-un,
                     dw=2*(size+un);
                    g.drawDiamond(dx, dy, dw, (o.getColorType()==THICK), o);
                    break;
                case CIRCLE:
                    double cx=r-size-un,
                     cy=c-size-un,
                     cw=2*size+2*un;
                    if (o.getColorType()==THICK) {
                        g.fillOval(cx, cy, cw, cw, true, false, o);
                    } else {
                        g.fillOval(cx, cy, cw, cw, new Color(255, 255, 255));
                        g.setColor(o);
                        g.drawOval(cx, cy, cw, cw);
                    }
                    break;
                case DOT:
                    if (o.getColorType()==THICK) {
                        g.fillRect(r, c, un, un, true, false, o);
                    } else {
                        g.drawLine(r, c, r, c);
                    }
                    break;
                case CROSS:
                    if (o.getColorType()==THICK) {
                        g.drawThickLine(r-size-un, c, r+size+un, c);
                        g.drawThickLine(r, c-size-un, r, c+size+un);
                    } else {
                        g.drawLine(r-size-un, c, r+size+un, c);
                        g.drawLine(r, c-size-un, r, c+size+un);
                    }
                    break;
                case DCROSS:
                    final double crx=r-size-un,
                     cry=c-size-un,
                     crw=2*(size+un);
                    if (o.getColorType()==THICK) {
                        g.drawThickLine(crx, cry, crx+crw, cry+crw);
                        g.drawThickLine(crx+crw, cry, crx, cry+crw);
                    } else {
                        g.drawLine(crx, cry, crx+crw, cry+crw);
                        g.drawLine(crx+crw, cry, crx, cry+crw);
                    }
                    break;
            }
        }
        return size;
    }

    @Override
    public String getDisplayValue() {
    	if (is3D()||PaletteManager.isSelected("boundedpoint")) return "("+Global.getLocaleNumber(X3D, "lengths")+(Global.getParameter("options.germanpoints", false)?"|"
                :";")+Global.getLocaleNumber(Y3D, "lengths")+(Global.getParameter("options.germanpoints", false)?"|"
                        :";")+Global.getLocaleNumber(Z3D, "lengths")+")";
    	else return "("+Global.getLocaleNumber(X, "lengths")+(Global.getParameter("options.germanpoints", false)?"|"
                :";")+Global.getLocaleNumber(Y, "lengths")+")";
    }

    @Override
    public String getCDPDisplayValue() {
        return "("+Global.getCDPLocaleNumber(X, 2)+" "+(Global.getParameter("options.germanpoints", false)?"|"
                :";")+Global.getCDPLocaleNumber(Y, 2)+")";
    }

    @Override
    public boolean isInRect(Rectangle r, ZirkelCanvas zc){
        return r.contains(zc.col(X),zc.row(Y));
    }

    @Override
    public boolean nearto(final int x, final int y, final ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        final double c=zc.col(X), r=zc.row(Y);
        final int size=(int) zc.selectionSize();
        Value=Math.abs(x-c)+Math.abs(y-r);
        return Value<=size*3/2;
    }

    public boolean nearto(final PointObject p) {
    	try {
            X=EX.getValue();
        } catch (final Exception e) {
            Valid=false;
            return false;
        }
    	try {
            Y=EY.getValue();
        } catch (final Exception e) {
            Valid=false;
            return false;
        }
        if (!Valid) {
            return false;
        }
        final double dx=p.X-X, dy=p.Y-Y;
        return Math.sqrt(dx*dx+dy*dy)<1e-9;
    }

    public double distanceTo(final int x, final int y, final ZirkelCanvas zc) {
        final double dx=x-zc.col(X), dy=y-zc.row(Y);
        return Math.sqrt(dx*dx+dy*dy);
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
    public double getX3D() {
    	return X3D;
    }
    
    @Override
    public double getY3D() {
    	return Y3D;
    }
    
    @Override
    public double getZ3D() {
    	return Z3D;
    }
    
    public void setX3D(double x) {
    	X3D = x;
    }
    
    public void setY3D(double y) {
    	Y3D = y;
    }
    
    public void setZ3D(double z) {
    	Z3D = z;
    }
    
    public boolean is3D() {
    	return Is3D;
    }
    
    public void setIs3D(boolean u) {
    	Is3D = u;
    }

    @Override
    public boolean moveable() {
        boolean fixed=Fixed;
        boolean fixed3D=Fixed3D;
        if (dependsOnItselfOnly()) {
            fixed=false;
        }
        if (Is3D) return Moveable&&!fixed3D&&!Keep;
        else return Moveable&&!fixed&&!Keep;
    }

    /**
     * Check if the point depends on itself and no other object. Such points are
     * moveable, even if they are fixed.
     *
     * @return
     */
    public boolean dependsOnItselfOnly() {
        boolean res=false;
        Enumeration e=depending();
        while (e.hasMoreElements()) {
            if ((ConstructionObject) e.nextElement()==this) {
                res=true;
                break;
            }
        }
        e=depending();
        while (e.hasMoreElements()) {
            if ((ConstructionObject) e.nextElement()!=this) {
                res=false;
                break;
            }
        }
        return res;
    }

    public boolean dependsOnParametersOnly() {
        final Enumeration e=depending();
        while (e.hasMoreElements()) {
            if (!((ConstructionObject) e.nextElement()).isParameter()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return o can move this point (moveable and not moved by something else)
     */
    public boolean moveableBy(final ConstructionObject o) {
        if (Bound!=null) {
            return false;
        }
        return moveable()&&(MovedBy==null||MovedBy==o);
    }

    public boolean moveablePoint() {
        if (Bound!=null) {
            return true;
        }
        return Moveable&&!Keep;
    }

    public boolean setBound(final String name) {
        if (name.equals("")) {
            Bound=null;
            setFixed(false);
            setFixed3D(false);
            Later=false;
            return true;
        }
        try {
            Bound=null;
            final ConstructionObject o=Cn.find(name);
            if (o instanceof PointonObject) {
                Bound=o;
                Moveable=true;
                Fixed=false;
                Fixed3D=false;
                KeepInside=false;
            } else if (o instanceof InsideObject) {
                Bound=o;
                Moveable=true;
                Fixed=false;
                Fixed3D=false;
                KeepInside=true;
            } else {
                return false;
            }
        } catch (final Exception e) {
            return false;
        }
        if (Cn.before(this, Bound)) {
            Cn.needsOrdering();
            Cn.dovalidate();
        }
        updateText();
        clearMagnetObjects();
        return true;
    }

    public void setBound(final ConstructionObject bound) {
        Bound=bound;
    }

    public boolean haveBoundOrder() {
        return (!Double.isNaN(BoundOrder));
    }

    public void setBoundOrder(final double boundorder) {
        BoundOrder=boundorder;
    }

    public void clearBoundOrder() {
        BoundOrder=Double.NaN;
    }

    public ConstructionObject getBound() {
        return Bound;
    }

    public double getBoundOrder() {
        return BoundOrder;
    }

    public void setMoveable(final boolean flag) {
        Moveable=flag;
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
    
    public boolean fixed3D() {
        return Fixed3D;
    }

    @Override
    public void setFixed3D(final boolean flag) {
        Fixed3D=flag;
        if (!Fixed3D) {
            EX3D=null;
            EY3D=null;
            EZ3D=null;
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
    public void setFixed(final String x3D, final String y3D, final String z3D) {
        Fixed3D=true;
        EX3D=new Expression(x3D, getConstruction(), this);
        EY3D=new Expression(y3D, getConstruction(), this);
        EZ3D=new Expression(z3D, getConstruction(), this);
    	setFixed("x(O)+("+EX3D+")*(x(X)-x(O))+("+EY3D+")*(x(Y)-x(O))+("+EZ3D+")*(x(Z)-x(O))", "y(O)+("+EX3D+")*(y(X)-y(O))+("+EY3D+")*(y(Y)-y(O))+("+EZ3D+")*(y(Z)-y(O))");	

        updateText();
    }

    public void validCoordinates() throws Exception {
        boolean val=(EX.isValid())&&(EY.isValid());
        if (!val) {
            throw new Exception("");
        }
    }
    
    public void validCoordinates3D() throws Exception {
        boolean val=(EX3D.isValid())&&(EY3D.isValid())&&(EZ3D.isValid());
        if (!val) {
            throw new Exception("");
        }
    }
    /* This part is for "monkey" animation. Point have a spin and it keeps going
     * until it reaches a window border. At this time, spin is recomputed and moves
     * following its new trajectory.
     */
    private double spin=Math.random()*2*Math.PI;

    public boolean insidewindow() {
        return insidewindow(X, Y);
    }

    public boolean insidewindow(double x, double y) {
        boolean b=(x>Cn.getX()-Cn.getW());
        b=b&&(x<Cn.getX()+Cn.getW());
        b=b&&(y<Cn.getY()+Cn.getH()/2);
        b=b&&(y>Cn.getY()-Cn.getH()/2);
        return b;
    }

    public void shake(ZirkelCanvas zc) {
        if(zc.isDP()){
            FixedCircleObject c = (FixedCircleObject) zc.getConstruction().find("Hz");
            if(c!=null){
                RDP2=c.getR();
                RDP2*=RDP2; //set to the square
            }
        }
        if (!insidewindow(X, Y)||(zc.isDP()&&(X*X+Y*Y>RDP2))) {
            return;
        }
        spin+=Math.random()-0.5;
        double d=zc.monkeySpeed()/4;
        double x=X+d*Math.cos(spin)/Cn.getPixel();
        double y=Y+d*Math.sin(spin)/Cn.getPixel();
        while (!insidewindow(x, y)||(zc.isDP()&&(x*x+y*y>RDP2))) {
            //      spin=Math.random()*2*Math.PI;
            //demi-tour carrément
            spin+=Math.PI;
            x=X+d*Math.cos(spin)/Cn.getPixel();
            y=Y+d*Math.sin(spin)/Cn.getPixel();
        }
        move(x, y);
//        magnet();
        validate();
    }

    /* this part is for exercise purpose :
     *
     */
    public void alea() {
        if (!insidewindow(X, Y)) {
            return;
        }
        double left=Cn.getX()-Cn.getW();
        double top=Cn.getY()+Cn.getH()/2;
        double x=left+Math.random()*2*Cn.getW();
        double y=top-Math.random()*Cn.getH();
        while (!insidewindow(x, y)) {
            x=left+Math.random()*2*Cn.getW();
            y=top-Math.random()*Cn.getH();
        }
        move(x, y);
//        magnet();
        validate();
    }

    @Override
    public void move(final double x, final double y) {
        if ((X==x)&&(Y==y)) {
            return;
        }
        // System.out.println(getName()+" : move !");
        X=x;
        Y=y;
        AlphaValid=false;
        computeBarycentricCoords();
    }
    
    @Override
    public void move3D(final double x3D, final double y3D, final double z3D) {
        if ((X3D==x3D)&&(Y3D==y3D)&&(Z3D==z3D)) {
            return;
        }
        // System.out.println(getName()+" : move !");
        X3D=x3D;
        Y3D=y3D;
        Z3D=z3D;
        AlphaValid=false;
        setFixed("x(O)+("+X3D+")*(x(X)-x(O))+("+Y3D+")*(x(Y)-x(O))+("+Z3D+")*(x(Z)-x(O))", "y(O)+("+X3D+")*(y(X)-y(O))+("+Y3D+")*(y(Y)-y(O))+("+Z3D+")*(y(Z)-y(O))");
        setFixed3D(false);
        //computeBarycentricCoords();
    }

    public void setXY(final double x, final double y) {
        if ((X==x)&&(Y==y)) {
            return;
        }
        // System.out.println(getName()+" : setXY !");
        X=x;
        Y=y;
    }
    
    public void setXYZ(final double x3D, final double y3D, final double z3D) {
        if ((X3D==x3D)&&(Y3D==y3D)&&(Z3D==z3D)) {
            return;
        }
        // System.out.println(getName()+" : setXY !");
        X3D=x3D;
        Y3D=y3D;
        Z3D=z3D;
        setFixed("x(O)+("+X3D+")*(x(X)-x(O))+("+Y3D+")*(x(Y)-x(O))+("+Z3D+")*(x(Z)-x(O))", "y(O)+("+X3D+")*(y(X)-y(O))+("+Y3D+")*(y(Y)-y(O))+("+Z3D+")*(y(Z)-y(O))");

    }

    public void setXYaway(final double x, final double y, final int r) {
        if (r>0) {
            setXY(x, y);
            return;
        }

        final double d=Math.sqrt((X-x)*(X-x)+(Y-y)*(Y-y));
        final double dblr=-r/Cn.getPixel();
        double xx=x+dblr, yy=y;
        if (d!=0) {
            xx=x+(X-x)*dblr/d;
            yy=y+(Y-y)*dblr/d;
        }

        // System.out.println(Cn.getPixel());
        // System.out.println("d="+d);
        // System.out.println("xx="+xx);
        // System.out.println("yy="+yy);
        // System.out.println("****");
        setXY(xx, yy);
    }

    public void setA(final double alpha) {
        Alpha=alpha;
    }

    public void setMagnetRayExp(final String s) {
        magnetRayExp=new Expression(s, Cn, this);
    }

    public String getMagnetRayExp() {
        return magnetRayExp.toString();
    }

    public int getMagnetRay() {
        int i=20;
        try {
            i=(int) Math.round(magnetRayExp.getValue());
        } catch (final Exception ex) {
        }
        return i;
    }

    public boolean isMagnet() {
        return (CurrentMagnetObject!=null);
    }

    private boolean isMagnetObject(final String name) {
        final Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            if (mo.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeMagnetObject(final String name) {
        final Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            if (mo.name().equals(name)) {
                magnetObjects.remove(mo);
            }
        }
    }

    /**
     * s represents the name of an object, or the string "name:ray" where ray is
     * the specific magnetic attraction
     *
     * @param s
     */
    public void addMagnetObject(final String s) {
        final String[] mo=s.split(":");
        final String name=mo[0];
        // int ray=-1;
        String ray=""+Integer.MIN_VALUE;
        if (mo.length>1) {
            ray=mo[1];
        }

        if (name.equals(getName())) {
            return;
        }
        final ConstructionObject o=Cn.find(name);
        if ((o!=null)&&(!isMagnetObject(name))) {
            magnetObjects.add(new MagnetObj(o, ray));
        }
    }

    public void setMagnetObjects(final String namelist) {
        String st="";
        char t;
        int p=0;
        for (int i=0; i<namelist.length(); i++) {
            t=namelist.charAt(i);
            if (t=='(') {
                p++;
            } else if (t==')') {
                p--;
            }
            if ((p>0)&&(t==',')) {
                st+="@@";
            } else {
                st+=t;
            }
        }

        final String[] s=st.split(",");
        magnetObjects.removeAllElements();
        for (final String element : s) {
            st=element.replaceAll("@@", ",");
            addMagnetObject(st);
        }
    }

    public void selectMagnetObjects(final boolean b) {
        final Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            if (mo.isInConstruction()) {
                mo.setSelected(b);
            }
        }
    }

    public void updateMagnetObjects() {
        final Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            if (!mo.isInConstruction()) {
                magnetObjects.remove(mo);
            }
        }
    }

    public void clearMagnetObjects() {
        magnetObjects.removeAllElements();
        setMagnetRayExp("20");

    }

    public void translateMagnetObjects() {
        final Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            mo.translate();
        }
    }

    public Vector getMagnetObjects() {
        return magnetObjects;
    }

    public String getMagnetObjectsString() {
        updateMagnetObjects();
        String s="";
        final Enumeration e=magnetObjects.elements();
        if (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            s=mo.name();
            if (mo.ray()>Integer.MIN_VALUE) {
                s+=":"+mo.rayexp();
            }
        }
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            s+=","+mo.name();
            if (mo.ray()>Integer.MIN_VALUE) {
                s+=":"+mo.rayexp();
            }
        }
        return s;
    }

    public void magnet() {
        ConstructionObject PtOnObject=null;
        ConstructionObject PtObject=null;
        final Enumeration e=magnetObjects.elements();
        int dp=Integer.MAX_VALUE, dm=Integer.MAX_VALUE;
        int rayp=0, raym=0;
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            final ConstructionObject o=mo.obj();
            final int mRay=(mo.ray()>Integer.MIN_VALUE)?mo.ray()
                    :getMagnetRay();
            final int mRayAbs=Math.abs(mRay);
            if (mRayAbs==0) {
                continue;
            }
            if (!o.valid()) {
                continue;
            }
            if (o instanceof PointObject) {
                final int i=o.getDistance(this);
                if ((i<=mRayAbs)&&(i<dp)) {
                    PtObject=o;
                    rayp=mRay;
                    dp=i;
                }
            } else if (o instanceof PointonObject) {
                final int i=o.getDistance(this);
                if ((i<=mRayAbs)&&(i<dm)) {
                    PtOnObject=o;
                    raym=mRay;
                    dm=i;
                }
            }
        }
        if (PtObject!=null) {
            final PointObject pt=(PointObject) PtObject;
            setXYaway(pt.getX(), pt.getY(), rayp);
            if (PtObject!=CurrentMagnetObject) {
                CurrentMagnetObject=PtObject;
                CurrentMagnetRay=rayp;
                Cn.reorderConstruction();
            }
            pipe_tools.Magnet_To_HTML(this.getName(), CurrentMagnetObject.getName());
        } else if (PtOnObject!=null) {
            final PointonObject pt=(PointonObject) PtOnObject;
            if (PtOnObject!=CurrentMagnetObject) {
                CurrentMagnetObject=PtOnObject;
                CurrentMagnetRay=raym;
                Cn.reorderConstruction();
            }
            pt.project(this);
            pipe_tools.Magnet_To_HTML(this.getName(), CurrentMagnetObject.getName());
        } else {
            CurrentMagnetObject=null;
            CurrentMagnetRay=Integer.MIN_VALUE;
            if (magnetObjects.size()>0) {
                pipe_tools.Magnet_To_HTML(this.getName(), "");
            }
        }
    }

    public void setCurrentMagnetObject() {
        ConstructionObject PtOnObject=null;
        ConstructionObject PtObject=null;
        final Enumeration e=magnetObjects.elements();
        int dp=Integer.MAX_VALUE, dm=Integer.MAX_VALUE;
        int rayp=0, raym=0;
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            final ConstructionObject o=mo.obj();
            final int mRay=(mo.ray()>Integer.MIN_VALUE)?mo.ray()
                    :getMagnetRay();
            if (o instanceof PointObject) {
                final int i=o.getDistance(this);
                if ((i<=mRay)&&(i<dp)) {
                    PtObject=o;
                    rayp=mRay;
                    dp=i;
                }
            } else if (o instanceof PointonObject) {
                final int i=o.getDistance(this);
                if ((i<=mRay)&&(i<dm)) {
                    PtOnObject=o;
                    raym=mRay;
                    dm=i;
                }
            }
        }
        if (PtObject!=null) {
            CurrentMagnetObject=PtObject;
            CurrentMagnetRay=rayp;
        } else if (PtOnObject!=null) {
            CurrentMagnetObject=PtOnObject;
            CurrentMagnetRay=raym;
        } else {
            CurrentMagnetObject=null;
            CurrentMagnetRay=Integer.MIN_VALUE;
        }
    }

    public ConstructionObject getCurrentMagnetObject() {
        return CurrentMagnetObject;
    }

    public void followMagnetObject() {
        if (CurrentMagnetObject!=null) {
            if (CurrentMagnetObject instanceof PointObject) {
                final PointObject pt=(PointObject) CurrentMagnetObject;
                if (CurrentMagnetRay>0) {
                    setXY(pt.getX(), pt.getY());
                }
            } else if (CurrentMagnetObject instanceof PointonObject) {
                project(CurrentMagnetObject, Alpha);
            }
        }
        magnet();
    }

    @Override
    public int getDistance(final PointObject P) {
        final double d=Math.sqrt(((getX()-P.getX())*(getX()-P.getX())+(getY()-P.getY())*(getY()-P.getY())));
        return (int) Math.round(d*Cn.getPixel());
    }

    public void project(final ConstructionObject o) {
        if (!(o instanceof PointonObject)) {
            return;
        }
        ((PointonObject) o).project(this);
        if (UseAlpha) {
            AlphaValid=true;
        }
    }

    public void project(final ConstructionObject o, final double alpha) {
        ((PointonObject) o).project(this, alpha);
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        updateText();
        updateMagnetObjects();
        if (Bound!=null) {
            xml.printArg("on", Bound.getName());
            if (KeepInside) {
                xml.printArg("inside", "true");
            }
        }
        if (magnetObjects.size()>0) {
            xml.printArg("magnetobjs", getMagnetObjectsString());
            xml.printArg("magnetd", ""+magnetRayExp.toString());
        }
        if (haveBoundOrder()) {
            xml.printArg("boundorder", String.valueOf(BoundOrder));
        }
        if (Fixed&&EX!=null) {
            xml.printArg("x", EX.toString());
            xml.printArg("actx", ""+X);
        } else {
            if (Bound!=null&&AlphaValid&&UseAlpha) {
                xml.printArg("alpha", ""+Alpha);
            }
            xml.printArg("x", ""+X);
        }
        if (Fixed&&EY!=null) {
            xml.printArg("y", EY.toString());
            xml.printArg("acty", ""+Y);
        } else {
            xml.printArg("y", ""+Y);
        }
        printType(xml);
        if (Is3D) {
            xml.printArg("is3D", "true");
        
	        if (Fixed3D&&EX3D!=null) {
	            xml.printArg("x3D", EX3D.toString());
	            xml.printArg("actx3D", ""+X3D);
	        } else {
	            xml.printArg("x3D", ""+X3D);
	        }
	        if (Fixed3D&&EY3D!=null) {
	            xml.printArg("y3D", EY3D.toString());
	            xml.printArg("acty3D", ""+Y3D);
	        } else {
	            xml.printArg("y3D", ""+Y3D);
	        }
	        if (Fixed3D&&EZ3D!=null) {
	            xml.printArg("z3D", EZ3D.toString());
	            xml.printArg("actz3D", ""+Z3D);
	        } else {
	            xml.printArg("z3D", ""+Z3D);
	        }
        }
        if (Fixed) {
            xml.printArg("fixed", "true");
        }
        if (Fixed3D) {
            xml.printArg("fixed3D", "true");
        }
        if (Increment>1e-4) {
            xml.printArg("increment", ""+getIncrement());
        }
    }

    public void printType(final XmlWriter xml) {
        if (Type!=0) {
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
    public int getType() {
        return Type;
    }

    @Override
    public void setType(final int type) {
        Type=type;
    }

    public void movedBy(final ConstructionObject o) {
        MovedBy=o;
    }

    @Override
    public boolean equals(final ConstructionObject o) {
        if (!(o instanceof PointObject)||!o.valid()) {
            return false;
        }
        final PointObject p=(PointObject) o;
        return equals(X, p.X)&&equals(Y, p.Y);
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
    
    @Override
    public String getEX3D() {
        if (EX3D!=null) {
            return EX3D.toString();
        } else {
            return ""+round(X3D);
        }
    }

    @Override
    public String getEY3D() {
        if (EY3D!=null) {
            return EY3D.toString();
        } else {
            return ""+round(Y3D);
        }
    }
    
    @Override
    public String getEZ3D() {
        if (EZ3D!=null) {
            return EZ3D.toString();
        } else {
            return ""+round(Z3D);
        }
    }

    public boolean isOn(final ConstructionObject o) {
        if (Bound!=null) {
            return o==Bound;
        }
        return o.contains(this);
    }

    @Override
    public void translate() {
        if (Bound!=null) {
            Bound=Bound.getTranslation();
        } 
        else if (Is3D&Fixed3D) { 
            try {
            	setFixed(EX3D.toString(), EY3D.toString(), EZ3D.toString());
            	EX3D.translate();
                EY3D.translate();
                EZ3D.translate();
            	}
            	catch (final Exception e) {}
        }
        
        else if (Fixed) {
            try {
                setFixed(EX.toString(), EY.toString());
                EX.translate();
                EY.translate();
            } catch (final Exception e) {
                }
        }
        magnetRayExp.translate();
        translateMagnetObjects();
    }

    @Override
    public Enumeration depending() {
        super.depending();
        if (Bound!=null) {
            DL.add(Bound);
        }
        if (Fixed) {
            if (EX!=null) {
                EX.addDep(this);
            }
            if (EY!=null) {
                EY.addDep(this);
            }
        }
        if (Is3D&&Fixed3D) {
            if (EX3D!=null) {
                EX3D.addDep(this);
            }
            if (EY3D!=null) {
                EY3D.addDep(this);
            }
            if (EZ3D!=null) {
                EZ3D.addDep(this);
            }
        }
        return DL.elements();

    }

    @Override
    public void snap(final ZirkelCanvas zc) {
        final double d=zc.getGridSize()/2;
        X=Math.round(X/d)*d;
        Y=Math.round(Y/d)*d;
        updateText();
    }

    public void setHalfIncrement(final ZirkelCanvas zc) {
        final double d=zc.getGridSize()/2;
        setIncrement(d);
        validate();
    }

    @Override
    public void updateCircleDep() {
        if (Bound!=null&&Bound instanceof PrimitiveCircleObject) {
            ((PrimitiveCircleObject) Bound).addDep(this);
        }
        if (Bound!=null&&Bound instanceof PrimitiveLineObject) {
            ((PrimitiveLineObject) Bound).addDep(this);
        }
    }

    public boolean isPointOn() {
        return Bound!=null;
    }

    public boolean isPointOnOrMagnet() {
        return (Bound!=null)||(CurrentMagnetObject!=null);
    }

    public void setLaterBind(final String s) {
        LaterBind=s;
    }

    // for macro constructions :
    @Override
    public ConstructionObject copy(final double x, final double y) {
        ConstructionObject o=null;
        try {
            o=(ConstructionObject) clone();
            setTranslation(o);
            o.translateConditionals();
            o.translate();
            o.setName();
            o.updateText();
            o.setBreak(false);
            // o.setTarget(false);
            // if the target is a Point inside polygon, then try
            // to follow the mouse ! :
            if (KeepInside&&Bound!=null&&Bound instanceof AreaObject) {
                o.move(x, y);
            }
        } catch (final Exception e) {
        }
        return o;
    }

    public void computeBarycentricCoords() {
        if (Bound==null) {
            return;
        }

        if (Bound instanceof QuadricObject) {
            final QuadricObject quad=((QuadricObject) Bound);
            final PointObject A=quad.P[0];
            final PointObject B=quad.P[1];
            final PointObject C=quad.P[2];
            final double a=B.getX()-A.getX();
            final double b=C.getX()-A.getX();
            final double c=this.getX()-A.getX();
            final double d=B.getY()-A.getY();
            final double e=C.getY()-A.getY();
            final double f=this.getY()-A.getY();
            final double det=a*e-d*b;
            if (det!=0) {
                Gx=(c*e-b*f)/det;
                Gy=(a*f-c*d)/det;
                BarycentricCoordsInitialzed=true;
            }
        } else if (KeepInside&&Bound instanceof AreaObject) {
            final AreaObject area=((AreaObject) Bound);
            if (area.V.size()>2) {
                final PointObject A=(PointObject) area.V.get(0);
                final PointObject B=(PointObject) area.V.get(1);
                final PointObject C=(PointObject) area.V.get(2);
                final double a=B.getX()-A.getX();
                final double b=C.getX()-A.getX();
                final double c=this.getX()-A.getX();
                final double d=B.getY()-A.getY();
                final double e=C.getY()-A.getY();
                final double f=this.getY()-A.getY();
                final double det=a*e-d*b;
                if (det!=0) {
                    Gx=(c*e-b*f)/det;
                    Gy=(a*f-c*d)/det;
                    BarycentricCoordsInitialzed=true;
                }
            }
        }
    }

    @Override
    public void setInside(final boolean flag) {
        KeepInside=flag;
        computeBarycentricCoords();
    }

    @Override
    public boolean isInside() {
        return KeepInside;
    }

    public boolean isFixedInWindow() {
        String xstr="(windoww/(windoww-d(windoww)))*(x("+getName()+")-windowcx)+windowcx+d(windowcx)";
        String ystr="(windoww/(windoww-d(windoww)))*(y("+getName()+")-windowcy)+windowcy+d(windowcy)";
        if ((xstr.equals(getEX()))&&(ystr.equals(getEY()))) {
            return true;
        }
        ;
        return false;
    }

    @Override
    public void laterBind(final Construction c) {
        if (LaterBind.equals("")) {
            return;
        }
        final ConstructionObject o=c.find(LaterBind);
        if (o!=null&&((o instanceof PointonObject)||(o instanceof InsideObject))) {
            Bound=o;
            updateText();
            validate();
        }
        LaterBind="";
    }

    public void setAlpha(final double alpha) {
        Alpha=alpha;
        AlphaValid=true;
    }

    public void setUseAlpha(final boolean flag) {
        UseAlpha=flag;
    }

    public boolean useAlpha() {
        return UseAlpha;
    }

    public double getAlpha() {
        return Alpha;
    }

    @Override
    public void round() {
        move(round(X, ZirkelCanvas.LengthsFactor), round(Y,
                ZirkelCanvas.LengthsFactor));
    }

    /**
     * For bounded points.
     */
    @Override
    public void setKeepClose(final double x, final double y) {
        KeepClose=true;
        XcOffset=x-X;
        YcOffset=y-Y;
    }

    @Override
    public boolean canKeepClose() {
        return true;
    }

    public boolean dontUpdate() {
        return DontUpdate;
    }

    public void dontUpdate(final boolean flag) {
        DontUpdate=flag;
    }

    /**
     * Overwrite setting of default things for macro targets.
     */
    // public void setTargetDefaults ()
    // { super.setTargetDefaults();
    // Type=Cn.DefaultType;
    // }
    @Override
    public void startDrag(final double x, final double y) {
    }

    @Override
    public void dragTo(final double x, final double y) {
        clearBoundOrder();
        if ((X==x)&&(Y==y)) {
            return;
        }
        // System.out.println(getName()+" : dragTo !");
        move(x, y);
    }

    @Override
    public double getOldX() {
        return 0;
    }

    @Override
    public double getOldY() {
        return 0;
    }

    @Override
    public void setIncrement(final double inc) {
        Increment=inc;
    }

    @Override
    public double getIncrement() {
        return Increment;
    }

    @Override
    public boolean isDriverObject() {
        if (Bound!=null) {
            return true;
        }
        // if (Fixed) return false;
        return Moveable&&!Keep;
    }

    @Override
    public boolean somethingChanged() {
        return ((getX()!=LASTX)||(getY()!=LASTY));
    }

    @Override
    public void clearChanges() {
        LASTX=getX();
        LASTY=getY();
    }
    
    public void setEX3D(String s) {
        EX3D=new Expression(s, JZirkelCanvas.getCurrentZC().getConstruction(), this);
    }
}
