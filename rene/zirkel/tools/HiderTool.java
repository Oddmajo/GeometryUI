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
import java.awt.event.MouseEvent;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;

public class HiderTool extends ObjectConstructor {

    boolean isShiftDown=false;

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        isShiftDown=e.isShiftDown();
        final ConstructionObject o=zc.selectObject(e.getX(), e.getY());
        setConstructionObject(o, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject obj, ZirkelCanvas zc) {
        if (obj==null) {
            return;
        }
        if (isShiftDown) {
            obj.setSuperHidden(true);
        } else {
            obj.setHidden(!obj.isHidden());
        }
        zc.repaint();
        zc.reloadCD();
	zc.update_distant(obj, 3);
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        zc.indicateObjects(e.getX(), e.getY());
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.name("message.hide", "Hide: Select an object!"));
    }
}
