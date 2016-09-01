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
 
 
 package rene.zirkel.tools;

// file: Hider.java

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.MouseEvent;

import eric.JEricPanel;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.gui.TextFieldAction;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;

class ReorderDialog extends CloseDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String Name = "";
	boolean Abort = true;
	TextField Input;

	public ReorderDialog(final ZirkelCanvas zc, final ConstructionObject o) {
		super(zc.getFrame(), Global.name("reorder.title"), true);
		setLayout(new BorderLayout());
		final JEricPanel north = new MyPanel();
		north.setLayout(new GridLayout(1, 0));
		north.add(new MyLabel(o.getName() + " : "
				+ Global.name("reorder.message")));
		final ConstructionObject ol = zc.getConstruction().lastDep(o);
		String s = "";
		if (ol != null)
			s = ol.getName();
		north.add(Input = new TextFieldAction(this, "Reorder", s));
		add("North", new Panel3D(north));
		final JEricPanel south = new MyPanel();
		south.add(new ButtonAction(this, Global.name("ok"), "OK"));
		south.add(new ButtonAction(this, Global.name("abort"), "Close"));
		add("South", south);
		pack();
		center(zc.getFrame());
		setVisible(true);
	}

	@Override
	public void doAction(final String o) {
		if (o.equals("OK")) {
			Abort = false;
			Name = Input.getText();
			doclose();
		} else
			super.doAction(o);
	}

	public String getResult() {
		return Name;
	}

	@Override
	public boolean isAborted() {
		return Abort;
	}
}

public class ReorderTool extends ObjectConstructor {
	@Override
	public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
		zc.y(e.getY());
		final ConstructionObject o = zc.selectObject(e.getX(), e.getY());
		if (o == null)
			return;
		final ReorderDialog d = new ReorderDialog(zc, o);
		if (!d.isAborted()) {
			final String name = d.getResult();
			if (!name.equals("")) {
				final ConstructionObject u = zc.getConstruction().find(name);
				if (u == null) {
					zc.warning(Global.name("reorder.notfound"));
					return;
				}
				if (!zc.getConstruction().reorder(o, u))
					zc.warning(Global.name("reorder.warning"));
			} else if (!zc.getConstruction().reorder(o, null))
				zc.warning(Global.name("reorder.warning"));
		}
		zc.repaint();
	}

	@Override
	public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
			final boolean simple) {
		zc.indicateObjects(e.getX(), e.getY());
	}

	@Override
	public void showStatus(final ZirkelCanvas zc) {
		zc.showStatus(Global.name("message.reorder",
		"Reorder: Select an object!"));
	}

	@Override
	public boolean useSmartBoard() {
		return false;
	}
}
