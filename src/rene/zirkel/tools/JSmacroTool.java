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
import eric.JSelectPopup;
import eric.JSprogram.ScriptThread;
import java.awt.event.MouseEvent;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;

import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.PointonObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.SegmentObject;

public class JSmacroTool extends ObjectConstructor implements Selector {

    ObjectConstructor OC;
    String MSG;
    String TYPE;
    ScriptThread TH;
    ConstructionObject O=null;
    boolean Point = false;

    public JSmacroTool(ZirkelCanvas zc, ScriptThread th, String msg, String type, ObjectConstructor oc) {
        OC=oc;
        MSG=msg;
        TYPE=type;
        TH=th;
        zc.repaint();
    }

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        if (TYPE.equals("Point")) {
	    Point = true;
            O=zc.selectCreatePoint(e.getX(), e.getY(), e.isAltDown());
            //TH.setJSO(O);
        } else if (TYPE.equals("Segment")) {
            O=zc.selectSegment(e.getX(), e.getY());
            //TH.setJSO(O);
        } else if (TYPE.equals("Line")) {
            O=zc.selectLine(e.getX(), e.getY());
            //TH.setJSO(O);
        } else if (TYPE.equals("Circle")) {
            O=zc.selectCircle(e.getX(), e.getY());
            //TH.setJSO(O);
        } else if (TYPE.equals("Expression")) {
            O=zc.selectObject(e.getX(), e.getY());
            //TH.setJSO(O);
        } else if (TYPE.equals("Polygon")) {
            O=zc.selectObject(e.getX(), e.getY());
            //TH.setJSO(O);
        } else if(TYPE.equals("Function")){
	    O=zc.selectObject(e.getX(), e.getY(), true);
	}
	setConstructionObject(O, zc);
//        if (TH.getJSO()!=null) {
//            O.setSelected(true);
//        }
    }

    @Override
    public void setConstructionObject(ConstructionObject O, ZirkelCanvas zc){
	if ((JSelectPopup.isCallerObject())&&(O instanceof PointonObject)&&Point) {
            int x=JSelectPopup.getMouseX();
            int y=JSelectPopup.getMouseY();
            PointObject o=new PointObject(zc.getConstruction(), zc.x(x), zc.y(y), O);
            o.setUseAlpha(true);
            zc.addObject(o);
            zc.validate();
            o.setDefaults();
            zc.repaint();
            o.edit(zc, false, false);
            O=o;
        }
	TH.setJSO(O);
	if (TH.getJSO()!=null) {
            O.setSelected(true);
        }
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        if (TYPE.equals("Point")) {
            zc.indicateCreatePoint(e.getX(), e.getY(), true);
        } else {
            zc.indicateWithSelector(e.getX(), e.getY(), this);
        }
    }

    @Override
    public boolean isAdmissible(final ZirkelCanvas zc,
            final ConstructionObject o) {
        if ((o instanceof PointObject)&&(TYPE.equals("Point"))) {
            return true;
        } else if ((o instanceof PrimitiveLineObject)&&(TYPE.equals("Line"))) {
            return true;
        } else if ((o instanceof PrimitiveCircleObject)&&(TYPE.equals("Circle"))) {
            return true;
        } else if ((o instanceof SegmentObject)&&(TYPE.equals("Segment"))) {
            return true;
        } else if ((o instanceof ExpressionObject)&&(TYPE.equals("Expression"))) {
            return true;
        } else if ((o instanceof AreaObject)&&(TYPE.equals("Polygon"))) {
            return true;
        } else if ((o instanceof FunctionObject)&&(TYPE.equals("Function"))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(MSG);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        zc.setTool(OC);
        zc.validate();
        zc.repaint();

    }

    @Override
    public synchronized void invalidate(final ZirkelCanvas zc) {
        if (O==null) {
//            TH.invalidII();
	    TH.killme();
        }
    }
    public void invalidate_and_saveoc(final ZirkelCanvas zc, final ObjectConstructor oc){
	OC = oc;
	invalidate(zc);
    }

    @Override
    public boolean useSmartBoard() {
        return false;
    }

    public ObjectConstructor getPreviousTool(){
	return OC;
    }
}
