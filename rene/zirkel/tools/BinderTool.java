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
import java.awt.event.MouseEvent;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.InsideObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;

public class BinderTool extends ObjectConstructor implements Selector {

    ObjectConstructor OC;
    PointObject P;

    public BinderTool(final ZirkelCanvas zc, final PointObject p,
            final ObjectConstructor oc) {
        P=p;
        OC=oc;
        P.setSelected(true);
        zc.repaint();
    }
    boolean Control, Shift;

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        Control=e.isControlDown();
        Shift=e.isShiftDown();
        final ConstructionObject o=zc.selectWithSelector(e.getX(), e.getY(),this);
        setConstructionObject(o, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject obj, ZirkelCanvas zc) {
        if (obj==null) {
            return;
        }
        if (Control&&!(obj instanceof InsideObject)) {
            return;
        }
        if (zc.getConstruction().dependsOn(obj, P)) {
            return;
        }

        P.setBound(obj.getName());
        P.setUseAlpha(!Shift);
        if(Control) P.setInside(true);

        zc.getConstruction().updateCircleDep();
        zc.repaint();
        reset(zc);
        eric.bar.JPropertiesBar.EditObject(P, false, false);
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        Control=e.isControlDown();
        zc.indicateWithSelector(e.getX(), e.getY(), this);
    }

    public boolean isAdmissible(final ZirkelCanvas zc,
            final ConstructionObject o) {
        if ((o instanceof InsideObject||o instanceof PointonObject)
                &&!zc.getConstruction().dependsOn(o, P)) {
            return true;
        }
        return false;
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.name("message.bindpoint"));
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
