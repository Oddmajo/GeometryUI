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
 
 
 package rene.dialogs;

import java.util.Vector;

import eric.JEricPanel;

import rene.gui.DoActionListener;

public class ItemPanel extends JEricPanel implements DoActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void display(final ItemEditorElement e) {
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void setName(final String name) {
	}

	public ItemEditorElement getElement() {
		return null;
	}

	public void newElement() {
	}

	public void help() {
	}

	public void doAction(final String o) {
	}

	public void itemAction(final String o, final boolean flag) {
	}

	/**
	 * Called, whenever an item is redefined.
	 * 
	 * @param v
	 *            The vector of KeyboardItem.
	 * @param item
	 *            The currently changed item number.
	 */
	public void notifyChange(final Vector v, final int item) {
	}

	/**
	 * Called, when the extra Button was pressed.
	 * 
	 * @v The vector of KeyboardItem.
	 * @return If the panel should be closed immediately.
	 */
	public boolean extra(final Vector v) {
		return false;
	}
}
