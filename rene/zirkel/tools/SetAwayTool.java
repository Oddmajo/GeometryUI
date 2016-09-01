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

import eric.bar.JPropertiesBar;
import java.awt.event.MouseEvent;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.IntersectionObject;
import rene.zirkel.objects.PointObject;

/**
 * A tool to set the the point an intersection point should stay away from or
 * should come close to.
 */

public class SetAwayTool extends ObjectConstructor implements Selector{
	ObjectConstructor OC;
	IntersectionObject P;
	boolean Away;

	public SetAwayTool(final ZirkelCanvas zc, final IntersectionObject p,
			final boolean away, final ObjectConstructor oc) {
		P = p;
		OC = oc;
		P.setStrongSelected(true);
                if (P.getAway()!=null){
                    P.getAway().setSelected(true);
                }
		Away = away;
		zc.repaint();
	}

	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		final ConstructionObject o = zc.selectPoint(e.getX(), e.getY());
		if (o == null)
			return;
		if (zc.getConstruction().dependsOn(o, P)) {
			zc.warning(ConstructionObject.text1(Global.name("error.depends"), P
					.getText()));
			return;
		}
                zc.clearSelected();
                P.setStrongSelected(true);
                if (P.getAway()==o){
                    P.setAway("");
                }else{
                    P.setAway(o.getName(), Away);
                    P.setUseAlpha(e.isShiftDown());
                    o.setSelected(true);
                }



//                if (P.getAway()==null) {
//                    P.setAway(o.getName(), Away);
//                    P.setUseAlpha(e.isShiftDown());
//                    P.getAway().setSelected(true);
//                }else{
//                    P.setAway("");
//                }


		zc.validate();
//		reset(zc);
                JPropertiesBar.RefreshBar();
	}

        @Override
	public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
			final boolean simple) {
		zc.indicateWithSelector(e.getX(), e.getY(), this);
	}



	@Override
	public void showStatus(final ZirkelCanvas zc) {
		if (Away)
			zc.showStatus(Global.name("message.setaway.away"));
		else
			zc.showStatus(Global.name("message.setaway.close"));
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

    public boolean isAdmissible(ZirkelCanvas zc, ConstructionObject o) {
        return (o instanceof PointObject);
    }
}
