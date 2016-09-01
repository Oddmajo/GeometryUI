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

// file: QuadricConstructor.java
import eric.JSelectPopup;
import java.awt.event.MouseEvent;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;
import rene.zirkel.objects.QuadricObject;

public class QuadricConstructor extends ObjectConstructor {

    private PointObject Points[];
    private int NPoints;
    private static PointObject previewPoint;
    private QuadricObject Quadric;
    private boolean newPoint = false;

    public static void deletePreview(final ZirkelCanvas zc) {
	zc.reset();
    }

    public void validQuadric(ZirkelCanvas zc){
	if(Quadric!=null){
	    zc.clearSelected();
	    Quadric.updateText();
	    initialize();
	}
    }

    public void initialize(){
	previewPoint = null;
	Quadric = null;
	NPoints = 0;
	Points = new PointObject[5];
    }

    private void arrangePoints() {
        if (NPoints<5) {
            Points[4]=previewPoint;
            for (int i=3; i>=NPoints; i--) {
                Points[i]=Points[0];
            }
        }
    }

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc){
	if (!zc.Visual) {
            return;
        }

	if (previewPoint==null) {
            previewPoint = new PointObject(zc.getConstruction(), "PrevPoint");
        }

	HideQuadric(true);
	final PointObject P = zc.selectCreatePoint(e.getX(), e.getY(), e.isAltDown());
	HideQuadric(false);

	newPoint = newPoint || zc.isNewPoint();
	setConstructionObject(P, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject P, ZirkelCanvas zc){
	// Il y a eu ambiguité et la méthode est forcément appelée par le
        // popupmenu de sélection :
        if ((JSelectPopup.isCallerObject())&&(P instanceof PointonObject)) {
            int x = JSelectPopup.getMouseX();
            int y = JSelectPopup.getMouseY();
            PointObject o = new PointObject(zc.getConstruction(), zc.x(x), zc.y(y), P);
	    newPoint = true;
            o.setUseAlpha(true);
            zc.addObject(o);
            zc.validate();
            o.setDefaults();
            zc.repaint();
            o.edit(zc, false, false);
            P=o;
        }

	if(P!=null){
	    Points[NPoints++] = (PointObject)P;
	    P.setSelected(true);

	    if(Quadric==null){
		arrangePoints();
		Quadric = new QuadricObject(zc.getConstruction(), Points);
		zc.addObject(Quadric);
		Quadric.setDefaults();
		zc.repaint();
	    }

	    if (NPoints==5) {
		validQuadric(zc);

		// Si on a créé un sommet à la volée, il faut réorganiser la construction
		// pour éviter un pb de dépendance
		if(newPoint){
		    zc.getConstruction().reorderConstruction();
		    zc.reloadCD();
		    newPoint = false;
		}
	    }
	}
	showStatus(zc);
    }

    public void HideQuadric(boolean b){
	if(Quadric!=null)
	    Quadric.setHidden(b);
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc, final boolean simple) {
        if (previewPoint!=null) {
            previewPoint.move(zc.x(e.getX()), zc.y(e.getY()));
	    HideQuadric(true);
            if (waitForPoint()) {
                PointObject pt = zc.indicateCreatePoint(e.getX(), e.getY(), false);
                if (zc.getConstruction().indexOf(pt)!=-1) {
                    previewPoint.move(pt.getX(), pt.getY());
                }
            }
	    HideQuadric(false);
	    Quadric.setDefaults();
            Quadric.validate();
            zc.repaint();
        }
        else super.mouseMoved(e, zc, simple);
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        zc.showStatus(Global.name("message.quadric")+" "+(NPoints+1));
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        super.reset(zc);
        if (zc.Visual) {
	    if(Quadric!=null){
		zc.delete(Quadric);
	    }
	    initialize();
            showStatus(zc);
        } else {
            zc.setPrompt(Global.name("prompt.quadric"));
        }
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {
        if (!testTree(tree, "Quadric")) {
            return false;
        }
        final XmlTag tag=tree.getTag();
        for (int i=0; i<5; i++) {
            if (!tag.hasParam("point"+(i+1))) {
                throw new ConstructionException("Quadric points missing!");
            }
        }
        try {
            final PointObject P[]=new PointObject[5];
            for (int i=0; i<5; i++) {
                P[i]=(PointObject) c.find(tag.getValue("point"+(i+1)));
            }
            final QuadricObject p=new QuadricObject(c, P);
            setName(tag, p);
            set(tree, p);
            c.add(p);
            setConditionals(tree, c, p);
        } catch (final ConstructionException e) {
            throw e;
        } catch (final Exception e) {
            throw new ConstructionException("Quadric points illegal!");
        }
        return true;
    }

    public String getPrompt() {
        return Global.name("prompt.quadric");
    }

    @Override
    public String getTag() {
        return "Quadric";
    }

    @Override
    public void construct(final Construction c, final String name,
            final String params[], final int nparams)
            throws ConstructionException {
        if (nparams!=5) {
            throw new ConstructionException(Global.name("exception.nparams"));
        }
        final ConstructionObject P[]=new PointObject[5];
        for (int i=0; i<5; i++) {
            P[i]=c.find(params[i]);
            if (P[i]==null) {
                throw new ConstructionException(Global.name("exception.notfound")
                        +" "+params[i]);
            }
            if (!(P[i] instanceof PointObject)) {
                throw new ConstructionException(Global.name("exception.type")
                        +" "+params[i]);
            }
        }
        final QuadricObject s=new QuadricObject(c, (PointObject[]) P);
        if (!name.equals("")) {
            s.setNameCheck(name);
        }
        c.add(s);
        s.setDefaults();
    }
}
