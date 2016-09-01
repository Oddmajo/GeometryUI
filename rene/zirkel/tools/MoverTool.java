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

import eric.bar.JProperties;
import eric.bar.JPropertiesBar;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import rene.gui.Global;
import rene.util.MyVector;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.FixedCircleObject;
import rene.zirkel.objects.MoveableObject;
import rene.zirkel.objects.PointObject;

/**
 * @author Rene
 * Tool to move objects around. Can move several objects at once.
 */
public class MoverTool extends ObjectConstructor {

    ConstructionObject P;
    boolean Selected=false;
    boolean ShowsValue, ShowsName;
    boolean Grab;
    boolean WasDrawable, ChangedDrawable;
    MyVector V=null;
    double OldX, OldY;
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
//		 System.out.println("dragging "+P.getName());

        zc.getConstruction().dontAlternate(false);
        synchronized (zc) {
            double oldx=0, oldy=0;
            if ((Global.getParameter("restrictedmove", false)||e.isShiftDown())
                    &&P instanceof PointObject) {
                if (IntersectionBecameInvalid) {
                    zc.getConstruction().dontAlternate(true);
                    IntersectionBecameInvalid=false;
                }
                oldx=((PointObject) P).getX();
                oldy=((PointObject) P).getY();
            }
            ((MoveableObject) P).dragTo(zc.x(e.getX()), zc.y(e.getY()));
            if ((Global.getParameter("options.movename", false)&&!P.isFixed())
                    ||(Global.getParameter("options.movefixname", true)&&P.isFixed())) {
                P.setShowValue(true);
                P.setShowName(true);
            }
            P.updateText();
            if (V!=null&&(P instanceof PointObject)) {
                final double dx=((PointObject) P).getX()-OldX, dy=((PointObject) P).getY()-OldY;
                final Enumeration en=V.elements();
                while (en.hasMoreElements()) {
                    final PointObject p=(PointObject) en.nextElement();
                    p.move(p.getX()+dx, p.getY()+dy);
                    p.updateText();
                }
                OldX=((PointObject) P).getX();
                OldY=((PointObject) P).getY();
            }
            if (P instanceof PointObject) {
                final PointObject Po=(PointObject) P;
                if (Po.dependsOnItselfOnly()) {
                    Po.dontUpdate(true);
                    zc.dovalidate();
                    Po.dontUpdate(false);
                }
                Po.magnet();
//                System.out.println("start dragging "+P.getName());
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
