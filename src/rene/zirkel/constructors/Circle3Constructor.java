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

// file: Cicle3Constructor.java

import java.awt.event.MouseEvent;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.Circle3Object;
import rene.zirkel.objects.PointObject;

public class Circle3Constructor extends ObjectConstructor {
	PointObject P1 = null, P2 = null, P3 = null;

	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		if (!zc.Visual)
			return;
		if (P1 == null) {
			P1 = select(e.getX(), e.getY(), zc, e.isAltDown());
			if (P1 != null) {
				P1.setSelected(true);
				zc.repaint();
			}
			showStatus(zc);
		} else if (P2 == null) {
			P2 = select(e.getX(), e.getY(), zc, e.isAltDown());
			if (P2 != null && P2 != P1) {
				P2.setSelected(true);
				zc.repaint();
			}
			showStatus(zc);
		} else {
			P3 = select(e.getX(), e.getY(), zc, e.isAltDown());
			if (P3 != null) {
				final Circle3Object c = new Circle3Object(zc.getConstruction(),
						P1, P2, P3);
				zc.addObject(c);
				c.setDefaults();
				c.updateCircleDep();
				P1 = P2 = P3 = null;
				zc.clearSelected();
				showStatus(zc);
			}
		}
	}

	@Override
	public boolean waitForLastPoint() {
		return P1 != null && P2 != null;
	}

	@Override
	public void finishConstruction(final MouseEvent e, final ZirkelCanvas zc) {
		P3 = select(e.getX(), e.getY(), zc, e.isAltDown());
		if (P3 != null) {
			final Circle3Object c = new Circle3Object(zc.getConstruction(), P1,
					P2, P3);
			zc.addObject(c);
			c.setDefaults();
			zc.validate();
			zc.repaint();
			P3 = null;
		}
	}

	public PointObject select(final int x, final int y, final ZirkelCanvas zc, boolean altdown) {
		return zc.selectCreatePoint(x, y, altdown);
	}

	@Override
	public void reset(final ZirkelCanvas zc) {
		super.reset(zc);
		if (zc.Visual) {
			P1 = P2 = P3 = null;
			showStatus(zc);
		} else {
			zc.setPrompt(Global.name("prompt.circle3"));
		}
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		if (P1 == null)
			zc.showStatus(Global.name("message.circle3.first",
			"Circle: Choose the first radius point!"));
		else if (P2 == null)
			zc.showStatus(Global.name("message.circle3.second",
			"Circle: Choose the second radius point!"));
		else
			zc.showStatus(Global.name("message.circle3.midpoint",
			"Circle: Choose the midpoint!"));
	}

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "Circle3"))
			return false;
		final XmlTag tag = tree.getTag();
		if (!tag.hasParam("midpoint") || !tag.hasParam("from")
				|| !tag.hasParam("to"))
			throw new ConstructionException("Circle3 parameters missing!");
		try {
			final PointObject p1 = (PointObject) c.find(tag
					.getValue("midpoint"));
			final PointObject p2 = (PointObject) c.find(tag.getValue("from"));
			final PointObject p3 = (PointObject) c.find(tag.getValue("to"));
			final Circle3Object o = new Circle3Object(c, p2, p3, p1);
			if (tag.hasParam("partial"))
				o.setPartial(true);
			if (tag.hasParam("start") && tag.hasParam("end"))
				o.setRange(tag.getValue("start"), tag.getValue("end"));
			setName(tag, o);
			set(tree, o);
			c.add(o);
			setConditionals(tree, c, o);
		} catch (final ConstructionException e) {
			throw e;
		} catch (final Exception e) {
			throw new ConstructionException("Circle3 parameters illegal!");
		}
		return true;
	}
}
