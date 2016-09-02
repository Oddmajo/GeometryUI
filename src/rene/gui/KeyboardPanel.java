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

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.Vector;

import eric.JEricPanel;

import rene.dialogs.ItemEditor;
import rene.dialogs.ItemEditorElement;
import rene.dialogs.ItemPanel;

/**
 * This is used as the display panel for the keyboard editor. It displays
 * information about the selected keyboard item.
 */

public class KeyboardPanel extends ItemPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextField MenuString, ActionName, CharKey;
	Checkbox Shift, Control, Alt;
	String Name = "";
	Choice C;
	ItemEditor E;

	public KeyboardPanel() {
		setLayout(new BorderLayout());

		final JEricPanel center = new JEricPanel();
		center.setLayout(new GridLayout(0, 1));
		// the menu item
		center.add(MenuString = new MyTextField("", 30));
		MenuString.setEditable(false);
		// the description
		center.add(ActionName = new MyTextField());
		ActionName.setEditable(false);
		// the key
		center.add(CharKey = new MyTextField());
		CharKey.setEditable(false);
		CharKey.addKeyListener(this);
		// modifiers
		center.add(Shift = new Checkbox(Global.name("keyeditor.shift")));
		center.add(Control = new Checkbox(Global.name("keyeditor.control")));
		center.add(Alt = new Checkbox(Global.name("keyeditor.alt")));
		add("Center", center);

		final JEricPanel south = new JEricPanel();
		south.setLayout(new BorderLayout());
		final JEricPanel c = new JEricPanel();
		// the choice of command keys
		C = new Choice();
		if (Global.NormalFont != null)
			C.setFont(Global.NormalFont);
		c.add(C);
		C.add("-------------");
		south.add("Center", c);
		// default and undefine buttons
		final JEricPanel buttons = new JEricPanel();
		buttons.add(new ButtonAction(this, Global.name("keyeditor.default"),
		"Default"));
		buttons.add(new ButtonAction(this, Global.name("keyeditor.none"),
		"None"));
		south.add("South", buttons);
		add("South", south);
	}

	public void setItemEditor(final ItemEditor e) {
		E = e;
	}

	/**
	 * Build a list of available command keys.
	 */
	public void makeCommandChoice() {
		C.removeAll();
		C.add("");
		for (int i = 1; i <= 5; i++) {
			final String s = commandShortcut(i);
			C.add(i + ": " + s);
		}
		C.select(0);
	}

	/**
	 * The the command shortcut number i.
	 */
	public String commandShortcut(final int i) {
		final String s = "command." + i;
		final Enumeration e = E.getElements().elements();
		while (e.hasMoreElements()) {
			final KeyboardItem k = (KeyboardItem) e.nextElement();
			if (k.getMenuString().equals(s)) {
				return k.shortcut();
			}
		}
		return "";
	}

	/**
	 * Set the key, if one is pressed inside the CharKey textfield.
	 */
	public void keyPressed(final KeyEvent e) {
		Shift.setState(e.isShiftDown());
		Control.setState(e.isControlDown());
		Alt.setState(e.isAltDown());
		CharKey.setText(KeyDictionary.translate(e.getKeyCode()).toLowerCase());
		C.select(0);
	}

	public void keyTyped(final KeyEvent e) {
	}

	public void keyReleased(final KeyEvent e) {
	}

	/*
	 * Override methods of ItemPanel
	 */

	/**
	 * Display this element on the panel.
	 */
	@Override
	public void display(final ItemEditorElement e) {
		final KeyboardItem k = (KeyboardItem) e;
		Name = k.getName();
		MenuString.setText(k.getMenuString());
		ActionName.setText(k.getActionName());
		CharKey.setText(k.getCharKey());
		MenuString.setText(k.getMenuString());
		Shift.setState(k.isShift());
		Control.setState(k.isControl());
		Alt.setState(k.isAlt());
		C.select(k.getCommandType());
	}

	/**
	 * Create a new keyboard element from the panel entries.
	 */
	@Override
	public ItemEditorElement getElement() {
		final int type = C.getSelectedIndex();
		return new KeyboardItem(CharKey.getText(), MenuString.getText(),
				ActionName.getText(), Shift.getState(), Control.getState(), Alt
				.getState(), type);
	}

	@Override
	public String getName() {
		return Name;
	}

	@Override
	public void setName(final String s) {
		Name = s;
		MenuString.setText(s);
	}

	/**
	 * Test on doublicate keys, and undefine them.
	 */
	@Override
	public void notifyChange(final Vector v, final int item) {
		final KeyboardItem changed = (KeyboardItem) v.elementAt(item);
		final String descr = changed.keyDescription();
		for (int i = 0; i < v.size(); i++) {
			if (i == item)
				continue;
			final KeyboardItem k = (KeyboardItem) v.elementAt(i);
			if (k.keyDescription().equals(descr)) {
				v.setElementAt(new KeyboardItem(k.getMenuString(), "none"), i);
			}
		}
		if (changed.getMenuString().startsWith("command.")) {
			makeCommandChoice();
		}
	}

	/**
	 * React on the Default and None buttons.
	 */
	@Override
	public void doAction(final String o) {
		if (o.equals("Default")) {
			final String s = MenuString.getText();
			final KeyboardItem k = new KeyboardItem(s, Global.name("key." + s));
			CharKey.setText(k.getCharKey());
			Shift.setState(k.isShift());
			Control.setState(k.isControl());
			Alt.setState(k.isAlt());
		} else if (o.equals("None")) {
			CharKey.setText("none");
			Shift.setState(false);
			Control.setState(false);
			Alt.setState(false);
		} else
			super.doAction(o);
	}

	/**
	 * User wishes to clear all keyboard definitions.
	 */
	@Override
	public boolean extra(final Vector v) {
		v.removeAllElements();
		return true;
	}
}
