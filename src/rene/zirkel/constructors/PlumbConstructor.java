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

// file: PlumbConstructor.java

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PlumbObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveLineObject;

public class PlumbConstructor extends ParallelConstructor {
	@Override
	public PrimitiveLineObject create(final Construction c,
			final PrimitiveLineObject l, final PointObject p) {
		return new PlumbObject(c, l, p);
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		if (L == null)
			zc.showStatus(Global.name("message.plumb.first",
			"Plumb Line: Choose a line!"));
		else
			zc.showStatus(Global.name("message.plumb.second",
			"Plumb Line: Choose a Point!"));
	}

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "Plumb"))
			return false;
		final XmlTag tag = tree.getTag();
		if (!tag.hasParam("point") || !tag.hasParam("line"))
			throw new ConstructionException("Plumb parameters missing!");
		try {
			final PointObject p1 = (PointObject) c.find(tag.getValue("point"));
			final PrimitiveLineObject p2 = (PrimitiveLineObject) c.find(tag
					.getValue("line"));
			final PlumbObject o = new PlumbObject(c, p2, p1);
			if (tag.hasParam("valid"))
				o.setRestricted(false);
			setName(tag, o);
			set(tree, o);
			if (tag.hasParam("partial"))
				o.setPartial(true);
			c.add(o);
			setConditionals(tree, c, o);
		} catch (final ConstructionException e) {
			throw e;
		} catch (final Exception e) {
			throw new ConstructionException("Plumb parameters illegal!");
		}
		return true;
	}

	@Override
	public String getPrompt() {
		return Global.name("prompt.plumb");
	}

	@Override
	public String getTag() {
		return "Plumb";
	}

	@Override
	public void construct(final Construction c, final String name,
			final String params[], final int nparams)
	throws ConstructionException {
		if (nparams != 2)
			throw new ConstructionException(Global.name("exception.nparams"));
		final ConstructionObject P1 = c.find(params[0]);
		if (P1 == null)
			throw new ConstructionException(Global.name("exception.notfound")
					+ " " + params[0]);
		final ConstructionObject P2 = c.find(params[1]);
		if (P2 == null)
			throw new ConstructionException(Global.name("exception.notfound")
					+ " " + params[1]);
		if (!(P1 instanceof PrimitiveLineObject))
			throw new ConstructionException(Global.name("exception.type") + " "
					+ params[0]);
		if (!(P2 instanceof PointObject))
			throw new ConstructionException(Global.name("exception.type") + " "
					+ params[1]);
		final PlumbObject s = new PlumbObject(c, (PrimitiveLineObject) P1,
				(PointObject) P2);
		if (!name.equals(""))
			s.setNameCheck(name);
		c.add(s);
		s.setDefaults();
	}

}
