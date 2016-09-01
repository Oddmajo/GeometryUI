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
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import rene.util.FileName;
import rene.util.list.ListClass;
import rene.util.list.ListElement;

/**
 * A TextField, which display the old input, when cursor up is pressed. The old
 * input is stored in a list. The class is derived from TextFieldAction.
 * 
 * @see TextFieldAction
 */

public class HistoryTextField extends TextFieldAction implements KeyListener,
DoActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ListClass H;
	PopupMenu M = null;
	boolean Trigger = false;
	public int MaxLength = 48;

	public HistoryTextField(final DoActionListener l, final String name) {
		super(l, name);
		H = new ListClass();
		H.append(new ListElement(""));
		addKeyListener(this);
	}

	public HistoryTextField(final DoActionListener l, final String name,
			final int s) {
		super(l, name, s);
		H = new ListClass();
		H.append(new ListElement(""));
		addKeyListener(this);
	}

	public void keyPressed(final KeyEvent ev) {
		switch (ev.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
			if (M == null) {
				M = new PopupMenu();
				ListElement e = H.last();
				int i = 0;
				final int n = Global.getParameter("history.length", 10);
				while (e != null && i < n) {
					final String t = (String) e.content();
					if (!t.equals("")) {
						final MenuItem item = new MenuItemAction(this, FileName
								.chop(t, MaxLength), t);
						M.add(item);
					}
					e = e.previous();
					i++;
				}
				add(M);
			}
			M.show(this, 10, 10);
			break;
		default:
			return;
		}
	}

	public void keyReleased(final KeyEvent e) {
	}

	public void keyTyped(final KeyEvent e) {
	}

	String Last = "";

	public void remember(final String s) {
		if (s.equals(Last))
			return;
		deleteFromHistory(s);
		Last = s;
		H.last().content(s);
		H.append(new ListElement(""));
		M = null;
	}

	public void deleteFromHistory(final String s) {
		ListElement e = H.first();
		while (e != null) {
			final String t = (String) e.content();
			final ListElement next = e.next();
			if (t.equals(s)) {
				H.remove(e);
				if (H.first() == null)
					H.append(new ListElement(""));
			}
			e = next;
		}
	}

	public void remember() {
		remember(getText());
	}

	public void saveHistory(final String name) {
		int i;
		final int n = Global.getParameter("history.length", 10);
		Global.removeAllParameters("history." + name);
		ListElement e = H.last();
		if (e == null)
			return;
		for (i = 0; i < n && e != null; e = e.previous()) {
			final String s = (String) e.content();
			if (!s.equals("")) {
				i++;
				Global.setParameter("history." + name + "." + i, s);
			}
		}
	}

	public void loadHistory(final String name) {
		int i = 1;
		H = new ListClass();
		H.append(new ListElement(""));
		while (Global.haveParameter("history." + name + "." + i)) {
			final String s = Global.getParameter("history." + name + "." + i,
			"");
			if (!s.equals("") && filterHistory(s))
				H.prepend(new ListElement(s));
			i++;
		}
	}

	public boolean filterHistory(final String name) {
		return true;
	}

	public ListClass getHistory() {
		return H;
	}

	public void setTrigger(final boolean f) {
		Trigger = f;
	}

	public void doAction(final String o) {
		if (!o.equals("")) {
			setText(o);
			if (Trigger)
				triggerAction();
		}
	}

	public void itemAction(final String o, final boolean flag) {
	}

	public static void main(final String args[]) {
		final CloseFrame f = new CloseFrame("test");
		final HistoryTextField t = new HistoryTextField(f, "Test", 30);
		t.remember("AAAA");
		t.remember("BBBB");
		t.remember("CCCC");
		t.remember("DDDD");
		f.setLayout(new BorderLayout());
		f.add("Center", t);
		f.add("South", new HistoryTextFieldChoice(t));
		f.pack();
		f.setVisible(true);
	}
}
