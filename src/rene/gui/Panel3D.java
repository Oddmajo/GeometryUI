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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import eric.JEricPanel;

/**
 * Panel3D extends the JPanel class with a 3D look.
 */

public class Panel3D extends JEricPanel implements LayoutManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Component C;

	/**
	 * Adds the component to the panel. This component is resized to leave 5
	 * pixel on each side.
	 */
	public Panel3D(final Component c) {
		C = c;
		setLayout(this);
		add(C);
		setBackground(C.getBackground());
	}

	public Panel3D(final Component c, final Color background) {
		C = c;
		setLayout(this);
		add(C);
		setBackground(background);
	}

//	@Override
//	public void paintComponent(final Graphics g) {
//		g.setColor(getBackground());
//		if (getSize().width > 0 && getSize().height > 0)
//			g.fill3DRect(0, 0, getSize().width, getSize().height, true);
//		// C.repaint(); // probably not necessary, but Mac OSX bug ?!?
//	}

	public void addLayoutComponent(final String arg0, final Component arg1) {
		C = arg1;
	}

	public void removeLayoutComponent(final Component arg0) {
		C = null;
	}

	public Dimension preferredLayoutSize(final Container arg0) {
		if (C != null)
			return new Dimension(C.getPreferredSize().width + 10, C
					.getPreferredSize().height + 10);
		return new Dimension(10, 10);
	}

	public Dimension minimumLayoutSize(final Container arg0) {
		if (C != null)
			return new Dimension(C.getMinimumSize().width + 10, C
					.getMinimumSize().height + 10);
		return new Dimension(10, 10);
	}

	public void layoutContainer(final Container arg0) {
		if (C == null)
			return;
		C.setLocation(5, 5);
		C.setSize(getSize().width - 10, getSize().height - 10);
	}
}
