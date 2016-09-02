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

// file: MidpointConstructor.java

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.MidpointObject;
import rene.zirkel.objects.PointObject;

public class MidpointConstructor extends LineConstructor {

	@Override
	public ConstructionObject create(final Construction c,
			final PointObject p1, final PointObject p2) {
		return new MidpointObject(c, p1, p2);
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		if (P1 == null)
			zc.showStatus(Global.name("message.midpoint.first",
			"Midpoint: Set the first point!"));
		else
			zc.showStatus(Global.name("message.midpoint.second",
			"Midpoint: Set the second point!"));
	}

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "Midpoint"))
			return false;
		final XmlTag tag = tree.getTag();
		if (!tag.hasParam("first") || !tag.hasParam("second"))
			throw new ConstructionException("Line points missing!");
		try {
			final PointObject p1 = (PointObject) c.find(tag.getValue("first"));
			final PointObject p2 = (PointObject) c.find(tag.getValue("second"));
			final MidpointObject p = new MidpointObject(c, p1, p2);
			PointConstructor.setType(tag, p);
			setName(tag, p);
			set(tree, p);
			c.add(p);
			setConditionals(tree, c, p);
		} catch (final ConstructionException e) {
			throw e;
		} catch (final Exception e) {
			throw new ConstructionException("Midpoint points illegal!");
		}
		return true;
	}

	@Override
	public String getPrompt() {
		return Global.name("prompt.midpoint");
	}

	@Override
	public String getTag() {
		return "Midpoint";
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
		if (!(P1 instanceof PointObject))
			throw new ConstructionException(Global.name("exception.type") + " "
					+ params[0]);
		final ConstructionObject P2 = c.find(params[1]);
		if (P2 == null)
			throw new ConstructionException(Global.name("exception.notfound")
					+ " " + params[1]);
		if (!(P2 instanceof PointObject))
			throw new ConstructionException(Global.name("exception.type") + " "
					+ params[1]);
		final MidpointObject s = new MidpointObject(c, (PointObject) P1,
				(PointObject) P2);
		if (!name.equals(""))
			s.setNameCheck(name);
		c.add(s);
		s.setDefaults();
	}

}
