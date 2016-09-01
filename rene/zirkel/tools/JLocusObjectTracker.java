/* 
 
Copyright 2006 Eric Hakenholz

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

import rene.zirkel.objects.*;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.graphics.TrackPainter;
import rene.zirkel.tools.ObjectTracker;

/**
 * 
 * @author erichake
 */
public class JLocusObjectTracker extends ObjectTracker implements TrackPainter,
Runnable, Selector {
	public JLocusObjectTracker() {
		// System.out.println("JLocusObjectTracker");
	}

	public JLocusObjectTracker(final ConstructionObject p,
			final PointObject pm, final ConstructionObject o,
			final ZirkelCanvas zc, final boolean animate, final boolean paint,
			final ConstructionObject po[]) {
		super(p, pm, o, zc, animate, paint, po);
	}

	/**
	 * it simply create the locus object, without any animation
	 */
	@Override
	public void run() {
		animate(ZC);
	}

	/**
	 * No animation : it simply create the locus object
	 * 
	 * @param zc
	 */
	@Override
	public void animate(final ZirkelCanvas zc) {
        
		// The DontPaint switch will be reset off at the end
            zc.setDontPaint(true);
        TrackObject t =null;
        if (P instanceof PointObject) {
            // Il s'agit d'un lieu de point (on utilise le nouvel objet)
            t = new JLocusTrackObject(zc.getConstruction(), P,
				PO, PN, O, PM);
        }else{
             // Il s'agit d'une enveloppe de droite (on utilise l'ancien objet)
            t = new TrackObject(zc.getConstruction(), P,
				PO, PN, O, PM);
        }


//        OldTrackObject t=new OldTrackObject(zc.getConstruction(),
//			P,PO,PN,O,PM);
		t.setDefaults();
		zc.addObject(t);
		reset(zc);
		zc.validate();
                zc.setDontPaint(false);
	}
}
