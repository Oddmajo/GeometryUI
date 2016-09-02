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
import java.awt.event.MouseEvent;

import rene.gui.Global;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.macro.Macro;
import rene.zirkel.objects.Circle3Object;
import rene.zirkel.objects.CircleObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.FixedCircleObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.SegmentObject;

public class CircleConstructor extends ObjectConstructor {

    PointObject P1=null, P2=null;
    boolean Fixed=false;
    boolean reallyFixed=false;
    int EventX, EventY;

    public CircleConstructor(final boolean fixed) {
        Fixed=fixed;
    }

    public CircleConstructor() {
        this(false);
    }
    FixedCircleObject C;
    ConstructionObject O;
    boolean ShowsValue, ShowsName;
    int Moved;

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }
        ConstructionObject obj=null;
        reallyFixed=(e.isShiftDown()||Fixed);
        EventX=e.getX();
        EventY=e.getY();
        if ((P1==null)||(!reallyFixed&&(P2==null))){
            obj=select(e.getX(), e.getY(), zc, e.isAltDown());
        }
        setConstructionObject(obj, zc);
    }

    public void setConstructionObject(ConstructionObject obj, ZirkelCanvas zc) {
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
            obj=o;
        }
        if (P1==null) {
            if (obj instanceof PointObject) {
                P1=(PointObject) obj;
                P1.setSelected(true);
                zc.repaint();
            }
        } else {
            if (reallyFixed) {
                final FixedCircleObject c=new FixedCircleObject(zc.getConstruction(), P1, zc.x(EventX), zc.y(EventY));
                zc.addObject(c);
                c.setDefaults();
                zc.repaint();
                O=C=c;
                ShowsValue=c.showValue();
                ShowsName=c.showName();
                if (Global.getParameter("options.movefixname", true)) {
                    C.setShowValue(true);
                    C.setShowName(true);
                }
                Dragging=true;
                Moved=0;
                P2=null;
            } else {
                if (obj instanceof PointObject) {
                    P2=(PointObject) obj;
                    if (P2==P1) {
                        P2=null;
                        return;
                    }
                    final CircleObject c=new CircleObject(zc.getConstruction(), P1, P2);
                    zc.addObject(c);
                    c.setDefaults();
                    c.validate();
                    zc.repaint();
                    if (P2.moveable()&&!P2.isPointOn()&&zc.isNewPoint()) {
                        ShowsValue=c.showValue();
                        ShowsName=c.showName();
                        if (Global.getParameter("options.movename", false)) {
                            c.setShowValue(true);
                            c.setShowName(true);
                        }
                        O=c;
                        Dragging=true;
                        Moved=0;
                    } else {
                        P1.setSelected(false);
                        P1=P2=null;
                    }
                }
            }
        }
        showStatus(zc);
    }

    @Override
    public boolean waitForLastPoint() {
        return P1!=null&&P2==null;
    }

    @Override
    public void finishConstruction(final MouseEvent e, final ZirkelCanvas zc) {
        P2=select(e.getX(), e.getY(), zc, e.isAltDown());
        if (P2!=null) {
            final CircleObject c=new CircleObject(zc.getConstruction(), P1,
                    P2);
            zc.addObject(c);
            c.setDefaults();
            c.validate();
            zc.repaint();
        }
        P2=null;
    }

    @Override
    public boolean waitForPoint() {
        return P1==null||!Fixed;
    }

    @Override
    public void mouseDragged(final MouseEvent e, final ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        }
        Moved++;
        if (P2==null) {
            C.init(zc.getConstruction(), zc.x(e.getX()), zc.y(e.getY()));
            if (C instanceof FixedCircleObject) {
                ((FixedCircleObject) C).setDragable(Moved>5);
            }
        } else {
            P2.move(zc.x(e.getX()), zc.y(e.getY()));
            zc.validate();
        }
        zc.repaint();
    }

    @Override
    public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        }
        Dragging=false;
        O.setShowValue(ShowsValue);
        O.setShowName(ShowsName);
        zc.repaint();
        if (P2==null) {
            P1.setSelected(false);
            P1=null;
            C.round();
            if (Moved<=5) {
                C.edit(zc, true, true);
                if (C instanceof FixedCircleObject
                        &&((FixedCircleObject) C).isEditAborted()) {
                    zc.delete(C);
                    zc.repaint();
                    reset(zc);
                }
            }
            C.validate();
        } else {
            P1.setSelected(false);
            P2.updateText();
            P1=P2=null;
        }
        O.updateCircleDep();
        zc.repaint();
        zc.showStatus();
    }

    public PointObject select(final int x, final int y, final ZirkelCanvas zc, boolean altdown) {
        return zc.selectCreatePoint(x, y, altdown);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        if (zc.Visual) {
            P1=P2=null;
            showStatus(zc);
        } else {
            zc.setPrompt(Global.name("prompt.circle"));
        }
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        if (Fixed) {
            if (P1==null) {
                zc.showStatus(Global.name("message.fixedcircle.midpoint"));
            } else {
                zc.showStatus(Global.name("message.fixedcircle.radius"));
            }
        } else {
            if (P1==null) {
                zc.showStatus(Global.name("message.circle.midpoint"));
            } else {
                zc.showStatus(Global.name("message.circle.radius"));
            }
        }
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {
        if (!testTree(tree, "Circle")) {
            return false;
        }
        final XmlTag tag=tree.getTag();
        try {
            if (!tag.hasParam("midpoint")) {
                throw new ConstructionException("Circle parameters missing!");
            }
            if (!tag.hasParam("through")) {
                if (tag.hasParam("fixed")) {
                    final PointObject p1=(PointObject) c.find(tag.getValue("midpoint"));
                    final FixedCircleObject o=new FixedCircleObject(c, p1, 0,
                            0);
                    c.add(o);
                    setConditionals(tree, c, o);
                    setName(tag, o);
                    set(tree, o);
                    if (tag.hasParam("partial")) {
                        o.setPartial(true);
                    }
                    if (tag.hasParam("filled")) {
                        o.setFilled(true);
                    }
                    if (tag.hasTrueParam("dragable")) {
                        o.setDragable(true);
                    }
                    if (tag.hasTrueParam("drawable")) {
                        o.setDragable(true); // downward compatibility
                    }
                    o.setFixed(tag.getValue("fixed"));
                    if (tag.hasParam("start")&&tag.hasParam("end")) {
                        o.setRange(tag.getValue("start"), tag.getValue("end"));
                    }
                    if (tag.hasParam("acute")) {
                        o.setObtuse(false);
                    }
                    if (tag.hasParam("chord")) {
                        o.setArc(false);
                    }
                } else {
                    if (!(c instanceof Macro)) {
                        throw new ConstructionException(
                                "Circle parameters missing!");
                    }
                    final PointObject p1=(PointObject) c.find(tag.getValue("midpoint"));
                    final PrimitiveCircleObject o=new PrimitiveCircleObject(
                            c, p1);
                    setName(tag, o);
                    set(tree, o);
                    c.add(o);
                    setConditionals(tree, c, o);
                }
            } else {
                final PointObject p1=(PointObject) c.find(tag.getValue("midpoint"));
                final PointObject p2=(PointObject) c.find(tag.getValue("through"));
                final CircleObject o=new CircleObject(c, p1, p2);
                setName(tag, o);
                set(tree, o);
                c.add(o);
                setConditionals(tree, c, o);
                if (tag.hasParam("partial")) {
                    o.setPartial(true);
                }
                if (tag.hasParam("filled")) {
                    o.setFilled(true);
                }
                if (tag.hasParam("start")&&tag.hasParam("end")) {
                    o.setRange(tag.getValue("start"), tag.getValue("end"));
                }
                if (tag.hasParam("acute")) {
                    o.setObtuse(false);
                }
                if (tag.hasParam("chord")) {
                    o.setArc(false);
                }
                if (tag.hasParam("fixed")) {
                    try {
                        o.setFixed(true, tag.getValue("fixed"));
                    } catch (final Exception e) {
                        throw new ConstructionException("Fixed value illegal!");
                    }
                }
            }
        } catch (final ConstructionException e) {
            throw e;
        } catch (final Exception e) {
            throw new ConstructionException("Circle parameters illegal!");
        }
        return true;
    }

    @Override
    public String getTag() {
        return "Circle";
    }

    @Override
    public void construct(final Construction c, final String name,
            final String params[], final int nparams)
            throws ConstructionException {
        if (nparams>3||nparams==0) {
            throw new ConstructionException(Global.name("exception.nparams"));
        }
        final ConstructionObject P1=c.find(params[0]);
        if (P1==null) {
            throw new ConstructionException(Global.name("exception.notfound")
                    +" "+params[0]);
        }
        if (!(P1 instanceof PointObject)) {
            throw new ConstructionException(Global.name("exception.type")+" "
                    +params[0]);
        }
        if (nparams==1) {
            final PrimitiveCircleObject s=new PrimitiveCircleObject(c,
                    (PointObject) P1);
            c.add(s);
            s.setDefaults();
            if (!name.equals("")) {
                s.setNameCheck(name);
            }
            return;
        }
        final ConstructionObject P2=c.find(params[1]);
        if (P2==null) {
            final Expression ex=new Expression(params[1], c, null);
            if (!ex.isValid()) {
                throw new ConstructionException(Global.name("exception.expression"));
            }
            final FixedCircleObject s=new FixedCircleObject(c,
                    (PointObject) P1, 0, 0);
            c.add(s);
            s.setDefaults();
            s.setFixed(params[1]);
            s.validate();
            if (!name.equals("")) {
                s.setNameCheck(name);
            }
            return;
        }
        if (P2 instanceof SegmentObject) {
            final Circle3Object s=new Circle3Object(c, ((SegmentObject) P2).getP1(), ((SegmentObject) P2).getP2(), (PointObject) P1);
            c.add(s);
            s.setDefaults();
            if (!name.equals("")) {
                s.setNameCheck(name);
            }
            return;
        }
        if (!(P2 instanceof PointObject)) {
            throw new ConstructionException(Global.name("exception.type")+" "
                    +params[1]);
        }
        if (nparams==3) {
            final ConstructionObject P3=c.find(params[2]);
            if (P3==null||!(P3 instanceof PointObject)) {
                final CircleObject s=new CircleObject(c, (PointObject) P1,
                        (PointObject) P2);
                if (!s.canFix()) {
                    throw new ConstructionException(Global.name("exception.canfix"));
                }
                s.setFixed(true, params[2]);
                if (!s.isValidFix()) {
                    throw new ConstructionException(Global.name("exception.fix")
                            +" "+params[2]);
                }
                c.add(s);
                s.validate();
                if (!name.equals("")) {
                    s.setNameCheck(name);
                }
                s.setDefaults();
                return;
            } else {
                final Circle3Object cr=new Circle3Object(c, (PointObject) P2,
                        (PointObject) P3, (PointObject) P1);
                c.add(cr);
                cr.setDefaults();
                if (!name.equals("")) {
                    cr.setNameCheck(name);
                }
                return;
            }
        }
        final CircleObject s=new CircleObject(c, (PointObject) P1,
                (PointObject) P2);
        c.add(s);
        s.setDefaults();
        if (!name.equals("")) {
            s.setName(name);
        }
    }
}
