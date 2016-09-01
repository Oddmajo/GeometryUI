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

// file: ObjectConstructor.java
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rene.gui.Global;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTagText;
import rene.util.xml.XmlTree;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;

public class ObjectConstructor {

    protected boolean Dragging=false;

    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
    }

    public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc) {
    }

    public void mouseDragged(final MouseEvent e, final ZirkelCanvas zc) {
    }

    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {

//        System.out.println("moved : "+e.getX()+" "+e.getY());
        PointObject p=null;
        if (simple&&(waitForPoint()||waitForLastPoint())) {
            zc.indicateCreatePoint(e.getX(), e.getY(), false);
            return;
        }
        if (waitForPoint()) {
            p=zc.indicateCreatePoint(e.getX(), e.getY(), false);
        }
        if (waitForLastPoint()) {
            if (zc.isPreview()) {
                if (p==null) {
                    zc.movePreview(e);
                } else {
                    zc.movePreview(p.getX(), p.getY());
                }
            } else {
                zc.prepareForPreview(e);
                finishConstruction(e, zc);
                return;
            }
        }
    }

    public void setConstructionObject(ConstructionObject obj,ZirkelCanvas zc){
    }

    public void finishConstruction(final MouseEvent e, final ZirkelCanvas zc) {
    }

    public boolean waitForLastPoint() {
        return false;
    }

    public boolean waitForPoint() {
        return true;
    }

    public void reset(final ZirkelCanvas zc) {
        zc.validate();
        zc.clearSelected();
    }

    public void resetFirstTime(final ZirkelCanvas zc) {
        reset(zc);
    }

    public void invalidate(final ZirkelCanvas zc) {
        zc.clearSelectionRectangle();
        zc.clearMultipleSelection();
    }

    public void showStatus(final ZirkelCanvas zc) {
    }

    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {
        return false;
    }

    public boolean testTree(final XmlTree t, final String tag) {
        return t.getTag().name().equals(tag);
    }

    public void setName(final XmlTag tag, final ConstructionObject o) {
        if (tag.hasParam("name")) {
            o.setName(tag.getValue("name"));

        }
        if (tag.hasParam("alias")) {
            o.setAlias(tag.getValue("alias"));
        }
    }

    public void set(XmlTree tree, final ConstructionObject o)
            throws ConstructionException {
        final XmlTag tag=tree.getTag();
        if (tag.hasParam("n")) {
            try {
                o.setNCount(new Integer(tag.getValue("n")).intValue());
                o.setGotNCount(true);
            } catch (final Exception ex) {
                throw new ConstructionException("Illegal count!");
            }
        }

        if (tag.hasParam("hidden")) {
            if (tag.getValue("hidden").equals("super")) {
                o.setSuperHidden(true);
            } else {
                o.setHidden(true);
            }
        }
        if (tag.hasTrueParam("tracked")) {
            o.setTracked(true);
        }
        if (tag.hasTrueParam("showvalue")) {
            o.setShowValue(true);
        }
        if (tag.hasTrueParam("showname")) {
            o.setShowName(true);
        }
        if (tag.hasTrueParam("background")) {
            o.setBack(true);
        } else {
            o.setBack(false);
        }
        if (tag.hasTrueParam("parameter")) {
            o.setParameter(true);
        }
        if (tag.hasTrueParam("mainparameter")) {
            o.setMainParameter(true);
        }
        if (tag.hasTrueParam("target")) {
            o.setTarget(true);
        }
        if (tag.hasTrueParam("break")) {
            o.setBreak(true);
        }
        if (tag.hasTrueParam("hidebreak")) {
            o.setHideBreak(true);
        }
        if (tag.hasTrueParam("solid")) {
            o.setSolid(true);
        }
        if (tag.hasTrueParam("bold")) {
            o.setBold(true);
        }
        if (tag.hasTrueParam("large")) {
            o.setLarge(true);
        }
        if (tag.hasParam("dp_mode")) {
            int x=Integer.parseInt(tag.getValue("dp_mode"));
            o.setDPMode(x);
        }
        if (tag.hasParam("xoffset")||tag.hasParam("yoffset")) {
            int x=0, y=0;
            try {
                if (tag.hasParam("xoffset")) {
                    x=Integer.parseInt(tag.getValue("xoffset"));
                }
                if (tag.hasParam("yoffset")) {
                    y=Integer.parseInt(tag.getValue("yoffset"));
                }
                o.setOffset(x, y);
            } catch (final Exception e) {
                throw new ConstructionException("Illegal offset value");
            }
        }
        if (tag.hasTrueParam("keepclose")) {
            o.setKeepClose(true);
        }
        if (tag.hasParam("xcoffset")||tag.hasParam("ycoffset")) {
            double x=0, y=0;
            try {
                if (tag.hasParam("xcoffset")) {
                    x=new Double(tag.getValue("xcoffset")).doubleValue();
                }
                if (tag.hasParam("ycoffset")) {
                    y=new Double(tag.getValue("ycoffset")).doubleValue();
                }
                o.setcOffset(x, y);
            } catch (final Exception e) {
                throw new ConstructionException("Illegal offset value");
            }
        }

        if (tag.hasParam("color")) {
            try {
                final String s=tag.getValue("color");
                int n=-1;
                for (int i=0; i<ZirkelFrame.ColorStrings.length; i++) {
                    if (s.equals(ZirkelFrame.ColorStrings[i])) {
                        o.setColor(i);
                        n=i;
                        break;
                    }
                }
                if (n<0) {
                    n=Integer.parseInt(s);
                    if (n<0||n>=ZirkelFrame.Colors.length) {
                        throw new Exception("");
                    }
                    o.setColor(n);
                }
            } catch (final Exception ex) {
                throw new ConstructionException("Illegal color index (1-"
                        +(ZirkelFrame.Colors.length-1)+")");
            }
        }
        if (tag.hasParam("scolor")) {
            String reg="([0-9]+),([0-9]+),([0-9]+)";
            Matcher m=Pattern.compile(reg).matcher(tag.getValue("scolor"));
            // Si une couleur RGB est trouvée, dans un format antérieur à la 3.5.5,
            // soit par exemple "175,250,99" :
            if (m.find()) {
                int red=Integer.parseInt(m.group(1));
                int green=Integer.parseInt(m.group(2));
                int blue=Integer.parseInt(m.group(3));
                o.setSpecialColor(new Color(red, green, blue));
            } else {
                reg="(.+),,(.+),,(.+)";
                m=Pattern.compile(reg).matcher(tag.getValue("scolor"));
                // Si une couleur RGB est trouvée, dans un format à partir de la 3.5.5,
                // soit par exemple "x(A)*255,,y(A)*255,,99" :
                if (m.find()) {
                    o.setSpecialColor(m.group(1), m.group(2), m.group(3));
                }
            }
        }
        if (tag.hasParam("type")) {
            final String type=tag.getValue("type");
            if (type.equals("thick")) {
                o.setColorType(ConstructionObject.THICK);
            }
            if (type.equals("thin")) {
                o.setColorType(ConstructionObject.THIN);
            }
            if (type.equals("invisible")) {
                o.setColorType(ConstructionObject.INVISIBLE);
            }
        }
        if (tag.hasParam("unit")) {
            o.setUnit(tag.getValue("unit"));
        } else {
            o.setUnit("");
        }
        final Enumeration e=tree.getContent();
        while (e.hasMoreElements()) {
            tree=(XmlTree) e.nextElement();
            if (tree.getTag() instanceof XmlTagText) {
                o.setText(((XmlTagText) tree.getTag()).getContent(), true);
            }
        }
    }

    public void setConditionals(final XmlTree tree, final Construction c,
            final ConstructionObject o) {
        o.clearConditionals();
        int i=0;
        final XmlTag tag=tree.getTag();
        while (tag.hasParam("ctag"+i)&&tag.hasParam("cexpr"+i)) {
            final String t=tag.getValue("ctag"+i);
            final String e=tag.getValue("cexpr"+i);
            final Expression ex=new Expression(e, c, o);
            o.addConditional(t, ex);
            i++;
        }
    }

    public String getTag() {
        return "???";
    }

    public void construct(final Construction c, final String name,
            final String params[], final int nparams)
            throws ConstructionException {
        throw new ConstructionException("");
    }

    // for MetaMover :
    public void pause(final boolean flag) {
    }

    public boolean useSmartBoard() {
        return true;
    }
}
