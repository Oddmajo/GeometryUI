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
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;

public class SetRangeTool extends ObjectConstructor {
	ObjectConstructor OC;
	PrimitiveCircleObject C;
	PointObject P1, P2;

	public SetRangeTool(final ZirkelCanvas zc, final PrimitiveCircleObject c,
			final ObjectConstructor oc) {
		C = c;
		OC = oc;
		C.setSelected(true);
		zc.repaint();
	}

	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		if (P1 == null) {
			P1 = zc.selectPoint(e.getX(), e.getY());
			if (P1 != null) {
				P1.setSelected(true);
				showStatus(zc);
				zc.repaint();
			}
		} else {
			P2 = zc.selectPoint(e.getX(), e.getY());
			if (P2 == null)
				return;
			C.setRange(P1, P2);
			reset(zc);
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
			final boolean simple) {
		zc.indicatePointObjects(e.getX(), e.getY());
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		if (P1 == null)
			zc.showStatus(Global.name("message.range.first"));
		else
			zc.showStatus(Global.name("message.range.second"));
	}

	@Override
	public void reset(final ZirkelCanvas zc) {
		super.reset(zc);
		zc.setTool(OC);
		zc.validate();
		zc.repaint();
	}
}
