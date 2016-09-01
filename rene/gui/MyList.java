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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyList extends java.awt.List implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyList(final int n) {
		super(n);
		if (Global.NormalFont != null)
			setFont(Global.NormalFont);
		addKeyListener(this);
	}

	public void keyPressed(final KeyEvent e) {
	}

	public void keyReleased(final KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			processActionEvent(new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED, ""));
		}
	}

	public void keyTyped(final KeyEvent e) {
	}
}
