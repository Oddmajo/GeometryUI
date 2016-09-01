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

import java.awt.event.MouseEvent;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.FunctionObject;

public class SetCurveCenterTool extends ObjectConstructor {
	ObjectConstructor OC;
	FunctionObject P;

	public SetCurveCenterTool(final ZirkelCanvas zc, final FunctionObject p,
			final ObjectConstructor oc) {
		P = p;
		OC = oc;
		P.setSelected(true);
		zc.repaint();
	}

	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		final ConstructionObject o = zc.selectPoint(e.getX(), e.getY());
		if (o == null)
			return;
		P.setCenter(o.getName());
		reset(zc);
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		zc.showStatus(Global.name("message.setcenter"));
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
		return false;
	}
}
