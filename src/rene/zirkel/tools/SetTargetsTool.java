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
import eric.macros.CreateMacroPanel;
import java.awt.event.MouseEvent;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;

public class SetTargetsTool extends ObjectConstructor {

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        zc.y(e.getY());
        final ConstructionObject o=zc.selectConstructableObject(e.getX(), e.getY());
        if (o==null||!o.isFlag()) {
            return;
        }
        if (o.isTarget()) {
            o.setTarget(false);
            o.setSelected(false);
            zc.getConstruction().removeTarget(o);
        }else{
            o.setTarget(true);
            o.setSelected(true);
            zc.getConstruction().addTarget(o);
        }
        zc.repaint();
        CreateMacroPanel.setTargetsComments();
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        zc.indicateConstructableObjects(e.getX(), e.getY());
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        zc.clearSelected();
        zc.getConstruction().clearTargets();
        zc.getConstruction().determineConstructables();
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.name("message.targets",
                "Macro Targets: Select the Targets!"));
    }

    @Override
    public boolean useSmartBoard() {
        return false;
    }
    
    @Override
    public void setConstructionObject(ConstructionObject o, ZirkelCanvas zc) {
        if (o==null||!o.isFlag()) {
            return;
        }
        if (o.isTarget()) {
            o.setTarget(false);
            o.setSelected(false);
            zc.getConstruction().removeTarget(o);
        }else{
            o.setTarget(true);
            o.setSelected(true);
            zc.getConstruction().addTarget(o);
        }
        zc.repaint();
        CreateMacroPanel.setTargetsComments();
    }
}
