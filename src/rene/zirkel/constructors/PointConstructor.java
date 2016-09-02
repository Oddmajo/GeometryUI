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
package rene.zirkel.constructors;

// file: PointConstructor.java
import eric.JSelectPopup;
import eric.bar.JPropertiesBar;
import java.awt.event.MouseEvent;

import rene.gui.Global;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;

public class PointConstructor extends ObjectConstructor {

//    boolean Fix;
    PointObject P;
    boolean ShowsValue, ShowsName;
    boolean ShiftDown=false;

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        ShiftDown=e.isShiftDown();
        final PointObject o=zc.selectCreatePoint(e.getX(), e.getY(), false,
                true, e.isAltDown());
        setConstructionObject(o, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject obj, ZirkelCanvas zc) {
        Dragging=false;
        // Il y a eu ambiguité et la méthode est forcément appelée par le
        // popupmenu de sélection :
        if ((JSelectPopup.isCallerObject())&&(obj instanceof PointonObject)) {
            int x=JSelectPopup.getMouseX();
            int y=JSelectPopup.getMouseY();
            PointObject o=new PointObject(zc.getConstruction(), zc.x(x), zc.y(y), obj);
            o.setUseAlpha(true);
            zc.addObject(o);
            zc.validate();
            o.setDefaults();
            zc.repaint();
            o.edit(zc, false, false);
        } else if (obj instanceof PointObject) {
            PointObject o=(PointObject) obj;
            if (o.isPointOn()) // create a point on an object
            {
                if (ShiftDown&&zc.isNewPoint()) {
                    o.setUseAlpha(true);
                }
            } else if (o.moveable()&&zc.isNewPoint()&&!zc.getAxis_show()) {
                P=o;
                ShowsValue=P.showValue();
                ShowsName=P.showName();
                Dragging=true;
                zc.repaint();
            } else if (o.moveable()&&zc.isNewPoint()&&zc.getAxis_show()&&ShiftDown) {
                P=o;
                try {
                    P.setFixed(""+P.round(P.getX(), ZirkelCanvas.LengthsFactor),
                            ""+P.round(P.getY(), ZirkelCanvas.LengthsFactor));

                    P.edit(zc, true, true);
                    P.validate();
                } catch (final Exception ex) {
                }
            } else if (ShiftDown&&!zc.isNewPoint()) {
                final PointObject p=new PointObject(zc.getConstruction(), o.getX(), o.getY());
                zc.addObject(p);
                p.setFixed(true);
            }
        }

    }

    @Override
    public void mouseDragged(final MouseEvent e, final ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        }
        if (Global.getParameter("options.movename", false)) {
            P.setShowValue(true);
            P.setShowName(true);
        }
        P.move(zc.x(e.getX()), zc.y(e.getY()));
        zc.repaint();
    }

    @Override
    public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        }
        Dragging=false;
        P.setShowValue(ShowsValue);
        P.setShowName(ShowsName);
        P.updateText();
        zc.repaint();
        if (ShiftDown) {
            try {
                P.setFixed(""+P.round(P.getX(), ZirkelCanvas.LengthsFactor),
                        ""+P.round(P.getY(), ZirkelCanvas.LengthsFactor));
                JPropertiesBar.EditObject(P);
                P.validate();
            } catch (final Exception ex) {
            }
        }
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.name("message.point", "Point: Set a point!"));
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {
        if (!testTree(tree, "Point")) {
            return false;
        }
        final XmlTag tag=tree.getTag();
        if (!tag.hasParam("x")||!tag.hasParam("y")) {
            throw new ConstructionException("Point coordinates missing!");
        }
        double x=0, y=0;
        try {
            if (tag.hasParam("actx")) {
                x=new Double(tag.getValue("actx")).doubleValue();

                // System.out.println(x);
            }
            if (tag.hasParam("acty")) {
                y=new Double(tag.getValue("acty")).doubleValue();

            }

        } catch (final Exception e) {
        }
        final PointObject p=new PointObject(c, x, y);
        try {
            x=new Expression(tag.getValue("x"), c, p).getValue();
            y=new Expression(tag.getValue("y"), c, p).getValue();
            p.move(x, y);
        } catch (final Exception e) {
        }
        setType(tag, p);
        setName(tag, p);
        set(tree, p);
        c.add(p);
        setConditionals(tree, c, p);

        if (tag.hasParam("fixed")) {
            p.setFixed(tag.getValue("x"), tag.getValue("y"));
        }
        if (tag.hasParam("increment")) {
            try {
                p.setIncrement(new Double(tag.getValue("increment")).doubleValue());
            } catch (final Exception e) {
            }
        }
        if (tag.hasParam("magnetobjs")) {
            // must manage the magnet list after the whole construction is
            // loaded (see zc.load)
            c.magnet.add(p);
            c.magnet.add(tag.getValue("magnetobjs"));
            p.setMagnetRayExp(tag.getValue("magnetd"));
        }
        return true;
    }

    static public void setType(final XmlTag tag, final PointObject p) {
        if (tag.hasParam("shape")) {
            final String s=tag.getValue("shape");
            if (s.equals("square")) {
                p.setType(0);
            }
            if (s.equals("diamond")) {
                p.setType(1);
            }
            if (s.equals("circle")) {
                p.setType(2);
            }
            if (s.equals("dot")) {
                p.setType(3);
            }
            if (s.equals("cross")) {
                p.setType(4);
            }
            if (s.equals("dcross")) {
                p.setType(5);
            }
        }
    }

    @Override
    public String getTag() {
        return "Point";
    }

    @Override
    public void construct(final Construction c, final String name,
            final String params[], final int nparams)
            throws ConstructionException {

        if (nparams==0) {
            final PointObject p=new PointObject(c, c.getX()+(Math.random()-0.5)*c.getW(), c.getY()+(Math.random()-0.5)*c.getW());
            if (!name.equals("")) {
                p.setNameCheck(name);
            }
            c.add(p);
            p.setDefaults();
            return;
        }
        if (nparams==1) {
            final ConstructionObject o=c.find(params[0]);
            if (o==null) {
                throw new ConstructionException(Global.name("exception.notfound")+" "+params[0]);
            }
            if (!(o instanceof PrimitiveLineObject)&&!(o instanceof PrimitiveCircleObject)) {
                throw new ConstructionException(Global.name("exception.type")+" "+params[0]);
            }
            final PointObject p=new PointObject(c, c.getX()+(Math.random()-0.5)*c.getW(), c.getY()+(Math.random()-0.5)*c.getW(), o);
            if (!name.equals("")) {
                p.setNameCheck(name);
            }
            c.add(p);
            p.setDefaults();
            return;
        }
        if (nparams!=2) {
            throw new ConstructionException(Global.name("exception.nparams"));
        }
        final Expression e1=new Expression(params[0], c, null);
        final Expression e2=new Expression(params[1], c, null);
        if (!e1.isValid()||!e2.isValid()) {
            throw new ConstructionException(Global.name("exception.expression"));
        }
        final PointObject p=new PointObject(c, 0, 0);
        try {
            final double x=new Double(params[0]).doubleValue();
            final double y=new Double(params[1]).doubleValue();
            p.move(x, y);
        } catch (final Exception e) {
            p.setFixed(params[0], params[1]);
        }
        c.add(p);
        p.validate();
        p.setDefaults();
        if (!name.equals("")) {
            p.setNameCheck(name);
        }
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        zc.setPrompt(Global.name("prompt.point"));
    }
}
