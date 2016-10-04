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

// file: LineConstructor.java
import eric.JSelectPopup;
import java.awt.event.MouseEvent;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.macro.Macro;
import rene.zirkel.objects.AxisObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.LineObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.SegmentObject;

public class LineConstructor extends ObjectConstructor {

    PointObject P1=null, P2=null;
    ConstructionObject O;
    boolean Fix=false;
    boolean ShowsValue, ShowsName, Moved;

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }
        Fix=e.isShiftDown()||isFixed();

        ConstructionObject obj=select(e.getX(), e.getY(), zc, e.isAltDown());
        if (obj!=null) {
            setConstructionObject(obj, zc);
        } else {
            Dragging=false;
        }
    }

    @Override
    public boolean waitForLastPoint() {
        return P1!=null&&P2==null;
    }

    @Override
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
//                System.out.println("premier point="+obj.getName());
                P1=(PointObject) obj;
                P1.setSelected(true);
                zc.repaint();
            }
        } else if (P2==null) {
            if (obj instanceof PointObject) {
//                System.out.println("deuxième point="+obj.getName());
                P2=(PointObject) obj;
                if (P2==P1) {
                    P2=null;
                    return;
                }
                final ConstructionObject o=create(zc.getConstruction(), P1,P2);
                zc.addObject(o);
                o.setDefaults();
                if (P2.moveable()&&!P2.isPointOn()&&zc.isNewPoint()) {
                    Dragging=true;
                    Moved=false;
                    O=o;
                    ShowsValue=o.showValue();
                    ShowsName=o.showName();
                    if ((Fix&&Global.getParameter("options.movefixname", true))||(!Fix&&Global.getParameter("options.movename",
                            false))) {
                        o.setShowValue(true);
                        o.setShowName(true);
                    }
                } else {
                    Dragging=false;
                    if (Fix) {
                        setFixed(zc, o);
                    }
                    P1=P2=null;
                }
                zc.clearSelected();
            }
        }
        showStatus(zc);
    }

    @Override
    public void finishConstruction(final MouseEvent e, final ZirkelCanvas zc) {
        P2=select(e.getX(), e.getY(), zc, e.isAltDown());
        if (P2!=null) {
            final ConstructionObject o=create(zc.getConstruction(), P1, P2);
            zc.addObject(o);
            o.setDefaults();
            zc.validate();
            zc.repaint();
            P2=null;
        }
    }

    @Override
    public void mouseDragged(final MouseEvent e, final ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        };
        Moved=true;
        if (P2!=null) {
            P2.move(zc.x(e.getX()), zc.y(e.getY()));
            zc.validate();
            zc.repaint();
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        }
        Dragging=false;
        O.setShowValue(ShowsValue);
        O.setShowName(ShowsName);
        if (Fix) {
            O.round();
        }
        zc.repaint();
        if (Fix&&!Moved) {
            setFixed(zc, O);
        } else {
            try {
                O.setFixed(false, O.getStringLength());
            } catch (Exception ex) {}
        }
        reset(zc);
    }

    public boolean isFixed() {
        return false;
    }

    public void setFixed(final ZirkelCanvas zc, final ConstructionObject o) {
    }

    public PointObject select(final int x, final int y, final ZirkelCanvas zc, boolean altdown) {
        return zc.selectCreatePoint(x, y, altdown);
    }

    public ConstructionObject create(Construction c,PointObject p1, PointObject p2) {
        return new LineObject(c, p1, p2);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        if (!zc.Visual) {
            zc.setPrompt(getPrompt());
        } else {
            zc.clearSelected();
            P1=P2=null;
            showStatus(zc);
        }
    }

    public String getPrompt() {
        return Global.name("prompt.line");
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        if (P1==null) {
            zc.showStatus(Global.name("message.line.first",
                    "Line: Set the first point!"));
        } else {
            zc.showStatus(Global.name("message.line.second",
                    "Line: Set the second point!"));
        }
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {

        if (!testTree(tree, "Line")) {
            return false;
        }
        final XmlTag tag=tree.getTag();

        if (tag.hasParam("xaxis")) {
            final AxisObject o=new AxisObject(c, true);
            setName(tag, o);
            set(tree, o);
            c.add(o);
            c.xAxis=o;
            return true;
        }
        if (tag.hasParam("yaxis")) {
            final AxisObject o=new AxisObject(c, false);
            setName(tag, o);
            set(tree, o);
            c.add(o);
            c.yAxis=o;
            return true;
        }

        if (!tag.hasParam("from")||!tag.hasParam("to")) {
            if (!(c instanceof Macro)) {
                throw new ConstructionException("Line points missing!");
            }
            final PrimitiveLineObject o=new PrimitiveLineObject(c);
            setName(tag, o);
            set(tree, o);
            c.add(o);
            setConditionals(tree, c, o);
        } else {
            try {
                final PointObject p1=(PointObject) c.find(tag.getValue("from"));
                final PointObject p2=(PointObject) c.find(tag.getValue("to"));
                final LineObject o=new LineObject(c, p1, p2);
                if (tag.hasParam("partial")) {
                    o.setPartial(true);
                }
                setName(tag, o);
                set(tree, o);
                c.add(o);
                setConditionals(tree, c, o);
            } catch (final ConstructionException e) {
                throw e;
            } catch (final Exception e) {
                throw new ConstructionException("Line points illegal!");
            }
        }
        return true;
    }

    @Override
    public String getTag() {
        return "Line";
    }

    @Override
    public void construct(final Construction c, final String name,
            final String params[], final int nparams)
            throws ConstructionException {

        if (nparams!=2) {
            throw new ConstructionException(Global.name("exception.nparams"));
        }
        final ConstructionObject P1=c.find(params[0]);
        if (P1==null) {
            throw new ConstructionException(Global.name("exception.notfound")+" "+params[0]);
        }
        final ConstructionObject P2=c.find(params[1]);
        if (P2==null) {
            throw new ConstructionException(Global.name("exception.notfound")+" "+params[1]);
        }
        if (!(P1 instanceof PointObject)) {
            throw new ConstructionException(Global.name("exception.type")+" "+params[0]);
        }
        if (!(P2 instanceof PointObject)) {
            throw new ConstructionException(Global.name("exception.type")+" "+params[1]);
        }
        final LineObject s=new LineObject(c, (PointObject) P1,
                (PointObject) P2);

        c.add(s);
        s.setDefaults();
        if (!name.equals("")) {
            s.setNameCheck(name);
        }
    }
}
