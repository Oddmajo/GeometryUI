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

import eric.JZirkelCanvas;
import eric.Media;
import java.awt.event.MouseEvent;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ImageObject;
import rene.zirkel.objects.PointObject;

public class ImageConstructor extends ObjectConstructor {
	PointObject P[];
	int NPoints;

	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		if (!zc.Visual)
			return;
		final PointObject p = zc.selectCreatePoint(e.getX(), e.getY(), e.isAltDown());
		if (p != null) {
			P[NPoints++] = p;
			p.setSelected(true);
			zc.repaint();
		}
		showStatus(zc);
		if (NPoints == 3) {
                    
			final String filename = zc.loadImage();
                        
			if (filename.equals("")) {
				reset(zc);
				return;
			}
                        Media.createMedia(filename);
			final ImageObject o = new ImageObject(zc.getConstruction(), P,
					filename);
                        
			zc.addObject(o);
			zc.clearSelected();
			reset(zc);
			zc.repaint();
		}
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		if (NPoints <= 1)
			zc.showStatus(ConstructionObject.text1(
					Global.name("message.image"), "" + (NPoints + 1)));
		else
			zc.showStatus(Global.name("message.image.last"));
	}

	@Override
	public void reset(final ZirkelCanvas zc) {
		super.reset(zc);
		if (zc.Visual) {
			P = new PointObject[3];
			NPoints = 0;
			showStatus(zc);
		} else {
			zc.setPrompt(Global.name("prompt.image"));
		}
	}

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "Image"))
			return false;
		final XmlTag tag = tree.getTag();
		for (int i = 0; i < 3; i++)
			if (!tag.hasParam("point" + (i + 1)))
				throw new ConstructionException("Image points missing!");
		if (!tag.hasParam("filename"))
			throw new ConstructionException("Image filename missing!");
		try {
			final PointObject P[] = new PointObject[3];
			for (int i = 0; i < 3; i++)
				P[i] = (PointObject) c.find(tag.getValue("point" + (i + 1)));
			final String filename = JZirkelCanvas.getFilePath(c)+tag.getValue("filename");

                        Media.createMedia(filename);
			final ImageObject p = new ImageObject(c, P, filename);

			setName(tag, p);
			set(tree, p);
			c.add(p);
			setConditionals(tree, c, p);
		} catch (final ConstructionException e) {
			throw e;
		} catch (final Exception e) {
			throw new ConstructionException("Image points illegal!");
		}
		return true;
	}

	public String getPrompt() {
		return Global.name("prompt.image");
	}

	@Override
	public String getTag() {
		return "Image";
	}

	@Override
	public void construct(final Construction c, final String name,
			final String params[], final int nparams)
	throws ConstructionException {
	}

}
