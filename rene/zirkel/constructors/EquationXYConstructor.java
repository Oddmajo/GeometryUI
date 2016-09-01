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

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.EquationXYObject;

/**
 * 
 * @author erichake
 */
public class EquationXYConstructor extends ObjectConstructor {

	@Override
	public boolean construct(final XmlTree tree, final Construction c)
	throws ConstructionException {
		if (!testTree(tree, "EqXY")) {
			return false;
		}
		final XmlTag tag = tree.getTag();
		if (tag.hasParam("f")) // function
		{
			final EquationXYObject eq = new EquationXYObject(c, tag
					.getValue("f"), Integer.parseInt(tag.getValue("Dhor")));
			eq.setName(tag.getValue("name"));

			// eq.setDefaults();
			set(tree, eq);
			c.add(eq);
			eq.updateText();
                        setConditionals(tree,c,eq);
		}
		return true;
	}
}
