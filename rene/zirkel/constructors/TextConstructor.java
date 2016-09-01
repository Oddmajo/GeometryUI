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

import java.awt.event.MouseEvent;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.TextObject;

public class TextConstructor extends ObjectConstructor {
	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		final double x = zc.x(e.getX()), y = zc.y(e.getY());
		final TextObject p = new TextObject(zc.getConstruction(), x, y);
		zc.addObject(p);
		p.edit(zc, true, true);
		p.setDefaults();
		zc.repaint();
	}

	@Override
	public boolean waitForPoint() {
		return false;
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		zc.showStatus(Global.name("message.text"));
	}

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "Text"))
			return false;
		final XmlTag tag = tree.getTag();
		if (!tag.hasParam("x") || !tag.hasParam("y"))
			throw new ConstructionException("Point coordinates missing!");
		final TextObject p = new TextObject(c, 0, 0);
		double x, y;
		try {
			x = new Expression(tag.getValue("x"), c, p).getValue();
			y = new Expression(tag.getValue("y"), c, p).getValue();
			p.move(x, y);
		} catch (final Exception e) {
		}
		setName(tag, p);
		set(tree, p);
		c.add(p);
		setConditionals(tree, c, p);
		p.setLines(p.getText());
		if (tag.hasParam("fixed")) {
			p.setFixed(tag.getValue("x"), tag.getValue("y"));
		}
		return true;
	}

	@Override
	public void reset(final ZirkelCanvas zc) {
		super.reset(zc);
		showStatus(zc);
	}
}
