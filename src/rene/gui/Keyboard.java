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

/*
 This file contains a keyboard translater. It translates key strokes
 into text strings. The strings are menu descriptions and key
 descriptions. Menu descriptions are used to call menu entries in
 EditorFrame, and key descriptions are used in TextDisplay.
 <p>
 JE supports up to 5 command keys, which may be prepended to other
 keys. Those keys are mapped to command.X, where X is from 1 to 5.
 There is also a special escape command key, mapped to command.escape.
 <p>
 Some strings are contained in the properties, others may be defined by
 the user, and are contained in the parameter file "je.cfg". There is
 also an editor for the keystrokes, which uses the ItemEditor dialog.
 */

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import rene.dialogs.ItemEditor;
import rene.dialogs.MyFileDialog;
import rene.util.sort.Sorter;

/**
 * A static class, which contains tranlations for key events. It keeps the
 * translations in a vector of KeyboardItem.
 * <p>
 * The class will statically generate the list of translations from the default
 * local properties and the JE configuration.
 */

public class Keyboard {
	static Vector V;
	static Hashtable Hmenu, Hcharkey;

	static {
		makeKeys();
	}

	/**
	 * Read the keys from the Global names and parameters, and put into a vector
	 * and two hash tables for easy access.
	 */
	public static void makeKeys() {
		V = new Vector();
		Hmenu = new Hashtable();
		Hcharkey = new Hashtable();
		// collect all predefined keys
		Enumeration e = Global.names();
		if (e == null)
			return;
		while (e.hasMoreElements()) {
			final String key = (String) e.nextElement();
			if (key.startsWith("key.")) {
				final String menu = key.substring(4);
				String charkey = Global.getParameter(key, "default");
				final String normalcharkey = Global.name(key);
				if (charkey.equals("default"))
					charkey = normalcharkey;
				final KeyboardItem k = new KeyboardItem(menu, charkey);
				V.addElement(k);
				Hmenu.put(menu, k);
				Hcharkey.put(charkey, k);
			}
		}
		// collect all user defined (double defined) keys
		e = Global.properties();
		while (e.hasMoreElements()) {
			final String key = (String) e.nextElement();
			if (key.startsWith("key.")) {
				final String menu = key.substring(4);
				if (findMenu(menu) != null)
					continue;
				final String charkey = Global.getParameter(key, "default");
				if (charkey.equals("default"))
					continue;
				final KeyboardItem k = new KeyboardItem(menu, charkey);
				V.addElement(k);
				Hmenu.put(menu, k);
				Hcharkey.put(charkey, k);
			}
		}
	}

	/**
	 * Find a menu string in the key definitions
	 */
	public static KeyboardItem findMenu(final String menu) {
		final Object o = Hmenu.get(menu);
		if (o == null)
			return null;
		else
			return (KeyboardItem) o;
	}

	/**
	 * Generate a shortcut for the menu item.
	 */
	public static String shortcut(final String tag) {
		final Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			final KeyboardItem item = (KeyboardItem) e.nextElement();
			if (item.getMenuString().equals(tag)) {
				String shortcut = item.shortcut();
				if (!shortcut.equals(""))
					shortcut = " (" + shortcut + ")";
				return shortcut;
			}
		}
		return "";
	}

	/**
	 * See, if the key event matches any of my translations, and get the menu
	 * entry.
	 */
	public static String findKey(final KeyEvent event, final int type) {
		final Object o = Hcharkey.get(toCharKey(event, type));
		if (o == null)
			return "";
		String s = ((KeyboardItem) o).getMenuString();
		while (s.endsWith("*"))
			s = s.substring(0, s.length() - 1);
		return s;
	}

	/**
	 * Make a keychar string from the event.
	 */
	public static String toCharKey(final KeyEvent e, final int type) {
		String s = "";
		if (type > 0)
			s = s + "esc" + type + ".";
		if (e.isShiftDown())
			s = s + "shift.";
		if (e.isControlDown())
			s = s + "control.";
		if (e.isAltDown())
			s = s + "alt.";
		return s + KeyDictionary.translate(e.getKeyCode()).toLowerCase();
	}

	/**
	 * Edit the translations.
	 */
	public static void edit(final Frame f) {
		final KeyboardItem keys[] = new KeyboardItem[V.size()];
		V.copyInto(keys);
		Sorter.sort(keys);
		final Vector v = new Vector();
		for (final KeyboardItem key2 : keys)
			v.addElement(key2);
		final KeyboardPanel p = new KeyboardPanel();
		final ItemEditor d = new ItemEditor(f, p, v, "keyeditor", Global
				.name("keyeditor.prompt"), true, false, true, Global
				.name("keyeditor.clearall"));
		p.setItemEditor(d);
		p.makeCommandChoice();
		d.center(f);
		d.setVisible(true);
		if (d.isAborted())
			return;
		Global.removeAllParameters("key.");
		V = d.getElements();
		Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			final KeyboardItem k = (KeyboardItem) e.nextElement();
			if (!k.getCharKey().equals("default")) {
				final String keytag = "key." + k.getMenuString();
				final String description = k.keyDescription();
				if (!Global.name(keytag).toLowerCase().equals(description)) {
					Global.setParameter(keytag, description);
				}
			}
		}
		makeKeys();
		if (d.getAction() == ItemEditor.SAVE) {
			final Properties parameters = new Properties();
			e = Global.properties();
			while (e.hasMoreElements()) {
				final String key = (String) e.nextElement();
				if (key.startsWith("key."))
					parameters.put(key, Global.getParameter(key, "default"));
			}
			final MyFileDialog save = new MyFileDialog(f, Global.name("save"),
					Global.name("save"), true);
			save.setPattern("*.keys");
			save.center(f);
			save.update();
			save.setVisible(true);
			if (save.isAborted())
				return;
			final String filename = save.getFilePath();
			if (filename.equals(""))
				return; // aborted dialog!
			try {
				final FileOutputStream o = new FileOutputStream(filename);
				parameters.store(o, "JE Keyboard Definition");
				// parameters.save(o,"JE Keyboard Definition");
			} catch (final Exception ex) {
			}
		} else if (d.getAction() == ItemEditor.LOAD) {
			final Properties parameters = new Properties();
			final MyFileDialog load = new MyFileDialog(f, Global.name("load"),
					Global.name("load"), true);
			load.setPattern("*.keys");
			load.center(f);
			load.update();
			load.setVisible(true);
			if (load.isAborted())
				return;
			final String filename = load.getFilePath();
			if (filename.equals(""))
				return; // aborted dialog!
			try {
				final FileInputStream in = new FileInputStream(filename);
				parameters.load(in);
			} catch (final Exception ex) {
			}
			Global.removeAllParameters("key.");
			e = parameters.keys();
			while (e.hasMoreElements()) {
				final String key = (String) e.nextElement();
				Global.setParameter(key, (String) parameters.get(key));
			}
			makeKeys();
		}
	}

	/**
	 * Append a list of keyboard shortcuts to a text area.
	 */
	public static Vector getKeys() {
		final Vector keys = new Vector();
		Sorter.sort(V);
		final Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			final KeyboardItem k = (KeyboardItem) e.nextElement();
			if (!k.getCharKey().equals("none")) {
				String shortcut = k.shortcut();
				final int n = shortcut.length();
				for (int i = 0; i < 30 - n; i++)
					shortcut = shortcut + " ";
				keys.addElement(shortcut + " = " + k.getActionName());
			}
		}
		return keys;
	}

	/**
	 * Find a shortcut for the command.
	 */
	public static String commandShortcut(final int type) {
		final Object o = Hmenu.get("command." + type);
		if (o == null)
			return "";
		return ((KeyboardItem) o).shortcut();
	}
}
