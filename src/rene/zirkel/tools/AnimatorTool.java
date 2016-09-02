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

import eric.animations.AnimationPanel;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.CircleObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.SegmentObject;

/**
 * Animator is a class to animate a point along a sequence of segments and/or
 * circles. The animation may either go back and forth or always in the same
 * direction. Shift-Click schaltet w‰hrend der Animation um.
 * 
 * @author Rene
 * 
 */
public class AnimatorTool extends ObjectConstructor implements Runnable,
        Selector {

    PointObject P;
    ZirkelCanvas ZC;
    boolean Running=false, Interactive=true, Complete=false;
    boolean Negative=false;
    boolean Original=false;
    double Delay=50;

    public AnimatorTool() {
        P=null;
    }

    public AnimatorTool(final PointObject p, final Vector v,
            final ZirkelCanvas zc, final boolean negative,
            final boolean original, final String delay) {
        P=p;
        if (!P.moveable()) {
            return;
        }
        final Enumeration e=v.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=zc.getConstruction().find(
                    (String) e.nextElement());
            if (!(o instanceof SegmentObject
                    ||o instanceof PrimitiveCircleObject||o instanceof PointObject)) {
                return;
            }
        }
        Stopped=false;
        ZC=zc;
        Complete=true;
        Negative=negative;
        Original=original;
        try {
            Delay=50;
            Delay=new Double(delay).doubleValue();
        } catch (final Exception ex) {
        }

        new Thread(this).start();
    }

    public void setInteractive(final boolean flag) {
        Interactive=flag;
    }

    public boolean isAdmissible(final ZirkelCanvas zc,
            final ConstructionObject o) {
        if ((o instanceof CircleObject)&&((CircleObject) o).getP2()==P) {
            return true;
        }
        if (zc.depends(o, P)) {
            return false;
        }
        return true;
    }

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        ConstructionObject o=zc.selectAnimationObject(e.getX(), e.getY());
        if (o!=null) {
            if (zc.isAnimated(o)) {
                o.setSelected(false);
                zc.removeAnimation(o);
            } else {
                o.setSelected(true);
                zc.addAnimation(o);
            }
        }
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        zc.indicateAnimationObjects(e.getX(), e.getY());
    }

    @Override
    public synchronized void reset(final ZirkelCanvas zc) {
        zc.selectAnimationPoints();
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        if (P==null) {
            zc.showStatus(Global.name("message.animator.point"));
        } else if (!Complete) {
            zc.showStatus(Global.name("message.animator.segment"));
        } else {
            zc.showStatus(Global.name("message.animator.running"));
        }
    }

    public void save(final XmlWriter xml) {
        ZC.getAnimations().printArgs(xml);
    }
    boolean Stopped=false;

    public void run() {

    }

    @Override
    public synchronized void invalidate(final ZirkelCanvas zc) {
        Stopped=true;
    }

    @Override
    public boolean useSmartBoard() {
        return false;
    }

}
