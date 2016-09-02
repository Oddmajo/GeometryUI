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

// file: SetRange.java

import java.awt.event.MouseEvent;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.PointObject;

public class SetFixedAngle extends ObjectConstructor implements Selector {
	ObjectConstructor OC;
	FixedAngleObject A;
	PointObject P1, P2, P3;

	public SetFixedAngle(final ZirkelCanvas zc, final FixedAngleObject a,
			final ObjectConstructor oc) {
		A = a;
		OC = oc;
		a.setSelected(true);
		zc.repaint();
	}

	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		if (P1 == null) {
			final ConstructionObject o = zc.selectWithSelector(e.getX(), e
					.getY(), this);
			if (o == null)
				return;
			if (o instanceof PointObject) {
				P1 = (PointObject) o;
				P1.setSelected(true);
				showStatus(zc);
				zc.repaint();
			} else if (o instanceof AngleObject
					|| o instanceof FixedAngleObject
					|| o instanceof ExpressionObject) {
				A.setFixed(o.getName());
				A.setDragable(false);
				A.updateText();
				reset(zc);
			}
		} else if (P2 == null) {
			P2 = zc.selectPoint(e.getX(), e.getY());
			if (P2 != null) {
				P2.setSelected(true);
				showStatus(zc);
				zc.repaint();
			}
		} else {
			P3 = zc.selectPoint(e.getX(), e.getY());
			if (P3 == null)
				return;
			A.setFixed("a(" + P1.getName() + "," + P2.getName() + ","
					+ P3.getName() + ")");
			reset(zc);
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
			final boolean simple) {
		zc.indicateWithSelector(e.getX(), e.getY(), this);
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		if (P1 == null)
			zc.showStatus(Global.name("message.setfixedangle.first"));
		else if (P2 == null)
			zc.showStatus(Global.name("message.setfixedangle.second"));
		else
			zc.showStatus(Global.name("message.setfixedangle.third"));
	}

	@Override
	public void reset(final ZirkelCanvas zc) {
		super.reset(zc);
		zc.setTool(OC);
		zc.validate();
		zc.repaint();
	}

	@Override
	public boolean useSmartBoard() {
		return P2 != null;
	}

	public boolean isAdmissible(final ZirkelCanvas zc,
			final ConstructionObject o) {
		if ((o instanceof PointObject || o instanceof AngleObject
				|| o instanceof FixedAngleObject || o instanceof ExpressionObject)
				&& !zc.getConstruction().dependsOn(o, A))
			return true;
		return false;
	}
}
