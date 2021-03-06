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

// file: Hider.java
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.MoveableObject;
import rene.zirkel.objects.TextObject;
import rene.zirkel.objects.UserFunctionObject;

/**
 * @author Rene Werkzeug zum Zoomen des Fensters und zum Verschieben.
 *         Verschieben funktioniert im Zentrum des Fensters.
 */
public class ZoomerTool extends ObjectConstructor {

	boolean Dragging = false, Zoom = false;
	double X, Y, W, X0, Y0;
	ObjectConstructor OC;

	public static void initNonDraggableObjects(final Construction c) {
		final Enumeration en = c.elements();
		while (en.hasMoreElements()) {
			final ConstructionObject o = (ConstructionObject) en.nextElement();
			if ((o instanceof TextObject) || (o instanceof ExpressionObject)
					|| (o instanceof UserFunctionObject)) {
				final MoveableObject mo = (MoveableObject) o;
				mo.startDrag(0, 0);
			}
		}
	}

	public static void shiftNonDraggableObjectsBy(final Construction c,
			final double dx, final double dy) {
		final Enumeration en = c.elements();
		while (en.hasMoreElements()) {
			final ConstructionObject o = (ConstructionObject) en.nextElement();
			if ((o instanceof TextObject) || (o instanceof ExpressionObject)
					|| (o instanceof UserFunctionObject)) {
				final MoveableObject mo = (MoveableObject) o;
				mo.dragTo(dx, dy);
			}
			

			// else if (!o.isKeepClose()) {
			//
			// System.out.println("dx="+dx);
			// o.setcOffset(o.xcOffset()+2*dx, o.ycOffset()+2*dy);
			//
			// // C.setXYW(C.getX()+dx*C.getW(), C.getY()+dy*C.getW(),
			// C.getW());
			//
			// };

		}
	}

	public static void zoomNonDraggableObjectsBy(final Construction c,
			final double f) {
		final Enumeration en = c.elements();

		while (en.hasMoreElements()) {
			final ConstructionObject o = (ConstructionObject) en.nextElement();
			if ((o instanceof TextObject) || (o instanceof ExpressionObject)
					|| (o instanceof UserFunctionObject)) {
				final MoveableObject mo = (MoveableObject) o;
				mo.move(c.getX() + (mo.getOldX() - c.getX()) * f, c.getY()
						+ (mo.getOldY() - c.getY()) * f);
			}
		}
	}

	public ZoomerTool() {
		super();
	}

	public ZoomerTool(final ObjectConstructor oc, final MouseEvent e,
			final ZirkelCanvas zc) {
		super();
		OC = oc;
		X0 = zc.x(e.getX());
		Y0 = zc.y(e.getY());
		final Construction c = zc.getConstruction();
		X = c.getX();
		Y = c.getY();
		W = c.getW();
		Zoom = false;
		zc.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		Dragging = true;
		initNonDraggableObjects(c);
	}

	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		X0 = zc.x(e.getX());
		Y0 = zc.y(e.getY());
		final Construction c = zc.getConstruction();
		X = c.getX();
		Y = c.getY();
		W = c.getW();
		Zoom = (Math.abs(X - X0) > W / 4 || Math.abs(Y - Y0) > W / 4);
		if (!Zoom) {
			zc.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
		Dragging = true;
		OC = null;
		initNonDraggableObjects(c);
	}

	@Override
	public void mouseDragged(final MouseEvent e, final ZirkelCanvas zc) {
		if (!Dragging) {
			return;
		}
		final Construction c = zc.getConstruction();
		c.setXYW(X, Y, W);
		zc.recompute();
		final double x = zc.x(e.getX()), y = zc.y(e.getY());
		if (Zoom) {
			final double f = Math.sqrt((X0 - X) * (X0 - X) + (Y0 - Y)
					* (Y0 - Y))
					/ Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y));
			c.setXYW(X, Y, f * W);
			zoomNonDraggableObjectsBy(c, f);
		} else {
			c.setXYW(X - (x - X0), Y - (y - Y0), W);
			shiftNonDraggableObjectsBy(c, X0 - x, Y0 - y);
		}
		zc.recompute();
		zc.validate();
		zc.repaint();
	}

	@Override
	public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc) {
		Dragging = Zoom = false;
		zc.setCursor(Cursor.getDefaultCursor());
		zc.recompute();
		zc.validate();
		zc.repaint();
		if (OC != null) {
			zc.setTool(OC);
		}
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		zc.showStatus(Global.name("message.zoom"));
	}

	@Override
	public void reset(final ZirkelCanvas zc) {
		zc.clearSelected();
		zc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		Zoom = Dragging = false;
	}

	@Override
	public void invalidate(final ZirkelCanvas zc) {
		zc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
			final boolean flag) {
		X0 = zc.x(e.getX());
		Y0 = zc.y(e.getY());
		final Construction c = zc.getConstruction();
		X = c.getX();
		Y = c.getY();
		W = c.getW();
		Zoom = (Math.abs(X - X0) > W / 4 || Math.abs(Y - Y0) > W / 4);
		if (!Zoom) {
			zc.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		} else {
			zc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public boolean useSmartBoard() {
		return false;
	}
}
