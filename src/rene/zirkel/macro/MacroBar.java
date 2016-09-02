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
 
 
 package rene.zirkel.macro;

import java.awt.Frame;
import java.util.Enumeration;
import java.util.Vector;

import rene.gui.Global;
import rene.gui.IconBar;

class MacroBarElement {
	public MacroItem M;
	public String Name;

	public MacroBarElement(final MacroItem m, final String name) {
		M = m;
		Name = name;
	}
}

public class MacroBar extends IconBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector V = new Vector();

	public MacroBar(final Frame f) {
		super(f, false);
		Resource = "/icons/";
	}

	public void update(final Vector macros) {
		removeAll();
		V.removeAllElements();
		final Enumeration e = macros.elements();
		while (e.hasMoreElements()) {
			final MacroItem m = (MacroItem) e.nextElement();
			final String name = m.Name;
			if (name.endsWith(")")
					&& (!m.M.isProtected() || Global.getParameter(
							"defaultmacrosinbar", true))) {
				addToggleLeft(name);
				V.addElement(new MacroBarElement(m, name));
			}
		}
		doLayout();
		forceRepaint();
	}

	public void deselectAll() {
		final Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			final MacroBarElement me = (MacroBarElement) e.nextElement();
			setState(me.Name, false);
		}
	}

	public Macro find(final String o) {
		final Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			final MacroBarElement me = (MacroBarElement) e.nextElement();
			if (me.Name.equals(o)) {
				return me.M.M;
			}
		}
		return null;
	}

	public void select(final Macro m) {
		final Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			final MacroBarElement me = (MacroBarElement) e.nextElement();
			if (me.M.M == m) {
				setState(me.Name, true);
			}
		}
	}

	@Override
	public String getHelp(final String name) {
		return name;
	}
}
