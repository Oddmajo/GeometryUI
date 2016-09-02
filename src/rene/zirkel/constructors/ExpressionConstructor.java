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
import rene.zirkel.objects.ExpressionObject;

public class ExpressionConstructor extends ObjectConstructor {
	ExpressionObject O;

	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		final double x = zc.x(e.getX()), y = zc.y(e.getY());
		final ExpressionObject o = new ExpressionObject(zc.getConstruction(),
				x, y);
		zc.addObject(o);
		o.setShowName(false);
		o.setDefaults();
		zc.repaint();
		Dragging = true;
		O = o;
	}

	@Override
	public void mouseDragged(final MouseEvent e, final ZirkelCanvas zc) {
		if (!Dragging)
			return;
		O.move(zc.x(e.getX()), zc.y(e.getY()));
		zc.repaint();
	}

	@Override
	public void mouseReleased(final MouseEvent e, final ZirkelCanvas zc) {
		if (!Dragging)
			return;
		Dragging = false;
		O.edit(zc, true, true);
	}

	@Override
	public boolean waitForPoint() {
		return false;
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		zc.showStatus(Global.name("message.expression",
		"Expression: Choose a place!"));
	}

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "Expression"))
			return false;
		final XmlTag tag = tree.getTag();
		if (!tag.hasParam("x") || !tag.hasParam("y"))
			throw new ConstructionException("Expression coordinates missing!");
		if (!tag.hasParam("value"))
			throw new ConstructionException(Global.name("exception.expression"));
		final ExpressionObject p = new ExpressionObject(c, 0, 0);
		double x, y;
		try {
			x = new Expression(tag.getValue("x"), c, p).getValue();
			y = new Expression(tag.getValue("y"), c, p).getValue();
			p.move(x, y);
		} catch (final Exception e) {
		}
		p.setDefaults();
		if (tag.hasParam("prompt"))
			p.setPrompt(tag.getValue("prompt"));
		if (tag.hasParam("fixed")) {
			p.setFixed(tag.getValue("x"), tag.getValue("y"));
		}
		p.setShowValue(tag.hasParam("showvalue"));
		setName(tag, p);
		set(tree, p);
		c.add(p);
		try {
			p.setExpression(tag.getValue("value"), c);
		} catch (final Exception e) {
			throw new ConstructionException(Global.name("exception.expression"));
		}
		setConditionals(tree, c, p);
		if (tag.hasTrueParam("slider")) {
			try {
				p.setSlider(tag.getValue("min"), tag.getValue("max"));
			} catch (final Exception e) {
				throw new ConstructionException(Global.name("exception.expression"));
			}
		}
		return true;
	}

	@Override
	public String getTag() {
		return "Expression";
	}

	@Override
	public void construct(final Construction c, final String name,
			final String params[], final int nparams)
	throws ConstructionException {
		if (nparams == 1) {
			final ExpressionObject o = new ExpressionObject(c, c.getX()
					+ (Math.random() - 0.5) * c.getW(), c.getY()
					+ (Math.random() - 0.5) * c.getW());
			if (!name.equals(""))
				o.setNameCheck(name);
			c.add(o);
			o.setDefaults();
			try {
				o.setExpression(params[0], c);
			} catch (final Exception e) {
				throw new ConstructionException(Global.name("exception.expression"));
			}
		} else
			throw new ConstructionException(Global.name("exception.nparams"));
	}

	@Override
	public boolean useSmartBoard() {
		return false;
	}

}