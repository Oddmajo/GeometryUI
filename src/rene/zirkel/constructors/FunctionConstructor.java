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

import rene.gui.Global;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.UserFunctionObject;

public class FunctionConstructor extends ObjectConstructor {

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "Function")) {
		    return false;
		}
		final XmlTag tag = tree.getTag();
		if (tag.hasParam("f")) {// function
			if (!tag.hasParam("var")) {
			    throw new ConstructionException("Function invalid!");
			}
			try {
				final String y = tag.getValue("f");
				final String var = tag.getValue("var");
				final UserFunctionObject f = new UserFunctionObject(c);
				f.setDefaults();
				setName(tag, f);
				set(tree, f);
				c.add(f);
				double xpos, ypos;
				try {
					if (tag.hasParam("fixed")) {
						f.setFixed(tag.getValue("x"), tag.getValue("y"));
					} else {
						xpos = new Expression(tag.getValue("x"), c, f)
						.getValue();
						ypos = new Expression(tag.getValue("y"), c, f)
						.getValue();
						f.move(xpos, ypos);
					}
				} catch (final Exception e) {
				}
				if (tag.hasParam("filled")) {
				    f.setFilled(true);
				}
				f.setExpressions(var, y);
				setConditionals(tree, c, f);
				f.updateText();
				return true;
			} catch (final Exception e) {
				throw new ConstructionException("Function invalid!");
			}
		} else { // curve
			if (!tag.hasParam("min") && !tag.hasParam("max")
					&& !tag.hasParam("d") && !tag.hasParam("var")
					&& !tag.hasParam("x") && !tag.hasParam("y")) {
				throw new ConstructionException("Function invalid!");
			}
			try {
				final String x = tag.getValue("x");
				final String y = tag.getValue("y");
				final String var = tag.getValue("var");
				final String d = tag.getValue("d");
				final String min = tag.getValue("min");
				final String max = tag.getValue("max");
				final FunctionObject f = new FunctionObject(c);
				f.setDefaults();
				setType(tag, f);
				setName(tag, f);
				set(tree, f);
				c.add(f);
				if (tag.hasParam("filled")) {
				    f.setFilled(true);
				} else {
				    f.setFilled(false);
				}
				f.setExpressions(var, x, y);
				f.setRange(min, max, d);
				f.setSpecial(tag.hasTrueParam("special"));
				if(tag.hasParam("minbound")) {
				    f.setMinOpen(tag.getValue("minbound").equals("0"));
				    f.setMinClosed(tag.getValue("minbound").equals("1"));
				}
				if(tag.hasParam("maxbound")) {
				    f.setMaxOpen(tag.getValue("maxbound").equals("0"));
				    f.setMaxClosed(tag.getValue("maxbound").equals("1"));
				}

				setConditionals(tree, c, f);
				if (tag.hasParam("center")) {
				    f.setCenter(tag.getValue("center"));
				}
				f.updateText();
				return true;
			} catch (final Exception e) {
				throw new ConstructionException("Function invalid!");
			}
		}
	}

	static public void setType(final XmlTag tag, final FunctionObject p) {
		if (tag.hasParam("shape")) {
			final String s = tag.getValue("shape");
			if (s.equals("square"))
				p.setType(0);
			if (s.equals("diamond"))
				p.setType(1);
			if (s.equals("circle"))
				p.setType(2);
			if (s.equals("dot"))
				p.setType(3);
			if (s.equals("cross"))
				p.setType(4);
			if (s.equals("dcross"))
				p.setType(5);
		}
	}

	@Override
	public String getTag() {
		return "Function";
	}

	@Override
	public void construct(final Construction c, final String name,
			final String params[], final int nparams)
	throws ConstructionException {
		if (nparams != 6)
			throw new ConstructionException(Global.name("exception.nparams"));
		boolean added = false;
		try {
			final FunctionObject F = new FunctionObject(c);
			c.add(F);
			added = true;
			if (!name.equals(""))
				F.setNameCheck(name);
			F.setRange(params[0], params[1], params[2]);
			F.setExpressions(params[3], params[4], params[5]);
			F.setDefaults();
		} catch (final ConstructionException e) {
			if (added)
				c.back();
			throw e;
		} catch (final Exception e) {
			if (added)
				c.back();
			throw new ConstructionException("Function Invalid!");
		}
	}
}
