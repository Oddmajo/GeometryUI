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

// file: Binder.java
import eric.bar.JPropertiesBar;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.SwingUtilities;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.structures.MagnetObj;

public class MagnetTool extends ObjectConstructor implements Selector {

    ObjectConstructor OC;
    PointObject P;

    public MagnetTool(final ZirkelCanvas zc, final PointObject p,
            final ObjectConstructor oc) {
        P=p;
        OC=oc;
    }

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        final ConstructionObject o=zc.selectWithSelector(e.getX(), e.getY(),this);
        setConstructionObject(o, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject o, ZirkelCanvas zc) {
        if (o==null) {
            reset(zc);
            return;
        }
        if (o==P) {
            return;
        }
        if (o.selected()) {
            P.removeMagnetObject(o.getName());
            o.setSelected(false);
            JPropertiesBar.RefreshBar();
        } else {
            P.addMagnetObject(o.getName());
            P.selectMagnetObjects(true);
            JPropertiesBar.RefreshBar();
        }
        zc.repaint();
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        zc.indicateWithSelector(e.getX(), e.getY(), this);
    }

    public boolean isAdmissible(final ZirkelCanvas zc,
            final ConstructionObject o) {
        return true;
        // if ((o instanceof InsideObject || o instanceof PointonObject) &&
        // !zc.getConstruction().dependsOn(o,P)) return true;
        // return false;
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.Loc("props.magnetmessage"));
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        zc.setTool(OC);
        zc.validate();
        JPropertiesBar.RefreshBar();
        zc.repaint();
    }

    @Override
    public boolean useSmartBoard() {
        return false;
    }
}
