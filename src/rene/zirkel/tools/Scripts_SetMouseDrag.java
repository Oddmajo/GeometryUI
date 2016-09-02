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

// file: SetParameter.java
import eric.JSprogram.ScriptItem;
import java.awt.event.MouseEvent;
import rene.gui.Global;

import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.PointObject;

public class Scripts_SetMouseDrag extends ObjectConstructor  implements Selector {
    ScriptItem ITEM;

    public Scripts_SetMouseDrag(ScriptItem item) {
        super();
        ITEM=item;
    }

    public void addFromControl(ExpressionObject o,ZirkelCanvas zc){
        if (o.selected()) {
            o.setSelected(false);
            ITEM.removeMouseDragTarget(o);
        } else {
            o.setSelected(true);
            ITEM.addMouseDragTarget(o);
        }
        ITEM.refreshMouseDragInputField();
        zc.repaint();
    }


    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        zc.y(e.getY());
        final ConstructionObject o = zc.selectWithSelector(e.getX(), e.getY(),
				this);
        if (o==null) {
            return;
        }
        if (o.selected()) {
            o.setSelected(false);
            ITEM.removeMouseDragTarget(o);
        } else {
            o.setSelected(true);
            ITEM.addMouseDragTarget(o);
	    ITEM.removeMouseUpTarget(o);
        }
        ITEM.refreshMouseDragInputField();
        zc.repaint();
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
//        zc.indicatePointObjects(e.getX(), e.getY());
        zc.indicateWithSelector(e.getX(), e.getY(), this);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        zc.clearSelected();
        showStatus(zc);
//        zc.clearJob();
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.Loc("JSmenu.dragaction.message"));
    }

    @Override
    public boolean useSmartBoard() {
        return false;
    }

    public boolean isAdmissible(final ZirkelCanvas zc,
			final ConstructionObject o) {
		if (o instanceof PointObject){
                    return true;
                }
                return false;
	}
}
