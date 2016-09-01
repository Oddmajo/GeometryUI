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
import java.util.Enumeration;
import java.util.Vector;
import rene.gui.Global;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;

public class AreaConstructor extends ObjectConstructor {

    AreaObject Polygon=null;
    PointObject PreviewPoint=null;
    boolean newPoint = false;

    public static void deletePreview(final ZirkelCanvas zc) {
        zc.reset();
    }

    public void deletePolygon(ZirkelCanvas zc) {
        if (Polygon!=null) {
            // Suppression et réinitialisation :
            zc.delete(Polygon);
            Polygon=null;
            zc.clearSelected();
        }
    }

    public void validPolygon(ZirkelCanvas zc) {
        if (Polygon!=null) {
            Polygon.clearPreviewPoint();
            Polygon.setIndicated(false);
	    zc.update_distant(Polygon, 1);
            Polygon=null;
            zc.clearSelected();
        }
    }

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }
        final PointObject P = zc.selectCreatePoint(e.getX(), e.getY(), e.isAltDown());
	newPoint = newPoint || zc.isNewPoint();
        setConstructionObject(P, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject P, ZirkelCanvas zc) {
        // Il y a eu ambiguité et la méthode est forcément appelée par le
        // popupmenu de sélection :
        if ((JSelectPopup.isCallerObject())&&(P instanceof PointonObject)) {
            int x=JSelectPopup.getMouseX();
            int y=JSelectPopup.getMouseY();
            PointObject o=new PointObject(zc.getConstruction(), zc.x(x), zc.y(y), P);
	    newPoint = true;
            o.setUseAlpha(true);
            zc.addObject(o);
            zc.validate();
            o.setDefaults();
            zc.repaint();
            o.edit(zc, false, false);
            P=o;
        }

        if (P!=null) {
            if (Polygon==null) {
                // Création et mise en forme de l'objet polygone :
                Polygon=new AreaObject(zc.getConstruction(), new Vector());
                zc.addObject(Polygon);
                Polygon.setDefaults();
                if (!Polygon.isSolid()) {
                    Polygon.setBack(true);
                }
                // Création du point preview (sommet n+1) :
                PreviewPoint=new PointObject(zc.getConstruction(), "PrevPoint");
                PreviewPoint.setXY(P.getX(), P.getY());
                Polygon.setPreviewPoint(PreviewPoint);
            }

            P.setSelected(true);
            final Enumeration en=Polygon.V.elements();
            while (en.hasMoreElements()) {
                // Si on "referme" le polygone en cliquant sur l'un des points existants :
                if (en.nextElement()==P) {
                    // Et si le nombre de sommets est inférieur à 3, cela s'apparente à
                    // une annulation :
                    if (Polygon.V.size()<3) {
                        deletePolygon(zc);
                    } else {
			// Si on a créé un sommet à la volée, il faut réorganiser
			// pour éviter un pb de dépendance
			if(newPoint){
			    System.out.println("new point !");
			    zc.getConstruction().reorderConstruction();
			    zc.reloadCD();
			    newPoint = false;
			}
                        validPolygon(zc);
                    }
                    // de toutes façons, on quitte :
                    return;
                }
            }
            Polygon.V.addElement(P);
            zc.validate();
            zc.repaint();
        }
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        PointObject p=zc.indicateCreatePoint(e.getX(), e.getY(), false);
        if (Polygon!=null) {
            if ((p==null)) {
                PreviewPoint.move(zc.x(e.getX()), zc.y(e.getY()));
            } else {
                PreviewPoint.move(p.getX(), p.getY());
            }
            Polygon.validate();
            Polygon.setDefaults();
            Polygon.setIndicated(true);
            zc.repaint();
        }
    }

    @Override
    public String getTag() {
        return "Polygon";
    }

    @Override
    public void construct(final Construction c, final String name,
            final String params[], final int nparams)
            throws ConstructionException {
        if (nparams<3) {
            throw new ConstructionException(Global.name("exception.nparams"));
        }
        final Vector v=new Vector();
        for (int i=0; i<nparams; i++) {
            final ConstructionObject o=c.find(params[i]);
            if (o==null) {
                throw new ConstructionException(Global.name("exception.notfound")
                        +" "+params[i]);
            }
            if (!(o instanceof PointObject)) {
                throw new ConstructionException(Global.name("exception.type")
                        +" "+params[i]);
            }
            v.addElement(o);
        }
        final AreaObject o=new AreaObject(c, v);
        if (!name.equals("")) {
            o.setNameCheck(name);
        }
        c.add(o);
        o.setDefaults();
        o.setBack(true);
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {
        if (!testTree(tree, "Polygon")) {
            return false;
        }
        final XmlTag tag=tree.getTag();
        try {
            int i=1;
            final Vector v=new Vector();
            while (true) {
                final PointObject p=(PointObject) c.find(tag.getValue("point"
                        +i));
                if (p==null) {
                    break;
                }
                v.addElement(p);
                i++;
            }
            final AreaObject o=new AreaObject(c, v);
            o.setBack(true);
            setName(tag, o);
            set(tree, o);
            c.add(o);
            setConditionals(tree, c, o);
            if (tag.hasParam("filled")) {
                o.setFilled(false);
            }
        } catch (final ConstructionException e) {
            throw e;
        } catch (final Exception e) {
            throw new ConstructionException("Polygon parameters illegal!");
        }
        return true;
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.name("message.area"));
        zc.setPrompt("="+Global.name("prompt.area"));
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        deletePolygon(zc);
        zc.showStatus(Global.name("message.area"));
    }
}
