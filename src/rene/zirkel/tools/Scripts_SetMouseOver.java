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
import eric.GUI.ZDialog.ZTextFieldAndLabel;
import eric.JSprogram.ScriptItem;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;

public class Scripts_SetMouseOver extends ObjectConstructor {
    ScriptItem ITEM;

    public Scripts_SetMouseOver(ScriptItem item) {
        super();
        ITEM=item;
    }
    

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        zc.y(e.getY());
        final ConstructionObject o=zc.selectPoint(e.getX(), e.getY());
        if (o==null) {
            return;
        }
        if (o.selected()) {
            o.setSelected(false);
            ITEM.removeMouseOverTarget(o);
        } else {
            o.setSelected(true);
            ITEM.addMouseOverTarget(o);
        }
        ITEM.refreshMouseOverInputField();
        zc.repaint();
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        zc.indicatePointObjects(e.getX(), e.getY());
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
	zc.showStatus(""); //otherwise, the ZTextFiledAndLabel are invisible on ZDialog windows when user ask them twice
//        zc.showStatus(Global.name("message.savejob.second"));
    }

    @Override
    public boolean useSmartBoard() {
        return false;
    }
}
