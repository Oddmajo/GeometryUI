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

// file: ParallelConstructor.java
import java.awt.event.MouseEvent;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ParallelObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveLineObject;

public class ParallelConstructor extends ObjectConstructor {

    PointObject P=null;
    PrimitiveLineObject L=null;

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }
        ConstructionObject obj=null;
        if (L==null) {
            obj=selectline(e.getX(), e.getY(), zc);
        } else {
            obj=select(e.getX(), e.getY(), zc, e.isAltDown());
        }
        setConstructionObject(obj, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject obj, ZirkelCanvas zc) {
        if (L==null) {
            if (obj instanceof PrimitiveLineObject) {
                L=(PrimitiveLineObject) obj;
                L.setSelected(true);
                zc.repaint();
            }
        } else if (P==null) {
            if (obj instanceof PointObject) {
                P=(PointObject) obj;
                final ConstructionObject o=create(zc.getConstruction(), L, P);
                zc.addObject(o);
                o.setDefaults();
                P=null;
                L=null;
                zc.clearSelected();
            }
        }
        showStatus(zc);
    }

    @Override
    public boolean waitForLastPoint() {
        return L!=null;
    }

    @Override
    public void finishConstruction(final MouseEvent e, final ZirkelCanvas zc) {
        P=select(e.getX(), e.getY(), zc, e.isAltDown());
        if (P!=null) {
            final ConstructionObject o=create(zc.getConstruction(), L, P);
            zc.addObject(o);
            o.setDefaults();
            zc.validate();
            zc.repaint();
            P=null;
        }
    }
//        @Override
//	public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
//			final boolean simple) {
//		if (!simple && waitForLastPoint()) {
//			if (zc.isPreview()) {
//				zc.movePreview(e);
//			} else {
//				zc.prepareForPreview(e);
//				finishConstruction(e, zc);
//			}
//		}
//		if (L == null)
//			zc.indicateLineObjects(e.getX(), e.getY());
//		else if (P == null)
//			zc.indicateCreatePoint(e.getX(), e.getY(), false);
//	}

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        if (L==null) {
            zc.indicateLineObjects(e.getX(), e.getY());
        } else if (P==null) {
            PointObject p=zc.indicateCreatePoint(e.getX(), e.getY(), false);
            if (!simple&&waitForLastPoint()) {
                if (zc.isPreview()) {
                    if (p==null) {
                        zc.movePreview(e);
                    } else {
                        zc.movePreview(p.getX(), p.getY());
                    }
                } else {
                    zc.prepareForPreview(e);
                    finishConstruction(e, zc);
                }
            }
        }


    }

    public PointObject select(final int x, final int y, final ZirkelCanvas zc, boolean altdown) {
        return zc.selectCreatePoint(x, y, altdown);
    }

    public PrimitiveLineObject selectline(final int x, final int y,
            final ZirkelCanvas zc) {
        return zc.selectLine(x, y);
    }

    public PrimitiveLineObject create(final Construction c,
            final PrimitiveLineObject l, final PointObject p) {
        return new ParallelObject(c, l, p);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        if (zc.Visual) {
            P=null;
            L=null;
            showStatus(zc);
        } else {
            zc.setPrompt(getPrompt());
        }
    }

    public String getPrompt() {
        return Global.name("prompt.parallel");
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        if (L==null) {
            zc.showStatus(Global.name("message.parallel.first",
                    "Parallel: Choose a line!"));
        } else {
            zc.showStatus(Global.name("message.parallel.second",
                    "Parallel: Choose a Point!"));
        }
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {
        if (!testTree(tree, "Parallel")) {
            return false;
        }
        final XmlTag tag=tree.getTag();
        if (!tag.hasParam("point")||!tag.hasParam("line")) {
            throw new ConstructionException("Parallel parameters missing!");
        }
        try {
            final PointObject p1=(PointObject) c.find(tag.getValue("point"));
            final PrimitiveLineObject p2=(PrimitiveLineObject) c.find(tag.getValue("line"));
            final ParallelObject o=new ParallelObject(c, p2, p1);
            setName(tag, o);
            set(tree, o);
            if (tag.hasParam("partial")) {
                o.setPartial(true);
            }
            c.add(o);
            setConditionals(tree, c, o);
        } catch (final ConstructionException e) {
            throw e;
        } catch (final Exception e) {
            throw new ConstructionException("Parallel parameters illegal!");
        }
        return true;
    }

    @Override
    public String getTag() {
        return "Parallel";
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
            throw new ConstructionException(Global.name("exception.notfound")
                    +" "+params[0]);
        }
        final ConstructionObject P2=c.find(params[1]);
        if (P2==null) {
            throw new ConstructionException(Global.name("exception.notfound")
                    +" "+params[1]);
        }
        if (!(P1 instanceof PrimitiveLineObject)) {
            throw new ConstructionException(Global.name("exception.type")+" "
                    +params[0]);
        }
        if (!(P2 instanceof PointObject)) {
            throw new ConstructionException(Global.name("exception.type")+" "
                    +params[1]);
        }
        final ParallelObject s=new ParallelObject(c,
                (PrimitiveLineObject) P1, (PointObject) P2);
        if (!name.equals("")) {
            s.setNameCheck(name);
        }
        c.add(s);
        s.setDefaults();
    }
}
