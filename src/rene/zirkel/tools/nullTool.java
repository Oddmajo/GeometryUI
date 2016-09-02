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

import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;


public class nullTool extends ObjectConstructor implements Selector {

    ObjectConstructor OC;
    String MSG;
    String TYPE;

    public nullTool(final ZirkelCanvas zc, final ObjectConstructor oc) {
        OC=oc;
        zc.repaint();
    }

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
    }

    public boolean isAdmissible(final ZirkelCanvas zc,
            final ConstructionObject o) {
        return false;
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
//        zc.showStatus(MSG);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        zc.setTool(OC);
        zc.validate();
        zc.repaint();
    }
}
