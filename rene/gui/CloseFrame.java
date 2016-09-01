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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.util.Hashtable;

class ToFrontDelay extends Thread {
	CloseFrame F;
	final int Delay = 500;

	public ToFrontDelay(final CloseFrame f) {
		F = f;
		start();
	}

	@Override
	public void run() {
		try {
			sleep(Delay);
		} catch (final Exception e) {
		}
		F.toFront();
		F.requestFocus();
	}
}

/**
 * A Frame, which can be closed with the close button in the window frame.
 * <p>
 * This frame may set an icon. The icon file must be a GIF with 16x16 dots in
 * 256 colors. We use the simple method, which does not work in the Netscape
 * browser.
 * <p>
 * This Frame is a DoActionListener. Thus it is possible to use TextFieldAction
 * etc. in it. Override doAction(String) and itemAction(String,boolean) to react
 * on events.
 * <p>
 * Sometimes the Frame wants to set the focus to a certain text field. To
 * support this, override focusGained().
 */

public class CloseFrame extends javax.swing.JFrame implements WindowListener,
ActionListener, DoActionListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CloseFrame(final String s) {
		super(s);
		addWindowListener(this);
		addFocusListener(this);
	}

	public CloseFrame() {
		addWindowListener(this);
		addFocusListener(this);
	}

	public void windowActivated(final WindowEvent e) {
	}

	public void windowClosed(final WindowEvent e) {
	}

	public void windowClosing(final WindowEvent e) {
		if (close())
			doclose();
	}

	public void windowDeactivated(final WindowEvent e) {
	}

	public void windowDeiconified(final WindowEvent e) {
	}

	public void windowIconified(final WindowEvent e) {
	}

	public void windowOpened(final WindowEvent e) {
	}

	/**
	 * @return if the frame should close now.
	 */
	public boolean close() {
		return true;
	}

	public void actionPerformed(final ActionEvent e) {
		doAction(e.getActionCommand());
	}

	public void doAction(final String o) {
		if ("Close".equals(o) && close())
			doclose();
	}

	/**
	 * Closes the frame. Override, if necessary, and call super.doclose().
	 */
	public void doclose() {
		setMenuBar(null); // for Linux ?!
		setVisible(false);
		// Because of a bug in Linux Java 1.4.2 etc.
		// dispose in a separate thread.
		final Thread t = new Thread() {
			@Override
			public void run() {
				// Global.disposeCurrentJZF();
			}
		};
		t.start();
	}

	public void itemAction(final String o, final boolean flag) {
	}

	// the icon things
	static Hashtable Icons = new Hashtable();

	public void seticon(final String file) {
		try {
			final Object o = Icons.get(file);
			if (o == null) {
				Image i;
				final InputStream in = getClass().getResourceAsStream(
						"/" + file);
				int pos = 0;
				int n = in.available();
				final byte b[] = new byte[20000];
				while (n > 0) {
					final int k = in.read(b, pos, n);
					if (k < 0)
						break;
					pos += k;
					n = in.available();
				}
				i = Toolkit.getDefaultToolkit().createImage(b, 0, pos);
				final MediaTracker T = new MediaTracker(this);
				T.addImage(i, 0);
				T.waitForAll();
				Icons.put(file, i);
				setIconImage(i);
			} else {
				setIconImage((Image) o);
			}
		} catch (final Exception e) {
		}
	}

	/**
	 * Override to set the focus somewhere.
	 */
	public void focusGained(final FocusEvent e) {
	}

	public void focusLost(final FocusEvent e) {
	}

	/**
	 * Note window position in Global.
	 */
	public void notePosition(final String name) {
		final Point l = getLocation();
		final Dimension d = getSize();
		Global.setParameter(name + ".x", l.x);
		Global.setParameter(name + ".y", l.y);
		Global.setParameter(name + ".w", d.width);
		if (d.height - Global.getParameter(name + ".h", 0) != 19)
			// works around a bug in Windows
			Global.setParameter(name + ".h", d.height);
		if ((getExtendedState() & Frame.MAXIMIZED_BOTH) != 0)
			Global.setParameter(name + ".maximized", true);
		else
			Global.removeParameter(name + ".maximized");
	}

	/**
	 * Set window position and size.
	 */
	public void setPosition(final String name) {
		if (Global.getParameter(name + ".maximized", false)) {
			setExtendedState(Frame.MAXIMIZED_BOTH);
			return;
		}
		final Point l = getLocation();
		final Dimension d = getSize();
		final Dimension dscreen = getToolkit().getScreenSize();
		int x = Global.getParameter(name + ".x", l.x);
		int y = Global.getParameter(name + ".y", l.y);
		int w = Global.getParameter(name + ".w", d.width);
		int h = Global.getParameter(name + ".h", d.height);
		if (w > dscreen.width)
			w = dscreen.width;
		if (h > dscreen.height)
			h = dscreen.height;
		if (x < 0)
			x = 0;
		if (x + w > dscreen.width)
			x = dscreen.width - w;
		if (y < 0)
			y = 0;
		if (y + h > dscreen.height)
			y = dscreen.height - h;
		setLocation(x, y);
		setSize(w, h);
	}

	public void front() {
		new ToFrontDelay(this);
	}

	public void center() {
		final Dimension dscreen = getToolkit().getScreenSize();
		final Dimension d = getSize();
		setLocation((dscreen.width - d.width) / 2,
				(dscreen.height - d.height) / 2);
	}

	public void centerOut(final Frame f) {
		final Dimension si = f.getSize(), d = getSize(), dscreen = getToolkit()
		.getScreenSize();
		final Point lo = f.getLocation();
		int x = lo.x + si.width - getSize().width + 20;
		int y = lo.y + si.height / 2 + 40;
		if (x + d.width > dscreen.width)
			x = dscreen.width - d.width - 10;
		if (x < 10)
			x = 10;
		if (y + d.height > dscreen.height)
			y = dscreen.height - d.height - 10;
		if (y < 10)
			y = 10;
		setLocation(x, y);
	}
}
