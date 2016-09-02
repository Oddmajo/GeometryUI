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
 
 
 package rene.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.io.PrintWriter;
import java.util.Enumeration;

import eric.JEricPanel;

import rene.gui.Global;
import rene.gui.Panel3D;
import rene.util.MyVector;

/**
 * An extended Version of the Viewer. It is able to reformat lines, when the
 * area is resized. It has no vertical scrollbar. Text is stored into a separate
 * string buffer, and will be formatted on repaint.
 */

public class ExtendedViewer extends Viewer implements AdjustmentListener,
MouseListener, MouseMotionListener, ActionListener, KeyListener,
WheelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextDisplay TD;
	Scrollbar Vertical;
	TextPosition Start, End;
	PopupMenu PM;
	int X, Y;
	JEricPanel P3D;
	MyVector V; // Vector of lines
	StringBuffer B; // Buffer for last line
	boolean Changed = false;

	public ExtendedViewer() {
		TD = new TextDisplay(this);
		setLayout(new BorderLayout());
		add("Center", P3D = new Panel3D(TD));
		add("East", Vertical = new Scrollbar(Scrollbar.VERTICAL, 0, 100, 0,
				1100));
		Vertical.addAdjustmentListener(this);
		TD.addMouseListener(this);
		TD.addMouseMotionListener(this);
		Start = End = null;
		PM = new PopupMenu();
		MenuItem mi = new MenuItem(Global.name("block.copy", "Copy"));
		mi.addActionListener(this);
		PM.add(mi);
		PM.addSeparator();
		mi = new MenuItem(Global.name("block.begin", "Begin Block"));
		mi.addActionListener(this);
		PM.add(mi);
		mi = new MenuItem(Global.name("block.end", "End Block"));
		mi.addActionListener(this);
		PM.add(mi);
		add(PM);
		final Wheel W = new Wheel(this);
		addMouseWheelListener(W);
		V = new MyVector();
		B = new StringBuffer();
	}

	@Override
	public void setFont(final Font f) {
		TD.init(f);
	}

	@Override
	public void appendLine(final String s) {
		B.append(s);
		V.addElement(B.toString());
		B.setLength(0);
		Changed = true;
	}

	public void newLine() {
		V.addElement(B.toString());
		B.setLength(0);
		Changed = true;
	}

	@Override
	public void appendLine(final String s, final Color c) {
		appendLine(s);
	}

	@Override
	public void append(final String s) {
		B.append(s);
	}

	@Override
	public void append(final String s, final Color c) {
		append(s);
	}

	@Override
	public void doUpdate(final boolean showlast) {
	}

	public void update() {
		resized();
		showFirst();
	}

	@Override
	public void adjustmentValueChanged(final AdjustmentEvent e) {
		if (e.getSource() == Vertical) {
			switch (e.getAdjustmentType()) {
			case AdjustmentEvent.UNIT_INCREMENT:
				TD.verticalUp();
				break;
			case AdjustmentEvent.UNIT_DECREMENT:
				TD.verticalDown();
				break;
			case AdjustmentEvent.BLOCK_INCREMENT:
				TD.verticalPageUp();
				break;
			case AdjustmentEvent.BLOCK_DECREMENT:
				TD.verticalPageDown();
				break;
			default:
				final int v = Vertical.getValue();
			Vertical.setValue(v);
			TD.setVertical(v);
			return;
			}
			setVerticalScrollbar();
		}
	}

	@Override
	public void setVerticalScrollbar() {
		if (Vertical == null)
			return;
		final int h = TD.computeVerticalSize();
		Vertical.setValues(TD.computeVertical(), h, 0, 1000 + h);
	}

	@Override
	public void setText(final String S) {
		TD.unmark();
		Start = End = null;
		TD.setText(S);
		V.removeAllElements();
		B.setLength(0);
		setVerticalScrollbar();
	}

	@Override
	public void save(final PrintWriter fo) {
		TD.save(fo);
	}

	@Override
	public void appendLine0(final String s) {
		appendLine(s);
	}

	@Override
	public void appendLine0(final String s, final Color c) {
		appendLine(s);
	}

	boolean Dragging = false;

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if (e.isPopupTrigger() || e.isMetaDown()) {
			PM.show(e.getComponent(), e.getX(), e.getY());
			X = e.getX();
			Y = e.getY();
		} else {
			TD.unmark(Start, End);
			Start = TD.getposition(e.getX(), e.getY());
			Start.oneleft();
			End = null;
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(150, 200);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(150, 200);
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		Dragging = false;
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		TD.unmark(Start, End);
		final TextPosition h = TD.getposition(e.getX(), e.getY());
		if (h != null)
			End = h;
		TD.mark(Start, End);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String o = e.getActionCommand();
		if (o.equals(Global.name("block.copy", "Copy")))
			TD.copy(Start, End);
		else if (o.equals(Global.name("block.begin", "Begin Block"))) {
			TD.unmark(Start, End);
			Start = TD.getposition(X, Y);
			Start.oneleft();
			if (End == null && TD.L.last() != null) {
				End = TD.lastpos();
			}
			TD.mark(Start, End);
		} else if (o.equals(Global.name("block.end", "End Block"))) {
			TD.unmark(Start, End);
			End = TD.getposition(X, Y);
			if (Start == null && TD.L.first() != null) {
				Start = new TextPosition(TD.L.first(), 0, 0);
			}
			TD.mark(Start, End);
		}
	}

	@Override
	public void keyPressed(final KeyEvent e) {
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C
				&& Start != null && End != null) {
			TD.copy(Start, End);
		}
	}

	@Override
	public void keyTyped(final KeyEvent e) {
	}

	@Override
	public void setTabWidth(final int t) {
		TD.setTabWidth(t);
	}

	@Override
	public void showFirst() {
		TD.showFirst();
		setVerticalScrollbar();
		TD.repaint();
	}

	@Override
	public void showLast() {
		TD.showlast();
		setVerticalScrollbar();
		TD.repaint();
	}

	@Override
	public boolean hasFocus() {
		return false;
	}

	@Override
	public void setBackground(final Color c) {
		TD.setBackground(c);
		P3D.setBackground(c);
		super.setBackground(c);
	}

	@Override
	public void up(final int n) {
		for (int i = 0; i < n; i++)
			TD.verticalUp();
		setVerticalScrollbar();
	}

	@Override
	public void down(final int n) {
		for (int i = 0; i < n; i++)
			TD.verticalDown();
		setVerticalScrollbar();
	}

	@Override
	public void pageUp() {
		TD.verticalPageUp();
		setVerticalScrollbar();
	}

	@Override
	public void pageDown() {
		TD.verticalPageDown();
		setVerticalScrollbar();
	}

	@Override
	public void paint(final Graphics G) {
		super.paint(G);
	}

	public void doAppend(final String s) {
		final char a[] = s.toCharArray();
		final int w[] = TD.getwidth(a);
		int start = 0, end = 0;
		final int W = TD.getSize().width;
		int goodbreak;
		while (start < a.length && a[start] == ' ')
			start++;
		if (start >= a.length) {
			TD.appendLine("");
			return;
		}
		int blanks = 0;
		String sblanks = "";
		int offset = 0;
		if (start > 0) {
			blanks = start;
			sblanks = new String(a, 0, blanks);
			offset = blanks + w[0];
		}
		while (start < a.length) {
			int tw = TD.Offset + offset;
			end = start;
			goodbreak = start;
			while (end < a.length && tw < W) {
				tw += w[end];
				if (a[end] == ' ')
					goodbreak = end;
				end++;
			}
			if (tw < W)
				goodbreak = end;
			if (goodbreak == start)
				goodbreak = end;
			if (blanks > 0)
				TD
				.appendLine(sblanks
						+ new String(a, start, goodbreak - start));
			else
				TD.appendLine(new String(a, start, goodbreak - start));
			start = goodbreak;
			while (start < a.length && a[start] == ' ')
				start++;
		}
	}

	@Override
	public synchronized void resized() {
		if (TD.getSize().width <= 0)
			return;
		TD.setText("");
		final Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			final String s = (String) e.nextElement();
			doAppend(s);
		}
		TD.repaint();
	}

	public void mouseWheelMoved(final MouseWheelEvent arg0) {
	}

}
