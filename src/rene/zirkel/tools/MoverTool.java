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
package rene.zirkel.tools;

import eric.GUI.palette.PaletteManager;
import eric.bar.JProperties;
import eric.bar.JPropertiesBar;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Enumeration;
import rene.gui.Global;
import rene.util.MyVector;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.FixedCircleObject;
import rene.zirkel.objects.MoveableObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.QuadricObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.objects.TwoPointLineObject;
import rene.zirkel.objects.VectorObject;

/**
 * @author Rene
 * Tool to move objects around. Can move several objects at once.
 */
// with addons by Dibs for 3D
public class MoverTool extends ObjectConstructor {

    ConstructionObject P;
    boolean Selected=false;
    boolean ShowsValue, ShowsName;
    boolean Grab;
    boolean WasDrawable, ChangedDrawable;
    MyVector V=null;
    double OldX, OldY;
    double OldX3D, OldY3D, OldZ3D;
    Graphics ZCG;

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        if (!Selected&&V!=null) {
            zc.clearSelected();
        }
        if (e.isControlDown()&&V==null) // force a fixed angle or fixed circle to become drawable!
        {
            P=zc.selectObject(e.getX(), e.getY());
            ChangedDrawable=false;
            if (P instanceof FixedCircleObject
                    &&((FixedCircleObject) P).fixedByNumber()) {
                WasDrawable=((FixedCircleObject) P).isDragable();
                ChangedDrawable=true;
                ((FixedCircleObject) P).setDragable(true);
            } else if (P instanceof FixedAngleObject
                    &&((FixedAngleObject) P).fixedByNumber()) {
                WasDrawable=((FixedAngleObject) P).isDragable();
                ChangedDrawable=true;
                ((FixedAngleObject) P).setDragable(true);
            } else {
                P=null;
            }
        }
        if (P==null) // no forced moveable fixed circle or angle
        {
            if (V!=null) // try to select another point
            {
                P=zc.selectMoveablePoint(e.getX(), e.getY());
            } else // try to select any moveable object
            {
                P=zc.selectMoveableObject(e.getX(), e.getY());
            }
        }

        if (P!=null&&V!=null) // Check, if we have that point already:
        {
            final Enumeration en=V.elements();
            while (en.hasMoreElements()) {
                if (P==en.nextElement()) // point found
                {
                    if (e.isShiftDown()) {
                        P=null;
                    } // selected a point twice, but want still more points
                    else {
                        V.removeElement(P);
                    }
                    // remove the point from the list and start dragging
                    break;
                }
            }
        }
        if (P!=null) // point is selected for movement
        {
            P.setSelected(true);
            zc.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        } else // point was chosen from a list of objects
        {
            Selected=(zc.findSelectedObject()!=null);
        }
        // Highlight all selected points:
        if (V!=null) {
            final Enumeration en=V.elements();
            while (en.hasMoreElements()) {
                ((PointObject) en.nextElement()).setSelected(true);
            }
        }
        if (P!=null) {
            if (P instanceof PointObject) {
                if (e.isShiftDown()) // want more points!
                {
                    if (V==null) {
                        V=new MyVector();
                    }
                    if (P!=null) {
                        V.addElement(P);
                    }
                    P=null;
                } else if (e.isControlDown()) // show current construction while moving
                {
                    zc.grab(true);
                    Grab=true;
                } else // remember old point position
                {
                    OldX=((PointObject) P).getX();
                    OldY=((PointObject) P).getY();
                    if (P instanceof PointObject&&((PointObject)P).is3D()) {
                    	OldX3D=((PointObject) P).getX3D();
                        OldY3D=((PointObject) P).getY3D();
                        OldZ3D=((PointObject) P).getZ3D();
                    }
		    if(((PointObject) P) instanceof MoveableObject){
		    	zc.prepareDragActionScripts(P);
		    }
                }
            } else if (P instanceof MoveableObject) // call startDrag method of the object
            {
                ((MoveableObject) P).startDrag(zc.x(e.getX()), zc.y(e.getY()));
                // System.out.println("start dragging "+P.getName());
                V=null;
            }
            if (ZCG!=null) {
                ZCG.dispose();
                ZCG=null;
            }
            ZCG=zc.getGraphics();
        }
        zc.repaint();
        showStatus(zc);
        if (P!=null) {
            ShowsName=P.showName();
            ShowsValue=P.showValue();
        }
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        zc.indicateMoveableObjects(e.getX(), e.getY(), e.isControlDown());
    }
    boolean IntersectionBecameInvalid=false;

    @Override
    public void setConstructionObject(ConstructionObject obj, ZirkelCanvas zc) {
        P=obj;
        if (P!=null) {
            if (V==null) {
                V=new MyVector();
            }
            V.addElement(P);
        }
    }

    /**
     * Drag a point in a new location and recompute the construction. Can show
     * the name and the value of the object to be dragged. Take care not to move
     * the point while ZirkelCanvas.paint is active. ZirkelCanvas.paint is
     * synchronized!
     *
     * @see rene.zirkel.ObjectConstructor#mouseDragged(java.awt.event.MouseEvent,
     *      rene.zirkel.ZirkelCanvas)
     */
    @Override
    public void mouseDragged(final MouseEvent e, final ZirkelCanvas zc) {
        if (P==null) {
            return;
        }
        if (P==zc.getConstruction().find("X")||P==zc.getConstruction().find("Y")||P==zc.getConstruction().find("Z")) {
            return;
        }
//		 System.out.println("dragging "+P.getName());

        zc.getConstruction().dontAlternate(false);
        synchronized (zc) {
            double oldx=0, oldy=0;
            double oldx3D=0, oldy3D=0, oldz3D=0;
            if ((Global.getParameter("restrictedmove", false)||e.isShiftDown())
                    &&P instanceof PointObject) {
                if (IntersectionBecameInvalid) {
                    zc.getConstruction().dontAlternate(true);
                    IntersectionBecameInvalid=false;
                }
            }
            try {
            oldx=((PointObject) P).getX();
            oldy=((PointObject) P).getY();
            oldx3D=((PointObject) P).getX3D();
            oldy3D=((PointObject) P).getY3D();
            oldz3D=((PointObject) P).getZ3D();
            } catch (final Exception ex) {}
            ((MoveableObject) P).dragTo(zc.x(e.getX()), zc.y(e.getY()));
            if ((Global.getParameter("options.movename", false)&&!P.isFixed())
                    ||(Global.getParameter("options.movefixname", true)&&P.isFixed())) {
                P.setShowValue(true);
                P.setShowName(true);
            }
            if (zc.is3D()&&P instanceof PointObject&&((PointObject) P).getBound()!=null) { // Dibs : 2D -> 3D
            	//zc.validate(); Dibs	
            	if (((PointObject) P).getBound() instanceof AreaObject) {
            			AreaObject surface= (AreaObject) ((PointObject) P).getBound();
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
                				double coeffe=P.getX()-x_O-x0*(x_X-x_O)-y0*(x_Y-x_O)-z0*(x_Z-x_O);
                				double coefff=P.getY()-y_O-x0*(y_X-y_O)-y0*(y_Y-y_O)-z0*(y_Z-y_O);
                				double alpha1=(coeffe*coeffd-coefff*coeffb)/(coeffa*coeffd-coeffb*coeffc);
                				double beta1=(coeffa*coefff-coeffc*coeffe)/(coeffa*coeffd-coeffb*coeffc);
                				final double xM=x0+alpha1*(x1-x0)+beta1*(x2-x0);
                				final double yM=y0+alpha1*(y1-y0)+beta1*(y2-y0);
                				final double zM=z0+alpha1*(z1-z0)+beta1*(z2-z0);
                				((PointObject) P).setX3D(xM);
                				((PointObject) P).setY3D(yM);
                				((PointObject) P).setZ3D(zM);
                				((PointObject) P).setFixed("x(O)+("+xM+")*(x(X)-x(O))+("+yM+")*(x(Y)-x(O))+("+z1+zM+")*(x(Z)-x(O))", "y(O)+("+xM+")*(y(X)-y(O))+("+yM+")*(y(Y)-y(O))+("+zM+")*(y(Z)-y(O))");
                				((PointObject) P).setIs3D(true);
                				P.validate();
            	            	} catch (final Exception eBary) {
            	            }
            			}
            		}
            	
            else if (((PointObject) P).getBound() instanceof TwoPointLineObject) {
        		TwoPointLineObject ligne= (TwoPointLineObject) ((PointObject) P).getBound();
    			if (((PointObject) ligne.getP1()).is3D()&&((PointObject) ligne.getP2()).is3D()) {
    				try {
    					double x1=((PointObject) ligne.getP1()).getX3D();
    					double y1=((PointObject) ligne.getP1()).getY3D();
        				double z1=((PointObject) ligne.getP1()).getZ3D();
        				double x2=((PointObject) ligne.getP2()).getX3D();
        				double y2=((PointObject) ligne.getP2()).getY3D();
        				double z2=((PointObject) ligne.getP2 ()).getZ3D();
        				double x_O=zc.getConstruction().find("O").getX();
        				double x_X=zc.getConstruction().find("X").getX();
        				double x_Y=zc.getConstruction().find("Y").getX();
        				double x_Z=zc.getConstruction().find("Z").getX();
        				double alpha1=(P.getX()-x_O-x1*(x_X-x_O)-y1*(x_Y-x_O)-z1*(x_Z-x_O))/((x2-x1)*(x_X-x_O)+(y2-y1)*(x_Y-x_O)+(z2-z1)*(x_Z-x_O));
        				if (x1==x2&&y1==y2) {
        					double y_O=zc.getConstruction().find("O").getY();
            				double y_X=zc.getConstruction().find("X").getY();
            				double y_Y=zc.getConstruction().find("Y").getY();
            				double y_Z=zc.getConstruction().find("Z").getY();
        					alpha1=(P.getY()-y_O-x1*(y_X-y_O)-y1*(y_Y-y_O)-z1*(y_Z-y_O))/((x2-x1)*(y_X-y_O)+(y2-y1)*(y_Y-y_O)+(z2-z1)*(y_Z-y_O));
        				}
        				final double xM=x1+alpha1*(x2-x1);
        				final double yM=y1+alpha1*(y2-y1);
        				final double zM=z1+alpha1*(z2-z1);
        				((PointObject) P).setX3D(xM);
        				((PointObject) P).setY3D(yM);
        				((PointObject) P).setZ3D(zM);
        				((PointObject) P).setIs3D(true);
        				((PointObject) P).setFixed("x(O)+("+xM+")*(x(X)-x(O))+("+yM+")*(x(Y)-x(O))+("+z1+zM+")*(x(Z)-x(O))", "y(O)+("+xM+")*(y(X)-y(O))+("+yM+")*(y(Y)-y(O))+("+zM+")*(y(Z)-y(O))");
    	            	} catch (final Exception eBary) {
    	            		}
    					}
    			}
            else if (((PointObject) P).getBound() instanceof QuadricObject) {
        		QuadricObject quadrique= (QuadricObject) ((PointObject) P).getBound();
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
        				double coeffe=P.getX()-x_O-x0*(x_X-x_O)-y0*(x_Y-x_O)-z0*(x_Z-x_O);
        				double coefff=P.getY()-y_O-x0*(y_X-y_O)-y0*(y_Y-y_O)-z0*(y_Z-y_O);
        				double alpha1=(coeffe*coeffd-coefff*coeffb)/(coeffa*coeffd-coeffb*coeffc);
        				double beta1=(coeffa*coefff-coeffc*coeffe)/(coeffa*coeffd-coeffb*coeffc);
        				((PointObject) P).setX3D(x0+alpha1*(x1-x0)+beta1*(x2-x0));
        				((PointObject) P).setY3D(y0+alpha1*(y1-y0)+beta1*(y2-y0));
        				((PointObject) P).setZ3D(z0+alpha1*(z1-z0)+beta1*(z2-z0));
        				((PointObject) P).setIs3D(true);
    	            	} catch (final Exception eBary) {
    	            }
        			
        		}
        	}
            }

            else if (P instanceof PointObject&&((PointObject) P).is3D()&&!((PointObject) P).fixed3D()&&P!=zc.getConstruction().find("O")) {
            		try {
            			double Dx3D = Math.sin(Math.toRadians(zc.getConstruction().find("E10").getValue()))*(P.getX()-oldx)-Math.sin(Math.toRadians(zc.getConstruction().find("E11").getValue()))*Math.cos(Math.toRadians(zc.getConstruction().find("E10").getValue()))*(P.getY()-oldy);
            			double Dy3D = Math.cos(Math.toRadians(zc.getConstruction().find("E10").getValue()))*(P.getX()-oldx)+Math.sin(Math.toRadians(zc.getConstruction().find("E11").getValue()))*Math.sin(Math.toRadians(zc.getConstruction().find("E10").getValue()))*(P.getY()-oldy);
            			double Dz3D = Math.cos(Math.toRadians(zc.getConstruction().find("E11").getValue()))*(P.getY()-oldy);
            			((PointObject) P).setX3D(oldx3D+Dx3D);
            			((PointObject) P).setY3D(oldy3D+Dy3D);
            			((PointObject) P).setZ3D(oldz3D+Dz3D);
            			((PointObject) P).setFixed("x(O)+("+(oldx3D+Dx3D)+")*(x(X)-x(O))+("+(oldy3D+Dy3D)+")*(x(Y)-x(O))+("+(oldz3D+Dz3D)+")*(x(Z)-x(O))", "y(O)+("+(oldx3D+Dx3D)+")*(y(X)-y(O))+("+(oldy3D+Dy3D)+")*(y(Y)-y(O))+("+(oldz3D+Dz3D)+")*(y(Z)-y(O))");
            			P.validate();
            		}
            		catch (final Exception f) {}
            	}
            P.updateText();
            if (V!=null&&P instanceof PointObject) {
            	if (!((PointObject)P).is3D()) {
            		final double dx=((PointObject) P).getX()-OldX, dy=((PointObject) P).getY()-OldY;
                	final Enumeration en=V.elements();
                	while (en.hasMoreElements()) {
                    	PointObject p=(PointObject) en.nextElement();
                    	p.move(p.getX()+dx, p.getY()+dy);
                    	p.updateText();
                	}
                	OldX=((PointObject) P).getX();
                	OldY=((PointObject) P).getY();
            	}
            	else {
            		final double dx3D=((PointObject) P).getX3D()-OldX3D;
            		final double dy3D=((PointObject) P).getY3D()-OldY3D;
            		final double dz3D=((PointObject) P).getZ3D()-OldZ3D;
                	final Enumeration en3D=V.elements();
                	while (en3D.hasMoreElements()) {
                    	PointObject p3D=(PointObject) en3D.nextElement();
                    	if (p3D.is3D()) {
                    	p3D.move3D(p3D.getX3D()+dx3D, p3D.getY3D()+dy3D,p3D.getZ3D()+dz3D);
                    	p3D.updateText();
                    	}
                	}
                	OldX3D=((PointObject) P).getX3D();
                	OldY3D=((PointObject) P).getY3D();
                	OldZ3D=((PointObject) P).getZ3D();
            	}
            }
            if (P instanceof PointObject) {
                final PointObject Po=(PointObject) P;
                if (Po.dependsOnItselfOnly()) {
                    Po.dontUpdate(true);
                    zc.dovalidate();
                    Po.dontUpdate(false);
                }
                Po.magnet();
//              System.out.println("start dragging "+P.getName());
                zc.runDragAction();
            }
            zc.validate();
            if ((Global.getParameter("restrictedmove", false)||e.isShiftDown())
                    &&P instanceof PointObject
                    &&zc.getConstruction().intersectionBecameInvalid()) {
                ((PointObject) P).dragTo(oldx, oldy);
                IntersectionBecameInvalid=true;
                zc.validate();
            }

	    if(JPropertiesBar.isBarVisible()){
		JProperties.refreshCoords();
	    }
	    zc.update_distant(P, 2);
        }

        if (ZCG==null) {
            ZCG=zc.getGraphics();
        }
        zc.paint(ZCG);
    }

    /**
     * Release the mouse after dragging.
     *
     * @param e
     * @param zc
     * @param rightmouse
     *            Call comes from a right mouse drag!
     */
    public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc,
            final boolean rightmouse) {
//        System.out.println("released !");
//        P.setShowValue(ShowsValue);
//		P.setShowName(ShowsName);

        if (P==null) {
            return;
        }
        if (ZCG!=null) {
            ZCG.dispose();
            ZCG=null;
        }
        zc.getConstruction().haveChanged();
        P.setShowValue(ShowsValue);
        P.setShowName(ShowsName);
        JPropertiesBar.RefreshBar();
        zc.setCursor(Cursor.getDefaultCursor());
        P.setSelected(false);

        if (zc.getAxis_show()&&!rightmouse
                &&Global.getParameter("grid.leftsnap", false)) {
            P.snap(zc);
            P.round();
            P.updateText();
        }
        zc.validate();
        if (Grab) {
            zc.grab(false);
            Grab=false;
        }
        if (ChangedDrawable) {
            if (P instanceof FixedCircleObject) {
                ((FixedCircleObject) P).setDragable(WasDrawable);
            } else if (P instanceof FixedAngleObject) {
                ((FixedAngleObject) P).setDragable(WasDrawable);
            }
        }
        zc.stopDragAction();
	zc.runUpAction(P);
        zc.clearSelected();
        zc.repaint();
        P=null;
        V=null;
        Selected=false;
        showStatus(zc);

    }

    @Override
    public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc) {
//        if (P==null) {
//            zc.clearSelectionRectangle();
//            zc.repaint();
//            zc.editMultipleSelection();
//        }
        mouseReleased(e, zc, false);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        V=null;
        P=null;
        zc.clearSelected();
        zc.repaint();
        Selected=false;
    }

    @Override
    public void resetFirstTime(final ZirkelCanvas zc) {
        if (V!=null) {
            V.removeAllElements();
        }
        zc.clearSelected();
        zc.selectAllMoveableVisibleObjects();
        final Graphics g=zc.getGraphics();
        if (g!=null) {
            zc.paint(g);
            g.dispose();
            try {
                Thread.sleep(200);
            } catch (final Exception e) {
            }
            zc.clearSelected();
        }
        zc.repaint();
        Selected=false;
        P=null;
        V=null;
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        if (P==null&&!Selected) {
            zc.showStatus(Global.name("message.move.select",
                    "Move: Select a point"));
        } else if (Selected) {
            zc.showStatus(Global.name("message.move.move",
                    "Move: Move the point"));
        } else {
            zc.showStatus(Global.name("message.move.move",
                    "Move: Move the point")
                    +" ("+P.getName()+")");
        }
    }

    @Override
    public boolean useSmartBoard() {
        return false;
    }
}
