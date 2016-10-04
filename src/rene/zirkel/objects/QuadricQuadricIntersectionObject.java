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
import java.util.ArrayList;
import java.util.Collections;
import rene.util.xml.XmlWriter;
import rene.zirkel.construction.Construction;
import rene.zirkel.structures.Coordinates4;
import rene.zirkel.structures.CoordinatesXY;

public class QuadricQuadricIntersectionObject extends IntersectionObject {

    private int Rank; //numéro de l'intersection, à savoir 0,1,2 ou 3

    public QuadricQuadricIntersectionObject(final Construction c,
            final QuadricObject P1, final QuadricObject P2,
            final int rank) {
        super(c, P1, P2);
        Rank=rank;
        validate();
    }

    // public void updateCircleDep ()
    // { ((QuadricObject)P2).addDep(this);
    // ((PrimitiveLineObject)P1).addDep(this);
    // }
    @Override
    public void validate() {
        final boolean oldvalid=Valid;
        if (!P1.valid()||!P2.valid()) {
            Valid=false;
        } else {
            Valid=true;
        }
        if (!Valid) {
            return;
        }
        
        final ArrayList<CoordinatesXY> c=QuadricObject.intersect((QuadricObject) P1, (QuadricObject) P2);
        Collections.sort(c);
        
        if (Double.isNaN(c.get(Rank).X)) {
            if (oldvalid&&getConstruction().shouldSwitch()) {
                doSwitch();
                if (!getConstruction().noteSwitch()) {
                    Switched=false;
                }
            }
            ;
            Valid=false;
            return;
        }
        setXY(c.get(Rank).X, c.get(Rank).Y);
        if (((QuadricObject) P1).getP()[0].is3D()||((QuadricObject) P1).getP()[1].is3D()||((QuadricObject) P1).getP()[2].is3D()||((QuadricObject) P1).getP()[3].is3D()||((QuadricObject) P1).getP()[4].is3D()||
            ((QuadricObject) P2).getP()[0].is3D()||((QuadricObject) P2).getP()[1].is3D()||((QuadricObject) P2).getP()[2].is3D()||((QuadricObject) P2).getP()[3].is3D()||((QuadricObject) P2).getP()[4].is3D()) {
        	try {
				double x0=((QuadricObject) P1).getP()[0].getX3D();
				double y0=((QuadricObject) P1).getP()[0].getY3D();
				double z0=((QuadricObject) P1).getP()[0].getZ3D();
				double x1=((QuadricObject) P1).getP()[1].getX3D();
				double y1=((QuadricObject) P1).getP()[1].getY3D();
				double z1=((QuadricObject) P1).getP()[1].getZ3D();
				double x2=((QuadricObject) P1).getP()[2].getX3D();
				double y2=((QuadricObject) P1).getP()[2].getY3D();
				double z2=((QuadricObject) P1).getP()[2].getZ3D();
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
				final double xM=x0+alpha1*(x1-x0)+beta1*(x2-x0);
				final double yM=y0+alpha1*(y1-y0)+beta1*(y2-y0);
				final double zM=z0+alpha1*(z1-z0)+beta1*(z2-z0);
				setX3D(xM);
				setY3D(yM);
				setZ3D(zM);
				setIs3D(true);
            	} catch (final Exception eBary) {
            }
        	// si les intersections n'existent pas en 3D, on les rendra superhidden
        	try {	// Dibs intersection 3D de cercles
            	double a1=((QuadricObject) P1).getP()[0].getX3D();
            	double b1=((QuadricObject) P1).getP()[0].getY3D();
            	double c1=((QuadricObject) P1).getP()[0].getZ3D();
            	double a2=((QuadricObject) P1).getP()[1].getX3D();
            	double b2=((QuadricObject) P1).getP()[1].getY3D();
            	double c2=((QuadricObject) P1).getP()[1].getZ3D();
            	double a3=((QuadricObject) P1).getP()[2].getX3D();
            	double b3=((QuadricObject) P1).getP()[2].getY3D();
            	double c3=((QuadricObject) P1).getP()[2].getZ3D();
            	double a1b=((QuadricObject) P2).getP()[0].getX3D();
            	double b1b=((QuadricObject) P2).getP()[0].getY3D();
            	double c1b=((QuadricObject) P2).getP()[0].getZ3D();
            	double a2b=((QuadricObject) P2).getP()[1].getX3D();
            	double b2b=((QuadricObject) P2).getP()[1].getY3D();
            	double c2b=((QuadricObject) P2).getP()[1].getZ3D();
            	double a3b=((QuadricObject) P2).getP()[2].getX3D();
            	double b3b=((QuadricObject) P2).getP()[2].getY3D();
            	double c3b=((QuadricObject) P2).getP()[2].getZ3D();
            	double a4=(a1+a2)/2, b4=(b1+b2)/2, c4=(c1+c2)/2;
            	double a4b=(a1b+a2b)/2, b4b=(b1b+b2b)/2, c4b=(c1b+c2b)/2;
            	double a5=(a1+a3)/2, b5=(b1+b3)/2, c5=(c1+c3)/2;
            	double a5b=(a1b+a3b)/2, b5b=(b1b+b3b)/2, c5b=(c1b+c3b)/2;
            	double a6=a2-a1, b6=b2-b1, c6=c2-c1;
            	double a6b=a2b-a1b, b6b=b2b-b1b, c6b=c2b-c1b;
            	double a7=a3-a1, b7=b3-b1, c7=c3-c1;
            	double a7b=a3b-a1b, b7b=b3b-b1b, c7b=c3b-c1b;
            	double a8=b6*c7-c6*b7, b8=c6*a7-a6*c7, c8=a6*b7-b6*a7;
            	double a8b=b6b*c7b-c6b*b7b, b8b=c6b*a7b-a6b*c7b, c8b=a6b*b7b-b6b*a7b;
            	double a9=b8*c6-c8*b6, b9=c8*a6-a8*c6, c9=a8*b6-b8*a6;
            	double a9b=b8b*c6b-c8b*b6b, b9b=c8b*a6b-a8b*c6b, c9b=a8b*b6b-b8b*a6b;
            	double a10=b7*c8-c7*b8, b10=c7*a8-a7*c8, c10=a7*b8-b7*a8;
            	double a10b=b7b*c8b-c7b*b8b, b10b=c7b*a8b-a7b*c8b, c10b=a7b*b8b-b7b*a8b;
            	double a11=a5-a4, b11=b5-b4, c11=c5-c4;
            	double a11b=a5b-a4b, b11b=b5b-b4b, c11b=c5b-c4b;
            	double det1=b9*a10-a9*b10;
            	double det1b=b9b*a10b-a9b*b10b;
            	double det2=a10*b11-b10*a11;
            	double det2b=a10b*b11b-b10b*a11b;
            	double a12=a4+det2/det1*a9, b12=b4+det2/det1*b9, c12=c4+det2/det1*c9; // centre
            	double a12b=a4b+det2b/det1b*a9b, b12b=b4b+det2b/det1b*b9b, c12b=c4b+det2b/det1b*c9b; 
            	double rcarre=(a12-a1)*(a12-a1)+(b12-b1)*(b12-b1)+(c12-c1)*(c12-c1); //rayons^2
            	double rbcarre=(a12b-a1)*(a12b-a1)+(b12b-b1)*(b12b-b1)+(c12b-c1)*(c12b-c1);
            	double n8=Math.sqrt(a8*a8+b8*b8+c8*c8); 
            	double n8b=Math.sqrt(a8b*a8b+b8b*b8b+c8b*c8b);
            	a8 /=n8; b8 /= n8; c8 /= n8;  // on norme P8
            	a8b /=n8b; b8b /= n8b; c8b /= n8b;
            	double a13=b8*c8b-c8*b8b, b13=c8*a8b-a8*c8b, c13=a8*b8b-b8*a8b; // pvect de pvects
            	double n13carre=a13*a13+b13*b13+c13*c13;
            	if (n13carre<1e-9) { // coplanaires
            		double dcentrescarre=(a12b-a12)*(a12b-a12)+(b12b-b12)*(b12b-b12)+(c12b-c12)*(c12b-c12);
            		double abs=(dcentrescarre+rcarre-rbcarre)/(2*Math.sqrt(dcentrescarre));
            		if (rcarre-abs*abs<=0) {
            			setSuperHidden(true);
            		}
            		else {
            			double ord=Math.sqrt(rcarre-abs*abs);
            			double a14=a12b-a12, b14=b12b-b12, c14=c12b-c12;
            			double a15=b8*c14-c8*b14, b15=c8*a14-a8*c14, c15=a8*b14-b8*a14;
            			double n14=Math.sqrt(a14*a14+b14*b14+c14*c14);
                		double n15=Math.sqrt(a15*a15+b15*b15+c15*c15);
                		a14/=n14; b14/=n14; c14/=n14;
                		a15/=n15; b15/=n15; c15/=n15;
                		double a16=a12+abs*a14+ord*a15, b16=b12+abs*b14+ord*b15, c16=c12+abs*c14+ord*c15;
                		double a17=a12+abs*a14-ord*a15, b17=b12+abs*b14-ord*b15, c17=c12+abs*c14-ord*c15;
                		final double xO=getConstruction().find("O").getX();
                        final double yO=getConstruction().find("O").getY();
                        final double deltaxX=getConstruction().find("X").getX()-xO;
                        final double deltaxY=getConstruction().find("Y").getX()-xO;
                        final double deltaxZ=getConstruction().find("Z").getX()-xO;
                        final double deltayX=getConstruction().find("X").getY()-yO;
                        final double deltayY=getConstruction().find("Y").getY()-yO;
                        final double deltayZ=getConstruction().find("Z").getY()-yO;
                        double posx=xO+a16*deltaxX+b16*deltaxY+c16*deltaxZ;  // coordonnées 2D
                        double posy=yO+a16*deltayX+b16*deltayY+c16*deltayZ;
                        double  erreurcarre=(c.get(Rank).X-posx)*(c.get(Rank).X-posx)+(c.get(Rank).Y-posy)*(c.get(Rank).Y-posy);
                        if (erreurcarre<1e-8) {
                        	setX3D(a16);
            				setY3D(b16);
            				setZ3D(c16);
                        }
                        posx=xO+a17*deltaxX+b17*deltaxY+c17*deltaxZ;  // coordonnées 2D 2eme sol
                        posy=yO+a17*deltayX+b17*deltayY+c17*deltayZ;
                        erreurcarre=(c.get(Rank).X-posx)*(c.get(Rank).X-posx)+(c.get(Rank).Y-posy)*(c.get(Rank).Y-posy);
                        if (erreurcarre<1e-8) {
                        	setX3D(a17);
            				setY3D(b17);
            				setZ3D(c17);
                        }
            		}
            	}
            	else { // pas coplanaires
            		boolean isGood=false;
            		double a14=a1b-a1, b14=b1b-b1, c14=c1b-c1;
            		double x1=(a14*(b7*c6b-b6b*c7)+a6b*(b14*c7-b7*c14)+a7*(b6b*c14-b14*c6b))/(a6*(b7*c6b-b6b*c7)+a6b*(b6*c7-b7*c6)+a7*(b6b*c6-b6*c6b));
            		double x2=(a14*(b6b*c6-b6*c6b)+a6*(b14*c6b-b6b*c14)+a6b*(b6*c14-b14*c6))/(a6*(b7*c6b-b6b*c7)+a6b*(b6*c7-b7*c6)+a7*(b6b*c6-b6*c6b));
            		double y1=(a6b*(b7b*c7-b7*c7b)+a7*(b6b*c7b-b7b*c6b)+a7b*(b7*c6b-b6b*c7)+a14*(b7*c6b-b6b*c7)+a6b*(b14*c7-b7*c14)+a7*(b6b*c14-b14*c6b))/(a6*(b7*c6b-b6b*c7)+a6b*(b6*c7-b7*c6)+a7*(b6b*c6-b6*c6b));
            		double y2=(a6*(b7b*c6b-b6b*c7b)+a6b*(b6*c7b-b7b*c6)+a7b*(b6b*c6-b6*c6b)+a14*(b6b*c6-b6*c6b)+a6*(b14*c6b-b6b*c14)+a6b*(b6*c14-b14*c6))/(a6*(b7*c6b-b6b*c7)+a6b*(b6*c7-b7*c6)+a7*(b6b*c6-b6*c6b));
            		double a15=a1+x1*a6+x2*a7, b15=b1+x1*b6+x2*b7, c15=c1+x1*c6+x2*c7;
            		double a16=a1+y1*a6+y2*a7, b16=b1+y1*b6+y2*b7, c16=c1+y1*c6+y2*c7;
            		double a17=a16-a15, b17=b16-b15, c17=c16-c15;
            		double a18=a15-a12, b18=b15-b12, c18=c15-c2;
            		try {
            			double x3=-(Math.sqrt((c17*c17+b17*b17+a17*a17)*rcarre+(-b17*b17-a17*a17)*c18*c18+(2*b17*b18+2*a17*a18)*c17*c18+(-b18*b18-a18*a18)*c17*c17-a17*a17*b18*b18+2*a17*a18*b17*b18-a18*a18*b17*b17)+c17*c18+b17*b18+a17*a18)/(c17*c17+b17*b17+a17*a17);
            			double x4=(Math.sqrt((c17*c17+b17*b17+a17*a17)*rcarre+(-b17*b17-a17*a17)*c18*c18+(2*b17*b18+2*a17*a18)*c17*c18+(-b18*b18-a18*a18)*c17*c17-a17*a17*b18*b18+2*a17*a18*b17*b18-a18*a18*b17*b17)-c17*c18-b17*b18-a17*a18)/(c17*c17+b17*b17+a17*a17);
            			double a19=a15+x3*a17, b19=b15+x3*b17, c19=c15+x3*c17; // soluces potentielles
            			double a20=a15+x4*a17, b20=b15+x4*b17, c20=c15+x4*c17;
            			double db19carre=(a19-a12b)*(a19-a12b)+(b19-b12b)*(b19-b12b)+(c19-c12b)*(c19-c12b);
            			double db20carre=(a20-a12b)*(a20-a12b)+(b20-b12b)*(b20-b12b)+(c20-c12b)*(c20-c12b);
            			if (Math.abs(db19carre-rbcarre)<1e-10) { // OK pour P19
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
                            double  erreurcarre=(c.get(Rank).X-posx)*(c.get(Rank).X-posx)+(c.get(Rank).Y-posy)*(c.get(Rank).Y-posy);
                            if (erreurcarre<1e-8) {
                            	isGood=true;
                				setX3D(a19);
                				setY3D(b19);
                				setZ3D(c19);
                            }
                            else if (Math.abs(db20carre-rbcarre)<1e-8) {
                            	posx=xO+a20*deltaxX+b20*deltaxY+c20*deltaxZ;  // coordonnées 2D
                                posy=yO+a20*deltayX+b20*deltayY+c20*deltayZ;
                                erreurcarre=(c.get(Rank).X-posx)*(c.get(Rank).X-posx)+(c.get(Rank).Y-posy)*(c.get(Rank).Y-posy);
                                if (erreurcarre<1e-8) {
                                	isGood=true;
                    				setX3D(a20);
                    				setY3D(b20);
                    				setZ3D(c20);
                                }
                            }
            			}
            			else if (Math.abs(db20carre-rbcarre)<1e-8) { // OK pour P20
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
                            double  erreurcarre=(c.get(Rank).X-posx)*(c.get(Rank).X-posx)+(c.get(Rank).Y-posy)*(c.get(Rank).Y-posy);
                            if (erreurcarre<1e-10) {
                            	isGood=true;
                				setX3D(a20);
                				setY3D(b20);
                				setZ3D(c20);
                            }
            			}
            		}
            	    catch (final Exception ex) {
            		setSuperHidden(true);
                    }	
            		if (!isGood) setSuperHidden(true);
            	}
            	
        	} catch (final Exception ex) {
            	}	
         }
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        super.printArgs(xml);
        xml.printArg("which", ""+Rank);
    }

    @Override
    public boolean isSwitchable() {
        return false;
    }

    @Override
    public boolean canAlternate() {
        return true;
    }
}
