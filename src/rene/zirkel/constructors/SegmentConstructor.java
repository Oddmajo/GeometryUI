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

// file: SegmentConstructor.java

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.SegmentObject;

public class SegmentConstructor extends LineConstructor {
	boolean Fixed = false;

	public SegmentConstructor() {
		this(false);
	}

	public SegmentConstructor(final boolean fixed) {
		Fixed = fixed;
	}

	@Override
	public ConstructionObject create(final Construction c,
			final PointObject p1, final PointObject p2) {
		return new SegmentObject(c, p1, p2);
	}

	@Override
	public boolean isFixed() {
		return Fixed;
	}

	@Override
	public void setFixed(final ZirkelCanvas zc, final ConstructionObject o) {
		if (o instanceof SegmentObject) {
			final SegmentObject s = (SegmentObject) o;
			if (s.canFix())
				try {
					s.validate();
					s.setFixed(true, "" + s.getLength());
					s.edit(zc, true, true);
					s.validate();
					zc.repaint();
				} catch (final Exception e) {
				}
				else {
					zc.warning(Global.name("error.fixedsegment"));
				}
		}
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		if (!Fixed) {
			if (P1 == null)
				zc.showStatus(Global.name("message.segment.first"));
			else
				zc.showStatus(Global.name("message.segment.second"));
		} else {
			if (P1 == null)
				zc.showStatus(Global.name("message.fixedsegment.first"));
			else
				zc.showStatus(Global.name("message.fixedsegment.second"));
		}
	}

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "Segment"))
			return false;
		final XmlTag tag = tree.getTag();
		if(tag.hasParam("arrow"))
		    return false; //parceque VectorConstructor est appelé après dans l'ordre chronologique
		if (!tag.hasParam("from") || !tag.hasParam("to"))
			throw new ConstructionException("Segment endpoints missing!");
		try {
			final PointObject p1 = (PointObject) c.find(tag.getValue("from"));
			final PointObject p2 = (PointObject) c.find(tag.getValue("to"));
			final SegmentObject o = new SegmentObject(c, p1, p2);
			setName(tag, o);
			set(tree, o);
			c.add(o);
			setConditionals(tree, c, o);
			//o.setArrow(tag.hasParam("arrow")); plus nécessaire
			if (tag.hasParam("fixed")) {
				try {
					o.setFixed(true, tag.getValue("fixed"));
				} catch (final Exception e) {
					throw new ConstructionException("Fixed value illegal!");
				}
			}
			if (tag.hasParam("code_symbol"))
				o.setSegmentCode(Integer.parseInt(tag.getValue("code_symbol")));
		} catch (final ConstructionException e) {
			throw e;
		} catch (final Exception e) {
			throw new ConstructionException("Segment endpoints illegal!");
		}
		return true;
	}

	@Override
	public String getPrompt() {
		return Global.name("prompt.segment");
	}

	@Override
	public String getTag() {
		return "Segment";
	}

	@Override
	public void construct(final Construction c, final String name,
			final String params[], final int nparams)
	throws ConstructionException {

		if (nparams != 2 && nparams != 3)
			throw new ConstructionException(Global.name("exception.nparams"));
		final ConstructionObject P1 = c.find(params[0]);
		if (P1 == null)
			throw new ConstructionException(Global.name("exception.notfound")
					+ " " + params[0]);
		if (!(P1 instanceof PointObject))
			throw new ConstructionException(Global.name("exception.type") + " "
					+ params[0]);
		ConstructionObject P2 = c.find(params[1]);
		if (P2 == null) {
			final Expression ex = new Expression(params[1], c, null);
			if (!ex.isValid())
				throw new ConstructionException(Global.name("exception.expression"));
			final double x = ex.getValue();
			P2 = new PointObject(c, ((PointObject) P1).getX() + x,
					((PointObject) P1).getY());
			c.add(P2);
			P2.setDefaults();
			final SegmentObject s = new SegmentObject(c, (PointObject) P1,
					(PointObject) P2);
			s.setDefaults();
			s.setFixed(true, params[1]);
			c.add(s);
			if (!name.equals(""))
				s.setNameCheck(name);
			return;
		}
		if (!(P2 instanceof PointObject))
			throw new ConstructionException(Global.name("exception.type") + " "
					+ params[1]);
		final SegmentObject s = new SegmentObject(c, (PointObject) P1,
				(PointObject) P2);
		if (nparams == 3) {
			if (!s.canFix())
				throw new ConstructionException(Global.name("exception.canfix"));
			s.setFixed(true, params[2]);
			if (!s.isValidFix())
				throw new ConstructionException(Global.name("exception.fix")
						+ " " + params[2]);
			s.validate();
		}
		c.add(s);
		s.setDefaults();
//                s.setArrow(c.isVector());
		if (!name.equals(""))
			s.setNameCheck(name);
	}
}
