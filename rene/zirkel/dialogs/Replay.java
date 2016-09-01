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
 
 
 package rene.zirkel.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Vector;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.Global;
import rene.gui.IconBar;
import rene.gui.IconBarListener;
import rene.gui.Panel3D;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.TextObject;

public class Replay extends CloseDialog implements IconBarListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ZirkelCanvas ZC;
	int Last;
	Construction C;
	Vector V; // Copied vector of visible objects
	boolean HaveBreaks;

	public Replay(final Frame f, final ZirkelCanvas zc) {
		super(f, Global.name("replay.title"), true);
		ZC = zc;
		

		C = zc.getConstruction();

		// Collect all visible Elements:
		V = new Vector();
		final Enumeration e = C.elements();
		while (e.hasMoreElements()) {
			final ConstructionObject o = (ConstructionObject) e.nextElement();
			if (!o.mustHide(ZC)
					|| o.isBreakHide()
					|| (o instanceof TextObject && o.valid() && !o
							.isSuperHidden())) {
				V.addElement(o);
			}
		}

		HaveBreaks = haveBreaks();

		Last = 0;
		setLast();
		setEnabled(true);

		pack();
	}

	public void iconPressed(final String o) {
		if (o.equals("close")) {
			doclose();
		} else if (o.equals("allback")) {
			Last = 0;
			setLast();
		} else if (o.equals("allforward")) {
			Last = V.size();
			setLast();
		} else if (o.equals("fastback")) {
			Last -= 10;
			if (Last < 0)
				Last = 0;
			setLast();
		} else if (o.equals("fastforward")) {
			Last += 10;
			if (Last > V.size())
				Last = V.size();
			setLast();
		} else if (o.equals("oneforward")) {
			Last++;
			if (Last > V.size())
				Last = V.size();
			setLast();
		} else if (o.equals("nextbreak")) {
			while (true) {
				Last++;
				if (Last > V.size()) {
					Last = V.size();
					break;
				}
				if (Last >= V.size()
						|| (Last > 0 && ((ConstructionObject) V.elementAt(Last))
								.isBreak()))
					break;
			}
			setLast();
		} else if (o.equals("setbreak")) {
			if (Last > 0) {
				final ConstructionObject ob = (ConstructionObject) V
				.elementAt(Last);
				ob.setBreak(!ob.isBreak());
				if (ob.isBreak() )
					ob.setHideBreak(true);
			}
			HaveBreaks = haveBreaks();
		} else if (o.equals("oneback")) {
			Last--;
			if (Last < 0)
				Last = 0;
			setLast();
		}
		start();
	}

	public void start() {

	}

	@Override
	public void doclose() {
		ZC.paintUntil(null);
		ZC.showStatus();
		final Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			final ConstructionObject o = (ConstructionObject) e.nextElement();
			if (o instanceof TextObject)
				((TextObject) o).setDoShow(false);
		}
		ZC.repaint();
		super.doclose();
	}

	ConstructionObject O = null;

	public void checkLast() {
		if (O != null)
			((TextObject) O).setDoShow(false);
		O = null;
		if (Last > V.size() - 1)
			return;
		O = (ConstructionObject) V.elementAt(Last);
		if (O != null && (O instanceof TextObject))
			((TextObject) O).setDoShow(true);
		else
			O = null;
	}

	public void setLast() {
		if (Last < V.size()) {
			final ConstructionObject o = (ConstructionObject) V.elementAt(Last);
			checkLast();
			ZC.paintUntil(o);
		} else {
			Last = V.size();
			checkLast();
			ZC.paintUntil(null);
		}
		if (Last >= 0 && Last < V.size()) {
			final ConstructionObject o = (ConstructionObject) V.elementAt(Last);
			ZC.showStatus(o.getName() + " : " + o.getText());
		} else {
		}
	}

	public boolean haveBreaks() {
		final Enumeration e = V.elements();
		while (e.hasMoreElements()) {
			if (((ConstructionObject) e.nextElement()).isBreak())
				return true;
		}
		return false;
	}
}
