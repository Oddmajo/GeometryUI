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

import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;

public class SelectTool extends ObjectConstructor {

    private static final int hotSize=5;
    private Point DragLoc=null;
    private Point GrowEast=null;
    private Point GrowSouth=null;
    private Point GrowNorth=null;
    private Point GrowWest=null;
    private Point GrowSouthEast=null;
    private ZirkelCanvas ZC;

    public SelectTool(ZirkelCanvas zc) {
        ZC=zc;
        zc.showCopyRectangle();
    }

    public boolean isInside(Point pt, ZirkelCanvas zc) {
        Rectangle sel=(Rectangle) zc.getCopyRectangle();
        if (sel==null) {
            return false;
        }
        sel=(Rectangle) sel.clone();
        sel.grow(-hotSize, -hotSize);
        return sel.contains(pt);
    }

    public boolean isEast(Point pt, ZirkelCanvas zc) {
        Rectangle sel=(Rectangle) zc.getCopyRectangle();
        if (sel==null) {
            return false;
        }
        boolean bool=(Math.abs(pt.x-sel.x-sel.width)<hotSize);
        bool=bool&&(pt.y>sel.y)&&(pt.y<(sel.y+sel.height));
        return bool;
    }

    public boolean isSouth(Point pt, ZirkelCanvas zc) {
        Rectangle sel=(Rectangle) zc.getCopyRectangle();
        if (sel==null) {
            return false;
        }
        boolean bool=(Math.abs(pt.y-sel.y-sel.height)<hotSize);
        bool=bool&&(pt.x>sel.x)&&(pt.x<(sel.x+sel.width));
        return bool;
    }

    public boolean isNorth(Point pt, ZirkelCanvas zc) {
        Rectangle sel=(Rectangle) zc.getCopyRectangle();
        if (sel==null) {
            return false;
        }
        boolean bool=(Math.abs(pt.y-sel.y)<hotSize);
        bool=bool&&(pt.x>sel.x)&&(pt.x<(sel.x+sel.width));
        return bool;
    }

    public boolean isWest(Point pt, ZirkelCanvas zc) {
        Rectangle sel=(Rectangle) zc.getCopyRectangle();
        if (sel==null) {
            return false;
        }
        boolean bool=(Math.abs(pt.x-sel.x)<hotSize);
        bool=bool&&(pt.y>sel.y)&&(pt.y<(sel.y+sel.height));
        return bool;
    }

    public boolean isSouthEast(Point pt, ZirkelCanvas zc) {
        Rectangle sel=(Rectangle) zc.getCopyRectangle();
        if (sel==null) {
            return false;
        }
        boolean bool=(Math.abs(pt.x-sel.x-sel.width)<hotSize);
        bool=bool&&(Math.abs(pt.y-sel.y-sel.height)<hotSize);
        return bool;
    }

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        
        if (isInside(e.getPoint(), zc)) {
            DragLoc=MouseInfo.getPointerInfo().getLocation();
        } else if (isSouthEast(e.getPoint(), zc)) {
            GrowSouthEast=MouseInfo.getPointerInfo().getLocation();
        } else if (isEast(e.getPoint(), zc)) {
            GrowEast=MouseInfo.getPointerInfo().getLocation();
        } else if (isSouth(e.getPoint(), zc)) {
            GrowSouth=MouseInfo.getPointerInfo().getLocation();
        } else if (isNorth(e.getPoint(), zc)) {
            GrowNorth=MouseInfo.getPointerInfo().getLocation();
        } else if (isWest(e.getPoint(), zc)) {
            GrowWest=MouseInfo.getPointerInfo().getLocation();
        } else {
            zc.startCopyRectangle(e.getX(), e.getY());
        }
        zc.keepCopyRectangleInside();
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        if (isInside(e.getPoint(), zc)) {
            zc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else if (isSouthEast(e.getPoint(), zc)) {
            zc.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        } else if (isEast(e.getPoint(), zc)) {
            zc.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        } else if (isSouth(e.getPoint(), zc)) {
            zc.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        } else if (isNorth(e.getPoint(), zc)) {
            zc.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        } else if (isWest(e.getPoint(), zc)) {
            zc.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        } else {
            zc.setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void mouseDragged(final MouseEvent e, final ZirkelCanvas zc) {
        Point pt=MouseInfo.getPointerInfo().getLocation();
        if (DragLoc!=null) {
            int dx=pt.x-DragLoc.x;
            int dy=pt.y-DragLoc.y;
            zc.translateCopyRectangle(dx, dy);
            DragLoc=pt;
        } else if (GrowSouthEast!=null) {
            int dx=pt.x-GrowSouthEast.x;
            int dy=pt.y-GrowSouthEast.y;
            zc.growCopyRectangle(dx, dy);
            GrowSouthEast=pt;
        } else if (GrowEast!=null) {
            int dx=pt.x-GrowEast.x;
            zc.growCopyRectangle(dx, 0);
            GrowEast=pt;
        } else if (GrowSouth!=null) {
            int dy=pt.y-GrowSouth.y;
            zc.growCopyRectangle(0, dy);
            GrowSouth=pt;
        } else if (GrowNorth!=null) {
            int dy=pt.y-GrowNorth.y;
            zc.getCopyRectangle().translate(0, dy);
            zc.growCopyRectangle(0, -dy);
            GrowNorth=pt;
        } else if (GrowWest!=null) {
            int dx=pt.x-GrowWest.x;
            zc.getCopyRectangle().translate(dx, 0);
            zc.growCopyRectangle(-dx, 0);
            GrowWest=pt;
        } else {
            zc.actualiseCopyRectangle(e);
        }
        zc.keepCopyRectangleInside();
        showStatus(zc);
    }

    @Override
    public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc) {
        DragLoc=null;
        GrowEast=null;
        GrowSouth=null;
        GrowNorth=null;
        GrowWest=null;
        GrowSouthEast=null;
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        Rectangle r=zc.getCopyRectangle();
        zc.showStatus("("+r.width+"x"+r.height+")   x="+r.x+" y="+r.y);
//        zc.showStatus("<html>x: <b>"+r.x+"</b></html>");
    }

    @Override
    public boolean useSmartBoard() {
        return false;
    }

    @Override
    public void invalidate(ZirkelCanvas zc) {
        zc.hideCopyRectangle();
        zc.repaint();
    }
}
