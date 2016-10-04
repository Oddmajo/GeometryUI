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
package rene.zirkel.constructors;

// file: PointConstructor.java
import java.util.ArrayList;
import java.util.Enumeration;

import java.awt.event.MouseEvent;

import eric.GUI.palette.PaletteManager;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Selector;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.InsideObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;
import rene.zirkel.objects.TwoPointLineObject;

public class BoundedPointConstructor extends ObjectConstructor implements
        Selector {

    boolean Control;


    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }
        Control=e.isControlDown();

        final ConstructionObject o=zc.selectWithSelector(e.getX(), e.getY(),
                this);
        if (o==null) {
            return;
        }
        final PointObject p=new PointObject(zc.getConstruction(), zc.x(e.getX()), zc.y(e.getY()), o);
        if (!e.isShiftDown()) {
            p.setUseAlpha(true);
        }
        // if (Control && o instanceof InsideObject) p.setInside(true);
        if (o instanceof InsideObject) {
            p.setInside(true);
        }
        if (zc.is3D()) { //Dibs : 2D -> 3D.
        	zc.validate(); //Dibs	
        	if (((PointObject) p).getBound() instanceof AreaObject) {
        			AreaObject surface= (AreaObject) ((PointObject) p).getBound();
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
            				double x_O=zc.getConstruction().find("O").getX();
            				double x_X=zc.getConstruction().find("X").getX();
            				double x_Y=zc.getConstruction().find("Y").getX();
            				double x_Z=zc.getConstruction().find("Z").getX();
            				double y_O=zc.getConstruction().find("O").getY();
            				double y_X=zc.getConstruction().find("X").getY();
            				double y_Y=zc.getConstruction().find("Y").getY();
            				double y_Z=zc.getConstruction().find("Z").getY();
            				double coeffa=(x1-x0)*(x_X-x_O)+(y1-y0)*(x_Y-x_O)+(z1-z0)*(x_Z-x_O);
            				double coeffb=(x2-x0)*(x_X-x_O)+(y2-y0)*(x_Y-x_O)+(z2-z0)*(x_Z-x_O);
            				double coeffc=(x1-x0)*(y_X-y_O)+(y1-y0)*(y_Y-y_O)+(z1-z0)*(y_Z-y_O);
            				double coeffd=(x2-x0)*(y_X-y_O)+(y2-y0)*(y_Y-y_O)+(z2-z0)*(y_Z-y_O);
            				double coeffe=p.getX()-x_O-x0*(x_X-x_O)-y0*(x_Y-x_O)-z0*(x_Z-x_O);
            				double coefff=p.getY()-y_O-x0*(y_X-y_O)-y0*(y_Y-y_O)-z0*(y_Z-y_O);
            				double alpha1=(coeffe*coeffd-coefff*coeffb)/(coeffa*coeffd-coeffb*coeffc);
            				double beta1=(coeffa*coefff-coeffc*coeffe)/(coeffa*coeffd-coeffb*coeffc);
            				((PointObject) p).setX3D(x0+alpha1*(x1-x0)+beta1*(x2-x0));
            				((PointObject) p).setY3D(y0+alpha1*(y1-y0)+beta1*(y2-y0));
            				((PointObject) p).setZ3D(z0+alpha1*(z1-z0)+beta1*(z2-z0));
            				((PointObject) p).setIs3D(true);
            				p.validate();
        	            	} catch (final Exception eBary) {
        	            }
        			}
        		}
        	}
        zc.addObject(p); 
        p.validate();
        p.setDefaults();
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        Control=e.isControlDown();
        zc.indicateWithSelector(e.getX(), e.getY(), this);
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.name("message.boundedpoint",
                "Bounded Point: Choose a circle or line!"));
    }

    public boolean isAdmissible(final ZirkelCanvas zc,
            final ConstructionObject o) {
        // if (Control && o instanceof InsideObject) return true;
        // else if (!Control && o instanceof PointonObject) return true;

        if (o instanceof InsideObject) {
            return true;
        }
        return false;
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {

        if (!testTree(tree, "PointOn")) {
            return false;
        }
        final XmlTag tag=tree.getTag();
        if (!tag.hasParam("on")) {
            throw new ConstructionException("Point bound missing!");
        }
        try {
            final ConstructionObject o=(ConstructionObject) c.find(tag.getValue("on"));
            if (o!=null&&!(o instanceof PointonObject)&&!(o instanceof InsideObject)) {
                throw new ConstructionException("");
            }
            double x=0, y=0;
            double x3D=0, y3D=0, z3D=0;
            try {
                x=new Double(tag.getValue("x")).doubleValue();
                y=new Double(tag.getValue("y")).doubleValue();
            } catch (final Exception e) {
            }
            try {
                x3D=new Double(tag.getValue("x3D")).doubleValue();
                y3D=new Double(tag.getValue("y3D")).doubleValue();
                z3D=new Double(tag.getValue("z3D")).doubleValue();
            } catch (final Exception e) {
            }
            PointObject p;
            if (o!=null) {
                p=new PointObject(c, x, y, o);
            } else {
                p=new PointObject(c, x, y);
                p.setLaterBind(tag.getValue("on"));
            }
            p.setInside(tag.hasTrueParam("inside"));
            try {
                final double alpha=new Double(tag.getValue("alpha")).doubleValue();
                p.setAlpha(alpha);
                p.setUseAlpha(true);
                if (tag.hasParam("on")) {
                    final ConstructionObject on=c.find(tag.getValue("on"));
                    if (on!=null) {
                        p.project(on, alpha);
                    }
                }
            } catch (final Exception e) {
            }
            if (tag.hasParam("shape")) {
                final String s=tag.getValue("shape");
                if (s.equals("square")) {
                    p.setType(0);
                }
                if (s.equals("diamond")) {
                    p.setType(1);
                }
                if (s.equals("circle")) {
                    p.setType(2);
                }
                if (s.equals("dot")) {
                    p.setType(3);
                }
                if (s.equals("cross")) {
                    p.setType(4);
                }
                if (s.equals("dcross")) {
                    p.setType(5);
                }
            }
            if (tag.hasParam("boundorder")) {
                p.setBoundOrder(Double.valueOf(tag.getValue("boundorder")).doubleValue());
            }
            if (tag.hasParam("is3D")) {
            	p.setIs3D(true);
            	p.move3D(x3D,y3D,z3D);
            	p.move(c.find("O").getX()+x3D*(c.find("X").getX()-c.find("O").getX())+y3D*(c.find("Y").getX()-c.find("O").getX())+z3D*(c.find("Z").getX()-c.find("O").getX()), c.find("O").getY()+x3D*(c.find("X").getY()-c.find("O").getY())+y3D*(c.find("Y").getY()-c.find("O").getY())+z3D*(c.find("Z").getY()-c.find("O").getY()));
            }
            setName(tag, p);
            set(tree, p);
            c.add(p);
            setConditionals(tree, c, p);
            if (tag.hasParam("fixed")) {
                p.setFixed(tag.getValue("x"), tag.getValue("y"));
            }
            if (tag.hasParam("increment")) {
                try {
                    p.setIncrement(new Double(tag.getValue("increment")).doubleValue());
                } catch (final Exception e) {
                }
            }
            
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ConstructionException("Illegal point bound!");
        }
        return true;
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        zc.setPrompt(Global.name("prompt.pointon"));
    }
}
