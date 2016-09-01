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
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.QuadricObject;
import rene.zirkel.objects.UserFunctionObject;

/**
 * Class to choose parameters for macro definition.
 * 
 * @author Rene
 */
public class SetParameterTool extends ObjectConstructor implements Selector {

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        zc.y(e.getY());
        final ConstructionObject o=zc.selectWithSelector(e.getX(), e.getY(),
                this);
        if (o==null) {
            return;
        }

        if (o.isMainParameter()) {
            o.setMainParameter(false);
            o.setSelected(false);
            zc.getConstruction().removeParameter(o);
            zc.repaint();
        } else {
            o.setMainParameter(true);
            o.setSelected(true);
            zc.getConstruction().addParameter(o);
            zc.repaint();
            if (e.isShiftDown()) {
                o.setSpecialParameter(true);
            }
        }
        CreateMacroPanel.setParametersComments();
    }

    public boolean isAdmissible(final ZirkelCanvas zc,
            final ConstructionObject o) {
        return (o instanceof PointObject
                ||o instanceof PrimitiveLineObject
                ||o instanceof PrimitiveCircleObject
                ||o instanceof ExpressionObject
                ||o instanceof AngleObject
                ||o instanceof AreaObject
                ||o instanceof FunctionObject
                ||o instanceof UserFunctionObject
                ||o instanceof QuadricObject);
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        zc.indicateWithSelector(e.getX(), e.getY(), this);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        zc.clearSelected();
        zc.getConstruction().clearParameters();
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.name("message.parameters",
                "Macro Parameters: Select the Parameters!"));
    }
}
