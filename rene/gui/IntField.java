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
 
 
 package rene.gui;

/**
 * A TextField, which holds an integer number with minimal and maximal range.
 */

public class IntField extends TextFieldAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntField(final DoActionListener l, final String name, final int v) {
		super(l, name, "" + v);
	}

	public IntField(final DoActionListener l, final String name, final int v,
			final int cols) {
		super(l, name, "" + v, cols);
	}

	public int value() {
		try {
			return Integer.parseInt(getText());
		} catch (final NumberFormatException e) {
			setText("" + 0);
			return 0;
		}
	}

	public int value(final int min, final int max) {
		int n;
		try {
			n = Integer.parseInt(getText());
		} catch (final NumberFormatException e) {
			setText("" + min);
			return min;
		}
		if (n < min) {
			n = min;
			setText("" + min);
		}
		if (n > max) {
			n = max;
			setText("" + max);
		}
		return n;
	}

	public void set(final int v) {
		setText("" + v);
	}

	public boolean valid() {
		try {
			Integer.parseInt(getText());
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}
}
