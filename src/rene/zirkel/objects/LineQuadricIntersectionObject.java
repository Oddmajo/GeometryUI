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

// file: LineCircleIntersectionObject.java

import rene.util.xml.XmlWriter;
import rene.zirkel.construction.Construction;
import rene.zirkel.structures.Coordinates;

public class LineQuadricIntersectionObject extends IntersectionObject {

	public LineQuadricIntersectionObject(final Construction c,
			final PrimitiveLineObject P1, final QuadricObject P2,
			final boolean first) {
		super(c, P1, P2);
		First = first;
		validate();
	}

	// public void updateCircleDep ()
	// { ((QuadricObject)P2).addDep(this);
	// ((PrimitiveLineObject)P1).addDep(this);
	// }

	@Override
	public void validate() {
		final boolean oldvalid = Valid;
		if (!P1.valid() || !P2.valid())
			Valid = false;
		else
			Valid = true;
		if (!Valid)
			return;
		final Coordinates c = PrimitiveLineObject.intersect(
				(PrimitiveLineObject) P1, (QuadricObject) P2);

		if (c == null) {
			if (oldvalid && getConstruction().shouldSwitch()) {
				doSwitch();
				if (!getConstruction().noteSwitch())
					Switched = false;
			} else if (oldvalid && Alternate && Away == null
					&& getConstruction().canAlternate()) {
				First = !First;
			}
			Valid = false;
			return;
		}
		double X1, Y1;
		if (getAway() != null) {
			final double x = getAway().getX(), y = getAway().getY();
			final double r = (x - c.X) * (x - c.X) + (y - c.Y) * (y - c.Y);
			final double r1 = (x - c.X1) * (x - c.X1) + (y - c.Y1) * (y - c.Y1);
			boolean flag = (r > r1);
			if (!StayAway)
				flag = !flag;
			if (flag) {
				setXY(c.X, c.Y);
				X1 = c.X1;
				Y1 = c.Y1;
			} else {
				setXY(c.X1, c.Y1);
				X1 = c.X;
				Y1 = c.Y;
			}
		} else {
			if (First) {
				setXY(c.X, c.Y);
				X1 = c.X1;
				Y1 = c.Y1;
			} else {
				setXY(c.X1, c.Y1);
				X1 = c.X;
				Y1 = c.Y;
			}
		}
		if (Restricted) {

			if (!((PrimitiveLineObject) P1).contains(X, Y)) {
				if (!((PrimitiveLineObject) P1).contains(X1, Y1)) {
					Valid = false;
				} else {
					setXY(X1, Y1);
				}

			}

		}
		if ((P1 instanceof TwoPointLineObject&&((TwoPointLineObject) P1).getP1().is3D()||((TwoPointLineObject) P1).getP2().is3D())
		||((QuadricObject) P2).getP()[0].is3D()||((QuadricObject) P2).getP()[1].is3D()||((QuadricObject) P2).getP()[2].is3D()||((QuadricObject) P2).getP()[3].is3D()||((QuadricObject) P2).getP()[4].is3D()) {
			TwoPointLineObject ligne= (TwoPointLineObject) P1;
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
				final double xM=x1+alpha1*(x2-x1);
				final double yM=y1+alpha1*(y2-y1);
				final double zM=z1+alpha1*(z2-z1);
				setX3D(xM);
				setY3D(yM);
				setZ3D(zM);
				setIs3D(true);
				//setFixed("x(O)+("+xM+")*(x(X)-x(O))+("+yM+")*(x(Y)-x(O))+("+z1+zM+")*(x(Z)-x(O))", "y(O)+("+xM+")*(y(X)-y(O))+("+yM+")*(y(Y)-y(O))+("+zM+")*(y(Z)-y(O))");
            	} catch (final Exception eBary) {
            		}
			// si les intersections n'existent pas en 3D, on les rendra superhidden
        	try {	
        		boolean isGood=false;
            	double a1=((QuadricObject) P2).getP()[0].getX3D();
            	double b1=((QuadricObject) P2).getP()[0].getY3D();
            	double c1=((QuadricObject) P2).getP()[0].getZ3D();
            	double a2=((QuadricObject) P2).getP()[1].getX3D();
            	double b2=((QuadricObject) P2).getP()[1].getY3D();
            	double c2=((QuadricObject) P2).getP()[1].getZ3D();
            	double a3=((QuadricObject) P2).getP()[2].getX3D();
            	double b3=((QuadricObject) P2).getP()[2].getY3D();
            	double c3=((QuadricObject) P2).getP()[2].getZ3D();
            	double a4=(a1+a2)/2, b4=(b1+b2)/2, c4=(c1+c2)/2;
            	double a5=(a1+a3)/2, b5=(b1+b3)/2, c5=(c1+c3)/2;
            	double a6=a2-a1, b6=b2-b1, c6=c2-c1;
            	double a7=a3-a1, b7=b3-b1, c7=c3-c1;
            	double a8=b6*c7-c6*b7, b8=c6*a7-a6*c7, c8=a6*b7-b6*a7;
            	double n8=Math.sqrt(a8*a8+b8*b8+c8*c8); 
            	a8 /=n8; b8 /= n8; c8 /= n8;  // on norme P8
            	double a9=b8*c6-c8*b6, b9=c8*a6-a8*c6, c9=a8*b6-b8*a6;
            	double a10=b7*c8-c7*b8, b10=c7*a8-a7*c8, c10=a7*b8-b7*a8;
            	double a11=a5-a4, b11=b5-b4, c11=c5-c4;
            	double det1=b9*a10-a9*b10;
            	double det2=a10*b11-b10*a11;
            	double a12=a4+det2/det1*a9, b12=b4+det2/det1*b9, c12=c4+det2/det1*c9; // centre
            	double rcarre=(a12-a1)*(a12-a1)+(b12-b1)*(b12-b1)+(c12-c1)*(c12-c1); //rayon^2
            	double a15=((PointObject) ligne.getP1()).getX3D();
				double b15=((PointObject) ligne.getP1()).getY3D();
				double c15=((PointObject) ligne.getP1()).getZ3D();
				double a16=((PointObject) ligne.getP2()).getX3D();
				double b16=((PointObject) ligne.getP2()).getY3D();
				double c16=((PointObject) ligne.getP2 ()).getZ3D();
				double a17=a16-a15, b17=b16-b15, c17=c16-c15;
        		double a18=a15-a12, b18=b15-b12, c18=c15-c12;
        		try {
        			double x3=-(Math.sqrt((c17*c17+b17*b17+a17*a17)*rcarre+(-b17*b17-a17*a17)*c18*c18+(2*b17*b18+2*a17*a18)*c17*c18+(-b18*b18-a18*a18)*c17*c17-a17*a17*b18*b18+2*a17*a18*b17*b18-a18*a18*b17*b17)+c17*c18+b17*b18+a17*a18)/(c17*c17+b17*b17+a17*a17);
        			double x4=(Math.sqrt((c17*c17+b17*b17+a17*a17)*rcarre+(-b17*b17-a17*a17)*c18*c18+(2*b17*b18+2*a17*a18)*c17*c18+(-b18*b18-a18*a18)*c17*c17-a17*a17*b18*b18+2*a17*a18*b17*b18-a18*a18*b17*b17)-c17*c18-b17*b18-a17*a18)/(c17*c17+b17*b17+a17*a17);
        			double a19=a15+x3*a17, b19=b15+x3*b17, c19=c15+x3*c17; // soluces potentielles
        			double a20=a15+x4*a17, b20=b15+x4*b17, c20=c15+x4*c17;
        			double a21=a19-a1, b21=b19-b1, c21=c19-c1;
        			double a22=a20-a1, b22=b20-b1, c22=c20-c1;
        			double pscal19 =a8*a21+b8*b21+c8*c21;
        			double pscal20 =a8*a22+b8*b22+c8*c22;
        			if (Math.abs(pscal19)<1e-8) { // OK pour P19
        				final double xO=getConstruction().find("O").getX();
                        final double yO=getConstruction().find("O").getY();
                        final double deltaxX=getConstruction().find("X").getX()-xO;
                        final double deltaxY=getConstruction().find("Y").getX()-xO;
                        final double deltaxZ=getConstruction().find("Z").getX()-xO;
                        final double deltayX=getConstruction().find("X").getY()-yO;
                        final double deltayY=getConstruction().find("Y").getY()-yO;
                        final double deltayZ=getConstruction().find("Z").getY()-yO;
                        double posx=xO+a19*deltaxX+b19*deltaxY+c19*deltaxZ;  // coordonnées 2D
                        double posy=yO+a19*deltayX+b19*deltayY+c19*deltayZ;
                        double  erreurcarre=(c.X-posx)*(c.X-posx)+(c.Y-posy)*(c.Y-posy);
                        double  erreurcarre2=(c.X1-posx)*(c.X1-posx)+(c.Y1-posy)*(c.Y1-posy);
                        if (erreurcarre<1e-8||erreurcarre2<1e-8) {
                        	isGood=true;
            				setX3D(a19);
            				setY3D(b19);
            				setZ3D(c19);
                        }
                        else if (Math.abs(pscal20)<1e-8) {
                        	posx=xO+a20*deltaxX+b20*deltaxY+c20*deltaxZ;  // coordonnées 2D
                            posy=yO+a20*deltayX+b20*deltayY+c20*deltayZ;
                            erreurcarre=(c.X-posx)*(c.X-posx)+(c.Y-posy)*(c.Y-posy);
                            erreurcarre2=(c.X1-posx)*(c.X1-posx)+(c.Y1-posy)*(c.Y1-posy);
                            if (erreurcarre<1e-8||erreurcarre2<1e-8) {
                            	isGood=true;
                				setX3D(a20);
                				setY3D(b20);
                				setZ3D(c20);
                            }
                        }
        			}
        			else if (Math.abs(pscal20)<1e-8) { // OK pour P20
        				final double xO=getConstruction().find("O").getX();
                        final double yO=getConstruction().find("O").getY();
                        final double deltaxX=getConstruction().find("X").getX()-xO;
                        final double deltaxY=getConstruction().find("Y").getX()-xO;
                        final double deltaxZ=getConstruction().find("Z").getX()-xO;
                        final double deltayX=getConstruction().find("X").getY()-yO;
                        final double deltayY=getConstruction().find("Y").getY()-yO;
                        final double deltayZ=getConstruction().find("Z").getY()-yO;
                        double posx=xO+a20*deltaxX+b20*deltaxY+c20*deltaxZ;  // coordonnées 2D
                        double posy=yO+a20*deltayX+b20*deltayY+c20*deltayZ;
                        double  erreurcarre=(c.X-posx)*(c.X-posx)+(c.Y-posy)*(c.Y-posy);
                        double  erreurcarre2=(c.X1-posx)*(c.X1-posx)+(c.Y1-posy)*(c.Y1-posy);
                        if (erreurcarre<1e-8||erreurcarre2<1e-8) {
                        	isGood=true;
            				setX3D(a20);
            				setY3D(b20);
            				setZ3D(c20);
                        }
        			}
        		}
        	    catch (final Exception ex) {
        		//setSuperHidden(true);
                }	
        		if (!isGood) {
        			setSuperHidden(true);
        			}
            	}
            	
        	 catch (final Exception ex) {
            	}	
		}
	}

	@Override
	public void printArgs(final XmlWriter xml) {
		super.printArgs(xml);
		if (First)
			xml.printArg("which", "first");
		else
			xml.printArg("which", "second");
	}

	@Override
	public boolean isSwitchable() {
		return true;
	}

	@Override
	public boolean canAlternate() {
		return true;
	}
}
