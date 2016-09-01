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
 
 
 package rene.dialogs;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;

import eric.JEricPanel;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.Global;
import rene.gui.MyLabel;
import rene.gui.MyPanel;

/**
 * This is a simple yes/no question. May be used as modal or non-modal dialog.
 * Modal Question dialogs must be overriden to do something sensible with the
 * tell method. In any case setVible(true) must be called in the calling
 * program.
 * <p>
 * The static YesString and NoString may be overriden for foreign languages.
 */

public class Question extends CloseDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int Result;
	Object O;
	Frame F;
	public static int NO = 0, YES = 1, ABORT = -1;

	public Question(final Frame f, final String c, final String title,
			final Object o, final boolean abort, final boolean flag) {
		super(f, title, flag);
		F = f;
		final JEricPanel pc = new MyPanel();
		final FlowLayout fl = new FlowLayout();
		pc.setLayout(fl);
		fl.setAlignment(FlowLayout.CENTER);
		pc.add(new MyLabel(" " + c + " "));
		getContentPane().add("Center", pc);
		final JEricPanel p = new MyPanel();
		p.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p.add(new ButtonAction(this, Global.name("yes"), "Yes"));
		p.add(new ButtonAction(this, Global.name("no"), "No"));
		if (abort)
			p.add(new ButtonAction(this, Global.name("abort"), "Abort"));
		getContentPane().add("South", p);
		O = o;
		pack();
	}

	public Question(final Frame f, final String c, final String title,
			final Object o, final boolean flag) {
		this(f, c, title, o, true, flag);
	}

	public Question(final Frame f, final String c, final String title) {
		this(f, c, title, null, true, true);
	}

	public Question(final Frame f, final String c, final String title,
			final boolean abort) {
		this(f, c, title, null, abort, true);
	}

	@Override
	public void doAction(final String o) {
		if (o.equals("Yes")) {
			tell(this, O, YES);
		} else if (o.equals("No")) {
			tell(this, O, NO);
		} else if (o.equals("Abort")) {
			tell(this, O, ABORT);
			Aborted = true;
		}
	}

	/**
	 * Needs to be overriden for modal usage. Should dispose the dialog.
	 */
	public void tell(final Question q, final Object o, final int f) {
		Result = f;
		doclose();
	}

	/**
	 * @return if the user pressed yes.
	 */
	public boolean yes() {
		return Result == YES;
	}

	public int getResult() {
		return Result;
	}
}
