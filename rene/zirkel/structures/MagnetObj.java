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
 
 
 package rene.zirkel.structures;

import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;

/**
 * 
 * @author erichake
 */
public class MagnetObj {

	private final ConstructionObject obj;
	// private int ray = -1;
	private Expression rayexp = null;

	// public MagnetObj(ConstructionObject o, int i) {
	// obj = o;
	// ray = i;
	// rayexp=new Expression(""+i, o.getConstruction(), o);
	// }

	public MagnetObj(final ConstructionObject o, final String s) {
		obj = o;
		rayexp = new Expression(s, o.getConstruction(), o);
	}

	public void setSelected(final boolean b) {
		obj.setSelected(b);
	}

	public void setStrongSelected(final boolean b) {
		obj.setStrongSelected(b);
	}

	public boolean isInConstruction() {
		return obj.isInConstruction();
	}

	public ConstructionObject obj() {
		return obj;
	}

	public String name() {
		return obj.getName();
	}

	public String rayexp() {
		return rayexp.toString();
	}

	public void translate() {
		rayexp.translate();
	}

	public int ray() {
		int i = Integer.MIN_VALUE;
		try {
			i = (int) Math.round(rayexp.getValue());
		} catch (final Exception ex) {
		}
		return i;
	}
}
